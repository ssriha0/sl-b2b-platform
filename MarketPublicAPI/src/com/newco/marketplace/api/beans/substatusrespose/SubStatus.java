package com.newco.marketplace.api.beans.substatusrespose;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("SubStatus")
public class SubStatus {

	@XStreamAlias("id")
	private Integer id;
	
	@XStreamAlias("description")
	private String description;


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
}
