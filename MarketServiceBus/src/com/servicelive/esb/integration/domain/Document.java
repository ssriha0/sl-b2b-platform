package com.servicelive.esb.integration.domain;

public class Document {
	private Long serviceOrderId;
	private String documentTitle;
	private String description;
	private Boolean logo;
	public void setServiceOrderId(Long serviceOrderId) {
		this.serviceOrderId = serviceOrderId;
	}
	public Long getServiceOrderId() {
		return serviceOrderId;
	}
	public void setDocumentTitle(String documentTitle) {
		this.documentTitle = documentTitle;
	}
	public String getDocumentTitle() {
		return documentTitle;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescription() {
		return description;
	}
	public void setLogo(Boolean logo) {
		this.logo = logo;
	}
	public Boolean getLogo() {
		return logo;
	}
}
