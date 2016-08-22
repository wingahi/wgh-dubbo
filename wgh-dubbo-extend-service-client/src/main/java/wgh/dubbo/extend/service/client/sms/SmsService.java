package wgh.dubbo.extend.service.client.sms;

import javax.jws.WebService;

@WebService
public interface SmsService {
	
	/**
	 * 生成验证码
	 * @param phoneNum
	 * @return 返回验证码
	 */
	public  int sendCode(String phoneNum) throws Exception;
	
}
