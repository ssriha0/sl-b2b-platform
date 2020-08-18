package com.servicelive.esb.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Payment")
public class NPSPayment {

	@XStreamAlias("PaymentMethodIndicator")
	private String paymentMethodInd;

	@XStreamAlias("PaymentAccountNumber")
	private String paymentAccNo;

	@XStreamAlias("PaymentExpirationDate")
	private String paymentExpDate;

	@XStreamAlias("AuthorizationNumber")
	private String authNo;

	@XStreamAlias("SecondaryPaymentMethodIndicator")
	private String secpaymentMethodInd;

	@XStreamAlias("SecondaryPaymentAccountNumber")
	private String secPaymentAccNo;

	@XStreamAlias("SecondaryPaymentExpirationDate")
	private String secPaymentExpDate;

	@XStreamAlias("SecondaryAuthorizationNumber")
	private String secAuthNo;

	public String getPaymentMethodInd() {
		return paymentMethodInd;
	}

	public void setPaymentMethodInd(String paymentMethodInd) {
		this.paymentMethodInd = paymentMethodInd;
	}

	public String getPaymentAccNo() {
		return paymentAccNo;
	}

	public void setPaymentAccNo(String paymentAccNo) {
		this.paymentAccNo = paymentAccNo;
	}

	public String getPaymentExpDate() {
		return paymentExpDate;
	}

	public void setPaymentExpDate(String paymentExpDate) {
		this.paymentExpDate = paymentExpDate;
	}

	public String getAuthNo() {
		return authNo;
	}

	public void setAuthNo(String authNo) {
		this.authNo = authNo;
	}

	public String getSecpaymentMethodInd() {
		return secpaymentMethodInd;
	}

	public void setSecpaymentMethodInd(String secpaymentMethodInd) {
		this.secpaymentMethodInd = secpaymentMethodInd;
	}

	public String getSecPaymentAccNo() {
		return secPaymentAccNo;
	}

	public void setSecPaymentAccNo(String secPaymentAccNo) {
		this.secPaymentAccNo = secPaymentAccNo;
	}

	public String getSecPaymentExpDate() {
		return secPaymentExpDate;
	}

	public void setSecPaymentExpDate(String secPaymentExpDate) {
		this.secPaymentExpDate = secPaymentExpDate;
	}

	public String getSecAuthNo() {
		return secAuthNo;
	}

	public void setSecAuthNo(String secAuthNo) {
		this.secAuthNo = secAuthNo;
	}

}
