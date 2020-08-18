package com.newco.marketplace.web.dto;

public class FindProviderTableRowDTO {

	private int percentMatch;
	private String providerName;
	private Double rawRating; // 0.0 - 5.0
	private int	ratingNumber; // 0-20 used to display stars
	private Integer yearsInBusiness;
	private Double distance;
	private String location;
	private boolean checked;
	
	public int getPercentMatch() {
		return percentMatch;
	}
	public void setPercentMatch(int percentMatch) {
		this.percentMatch = percentMatch;
	}
	public String getProviderName() {
		return providerName;
	}
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	public Double getRawRating() {
		return rawRating;
	}
	public void setRawRating(Double rawRating) {
		this.rawRating = rawRating;
	}
	public int getRatingNumber() {
		return ratingNumber;
	}
	public void setRatingNumber(int ratingNumber) {
		this.ratingNumber = ratingNumber;
	}
	public Integer getYearsInBusiness() {
		return yearsInBusiness;
	}
	public void setYearsInBusiness(Integer yearsInBusiness) {
		this.yearsInBusiness = yearsInBusiness;
	}
	public Double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	
}
