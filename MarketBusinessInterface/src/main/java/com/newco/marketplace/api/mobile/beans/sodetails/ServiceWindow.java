package com.newco.marketplace.api.mobile.beans.sodetails;

import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing sku information.
 * @author Infosys
 *
 */

@XStreamAlias("serviceWindow")
public class ServiceWindow {
	
	@XStreamAlias("startTime")
	private String startTime;
	
	@XStreamAlias("endTime")
	private String endTime;

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
}
