package com.newco.marketplace.web.dto.provider;

import com.newco.marketplace.web.dto.SerializedBaseDTO;

public class TermsAndCondDto extends SerializedBaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1036014268633058834L;
	private Boolean acceptTerms = Boolean.FALSE;
	private Boolean acceptBucksTerms = Boolean.FALSE;
	//SLT-2236
	private Boolean acceptNewBucksTerms = Boolean.FALSE;
	private int acceptNewBucksTermsInd ;
	private int acceptTermsInd ;
	private int acceptBucksTermsInd ;
	int vendorId;
	int id;
	String termsContent;
    String slBucksText;

	public String getSlBucksText() {
		return slBucksText;
	}

	public void setSlBucksText(String slBucksText) {
		this.slBucksText = slBucksText;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTermsContent() {
		return termsContent;
	}

	public void setTermsContent(String termsContent) {
		this.termsContent = termsContent;
	}

	/**
	 * @return the acceptTerms
	 */
	public Boolean getAcceptTerms() {
		return acceptTerms;
	}

	/**
	 * @param acceptTerms the acceptTerms to set
	 */
	public void setAcceptTerms(Boolean acceptTerms) {
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

	public Boolean getAcceptBucksTerms() {
		return acceptBucksTerms;
	}

	public void setAcceptBucksTerms(Boolean acceptBucksTerms) {
		this.acceptBucksTerms = acceptBucksTerms;
	}

	public int getAcceptTermsInd() {
		return acceptTermsInd;
	}

	public void setAcceptTermsInd(int acceptTermsInd) {
		this.acceptTermsInd = acceptTermsInd;
	}

	public int getAcceptBucksTermsInd() {
		return acceptBucksTermsInd;
	}

	public void setAcceptBucksTermsInd(int acceptBucksTermsInd) {
		this.acceptBucksTermsInd = acceptBucksTermsInd;
	}

	public Boolean getAcceptNewBucksTerms() {
		return acceptNewBucksTerms;
	}

	public void setAcceptNewBucksTerms(Boolean acceptNewBucksTerms) {
		this.acceptNewBucksTerms = acceptNewBucksTerms;
	}

	public int getAcceptNewBucksTermsInd() {
		return acceptNewBucksTermsInd;
	}

	public void setAcceptNewBucksTermsInd(int acceptNewBucksTermsInd) {
		this.acceptNewBucksTermsInd = acceptNewBucksTermsInd;
	}
	
}
