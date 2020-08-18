/*
 *	Date        Project       Author         Version

 * -----------  --------- 	-----------  	---------
 * 28-Jun-2012	KMSRTVST   Infosys				1.0
 * 
 * 
 */

package com.newco.marketplace.buyeroutboundnotification.service;

import com.newco.marketplace.buyeroutboundnotification.vo.BuyerOutboundFailOverVO;
import com.newco.marketplace.exception.core.BusinessServiceException;

/**
 * Interface for buyer notification.This class fetches the data from the
 * database
 * 
 */
public interface IBuyerOutBoundNotificationJMSService {

   public void callJMSService(BuyerOutboundFailOverVO failoverVO) throws BusinessServiceException;
	
}
