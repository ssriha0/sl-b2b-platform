package com.newco.marketplace.api.beans.so.complete;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("completeSoRequest")
public class CompleteSORequest {

	@XStreamAlias("resolutionDesc")
	private String resolutionDesc;
	
	@XStreamAlias("partsFinalPrice")
	private String partsFinalPrice;
	
	@XStreamAlias("laborFinalPrice")
	private String laborFinalPrice;
	
	@XStreamAlias("parts")
	private Parts parts;
	
	@XStreamAlias("buyerRefs")
	private BuyerRefs buyerRefs;
	

	public String getPartsFinalPrice() {
		return partsFinalPrice;
	}

	public String getLaborFinalPrice() {
		return laborFinalPrice;
	}

	public Parts getParts() {
		return parts;
	}

	public BuyerRefs getBuyerRefs() {
		return buyerRefs;
	}

	

	public void setPartsFinalPrice(String partsFinalPrice) {
		this.partsFinalPrice = partsFinalPrice;
	}

	public void setLaborFinalPrice(String laborFinalPrice) {
		this.laborFinalPrice = laborFinalPrice;
	}

	public void setParts(Parts parts) {
		this.parts = parts;
	}

	public void setBuyerRefs(BuyerRefs buyerRefs) {
		this.buyerRefs = buyerRefs;
	}

	public String getResolutionDesc() {
		return resolutionDesc;
	}

	public void setResolutionDesc(String resolutionDesc) {
		this.resolutionDesc = resolutionDesc;
	}
	
}
