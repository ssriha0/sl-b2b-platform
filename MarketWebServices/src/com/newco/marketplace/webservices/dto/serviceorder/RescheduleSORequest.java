package com.newco.marketplace.webservices.dto.serviceorder;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.xfire.aegis.type.java5.XmlElement;

public class RescheduleSORequest extends ABaseWebserviceRequest {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7476850851950052191L;
	private String serviceOrderID;
	private String newDateStart;
	private String newTimeStart;
	private String newDateEnd;
	private String newTimeEnd;
	
	@Override
	public String toString() {
		return new ToStringBuilder(this)
			.append("serviceOrderID", getServiceOrderID())
			.toString();
	}
	
	@XmlElement(minOccurs="1",nillable=false)
	public String getServiceOrderID() {
		return serviceOrderID;
	}

	public void setServiceOrderID(String serviceOrder) {
		this.serviceOrderID = serviceOrder;
	}

	@XmlElement(minOccurs="1")
	public String getNewDateStart() {
		return newDateStart;
	}

	public void setNewDateStart(String newDateStart) {
		this.newDateStart = newDateStart;
	}

	@XmlElement(minOccurs="1")
	public String getNewTimeStart() {
		return newTimeStart;
	}

	public void setNewTimeStart(String newTimeStart) {
		this.newTimeStart = newTimeStart;
	}

	@XmlElement(minOccurs="0")
	public String getNewDateEnd() {
		return newDateEnd;
	}

	public void setNewDateEnd(String newDateEnd) {
		this.newDateEnd = newDateEnd;
	}

	@XmlElement(minOccurs="0")
	public String getNewTimeEnd() {
		return newTimeEnd;
	}

	public void setNewTimeEnd(String newTimeEnd) {
		this.newTimeEnd = newTimeEnd;
	}

}
