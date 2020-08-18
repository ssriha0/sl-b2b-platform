/**
 * 
 */
package com.servicelive.spn.auditor.vo;

import java.util.Date;
import java.util.List;

import com.servicelive.domain.lookup.LookupSPNOptOutReason;

/**
 * @author madhup_chand
 *
 */
public class SPNProviderFirmStateVO {
	
	private Integer spnId;
	private List<String> states;
	private String wfState;
	private Boolean auditRequired;
	private Boolean agreementInd;
	private Date applicationSubmissionDate;
	private String comment;
	private Date createdDate = new Date();
	private String modifiedBy;
	private Date modifiedDate = new Date();
	private String optOutComment;
	private String optOutReason;
	private Integer firmId;
	private String username;
	private Date reviewedDate;
	private Integer statusActionId;
	private Integer statusOverrideReasonId;
	private String statusOverrideComments;
	private String statusOverrideState;
	private String reviewedBy;
	private Double performanceScore;
	private Boolean overrideInd;
	
	
	public Double getPerformanceScore() {
		return performanceScore;
	}
	public void setPerformanceScore(Double performanceScore) {
		this.performanceScore = performanceScore;
	}
	public String getReviewedBy() {
		return reviewedBy;
	}
	public void setReviewedBy(String reviewedBy) {
		this.reviewedBy = reviewedBy;
	}
	public Integer getSpnId() {
		return spnId;
	}
	public void setSpnId(Integer spnId) {
		this.spnId = spnId;
	}
	public List<String> getStates() {
		return states;
	}
	public void setStates(List<String> states) {
		this.states = states;
	}
	public String getWfState() {
		return wfState;
	}
	public void setWfState(String wfState) {
		this.wfState = wfState;
	}
	public Boolean getAuditRequired() {
		return auditRequired;
	}
	public void setAuditRequired(Boolean auditRequired) {
		this.auditRequired = auditRequired;
	}
	public Boolean getAgreementInd() {
		return agreementInd;
	}
	public void setAgreementInd(Boolean agreementInd) {
		this.agreementInd = agreementInd;
	}
	public Date getApplicationSubmissionDate() {
		return applicationSubmissionDate;
	}
	public void setApplicationSubmissionDate(Date applicationSubmissionDate) {
		this.applicationSubmissionDate = applicationSubmissionDate;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getOptOutComment() {
		return optOutComment;
	}
	public void setOptOutComment(String optOutComment) {
		this.optOutComment = optOutComment;
	}

	public String getOptOutReason() {
		return optOutReason;
	}
	public void setOptOutReason(String optOutReason) {
		this.optOutReason = optOutReason;
	}
	public Integer getFirmId() {
		return firmId;
	}
	public void setFirmId(Integer firmId) {
		this.firmId = firmId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Date getReviewedDate() {
		return reviewedDate;
	}
	public void setReviewedDate(Date reviewedDate) {
		this.reviewedDate = reviewedDate;
	}
	public Integer getStatusActionId() {
		return statusActionId;
	}
	public void setStatusActionId(Integer statusActionId) {
		this.statusActionId = statusActionId;
	}
	public Integer getStatusOverrideReasonId() {
		return statusOverrideReasonId;
	}
	public void setStatusOverrideReasonId(Integer statusOverrideReasonId) {
		this.statusOverrideReasonId = statusOverrideReasonId;
	}
	public String getStatusOverrideComments() {
		return statusOverrideComments;
	}
	public void setStatusOverrideComments(String statusOverrideComments) {
		this.statusOverrideComments = statusOverrideComments;
	}
	public String getStatusOverrideState() {
		return statusOverrideState;
	}
	public void setStatusOverrideState(String statusOverrideState) {
		this.statusOverrideState = statusOverrideState;
	}
	public Boolean getOverrideInd() {
		return overrideInd;
	}
	public void setOverrideInd(Boolean overrideInd) {
		this.overrideInd = overrideInd;
	}	
	

}
