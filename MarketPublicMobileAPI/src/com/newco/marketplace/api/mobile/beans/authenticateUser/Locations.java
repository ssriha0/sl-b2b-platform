package com.newco.marketplace.api.mobile.beans.authenticateUser;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XmlRootElement(name = "locations")
@XStreamAlias("locations")
@XmlAccessorType(XmlAccessType.FIELD)
public class Locations {	
	@XStreamImplicit(itemFieldName="location")
	private List<LocationDetail> location;

	public List<LocationDetail> getLocation() {
		return location;
	}

	public void setLocation(List<LocationDetail> location) {
		this.location = location;
	}
	
}
