package com.newco.marketplace.api.beans;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("jobCode")
public class JobCode {

	@XStreamAlias("jobCodeId")
	private String jobCodeId;

	@XStreamAlias("sequence")
	private String sequence;

	@XStreamAlias("relatedFlag")
	private String relatedFlag;

	@XStreamAlias("chargeAmount")
	private String chargeAmount;

	@XStreamAlias("coverageCode")
	private String coverageCode;

	public String getJobCodeId() {
		return jobCodeId;
	}

	public void setJobCodeId(String jobCodeId) {
		this.jobCodeId = jobCodeId;
	}

	public String getRelatedFlag() {
		return relatedFlag;
	}

	public void setRelatedFlag(String relatedFlag) {
		this.relatedFlag = relatedFlag;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
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
