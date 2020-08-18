package com.newco.marketplace.api.mobile.beans.sodetails;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This is a generic bean class for storing documents information.
 * @author Infosys
 *
 */

@XStreamAlias("documents")
@XmlAccessorType(XmlAccessType.FIELD)
public class Documents {
	
	@XStreamAlias("maxUploadLimitPerSO")
	private Integer maxUploadLimitPerSO;
	
	@XStreamAlias("remaininguploadLimitPerSO")
	private Integer remaininguploadLimitPerSO;
	
	@XStreamAlias("maxUploadlimitPerDoc")
	private Integer maxuploadlimitPerDoc;
	
	@XStreamImplicit(itemFieldName="document")
	private List<Document> document;

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

	public List<Document> getDocument() {
		return document;
	}

	public void setDocument(List<Document> document) {
		this.document = document;
	}
	

	
}
