package com.newco.marketplace.vo.leadprofile;

import java.util.Date;
import java.util.List;
public class LeadProfileDetailsVO{

	private String leadPartnerId;
	private Integer leadStatusId;
	private String leadStatusDesc;
	private String providerFirmId;	
	private String leadEmailId;	
	private String leadPhoneNo;   
	private String leadDailyLimit;
	private String leadSmsNo;	
	private String leadMonthlyLimit;
	private Integer urgencyNextDay;
	private Integer urgencySameDay;
	//Business Address	
	private List<String> projectType;
	private String locationType;
	//Setting variable for available days of week
	private Integer mondayInd;
	private Integer tuesndayInd;
	private Integer wednesdayInd;
	private Integer thursdayInd;
	private Integer fridayInd;
	private Integer saturdayInd;
	private Integer sundayInd;
	private String serviceTime;
	private Date createdDate;
	private Date modifedDate;
	private String createdBy;
	private Double monthlyBudget;
	private String modifiedBy;
	private List<LeadServicePriceVO> leadServicePrice;
	
	//parameters for createNewPartner API
	private String login;
	private String password;
	private String companyName;
	private String firstName;
	private String lastName;
	private String address;
	private String street2;
	private String city;
	private String state;
	private String country;
	private String zip;
	private String fax;
	private String website;
	private String contactEmail;
	private Integer status;
	private Integer statusReason;
	private Integer newsLetter;
	private String comments;
	private Integer salesRepId;
	private String promoCode;
	private Integer partnerlabel;
	private Integer secPartnerLabel;
	private Integer partnerGroup;
		
	//parameters for competitiveInsertFilterSet API
	private String filterSetName;
	private Double filterSetPrice;
	private String acceptedSource;
	private Integer matchPriority;
	private String acceptManuallyReviewedLeads;
	private Integer zipmode;
	private String skill;
	
	
	public Double getMonthlyBudget() {
		return monthlyBudget;
	}
	public void setMonthlyBudget(Double monthlyBudget) {
		this.monthlyBudget = monthlyBudget;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getModifedDate() {
		return modifedDate;
	}
	public void setModifedDate(Date modifedDate) {
		this.modifedDate = modifedDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	
	
	
	public List<LeadServicePriceVO> getLeadServicePrice() {
		return leadServicePrice;
	}
	public void setLeadServicePrice(List<LeadServicePriceVO> leadServicePrice) {
		this.leadServicePrice = leadServicePrice;
	}
	public String getProviderFirmId() {
		return providerFirmId;
	}
	public void setProviderFirmId(String providerFirmId) {
		this.providerFirmId = providerFirmId;
	}
	public String getLeadEmailId() {
		return leadEmailId;
	}
	public void setLeadEmailId(String leadEmailId) {
		this.leadEmailId = leadEmailId;
	}
	
	public String getLeadPhoneNo() {
		return leadPhoneNo;
	}
	public void setLeadPhoneNo(String leadPhoneNo) {
		this.leadPhoneNo = leadPhoneNo;
	}

	public String getLeadDailyLimit() {
		return leadDailyLimit;
	}
	public void setLeadDailyLimit(String leadDailyLimit) {
		this.leadDailyLimit = leadDailyLimit;
	}
	
	public String getLeadMonthlyLimit() {
		return leadMonthlyLimit;
	}
	public void setLeadMonthlyLimit(String leadMonthlyLimit) {
		this.leadMonthlyLimit = leadMonthlyLimit;
	}
	public Integer getUrgencyNextDay() {
		return urgencyNextDay;
	}
	public void setUrgencyNextDay(Integer urgencyNextDay) {
		this.urgencyNextDay = urgencyNextDay;
	}
	public Integer getUrgencySameDay() {
		return urgencySameDay;
	}
	public void setUrgencySameDay(Integer urgencySameDay) {
		this.urgencySameDay = urgencySameDay;
	}
	public List<String> getProjectType() {
		return projectType;
	}
	public void setProjectType(List<String> projectType) {
		this.projectType = projectType;
	}
	public String getLocationType() {
		return locationType;
	}
	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}
	public String getLeadSmsNo() {
		return leadSmsNo;
	}
	public void setLeadSmsNo(String leadSmsNo) {
		this.leadSmsNo = leadSmsNo;
	}
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getContactEmail() {
		return contactEmail;
	}
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getStatusReason() {
		return statusReason;
	}
	public void setStatusReason(Integer statusReason) {
		this.statusReason = statusReason;
	}
	public Integer getNewsLetter() {
		return newsLetter;
	}
	public void setNewsLetter(Integer newsLetter) {
		this.newsLetter = newsLetter;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Integer getSalesRepId() {
		return salesRepId;
	}
	public void setSalesRepId(Integer salesRepId) {
		this.salesRepId = salesRepId;
	}
	public String getPromoCode() {
		return promoCode;
	}
	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}
	public Integer getPartnerlabel() {
		return partnerlabel;
	}
	public void setPartnerlabel(Integer partnerlabel) {
		this.partnerlabel = partnerlabel;
	}
	public Integer getSecPartnerLabel() {
		return secPartnerLabel;
	}
	public void setSecPartnerLabel(Integer secPartnerLabel) {
		this.secPartnerLabel = secPartnerLabel;
	}
	public Integer getPartnerGroup() {
		return partnerGroup;
	}
	public void setPartnerGroup(Integer partnerGroup) {
		this.partnerGroup = partnerGroup;
	}
	public String getFilterSetName() {
		return filterSetName;
	}
	public void setFilterSetName(String filterSetName) {
		this.filterSetName = filterSetName;
	}
	public Double getFilterSetPrice() {
		return filterSetPrice;
	}
	public void setFilterSetPrice(Double filterSetPrice) {
		this.filterSetPrice = filterSetPrice;
	}
	public String getAcceptedSource() {
		return acceptedSource;
	}
	public void setAcceptedSource(String acceptedSource) {
		this.acceptedSource = acceptedSource;
	}
	public Integer getMatchPriority() {
		return matchPriority;
	}
	public void setMatchPriority(Integer matchPriority) {
		this.matchPriority = matchPriority;
	}
	public String getAcceptManuallyReviewedLeads() {
		return acceptManuallyReviewedLeads;
	}
	public void setAcceptManuallyReviewedLeads(String acceptManuallyReviewedLeads) {
		this.acceptManuallyReviewedLeads = acceptManuallyReviewedLeads;
	}
	public Integer getZipmode() {
		return zipmode;
	}
	public void setZipmode(Integer zipmode) {
		this.zipmode = zipmode;
	}
	public String getSkill() {
		return skill;
	}
	public void setSkill(String skill) {
		this.skill = skill;
	}
	public Integer getMondayInd() {
		return mondayInd;
	}
	public void setMondayInd(Integer mondayInd) {
		this.mondayInd = mondayInd;
	}
	public Integer getTuesndayInd() {
		return tuesndayInd;
	}
	public void setTuesndayInd(Integer tuesndayInd) {
		this.tuesndayInd = tuesndayInd;
	}
	public Integer getWednesdayInd() {
		return wednesdayInd;
	}
	public void setWednesdayInd(Integer wednesdayInd) {
		this.wednesdayInd = wednesdayInd;
	}
	public Integer getThursdayInd() {
		return thursdayInd;
	}
	public void setThursdayInd(Integer thursdayInd) {
		this.thursdayInd = thursdayInd;
	}
	public Integer getFridayInd() {
		return fridayInd;
	}
	public void setFridayInd(Integer fridayInd) {
		this.fridayInd = fridayInd;
	}
	public Integer getSaturdayInd() {
		return saturdayInd;
	}
	public void setSaturdayInd(Integer saturdayInd) {
		this.saturdayInd = saturdayInd;
	}
	public Integer getSundayInd() {
		return sundayInd;
	}
	public void setSundayInd(Integer sundayInd) {
		this.sundayInd = sundayInd;
	}
	public String getServiceTime() {
		return serviceTime;
	}
	public void setServiceTime(String serviceTime) {
		this.serviceTime = serviceTime;
	}
	public String getStreet2() {
		return street2;
	}
	public void setStreet2(String street2) {
		this.street2 = street2;
	}
	public String getLeadPartnerId() {
		return leadPartnerId;
	}
	public void setLeadPartnerId(String leadPartnerId) {
		this.leadPartnerId = leadPartnerId;
	}
	public Integer getLeadStatus() {
		return leadStatusId;
	}
	public void setLeadStatus(Integer leadStatus) {
		this.leadStatusId = leadStatus;
	}
	public String getLeadStatusDesc() {
		return leadStatusDesc;
	}
	public void setLeadStatusDesc(String leadStatusDesc) {
		this.leadStatusDesc = leadStatusDesc;
	}
		
}
