package com.newco.marketplace.dto.vo.providerSearch;

import com.sears.os.vo.SerializableBaseVO;


public class ProviderStarResultsVO extends SerializableBaseVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4358353062514826921L;
	private int providerResourceId;
	private double providerStarRating;
	
	
	public int getProviderResourceId() {
		return providerResourceId;
	}
	public void setProviderResourceId(int providerResourceId) {
		this.providerResourceId = providerResourceId;
	}
	public double getProviderStarRating() {
		return providerStarRating;
	}
	public void setProviderStarRating(double providerStarRating) {
		this.providerStarRating = providerStarRating;
	}
}
