package com.newco.marketplace.web.dto;

import java.io.File;

import com.newco.marketplace.utils.DocumentUtils;

/*
 * Maintenance History
 * $Log: SLDocumentDTO.java,v $
 * Revision 1.6  2008/04/26 01:13:45  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.3.12.1  2008/04/01 22:04:14  mhaye05
 * merged changed from Head into I18_Fin branch
 *
 * Revision 1.4  2008/03/27 18:57:56  mhaye05
 * Merged I18_ADM to Head
 *
 * Revision 1.3.16.2  2008/03/14 01:01:31  dmill03
 * updated for new ajaxable interface
 *
 * Revision 1.3.16.1  2008/03/12 23:20:07  dmill03
 * updated by dmil03 for audit widgets and ajax support
 *
 * Revision 1.3  2008/02/14 23:44:48  mhaye05
 * Merged Feb4_release branch into head
 *
 * Revision 1.2  2008/02/11 22:31:08  pbhinga
 * Changes for Buyer Doc Manager Story. Minor changes for restructuring exception handling and using right constants.
 *
 * Revision 1.1  2008/02/08 23:37:25  pbhinga
 * Checked for Iteration 17 functionality - Document Manager. Reviewed by Gordon.
 *
 *
 */
public class SLDocumentDTO extends AbstractAjaxResultsDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9071581740258184434L;
	private Integer documentId;
	private String name;
	private String desc;
	private String title;
	private Double sizeDouble;
	private Integer size;
	private String sizeString;
	private byte[] blobBytes;
	private String documentType;
	
	
	private File upload;//
	private Integer category;
	private String uploadContentType; //The content type of the file
    private String uploadFileName; //The uploaded file name
    private String fileCaption;
	
	public String getFileCaption() {
		return fileCaption;
	}
	public void setFileCaption(String fileCaption) {
		this.fileCaption = fileCaption;
	}
	public String getUploadContentType() {
		return uploadContentType;
	}
	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}
	public String getUploadFileName() {
		return uploadFileName;
	}
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	public File getUpload() {
		return upload;
	}
	public void setUpload(File upload) {
		this.upload = upload;
	}
	public String getName() {
		return name;
	}
	public void setName(String fileName) {
		this.name = fileName;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getSizeString() {
		return sizeString;
	}
	public void setSizeString(String sizeString) {
		this.sizeString = sizeString;
	}
	public Integer getDocumentId() {
		return documentId;
	}
	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
	}
	public byte[] getBlobBytes() {
		return blobBytes;
	}
	public void setBlobBytes(byte[] blobBytes) {
		this.blobBytes = blobBytes;
	}
	public String toJSON() {
		// TODO Auto-generated method stub
		return null;
	}
	public String toXml() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("<document>");
		sb.append("<document_id>").append(this.getDocumentId()).append("</document_id>");
		sb.append("<document_name>").append(this.getName()).append("</document_name>");
		sb.append("<document_size>").append(DocumentUtils.convertBytesToKiloBytes(this.getSize().intValue(), true)).append("</document_size>");
		sb.append("<document_desc>").append(this.getDesc()).append("</document_desc>");
		sb.append("</document>");
		return sb.toString();
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public Double getSizeDouble() {
		return sizeDouble;
	}
	public void setSizeDouble(Double sizeDouble) {
		this.sizeDouble = sizeDouble;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public StringBuffer getBuffer() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	public Integer getCategory() {
		return category;
	}
	public void setCategory(Integer category) {
		this.category = category;
	}

		

}
