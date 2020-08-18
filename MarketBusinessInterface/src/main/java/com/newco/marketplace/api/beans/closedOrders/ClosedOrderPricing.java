package com.newco.marketplace.api.beans.closedOrders;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing pricing information.
 * @author Infosys
 *
 */
@XStreamAlias("pricing")
@XmlAccessorType(XmlAccessType.FIELD)
public class ClosedOrderPricing {
	
	@XStreamAlias("priceModel")
	private String priceModel;
	
	@XStreamAlias("laborSpendLimit")
	private String laborSpendLimit;
	
	@XStreamAlias("partsSpendLimit")
	private String partsSpendLimit;
	
	@XStreamAlias("finalPriceForLabor")
	private String finalPriceForLabor;
	
	@XStreamAlias("finalPriceForParts")
	private String finalPriceForParts;

	public String getPriceModel() {
		return priceModel;
	}

	public void setPriceModel(String priceModel) {
		this.priceModel = priceModel;
	}

	public String getLaborSpendLimit() {
		return laborSpendLimit;
	}

	public void setLaborSpendLimit(String laborSpendLimit) {
		this.laborSpendLimit = laborSpendLimit;
	}

	public String getPartsSpendLimit() {
		return partsSpendLimit;
	}

	public void setPartsSpendLimit(String partsSpendLimit) {
		this.partsSpendLimit = partsSpendLimit;
	}

	public String getFinalPriceForLabor() {
		return finalPriceForLabor;
	}

	public void setFinalPriceForLabor(String finalPriceForLabor) {
		this.finalPriceForLabor = finalPriceForLabor;
	}

	public String getFinalPriceForParts() {
		return finalPriceForParts;
	}

	public void setFinalPriceForParts(String finalPriceForParts) {
		this.finalPriceForParts = finalPriceForParts;
	}

	

}
