package com.newco.marketplace.vo;

import java.util.List;

public class ServiceOrderReschedule {
	String orderNumber;
	String serviceUnit;
	ServiceInfoReschedule serviceInfo;
	Merchandise merchandise;
	List<JobCodes> jobCodes;
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getServiceUnit() {
		return serviceUnit;
	}
	public void setServiceUnit(String serviceUnit) {
		this.serviceUnit = serviceUnit;
	}
	public ServiceInfoReschedule getServiceInfo() {
		return serviceInfo;
	}
	public void setServiceInfo(ServiceInfoReschedule serviceInfo) {
		this.serviceInfo = serviceInfo;
	}
	public Merchandise getMerchandise() {
		return merchandise;
	}
	public void setMerchandise(Merchandise merchandise) {
		this.merchandise = merchandise;
	}
	public List<JobCodes> getJobCodes() {
		return jobCodes;
	}
	public void setJobCodes(List<JobCodes> jobCodes) {
		this.jobCodes = jobCodes;
	}
	
	
}
