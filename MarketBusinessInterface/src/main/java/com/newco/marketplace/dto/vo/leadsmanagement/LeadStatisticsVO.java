package com.newco.marketplace.dto.vo.leadsmanagement;

import java.util.Date;

public class LeadStatisticsVO {
	private String slLeadId;
	private Date requestDate;
	private Date responseDate;
	private String dataFlowDirection;
	private String typeOfInteraction;
	private String reqResXML;
	private Date createdDate;
	private Date modifiedDate;
	private String createdBy;
	private String modifiedBy;
	
	
	public LeadStatisticsVO(String slLeadId, Date requestDate,
			Date responseDate, String dataFlowDirection,
			String typeOfInteraction, Date createdDate, Date modifiedDate,
			String createdBy, String modifiedBy) {
		this.slLeadId = slLeadId;
		this.requestDate = requestDate;
		this.responseDate = responseDate;
		this.dataFlowDirection = dataFlowDirection;
		this.typeOfInteraction = typeOfInteraction;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
	}

	public LeadStatisticsVO(String slLeadId, Date requestDate,
			Date responseDate, String dataFlowDirection,
			String typeOfInteraction, Date createdDate, Date modifiedDate,
			String createdBy, String modifiedBy, String reqResXML) {
		this.slLeadId = slLeadId;
		this.requestDate = requestDate;
		this.responseDate = responseDate;
		this.dataFlowDirection = dataFlowDirection;
		this.typeOfInteraction = typeOfInteraction;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.reqResXML = reqResXML;
	}

	public LeadStatisticsVO() {

	}

	public String getSlLeadId() {
		return slLeadId;
	}

	public void setSlLeadId(String slLeadId) {
		this.slLeadId = slLeadId;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public Date getResponseDate() {
		return responseDate;
	}

	public void setResponseDate(Date responseDate) {
		this.responseDate = responseDate;
	}

	public String getDataFlowDirection() {
		return dataFlowDirection;
	}

	public void setDataFlowDirection(String dataFlowDirection) {
		this.dataFlowDirection = dataFlowDirection;
	}

	public String getTypeOfInteraction() {
		return typeOfInteraction;
	}

	public void setTypeOfInteraction(String typeOfInteraction) {
		this.typeOfInteraction = typeOfInteraction;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
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

	public String getReqResXML() {
		return reqResXML;
	}

	public void setReqResXML(String reqResXML) {
		this.reqResXML = reqResXML;
	}

}