package com.servicelive.hsr.parts.services.impl;

import java.util.ArrayList;
import java.util.List;

import com.servicelive.hsr.parts.dao.FetchHSRPriceRateDao;
import com.servicelive.hsr.parts.dao.impl.FetchHSRPriceRateDaoImpl;
import com.servicelive.hsr.parts.services.IFetchHSRPriceRateService;
import com.servicelive.domain.hsr.parts.BuyerPartsPriceCalcValues;



public class FetchHSRPriceRateServiceImpl implements IFetchHSRPriceRateService{
	private FetchHSRPriceRateDao fetchHSRPriceRateDao;
	
	public FetchHSRPriceRateDao getFetchHSRPriceRateDao() {
		return fetchHSRPriceRateDao;
	}

	public void setFetchHSRPriceRateDao(FetchHSRPriceRateDao fetchHSRPriceRateDao) {
		this.fetchHSRPriceRateDao = fetchHSRPriceRateDao;
	}

	public BuyerPartsPriceCalcValues fetchReimbursementRate(String partCoverageType, String partSourceType) 
	{
		return fetchHSRPriceRateDao.getReimbursementRate(partCoverageType,partSourceType);
	}

}
