package com.servicelive.orderfulfillment.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "so_cancellation")
@XmlRootElement()
public class SOCancellation extends SOChild{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1373806363093793981L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cancellation_id")
	private Integer cancellationId;

	@Column(name = "cancellation_amt")
	private Double cancelAmt;
	
	@Column(name = "cancellation_reason_code")
	private Integer cancelReasonCode;
	
	@Column(name = "cancellation_comments")
	private String cancelComments;
	
	@Column(name = "previous_wf_state")
	private Integer previousState;
	
	@Column(name = "cancel_ind")
	private Boolean cancelInd;
	
	@Column(name = "payment_approve_ind")
	private Boolean paymentApproveInd;

	public Integer getCancellationId() {
		return cancellationId;
	}

	public void setCancellationId(Integer cancellationId) {
		this.cancellationId = cancellationId;
	}

	public Double getCancelAmt() {
		return cancelAmt;
	}

	public void setCancelAmt(Double cancelAmt) {
		this.cancelAmt = cancelAmt;
	}

	public Integer getCancelReasonCode() {
		return cancelReasonCode;
	}

	public void setCancelReasonCode(Integer cancelReasonCode) {
		this.cancelReasonCode = cancelReasonCode;
	}

	public String getCancelComments() {
		return cancelComments;
	}

	public void setCancelComments(String cancelComments) {
		this.cancelComments = cancelComments;
	}

	public Integer getPreviousState() {
		return previousState;
	}

	public void setPreviousState(Integer previousState) {
		this.previousState = previousState;
	}

	public Boolean getCancelInd() {
		return cancelInd;
	}

	public void setCancelInd(Boolean cancelInd) {
		this.cancelInd = cancelInd;
	}

	public Boolean getPaymentApproveInd() {
		return paymentApproveInd;
	}

	public void setPaymentApproveInd(Boolean paymentApproveInd) {
		this.paymentApproveInd = paymentApproveInd;
	}

	
}