/*
 *	Date        Project       Author         Version

 * -----------  --------- 	-----------  	---------
 * 28-Jun-2012	KMSRTVST   Infosys				1.0
 * 
 * 
 */

/*
 * 
 * This is the interface for BO class for checking for reason codes
 * 
 */
package com.newco.marketplace.business.iBusiness.reasonCode;

import com.newco.marketplace.exception.core.BusinessServiceException;

/**
 * 
 *
 */
public interface IManageReasonCodeBO {
	Integer isAReasonCode(int buyerId, String type, String reasonCode);
	public Integer getDefaultReasonCodeId(String type, String reasonCode) throws BusinessServiceException;
	
}
