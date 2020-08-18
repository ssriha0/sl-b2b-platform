package com.newco.marketplace.business.businessImpl.so.order;

import java.util.Map;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

public interface IProBuyerUpdateBO {

	public ProcessResponse updateServiceOrder(ServiceOrder matchingSO, ServiceOrder updatedSO, Map<String,Object> buyerParameters, SecurityContext securityContext)
		throws BusinessServiceException;
	
}
