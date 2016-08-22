package wgh.dubbo.openapi.server.weixin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import wgh.dubbo.common.Consts;
import wgh.dubbo.common.utils.MD5Crypter;
import wgh.dubbo.common.utils.Utils;
import wgh.dubbo.extend.service.client.dto.weixin.UnifiedorderDto;
import wgh.dubbo.extend.service.client.dto.weixin.UnifiedorderRespDto;
import wgh.dubbo.extend.service.client.weixin.WeixinService;
import wgh.dubbo.openapi.server.BaseController;
import wgh.dubbo.openapi.server.common.ObjectResponse;



@Controller()
@RequestMapping(produces = "application/json;charset=UTF-8",value = "/weixinApp")
public class WeiXinAppPayController extends BaseController {
	@Autowired
	private WeixinService weixinService;
	
	
	/**
	 * 微信支付
	 * @param out_trade_no 商户订单号
	 * @param body 商品描述
	 * @param nonce_str 随机字符串
	 * @param total_fee 总金额
	 * @param trade_type 交易类型
	 * @param order_type 交易类型   1充值，2保障会员3，购买保险
	 * @param token 验证密钥
	 * @return
	 */
	@RequestMapping(value= "/weixinAppPay")
	public @ResponseBody ObjectResponse weiXinConfirm(
			@RequestParam(value = "out_trade_no", required = true) String out_trade_no,
			@RequestParam(value = "body", required = true) String body,
			@RequestParam(value = "nonce_str", required = true) String nonce_str,
			@RequestParam(value = "total_fee", required = true) String total_fee,
			@RequestParam(value = "trade_type", required = true) String trade_type,
			@RequestParam(value = "order_type", required = true) int order_type,
			@RequestParam(value = "token", required = true) String token){
		ObjectResponse response = new ObjectResponse(Consts.API_CODE_ERROR, "");
        boolean isExist = true;
		try {
			
			if(isExist){
				StringBuffer buffer = new StringBuffer();
				buffer.append("body=").append(body);
				buffer.append("&").append("nonce_str=").append(nonce_str);
				buffer.append("&").append("order_type=").append(order_type);
				buffer.append("&").append("out_trade_no=").append(out_trade_no);
				buffer.append("&").append("total_fee=").append(total_fee);
				buffer.append("&").append("trade_type=").append(trade_type);
				buffer.append("&").append("HpCp0TvERZM7sKwe3nrH9quUKklkhTe8");
				if(!token.equals(MD5Crypter.MD5EncodeForUtf8LC(buffer.toString()))){
					response.setRetCode(Consts.API_CODE_ERROR);
					response.setRetMsg("签名验证失败！");
				}else{
					UnifiedorderDto unifiedorderDto = new UnifiedorderDto();
			    	unifiedorderDto.setNonceStr(nonce_str);
			    	unifiedorderDto.setBody(body);
			    	unifiedorderDto.setOutTradeNo(out_trade_no);
			    	unifiedorderDto.setTotalFee(total_fee);
			    	unifiedorderDto.setTradeType(trade_type);
			    	unifiedorderDto.setAttach(order_type);
			    	UnifiedorderRespDto res = weixinService.appUnifiedorder(unifiedorderDto);
			    	response.setResult(res);
					response.setRetCode(Consts.API_CODE_SUCCESS);
				}
			}else{
				response.setRetCode(Consts.API_CODE_ERROR);
				response.setRetMsg("订单号不存在！");
			}
		} catch (Exception e) {
			response.setRetCode(Consts.API_CODE_ERROR);
			response.setRetMsg(e.getMessage());
			log.error(e.getMessage(),e);
		}
		return response;
	}
}
