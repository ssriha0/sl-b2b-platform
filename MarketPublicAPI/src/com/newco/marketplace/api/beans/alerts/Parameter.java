package com.newco.marketplace.api.beans.alerts;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing parameter information.
 * @author Infosys
 *
 */
@XStreamAlias("parameter")
public class Parameter {
	
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
