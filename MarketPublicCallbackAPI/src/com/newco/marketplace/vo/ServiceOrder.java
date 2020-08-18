package com.newco.marketplace.vo;

import java.util.List;

public class ServiceOrder {
	Merchandise merchandise;
	String orderNumber;
	List<JobCodes> jobCodes;
	PaymentTotalInformation paymentTotalInformation;
	ServiceInfo serviceInfo;
	List<Parts> parts;
	String serviceUnit;

	public Merchandise getMerchandise() {
		return merchandise;
	}

	public void setMerchandise(Merchandise merchandise) {
		this.merchandise = merchandise;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public List<JobCodes> getJobCodes() {
		return jobCodes;
	}

	public void setJobCodes(List<JobCodes> jobCodes) {
		this.jobCodes = jobCodes;
	}

	public PaymentTotalInformation getPaymentTotalInformation() {
		return paymentTotalInformation;
	}

	public void setPaymentTotalInformation(PaymentTotalInformation paymentTotalInformation) {
		this.paymentTotalInformation = paymentTotalInformation;
	}

	public ServiceInfo getServiceInfo() {
		return serviceInfo;
	}

	public void setServiceInfo(ServiceInfo serviceInfo) {
		this.serviceInfo = serviceInfo;
	}

	

	public String getServiceUnit() {
		return serviceUnit;
	}

	public void setServiceUnit(String serviceUnit) {
		this.serviceUnit = serviceUnit;
	}

	public List<Parts> getParts() {
		return parts;
	}

	public void setParts(List<Parts> parts) {
		this.parts = parts;
	}

}
