package com.newco.marketplace.api.beans.firmDetail;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("polAndProc")
@XmlAccessorType(XmlAccessType.FIELD)
public class PolAndProc {

	@XStreamAlias("policyType")
	private String policyType;
	
	@XStreamAlias("policyValue")
	private String policyValue;
	
	@XStreamAlias("policyPlanType")
	private String policyPlanType;
	
	@XStreamAlias("policyPlanValue")
	private String policyPlanValue;

	public PolAndProc() {
		this.policyValue = "N/A";
		this.policyPlanValue = "N/A";
	}
	public String getPolicyType() {
		return policyType;
	}

	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}

	public String getPolicyValue() {
		return policyValue;
	}

	public void setPolicyValue(String policyValue) {
		this.policyValue = policyValue;
	}

	public String getPolicyPlanType() {
		return policyPlanType;
	}

	public void setPolicyPlanType(String policyPlanType) {
		this.policyPlanType = policyPlanType;
	}

	public String getPolicyPlanValue() {
		return policyPlanValue;
	}

	public void setPolicyPlanValue(String policyPlanValue) {
		this.policyPlanValue = policyPlanValue;
	}
	
	
	
}
