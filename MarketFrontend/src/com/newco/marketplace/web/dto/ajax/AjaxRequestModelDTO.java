package com.newco.marketplace.web.dto.ajax;

import com.newco.marketplace.web.dto.SerializedBaseDTO;

public class AjaxRequestModelDTO extends SerializedBaseDTO{
	

	private AjaxCommonCredentialReq credentialRequest;
	private AjaxCommonEnityApprovalReq  companyOrServicePro;
	private AjaxCommonAddNoteReq noteReq;
	private String actionSubmitType;
	
	
	public AjaxCommonEnityApprovalReq getCompanyOrServicePro() {
		return companyOrServicePro;
	}
	public void setCompanyOrServicePro(
			AjaxCommonEnityApprovalReq companyOrServicePro) {
		this.companyOrServicePro = companyOrServicePro;
	}
	public AjaxCommonCredentialReq getCredentialRequest() {
		return credentialRequest;
	}
	public void setCredentialRequest(AjaxCommonCredentialReq credentialRequest) {
		this.credentialRequest = credentialRequest;
	}
	public AjaxCommonAddNoteReq getNoteReq() {
		return noteReq;
	}
	public void setNoteReq(AjaxCommonAddNoteReq noteReq) {
		this.noteReq = noteReq;
	}
	public String getActionSubmitType() {
		return actionSubmitType;
	}
	public void setActionSubmitType(String actionSubmitType) {
		this.actionSubmitType = actionSubmitType;
	}
	
	

}
