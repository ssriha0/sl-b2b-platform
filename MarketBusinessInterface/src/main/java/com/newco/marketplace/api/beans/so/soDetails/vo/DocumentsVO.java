package com.newco.marketplace.api.beans.so.soDetails.vo;

import java.util.List;

public class DocumentsVO {

	private Integer maxUploadLimitPerSO;
	private Integer remaininguploadLimitPerSO;	
	private Integer maxuploadlimitPerDoc;	
	private List<DocumentDetailsVO> document;
	
	public Integer getMaxUploadLimitPerSO() {
		return maxUploadLimitPerSO;
	}
	public void setMaxUploadLimitPerSO(Integer maxUploadLimitPerSO) {
		this.maxUploadLimitPerSO = maxUploadLimitPerSO;
	}
	public Integer getRemaininguploadLimitPerSO() {
		return remaininguploadLimitPerSO;
	}
	public void setRemaininguploadLimitPerSO(Integer remaininguploadLimitPerSO) {
		this.remaininguploadLimitPerSO = remaininguploadLimitPerSO;
	}
	public Integer getMaxuploadlimitPerDoc() {
		return maxuploadlimitPerDoc;
	}
	public void setMaxuploadlimitPerDoc(Integer maxuploadlimitPerDoc) {
		this.maxuploadlimitPerDoc = maxuploadlimitPerDoc;
	}
	public List<DocumentDetailsVO> getDocument() {
		return document;
	}
	public void setDocument(List<DocumentDetailsVO> document) {
		this.document = document;
	}
	
	
}
