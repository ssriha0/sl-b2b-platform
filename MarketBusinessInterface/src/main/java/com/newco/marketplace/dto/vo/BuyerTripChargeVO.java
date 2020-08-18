package com.newco.marketplace.dto.vo;

import com.newco.marketplace.webservices.base.CommonVO;

public class BuyerTripChargeVO 
	extends CommonVO 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4077237643679932018L;
	private Integer buyerID;
	private Integer mainCategoryID;
	/**
	 * @return the buyerID
	 */
	public Integer getBuyerID() {
		return buyerID;
	}
	/**
	 * @param buyerID the buyerID to set
	 */
	public void setBuyerID(Integer buyerID) {
		this.buyerID = buyerID;
	}
	/**
	 * @return the mainCategoryID
	 */
	public Integer getMainCategoryID() {
		return mainCategoryID;
	}
	/**
	 * @param mainCategoryID the mainCategoryID to set
	 */
	public void setMainCategoryID(Integer mainCategoryID) {
		this.mainCategoryID = mainCategoryID;
	}
}
