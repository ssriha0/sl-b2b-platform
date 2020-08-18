/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 08-SEP-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.beans.search.providerProfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.newco.marketplace.api.annotation.OptionalParam;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * This is a bean class for storing all information of 
 * the Insurance Details 
 * @author Infosys
 *
 */
@XStreamAlias("insurance")
@XmlAccessorType(XmlAccessType.FIELD)
public class CompanyInsurance {
	@XStreamAlias("name")
	@XStreamAsAttribute()
	private String name;
	
	@XStreamAlias("amount")
	private Integer amount;
	
	@OptionalParam
	@XStreamAlias("verificationDate")
	private String  verificationDate;
	
	
	@XStreamAlias("verified")
	@XStreamAsAttribute()  
	private Boolean verified;
	
	
	public CompanyInsurance() {
		
	} 
	
	public CompanyInsurance(String name, boolean  verified, String date, Integer amount) {
		this.name = name;
		this.verified = verified;
		this.verificationDate = date;
		this.amount = amount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getVerificationDate() {
		return verificationDate;
	}

	public void setVerificationDate(String verificationDate) {
		this.verificationDate = verificationDate;
	}

	public Boolean getVerified() {
		return verified;
	}

	public void setVerified(Boolean verified) {
		this.verified = verified;
	}
}
