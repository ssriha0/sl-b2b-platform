package com.newco.marketplace.dto.vo.provider;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class would act as the VO class for search Firms API for standard service offerings
 *
 */
public class SearchFirmsResponseVO {
	
	private Integer firmId;
	private String firmName;
	private Double rating;
	private List<ServiceOfferingsVO> serviceOfferingsList;
	private Map<Integer,ServiceOfferingsVO> firmPriceMap;
	private Map<Integer,List<Integer>> firmOfferingMap;
	private Map<Integer,List<AvailableTimeSlotVO>> offerAvailablityMap;
	private Map<Long, String> firmNames;
	private Map <Integer,BigDecimal> aggregateRatingMap;

	public Integer getFirmId() {
		return firmId;
	}
	public void setFirmId(Integer firmId) {
		this.firmId = firmId;
	}
	public String getFirmName() {
		return firmName;
	}
	public void setFirmName(String firmName) {
		this.firmName = firmName;
	}
	public Double getRating() {
		return rating;
	}
	public void setRating(Double rating) {
		this.rating = rating;
	}
	
	public List<ServiceOfferingsVO> getServiceOfferingsList() {
		return serviceOfferingsList;
	}
	public void setServiceOfferingsList(
			List<ServiceOfferingsVO> serviceOfferingsList) {
		this.serviceOfferingsList = serviceOfferingsList;
	}
	public Map<Integer, ServiceOfferingsVO> getFirmPriceMap() {
		return firmPriceMap;
	}
	public void setFirmPriceMap(Map<Integer, ServiceOfferingsVO> firmPriceMap) {
		this.firmPriceMap = firmPriceMap;
	}
	public Map<Integer, List<Integer>> getFirmOfferingMap() {
		return firmOfferingMap;
	}
	public void setFirmOfferingMap(Map<Integer, List<Integer>> firmOfferingMap) {
		this.firmOfferingMap = firmOfferingMap;
	}
	public Map<Integer, List<AvailableTimeSlotVO>> getOfferAvailablityMap() {
		return offerAvailablityMap;
	}
	public void setOfferAvailablityMap(
			Map<Integer, List<AvailableTimeSlotVO>> offerAvailablityMap) {
		this.offerAvailablityMap = offerAvailablityMap;
	}
	public Map<Long, String> getFirmNames() {
		return firmNames;
	}
	public void setFirmNames(Map<Long, String> firmNames) {
		this.firmNames = firmNames;
	}
	public Map<Integer, BigDecimal> getAggregateRatingMap() {
		return aggregateRatingMap;
	}
	public void setAggregateRatingMap(Map<Integer, BigDecimal> aggregateRatingMap) {
		this.aggregateRatingMap = aggregateRatingMap;
	}
	
}
