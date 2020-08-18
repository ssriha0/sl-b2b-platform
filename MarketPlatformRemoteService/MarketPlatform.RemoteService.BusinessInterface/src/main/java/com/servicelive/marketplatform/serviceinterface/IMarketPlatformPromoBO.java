package com.servicelive.marketplatform.serviceinterface;

import com.servicelive.marketplatform.common.vo.FeeType;


public interface IMarketPlatformPromoBO {

	public double getPromoFee(String soId, Long buyerId, FeeType feeType);
	public void applySOPromotions(String soId, Long buyerId);
}
