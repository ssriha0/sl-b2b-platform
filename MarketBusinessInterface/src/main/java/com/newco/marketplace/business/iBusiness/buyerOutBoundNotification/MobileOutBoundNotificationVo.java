package com.newco.marketplace.business.iBusiness.buyerOutBoundNotification;

import java.util.Date;
import java.util.List;

public class MobileOutBoundNotificationVo {
	private Date serviceOrderScheduleToDate;
	private Date serviceOrderScheduleFromDate;
	private String serviceOrderScheduleFromTime;
	private String serviceOrderScheduleToTime;
	

	private Date serviceOrderRescheduleToDate;
	private Date serviceOrderRescheduleFromDate;
	private String serviceOrderRescheduleFromTime;
	private String serviceOrderRescheduleToTime;

	private String timeZone;
	private String soId;
	private Integer entityId;
	private String reasonDescr;
	private Integer vendorId;
	private Integer resourceId;
	private List<String> soIds;
	private Integer routedProviderId;
    
	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public String getServiceOrderScheduleFromTime() {
		return serviceOrderScheduleFromTime;
	}

	public void setServiceOrderScheduleFromTime(
			String serviceOrderScheduleFromTime) {
		this.serviceOrderScheduleFromTime = serviceOrderScheduleFromTime;
	}

	public Date getServiceOrderScheduleToDate() {
		return serviceOrderScheduleToDate;
	}

	public void setServiceOrderScheduleToDate(Date serviceOrderScheduleToDate) {
		this.serviceOrderScheduleToDate = serviceOrderScheduleToDate;
	}

	public Date getServiceOrderScheduleFromDate() {
		return serviceOrderScheduleFromDate;
	}

	public void setServiceOrderScheduleFromDate(
			Date serviceOrderScheduleFromDate) {
		this.serviceOrderScheduleFromDate = serviceOrderScheduleFromDate;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public String getServiceOrderScheduleToTime() {
		return serviceOrderScheduleToTime;
	}

	public void setServiceOrderScheduleToTime(String serviceOrderScheduleToTime) {
		this.serviceOrderScheduleToTime = serviceOrderScheduleToTime;
	}

	public Date getServiceOrderRescheduleToDate() {
		return serviceOrderRescheduleToDate;
	}

	public void setServiceOrderRescheduleToDate(
			Date serviceOrderRescheduleToDate) {
		this.serviceOrderRescheduleToDate = serviceOrderRescheduleToDate;
	}

	public Date getServiceOrderRescheduleFromDate() {
		return serviceOrderRescheduleFromDate;
	}

	public void setServiceOrderRescheduleFromDate(
			Date serviceOrderRescheduleFromDate) {
		this.serviceOrderRescheduleFromDate = serviceOrderRescheduleFromDate;
	}

	public String getServiceOrderRescheduleFromTime() {
		return serviceOrderRescheduleFromTime;
	}

	public void setServiceOrderRescheduleFromTime(
			String serviceOrderRescheduleFromTime) {
		this.serviceOrderRescheduleFromTime = serviceOrderRescheduleFromTime;
	}

	public String getServiceOrderRescheduleToTime() {
		return serviceOrderRescheduleToTime;
	}

	public void setServiceOrderRescheduleToTime(
			String serviceOrderRescheduleToTime) {
		this.serviceOrderRescheduleToTime = serviceOrderRescheduleToTime;
	}

	public Integer getEntityId() {
		return entityId;
	}

	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}

	public String getReasonDescr() {
		return reasonDescr;
	}

	public void setReasonDescr(String reasonDescr) {
		this.reasonDescr = reasonDescr;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public List<String> getSoIds() {
		return soIds;
	}

	public void setSoIds(List<String> soIds) {
		this.soIds = soIds;
	}

	public Integer getRoutedProviderId() {
		return routedProviderId;
	}

	public void setRoutedProviderId(Integer routedProviderId) {
		this.routedProviderId = routedProviderId;
	}

}
