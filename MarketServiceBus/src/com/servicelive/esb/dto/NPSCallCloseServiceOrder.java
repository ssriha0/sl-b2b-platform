package com.servicelive.esb.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("CallCloseInstallationServiceOrder")
public class NPSCallCloseServiceOrder {
	
	@XStreamAlias("ServiceUnitNumber")
	private String unitNo;
	
	@XStreamAlias("ServiceOrderNumber")
	private String orderNo;
	
	@XStreamAlias("SalesCheck")
	private NPSSalesCheck salesCheck;
	
	@XStreamAlias("RouteDate")
	private String routeDate;
	
	@XStreamAlias("InstalledDate")
	private String installedDate;
	
	@XStreamAlias("TechID")
	private String techID;
	
	@XStreamAlias("CallCode")
	private String callCode;
	
	@XStreamAlias("ServiceOrderStatusCode")
	private String statusCode;
	
	@XStreamAlias("ServiceOrderType")
	private String orderType;
	
	@XStreamAlias("TechComments")
	private String techComments;
	
	@XStreamAlias("LastModifiedByEmployeeID")
	private String modifiedBy = "";
	
	@XStreamAlias("SubAccountCode")
	private String subAccountCode = "";
	
	@XStreamAlias("ChargeStoreNumber")
	private String chargeStoreNo = "";
	
	@XStreamAlias("Monetary")
	private NPSMonetary monetary;
	
	@XStreamAlias("Discounts")
	private NPSDiscounts discounts;
	
	@XStreamAlias("Payment")
	private NPSPayment payment;
	
	@XStreamAlias("Merchandise")
	private NPSMerchandise merchandise;
	
	@XStreamAlias("JobCodes")
	private NPSJobCodes jobCodes;
	
	@XStreamAlias("MerchandiseDisposition")
	private NPSMerchandiseDisposition merchandiseDisposition;

	public String getUnitNo() {
		return unitNo;
	}

	public void setUnitNo(String unitNo) {
		this.unitNo = unitNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public NPSSalesCheck getSalesCheck() {
		return salesCheck;
	}

	public void setSalesCheck(NPSSalesCheck salesCheck) {
		this.salesCheck = salesCheck;
	}

	public String getRouteDate() {
		return routeDate;
	}

	public void setRouteDate(String routeDate) {
		this.routeDate = routeDate;
	}

	public String getInstalledDate() {
		return installedDate;
	}

	public void setInstalledDate(String installedDate) {
		this.installedDate = installedDate;
	}

	public String getTechID() {
		return techID;
	}

	public void setTechID(String techID) {
		this.techID = techID;
	}

	public String getCallCode() {
		return callCode;
	}

	public void setCallCode(String callCode) {
		this.callCode = callCode;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getTechComments() {
		return techComments;
	}

	public void setTechComments(String techComments) {
		this.techComments = techComments;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getSubAccountCode() {
		return subAccountCode;
	}

	public void setSubAccountCode(String subAccountCode) {
		this.subAccountCode = subAccountCode;
	}

	public String getChargeStoreNo() {
		return chargeStoreNo;
	}

	public void setChargeStoreNo(String chargeStoreNo) {
		this.chargeStoreNo = chargeStoreNo;
	}

	public NPSMonetary getMonetary() {
		return monetary;
	}

	public void setMonetary(NPSMonetary monetary) {
		this.monetary = monetary;
	}

	public NPSDiscounts getDiscounts() {
		return discounts;
	}

	public void setDiscounts(NPSDiscounts discounts) {
		this.discounts = discounts;
	}

	public NPSPayment getPayment() {
		return payment;
	}

	public void setPayment(NPSPayment payment) {
		this.payment = payment;
	}

	public NPSMerchandise getMerchandise() {
		return merchandise;
	}

	public void setMerchandise(NPSMerchandise merchandise) {
		this.merchandise = merchandise;
	}

	public NPSJobCodes getJobCodes() {
		return jobCodes;
	}

	public void setJobCodes(NPSJobCodes jobCodes) {
		this.jobCodes = jobCodes;
	}

	public NPSMerchandiseDisposition getMerchandiseDisposition() {
		return merchandiseDisposition;
	}

	public void setMerchandiseDisposition(
			NPSMerchandiseDisposition merchandiseDisposition) {
		this.merchandiseDisposition = merchandiseDisposition;
	}

}
