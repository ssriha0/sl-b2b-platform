package com.newco.marketplace.api.mobile.beans.updateSoCompletion;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * This is a generic bean class for storing addOn Payment Details information.
 * @author Infosys
 *
 */
@XStreamAlias("addOnPaymentDetails")
public class AddOnPaymentDetails {
	
	@XStreamAlias("paymentType")
	private String paymentType;
	
	@XStreamAlias("checkNumber")
	private String checkNumber;
	
	@XStreamAlias("ccNumber")
	private String ccNumber;
	
	@XStreamAlias("ccType")
	private String ccType;
	
	@XStreamAlias("expirationDate")
	private String expirationDate;
	
	@XStreamAlias("preAuthNumber")
	private String preAuthNumber;
	
	@XStreamAlias("amountAuthorized")
	private String amountAuthorized;

	@XStreamOmitField
	private Integer year;
	@XStreamOmitField
	private Integer month;
	@XStreamOmitField
	private String soId;
	
	@XStreamOmitField
	private String maskedAccNum;
	
	@XStreamOmitField
	private String token;
	
	
	@XStreamOmitField
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

	
	
}
