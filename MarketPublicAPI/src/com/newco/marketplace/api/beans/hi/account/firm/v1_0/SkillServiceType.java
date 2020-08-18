package com.newco.marketplace.api.beans.hi.account.firm.v1_0;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XmlRootElement(name = "skillServiceTypes")
@XStreamAlias("skillServiceTypes")
@XmlAccessorType(XmlAccessType.FIELD)
public class SkillServiceType {

	@XStreamImplicit(itemFieldName = "serviceType")
	private List<String> serviceType;

	public List<String> getServiceType() {
		return serviceType;
	}

	public void setServiceType(List<String> serviceType) {
		this.serviceType = serviceType;
	}

	
}
