package com.servicelive.esb.dto;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("InstallationServiceOrder")
public class ServiceOrder implements Comparable<ServiceOrder>, Serializable {

	/** generated serialVersionUID */
	private static final long serialVersionUID = -2260160779458164777L;

	@XStreamAlias("Customer")
	private ServiceOrderCustomer customer;
	
	@XStreamAlias("JobCodes")
	private JobCodes jobCodes; 
	
	@XStreamAlias("Messages")
	private Messages messages;
	
	@XStreamAlias("Merchandise")
	private Merchandise merchandise;
	
	@XStreamAlias("OrderTakenTime")
	private String orderTakenTime;
	
	@XStreamAlias("OrderTakenDate")
	private String orderTakenDate;
	
	@XStreamAlias("OriginalScheduledDate")
	private String originalScheduledDate;
	
	@XStreamAlias("OriginalTimeFrom")
	private String originalTimeFrom;
	
	@XStreamAlias("OriginalTimeTo")
	private String originalTimeTo;
	
	@XStreamAlias("PermanentInstructions")
	private String permanentInstructions;
	
	@XStreamAlias("PromisedTimeFrom")
	private String promisedTimeFrom;
	
	@XStreamAlias("PromisedTimeTo")
	private String promisedTimeTo;
	
	@XStreamAlias("PromisedDate")
	private String promisedDate;
	
	@XStreamAlias("RepairAt")
	private RepairLocation repairLocation;
	
	@XStreamAlias("ServiceRequested")
	private String requestedService;
	
	@XStreamAlias("SalesCheck")
	private SalesCheck salesCheck;
	
	@XStreamAlias("ServiceOrderStatusCode")
	private String serviceOrderStatusCode;
	
	@XStreamAlias("ServiceOrderType")
	private String serviceOrderType;
	
	@XStreamAlias("ServiceOrderNumber")
	private String serviceOrderNumber;
	
	@XStreamAlias("ServiceUnitNumber")
	private String serviceUnitNumber;
	
	@XStreamAlias("SpecialInstructions1")
	private String specialInstructions1;
	
	@XStreamAlias("SpecialInstructions2")
	private String specialInstructions2;
	
	@XStreamAlias("ProcessID")
	private String processId;
	
	@XStreamAlias("TechID")
	private String techId;
	
	@XStreamAlias("TransactionType")
	private String transactionType;
	
	@XStreamAlias("Logistics")
	private Logistics logistics;
	
	public ServiceOrderCustomer getCustomer() {
		return customer;
	}

	public void setCustomer(ServiceOrderCustomer customer) {
		this.customer = customer;
	}

	public Logistics getLogistics() {
		return logistics;
	}

	public void setLogistics(Logistics logistics) {
		this.logistics = logistics;
	}

	public Merchandise getMerchandise() {
		return merchandise;
	}

	public void setMerchandise(Merchandise merchandise) {
		this.merchandise = merchandise;
	}

	public String getOrderTakenDate() {
		return orderTakenDate;
	}

	public void setOrderTakenDate(String orderTakenDate) {
		this.orderTakenDate = orderTakenDate;
	}

	public String getOrderTakenTime() {
		return orderTakenTime;
	}

	public void setOrderTakenTime(String orderTakenTime) {
		this.orderTakenTime = orderTakenTime;
	}

	public String getOriginalScheduledDate() {
		return originalScheduledDate;
	}

	public void setOriginalScheduledDate(String originalScheduledDate) {
		this.originalScheduledDate = originalScheduledDate;
	}

	public String getOriginalTimeFrom() {
		return originalTimeFrom;
	}

	public void setOriginalTimeFrom(String originalTimeFrom) {
		this.originalTimeFrom = originalTimeFrom;
	}

	public String getOriginalTimeTo() {
		return originalTimeTo;
	}

	public void setOriginalTimeTo(String originalTimeTo) {
		this.originalTimeTo = originalTimeTo;
	}

	public String getPermanentInstructions() {
		return permanentInstructions;
	}

	public void setPermanentInstructions(String permanentInstructions) {
		this.permanentInstructions = permanentInstructions;
	}

	public String getPromisedDate() {
		return promisedDate;
	}

	public void setPromisedDate(String promisedDate) {
		this.promisedDate = promisedDate;
	}

	public String getPromisedTimeFrom() {
		return promisedTimeFrom;
	}

	public void setPromisedTimeFrom(String promisedTimeFrom) {
		this.promisedTimeFrom = promisedTimeFrom;
	}

	public String getPromisedTimeTo() {
		return promisedTimeTo;
	}

	public void setPromisedTimeTo(String promisedTimeTo) {
		this.promisedTimeTo = promisedTimeTo;
	}

	public RepairLocation getRepairLocation() {
		return repairLocation;
	}

	public void setRepairLocation(RepairLocation repairLocation) {
		this.repairLocation = repairLocation;
	}

	public String getRequestedService() {
		return requestedService;
	}

	public void setRequestedService(String requestedService) {
		this.requestedService = requestedService;
	}

	public SalesCheck getSalesCheck() {
		return salesCheck;
	}

	public void setSalesCheck(SalesCheck salesCheck) {
		this.salesCheck = salesCheck;
	}

	public String getServiceOrderStatusCode() {
		return serviceOrderStatusCode;
	}

	public void setServiceOrderStatusCode(String serviceOrderStatusCode) {
		this.serviceOrderStatusCode = serviceOrderStatusCode;
	}

	public String getServiceOrderType() {
		return serviceOrderType;
	}

	public void setServiceOrderType(String serviceOrderType) {
		this.serviceOrderType = serviceOrderType;
	}

	public String getServiceUnitNumber() {
		return serviceUnitNumber;
	}

	public void setServiceUnitNumber(String serviceUnitNumber) {
		this.serviceUnitNumber = serviceUnitNumber;
	}

	public String getSpecialInstructions1() {
		return specialInstructions1;
	}

	public void setSpecialInstructions1(String specialInstructions1) {
		this.specialInstructions1 = specialInstructions1;
	}

	public String getSpecialInstructions2() {
		return specialInstructions2;
	}

	public void setSpecialInstructions2(String specialInstructions2) {
		this.specialInstructions2 = specialInstructions2;
	}

	public String getTechId() {
		return techId;
	}

	public void setTechId(String techId) {
		this.techId = techId;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getServiceOrderNumber() {
		return serviceOrderNumber;
	}

	public void setServiceOrderNumber(String serviceOederNumber) {
		this.serviceOrderNumber = serviceOederNumber;
	}

	public Messages getMessages() {
		return messages;
	}

	public void setMessages(Messages messages) {
		this.messages = messages;
	}

	public JobCodes getJobCodes() {
		return jobCodes;
	}

	public void setJobCodes(JobCodes jobCodes) {
		this.jobCodes = jobCodes;
	}
	
	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}
	
	public String toString() {
		return new StringBuffer("ServiceOrderObject:")
				.append(serviceUnitNumber).append(":")
				.append(serviceOrderNumber).append(":")
				.append(serviceOrderType).append(":")
				.append(transactionType).toString() ;
	}
	
	public String getUniqueKey() {
		return serviceUnitNumber + serviceOrderNumber;
	}

	public int compareTo(ServiceOrder o) {		
		final int BEFORE = -1;
		final int EQUAL = 0;
		final int AFTER = 1;
		int r = this.getUniqueKey().compareTo(o.getUniqueKey());
		if (r == EQUAL) {
			if (this.transactionType.equals(o.getServiceOrderType()))
				return r;
			else if (this.transactionType.equals("NEW"))
				return BEFORE;
			else 
				return AFTER;
		}
		return r;
	}

}
