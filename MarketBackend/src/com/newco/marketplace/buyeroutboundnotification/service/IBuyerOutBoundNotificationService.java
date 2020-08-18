/*
 *	Date        Project       Author         Version

 * -----------  --------- 	-----------  	---------
 * 28-Jun-2012	KMSRTVST   Infosys				1.0
 * 
 * 
 */

package com.newco.marketplace.buyeroutboundnotification.service;

import java.util.HashMap;
import java.util.List;

import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.newco.marketplace.buyeroutboundnotification.beans.RequestMsgBody;
import com.newco.marketplace.buyeroutboundnotification.vo.BuyerOutboundFailOverVO;
import com.newco.marketplace.buyeroutboundnotification.vo.BuyerOutboundNotificationVO;
import com.newco.marketplace.buyeroutboundnotification.vo.CustomrefsOmsVO;
import com.newco.marketplace.buyeroutboundnotification.vo.RequestMessageVO;
import com.newco.marketplace.buyeroutboundnotification.vo.RequestVO;
import com.newco.marketplace.buyeroutboundnotification.vo.SOCustRefOutboundNotificationVO;
import com.newco.marketplace.buyeroutboundnotification.vo.SOLocationNotesOutboundNotificationVO;
import com.newco.marketplace.dto.vo.alert.AlertTaskVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;

/**
 * Interface for buyer notification.This class fetches the data from the
 * database
 * 
 */
public interface IBuyerOutBoundNotificationService {

	//check these 3 methods
	public void callNPSNotification(String soId);
	public BuyerOutboundFailOverVO callService(RequestMsgBody requestMsgBody,String soId) throws BusinessServiceException;
	public RequestMsgBody getNPSNotificationRequest(ServiceOrder targetSo,BuyerOutboundNotificationVO sourceSo)	throws BusinessServiceException;
	public String fetchResponse()throws BusinessServiceException;
	public void updateSuccessIndicator(String seqNo,boolean failureInd)throws BusinessServiceException;
	public CustomrefsOmsVO getCustomReferenceOms(String soId)throws BusinessServiceException;
	public List<Integer> getTierRoutedProviders(String soId)throws BusinessServiceException;
	public HashMap<String,BuyerOutboundNotificationVO> getServiceOrderDetails(List<String> soIdList)throws BusinessServiceException;
	public RequestVO getWebServiceDetails(int buyerId)throws BusinessServiceException;
	public BuyerOutboundFailOverVO insertBuyerOutboundNotification(RequestMsgBody requestMsgBody, String soId)throws BusinessServiceException;
	public String generateSeqNo()throws BusinessServiceException;
	public void checkNotification(RequestMsgBody newRequest,String soId)throws BusinessServiceException;
	
	public BuyerOutboundNotificationVO getScheduleDetails(String soId)
	throws BusinessServiceException;
	public String getReasonCode(Integer reasonCodeId);
	public String getRescheduleReason(Integer reasonCodeId);
	
	public BuyerOutboundNotificationVO getCounterOfferDetails(BuyerOutboundNotificationVO counterOffer)
	throws BusinessServiceException;
	
	public List<BuyerOutboundNotificationVO> getCounterOfferDetailsForGroup(BuyerOutboundNotificationVO counterOffer)
	throws BusinessServiceException;
	
	public List<String> getSoIdsForGroup(String groupId)
	throws BusinessServiceException;
	
	public BuyerOutboundNotificationVO getLoggingDetails(String soId)
	throws BusinessServiceException;
	
	public SOLocationNotesOutboundNotificationVO getLocationNotes(String soId)
			throws BusinessServiceException;
	public List<SOCustRefOutboundNotificationVO> getCustRefs(String soId)
			throws BusinessServiceException;
	public RequestMsgBody getNPSNotificationRequest(ServiceOrder fromFrontEnd,
			BuyerOutboundNotificationVO compareTo,String modifiedUserId)
			throws BusinessServiceException;
	public List<BuyerOutboundFailOverVO> fetchRecords()throws BusinessServiceException;
	
	public RequestMsgBody getNPSNotificationRequestForNotes(RequestMessageVO soNote)throws BusinessServiceException;
	public void updateNotification(BuyerOutboundFailOverVO failOverVO)throws BusinessServiceException;
	public void insertInAlertTask(AlertTaskVO alertTask)throws BusinessServiceException;
	public void addAlertToQueue(AlertTask alertTask) throws BusinessServiceException;

	public List<String> getResonCodeDetailsforCounterOffer(Integer soRoutedProviderId);
	public BuyerOutboundFailOverVO fetchNotification(String sequenceNo)	throws BusinessServiceException;
	public String getPropertyFromDB(String appKey)	throws BusinessServiceException;
	
	public String getMockedUpData(String date) throws BusinessServiceException;

	public Integer getBuyerIdForSo(String soId)throws BusinessServiceException;

}
