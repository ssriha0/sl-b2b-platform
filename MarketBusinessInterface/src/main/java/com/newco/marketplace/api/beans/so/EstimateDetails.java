package com.newco.marketplace.api.beans.so;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("estimateDetails")
public class EstimateDetails {
	
	@XStreamAlias("laborTasks")
	private LaborTasks laborTasks;
	
	@XStreamAlias("parts")
	private EstimateParts parts;
	
	
	@XStreamAlias("otherServices")
	private OtherServices otherServices;

	public LaborTasks getLaborTasks() {
		return laborTasks;
	}

	public void setLaborTasks(LaborTasks laborTasks) {
		this.laborTasks = laborTasks;
	}

	public EstimateParts getParts() {
		return parts;
	}

	public void setParts(EstimateParts parts) {
		this.parts = parts;
	}

	public OtherServices getOtherServices() {
		return otherServices;
	}

	public void setOtherServices(OtherServices otherServices) {
		this.otherServices = otherServices;
	}
	
	

}
