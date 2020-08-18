package com.newco.marketplace.api.mobile.beans.Filter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("searchCriteria")
@XmlAccessorType(XmlAccessType.FIELD)
public class FilterSearchCriteria {

	@XStreamAlias("markets")
	private MarketCriteria markets;
	
	@XStreamAlias("orderStatuses")
	private StatusCriteria orderStatuses;
	
	@XStreamAlias("orderSubstatuses")
	private SubStatusCriteria orderSubstatuses;
	
	@XStreamAlias("serviceProNames")
	private ServiceProNameCriteria serviceProNames;
	
	@XStreamAlias("appointment")
	private AppointmentDateCriteria appointment;
	
	@XStreamAlias("flaggedSO")
	private Boolean flagged;
	
	@XStreamAlias("unAssigned")
	private Boolean unAssigned;

	public Boolean getFlagged() {
		return flagged;
	}

	public void setFlagged(Boolean flagged) {
		this.flagged = flagged;
	}

	public Boolean getUnAssigned() {
		return unAssigned;
	}

	public void setUnAssigned(Boolean unAssigned) {
		this.unAssigned = unAssigned;
	}

	public MarketCriteria getMarkets() {
		return markets;
	}

	public void setMarkets(MarketCriteria markets) {
		this.markets = markets;
	}

	public StatusCriteria getOrderStatuses() {
		return orderStatuses;
	}

	public void setOrderStatuses(StatusCriteria orderStatuses) {
		this.orderStatuses = orderStatuses;
	}

	public SubStatusCriteria getOrderSubstatuses() {
		return orderSubstatuses;
	}

	public void setOrderSubstatuses(SubStatusCriteria orderSubstatuses) {
		this.orderSubstatuses = orderSubstatuses;
	}

	public ServiceProNameCriteria getServiceProNames() {
		return serviceProNames;
	}

	public void setServiceProNames(ServiceProNameCriteria serviceProNames) {
		this.serviceProNames = serviceProNames;
	}

	public AppointmentDateCriteria getAppointmentDates() {
		return appointment;
	}

	public void setAppointmentDates(AppointmentDateCriteria appointment) {
		this.appointment = appointment;
	}

	



}