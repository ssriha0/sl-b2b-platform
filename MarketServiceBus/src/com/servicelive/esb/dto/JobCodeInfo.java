package com.servicelive.esb.dto;

import java.io.Serializable;


public class JobCodeInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String jobCode;
	private String specCode;
	private float marginRate;
	private int leadTimeDays;
	private String jobCodeDesc;
	private String stockNumber;
	private String inclusionDesc;

	public JobCodeInfo(String jobCode, String specCode) {
		super();
		this.jobCode = jobCode;
		this.specCode = specCode;
	}

	public JobCodeInfo() {
	}

	public float getMarginRate() {
		return marginRate;
	}

	public int getLeadTimeDays() {
		return leadTimeDays;
	}

	public void setMarginRate(float rate) {
		marginRate = rate;
	}

	public void setLeadTimeDays(int leadTimeDays) {
		this.leadTimeDays = leadTimeDays;
	}

	public String getJobCode() {
		return jobCode;
	}

	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}

	public String getSpecCode() {
		return specCode;
	}

	public void setSpecCode(String specCode) {
		this.specCode = specCode;
	}

	public String getInclusionDesc() {
		return inclusionDesc;
	}

	public void setInclusionDesc(String inclusionDesc) {
		this.inclusionDesc = inclusionDesc;
	}

	public String getJobCodeDesc() {
		return jobCodeDesc;
	}

	public void setJobCodeDesc(String jobCodeDesc) {
		this.jobCodeDesc = jobCodeDesc;
	}

	public String getStockNumber() {
		return stockNumber;
	}

	public void setStockNumber(String stockNumber) {
		this.stockNumber = stockNumber;
	}
	public String toString()
	{
		StringBuilder sb = new StringBuilder("\nspecCode: "+specCode+" stockNumber: "+stockNumber);
		return sb.toString();
	}
	
	public String getPrimaryKey() {
		return jobCode + "-" + specCode;
	}

}
