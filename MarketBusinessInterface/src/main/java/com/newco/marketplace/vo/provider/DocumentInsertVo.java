package com.newco.marketplace.vo.provider;

import com.sears.os.vo.SerializableBaseVO;

public class DocumentInsertVo extends SerializableBaseVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6666331029158612250L;
	protected byte[]  credentialDocumentBytes ;
	protected String credentialDocumentFileName ;
	protected String credentialDocumentExtention ;
	protected String credentialDocumentReference ;
	protected String  credentialName;
	protected String  credentialSource;
	protected int credentialNumber;
	protected int credentialId;
	protected int vendorId;
	
	

	public String getCredentialName() {
		return credentialName;
	}
	public void setCredentialName(String credentialName) {
		this.credentialName = credentialName;
	}
	public int getCredentialNumber() {
		return credentialNumber;
	}
	public void setCredentialNumber(int  credentialNumber) {
		this.credentialNumber = credentialNumber;
	}
	public String getCredentialSource() {
		return credentialSource;
	}
	public void setCredentialSource(String credentialSource) {
		this.credentialSource = credentialSource;
	}
	public int getCredentialId() {
		return credentialId;
	}
	public void setCredentialId(int credentialId) {
		this.credentialId = credentialId;
	}
	public byte[] getCredentialDocumentBytes() {
		return credentialDocumentBytes;
	}
	public void setCredentialDocumentBytes(byte[] credentialDocumentBytes) {
		this.credentialDocumentBytes = credentialDocumentBytes;
	}
	public String getCredentialDocumentExtention() {
		return credentialDocumentExtention;
	}
	public void setCredentialDocumentExtention(String credentialDocumentExtention) {
		this.credentialDocumentExtention = credentialDocumentExtention;
	}
	public String getCredentialDocumentFileName() {
		return credentialDocumentFileName;
	}
	public void setCredentialDocumentFileName(String credentialDocumentFileName) {
		this.credentialDocumentFileName = credentialDocumentFileName;
	}
	public String getCredentialDocumentReference() {
		return credentialDocumentReference;
	}
	public void setCredentialDocumentReference(String credentialDocumentReference) {
		this.credentialDocumentReference = credentialDocumentReference;
	}
	public int getVendorId() {
		return vendorId;
	}
	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}
	
	
}
