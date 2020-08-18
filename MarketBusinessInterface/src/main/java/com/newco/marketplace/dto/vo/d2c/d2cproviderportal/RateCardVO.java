package com.newco.marketplace.dto.vo.d2c.d2cproviderportal;

import java.util.List;

public class RateCardVO {
	//Lookup
	private List<ServiceDayVO> ServiceDaysVOs;
	private List<ServiceRatePeriodVO>serviceRatePeriodVOs;
	//RateCardprice
	private Integer vendorCategoryPriceId;
	private Double price;
	private ServiceDayVO serviceDayVOs;
	private ServiceRatePeriodVO serviceRatePeriodVO;
	
	public List<ServiceDayVO> getServiceDaysVOs() {
		return ServiceDaysVOs;
	}
	public void setServiceDaysVOs(List<ServiceDayVO> serviceDaysVOs) {
		ServiceDaysVOs = serviceDaysVOs;
	}
	public List<ServiceRatePeriodVO> getServiceRatePeriodVOs() {
		return serviceRatePeriodVOs;
	}
	public void setServiceRatePeriodVOs(
			List<ServiceRatePeriodVO> serviceRatePeriodVOs) {
		this.serviceRatePeriodVOs = serviceRatePeriodVOs;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public ServiceDayVO getServiceDayVOs() {
		return serviceDayVOs;
	}
	public void setServiceDayVOs(ServiceDayVO serviceDayVOs) {
		this.serviceDayVOs = serviceDayVOs;
	}
	public ServiceRatePeriodVO getServiceRatePeriodVO() {
		return serviceRatePeriodVO;
	}
	public void setServiceRatePeriodVO(ServiceRatePeriodVO serviceRatePeriodVO) {
		this.serviceRatePeriodVO = serviceRatePeriodVO;
	}
	public Integer getVendorCategoryPriceId() {
		return vendorCategoryPriceId;
	}
	public void setVendorCategoryPriceId(Integer vendorCategoryPriceId) {
		this.vendorCategoryPriceId = vendorCategoryPriceId;
	}
	
	
}
