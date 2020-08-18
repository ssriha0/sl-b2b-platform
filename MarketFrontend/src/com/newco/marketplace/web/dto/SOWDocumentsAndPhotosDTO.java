package com.newco.marketplace.web.dto;

import java.util.List;

import com.newco.marketplace.interfaces.OrderConstants;

public class SOWDocumentsAndPhotosDTO extends SOWBaseTabDTO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9185855616871662471L;
	private SODocumentDTO document;
	private List<SODocumentDTO> documents;
	private String description;
	private String documentSelection;
	private String documentUsageMessage;
	private String validationMessages;
	private String tab;
	private String validDisplayExtensions;
	private String priceModel;
	private Integer docCategoryId;
	private boolean autocloseOn;
	private String permitWarningStatus;
	private String documentTitle;
	private List <Integer> documentIds;
	private String docSrc;

	public String getDocSrc() {
		return docSrc;
	}

	public void setDocSrc(String docSrc) {
		this.docSrc = docSrc;
	}

	public List<Integer> getDocumentIds() {
		return documentIds;
	}

	public void setDocumentIds(List<Integer> documentIds) {
		this.documentIds = documentIds;
	}

	public String getDocumentTitle() {
		return documentTitle;
	}

	public void setDocumentTitle(String documentTitle) {
		this.documentTitle = documentTitle;
	}

	public String getTab() {
		return tab;
	}

	public void setTab(String tab) {
		this.tab = tab;
	}

	public String getValidationMessages() {
		return validationMessages;
	}

	public void setValidationMessages(String validationMessages) {
		this.validationMessages = validationMessages;
	}

	public String getDocumentUsageMessage() {
		return documentUsageMessage;
	}

	public void setDocumentUsageMessage(String documentUsageMessage) {
		this.documentUsageMessage = documentUsageMessage;
	}


	public String getDocumentSelection() {
		return documentSelection;
	}

	public void setDocumentSelection(String documentSelection) {
		this.documentSelection = documentSelection;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public SODocumentDTO getDocument() {
		return document;
	}

	public void setDocument(SODocumentDTO document) {
		this.document = document;
	}

	public List<SODocumentDTO> getDocuments() {
		return documents;
	}

	public void setDocuments(List<SODocumentDTO> documents) {
		this.documents = documents;
	}

	@Override
	public void validate() {
		if(getDescription() != null && getDescription().length() > 255){
			addError(getTheResourceBundle().getString("Description"),
					getTheResourceBundle().getString("Description_Validation_Msg"), 
					OrderConstants.SOW_TAB_ERROR);
		}
		if(getDocumentSelection() != null && getDocumentSelection().length() > 255){
			addError(getTheResourceBundle().getString("Document_Selection"),
					getTheResourceBundle().getString("Document_Selection_Msg"),
					OrderConstants.SOW_TAB_ERROR);
		}
	//	return getErrors();
	}

	@Override
	public String getTabIdentifier() {
		return OrderConstants.SOW_SOW_TAB;
	}

	public String getPartsSuppliedBy() {
		//return partsSuppliedBy;
		return OrderConstants.SOW_SOW_PARTS_NOT_REQUIRED.toString();
	}

	public String getValidDisplayExtensions() {
		return validDisplayExtensions;
	}

	public void setValidDisplayExtensions(String validDisplayExtensions) {
		this.validDisplayExtensions = validDisplayExtensions;
	}

	public String getPriceModel()
	{
		return priceModel;
	}

	public void setPriceModel(String priceModel)
	{
		this.priceModel = priceModel;
	}

	public Integer getDocCategoryId() {
		return docCategoryId;
	}

	public void setDocCategoryId(Integer docCategoryId) {
		this.docCategoryId = docCategoryId;
	}

	public boolean isAutocloseOn() {
		return autocloseOn;
	}

	public void setAutocloseOn(boolean autocloseOn) {
		this.autocloseOn = autocloseOn;
	}

	public String getPermitWarningStatus() {
		return permitWarningStatus;
	}

	public void setPermitWarningStatus(String permitWarningStatus) {
		this.permitWarningStatus = permitWarningStatus;
	}


	
	
}
