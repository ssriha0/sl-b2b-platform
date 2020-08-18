package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
@XmlRootElement(name = "Attachments")
@XStreamAlias("Attachments")
@XmlAccessorType(XmlAccessType.FIELD)
public class Attachments {

	@XmlElement(name="Attachment")
	@XStreamImplicit(itemFieldName = "Attachment")
	private List<Attachment> attachmentList;

	public List<Attachment> getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(List<Attachment> attachmentList) {
		this.attachmentList = attachmentList;
	}
	



}
