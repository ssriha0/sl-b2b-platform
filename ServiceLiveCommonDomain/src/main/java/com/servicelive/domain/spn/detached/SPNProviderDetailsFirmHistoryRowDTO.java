package com.servicelive.domain.spn.detached;

import java.io.Serializable;
import java.util.Date;

public class SPNProviderDetailsFirmHistoryRowDTO implements Serializable{

	private static final long serialVersionUID = 2812679180552360128L;
	
	private Date dateChanged;
	private String changeEnteredByName;
	private Integer changeEnteredByID;
	private String network;
	private String action;
	private String comment;
	private String reason;
	// SL-12300 : Variable to store the validity date for overridden network status
	private String validityDate;
	
	
	public String getValidityDate() {
		return validityDate;
	}
	public void setValidityDate(String validityDate) {
		this.validityDate = validityDate;
	}
	public Date getDateChanged() {
		return dateChanged;
	}
	public void setDateChanged(Date dateChanged) {
		this.dateChanged = dateChanged;
	}
	public String getChangeEnteredByName() {
		return changeEnteredByName;
	}
	public void setChangeEnteredByName(String changeEnteredByName) {
		this.changeEnteredByName = changeEnteredByName;
	}
	public Integer getChangeEnteredByID() {
		return changeEnteredByID;
	}
	public void setChangeEnteredByID(Integer changeEnteredByID) {
		this.changeEnteredByID = changeEnteredByID;
	}
	public String getNetwork() {
		return network;
	}
	public void setNetwork(String network) {
		this.network = network;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

}
