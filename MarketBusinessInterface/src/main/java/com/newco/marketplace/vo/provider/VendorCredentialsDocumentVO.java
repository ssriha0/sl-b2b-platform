package com.newco.marketplace.vo.provider;

import com.sears.os.vo.SerializableBaseVO;

public class VendorCredentialsDocumentVO extends SerializableBaseVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1481883950923180831L;
	protected int vendorCredId;
	protected int documentId;
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
	
}
