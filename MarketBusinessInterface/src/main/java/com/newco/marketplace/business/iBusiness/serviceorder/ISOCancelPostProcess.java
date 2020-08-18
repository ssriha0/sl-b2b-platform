package com.newco.marketplace.business.iBusiness.serviceorder;

import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.exception.core.BusinessServiceException;

public interface ISOCancelPostProcess {
	/**
	 * @param serviceOrderId
	 * @param buyerId
	 * @throws BusinessServiceException
	 */
	public void execute (String serviceOrderId, Integer buyerId) throws BusinessServiceException;
	public void execute (String serviceOrderId) throws BusinessServiceException;
	public void execute (ServiceOrder so) throws BusinessServiceException;

}
