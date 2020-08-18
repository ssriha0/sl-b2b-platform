package com.newco.marketplace.api.criteria.vo;

public class SearchCriteriaVO {

	private Integer resourceId;
	private String routedProvider;
	private Integer marketId;
	private String  marketName;
	
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public String getRoutedProvider() {
		return routedProvider;
	}
	public void setRoutedProvider(String routedProvider) {
		this.routedProvider = routedProvider;
	}
	public Integer getMarketId() {
		return marketId;
	}
	public void setMarketId(Integer marketId) {
		this.marketId = marketId;
	}
	public String getMarketName() {
		return marketName;
	}
	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}
	
	
	
}
