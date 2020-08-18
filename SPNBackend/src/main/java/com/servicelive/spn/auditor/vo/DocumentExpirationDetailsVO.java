package com.servicelive.spn.auditor.vo;

import java.util.Date;

/**
 * 
 *
 */
public class DocumentExpirationDetailsVO
{
	private Integer notificationId;
	private String requirementType ;
	private Date expirationDate;
	private Integer expiresIn;
	private String action;
	private Date noticeSentOn;
	
	public Integer getNotificationId() {
		return notificationId;
	}
	public void setNotificationId(Integer notificationId) {
		this.notificationId = notificationId;
	}
	public String getRequirementType() {
		return requirementType;
	}
	public void setRequirementType(String requirementType) {
		this.requirementType = requirementType;
	}
	public Date getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	public Integer getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(Integer expiresIn) {
		this.expiresIn = expiresIn;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Date getNoticeSentOn() {
		return noticeSentOn;
	}
	public void setNoticeSentOn(Date noticeSentOn) {
		this.noticeSentOn = noticeSentOn;
	}
	
}
