package com.newco.marketplace.api.mobile.beans.addsoproviderpart;

import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.thoughtworks.xstream.annotations.XStreamAlias;


@XSD(name = "findSupplierRequest.xsd", path = "/resources/schemas/mobile/v2_0/")
@XmlRootElement(name = "findSupplierRequest")
@XStreamAlias("findSupplierRequest")
public class FindSupplierRequest {
    
	@XStreamAlias("latitude")
	private Float latitude;
	
	@XStreamAlias("longitude")
	private Float longitude;
	
	@XStreamAlias("maxDistance")
	private Byte maxDistance;
	
	@XStreamAlias("parts")
	private Parts parts;

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	public Byte getMaxDistance() {
		return maxDistance;
	}

	public void setMaxDistance(Byte maxDistance) {
		this.maxDistance = maxDistance;
	}

	public Parts getParts() {
		return parts;
	}

	public void setParts(Parts parts) {
		this.parts = parts;
	}


}
