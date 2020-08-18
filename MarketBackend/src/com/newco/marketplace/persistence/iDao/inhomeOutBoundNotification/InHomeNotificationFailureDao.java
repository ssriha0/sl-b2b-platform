package com.newco.marketplace.persistence.iDao.inhomeOutBoundNotification;

import java.util.List;

import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.inhomeoutboundnotification.vo.InHomeOutBoundNotificationVO;
import com.newco.marketplace.inhomeoutboundnotification.vo.NotificationOwnerDetails;

public interface InHomeNotificationFailureDao {

	/**
	 * @param recordsProcessingLimit 
	 * @return
	 * @throws DataServiceException
	 */
	public List<InHomeOutBoundNotificationVO > fetchFailedRecords(Integer recordsProcessingLimit) throws DataServiceException;

	/**
	 * @param flagKey
	 * @return
	 */
	public String getPropertyFromApplicationFlag(String flagKey) throws DataServiceException;

	/**
	 * @return
	 * @throws DataServiceException
	 */
	public List<NotificationOwnerDetails> getNotificationOwnerDetails() throws DataServiceException;
	
	public void setEmailIndicator(List<String> failureList) throws DataServiceException;
	/**
	 * @param appKey
	 * @return
	 * @throws DataServiceException
	 */
	public String getPropertyFromDB(String appKey)	throws DataServiceException;

	/**
	 * @return
	 * @throws DataServiceException
	 */
	public Integer fetchRecordsCount()throws DataServiceException;

	public Integer fetchRecordsCountForWaiting()throws DataServiceException;

	public List<InHomeOutBoundNotificationVO> fetchWaitingRecords(Integer recordsProcessingLimit) throws DataServiceException;
	

}