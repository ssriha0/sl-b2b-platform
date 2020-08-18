package com.servicelive.esb.dto;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("RepairAt")
public class RepairLocation implements Serializable {

	/** generated serialVersionUID */
	private static final long serialVersionUID = 3226376734171678539L;

	@XStreamAlias("Address")
	private Address address;
	
	@XStreamAlias("AltPhone")
	private String altPhone;
	
	@XStreamAlias("Phone")
	private String phone;

	
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getAltPhone() {
		return altPhone;
	}

	public void setAltPhone(String altPhone) {
		this.altPhone = altPhone;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
