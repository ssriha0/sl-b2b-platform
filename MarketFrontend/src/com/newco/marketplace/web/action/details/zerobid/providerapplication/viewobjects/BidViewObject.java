package com.newco.marketplace.web.action.details.zerobid.providerapplication.viewobjects;


public class BidViewObject  {
	
	private static final long serialVersionUID = 1205698967346677487L;
	
	private Double hourlyRate;
	private Long totalHours;
	private Double maximumLaborCost;
	private Double materialCost;
	private String expiration;
	private String comment;
	private Long buyerId;
	private Long orderId;
	private Long providerId;

	public Long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getProviderId() {
		return providerId;
	}

	public void setProviderId(Long providerId) {
		this.providerId = providerId;
	}

	public Double getHourlyRate() {
		return hourlyRate;
	}

	public void setHourlyRate(Double hourlyRate) {
		this.hourlyRate = hourlyRate;
	}

	public Long getTotalHours() {
		return totalHours;
	}

	public void setTotalHours(Long totalHours) {
		this.totalHours = totalHours;
	}

	public Double getMaterialCost() {
		if (materialCost == null) {
			return 0.0;
		}
		return materialCost;
	}

	public void setMaterialCost(Double materialCost) {
		this.materialCost = materialCost;
	}

	public String getExpiration() {
		return expiration;
	}

	public void setExpiration(String expiration) {
		this.expiration = expiration;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setMaximumLaborCost(Double maximumLaborCost) {
		this.maximumLaborCost = maximumLaborCost;
	}

	public Double getMaximumLaborCost() {
		return maximumLaborCost;
	}
	
}
