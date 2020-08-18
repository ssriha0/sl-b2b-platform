package com.newco.marketplace.dto.vo.providerSearch;

import com.sears.os.vo.SerializableBaseVO;


public class ProviderSkillResultsVO extends SerializableBaseVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7547415963975230913L;
	private String providerFirstName;
	private String providerLastName;
	private int providerResourceId;
	private int providerId;
	private int providerStatusId;
	private int skillNodeId;
	private int skillLevel;
	private double providerSkillRating;
	private String city;
	private String state;
	private double distanceFromZipInMiles;
	private double providerLatitude;
	private double providerLongitude;
	private int[] allProviderSkills;
	private Double firmPerformanceScore;
	private String firmName;
	
	public int[] getAllProviderSkills() {
		return allProviderSkills;
	}
	public void setAllProviderSkills(int[] allProviderSkills) {
		this.allProviderSkills = allProviderSkills;
	}
	public String getProviderFirstName() {
		return providerFirstName;
	}
	public void setProviderFirstName(String providerFirstName) {
		this.providerFirstName = providerFirstName;
	}
	public String getProviderLastName() {
		return providerLastName;
	}
	public void setProviderLastName(String providerLastName) {
		this.providerLastName = providerLastName;
	}
	public int getProviderResourceId() {
		return providerResourceId;
	}
	public void setProviderResourceId(int providerResourceId) {
		this.providerResourceId = providerResourceId;
	}
	public int getProviderStatusId() {
		return providerStatusId;
	}
	public void setProviderStatusId(int providerStatusId) {
		this.providerStatusId = providerStatusId;
	}
	public int getSkillLevel() {
		return skillLevel;
	}
	public void setSkillLevel(int skillLevel) {
		this.skillLevel = skillLevel;
	}
	public int getSkillNodeId() {
		return skillNodeId;
	}
	public void setSkillNodeId(int skillNodeId) {
		this.skillNodeId = skillNodeId;
	}
	public double getProviderSkillRating() {
		return providerSkillRating;
	}
	public void setProviderSkillRating(double providerSkillRating) {
		this.providerSkillRating = providerSkillRating;
	}
	public int getProviderId() {
		return providerId;
	}
	public void setProviderId(int providerId) {
		this.providerId = providerId;
	}
	public double getDistanceFromZipInMiles() {
		return distanceFromZipInMiles;
	}
	public void setDistanceFromZipInMiles(double distanceFromZipInMiles) {
		this.distanceFromZipInMiles = distanceFromZipInMiles;
	}
	public double getProviderLatitude() {
		return providerLatitude;
	}
	public void setProviderLatitude(double providerLatitude) {
		this.providerLatitude = providerLatitude;
	}
	public double getProviderLongitude() {
		return providerLongitude;
	}
	public void setProviderLongitude(double providerLongitude) {
		this.providerLongitude = providerLongitude;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Double getFirmPerformanceScore() {
		return firmPerformanceScore;
	}
	public void setFirmPerformanceScore(Double firmPerformanceScore) {
		this.firmPerformanceScore = firmPerformanceScore;
	}
	public String getFirmName() {
		return firmName;
	}
	public void setFirmName(String firmName) {
		this.firmName = firmName;
	}
	
}
