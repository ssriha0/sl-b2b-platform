package com.newco.marketplace.dto.vo;

import com.newco.marketplace.webservices.base.CommonVO;

public class TripChargeVO 
	extends CommonVO
{

	private static final long serialVersionUID = -2811423741677543131L;
	
	private Integer buyerID;
	private Integer mainCategoryID;
	private String mainCategoryDesc;
	private Double tripCharge;
	
	public Integer getMainCategoryID() {
		return mainCategoryID;
	}
	public void setMainCategoryID(Integer mainCategoryID) {
		this.mainCategoryID = mainCategoryID;
	}
	public String getMainCategoryDesc() {
		return mainCategoryDesc;
	}
	public void setMainCategoryDesc(String mainCategoryDesc) {
		this.mainCategoryDesc = mainCategoryDesc;
	}
	public Double getTripCharge() {
		return tripCharge;
	}
	public void setTripCharge(Double tripCharge) {
		this.tripCharge = tripCharge;
	}
	public Integer getBuyerID() {
		return buyerID;
	}
	public void setBuyerID(Integer buyerID) {
		this.buyerID = buyerID;
	}
}
