package com.newco.marketplace.api.mobile.beans.addsoproviderpart;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("supplier")
@XmlAccessorType(XmlAccessType.FIELD)
public class Supplier {

	@XStreamAlias("name")
	private String name;

	@XStreamAlias("address1")
	private String address1;

	@XStreamAlias("address2")
	private String address2;

	@XStreamAlias("city")
	private String city;

	@XStreamAlias("state")
	private String state;

	@XStreamAlias("zip")
	private String zip;

	@XStreamAlias("phone")
	private String phone;

	@XStreamAlias("fax")
	private String fax;

	@XStreamAlias("latitude")
	private Float latitude;

	@XStreamAlias("longitude")
	private Float longitude;

	@XStreamAlias("distance")
	private Double distance;
    
	@XStreamAlias("parts")
	private Parts parts;
	
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

	public Parts getParts() {
		return parts;
	}

	public void setParts(Parts parts) {
		this.parts = parts;
	}

}