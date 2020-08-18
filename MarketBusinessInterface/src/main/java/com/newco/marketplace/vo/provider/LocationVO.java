package com.newco.marketplace.vo.provider;

import com.sears.os.vo.SerializableBaseVO;


public class LocationVO extends SerializableBaseVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3791119099952602274L;
	private String zip = null;
	private String city = null;
	private String state = null;


	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}


}
