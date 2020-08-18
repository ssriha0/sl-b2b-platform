package com.newco.marketplace.api.beans.closedOrders;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing general section information.
 * @author Infosys
 *
 */
@XStreamAlias("sectionGeneral")
@XmlAccessorType(XmlAccessType.FIELD)
public class ClosedOrderGeneralSection {
	
	@XStreamAlias("title")
	private String title;
	
	@XStreamAlias("overview")
	private String overview;
	
	@XStreamAlias("buyerTerms")
	private String buyerTerms;
	
	@XStreamAlias("specialInstructions")
	private String specialInstructions;
	

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

}
