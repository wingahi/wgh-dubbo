package wgh.dubbo.extend.service.server.impl.weixin;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom2.JDOMException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;









import wgh.dubbo.common.utils.Utils;
import wgh.dubbo.extend.service.server.impl.weixin.utils.RequestHandler;
import wgh.dubbo.extend.service.server.impl.weixin.utils.ResponseHandler;
import wgh.dubbo.extend.service.server.impl.weixin.utils.XMLUtil;




import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@SuppressWarnings("serial")
@Component("WxAppPayNotifyServlet")
public class WxAppPayNotifyServlet extends HttpServlet {
	public final Log log = LogFactory.getLog(this.getClass());
	/**
	 * Constructor of the object.
	 */
	public WxAppPayNotifyServlet() {
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
		SortedMap map = new TreeMap();
		try {
			map = (SortedMap) XMLUtil.doXMLParse(request);
		} catch (JDOMException e1) {
			System.out.println("获取微信支付回调信息失败！");
			log.error("获取微信支付回调信息失败！", e1);
		}
		String resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
		//创建支付应答对象
		ResponseHandler resHandler = new ResponseHandler(request, response);
		resHandler.setKey("HpCp0TvERZM7sKwe3nrH9quUKklkhTe8");
		//创建请求对象
		RequestHandler queryReq = new RequestHandler(null, null);
		queryReq.init();
		
		String returnCode = String.valueOf(map.get("return_code"));
		String returnMsg = String.valueOf(map.get("return_msg"));
		if("SUCCESS".equals(returnCode)){
			returnCode = String.valueOf(map.get("err_code"));
			returnMsg = String.valueOf(map.get("err_code_des"));
		}
		String errorcode = returnCode;
		String errormsg = returnMsg;
		String result_code  = "";//支付结果
		String orderNum = "";//商户订单号
		String transaction_id = "";//微信支付订单号
		String attach = "";//原样返回参数
		String timeEnd = "";//支付完成时间
		int total_fee = 0;//金额,以分为单位
		ServletContext context = request.getSession().getServletContext();
		WebApplicationContext webContext = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
		//InsurePayBillService payBillService = (InsurePayBillService)webContext.getBean("InsurePayBillService");
		if(resHandler.isTenpaySign(map) == true) {
			System.out.println("签名成功进来了！！！！！！！！！！！！");
			result_code  = String.valueOf(map.get("result_code"));
			total_fee = Integer.parseInt(String.valueOf(map.get("total_fee")));
			transaction_id = String.valueOf(map.get("transaction_id"));
			orderNum = String.valueOf(map.get("out_trade_no"));
			attach  = String.valueOf(map.get("attach"));
			timeEnd  = String.valueOf(map.get("time_end"));
			if("SUCCESS".equals(result_code)){
				//FinanceInsurePayBillDto payDto = null;
				try {
					//payDto = payBillService.searInsurePayInfo(orderNum, attach);
				} catch (Exception e) {
					errormsg = "获取支付订单信息错误，orderNum=" +orderNum + "," + e.getMessage();
				}
				if(Utils.isNull("payDto")){
					errormsg = "支付订单信息为空，orderNum=" +orderNum;
				}else{
					Double payAmount = 0.0;//payDto.getAmount();
					int orderPay = (int)(payAmount.floatValue());//微信是以分为单位
					if(orderPay != total_fee){
						errormsg = "金额不对应，orderPay" +orderPay + ",total_fee=" +total_fee;
					}else{
						//给财付通系统发送成功信息，财付通系统收到此结果后不再进行后续通知
						System.out.println("微信支付成功接收回调信息！！！！！！");
						resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
					}
				}
			}else{
				errormsg = "微信支付失败，result_code=" + result_code;
			}
			try {
				if("1".equals(attach)){
					log.info("更新充值支付订单状态！");
					//DepositInfoService depositInfoService = (DepositInfoService)webContext.getBean("depositInfoService");
					//depositInfoService.updateWeiXinDepositInfoState(orderNum, "SUCCESS".equals(result_code)?Consts.ORDER_STATE_PAY:Consts.ORDER_STATE_TAIL);
				}else if("2".equals(attach)){
					log.info("更新开通保障会员支付订单状态！");
					//UserEachHelpService eachHelpService = (UserEachHelpService)webContext.getBean("userEachHelpService");
					//eachHelpService.updatePaymentSuccess(orderNum, "SUCCESS".equals(result_code)?Consts.ORDER_STATE_PAY:Consts.ORDER_STATE_TAIL);
				}else if("3".equals(attach)){
					System.out.println("更新购买保险支付订单状态！！！！！！！！");
					log.info("更新购买保险支付订单状态！");
					//InsurePolicyService policyService = (InsurePolicyService)webContext.getBean("InsurePolicyService");
					//policyService.updateInsureBillState(orderNum, "SUCCESS".equals(result_code)?Consts.INSURE_PAY_SUCCEED:Consts.INSURE_PAY_TAIL);
				}else{
					System.out.println("申请加入会费制圈子成功接收支付回调结果！！！");
					if("SUCCESS".equals(result_code)){
						log.info("支付成功推送信息给圈主审批！");
//						CircleActivityService activityService = (CircleActivityService)webContext.getBean("circleActivityService");
//						ActivityOrderPayInfoDto orderPayInfoDto = activityService.getActivityOrderPayInfo(orderNum, 1);
//						List<String> userIds = activityService.getCircleMastersByOrderNum(String.valueOf(orderPayInfoDto.getArticleId()), "1");
//						MessageService messageService = (MessageService)webContext.getBean("messageService");
//						boolean reslut =  messageService.sendMsgIdToUserIds(orderPayInfoDto.getMsgId(), userIds);
//						MessageDto message = messageService.getMessage(orderPayInfoDto.getMsgId());
//						if(reslut){
//							log.info("成功推送信息给圈主审批！");
//							MessageDto messageDto = new MessageDto();
//							messageDto.setCastType(Consts.PUSH_CUSTOMIZEDCAST);
//							messageDto.setMsgTitle(Consts.PUSH_MSG_TITLE);
//							messageDto.setMsgContent("您的加入申请已成功发送！");
//							messageDto.setMsgType(Consts.JPUSH_MSG_TYPE_CIRCLE_ENTRY_RESULT);
//							messageDto.setIsShow(Consts.STATE_TRUE);
//							messageDto.setMsgCategory(Consts.RELATION_CIRCLE);
//							messageDto.setSenderUserId(0);
//							messageDto.setMsgDealType(0);
//							messageDto.setUserId(orderPayInfoDto.getUserId());
//							messageDto.setCreateTime(Utils.getCurrentDate());
//							messageDto.setIsShowBar(0);
////							messageDto.setDealNickName(user.getNickName()+"");
////							messageDto.setDealUserId(optUserId);
//							messageDto.setMsgDealResult(0);
////							messageDto.setMsgDealTime(Utils.getCurrentDate());
//							messageDto.setSend(true);
//							messageDto.setMsgRefId(0);
//							messageDto.setState(Consts.STATE_FALSE);
//							messageDto.setObjectId(orderPayInfoDto.getArticleId());
//
//							if (Utils.isNotNull(orderPayInfoDto.getArticleId())) {
//								messageDto.setObjectName(message.getObjectName());
//							}
//							Map<String, String> map1 = new HashMap<String, String>();
//							map1.put("join_reason", "您的加入申请已提交，将由群主或管理员进行审核。一旦审核通过，系统将会立即通知您；如果审核不通过将会退回您的费用，敬请留意，感谢参与。");
//							messageDto.setMsgDetail(JSONObject.toJSONString(map1));
//							messageService.sendJPushNoticeMessage(messageDto);
//							log.info("成功推送信息给申请人！");
//						}else{
//							log.info("推送信息给圈主审批失败！");
//						}
					}
				}
			} catch (Exception e) {
				errormsg = "更新保险订单状态错误，orderNum=" +orderNum + "," + e.getMessage();
			}
			System.out.println("-------------------开始更新支付信息---------");
		//	FinanceInsurePayBillDto payBillDto = new FinanceInsurePayBillDto();
//			try {
//				payBillDto.setTradeNo(transaction_id);
//				if(Utils.isNotEmpty(timeEnd)){
//					SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
//					SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//					payBillDto.setPayTime(format2.parse(format2.format(format.parse(timeEnd))));
//				}else{
//					payBillDto.setPayTime(Utils.getCurrentDate());
//				}
//				payBillDto.setStatus("SUCCESS".equals(result_code)?String.valueOf(Consts.ORDER_STATE_PAY):String.valueOf(Consts.ORDER_STATE_TAIL));
//				payBillDto.setErrorCode(errorcode);
//				payBillDto.setErrorMsg(errormsg);
//				payBillDto.setOrderNum(orderNum);
//				payBillService.updateInsurePayBill(payBillDto);
//				System.out.println("-------------------顺利结束更新支付信息---------");
//			} catch (Exception e) {
//				System.out.println("-------------------更新支付信息失败---------");
//				log.error("更新保险支付订单信息错误，orderNum=" +orderNum + "," + e.getMessage());
//			}
		}else{
			System.out.println("------微信支付回调签名验证失败--------");
			errormsg = "微信支付回调签名验证失败:" +JSON.toJSONString(map);
		}
		log.info(errormsg);
		System.out.println(errormsg);
		resHandler.sendToCFT(resXml);
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
