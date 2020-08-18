package com.servicelive.esb.integration.domain;


public class OmsBuyerNotificationResponseMessage {
	private Long omsBuyerNotificationResponseMessageId;
	private Long buyerNotificationResponseId;
	private String message;
	private Integer ownerId;
	private String ownerName;
	private String emailIds;
	private String workInstruction;
	private Boolean success;
	private Boolean reportable;
	
	public OmsBuyerNotificationResponseMessage(Long omsBuyerNotificationResponseMessageId, 
			Long buyerNotificationResponseId, String message, Integer ownerId, String ownerName, String emailIds,
			String workInstruction, Boolean success, Boolean reportable) {
				
		this.omsBuyerNotificationResponseMessageId = omsBuyerNotificationResponseMessageId;
		this.buyerNotificationResponseId = buyerNotificationResponseId;
		this.message = message;
		this.setOwnerId(ownerId);
		this.ownerName = ownerName;
		this.emailIds = emailIds;
		this.workInstruction = workInstruction;
		this.success = success;
		this.reportable = reportable;
	}
	
	public void setOmsBuyerNotificationResponseMessageId(
			Long omsBuyerNotificationResponseMessageId) {
		this.omsBuyerNotificationResponseMessageId = omsBuyerNotificationResponseMessageId;
	}
	public Long getOmsBuyerNotificationResponseMessageId() {
		return omsBuyerNotificationResponseMessageId;
	}
	public void setBuyerNotificationResponseId(
			Long buyerNotificationResponseId) {
		this.buyerNotificationResponseId = buyerNotificationResponseId;
	}
	public Long getBuyerNotificationResponseId() {
		return buyerNotificationResponseId;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMessage() {
		return message;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setEmailIds(String emailIds) {
		this.emailIds = emailIds;
	}

	public String getEmailIds() {
		return emailIds;
	}

	public void setWorkInstruction(String workInstruction) {
		this.workInstruction = workInstruction;
	}

	public String getWorkInstruction() {
		return workInstruction;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setReportable(Boolean reportable) {
		this.reportable = reportable;
	}

	public Boolean getReportable() {
		return reportable;
	}

	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}

	public Integer getOwnerId() {
		return ownerId;
	}
}
