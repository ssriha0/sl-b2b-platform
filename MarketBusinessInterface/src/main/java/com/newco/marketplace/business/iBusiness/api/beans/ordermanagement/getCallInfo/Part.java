package com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.getCallInfo;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("part")
public class Part {
	

	
	@XStreamAlias("manufacturer")
	private String manufacturer;
	
	@XStreamAlias("model")
	private String modelNo;
	
	@XStreamAlias("availabilityDate")
	private String availabilityDate;
	
	@XStreamAlias("merchandiseCode")
	private String merchandiseCode;
	
	@XStreamAlias("brand")
	private String brand;
	
	@XStreamAlias("location")
	private Location location;

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getModelNo() {
		return modelNo;
	}

	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}

	public String getAvailabilityDate() {
		return availabilityDate;
	}

	public void setAvailabilityDate(String availabilityDate) {
		this.availabilityDate = availabilityDate;
	}

	public String getMerchandiseCode() {
		return merchandiseCode;
	}

	public void setMerchandiseCode(String merchandiseCode) {
		this.merchandiseCode = merchandiseCode;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	
	

}
