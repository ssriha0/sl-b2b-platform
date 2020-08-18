package com.newco.marketplace.inhomeoutboundnotification.service;

import java.util.List;

import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.inhomeoutboundnotification.vo.InHomeOutBoundNotificationVO;
import com.newco.marketplace.inhomeoutboundnotification.vo.NotificationOwnerDetails;

public interface InHomeNotificationFailureService {

	/**
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<InHomeOutBoundNotificationVO > fetchFailedRecords(Integer recordsProcessingLimit) throws BusinessServiceException;

	/**
	 * @param flagKey
	 * @return
	 */
	public String getApplicationFlag(String flagKey) throws BusinessServiceException;

	/**
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<NotificationOwnerDetails> getNotificationOwnerDetails() throws BusinessServiceException;
	
	public void setEmailIndicator(List<String> failureList) throws BusinessServiceException;
	
	/**to get the app_value
	 * @param appKey
	 * @return
	 * @throws BusinessServiceException
	 */
	public String getPropertyFromDB(String appKey)	throws BusinessServiceException;
	
	/**
	 * @return
	 * @throws BusinessServiceException
	 */
	public Integer fetchRecordsCount()throws BusinessServiceException;

	/**to get the count
	 * @param 
	 * @return Count
	 * @throws BusinessServiceException
	 */
	public Integer fetchRecordsCountForWaiting()throws BusinessServiceException;
	/**
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<InHomeOutBoundNotificationVO> fetchWaitingRecords(Integer recordsProcessingLimit)throws BusinessServiceException;

}
