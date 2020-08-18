package com.servicelive.hsr.parts.services;

import com.newco.marketplace.exception.DelegateException;
import com.servicelive.domain.hsr.parts.BuyerPartsPriceCalcValues;


public interface IFetchHSRPriceRateService {
	public BuyerPartsPriceCalcValues fetchReimbursementRate(String partCoverageType, String partSourceType);

}
