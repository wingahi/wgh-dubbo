package wgh.dubbo.extend.service.server.impl.weixin;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;








import wgh.dubbo.common.Consts;
import wgh.dubbo.common.utils.Utils;
import wgh.dubbo.extend.service.server.impl.weixin.utils.RequestHandler;
import wgh.dubbo.extend.service.server.impl.weixin.utils.ResponseHandler;

import com.alibaba.fastjson.JSON;

@SuppressWarnings("serial")
@Component("WxpayNotifyServlet")
public class WxpayNotifyServlet extends HttpServlet {

	public final Log log = LogFactory.getLog(this.getClass());
	
	/**
	 * Constructor of the object.
	 */
	public WxpayNotifyServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.info("微信支付回调++++++++++++++++++++");
		//创建支付应答对象
		ResponseHandler resHandler = new ResponseHandler(request, response);
		resHandler.setKey("FMJINRONG28492HGJ30F2JG348F9Y9I3");
		//创建请求对象
		RequestHandler queryReq = new RequestHandler(null, null);
		queryReq.init();
		
		String return_code = resHandler.getParameter("return_code");
		String return_msg = resHandler.getParameter("return_msg");
		if("SUCCESS".equals(return_code)){
			return_code = resHandler.getParameter("err_code");
			return_msg = resHandler.getParameter("err_code_des");
		}
		String errorcode = return_code;
		String errormsg = return_msg;
		String mch_id = "";//商户号
		String result_code  = "";//支付结果
		String orderNum = "";//商户订单号
		String transaction_id = "";//财付通订单号
		int total_fee = 0;//金额,以分为单位
		String openid = "";//用户标识
		String time_end = "";//支付完成时间
		String trade_type = "";//交易类型：JSAPI、NATIVE、APP
		ServletContext context = request.getSession().getServletContext();
		WebApplicationContext webContext = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
		//OrderInfoService orderInfoService = (OrderInfoService) webContext.getBean("orderInfoService");
		if(resHandler.isTenpaySign() == true) {
			mch_id = resHandler.getParameter("mch_id");
			orderNum = resHandler.getParameter("out_trade_no");
			transaction_id = resHandler.getParameter("transaction_id");
			total_fee = Integer.parseInt(resHandler.getParameter("total_fee"));
			result_code  = resHandler.getParameter("result_code");
			openid = resHandler.getParameter("openid");
			time_end = resHandler.getParameter("time_end");

			//判断签名及结果
			if("SUCCESS".equals(result_code)) {
				//即时到账处理业务开始
				//处理数据库逻辑
				//注意交易单不要重复处理
				//注意判断返回金额
				try{
//					OrderInfoDto orderInfoDto = null;
//					try {
//						orderInfoDto = orderInfoService.getOrderInfo(orderNum);
//					} catch (Exception e) {
//						errormsg = "获取订单信息错误，orderNum=" +orderNum + "," + e.getMessage();
//					}
//					if(Utils.isNull(orderInfoDto)){
//						errormsg = "订单信息为空，orderNum=" +orderNum;
//					}else{
//						Double payAmount = orderInfoDto.getPayAmount();
//						int orderPay = (int)(payAmount.floatValue() * 100);//微信是以分为单位
//						if(orderPay != total_fee){
//							errormsg = "金额不对应，orderPay" +orderPay + ",total_fee=" +total_fee;
//						}else if(orderInfoDto.getState() == Consts.ORDER_STATE_NO_PAY){
//							try {
//								orderInfoService.updateOrderInfoState(orderNum,Consts.ORDER_STATE_PAY);
//								//给财付通系统发送成功信息，财付通系统收到此结果后不再进行后续通知
//								resHandler.sendToCFT("success");
//							} catch (Exception e) {
//								errormsg = "更新订单支付状态错误，orderNum=" +orderNum + "," + e.getMessage();
//							}
//						}
//						//state已付款，返回成功
//						else{
//							//给财付通系统发送成功信息，财付通系统收到此结果后不再进行后续通知
//							resHandler.sendToCFT("success");
//						}
//					}
				}catch (Exception e) {
					log.error(e.getMessage(),e);
				}
			}else{
				errormsg = "微信支付失败，result_code=" + result_code;
			}
		}else{
			log.info(JSON.toJSONString(resHandler));
			errormsg = "微信支付回调签名验证失败" +JSON.toJSONString(resHandler);
		}
		
		//记录支付信息
//		OrderPayCacheService orderPayCacheService = (OrderPayCacheService) webContext.getBean("orderPayCacheService"); 
//		String cacheName = "payInfoCache";
//		String key = orderNum + errorcode;
//		int recordFlag = orderPayCacheService.getOrderPayInfo(cacheName, key);
//		if(recordFlag == 0){
//			OrderPayInfoDto orderPayInfoDto = new OrderPayInfoDto();
//			orderPayInfoDto.setMerchantAccount(mch_id);
//			orderPayInfoDto.setOrderNum(orderNum);
//			orderPayInfoDto.setTradeNo(transaction_id);
//			orderPayInfoDto.setIdentityId(openid);
//			orderPayInfoDto.setAmount(total_fee);
//			orderPayInfoDto.setStatus(result_code);
//			orderPayInfoDto.setErrorCode(errorcode);
//			orderPayInfoDto.setErrorMsg(errormsg);
//			orderPayInfoDto.setPayType(Consts.PAY_TYPE_WEIXIN);
//			orderPayInfoDto.setTradeType(trade_type);
//			if(Utils.isNotEmpty(time_end)){
//				Date payTime = Utils.convertStringToDate6(time_end);
//				orderPayInfoDto.setPayTime(payTime);
//			}
//			try {
//				orderInfoService.addOrderPayInfo(orderPayInfoDto);
//				orderPayCacheService.putOrderPayInfoCache(cacheName, key, 1);
//			} catch (Exception e) {
//				log.error(e.getMessage(),e);
//			}
//		}
		
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
