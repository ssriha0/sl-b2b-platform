package com.newco.marketplace.dto.vo.spn;

import com.sears.os.vo.SerializableBaseVO;
/**
 * @author Infosys
 * The class is used to store the details of Buyer Agreement documents.
 *
 */
public class SPNProvUploadedDocsVO extends SerializableBaseVO{
	private static final long serialVersionUID = 6759991722815233486L;
	private int spnId;
	private int spnBuyerDocId;
	private int provFirmUplDocId;
	private int vendorId;
	private String docStateId;
	private String docStateDesc;
	private String docTypeDesc;
	private String docDescription;
	private String docFileName;
	private String docTitle;
	private String docFormat;
	private String docFormatDescription;
    private byte[]  docBytes;	
    private long docFileSize;
    private int buyerId;
    private int activeInd;
    private int deletedInd;
    private int id;
    private String userName;
    private int provFirmUpldOldDocId;

	public byte[] getDocBytes() {
		return docBytes;
	}
	public void setDocBytes(byte[] docBytes) {
		this.docBytes = docBytes;
	}
	public long getDocFileSize() {
		return docFileSize;
	}
	public void setDocFileSize(long docFileSize) {
		this.docFileSize = docFileSize;
	}
	public int getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(int buyerId) {
		this.buyerId = buyerId;
	}
	public int getActiveInd() {
		return activeInd;
	}
	public void setActiveInd(int activeInd) {
		this.activeInd = activeInd;
	}
	public int getDeletedInd() {
		return deletedInd;
	}
	public void setDeletedInd(int deletedInd) {
		this.deletedInd = deletedInd;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSpnId() {
		return spnId;
	}
	public void setSpnId(int spnId) {
		this.spnId = spnId;
	}
	public int getSpnBuyerDocId() {
		return spnBuyerDocId;
	}
	public void setSpnBuyerDocId(int spnBuyerDocId) {
		this.spnBuyerDocId = spnBuyerDocId;
	}
	public int getProvFirmUplDocId() {
		return provFirmUplDocId;
	}
	public void setProvFirmUplDocId(int provFirmUplDocId) {
		this.provFirmUplDocId = provFirmUplDocId;
	}
	public int getVendorId() {
		return vendorId;
	}
	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
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
	public String getDocFormatDescription() {
		return docFormatDescription;
	}
	public void setDocFormatDescription(String docFormatDescription) {
		this.docFormatDescription = docFormatDescription;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getProvFirmUpldOldDocId() {
		return provFirmUpldOldDocId;
	}
	public void setProvFirmUpldOldDocId(int provFirmUpldOldDocId) {
		this.provFirmUpldOldDocId = provFirmUpldOldDocId;
	}
}