package com.newco.marketplace.leadoutboundnotification.service;

import java.util.List;

import com.newco.marketplace.leadoutboundnotification.vo.LeadOutBoundNotificationVO;
import com.newco.marketplace.exception.core.BusinessServiceException;

public interface ILeadOutBoundNotificationService {
	/**
	 * @param noOfRetries
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<LeadOutBoundNotificationVO> fetchRecords(Integer noOfRetries, Integer recordsProcessingLimit)throws BusinessServiceException;
	/**
	 * @param noOfRetries
	 * @return
	 * @throws BusinessServiceException
	 */
	public Integer fetchRecordsCount(Integer noOfRetries) throws BusinessServiceException;
	/**
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<LeadOutBoundNotificationVO> fetchFailedRecords() throws BusinessServiceException;
	/**
	 * @param failureList
	 * @throws BusinessServiceException
	 */
	public void setEmailIndicator(List<String> failureList) throws BusinessServiceException;
	/**
	 * @param appKey
	 * @return
	 * @throws BusinessServiceException
	 */
	public String getPropertyFromDB(String appKey)	throws BusinessServiceException;
	/**
	 * @param leadOutBoundNotificationVO
	 * @throws BusinessServiceException
	 */
	public void updateNotification(LeadOutBoundNotificationVO leadOutBoundNotificationVO)throws BusinessServiceException;
	/**
	 * @param leadId
	 * @throws BusinessServiceException
	 */
	public void insertLeadOutBoundDetails(String leadId) throws BusinessServiceException;
}
