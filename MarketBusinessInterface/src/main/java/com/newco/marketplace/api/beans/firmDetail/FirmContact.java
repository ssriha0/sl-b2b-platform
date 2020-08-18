package com.newco.marketplace.api.beans.firmDetail;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("contact")
@XmlAccessorType(XmlAccessType.FIELD)
public class FirmContact {
			
	@XStreamAlias("email")
	private String email;
	
	@XStreamAlias("web")
	private String web;
	
	@XStreamAlias("altEmail")
	private String altEmail;
	
	@XStreamAlias("businessPhone")
	private String businessPhone;
	
	@XStreamAlias("businessPhoneExt")
	private String businessPhoneExt;
	
	@XStreamAlias("businessFax")
	private String businessFax;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	public String getBusinessPhoneExt() {
		return businessPhoneExt;
	}

	public void setBusinessPhoneExt(String businessPhoneExt) {
		this.businessPhoneExt = businessPhoneExt;
	}

	public String getBusinessFax() {
		return businessFax;
	}

	public void setBusinessFax(String businessFax) {
		this.businessFax = businessFax;
	}

	public String getAltEmail() {
		return altEmail;
	}

	public void setAltEmail(String altEmail) {
		this.altEmail = altEmail;
	}

	public String getBusinessPhone() {
		return businessPhone;
	}

	public void setBusinessPhone(String businessPhone) {
		this.businessPhone = businessPhone;
	}
	
	
}
