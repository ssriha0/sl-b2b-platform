package com.newco.marketplace.api.mobile.beans.sodetails;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This is a generic bean class for storing documents information.
 * @author Infosys
 *
 */

@XStreamAlias("signatures")
@XmlAccessorType(XmlAccessType.FIELD)
public class SignatureDocuments {
	
	@XStreamImplicit(itemFieldName="signature")
	private List<SignatureDocument> signature;
	
	@XStreamAlias("providerSignatureStatus")
	private String providerSignatureStatus;
	
	@XStreamAlias("customerSignatureStatus")
	private String customerSignatureStatus;
	
	

	public String getProviderSignatureStatus() {
		return providerSignatureStatus;
	}

	public void setProviderSignatureStatus(String providerSignatureStatus) {
		this.providerSignatureStatus = providerSignatureStatus;
	}

	public String getCustomerSignatureStatus() {
		return customerSignatureStatus;
	}

	public void setCustomerSignatureStatus(String customerSignatureStatus) {
		this.customerSignatureStatus = customerSignatureStatus;
	}

	public List<SignatureDocument> getSignature() {
		return signature;
	}

	public void setSignature(List<SignatureDocument> signature) {
		this.signature = signature;
	}
	
}
