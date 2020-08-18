package com.newco.marketplace.web.dto.provider;


public class TermsDto extends BaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -210796776975258274L;
	int acceptTerms;
	int vendorId;
	String resourceId;
	String termsContent;
	String action;
	
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getTermsContent() {
		return termsContent;
	}

	public void setTermsContent(String termsContent) {
		this.termsContent = termsContent;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
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
	
}
