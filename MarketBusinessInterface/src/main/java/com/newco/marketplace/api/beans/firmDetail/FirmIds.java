package com.newco.marketplace.api.beans.firmDetail;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("firmIds")
@XmlAccessorType(XmlAccessType.FIELD)
public class FirmIds {
	
	@XStreamImplicit(itemFieldName = "firmId")
	private List<String> firmId;

	public List<String> getFirmId() {
		return firmId;
	}

	public void setFirmId(List<String> firmId) {
		this.firmId = firmId;
	}

	
}
