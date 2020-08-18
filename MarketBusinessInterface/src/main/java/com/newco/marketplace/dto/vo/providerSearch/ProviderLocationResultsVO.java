package com.newco.marketplace.dto.vo.providerSearch;

import com.sears.os.vo.SerializableBaseVO;

public class ProviderLocationResultsVO extends SerializableBaseVO{

	private static final long serialVersionUID = 8936692078746148004L;
	
	private int providerResourceId;
	private String providerGisLatitude;
	private String providerGisLongitude;
	private String city;
	private String state;
	private String zip;

	public String getProviderGisLatitude() {
		return providerGisLatitude;
	}
	public void setProviderGisLatitude(String providerGisLatitude) {
		this.providerGisLatitude = providerGisLatitude;
	}
	public String getProviderGisLongitude() {
		return providerGisLongitude;
	}
	public void setProviderGisLongitude(String providerGisLongitude) {
		this.providerGisLongitude = providerGisLongitude;
	}
	public int getProviderResourceId() {
		return providerResourceId;
	}
	public void setProviderResourceId(int providerResourceId) {
		this.providerResourceId = providerResourceId;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}

}
