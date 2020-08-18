package com.newco.marketplace.api.mobile.beans.getProviderProfile;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("address")
public class Address {

	@XStreamAlias("dispAddStreet1")
	private String dispAddStreet1;
	
	@XStreamAlias("dispAddStreet2")
	private String dispAddStreet2;
	
	@XStreamAlias("dispAddApt")
	private String dispAddApt;
	
	@XStreamAlias("dispAddCity")
	private String dispAddCity;
	
	@XStreamAlias("dispAddState")
	private String dispAddState;
	
	@XStreamAlias("dispAddZip")
	private String dispAddZip;
	
	public Address(){};
	
	public String getDispAddStreet1() {
		return dispAddStreet1;
	}
	public void setDispAddStreet1(String dispAddStreet1) {
		this.dispAddStreet1 = dispAddStreet1;
	}
	public String getDispAddStreet2() {
		return dispAddStreet2;
	}
	public void setDispAddStreet2(String dispAddStreet2) {
		this.dispAddStreet2 = dispAddStreet2;
	}
	public String getDispAddApt() {
		return dispAddApt;
	}
	public void setDispAddApt(String dispAddApt) {
		this.dispAddApt = dispAddApt;
	}
	public String getDispAddCity() {
		return dispAddCity;
	}
	public void setDispAddCity(String dispAddCity) {
		this.dispAddCity = dispAddCity;
	}
	public String getDispAddState() {
		return dispAddState;
	}
	public void setDispAddState(String dispAddState) {
		this.dispAddState = dispAddState;
	}
	public String getDispAddZip() {
		return dispAddZip;
	}
	public void setDispAddZip(String dispAddZip) {
		this.dispAddZip = dispAddZip;
	}
}
