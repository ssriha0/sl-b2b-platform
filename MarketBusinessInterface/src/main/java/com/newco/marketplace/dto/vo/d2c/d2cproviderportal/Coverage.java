package com.newco.marketplace.dto.vo.d2c.d2cproviderportal;

public class Coverage {
	private String id;
	private String vendorId;
	private String locId;
	private String coverageRadius;
	private String zip;
	
	

	public String getCoverageRadius() {
		return coverageRadius;
	}
	public void setCoverageRadius(String coverageRadius) {
		this.coverageRadius = coverageRadius;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getVendorId() {
		return vendorId;
	}
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}
	public String getLocId() {
		return locId;
	}
	public void setLocId(String locId) {
		this.locId = locId;
	}
	@Override
	public String toString() {
		return this.zip;
	}



}
