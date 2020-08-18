package com.newco.marketplace.dto.vo.serviceorder;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class JobCodeVO {

	private String jobCodeId;
	private String sequence;
	private String relatedFlag;
	private String chargeAmount;
	private String coverageCode;
	public String getJobCodeId() {
		return jobCodeId;
	}
	public void setJobCodeId(String jobCodeId) {
		this.jobCodeId = jobCodeId;
	}
	public String getSequence() {
		return sequence;
	}
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	public String getRelatedFlag() {
		return relatedFlag;
	}
	public void setRelatedFlag(String relatedFlag) {
		this.relatedFlag = relatedFlag;
	}
	public String getChargeAmount() {
		return chargeAmount;
	}
	public void setChargeAmount(String chargeAmount) {
		this.chargeAmount = chargeAmount;
	}
	public String getCoverageCode() {
		return coverageCode;
	}
	public void setCoverageCode(String coverageCode) {
		this.coverageCode = coverageCode;
	}
	
	
}
