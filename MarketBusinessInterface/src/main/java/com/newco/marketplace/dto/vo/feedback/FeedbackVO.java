package com.newco.marketplace.dto.vo.feedback;

import java.io.Serializable;
import java.util.Date;


public class FeedbackVO implements Serializable{
	private static final long serialVersionUID = -1527770441184997895L;
	private String category;
	private Integer categoryId;
	private String feedbackComments;
	//private Boolean contactInd;
	private String tabName;
	private String pageName;
	private String sourceURL;
	private Integer resourceId;
	private Integer companyId;
	private Integer roleId;
	private String modifiedBy;
	private Integer documentId;
	private String screenshotURL;
	private String fileName;
	private String firstName;
	private String lastName;
	private String firmName;
	private Date createdDate;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirmName() {
		return firmName;
	}
	public void setFirmName(String firmName) {
		this.firmName = firmName;
	}
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getScreenshotURL() {
		return screenshotURL;
	}
	public void setScreenshotURL(String screenshotURL) {
		this.screenshotURL = screenshotURL;
	}

	public Integer getDocumentId() {
		return documentId;
	}
	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
	}
	/*
	public Boolean getContactInd() {
		return contactInd;
	}
	public void setContactInd(Boolean contactInd) {
		this.contactInd = contactInd;
	}
	*/
	
	public String getFeedbackComments() {
		return feedbackComments;
	}
	public void setFeedbackComments(String feedbackComments) {
		this.feedbackComments = feedbackComments;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getTabName() {
		return tabName;
	}
	public void setTabName(String tabName) {
		this.tabName = tabName;
	}
	public String getPageName() {
		return pageName;
	}
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
	public String getSourceURL() {
		return sourceURL;
	}
	public void setSourceURL(String sourceURL) {
		this.sourceURL = sourceURL;
	}
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

}
