package com.newco.marketplace.api.mobile.beans.addsoproviderpart;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("ns2:suppliers")
public class LpnSupplier {
	
	@XStreamAlias("ns2:name")
	private String name;

	@XStreamAlias("ns2:address1")
	private String address1;

	@XStreamAlias("ns2:address2")
	private String address2;

	@XStreamAlias("ns2:city")
	private String city;

	@XStreamAlias("ns2:state")
	private String state;

	@XStreamAlias("ns2:zip")
	private String zip;

	@XStreamAlias("ns2:phone")
	private String phone;

	@XStreamAlias("ns2:fax")
	private String fax;

	@XStreamAlias("ns2:latitude")
	private Float latitude;

	@XStreamAlias("ns2:longitude")
	private Float longitude;

	@XStreamAlias("ns2:distance")
	private Double distance;
	
	@XStreamImplicit(itemFieldName="ns2:parts")
	private List<LpnPart> parts;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

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

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public List<LpnPart> getParts() {
		return parts;
	}

	public void setParts(List<LpnPart> parts) {
		this.parts = parts;
	}
}
