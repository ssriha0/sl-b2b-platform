package com.newco.marketplace.api.beans.wallet;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.common.UserIdentificationRequest;
import com.newco.marketplace.api.common.Identification;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XSD(name = "addFundsToWalletRequest.xsd", path = "/resources/schemas/wallet/")
@XStreamAlias("addFundsToWalletRequest")
public class AddFundsToWalletRequest extends UserIdentificationRequest {

	
	@XStreamAlias("amount")
	Double amount;

	@XStreamAlias("cvv")
	String cvv;
	
	@XStreamAlias("accountType")
	@XStreamAsAttribute()
	String accountType;
	
	@XStreamAlias("promotion")
	  private Promotion promotion;

	  public Promotion getPromotion()
	  {
	    return this.promotion;
	  }

	  public void setPromotion(Promotion promotion) {
	    this.promotion = promotion;
	  }
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

}
