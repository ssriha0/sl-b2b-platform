package com.newco.marketplace.api.beans.leadprofile.leadprofilerequest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;


@XmlRootElement(name = "leadProfileCreationRequest")
@XStreamAlias("leadProfileCreationRequest")
public class LeadProfileCreationRequest {
	@XStreamAlias("version")   
	@XStreamAsAttribute()   
	private String version = PublicAPIConstant.SORESPONSE_VERSION;
	 
	@XStreamAlias("xsi:schemaLocation")   
	@XStreamAsAttribute()   
	private String schemaLocation = PublicAPIConstant.ProviderAccount.NAMESPACE+"leadProfileCreationRequest.xsd";
	 
	@XStreamAlias("xmlns")   
	@XStreamAsAttribute()   
	private String namespace = PublicAPIConstant.ProviderAccount.NAMESPACE+"leadProfileCreationRequest";
	
	@XStreamAlias("xmlns:xsi")   
	@XStreamAsAttribute()   
	private String schemaInstance = PublicAPIConstant.SCHEMA_INSTANCE;
	
	//Business Information
	@XStreamAlias("providerFirmId")
	private String providerFirmId;
	
	@XStreamAlias("leadSmsNo")
	private String leadSmsNo;
	
	@XStreamAlias("leadEmailId")
	private String leadEmailId;
	

    @XStreamAlias("leadPhoneNo")
	private String leadPhoneNo;
    
	@XStreamAlias("leadDailyLimit")
	private String leadDailyLimit;
	
	@XStreamAlias("leadMonthlyLimit")
	private String leadMonthlyLimit;
	
	@XStreamAlias("availableDaysOfWeek")
	private String availableDaysOfWeek;
	
	@XStreamAlias("urgencyServices")
	private String urgencyOfService;

	@XStreamAlias("leadPackageID")
	private String leadPackageID;
	
	@XStreamAlias("interestedInSHSLeads")
	private Boolean interestedInSHSLeads;
	
	@XStreamAlias("locationType")
	private String locationType;
	
	@XStreamAlias("leadMonthlyBudget")
	private String leadMonthlyBudget;
	
	@XStreamAlias("coverageInMiles")
	private String coverageInMiles;
	
	@XStreamAlias("isLicensingRequired")
	private String isLicensingRequired;
	
	@XStreamAlias("licensingStates")
	private String licensingStates;
	
	@XStreamAlias("skill")
	private String skill;
	
	@XStreamAlias("isMultipleLocation")
	private String isMultipleLocation;
	
	@XStreamAlias("comments")
	private String comments;
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getSchemaLocation() {
		return schemaLocation;
	}
	public void setSchemaLocation(String schemaLocation) {
		this.schemaLocation = schemaLocation;
	}
	public String getNamespace() {
		return namespace;
	}
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	public String getSchemaInstance() {
		return schemaInstance;
	}
	public void setSchemaInstance(String schemaInstance) {
		this.schemaInstance = schemaInstance;
	}

	@XmlElement(required=true)
	public String getProviderFirmId() {
		return providerFirmId;
	}
	public void setProviderFirmId(String providerFirmId) {
		this.providerFirmId = providerFirmId;
	}
	@XmlElement(required=true)
	public String getLeadSmsNo() {
		return leadSmsNo;
	}
	public void setLeadSmsNo(String leadSmsNo) {
		this.leadSmsNo = leadSmsNo;
	}
	@XmlElement(required=true)
	public String getLeadEmailId() {
		return leadEmailId;
	}
	public void setLeadEmailId(String leadEmailId) {
		this.leadEmailId = leadEmailId;
	}
	@XmlElement(required=true)
	public String getLeadPhoneNo() {
		return leadPhoneNo;
	}
	public void setLeadPhoneNo(String leadPhoneNo) {
		this.leadPhoneNo = leadPhoneNo;
	}
	@XmlElement(required=true)
	public String getLeadDailyLimit() {
		return leadDailyLimit;
	}
	public void setLeadDailyLimit(String leadDailyLimit) {
		this.leadDailyLimit = leadDailyLimit;
	}
	@XmlElement(required=true)
	public String getLeadMonthlyLimit() {
		return leadMonthlyLimit;
	}
	public void setLeadMonthlyLimit(String leadMonthlyLimit) {
		this.leadMonthlyLimit = leadMonthlyLimit;
	}
	
	
	
	@XmlElement(required=true)
	public String getAvailableDaysOfWeek() {
		return availableDaysOfWeek;
	}
	@XmlElement(required=true)
	public String getUrgencyOfService() {
		return urgencyOfService;
	}
	public void setUrgencyOfService(String urgencyOfService) {
		this.urgencyOfService = urgencyOfService;
	}
	public void setAvailableDaysOfWeek(String availableDaysOfWeek) {
		this.availableDaysOfWeek = availableDaysOfWeek;
	}	
	@XmlElement(required=true)
	public String getLocationType() {
		return locationType;
	}
	
	
	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}
		
	@XmlElement(required=true)
	public String getLeadPackageID() {
		return leadPackageID;
	}
	public void setLeadPackageID(String leadPackageID) {
		this.leadPackageID = leadPackageID;
	}

	public Boolean isInterestedInSHSLeads() {
		return interestedInSHSLeads;
	}
	public void setInterestedInSHSLeads(Boolean interestedInSHSLeads) {
		if(interestedInSHSLeads==null)
		{
			this.interestedInSHSLeads=false;	
		}
		else
		{
			this.interestedInSHSLeads = interestedInSHSLeads;
		}
	}
	@XmlElement(required=true)
	public String getLeadMonthlyBudget() {
		return leadMonthlyBudget;
	}
	public void setLeadMonthlyBudget(String leadMonthlyBudget) {
		this.leadMonthlyBudget = leadMonthlyBudget;
	}
	@XmlElement(required=true)
	public String getCoverageInMiles() {
		return coverageInMiles;
	}
	public void setCoverageInMiles(String coverageInMiles) {
		this.coverageInMiles = coverageInMiles;
	}
	@XmlElement(required=true)
	public String getIsLicensingRequired() {
		return isLicensingRequired;
	}
	public void setIsLicensingRequired(String isLicensingRequired) {
		this.isLicensingRequired = isLicensingRequired;
	}
	@XmlElement(required=true)
	public String getLicensingStates() {
		return licensingStates;
	}
	public void setLicensingStates(String licensingStates) {
		this.licensingStates = licensingStates;
	}
	@XmlElement(required=true)
	public String getSkill() {
		return skill;
	}
	public void setSkill(String skill) {
		this.skill = skill;
	}
	
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public String getIsMultipleLocation() {
		return isMultipleLocation;
	}
	public void setIsMultipleLocation(String isMultipleLocation) {
		this.isMultipleLocation = isMultipleLocation;
	}
	@Override
    public String toString() {
        return "LeadProfileCreationRequest [providerFirmId=" + providerFirmId + ",leadSmsNo=" + leadSmsNo + ",leadEmailId=" + leadEmailId + ",leadPhoneNo="
        		+ leadPhoneNo + ",leadDailyLimit=" + leadDailyLimit + ",leadMonthlyLimit=" + leadMonthlyLimit + ",availableDaysOfWeek=" + availableDaysOfWeek
           		+ ",interestedInSHSLeads=" + interestedInSHSLeads + ",leadPackageID="+leadPackageID+",locationType=" + locationType+"," +
           				"leadMonthlyBudget=" + leadMonthlyBudget+",urgencyOfService="+urgencyOfService+",skill="+skill+",isMultipleLocation="+isMultipleLocation+",isLicensingRequired="+
           		isLicensingRequired+",licensingStates="+licensingStates+",coverageInMiles="+coverageInMiles+",comments="+comments+"]";
	}
	
}
