package com.newco.marketplace.api.mobile.beans.updateSoCompletion;


/**
 * This is a generic bean class for storing addOn Payment Details information.
 * author Infosys
 *
 */
public class AddOnPaymentHistoryDetails {
	
	private String paymentType;
	private String checkNumber;
	private String ccNumber;
	private String ccType;
	private String expirationDate;
	private String preAuthNumber;
	private String amountAuthorized;
	private Integer year;
	private Integer month;
	private String soId;
	private String maskedAccNum;
	private String token;
	private String response;
	private Double amount;
	
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

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getPreAuthNumber() {
		return preAuthNumber;
	}

	public void setPreAuthNumber(String preAuthNumber) {
		this.preAuthNumber = preAuthNumber;
	}

	public String getAmountAuthorized() {
		return amountAuthorized;
	}

	public void setAmountAuthorized(String amountAuthorized) {
		this.amountAuthorized = amountAuthorized;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getMaskedAccNum() {
		return maskedAccNum;
	}

	public void setMaskedAccNum(String maskedAccNum) {
		this.maskedAccNum = maskedAccNum;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	
	
}
