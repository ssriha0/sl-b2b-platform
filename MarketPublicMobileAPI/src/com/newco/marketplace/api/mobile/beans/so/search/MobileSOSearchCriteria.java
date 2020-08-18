package com.newco.marketplace.api.mobile.beans.so.search;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("searchCriteria")
@XmlAccessorType(XmlAccessType.FIELD)
public class MobileSOSearchCriteria {
	
	/*@XStreamAlias("statuses")
	private SearchIntegerElement statuses;*/
	@XStreamAlias("custPhones")
	private SearchStringElement custPhones;
	@XStreamAlias("custNames")
	private SearchStringElement custNames;
	@XStreamAlias("soIds")
	private SearchStringElement soIds;
	/*@XStreamAlias("endDates")
	private SearchStringElement endDates;
	@XStreamAlias("startDates")
	private SearchStringElement startDates;
	@XStreamAlias("serviceProNames")
	private SearchStringElement serviceProNames;*/
	@XStreamAlias("zipCodes")
	private SearchStringElement zipCodes;
	/*@XStreamAlias("cityNames")
	private SearchStringElement cityNames;*/
	/*@XStreamAlias("resourceIds")
	private SearchIntegerElement resourceIds;*/
	
	public SearchStringElement getCustPhones() {
		return custPhones;
	}
	public void setCustPhones(SearchStringElement custPhones) {
		this.custPhones = custPhones;
	}
	public SearchStringElement getCustNames() {
		return custNames;
	}
	public void setCustNames(SearchStringElement custNames) {
		this.custNames = custNames;
	}
	public SearchStringElement getSoIds() {
		return soIds;
	}
	public void setSoIds(SearchStringElement soIds) {
		this.soIds = soIds;
	}
	/*public SearchStringElement getEndDates() {
		return endDates;
	}
	public void setEndDates(SearchStringElement endDates) {
		this.endDates = endDates;
	}
	public SearchStringElement getStartDates() {
		return startDates;
	}
	public void setStartDates(SearchStringElement startDates) {
		this.startDates = startDates;
	}
	public SearchStringElement getServiceProNames() {
		return serviceProNames;
	}
	public void setServiceProNames(SearchStringElement serviceProNames) {
		this.serviceProNames = serviceProNames;
	}*/
	public SearchStringElement getZipCodes() {
		return zipCodes;
	}
	public void setZipCodes(SearchStringElement zipCodes) {
		this.zipCodes = zipCodes;
	}
	
	/*public SearchStringElement getCityNames() {
		return cityNames;
	}
	public void setCityNames(SearchStringElement cityNames) {
		this.cityNames = cityNames;
	}*/
	/*public SearchIntegerElement getResourceIds() {
		return resourceIds;
	}
	public void setResourceIds(SearchIntegerElement resourceIds) {
		this.resourceIds = resourceIds;
	}*/
/*	public SearchIntegerElement getStatuses() {
		return statuses;
	}
	public void setStatuses(SearchIntegerElement statuses) {
		this.statuses = statuses;
	}
*/
	

	
	
}
