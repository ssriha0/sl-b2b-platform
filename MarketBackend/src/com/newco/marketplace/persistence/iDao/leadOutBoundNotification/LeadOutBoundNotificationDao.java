package com.newco.marketplace.persistence.iDao.leadOutBoundNotification;

import java.util.List;

import com.newco.marketplace.leadoutboundnotification.vo.LeadOutBoundNotificationVO;
import com.newco.marketplace.leadoutboundnotification.vo.LeadOutboundDetailsVO;
import com.newco.marketplace.exception.core.DataServiceException;

public interface LeadOutBoundNotificationDao {
	
	/**
	 * @param noOfRetries
	 * @return
	 * @throws DataServiceException
	 */
	public List<LeadOutBoundNotificationVO> fetchRecords(Integer noOfRetries, Integer recordsProcessingLimit)throws DataServiceException;
	/**
	 * @param noOfRetries
	 * @return
	 * @throws DataServiceException
	 */
	public Integer fetchRecordsCount(Integer noOfRetries) throws DataServiceException;
	/**
	 * @return
	 * @throws DataServiceException
	 */
	public List<LeadOutBoundNotificationVO> fetchFailedRecords() throws DataServiceException;
	/**
	 * @param failureList
	 * @throws DataServiceException
	 */
	public void setEmailIndicator(List<String> failureList) throws DataServiceException;
	/**
	 * @param appKey
	 * @return
	 * @throws DataServiceException
	 */
	public String getPropertyFromDB(String appKey)	throws DataServiceException;
	/**
	 * @param leadIds
	 * @throws DataServiceException
	 */
	public void updateStatus(List<String> leadIds) throws DataServiceException;
	/**
	 * @param leadOutBoundNotificationVO
	 * @throws DataServiceException
	 */
	public void updateNotification(LeadOutBoundNotificationVO leadOutBoundNotificationVO) throws DataServiceException;
	/**
	 * @param leadId
	 * @return
	 * @throws DataServiceException
	 */
	public LeadOutboundDetailsVO fetchLeadDetails(String leadId) throws DataServiceException;
	/**
	 * @param leadOutBoundNotificationVO
	 * @throws DataServiceException
	 */
	public void insertLeadOutBoundDetails(LeadOutBoundNotificationVO leadOutBoundNotificationVO) throws DataServiceException;
}