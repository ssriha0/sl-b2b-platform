package com.newco.marketplace.dto.vo.leadsmanagement;

import java.io.File;
import java.sql.Timestamp;

//Vo class which holds lead_notes values.
public class BuyerLeadAttachmentVO {
	
	private Integer docCategoryId; 
   	private Integer documentId;
   	private Integer vendorId;
   	private String description; 
   	private String title;
   	private String fileName;
   	private String format;
   	private String source; 
   	private String keywords; 
	private Timestamp expireDate; 
	private Timestamp purgeDate;
	private Timestamp modifiedDate;
	private Integer roleId;
	private Integer delInd;
	private String docPath;
	private Double docSize;
	private Timestamp createdDate;
	private Integer entityId;
	private String firstName; 
	private String lastName;
	private String companyName;
	private byte[] blobBytes;
	private File document;
	protected String docWriteReadInd;
	private String leadId;
	private Integer companyId;
	protected String docSource;
	protected String filePath;
	private Integer leadDocId;
	private String createdBy;
	
	public Integer getDocCategoryId() {
		return docCategoryId;
	}
	public void setDocCategoryId(Integer docCategoryId) {
		this.docCategoryId = docCategoryId;
	}
	public Integer getDocumentId() {
		return documentId;
	}
	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public Timestamp getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(Timestamp expireDate) {
		this.expireDate = expireDate;
	}
	public Timestamp getPurgeDate() {
		return purgeDate;
	}
	public void setPurgeDate(Timestamp purgeDate) {
		this.purgeDate = purgeDate;
	}
	public Timestamp getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public Integer getDelInd() {
		return delInd;
	}
	public void setDelInd(Integer delInd) {
		this.delInd = delInd;
	}
	public String getDocPath() {
		return docPath;
	}
	public void setDocPath(String docPath) {
		this.docPath = docPath;
	}
	public Double getDocSize() {
		return docSize;
	}
	public void setDocSize(Double docSize) {
		this.docSize = docSize;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public Integer getEntityId() {
		return entityId;
	}
	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}
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
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public byte[] getBlobBytes() {
		return blobBytes;
	}
	public void setBlobBytes(byte[] blobBytes) {
		this.blobBytes = blobBytes;
	}
	public File getDocument() {
		return document;
	}
	public void setDocument(File document) {
		this.document = document;
	}
	public String getDocWriteReadInd() {
		return docWriteReadInd;
	}
	public void setDocWriteReadInd(String docWriteReadInd) {
		this.docWriteReadInd = docWriteReadInd;
	}
	public String getLeadId() {
		return leadId;
	}
	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public String getDocSource() {
		return docSource;
	}
	public void setDocSource(String docSource) {
		this.docSource = docSource;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public Integer getLeadDocId() {
		return leadDocId;
	}
	public void setLeadDocId(Integer leadDocId) {
		this.leadDocId = leadDocId;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	
    
}
