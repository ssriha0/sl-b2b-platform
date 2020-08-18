package com.newco.marketplace.dto.vo.serviceorder;

public class PaymentDetailsVO {

	private String paymentType;
	private String checkNumber;
	private String ccNumber;
	private String ccType;
	private Integer cardExpireMonth;	
	private Integer cardExpireYear;	
	private String preAuthNumber;	
	private Double amountAuthorized;	
	private String maskedAccNumber;
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getCheckNumber() {
		return checkNumber;
	}
	public void setCheckNumber(String checkNumber) {
		this.checkNumber = checkNumber;
	}
	public String getCcNumber() {
		return ccNumber;
	}
	public void setCcNumber(String ccNumber) {
		this.ccNumber = ccNumber;
	}
	public String getCcType() {
		return ccType;
	}
	public void setCcType(String ccType) {
		this.ccType = ccType;
	}
	public Integer getCardExpireMonth() {
		return cardExpireMonth;
	}
	public void setCardExpireMonth(Integer cardExpireMonth) {
		this.cardExpireMonth = cardExpireMonth;
	}
	public Integer getCardExpireYear() {
		return cardExpireYear;
	}
	public void setCardExpireYear(Integer cardExpireYear) {
		this.cardExpireYear = cardExpireYear;
	}
	public String getPreAuthNumber() {
		return preAuthNumber;
	}
	public void setPreAuthNumber(String preAuthNumber) {
		this.preAuthNumber = preAuthNumber;
	}
	public Double getAmountAuthorized() {
		return amountAuthorized;
	}
	public void setAmountAuthorized(Double amountAuthorized) {
		this.amountAuthorized = amountAuthorized;
	}
	public String getMaskedAccNumber() {
		return maskedAccNumber;
	}
	public void setMaskedAccNumber(String maskedAccNumber) {
		this.maskedAccNumber = maskedAccNumber;
	}
}
