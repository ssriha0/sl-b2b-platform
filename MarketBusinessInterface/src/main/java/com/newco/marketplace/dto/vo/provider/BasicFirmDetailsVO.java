package com.newco.marketplace.dto.vo.provider;

import java.util.Date;


/**
 * This class would act as the VO class for getFirmDetails Service
 * @author neenu_manoharan
 *
 */
public class BasicFirmDetailsVO {
	
	private Integer firmId;
	private String businessName;
	private String overView;
	private String firstName;
	private String lastName;
	private Double firmAggregateRating;
	private Integer reviewCount;
	private Integer numberOfEmployees;
	private Double yearsOfService;
	private Double minHourlyRate;
	private Double maxHourlyRate;
	private String street1;
	private String street2;
	private String city;
	private String state;
	private String zip;
	private String zip4;
	private Date startDate;
	private String email;
	private String web;
	private String altEmail;
	private String businessPhone;
	private String businessPhoneExt;
	private String businessFax;
	//SL-21446- Adding company logo url
	private String companyLogoUrl;
	
	// SL-21456 - Adding KPIs
	private Double firmAverageTimeToAccept;
	private Double firmAverageArrivalWindow;
	
	// SL-21464
	private String primaryIndustryDesc;
	
	public Integer getFirmId() {
		return firmId;
	}
	public void setFirmId(Integer firmId) {
		this.firmId = firmId;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getOverView() {
		return overView;
	}
	public void setOverView(String overView) {
		this.overView = overView;
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
	public Double getFirmAggregateRating() {
		return firmAggregateRating;
	}
	public void setFirmAggregateRating(Double firmAggregateRating) {
		this.firmAggregateRating = firmAggregateRating;
	}
	public Integer getReviewCount() {
		return reviewCount;
	}
	public void setReviewCount(Integer reviewCount) {
		this.reviewCount = reviewCount;
	}
	public Integer getNumberOfEmployees() {
		return numberOfEmployees;
	}
	public void setNumberOfEmployees(Integer numberOfEmployees) {
		this.numberOfEmployees = numberOfEmployees;
	}
	public Double getYearsOfService() {
		return yearsOfService;
	}
	public void setYearsOfService(Double yearsOfService) {
		this.yearsOfService = yearsOfService;
	}
	public Double getMinHourlyRate() {
		return minHourlyRate;
	}
	public void setMinHourlyRate(Double minHourlyRate) {
		this.minHourlyRate = minHourlyRate;
	}
	public Double getMaxHourlyRate() {
		return maxHourlyRate;
	}
	public void setMaxHourlyRate(Double maxHourlyRate) {
		this.maxHourlyRate = maxHourlyRate;
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
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWeb() {
		return web;
	}
	public void setWeb(String web) {
		this.web = web;
	}
	public String getAltEmail() {
		return altEmail;
	}
	public void setAltEmail(String altEmail) {
		this.altEmail = altEmail;
	}
	public String getBusinessPhone() {
		return businessPhone;
	}
	public void setBusinessPhone(String businessPhone) {
		this.businessPhone = businessPhone;
	}
	public String getBusinessPhoneExt() {
		return businessPhoneExt;
	}
	public void setBusinessPhoneExt(String businessPhoneExt) {
		this.businessPhoneExt = businessPhoneExt;
	}
	public String getBusinessFax() {
		return businessFax;
	}
	public void setBusinessFax(String businessFax) {
		this.businessFax = businessFax;
	}
	public String getCompanyLogoUrl() {
		return companyLogoUrl;
	}
	public void setCompanyLogoUrl(String companyLogoUrl) {
		this.companyLogoUrl = companyLogoUrl;
	}
	
	public Double getFirmAverageTimeToAccept() {
		return firmAverageTimeToAccept;
	}
	public void setFirmAverageTimeToAccept(Double firmAverageTimeToAccept) {
		this.firmAverageTimeToAccept = firmAverageTimeToAccept;
	}
	
	public Double getFirmAverageArrivalWindow() {
		return firmAverageArrivalWindow;
	}
	public void setFirmAverageArrivalWindow(Double firmAverageArrivalWindow) {
		this.firmAverageArrivalWindow = firmAverageArrivalWindow;
	}
	public String getPrimaryIndustryDesc() {
		return primaryIndustryDesc;
	}
	public void setPrimaryIndustryDesc(String primaryIndustryDesc) {
		this.primaryIndustryDesc = primaryIndustryDesc;
	}
	
	
}
