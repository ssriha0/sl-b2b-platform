package com.newco.marketplace.leadoutboundnotification.service;

import com.newco.marketplace.leadoutboundnotification.vo.LeadOutBoundNotificationVO;
import com.newco.marketplace.exception.core.BusinessServiceException;

public interface ILeadOutBoundAPIService {
	
	/**
	 * @param requestXml
	 * @param noOfRetries
	 * @param leadId
	 * @param notification
	 * @return
	 * @throws BusinessServiceException
	 */
	public void callAPIService(String requestXml,Integer noOfRetries,String leadId, LeadOutBoundNotificationVO notification) throws BusinessServiceException;
}
