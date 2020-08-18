package com.newco.marketplace.vo.leadmanagement.lead;



public class CancelLeadDetail {

	private Integer reasonCode=0;
	private String comments="";
	private String leadId;
	private boolean revokePointsInd=false;
	private int status=0;
	private boolean chkAllProviderInd=false;
	private Integer cancelInitiatedBy;

	public Integer getCancelInitiatedBy() {
		return cancelInitiatedBy;
	}
	public void setCancelInitiatedBy(Integer cancelInitiatedBy) {
		this.cancelInitiatedBy = cancelInitiatedBy;
	}
	
	public Integer getReasonCode() {
		return reasonCode;
	}
	public void setReasonCode(Integer reasonCode) {
		this.reasonCode = reasonCode;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public boolean isRevokePointsInd() {
		return revokePointsInd;
	}
	public void setRevokePointsInd(boolean revokePointsInd) {
		this.revokePointsInd = revokePointsInd;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public boolean isChkAllProviderInd() {
		return chkAllProviderInd;
	}
	public void setChkAllProviderInd(boolean chkAllProviderInd) {
		this.chkAllProviderInd = chkAllProviderInd;
	}
	public String getLeadId() {
		return leadId;
	}
	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}
	
	
}
