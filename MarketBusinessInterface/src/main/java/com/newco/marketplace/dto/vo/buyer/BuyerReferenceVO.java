package com.newco.marketplace.dto.vo.buyer;

import java.sql.Date;

import com.newco.marketplace.webservices.base.CommonVO;

public class BuyerReferenceVO extends CommonVO {

	private static final long serialVersionUID = 6669477163271440647L;
	
	private Integer buyerId;
	private Integer buyerRefTypeId;
	private String referenceType;
	private String referenceDescription;
	private String referenceValue;	
	private Integer soIdentifier;
	private Integer activeInd;
	private Integer buyerInput;
	private Integer providerInput;
	private Integer required;
	private Integer searchable;	
	private boolean privateInd;
	private Integer editable;
	private Integer pdfRefInd;
	private Date createdDate;
	private Date modifiedDate;
	private String modifiedBy;
	
	//SL-18825
	//VO attribute for new attribute 'Display field if no value' in Manage Custom Reference
	private Integer displayNoValue;
	
	public Integer getDisplayNoValue() {
		return displayNoValue;
	}

	public void setDisplayNoValue(Integer displayNoValue) {
		this.displayNoValue = displayNoValue;
	}

	public Integer getPdfRefInd() {
		return pdfRefInd;
	}

	public void setPdfRefInd(Integer pdfRefInd) {
		this.pdfRefInd = pdfRefInd;
	}

	public Integer getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}

	public Integer getBuyerRefTypeId() {
		return buyerRefTypeId;
	}

	public void setBuyerRefTypeId(Integer buyerRefTypeId) {
		this.buyerRefTypeId = buyerRefTypeId;
	}

	public String getReferenceType() {
		return referenceType;
	}

	public void setReferenceType(String referenceType) {
		this.referenceType = referenceType;
	}

	public String getReferenceDescription() {
		return referenceDescription;
	}

	public void setReferenceDescription(String referenceDescription) {
		this.referenceDescription = referenceDescription;
	}

	public Integer getSoIdentifier() {
		return soIdentifier;
	}

	public void setSoIdentifier(Integer soIdentifier) {
		this.soIdentifier = soIdentifier;
	}
	
	public boolean isSoIdentifier() {
		return ((soIdentifier != null && soIdentifier.intValue() == 1) ? true : false);
	}

	public Integer getActiveInd() {
		return activeInd;
	}

	public void setActiveInd(Integer activeInd) {
		this.activeInd = activeInd;
	}

	public Integer getBuyerInput() {
		return buyerInput;
	}

	public void setBuyerInput(Integer buyerInput) {
		this.buyerInput = buyerInput;
	}

	public Integer getProviderInput() {
		return providerInput;
	}

	public void setProviderInput(Integer providerInput) {
		this.providerInput = providerInput;
	}

	public Integer getRequired() {
		return required;
	}

	public void setRequired(Integer required) {
		this.required = required;
	}

	public Integer getSearchable() {
		return searchable;
	}

	public void setSearchable(Integer searchable) {
		this.searchable = searchable;
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

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getReferenceValue() {
		return referenceValue;
	}

	public void setReferenceValue(String referenceValue) {
		this.referenceValue = referenceValue;
	}

	public boolean isPrivateInd() {
		return privateInd;
	}

	public void setPrivateInd(boolean privateInd) {
		this.privateInd = privateInd;
	}

	public Integer getEditable() {
		return editable;
	}

	public void setEditable(Integer editable) {
		this.editable = editable;
	}

}
