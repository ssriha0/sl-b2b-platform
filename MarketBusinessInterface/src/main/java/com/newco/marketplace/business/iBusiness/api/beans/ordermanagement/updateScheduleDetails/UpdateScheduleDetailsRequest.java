package com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.updateScheduleDetails;

import java.sql.Timestamp;

import com.newco.marketplace.business.iBusiness.om.api.beans.common.UserIdentificationRequest;
import com.newco.marketplace.business.iBusiness.om.api.utils.constants.PublicAPIConstant;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("updateScheduleDetlsRequest")  
public class UpdateScheduleDetailsRequest extends UserIdentificationRequest{
	
	@XStreamAlias("version")   
	@XStreamAsAttribute()   
	private String version = PublicAPIConstant.SORESPONSE_VERSION;
	 
	@XStreamAlias("xsi:schemaLocation")   
	@XStreamAsAttribute()   
	private String schemaLocation;
	 
	@XStreamAlias("xmlns")   
	@XStreamAsAttribute()   
	private String namespace = PublicAPIConstant.UPDATE_SCHEDULE_DETAILS_NAMESPACE;
	 
	@XStreamAlias("xmlns:xsi")   
	@XStreamAsAttribute()   
	private String schemaInstance = PublicAPIConstant.SCHEMA_INSTANCE;
	
	@XStreamAlias("scheduleStatusId")   
	private int scheduleStatusId;
	@XStreamAlias("reasonId")   
	private String reasonId;
	@XStreamAlias("soNotes")   
	private String soNotes;
	@XStreamAlias("specialInstructions")   
	private String specialInstructions;	
	@XStreamAlias("eta")   
	private String eta;
	@XStreamAlias("customerConfirmInd")   
	private Boolean customerConfirmInd;
	
	@XStreamAlias("source")   
	private String source;

	@XStreamAlias("serviceTimeStart")
	private String serviceTimeStart;
	@XStreamAlias("serviceTimeEnd")
	private String serviceTimeEnd;
	@XStreamAlias("rescheduleServiceDateTime1")
	private String rescheduleServiceDateTime1;
	@XStreamAlias("rescheduleServiceDateTime2")
	private String rescheduleServiceDateTime2;
	@XStreamAlias("modifiedByName")
	private String modifiedByName;
	/**SL 18896 R8.2, pass the startDate & endDate parameter START**/
	@XStreamAlias("startDate")   
	private String startDate;
	 
	@XStreamAlias("endDate")   
	private String endDate;
	/**SL 18896 R8.2, pass the startDate & endDate parameter END**/
	
	public int getScheduleStatus() {
		return scheduleStatusId;
	}

	public void setScheduleStatus(int scheduleStatusId) {
		this.scheduleStatusId = scheduleStatusId;
	}


	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getReason() {
		return reasonId;
	}

	public void setReason(String reasonId) {
		this.reasonId = reasonId;
	}

	public String getEta() {
		return eta;
	}

	public void setEta(String eta) {
		this.eta = eta;
	}

	

	public String getSpecialInstructions() {
		return specialInstructions;
	}

	public void setSpecialInstructions(String specialInstructions) {
		this.specialInstructions = specialInstructions;
	}

	public String getSoNotes() {
		return soNotes;
	}

	public void setSoNotes(String soNotes) {
		this.soNotes = soNotes;
	}

	public Boolean getCustomerConfirmInd() {
		return customerConfirmInd;
	}

	public void setCustomerConfirmInd(Boolean customerConfirmInd) {
		this.customerConfirmInd = customerConfirmInd;
	}

	public String getServiceTimeStart() {
		return serviceTimeStart;
	}

	public void setServiceTimeStart(String serviceTimeStart) {
		this.serviceTimeStart = serviceTimeStart;
	}

	public String getServiceTimeEnd() {
		return serviceTimeEnd;
	}

	public void setServiceTimeEnd(String serviceTimeEnd) {
		this.serviceTimeEnd = serviceTimeEnd;
	}

	public String getRescheduleServiceDateTime1() {
		return rescheduleServiceDateTime1;
	}

	public void setRescheduleServiceDateTime1(String rescheduleServiceDateTime1) {
		this.rescheduleServiceDateTime1 = rescheduleServiceDateTime1;
	}

	public String getRescheduleServiceDateTime2() {
		return rescheduleServiceDateTime2;
	}

	public void setRescheduleServiceDateTime2(String rescheduleServiceDateTime2) {
		this.rescheduleServiceDateTime2 = rescheduleServiceDateTime2;
	}

	public String getModifiedByName() {
		return modifiedByName;
	}

	public void setModifiedByName(String modifiedByName) {
		this.modifiedByName = modifiedByName;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
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

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
}
