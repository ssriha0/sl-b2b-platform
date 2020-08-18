package com.newco.marketplace.gwt.providersearch.client;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SimpleProviderSearchProviderResultVO implements IsSerializable, Serializable{

	
	private String providerFirstName;
	private String providerLastName;
	private double providerRating;
	private double distanceFromBuyer;
	private int resourceId;
	
	private double providerLatitude;
	private double providerLongitude;
	private double providerStarRating;
	private int providerStarRatingImage;	
	private Integer backgroundCheckStatus;
	private boolean isBackgroundCheckClear;
	private Boolean isSLVerified;
	private Integer vendorID;
	private Double distance;
	private String city;
	private String state;
	private Integer percentageMatch;
	private boolean selected = false;
	private Date createdDate;
	private Integer totalSOCompleted;
	private boolean isLockedProvider = false;
	private List languagesKnownByMe; //List of LanaguageVO from the GWT package
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
	public double getProviderRating() {
		return providerRating;
	}
	public void setProviderRating(double providerRating) {
		this.providerRating = providerRating;
	}
	public double getDistanceFromBuyer() {
		return distanceFromBuyer;
	}
	public void setDistanceFromBuyer(double distanceFromBuyer) {
		this.distanceFromBuyer = distanceFromBuyer;
	}
	public int getResourceId() {
		return resourceId;
	}
	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
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
	public double  getProviderStarRating() {
		return providerStarRating;
	}
	public void setProviderStarRating(double providerStarRating) {
		this.providerStarRating = providerStarRating;
	}
	public Integer getBackgroundCheckStatus() {
		return backgroundCheckStatus;
	}
	public void setBackgroundCheckStatus(Integer backgroundCheckStatus) {
		this.backgroundCheckStatus = backgroundCheckStatus;
	}
	public Boolean getIsSLVerified() {
		return isSLVerified;
	}
	public void setIsSLVerified(Boolean isSLVerified) {
		this.isSLVerified = isSLVerified;
	}
	public Integer getVendorID() {
		return vendorID;
	}
	public void setVendorID(Integer vendorID) {
		this.vendorID = vendorID;
	}
	public Double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
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
	public Integer getPercentageMatch() {
		return percentageMatch;
	}
	public void setPercentageMatch(Integer percentageMatch) {
		this.percentageMatch = percentageMatch;
	}
	public boolean getSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Integer getTotalSOCompleted() {
		return totalSOCompleted;
	}
	public void setTotalSOCompleted(Integer totalSOCompleted) {
		this.totalSOCompleted = totalSOCompleted;
	}
	public int getProviderStarRatingImage() {
		return providerStarRatingImage;
	}
	public void setProviderStarRatingImage(int providerStarRatingImage) {
		this.providerStarRatingImage = providerStarRatingImage;
	}
	public List getLanguagesKnownByMe() {
		return languagesKnownByMe;
	}
	public void setLanguagesKnownByMe(List languagesKnownByMe) {
		this.languagesKnownByMe = languagesKnownByMe;
	}
	public boolean isBackgroundCheckClear() {
		return isBackgroundCheckClear;
	}
	public void setBackgroundCheckClear(boolean isBackgroundCheckClear) {
		this.isBackgroundCheckClear = isBackgroundCheckClear;
	}
	public boolean isLockedProvider() {
		return isLockedProvider;
	}
	public void setLockedProvider(boolean isLockedProvider) {
		this.isLockedProvider = isLockedProvider;
	}
}
