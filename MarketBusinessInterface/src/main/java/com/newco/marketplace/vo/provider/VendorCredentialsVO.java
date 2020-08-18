package com.newco.marketplace.vo.provider;

import java.sql.Date;

import com.sears.os.vo.SerializableBaseVO;

public class VendorCredentialsVO extends SerializableBaseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7522150510986467855L;
	//private FormFile credentialDocument =null;
	private int credentialDocumentId =0;
	private int vendorCredId;
	private int vendorId;
	private int categoryId;
	private int typeId;
	private String credCategory;
	private String credType;
	private String source;
	private String name;
	private String credentialNumber ="";
	private Date issueDate;
	private Date expirationDate;
	private String city;
	private String county;
	private String state;
	private String notes;
	private String createdDate;
	private String modifiedDate;
	private String modifiedBy;
	private String editURL = null;
	private int wfStateId = -1;
	// SL 21142 LMS credential upload Jira.
	private Boolean isFileUploaded;
	// SL 21142 End
        
	/**
	 * @return the wfStateId
	 */
	public int getWfStateId() {
		return wfStateId;
	}
	/**
	 * @param wfStateId the wfStateId to set
	 */
	public void setWfStateId(int wfStateId) {
		this.wfStateId = wfStateId;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCounty() {
		return this.county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getCreatedDate() {
		return this.createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public int getCategoryId() {
		return this.categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getTypeId() {
		return this.typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public Date getExpirationDate() {
		return this.expirationDate;
	}
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	public Date getIssueDate() {
		return this.issueDate;
	}
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}
	public String getModifiedBy() {
		return this.modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public String getModifiedDate() {
		return this.modifiedDate;
	}
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getVendorId() {
		return this.vendorId;
	}
	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}
	public String getSource() {
		return this.source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getState() {
		return this.state;
	}
	public void setState(String state) {
		this.state = state;
	}

	public int getVendorCredId() {
		return vendorCredId;
	}
	public void setVendorCredId(int vendorCredId) {    
		this.vendorCredId = vendorCredId;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getCredCategory() {
		return credCategory;
	}
	public void setCredCategory(String credCategory) {
		this.credCategory = credCategory;
	}
	public String getCredType() {
		return credType;
	}
	public void setCredType(String credType) {
		this.credType = credType;
	}
	public int getCredentialDocumentId() {
		return credentialDocumentId;
	}
	public void setCredentialDocumentId(int credentialDocumentId) {
		this.credentialDocumentId = credentialDocumentId;
	}
//	public FormFile getCredentialDocument() {
//		return credentialDocument;
//	}
//	public void setCredentialDocument(FormFile credentialDocument) {
//		this.credentialDocument = credentialDocument;
//	}
	public String getCredentialNumber() {
		return credentialNumber;
	}
	public void setCredentialNumber(String credentialNumber) {
		this.credentialNumber = credentialNumber;
	}

        public String getEditURL() {
            return this.editURL;
        }
        public void setEditURL(String editURL) {
            this.editURL = editURL;
        }
	public Boolean getIsFileUploaded() {
		return isFileUploaded;
	}

	public void setIsFileUploaded(Boolean isFileUploaded) {
		this.isFileUploaded = isFileUploaded;
	}
}