package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;

import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.business.iBusiness.om.api.beans.common.UserIdentificationRequest;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("ScheduleAppointmentRequest")
public class ScheduleAppointmentRequest extends UserIdentificationRequest {

	@XStreamAlias("xsi:schemaLocation")
	@XStreamAsAttribute()
	private String schemaLocation = PublicAPIConstant.SCHEDULE_INFO__REQUEST_SCHEMALOCATION;

	@XStreamAlias("xmlns")
	@XStreamAsAttribute()
	private String namespace = PublicAPIConstant.NEW_SERVICES_NAMESPACE;

	@XStreamAlias("xmlns:xsi")
	@XStreamAsAttribute()
	private String schemaInstance = PublicAPIConstant.SCHEMA_INSTANCE;

	@XStreamAlias("LeadId")
	private String leadId;

	@XStreamAlias("FirmId")
	private Integer vendorId;

	@XStreamAlias("ServiceStartTime")
	private String serviceStartTime;

	@XStreamAlias("ServiceEndTime")
	private String serviceEndTime;

	@XStreamAlias("ServiceDate")
	private String serviceDate;

	@XStreamAlias("rescheduleReason")
	private String resheduleReason;
	// Result of validation
	private ResultsCode validationCode;
	
	@XStreamAlias("recheduleIndicator")
	private boolean recheduleIndicator;
	// To set existing service date in case of reschedule
	private String scheduledDate;

	public String getLeadId() {
		return leadId;
	}

	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public String getServiceStartTime() {
		return serviceStartTime;
	}

	public void setServiceStartTime(String serviceStartTime) {
		this.serviceStartTime = serviceStartTime;
	}

	public Boolean getRecheduleIndicator() {
		return recheduleIndicator;
	}

	public void setRecheduleIndicator(Boolean recheduleIndicator) {
		this.recheduleIndicator = recheduleIndicator;
	}

	public String getServiceEndTime() {
		return serviceEndTime;
	}

	public void setServiceEndTime(String serviceEndTime) {
		this.serviceEndTime = serviceEndTime;
	}

	public String getServiceDate() {
		return serviceDate;
	}

	public void setServiceDate(String serviceDate) {
		this.serviceDate = serviceDate;
	}

	public ResultsCode getValidationCode() {
		return validationCode;
	}

	public String getScheduledDate() {
		return scheduledDate;
	}

	public String getResheduleReason() {
		return resheduleReason;
	}

	public void setResheduleReason(String resheduleReason) {
		this.resheduleReason = resheduleReason;
	}

	public void setScheduledDate(String scheduledDate) {
		this.scheduledDate = scheduledDate;
	}

	public void setValidationCode(ResultsCode validationCode) {
		this.validationCode = validationCode;
	}

	public String getSchemaLocation() {
		return schemaLocation;
	}

	public void setSchemaLocation(String schemaLocation) {
		this.schemaLocation = schemaLocation;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getSchemaInstance() {
		return schemaInstance;
	}

	public void setSchemaInstance(String schemaInstance) {
		this.schemaInstance = schemaInstance;
	}

}
