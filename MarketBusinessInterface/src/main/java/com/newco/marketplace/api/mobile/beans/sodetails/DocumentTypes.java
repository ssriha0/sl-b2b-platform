package com.newco.marketplace.api.mobile.beans.sodetails;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This is a generic bean class for storing documents information.
 * @author Infosys
 *
 */

@XStreamAlias("documentTypes")
@XmlAccessorType(XmlAccessType.FIELD)
public class DocumentTypes {
	
	@XStreamAlias("maxUploadLimitPerSO")
	private Integer maxUploadLimitPerSO;
	
	@XStreamAlias("remaininguploadLimitPerSO")
	private Integer remaininguploadLimitPerSO;
	
	@XStreamAlias("maxuploadlimitPerDoc")
	private Integer maxuploadlimitPerDoc;
	
	@XStreamImplicit(itemFieldName="documentType")
	private List<DocumentType> documentType;

	public List<DocumentType> getDocumentType() {
		return documentType;
	}

	public void setDocumentType(List<DocumentType> documentType) {
		this.documentType = documentType;
	}

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


	
}
