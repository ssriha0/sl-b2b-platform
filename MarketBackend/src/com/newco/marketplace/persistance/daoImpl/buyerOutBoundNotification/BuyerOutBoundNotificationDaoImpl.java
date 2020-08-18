package com.newco.marketplace.persistance.daoImpl.buyerOutBoundNotification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.newco.marketplace.buyeroutboundnotification.vo.BuyerOutboundFailOverVO;
import com.newco.marketplace.buyeroutboundnotification.vo.BuyerOutboundNotificationVO;
import com.newco.marketplace.buyeroutboundnotification.vo.CustomrefsOmsVO;
import com.newco.marketplace.buyeroutboundnotification.vo.RequestVO;
import com.newco.marketplace.buyeroutboundnotification.vo.SOCustRefOutboundNotificationVO;
import com.newco.marketplace.buyeroutboundnotification.vo.SOLocationNotesOutboundNotificationVO;
import com.newco.marketplace.dto.vo.alert.AlertTaskVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.buyerOutBoundNotification.BuyerOutBoundNotificationDao;
import com.sears.os.dao.impl.ABaseImplDao;


public class BuyerOutBoundNotificationDaoImpl extends ABaseImplDao implements BuyerOutBoundNotificationDao {	 
	
	public HashMap<String,BuyerOutboundNotificationVO> getServiceOrderDetails(List<String> soIdList)
			throws DataServiceException {  
		HashMap<String,BuyerOutboundNotificationVO> buyerOutboundNotificationVOMap = new HashMap<String, BuyerOutboundNotificationVO>();
		List<BuyerOutboundNotificationVO> buyerNotificationList=new ArrayList<BuyerOutboundNotificationVO>();
		HashMap soIdListMap = new HashMap();
   	    soIdListMap.put("soIdList", soIdList);
   	    buyerNotificationList =queryForList("getServiceOrderSpecificDetails.query", soIdListMap);
   	    for(BuyerOutboundNotificationVO notifyList:buyerNotificationList){
   	    	if(null!=notifyList.getSoId()){
   	    		buyerOutboundNotificationVOMap.put(notifyList.getSoId(), notifyList);
   	    	}
   	    }
   	    
		return buyerOutboundNotificationVOMap;

	}
     //get schedule details for service order. 
	public BuyerOutboundNotificationVO getScheduleDetails(String soId)
	throws DataServiceException
	{
		BuyerOutboundNotificationVO buyerOutboundNotificationVO = new BuyerOutboundNotificationVO();
		buyerOutboundNotificationVO = (BuyerOutboundNotificationVO) queryForObject(
				"getScheduleDetails.query", soId);
		return buyerOutboundNotificationVO;
	}
	
	//get logging comment for schedule to obtain reasonCode & comment
	public BuyerOutboundNotificationVO getLoggingDetails(String soId)
	throws DataServiceException
	{
		BuyerOutboundNotificationVO buyerOutboundNotificationVO = new BuyerOutboundNotificationVO();
		buyerOutboundNotificationVO = (BuyerOutboundNotificationVO) queryForObject(
				"getLoggingDetails.query", soId);
		return buyerOutboundNotificationVO;
		
	}
	
	//get counter offer details for service order.
	public BuyerOutboundNotificationVO getCounterOfferDetails(BuyerOutboundNotificationVO counterOffer)
	throws DataServiceException
	{
		BuyerOutboundNotificationVO buyerOutboundNotificationVO = new BuyerOutboundNotificationVO();
		buyerOutboundNotificationVO = (BuyerOutboundNotificationVO) queryForObject(
				"getCounterOfferDetails.query", counterOffer);
		return buyerOutboundNotificationVO;
	}
	
	//get counter offer details for grouped order
	public List<BuyerOutboundNotificationVO> getCounterOfferDetailsForGroup(BuyerOutboundNotificationVO counterOffer)
	throws DataServiceException
	{
		List<BuyerOutboundNotificationVO> buyerOutboundNotificationList
		= new ArrayList<BuyerOutboundNotificationVO>();
		buyerOutboundNotificationList = (List<BuyerOutboundNotificationVO>) queryForList(
				"getCounterOfferDetailsForGroup.query", counterOffer);
		return buyerOutboundNotificationList;
	}
	
	// get soIds in a grouped order
	public List<String> getSoIdsForGroup(String groupId)
	throws DataServiceException
	{
		List<String> soIds=new ArrayList<String>();
	
		soIds = (List<String>) queryForList(
				"getSoIdsForGroup.query", groupId);
		return soIds;	
	}
	
	// get reasonCode for reschedule
	public String getReasonCode(Integer reasonCodeId)
	throws DataServiceException
	{
		String reasonCode=null;
		reasonCode = (String) queryForObject("getReasonCode.query", reasonCodeId);
		
		return reasonCode;	
	}
	
	// below method added as part of SL-19240
	public String getRescheduleReason(Integer reasonCodeId) throws DataServiceException
	{
		String reasonCode=null;
		reasonCode = (String) queryForObject("getRescheduleReasonCode.query", reasonCodeId);;
		return reasonCode;	
	}
	/**
	 * Get the location notes
	 */
	public SOLocationNotesOutboundNotificationVO getLocationNotes(String soId)
			throws DataServiceException {
		SOLocationNotesOutboundNotificationVO locationNotesVO = new SOLocationNotesOutboundNotificationVO();
		try {		
			locationNotesVO = (SOLocationNotesOutboundNotificationVO) queryForObject("getLocationNotes", soId);
		}
		catch (Exception ex) {
			String strMessage = "Unexpected error while retrieving location notes for so:"+soId;
			logger.error(strMessage, ex);
			throw new DataServiceException(strMessage, ex);
		}
		return locationNotesVO;
	}

	/**
	 * Get the custom references
	 */
	public List<SOCustRefOutboundNotificationVO> getCustRefs(String soId)
			throws DataServiceException {
		List<SOCustRefOutboundNotificationVO> custRefVO = null;
		try {
			custRefVO = queryForList("getCustRef", soId);
		} catch (Exception ex) {
			String strMessage = "Unexpected error while retrieving custom refs for so:"+soId;
			logger.error(strMessage, ex);
			throw new DataServiceException(strMessage, ex);
		}
		return custRefVO;
	}
	
	/**
	 * Get the schedule details
	 */
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.buyerOutBoundNotification.BuyerOutBoundNotificationDao#getWebServiceDetails(int)
	 * method fetches data from table buyer_outbound_service.
	 */
	public RequestVO getWebServiceDetails(int buyerId)
			throws DataServiceException {
		RequestVO requestVO = new RequestVO();
		requestVO = (RequestVO) queryForObject("getRequestdata.query", buyerId);
		if (null != requestVO) {
			return requestVO;
		} else {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.buyerOutBoundNotification.BuyerOutBoundNotificationDao#insertBuyerOutboundNotification(com.newco.marketplace.buyeroutboundnotification.vo.BuyerOutboundFailOverVO)
	 * Insert a new row in  buyer_outbound_notification table
	 */
	public void insertBuyerOutboundNotification(BuyerOutboundFailOverVO failoverVO) throws DataServiceException{
		insert("insert_buyerFailover.query", failoverVO);
		
		
		
	}
/*
 * (non-Javadoc)
 * @see com.newco.marketplace.persistence.iDao.buyerOutBoundNotification.BuyerOutBoundNotificationDao#generateSeqNO()
 * This method generate Sequence Number.
 */
	public Integer generateSeqNO() throws DataServiceException {
		Integer seqNo= (Integer) getSqlMapClientTemplate().queryForObject("generateSeqNo.query");
		if(null==seqNo){
			seqNo = 0;
		}
		seqNo=seqNo+1;
		return seqNo;
	}


	//get orderNo for a service order
	public Integer getOrderNo(String soId)
	{
		try {

			return (Integer) queryForObject("getserviceOrderId.query", soId);
		} catch (IllegalStateException ex) {
			
			 return null;
		}
		catch (Exception ex) {
			
			 return null;
		}	
	}
	
	public void updateSuccessIndicator(String seqNo,boolean failureInd)
			throws DataServiceException {
		BuyerOutboundFailOverVO failureVO=new BuyerOutboundFailOverVO();
		failureVO.setActiveInd(false);
		try
		{
			if(failureInd)
			{
				failureVO.setActiveInd(false);
				
			}else{
				failureVO.setActiveInd(true);
			}
			
		insert("insertSuccessInd.insert",failureVO);
		}
		catch(Exception ex)
		{
			
		}
	}
	
		
		
	
	
	public CustomrefsOmsVO  getCustomReferenceOms(Integer orderId) throws DataServiceException
	{
		CustomrefsOmsVO customref= new CustomrefsOmsVO();
		customref=(CustomrefsOmsVO) queryForObject("fetchCustomrefOms.query", orderId);
		if (null != customref) {
			return customref;
		} else {
			return null;
		}
	}

	
	/*
	 * (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.buyerOutBoundNotification.BuyerOutBoundNotificationDao#checkNotification(java.lang.String)
	 * this method fetches xml from  buyer_outbound_notification table.
	 */

	public BuyerOutboundFailOverVO checkNotification(String soId)
			throws DataServiceException {
		BuyerOutboundFailOverVO failOverVO = new BuyerOutboundFailOverVO();
		failOverVO = (BuyerOutboundFailOverVO) queryForObject(
				"fetchBuyeOutboundNotificationXml.query", soId);
		return failOverVO;
	}

	public List<BuyerOutboundFailOverVO> fetchRecords()
			throws DataServiceException {
		List<BuyerOutboundFailOverVO> failOver = (List<BuyerOutboundFailOverVO>) queryForList(
				"fetchRecords.query");

		return failOver;
	}

	public void updateActiveIndFalse(String seqNo) throws DataServiceException {
		update("updateActiveIndFalse.query", seqNo);

	}

	public void updateActiveIndTrue(String seqNo) throws DataServiceException {
		update("updateActiveIndTrue.query", seqNo);

	}

	public void updateActiveIndicator(String soId) throws DataServiceException {
		update("update_activeIndicator.query", soId);

	}

	public void updateActiveIndicator(BuyerOutboundFailOverVO buyerOutboundFailOverVO) throws DataServiceException {
	
		update("update_activeIndicator.query", buyerOutboundFailOverVO);

	}


	public List<BuyerOutboundFailOverVO> getXml() throws DataServiceException {
		// TODO Auto-generated method stub
		return null;
	}


	public void alertMailFailover() throws DataServiceException {
		// TODO Auto-generated method stub
		
	}





	public void updateNotification(BuyerOutboundFailOverVO failOverVO)throws DataServiceException {
		update("updateBuyerNotification.query", failOverVO);
		
	}
	
	public void insertInAlertTask(AlertTaskVO alertTask) throws DataServiceException {
		//insert("insertInAlertTask.query",alertTask);
		
	}
	public boolean addAlertToQueue(AlertTask alertTask) throws DataServiceException
	{
		try {
            insert("alert.insert", alertTask);
        } catch (Exception e) {
            throw new DataServiceException("Exception occured at AlertDaoImpl-addAlertToQueue", e);
        }
        return true;
	}




	public void insertInBuyerOutBound(String soId) throws DataServiceException {
		// insert("insertInBuyerOutBound.query",soId);
		
	}
	public void updateSuccessIndicatorForApi(BuyerOutboundFailOverVO failOver)throws DataServiceException
	{
		update("updateSuccesIndForApi.query",failOver);
	}
	// get reasonCode for counter Offer.
	public List<String> getResonCodeDetailsforCounterOffer(Integer soRoutedProviderId)
	throws DataServiceException
	{
		List<String> reasonCodeList=new ArrayList<String>();
		reasonCodeList = (List<String>) queryForList(
				"getResonCodeDetailsforCounterOffer.query", soRoutedProviderId);
		return reasonCodeList;	
	}
	
	// fetch notification entry for a sequence no
	public BuyerOutboundFailOverVO fetchNotification(String sequenceNo)
	throws DataServiceException {
		BuyerOutboundFailOverVO failOver = (BuyerOutboundFailOverVO) queryForObject(
				"fetchNotification.query",sequenceNo);
		
		return failOver;
}
	
	//get value from application_properties for a key
	public String getPropertyFromDB(String appKey)	throws DataServiceException
	{
	String value=(String) queryForObject("getAppKeyValue.query",appKey);
	return value;
	}
	
	
   //get notification request xml without Notes for a service order
	public BuyerOutboundFailOverVO getRequestWithoutNotes(String soId)throws DataServiceException
	{
		BuyerOutboundFailOverVO vo=new BuyerOutboundFailOverVO();
		vo.setSoId(soId);
		vo.setXml("%<MESSAGES>%");
		BuyerOutboundFailOverVO failOver = (BuyerOutboundFailOverVO) queryForObject(
				"getRequestWithoutNotes.query",vo);
		
		return failOver; 
	}

    // mark status as excludes and update active_ind as 0
	public void excludeNotification(BuyerOutboundFailOverVO buyerOutboundFailOverVO) throws DataServiceException 
	{
		update("excludeNotification.query",buyerOutboundFailOverVO);
	}
	//Getting timezone for so
    public String getServiceLocationTimeZone(String soId) throws DataServiceException{
    	String timeZone=(String) queryForObject("getServiceLocationTimeZone.query", soId);
		return timeZone;
    	
    }
    
	public Integer getBuyerIdForSo(String soId)throws DataServiceException
	{
		// code change for SLT-2112
		Map<String, String> parameter = new HashMap<String, String>();
		parameter.put("soId", soId);
		Integer buyerId=(Integer) queryForObject("getBuyerIdForSo.query", parameter);
		return buyerId;	
	}
	
	public List<Integer> getTierRoutedProviders(String soId)throws DataServiceException
	{
		List<Integer> reasonCodeList=new ArrayList<Integer>();
		reasonCodeList = (List<Integer>) queryForList(
				"getTierRoutedEligibleProviders.query", soId);
		return reasonCodeList;	
		
	}


}
	
	


