/**
 * 
 */
package com.servicelive.esb.integration.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author sahmed
 *
 */
public class OmsBuyerNotification implements Serializable {

	/** generated serialVersionUID */
	private static final long serialVersionUID = 5645286987760795933L;
	private long omsBuyerNotificationId;
	private Long buyerNotificationId;
	private String techComment;
	private String modelNumber;
	private String serialNumber;
	private Date installationDate;
	private BigDecimal amountCollected;
	private String paymentMethod;
	private String paymentAccountNumber;
	private String paymentExpirationDate;
	private String paymentAuthorizationNumber;
	private String maskedAccountNo;
	private String token;
	
	public OmsBuyerNotification() {}

	public OmsBuyerNotification(long omsBuyerNotificationId,
			Long buyerNotificationId, String techComment, String modelNumber,
			String serialNumber, Date installationDate,
			BigDecimal amountCollected, String paymentMethod,
			String paymentAccountNumber, String paymentExpirationDate,
			String paymentAuthorizationNumber) {
		this.omsBuyerNotificationId = omsBuyerNotificationId;
		this.buyerNotificationId = buyerNotificationId;
		this.techComment = techComment;
		this.setModelNumber(modelNumber);
		this.setSerialNumber(serialNumber);
		this.installationDate = installationDate;
		this.amountCollected = amountCollected;
		this.paymentMethod = paymentMethod;
		this.paymentAccountNumber = paymentAccountNumber;
		this.paymentExpirationDate = paymentExpirationDate;
		this.paymentAuthorizationNumber = paymentAuthorizationNumber;
	}
	public OmsBuyerNotification(long omsBuyerNotificationId,
			Long buyerNotificationId, String techComment, String modelNumber,
			String serialNumber, Date installationDate,
			BigDecimal amountCollected, String paymentMethod,
			String paymentAccountNumber, String paymentExpirationDate,
			String paymentAuthorizationNumber,
			String maskedAccountNo,String token) {
		this.omsBuyerNotificationId = omsBuyerNotificationId;
		this.buyerNotificationId = buyerNotificationId;
		this.techComment = techComment;
		this.setModelNumber(modelNumber);
		this.setSerialNumber(serialNumber);
		this.installationDate = installationDate;
		this.amountCollected = amountCollected;
		this.paymentMethod = paymentMethod;
		this.paymentAccountNumber = paymentAccountNumber;
		this.paymentExpirationDate = paymentExpirationDate;
		this.paymentAuthorizationNumber = paymentAuthorizationNumber;
		this.maskedAccountNo = maskedAccountNo;
		this.token = token;
				
	}
	public BigDecimal getAmountCollected() {
		return this.amountCollected;
	}
	public Long getBuyerNotificationId() {
		return this.buyerNotificationId;
	}
	public Date getInstallationDate() {
		return this.installationDate;
	}
	public String getModelNumber() {
		return modelNumber;
	}
	public long getOmsBuyerNotificationId() {
		return this.omsBuyerNotificationId;
	}
	public String getPaymentAccountNumber() {
		return this.paymentAccountNumber;
	}
	public String getPaymentAuthorizationNumber() {
		return this.paymentAuthorizationNumber;
	}
	public String getPaymentExpirationDate() {
		return this.paymentExpirationDate;
	}
	public String getPaymentMethod() {
		return this.paymentMethod;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public String getTechComment() {
		return this.techComment;
	}
	public void setAmountCollected(BigDecimal amountCollected) {
		this.amountCollected = amountCollected;
	}
	public void setBuyerNotificationId(Long buyerNotificationId) {
		this.buyerNotificationId = buyerNotificationId;
	}
	public void setInstallationDate(Date installationDate) {
		this.installationDate = installationDate;
	}
	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}
	public void setOmsBuyerNotificationId(long omsBuyerNotificationId) {
		this.omsBuyerNotificationId = omsBuyerNotificationId;
	}
	public void setPaymentAccountNumber(String paymentAccountNumber) {
		this.paymentAccountNumber = paymentAccountNumber;
	}
	public void setPaymentAuthorizationNumber(
			String paymentAuthorizationNumber) {
		this.paymentAuthorizationNumber = paymentAuthorizationNumber;
	}
	
	public void setPaymentExpirationDate(String paymentExpirationDate) {
		this.paymentExpirationDate = paymentExpirationDate;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public void setTechComment(String techComment) {
		this.techComment = techComment;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public String getMaskedAccountNo() {
		return maskedAccountNo;
	}

	public void setMaskedAccountNo(String maskedAccountNo) {
		this.maskedAccountNo = maskedAccountNo;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
