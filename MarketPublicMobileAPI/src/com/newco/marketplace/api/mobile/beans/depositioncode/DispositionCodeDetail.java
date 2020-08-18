package com.newco.marketplace.api.mobile.beans.depositioncode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("dispositionCodeDetail")
@XmlAccessorType(XmlAccessType.FIELD)
public class DispositionCodeDetail {

	@XStreamAlias("codeId")
	private String codeId;
	
	@XStreamAlias("description")
	private String description;
	
	@XStreamAlias("isClientCharged")
	private boolean isClientCharged;

	public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isClientCharged() {
		return isClientCharged;
	}

	public void setClientCharged(boolean isClientCharged) {
		this.isClientCharged = isClientCharged;
	}
	
}
