package com.newco.marketplace.api.beans.so;

import com.newco.marketplace.api.beans.so.retrieve.TimeOnSites;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing general section information.
 * @author Infosys
 *
 */
@XStreamAlias("sectionGeneral")
public class GeneralSection {
	
	@XStreamAlias("title")
	private String title;
	
	@XStreamAlias("overview")
	private String overview;
	
	@XStreamAlias("buyerTerms")
	private String buyerTerms;
	
	@XStreamAlias("specialInstructions")
	private String specialInstructions;
	
	@XStreamAlias("previousState")
	private String previousState;
	
	@XStreamAlias("timeOnSites")
	private TimeOnSites timeOnSites;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public String getBuyerTerms() {
		return buyerTerms;
	}

	public void setBuyerTerms(String buyerTerms) {
		this.buyerTerms = buyerTerms;
	}

	public String getSpecialInstructions() {
		return specialInstructions;
	}

	public void setSpecialInstructions(String specialInstructions) {
		this.specialInstructions = specialInstructions;
	}

	public String getPreviousState() {
		return previousState;
	}

	public void setPreviousState(String previousState) {
		this.previousState = previousState;
	}

	public TimeOnSites getTimeOnSites() {
		return timeOnSites;
	}

	public void setTimeOnSites(TimeOnSites timeOnSites) {
		this.timeOnSites = timeOnSites;
	}

}
