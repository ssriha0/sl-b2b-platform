package com.newco.marketplace.api.beans.so.addEstimate;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("otherServices")
@XmlAccessorType(XmlAccessType.FIELD)
public class OtherServices {

	@XStreamImplicit(itemFieldName="otherService")
	private List<OtherService> otherService;

	public List<OtherService> getOtherService() {
		return otherService;
	}

	public void setOtherService(List<OtherService> otherService) {
		this.otherService = otherService;
	}

}
