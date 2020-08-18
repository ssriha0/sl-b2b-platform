package com.newco.marketplace.vo.hi.provider;

public class ReasonCodeVO {
	private Integer reasonCodeId;
	private String reasonCodeValue;
	private Boolean termsConInd = false;
	private Boolean bucksInd = false;

	public Integer getReasonCodeId() {
		return reasonCodeId;
	}

	public void setReasonCodeId(Integer reasonCodeId) {
		this.reasonCodeId = reasonCodeId;
	}

	public String getReasonCodeValue() {
		return reasonCodeValue;
	}

	public void setReasonCodeValue(String reasonCodeValue) {
		this.reasonCodeValue = reasonCodeValue;
	}

	public Boolean getTermsConInd() {
		return termsConInd;
	}

	public void setTermsConInd(Boolean termsConInd) {
		this.termsConInd = termsConInd;
	}

	public Boolean getBucksInd() {
		return bucksInd;
	}

	public void setBucksInd(Boolean bucksInd) {
		this.bucksInd = bucksInd;
	}

	
}
