package com.newco.marketplace.vo.provider;

public class ResourceCredentialsDocumentVO extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3028055504663545225L;
	protected int resourceCredId;
	protected int documentId;

	public int getDocumentId() {
		return documentId;
	}

	public void setDocumentId(int documentId) {
		this.documentId = documentId;
	}

	public int getResourceCredId() {
		return resourceCredId;
	}

	public void setResourceCredId(int vendorId) {
		this.resourceCredId = vendorId;
	}

}
