package wgh.dubbo.extend.service.client.dto.weixin;

import java.io.Serializable;

public class FBXWeixinPayReq implements Serializable
{
	private static final long serialVersionUID = 4724329871133228441L;

	private String payPlatformName;

	private String payType;

	private String orderNo;

	private double totalFee;

	private String type;

	private String notifyUrl;

	private String nonceStr;

	private String paySuccessUrl;
	
	private String payCanncelUrl;
	
	public String getPayCanncelUrl()
	{
		return payCanncelUrl;
	}

	public void setPayCanncelUrl(String payCanncelUrl)
	{
		this.payCanncelUrl = payCanncelUrl;
	}

	private String productName;

	public String getPayPlatformName()
	{
		return payPlatformName;
	}

	public void setPayPlatformName(String payPlatformName)
	{
		this.payPlatformName = payPlatformName;
	}

	public String getPayType()
	{
		return payType;
	}

	public void setPayType(String payType)
	{
		this.payType = payType;
	}
	 
	public String getOrderNo()
	{
		return orderNo;
	}

	public void setOrderNo(String orderNo)
	{
		this.orderNo = orderNo;
	}

	public double getTotalFee()
	{
		return totalFee;
	}

	public void setTotalFee(double totalFee)
	{
		this.totalFee = totalFee;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getNotifyUrl()
	{
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl)
	{
		this.notifyUrl = notifyUrl;
	}

	public String getNonceStr()
	{
		return nonceStr;
	}

	public void setNonceStr(String nonceStr)
	{
		this.nonceStr = nonceStr;
	}


	public String getPaySuccessUrl()
	{
		return paySuccessUrl;
	}

	public void setPaySuccessUrl(String paySuccessUrl)
	{
		this.paySuccessUrl = paySuccessUrl;
	}

	public String getProductName()
	{
		return productName;
	}

	public void setProductName(String productName)
	{
		this.productName = productName;
	}

}
