package com.newco.marketplace.dto.vo.leadsmanagement;

public class RatingVO {

	private Integer resourceId;
	private int aggregateRating;

	public int getAggregateRating() {
		return aggregateRating;
	}

	public void setAggregateRating(int aggregateRating) {
		this.aggregateRating = aggregateRating;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

}
