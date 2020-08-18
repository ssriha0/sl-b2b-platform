package com.newco.marketplace.dto.vo.providerSearch;


public class ETMProviderSearchRequest extends ProviderSearchCriteriaVO {
	
	private String searchQueryKey;
	private Double zipLatitude;
	private Double zipLongitude;
	private Integer nodeId;
	private Integer distanceFilter;
	private String zip;
	
	public ETMProviderSearchRequest() {
		super();
	}
	
	public Integer getDistanceFilter() {
		return distanceFilter;
	}
	public void setDistanceFilter(Integer distanceFilter) {
		this.distanceFilter = distanceFilter;
	}
	public Integer getNodeId() {
		return nodeId;
	}
	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}
	public String getSearchQueryKey() {
		return searchQueryKey;
	}
	public void setSearchQueryKey(String searchQueryKey) {
		this.searchQueryKey = searchQueryKey;
	}
	public Double getZipLatitude() {
		return zipLatitude;
	}
	public void setZipLatitude(Double zipLatitude) {
		this.zipLatitude = zipLatitude;
	}
	public Double getZipLongitude() {
		return zipLongitude;
	}
	public void setZipLongitude(Double zipLongitude) {
		this.zipLongitude = zipLongitude;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

}
