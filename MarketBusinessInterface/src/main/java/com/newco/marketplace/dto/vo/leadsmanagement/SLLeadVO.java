package com.newco.marketplace.dto.vo.leadsmanagement;

import java.sql.Timestamp;
import java.util.Date;
//Vo class which holds sl_lead_hdr values.
public class SLLeadVO {
	private String slLeadId;
	private String lmsLeadId;
    private String customerZipCode;
	private String skill;
	private Date serviceDate;
    private String serviceTimeZone;
    private String serviceStartTime;
    private String serviceEndTime;
    private String urgencyOfService;
    private String projectDescription;
    private String leadType;
    private String leadSource;
    private String leadWfStatus;
	private String primaryProject;
    private String secondaryProjects;
    private Double leadFinalPrice;
    private Date createdDate;
	private Date modifiedDate;
   	private String createdBy;
   	private String modifiedBy;
   	private String clientId;
   	private int buyerId;
   	private String leadCategory;
   	//Added for fetching eligible providers
   	private int spnId;
   	private int slNodeId;
   	private int leadWFStateInt;
   	private boolean nonLaunchZip=false;
	private boolean noProvLead=false;
	
	
    private Integer rewardPoints;
    //Added for Email
    private String clientProjectType;
    
    //Added for SL-19727
    private String serviceCategoryDesc;
    private String clientProjectDesc;
    private String swyrRewardPoints;
   
   	public SLLeadVO() {

	}
	public SLLeadVO(String slLeadId, String customerZipCode, String skill,
			String urgencyOfService, String leadType, String leadSource, String leadWfStatus,
			String primaryProject, Date createdDate,
			Date modifiedDate, String createdBy, String modifiedBy,
			String clientId,int buyerId) {
		this.slLeadId = slLeadId;
		this.customerZipCode = customerZipCode;
		this.skill = skill;
		this.urgencyOfService = urgencyOfService;
		this.leadType = leadType;
		this.leadSource = leadSource;
		this.leadWfStatus = leadWfStatus;
		this.primaryProject = primaryProject;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.clientId = clientId;
		this.buyerId = buyerId;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public int getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(int buyerId) {
		this.buyerId = buyerId;
	}
	public String getSlLeadId() {
		return slLeadId;
	}
	public void setSlLeadId(String slLeadId) {
		this.slLeadId = slLeadId;
	}
	public String getLmsLeadId() {
		return lmsLeadId;
	}
	public void setLmsLeadId(String lmsLeadId) {
		this.lmsLeadId = lmsLeadId;
	}
	public String getCustomerZipCode() {
		return customerZipCode;
	}
	public void setCustomerZipCode(String customerZipCode) {
		this.customerZipCode = customerZipCode;
	}
	public String getSkill() {
		return skill;
	}
	public void setSkill(String skill) {
		this.skill = skill;
	}
	public Date getServiceDate() {
		return serviceDate;
	}
	public void setServiceDate(Date serviceDate) {
		this.serviceDate = serviceDate;
	}
	public String getServiceTimeZone() {
		return serviceTimeZone;
	}
	public void setServiceTimeZone(String serviceTimeZone) {
		this.serviceTimeZone = serviceTimeZone;
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
	public String getUrgencyOfService() {
		return urgencyOfService;
	}
	public void setUrgencyOfService(String urgencyOfService) {
		this.urgencyOfService = urgencyOfService;
	}
	public String getProjectDescription() {
		return projectDescription;
	}
	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}
	public String getLeadType() {
		return leadType;
	}
	public void setLeadType(String leadType) {
		this.leadType = leadType;
	}
	public String getLeadCategory() {
		return leadCategory;
	}
	public void setLeadCategory(String leadCategory) {
		this.leadCategory = leadCategory;
	}
	public String getLeadSource() {
		return leadSource;
	}
	public void setLeadSource(String leadSource) {
		this.leadSource = leadSource;
	}
	public String getLeadWfStatus() {
		return leadWfStatus;
	}
	public void setLeadWfStatus(String leadWfStatus) {
		this.leadWfStatus = leadWfStatus;
	}
	public String getPrimaryProject() {
		return primaryProject;
	}
	public void setPrimaryProject(String primaryProject) {
		this.primaryProject = primaryProject;
	}
	public String getSecondaryProjects() {
		return secondaryProjects;
	}
	public void setSecondaryProjects(String secondaryProjects) {
		this.secondaryProjects = secondaryProjects;
	}
	public Double getLeadFinalPrice() {
		return leadFinalPrice;
	}
	public void setLeadFinalPrice(Double leadFinalPrice) {
		this.leadFinalPrice = leadFinalPrice;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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
	public int getSpnId() {
		return spnId;
	}
	public void setSpnId(int spnId) {
		this.spnId = spnId;
	}
	public int getSlNodeId() {
		return slNodeId;
	}
	public void setSlNodeId(int slNodeId) {
		this.slNodeId = slNodeId;
	}
	public int getLeadWFStateInt() {
		return leadWFStateInt;
	}
	public void setLeadWFStateInt(int leadWFStateInt) {
		this.leadWFStateInt = leadWFStateInt;
	}
	public Integer getRewardPoints() {
		return rewardPoints;
	}
	public void setRewardPoints(Integer rewardPoints) {
		this.rewardPoints = rewardPoints;
	}
	public String getClientProjectType() {
		return clientProjectType;
	}
	public void setClientProjectType(String clientProjectType) {
		this.clientProjectType = clientProjectType;
	}
	public String getServiceCategoryDesc() {
		return serviceCategoryDesc;
	}
	public void setServiceCategoryDesc(String serviceCategoryDesc) {
		this.serviceCategoryDesc = serviceCategoryDesc;
	}
	public String getClientProjectDesc() {
		return clientProjectDesc;
	}
	public void setClientProjectDesc(String clientProjectDesc) {
		this.clientProjectDesc = clientProjectDesc;
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
	public String getSwyrRewardPoints() {
		return swyrRewardPoints;
	}
	public void setSwyrRewardPoints(String swyrRewardPoints) {
		this.swyrRewardPoints = swyrRewardPoints;
	}
	
		
}
