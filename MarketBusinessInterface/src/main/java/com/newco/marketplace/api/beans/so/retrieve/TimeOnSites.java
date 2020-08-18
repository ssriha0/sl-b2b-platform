package com.newco.marketplace.api.beans.so.retrieve;

import java.util.List;

import com.newco.marketplace.api.beans.so.timeonsite.SOTimeOnSiteRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This is a generic bean class for storing information for a list of notes.
 * @author Infosys
 *
 */
@XStreamAlias("TimeOnSites")
public class TimeOnSites {
	@XStreamImplicit(itemFieldName="timeOnSite")
	private List<SOTimeOnSiteRequest> timeOnSites;

	public List<SOTimeOnSiteRequest> getTimeOnSites() {
		return timeOnSites;
	}

	public void setTimeOnSites(List<SOTimeOnSiteRequest> timeOnSites) {
		this.timeOnSites = timeOnSites;
	}

	}