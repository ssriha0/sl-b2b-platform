package com.newco.marketplace.business.iBusiness.conditionalautorouting;

import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSearchResultsVO;
import com.newco.marketplace.exception.BusinessServiceException;

public interface IConditionalAutoRoutingBO {
	
	/**
	 * 
	 * @param searchResultVO
	 * @throws BusinessServiceException
	 */
	public void routeIndividualConditionalOrder(ServiceOrderSearchResultsVO searchResultVO) throws BusinessServiceException;

}
