package com.newco.marketplace.api.beans.so.complete;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("part")
public class PartList {

	@XStreamAlias("manufacturer")
	private String manufacturer;
	
	@XStreamAlias("partsId")
	private String partsId;
	
	@XStreamAlias("returnCarrier")
	private String returnCarrier;
	
	@XStreamAlias("returnTrackingNumber")
	private String returnTrackingNumber;

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getPartsId() {
		return partsId;
	}

	public void setPartsId(String partsId) {
		this.partsId = partsId;
	}

	public String getReturnCarrier() {
		return returnCarrier;
	}

	public void setReturnCarrier(String returnCarrier) {
		this.returnCarrier = returnCarrier;
	}

	public String getReturnTrackingNumber() {
		return returnTrackingNumber;
	}

	public void setReturnTrackingNumber(String returnTrackingNumber) {
		this.returnTrackingNumber = returnTrackingNumber;
	}
	
}
