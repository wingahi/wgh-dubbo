package wgh.dubbo.extend.service.client.weixin;

import javax.jws.WebService;

import wgh.dubbo.extend.service.client.dto.weixin.UnifiedorderDto;
import wgh.dubbo.extend.service.client.dto.weixin.UnifiedorderRespDto;
import wgh.dubbo.extend.service.client.dto.weixin.WxAccessTokenDto;
import wgh.dubbo.extend.service.client.dto.weixin.WxUserInfoDto;



//微信相关接口
@WebService
public interface WeixinService {

	//获取jsapi_ticket
	public String getJsapiTicket() throws Exception;
	
	//获取openId
	public String getOpenId(String code) throws Exception;
		
	//公众号提交预支付(统一下单）
	public UnifiedorderRespDto unifiedorder(UnifiedorderDto unifiedorderDto) throws Exception;
	
	//微信App提交预支付（统一下单）
	public UnifiedorderRespDto appUnifiedorder(UnifiedorderDto unifiedorderDto) throws Exception;
	/**
	 * 获取微信用户信息
	 * @param access_token
	 * @param openid
	 * @param lang
	 * @return
	 * @throws Exception
	 */
	public WxUserInfoDto getWxUserInfo(String access_token,String openid,String lang) throws Exception;
	/**
	 * 根据前端传入的code获取微信用户信息
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public WxUserInfoDto getWxUserInfoByCode(String code,String appId,String secret) throws Exception; 
	/**
	 * 获取微信token
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public WxAccessTokenDto getWxAccessToken(String code,String appId,String secret) throws Exception;
}
