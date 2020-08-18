package com.newco.marketplace.vo.provider;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;

import com.sears.os.vo.SerializableBaseVO;

public class DocumentVO extends SerializableBaseVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5608596622420962557L;
	protected int documentId ;
	protected int vendorId ;
	protected int docCategoryId ;
	protected String description;
	protected String title;
	protected String fileName;
	protected String fileReference;
	protected String format;
	protected String source;
	protected String keywords;
	protected String modifiedBy;
	protected Timestamp expireDate;
	protected Timestamp purgeDate;
	protected Timestamp lastUpdateTimestamp;
	protected byte[] blobBytes;
	protected String roleId;
	private long docFileSize;
	
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Timestamp getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(Timestamp expireDate) {
		this.expireDate = expireDate;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileReference() {
		return fileReference;
	}
	public void setFileReference(String fileReference) {
		this.fileReference = fileReference;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public Timestamp getPurgeDate() {
		return purgeDate;
	}
	public void setPurgeDate(Timestamp purgeDate) {
		this.purgeDate = purgeDate;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getDocCategoryId() {
		return docCategoryId;
	}
	public void setDocCategoryId(int typeId) {
		this.docCategoryId = typeId;
	}
	public InputStream getFileBlob() {
		return new ByteArrayInputStream(blobBytes);
	}
	public byte[] getBlobBytes() {
		return blobBytes;
	}
	public void setBlobBytes(byte[] inFileBlob) {
		this.blobBytes = inFileBlob;
	}
	
	public int getDocumentId() {
		return documentId;
	}
	public void setDocumentId(int documentId) {
		this.documentId = documentId;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public void setFileBlob(  InputStream iStream) throws IOException {
	
		System.err.println(" IN THE BLOB THING " + iStream.available());
		iStream.read(this.blobBytes);
	
	}
	
	public String getMimeType() {
		
		if (this.getFileName() == null)
			return "text/a";
		
		String format = getFileName().substring(getFileName().lastIndexOf(".")+1).trim();
		if(format.equalsIgnoreCase("a")) //check the output file type
		return "text/a";
		else if(format.equalsIgnoreCase("ucc"))
		return "text/ucc";
		else if(format.equalsIgnoreCase("exe"))
		return "application/octet-stream";
		else if(format.equalsIgnoreCase("jpg"))
		return "image/jpg";
		else if(format.equalsIgnoreCase("xls"))
		return "application/vnd.ms-excel";
		else if(format.equalsIgnoreCase("ppt"))
		return "application/x-msdownload";
		else if(format.equalsIgnoreCase("doc"))
		return "application/msword";
		else if(format.equalsIgnoreCase("rtf"))
		return "application/rtf"; 
		else if(format.equalsIgnoreCase("txt"))
		return "text/ascii";
		else if(format.equalsIgnoreCase("pdf"))
		return "application/pdf";
		else if(format.equalsIgnoreCase("audio_basic"))
		return "audio/basic";
		else if(format.equalsIgnoreCase("audio_wav"))
		return "audio/wav";
		else if(format.equalsIgnoreCase("image_gif"))
		return "image/gif";
		else if(format.equalsIgnoreCase("image_jpeg"))
		return "image/jpeg";
		else if(format.equalsIgnoreCase("image_bmp"))
		return "image/bmp";
		else if(format.equalsIgnoreCase("image_x-png"))
		return "image/x-png";
		else if(format.equalsIgnoreCase("msdownload"))
		return "application/x-msdownload";
		else if(format.equalsIgnoreCase("video_avi"))
		return "video/avi";
		else if(format.equalsIgnoreCase("video_mpeg"))
		return "video/mpeg";
		else if(format.equalsIgnoreCase("html"))
		return "text/html";
		else if(format.equalsIgnoreCase("xml"))
		return "text/xml";
		else
		return null;
		}
	public Timestamp getLastUpdateTimestamp() {
		return lastUpdateTimestamp;
	}
	public void setLastUpdateTimestamp(Timestamp lastUpdateTimestamp) {
		this.lastUpdateTimestamp = lastUpdateTimestamp;
	}
	public int getVendorId() {
		return vendorId;
	}
	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}
	public long getDocFileSize() {
		return docFileSize;
	}
	public void setDocFileSize(long docFileSize) {
		this.docFileSize = docFileSize;
	}
	

	

}
