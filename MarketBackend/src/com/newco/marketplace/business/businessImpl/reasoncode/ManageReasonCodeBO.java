
/*
 *	Date        Project       Author         Version

 * -----------  --------- 	-----------  	---------
 * 28-Jun-2012	KMSRTVST   Infosys				1.0
 * 
 * 
 */

/*
 * 
 * This is the BO class for checking for reason codes
 * 
 */
package com.newco.marketplace.business.businessImpl.reasoncode;
import java.util.List;
import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.reasonCode.IManageReasonCodeBO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.reasoncode.ManageReasonCodeDao;


public class ManageReasonCodeBO implements IManageReasonCodeBO{
	
	
	//Create the logger
	private static final Logger LOGGER = Logger.getLogger(ManageReasonCodeBO.class.getName());
    private ManageReasonCodeDao reasonCodedao;


	public ManageReasonCodeDao getReasonCodedao() {
		return reasonCodedao;
	}

	public void setReasonCodedao(ManageReasonCodeDao reasonCodedao) {
		this.reasonCodedao = reasonCodedao;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.reasonCode.IManageReasonCodeBO#isAReasonCode(int, java.lang.String, java.lang.String)
	 */
	public Integer isAReasonCode(int buyerId, String type,String reasonCode) {
		// TODO Auto-generated method stub
		Integer reasonCodeId=null;
		try {
			reasonCodeId = getReasonCodedao().isAReasonCode(buyerId,type,reasonCode);
		} 
		catch (DataServiceException e) {
			LOGGER.error("Exception :" + e);
		}
		return reasonCodeId;
	}

	/**
	 * Returns the reason code id of the Default SKU Reason code.
	 * @param type : It will be Cancellation /Manage Scope
	 * @param reasonCode : The reason code
	 * @return Reason code Id
	 * */
	public Integer getDefaultReasonCodeId(String type, String reasonCode) throws BusinessServiceException {
		Integer reasonCodeId=null;
		try {
			reasonCodeId = getReasonCodedao().getDefaultReasonCodeId(type,reasonCode);
		} catch (DataServiceException e) {
			LOGGER.error("Exception :" + e);
			throw new BusinessServiceException(e.getMessage(),e);
		}
		return reasonCodeId;
	}
	
	
}
