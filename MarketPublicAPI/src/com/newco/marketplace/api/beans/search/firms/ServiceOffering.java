package com.newco.marketplace.api.beans.search.firms;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("serviceOffering")
@XmlAccessorType(XmlAccessType.FIELD)
public class ServiceOffering {
	
	@XStreamAlias("sku")
	private String sku;
	
	@XStreamAlias("priceList")
	private PriceList priceList;
	
	@XStreamAlias("availableTimeSlotsList")
	private AvailableTimeSlotsList availableTimeSlotsList;

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public PriceList getPriceList() {
		return priceList;
	}

	public void setPriceList(PriceList priceList) {
		this.priceList = priceList;
	}

	public AvailableTimeSlotsList getAvailableTimeSlotsList() {
		return availableTimeSlotsList;
	}

	public void setAvailableTimeSlotsList(
			AvailableTimeSlotsList availableTimeSlotsList) {
		this.availableTimeSlotsList = availableTimeSlotsList;
	}
	

	
}
