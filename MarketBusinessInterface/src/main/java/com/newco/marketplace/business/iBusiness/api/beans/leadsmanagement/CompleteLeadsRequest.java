package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;


import java.util.Date;

import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamOmitField;


@XStreamAlias("CompleteLeadsRequest")
public class CompleteLeadsRequest {

	@XStreamAlias("xsi:schemaLocation")   
	@XStreamAsAttribute()   
	private String schemaLocation = PublicAPIConstant.COMPLETE_LEAD_REQUEST_SCHEMALOCATION;
	 
	@XStreamAlias("xmlns")   
	@XStreamAsAttribute()   
	private String namespace = PublicAPIConstant.NEW_SERVICES_NAMESPACE;
	 
	@XStreamAlias("xmlns:xsi")   
	@XStreamAsAttribute()   
	private String schemaInstance = PublicAPIConstant.SCHEMA_INSTANCE;
	
	@XStreamAlias("LeadId")
	private String leadId;

	@XStreamAlias("FirmId")
	private String firmId;

	@XStreamAlias("ResourceId")
	private String resourceId;

	@XStreamAlias("CompletedDate")
	private String completedDate;

	@XStreamAlias("CompletedTime")
	private String completedTime;

	@XStreamAlias("CompletionComment")
	private String completionComment;

	@XStreamAlias("Price")
	private Price price;
	
	@XStreamAlias("Document")
	private Document document;
	
	@XStreamAlias("NumberOfVisits")
	private Integer numberOfVisits;
	
	@XStreamOmitField
	private Date completeDateAsDate;

	// Result of validation
	private ResultsCode validationCode;

	public ResultsCode getValidationCode() {
		return validationCode;
	}

	public void setValidationCode(ResultsCode validationCode) {
		this.validationCode = validationCode;
	}

	public String getLeadId() {
		return leadId;
	}

	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}

	public String getFirmId() {
		return firmId;
	}

	public void setFirmId(String firmId) {
		this.firmId = firmId;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getCompletedDate() {
		return completedDate;
	}

	public void setCompletedDate(String completedDate) {
		this.completedDate = completedDate;
	}

	public String getCompletedTime() {
		return completedTime;
	}

	public void setCompletedTime(String completedTime) {
		this.completedTime = completedTime;
	}

	public String getCompletionComment() {
		return completionComment;
	}

	public void setCompletionComment(String completionComment) {
		this.completionComment = completionComment;
	}

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}
	
	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public Integer getNumberOfVisits() {
		return numberOfVisits;
	}

	public void setNumberOfVisits(Integer numberOfVisits) {
		this.numberOfVisits = numberOfVisits;
	}
	public Date getCompleteDateAsDate() {
		return completeDateAsDate;
	}

	public void setCompleteDateAsDate(Date completeDateAsDate) {
		this.completeDateAsDate = completeDateAsDate;
	}


}
