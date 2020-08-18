package com.newco.marketplace.api.mobile.beans.sodetails;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * This is a generic bean class for storing DocumentType information.
 * @author Infosys
 *
 */

@XStreamAlias("documentType")
@XmlAccessorType(XmlAccessType.FIELD)
public class DocumentType {
	
	@XStreamAlias("docTypeId")
	private Integer docTypeId;
	
	@XStreamAlias("documentTitle")
	private String documentTitle;
	
	@XStreamAlias("mandatoryInd")
	private String mandatoryInd;

	public Integer getDocTypeId() {
		return docTypeId;
	}

	public void setDocTypeId(Integer docTypeId) {
		this.docTypeId = docTypeId;
	}

	public String getDocumentTitle() {
		return documentTitle;
	}

	public void setDocumentTitle(String documentTitle) {
		this.documentTitle = documentTitle;
	}

	public String getMandatoryInd() {
		return mandatoryInd;
	}

	public void setMandatoryInd(String mandatoryInd) {
		this.mandatoryInd = mandatoryInd;
	}

}
