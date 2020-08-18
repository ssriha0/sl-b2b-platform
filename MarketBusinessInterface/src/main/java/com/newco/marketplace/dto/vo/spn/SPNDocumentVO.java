package com.newco.marketplace.dto.vo.spn;

import java.util.Date;

import com.sears.os.vo.SerializableBaseVO;
/**
 * @author Infosys
 * The class is used to store the details of Buyer Agreement documents.
 *
 */
public class SPNDocumentVO extends SerializableBaseVO{
	private static final long serialVersionUID = 8101426269307024786L;
	private int docId; 
	private int spnId;
	private int docTypeId;
	private String docTypeDescription;
	private String fileName;
	private String docDescr;
	private String documentTitle;
	private byte[] documentContent;
	private String content;
	private String format;
	private String state;
	private String rejectId;
	private String rejectReason;
	private String buyerName;
	private String spnName;
	private int firmId;
	private Date time;
	private String modifiedBy;
	private Integer actionMappingId;
	
	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}	
	public byte[] getDocumentContent() {
		return documentContent;
	}
	public void setDocumentContent(byte[] documentContent) {
		this.documentContent = documentContent;
	}
	public String getDocumentTitle() {
		return documentTitle;
	}
	public void setDocumentTitle(String documentTitle) {
		this.documentTitle = documentTitle;
	}	
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
	public int getDocTypeId() {
		return docTypeId;
	}
	public void setDocTypeId(int docTypeId) {
		this.docTypeId = docTypeId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getDocTypeDescription() {
		return docTypeDescription;
	}
	public int getFirmId() {
		return firmId;
	}

	public void setFirmId(int firmId) {
		this.firmId = firmId;
	}


	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Date getTime() {
		return time;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getSpnName() {
		return spnName;
	}

	public void setSpnName(String spnName) {
		this.spnName = spnName;
	}
	public void setDocTypeDescription(String docTypeDescription) {
		this.docTypeDescription = docTypeDescription;
	}

	public String getDocDescr() {
		return docDescr;
	}

	public void setDocDescr(String docDescr) {
		this.docDescr = docDescr;
	}

	public String getRejectId() {
		return rejectId;
	}

	public void setRejectId(String rejectId) {
		this.rejectId = rejectId;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * @return the actionMappingId
	 */
	public Integer getActionMappingId() {
		return actionMappingId;
	}

	/**
	 * @param actionMappingId the actionMappingId to set
	 */
	public void setActionMappingId(Integer actionMappingId) {
		this.actionMappingId = actionMappingId;
	}
}