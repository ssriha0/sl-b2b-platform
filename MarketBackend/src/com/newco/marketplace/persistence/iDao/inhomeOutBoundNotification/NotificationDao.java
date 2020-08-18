package com.newco.marketplace.persistence.iDao.inhomeOutBoundNotification;

import java.util.List;
import java.util.Map;

import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.inhomeoutboundnotification.vo.CustomReferenceVO;
import com.newco.marketplace.inhomeoutboundnotification.vo.InHomeOutBoundNotificationVO;
import com.newco.marketplace.inhomeoutboundnotification.vo.InHomeRescheduleVO;
import com.newco.marketplace.inhomeoutboundnotification.vo.InHomeSODetailsVO;


public interface NotificationDao {
	
	
	/**Description:To get the outbound_notfication_flag
	 * @param appKey
	 * @return
	 * @throws DataServiceException
	 */
	public String getOutBoundFlag(String appKey) throws DataServiceException;
	
	
	/**Description:To get message for statusAndSubstatus
	 * @param StatusId
	 * @param subStatusId
	 * @return
	 * @throws DataServiceException
	 */
	public String getMessageForStatusAndSubStatus(Integer StatusId,Integer subStatusId) throws DataServiceException;
	
	
	/**Description:To get the NPSIndicator
	 * @param SOId
	 * @return
	 * @throws DataServiceException
	 */
	public Integer getNpsIndicator(String SOId) throws DataServiceException;
	
	/**Description:To get serviceOrder Details
	 * @param paramMap
	 * @return
	 * @throws DataServiceException
	 */
	public List<CustomReferenceVO> getDetailsOfSo(Map paramMap) throws DataServiceException;

	/**Description:To insert serviceOrder Details
	 * @param notificationVO
	 */
	public void insertInHomeOutBoundDetails(InHomeOutBoundNotificationVO notificationVO)throws DataServiceException;


	/**
	 * @return
	 * @throws DataServiceException
	 */
	public Integer generateSeqNO()throws DataServiceException;
	
	public InHomeRescheduleVO getSoDetailsForReschedule(InHomeRescheduleVO inHomeRescheduleVO)throws DataServiceException;
	
	
	public InHomeSODetailsVO getSoDetailsForNotes(InHomeSODetailsVO inHomeSODetailsVO)throws DataServiceException;

	
	/**Description://R12_0 fetching so details for revisit needed (Service Operations API)
	 * To get serviceOrder Details
	 * @param paramMap
	 * @return
	 * @throws DataServiceException
	 */
	public List<CustomReferenceVO> getDetailsOfInHomeSo(Map paramMap) throws DataServiceException;

	/**SL-21241
	 * Get the jobCode for main category of the SO
	 * @param soId
	 * @return
	 * @throws DataServiceException
	 */
	public String getJobCodeForMainCategory(String soId) throws DataServiceException;
	
	/**logging notifications to the relay services 
	 * @param soId
	 * @param string
	 * @param responseCode
	 * @throws DataServiceException
	 */
	public void loggingRelayServicesNotification(String soId, String request,
			int responseCode) throws DataServiceException;
	// SL-21469
	public Integer getBuyerId(String soId) throws DataServiceException;
	

}
