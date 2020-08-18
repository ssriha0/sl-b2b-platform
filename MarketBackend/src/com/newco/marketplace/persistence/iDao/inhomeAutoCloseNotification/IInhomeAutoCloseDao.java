package com.newco.marketplace.persistence.iDao.inhomeAutoCloseNotification;

import java.util.List;

import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.inhomeautoclosenotification.vo.InHomeAutoCloseVO;

public interface IInhomeAutoCloseDao {
	
	/**
	 * @param noOfRetries
	 * @param recordsProcessingLimit
	 * @return
	 * @throws DataServiceException
	 */
	public List<InHomeAutoCloseVO> fetchRecords()throws DataServiceException;
	
	
	/**
	 * @param appKey
	 * @return
	 * @throws DataServiceException
	 */
	public String getRecipientIdFromDB(String appKey)	throws DataServiceException;
	
	public void setEmailIndicator(List<String> failureList) throws DataServiceException;

}
