package com.newco.marketplace.api.mobile.beans.companyProfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("insurancePoliciesOnFile")
@XmlAccessorType(XmlAccessType.FIELD)
public class InsurancePoliciesOnFile {

	@XStreamAlias("insurancePoliciesInd")
	private Boolean insurancePoliciesInd;
	
	@XStreamAlias("policyDetails")
	private PolicyDetails policyDetails;
	
	
	public PolicyDetails getPolicyDetails() {
		return policyDetails;
	}
	public void setPolicyDetails(PolicyDetails policyDetails) {
		this.policyDetails = policyDetails;
	}
	public Boolean getInsurancePoliciesInd() {
		return insurancePoliciesInd;
	}
	public void setInsurancePoliciesInd(Boolean insurancePoliciesInd) {
		this.insurancePoliciesInd = insurancePoliciesInd;
	}
	
	
}
