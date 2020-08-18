package com.newco.marketplace.persistence.iDao.inhomeOutBoundNotification;

import java.util.HashMap;
import java.util.List;

import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.inhomeoutboundnotification.vo.InHomeOutBoundNotificationVO;
import com.newco.marketplace.inhomeoutboundnotification.vo.NotificationOwnerDetails;

/**
 * @author Infosys
 *
 */
public interface IInHomeOutBoundNotificationDao {
	
	/**
	 * @param appKey
	 * @return
	 * @throws DataServiceException
	 */
	public String getPropertyFromDB(String appKey)	throws DataServiceException;
	
	/**
	 * @param inhomeNpsFlag
	 * @return
	 * @throws DataServiceException
	 */
	public String getPropertyFromApplicationFlag(String inhomeNpsFlag)throws DataServiceException;
	
	/**
	 * @param noOfRetries
	 * @return
	 * @throws DataServiceException
	 */
	public Integer fetchRecordsCount(Integer noOfRetries, String serviceId) throws DataServiceException;
	
	/**
	 * @param noOfRetries
	 * @param serviceId
	 * @param recordsProcessingLimit
	 * @return
	 * @throws DataServiceException
	 */
	public List<InHomeOutBoundNotificationVO> fetchRecords(Integer noOfRetries, String serviceId, 
			Integer recordsProcessingLimit)throws DataServiceException;
	
	/**
	 * @param leadIds
	 * @throws DataServiceException
	 */
	public void updateStatus(List<String> notificationIds) throws DataServiceException;
	
	/**get request header & url
	 * @param serviceId
	 * @return
	 */
	public HashMap<String, String> fetchApiDetails(String serviceId) throws DataServiceException;
	
	/**
	 * @param notification
	 * @throws DataServiceException
	 */
	public void updateNotification(InHomeOutBoundNotificationVO notification) throws DataServiceException;
	/**
	 * @param soId
	 * @throws DataServiceException
	 */
	public void updateNPSIndicator(String soId)throws DataServiceException;
	
	/**
	 * @param errorCode
	 * @throws DataServiceException
	 */
	
	public NotificationOwnerDetails getSystemDownEmailAddress(String errorCode) throws DataServiceException;
	
	
	public void updateSystemDownStatus(List<String> notificationIds) throws DataServiceException;
	
}