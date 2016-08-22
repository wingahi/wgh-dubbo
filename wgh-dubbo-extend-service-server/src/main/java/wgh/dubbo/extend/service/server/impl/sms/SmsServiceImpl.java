package wgh.dubbo.extend.service.server.impl.sms;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.jws.WebService;

import org.apache.commons.codec.Encoder;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;










import wgh.dubbo.common.utils.Utils;
import wgh.dubbo.common.utils.WebClient;
import wgh.dubbo.common.utils.WebClient.HTTPMethod;
import wgh.dubbo.extend.service.client.sms.SmsService;
import wgh.dubbo.extend.service.server.BaseService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


public class SmsServiceImpl extends BaseService implements SmsService {
	
	//@Value("${lsm.sms_url}")
	String lsmSmsUrl;
	//@Value("${lsm.sms_key}")
	String lsmSmsKey;

	@Override
	public int sendCode(String phoneNum) {
		int mobile_code = (int)((Math.random()*9+1)*100000);//验证码规则：6位随机数
  		WebClient web = new WebClient(lsmSmsUrl, HTTPMethod.POST);
  		//整合错误注释
//  		try {
//			web.getRequestHeaders().put("Authorization", "Basic " +
//					new Base64().encodeToString(("api:"+lsmSmsKey).getBytes("utf-8")));
//		} catch (UnsupportedEncodingException e) {
//			log.error(e.getMessage(),e);
//			return -1;
//		}
  		web.addPostParameter("mobile", phoneNum);
  		web.addPostParameter("message", "您的验证码是：" + mobile_code + "。请不要把验证码泄露给其他人。【疯蜜】");
  		web.setTimeout(5000, 10000);
  		String resp = null;
  		try {
  			resp = web.getTextContent();
  		} catch (IOException e) {
  			log.error(e.getMessage(), e);
  			return -1;
  		}

          JSONObject jsonObj = JSON.parseObject(resp);
          if(Utils.isNotNull(jsonObj)){
          	String errorCode = jsonObj.getString("error");
          	String msg = jsonObj.getString("msg");
          	if("0".equals(errorCode) && "ok".equalsIgnoreCase(msg)){
          		return mobile_code;
          	}else{
          		 log.error("发送短信异常=" + phoneNum + "#" + jsonObj);
          	}
  		}
          return -1;
	}

}
