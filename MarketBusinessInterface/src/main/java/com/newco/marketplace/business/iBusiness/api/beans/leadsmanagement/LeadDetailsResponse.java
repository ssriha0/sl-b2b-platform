package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XSD(name="leadFirmDetailResponse.xsd", path="/resources/schemas/newservices/")
@XmlRootElement(name = "leadFirmDetailResponse")
@XStreamAlias("leadFirmDetailResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class LeadDetailsResponse  implements IAPIResponse {
	
	@XStreamAlias("results")
	private Results results;
	
	@XStreamAlias("leadId")
	private String leadId;
	
	@XStreamAlias("buyerId")
	private Integer buyerId;
	
	@XStreamAlias("jobType")
	private String jobType;
	
	@XStreamAlias("serviceType")
	private String serviceType;
	
	@XStreamAlias("serviceCategory")
	private String serviceCategory;
	
	@XStreamAlias("leadStatus")
	private String leadStatus;
	
	@XStreamAlias("urgency")
	private String urgency;
	
	@XStreamAlias("projectDescription")
	private String projectDescription;
	
	@XStreamAlias("secondaryProjects")
	private String secondaryProjects;
	
	@XStreamAlias("customerDetails")
	private CustomerDetails customerDetails;
	
	@XStreamAlias("servicePreferredDate")
	private String servicePreferredDate;

    @XStreamAlias("servicePreferredStartTime")
	private String servicePreferredStartTime;

	@XStreamAlias("servicePreferredEndTime")
	private String servicePreferredEndTime;
	
	@XStreamAlias("serviceTimeZone")
	private String timeZone;
	
	/*
	@XStreamAlias("firmDetailList")
	private List<LeadFirmDetails> firmDetailList;
	*/
	@XStreamAlias("firmDetailList")
	private LeadFirmDetailsList firmDetailList;
	
	public Results getResults() {
		return results;
	}

	public void setResults(Results results) {
		this.results = results;
	}

	
	public Integer getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getServiceCategory() {
		return serviceCategory;
	}

	public void setServiceCategory(String serviceCategory) {
		this.serviceCategory = serviceCategory;
	}

	public String getLeadStatus() {
		return leadStatus;
	}

	public void setLeadStatus(String leadStatus) {
		this.leadStatus = leadStatus;
	}

	public String getUrgency() {
		return urgency;
	}

	public void setUrgency(String urgency) {
		this.urgency = urgency;
	}
	
	/*
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	*/
	

	public CustomerDetails getCustomerDetails() {
		return customerDetails;
	}

	public String getProjectDescription() {
		return projectDescription;
	}

	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}

	public void setCustomerDetails(CustomerDetails customerDetails) {
		this.customerDetails = customerDetails;
	}

	public String getServicePreferredDate() {
		return servicePreferredDate;
	}

	public void setServicePreferredDate(String servicePreferredDate) {
		this.servicePreferredDate = servicePreferredDate;
	}

	public String getServicePreferredStartTime() {
		return servicePreferredStartTime;
	}

	public void setServicePreferredStartTime(String servicePreferredStartTime) {
		this.servicePreferredStartTime = servicePreferredStartTime;
	}

	public String getServicePreferredEndTime() {
		return servicePreferredEndTime;
	}

	public void setServicePreferredEndTime(String servicePreferredEndTime) {
		this.servicePreferredEndTime = servicePreferredEndTime;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
    /*
	public LeadFirmDetails getFirmDetailList() {
		return firmDetailList;
	}

	public void setFirmDetailList(LeadFirmDetails firmDetailList) {
		this.firmDetailList = firmDetailList;
	}
	*/
	
	public void setVersion(String version){}

	/*
	public List<LeadFirmDetails> getFirmDetailList() {
		return firmDetailList;
	}

	public void setFirmDetailList(List<LeadFirmDetails> firmDetailList) {
		this.firmDetailList = firmDetailList;
	}
	 */
	
	
	public void setSchemaLocation(String schemaLocation) {}

	public LeadFirmDetailsList getFirmDetailList() {
		return firmDetailList;
	}

	public void setFirmDetailList(LeadFirmDetailsList firmDetailList) {
		this.firmDetailList = firmDetailList;
	}

	public void setNamespace(String namespace) {}

	public void setSchemaInstance(String schemaInstance) {}

	public String getLeadId() {
		return leadId;
	}

	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}

	public String getSecondaryProjects() {
		return secondaryProjects;
	}

	public void setSecondaryProjects(String secondaryProject) {
		this.secondaryProjects = secondaryProject;
	}
	
}
