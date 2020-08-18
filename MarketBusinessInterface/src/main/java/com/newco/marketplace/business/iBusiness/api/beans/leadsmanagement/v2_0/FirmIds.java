package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("FirmIds")
@XmlAccessorType(XmlAccessType.FIELD)
public class FirmIds {
	@XStreamImplicit(itemFieldName = "FirmId")
	@XmlElement(name="FirmId")
	private List<String>firmId;

	public List<String> getFirmId() {
		return firmId;
	}

	public void setFirmId(List<String> firmId) {
		this.firmId = firmId;
	}
}
