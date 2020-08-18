package com.newco.marketplace.vo.provider;

import java.io.File;
import java.util.Date;
import java.util.List;

import com.sears.os.vo.SerializableBaseVO;

public class TeamCredentialsVO extends SerializableBaseVO{
	/**
	 *
	 */
	private static final long serialVersionUID = 5018966527792748815L;
	private int resourceCredId;
	private int resourceId = 0;
	private int vendorId = 0;
	private int categoryId;
	private int typeId;
	private String credCategory;
	private String credType;
	private String licenseName;
	private String issuerName;
	private Date issueDate;
	private Date expirationDate;
	private String no;
	private String city;
	private String county;
	private String state;
	private String notes;
	private String createdDate;
	private Date modifiedDate;
	private String modifiedBy;
	private String credentialNumber;
	private int credentialDocumentId = 0;
	private String editURL = null;
	private int wfStateId = -1;
	private String resourceName;
	private boolean isVerified = false;
	private List<Integer> includedStatus;
	//SL20014-variable added to view provider uploaded documents for buyer
	private int currentDocumentID;
	//SL20014-end
	// SL 21142 LMS credential upload Jira.
	private Boolean isFileUploaded;
	// SL 21142 End
	public int getCurrentDocumentID() {
		return currentDocumentID;
	}

	public void setCurrentDocumentID(int currentDocumentID) {
		this.currentDocumentID = currentDocumentID;
	}

	/**
     * Variable added for Document upload
     */
    File file = null;
    protected byte[]  credentialDocumentBytes;
	protected String credentialDocumentFileName;
	protected String credentialDocumentExtention;
	protected String credentialDocumentReference;
	protected long credentialDocumentFileSize;

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public byte[] getCredentialDocumentBytes() {
		return credentialDocumentBytes;
	}

	public void setCredentialDocumentBytes(byte[] credentialDocumentBytes) {
		this.credentialDocumentBytes = credentialDocumentBytes;
	}

	public String getCredentialDocumentFileName() {
		return credentialDocumentFileName;
	}

	public void setCredentialDocumentFileName(String credentialDocumentFileName) {
		this.credentialDocumentFileName = credentialDocumentFileName;
	}

	public String getCredentialDocumentExtention() {
		return credentialDocumentExtention;
	}

	public void setCredentialDocumentExtention(String credentialDocumentExtention) {
		this.credentialDocumentExtention = credentialDocumentExtention;
	}

	public String getCredentialDocumentReference() {
		return credentialDocumentReference;
	}

	public void setCredentialDocumentReference(String credentialDocumentReference) {
		this.credentialDocumentReference = credentialDocumentReference;
	}

	public long getCredentialDocumentFileSize() {
		return credentialDocumentFileSize;
	}

	public void setCredentialDocumentFileSize(long credentialDocumentFileSize) {
		this.credentialDocumentFileSize = credentialDocumentFileSize;
	}

	/**
	 * @return the wfStateId
	 */
	public int getWfStateId() {
		return wfStateId;
	}

	/**
	 * @param wfStateId
	 *            the wfStateId to set
	 */
	public void setWfStateId(int wfStateId) {
		this.wfStateId = wfStateId;
	}

	public int getCredentialDocumentId() {
		return credentialDocumentId;
	}

	public void setCredentialDocumentId(int credentialDocumentId) {
		this.credentialDocumentId = credentialDocumentId;
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

	public String getNo() {
		return this.no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public int getResourceId() {
		return this.resourceId;
	}

	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}

	public String getIssuerName() {
		return issuerName;
	}

	public void setIssuerName(String issuerName) {
		this.issuerName = issuerName;
	}

	public String getLicenseName() {
		return licenseName;
	}

	public void setLicenseName(String licenseName) {
		this.licenseName = licenseName;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getResourceCredId() {
		return resourceCredId;
	}

	public void setResourceCredId(int resourceCredId) {
		this.resourceCredId = resourceCredId;
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

	public String getCredentialNumber() {
		return credentialNumber;
	}

	public void setCredentialNumber(String credentialNumber) {
		this.credentialNumber = credentialNumber;
	}

	public int getVendorId() {
		return vendorId;
	}

	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}

	public String getEditURL() {
		return this.editURL;
	}

	public void setEditURL(String editURL) {
		this.editURL = editURL;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}

	public boolean getisVerified() {
		return isVerified;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	/**
	 * @return the includedStatus
	 */
	public List<Integer> getIncludedStatus() {
		return includedStatus;
	}

	/**
	 * @param includedStatus the includedStatus to set
	 */
	public void setIncludedStatus(List<Integer> includedStatus) {
		this.includedStatus = includedStatus;
	}

	public Boolean getIsFileUploaded() {
		return isFileUploaded;
	}

	public void setIsFileUploaded(Boolean isFileUploaded) {
		this.isFileUploaded = isFileUploaded;
	}
	
}