package com.servicelive.esb.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("JobCode")
public class NPSJobCode {

	@XStreamAlias("SequenceNumber")
	private String sequenceNumber;

	@XStreamAlias("Number")
	private String Number;

	@XStreamAlias("ChargeCode")
	private String chargeCode;

	@XStreamAlias("Type")
	private String type;

	@XStreamAlias("Coverage")
	private String coverage;

	@XStreamAlias("Description")
	private String description;

	@XStreamAlias("Amount")
	private String amount;

	@XStreamAlias("Status")
	private String status;

	public String getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public String getNumber() {
		return Number;
	}

	public void setNumber(String number) {
		Number = number;
	}

	public String getChargeCode() {
		return chargeCode;
	}

	public void setChargeCode(String chargeCode) {
		this.chargeCode = chargeCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
