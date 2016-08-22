package wgh.dubbo.extend.service.client.dto.weixin;

import java.io.Serializable;

public class FBXUnionPayReq implements Serializable
{
	/**
	 * 银联支付
	 */
	private static final long serialVersionUID = -7145376540309937850L;

	private String payPlatformName;

	private String payType;

	private String orderNo;

	private double totalFee;

	private String type;

	private String notifyUrl;;

	private String cardName;

	private String openBankId;

	private String cardNo;

	private String idType;

	private String idNumber;

	private String nonceStr;

	private String paySuccessUrl;

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

	public String getCardName()
	{
		return cardName;
	}

	public void setCardName(String cardName)
	{
		this.cardName = cardName;
	}

	public String getOpenBankId()
	{
		return openBankId;
	}

	public void setOpenBankId(String openBankId)
	{
		this.openBankId = openBankId;
	}

	public String getCardNo()
	{
		return cardNo;
	}

	public void setCardNo(String cardNo)
	{
		this.cardNo = cardNo;
	}

	public String getIdType()
	{
		return idType;
	}

	public void setIdType(String idType)
	{
		this.idType = idType;
	}

	public String getIdNumber()
	{
		return idNumber;
	}

	public void setIdNumber(String idNumber)
	{
		this.idNumber = idNumber;
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

}
