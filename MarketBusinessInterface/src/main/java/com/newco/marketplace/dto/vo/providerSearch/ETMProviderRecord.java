package com.newco.marketplace.dto.vo.providerSearch;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.sears.os.vo.SerializableBaseVO;

public class ETMProviderRecord extends SerializableBaseVO{

	private static final long serialVersionUID = 1907389665622260294L;
	private String searchQueryKey;
	private Integer resourceId;
	private Integer nodeId;
	private String nodeName;
	private String firstName;
	private String lastName;
	private String zip;
	private String city;
	private String state;
	private Double vendorGisLat;
	private Double vendorGisLong;
	private Integer vendorId;
	private String businessName;
	private Integer backgroundStateId;
	private String backgroundStatus;
	private Integer totalOrdersCompleted;
	private Integer ratingCount = Integer.valueOf(-1);
	private Double ratingScore;
	private Double hourlyRate;
	private Integer credCatId;
	private Integer credTypeId;
	private Integer resourceCredWFState;
	private Integer resourceLangId;
	private Integer langId;
	private Double distance;
	private String username;
	private Timestamp createdDate;
	private Integer ratingImageId;
	private Integer buyersTotalOrder = Integer.valueOf(0);//Total order given to this proider by the buyerId
	private Integer buyersRatingsImageId;//image id number with corresponding
	private Double buyersRatingScore;//this is what we use for collecting what are the ratign given by buyer tho this provider
	private Integer buyersRatingCount;
	private List<ProviderSPNetStateResultsVO> spnetStates = new ArrayList<ProviderSPNetStateResultsVO>();
	
	public Integer getBackgroundStateId() {
		return backgroundStateId;
	}
	public void setBackgroundStateId(Integer backgroundStateId) {
		this.backgroundStateId = backgroundStateId;
	}
	public String getBackgroundStatus() {
		return backgroundStatus;
	}
	public void setBackgroundStatus(String backgroundStatus) {
		this.backgroundStatus = backgroundStatus;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Integer getCredCatId() {
		return credCatId;
	}
	public void setCredCatId(Integer credCatId) {
		this.credCatId = credCatId;
	}
	public Integer getCredTypeId() {
		return credTypeId;
	}
	public void setCredTypeId(Integer credTypeId) {
		this.credTypeId = credTypeId;
	}
	public Double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public Double getHourlyRate() {
		return hourlyRate;
	}
	public void setHourlyRate(Double hourlyRate) {
		this.hourlyRate = hourlyRate;
	}
	public Integer getLangId() {
		return langId;
	}
	public void setLangId(Integer langId) {
		this.langId = langId;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public Integer getRatingCount() {
		return ratingCount;
	}
	public void setRatingCount(Integer ratingCount) {
		this.ratingCount = ratingCount;
	}
	public Double getRatingScore() {
		return ratingScore;
	}
	public void setRatingScore(Double ratingScore) {
		this.ratingScore = ratingScore;
	}
	
	public Integer getResourceCredWFState() {
		return resourceCredWFState;
	}
	public void setResourceCredWFState(Integer resourceCredWFState) {
		this.resourceCredWFState = resourceCredWFState;
	}
	public Integer getResourceLangId() {
		return resourceLangId;
	}
	public void setResourceLangId(Integer resourceLangId) {
		this.resourceLangId = resourceLangId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Integer getTotalOrdersCompleted() {
		return totalOrdersCompleted;
	}
	public void setTotalOrdersCompleted(Integer totalOrdersCompleted) {
		this.totalOrdersCompleted = totalOrdersCompleted;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getSearchQueryKey() {
		return searchQueryKey;
	}
	public void setSearchQueryKey(String searchQueryKey) {
		this.searchQueryKey = searchQueryKey;
	}
	public Double getVendorGisLat() {
		return vendorGisLat;
	}
	public void setVendorGisLat(Double vendorGisLat) {
		this.vendorGisLat = vendorGisLat;
	}
	public Double getVendorGisLong() {
		return vendorGisLong;
	}
	public void setVendorGisLong(Double vendorGisLong) {
		this.vendorGisLong = vendorGisLong;
	}
	public Integer getNodeId() {
		return nodeId;
	}
	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @return the ratingImageId
	 */
	public Integer getRatingImageId() {
		return ratingImageId;
	}
	/**
	 * @param ratingImageId the ratingImageId to set
	 */
	public void setRatingImageId(Integer ratingImageId) {
		this.ratingImageId = ratingImageId;
	}
	/**
	 * @return the spnetStates
	 */
	public List<ProviderSPNetStateResultsVO> getSpnetStates() {
		return spnetStates;
	}
	/**
	 * @param spnetStates the spnetStates to set
	 */
	public void setSpnetStates(List<ProviderSPNetStateResultsVO> spnetStates) {
		this.spnetStates = spnetStates;
	}
	/**
	 * @return the buyersTotalOrder
	 */
	public Integer getBuyersTotalOrder() {
		return buyersTotalOrder;
	}
	/**
	 * @param buyersTotalOrder the buyersTotalOrder to set
	 */
	public void setBuyersTotalOrder(Integer buyersTotalOrder) {
		this.buyersTotalOrder = buyersTotalOrder;
	}
	/**
	 * @return the buyersRatingsImageId
	 */
	public Integer getBuyersRatingsImageId() {
		return buyersRatingsImageId;
	}
	/**
	 * @param buyersRatingsImageId the buyersRatingsImageId to set
	 */
	public void setBuyersRatingsImageId(Integer buyersRatingsImageId) {
		this.buyersRatingsImageId = buyersRatingsImageId;
	}
	/**
	 * @return the buyersRatingCount
	 */
	public Integer getBuyersRatingCount() {
		return buyersRatingCount;
	}
	/**
	 * @param buyersRatingCount the buyersRatingCount to set
	 */
	public void setBuyersRatingCount(Integer buyersRatingCount) {
		this.buyersRatingCount = buyersRatingCount;
	}
	/**
	 * @return the buyersRatingScore
	 */
	public Double getBuyersRatingScore() {
		return buyersRatingScore;
	}
	/**
	 * @param buyersRatingScore the buyersRatingScore to set
	 */
	public void setBuyersRatingScore(Double buyersRatingScore) {
		this.buyersRatingScore = buyersRatingScore;
	}
	
	
}