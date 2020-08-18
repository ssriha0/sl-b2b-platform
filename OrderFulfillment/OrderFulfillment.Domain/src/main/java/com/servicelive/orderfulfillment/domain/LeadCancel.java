package com.servicelive.orderfulfillment.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "lead_cancellation")
@XmlRootElement()
public class LeadCancel extends LeadChild{
	private static final long serialVersionUID = 9180446939179439349L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cancellation_id")
	private Integer leadCancellationId;
	
	@Column(name="cancellation_reason_code")
	private Integer cancellationReasonCode;
	
	@Column(name="cancellation_comments")
	private String cancellationComments;
	
	@Column(name="previous_wf_state")
	private Integer prevState;
	
	@Column(name="modified_by")
	private String modifiedBy;
	
	@Column(name="vendor_id")
	private Integer vendorId;

	@Column(name="cancel_initiated_by")
	private String cancelInitiatedBy;
	
	
	public String getCancelInitiatedBy() {
		return cancelInitiatedBy;
	}

	public void setCancelInitiatedBy(String cancelInitiatedBy) {
		this.cancelInitiatedBy = cancelInitiatedBy;
	}

	public Integer getLeadCancellationId() {
		return leadCancellationId;
	}

	public void setLeadCancellationId(Integer leadCancellationId) {
		this.leadCancellationId = leadCancellationId;
	}

	public Integer getCancellationReasonCode() {
		return cancellationReasonCode;
	}

	public void setCancellationReasonCode(Integer cancellationReasonCode) {
		this.cancellationReasonCode = cancellationReasonCode;
	}

	public String getCancellationComments() {
		return cancellationComments;
	}

	public void setCancellationComments(String cancellationComments) {
		this.cancellationComments = cancellationComments;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Integer getPrevState() {
		return prevState;
	}

	public void setPrevState(Integer prevState) {
		this.prevState = prevState;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
		
}
