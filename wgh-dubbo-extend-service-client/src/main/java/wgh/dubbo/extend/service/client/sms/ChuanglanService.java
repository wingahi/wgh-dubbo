package wgh.dubbo.extend.service.client.sms;

import javax.jws.WebService;

@WebService
public interface ChuanglanService {
	
	/**
	 * 单号码发送验证码
	 * @param phoneNum
	 * @return 返回验证码
	 */
	public String sendVerifyCode(String mobile) throws Exception;
	
}
