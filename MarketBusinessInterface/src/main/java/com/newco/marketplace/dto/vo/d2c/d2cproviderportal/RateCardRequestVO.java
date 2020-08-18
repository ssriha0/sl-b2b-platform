package com.newco.marketplace.dto.vo.d2c.d2cproviderportal;

import java.util.List;

public class RateCardRequestVO {
	private String primaryIndustry;
	private List<RateCardJsonRequest> rateCardPriceUIModal;
	
	public String getPrimaryIndustry() {
		return primaryIndustry;
	}
	public void setPrimaryIndustry(String primaryIndustry) {
		this.primaryIndustry = primaryIndustry;
	}
	
	public List<RateCardJsonRequest> getRateCardPriceUIModal() {
		return rateCardPriceUIModal;
	}
	public void setRateCardPriceUIModal(
			List<RateCardJsonRequest> rateCardPriceUIModal) {
		this.rateCardPriceUIModal = rateCardPriceUIModal;
	}
	
}
