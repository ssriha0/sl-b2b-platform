package com.newco.marketplace.inhomeoutboundnotification.service;

import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.inhomeoutboundnotification.vo.InHomeRescheduleVO;
import com.newco.marketplace.inhomeoutboundnotification.vo.InHomeSODetailsVO;


public interface INotificationService {
	/**Description:To get the value of outBoundFlag
	 * @param appKey
	 * @return
	 * @throws BusinessServiceException
	 */
	public String getOutBoundFlag(String appKey) throws BusinessServiceException;
	/**Description:To insert into DB for status change
	 * @param soId
	 * @param statusId
	 * @param substatusId
	 * @param empId 
	 * @param resourceId 
	 * @throws BusinessServiceException
	 */
	public void insertNotification(String soId,Integer statusId,Integer substatusId,Integer empId) throws BusinessServiceException;
	
	/**
	 * @param soId
	 * @param message
	 * @throws BusinessServiceException
	 */
	public void insertNotification(String soId, String message) throws BusinessServiceException;

	/**Description:To Validate eligibility of service order for NPS notification
	 * @param buyerId
	 * @param currentServiceOrderId
	 * @return
	 * @throws BusinessServiceException 
	 */
	public boolean validateNPSNotificationEligibility(Integer buyerId,String currentServiceOrderId)throws BusinessServiceException;
	
	
	/**Description:To insert into DB for add note
	 * @param soId
	 * @param noteTypeId
	 * @param roleId
	 * @param subjMessage
	 * @param resourceId
	 * @param empId
	 * @throws BusinessServiceException 
	 */ 
	public void insertNotification(InHomeSODetailsVO inHomeSODetailsVO) throws BusinessServiceException;
	
	public void insertNotification(InHomeRescheduleVO homeRescheduleVO)throws BusinessServiceException; 
	
	public InHomeRescheduleVO getSoDetailsForReschedule(InHomeRescheduleVO inHomeRescheduleVO)throws BusinessServiceException; 
	
	public InHomeSODetailsVO getSoDetailsForNotes(InHomeSODetailsVO inHomeSODetailsVO)throws BusinessServiceException; 
	
	public String createRescheduleMessageForProviderRescheduleAPI(InHomeRescheduleVO result);
	
	//R12_0 inserting request xml of Service Operations API for revisit needed 
	public void insertNotificationServiceOperationsAPI(String soId,InHomeRescheduleVO input) throws BusinessServiceException; 
}
