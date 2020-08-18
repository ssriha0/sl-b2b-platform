package com.newco.marketplace.beans.d2c.d2cproviderportal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.beans.Results;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XmlRootElement(name = "d2CGetFirmDetailsResponse")
@XStreamAlias("d2CGetFirmDetailsResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class D2CGetFirmDetailsResponse {

	@XStreamAlias("results")
	private Results results;
	
	@XStreamAlias("firms")
	private D2CFirms firms;
	
	@XStreamAlias("buyerRetailPrice")
	private Double buyerRetailPrice;
	
	@XStreamAlias("corelationId")
	private String corelationId;
	
	public Results getResults() {
		return results;
	}

	public void setResults(Results results) {
		this.results = results;
	}

	public D2CFirms getFirms() {
		return firms;
	}

	public void setFirms(D2CFirms firms) {
		this.firms = firms;
	}

	public Double getBuyerRetailPrice() {
		return buyerRetailPrice;
	}

	public void setBuyerRetailPrice(Double buyerRetailPrice) {
		this.buyerRetailPrice = buyerRetailPrice;
	}
	
	public String getCorelationId() {
		return corelationId;
	}

	public void setCorelationId(String corelationId) {
		this.corelationId = corelationId;
	}
}
