package com.newco.marketplace.persistance.daoImpl.inhomeOutBoundNotification;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.inhomeoutboundnotification.vo.CustomReferenceVO;
import com.newco.marketplace.inhomeoutboundnotification.vo.InHomeOutBoundNotificationVO;
import com.newco.marketplace.inhomeoutboundnotification.vo.InHomeRescheduleVO;
import com.newco.marketplace.inhomeoutboundnotification.vo.InHomeSODetailsVO;
import com.newco.marketplace.persistence.iDao.inhomeOutBoundNotification.NotificationDao;
import com.sears.os.dao.impl.ABaseImplDao;


public class NotificationDaoImpl extends  ABaseImplDao implements NotificationDao{
	private static final Logger LOGGER = Logger.getLogger(NotificationDaoImpl.class.getName());

	//To get the outbound_notfication_flag
	public String getOutBoundFlag(String appKey) throws DataServiceException {
		// TODO Auto-generated method stub
		
		String outBoundFlag = "";
		try{
			outBoundFlag = (String) queryForObject("getAppFlagValue.query",appKey);
		}
		catch(Exception e){
			LOGGER.error("Exception in getting outboundFlag value " + e.getMessage(), e);
		
		}
		return outBoundFlag;
	
	}

	//To get message for statusAndSubstatus
	public String getMessageForStatusAndSubStatus(Integer StatusId,Integer subStatusId) throws DataServiceException {
		// TODO Auto-generated method stub
		String message="";
		Map<String,Integer> messageMap=new HashMap<String, Integer>();
		messageMap.put("statusId", StatusId);
		messageMap.put("subStatusId", subStatusId);
		
		try{
			message = (String) queryForObject("getMessageForStatusAndSubStatus.query",messageMap);
		}
		catch(Exception e){
			LOGGER.error("Exception in getMessageForStatusAndSubStatus" + e.getMessage(), e);
		
		}
		return message;
	}

	//To get the NPSIndicator
	public Integer getNpsIndicator(String SOId) throws DataServiceException {
		// TODO Auto-generated method stub
		Integer npsIndicator = 0;
		try{
			npsIndicator = (Integer) queryForObject("getNpsIndicator.query",SOId);
		}
		catch(Exception e){
			LOGGER.error("Exception in getNpsIndicator" + e.getMessage(), e);
		
		}
		return npsIndicator;
		
	}

	
	public List<CustomReferenceVO> getDetailsOfSo(Map paramMap)throws DataServiceException {
		List<CustomReferenceVO> list = null;
		try{
			list =  queryForList("getDetailsOfSo.query",paramMap);
		}
		catch(Exception e){
			LOGGER.error("Exception in getDetailsOfSo" + e.getMessage(), e);
		}
		return list;
	}
  
	//R12_0 fetching so details for revisit needed (Service Operations API)
	public List<CustomReferenceVO> getDetailsOfInHomeSo(Map paramMap)throws DataServiceException {
		List<CustomReferenceVO> list = null;
		try{
			list =  queryForList("getDetailsOfInHomeSo.query",paramMap);
		}
		catch(Exception e){
			LOGGER.error("Exception in getDetailsOfInHomeSo" + e.getMessage(), e);
		}
		return list;
	}
	
	public void insertInHomeOutBoundDetails(InHomeOutBoundNotificationVO notificationVO)throws DataServiceException  {
		try{
			insert("insertinHomeOutBoundDetails.query",notificationVO);
		}catch(Exception e){
				LOGGER.info("Exception occurred in insertLeadOutBoundDetails: "+e.getMessage());
				throw new DataServiceException(e.getMessage());
		 }
	}
		
	public Integer generateSeqNO() throws DataServiceException {
		Integer seqNo= (Integer) getSqlMapClientTemplate().queryForObject("generateSeqNo.query");
		if(null==seqNo){
			seqNo = 0;
		}
		seqNo=seqNo+1;
		return seqNo;
	}
	
	public InHomeRescheduleVO getSoDetailsForReschedule(InHomeRescheduleVO inHomeRescheduleVO)throws DataServiceException{
		InHomeRescheduleVO result = new InHomeRescheduleVO();
		try{
			result = (InHomeRescheduleVO) queryForObject("getSoDetailsForReschedule.query",inHomeRescheduleVO);
		}
		catch(Exception e){
			LOGGER.error("Exception in getting SO Details" + e.getMessage());
		
		}
		return result;
	}
	
	
	
	public InHomeSODetailsVO getSoDetailsForNotes(InHomeSODetailsVO inHomeSODetailsVO)throws DataServiceException{
		InHomeSODetailsVO result = new InHomeSODetailsVO();
		try{
			result = (InHomeSODetailsVO) queryForObject("getSoDetailsForNotes.query",inHomeSODetailsVO);
		}
		catch(Exception e){
			LOGGER.error("Exception in getting SO Details" + e.getMessage());
		
		}
		return result;
	}

	/**SL-21241
	 * Get the jobCode for main category of the SO
	 * @param soId
	 * @return
	 * @throws DataServiceException
	 */
	public String getJobCodeForMainCategory(String soId) throws DataServiceException {
		
		String jobCode = null;
		try{
			jobCode = (String) queryForObject("getJobCodeForMainCateory.query",soId);
		}
		catch(Exception e){
			LOGGER.error("Exception occurred in NotificationDaoImpl.getJobCodeForMainCategory() due to " + e, e);
		}
		return jobCode;
	}
	
	/**
	 * logging notifications to the relay services
	 * @param soId
	 * @param request
	 * @param responseCode
	 * @throws DataServiceException
	 */
	public void loggingRelayServicesNotification(String soId, String request,
			int responseCode) throws DataServiceException {
		HashMap params = new HashMap();
		params.put("soId",soId);
		params.put("request",request);
		params.put("response",responseCode);
		try{
			insert("logRelayServicesNotification.insert", params);
		}catch (Exception e) {
			throw new DataServiceException("Exception in loggingRelayServicesNotification() of NotificationDaoImpl", e);
		}
	}
	// SL-21469
	public Integer getBuyerId(String soId) throws DataServiceException{
		
		Integer buyerId = null;
		try{
			// code change for SLT-2112
			Map<String, String> parameter = new HashMap<String, String>();
			parameter.put("soId", soId);
			 buyerId=(Integer) queryForObject("getBuyerIdForSo.query", parameter);
		}
		catch(Exception e){
			LOGGER.error("Exception occurred in NotificationDaoImpl.getBuyerId() due to " + e, e);
		}
		return buyerId;
	}
	
	
}
