package com.servicelive.esb.integration.domain;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

public class OmsTask implements Serializable {

	/** generated serialVersionUID */
	private static final long serialVersionUID = 3957918282647505753L;

	private long omsTaskId;
	private Long taskId;
	private String chargeCode;
	private String coverage;
	private String type;
	private String description;
	
	
	public OmsTask() { }

	public OmsTask(long omsTaskId, Long taskId, String chargeCode,
			String coverage, String type, String description) {
		super();
		this.setOmsTaskId(omsTaskId);
		this.taskId = taskId;
		this.chargeCode = chargeCode;
		this.coverage = coverage;
		this.type = type;
		this.description = description;
	}

	public String getChargeCode() {
		return chargeCode;
	}

	public String getCoverage() {
		return coverage;
	}

	public String getDescription() {
		return description;
	}

	public long getOmsTaskId() {
		return omsTaskId;
	}

	public Long getTaskId() {
		return taskId;
	}

	public String getType() {
		return type;
	}

	public void setChargeCode(String chargeCode) {
		this.chargeCode = chargeCode;
	}

	public void setCoverage(String coverage) {
		this.coverage = coverage;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setOmsTaskId(long omsTaskId) {
		this.omsTaskId = omsTaskId;
	}
	
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
