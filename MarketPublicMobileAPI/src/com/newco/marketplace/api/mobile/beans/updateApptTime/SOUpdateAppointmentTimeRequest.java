package com.newco.marketplace.api.mobile.beans.updateApptTime;

import javax.xml.bind.annotation.XmlRootElement;
import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.business.iBusiness.om.api.utils.constants.PublicAPIConstant;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XSD(name="soEditAppointmentRequest.xsd", path="/resources/schemas/mobile/")
@XmlRootElement(name="soEditAppointmentRequest")
@XStreamAlias("soEditAppointmentRequest")  
public class SOUpdateAppointmentTimeRequest {
	
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
	 private String customerConfirmedInd;
	 
	 @XStreamAlias("custNotAvailReason")   
	 private Integer custNotAvailReason;
	 

	 
	public String getCustomerConfirmedInd() {
		return customerConfirmedInd;
	}

	public void setCustomerConfirmedInd(String customerConfirmedInd) {
		this.customerConfirmedInd = customerConfirmedInd;
	}

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

	public Integer getCustNotAvailReason() {
		return custNotAvailReason;
	}

	public void setCustNotAvailReason(Integer custNotAvailReason) {
		this.custNotAvailReason = custNotAvailReason;
	}


}
