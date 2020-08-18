package com.newco.marketplace.dto.vo;

import java.io.File;/**
 * VO for the document table
 * Class to store all the details related to document section. 
 * @author Infosys 
 *
 * 
 */
import java.sql.Timestamp;

public class DocumentForSkuVO {
	
	protected Integer documentId ;
	protected String description;
	protected String title;
	protected String fileName;
	protected String format;
	protected String source;
	protected String keywords;
	protected Timestamp expireDate;
	protected Timestamp purgeDate;
	protected Timestamp createdDate;
	protected Timestamp modifiedDate;
	protected Integer vendorId;
	protected Integer roleId;
	protected Integer entityId;
	protected Integer deleteInd;
	protected String docPath;
	protected Long docSize;

	
	public Integer getDocumentId() {
		return documentId;
	}
	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
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
	
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public Timestamp getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public Integer getEntityId() {
		return entityId;
	}
	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}
	
	public String getDocPath() {
		return docPath;
	}
	public void setDocPath(String docPath) {
		this.docPath = docPath;
	}
	public Long getDocSize() {
		return docSize;
	}
	public void setDocSize(Long docSize) {
		this.docSize = docSize;
	}
	
	

}
