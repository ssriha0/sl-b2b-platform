package com.servicelive.orderfulfillment.vo;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a bean class for storing request information for the In Home Out
 * bound Notification Service
 * 
 * @author Infosys
 */
@XStreamAlias("jobCodeData")
public class JobCodeData {
	
	/**
	 * constructor to set the default values
	 */
	public JobCodeData(){
		this.jobChargeCd = InHomeNPSConstants.Y;
		this.jobCode = InHomeNPSConstants.DEFAULT_JOB_CODE;
		this.jobCodePrimaryFl = InHomeNPSConstants.Y;
		this.jobRelatedFl = InHomeNPSConstants.Y;
	}
	
	@XStreamAlias("jobCalcPrice")
	private String jobCalcPrice;
	
	@XStreamAlias("jobChargeCd")
	private String jobChargeCd;
	
	@XStreamAlias("jobCode")
	private String jobCode;
	
	@XStreamAlias("jobCoverageCd")
	private String jobCoverageCd;

	@XStreamAlias("jobCodePrimaryFl")
	private String jobCodePrimaryFl;

	@XStreamAlias("jobRelatedFl")
	private String jobRelatedFl;

	public String getJobCalcPrice() {
		return jobCalcPrice;
	}

	public void setJobCalcPrice(String jobCalcPrice) {
		this.jobCalcPrice = jobCalcPrice;
	}

	public String getJobChargeCd() {
		return jobChargeCd;
	}

	public void setJobChargeCd(String jobChargeCd) {
		this.jobChargeCd = jobChargeCd;
	}

	public String getJobCode() {
		return jobCode;
	}

	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}

	public String getJobCoverageCd() {
		return jobCoverageCd;
	}

	public void setJobCoverageCd(String jobCoverageCd) {
		this.jobCoverageCd = jobCoverageCd;
	}

	public String getJobCodePrimaryFl() {
		return jobCodePrimaryFl;
	}

	public void setJobCodePrimaryFl(String jobCodePrimaryFl) {
		this.jobCodePrimaryFl = jobCodePrimaryFl;
	}

	public String getJobRelatedFl() {
		return jobRelatedFl;
	}

	public void setJobRelatedFl(String jobRelatedFl) {
		this.jobRelatedFl = jobRelatedFl;
	}

	
	
}
