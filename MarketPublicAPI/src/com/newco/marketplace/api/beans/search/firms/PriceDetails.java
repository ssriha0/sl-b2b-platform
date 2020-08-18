package com.newco.marketplace.api.beans.search.firms;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("priceDetails")
@XmlAccessorType(XmlAccessType.FIELD)
public class PriceDetails {
	
	@XStreamAlias("zip")
	private String zip;
	
	@XStreamAlias("price")
	private String price;

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	

	
}
