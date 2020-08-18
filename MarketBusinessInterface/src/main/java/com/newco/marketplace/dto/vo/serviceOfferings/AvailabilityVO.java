package com.newco.marketplace.dto.vo.serviceOfferings;

import java.io.Serializable;
import java.sql.Date;

public class AvailabilityVO implements Serializable
{
	private static final long serialVersionUID = -1527770441184997995L; 
	
    private Integer offeringId;
    private String dayOfTheWeek;
    private Integer timeWindow;
    private Date createdDate;
    private Date modifiedDate;
    private String createdBy;
    private String modifiedBy;
    //Added for History
    private String action;
    private Boolean deleteInd;
    private String timeWindowDesc;
    private Integer vendorId;
    
	public Integer getOfferingId() {
		return offeringId;
	}
	public void setOfferingId(Integer offeringId) {
		this.offeringId = offeringId;
	}
	public String getDayOfTheWeek() {
		return dayOfTheWeek;
	}
	public void setDayOfTheWeek(String dayOfTheWeek) {
		this.dayOfTheWeek = dayOfTheWeek;
	}
	public Integer getTimeWindow() {
		return timeWindow;
	}
	public void setTimeWindow(Integer timeWindow) {
		this.timeWindow = timeWindow;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Boolean getDeleteInd() {
		return deleteInd;
	}
	public void setDeleteInd(Boolean deleteInd) {
		this.deleteInd = deleteInd;
	}
	public String getTimeWindowDesc() {
		return timeWindowDesc;
	}
	public void setTimeWindowDesc(String timeWindowDesc) {
		this.timeWindowDesc = timeWindowDesc;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
    
	 
	
}
