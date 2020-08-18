package com.newco.marketplace.web.dto;

import java.util.List;

import com.newco.marketplace.interfaces.OrderConstants;

public class SLDocumentsAndPhotosDTO extends SOWBaseTabDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9185855616871662471L;
	private SLDocumentDTO document;
	private List<SLDocumentDTO> documents;
	private String description;
	private String documentSelection;
	private String documentUsageMessage;
	private String validationMessages;
	private String tab;
	private String title;
	private boolean logoDocumentInd;
	private String documetType;

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

	public SLDocumentDTO getDocument() {
		return document;
	}

	public void setDocument(SLDocumentDTO document) {
		this.document = document;
	}

	public List<SLDocumentDTO> getDocuments() {
		return documents;
	}

	public void setDocuments(List<SLDocumentDTO> documents) {
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

	public String getTabIdentifier() {
		return OrderConstants.SOW_SOW_TAB;
	}

	public String getPartsSuppliedBy() {
		//return partsSuppliedBy;
		return OrderConstants.SOW_SOW_PARTS_NOT_REQUIRED.toString();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isLogoDocumentInd() {
		return logoDocumentInd;
	}

	public void setLogoDocumentInd(boolean logoDocumentInd) {
		this.logoDocumentInd = logoDocumentInd;
	}

	public String getDocumetType() {
		return documetType;
	}

	public void setDocumetType(String documetType) {
		this.documetType = documetType;
	}

	
}
