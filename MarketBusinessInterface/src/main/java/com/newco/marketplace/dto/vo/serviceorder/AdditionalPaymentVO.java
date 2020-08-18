package com.newco.marketplace.dto.vo.serviceorder;

import com.newco.marketplace.webservices.base.CommonVO;

/**
 * value object representing the so_additional_payment 
 * @author dpotlur
 *
 */

public class AdditionalPaymentVO extends CommonVO{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5510743211287465507L;
	private String soId;
	private String additionalPaymentDesc="";
	private String paymentType="";
	private Double paymentAmount;
	
	// Credit Cards
	private String cardType="";
	private Integer cardExpireMonth;
	private Integer cardExpireYear;
	private String authNumber = null;
	
	private String paymentReceivedDate;
	// Checks
	private String cardNo="";
	private String checkNo="";
	
	//SL-20853
	private String maskedAccountNo;
	private String token;
	private String encryptedCardNo;
	private boolean isTokenizedANdMasked = false;
	
	//R16_1_1: SL-21270
	private Integer addonId;

	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public String getAdditionalPaymentDesc() {
		return additionalPaymentDesc;
	}
	public void setAdditionalPaymentDesc(String additionalPaymentDesc) {
		this.additionalPaymentDesc = additionalPaymentDesc;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getCheckNo() {
		return checkNo;
	}
	public void setCheckNo(String checkNo) {
		this.checkNo = checkNo;
	}
	public Double getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
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
	public String getAuthNumber()
	{
		return authNumber;
	}
	public void setAuthNumber(String authNumber)
	{
		this.authNumber = authNumber;
	}
	public String getPaymentReceivedDate() {
		return paymentReceivedDate;
	}
	public void setPaymentReceivedDate(String paymentReceivedDate) {
		this.paymentReceivedDate = paymentReceivedDate;
	}
	//SL-20853
	public String getMaskedAccountNo() {
		return maskedAccountNo;
	}
	public void setMaskedAccountNo(String maskedAccountNo) {
		this.maskedAccountNo = maskedAccountNo;
	}
	public String getEncryptedCardNo() {
		return encryptedCardNo;
	}
	public void setEncryptedCardNo(String encryptedCardNo) {
		this.encryptedCardNo = encryptedCardNo;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public boolean isTokenizedANdMasked() {
		return isTokenizedANdMasked;
	}
	public void setTokenizedANdMasked(boolean isTokenizedANdMasked) {
		this.isTokenizedANdMasked = isTokenizedANdMasked;
	}
	public Integer getAddonId() {
		return addonId;
	}
	public void setAddonId(Integer addonId) {
		this.addonId = addonId;
	}
	

	

	

}
