package com.servicelive.esb.dto;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("JobCode")
public class JobCode implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@XStreamAlias("Amount")
	private String amount;
	
	@XStreamAlias("ChargeCode")
	private String chargeCode;
	
	@XStreamAlias("Coverage")
	private String coverage;
	
	@XStreamAlias("Description")
	private String description;
	
	@XStreamAlias("Number")
	private String number;
	
	@XStreamAlias("SequenceNumber")
    private String sequenceNumber;
	
	@XStreamAlias("Type")
    private String type;

	private JobCodeInfo jobCodeInfo;
	
    public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getChargeCode() {
		return chargeCode;
	}

	public void setChargeCode(String chargeCode) {
		this.chargeCode = chargeCode;
	}

	public String getCoverage() {
		return coverage;
	}

	public void setCoverage(String coverage) {
		this.coverage = coverage;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setJobCodeInfo(JobCodeInfo jobCodeInfo) {
		this.jobCodeInfo = jobCodeInfo;
		
	}

	public JobCodeInfo getJobCodeInfo() {
		return jobCodeInfo;
	}
	public String toString() {
		return "\r\n" + ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
