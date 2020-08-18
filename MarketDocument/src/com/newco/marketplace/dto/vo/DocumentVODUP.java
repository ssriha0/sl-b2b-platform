package com.newco.marketplace.dto.vo;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;

import com.newco.marketplace.webservices.base.CommonVO;

/**
 * VO for the document table
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision: 1.6 $ $Author: glacy $ $Date: 2008/05/02 21:23:59 $
 */

/*
 * Maintenance History
 * $Log: DocumentVO.java,v $
 * Revision 1.6  2008/05/02 21:23:59  glacy
 * Shyam: Merged I19_OMS branch to HEAD.
 *
 * Revision 1.5  2008/04/26 00:40:09  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.3.42.1  2008/04/18 21:38:49  rgurra0
 * added spnNetworkId
 *
 * Revision 1.3.22.1  2008/04/23 11:41:13  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.4  2008/04/23 05:16:55  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.3  2008/01/04 16:07:00  mhaye05
 * fixed bug where we were not sending company id for buyer authorization check
 *
 * Revision 1.2  2007/11/05 16:49:58  akashya
 * fixed attribute types
 *
 * Revision 1.1  2007/11/02 01:10:53  akashya
 * Initial checkin
 *
 */
public class DocumentVODUP  extends CommonVO{
	static final long serialVersionUID = 111111;
	
	protected int docType;
	protected boolean storeInDatabase;
	protected String soId;
	protected File document;
	protected Integer documentId ;
	protected Integer docCategoryId;
	protected String description;
	protected String title;
	protected String fileName;
	protected String format;
	protected String source;
	protected String keywords;
	protected Timestamp expireDate;
	protected Timestamp purgeDate;
	protected byte[] blobBytes;
	protected Timestamp createdDate;
	protected Timestamp modifiedDate;
	protected Integer vendorId;
	protected Integer roleId;
	protected Integer entityId;
	protected Integer delInd;
	protected String docPath;
	protected Long docSize;
	protected Integer companyId;
	protected Integer spnNetworkId;
	
	protected Timestamp lastUpdateTimestamp;
	protected Integer buyerId;
	protected String docWriteReadInd;
	protected String filePath;
	
	
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Timestamp getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(Timestamp expireDate) {
		this.expireDate = expireDate;
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
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public Timestamp getPurgeDate() {
		return purgeDate;
	}
	public void setPurgeDate(Timestamp purgeDate) {
		this.purgeDate = purgeDate;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getDocCategoryId() {
		return docCategoryId;
	}
	public void setDocCategoryId(Integer typeId) {
		this.docCategoryId = typeId;
	}
	public InputStream getFileBlob() {
		return new ByteArrayInputStream(blobBytes);
	}
	public byte[] getBlobBytes() {
		return blobBytes;
	}
	public void setBlobBytes(byte[] inFileBlob) {
		this.blobBytes = inFileBlob;
	}
	
	public Integer getDocumentId() {
		return documentId;
	}
	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
	}

	public void setFileBlob(  InputStream iStream) throws IOException {
		iStream.read(this.blobBytes);
	}
	
	public String getMimeType() {
		return this.format;
	}
	public Timestamp getLastUpdateTimestamp() {
		return lastUpdateTimestamp;
	}
	public void setLastUpdateTimestamp(Timestamp lastUpdateTimestamp) {
		this.lastUpdateTimestamp = lastUpdateTimestamp;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public Integer getDocType() {
		return Integer.valueOf(docType);
	}
	public void setDocType(Integer docType) {
		this.docType = docType.intValue();
	}
	public File getDocument() {
		return document;
	}
	public void setDocument(File document) {
		this.document = document;
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
	public Long getDocSize() {
		return docSize;
	}
	public void setDocSize(Long docSize) {
		this.docSize = docSize;
	}
	public boolean isStoreInDatabase() {
		return storeInDatabase;
	}
	public void setStoreInDatabase(boolean storeInDatabase) {
		this.storeInDatabase = storeInDatabase;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public void setDocType(int docType) {
		this.docType = docType;
	}
	public Integer getSpnNetworkId() {
		return spnNetworkId;
	}
	public void setSpnNetworkId(Integer spnNetworkId) {
		this.spnNetworkId = spnNetworkId;
	}
	public String getDocWriteReadInd() {
		return docWriteReadInd;
	}
	public void setDocWriteReadInd(String docWriteReadInd) {
		this.docWriteReadInd = docWriteReadInd;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
}