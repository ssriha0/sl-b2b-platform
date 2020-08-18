package com.newco.marketplace.search.vo;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.search.types.ResultMode;
import com.newco.marketplace.search.types.ServiceTypes;
import com.newco.marketplace.search.solr.bo.BaseSearchBO;

public class ProviderSearchRequestVO extends BaseRequestVO{
	public static final int SORT_BY_DISTANCE = 0;
	public static final int SORT_BY_RATING = 1;
	
	public static final int SORT_ORDER_DESC = 0;
	public static final int SORT_ORDER_ASC = 1;
	/**
	 * ids are resource Ids when reqTYpe = REQ_TYPE_BY_RESOURCE_ID
	 * ids are categories Ids when reqType = REQ_TYPE_BY_ZIP
	 */
	private List<ServiceTypes> serviceTypes;
	private List<String> languages;
	private int maxDistance;
	private float minRating;
	private float maxRating = BaseSearchBO.MAX_RATING;
	private ResultMode resultMode;
	private String zipCode;
	private List<Integer> categoryIds;
	private List<Integer> providerIds;
	private List<Integer> ids;
	private int sortBy;
	private int sortOrder;
	private int companyId;
	private String city;
	private String state;
		
	public List<Integer> getIds() {
		return ids;
	}
	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}
	
	public void appendIdToList(int id) {
		if (ids == null) {
			ids = new ArrayList<Integer>();
		}
		ids.add(Integer.valueOf(id));
	}

	
	public List<String> getLanguages() {
		if (languages == null)
			languages = new ArrayList<String>();
		return languages;
	}
	public void setLanguages(List<String> languages) {
		this.languages = languages;
	}
	
	public int getMaxDistance() {
		return maxDistance;
	}
	public void setMaxDistance(int maxDistance) {
		if (maxDistance == 0)
			maxDistance = DEFAULT_RADIOUS;
		this.maxDistance = maxDistance;
	}

	public ResultMode getResultMode() {
		return resultMode;
	}
	public void setResultMode(ResultMode resultMode) {
		this.resultMode = resultMode;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	public void appendLanguageToList(String lang) {
		if (languages == null) {
			languages = new ArrayList<String>();
		}
		languages.add(lang);
	}
	
	public float getMinRating() {
		return minRating;
	}
	public void setMinRating(float minRating) {
		this.minRating = minRating;
	}
	public List<ServiceTypes> getServiceTypes() {
		return serviceTypes;
	}
	public void setServiceTypes(List<ServiceTypes> serviceTypes) {
		this.serviceTypes = serviceTypes;
	}
	
	public void addServiceType(ServiceTypes serviceType) {
		if (this.serviceTypes == null)
			this.serviceTypes = new ArrayList<ServiceTypes>();
		this.serviceTypes.add(serviceType);
	}
	public List<Integer> getCategoryIds() {
		return categoryIds;
	}
	public void setCategoryIds(List<Integer> categoryIds) {
		this.categoryIds = categoryIds;
	}
	
	public void addCategoryId(Integer categoryId) {
		if (this.categoryIds  == null)
			this.categoryIds  = new ArrayList<Integer>();
		this.categoryIds.add(categoryId);
	}
	
	public List<Integer> getProviderIds() {
		return providerIds;
	}
	public void setProviderIds(List<Integer> providerIds) {
		this.providerIds = providerIds;
	}
	public int getSortBy() {
		return sortBy;
	}
	public void setSortBy(int sortBy) {
		this.sortBy = sortBy;
	}
	public int getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public float getMaxRating() {
		return maxRating;
	}
	public void setMaxRating(float maxRating) {
		this.maxRating = maxRating;
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
	
	
}
