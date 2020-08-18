package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamOmitField;


@XStreamAlias("Partner")
public class FirmIdPrice {
	
	@XStreamAlias("Price")
	@XStreamAsAttribute()
	private String price;

	private String id;
	
	@XStreamOmitField
	private String slFirmId;

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSlFirmId() {
		return slFirmId;
	}

	public void setSlFirmId(String slFirmId) {
		this.slFirmId = slFirmId;
	}


}
