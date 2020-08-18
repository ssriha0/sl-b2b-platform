package com.newco.marketplace.api.mobile.beans.companyProfile;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("policyDetails")
@XmlAccessorType(XmlAccessType.FIELD)
public class PolicyDetails {

	@XStreamImplicit(itemFieldName="policyDetail")
	private	List<PolicyDetail> policyDetail;

	public List<PolicyDetail> getPolicyDetail() {
		return policyDetail;
	}

	public void setPolicyDetail(List<PolicyDetail> policyDetail) {
		this.policyDetail = policyDetail;
	}
}
