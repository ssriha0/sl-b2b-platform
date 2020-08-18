package com.newco.marketplace.api.beans.firmDetail;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("firmDetails")
@XmlAccessorType(XmlAccessType.FIELD)
public class FirmDetails {
	
	@XStreamAlias("firmId")
	private Integer firmId;
	
	@XStreamAlias("businessName")
	private String businessName;
	
	//SL-21446-Adding company logo 
	@XStreamAlias("companyLogoUrl")
	private String companyLogoUrl;
	
	@XStreamAlias("overView")
	private String overView;
	
	@XStreamAlias("firmOwner")
	private String firmOwner;
	
	@XStreamAlias("firmAggregateRating")
	private Double firmAggregateRating;
	
	@XStreamAlias("firmAverageTimeToAccept")
	private String firmAverageTimeToAccept;
	
	@XStreamAlias("firmAverageArrivalWindow")
	private String firmAverageArrivalWindow;
	
	@XStreamAlias("reviewCount")
	private Integer reviewCount;
	
	@XStreamAlias("numberOfEmployees")
	private Integer numberOfEmployees;
	
	@XStreamAlias("yearsOfService")
	private Double yearsOfService;
	
	@XStreamAlias("hourlyRate")
	private String hourlyRate;
	
	@XStreamAlias("location")
	private FirmLocation location;
	
	@XStreamAlias("lastCompletedProject")
	private LastCompletedProject lastCompletedProject;
	
	@XStreamAlias("contact")
	private FirmContact contact;
	
	@XStreamAlias("statistics")
	private FirmStatistics statistics;
	
	@XStreamAlias("services")
	private FirmServices services;
	
	@XStreamAlias("warrantyList")
	private WarrantyList warrantyList;

	@XStreamAlias("polAndProcList")
	private PolAndProcList polAndProcList;
	
	@XStreamAlias("reviews")
	private Reviews reviews;
	
	@XStreamAlias("insurances")
	private Insurances insurances;
	
	@XStreamAlias("credentials")
	private Credentials credentials;	
	

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
	
	public String getCompanyLogoUrl() {
		return companyLogoUrl;
	}

	public void setCompanyLogoUrl(String companyLogoUrl) {
		this.companyLogoUrl = companyLogoUrl;
	}

	public String getOverView() {
		return overView;
	}

	public void setOverView(String overView) {
		this.overView = overView;
	}

	public String getFirmOwner() {
		return firmOwner;
	}

	public void setFirmOwner(String firmOwner) {
		this.firmOwner = firmOwner;
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

	public String getHourlyRate() {
		return hourlyRate;
	}

	public void setHourlyRate(String hourlyRate) {
		this.hourlyRate = hourlyRate;
	}

	public FirmLocation getLocation() {
		return location;
	}

	public void setLocation(FirmLocation location) {
		this.location = location;
	}

	public LastCompletedProject getLastCompletedProject() {
		return lastCompletedProject;
	}

	public void setLastCompletedProject(LastCompletedProject lastCompletedProject) {
		this.lastCompletedProject = lastCompletedProject;
	}

	public FirmContact getContact() {
		return contact;
	}

	public void setContact(FirmContact contact) {
		this.contact = contact;
	}

	public FirmStatistics getStatistics() {
		return statistics;
	}

	public void setStatistics(FirmStatistics statistics) {
		this.statistics = statistics;
	}

	public FirmServices getServices() {
		return services;
	}

	public void setServices(FirmServices services) {
		this.services = services;
	}


	public WarrantyList getWarrantyList() {
		return warrantyList;
	}

	public void setWarrantyList(WarrantyList warrantyList) {
		this.warrantyList = warrantyList;
	}

	public PolAndProcList getPolAndProcList() {
		return polAndProcList;
	}

	public void setPolAndProcList(PolAndProcList polAndProcList) {
		this.polAndProcList = polAndProcList;
	}

	public Reviews getReviews() {
		return reviews;
	}

	public void setReviews(Reviews reviews) {
		this.reviews = reviews;
	}

	public Insurances getInsurances() {
		return insurances;
	}

	public void setInsurances(Insurances insurances) {
		this.insurances = insurances;
	}

	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	public String getFirmAverageTimeToAccept() {
		return firmAverageTimeToAccept;
	}
	public void setFirmAverageTimeToAccept(String firmAverageTimeToAccept) {
		this.firmAverageTimeToAccept = firmAverageTimeToAccept;
	}
	
	public String getFirmAverageArrivalWindow() {
		return firmAverageArrivalWindow;
	}
	public void setFirmAverageArrivalWindow(String firmAverageArrivalWindow) {
		this.firmAverageArrivalWindow = firmAverageArrivalWindow;
	}
	
}
