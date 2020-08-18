package com.newco.marketplace.dto.vo.serviceorder;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.sears.os.vo.request.ABaseServiceRequestVO;
public class ProblemResolutionSoVO extends ABaseServiceRequestVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 657733087656275238L;
	private String soId = "";
	private int wfStateId = 0;
	private int subStatusId = 0;
	private String subStatusDesc = "";
	private int lastStateId = 0;
	private String pbComment = "";
	private String modifiedBy = "";
	
	public String getModifiedBy() {
		return modifiedBy;
	}

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public int getWfStateId() {
		return wfStateId;
	}

	public void setWfStateId(int wfStateId) {
		this.wfStateId = wfStateId;
	}

	public int getSubStatusId() {
		return subStatusId;
	}

	public void setSubStatusId(int subStatusId) {
		this.subStatusId = subStatusId;
	}
	
	public int getLastStateId() {
		return lastStateId;
	}

	public void setLastStateId(int lastStateId) {
		this.lastStateId = lastStateId;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getSubStatusDesc() {
		return subStatusDesc;
	}

	public void setSubStatusDesc(String subStatusDesc) {
		this.subStatusDesc = subStatusDesc;
	}
	
	public String getPbComment() {
		return pbComment;
	}

	public void setPbComment(String pbComment) {
		this.pbComment = pbComment;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
			.append("soId", getSoId())
			.append("wfStateId", getWfStateId())
			.append("subStatusId", getSubStatusId())
			.append("subStatusDesc", getSubStatusDesc())
			.append("lastStateId", getLastStateId())			
			.append("pbComment", getPbComment())
			.append("modifiedBy", getModifiedBy())
			.toString();
	}
}
