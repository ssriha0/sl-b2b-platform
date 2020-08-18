package com.newco.marketplace.api.mobile.beans.soDetails.v3_0;

import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing pickupLocation information.
 * @author Infosys
 *
 */

@XStreamAlias("pickupLocation")
public class PickUpLocation {
		
	@XStreamAlias("pickupLocationName")
	private String pickupLocationName;
	
	@XStreamAlias("pickupLocationStreet1")
	private String pickupLocationStreet1;
	
	@XStreamAlias("pickupLocationStreet2")
	private String pickupLocationStreet2;
	
	@XStreamAlias("pickupLocationCity")
	private String pickupLocationCity;
	
	@XStreamAlias("pickupLocationState")
	private String pickupLocationState;
	
	@XStreamAlias("pickupLocationZip")
	private String pickupLocationZip;
    
	public String getPickupLocationName() {
		return pickupLocationName;
	}

	public void setPickupLocationName(String pickupLocationName) {
		this.pickupLocationName = pickupLocationName;
	}

	public String getPickupLocationStreet1() {
		return pickupLocationStreet1;
	}

	public void setPickupLocationStreet1(String pickupLocationStreet1) {
		this.pickupLocationStreet1 = pickupLocationStreet1;
	}

	public String getPickupLocationStreet2() {
		return pickupLocationStreet2;
	}

	public void setPickupLocationStreet2(String pickupLocationStreet2) {
		this.pickupLocationStreet2 = pickupLocationStreet2;
	}

	public String getPickupLocationCity() {
		return pickupLocationCity;
	}

	public void setPickupLocationCity(String pickupLocationCity) {
		this.pickupLocationCity = pickupLocationCity;
	}

	public String getPickupLocationState() {
		return pickupLocationState;
	}

	public void setPickupLocationState(String pickupLocationState) {
		this.pickupLocationState = pickupLocationState;
	}

	public String getPickupLocationZip() {
		return pickupLocationZip;
	}

	public void setPickupLocationZip(String pickupLocationZip) {
		this.pickupLocationZip = pickupLocationZip;
	}

}
