package wgh.dubbo.openapi.server.weixin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import wgh.dubbo.openapi.server.common.SignUtil;
import wgh.dubbo.openapi.server.common.StringResponse;



@Controller()
@RequestMapping(produces = "application/json;charset=UTF-8",value = "/weixinH5")
public class WeiXinPublicController {
	
	/**
	 * 
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @param echostr
	 * @return
	 */
	@RequestMapping(value= "/sign")
	public @ResponseBody 
	StringResponse weiXinSign(
			@RequestParam(value = "signature", required = true) String signature,
			@RequestParam(value = "timestamp", required = true) String timestamp,
			@RequestParam(value = "nonce", required = true) String nonce,
			@RequestParam(value = "echostr", required = true) String echostr
			){
		StringResponse objectResponse = new StringResponse();
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，否则接入失败  
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {  
        	objectResponse.setResult(echostr);  
        }  
		return objectResponse;
	}
	
}
