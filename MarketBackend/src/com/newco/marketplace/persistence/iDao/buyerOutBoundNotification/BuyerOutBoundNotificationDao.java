package com.newco.marketplace.persistence.iDao.buyerOutBoundNotification;

import java.util.HashMap;
import java.util.List;

import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.newco.marketplace.buyeroutboundnotification.vo.BuyerOutboundFailOverVO;
import com.newco.marketplace.buyeroutboundnotification.vo.BuyerOutboundNotificationVO;
import com.newco.marketplace.buyeroutboundnotification.vo.CustomrefsOmsVO;
import com.newco.marketplace.buyeroutboundnotification.vo.RequestVO;
import com.newco.marketplace.buyeroutboundnotification.vo.SOCustRefOutboundNotificationVO;
import com.newco.marketplace.buyeroutboundnotification.vo.SOLocationNotesOutboundNotificationVO;
import com.newco.marketplace.dto.vo.alert.AlertTaskVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;

/**
 * @author Infosys
 * Interface to fetch the notfication details
 *
 */
public interface BuyerOutBoundNotificationDao {
	public HashMap<String,BuyerOutboundNotificationVO> getServiceOrderDetails(List<String> soIdList)
			throws DataServiceException;   
	public BuyerOutboundNotificationVO getScheduleDetails(String soId)
	throws DataServiceException;
	public BuyerOutboundNotificationVO getLoggingDetails(String soId)
	throws DataServiceException;
	public BuyerOutboundNotificationVO getCounterOfferDetails(BuyerOutboundNotificationVO counterOffer)
	throws DataServiceException;
	public List<BuyerOutboundNotificationVO> getCounterOfferDetailsForGroup(BuyerOutboundNotificationVO counterOffer)
	throws DataServiceException;
	public List<String> getSoIdsForGroup(String groupId)
	throws DataServiceException;
	public String getReasonCode(Integer reasonCodeId) throws DataServiceException;
	public String getRescheduleReason(Integer reasonCodeId) throws DataServiceException;
	public SOLocationNotesOutboundNotificationVO getLocationNotes(String soId)
			throws DataServiceException;

	public List<SOCustRefOutboundNotificationVO> getCustRefs(String soId)
			throws DataServiceException;

	public Integer getOrderNo(String soId);
	public RequestVO getWebServiceDetails(int buyerId)
			throws DataServiceException;

	public void insertBuyerOutboundNotification(
			BuyerOutboundFailOverVO failoverVO) throws DataServiceException;

	public Integer generateSeqNO() throws DataServiceException;

	public void updateSuccessIndicator(String seqNo, boolean update)
			throws DataServiceException;

	public CustomrefsOmsVO getCustomReferenceOms(Integer orderId)
			throws DataServiceException;
	public BuyerOutboundFailOverVO checkNotification(String soId)throws DataServiceException;;
	public List<BuyerOutboundFailOverVO> fetchRecords()throws DataServiceException;
	public void updateActiveIndFalse(String seqNo)throws DataServiceException;
	public void updateActiveIndTrue(String seqNo)throws DataServiceException;
	
	public void updateActiveIndicator(String soId)throws DataServiceException;
	public void updateActiveIndicator(BuyerOutboundFailOverVO buyerOutboundFailOverVO) throws DataServiceException ;
	public List<BuyerOutboundFailOverVO> getXml()throws DataServiceException;
	public void alertMailFailover()throws DataServiceException;
	public void updateNotification(BuyerOutboundFailOverVO failOverVO)throws DataServiceException;
	public void insertInAlertTask(AlertTaskVO alertTask)throws DataServiceException;
	public boolean addAlertToQueue(AlertTask alertTask) throws DataServiceException;

	public void insertInBuyerOutBound(String soId)throws DataServiceException;
	public void updateSuccessIndicatorForApi(BuyerOutboundFailOverVO failOver)throws DataServiceException;
	public List<String> getResonCodeDetailsforCounterOffer(Integer soRoutedProviderId)
	throws DataServiceException;
	public BuyerOutboundFailOverVO fetchNotification(String sequenceNo)	throws DataServiceException;
	
	public String getPropertyFromDB(String appKey)	throws DataServiceException;
	
	public BuyerOutboundFailOverVO getRequestWithoutNotes(String soId)throws DataServiceException;
	
	public String getServiceLocationTimeZone(String soID)throws DataServiceException;
	
	public void excludeNotification(BuyerOutboundFailOverVO buyerOutboundFailOverVO) throws DataServiceException ;

	public Integer getBuyerIdForSo(String soId)throws DataServiceException;
	public List<Integer> getTierRoutedProviders(String soId)throws DataServiceException;


}
