package com.newco.marketplace.api.beans.so.addEstimate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("estimateItemsHistory")
@XmlAccessorType(XmlAccessType.FIELD)
public class EstimateItemsHistory {
	
	@XStreamAlias("laborTasks")
	private LaborTasksHistory laborTasks;
	
	@XStreamAlias("parts")
	private EstimatePartsHistory parts;

	@XStreamAlias("otherServices")
	private EstimateOtherServicesHistory otherServices;

	public LaborTasksHistory getLaborTasks() {
		return laborTasks;
	}

	public void setLaborTasks(LaborTasksHistory laborTasks) {
		this.laborTasks = laborTasks;
	}

	public EstimatePartsHistory getParts() {
		return parts;
	}

	public void setParts(EstimatePartsHistory parts) {
		this.parts = parts;
	}

	public EstimateOtherServicesHistory getOtherServices() {
		return otherServices;
	}

	public void setOtherServices(EstimateOtherServicesHistory otherServices) {
		this.otherServices = otherServices;
	}

	

}
