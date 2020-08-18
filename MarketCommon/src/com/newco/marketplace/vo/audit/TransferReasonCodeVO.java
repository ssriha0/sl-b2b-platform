package com.newco.marketplace.vo.audit;

public class TransferReasonCodeVO {
	private Integer reasonCodeId=-1;
	private String description=null;
	private Integer sortOrder=-1;
	private Integer emailTemplateId=-1;
	private String transferReasonNote=null;
	public Integer getReasonCodeId() {
		return reasonCodeId;
	}
	public void setReasonCodeId(Integer reasonCodeId) {
		this.reasonCodeId = reasonCodeId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}
	public Integer getEmailTemplateId() {
		return emailTemplateId;
	}
	public void setEmailTemplateId(Integer emailTemplateId) {
		this.emailTemplateId = emailTemplateId;
	}
	public String getTransferReasonNote() {
		return transferReasonNote;
	}
	public void setTransferReasonNote(String transferReasonNote) {
		this.transferReasonNote = transferReasonNote;
	}
}
