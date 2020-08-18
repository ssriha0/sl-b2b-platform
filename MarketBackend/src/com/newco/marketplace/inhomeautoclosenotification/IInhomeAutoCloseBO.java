package com.newco.marketplace.inhomeautoclosenotification;

import java.util.List;

import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.inhomeautoclosenotification.vo.InHomeAutoCloseVO;

public interface IInhomeAutoCloseBO {

	
	/**
	 * @param noOfRetries
	 * @param recordsProcessingLimit
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<InHomeAutoCloseVO> fetchRecords()throws BusinessServiceException;
	
	/**
	 * @param appKey
	 * @return
	 * @throws BusinessServiceException
	 */
	public String getRecipientIdFromDB(String appKey)	throws BusinessServiceException;
	
	public void setEmailIndicator(List<String> failureList) throws BusinessServiceException;
}
