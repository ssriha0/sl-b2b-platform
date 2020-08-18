package com.newco.marketplace.api.mobile.beans.updateSoCompletion;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("hSCreditCardResponse")
public class HSCreditCardResponse {
	
	@XStreamAlias("correlationId")
	private String correlationId;

	@XStreamAlias("responseCode")
	private String responseCode;
	
	@XStreamAlias("responseMessage")
	private String responseMessage;
	
	@XStreamAlias("token")
	private String token;
	
	@XStreamAlias("maskedAccount")
	private String maskedAccount;
	
	@XStreamAlias("messages")
	private String messages;
	
	@XStreamAlias("transAmt")
	private String transAmt;
	
	@XStreamAlias("auditNo")
	private String auditNo;
	
	@XStreamAlias("refNo")
	private String refNo;
	
	@XStreamAlias("approvalCd")
	private String approvalCd;
	
	@XStreamAlias("actionCd")
	private String actionCd;
	
	@XStreamAlias("addlResponseData")
	private String addlResponseData;
	
	@XStreamAlias("authCd")
	private String authCd;
	
	@XStreamAlias("authRespCd")
	private String authRespCd;
	
	@XStreamAlias("newAcctNo")
	private String newAcctNo;
	
	@XStreamAlias("ajbKey")
	private String ajbKey;
	
	@XStreamAlias("avsStatus")
	private String avsStatus;
	
	
	
	
	
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
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
	public String getMessages() {
		return messages;
	}
	public void setMessages(String messages) {
		this.messages = messages;
	}
	public String getTransAmt() {
		return transAmt;
	}
	public void setTransAmt(String transAmt) {
		this.transAmt = transAmt;
	}
	public String getAuditNo() {
		return auditNo;
	}
	public void setAuditNo(String auditNo) {
		this.auditNo = auditNo;
	}
	public String getRefNo() {
		return refNo;
	}
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}
	public String getApprovalCd() {
		return approvalCd;
	}
	public void setApprovalCd(String approvalCd) {
		this.approvalCd = approvalCd;
	}
	public String getActionCd() {
		return actionCd;
	}
	public void setActionCd(String actionCd) {
		this.actionCd = actionCd;
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
	public String getAuthRespCd() {
		return authRespCd;
	}
	public void setAuthRespCd(String authRespCd) {
		this.authRespCd = authRespCd;
	}
	public String getNewAcctNo() {
		return newAcctNo;
	}
	public void setNewAcctNo(String newAcctNo) {
		this.newAcctNo = newAcctNo;
	}
	public String getAjbKey() {
		return ajbKey;
	}
	public void setAjbKey(String ajbKey) {
		this.ajbKey = ajbKey;
	}
	public String getAvsStatus() {
		return avsStatus;
	}
	public void setAvsStatus(String avsStatus) {
		this.avsStatus = avsStatus;
	}
	public String getCorrelationId() {
		return correlationId;
	}
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}
	
	
}

