package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;

import java.math.BigDecimal;
import java.util.Date;

import com.newco.marketplace.api.annotation.OptionalParam;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("Insurance")
public class PostInsurance {

	@XStreamAlias("Name")
	@XStreamAsAttribute()
	private String name;

	@XStreamAlias("Verified")
	@XStreamAsAttribute()
	private Boolean verified;

	@XStreamAlias("Amount")
	private Integer amount;

	@OptionalParam
	@XStreamAlias("VerificationDate")
	private Date verificationDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public Date getVerificationDate() {
		return verificationDate;
	}

	public void setVerificationDate(Date verificationDate) {
		this.verificationDate = verificationDate;
	}

	public Boolean getVerified() {
		return verified;
	}

	public void setVerified(Boolean verified) {
		this.verified = verified;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}


	

}
