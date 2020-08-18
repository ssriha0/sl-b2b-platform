package com.newco.marketplace.web.dto.simple;

import java.io.InputStream;
import java.util.List;

import com.newco.marketplace.web.dto.SODocumentDTO;
import com.newco.marketplace.web.dto.SOWBaseTabDTO;

public class CreateServiceOrderDocAndPhotosDTO extends SOWBaseTabDTO {
	
	private static final long serialVersionUID = -2147870991931526459L;
	
	private SODocumentDTO document;
	private List<SODocumentDTO> documents;
	private String description;
	private String documentSelection;
	private String documentUsageMessage;
	private String validationMessages;
	private String tab;
	private List<SODocumentDTO> photoDocuments;
	private SODocumentDTO photoDoc;
	private InputStream imageStreamView;
	private String priceModel;

	
	public List<SODocumentDTO> getDocuments() {
		return documents;
	}
	public void setDocuments(List<SODocumentDTO> documents) {
		this.documents = documents;
	}
	public List<SODocumentDTO> getPhotoDocuments() {
		return photoDocuments;
	}
	public void setPhotoDocuments(List<SODocumentDTO> photoDocuments) {
		this.photoDocuments = photoDocuments;
	}
	public SODocumentDTO getDocument() {
		return document;
	}
	public void setDocument(SODocumentDTO document) {
		this.document = document;
	}
	public SODocumentDTO getPhotoDoc() {
		return photoDoc;
	}
	public void setPhotoDoc(SODocumentDTO photoDoc) {
		this.photoDoc = photoDoc;
	}
	@Override
	public String getTabIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void validate() {
		// TODO Auto-generated method stub
		
	}
	public String getDocumentSelection() {
		return documentSelection;
	}
	public void setDocumentSelection(String documentSelection) {
		this.documentSelection = documentSelection;
	}
	public InputStream getImageStreamView() {
		return imageStreamView;
	}
	public void setImageStreamView(InputStream imageStreamView) {
		this.imageStreamView = imageStreamView;
	}
	public String getPriceModel()
	{
		return priceModel;
	}
	public void setPriceModel(String priceModel)
	{
		this.priceModel = priceModel;
	}


}
