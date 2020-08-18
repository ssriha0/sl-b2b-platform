package com.newco.marketplace.dto.vo.serviceOfferings;

import java.io.Serializable;
import java.util.List;

public class ServiceOfferingHistoryVO implements Serializable{
	
	private static final long serialVersionUID = -1527770441184997995L; 
    private Integer offeringId;
    private List<ServiceOfferingsVO> serviceOfferingsHistoryList;
    private List<PricingVO> serviceOfferingspricingHistoryList;
    private List<AvailabilityVO> serviceOfferingsAvailabilityHistoryList;
    
	public Integer getOfferingId() {
		return offeringId;
	}
	public void setOfferingId(Integer offeringId) {
		this.offeringId = offeringId;
	}
	public List<ServiceOfferingsVO> getServiceOfferingsHistoryList() {
		return serviceOfferingsHistoryList;
	}
	public void setServiceOfferingsHistoryList(
			List<ServiceOfferingsVO> serviceOfferingsHistoryList) {
		this.serviceOfferingsHistoryList = serviceOfferingsHistoryList;
	}
	public List<PricingVO> getServiceOfferingspricingHistoryList() {
		return serviceOfferingspricingHistoryList;
	}
	public void setServiceOfferingspricingHistoryList(
			List<PricingVO> serviceOfferingspricingHistoryList) {
		this.serviceOfferingspricingHistoryList = serviceOfferingspricingHistoryList;
	}
	public List<AvailabilityVO> getServiceOfferingsAvailabilityHistoryList() {
		return serviceOfferingsAvailabilityHistoryList;
	}
	public void setServiceOfferingsAvailabilityHistoryList(
			List<AvailabilityVO> serviceOfferingsAvailabilityHistoryList) {
		this.serviceOfferingsAvailabilityHistoryList = serviceOfferingsAvailabilityHistoryList;
	}
    
	
}
