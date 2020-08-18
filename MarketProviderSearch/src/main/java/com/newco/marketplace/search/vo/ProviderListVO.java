package com.newco.marketplace.search.vo;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.search.types.Bucket;

public class ProviderListVO {
	private List<ProviderSearchResponseVO> providerSearchResponseVO;
	private List<Bucket> facets;
	
	public ProviderListVO(
			List<ProviderSearchResponseVO> providerSearchResponseVO,
			int totalProvidersFound) {
		super();
		this.providerSearchResponseVO = providerSearchResponseVO;
		this.totalProvidersFound = totalProvidersFound;
	}
	private int totalProvidersFound;
	
	public List<ProviderSearchResponseVO> getProviderSearchResponseVO() {
		return providerSearchResponseVO;
	}
	public void setProviderSearchResponseVO(
			List<ProviderSearchResponseVO> providerSearchResponseVO) {
		this.providerSearchResponseVO = providerSearchResponseVO;
	}
	public int getTotalProvidersFound() {
		return totalProvidersFound;
	}
	public void setTotalProvidersFound(int totalProvidersFound) {
		this.totalProvidersFound = totalProvidersFound;
	}
	public List<Bucket> getFacets() {
		if (facets == null)
			return new ArrayList<Bucket>();
		
		return facets;
	}
	public void setFacets(List<Bucket> facets) {
		this.facets = facets;
	}
	
}
