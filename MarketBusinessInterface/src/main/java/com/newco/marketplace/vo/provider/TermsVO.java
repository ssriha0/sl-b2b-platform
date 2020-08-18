package com.newco.marketplace.vo.provider;

import com.sears.os.vo.SerializableBaseVO;

public class TermsVO extends SerializableBaseVO{
	
	
	private static final long serialVersionUID = 1L;
	int acceptTerms;
	int vendorId;
	String resourceId; 
	String termsContent;

	
	/**
	 * 
	 * @return termsContent
	 */
	public String getTermsContent() {
		return termsContent;
	}
	/**
	 * 
	 * @param termsContent
	 */
	public void setTermsContent(String termsContent) {
		this.termsContent = termsContent;
	}

	/**
	 * @return the acceptTerms
	 */
	public int getAcceptTerms() {
		return acceptTerms;
	}

	/**
	 * @param acceptTerms the acceptTerms to set
	 */
	public void setAcceptTerms(int acceptTerms) {
		this.acceptTerms = acceptTerms;
	}

	/**
	 * @return the vendorId
	 */
	public int getVendorId() {
		return vendorId;
	}

	/**
	 * @param vendorId the vendorId to set
	 */
	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	
}