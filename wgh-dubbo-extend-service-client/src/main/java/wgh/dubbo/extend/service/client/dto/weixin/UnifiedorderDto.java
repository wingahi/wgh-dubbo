package wgh.dubbo.extend.service.client.dto.weixin;

@SuppressWarnings("serial")
public class UnifiedorderDto implements java.io.Serializable {
	
	private String nonceStr;
	private String body;
	private String outTradeNo;
	private String totalFee;
	private String tradeType;
	private String openid;
	private Integer attach;//附加参数
	
	public Integer getAttach() {
		return attach;
	}
	public void setAttach(Integer attach) {
		this.attach = attach;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public String getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}

}
