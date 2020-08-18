package com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.editAppTime;

import com.newco.marketplace.business.iBusiness.om.api.beans.common.UserIdentificationRequest;
import com.newco.marketplace.business.iBusiness.om.api.utils.constants.PublicAPIConstant;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("soEditAppointmentRequest")  
public class SOEditAppointmentTimeRequest extends UserIdentificationRequest {
	
	@XStreamAlias("version")   
	@XStreamAsAttribute()   
	private String version = PublicAPIConstant.SORESPONSE_VERSION;
	 
	@XStreamAlias("xsi:schemaLocation")   
	@XStreamAsAttribute()   
	private String schemaLocation;
	 
	@XStreamAlias("xmlns")   
	@XStreamAsAttribute()   
	private String namespace = PublicAPIConstant.FETCH_SO_NAMESPACE;
	 
	@XStreamAlias("xmlns:xsi")   
	@XStreamAsAttribute()   
	private String schemaInstance = PublicAPIConstant.SCHEMA_INSTANCE;
	
	 @XStreamAlias("startTime")   
	 private String startTime;
	 
	 @XStreamAlias("endTime")   
	 private String endTime;
	 
	 @XStreamAlias("eta")   
	 private String eta;
	 
	 @XStreamAlias("customerConfirmedInd")   
	 private Boolean customerConfirmedInd;
	 
	 /**SL 18896 R8.2, pass the startDate & endDate parameter START**/
	 @XStreamAlias("startDate")   
	 private String startDate;
	 
	 @XStreamAlias("endDate")   
	 private String endDate;
	 /**SL 18896 R8.2, pass the startDate & endDate parameter END**/
	 
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getEta() {
		return eta;
	}

	public void setEta(String eta) {
		this.eta = eta;
	}

	public Boolean getCustomerConfirmedInd() {
		return customerConfirmedInd;
	}

	public void setCustomerConfirmedInd(Boolean customerConfirmedInd) {
		this.customerConfirmedInd = customerConfirmedInd;
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
