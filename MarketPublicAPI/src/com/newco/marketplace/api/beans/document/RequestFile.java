package com.newco.marketplace.api.beans.document;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("file")
public class RequestFile {
		
	@XStreamAlias("filename")
	private String fileName;	
	
	@XStreamAlias("blobBytes")
	private byte[]  blobBytes;
	
	@XStreamAlias("description")
	private String description;	
	
	public byte[] getBlobBytes() {
		return blobBytes;
	}
	public void setBlobBytes(byte[] blobBytes) {
		this.blobBytes = blobBytes;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
		
}
