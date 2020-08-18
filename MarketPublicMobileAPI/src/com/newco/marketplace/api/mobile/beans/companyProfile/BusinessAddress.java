package com.newco.marketplace.api.mobile.beans.companyProfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("businessAddress")
@XmlAccessorType(XmlAccessType.FIELD)
public class BusinessAddress {

	@XStreamAlias("businessStreet1")
	private String businessStreet1;
	
	@XStreamAlias("businessStreet2")
	private String businessStreet2;
	
	@XStreamAlias("businessCity")
	private String businessCity;
	
	@XStreamAlias("businessState")
	private String businessState;
	
	@XStreamAlias("businessZip")
	private String businessZip;
	
	public String getBusinessStreet1() {
		return businessStreet1;
	}
	public void setBusinessStreet1(String businessStreet1) {
		this.businessStreet1 = businessStreet1;
	}
	public String getBusinessStreet2() {
		return businessStreet2;
	}
	public void setBusinessStreet2(String businessStreet2) {
		this.businessStreet2 = businessStreet2;
	}
	public String getBusinessCity() {
		return businessCity;
	}
	public void setBusinessCity(String businessCity) {
		this.businessCity = businessCity;
	}
	public String getBusinessState() {
		return businessState;
	}
	public void setBusinessState(String businessState) {
		this.businessState = businessState;
	}
	public String getBusinessZip() {
		return businessZip;
	}
	public void setBusinessZip(String businessZip) {
		this.businessZip = businessZip;
	}
}
