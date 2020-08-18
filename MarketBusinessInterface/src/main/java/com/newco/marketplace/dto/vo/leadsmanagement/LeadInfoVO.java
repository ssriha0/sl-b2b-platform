package com.newco.marketplace.dto.vo.leadsmanagement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LeadInfoVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer firmId;
	private String firmStatus;
	private String skill;
	private String projectType;
	private String urgencyOfService;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String slLeadId;
	private String leadSource;
	private String clientId;
	private String leadCategory;
	private String email;
	private String street1;
	private String street2;
	private String concatStreet;
	private String city;
	private String zip;
	private String state;
	private Double leadPrice;
	private String leadType;
	private String additionalProjects;
	private String description;
	private Date preferredAppointment;
	private String preferredAppointmentTime;
	private Date createdDate;
	private String createdOn;
	private String customerName;
	private String location;
	private String searchColumnName;
	private String preferredTime;
	private String membershipId;
	private Integer reward;
	private Integer resourceAssigned;
	private String resFirstName;
	private String resLastName;
	private Date completionDate;
	private String completionTime;
	private Integer numberOfTrips;
	private String completionComments;
	private Double leadFinalPrice;
	private Double leadMaterialPrice;
	private Double leadLaborPrice;
	private String dlSavingFlag;
	private String leadStatus;
	private String phoneNo;
	private String projectTitle;
	private String category;
	private String secondaryProject;
	private Integer totalLeadCount;
	private String serviceTimeZone;
	private String startTime;
	private String endTime;
	private Date cancelledDate;


	private String cancelledBy;
	private String cancelledReason;
	private String scheduledStartTime;
	private String scheduledEndTime;
	private Date scheduledDate;
	private String lmsLeadId;
   //SL-20893 Displaying firmName in searchResults
	private List<LeadInfoProviderDetails> firmDetails; 
	
	public String getLmsLeadId() {
		return lmsLeadId;
	}

	public void setLmsLeadId(String lmsLeadId) {
		this.lmsLeadId = lmsLeadId;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getPreferredAppointmentTime() {
		return preferredAppointmentTime;
	}

	public void setPreferredAppointmentTime(String preferredAppointmentTime) {
		this.preferredAppointmentTime = preferredAppointmentTime;
	}

	private List<BuyerLeadLookupVO> stateCodes = new ArrayList<BuyerLeadLookupVO>();

	public List<BuyerLeadLookupVO> getStateCodes() {
		return stateCodes;
	}

	public void setStateCodes(List<BuyerLeadLookupVO> stateCodes) {
		this.stateCodes = stateCodes;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getDlSavingFlag() {
		return dlSavingFlag;
	}

	public void setDlSavingFlag(String dlSavingFlag) {
		this.dlSavingFlag = dlSavingFlag;
	}

	public String getLeadStatus() {
		return leadStatus;
	}

	public void setLeadStatus(String leadStatus) {
		this.leadStatus = leadStatus;
	}

	public String getSecondaryProject() {
		return secondaryProject;
	}

	public void setSecondaryProject(String secondaryProject) {
		this.secondaryProject = secondaryProject;
	}

	public String getProjectTitle() {
		return projectTitle;
	}

	public void setProjectTitle(String projectTitle) {
		this.projectTitle = projectTitle;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPreferredTime() {
		return preferredTime;
	}

	public void setPreferredTime(String preferredTime) {
		this.preferredTime = preferredTime;
	}

	public String getMembershipId() {
		return membershipId;
	}

	public void setMembershipId(String membershipId) {
		this.membershipId = membershipId;
	}

	public Integer getReward() {
		return reward;
	}

	public void setReward(Integer reward) {
		this.reward = reward;
	}

	public String getLeadSource() {
		return leadSource;
	}

	public void setLeadSource(String leadSource) {
		this.leadSource = leadSource;
	}

	public String getLeadCategory() {
		return leadCategory;
	}

	public void setLeadCategory(String leadCategory) {
		this.leadCategory = leadCategory;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Double getLeadPrice() {
		return leadPrice;
	}

	public void setLeadPrice(Double leadPrice) {
		this.leadPrice = leadPrice;
	}

	public String getLeadType() {
		return leadType;
	}

	public void setLeadType(String leadType) {
		this.leadType = leadType;
	}

	public String getAdditionalProjects() {
		return additionalProjects;
	}

	public void setAdditionalProjects(String additionalProjects) {
		this.additionalProjects = additionalProjects;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getPreferredAppointment() {
		return preferredAppointment;
	}

	public void setPreferredAppointment(Date preferredAppointment) {
		this.preferredAppointment = preferredAppointment;
	}

	public Integer getFirmId() {
		return firmId;
	}

	public void setFirmId(Integer firmId) {
		this.firmId = firmId;
	}

	public String getSlLeadId() {
		return slLeadId;
	}

	public void setSlLeadId(String slLeadId) {
		this.slLeadId = slLeadId;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getUrgencyOfService() {
		return urgencyOfService;
	}

	public void setUrgencyOfService(String urgencyOfService) {
		this.urgencyOfService = urgencyOfService;
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getFirmStatus() {
		return firmStatus;
	}

	public void setFirmStatus(String firmStatus) {
		this.firmStatus = firmStatus;
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

	public String getConcatStreet() {
		return concatStreet;
	}

	public void setConcatStreet(String concatStreet) {
		this.concatStreet = concatStreet;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getSearchColumnName() {
		return searchColumnName;
	}

	public void setSearchColumnName(String searchColumnName) {
		this.searchColumnName = searchColumnName;
	}

	public Integer getTotalLeadCount() {
		return totalLeadCount;
	}

	public void setTotalLeadCount(Integer totalLeadCount) {
		this.totalLeadCount = totalLeadCount;
	}

	public String getServiceTimeZone() {
		return serviceTimeZone;
	}

	public void setServiceTimeZone(String serviceTimeZone) {
		this.serviceTimeZone = serviceTimeZone;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getResourceAssigned() {
		return resourceAssigned;
	}

	public void setResourceAssigned(Integer resourceAssigned) {
		this.resourceAssigned = resourceAssigned;
	}

	public String getResFirstName() {
		return resFirstName;
	}

	public void setResFirstName(String resFirstName) {
		this.resFirstName = resFirstName;
	}

	public String getResLastName() {
		return resLastName;
	}

	public void setResLastName(String resLastName) {
		this.resLastName = resLastName;
	}

	public Date getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(Date completionDate) {
		this.completionDate = completionDate;
	}

	public String getCompletionTime() {
		return completionTime;
	}

	public void setCompletionTime(String completionTime) {
		this.completionTime = completionTime;
	}

	public Integer getNumberOfTrips() {
		return numberOfTrips;
	}

	public void setNumberOfTrips(Integer numberOfTrips) {
		this.numberOfTrips = numberOfTrips;
	}

	public Date getCancelledDate() {
		return cancelledDate;
	}

	public void setCancelledDate(Date cancelledDate) {
		this.cancelledDate = cancelledDate;
	}

	public String getCancelledBy() {
		return cancelledBy;
	}

	public void setCancelledBy(String cancelledBy) {
		this.cancelledBy = cancelledBy;
	}

	public String getCancelledReason() {
		return cancelledReason;
	}

	public void setCancelledReason(String cancelledReason) {
		this.cancelledReason = cancelledReason;
	}

	public String getCompletionComments() {
		return completionComments;
	}

	public void setCompletionComments(String completionComments) {
		this.completionComments = completionComments;
	}

	public Double getLeadFinalPrice() {
		return leadFinalPrice;
	}

	public void setLeadFinalPrice(Double leadFinalPrice) {
		this.leadFinalPrice = leadFinalPrice;
	}

	public Double getLeadMaterialPrice() {
		return leadMaterialPrice;
	}

	public void setLeadMaterialPrice(Double leadMaterialPrice) {
		this.leadMaterialPrice = leadMaterialPrice;
	}

	public Double getLeadLaborPrice() {
		return leadLaborPrice;
	}

	public void setLeadLaborPrice(Double leadLaborPrice) {
		this.leadLaborPrice = leadLaborPrice;
	}

	public String getScheduledStartTime() {
		return scheduledStartTime;
	}

	public void setScheduledStartTime(String scheduledStartTime) {
		this.scheduledStartTime = scheduledStartTime;
	}

	public String getScheduledEndTime() {
		return scheduledEndTime;
	}

	public void setScheduledEndTime(String scheduledEndTime) {
		this.scheduledEndTime = scheduledEndTime;
	}

	public Date getScheduledDate() {
		return scheduledDate;
	}

	public void setScheduledDate(Date scheduledDate) {
		this.scheduledDate = scheduledDate;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public List<LeadInfoProviderDetails> getFirmDetails() {
		return firmDetails;
	}

	//Sl-20893
	public void setFirmDetails(List<LeadInfoProviderDetails> firmDetails) {
		this.firmDetails = firmDetails;
	}

	public String getFirmName() {
		String firmName="";
		if(null != this.getFirmDetails() && this.getFirmDetails().size()>0){
			for(LeadInfoProviderDetails firmDetail: this.getFirmDetails()){
				firmName= firmName+firmDetail.getFirm()+"</br>"+"(ID #"+firmDetail.getFirmId()+")";
				firmName=firmName+"</br>";
			}
		}
		return firmName;
	}

}
