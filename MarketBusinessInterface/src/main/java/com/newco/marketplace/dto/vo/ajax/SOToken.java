package com.newco.marketplace.dto.vo.ajax;

import com.sears.os.vo.SerializableBaseVO;

public class SOToken extends SerializableBaseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2078364033577107525L;
	private String soId;
	private Integer rejectedCount;
	private Integer routedCount;
	private Integer condAcceptCount;
	private double spendLimit;
	private boolean hasChanged;
	private boolean exists;
	private String changeType;
	private Integer routedTabCount;
	
	
	
	public Integer getRoutedTabCount() {
		return routedTabCount;
	}
	public void setRoutedTabCount(Integer routedTabCount) {
		this.routedTabCount = routedTabCount;
	}
	public Integer getRejectedCount() {
		return rejectedCount;
	}
	public void setRejectedCount(Integer rejectedCount) {
		this.rejectedCount = rejectedCount;
	}
	public String getChangeType() {
		return changeType;
	}
	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}
	public Integer getCondAcceptCount() {
		return condAcceptCount;
	}
	public void setCondAcceptCount(Integer condAcceptCount) {
		this.condAcceptCount = condAcceptCount;
	}
	public boolean getExists() {
		return exists;
	}
	public void setExists(boolean exists) {
		this.exists = exists;
	}
	public boolean getHasChanged() {
		return hasChanged;
	}
	public void setHasChanged(boolean hasChanged) {
		this.hasChanged = hasChanged;
	}

	public Integer getRoutedCount() {
		return routedCount;
	}
	public void setRoutedCount(Integer routedCount) {
		this.routedCount = routedCount;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public double getSpendLimit() {
		return spendLimit;
	}
	public void setSpendLimit(double spendLimit) {
		this.spendLimit = spendLimit;
	}
	
	
	
	

}
