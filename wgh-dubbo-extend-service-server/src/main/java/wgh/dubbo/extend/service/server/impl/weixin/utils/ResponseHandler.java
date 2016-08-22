package wgh.dubbo.extend.service.server.impl.weixin.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom2.JDOMException;

import wgh.dubbo.common.utils.Utils;
import wgh.dubbo.extend.service.server.BaseService;




/**
 * 微信支付服务器签名支付请求应答类
 * api说明： 
 * getKey()/setKey(),获取/设置密钥
 * getParameter()/setParameter(),获取/设置参数值 getAllParameters(),获取所有参数
 * isTenpaySign(),是否财付通签名,true:是 false:否
 * getDebugInfo(),获取debug信息
 */
public class ResponseHandler extends BaseService{

	/** 密钥 */
	private String key;

	/** 应答的参数 */
	private SortedMap parameters;

	/** debug信息 */
	private String debugInfo;

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String uriEncoding;

	/**
	 * 构造函数
	 * 
	 * @param request
	 * @param response
	 */
	public ResponseHandler(HttpServletRequest request,HttpServletResponse response) {
		this.request = request;
		this.response = response;

		this.key = "";
		this.parameters = new TreeMap();
		this.debugInfo = "";

		this.uriEncoding = "";

		//demo代码
		Map m = this.request.getParameterMap();
		Iterator it = m.keySet().iterator();
		while (it.hasNext()) {
			String k = (String) it.next();
			String v = ((String[]) m.get(k))[0];
			this.setParameter(k, v);
		}
		
//		//demo代码验证不通过，新写实现(根据“支付结果通用通知：举例”返回参数)
//		ServletInputStream sis = null;  
//        String xmlData = null;   
//        try {  
//            // 取HTTP请求流  
//            sis = request.getInputStream();  
//            // 取HTTP请求流长度  
//            int size = request.getContentLength();  
//            // 用于缓存每次读取的数据  
//            byte[] buffer = new byte[size];  
//            // 用于存放结果的数组  
//            byte[] xmldataByte = new byte[size];  
//            int count = 0;  
//            int rbyte = 0;  
//            // 循环读取  
//            while (count < size) {   
//                // 每次实际读取长度存于rbyte中  
//                rbyte = sis.read(buffer);   
//                for(int i=0;i<rbyte;i++) {  
//                    xmldataByte[count + i] = buffer[i];  
//                }  
//                count += rbyte;  
//            } 
//            xmlData = new String(xmldataByte, "UTF-8");  
//        }catch (Exception e) {
//			log.error(e.getMessage(),e);
//		}
//        Document document = null;
//		try {
//			document = DocumentHelper.parseText(xmlData);
//		} catch (DocumentException e1) {
//			log.error(e1.getMessage(),e1);
//		}
//		Element root = document.getRootElement(); 
//        String appid = root.elementTextTrim("appid");
//        String bank_type = root.elementTextTrim("bank_type");
//        String cash_fee = root.elementTextTrim("cash_fee");
//        String device_info = root.elementTextTrim("device_info");
//        String fee_type = root.elementTextTrim("fee_type");
//        String is_subscribe = root.elementTextTrim("is_subscribe");
//        String mch_id = root.elementTextTrim("mch_id");
//        String nonce_str = root.elementTextTrim("nonce_str");
//        String openid = root.elementTextTrim("openid");
//        String out_trade_no = root.elementTextTrim("out_trade_no");
//        String result_code = root.elementTextTrim("result_code");
//        String return_code = root.elementTextTrim("return_code");
//        String sign = root.elementTextTrim("sign");
//        String time_end = root.elementTextTrim("time_end");
//        String total_fee = root.elementTextTrim("total_fee");
//        String trade_type = root.elementTextTrim("trade_type");
//        String transaction_id = root.elementTextTrim("transaction_id");
//
//        this.setParameter("appid", appid);
//        this.setParameter("bank_type", bank_type);
//        this.setParameter("cash_fee", cash_fee);
//        this.setParameter("device_info", device_info);
//        this.setParameter("fee_type", fee_type);
//        this.setParameter("is_subscribe", is_subscribe);
//        this.setParameter("mch_id", mch_id);
//        this.setParameter("nonce_str", nonce_str);
//        this.setParameter("openid", openid);
//        this.setParameter("out_trade_no", out_trade_no);
//        this.setParameter("result_code", result_code);
//        this.setParameter("return_code", return_code);
//        this.setParameter("sign", sign);
//        this.setParameter("time_end", time_end);
//        this.setParameter("total_fee", total_fee);
//        this.setParameter("trade_type", trade_type);
//        this.setParameter("transaction_id", transaction_id);

	}

	/**
	 *获取密钥
	 */
	public String getKey() {
		return key;
	}

	/**
	 *设置密钥
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * 获取参数值
	 * @param parameter 参数名称
	 * @return String
	 */
	public String getParameter(String parameter) {
		String s = (String) this.parameters.get(parameter);
		return (null == s) ? "" : s;
	}

	/**
	 * 设置参数值
	 * @param parameter参数名称
	 * @param parameterValue参数值
	 */
	public void setParameter(String parameter, String parameterValue) {
		String v = "";
		if (null != parameterValue) {
			v = parameterValue.trim();
		}
		this.parameters.put(parameter, v);
	}

	/**
	 * 返回所有的参数
	 * 
	 * @return SortedMap
	 */
	public SortedMap getAllParameters() {
		return this.parameters;
	}
	public void doParse(String xmlContent) throws JDOMException, IOException {
		this.parameters.clear();
		//解析xml,得到map
		Map m = XMLUtil.doXMLParse(xmlContent);
		
		//设置参数
		Iterator it = m.keySet().iterator();
		while(it.hasNext()) {
			String k = (String) it.next();
			String v = (String) m.get(k);
			this.setParameter(k, v);
		}
	}
	/**
	 * 是否财付通签名,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
	 * 
	 * @return boolean
	 */
	public boolean isTenpaySign() {
		StringBuffer sb = new StringBuffer();
		Set es = this.parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (!"sign".equals(k) && null != v && !"".equals(v)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + this.getKey());
		// 算出摘要
		String enc = TenpayUtil.getCharacterEncoding(this.request,this.response);
		String sign = MD5Util.MD5Encode(sb.toString(), enc).toLowerCase();
		String tenpaySign = this.getParameter("sign").toLowerCase();
		boolean bool = tenpaySign.equals(sign);
		if(!bool){
			log.info(sb.toString() + "=>sign:" +sign + ",tenpaySign:"+tenpaySign);
			System.out.println(sb.toString() + "=>sign:" +sign + ",tenpaySign:"+tenpaySign);
		}
		return bool;
	}
	
	public boolean isTenpayInsureSign() {
		StringBuffer sb = new StringBuffer();
		Set es = this.parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (!"sign".equals(k) && null != v && !"".equals(v)) {
				try {
					String result = new String(v.getBytes("iso-8859-1"),"utf-8");
					sb.append(k + "=" + result + "&");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		String resutl = sb.toString().substring(0, sb.toString().length()-1);
		resutl = resutl.concat("2P83ReOdG00PTaub");
		// 算出摘要
		String enc = TenpayUtil.getCharacterEncoding(this.request,this.response);
		String sign = MD5Util.MD5Encode(resutl, enc).toLowerCase();
		String tenpaySign = this.getParameter("sign").toLowerCase();
		boolean bool = tenpaySign.equals(sign);
		if(!bool){
			log.info(resutl + "=>sign:" +sign + ",tenpaySign:"+tenpaySign);
			System.out.println(resutl + "=>sign:" +sign + ",tenpaySign:"+tenpaySign);
		}
		return bool;
	}
	
	
	public boolean isTenpaySign(Map map) {
		if(Utils.isEmpty(map)){
			System.out.println("参数map为空！");
			log.error("参数为空！");
			return false;
		}
		StringBuffer sb = new StringBuffer();
		Set es = map.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (!"sign".equals(k) && null != v && !"".equals(v)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + this.getKey());
		// 算出摘要
		String enc = TenpayUtil.getCharacterEncoding(this.request,this.response);
		String sign = MD5Util.MD5Encode(sb.toString(), enc).toLowerCase();
		String tenpaySign =String.valueOf(map.get("sign")).toLowerCase();
		boolean bool = tenpaySign.equals(sign);
		if(!bool){
			log.info(sb.toString() + "=>sign:" +sign + ",tenpaySign:"+tenpaySign);
			System.out.println(sb.toString() + "=>sign:" +sign + ",tenpaySign:"+tenpaySign);
		}
		return bool;
	}

	/**
	 * 返回处理结果给财付通服务器。
	 * 
	 * @param msg
	 *            : Success or fail。
	 * @throws IOException
	 */
	public void sendToCFT(String msg) throws IOException {
		String strHtml = msg;
		PrintWriter out = this.getHttpServletResponse().getWriter();
		out.println(strHtml);
		out.flush();
		out.close();

	}

	/**
	 * 获取uri编码
	 * 
	 * @return String
	 */
	public String getUriEncoding() {
		return uriEncoding;
	}

	/**
	 * 设置uri编码
	 * 
	 * @param uriEncoding
	 * @throws UnsupportedEncodingException
	 */
	public void setUriEncoding(String uriEncoding)
			throws UnsupportedEncodingException {
		if (!"".equals(uriEncoding.trim())) {
			this.uriEncoding = uriEncoding;

			// 编码转换
			String enc = TenpayUtil.getCharacterEncoding(request, response);
			Iterator it = this.parameters.keySet().iterator();
			while (it.hasNext()) {
				String k = (String) it.next();
				String v = this.getParameter(k);
				v = new String(v.getBytes(uriEncoding.trim()), enc);
				this.setParameter(k, v);
			}
		}
	}

	/**
	 *获取debug信息
	 */
	public String getDebugInfo() {
		return debugInfo;
	}
	/**
	 *设置debug信息
	 */
	protected void setDebugInfo(String debugInfo) {
		this.debugInfo = debugInfo;
	}

	protected HttpServletRequest getHttpServletRequest() {
		return this.request;
	}

	protected HttpServletResponse getHttpServletResponse() {
		return this.response;
	}

}
