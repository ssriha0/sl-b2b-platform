package com.newco.marketplace.dto.vo;

import com.sears.os.vo.SerializableBaseVO;

public class VendorCredentialsDocumentVO extends SerializableBaseVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6192644641107513979L;
	protected int vendorCredId;
	protected int documentId;
	protected String activeInd = null;
	public int getDocumentId() {
		return documentId;
	}
	public void setDocumentId(int documentId) {
		this.documentId = documentId;
	}
	public int getVendorCredId() {
		return vendorCredId;
	}
	public void setVendorCredId(int vendorId) {
		this.vendorCredId = vendorId;
	}
	public String getActiveInd() {
		return activeInd;
	}
	public void setActiveInd(String activeInd) {
		this.activeInd = activeInd;
	}
	
}
