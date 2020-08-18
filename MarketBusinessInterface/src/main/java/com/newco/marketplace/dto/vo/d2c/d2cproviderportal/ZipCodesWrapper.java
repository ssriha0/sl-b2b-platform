package com.newco.marketplace.dto.vo.d2c.d2cproviderportal;

import java.util.ArrayList;
import java.util.List;

public class ZipCodesWrapper {
	
    private String vendorLocId;
	private String coverageRadius;
	List<ZipCodesData> zipCodesData = new ArrayList<ZipCodesData>();
	
	public String getVendorLocId() {
		return vendorLocId;
	}
	public void setVendorLocId(String vendorLocId) {
		this.vendorLocId = vendorLocId;
	}
	public String getCoverageRadius() {
		return coverageRadius;
	}
	public void setCoverageRadius(String coverageRadius) {
		this.coverageRadius = coverageRadius;
	}
	public List<ZipCodesData> getZipCodesData() {
		return zipCodesData;
	}
	public void setZipCodesData(List<ZipCodesData> zipCodesData) {
		this.zipCodesData = zipCodesData;
	}
	


}
