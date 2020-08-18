package com.newco.marketplace.api.beans.so.complete;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("buyerRefs")
public class BuyerRefs {

	@XStreamImplicit(itemFieldName="buyerRef")
	private List<BuyerRef> buyerRef;

	public List<BuyerRef> getBuyerRef() {
		return buyerRef;
	}

	public void setBuyerRef(List<BuyerRef> buyerRef) {
		this.buyerRef = buyerRef;
	}
	
	
	
}
