package com.newco.marketplace.dto.vo.spn;

import java.util.Date;

import com.sears.os.vo.SerializableBaseVO;
/**
 * @author Infosys
 * The class is used to store the details of Buyer Agreement documents.
 *
 */
public class SPNSignAndReturnDocumentVO extends SerializableBaseVO{
	private static final long serialVersionUID = -3954899124631601209L;
	private int docId; 
	private int spnId;
	private String fileName;
	private String documentTitle;
	private String format;
	private String buyerDocFormatDescription;
	
	private Integer providerUploadInd; 
	private String docStateId;
	private String docStateDesc;
	private String docTypeDesc;
	private String docDescription;
	private String docFileName;
	private String docTitle;
	private String docFormat;
	private int provFirmUplDocId;
	private String provDocFormatDescription;
	private String auditorComments;
	private Date auditedDate;
	private String auditedBy;
	private String pageNo;
	
	public int getDocId() {
		return docId;
	}
	public void setDocId(int docId) {
		this.docId = docId;
	}
	public int getSpnId() {
		return spnId;
	}
	public void setSpnId(int spnId) {
		this.spnId = spnId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getDocumentTitle() {
		return documentTitle;
	}
	public void setDocumentTitle(String documentTitle) {
		this.documentTitle = documentTitle;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public Integer getProviderUploadInd() {
		return providerUploadInd;
	}
	public void setProviderUploadInd(Integer providerUploadInd) {
		this.providerUploadInd = providerUploadInd;
	}
	public String getDocStateId() {
		return docStateId;
	}
	public void setDocStateId(String docStateId) {
		this.docStateId = docStateId;
	}
	public String getDocStateDesc() {
		return docStateDesc;
	}
	public void setDocStateDesc(String docStateDesc) {
		this.docStateDesc = docStateDesc;
	}
	public String getDocTypeDesc() {
		return docTypeDesc;
	}
	public void setDocTypeDesc(String docTypeDesc) {
		this.docTypeDesc = docTypeDesc;
	}
	public String getDocDescription() {
		return docDescription;
	}
	public void setDocDescription(String docDescription) {
		this.docDescription = docDescription;
	}
	public String getDocFileName() {
		return docFileName;
	}
	public void setDocFileName(String docFileName) {
		this.docFileName = docFileName;
	}
	public String getDocTitle() {
		return docTitle;
	}
	public void setDocTitle(String docTitle) {
		this.docTitle = docTitle;
	}
	public String getDocFormat() {
		return docFormat;
	}
	public void setDocFormat(String docFormat) {
		this.docFormat = docFormat;
	}
	public int getProvFirmUplDocId() {
		return provFirmUplDocId;
	}
	public void setProvFirmUplDocId(int provFirmUplDocId) {
		this.provFirmUplDocId = provFirmUplDocId;
	}
	public String getBuyerDocFormatDescription() {
		return buyerDocFormatDescription;
	}
	public void setBuyerDocFormatDescription(String buyerDocFormatDescription) {
		this.buyerDocFormatDescription = buyerDocFormatDescription;
	}
	public String getProvDocFormatDescription() {
		return provDocFormatDescription;
	}
	public void setProvDocFormatDescription(String provDocFormatDescription) {
		this.provDocFormatDescription = provDocFormatDescription;
	}
	public String getAuditorComments() {
		return auditorComments;
	}
	public void setAuditorComments(String auditorComments) {
		this.auditorComments = auditorComments;
	}
	public Date getAuditedDate() {
		return auditedDate;
	}
	public void setAuditedDate(Date auditedDate) {
		this.auditedDate = auditedDate;
	}
	public String getAuditedBy() {
		return auditedBy;
	}
	public void setAuditedBy(String auditedBy) {
		this.auditedBy = auditedBy;
	}
	public String getPageNo() {
		return pageNo;
	}
	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}
}