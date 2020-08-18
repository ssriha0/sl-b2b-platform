package com.newco.marketplace.web.dto.ajax;

import java.util.List;

import com.newco.marketplace.web.dto.SerializedBaseDTO;

public class AjaxCommonCredentialReq extends SerializedBaseDTO{

	private String currentKey;
	private String currentVal;
	private String selectType;
	private String subSelectName;
	private String sendEmail;
	private String commonReviewNote;

	private String credentialKey;
	private String resourceCredentialKey;
	private String theResourceId;

	private String commonSEL2;
	private List commonSEL2SUB;
	
	private String auditTimeLoggingIdNew;
	
	
	public String getCommonSEL2() {
		return commonSEL2;
	}
	public void setCommonSEL2(String commonSEL2) {
		this.commonSEL2 = commonSEL2;
	}
	public List getCommonSEL2SUB() {
		return commonSEL2SUB;
	}
	public void setCommonSEL2SUB(List commonSEL2SUB) {
		this.commonSEL2SUB = commonSEL2SUB;
	}

	public String getCredentialKey() {
		return credentialKey;
	}
	public void setCredentialKey(String credentialKey) {
		this.credentialKey = credentialKey;
	}
	public String getResourceCredentialKey() {
		return resourceCredentialKey;
	}
	public void setResourceCredentialKey(String resourceCredentialKey) {
		this.resourceCredentialKey = resourceCredentialKey;
	}
	public String getTheResourceId() {
		return theResourceId;
	}
	public void setTheResourceId(String theResourceId) {
		this.theResourceId = theResourceId;
	}
	public String getSendEmail() {
		return sendEmail;
	}
	public void setSendEmail(String sendEmail) {
		this.sendEmail = sendEmail;
	}
	public String getCurrentKey() {
		return currentKey;
	}
	public void setCurrentKey(String currentKey) {
		this.currentKey = currentKey;
	}
	public String getCurrentVal() {
		return currentVal;
	}
	public void setCurrentVal(String currentVal) {
		this.currentVal = currentVal;
	}
	public String getSelectType() {
		return selectType;
	}
	public void setSelectType(String selectType) {
		this.selectType = selectType;
	}
	public String getSubSelectName() {
		return subSelectName;
	}
	public void setSubSelectName(String subSelectName) {
		this.subSelectName = subSelectName;
	}
	/**
	 * @return the commonReviewNote
	 */
	public String getCommonReviewNote() {
		return commonReviewNote;
	}
	/**
	 * @param commonReviewNote the commonReviewNote to set
	 */
	public void setCommonReviewNote(String commonReviewNote) {
		this.commonReviewNote = commonReviewNote;
	}
	public String getAuditTimeLoggingIdNew() {
		return auditTimeLoggingIdNew;
	}
	public void setAuditTimeLoggingIdNew(String auditTimeLoggingIdNew) {
		this.auditTimeLoggingIdNew = auditTimeLoggingIdNew;
	}


}
