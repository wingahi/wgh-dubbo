package wgh.dubbo.extend.service.server.impl.weixin;

import java.io.IOException;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.jws.WebService;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import wgh.dubbo.cache.impl.redis.RedisUtil;
import wgh.dubbo.common.Consts;
import wgh.dubbo.common.utils.Utils;
import wgh.dubbo.common.utils.WebClient;
import wgh.dubbo.common.utils.WebClient.HTTPMethod;
import wgh.dubbo.extend.service.client.dto.weixin.UnifiedorderDto;
import wgh.dubbo.extend.service.client.dto.weixin.UnifiedorderRespDto;
import wgh.dubbo.extend.service.client.dto.weixin.WxAccessTokenDto;
import wgh.dubbo.extend.service.client.dto.weixin.WxUserInfoDto;
import wgh.dubbo.extend.service.client.weixin.WeixinService;
import wgh.dubbo.extend.service.server.BaseService;
import wgh.dubbo.extend.service.server.impl.weixin.utils.RequestHandler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class WeixinServiceImpl extends BaseService implements WeixinService{


	
	@Value("${weixin.appid}")
	private String appid;
	@Value("${weixin.secret}")
	private String secret;
	@Value("${weixin.key}")
	private String key;
	@Value("${weixin.mchid}")
	private String mchid;
	@Value("${weixinBindPayURL}")
	private String weixinBindPayURL;
	
	
	@Value("${weixinApp.key}")
	private String appKey;
	@Value("${weixinApp.appid}")
	private String appAppid;
	@Value("${weixinApp.secret}")
	private String appSecret;
	@Value("${weixinApp.mchid}")
	private String appMchid;
	@Value("${weixinAppBindPayURL}")
	private String weixinAppBindPayURL;
	
	@Autowired
	private RedisUtil redisUtil;
	
	
	@Override
	public String getJsapiTicket() {
		// TODO Auto-generated method stub
		String ticket = this.getTicketCache();
		return ticket;
	}

	
	/**
	 * 从缓存里取jsapi_ticket
	 * @return
	 */
    public String getTicketCache() {
    	String tichet = "";
		String tichetKey = "ticket";
    	try {
    		 tichet = redisUtil.get(key);
			if (Utils.isNull(tichet)) {
				log.info("cache is empty,start to add ticketCache cache");
				tichet = this.putTicketCache( tichetKey);
			} 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
    	return tichet;
	}
    
	/**
	 * 保存到缓存
	 * @param cacheName ,key
	 */
	public String putTicketCache(String key) {
		//获取access_token
		String accessToken = "";
		String accessTokenKey = redisUtil.get(key);
		
		if(Utils.isEmpty(accessToken)){
			log.error("accessToken is null");
			return "";
		}
		
		//获得jsapi_ticket
		String jsapiTicketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?" +
				"access_token=" +accessToken + "&type=jsapi";
		WebClient ticketWeb = new WebClient(jsapiTicketUrl, HTTPMethod.GET);
		ticketWeb.setTimeout(5000, 10000);
		String ticketResp = null;
		try {
			ticketResp = ticketWeb.getTextContent();
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			return "";
		}
		
		log.info("获取jsapi_ticket：" + JSON.toJSONString(ticketResp));
		String ticket = "";
		Integer timeOut = null;
        JSONObject ticketJsonObj = JSON.parseObject(ticketResp);
        if(Utils.isNotNull(ticketJsonObj)){
        	ticket = ticketJsonObj.getString("ticket");
        	timeOut = ticketJsonObj.getInteger("expires_in");
		}
		if(Utils.isEmpty(ticket)){
			log.error("ticket is null");
		}else{
			if(Utils.isNull(timeOut)){
				timeOut = 7200;
			}
			redisUtil.setex( key, ticket, timeOut);
		}
		
		return ticket;
	}
	
	/**
	 * 保存accessToken到缓存
	 * @param cacheName ,key
	 */
	public String putAccessTokenCache(String key) {
		//获取access_token
		String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential" +
				"&appid=" +appid +"&secret=" +secret;
		WebClient web = new WebClient(accessTokenUrl, HTTPMethod.GET);
		web.setTimeout(5000, 10000);
		String resp = null;
		try {
			resp = web.getTextContent();
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			return "";
		}

		log.info("获取access_token：" + JSON.toJSONString(resp));
		String accessToken = "";
		Integer timeOut = null;
        JSONObject jsonObj = JSON.parseObject(resp);
        if(Utils.isNotNull(jsonObj)){
        	accessToken = jsonObj.getString("access_token");
        	timeOut = jsonObj.getInteger("expires_in");
		}
		if(Utils.isEmpty(accessToken)){
			log.error("accessToken is null");
		}else{
			if(Utils.isNull(timeOut)){
				timeOut = 7200;
			}
			redisUtil.setex( key, accessToken, timeOut);
		}
		
		return accessToken;
	}
	
	
	@Override
	public String getOpenId(String code) {
		// TODO Auto-generated method stub
		String openId = "";
		if(Utils.isEmpty(code)){
			log.error("getOpenId：code is null");
			return openId;
		}
		String openIdUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
				"appid=" +appid + "&secret=" +secret + "&code=" +code + "&grant_type=authorization_code";
		WebClient ticketWeb = new WebClient(openIdUrl, HTTPMethod.GET);
		ticketWeb.setTimeout(5000, 10000);
		String openIdResp = null;
		try {
			openIdResp = ticketWeb.getTextContent();
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			return "";
		}
		JSONObject openIdJsonObj = JSON.parseObject(openIdResp);
		if(Utils.isNotNull(openIdJsonObj)){
			openId = openIdJsonObj.toJSONString();
		}
		
		return openId;
	}

	
	
	
	@Override
	public UnifiedorderRespDto unifiedorder(UnifiedorderDto unifiedorderDto)  {
		// TODO Auto-generated method stub
		RequestHandler queryReq = new RequestHandler(null, null);
		queryReq.setKey(key);
		queryReq.init();
		
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", appid);
		packageParams.put("mch_id", mchid);
		packageParams.put("device_info", "WEB");
		packageParams.put("nonce_str", unifiedorderDto.getNonceStr());
		packageParams.put("body", unifiedorderDto.getBody());
		packageParams.put("out_trade_no", unifiedorderDto.getOutTradeNo());
		packageParams.put("fee_type", "CNY");
		packageParams.put("total_fee", unifiedorderDto.getTotalFee());
		packageParams.put("notify_url", weixinBindPayURL);
		packageParams.put("trade_type", unifiedorderDto.getTradeType());
		packageParams.put("openid", unifiedorderDto.getOpenid());
		
		String xmlResp = queryReq.unifiedorder(packageParams);
		log.info(JSON.toJSONString(xmlResp));
		Document document = null;
		try {
			document = DocumentHelper.parseText(xmlResp);
		} catch (DocumentException e1) {
			log.error(e1.getMessage(),e1);
		}
		Element root = document.getRootElement(); 
        String returnCode = root.elementTextTrim("return_code");
        String returnMsg = root.elementTextTrim("return_msg");
        String resultCode = root.elementTextTrim("result_code");
        String appid = root.elementTextTrim("appid");
        String mchId = root.elementTextTrim("mch_id");
        String deviceInfo = root.elementTextTrim("device_info");
        String sign = root.elementTextTrim("sign");
        String prepayId = root.elementTextTrim("prepay_id");
        String tradeType = root.elementTextTrim("trade_type");
        
		UnifiedorderRespDto dto = new UnifiedorderRespDto();
		dto.setReturnCode(returnCode);
		dto.setReturnMsg(returnMsg);
		dto.setResultCode(resultCode);
		dto.setAppid(appid);
		dto.setMchId(mchId);
		dto.setDeviceInfo(deviceInfo);
		dto.setSign(sign);
		dto.setPrepayId(prepayId);
		dto.setTradeType(tradeType);
		return dto;
	}

	@Override
	public UnifiedorderRespDto appUnifiedorder(UnifiedorderDto unifiedorderDto) throws Exception {
		RequestHandler queryReq = new RequestHandler(null, null);
		queryReq.setKey(appKey);
		queryReq.init();
		
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", appAppid);
		packageParams.put("mch_id", appMchid);
		packageParams.put("nonce_str", unifiedorderDto.getNonceStr());
		packageParams.put("body", unifiedorderDto.getBody());
		packageParams.put("device_info", "WEB");
		packageParams.put("out_trade_no", unifiedorderDto.getOutTradeNo());
		packageParams.put("fee_type", "CNY");
		packageParams.put("total_fee", unifiedorderDto.getTotalFee());
		packageParams.put("notify_url", weixinAppBindPayURL);
		packageParams.put("trade_type", unifiedorderDto.getTradeType());
		packageParams.put("attach", String.valueOf(unifiedorderDto.getAttach()));
		
		String xmlResp = queryReq.unifiedorder(packageParams);
		log.info(JSON.toJSONString(xmlResp));
		Document document = null;
		try {
			document = DocumentHelper.parseText(xmlResp);
		} catch (DocumentException e1) {
			log.error(e1.getMessage(),e1);
		}
		Element root = document.getRootElement(); 
        String returnCode = root.elementTextTrim("return_code");
        String returnMsg = root.elementTextTrim("return_msg");
        String resultCode = root.elementTextTrim("result_code");
        String appid = root.elementTextTrim("appid");
        String mchId = root.elementTextTrim("mch_id");
        String deviceInfo = root.elementTextTrim("device_info");
        String nonceStr = root.elementText("nonce_str");
        String sign = root.elementTextTrim("sign");
        String prepayId = root.elementTextTrim("prepay_id");
        String tradeType = root.elementTextTrim("trade_type");
        
        if("SUCCESS".equals(returnCode)){
        	
        }
		UnifiedorderRespDto dto = new UnifiedorderRespDto();
		dto.setReturnCode(returnCode);
		dto.setReturnMsg(returnMsg);
		dto.setResultCode(resultCode);
		dto.setAppid(appid);
		dto.setMchId(mchId);
		dto.setDeviceInfo(deviceInfo);
		dto.setSign(sign);
		dto.setPrepayId(prepayId);
		dto.setTradeType(tradeType);
		dto.setNonceStr(nonceStr);
		return dto;
	}

	@Override
	public WxUserInfoDto getWxUserInfo(String access_token, String openid,
			String lang) throws Exception {
		// TODO Auto-generated method stub
		if(Utils.isEmpty(access_token) || Utils.isEmpty(openid)){
			log.error("getWxUserInfoDto：access_token or code is null");
		}
		System.out.println("getWxUserInfo:access_token:"+access_token+",openid:"+openid);
		String openIdUrl = "https://api.weixin.qq.com/sns/userinfo?access_token="+access_token+"&openid="+openid;
		WebClient ticketWeb = new WebClient(openIdUrl, HTTPMethod.GET);
		ticketWeb.setTimeout(5000, 10000);
		String openIdResp = null;
		WxUserInfoDto wxUserInfoDto=null;
		try {
			openIdResp = ticketWeb.getTextContent();
			System.out.println("getWxUserInfoResult:"+openIdResp);
			if(Utils.isNotNull(openIdResp)){
			   wxUserInfoDto = JSON.parseObject(openIdResp, WxUserInfoDto.class);
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			
		}
		return wxUserInfoDto;
	}

	@Override
	public WxUserInfoDto getWxUserInfoByCode(String code,String appId,String secret) throws Exception {
		// TODO Auto-generated method stub
		WxAccessTokenDto wxAccessTokenDto=this.getWxAccessToken(code, appId, secret);

		WxUserInfoDto wxUserInfoDto = null;
		if(Utils.isNotNull(wxAccessTokenDto)){
			wxUserInfoDto = this.getWxUserInfo(wxAccessTokenDto.getAccess_token(), wxAccessTokenDto.getOpenid(), null);
		}
		return wxUserInfoDto;
	}

	@Override
	public WxAccessTokenDto getWxAccessToken(String code,String appId,String secret) throws Exception {
		// TODO Auto-generated method stub
		if(Utils.isEmpty(code)){
			log.error("getWxAccessToken：code is null");
			return null;
		}
		
		System.out.println("code:"+code);
		
		String openIdUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
				"appid=" +appId + "&secret=" +secret + "&code=" +code + "&grant_type=authorization_code";
		WebClient ticketWeb = new WebClient(openIdUrl, HTTPMethod.GET);
		ticketWeb.setTimeout(5000, 10000);
		String openIdResp = null;
		WxAccessTokenDto wxAccessTokenDto=null;
		try {
			openIdResp = ticketWeb.getTextContent();
			System.out.println("getOpenIdResult:"+openIdResp);
			if(Utils.isNotNull(openIdResp)){
				wxAccessTokenDto=JSONObject.parseObject(openIdResp, WxAccessTokenDto.class);
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		
		return wxAccessTokenDto;
	}
	
}
