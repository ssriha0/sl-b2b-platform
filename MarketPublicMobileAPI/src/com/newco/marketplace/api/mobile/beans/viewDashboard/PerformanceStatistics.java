package com.newco.marketplace.api.mobile.beans.viewDashboard;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("performanceStatistics")
public class PerformanceStatistics {

	@XStreamAlias("numberOfRatingsReceived")
	private Integer	numberOfRatingsReceived;
	
	@XStreamAlias("lifetimeRating")
	private Double	lifetimeRating;
	
	@XStreamAlias("currentRating")
	private Double	currentRating;
	
	public Integer getNumberOfRatingsReceived() {
		return numberOfRatingsReceived;
	}
	public void setNumberOfRatingsReceived(Integer numberOfRatingsReceived) {
		this.numberOfRatingsReceived = numberOfRatingsReceived;
	}
	public Double getLifetimeRating() {
		return lifetimeRating;
	}
	public void setLifetimeRating(Double lifetimeRating) {
		this.lifetimeRating = lifetimeRating;
	}
	public Double getCurrentRating() {
		return currentRating;
	}
	public void setCurrentRating(Double currentRating) {
		this.currentRating = currentRating;
	}

}
