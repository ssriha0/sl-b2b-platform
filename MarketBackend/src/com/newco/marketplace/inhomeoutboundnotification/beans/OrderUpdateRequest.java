package com.newco.marketplace.inhomeoutboundnotification.beans;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * This is a bean class for storing request information for 
 * the In Home Out bound Notification Service
 * @author Infosys
 */
@XStreamAlias("OrderUpdateRequest")
public class OrderUpdateRequest {
	
	@XStreamAlias("CorrelationId")
	private String CorrelationId;
	
	@XStreamAlias("orderType")
	private String orderType;
	
	@XStreamAlias("unitNum")
	private String unitNum;
	
	@XStreamAlias("orderNum")
	private String orderNum;
	
	@XStreamAlias("routeDate")
	private String routeDate;
	
	@XStreamAlias("techId")
	private String techId;	
	
	@XStreamAlias("callCd")
	private String callCd;
	
	@XStreamAlias("serviceFromTime")
	private String serviceFromTime;

	@XStreamAlias("serviceToTime")
	private String serviceToTime;
	
	@XStreamAlias("applBrand")
	private String applBrand;
	
	@XStreamAlias("modelNum")
	private String modelNum;
	
	@XStreamAlias("techComments")
	private String techComments;
	
	@XStreamAlias("jobCodeData")
	private JobCodeData jobCodeData;
	
	@XStreamAlias("partsDatas")
	@XStreamImplicit(itemFieldName="partsDatas")
	private List<PartsDatas> partsDatas = new ArrayList<PartsDatas>();

	@XStreamAlias("chargeCd")
	private String chargeCd;
	
	@XStreamAlias("chargeStoreNum")
	private String chargeStoreNum;
	
	@XStreamAlias("transit")
	private String transit;
	
	@XStreamAlias("reschdDate")
	private String reschdDate;

	@XStreamAlias("reschdFromTime")
	private String reschdFromTime;

	@XStreamAlias("reschdToTime")
	private String reschdToTime;

	@XStreamAlias("estMinAmt")
	private String estMinAmt;

	@XStreamAlias("estMaxAmt")
	private String estMaxAmt;
	
	@XStreamAlias("primaryAmountCollected")
	private String primaryAmountCollected;
	
	@XStreamAlias("secondaryAmountCollected")
	private String secondaryAmountCollected;
	
	@XStreamAlias("laborAssociateDiscount")
	private String laborAssociateDiscount;

	@XStreamAlias("laborPromotionDiscount")
	private String laborPromotionDiscount;

	@XStreamAlias("laborCouponDiscount")
	private String laborCouponDiscount;

	@XStreamAlias("laborDiscountReasonCode1")
	private String laborDiscountReasonCode1;

	@XStreamAlias("laborDiscountReasonCode2")
	private String laborDiscountReasonCode2;

	@XStreamAlias("partDiscount")
	private String partDiscount;

	@XStreamAlias("partPromotionDiscount")
	private String partPromotionDiscount;

	@XStreamAlias("partCouponDiscount")
	private String partCouponDiscount;

	@XStreamAlias("partDiscountReasonCode1")
	private String partDiscountReasonCode1;

	@XStreamAlias("partDiscountReasonCode2")
	private String partDiscountReasonCode2;

	@XStreamAlias("agreeDiscount")
	private String agreeDiscount;

	@XStreamAlias("agreePromotionDiscount")
	private String agreePromotionDiscount;

	@XStreamAlias("agreeCouponDiscount")
	private String agreeCouponDiscount;

	@XStreamAlias("agreeDiscountReasonCode1")
	private String agreeDiscountReasonCode1;

	@XStreamAlias("agreeDiscountReasonCode2")
	private String agreeDiscountReasonCode2;

	@XStreamAlias("paymentMethodIndicator")
	private String paymentMethodIndicator;

	@XStreamAlias("paymentAccountNumber")
	private String paymentAccountNumber;

	@XStreamAlias("paymentExpirationDate")
	private String paymentExpirationDate;

	@XStreamAlias("AuthorizationNumber")
	private String AuthorizationNumber;

	@XStreamAlias("secondaryPaymentMethodIndicator")
	private String secondaryPaymentMethodIndicator;

	@XStreamAlias("secondaryPaymentAccountNumber")
	private String secondaryPaymentAccountNumber;

	@XStreamAlias("secondaryPaymentExpirationDate")
	private String secondaryPaymentExpirationDate;

	@XStreamAlias("secondaryAuthorizationNumber")
	private String secondaryAuthorizationNumber;
	
	@XStreamAlias("laborPpdAmt")
	private String laborPpdAmt;

	@XStreamAlias("refNum")
	private String refNum;

	@XStreamAlias("taxExmptNum")
	private String taxExmptNum;

	@XStreamAlias("sywrMemberId")
	private String sywrMemberId;

	@XStreamAlias("srvPrdSold")
	private String srvPrdSold;

	@XStreamAlias("altServiceLocationFlag")
	private String altServiceLocationFlag;

	@XStreamAlias("altServiceLocationNumber")
	private String altServiceLocationNumber;

	@XStreamAlias("repairTagBarcode")
	private String repairTagBarcode;

	@XStreamAlias("reasonCode")
	private String reasonCode;	

	@XStreamAlias("updateCustomerFl")
	private String updateCustomerFl;
	
	@XStreamAlias("customerData")
	private CustomerData customerData;

	@XStreamAlias("updateMerchandiseFl")
	private String updateMerchandiseFl;
	
	@XStreamAlias("merchandiseData")
	private MerchandiseData merchandiseData;
	
	@XStreamAlias("updateUdfFl")
	private String updateUdfFl;
	
	@XStreamAlias("userDefFields")
	private UserDefFields userDefFields;

	@XStreamAlias("sendMessageFl")
	private String sendMessageFl;
	
	@XStreamAlias("sendMsgData")
	private SendMsgData sendMsgData;
	
	//not part of request
	@XStreamOmitField
	private String soId;

	
	public String getCorrelationId() {
		return CorrelationId;
	}

	public void setCorrelationId(String correlationId) {
		CorrelationId = correlationId;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getUnitNum() {
		return unitNum;
	}

	public void setUnitNum(String unitNum) {
		this.unitNum = unitNum;
	}

	public String getTechId() {
		return techId;
	}

	public void setTechId(String techId) {
		this.techId = techId;
	}

	public String getRouteDate() {
		return routeDate;
	}

	public void setRouteDate(String routeDate) {
		this.routeDate = routeDate;
	}

	public String getCallCd() {
		return callCd;
	}

	public void setCallCd(String callCd) {
		this.callCd = callCd;
	}

	public String getServiceFromTime() {
		return serviceFromTime;
	}

	public void setServiceFromTime(String serviceFromTime) {
		this.serviceFromTime = serviceFromTime;
	}

	public String getServiceToTime() {
		return serviceToTime;
	}

	public void setServiceToTime(String serviceToTime) {
		this.serviceToTime = serviceToTime;
	}

	public String getChargeStoreNum() {
		return chargeStoreNum;
	}

	public void setChargeStoreNum(String chargeStoreNum) {
		this.chargeStoreNum = chargeStoreNum;
	}

	public JobCodeData getJobCodeData() {
		return jobCodeData;
	}

	public void setJobCodeData(JobCodeData jobCodeData) {
		this.jobCodeData = jobCodeData;
	}

	public String getSecondaryAmountCollected() {
		return secondaryAmountCollected;
	}

	public void setSecondaryAmountCollected(String secondaryAmountCollected) {
		this.secondaryAmountCollected = secondaryAmountCollected;
	}

	public String getUpdateCustomerFl() {
		return updateCustomerFl;
	}

	public void setUpdateCustomerFl(String updateCustomerFl) {
		this.updateCustomerFl = updateCustomerFl;
	}

	public CustomerData getCustomerData() {
		return customerData;
	}

	public void setCustomerData(CustomerData customerData) {
		this.customerData = customerData;
	}

	public String getUpdateMerchandiseFl() {
		return updateMerchandiseFl;
	}

	public void setUpdateMerchandiseFl(String updateMerchandiseFl) {
		this.updateMerchandiseFl = updateMerchandiseFl;
	}

	public MerchandiseData getMerchandiseData() {
		return merchandiseData;
	}

	public void setMerchandiseData(MerchandiseData merchandiseData) {
		this.merchandiseData = merchandiseData;
	}

	public String getUpdateUdfFl() {
		return updateUdfFl;
	}

	public void setUpdateUdfFl(String updateUdfFl) {
		this.updateUdfFl = updateUdfFl;
	}

	public String getSendMessageFl() {
		return sendMessageFl;
	}

	public void setSendMessageFl(String sendMessageFl) {
		this.sendMessageFl = sendMessageFl;
	}

	public String getTechComments() {
		return techComments;
	}

	public void setTechComments(String techComments) {
		this.techComments = techComments;
	}

	public String getChargeCd() {
		return chargeCd;
	}

	public void setChargeCd(String chargeCd) {
		this.chargeCd = chargeCd;
	}

	public String getTransit() {
		return transit;
	}

	public void setTransit(String transit) {
		this.transit = transit;
	}

	public String getReschdDate() {
		return reschdDate;
	}

	public void setReschdDate(String reschdDate) {
		this.reschdDate = reschdDate;
	}

	public String getReschdFromTime() {
		return reschdFromTime;
	}

	public void setReschdFromTime(String reschdFromTime) {
		this.reschdFromTime = reschdFromTime;
	}

	public String getReschdToTime() {
		return reschdToTime;
	}

	public void setReschdToTime(String reschdToTime) {
		this.reschdToTime = reschdToTime;
	}

	public String getEstMinAmt() {
		return estMinAmt;
	}

	public void setEstMinAmt(String estMinAmt) {
		this.estMinAmt = estMinAmt;
	}

	public String getEstMaxAmt() {
		return estMaxAmt;
	}

	public void setEstMaxAmt(String estMaxAmt) {
		this.estMaxAmt = estMaxAmt;
	}

	public String getPrimaryAmountCollected() {
		return primaryAmountCollected;
	}

	public void setPrimaryAmountCollected(String primaryAmountCollected) {
		this.primaryAmountCollected = primaryAmountCollected;
	}

	public String getLaborAssociateDiscount() {
		return laborAssociateDiscount;
	}

	public void setLaborAssociateDiscount(String laborAssociateDiscount) {
		this.laborAssociateDiscount = laborAssociateDiscount;
	}

	public String getLaborPromotionDiscount() {
		return laborPromotionDiscount;
	}

	public void setLaborPromotionDiscount(String laborPromotionDiscount) {
		this.laborPromotionDiscount = laborPromotionDiscount;
	}

	public String getLaborCouponDiscount() {
		return laborCouponDiscount;
	}

	public void setLaborCouponDiscount(String laborCouponDiscount) {
		this.laborCouponDiscount = laborCouponDiscount;
	}

	public String getLaborDiscountReasonCode1() {
		return laborDiscountReasonCode1;
	}

	public void setLaborDiscountReasonCode1(String laborDiscountReasonCode1) {
		this.laborDiscountReasonCode1 = laborDiscountReasonCode1;
	}

	public String getLaborDiscountReasonCode2() {
		return laborDiscountReasonCode2;
	}

	public void setLaborDiscountReasonCode2(String laborDiscountReasonCode2) {
		this.laborDiscountReasonCode2 = laborDiscountReasonCode2;
	}

	public String getPartDiscount() {
		return partDiscount;
	}

	public void setPartDiscount(String partDiscount) {
		this.partDiscount = partDiscount;
	}

	public String getPartPromotionDiscount() {
		return partPromotionDiscount;
	}

	public void setPartPromotionDiscount(String partPromotionDiscount) {
		this.partPromotionDiscount = partPromotionDiscount;
	}

	public String getPartCouponDiscount() {
		return partCouponDiscount;
	}

	public void setPartCouponDiscount(String partCouponDiscount) {
		this.partCouponDiscount = partCouponDiscount;
	}

	public String getPartDiscountReasonCode1() {
		return partDiscountReasonCode1;
	}

	public void setPartDiscountReasonCode1(String partDiscountReasonCode1) {
		this.partDiscountReasonCode1 = partDiscountReasonCode1;
	}

	public String getPartDiscountReasonCode2() {
		return partDiscountReasonCode2;
	}

	public void setPartDiscountReasonCode2(String partDiscountReasonCode2) {
		this.partDiscountReasonCode2 = partDiscountReasonCode2;
	}

	public String getAgreeDiscount() {
		return agreeDiscount;
	}

	public void setAgreeDiscount(String agreeDiscount) {
		this.agreeDiscount = agreeDiscount;
	}

	public String getAgreePromotionDiscount() {
		return agreePromotionDiscount;
	}

	public void setAgreePromotionDiscount(String agreePromotionDiscount) {
		this.agreePromotionDiscount = agreePromotionDiscount;
	}

	public String getAgreeCouponDiscount() {
		return agreeCouponDiscount;
	}

	public void setAgreeCouponDiscount(String agreeCouponDiscount) {
		this.agreeCouponDiscount = agreeCouponDiscount;
	}

	public String getAgreeDiscountReasonCode1() {
		return agreeDiscountReasonCode1;
	}

	public void setAgreeDiscountReasonCode1(String agreeDiscountReasonCode1) {
		this.agreeDiscountReasonCode1 = agreeDiscountReasonCode1;
	}

	public String getAgreeDiscountReasonCode2() {
		return agreeDiscountReasonCode2;
	}

	public void setAgreeDiscountReasonCode2(String agreeDiscountReasonCode2) {
		this.agreeDiscountReasonCode2 = agreeDiscountReasonCode2;
	}

	public String getPaymentMethodIndicator() {
		return paymentMethodIndicator;
	}

	public void setPaymentMethodIndicator(String paymentMethodIndicator) {
		this.paymentMethodIndicator = paymentMethodIndicator;
	}

	public String getPaymentAccountNumber() {
		return paymentAccountNumber;
	}

	public void setPaymentAccountNumber(String paymentAccountNumber) {
		this.paymentAccountNumber = paymentAccountNumber;
	}

	public String getPaymentExpirationDate() {
		return paymentExpirationDate;
	}

	public void setPaymentExpirationDate(String paymentExpirationDate) {
		this.paymentExpirationDate = paymentExpirationDate;
	}

	public String getAuthorizationNumber() {
		return AuthorizationNumber;
	}

	public void setAuthorizationNumber(String authorizationNumber) {
		AuthorizationNumber = authorizationNumber;
	}

	public String getSecondaryPaymentMethodIndicator() {
		return secondaryPaymentMethodIndicator;
	}

	public void setSecondaryPaymentMethodIndicator(
			String secondaryPaymentMethodIndicator) {
		this.secondaryPaymentMethodIndicator = secondaryPaymentMethodIndicator;
	}

	public String getSecondaryPaymentAccountNumber() {
		return secondaryPaymentAccountNumber;
	}

	public void setSecondaryPaymentAccountNumber(
			String secondaryPaymentAccountNumber) {
		this.secondaryPaymentAccountNumber = secondaryPaymentAccountNumber;
	}

	public String getSecondaryPaymentExpirationDate() {
		return secondaryPaymentExpirationDate;
	}

	public void setSecondaryPaymentExpirationDate(
			String secondaryPaymentExpirationDate) {
		this.secondaryPaymentExpirationDate = secondaryPaymentExpirationDate;
	}

	public String getSecondaryAuthorizationNumber() {
		return secondaryAuthorizationNumber;
	}

	public void setSecondaryAuthorizationNumber(String secondaryAuthorizationNumber) {
		this.secondaryAuthorizationNumber = secondaryAuthorizationNumber;
	}

	public UserDefFields getUserDefFields() {
		return userDefFields;
	}

	public void setUserDefFields(UserDefFields userDefFields) {
		this.userDefFields = userDefFields;
	}

	public SendMsgData getSendMsgData() {
		return sendMsgData;
	}

	public void setSendMsgData(SendMsgData sendMsgData) {
		this.sendMsgData = sendMsgData;
	}

	public String getLaborPpdAmt() {
		return laborPpdAmt;
	}

	public void setLaborPpdAmt(String laborPpdAmt) {
		this.laborPpdAmt = laborPpdAmt;
	}

	public String getRefNum() {
		return refNum;
	}

	public void setRefNum(String refNum) {
		this.refNum = refNum;
	}

	public String getTaxExmptNum() {
		return taxExmptNum;
	}

	public void setTaxExmptNum(String taxExmptNum) {
		this.taxExmptNum = taxExmptNum;
	}

	public String getSywrMemberId() {
		return sywrMemberId;
	}

	public void setSywrMemberId(String sywrMemberId) {
		this.sywrMemberId = sywrMemberId;
	}

	public String getSrvPrdSold() {
		return srvPrdSold;
	}

	public void setSrvPrdSold(String srvPrdSold) {
		this.srvPrdSold = srvPrdSold;
	}

	public String getAltServiceLocationFlag() {
		return altServiceLocationFlag;
	}

	public void setAltServiceLocationFlag(String altServiceLocationFlag) {
		this.altServiceLocationFlag = altServiceLocationFlag;
	}

	public String getAltServiceLocationNumber() {
		return altServiceLocationNumber;
	}

	public void setAltServiceLocationNumber(String altServiceLocationNumber) {
		this.altServiceLocationNumber = altServiceLocationNumber;
	}

	public String getRepairTagBarcode() {
		return repairTagBarcode;
	}

	public void setRepairTagBarcode(String repairTagBarcode) {
		this.repairTagBarcode = repairTagBarcode;
	}

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public List<PartsDatas> getPartsDatas() {
		return partsDatas;
	}

	public void setPartsDatas(List<PartsDatas> partsDatas) {
		this.partsDatas = partsDatas;
	}

	public String getApplBrand() {
		return applBrand;
	}

	public void setApplBrand(String applBrand) {
		this.applBrand = applBrand;
	}

	public String getModelNum() {
		return modelNum;
	}

	public void setModelNum(String modelNum) {
		this.modelNum = modelNum;
	}

	
}
