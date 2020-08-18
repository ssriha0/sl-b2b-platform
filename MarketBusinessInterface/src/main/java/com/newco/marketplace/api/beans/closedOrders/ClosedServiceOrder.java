package com.newco.marketplace.api.beans.closedOrders;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("serviceorder")
@XmlAccessorType(XmlAccessType.FIELD)

public class ClosedServiceOrder {
	

	@XStreamAlias("soId")
	private String soId;
	
	@XStreamAlias("createdDate")
	private String createdDate;
	
	@XStreamAlias("closedDate")
	private String closedDate;
	
	@XStreamAlias("buyerId")
	private Integer buyerId;

	@XStreamAlias("sectionGeneral")
	private ClosedOrderGeneralSection sectionGeneral;

	@XStreamAlias("serviceLocation")
	private ClosedOrderLocation serviceLocation;

	@XStreamAlias("schedule")
	private ClosedOrderSchedule schedule;
	
	@XStreamAlias("pricing")
	private ClosedOrderPricing pricing;
	
	@XStreamAlias("contacts")
	private ClosedOrderContacts contacts;
	
	@XStreamAlias("review")
	private ClosedOrderReview review;

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(String closedDate) {
		this.closedDate = closedDate;
	}

	public Integer getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}

	public ClosedOrderGeneralSection getSectionGeneral() {
		return sectionGeneral;
	}

	public void setSectionGeneral(ClosedOrderGeneralSection sectionGeneral) {
		this.sectionGeneral = sectionGeneral;
	}

	public ClosedOrderLocation getServiceLocation() {
		return serviceLocation;
	}

	public void setServiceLocation(ClosedOrderLocation serviceLocation) {
		this.serviceLocation = serviceLocation;
	}

	public ClosedOrderSchedule getSchedule() {
		return schedule;
	}

	public void setSchedule(ClosedOrderSchedule schedule) {
		this.schedule = schedule;
	}

	public ClosedOrderPricing getPricing() {
		return pricing;
	}

	public void setPricing(ClosedOrderPricing pricing) {
		this.pricing = pricing;
	}

	public ClosedOrderContacts getContacts() {
		return contacts;
	}

	public void setContacts(ClosedOrderContacts contacts) {
		this.contacts = contacts;
	}

	public ClosedOrderReview getReview() {
		return review;
	}

	public void setReview(ClosedOrderReview review) {
		this.review = review;
	}


}
