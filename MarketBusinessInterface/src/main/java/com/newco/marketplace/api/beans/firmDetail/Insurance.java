package com.newco.marketplace.api.beans.firmDetail;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("insurance")
@XmlAccessorType(XmlAccessType.FIELD)
public class Insurance {

	@XStreamAlias("name")
	private String name;
	
	@XStreamAlias("verified")
	private String verified;
		
	@XStreamAlias("verificationDate")
	private String verificationDate;
	
	@XStreamAlias("amount")
	private Double amount;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVerified() {
		return verified;
	}

	public void setVerified(String verified) {
		this.verified = verified;
	}


	public String getVerificationDate() {
		return verificationDate;
	}

	public void setVerificationDate(String verificationDate) {
		this.verificationDate = verificationDate;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	
}
