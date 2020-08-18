package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.dto.vo.LocationVO;

@XSD(name="matchProviderFirmsRequest.xsd", path="/resources/schemas/b2c/")
@XmlRootElement(name="MatchProviderRequest")
@XStreamAlias("MatchProviderRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class FetchProviderFirmRequest {

	@XStreamAlias("CustomerZipCode")
	@XmlElement(name="CustomerZipCode")
	private String customerZipCode;

	@XStreamAlias("PrimaryProject")
	@XmlElement(name="PrimaryProject")
	private String primaryProject;
	
	@XStreamAlias("Skill")
	@XmlElement(name="Skill")
	private String skill;
	
	@XStreamAlias("UrgencyOfService")
	@XmlElement(name="UrgencyOfService")
	private String urgencyOfService;
	
	@XStreamAlias("NumberOfMatches")
	@XmlElement(name="NumberOfMatches")
	private Integer noOfMatches;
	
	@XStreamAlias("ClientId")
	@XmlElement(name="ClientId")
	private String clientId;
	
	@XStreamAlias("LeadSource")
	@XmlElement(name="LeadSource")
	private String leadSource;
	
	//location vo
	@XStreamOmitField
	private LocationVO locationVO;
	
	//client project id
	@XStreamOmitField
	private Integer lmsProjectTypeId;
	
	//client project description
	@XStreamOmitField
	private String lmsProjectDescription;	
	
	//lead Category
	@XStreamOmitField
	private String leadCategory;
	
	//servicelive project id
	@XStreamOmitField
	private Integer slNodeId;
	
	//Firm id list
	@XStreamOmitField
	private List<Integer>firmIds;
	
	//buyer id
	@XStreamOmitField
	private Long buyerId;
	
	//Result of validation
	@XStreamOmitField
	private ResultsCode validationCode;	
	
	//holding current firm id list
	@XStreamOmitField
	private List<Integer> firmIdList;
	
	//SL lead id
	@XStreamOmitField
	private String slLeadId;
	
	@XStreamOmitField
	private Boolean leadNonLaunchZipSwitch;
	
	//Default distance
	@XStreamOmitField
	private Integer distance;
	
	public String getSlLeadId() {
		return slLeadId;
	}

	public void setSlLeadId(String slLeadId) {
		this.slLeadId = slLeadId;
	}

	public Long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}

	public List<Integer> getFirmIds() {
		return firmIds;
	}

	public void setFirmIds(List<Integer> firmIds) {
		this.firmIds = firmIds;
	}

	public String getLeadSource() {
		return leadSource;
	}

	public void setLeadSource(String leadSource) {
		this.leadSource = leadSource;
	}

	public Integer getNoOfMatches() {
		return noOfMatches;
	}

	public void setNoOfMatches(Integer noOfMatches) {
		this.noOfMatches = noOfMatches;
	}	

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public String getUrgencyOfService() {
		return urgencyOfService;
	}

	public void setUrgencyOfService(String urgencyOfService) {
		this.urgencyOfService = urgencyOfService;
	}
    
	public String getCustomerZipCode() {
		return customerZipCode;
	}

	public void setCustomerZipCode(String customerZipCode) {
		this.customerZipCode = customerZipCode;
	}

	public String getPrimaryProject() {
		return primaryProject;
	}

	public void setPrimaryProject(String primaryProject) {
		this.primaryProject = primaryProject;
	}

	public LocationVO getLocationVO() {
		return locationVO;
	}

	public void setLocationVO(LocationVO locationVO) {
		this.locationVO = locationVO;
	}

	public Integer getLmsProjectTypeId() {
		return lmsProjectTypeId;
	}

	public void setLmsProjectTypeId(Integer lmsProjectTypeId) {
		this.lmsProjectTypeId = lmsProjectTypeId;
	}

	public Integer getSlNodeId() {
		return slNodeId;
	}

	public void setSlNodeId(Integer slNodeId) {
		this.slNodeId = slNodeId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getLmsProjectDescription() {
		return lmsProjectDescription;
	}

	public void setLmsProjectDescription(String lmsProjectDescription) {
		this.lmsProjectDescription = lmsProjectDescription;
	}

	public ResultsCode getValidationCode() {
		return validationCode;
	}

	public void setValidationCode(ResultsCode validationCode) {
		this.validationCode = validationCode;
	}
	public String getLeadCategory() {
		return leadCategory;
	}

	public void setLeadCategory(String leadCategory) {
		this.leadCategory = leadCategory;
	}

	public List<Integer> getFirmIdList() {
		return firmIdList;
	}

	public void setFirmIdList(List<Integer> firmIdList) {
		this.firmIdList = firmIdList;
	}

	public Boolean getLeadNonLaunchZipSwitch() {
		return leadNonLaunchZipSwitch;
	}

	public void setLeadNonLaunchZipSwitch(Boolean leadNonLaunchZipSwitch) {
		this.leadNonLaunchZipSwitch = leadNonLaunchZipSwitch;
	}

	public Integer getDistance() {
		return distance;
	}

	public void setDistance(Integer distance) {
		this.distance = distance;
	}
}
