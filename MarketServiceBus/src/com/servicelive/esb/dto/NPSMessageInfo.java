package com.servicelive.esb.dto;

import java.io.Serializable;

public class NPSMessageInfo implements Serializable {
	
	/** generated serialVersionUID */
	private static final long serialVersionUID = 1851060592418419561L;

	private boolean reportable;
	
	private boolean success_ind;
	
	private int owner_id;
	
	private String message;
	
	private String emailIds;

	public boolean isReportable() {
		return reportable;
	}

	public void setReportable(boolean reportable) {
		this.reportable = reportable;
	}

	public boolean isSuccess_ind() {
		return success_ind;
	}

	public void setSuccess_ind(boolean success_ind) {
		this.success_ind = success_ind;
	}

	public int getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(int owner_id) {
		this.owner_id = owner_id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getEmailIds() {
		return emailIds;
	}

	public void setEmailIds(String emailIds) {
		this.emailIds = emailIds;
	}

}
