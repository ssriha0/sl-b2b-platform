package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Price")
public class Price {
	
	@XStreamAlias("MaterialPrice")
	private Double materialPrice;

	@XStreamAlias("LaborPrice")
	private Double laborPrice;

	public Double getMaterialPrice() {
		return materialPrice;
	}

	public void setMaterialPrice(Double materialPrice) {
		this.materialPrice = materialPrice;
	}

	public Double getLaborPrice() {
		return laborPrice;
	}

	public void setLaborPrice(Double laborPrice) {
		this.laborPrice = laborPrice;
	}


	
}
