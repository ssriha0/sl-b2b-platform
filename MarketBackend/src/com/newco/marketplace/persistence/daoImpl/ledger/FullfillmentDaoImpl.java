/**
 * 
 */
package com.newco.marketplace.persistence.daoImpl.ledger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.ach.FieldDetailVO;
import com.newco.marketplace.dto.vo.fullfillment.EntityPromoCodesVO;
import com.newco.marketplace.dto.vo.fullfillment.FullfillmentAdminToolVO;
import com.newco.marketplace.dto.vo.fullfillment.FullfillmentEntryVO;
import com.newco.marketplace.dto.vo.fullfillment.FullfillmentRuleVO;
import com.newco.marketplace.dto.vo.fullfillment.FullfillmentVLAccountsVO;
import com.newco.marketplace.dto.vo.fullfillment.VLHeartbeatVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.FullfillmentConstants;
import com.newco.marketplace.interfaces.LedgerConstants;
import com.newco.marketplace.persistence.iDao.ledger.IFullfillmentDao;
import com.newco.marketplace.utils.StackTraceHelper;
import com.sears.os.dao.impl.ABaseImplDao;

/**
 * @author schavda
 * 
 */
public class FullfillmentDaoImpl extends ABaseImplDao implements IFullfillmentDao {

	private static final Logger logger = Logger.getLogger(FullfillmentDaoImpl.class.getName());
	Map<String,FullfillmentVLAccountsVO> dataMap = new HashMap<String,FullfillmentVLAccountsVO>();

	public List getFullfillmentRules(FullfillmentRuleVO fullfillmentRuleVO) throws DataServiceException {
		logger.debug("FullfillmentDaoImpl-->getFullfillmentRules()-->START");
		try {
			return queryForList("fullfillment_rule.query", fullfillmentRuleVO);
		} 
		catch (Exception ex) {
			logger.error("FullfillmentDaoImpl-->getFullfillmentRules()-->EXCEPTION-->", ex);
			throw new DataServiceException("FullfillmentDaoImpl-->getFullfillmentRules-->EXCEPTION", ex);
		}
	}
	public Integer getCountOfFullfillmentData() throws DataServiceException 
	{
		logger.debug("FullfillmentDaoImpl-->getFullfillmentRules()-->START");
		try {
			return (Integer)queryForObject("getCountOfFullfillmentData.query",null);
		} 
		catch (Exception ex) {
			logger.error("FullfillmentDaoImpl-->getFullfillmentRules()-->EXCEPTION-->", ex);
			throw new DataServiceException("FullfillmentDaoImpl-->getFullfillmentRules-->EXCEPTION", ex);
		}
	}
	
	public List<FullfillmentEntryVO> getAllFullfillmentData(int startNumber, int fetchSize) throws DataServiceException {
		logger.debug("FullfillmentDaoImpl-->getFullfillmentRules()-->START");
		try {
			// this method gets data in batches from startNumber position to fetchSize
			return getSqlMapClientTemplate().queryForList("fullfillmentDataForClosedLoopFile.query", null, startNumber, fetchSize);

		} 
		catch (Exception ex) {
			logger.error("FullfillmentDaoImpl-->getFullfillmentRules()-->EXCEPTION-->", ex);
			throw new DataServiceException("FullfillmentDaoImpl-->getFullfillmentRules-->EXCEPTION", ex);
		}
	}
	
	public ArrayList<FieldDetailVO> retrieveClosedLoopFileTemplateDetais(
			String identifier) throws DataServiceException {
		ArrayList<FieldDetailVO> fieldDetailList = new ArrayList<FieldDetailVO>();

		try {
			fieldDetailList = (ArrayList) queryForList("closedLoopConversionFileTemplate.select", identifier);
		} catch (Exception ex) {
			logger.info("[NachaMetaDataDaoImpl.retrieveConfirmation DetailRecord - Exception] "
							+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException(
					"NachaMetaDataDaoImpl.retrieveConfirmationDetailRecord() Exception",ex);
		}
		return fieldDetailList;
	}

	
	public FullfillmentVLAccountsVO getFullfillmentVLAccounts(FullfillmentVLAccountsVO fullfillmentVLAccountsVO) throws DataServiceException{
		logger.debug("FullfillmentDaoImpl-->getFullfillmentVLAccounts()-->START");
		try {
			return (FullfillmentVLAccountsVO)queryForObject("fullfillment_VLAccounts.query", fullfillmentVLAccountsVO);
		} 
		catch (Exception ex) {
			logger.error("FullfillmentDaoImpl-->getFullfillmentVLAccounts()-->EXCEPTION-->", ex);
			throw new DataServiceException("FullfillmentDaoImpl-->getFullfillmentVLAccounts-->EXCEPTION", ex);
		}
	}
	
	public void loadTransactionEntries(ArrayList<FullfillmentEntryVO> fullfillmentEntries) throws DataServiceException{
		logger.debug("FullfillmentDaoImpl-->loadTransactionEntries()-->START");
		try {
			int iSize = fullfillmentEntries.size();
			for(int i=0; i<iSize; i++){
				FullfillmentEntryVO fullfillmentEntryVO =  fullfillmentEntries.get(i);
				//If the transaction amount is 0 we can set the reconsiled ind as 0 to indicate it does not have to be reconsiled.
				if(fullfillmentEntryVO.getTransAmount()==0.0 && 
				LedgerConstants.BUSINESS_TRANSACTION_NEW_PROVIDER != fullfillmentEntryVO.getBusTransId().intValue() &&
				LedgerConstants.BUSINESS_TRANSACTION_NEW_BUYER != fullfillmentEntryVO.getBusTransId().intValue())
				{
						fullfillmentEntryVO.setReconsiledInd(0);
						fullfillmentEntryVO.setReconsiledDate(new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()));
				}
				insert("fullfillment_entry.insert", fullfillmentEntryVO);
				//}
			}
			
		} 
		catch (Exception ex) {
			logger.error("FullfillmentDaoImpl-->loadTransactionEntries()-->EXCEPTION-->", ex);
			throw new DataServiceException("FullfillmentDaoImpl-->loadTransactionEntries-->EXCEPTION", ex);
		}
	}
	
	public void insertFullfillmentEntry(FullfillmentEntryVO fullfillmentEntryVO)  throws DataServiceException{
		logger.debug("FullfillmentDaoImpl-->insertFullfillmentEntry()-->START");
		int busTransId = fullfillmentEntryVO.getBusTransId().intValue();
		try {
				if((fullfillmentEntryVO.getTransAmount() != null && fullfillmentEntryVO.getTransAmount().doubleValue() > 0.0)
						|| LedgerConstants.BUSINESS_TRANSACTION_NEW_PROVIDER == busTransId 
						|| LedgerConstants.BUSINESS_TRANSACTION_NEW_BUYER == busTransId){
						insert("fullfillment_entry.insert", fullfillmentEntryVO);
				}
		
		} 
		catch (Exception ex) {
			logger.error("FullfillmentDaoImpl-->insertFullfillmentEntry()-->EXCEPTION-->", ex);
			throw new DataServiceException("FullfillmentDaoImpl-->insertFullfillmentEntry-->EXCEPTION", ex);
		}
	}
	public Map getFullfillmentSLAccounts() throws DataServiceException{
		logger.debug("FullfillmentDaoImpl-->getFullfillmentSLAccounts()-->START");
		try {
			if(dataMap != null && dataMap.size() > 0){
				return dataMap;
			}
			List<FullfillmentVLAccountsVO> accountsVO = (ArrayList<FullfillmentVLAccountsVO>)queryForList("fullfillment_SLAccounts.query", null);
			
			for (FullfillmentVLAccountsVO data : accountsVO) {
				dataMap.put(data.getAccountCode(), data);
			}
			return dataMap;
		} 
		catch (Exception ex) {
			logger.error("FullfillmentDaoImpl-->getFullfillmentSLAccounts()-->EXCEPTION-->", ex);
			throw new DataServiceException("FullfillmentDaoImpl-->getFullfillmentSLAccounts-->EXCEPTION", ex);
		}
	}
	
	public Long getSLAccountForHeartBeat() throws DataServiceException{
		Long heartBeatAccountId = null;
		try{
			heartBeatAccountId = (Long) queryForObject("fullfillment_SLAccounts_Heartbeat.query", FullfillmentConstants.VL_ACCOUNT_POSTING_FEE);
		}catch(Exception e){
			logger.error("");
			throw new DataServiceException("");
		}
		
		return heartBeatAccountId;
	}
	
	public Long getSLAccountForSL3() throws DataServiceException{
		Long heartBeatAccountId = null;
		try{
			heartBeatAccountId = (Long) queryForObject("fullfillment_SLAccounts_Heartbeat.query", FullfillmentConstants.VL_ACCOUNT_PREFUNDING_ACCOUNT);
		}catch(Exception e){
			logger.error("");
			throw new DataServiceException("");
		}
		
		return heartBeatAccountId;
	}
	
	public FullfillmentEntryVO getFullfillmentEntry(Integer groupId,
			Integer sortOrder) throws DataServiceException{
		Map<String, Integer> paramMap = new HashMap<String, Integer>();
		FullfillmentEntryVO feVO;
		paramMap.put("grpId", groupId);
		paramMap.put("srtOrder", sortOrder);
		try{
			feVO = (FullfillmentEntryVO) queryForObject("fullfillmentBySort_Grp.query", paramMap);
		}catch(Exception e){
			logger.error("");
			throw new DataServiceException("");
		}
		
		return feVO;
	}

	
	public void updateReconcileStatus(Long fullfillmentEntryId,
			Long reconciledInd, Long primaryAccountNo,Double vlBalance)  throws DataServiceException{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("fullfillmentEntryId", fullfillmentEntryId);
		paramMap.put("reconciledInd", reconciledInd);
		paramMap.put("primaryAccountNo",primaryAccountNo);
		paramMap.put("vlBalance",vlBalance);
		try{
			update("fullfillmentEntry.update", paramMap);
		}catch(Exception e){
			logger.error("FullfillmentDaoImpl-->updateReconcileStatus()-->EXCEPTION-->", e);
			throw new DataServiceException("FullfillmentDaoImpl-->updateReconcileStatus()-->EXCEPTION-->", e);
		}

	}

	@SuppressWarnings("unchecked")
	public void updateFullfillmentGrpId(Long fullfillmentGrpId,
			String soId){
		Map paramMap = new HashMap();
		paramMap.put("fullfillmentGrpId", fullfillmentGrpId);
		paramMap.put("soId", soId);
		update("fullfillmentEntryAch.update", paramMap);
	}

	
	public void updateFullfillmentLogStatus(Long fullfillment_grp_id, Integer status)
		throws DataServiceException{
		Map<String, Long> paramMap = new HashMap<String, Long>();
		paramMap.put("fullfillmentGrpId", fullfillment_grp_id);
		paramMap.put("reconciledInd", status.longValue());
		try{
			update("fullfillmentLogReconciled.update", paramMap);
		}catch(Exception e){
			logger.error("FullfillmentDaoImpl-->updateFullfillmentLogStatus()-->EXCEPTION-->", e);
			throw new DataServiceException("FullfillmentDaoImpl-->updateFullfillmentLogStatus()-->EXCEPTION-->", e);
		}
	}
	
	public Map<String, Object> getActionCodeId(String actionCode) throws DataServiceException{
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try{
			returnMap = (HashMap<String, Object>)queryForObject("fectchActionCodeIdandStatus.query", actionCode);
		}catch(Exception e){
			logger.error("FullfillmentDaoImpl-->getActionCodeId()-->EXCEPTION-->", e);
			throw new DataServiceException("FullfillmentDaoImpl-->getActionCodeId()-->EXCEPTION-->", e);
		}
		return returnMap;
	}
	
	public FullfillmentEntryVO getFullfillmentMessageDetail(Long fullfillmentEntryId) throws DataServiceException{
		logger.debug("FullfillmentDaoImpl-->getFullfillmentMessageDetail()-->START");
		try {
			return (FullfillmentEntryVO)queryForObject("fullfillmentMessageDetail.query", fullfillmentEntryId);
		} 
		catch (Exception ex) {
			logger.error("FullfillmentDaoImpl-->getFullfillmentMessageDetail()-->EXCEPTION-->", ex);
			throw new DataServiceException("FullfillmentDaoImpl-->getFullfillmentMessageDetail-->EXCEPTION", ex);
		}
		
	}
	
	public Long getNextFullfillmentEntryId(FullfillmentEntryVO fullfillmentEntryVO) throws DataServiceException{
		logger.debug("FullfillmentDaoImpl-->getNextFullfillmentEntryId()-->START");
		try {
			return (Long)queryForObject("nextFullfillmentEntryId.query", fullfillmentEntryVO);
		} 
		catch (Exception ex) {
			logger.error("FullfillmentDaoImpl-->getNextFullfillmentEntryId()-->EXCEPTION-->", ex);
			throw new DataServiceException("FullfillmentDaoImpl-->getNextFullfillmentEntryId-->EXCEPTION", ex);
		}
	}
	
	public FullfillmentEntryVO getFirstUnReconciledTrans(Long fullfillmentGroupId) throws DataServiceException{
		logger.debug("FullfillmentDaoImpl-->getFirstUnReconciledTrans()-->START");
		try {
			return (FullfillmentEntryVO)queryForObject("getFirstUnReconciledTrans.query", fullfillmentGroupId);
		} 
		catch (Exception ex) {
			logger.error("FullfillmentDaoImpl-->getFirstUnReconciledTrans()-->EXCEPTION-->", ex);
			throw new DataServiceException("FullfillmentDaoImpl-->getFirstUnReconciledTrans-->EXCEPTION", ex);
		}
	}
	
	public FullfillmentEntryVO getNextFullfillmentEntryDetails(FullfillmentEntryVO fullfillmentEntryVO) throws DataServiceException{
		logger.debug("FullfillmentDaoImpl-->getNextFullfillmentEntryDetails()-->START");
		try {
			return (FullfillmentEntryVO)queryForObject("nextFullfillmentEntryDetails.query", fullfillmentEntryVO);
		} 
		catch (Exception ex) {
			logger.error("FullfillmentDaoImpl-->getNextFullfillmentEntryDetails()-->EXCEPTION-->", ex);
			throw new DataServiceException("FullfillmentDaoImpl-->getNextFullfillmentEntryDetails-->EXCEPTION", ex);
		}
	}
	
	public boolean checkVLAccountValidity(Long ledgerEntityId) throws DataServiceException{
		boolean flag = false;
		int cnt;
		try{
			cnt = (Integer)queryForObject("SLAccountCount.query", ledgerEntityId);
			if(cnt > 0){
				flag = true;
			}
		}catch(Exception e){
			logger.error("FullfillmentDaoImpl-->checkVLAccountValidity()-->EXCEPTION-->", e);
			throw new DataServiceException("FullfillmentDaoImpl-->checkVLAccountValidity()-->EXCEPTION-->", e);
		}
		return flag;
	}
	
	public void  createVLAcountEntry(FullfillmentVLAccountsVO vlAccountVO) throws DataServiceException{
		try{
			insert("createVLAccounts.insert", vlAccountVO);
		}catch(Exception e){
			logger.error("FullfillmentDaoImpl-->createVLAcountEntry()-->EXCEPTION-->", e);
			throw new DataServiceException("FullfillmentDaoImpl-->createVLAcountEntry()-->EXCEPTION-->", e);
		}
	}
	
	public void updateVLAccount(FullfillmentVLAccountsVO vlAccountVO) throws DataServiceException{
		try{
			update("setv2accountno.update", vlAccountVO);
		}catch(Exception e){
			logger.error("FullfillmentDaoImpl-->updateVLAccount()-->EXCEPTION-->", e);
			throw new DataServiceException("FullfillmentDaoImpl-->updateVLAccount()-->EXCEPTION-->", e);
		}
	}
	
	public void updateFullfillmentVLAccount(FullfillmentEntryVO fullfillmentEntryVO) throws DataServiceException{
		try{
			update("fullfillmentVLAccount.update", fullfillmentEntryVO);
		}catch(Exception e){
			logger.error("FullfillmentDaoImpl-->updateFullfillmentVLAccount()-->EXCEPTION-->", e);
			throw new DataServiceException("FullfillmentDaoImpl-->updateFullfillmentVLAccount()-->EXCEPTION-->", e);
		}
	}
	
	public EntityPromoCodesVO getEntityPromoCodes(EntityPromoCodesVO entityPromoCodesVO) throws DataServiceException{
		try{
			return (EntityPromoCodesVO)queryForObject("getEntityPromoCodes.query", entityPromoCodesVO);
		}catch(Exception e){
			logger.error("FullfillmentDaoImpl-->getEntityPromoCodes()-->EXCEPTION-->", e);
			throw new DataServiceException("FullfillmentDaoImpl-->getEntityPromoCodes()-->EXCEPTION-->", e);
		}
	}
	
	public VLHeartbeatVO getValuelinkStatuses() throws DataServiceException{
		try{
			return (VLHeartbeatVO)queryForObject("valueLinkHeartBeat.query", null);
		}catch(Exception e){
			logger.error("FullfillmentDaoImpl-->getValuelinkStatuses-->EXCEPTION-->", e);
			throw new DataServiceException("FullfillmentDaoImpl-->getValuelinkStatuses-->EXCEPTION-->", e);
		}		
	}
	
	public void updateSharpVLStatus(String system, String status){
		if(system.equalsIgnoreCase(FullfillmentConstants.SHARP_SYSTEM)){
			update("valueLinkSharpInd.update", status);
		}else if(system.equalsIgnoreCase(FullfillmentConstants.VL_SYSTEM)){
			update("valueLinkInd.update", status);
		}
	}
	
	public void createHeartBeatMessage(String heartBeatId) throws DataServiceException{
		try{
			insert("heartBeat_entry.insert", heartBeatId);
		}catch(Exception e){
			logger.error("FullfillmentDaoImpl-->createHeartBeatMessage-->EXCEPTION-->", e);
			throw new DataServiceException("FullfillmentDaoImpl-->createHeartBeatMessage-->EXCEPTION-->", e);
		}
	}
	
	public void deleteHeartBeatMessage(String heartBeatId) throws DataServiceException{
		try{
			delete("removeHearBeatMsg.delete", heartBeatId);
		}catch(Exception e){
			logger.error("FullfillmentDaoImpl-->deleteHeartBeatMessage-->EXCEPTION-->", e);
			throw new DataServiceException("FullfillmentDaoImpl-->deleteHeartBeatMessage-->EXCEPTION-->", e);
		}
	}
	
	public Map<String, String> getExternalQueueStatus() throws DataServiceException{
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("sharpInd", null);
		resultMap.put("valueLinkInd", null);
		try{
			resultMap = (HashMap<String, String>)queryForObject("externalQueues_status.select", resultMap);
		}catch(Exception e){
			logger.error("FullfillmentDaoImpl --> getExternalQueueStatus --> Exception -->", e);
			throw new DataServiceException("FullfillmentDaoImpl --> getExternalQueueStatus --> Exception -->", e);
		}
		return resultMap;
	}
	
	public void moveFullfillmentVLAccountToHistory(FullfillmentVLAccountsVO fullfillmentVLAccountsVO) throws DataServiceException{
		try{
			insert("moveFullfillmentVLAccountToHistory.insert", fullfillmentVLAccountsVO);
			delete("fullfillmentVLAccount.delete", fullfillmentVLAccountsVO);
		}catch(Exception e){
			logger.error("FullfillmentDaoImpl-->moveFullfillmentVLAccountToHistory()-->EXCEPTION-->", e);
			throw new DataServiceException("FullfillmentDaoImpl-->moveFullfillmentVLAccountToHistory()-->EXCEPTION-->", e);
		}
	}
	
	public void replaceFullfillmentGroupId(FullfillmentEntryVO fullfillmentEntryVO) throws DataServiceException{
		try{
			update("replaceFullfillmentGroupId.update", fullfillmentEntryVO);
		}catch(Exception e){
			logger.error("FullfillmentDaoImpl-->replaceFullfillmentGroupId()-->EXCEPTION-->", e);
			throw new DataServiceException("FullfillmentDaoImpl-->replaceFullfillmentGroupId()-->EXCEPTION-->", e);
		}
	}
	
	public void sortFullfillmentEntryIds(FullfillmentEntryVO fullfillmentEntryVO) throws DataServiceException{
		try{
			update("sortFullfillmentEntryIds.update", fullfillmentEntryVO);
		}catch(Exception e){
			logger.error("FullfillmentDaoImpl-->sortFullfillmentEntryIds()-->EXCEPTION-->", e);
			throw new DataServiceException("FullfillmentDaoImpl-->sortFullfillmentEntryIds()-->EXCEPTION-->", e);
		}
	}
	
	public List<FullfillmentEntryVO> getRequestsWithNoResponses() throws DataServiceException{
		logger.debug("FullfillmentDaoImpl-->getRequestsWithNoResponses()-->START");
		try {
			return (List<FullfillmentEntryVO>)queryForList("fullfillmentRequestsWithNoResponses.query", null);
		} 
		catch (Exception ex) {
			logger.error("FullfillmentDaoImpl-->getRequestsWithNoResponses()-->EXCEPTION-->", ex);
			throw new DataServiceException("FullfillmentDaoImpl-->getRequestsWithNoResponses-->EXCEPTION", ex);
		}		
	}

	public String getActionCodeDesc(String actionCode) throws DataServiceException{
		logger.debug("FullfillmentDaoImpl-->getActionCodeDesc()-->START");
		try {
			return (String)queryForObject("fectchActionCodeDesc.query", actionCode);
		} 
		catch (Exception ex) {
			logger.error("FullfillmentDaoImpl-->getActionCodeDesc()-->EXCEPTION-->", ex);
			throw new DataServiceException("FullfillmentDaoImpl-->getActionCodeDesc-->EXCEPTION", ex);
		}
	}
	
	public Long checkBuyerAccountReqExists(Integer buyerId)  throws DataServiceException{
		logger.debug("FullfillmentDaoImpl-->checkBuyerAccountReqExists()-->START");
		try {
			return (Long)queryForObject("checkBuyerAccountReqExists.query", buyerId);
		} 
		catch (Exception ex) {
			logger.error("FullfillmentDaoImpl-->checkBuyerAccountReqExists()-->EXCEPTION-->", ex);
			throw new DataServiceException("FullfillmentDaoImpl-->checkBuyerAccountReqExists()-->EXCEPTION", ex);
		}
	}
	
	public void updateNonReconsiledInd() throws DataServiceException {
		try{
			update("fullfillmentEntry.updatedReconsiledInd",null);
		}catch(Exception e){
			logger.error("FullfillmentDaoImpl-->updateNonReconsiledInd()-->EXCEPTION-->", e);
			throw new DataServiceException("FullfillmentDaoImpl-->updateNonReconsiledInd()-->EXCEPTION-->", e);
		}
	}
	
	public void insertProcessResponseLogging(Long fullfillmentEntryId, String responseStr) throws DataServiceException
	{
		try{
			HashMap<String,String> processResponseMap = new HashMap<String,String>();
			processResponseMap.put("fullfillmentEntryId", fullfillmentEntryId+"");
			processResponseMap.put("responseString", responseStr);
			insert("insertProcessResponseLogging",processResponseMap);
		}catch(Exception e){
			logger.error("FullfillmentDaoImpl-->insertProcessResponseLogging()-->EXCEPTION-->", e);
			throw new DataServiceException("FullfillmentDaoImpl-->insertProcessResponseLogging()-->EXCEPTION-->", e);
		}
	}
	
	public void insertProcessRequestLogging(Long fullfillmentEntryId, String requestStr) throws DataServiceException
	{
		try{
			HashMap<String,String> processRequestMap = new HashMap<String,String>();
			processRequestMap.put("fullfillmentEntryId", fullfillmentEntryId+"");
			processRequestMap.put("requestString", requestStr);
			System.out.println("bytes length" + requestStr.length());
			insert("insertProcessRequestLogging",processRequestMap);
		}catch(Exception e){
			logger.error("FullfillmentDaoImpl-->insertProcessRequestLogging()-->EXCEPTION-->", e);
			throw new DataServiceException("FullfillmentDaoImpl-->insertProcessRequestLogging()-->EXCEPTION-->", e);
		}
	}

	public List<FullfillmentEntryVO> getFulfillmentRecordsByGroupId(Long fullfillmentGroupId) throws DataServiceException {
		logger.debug("FullfillmentDaoImpl-->()-->START");
		try {
			return queryForList("fullfillmentGroupList.query", fullfillmentGroupId);
		} 
		catch (Exception ex) {
			logger.error("FullfillmentDaoImpl-->getFulfillmentRecordsByGroupId()-->EXCEPTION-->", ex);
			throw new DataServiceException("FullfillmentDaoImpl-->getFulfillmentRecordsByGroupId-->EXCEPTION", ex);
		}
	}
	
	public void updateLastPutTimeAndDepth(String queueName,String pan) throws DataServiceException
	{
		try
		{
			HashMap<String,String> queueMap = new HashMap<String,String>();
			queueMap.put("queueName", queueName);
			queueMap.put("pan", pan);
			update("messageQueue.putTimeDepthUpdate",queueMap);
		}catch(Exception e)
		{
			logger.error("FullfillmentDaoImpl-->updateLastPutTimeAndDepth()-->EXCEPTION-->", e);
			throw new DataServiceException("FullfillmentDaoImpl-->updateLastPutTimeAndDepth()-->EXCEPTION-->", e);
		}
	}
	
	public void updateLastTransmittedTimeAndDepth(String queueName) throws DataServiceException
	{
		try
		{

			update("messageQueue.transmittedTimeDepthUpdate",queueName);
		}catch(Exception e)
		{
			logger.error("FullfillmentDaoImpl-->updateLastTransmittedTimeAndDepth()-->EXCEPTION-->", e);
			throw new DataServiceException("FullfillmentDaoImpl-->updateLastTransmittedTimeAndDepth()-->EXCEPTION-->", e);
		}
	}
	
	public void insertAdminToolLogging(FullfillmentAdminToolVO adminToolVO)throws DataServiceException
	{
		try
		{
			ArrayList<String> fullfillmentEntryIdList = adminToolVO.getFullfillmentEntryIdList();
			for (String fullfillmentEntryId : fullfillmentEntryIdList) {
				adminToolVO.setFullfillmentEntryId(fullfillmentEntryId);
				insert("insertAdminToolLogging",adminToolVO);
			}
		}catch(Exception e)
		{
			logger.error("FullfillmentDaoImpl-->insertAdminToolLogging()-->EXCEPTION-->", e);
			throw new DataServiceException("FullfillmentDaoImpl-->insertAdminToolLogging()-->EXCEPTION-->", e);
		}
	}
	
	public FullfillmentVLAccountsVO getFullfillmentVLAccountsByEntityId(Long entityId) throws DataServiceException{
		logger.debug("FullfillmentDaoImpl-->getFullfillmentVLAccounts()-->START");
		try {
			return (FullfillmentVLAccountsVO)queryForObject("fullfillment_VLAccountsByEntityId.query", entityId);
		} 
		catch (Exception ex) {
			logger.error("FullfillmentDaoImpl-->getFullfillmentVLAccountsByEntityId()-->EXCEPTION-->", ex);
			throw new DataServiceException("FullfillmentDaoImpl-->getFullfillmentVLAccountsByEntityId-->EXCEPTION", ex);
		}
	}
	
	public Integer checkFullfillmentReconciledIndicator(String soId) throws DataServiceException {
		logger.debug("FullfillmentDaoImpl-->getFullfillmentReconciledIndicator()-->START");
		try {
			
			
			return (Integer) queryForObject("checkReconciledIndicator.query", soId);
		} 
		catch (Exception ex) {
			logger.error("FullfillmentDaoImpl-->getFullfillmentReconciledIndicator()-->EXCEPTION-->", ex);
			throw new DataServiceException("FullfillmentDaoImpl-->getFullfillmentReconciledIndicator-->EXCEPTION", ex);
		}
	}
	
	public Integer getCountOfFullfillmentRecordsForSO(String soId) throws DataServiceException 
	{
		logger.debug("FullfillmentDaoImpl-->getCountOfFullfillmentRecordsForSO()-->START");
		try {
			
			
			return (Integer) queryForObject("countOfFullfillmentRecordsForSO.query", soId);
		} 
		catch (Exception ex) {
			logger.error("FullfillmentDaoImpl-->getCountOfFullfillmentRecordsForSO()-->EXCEPTION-->", ex);
			throw new DataServiceException("FullfillmentDaoImpl-->getCountOfFullfillmentRecordsForSO-->EXCEPTION", ex);
		}
	}
	
	public void insertNMMResponseLogging(Long fullfillmentEntryId, String responseStr) throws DataServiceException
	{
		try{
			HashMap<String,String> processResponseMap = new HashMap<String,String>();
			processResponseMap.put("fullfillmentEntryId", fullfillmentEntryId+"");
			processResponseMap.put("responseString", responseStr);
			insert("insertNMMResponseLogging",processResponseMap);
		}catch(Exception e){
			logger.error("FullfillmentDaoImpl-->insertNMMResponseLogging()-->EXCEPTION-->", e);
			throw new DataServiceException("FullfillmentDaoImpl-->insertNMMResponseLogging()-->EXCEPTION-->", e);
		}
	}
 	
 	public void insertBalanceInquiryLogging(Long fullfillmentEntryId, String responseStr) throws DataServiceException
 	{
		try{
			HashMap<String,String> processResponseMap = new HashMap<String,String>();
			processResponseMap.put("fullfillmentEntryId", fullfillmentEntryId+"");
			processResponseMap.put("responseString", responseStr);
			insert("insertBalanceInquiryLogging",processResponseMap);
		}catch(Exception e){
			logger.error("FullfillmentDaoImpl-->insertBalanceInquiryLogging()-->EXCEPTION-->", e);
			throw new DataServiceException("FullfillmentDaoImpl-->insertBalanceInquiryLogging()-->EXCEPTION-->", e);
		}
 	}
 	
 	public List<FullfillmentRuleVO> getFullfillmentRulesForGroup(Long fullfillmentGroupId) throws DataServiceException
 	{
		logger.debug("FullfillmentDaoImpl-->getFullfillmentRulesForGroup()-->START");
		try {
			return queryForList("fullfillmentRuleIdForGroup.query", fullfillmentGroupId);
		} 
		catch (Exception ex) {
			logger.error("FullfillmentDaoImpl-->getFullfillmentRulesForGroup()-->EXCEPTION-->", ex);
			throw new DataServiceException("FullfillmentDaoImpl-->getFullfillmentRulesForGroup-->EXCEPTION", ex);
		}
		
 	}
 	
 	public void updatePanAndStateCdForActivations(FullfillmentEntryVO fullfillmentEntryVO) throws DataServiceException
 	{
		logger.debug("FullfillmentDaoImpl-->updatePanAndStateCdForActivations()-->START");
		try {
			update("updatePanAndStateCdForActivations.update", fullfillmentEntryVO);
		} 
		catch (Exception ex) {
			logger.error("FullfillmentDaoImpl-->updatePanAndStateCdForActivations()-->EXCEPTION-->", ex);
			throw new DataServiceException("FullfillmentDaoImpl-->updatePanAndStateCdForActivations-->EXCEPTION", ex);
		}
 	}
 	
 	public List<String> getV1V2AccountsForBalanceEnquiry() throws DataServiceException
 	{
 		try
 		{
 			return queryForList("v1v2AccountsBalanceEnquiry.query", null);
 		}
		catch (Exception ex) {
			logger.error("FullfillmentDaoImpl-->getV1V2AccountsForBalanceEnquiry()-->EXCEPTION-->", ex);
			throw new DataServiceException("FullfillmentDaoImpl-->getV1V2AccountsForBalanceEnquiry-->EXCEPTION", ex);
		}
 	}
 	
 	public void updateV1V2AccountBalanceEnquiry(FullfillmentEntryVO fullfillmentEntryVO) throws DataServiceException
 	{
 		try
 		{
 			update("v1AccountBalance.update", fullfillmentEntryVO);
 			update("v2AccountBalance.update", fullfillmentEntryVO);
 		}
		catch (Exception ex) {
			logger.error("FullfillmentDaoImpl-->updateV1V2AccountBalanceEnquiry()-->EXCEPTION-->", ex);
			throw new DataServiceException("FullfillmentDaoImpl-->updateV1V2AccountBalanceEnquiry-->EXCEPTION", ex);
		}
 	}
 	
 	public List<Long> getFullfillmentEntryIdsForGroup(Long fullfillmentGrpId) throws DataServiceException
 	{
 		try
 		{
 			return queryForList("fullfillmentIdsForGroup.query", fullfillmentGrpId);
 			
 		}
 		catch (Exception ex) {
			logger.error("FullfillmentDaoImpl-->getFullfillmentEntryIdsForGroup()-->EXCEPTION-->", ex);
			throw new DataServiceException("FullfillmentDaoImpl-->getFullfillmentEntryIdsForGroup-->EXCEPTION", ex);
		}
 	}
 	
 	public  FullfillmentRuleVO getFullfillmentRuleData(Integer fullfillmentRuleId) throws DataServiceException
 	{
 		try
 		{
 			return (FullfillmentRuleVO)queryForObject("fullfillment_rule_data.query", fullfillmentRuleId);
 			
 		}
 		catch (Exception ex) {
			logger.error("FullfillmentDaoImpl-->getFullfillmentRuleData()-->EXCEPTION-->", ex);
			throw new DataServiceException("FullfillmentDaoImpl-->getFullfillmentRuleData-->EXCEPTION", ex);
		}
 	}
}
