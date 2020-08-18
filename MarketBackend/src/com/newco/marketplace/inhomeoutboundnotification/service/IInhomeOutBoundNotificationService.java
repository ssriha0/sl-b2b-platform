package com.newco.marketplace.inhomeoutboundnotification.service;

import java.util.HashMap;
import java.util.List;

import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.inhomeoutboundnotification.vo.InHomeOutBoundNotificationVO;
import com.newco.marketplace.inhomeoutboundnotification.vo.NotificationOwnerDetails;

/**
 * @author Infosys
 *
 */

public interface IInhomeOutBoundNotificationService {

	/**to get the app_value
	 * @param appKey
	 * @return
	 * @throws BusinessServiceException
	 */
	public String getPropertyFromDB(String appKey)	throws BusinessServiceException;

	/**to get the value of inhome_outbound_flag
	 * @param inhomeNpsFlag
	 * @return
	 * @throws BusinessServiceException
	 */
	public String getInHomeNpsFlag(String inhomeNpsFlag) throws BusinessServiceException;

	/**get the count of records in waiting & retry status
	 * @param noOfRetries
	 * @param serviceId
	 * @return
	 * @throws BusinessServiceException
	 */
	public Integer fetchRecordsCount(Integer noOfRetries, String serviceId) throws BusinessServiceException;
	
	/**get the records in waiting & retry status
	 * @param noOfRetries
	 * @param serviceId
	 * @param recordsProcessingLimit
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<InHomeOutBoundNotificationVO> fetchRecords(Integer noOfRetries, String serviceId, Integer recordsProcessingLimit) throws BusinessServiceException;

	/**fetch the request header & url for serviceId
	 * @param serviceId
	 * @return
	 */
	public HashMap <String, String> fetchApiDetails(String serviceId) throws BusinessServiceException;

	/**
	 * @param notification
	 * @throws BusinessServiceException
	 */
	public void updateNotification(InHomeOutBoundNotificationVO notification) throws BusinessServiceException;
	/**
	 * @param soId
	 * @throws BusinessServiceException
	 */
	public void updateNPSIndicator(String soId)throws BusinessServiceException;
	
	
	/**
	 * @param errorCode
	 * @throws BusinessServiceException
	 */
	public NotificationOwnerDetails getSystemDownEmailAddress(String errorCode) throws BusinessServiceException;
	
	
	public void updateSystemDownStatus(List<String> notificationIds) throws BusinessServiceException;

}
