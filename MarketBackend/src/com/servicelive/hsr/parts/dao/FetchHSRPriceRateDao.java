package com.servicelive.hsr.parts.dao;
import com.servicelive.domain.hsr.parts.BuyerPartsPriceCalcValues;

public interface FetchHSRPriceRateDao {
	public BuyerPartsPriceCalcValues getReimbursementRate(String partCoverageType,
			String partSourceType);
	}
