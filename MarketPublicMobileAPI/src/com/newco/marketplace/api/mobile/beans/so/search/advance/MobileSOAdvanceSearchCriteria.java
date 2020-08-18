package com.newco.marketplace.api.mobile.beans.so.search.advance;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.newco.marketplace.api.mobile.beans.so.search.SearchIntegerElement;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("advanceSearchCriteria")
@XmlAccessorType(XmlAccessType.FIELD)
public class MobileSOAdvanceSearchCriteria {
	
	@XStreamAlias("markets")
	private SearchIntegerElement markets;
	@XStreamAlias("statuses")
	private SearchIntegerElement statuses;
	@XStreamAlias("subStatuses")
	private SearchIntegerElement subStatuses;
	@XStreamAlias("serviceProIds")
	private SearchIntegerElement serviceProIds;
	@XStreamAlias("appointment")
	private Appointment appointment;
	@XStreamAlias("flaggedOnlyInd")
	private Boolean flaggedOnlyInd;
	@XStreamAlias("unAssignedInd")
	private Boolean unAssignedInd;
	
	
	public Boolean getFlaggedOnlyInd() {
		return flaggedOnlyInd;
	}
	public void setFlaggedOnlyInd(Boolean flaggedOnlyInd) {
		this.flaggedOnlyInd = flaggedOnlyInd;
	}
	public Boolean getUnAssignedInd() {
		return unAssignedInd;
	}
	public void setUnAssignedInd(Boolean unAssignedInd) {
		this.unAssignedInd = unAssignedInd;
	}
	public SearchIntegerElement getMarkets() {
		return markets;
	}
	public void setMarkets(SearchIntegerElement markets) {
		this.markets = markets;
	}
	public SearchIntegerElement getStatuses() {
		return statuses;
	}
	public void setStatuses(SearchIntegerElement statuses) {
		this.statuses = statuses;
	}
	public SearchIntegerElement getSubStatuses() {
		return subStatuses;
	}
	public void setSubStatuses(SearchIntegerElement subStatuses) {
		this.subStatuses = subStatuses;
	}
	public SearchIntegerElement getServiceProIds() {
		return serviceProIds;
	}
	public void setServiceProIds(SearchIntegerElement serviceProIds) {
		this.serviceProIds = serviceProIds;
	}
	public Appointment getAppointment() {
		return appointment;
	}
	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

	
}
