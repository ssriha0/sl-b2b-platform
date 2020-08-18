package com.newco.marketplace.api.mobile.beans.Filter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("serviceProNames")
@XmlAccessorType(XmlAccessType.FIELD)
public class ServiceProName {

	@XStreamAlias("id")
	private Integer id;
	
	@XStreamAlias("value")
	private String value;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	

}
