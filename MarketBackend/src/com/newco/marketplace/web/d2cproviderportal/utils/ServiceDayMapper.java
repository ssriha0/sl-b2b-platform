package com.newco.marketplace.web.d2cproviderportal.utils;

import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.ServiceDayVO;
import com.servicelive.domain.d2cproviderportal.LookupServiceDay;

public class ServiceDayMapper {

	public ServiceDayVO convertDTOtoVO(ServiceDayVO serviceDayVO,LookupServiceDay serviceDay) {
		
		serviceDayVO.setId(serviceDay.getId());
		serviceDayVO.setName(serviceDay.getName());
		
		return serviceDayVO;
	}
}
