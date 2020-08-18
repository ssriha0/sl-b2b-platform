package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.common.ResultsCode;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.FirmIdPrice;

@XSD(name="leadRequest.xsd", path="/resources/schemas/b2c/")
@XmlRootElement(name="LeadRequest")
@XStreamAlias("LeadRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class LeadRequest {

	@XStreamAlias("LeadId")
	@XmlElement(name="LeadId")
	private String leadId;

	@XStreamAlias("FirmIds")
	@XmlElement(name="FirmIds")
	private FirmIds firmIds;

	@XStreamAlias("ProjectDescription")
	@XmlElement(name="ProjectDescription")
	private String ProjectDescription;

	@XStreamAlias("SecondaryProjects")
	@XmlElement(name="SecondaryProjects")
	private String secondaryProjects;

	@XStreamAlias("ServicePreferredDate")
	@XmlElement(name="ServicePreferredDate")
	private String serviceDate;

	@XStreamAlias("ServiceTimeZone")
	@XmlElement(name="ServiceTimeZone")
	private String serviceTimeZone;

	@XStreamAlias("ServicePreferredStartTime")
	@XmlElement(name="ServicePreferredStartTime")
	private String serviceStartTime;

	@XStreamAlias("ServicePreferredEndTime")
	@XmlElement(name="ServicePreferredEndTime")
	private String serviceEndTime;

	@XStreamAlias("CustomerContact")
	@XmlElement(name="CustomerContact")
	private CustomerContact custContact;

	@XStreamAlias("MemberShipNumber")
	@XmlElement(name="MemberShipNumber")
	private String SYWRMemberId;
	
	//lead pricing type
	private String leadPricingType;
	
	//LMS Firm IDs and price map
	private Map<String,Double> LMSFirmIdPriceMap;
	
	//Result of validation
	private ResultsCode validationCode;	
	
	private String LMSLeadId;
	
	//FirmIdPrice
	private List<FirmIdPrice> firmIdPriceList;
	
	private String urgencyOfService;
	
	private String leadSource;
	
	private String projectType;
	
    private String skill;
	
	private String leadCategory;
	
	private String clientProjectType;
	
	@XStreamOmitField
	private boolean invalidFirmPriceData;
	
	@XStreamOmitField
	private boolean nonLaunchZip;
	
	@XStreamOmitField
	private boolean noProvLead;
	
    public String getUrgencyOfService() {
		return urgencyOfService;
	}

	public void setUrgencyOfService(String urgencyOfService) {
		this.urgencyOfService = urgencyOfService;
	}

	public String getLeadSource() {
		return leadSource;
	}

	public void setLeadSource(String leadSource) {
		this.leadSource = leadSource;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}


	public ResultsCode getValidationCode() {
		return validationCode;
	}

	public void setValidationCode(ResultsCode validationCode) {
		this.validationCode = validationCode;
	}

	public String getLeadPricingType() {
		return leadPricingType;
	}

	public void setLeadPricingType(String leadPricingType) {
		this.leadPricingType = leadPricingType;
	}

	public String getServiceTimeZone() {
		return serviceTimeZone;
	}

	public void setServiceTimeZone(String serviceTimeZone) {
		this.serviceTimeZone = serviceTimeZone;
	}

	public String getServiceDate() {
		return serviceDate;
	}

	public void setServiceDate(String serviceDate) {
		this.serviceDate = serviceDate;
	}

	public String getServiceStartTime() {
		return serviceStartTime;
	}

	public void setServiceStartTime(String serviceStartTime) {
		this.serviceStartTime = serviceStartTime;
	}

	public String getServiceEndTime() {
		return serviceEndTime;
	}

	public void setServiceEndTime(String serviceEndTime) {
		this.serviceEndTime = serviceEndTime;
	}

	public String getLeadId() {
		return leadId;
	}

	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}

	public FirmIds getFirmIds() {
		return firmIds;
	}

	public void setFirmIds(FirmIds firmIds) {
		this.firmIds = firmIds;
	}

	public CustomerContact getCustContact() {
		return custContact;
	}

	public void setCustContact(CustomerContact custContact) {
		this.custContact = custContact;
	}

	public String getProjectDescription() {
		return ProjectDescription;
	}

	public void setProjectDescription(String projectDescription) {
		ProjectDescription = projectDescription;
	}

	public String getSYWRMemberId() {
		return SYWRMemberId;
	}

	public void setSYWRMemberId(String sYWRMemberId) {
		SYWRMemberId = sYWRMemberId;
	}

	public String getSecondaryProjects() {
		return secondaryProjects;
	}

	public void setSecondaryProjects(String secondaryProjects) {
		this.secondaryProjects = secondaryProjects;
	}

	public Map<String, Double> getLMSFirmIdPriceMap() {
		return LMSFirmIdPriceMap;
	}

	public void setLMSFirmIdPriceMap(Map<String, Double> firmIdPriceMap) {
		LMSFirmIdPriceMap = firmIdPriceMap;
	}

	public String getLMSLeadId() {
		return LMSLeadId;
	}

	public void setLMSLeadId(String leadId) {
		LMSLeadId = leadId;
	}

	public List<FirmIdPrice> getFirmIdPriceList() {
		return firmIdPriceList;
	}

	public void setFirmIdPriceList(List<FirmIdPrice> firmIdPriceList) {
		this.firmIdPriceList = firmIdPriceList;
	}

	public boolean isInvalidFirmPriceData() {
		return invalidFirmPriceData;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public String getLeadCategory() {
		return leadCategory;
	}

	public void setLeadCategory(String leadCategory) {
		this.leadCategory = leadCategory;
	}

	public void setInvalidFirmPriceData(boolean invalidFirmPriceData) {
		this.invalidFirmPriceData = invalidFirmPriceData;
	}

	public String getClientProjectType() {
		return clientProjectType;
	}

	public void setClientProjectType(String clientProjectType) {
		this.clientProjectType = clientProjectType;
	}

	public boolean isNonLaunchZip() {
		return nonLaunchZip;
	}

	public void setNonLaunchZip(boolean nonLaunchZip) {
		this.nonLaunchZip = nonLaunchZip;
	}

	public boolean isNoProvLead() {
		return noProvLead;
	}

	public void setNoProvLead(boolean noProvLead) {
		this.noProvLead = noProvLead;
	}
	
	
	

}
