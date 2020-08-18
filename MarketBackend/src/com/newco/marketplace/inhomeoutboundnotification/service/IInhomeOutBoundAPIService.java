package com.newco.marketplace.inhomeoutboundnotification.service;

import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.inhomeoutboundnotification.vo.InHomeOutBoundNotificationVO;

/**
 * @author Infosys
 *
 */

public interface IInhomeOutBoundAPIService {

	/**
	 * @param noOfRetries
	 * @param vo
	 */
	public String callAPIService(Integer noOfRetries, InHomeOutBoundNotificationVO vo, String serviceId) throws BusinessServiceException;
}
