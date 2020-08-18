package com.newco.marketplace.business.businessImpl.providerSearch.rating;

import com.newco.marketplace.dto.vo.providerSearch.RatingParameterBean;

/**
 * @author zizrale
 *
 */
public class StarParameterBean extends RatingParameterBean{
/**
	 * 
	 */
	private static final long serialVersionUID = -3718200495248754956L;
	private Double numberOfStars;
	private boolean includeNonRated;
	

	public StarParameterBean() { 
		includeNonRated = false;
	}
	public Double getNumberOfStars(){
		return numberOfStars;
	}
	
	public void setNumberOfStars(Double numOfStars){
		this.numberOfStars = numOfStars;
	}

	/**
	 * @return the includeNonRated
	 */
	public boolean isIncludeNonRated() {
		return includeNonRated;
	}

	/**
	 * @param includeNonRated the includeNonRated to set
	 */
	public void setIncludeNonRated(boolean includeNonRated) {
		this.includeNonRated = includeNonRated;
	}
	

}
