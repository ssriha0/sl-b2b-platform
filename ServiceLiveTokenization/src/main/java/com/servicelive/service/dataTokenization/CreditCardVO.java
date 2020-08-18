/**
 * 
 */
package com.servicelive.service.dataTokenization;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement()
public class CreditCardVO implements Serializable{
	
	/**
	 * 
	 */
	protected static final long serialVersionUID = 6724803049756925009L;
	protected Long cardId;
	protected Long cardTypeId;
	protected Integer entityTypeId;
	protected Integer entityId;
	protected Integer countryId;
	protected Integer accountTypeId;
	protected Integer accountStatusId;
	protected String cardNo;
	protected String encCardNo;
	protected String expireDate;
	protected String cardVerificationNo;
	protected String cardHolderName;
	protected Date createdDate;
	protected Date modifiedDate;
	protected String modifiedBy;
	
	protected Integer locationId;
	protected Integer locationTypeId;
	protected Double transactionAmount;
	protected String address;
	protected String billingAddress1;
	protected String billingAddress2;
	protected String billingAptNo;
	protected String billingCity;
	protected String billingState;
	protected String billingLocationName;
	protected String zipcode;
	
	protected String transactionId;
	protected String authorizerRespCode;
	protected String authorizationCode;
	protected Double authorizationAmount;
	protected String transIdentifier;
	protected Date responseDate;
	protected boolean authorized;
	protected String response;
	protected Integer LedgerEntryRuleId;
	
	protected Integer businessTransId;
	protected Integer vendorId;
	protected boolean active_ind;
    protected boolean enabled_ind; 
	protected String fundingTypeId;
	
	protected String userName;
	
	protected boolean sendFulfillment;
	protected boolean default_ind;
	protected boolean dollarAuth;
	//PCI-Vault --START
	private long respId;
	private String hsTransAmount;
	private String responseCode;
	private String responseMessage;
	private String addlResponseData;
	private String authCd;
	private String token;
	private String maskedAccount;
	private String ajbKey;
	private String achProcessQueueID;
	//PCI-Vault --END
	protected String transAmount;

	public boolean isDollarAuth() {
		return dollarAuth;
	}
	public void setDollarAuth(boolean dollarAuth) {
		this.dollarAuth = dollarAuth;
	}

	//B2C start
	protected String cidResponseCode;
	protected String ansiResponseCode;
	protected String addrResponseCode;
	/**
	 * @return the accountStatusId
	 */
	public Integer getAccountStatusId() {
		return accountStatusId;
	}
	/**
	 * @return the accountTypeId
	 */
	public Integer getAccountTypeId() {
		return accountTypeId;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	public String getAddrResponseCode() {
		return addrResponseCode;
	}
	public String getAnsiResponseCode() {
		return ansiResponseCode;
	}
	/**
	 * @return the authorizationAmount
	 */
	public Double getAuthorizationAmount() {
		return authorizationAmount;
	}
	/**
	 * @return the authorizationCode
	 */
	public String getAuthorizationCode() {
		return authorizationCode;
	}
	/**
	 * @return the authorizerRespCode
	 */
	public String getAuthorizerRespCode() {
		return authorizerRespCode;
	}
	public String getBillingAddress1() {
		return billingAddress1;
	}
	public String getBillingAddress2() {
		return billingAddress2;
	}
	public String getBillingAptNo() {
		return billingAptNo;
	}
	public String getBillingCity() {
		return billingCity;
	}
	public String getBillingLocationName() {
		return billingLocationName;
	}
	public String getBillingState() {
		return billingState;
	}
	/**
	 * @return the businessTransId
	 */
	public Integer getBusinessTransId() {
		return businessTransId;
	}
	/**
	 * @return the cardHolderName
	 */
	public String getCardHolderName() {
		return cardHolderName;
	}
	/**
	 * @return the cardId
	 */
	public Long getCardId() {
		return cardId;
	}
	/**
	 * @return the cardNo
	 */
	public String getCardNo() {
		return cardNo;
	}
	/**
	 * @return the cardTypeId
	 */
	public Long getCardTypeId() {
		return cardTypeId;
	}
	/**
	 * @return the cardVerificationNo
	 */
	public String getCardVerificationNo() {
		return cardVerificationNo;
	}
	public String getCidResponseCode() {
		return cidResponseCode;
	}
	/**
	 * @return the countryId
	 */
	public Integer getCountryId() {
		return countryId;
	}
	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}
	/**
	 * @return the encCardNo
	 */
	public String getEncCardNo() {
		return encCardNo;
	}
	/**
	 * @return the entityId
	 */
	public Integer getEntityId() {
		return entityId;
	}
	/**
	 * @return the entityTypeId
	 */
	public Integer getEntityTypeId() {
		return entityTypeId;
	}
	/**
	 * @return the expireDate
	 */
	public String getExpireDate() {
		return expireDate;
	}
	public String getFundingTypeId() {
		return fundingTypeId;
	}
	public Integer getLedgerEntryRuleId() {
		return LedgerEntryRuleId;
	}
	public Integer getLocationId() {
		return locationId;
	}
	/**
	 * @return the locationTypeId
	 */
	public Integer getLocationTypeId() {
		return locationTypeId;
	}
	/**
	 * @return the modifiedBy
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}
	/**
	 * @return the modifiedDate
	 */
	public Date getModifiedDate() {
		return modifiedDate;
	}
	/**
	 * @return the response
	 */
	public String getResponse() {
		return response;
	}
	/**
	 * @return the responseDate
	 */
	public Date getResponseDate() {
		return responseDate;
	}
	/**
	 * @return the transactionAmount
	 */
	public Double getTransactionAmount() {
		return transactionAmount;
	}
	/**
	 * @return the transactionId
	 */
	public String getTransactionId() {
		return transactionId;
	}
	/**
	 * @return the transIdentifier
	 */
	public String getTransIdentifier() {
		return transIdentifier;
	}
	public String getUserName() {
		return userName;
	}
	//B2C end
	/**
	 * @return the vendorId
	 */
	public Integer getVendorId() {
		return vendorId;
	}
	/**
	 * @return the zipcode
	 */
	public String getZipcode() {
		return zipcode;
	}
	public boolean isActive_ind() {
		return active_ind;
	}
	/**
	 * @return the authorized
	 */
	public boolean isAuthorized() {
		return authorized;
	}
	public boolean isDefault_ind() {
		return this.default_ind;
	}
	public boolean isEnabled_ind() {
		return enabled_ind;
	}
	public boolean isSendFulfillment() {
		return sendFulfillment;
	}
	/**
	 * @param accountStatusId the accountStatusId to set
	 */
	@XmlElement()
	public void setAccountStatusId(Integer accountStatusId) {
		this.accountStatusId = accountStatusId;
	}
	/**
	 * @param accountTypeId the accountTypeId to set
	 */
	@XmlElement()
	public void setAccountTypeId(Integer accountTypeId) {
		this.accountTypeId = accountTypeId;
	}

	@XmlElement()
	public void setActive_ind(boolean active_ind) {
		this.active_ind = active_ind;
	}

	/**
	 * @param address the address to set
	 */
	@XmlElement()
	public void setAddress(String address) {
		this.address = address;
	}

	@XmlElement()
	public void setAddrResponseCode(String addrResponseCode) {
		this.addrResponseCode = addrResponseCode;
	}

	@XmlElement()
	public void setAnsiResponseCode(String ansiResponseCode) {
		this.ansiResponseCode = ansiResponseCode;
	}

	/**
	 * @param authorizationAmount the authorizationAmount to set
	 */
	@XmlElement()
	public void setAuthorizationAmount(Double authorizationAmount) {
		this.authorizationAmount = authorizationAmount;
	}

	/**
	 * @param authorizationCode the authorizationCode to set
	 */
	@XmlElement()
	public void setAuthorizationCode(String authorizationCode) {
		this.authorizationCode = authorizationCode;
	}

	/**
	 * @param authorized the authorized to set
	 */
	@XmlElement()
	public void setAuthorized(boolean authorized) {
		this.authorized = authorized;
	}

	/**
	 * @param authorizerRespCode the authorizerRespCode to set
	 */
	@XmlElement()
	public void setAuthorizerRespCode(String authorizerRespCode) {
		this.authorizerRespCode = authorizerRespCode;
	}

	@XmlElement()
	public void setBillingAddress1(String billingAddress1) {
		this.billingAddress1 = billingAddress1;
	}

	@XmlElement()
	public void setBillingAddress2(String billingAddress2) {
		this.billingAddress2 = billingAddress2;
	}

	@XmlElement()
	public void setBillingAptNo(String billingAptNo) {
		this.billingAptNo = billingAptNo;
	}

	@XmlElement()
	public void setBillingCity(String billingCity) {
		this.billingCity = billingCity;
	}

	@XmlElement()
	public void setBillingLocationName(String billingLocationName) {
		this.billingLocationName = billingLocationName;
	}

	@XmlElement()
	public void setBillingState(String billingState) {
		this.billingState = billingState;
	}

	/**
	 * @param businessTransId the businessTransId to set
	 */
	@XmlElement()
	public void setBusinessTransId(Integer businessTransId) {
		this.businessTransId = businessTransId;
	}

	/**
	 * @param cardHolderName the cardHolderName to set
	 */
	@XmlElement()
	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}

	/**
	 * @param cardId the cardId to set
	 */
	@XmlElement()
	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}

	/**
	 * @param cardNo the cardNo to set
	 */
	@XmlElement()
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	/**
	 * @param cardTypeId the cardTypeId to set
	 */
	@XmlElement()
	public void setCardTypeId(Long cardTypeId) {
		this.cardTypeId = cardTypeId;
	}

	/**
	 * @param cardVerificationNo the cardVerificationNo to set
	 */
	@XmlElement()
	public void setCardVerificationNo(String cardVerificationNo) {
		this.cardVerificationNo = cardVerificationNo;
	}

	@XmlElement()
	public void setCidResponseCode(String cidResponseCode) {
		this.cidResponseCode = cidResponseCode;
	}

	/**
	 * @param countryId the countryId to set
	 */
	@XmlElement()
	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	@XmlElement()
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@XmlElement()
	public void setDefault_ind(boolean b) {
		this.default_ind = b;
	}

	@XmlElement()
	public void setEnabled_ind(boolean enabled_ind) {
		this.enabled_ind = enabled_ind;
	}

	/**
	 * @param encCardNo the encCardNo to set
	 */
	@XmlElement()
	public void setEncCardNo(String encCardNo) {
		this.encCardNo = encCardNo;
	}

	/**
	 * @param entityId the entityId to set
	 */
	@XmlElement()
	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}

	/**
	 * @param entityTypeId the entityTypeId to set
	 */
	@XmlElement()
	public void setEntityTypeId(Integer entityTypeId) {
		this.entityTypeId = entityTypeId;
	}

	/**
	 * @param expireDate the expireDate to set
	 */
	@XmlElement()
	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}

	@XmlElement()
	public void setFundingTypeId(String fundingTypeId) {
		this.fundingTypeId = fundingTypeId;
	}

	@XmlElement()
	public void setLedgerEntryRuleId(Integer ledgerEntryRuleId) {
		LedgerEntryRuleId = ledgerEntryRuleId;
	}

	@XmlElement()
	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	/**
	 * @param locationTypeId the locationTypeId to set
	 */
	@XmlElement()
	public void setLocationTypeId(Integer locationTypeId) {
		this.locationTypeId = locationTypeId;
	}

	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	@XmlElement()
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	@XmlElement()
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	/**
	 * @param response the response to set
	 */
	@XmlElement()
	public void setResponse(String response) {
		this.response = response;
	}

	/**
	 * @param responseDate the responseDate to set
	 */
	@XmlElement()
	public void setResponseDate(Date responseDate) {
		this.responseDate = responseDate;
	}

	@XmlElement()
	public void setSendFulfillment(boolean sendFulfillment) {
		this.sendFulfillment = sendFulfillment;
	}

	/**
	 * @param transactionAmount the transactionAmount to set
	 */
	@XmlElement()
	public void setTransactionAmount(Double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	/**
	 * @param transactionId the transactionId to set
	 */
	@XmlElement()
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * @param transIdentifier the transIdentifier to set
	 */
	@XmlElement()
	public void setTransIdentifier(String transIdentifier) {
		this.transIdentifier = transIdentifier;
	}

	@XmlElement()
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @param vendorId the vendorId to set
	 */
	@XmlElement()
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	/**
	 * @param zipcode the zipcode to set
	 */
	@XmlElement()
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getTransAmount() {
		return transAmount;
	}
	public void setTransAmount(String transAmount) {
		this.transAmount = transAmount;
	}
	public String getHsTransAmount() {
		return hsTransAmount;
	}
	public void setHsTransAmount(String hsTransAmount) {
		this.hsTransAmount = hsTransAmount;
	}
	
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	public String getAddlResponseData() {
		return addlResponseData;
	}
	public void setAddlResponseData(String addlResponseData) {
		this.addlResponseData = addlResponseData;
	}
	public String getAuthCd() {
		return authCd;
	}
	public void setAuthCd(String authCd) {
		this.authCd = authCd;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getMaskedAccount() {
		return maskedAccount;
	}
	public void setMaskedAccount(String maskedAccount) {
		this.maskedAccount = maskedAccount;
	}
	public String getAjbKey() {
		return ajbKey;
	}
	public void setAjbKey(String ajbKey) {
		this.ajbKey = ajbKey;
	}
	public String getAchProcessQueueID() {
		return achProcessQueueID;
	}
	public void setAchProcessQueueID(String achProcessQueueID) {
		this.achProcessQueueID = achProcessQueueID;
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public long getRespId() {
		return respId;
	}
	public void setRespId(long respId) {
		this.respId = respId;
	}

	
}
