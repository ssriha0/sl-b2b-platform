package com.servicelive.marketplatform.service;

import org.springframework.transaction.annotation.Transactional;

import com.newco.marketplace.business.iBusiness.promo.PromoBO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.servicelive.marketplatform.common.exception.MarketPlatformException;
import com.servicelive.marketplatform.common.vo.FeeType;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformPromoBO;

public class MarketPlatformPromoBO implements IMarketPlatformPromoBO {

	private PromoBO promoBO;
	
	@Transactional
	public void applySOPromotions(String soId, Long buyerId) {
		try {
			promoBO.applySOPromotions(soId, buyerId);
		} catch (BusinessServiceException e) {
			throw new MarketPlatformException(e);
		}
	}

	public double getPromoFee(String soId, Long buyerId, FeeType feeType) {
		try {
			return promoBO.getPromoFee(soId, buyerId, feeType.name());
		} catch (BusinessServiceException e) {
			throw new MarketPlatformException(e);
		}
	}

	public void setPromoBO(PromoBO promoBO) {
		this.promoBO = promoBO;
	}

}
