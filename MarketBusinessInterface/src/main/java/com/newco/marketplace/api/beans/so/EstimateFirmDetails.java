package com.newco.marketplace.api.beans.so;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("firmDetails")
public class EstimateFirmDetails {

	@XStreamAlias("vendorId")
	private Integer vendorId;
	
	@XStreamAlias("location")
	private EstimateLocation location;

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public EstimateLocation getLocation() {
		return location;
	}

	public void setLocation(EstimateLocation location) {
		this.location = location;
	}
}
