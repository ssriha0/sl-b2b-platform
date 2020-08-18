/*
 *	Date        Project       Author         Version

 * -----------  --------- 	-----------  	---------
 * 28-Jun-2012	KMSRTVST   Infosys				1.0
 * 
 * 
 */

/*
 * 
 * This is the Dao Impl class for checking for reason codes
 * 
 */
package com.newco.marketplace.persistence.daoImpl.reasoncode;

import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.reasonCode.ReasonCodeVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.iDao.reasoncode.ManageReasonCodeDao;
import com.sears.os.dao.impl.ABaseImplDao;

public class ManageReasonCodeDaoImpl extends ABaseImplDao implements ManageReasonCodeDao {

	//Create the logger
	private static final Logger logger = Logger.getLogger(ManageReasonCodeDaoImpl.class.getName());
	

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.reasoncode.ManageReasonCodeDao#isAReasonCode(int, java.lang.String, java.lang.String)
	 *
	 *returns true if the reason code exists
	 *
	 */
	public Integer isAReasonCode(int buyerId, String type, String reasonCode) throws DataServiceException{
	
		ReasonCodeVO reasonCodeVO=new ReasonCodeVO();
		Integer reasonCodeId;
		try{
			reasonCodeVO.setBuyerId(buyerId);
			reasonCodeVO.setReasonCodeType(type);
			reasonCodeVO.setReasonCodeStatus(OrderConstants.ACTIVE_CODE);
			reasonCodeVO.setReasonCode(reasonCode);
			reasonCodeId=(Integer) queryForObject("so.reasonExists.query", reasonCodeVO);
			return reasonCodeId;
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new DataServiceException(ex.getMessage(), ex);
		}
		
	}
	
	
	/**
	 * Returns the reason code id of the Default SKU Reason code. Only active reason codes are considered.
	 * @param type : It will be Cancellation /Manage Scope
	 * @param reasonCode : The reason code
	 * @return Reason code Id
	 * */
	public Integer getDefaultReasonCodeId(String type, String reasonCode) throws DataServiceException{
		ReasonCodeVO reasonCodeVO=new ReasonCodeVO();
		Integer reasonCodeId;
		try{
			reasonCodeVO.setReasonCodeType(type);			
			reasonCodeVO.setReasonCode(reasonCode);
			reasonCodeId=(Integer) queryForObject("getDefaultReasonCodeId.query", reasonCodeVO);
			return reasonCodeId;
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new DataServiceException(ex.getMessage(), ex);
		}
	}

	
}