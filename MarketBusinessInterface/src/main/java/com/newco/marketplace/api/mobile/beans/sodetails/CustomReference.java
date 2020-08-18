package com.newco.marketplace.api.mobile.beans.sodetails;

import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing customer reference information.
 * @author Infosys
 *
 */

@XStreamAlias("customRef")
public class CustomReference {
	
	@XStreamAlias("name")
	private String name;
	
	@XStreamAlias("value")
	private String value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
