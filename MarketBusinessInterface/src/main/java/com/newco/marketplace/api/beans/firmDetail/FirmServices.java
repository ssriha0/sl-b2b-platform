package com.newco.marketplace.api.beans.firmDetail;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("services")
@XmlAccessorType(XmlAccessType.FIELD)
public class FirmServices {
	
	@XStreamImplicit(itemFieldName="service")
	private List<FirmService> service;

	public List<FirmService> getService() {
		return service;
	}

	public void setService(List<FirmService> service) {
		this.service = service;
	}
	
}
