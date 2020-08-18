package com.newco.marketplace.dto.vo.leadsmanagement;


import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder; 

public class InsideSalesLeadVO { 
	
	private Integer oldIsleadId;
	private Integer isleadId;
	private String id;
	private String accountTypeId;
	private String externalId;
	private String ownerUserId;
	private String ownerUserIds;
	private String ownerFirstName;
	private String ownerLastName;
	private String dateCreated;
	private String createdByUserId;
	private String dateModified;
	private String modifiedByUserId;
	private String deleted;
	private String dateDeleted;
	private String accountId;
	private String accountNumber;
	private String namePrefix;
	private String firstName;
	private String middleName;
	private String lastName;
	private String birthdate;
	private String title;
	private String phone;
	private String mobilePhone;
	private String fax;
	private String homePhone;
	private String otherPhone;
	private String email;
	private String emailOptOut;
	private String website;
	private String addr1;
	private String addr2;
	private String city;
	private String state;
	private String stateAbbrev;
	private String zip;
	private String country;
	private String countryAbbrev;
	private String assistantFirstName;
	private String assistantLastName;
	private String assistantPhone;
	private String companyName;
	private String industry;
	private String annualRevenue;
	private String tickerSymbol;
	private String numberOfEmployees;
	private String companyWebsite;
	private String accountOwnership;
	private String campaignId;
	private String campaign;
	private String status;
	private String previousStatus;

	private String webOfferType;
	private String source;
	private String rating;
	private String description;
	private String externalObject;
	private String doNotCall;
	private String fedDoNotCall;
	private String leadScore;
	private String contactScore;
	private String closeScore;
	private String statusChangedDate;
	private String lastPurchase;
	private String lastInquiry;
	private String SICCode;
	private String companySizeId;
	private String stateId;
	private String mergedDate;
	private String descriptionId;
	private String ratingId;
	private String assistantContactInfoId;
	private String addressId;
	private String industryId;
	private String statusId;
	private String salesforceOwnerId;
	private String adId;
	private String adGroupId;
	private String sourceId;
	private String deduped;
	private String adCampaignId;
	private String accountSite;
	private String searchEngineId;
	private String masterLeadId;
	private String webOfferTypeId;
	private String accountOwnershipId;
	private String appointmentSet;
	private String slCreatedDate;
	private String slModifiedDate;
	
	private String statusChangedTime;
	private String changerId;
	private String changer;
	private String owner;
	private Date createdDate;


	

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAccountTypeId() {
		return accountTypeId;
	}
	public void setAccountTypeId(String accountTypeId) {
		this.accountTypeId = accountTypeId;
	}
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public String getOwnerUserId() {
		return ownerUserId;
	}
	public void setOwnerUserId(String ownerUserId) {
		this.ownerUserId = ownerUserId;
	}
	public String getOwnerUserIds() {
		return ownerUserIds;
	}
	public void setOwnerUserIds(String ownerUserIds) {
		this.ownerUserIds = ownerUserIds;
	}
	public String getOwnerFirstName() {
		return ownerFirstName;
	}
	public void setOwnerFirstName(String ownerFirstName) {
		this.ownerFirstName = ownerFirstName;
	}
	public String getOwnerLastName() {
		return ownerLastName;
	}
	public void setOwnerLastName(String ownerLastName) {
		this.ownerLastName = ownerLastName;
	}
	public String getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}
	public String getCreatedByUserId() {
		return createdByUserId;
	}
	public void setCreatedByUserId(String createdByUserId) {
		this.createdByUserId = createdByUserId;
	}
	public String getDateModified() {
		return dateModified;
	}
	public void setDateModified(String dateModified) {
		this.dateModified = dateModified;
	}
	public String getModifiedByUserId() {
		return modifiedByUserId;
	}
	public void setModifiedByUserId(String modifiedByUserId) {
		this.modifiedByUserId = modifiedByUserId;
	}
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	public String getDateDeleted() {
		return dateDeleted;
	}
	public void setDateDeleted(String dateDeleted) {
		this.dateDeleted = dateDeleted;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getNamePrefix() {
		return namePrefix;
	}
	public void setNamePrefix(String namePrefix) {
		this.namePrefix = namePrefix;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getHomePhone() {
		return homePhone;
	}
	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}
	public String getOtherPhone() {
		return otherPhone;
	}
	public void setOtherPhone(String otherPhone) {
		this.otherPhone = otherPhone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmailOptOut() {
		return emailOptOut;
	}
	public void setEmailOptOut(String emailOptOut) {
		this.emailOptOut = emailOptOut;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getAddr1() {
		return addr1;
	}
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}
	public String getAddr2() {
		return addr2;
	}
	public void setAddr2(String addr2) {
		this.addr2 = addr2;
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
	public String getStateAbbrev() {
		return stateAbbrev;
	}
	public void setStateAbbrev(String stateAbbrev) {
		this.stateAbbrev = stateAbbrev;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCountryAbbrev() {
		return countryAbbrev;
	}
	public void setCountryAbbrev(String countryAbbrev) {
		this.countryAbbrev = countryAbbrev;
	}
	public String getAssistantFirstName() {
		return assistantFirstName;
	}
	public void setAssistantFirstName(String assistantFirstName) {
		this.assistantFirstName = assistantFirstName;
	}
	public String getAssistantLastName() {
		return assistantLastName;
	}
	public void setAssistantLastName(String assistantLastName) {
		this.assistantLastName = assistantLastName;
	}
	public String getAssistantPhone() {
		return assistantPhone;
	}
	public void setAssistantPhone(String assistantPhone) {
		this.assistantPhone = assistantPhone;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getAnnualRevenue() {
		return annualRevenue;
	}
	public void setAnnualRevenue(String annualRevenue) {
		this.annualRevenue = annualRevenue;
	}
	public String getTickerSymbol() {
		return tickerSymbol;
	}
	public void setTickerSymbol(String tickerSymbol) {
		this.tickerSymbol = tickerSymbol;
	}
	public String getNumberOfEmployees() {
		return numberOfEmployees;
	}
	public void setNumberOfEmployees(String numberOfEmployees) {
		this.numberOfEmployees = numberOfEmployees;
	}
	public String getCompanyWebsite() {
		return companyWebsite;
	}
	public void setCompanyWebsite(String companyWebsite) {
		this.companyWebsite = companyWebsite;
	}
	public String getAccountOwnership() {
		return accountOwnership;
	}
	public void setAccountOwnership(String accountOwnership) {
		this.accountOwnership = accountOwnership;
	}
	public String getCampaignId() {
		return campaignId;
	}
	public void setCampaignId(String campaignId) {
		this.campaignId = campaignId;
	}
	public String getCampaign() {
		return campaign;
	}
	public void setCampaign(String campaign) {
		this.campaign = campaign;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getWebOfferType() {
		return webOfferType;
	}
	public void setWebOfferType(String webOfferType) {
		this.webOfferType = webOfferType;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getExternalObject() {
		return externalObject;
	}
	public void setExternalObject(String externalObject) {
		this.externalObject = externalObject;
	}
	public String getDoNotCall() {
		return doNotCall;
	}
	public void setDoNotCall(String doNotCall) {
		this.doNotCall = doNotCall;
	}
	public String getFedDoNotCall() {
		return fedDoNotCall;
	}
	public void setFedDoNotCall(String fedDoNotCall) {
		this.fedDoNotCall = fedDoNotCall;
	}
	public String getLeadScore() {
		return leadScore;
	}
	public void setLeadScore(String leadScore) {
		this.leadScore = leadScore;
	}
	public String getContactScore() {
		return contactScore;
	}
	public void setContactScore(String contactScore) {
		this.contactScore = contactScore;
	}
	public String getCloseScore() {
		return closeScore;
	}
	public void setCloseScore(String closeScore) {
		this.closeScore = closeScore;
	}
	public String getStatusChangedDate() {
		return statusChangedDate;
	}
	public void setStatusChangedDate(String statusChangedDate) {
		this.statusChangedDate = statusChangedDate;
	}
	public String getLastPurchase() {
		return lastPurchase;
	}
	public void setLastPurchase(String lastPurchase) {
		this.lastPurchase = lastPurchase;
	}
	public String getLastInquiry() {
		return lastInquiry;
	}
	public void setLastInquiry(String lastInquiry) {
		this.lastInquiry = lastInquiry;
	}
	public String getSICCode() {
		return SICCode;
	}
	public void setSICCode(String sICCode) {
		SICCode = sICCode;
	}
	public String getCompanySizeId() {
		return companySizeId;
	}
	public void setCompanySizeId(String companySizeId) {
		this.companySizeId = companySizeId;
	}
	public String getStateId() {
		return stateId;
	}
	public void setStateId(String stateId) {
		this.stateId = stateId;
	}
	public String getMergedDate() {
		return mergedDate;
	}
	public void setMergedDate(String mergedDate) {
		this.mergedDate = mergedDate;
	}
	public String getDescriptionId() {
		return descriptionId;
	}
	public void setDescriptionId(String descriptionId) {
		this.descriptionId = descriptionId;
	}
	public String getRatingId() {
		return ratingId;
	}
	public void setRatingId(String ratingId) {
		this.ratingId = ratingId;
	}
	public String getAssistantContactInfoId() {
		return assistantContactInfoId;
	}
	public void setAssistantContactInfoId(String assistantContactInfoId) {
		this.assistantContactInfoId = assistantContactInfoId;
	}
	public String getAddressId() {
		return addressId;
	}
	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}
	public String getIndustryId() {
		return industryId;
	}
	public void setIndustryId(String industryId) {
		this.industryId = industryId;
	}
	public String getStatusId() {
		return statusId;
	}
	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}
	public String getSalesforceOwnerId() {
		return salesforceOwnerId;
	}
	public void setSalesforceOwnerId(String salesforceOwnerId) {
		this.salesforceOwnerId = salesforceOwnerId;
	}
	public String getAdId() {
		return adId;
	}
	public void setAdId(String adId) {
		this.adId = adId;
	}
	public String getAdGroupId() {
		return adGroupId;
	}
	public void setAdGroupId(String adGroupId) {
		this.adGroupId = adGroupId;
	}
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	public String getDeduped() {
		return deduped;
	}
	public void setDeduped(String deduped) {
		this.deduped = deduped;
	}
	public String getAdCampaignId() {
		return adCampaignId;
	}
	public void setAdCampaignId(String adCampaignId) {
		this.adCampaignId = adCampaignId;
	}
	public String getAccountSite() {
		return accountSite;
	}
	public void setAccountSite(String accountSite) {
		this.accountSite = accountSite;
	}
	public String getSearchEngineId() {
		return searchEngineId;
	}
	public void setSearchEngineId(String searchEngineId) {
		this.searchEngineId = searchEngineId;
	}
	public String getMasterLeadId() {
		return masterLeadId;
	}
	public void setMasterLeadId(String masterLeadId) {
		this.masterLeadId = masterLeadId;
	}
	public String getWebOfferTypeId() {
		return webOfferTypeId;
	}
	public void setWebOfferTypeId(String webOfferTypeId) {
		this.webOfferTypeId = webOfferTypeId;
	}
	public String getAccountOwnershipId() {
		return accountOwnershipId;
	}
	public void setAccountOwnershipId(String accountOwnershipId) {
		this.accountOwnershipId = accountOwnershipId;
	}
	public String getAppointmentSet() {
		return appointmentSet;
	}
	public void setAppointmentSet(String appointmentSet) {
		this.appointmentSet = appointmentSet;
	}
	public String getSlCreatedDate() {
		return slCreatedDate;
	}
	public void setSlCreatedDate(String slCreatedDate) {
		this.slCreatedDate = slCreatedDate;
	}
	public String getSlModifiedDate() {
		return slModifiedDate;
	}
	public void setSlModifiedDate(String slModifiedDate) {
		this.slModifiedDate = slModifiedDate;
	}

	
	public String getPreviousStatus() {
		return previousStatus;
	}
	public void setPreviousStatus(String previousStatus) {
		this.previousStatus = previousStatus;
	}
	
	
	public String getStatusChangedTime() {
		return statusChangedTime;
	}
	public void setStatusChangedTime(String statusChangedTime) {
		this.statusChangedTime = statusChangedTime;
	}
	
	
	
	public String getChangerId() {
		return changerId;
	}
	public void setChangerId(String changerId) {
		this.changerId = changerId;
	}
	
	
	public String getChanger() {
		return changer;
	}
	public void setChanger(String changer) {
		this.changer = changer;
	}
	
	
	
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	
	public Integer getIsleadId() {
		return isleadId;
	}
	public void setIsleadId(Integer isleadId) {
		this.isleadId = isleadId;
	}
	
	
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	public Integer getOldIsleadId() {
		return oldIsleadId;
	}
	public void setOldIsleadId(Integer oldIsleadId) {
		this.oldIsleadId = oldIsleadId;
	}
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	
	
	 
	
	
	

	
	
}
