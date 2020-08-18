package com.newco.marketplace.leadoutboundnotification.beans;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("camscreatesorequest")
public class RequestLeadDetails {
	
	@XStreamAlias("customernum")
	private String custNum;
	
	@XStreamAlias("customerfirstname")
	private String custFirstName;
	
	@XStreamAlias("customermiddlename")
	private String custMiddleName;
	
	@XStreamAlias("customerlastname")
	private String custLastName;
	
	@XStreamAlias("address1")
	private String address1;
	
	@XStreamAlias("address2")
	private String address2;
	
	@XStreamAlias("aptnum")
	private String aptNum;
	
	@XStreamAlias("city")
	private String city;
	
	@XStreamAlias("state")
	private String state;
	
	@XStreamAlias("zip")
	private String zip;
	
	@XStreamAlias("zipext")
	private String zipExt;
	
	@XStreamAlias("phonenum")
	private String phoneNum;
	
	@XStreamAlias("alternatephonenum")
	private String alternatePhoneNum;
	
	@XStreamAlias("repairaddress")
	private String repairAddress;
	
	@XStreamAlias("repairaddressapt")
	private String repairAddressApt;
	
	@XStreamAlias("repairaddresscity")
	private String repairAddressCity;
	
	@XStreamAlias("repairaddressstate")
	private String repairAddressState;
	
	@XStreamAlias("repairaddresszip")
	private String repairAddressZip;
	
	@XStreamAlias("repairaddresszipext")
	private String repairAddressZipExt;
	
	@XStreamAlias("repairphonenum")
	private String repairPhoneNum;
	
	@XStreamAlias("repairaddresscrossstreet")
	private String repairAddressCrossStreet;
	
	@XStreamAlias("itemsuffix")
	private String itemSuffix;
	
	@XStreamAlias("merchandisecode")
	private String merchandiseCode;
	
	@XStreamAlias("brand")
	private String brand;
	
	@XStreamAlias("modelnum")
	private String modelNum;
	
	@XStreamAlias("serialnum")
	private String serialNum;
	
	@XStreamAlias("division")
	private String division;
	
	@XStreamAlias("stocknumber")
	private String stockNumber;
	
	@XStreamAlias("coveragecode")
	private String coverageCode;
	
	@XStreamAlias("purchaseddate")
	private String purchasedDate;

	@XStreamAlias("searspurchase")
	private String searsPurchase;
	
	@XStreamAlias("servicerequest")
	private String serviceRequest;
	
	@XStreamAlias("specialinstructions")
	private String specialInstructions;
	
	@XStreamAlias("serviceunitnum")
	private String serviceUnitNum;
	
	@XStreamAlias("servicescheduledate")
	private String serviceScheduleDate;
		
	@XStreamAlias("serviceschedulefromtime")
	private String serviceScheduleFromTime;
	
	@XStreamAlias("servicescheduletotime")
	private String serviceScheduleToTime;
	
	@XStreamAlias("emergencyserviceflag")
	private String emergencyServiceFlag;

	@XStreamAlias("promotema")
	private String promotema;
	
	@XStreamAlias("donotpromotemareason")
	private String doNotPromotemaReason;
	
	@XStreamAlias("purchaseordernum")
	private String purchaseOrderNum;
	
	@XStreamAlias("paymentmethod")
	private String paymentMethod;
	
	@XStreamAlias("accountnum")
	private String accountNum;
	
	@XStreamAlias("expirationdate")
	private String expirationDate;
	
	@XStreamAlias("promoteother")
	private String promoteOther;
	
	@XStreamAlias("donotpromoteotherreason")
	private String doNotPromoteOtherReason;
	
	@XStreamAlias("thirdpartyid")
	private String thirdPartyId;
	
	@XStreamAlias("thirdpartyauthid")
	private String thirdPartyAuthId;
	
	@XStreamAlias("thirdpartycontractnum")
	private String thirdPartyContractNum;
	
	@XStreamAlias("thirdpartycontractexpdate")
	private String thirdPartyContractExpDate;
	
	@XStreamAlias("geocode")
	private String geoCode;
	
	@XStreamAlias("countycode")
	private String countyCode;
	
	@XStreamAlias("verazipstate")
	private String verazipState;
	
	@XStreamAlias("countyname")
	private String countyName;
	
	@XStreamAlias("inoutcitylimits")
	private String inOutCityLimits;
	
	@XStreamAlias("resubmitsoind")
	private String resubmitSoInd;
	
	@XStreamAlias("capacityarea")
	private String capacityArea;
	
	@XStreamAlias("capacityneeded")
	private String capacityNeeded;
	
	@XStreamAlias("workarea")
	private String workArea;
	
	public String getRepairAddress() {
		return repairAddress;
	}

	public void setRepairAddress(String repairAddress) {
		this.repairAddress = repairAddress;
	}

	public String getRepairAddressApt() {
		return repairAddressApt;
	}

	public void setRepairAddressApt(String repairAddressApt) {
		this.repairAddressApt = repairAddressApt;
	}

	public String getRepairAddressCity() {
		return repairAddressCity;
	}

	public void setRepairAddressCity(String repairAddressCity) {
		this.repairAddressCity = repairAddressCity;
	}

	public String getRepairAddressState() {
		return repairAddressState;
	}

	public void setRepairAddressState(String repairAddressState) {
		this.repairAddressState = repairAddressState;
	}

	public String getRepairAddressZip() {
		return repairAddressZip;
	}

	public void setRepairAddressZip(String repairAddressZip) {
		this.repairAddressZip = repairAddressZip;
	}

	public String getRepairAddressZipExt() {
		return repairAddressZipExt;
	}

	public void setRepairAddressZipExt(String repairAddressZipExt) {
		this.repairAddressZipExt = repairAddressZipExt;
	}

	public String getRepairPhoneNum() {
		return repairPhoneNum;
	}

	public void setRepairPhoneNum(String repairPhoneNum) {
		this.repairPhoneNum = repairPhoneNum;
	}

	public String getRepairAddressCrossStreet() {
		return repairAddressCrossStreet;
	}

	public void setRepairAddressCrossStreet(String repairAddressCrossStreet) {
		this.repairAddressCrossStreet = repairAddressCrossStreet;
	}

	public String getItemSuffix() {
		return itemSuffix;
	}

	public void setItemSuffix(String itemSuffix) {
		this.itemSuffix = itemSuffix;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModelNum() {
		return modelNum;
	}

	public void setModelNum(String modelNum) {
		this.modelNum = modelNum;
	}

	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getStockNumber() {
		return stockNumber;
	}

	public void setStockNumber(String stockNumber) {
		this.stockNumber = stockNumber;
	}

	public String getSpecialInstructions() {
		return specialInstructions;
	}

	public void setSpecialInstructions(String specialInstructions) {
		this.specialInstructions = specialInstructions;
	}

	public String getPromotema() {
		return promotema;
	}

	public void setPromotema(String promotema) {
		this.promotema = promotema;
	}

	public String getDoNotPromotemaReason() {
		return doNotPromotemaReason;
	}

	public void setDoNotPromotemaReason(String doNotPromotemaReason) {
		this.doNotPromotemaReason = doNotPromotemaReason;
	}

	public String getPurchaseOrderNum() {
		return purchaseOrderNum;
	}

	public void setPurchaseOrderNum(String purchaseOrderNum) {
		this.purchaseOrderNum = purchaseOrderNum;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getPromoteOther() {
		return promoteOther;
	}

	public void setPromoteOther(String promoteOther) {
		this.promoteOther = promoteOther;
	}

	public String getDoNotPromoteOtherReason() {
		return doNotPromoteOtherReason;
	}

	public void setDoNotPromoteOtherReason(String doNotPromoteOtherReason) {
		this.doNotPromoteOtherReason = doNotPromoteOtherReason;
	}

	public String getThirdPartyId() {
		return thirdPartyId;
	}

	public void setThirdPartyId(String thirdPartyId) {
		this.thirdPartyId = thirdPartyId;
	}

	public String getThirdPartyAuthId() {
		return thirdPartyAuthId;
	}

	public void setThirdPartyAuthId(String thirdPartyAuthId) {
		this.thirdPartyAuthId = thirdPartyAuthId;
	}

	public String getThirdPartyContractNum() {
		return thirdPartyContractNum;
	}

	public void setThirdPartyContractNum(String thirdPartyContractNum) {
		this.thirdPartyContractNum = thirdPartyContractNum;
	}

	public String getThirdPartyContractExpDate() {
		return thirdPartyContractExpDate;
	}

	public void setThirdPartyContractExpDate(String thirdPartyContractExpDate) {
		this.thirdPartyContractExpDate = thirdPartyContractExpDate;
	}

	public String getGeoCode() {
		return geoCode;
	}

	public void setGeoCode(String geoCode) {
		this.geoCode = geoCode;
	}

	public String getCountyCode() {
		return countyCode;
	}

	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}

	public String getVerazipState() {
		return verazipState;
	}

	public void setVerazipState(String verazipState) {
		this.verazipState = verazipState;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public String getInOutCityLimits() {
		return inOutCityLimits;
	}

	public void setInOutCityLimits(String inOutCityLimits) {
		this.inOutCityLimits = inOutCityLimits;
	}

	public String getResubmitSoInd() {
		return resubmitSoInd;
	}

	public void setResubmitSoInd(String resubmitSoInd) {
		this.resubmitSoInd = resubmitSoInd;
	}

	public String getCapacityArea() {
		return capacityArea;
	}

	public void setCapacityArea(String capacityArea) {
		this.capacityArea = capacityArea;
	}

	public String getCapacityNeeded() {
		return capacityNeeded;
	}

	public void setCapacityNeeded(String capacityNeeded) {
		this.capacityNeeded = capacityNeeded;
	}

	public String getWorkArea() {
		return workArea;
	}

	public void setWorkArea(String workArea) {
		this.workArea = workArea;
	}

	public String getCustFirstName() {
		return custFirstName;
	}

	public void setCustFirstName(String custFirstName) {
		this.custFirstName = custFirstName;
	}

	public String getCustMiddleName() {
		return custMiddleName;
	}

	public void setCustMiddleName(String custMiddleName) {
		this.custMiddleName = custMiddleName;
	}

	public String getCustLastName() {
		return custLastName;
	}

	public void setCustLastName(String custLastName) {
		this.custLastName = custLastName;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAptNum() {
		return aptNum;
	}

	public void setAptNum(String aptNum) {
		this.aptNum = aptNum;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getZipExt() {
		return zipExt;
	}

	public void setZipExt(String zipExt) {
		this.zipExt = zipExt;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getAlternatePhoneNum() {
		return alternatePhoneNum;
	}

	public void setAlternatePhoneNum(String alternatePhoneNum) {
		this.alternatePhoneNum = alternatePhoneNum;
	}

	public String getMerchandiseCode() {
		return merchandiseCode;
	}

	public void setMerchandiseCode(String merchandiseCode) {
		this.merchandiseCode = merchandiseCode;
	}

	public String getCoverageCode() {
		return coverageCode;
	}

	public void setCoverageCode(String coverageCode) {
		this.coverageCode = coverageCode;
	}

	public String getSearsPurchase() {
		return searsPurchase;
	}

	public void setSearsPurchase(String searsPurchase) {
		this.searsPurchase = searsPurchase;
	}

	public String getServiceUnitNum() {
		return serviceUnitNum;
	}

	public void setServiceUnitNum(String serviceUnitNum) {
		this.serviceUnitNum = serviceUnitNum;
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

	public String getServiceScheduleToTime() {
		return serviceScheduleToTime;
	}

	public void setServiceScheduleToTime(String serviceScheduleToTime) {
		this.serviceScheduleToTime = serviceScheduleToTime;
	}

	public String getEmergencyServiceFlag() {
		return emergencyServiceFlag;
	}

	public void setEmergencyServiceFlag(String emergencyServiceFlag) {
		this.emergencyServiceFlag = emergencyServiceFlag;
	}

	public String getCustNum() {
		return custNum;
	}

	public void setCustNum(String custNum) {
		this.custNum = custNum;
	}

	public String getPurchasedDate() {
		return purchasedDate;
	}

	public void setPurchasedDate(String purchasedDate) {
		this.purchasedDate = purchasedDate;
	}

	public String getServiceRequest() {
		return serviceRequest;
	}

	public void setServiceRequest(String serviceRequest) {
		this.serviceRequest = serviceRequest;
	}
}
