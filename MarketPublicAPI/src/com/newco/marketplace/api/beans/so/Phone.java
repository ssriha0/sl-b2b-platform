package com.newco.marketplace.api.beans.so;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing primary phone information.
 * @author Infosys
 *
 */
@XStreamAlias("primaryPhone")
public class Phone {
	
	@XStreamAlias("phoneType")
	private String phoneType;
		
	@XStreamAlias("number")
	private String number;
		
	@XStreamAlias("extension")
	private String extension;

	public String getPhoneType() {
		return phoneType;
	}

	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

}
