package com.newco.marketplace.dto.vo.survey;

import com.sears.os.vo.SerializableBaseVO;


public class SurveyRatingsVO extends SerializableBaseVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7507407523303360374L;
	private Double currentRating;
	private Double historicalRating;
	private Double givenRating;
	private Integer numberOfRatingsGiven;
	private Integer numberOfRatingsReceived;
	
	// For Provider Search 
	private Double myRating;
	private Integer totalOrdersComplete;
	private Integer myOrdersComplete;
	private Integer vendorResourceID;
	private Integer buyerID;
		
	public Integer getNumberOfRatingsGiven() {
		return numberOfRatingsGiven;
	}
	public Integer getMyOrdersComplete() {
		return myOrdersComplete;
	}
	public void setMyOrdersComplete(Integer myOrdersComplete) {
		this.myOrdersComplete = myOrdersComplete;
	}
	public Double getMyRating() {
		return myRating;
	}
	public void setMyRating(Double myRating) {
		this.myRating = myRating;
	}
	public Integer getTotalOrdersComplete() {
		return totalOrdersComplete;
	}
	public void setTotalOrdersComplete(Integer totalOrdersComplete) {
		this.totalOrdersComplete = totalOrdersComplete;
	}
	public void setNumberOfRatingsGiven(Integer numberOfRatingsGiven) {
		this.numberOfRatingsGiven = numberOfRatingsGiven;
	}
	public Integer getNumberOfRatingsReceived() {
		return numberOfRatingsReceived;
	}
	public void setNumberOfRatingsReceived(Integer numberOfRatingsReceived) {
		this.numberOfRatingsReceived = numberOfRatingsReceived;
	}
	public Double getCurrentRating() {
		return currentRating;
	}
	public void setCurrentRating(Double currentRating) {
		this.currentRating = currentRating;
	}
	public Double getHistoricalRating() {
		return historicalRating;
	}
	public void setHistoricalRating(Double historicalRating) {
		this.historicalRating = historicalRating;
	}
	public Double getGivenRating() {
		return givenRating;
	}
	public void setGivenRating(Double givenRating) {
		this.givenRating = givenRating;
	}
	public Integer getBuyerID() {
		return buyerID;
	}
	public void setBuyerID(Integer buyerID) {
		this.buyerID = buyerID;
	}
	public Integer getVendorResourceID() {
		return vendorResourceID;
	}
	public void setVendorResourceID(Integer vendorResourceID) {
		this.vendorResourceID = vendorResourceID;
	}	
	
}
