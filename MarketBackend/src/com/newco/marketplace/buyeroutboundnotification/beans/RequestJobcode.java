package com.newco.marketplace.buyeroutboundnotification.beans;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("JOBCODE")
public class RequestJobcode {
	
	@XStreamAlias("jobCodeSeqNo")
	private String jobCodeSeqNo;
	
	@XStreamAlias("jobCode")
	private String jobCode;
	
	@XStreamAlias("chargeCode")
	private String chargeCode;
	
	@XStreamAlias("coverageCode")
	private String coverageCode;
	
	@XStreamAlias("nonRelatedChargeCode")
	private String nonRelatedChargeCode;
	
	@XStreamAlias("jobCodeDescription")
	private String jobCodeDescription;
	
	@XStreamAlias("jobCodePrice")
	private String jobCodePrice;
	
	@XStreamAlias("jobCodeStatus")
	private String jobCodeStatus;

	public String getJobCodeSeqNo() {
		return jobCodeSeqNo;
	}

	public void setJobCodeSeqNo(String jobCodeSeqNo) {
		this.jobCodeSeqNo = jobCodeSeqNo;
	}

	public String getJobCode() {
		return jobCode;
	}

	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}

	public String getChargeCode() {
		return chargeCode;
	}

	public void setChargeCode(String chargeCode) {
		this.chargeCode = chargeCode;
	}

	public String getCoverageCode() {
		return coverageCode;
	}

	public void setCoverageCode(String coverageCode) {
		this.coverageCode = coverageCode;
	}

	public String getNonRelatedChargeCode() {
		return nonRelatedChargeCode;
	}

	public void setNonRelatedChargeCode(String nonRelatedChargeCode) {
		this.nonRelatedChargeCode = nonRelatedChargeCode;
	}

	public String getJobCodeDescription() {
		return jobCodeDescription;
	}

	public void setJobCodeDescription(String jobCodeDescription) {
		this.jobCodeDescription = jobCodeDescription;
	}

	public String getJobCodePrice() {
		return jobCodePrice;
	}

	public void setJobCodePrice(String jobCodePrice) {
		this.jobCodePrice = jobCodePrice;
	}

	public String getJobCodeStatus() {
		return jobCodeStatus;
	}

	public void setJobCodeStatus(String jobCodeStatus) {
		this.jobCodeStatus = jobCodeStatus;
	}
}
