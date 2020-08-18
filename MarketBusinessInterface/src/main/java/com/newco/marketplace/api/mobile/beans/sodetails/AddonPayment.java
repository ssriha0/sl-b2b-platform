package com.newco.marketplace.api.mobile.beans.sodetails;


import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * This is a generic bean class for storing addonPayment information.
 * @author Infosys
 *
 */

@XStreamAlias("addonPayment")
public class AddonPayment {

	@XStreamAlias("paymentType")
	private String paymentType;

	@XStreamAlias("checkNumber")
	private String checkNumber;

	@XStreamAlias("ccNumber")
	private String ccNumber;

	@XStreamAlias("ccType")
	private String ccType;

	@XStreamAlias("cardExpireMonth")
	private Integer cardExpireMonth;

	@XStreamAlias("cardExpireYear")
	private Integer cardExpireYear;
	
	@XStreamAlias("preAuthNumber")
	private String preAuthNumber;

	@XStreamAlias("amountAuthorized")
	private Double amountAuthorized;
	
	@XStreamOmitField
	private String maskedAccNumber;
	
	

	public String getMaskedAccNumber() {
		return maskedAccNumber;
	}

	public void setMaskedAccNumber(String maskedAccNumber) {
		this.maskedAccNumber = maskedAccNumber;
	}

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

}
