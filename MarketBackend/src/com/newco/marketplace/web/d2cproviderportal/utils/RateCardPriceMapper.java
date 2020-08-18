package com.newco.marketplace.web.d2cproviderportal.utils;

import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.RateCardVO;
import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.ServiceDayVO;
import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.ServiceRatePeriodVO;
import com.servicelive.domain.d2cproviderportal.VendorCategoryPriceCard;

public class RateCardPriceMapper {

	public RateCardVO convertDTOtoVO(RateCardVO rateCardVo,
			VendorCategoryPriceCard rateCardPrice) {
		rateCardVo.setVendorCategoryPriceId(rateCardPrice.getId());
		rateCardVo.setPrice(rateCardPrice.getPrice());

		ServiceDayVO serviceDayVO = new ServiceDayVO();
		serviceDayVO.setId(rateCardPrice.getServiceDay().getId());
		rateCardVo.setServiceDayVOs(serviceDayVO);

		ServiceRatePeriodVO serviceRatePeriodVO = new ServiceRatePeriodVO();
		serviceRatePeriodVO.setId(rateCardPrice.getServiceRatePeriod().getId());
		rateCardVo.setServiceRatePeriodVO(serviceRatePeriodVO);
		return rateCardVo;
	}
}
