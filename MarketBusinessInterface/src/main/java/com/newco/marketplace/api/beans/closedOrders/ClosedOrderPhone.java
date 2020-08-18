package com.newco.marketplace.api.beans.closedOrders;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing primary phone information.
 * @author Infosys
 *
 */
@XStreamAlias("primaryPhone")
@XmlAccessorType(XmlAccessType.FIELD)
public class ClosedOrderPhone {
	
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
