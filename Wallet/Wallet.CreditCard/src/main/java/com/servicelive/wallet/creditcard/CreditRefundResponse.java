package com.servicelive.wallet.creditcard;

import java.util.List;

import com.servicelive.common.CommonConstants;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("ns2:CreditRefundResponse")
public class CreditRefundResponse {


	@XStreamAsAttribute
    final String xmlns = CommonConstants.RESPONSE_NAMESPACE;

    @XStreamAsAttribute 
    @XStreamAlias("xmlns:ns2")
    final String xlink=CommonConstants.HS_REFUND_RESPONSE_NAMESPACE_END;
	
	@XStreamAlias("CorrelationId")
	private String CorrelationId;
	
	@XStreamAlias("ResponseCode")
	private String ResponseCode;
	
	@XStreamAlias("ResponseMessage")
	private String ResponseMessage;
	
	@XStreamImplicit(itemFieldName="messages")
	private List<String> messages;
	
	@XStreamAlias("ns2:transAmt")
	private String transAmt;
	
	@XStreamAlias("ns2:auditNo")
	private String auditNo;
	
	@XStreamAlias("ns2:refNo")
	private String refNo;
	
	@XStreamAlias("ns2:approvalCd")
	private String approvalCd;
	
	@XStreamAlias("ns2:actionCd")
	private String actionCd;
	
	@XStreamAlias("ns2:addlResponseData")
	private String addlResponseData;
	
	@XStreamAlias("ns2:authCd") 
	private String authCd;
	
	@XStreamAlias("ns2:authRespCd") 
	private String authRespCd;
	
	@XStreamAlias("ns2:token")
	private String token;
	
	@XStreamAlias("ns2:maskedAccount")
	private String maskedAccount;
	
	@XStreamAlias("ns2:ajbKey")
	private String ajbKey;
	
	@XStreamAlias("ns2:avsStatus")
	private String avsStatus;
	
	public String getCorrelationId() {
		return CorrelationId;
	}
	public void setCorrelationId(String correlationId) {
		CorrelationId = correlationId;
	}
	
	
	public String getResponseCode() {
		return ResponseCode;
	}
	public void setResponseCode(String responseCode) {
		ResponseCode = responseCode;
	}
	public String getResponseMessage() {
		return ResponseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		ResponseMessage = responseMessage;
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
	public String getApprovalCd() {
		return approvalCd;
	}
	public void setApprovalCd(String approvalCd) {
		this.approvalCd = approvalCd;
	}
	public String getAuthCd() {
		return authCd;
	}
	public void setAuthCd(String authCd) {
		this.authCd = authCd;
	}
	public List<String> getMessages() {
		return messages;
	}
	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
	public String getAuthRespCd() {
		return authRespCd;
	}
	public void setAuthRespCd(String authRespCd) {
		this.authRespCd = authRespCd;
	}
	/**
	 * @return the avsStatus
	 */
	public String getAvsStatus() {
		return avsStatus;
	}
	/**
	 * @param avsStatus the avsStatus to set
	 */
	public void setAvsStatus(String avsStatus) {
		this.avsStatus = avsStatus;
	}


}

