package com.newco.marketplace.dto.vo.d2c.d2cproviderportal;

public class RateCardJsonRequest {
	private Integer vendorCategoryPriceId;
	private String price;
	private ServiceDayVO serviceDayVOs;
	private ServiceRatePeriodVO serviceRatePeriodVO;
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
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
