package com.newco.marketplace.dto.vo.serviceorder;

import java.sql.Date;

import com.newco.marketplace.dto.vo.LocationVO;
import com.newco.marketplace.webservices.base.CommonVO;

/**
 * $Revision$ $Author$ $Date$
 */
public class Buyer extends CommonVO {

	private static final long serialVersionUID = 4092472119099217858L;
	private Integer buyerId;
	private String sourceId;
	
	private String userName = null;
	private String identity;
	private Date createdDate;
	private Date modifiedDate;
	private String modifiedBy;
	private LocationVO buyerPrimaryLocation;
	private LocationVO buyerBillingLocation;
	private Contact buyerContact;
    private Integer contactId;
    private Integer roleId;
	private Integer priLocnId;
	private Integer billLocnId;
	private Integer fundingTypeId;
	private Double  postingFee;
	private Double  cancellationFee;
	private String businessStarted;
	private String primaryIndustry;
	private String busPhoneNo;
	private String busFaxNo;
	private String busExtn;
	private String howDidYouHear;
	private String businessType;
	private String companySize;
	private String salesVolume;
	private String webAddress;
	private String promotionCode;
	private String einNoEnc = null;
	private String einNo = null;
	private Integer foreignOwnedInd;
	private Integer foreignOwnedPct = -1;
	private Double aggregateRatingScore;
	private Integer accountContactId = null;
	private String serviceLiveBucksInd;
	private Date serviceLiveBucksAcceptedDate;
	private String termsAndCondition;
	private Date termsAndConditionAcceptedDate;
	private String termsAndConditionId;
	private boolean permitInd = false;
	
	private String businessName= "";
	private Integer minTimeWindow;
	private Integer maxTimeWindow;
	
	public Integer getMinTimeWindow() {
		return minTimeWindow;
	}
	public void setMinTimeWindow(Integer minTimeWindow) {
		this.minTimeWindow = minTimeWindow;
	}
	public Integer getMaxTimeWindow() {
		return maxTimeWindow;
	}
	public void setMaxTimeWindow(Integer maxTimeWindow) {
		this.maxTimeWindow = maxTimeWindow;
	}
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public LocationVO getBuyerPrimaryLocation() {
		return buyerPrimaryLocation;
	}
	public void setBuyerPrimaryLocation(LocationVO buyerPrimaryLocation) {
		this.buyerPrimaryLocation = buyerPrimaryLocation;
	}
	public LocationVO getBuyerBillingLocation() {
		return buyerBillingLocation;
	}
	public void setBuyerBillingLocation(LocationVO buyerBillingLocation) {
		this.buyerBillingLocation = buyerBillingLocation;
	}
	public Contact getBuyerContact() {
		return buyerContact;
	}
	public void setBuyerContact(Contact buyerContact) {
		this.buyerContact = buyerContact;
	}
	public Integer getContactId() {
		return contactId;
	}
	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}
	public Integer getPriLocnId() {
		return priLocnId;
	}
	public void setPriLocnId(Integer priLocnId) {
		this.priLocnId = priLocnId;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public Integer getFundingTypeId() {
		return fundingTypeId;
	}
	public void setFundingTypeId(Integer fundingTypeId) {
		this.fundingTypeId = fundingTypeId;
	}
	public Double getPostingFee() {
		return postingFee;
	}
	public void setPostingFee(Double postingFee) {
		this.postingFee = postingFee;
	}
	public Double getCancellationFee() {
		return cancellationFee;
	}
	public void setCancellationFee(Double cancellationFee) {
		this.cancellationFee = cancellationFee;
	}
	public String getBusinessStarted() {
		return businessStarted;
	}
	public void setBusinessStarted(String businessStarted) {
		this.businessStarted = businessStarted;
	}
	public String getPrimaryIndustry() {
		return primaryIndustry;
	}
	public void setPrimaryIndustry(String primaryIndustry) {
		this.primaryIndustry = primaryIndustry;
	}
	public String getBusPhoneNo() {
		return busPhoneNo;
	}
	public void setBusPhoneNo(String busPhoneNo) {
		this.busPhoneNo = busPhoneNo;
	}
	public String getBusFaxNo() {
		return busFaxNo;
	}
	public void setBusFaxNo(String busFaxNo) {
		this.busFaxNo = busFaxNo;
	}
	public String getHowDidYouHear() {
		return howDidYouHear;
	}
	public void setHowDidYouHear(String howDidYouHear) {
		this.howDidYouHear = howDidYouHear;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public String getCompanySize() {
		return companySize;
	}
	public void setCompanySize(String companySize) {
		this.companySize = companySize;
	}
	public String getSalesVolume() {
		return salesVolume;
	}
	public void setSalesVolume(String salesVolume) {
		this.salesVolume = salesVolume;
	}
	public String getWebAddress() {
		return webAddress;
	}
	public void setWebAddress(String webAddress) {
		this.webAddress = webAddress;
	}
	public String getPromotionCode() {
		return promotionCode;
	}
	public void setPromotionCode(String promotionCode) {
		this.promotionCode = promotionCode;
	}

	public Integer getBillLocnId() {
		return billLocnId;
	}
	public void setBillLocnId(Integer billLocnId) {
		this.billLocnId = billLocnId;
	}
	public String getBusExtn() {
		return busExtn;
	}
	public void setBusExtn(String busExtn) {
		this.busExtn = busExtn;
	}
	public String getEinNoEnc() {
		return einNoEnc;
	}
	public void setEinNoEnc(String einNoEnc) {
		this.einNoEnc = einNoEnc;
	}
	public String getEinNo() {
		return einNo;
	}
	public void setEinNo(String einNo) {
		this.einNo = einNo;
	}
	public Integer getForeignOwnedInd() {
		return foreignOwnedInd;
	}
	public void setForeignOwnedInd(Integer foreignOwnedInd) {
		this.foreignOwnedInd = foreignOwnedInd;
	}
	public Integer getForeignOwnedPct() {
		return foreignOwnedPct;
	}
	public void setForeignOwnedPct(Integer foreignOwnedPct) {
		this.foreignOwnedPct = foreignOwnedPct;
	}
	public Double getAggregateRatingScore() {
		return aggregateRatingScore;
	}
	public void setAggregateRatingScore(Double aggregateRatingScore) {
		this.aggregateRatingScore = aggregateRatingScore;
	}
	public Integer getAccountContactId() {
		return accountContactId;
	}
	public void setAccountContactId(Integer accountContactId) {
		this.accountContactId = accountContactId;
	}
	public String getServiceLiveBucksInd() {
		return serviceLiveBucksInd;
	}
	public void setServiceLiveBucksInd(String serviceLiveBucksInd) {
		this.serviceLiveBucksInd = serviceLiveBucksInd;
	}
	public Date getServiceLiveBucksAcceptedDate() {
		return serviceLiveBucksAcceptedDate;
	}
	public void setServiceLiveBucksAcceptedDate(Date serviceLiveBucksAcceptedDate) {
		this.serviceLiveBucksAcceptedDate = serviceLiveBucksAcceptedDate;
	}
	public String getTermsAndCondition() {
		return termsAndCondition;
	}
	public void setTermsAndCondition(String termsAndCondition) {
		this.termsAndCondition = termsAndCondition;
	}
	public Date getTermsAndConditionAcceptedDate() {
		return termsAndConditionAcceptedDate;
	}
	public void setTermsAndConditionAcceptedDate(Date termsAndConditionAcceptedDate) {
		this.termsAndConditionAcceptedDate = termsAndConditionAcceptedDate;
	}
	public String getTermsAndConditionId() {
		return termsAndConditionId;
	}
	public void setTermsAndConditionId(String termsAndConditionId) {
		this.termsAndConditionId = termsAndConditionId;
	}
    /** @return the roleId */
    public Integer getRoleId() {
        return roleId;
    }
    /** @param roleId the roleId to set */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
	public boolean isPermitInd() {
		return permitInd;
	}
	public void setPermitInd(boolean permitInd) {
		this.permitInd = permitInd;
	}
	private String dob;
	private String altIDDocType;
	private String altIDCountryIssue;
	private Integer piiIndex;

	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	
	public String getAltIDDocType() {
		return altIDDocType;
	}
	public void setAltIDDocType(String altIDDocType) {
		this.altIDDocType = altIDDocType;
	}

	public String getAltIDCountryIssue() {
		return altIDCountryIssue;
	}
	public void setAltIDCountryIssue(String altIDCountryIssue) {
		this.altIDCountryIssue = altIDCountryIssue;
	}

	public Integer getPiiIndex() {
		return piiIndex;
	}
	public void setPiiIndex(Integer piiIndex) {
		this.piiIndex = piiIndex;
	}
	
	
	
	
	
	
}
