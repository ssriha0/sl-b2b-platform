package com.newco.marketplace.api.mobile.beans.companyProfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("policyDetail")
@XmlAccessorType(XmlAccessType.FIELD)
public class PolicyDetail {

	@XStreamAlias("policyType")
	private String	policyType;
	
	@XStreamAlias("carrierName")
	private String	carrierName;
	
	@XStreamAlias("expirationDate")
	private String	expirationDate;
	
	@XStreamAlias("documentId")
	private String	documentId;
	
	@XStreamAlias("serviceLiveVerificationStatus")
	private String	serviceLiveVerificationStatus;
	
	
	public String getPolicyType() {
		return policyType;
	}
	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}
	public String getCarrierName() {
		return carrierName;
	}
	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}
	public String getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}
	public String getDocumentId() {
		return documentId;
	}
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}
	public String getServiceLiveVerificationStatus() {
		return serviceLiveVerificationStatus;
	}
	public void setServiceLiveVerificationStatus(
			String serviceLiveVerificationStatus) {
		this.serviceLiveVerificationStatus = serviceLiveVerificationStatus;
	}
}
