package com.newco.marketplace.api.beans.search.firms;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("priceList")
@XmlAccessorType(XmlAccessType.FIELD)
public class PriceList {
	
	
	@XStreamAlias("priceDetails")
	@XStreamImplicit(itemFieldName="priceDetails")
	private List<PriceDetails> priceDetails;

	public List<PriceDetails> getPriceDetails() {
		return priceDetails;
	}

	public void setPriceDetails(List<PriceDetails> priceDetails) {
		this.priceDetails = priceDetails;
	}
	
	
}
