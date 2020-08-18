/*
 *	Date        Project       Author         Version

 * -----------  --------- 	-----------  	---------
 * 28-Jun-2012	KMSRTVST   Infosys				1.0
 * 
 * 
 */

/*
 * 
 * This is the Dao class for checking for reason codes
 * 
 */
package com.newco.marketplace.persistence.iDao.reasoncode;

import com.newco.marketplace.exception.core.DataServiceException;

public interface ManageReasonCodeDao {

	Integer isAReasonCode(int buyerId, String type, String reasonCode)throws DataServiceException;

	public Integer getDefaultReasonCodeId(String type, String reasonCode)throws DataServiceException;

}