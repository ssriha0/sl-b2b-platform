package com.newco.marketplace.dto.vo.provider;


import com.sears.os.vo.SerializableBaseVO;

public class ProviderDetailWithSOAccepted  extends SerializableBaseVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5828343763682833179L;

	/**
	 * 
	 */
	
	private String serviceOrder;
	
	private String serviceStartTime;
	
	private String serviceEndTime;

	public String getServiceOrder() {
		return serviceOrder;
	}

	public void setServiceOrder(String serviceOrder) {
		this.serviceOrder = serviceOrder;
	}

	public String getServiceStartTime() {
		return serviceStartTime;
	}

	public void setServiceStartTime(String serviceStartTime) {
		this.serviceStartTime = serviceStartTime;
	}

	public String getServiceEndTime() {
		return serviceEndTime;
	}

	public void setServiceEndTime(String serviceEndTime) {
		this.serviceEndTime = serviceEndTime;
	}
	
	
}
