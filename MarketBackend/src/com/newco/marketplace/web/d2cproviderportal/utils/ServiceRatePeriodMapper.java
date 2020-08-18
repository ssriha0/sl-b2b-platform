package com.newco.marketplace.web.d2cproviderportal.utils;

import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.ServiceRatePeriodVO;
import com.servicelive.domain.d2cproviderportal.LookupServiceRatePeriod;

public class ServiceRatePeriodMapper {

	public ServiceRatePeriodVO convertDTOtoVO(ServiceRatePeriodVO serviceRatePeriodVO,
			LookupServiceRatePeriod lookupServiceRatePeriod) {
		
		serviceRatePeriodVO.setId(lookupServiceRatePeriod.getId());
		serviceRatePeriodVO.setName(lookupServiceRatePeriod.getName());
		
		return serviceRatePeriodVO;
	}

}
