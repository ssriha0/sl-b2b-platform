package com.newco.marketplace.dto.vo.leadsmanagement;

import java.util.List;

public class CancelLeadMailVO {

	private String leadId;
	private List<Integer> firmIds;
	private String cancelDate;
	private String cancelComments;
	private String cancelMailReceiverType;
	
	
	public String getCancelMailReceiverType() {
		return cancelMailReceiverType;
	}

	public void setCancelMailReceiverType(String cancelMailReceiverType) {
		this.cancelMailReceiverType = cancelMailReceiverType;
	}

	public String getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(String cancelDate) {
		this.cancelDate = cancelDate;
	}

	public String getCancelComments() {
		return cancelComments;
	}

	public void setCancelComments(String cancelComments) {
		this.cancelComments = cancelComments;
	}

	public String getLeadId() {
		return leadId;
	}
	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}
	public List<Integer> getFirmIds() {
		return firmIds;
	}
	public void setFirmIds(List<Integer> firmIds) {
		this.firmIds = firmIds;
	}
	
	
}
