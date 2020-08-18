package com.newco.marketplace.business.businessImpl.providerSearch.rating;

import com.newco.marketplace.dto.vo.providerSearch.RatingParameterBean;

public class BackgroundCheckParameterBean extends RatingParameterBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5135928506399236241L;
	private Integer backgroundCheck;

	public Integer getBackgroundCheck() {
		return backgroundCheck;
	}

	public void setBackgroundCheck(Integer backgroundCheck) {
		this.backgroundCheck = backgroundCheck;
	}

}
