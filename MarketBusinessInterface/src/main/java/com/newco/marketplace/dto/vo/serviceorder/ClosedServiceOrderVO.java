package com.newco.marketplace.dto.vo.serviceorder;

import java.sql.Timestamp;

import com.newco.marketplace.webservices.base.CommonVO;

/**
 * This class would act as a VO class for retrieving closed SO Details for provider.
 * 
 * @author Infosys
 * @version 1.0
 */

public class ClosedServiceOrderVO extends CommonVO {
	
	private static final long serialVersionUID = 1L;

	private String soId;
	private Timestamp createdDate;
	private Timestamp closedDate;
	private Integer buyerId;
	private String sowTitle;
	private String sowDs;
	private String providerInstructions;
	private String buyerTerms;
	private Integer serviceDateTypeId;
	private Timestamp serviceDate1;
	private Timestamp serviceDate2;
	private String serviceTimeStart;
	private String serviceTimeEnd;
	private String serviceLocationTimeZone;
	private Double spendLimitLabor;
	private Double spendLimitParts;
	private Double LaborFinalPrice=0.0;
	private Double PartsFinalPrice=0.0;
	private String priceModel;
	private Integer locTypeId;
	private String locnName;
	private String street1;
	private String street2;
	private String city ;
	private String state;
	private String zip;
	private String zip4;
	private String serviceFirstName;
	private String serviceLastName;
	private String serviceEmail;
	private String servicePhoneNo;
	private String servicePhoneNoExt;
	private Integer servicePhoneTypeId;
	private Integer servicePhoneClassId;
	private String serviceAltphoneNo;
	private String serviceAltphoneNoExt;
	private Integer serviceAltphoneTypeId;
	private Integer serviceAltphoneClassId;
	private String buyerSupportFirstName;
	private String buyerSupportLastName;
	private String buyerSupportEmail;
	private String buyerSupportPhoneNo;
	private String buyerSupportPhoneNoExt;
	private String buyerSupportCellNo;
	private Double overallRatingScore;
	private String comments;
	private Timestamp reviewDate;
	
	
	
	public Double getLaborFinalPrice() {
		return LaborFinalPrice;
	}
	public void setLaborFinalPrice(Double laborFinalPrice) {
		LaborFinalPrice = laborFinalPrice;
	}
	public Double getPartsFinalPrice() {
		return PartsFinalPrice;
	}
	public void setPartsFinalPrice(Double partsFinalPrice) {
		PartsFinalPrice = partsFinalPrice;
	}
	public Timestamp getClosedDate() {
		return closedDate;
	}
	public void setClosedDate(Timestamp closedDate) {
		this.closedDate = closedDate;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public String getProviderInstructions() {
		return providerInstructions;
	}
	public void setProviderInstructions(String providerInstructions) {
		this.providerInstructions = providerInstructions;
	}
		
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public String getSowDs() {
		return sowDs;
	}
	public void setSowDs(String sowDs) {
		this.sowDs = sowDs;
	}
	public String getSowTitle() {
		return sowTitle;
	}
	public void setSowTitle(String sowTitle) {
		this.sowTitle = sowTitle;
	}
	
	public Double getSpendLimitLabor() {
		if(spendLimitLabor == null){
			spendLimitLabor = new Double(0.0);
		}
		return spendLimitLabor;
	}
	public void setSpendLimitLabor(Double spendLimitLabor) {
		this.spendLimitLabor = spendLimitLabor;
	}
	public Double getSpendLimitParts() {
		if(spendLimitParts == null){
			return 0.0;
		}else{
			return spendLimitParts;
		}
	}
	public void setSpendLimitParts(Double spendLimitParts) {
		this.spendLimitParts = spendLimitParts;
	}
	public Timestamp getServiceDate1() {
		return serviceDate1;
	}
	public void setServiceDate1(Timestamp serviceDate1) {
		this.serviceDate1 = serviceDate1;
	}
	public Timestamp getServiceDate2() {
		return serviceDate2;
	}
	public void setServiceDate2(Timestamp serviceDate2) {
		this.serviceDate2 = serviceDate2;
	}
	public String getServiceTimeStart() {
		return serviceTimeStart;
	}
	public void setServiceTimeStart(String serviceTimeStart) {
		this.serviceTimeStart = serviceTimeStart;
	}
	public String getServiceTimeEnd() {
		return serviceTimeEnd;
	}
	public void setServiceTimeEnd(String serviceTimeEnd) {
		this.serviceTimeEnd = serviceTimeEnd;
	}        
		
	
	public Integer getServiceDateTypeId() {
		return serviceDateTypeId;
	}
	public void setServiceDateTypeId(Integer serviceDateTypeId) {
		this.serviceDateTypeId = serviceDateTypeId;
	}
	
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	public String getServiceLocationTimeZone() {
		return serviceLocationTimeZone;
	}
	public void setServiceLocationTimeZone(String serviceLocationTimeZone) {
		this.serviceLocationTimeZone = serviceLocationTimeZone;
	}
	
	public String getPriceModel()
	{
		return priceModel;
	}
	public void setPriceModel(String priceModel)
	{
		this.priceModel = priceModel;
	}

	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getBuyerTerms() {
		return buyerTerms;
	}
	public void setBuyerTerms(String buyerTerms) {
		this.buyerTerms = buyerTerms;
	}
	public Integer getLocTypeId() {
		return locTypeId;
	}
	public void setLocTypeId(Integer locTypeId) {
		this.locTypeId = locTypeId;
	}
	public String getLocnName() {
		return locnName;
	}
	public void setLocnName(String locnName) {
		this.locnName = locnName;
	}
	public String getStreet1() {
		return street1;
	}
	public void setStreet1(String street1) {
		this.street1 = street1;
	}
	public String getStreet2() {
		return street2;
	}
	public void setStreet2(String street2) {
		this.street2 = street2;
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
	public String getZip4() {
		return zip4;
	}
	public void setZip4(String zip4) {
		this.zip4 = zip4;
	}
	public String getServiceFirstName() {
		return serviceFirstName;
	}
	public void setServiceFirstName(String serviceFirstName) {
		this.serviceFirstName = serviceFirstName;
	}
	public String getServiceLastName() {
		return serviceLastName;
	}
	public void setServiceLastName(String serviceLastName) {
		this.serviceLastName = serviceLastName;
	}
	public String getServiceEmail() {
		return serviceEmail;
	}
	public void setServiceEmail(String serviceEmail) {
		this.serviceEmail = serviceEmail;
	}
	public String getServicePhoneNo() {
		return servicePhoneNo;
	}
	public void setServicePhoneNo(String servicePhoneNo) {
		this.servicePhoneNo = servicePhoneNo;
	}
	public String getServicePhoneNoExt() {
		return servicePhoneNoExt;
	}
	public void setServicePhoneNoExt(String servicePhoneNoExt) {
		this.servicePhoneNoExt = servicePhoneNoExt;
	}
	public Integer getServicePhoneTypeId() {
		return servicePhoneTypeId;
	}
	public void setServicePhoneTypeId(Integer servicePhoneTypeId) {
		this.servicePhoneTypeId = servicePhoneTypeId;
	}
	public Integer getServicePhoneClassId() {
		return servicePhoneClassId;
	}
	public void setServicePhoneClassId(Integer servicePhoneClassId) {
		this.servicePhoneClassId = servicePhoneClassId;
	}
	public String getServiceAltphoneNo() {
		return serviceAltphoneNo;
	}
	public void setServiceAltphoneNo(String serviceAltphoneNo) {
		this.serviceAltphoneNo = serviceAltphoneNo;
	}
	public String getServiceAltphoneNoExt() {
		return serviceAltphoneNoExt;
	}
	public void setServiceAltphoneNoExt(String serviceAltphoneNoExt) {
		this.serviceAltphoneNoExt = serviceAltphoneNoExt;
	}
	public Integer getServiceAltphoneTypeId() {
		return serviceAltphoneTypeId;
	}
	public void setServiceAltphoneTypeId(Integer serviceAltphoneTypeId) {
		this.serviceAltphoneTypeId = serviceAltphoneTypeId;
	}
	public Integer getServiceAltphoneClassId() {
		return serviceAltphoneClassId;
	}
	public void setServiceAltphoneClassId(Integer serviceAltphoneClassId) {
		this.serviceAltphoneClassId = serviceAltphoneClassId;
	}
	public String getBuyerSupportFirstName() {
		return buyerSupportFirstName;
	}
	public void setBuyerSupportFirstName(String buyerSupportFirstName) {
		this.buyerSupportFirstName = buyerSupportFirstName;
	}
	public String getBuyerSupportLastName() {
		return buyerSupportLastName;
	}
	public void setBuyerSupportLastName(String buyerSupportLastName) {
		this.buyerSupportLastName = buyerSupportLastName;
	}
	public String getBuyerSupportEmail() {
		return buyerSupportEmail;
	}
	public void setBuyerSupportEmail(String buyerSupportEmail) {
		this.buyerSupportEmail = buyerSupportEmail;
	}
	public String getBuyerSupportPhoneNo() {
		return buyerSupportPhoneNo;
	}
	public void setBuyerSupportPhoneNo(String buyerSupportPhoneNo) {
		this.buyerSupportPhoneNo = buyerSupportPhoneNo;
	}
	public String getBuyerSupportPhoneNoExt() {
		return buyerSupportPhoneNoExt;
	}
	public void setBuyerSupportPhoneNoExt(String buyerSupportPhoneNoExt) {
		this.buyerSupportPhoneNoExt = buyerSupportPhoneNoExt;
	}
	public String getBuyerSupportCellNo() {
		return buyerSupportCellNo;
	}
	public void setBuyerSupportCellNo(String buyerSupportCellNo) {
		this.buyerSupportCellNo = buyerSupportCellNo;
	}
	public Double getOverallRatingScore() {
		return overallRatingScore;
	}
	public void setOverallRatingScore(Double overallRatingScore) {
		this.overallRatingScore = overallRatingScore;
	}
	public Timestamp getReviewDate() {
		return reviewDate;
	}
	public void setReviewDate(Timestamp reviewDate) {
		this.reviewDate = reviewDate;
	}

	
}
