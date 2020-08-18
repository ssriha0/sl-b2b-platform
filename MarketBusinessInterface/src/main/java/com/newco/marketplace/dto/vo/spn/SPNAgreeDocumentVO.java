package com.newco.marketplace.dto.vo.spn;

import java.util.Date;

import com.sears.os.vo.SerializableBaseVO;

/**
 * @author Infosys
 * The class is used to store the details of Buyer Agreement documents.
 *
 */
public class SPNAgreeDocumentVO extends SerializableBaseVO{
	private static final long serialVersionUID = -3954899124631601209L;
	private int docId; 
	private int spnId;
	private String documentTitle;
	private byte[] documentData;
	private String documentDataString;

	private String docStateId;
	private Date modifiedDate;
	
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
	public String getDocumentTitle() {
		return documentTitle;
	}
	public void setDocumentTitle(String documentTitle) {
		this.documentTitle = documentTitle;
	}
	public String getDocStateId() {
		return docStateId;
	}
	public void setDocStateId(String docStateId) {
		this.docStateId = docStateId;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public byte[] getDocumentData() {
		return documentData;
	}
	public void setDocumentData(byte[] documentData) {
		this.documentData = documentData;
		String tmpString = new String(documentData);
		
		setDocumentDataString(tmpString);
	}
	public String getDocumentDataString() {
		String tmpString = new String(this.documentData);
		setDocumentDataString(tmpString);
		
		return tmpString;
	}
	public void setDocumentDataString(String documentDataString) {
		this.documentDataString = documentDataString;
	}	
}