package com.servicelive.esb.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Monetary")
public class NPSMonetary {

	@XStreamAlias("AmountCollected")
	private String amountCollected;

	@XStreamAlias("PrimaryAmountCollected")
	private String primaryAmountCollected;

	@XStreamAlias("SecondaryAmountCollected")
	private String secondaryAmountCollected;

	public String getAmountCollected() {
		return amountCollected;
	}

	public void setAmountCollected(String amountCollected) {
		this.amountCollected = amountCollected;
	}

	public String getPrimaryAmountCollected() {
		return primaryAmountCollected;
	}

	public void setPrimaryAmountCollected(String primaryAmountCollected) {
		this.primaryAmountCollected = primaryAmountCollected;
	}

	public String getSecondaryAmountCollected() {
		return secondaryAmountCollected;
	}

	public void setSecondaryAmountCollected(String secondaryAmountCollected) {
		this.secondaryAmountCollected = secondaryAmountCollected;
	}

}
