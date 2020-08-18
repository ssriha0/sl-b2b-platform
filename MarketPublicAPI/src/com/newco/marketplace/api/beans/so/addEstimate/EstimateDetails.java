package com.newco.marketplace.api.beans.so.addEstimate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("estimateDetails")
@XmlAccessorType(XmlAccessType.FIELD)
public class EstimateDetails {
	
	@XStreamAlias("laborTasks")
	@XmlElement(name="laborTasks")
	private LaborTasks laborTasks;
	
	@XStreamAlias("parts")
	@XmlElement(name="parts")
	private Parts parts;

	@XStreamAlias("otherServices")
	@XmlElement(name="otherServices")
	private OtherServices otherServices;

	public LaborTasks getLaborTasks() {
		return laborTasks;
	}

	public void setLaborTasks(LaborTasks laborTasks) {
		this.laborTasks = laborTasks;
	}

	public Parts getParts() {
		return parts;
	}

	public void setParts(Parts parts) {
		this.parts = parts;
	}
	
	public OtherServices getOtherServices() {
		return otherServices;
	}

	public void setOtherServices(OtherServices otherServices) {
		this.otherServices = otherServices;
	}
	
	
	

}
