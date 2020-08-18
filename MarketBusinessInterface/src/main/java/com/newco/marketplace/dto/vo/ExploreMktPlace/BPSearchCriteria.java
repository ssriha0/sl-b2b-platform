package com.newco.marketplace.dto.vo.ExploreMktPlace;

import com.newco.marketplace.criteria.ICriteria;

public class BPSearchCriteria implements ICriteria {

	private static final long serialVersionUID = -3358586174442579757L;
	private String flName;
	private int userId = -1;
	
	public String getFlName() {
		return flName;
	}
	
	public void setFlName(String flName) {
		this.flName = flName;
	}
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void reset() {

	}
	
	public boolean isSet() {
		return false;
	}

}
