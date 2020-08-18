/**
 * 
 */
package com.newco.marketplace.web.utils;

/**
 * @author Covansys - Offshore
 *
 */
public class FileUpload {
	
	protected byte[]  credentialDocumentBytes;
	protected String credentialDocumentFileName;
	protected String credentialDocumentExtention;
	protected String credentialDocumentReference;
	protected long credentialDocumentFileSize;
	
	public long getCredentialDocumentFileSize() {
		return credentialDocumentFileSize;
	}
	public void setCredentialDocumentFileSize(long credentialDocumentFileSize) {
		this.credentialDocumentFileSize = credentialDocumentFileSize;
	}
	public byte[] getCredentialDocumentBytes() {
		return credentialDocumentBytes;
	}
	public void setCredentialDocumentBytes(byte[] credentialDocumentBytes) {
		this.credentialDocumentBytes = credentialDocumentBytes;
	}
	public String getCredentialDocumentFileName() {
		return credentialDocumentFileName;
	}
	public void setCredentialDocumentFileName(String credentialDocumentFileName) {
		this.credentialDocumentFileName = credentialDocumentFileName;
	}
	public String getCredentialDocumentExtention() {
		return credentialDocumentExtention;
	}
	public void setCredentialDocumentExtention(String credentialDocumentExtention) {
		this.credentialDocumentExtention = credentialDocumentExtention;
	}
	public String getCredentialDocumentReference() {
		return credentialDocumentReference;
	}
	public void setCredentialDocumentReference(String credentialDocumentReference) {
		this.credentialDocumentReference = credentialDocumentReference;
	}
}
