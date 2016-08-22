package wgh.dubbo.extend.service.server.impl.sms;

import java.io.IOException;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;








import wgh.dubbo.common.utils.Utils;
import wgh.dubbo.common.utils.WebClient;
import wgh.dubbo.common.utils.WebClient.HTTPMethod;
import wgh.dubbo.extend.service.client.sms.ChuanglanService;
import wgh.dubbo.extend.service.server.BaseService;

import com.alibaba.fastjson.JSON;


public class ChuanglanServiceImpl extends BaseService implements ChuanglanService {

	@Value("${cl.account}")
	String clAccount;
	@Value("${cl.pswd}")
	String clPswd;
	@Value("${cl.send_url}")
	String clSendurl;

	@Override
	public String sendVerifyCode(String mobile) {
		String verifyCode = Utils.getRandom(6);
  		WebClient web = new WebClient(clSendurl, HTTPMethod.POST);
  		web.addPostParameter("account", clAccount);
  		web.addPostParameter("pswd", clPswd);
  		web.addPostParameter("mobile", mobile);
  		web.addPostParameter("msg", "您的验证码是：" + verifyCode + "。请不要把验证码泄露给其他人。【疯蜜】");
  		web.addPostParameter("needstatus", "true");
  		web.setTimeout(5000, 10000);
  		String resp = null;
  		try {
  			resp = web.getTextContent();
  		} catch (IOException e) {
  			log.error(e.getMessage(), e);
  			return "";
  		}
  		System.out.println(JSON.toJSONString(resp));
  		String code = "-1";
  		if(Utils.isNotEmpty(resp)){
  			resp = resp.replace("\n", ",");
  			String[] result = resp.split(",");
  			if(result.length >= 2){
  				code = result[1];
  			}
  		}
  		if("0".equals(code)){
  			return verifyCode;
  		}else{
			log.error("发送短信异常mobile=" + mobile + ":" + JSON.toJSONString(resp));
		}
        return "";
	}

}
