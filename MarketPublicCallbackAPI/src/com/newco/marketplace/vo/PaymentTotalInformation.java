package com.newco.marketplace.vo;

public class PaymentTotalInformation {
	String primaryPaymentMethod;
	String primaryAmountCollected;

	public String getPrimaryPaymentMethod() {
		return primaryPaymentMethod;
	}

	public void setPrimaryPaymentMethod(String primaryPaymentMethod) {
		this.primaryPaymentMethod = primaryPaymentMethod;
	}

	public String getPrimaryAmountCollected() {
		return primaryAmountCollected;
	}

	public void setPrimaryAmountCollected(String primaryAmountCollected) {
		this.primaryAmountCollected = primaryAmountCollected;
	}

}
