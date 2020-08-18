package com.newco.marketplace.api.beans.so;


import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing routed provider information.
 * @author Infosys
 *
 */
@XStreamAlias("routedprovider")
public class RoutedProvider {
	
	@XStreamAlias("resourceID")
	private Integer resourceId;
	
	@XStreamAlias("companyID")
	private Integer companyID;
	
	@XStreamAlias("createdDate")
	private String createdDate;
	
	@XStreamAlias("response")
	private String response;
	
	@XStreamAlias("acceptedProviderContact")
	private Contact providerContact;
	
	@XStreamAlias("comment")
	private String comment;
	
	@XStreamAlias("offerExpiration")
	private String offerExpiration;
	
	@XStreamAlias("increaseSpend")	
	private Pricing increaseSpend; 
	
	@XStreamAlias("rescheduleDates")
	private Schedule rescheduleDates;



	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getOfferExpiration() {
		return offerExpiration;
	}

	public void setOfferExpiration(String offerExpiration) {
		this.offerExpiration = offerExpiration;
	}

	public Pricing getIncreaseSpend() {
		return increaseSpend;
	}

	public void setIncreaseSpend(Pricing increaseSpend) {
		this.increaseSpend = increaseSpend;
	}

	public Schedule getRescheduleDates() {
		return rescheduleDates;
	}

	public void setRescheduleDates(Schedule rescheduleDates) {
		this.rescheduleDates = rescheduleDates;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public Integer getCompanyID() {
		return companyID;
	}

	public void setCompanyID(Integer companyID) {
		this.companyID = companyID;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public Contact getProviderContact() {
		return providerContact;
	}

	public void setProviderContact(Contact providerContact) {
		this.providerContact = providerContact;
	}
	
}
