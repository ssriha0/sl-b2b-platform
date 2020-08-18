package com.newco.marketplace.buyeroutboundnotification.beans;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("ORDER")
public class ResponseOrder {
	
	@XStreamAlias("salescheck")
	private String salescheck;
	
	@XStreamAlias("salesDate")
	private String salesDate;
	
	@XStreamAlias("serviceUnitNumber")
	private String serviceUnitNumber;
	
	@XStreamAlias("serviceOrderNumber")
	private String serviceOrderNumber;
	
	@XStreamAlias("responseCode")
	private String responseCode;
	
	@XStreamAlias("ERRORCODES")
	private ErrorCodes errorCodes;

	public String getSalescheck() {
		return salescheck;
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

	public ErrorCodes getErrorCodes() {
		return errorCodes;
	}

	public void setErrorCodes(ErrorCodes errorCodes) {
		this.errorCodes = errorCodes;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

}
