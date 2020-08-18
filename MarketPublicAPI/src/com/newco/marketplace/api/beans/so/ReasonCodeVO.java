package com.newco.marketplace.api.beans.so;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing reason codes for request reschedule information.
 * @author Infosys
 *
 */
@XStreamAlias("reasonCode")
public class ReasonCodeVO {
	
	@XStreamAlias("id")
	private Integer id;
	
	@XStreamAlias("descr")
	private String descr;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}
	
}
