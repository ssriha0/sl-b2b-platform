package com.newco.marketplace.dto.vo.alert;

import com.sears.os.vo.SerializableBaseVO;

public class AlertBase extends SerializableBaseVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6857089904247141658L;
	public static final int ALERT_NONE = 0;
	public static final int ALERT_TASK = 1;
	public static final int ALERT_ALERT = 2;
	
	java.sql.Timestamp alertTime = null;
	int contactId;
	String alertMessage;
	String targetType;
	int targetKey;
	int contactMethodId;
	int alertLevel;
	int workflowStateTransitionId;
	
	public String getAlertMessage() {
		return alertMessage;
	}
	public void setAlertMessage(String alertMessage) {
		this.alertMessage = alertMessage;
	}
	public java.sql.Timestamp getAlertTime() {
		return alertTime;
	}
	public void setAlertTime(java.sql.Timestamp alertTime) {
		this.alertTime = alertTime;
	}
	public int getContactId() {
		return contactId;
	}
	public void setContactId(int contactId) {
		this.contactId = contactId;
	}
	public int getTargetKey() {
		return targetKey;
	}
	public void setTargetKey(int targetKey) {
		this.targetKey = targetKey;
	}
	public String getTargetType() {
		return targetType;
	}
	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

}
