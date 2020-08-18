package com.newco.marketplace.dto.vo.ajax;

import com.sears.os.vo.SerializableBaseVO;


public class AjaxDetailedCountsVO extends SerializableBaseVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1988389874544965212L;
	private Integer providerCount = -1;
	private Integer condAcceptCount = -1;
	private Integer rejectCount =-1;
	private String spendLimit = "";
	private String soId = "";
	
	
	public Integer getCondAcceptCount() {
		return condAcceptCount;
	}
	public void setCondAcceptCount(Integer condAcceptCount) {
		this.condAcceptCount = condAcceptCount;
	}
	public Integer getProviderCount() {
		return providerCount;
	}
	public void setProviderCount(Integer providerCount) {
		this.providerCount = providerCount;
	}
	public Integer getRejectCount() {
		return rejectCount;
	}
	public void setRejectCount(Integer rejectCount) {
		this.rejectCount = rejectCount;
	}
	public String getSpendLimit() {
		return spendLimit;
	}
	public void setSpendLimit(String spendLimit) {
		this.spendLimit = spendLimit;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	
	
	
}