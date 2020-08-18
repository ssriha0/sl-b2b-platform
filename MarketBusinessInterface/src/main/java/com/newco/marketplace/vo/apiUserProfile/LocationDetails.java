package com.newco.marketplace.vo.apiUserProfile;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;
@XmlRootElement(name = "listOflocations")
@XStreamAlias("listOflocations")
@XmlAccessorType(XmlAccessType.FIELD)
public class LocationDetails {
	@XmlElement(name="location")
	private List<LocationResponseDate> location;

	public List<LocationResponseDate> getLocation() {
		return location;
	}

	public void setLocation(List<LocationResponseDate> location) {
		this.location = location;
	}


	

}
