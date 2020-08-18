package com.newco.marketplace.buyeroutboundnotification.beans;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("ORDER")
public class RequestOrder {
	
	@XStreamAlias("clientId")
	private String clientid;
	
	@XStreamAlias("salescheck")
	private String salescheck;
	
	@XStreamAlias("salesDate")
	private String salesDate;
	
	@XStreamAlias("serviceUnitNumber")
	private String serviceUnitNumber;
	
	@XStreamAlias("serviceOrderNumber")
	private String serviceOrderNumber;
	
	@XStreamAlias("serviceScheduleDate")
	private String serviceScheduleDate;
	
	@XStreamAlias("serviceScheduleToTime")
	private String serviceScheduletoTime;
	
	@XStreamAlias("serviceScheduleFromTime")
	private String serviceScheduleFromTime;
	
	@XStreamAlias("serviceOrderSpecialInstruction1")
	private String serviceOrderSpecialInstruction1;
	
	@XStreamAlias("serviceOrderSpecialInstruction2")
	private String serviceOrderSpecialInstruction2;
	
	@XStreamAlias("customerRepairAtAddress")
	private String customerAddress;
	
	@XStreamAlias("customerRepairAtCity")
	private String customerCity;
	
	@XStreamAlias("customerRepairAtState")
	private String customerState;
	
	@XStreamAlias("customerRepairAtZipCode")
	private String customerZipCode;
	
	@XStreamAlias("customerRepairAtZipCodeSuffix")
	private String customerZipCodeSuffix;
	
	@XStreamAlias("customerPrimaryPhoneNumber")
	private String customerPrimaryPhoneNumber;
	
	@XStreamAlias("customerRepairAtPhoneNumber")
	private String customerAlternatePhoneNumber;
	
	
	@XStreamAlias("merchSerialNumber")
	private Integer merchSerialNumber;
	
	@XStreamAlias("merchModelNumber")
	private Integer merchModelNumber;
	
	@XStreamAlias("merchManufactureBrandName")
	private String merchManufuactureBrandName;
	
	@XStreamAlias("serviceOrderRescheduledFlag")
	private String serviceOrderRescheduledFlag;
	
	@XStreamAlias("RESCHEDINFORMATION")
	private RequestReschedInformation requestReschedInformation;
	
	@XStreamAlias("MESSAGES")
	private RequestMessages messages;
	
	@XStreamImplicit(itemFieldName="JOBCODE")
	private List<RequestJobcode> Jobcode;

	public String getSalescheck() {
		return salescheck;
	}

	public String getServiceScheduleDate() {
		return serviceScheduleDate;
	}

	public void setServiceScheduleDate(String serviceScheduleDate) {
		this.serviceScheduleDate = serviceScheduleDate;
	}

	public String getServiceScheduleFromTime() {
		return serviceScheduleFromTime;
	}

	public void setServiceScheduleFromTime(String serviceScheduleFromTime) {
		this.serviceScheduleFromTime = serviceScheduleFromTime;
	}

	public String getServiceScheduletoTime() {
		return serviceScheduletoTime;
	}

	public void setServiceScheduletoTime(String serviceScheduletoTime) {
		this.serviceScheduletoTime = serviceScheduletoTime;
	}

	public void setSalescheck(String salescheck) {
		this.salescheck = salescheck;
	}

	public String getSalesDate() {
		return salesDate;
	}

	public void setSalesDate(String salesDate) {
		this.salesDate = salesDate;
	}


	public String getServiceUnitNumber() {
		return serviceUnitNumber;
	}

	public void setServiceUnitNumber(String serviceUnitNumber) {
		this.serviceUnitNumber = serviceUnitNumber;
	}

	public String getServiceOrderNumber() {
		return serviceOrderNumber;
	}

	public void setServiceOrderNumber(String serviceOrderNumber) {
		this.serviceOrderNumber = serviceOrderNumber;
	}

	public String getServiceOrderSpecialInstruction1() {
		return serviceOrderSpecialInstruction1;
	}

	public void setServiceOrderSpecialInstruction1(
			String serviceOrderSpecialInstruction1) {
		this.serviceOrderSpecialInstruction1 = serviceOrderSpecialInstruction1;
	}

	public String getServiceOrderSpecialInstruction2() {
		return serviceOrderSpecialInstruction2;
	}

	public void setServiceOrderSpecialInstruction2(
			String serviceOrderSpecialInstruction2) {
		this.serviceOrderSpecialInstruction2 = serviceOrderSpecialInstruction2;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public String getCustomerCity() {
		return customerCity;
	}

	public void setCustomerCity(String customerCity) {
		this.customerCity = customerCity;
	}

	public String getCustomerState() {
		return customerState;
	}

	public void setCustomerState(String customerState) {
		this.customerState = customerState;
	}

	public String getCustomerZipCode() {
		return customerZipCode;
	}

	public void setCustomerZipCode(String customerZipCode) {
		this.customerZipCode = customerZipCode;
	}

	public Integer getMerchSerialNumber() {
		return merchSerialNumber;
	}

	public void setMerchSerialNumber(Integer merchSerialNumber) {
		this.merchSerialNumber = merchSerialNumber;
	}

	public Integer getMerchModelNumber() {
		return merchModelNumber;
	}

	public void setMerchModelNumber(Integer merchModelNumber) {
		this.merchModelNumber = merchModelNumber;
	}

	public String getMerchManufuactureBrandName() {
		return merchManufuactureBrandName;
	}

	public void setMerchManufuactureBrandName(String merchManufuactureBrandName) {
		this.merchManufuactureBrandName = merchManufuactureBrandName;
	}
	

	public String getServiceOrderRescheduledFlag() {
		return serviceOrderRescheduledFlag;
	}

	public void setServiceOrderRescheduledFlag(String serviceOrderRescheduledFlag) {
		this.serviceOrderRescheduledFlag = serviceOrderRescheduledFlag;
	}

	public RequestReschedInformation getRequestReschedInformation() {
		return requestReschedInformation;
	}

	public void setRequestReschedInformation(
			RequestReschedInformation requestReschedInformation) {
		this.requestReschedInformation = requestReschedInformation;
	}

	public List<RequestJobcode> getJobcode() {
		return Jobcode;
	}

	public void setJobcode(List<RequestJobcode> jobcode) {
		Jobcode = jobcode;
	}

	public String getClientid() {
		return clientid;
	}

	public void setClientid(String clientid) {
		this.clientid = clientid;
	}

	public RequestMessages getMessages() {
		return messages;
	}

	public void setMessages(RequestMessages messages) {
		this.messages = messages;
	}

	public String getCustomerPrimaryPhoneNumber() {
		return customerPrimaryPhoneNumber;
	}

	public void setCustomerPrimaryPhoneNumber(String customerPrimaryPhoneNumber) {
		this.customerPrimaryPhoneNumber = customerPrimaryPhoneNumber;
	}

	public String getCustomerAlternatePhoneNumber() {
		return customerAlternatePhoneNumber;
	}

	public void setCustomerAlternatePhoneNumber(
			String customerAlternatePhoneNumber) {
		this.customerAlternatePhoneNumber = customerAlternatePhoneNumber;
	}

	public String getCustomerZipCodeSuffix() {
		return customerZipCodeSuffix;
	}

	public void setCustomerZipCodeSuffix(String customerZipCodeSuffix) {
		this.customerZipCodeSuffix = customerZipCodeSuffix;
	}

	
	
	

}
