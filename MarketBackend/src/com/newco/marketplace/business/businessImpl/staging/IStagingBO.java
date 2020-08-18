package com.newco.marketplace.business.businessImpl.staging;

import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.exception.core.BusinessServiceException;


public interface IStagingBO {

	
	/**
	 * Updates final price for all the skus in the order in the staging tables
	 * 	
	 * @param serviceOrder
	 * @param finalPriceLabor
	 * @param permitPrice
	 * @param totalAddOnServiceFee
	 * @throws BusinessServiceException
	 */
	public void updateFinalPrice(ServiceOrder serviceOrder, Double finalPriceLabor, Double permitPrice, Double totalAddOnServiceFee) throws BusinessServiceException; 
	
}
