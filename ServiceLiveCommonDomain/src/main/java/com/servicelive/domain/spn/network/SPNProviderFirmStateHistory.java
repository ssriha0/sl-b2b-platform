package com.servicelive.domain.spn.network;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.servicelive.domain.LoggableBaseDomain;
import com.servicelive.domain.lookup.LookupSPNOptOutReason;
import com.servicelive.domain.lookup.LookupSPNStatusActionMapper;
import com.servicelive.domain.lookup.LookupSPNStatusOverrideReason;
import com.servicelive.domain.lookup.LookupSPNWorkflowState;
import com.servicelive.domain.provider.ProviderFirm;
import com.servicelive.domain.userprofile.User;

/**
 * 
 * @author svanloon
 * 
 */
@Entity
@Table(name = "spnet_provider_firm_st_history")
public class SPNProviderFirmStateHistory extends LoggableBaseDomain {

	private static final long serialVersionUID = 20100412L;

	@Id
	@Column(name = "id", unique = true, nullable = false, insertable = false, updatable = false, length = 250)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.REFRESH })
	@JoinColumn(name = "spn_id", unique = false, nullable = false, insertable = false, updatable = false)
	private SPNHeader spnHeader;

	@ManyToOne
	@JoinColumn(name = "provider_firm_id", unique = false, nullable = false, insertable = false, updatable = false)
	private ProviderFirm providerFirm;

	@ManyToOne
	@JoinColumn(name = "provider_wf_state", unique = false, nullable = false, insertable = true, updatable = true)
	private LookupSPNWorkflowState wfState;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "app_submission_date", unique = false, nullable = true, insertable = true, updatable = true)
	private Date applicationSubmissionDate;

	@Column(name = "agreement_ind", unique = false, nullable = false, insertable = true, updatable = true)
	private Boolean agreementInd;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinColumn(name = "opt_out_reason_code", unique = false, nullable = true, insertable = true, updatable = true)
	private LookupSPNOptOutReason optOutReason;

	@Column(name = "opt_out_comment", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	private String optOutComment;

	@Column(name = "comments", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	private String comment;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "reviewed_date", unique = false, nullable = true, insertable = true, updatable = true)
	private Date reviewedDate;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinColumn(name = "reviewed_by", unique = false, nullable = true, insertable = true, updatable = true)
	private User reviewedBy;

	// status_override_reason_id int(5) YES MUL (null)
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinColumn(name = "status_override_reason_id", unique = false, nullable = true, insertable = true, updatable = true)
	private LookupSPNStatusOverrideReason lookupSPNStatusOverrideReason;

	// status_override_comments varchar(250) YES (null)
	@Column(name = "status_override_comments", unique = false, nullable = true, insertable = true, updatable = true, length = 750)
	private String statusOverrideComments;

	// status_update_action_id int(5) YES MUL (null)
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinColumn(name = "status_update_action_id", unique = false, nullable = true, insertable = true, updatable = true)
	private LookupSPNStatusActionMapper lookupSPNStatusActionMapper;

	@ManyToOne
	@JoinColumn(name = "status_override_state", unique = false, nullable = true, insertable = true, updatable = true)
	private LookupSPNWorkflowState statusOverrideState;
	
	// SL-12300
	// The expiry date for override from front-end will be saved in this column. 
	@Column(name = "status_override_effective_date", unique = false, nullable = true, insertable = true, updatable = true)
	private String statusOverrideEffectiveDate;
	
	//JIRA SL-12300
	//Indicator to identify that the network status is overridden from the front-end.
	@Column(name = "status_override_ind", insertable = true, updatable = true)
	private Boolean statusOverrideInd;
	
	public String getStatusOverrideEffectiveDate() {
		return statusOverrideEffectiveDate;
	}

	public void setStatusOverrideEffectiveDate(String statusOverrideEffectiveDate) {
		this.statusOverrideEffectiveDate = statusOverrideEffectiveDate;
	}

	/**
	 * @return the statusOverrideState
	 */
	public LookupSPNWorkflowState getStatusOverrideState() {
		return statusOverrideState;
	}

	/**
	 * @param statusOverrideState
	 *            the statusOverrideState to set
	 */
	public void setStatusOverrideState(LookupSPNWorkflowState statusOverrideState) {
		this.statusOverrideState = statusOverrideState;
	}

	/**
	 * @return the wfState
	 */
	public LookupSPNWorkflowState getWfState() {
		return wfState;
	}

	/**
	 * @param wfState
	 *            the wfState to set
	 */
	public void setWfState(LookupSPNWorkflowState wfState) {
		this.wfState = wfState;
	}

	/**
	 * @return the reviewedDate
	 */
	public Date getReviewedDate() {
		return reviewedDate;
	}

	/**
	 * @param reviewedDate
	 *            the reviewedDate to set
	 */
	public void setReviewedDate(Date reviewedDate) {
		this.reviewedDate = reviewedDate;
	}

	/**
	 * @return the applicationSubmissionDate
	 */
	public Date getApplicationSubmissionDate() {
		return applicationSubmissionDate;
	}

	/**
	 * @param applicationSubmissionDate
	 *            the applicationSubmissionDate to set
	 */
	public void setApplicationSubmissionDate(Date applicationSubmissionDate) {
		this.applicationSubmissionDate = applicationSubmissionDate;
	}

	/**
	 * @return the agreementInd
	 */
	public Boolean getAgreementInd() {
		return agreementInd;
	}

	/**
	 * @param agreementInd
	 *            the agreementInd to set
	 */
	public void setAgreementInd(Boolean agreementInd) {
		this.agreementInd = agreementInd;
	}

	/**
	 * @return the optOutReason
	 */
	public LookupSPNOptOutReason getOptOutReason() {
		return optOutReason;
	}

	/**
	 * @param optOutReason
	 *            the optOutReason to set
	 */
	public void setOptOutReason(LookupSPNOptOutReason optOutReason) {
		this.optOutReason = optOutReason;
	}

	/**
	 * @return the optOutComment
	 */
	public String getOptOutComment() {
		return optOutComment;
	}

	/**
	 * @param optOutComment
	 *            the optOutComment to set
	 */
	public void setOptOutComment(String optOutComment) {
		this.optOutComment = optOutComment;
	}

	/**
	 * @return the reviewedBy
	 */
	public User getReviewedBy() {
		return reviewedBy;
	}

	/**
	 * @param reviewedBy
	 *            the reviewedBy to set
	 */
	public void setReviewedBy(User reviewedBy) {
		this.reviewedBy = reviewedBy;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return the lookupSPNStatusOverrideReason
	 */
	public LookupSPNStatusOverrideReason getLookupSPNStatusOverrideReason() {
		return lookupSPNStatusOverrideReason;
	}

	/**
	 * @param lookupSPNStatusOverrideReason
	 *            the lookupSPNStatusOverrideReason to set
	 */
	public void setLookupSPNStatusOverrideReason(LookupSPNStatusOverrideReason lookupSPNStatusOverrideReason) {
		this.lookupSPNStatusOverrideReason = lookupSPNStatusOverrideReason;
	}

	/**
	 * @return the statusOverrideComments
	 */
	public String getStatusOverrideComments() {
		return statusOverrideComments;
	}

	/**
	 * @param statusOverrideComments
	 *            the statusOverrideComments to set
	 */
	public void setStatusOverrideComments(String statusOverrideComments) {
		this.statusOverrideComments = statusOverrideComments;
	}

	/**
	 * @return the lookupSPNStatusActionMapper
	 */
	public LookupSPNStatusActionMapper getLookupSPNStatusActionMapper() {
		return lookupSPNStatusActionMapper;
	}

	/**
	 * @param lookupSPNStatusActionMapper
	 *            the lookupSPNStatusActionMapper to set
	 */
	public void setLookupSPNStatusActionMapper(LookupSPNStatusActionMapper lookupSPNStatusActionMapper) {
		this.lookupSPNStatusActionMapper = lookupSPNStatusActionMapper;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the spnHeader
	 */
	public SPNHeader getSpnHeader() {
		return spnHeader;
	}

	/**
	 * @param spnHeader
	 *            the spnHeader to set
	 */
	public void setSpnHeader(SPNHeader spnHeader) {
		this.spnHeader = spnHeader;
	}

	/**
	 * @return the providerFirm
	 */
	public ProviderFirm getProviderFirm() {
		return providerFirm;
	}

	/**
	 * @param providerFirm
	 *            the providerFirm to set
	 */
	public void setProviderFirm(ProviderFirm providerFirm) {
		this.providerFirm = providerFirm;
	}

	public Boolean getStatusOverrideInd() {
		return statusOverrideInd;
	}

	public void setStatusOverrideInd(Boolean statusOverrideInd) {
		this.statusOverrideInd = statusOverrideInd;
	}

}
