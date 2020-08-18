package com.newco.marketplace.vo.provider;

import com.sears.os.vo.SerializableBaseVO;

public class TermsAndCondVO extends SerializableBaseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2945816569925596482L;
	private boolean acceptTerms;
	private boolean acceptBucksTerms;
	private int acceptTermsInd;
	private int acceptBucksTermsInd;
	int vendorId;
	int id;
	String termsContent;
	String slBucksText;
	//SLT-2236
	private boolean acceptNewBucksTerms;
	private int acceptNewBucksTermsInd;
	
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
	public boolean getAcceptTerms() {
		return acceptTerms;
	}

	/**
	 * @param acceptTerms the acceptTerms to set
	 */
	public void setAcceptTerms(boolean acceptTerms) {
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
    
	public String getSlBucksText() {
		return slBucksText;
	}

	public void setSlBucksText(String slBucksText) {
		this.slBucksText = slBucksText;
	}

	public boolean getAcceptBucksTerms() {
		return acceptBucksTerms;
	}

	public void setAcceptBucksTerms(boolean acceptBucksTerms) {
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

	public boolean isAcceptNewBucksTerms() {
		return acceptNewBucksTerms;
	}

	public void setAcceptNewBucksTerms(boolean acceptNewBucksTerms) {
		this.acceptNewBucksTerms = acceptNewBucksTerms;
	}

	public int getAcceptNewBucksTermsInd() {
		return acceptNewBucksTermsInd;
	}

	public void setAcceptNewBucksTermsInd(int acceptNewBucksTermsInd) {
		this.acceptNewBucksTermsInd = acceptNewBucksTermsInd;
	}
	
}