/**
 * 
 */
package com.servicelive.wallet.valuelink.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.servicelive.common.ABaseDao;
import com.servicelive.common.CommonConstants;
import com.servicelive.common.exception.DataServiceException;
import com.servicelive.lookup.vo.ValueLinkAccountsVO;
import com.servicelive.wallet.serviceinterface.vo.ValueLinkEntryVO;
import com.servicelive.wallet.valuelink.vo.ValueLinkRuleVO;

/**
 * Class ValueLinkDao.
 */
public class ValueLinkDao extends ABaseDao implements IValueLinkDao {

	/** The Constant logger. */
	static final Logger logger = Logger.getLogger(ValueLinkDao.class);

	public Integer getCountOfValueLinkRecordsForSO(String soId) throws DataServiceException {
		try {
			return (Integer) queryForObject("countOfFullfillmentRecordsForSO.query", soId);
		} 
		catch (Exception ex) {
			logger.error("ValueLinkDao.getCountOfFullfillmentRecordsForSO()-->EXCEPTION-->", ex);
			throw new DataServiceException("ValueLinkDao.getCountOfFullfillmentRecordsForSO()-->EXCEPTION", ex);
		}
	}
	
	
	public Double getCompletedSOLedgerAmount(long vendorId) throws DataServiceException {
		try {
			return (Double) queryForObject("getCompletedSOLedgerAmount.query", vendorId);
		} 
		catch (Exception ex) {
			logger.error("ValueLinkDao.getCountOfFullfillmentRecordsForSO()-->EXCEPTION-->", ex);
			throw new DataServiceException("ValueLinkDao.getCountOfFullfillmentRecordsForSO()-->EXCEPTION", ex);
		}
	}
	
	public Double getSLACreditAmount(long vendorId) throws DataServiceException {
		try {
			return (Double) queryForObject("getSLACreditAmount.query", vendorId);
		} 
		catch (Exception ex) {
			logger.error("ValueLinkDao.getSLACreditAmount()-->EXCEPTION-->", ex);
			throw new DataServiceException("ValueLinkDao.getSLACreditAmount()-->EXCEPTION", ex);
		}
	}

	public Integer isACHTransPending(String soId) throws DataServiceException {
		try {
			return (Integer) queryForObject("isACHTransPending.query", soId);
		} 
		catch (Exception ex) {
			logger.error("ValueLinkDao.getCountOfFullfillmentRecordsForSO()-->EXCEPTION-->", ex);
			throw new DataServiceException("ValueLinkDao.getCountOfFullfillmentRecordsForSO()-->EXCEPTION", ex);
		}
	}
	
	public Integer hasPreviousAddOn(String serviceOrderId) throws DataServiceException{
		
		try {
			return (Integer) queryForObject("hasPreviousAddOn.query", serviceOrderId);
		} 
		catch (Exception ex) {
			logger.error("ValueLinkDao.hasPreviousAddOn-->EXCEPTION-->", ex);
			throw new DataServiceException("ValueLinkDao.getCountOfFullfillmentRecordsForSO()-->EXCEPTION", ex);
		}
		
		}


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.servicelive.wallet.valuelink.dao.IValueLinkDao#getNextValueLinkEntryDetails(com.servicelive.wallet.valuelink.vo.ValueLinkEntryVO)
	 */
	public ValueLinkEntryVO getNextValueLinkEntryDetails(ValueLinkEntryVO fullfillmentEntryVO) throws DataServiceException {

		logger.debug("ValueLinkDao-->getNextFullfillmentEntryDetails()-->START");
		try {
			return (ValueLinkEntryVO) queryForObject("nextFullfillmentEntryDetails.query", fullfillmentEntryVO);
		} catch (Exception ex) {
			logger.error("ValueLinkDao-->getNextFullfillmentEntryDetails()-->EXCEPTION-->", ex);
			throw new DataServiceException("ValueLinkDao-->getNextFullfillmentEntryDetails-->EXCEPTION", ex);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.servicelive.wallet.valuelink.dao.IValueLinkDao#getValueLinkMessageDetail(java.lang.Long)
	 */
	public ValueLinkEntryVO getValueLinkMessageDetail(Long fullfillmentEntryId) throws DataServiceException {

		logger.debug("ValueLinkDao-->getFullfillmentMessageDetail()-->START");
		try {
			return (ValueLinkEntryVO) queryForObject("fullfillmentMessageDetail.query", fullfillmentEntryId);
		} catch (Exception ex) {
			logger.error("ValueLinkDao-->getFullfillmentMessageDetail()-->EXCEPTION-->", ex);
			throw new DataServiceException("ValueLinkDao-->getFullfillmentMessageDetail-->EXCEPTION", ex);
		}

	}

	/**
	 * Gets the value link promo codes.
	 * 
	 * @return the value link promo codes
	 */
	@SuppressWarnings("unchecked")
	private Map<Integer, String> getValueLinkPromoCodes() {

		return (Map<Integer, String>) queryForObject("getPromoCodes.query", null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.servicelive.wallet.valuelink.dao.IValueLinkDao#getValueLinkRules(java.lang.Long, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public List<ValueLinkRuleVO> getValueLinkRules(Long businessTransactionId, Long fundingTypeId) throws DataServiceException {

		logger.debug("ValueLinkDao-->getValueLinkRules()-->START");
		ValueLinkRuleVO criteria = new ValueLinkRuleVO();
		criteria.setBusTransId(businessTransactionId);
		criteria.setFundingTypeId(fundingTypeId);
		try {
			return (List<ValueLinkRuleVO>) queryForList("getValueLinkRules", criteria);
		} catch (Exception ex) {
			logger.error("ValueLinkDao-->getValueLinkRules()-->EXCEPTION-->", ex);
			throw new DataServiceException("ValueLinkDao-->getValueLinkRules-->EXCEPTION", ex);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.servicelive.wallet.valuelink.dao.IValueLinkDao#loadTransactionEntries(java.util.List)
	 */
	public void loadTransactionEntries(List<ValueLinkEntryVO> entries) throws DataServiceException {

		Long originatingEntityId = findOriginatingEntityId(entries);

		logger.debug("ValueLinkDao-->loadTransactionEntries()-->START");
		try {
			for (ValueLinkEntryVO entry : entries) {
				// If the transaction amount is 0 we can set the reconsiled ind as 0 to indicate it does not have to be reconsiled.
				if (entry.getTransAmount().doubleValue() == 0.0 && CommonConstants.BUSINESS_TRANSACTION_NEW_PROVIDER != entry.getBusTransId().intValue()
					&& CommonConstants.BUSINESS_TRANSACTION_NEW_BUYER != entry.getBusTransId().intValue()) {
					entry.setReconsiledInd(0);
					entry.setPtdReconsiledInd(10);
					entry.setReconsiledDate(new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()));
				}
				entry.setOriginatingEntityId(originatingEntityId);
				insert("fullfillment_entry.insert", entry);
			}
		} catch (Exception ex) {
			logger.error("ValueLinkDao-->loadTransactionEntries()-->EXCEPTION-->", ex);
			throw new DataServiceException("ValueLinkDao-->loadTransactionEntries-->EXCEPTION", ex);
		}
	}

    public Long insertFullfillmentGroup() throws DataServiceException {
        logger.debug("ValueLinkDao-->insertFullfillmentGroup()-->START");
        try {
            return ((Integer)insert("fullfillment_group.insert", null)).longValue();
        } catch (Exception ex) {
            logger.error("ValueLinkDao-->insertFullfillmentGroup()-->EXCEPTION-->", ex);
            throw new DataServiceException("ValueLinkDao-->insertFullfillmentGroup-->EXCEPTION", ex);
        }
    }


	
	/**
	 * Description: Determine entity id for a group of fullfillment records 
	 * @param entries
	 * @return <code>Long</code>
	 */
	private Long findOriginatingEntityId(List<ValueLinkEntryVO> entries) {
		Long originatingEntityId = new Long(0);
		for (ValueLinkEntryVO entry : entries) {
			if (entry.getEntityTypeId().equals(CommonConstants.LEDGER_ENTITY_TYPE_BUYER)
					|| entry.getEntityTypeId().equals(CommonConstants.LEDGER_ENTITY_TYPE_PROVIDER)
					|| entry.getEntityTypeId().equals(CommonConstants.LEDGER_ENTITY_TYPE_SERVICELIVE_ESCROW)) {
				originatingEntityId = entry.getLedgerEntityId();
				break;
			}

		}
		return originatingEntityId;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.valuelink.dao.IValueLinkDao#insertProcessResponseLogging(java.lang.Long, java.lang.String)
	 */
	public void insertProcessResponseLogging(Long valueLinkEntryId, String responseStr) throws DataServiceException {

		try {
			HashMap<String, String> processResponseMap = new HashMap<String, String>();
			processResponseMap.put("valueLinkEntryId", valueLinkEntryId + "");
			processResponseMap.put("responseString", responseStr);
			insert("insertProcessResponseLogging", processResponseMap);
		} catch (Exception e) {
			logger.error("ValueLinkDao-->insertProcessResponseLogging()-->EXCEPTION-->", e);
			throw new DataServiceException("ValueLinkDao-->insertProcessResponseLogging()-->EXCEPTION-->", e);
		}
	}
	
	/**
	 * 
	 * @param valueLinkEntryId
	 * @throws DataServiceException
	 */
	public void deleteProcessResponseLogging(Long valueLinkEntryId) throws DataServiceException {
		try {
			delete("deleteProcessResponseLogging", valueLinkEntryId);
		} catch (Exception e) {
			logger.error("ValueLinkDao-->deleteProcessResponseLogging()-->EXCEPTION-->", e);
			throw new DataServiceException("ValueLinkDao-->deleteProcessResponseLogging()-->EXCEPTION-->",e);
		}
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.valuelink.dao.IValueLinkDao#insertProcessRequestLogging(java.lang.Long, java.lang.String)
	 */
	public void insertProcessRequestLogging(Long valueLinkEntryId, String requestStr) throws DataServiceException {

		try {
			HashMap<String, String> processRequestMap = new HashMap<String, String>();
			processRequestMap.put("valueLinkEntryId", valueLinkEntryId + "");
			processRequestMap.put("requestString", requestStr);
			insert("insertProcessRequestLogging", processRequestMap);
		} catch (Exception e) {
			logger.error("ValueLinkDao-->insertProcessRequestLogging()-->EXCEPTION-->", e);
			throw new DataServiceException("ValueLinkDao-->insertProcessRequestLogging()-->EXCEPTION-->", e);
		}
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.valuelink.dao.IValueLinkDao#getActionCodeIdAndStatus(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getActionCodeIdAndStatus(String actionCode) throws DataServiceException {

		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			returnMap = (HashMap<String, Object>) queryForObject("getActionCodeIdAndStatus", actionCode);
		} catch (Exception e) {
			logger.error("ValueLinkDao-->getActionCodeId()-->EXCEPTION-->", e);
			throw new DataServiceException("ValueLinkDao-->getActionCodeId()-->EXCEPTION-->", e);
		}
		return returnMap;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.valuelink.dao.IValueLinkDao#updateReconcileStatus(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Double)
	 */
	public void updateReconcileStatus(Long valueLinkEntryId, Long reconciledInd, Long primaryAccountNo, Double vlBalance) throws DataServiceException {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("fullfillmentEntryId", valueLinkEntryId);
		paramMap.put("reconciledInd", reconciledInd);
		paramMap.put("primaryAccountNo", primaryAccountNo);
		paramMap.put("vlBalance", vlBalance);
		try {
			update("fullfillmentEntry.update", paramMap);
		} catch (Exception e) {
			logger.error("ValueLinkDao-->updateReconcileStatus()-->EXCEPTION-->", e);
			throw new DataServiceException("ValueLinkDao-->updateReconcileStatus()-->EXCEPTION-->", e);
		}
	}
	
	public void updateReconcileStatusToNull(Long fulfillmentEntryId) throws DataServiceException {
		try {
			update("updateReconcileStatusToNull", fulfillmentEntryId);
		} catch (Exception e) {
			logger.error("ValueLinkDao-->updateReconcileStatusToNull()-->EXCEPTION-->", e);
			throw new DataServiceException("ValueLinkDao-->updateReconcileStatusToNull()-->EXCEPTION-->", e);
		}		
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.valuelink.dao.IValueLinkDao#updateFullfillmentVLAccount(com.servicelive.wallet.valuelink.vo.ValueLinkEntryVO)
	 */
	public void updateFullfillmentVLAccount(ValueLinkEntryVO valueLinkEntryVO) throws DataServiceException {

		try {
			update("fullfillmentVLAccount.update", valueLinkEntryVO);
		} catch (Exception e) {
			logger.error("ValueLinkDao-->updateFullfillmentVLAccount()-->EXCEPTION-->", e);
			throw new DataServiceException("ValueLinkDao-->updateFullfillmentVLAccount()-->EXCEPTION-->", e);
		}
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.valuelink.dao.IValueLinkDao#updateFullfillmentLogStatus(java.lang.Long, java.lang.Integer)
	 */
	public void updateFullfillmentLogStatus(Long valueLinkGroupId, Integer status) throws DataServiceException {

		Map<String, Long> paramMap = new HashMap<String, Long>();
		paramMap.put("fullfillmentGrpId", valueLinkGroupId);
		paramMap.put("reconciledInd", status.longValue());
		try {
			update("fullfillmentLogReconciled.update", paramMap);
		} catch (Exception e) {
			logger.error("ValueLinkDao-->updateFullfillmentLogStatus()-->EXCEPTION-->", e);
			throw new DataServiceException("ValueLinkDao-->updateFullfillmentLogStatus()-->EXCEPTION-->", e);
		}
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.valuelink.dao.IValueLinkDao#updateV1V2AccountBalanceEnquiry(com.servicelive.wallet.valuelink.vo.ValueLinkEntryVO)
	 */
	public void updateV1V2AccountBalanceEnquiry(ValueLinkEntryVO valueLinkEntryVO) throws DataServiceException {

		try {
			logger.info("updateV1V2AccountBalanceEnquiry in Dao");
			update("v1AccountBalance.update", valueLinkEntryVO);
			logger.info("V1AccountNo: "+valueLinkEntryVO.getV1AccountNo());
			logger.info("V2AccountNo: "+valueLinkEntryVO.getV2AccountNo());
			logger.info("vlBalance: "+valueLinkEntryVO.getVlBalance());
			update("v2AccountBalance.update", valueLinkEntryVO);			
		} catch (Exception ex) {
			logger.error("ValueLinkDao-->updateV1V2AccountBalanceEnquiry()-->EXCEPTION-->", ex);
			throw new DataServiceException("ValueLinkDao-->updateV1V2AccountBalanceEnquiry-->EXCEPTION", ex);
		}
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.servicelive.wallet.valuelink.dao.IValueLinkDao#updateV1V2AccountBalance(com.servicelive.wallet.valuelink.vo.ValueLinkEntryVO)
	 */
	public void updateV1V2AccountBalance(ValueLinkEntryVO valueLinkEntryVO) throws DataServiceException {

		try {
			
			logger.info("updateV1V2AccountBalance and avoid deadlock in Dao");
			update("v1AccountBalanceTransaction.update", valueLinkEntryVO);
			logger.info("V1AccountNo: "+valueLinkEntryVO.getV1AccountNo());
			logger.info("V2AccountNo: "+valueLinkEntryVO.getV2AccountNo());
			logger.info("vlBalance: "+valueLinkEntryVO.getVlBalance());
			update("v2AccountBalanceTransaction.update", valueLinkEntryVO);			
		} catch (Exception ex) {
			logger.error("ValueLinkDao-->updateV1V2AccountBalance()-->EXCEPTION-->", ex);
			throw new DataServiceException("ValueLinkDao-->updateV1V2AccountBalance-->EXCEPTION", ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.valuelink.dao.IValueLinkDao#getActionCodeDesc(java.lang.String)
	 */
	public String getActionCodeDesc(String actionCode) throws DataServiceException {

		logger.debug("ValueLinkDao-->getActionCodeDesc()-->START");
		try {
			return (String) queryForObject("fectchActionCodeDesc.query", actionCode);
		} catch (Exception ex) {
			logger.error("ValueLinkDao-->getActionCodeDesc()-->EXCEPTION-->", ex);
			throw new DataServiceException("ValueLinkDao-->getActionCodeDesc-->EXCEPTION", ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.valuelink.dao.IValueLinkDao#insertFullfillmentEntry(com.servicelive.wallet.valuelink.vo.ValueLinkEntryVO)
	 */
	public Long insertFullfillmentEntry(ValueLinkEntryVO details) throws DataServiceException {

		logger.debug("ValueLinkDao-->insertFullfillmentEntry()-->START");
		int busTransId = details.getBusTransId().intValue();
		details.setFundingTypeId(0l);
		try {
			if ((details.getTransAmount() != null && details.getTransAmount().doubleValue() > 0.0) || CommonConstants.BUSINESS_TRANSACTION_NEW_PROVIDER == busTransId
				|| CommonConstants.BUSINESS_TRANSACTION_NEW_BUYER == busTransId) {
				details.setOriginatingEntityId(details.getLedgerEntityId());
				Long newFulfillmentEntryId =  new Long((Integer)insert("fullfillment_entry.insert", details));
				details.setFullfillmentEntryId(newFulfillmentEntryId);
				return newFulfillmentEntryId;
			} else {
                throw new Exception("Invalid transaction amount: " + details.getTransAmount() +
                        " or busTransId: " + busTransId);
            }

		} catch (Exception ex) {
			logger.error("ValueLinkDao-->insertFullfillmentEntry()-->EXCEPTION-->", ex);
			throw new DataServiceException("ValueLinkDao-->insertFullfillmentEntry-->EXCEPTION", ex);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.servicelive.wallet.valuelink.dao.IValueLinkDao#isValueLinkUp()
	 */
	public boolean isValueLinkUp() throws DataServiceException {
		try{
			logger.info("+++++ : before value link check");
			String ind = (String)queryForObject("valueLinkHeartBeat.query", null);
			logger.info("+++++ : after value link check : "+ind);
			return "Y".equals(ind);
		}catch(Exception e){
			logger.error("ValueLinkDao-->getValuelinkStatuses-->EXCEPTION-->", e);
			throw new DataServiceException("ValueLinkDao-->getValuelinkStatuses-->EXCEPTION-->", e);
		}		
	}
	
	/* (non-Javadoc)
	 * @see com.servicelive.wallet.valuelink.dao.IValueLinkDao#updateValueLinkStatus(boolean)
	 */
	public void updateValueLinkStatus(boolean up) throws DataServiceException {
		String status = "Y";
		if( !up ) {
			status = "N";
		}
		update("valueLinkInd.update", status);
	}
	
	public boolean isActivationRequestPendingForEntity(Long entityId) throws DataServiceException {
		Integer count = (Integer)queryForObject("countPendingActivationRequestsForEntity",entityId);
		return( count != null && count > 0 );
	}
	
	public List<ValueLinkEntryVO> getValueLinkMessageDetailByGroupId(Long groupId) throws DataServiceException {

		try {
			return (List<ValueLinkEntryVO>) queryForList("fullfillmentMessageDetailByGroup.query", groupId);
		} catch (Exception ex) {
			logger.error("valueLinkDao-->getValueLinkMessageDetailByGroupId()-->EXCEPTION-->", ex);
			throw new DataServiceException("valueLinkDao-->getValueLinkMessageDetailByGroupId-->EXCEPTION", ex);
		}
	}
	
	public ValueLinkEntryVO getFirstUnReconciledTrans(Long groupId) throws DataServiceException {
		try {
			return (ValueLinkEntryVO) queryForObject("getFirstUnReconciledTrans.query", groupId);
		} catch (Exception ex) {
			logger.error("valueLinkDao-->getFirstUnReconciledTrans()-->EXCEPTION-->", ex);
			throw new DataServiceException("valueLinkDao-->getFirstUnReconciledTrans-->EXCEPTION", ex);
		}

	}
	
	public void insertAdminToolLogging(String valueLinkEntryId, String comments, String userName)throws DataServiceException
	{
		try{
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("comments", comments);
			parameters.put("userName", userName);
			parameters.put("valueLinkEntryId", valueLinkEntryId);
			insert("insertAdminToolLogging", parameters);
		}catch(Exception e){
			logger.error("ValueLinkDao-->insertAdminToolLogging()-->EXCEPTION-->", e);
			throw new DataServiceException("ValueLinkDao-->insertAdminToolLogging()-->EXCEPTION-->", e);
		}
	}
	public boolean checkVLAccountValidity(Long ledgerEntityId)throws DataServiceException {
		boolean flag = false;
		int cnt;
		try{
			cnt = (Integer)queryForObject("SLAccountCount.query", ledgerEntityId);
			if(cnt > 0){
				flag = true;
			}
		}catch(Exception e){
			logger.error("ValueLinkDao-->checkVLAccountValidity()-->EXCEPTION-->", e);
			throw new DataServiceException("ValueLinkDao-->checkVLAccountValidity()-->EXCEPTION-->", e);
		}
		return flag;
	}
	
	public void createVLAcountEntry(ValueLinkAccountsVO vlAccountVO)throws DataServiceException {
		try{
			insert("createVLAccounts.insert", vlAccountVO);
		}catch(Exception e){
			logger.error("ValueLinkDao-->createVLAcountEntry()-->EXCEPTION-->", e);
			throw new DataServiceException("ValueLinkDao-->createVLAcountEntry()-->EXCEPTION-->", e);
		}
	}
	
	public void moveFullfillmentVLAccountToHistory(ValueLinkAccountsVO vlAccountsVO) throws DataServiceException {
		try{
			insert("moveFullfillmentVLAccountToHistory.insert", vlAccountsVO);
			delete("fullfillmentVLAccount.delete", vlAccountsVO);
		}catch(Exception e){
			logger.error("ValueLinkDao-->moveFullfillmentVLAccountToHistory()-->EXCEPTION-->", e);
			throw new DataServiceException("ValueLinkDao-->moveFullfillmentVLAccountToHistory()-->EXCEPTION-->", e);
		}
	}
	
	public ValueLinkAccountsVO getValueLinkAccounts(ValueLinkAccountsVO criteria)throws DataServiceException {
		logger.debug("ValueLinkDao-->getValueLinkAccounts()-->START");
		return (ValueLinkAccountsVO) queryForObject("getValueLinkAccounts", criteria);
	}
	
	public void updateVLAccount(ValueLinkAccountsVO vlAccountVO) throws DataServiceException{
		try{
			update("setv2accountno.update", vlAccountVO);
		}catch(Exception e){
			logger.error("ValueLinkDao-->updateVLAccount()-->EXCEPTION-->", e);
			throw new DataServiceException("ValueLinkDao-->updateVLAccount()-->EXCEPTION-->", e);
		}
	}

	/** dataMap. */
	Map<String,ValueLinkAccountsVO> dataMap = new HashMap<String,ValueLinkAccountsVO>();
	

	/* (non-Javadoc)
	 * @see com.servicelive.lookup.dao.ILookupDao#getFullfillmentSLAccounts()
	 */
	public ValueLinkAccountsVO getSLValueLinkAccounts(String accountName) throws DataServiceException {
		logger.debug("ValueLinkDao-->getSLValueLinkAccounts()-->START");
		if(dataMap != null && dataMap.size() > 0){
			return dataMap.get(accountName);
		}
		List<ValueLinkAccountsVO> accountsVO = (ArrayList<ValueLinkAccountsVO>)queryForList("fullfillment_SLAccounts.query", null);
		
		for (ValueLinkAccountsVO data : accountsVO) {
			dataMap.put(data.getAccountCode(), data);
		}
		return dataMap.get(accountName);
	}

	//deleting the entry in VLAccounts table if the V1account is zero
	public void deleteVLAccountsWhenAcNoisZero(ValueLinkAccountsVO vlAccountsVO) throws DataServiceException {
		try{
			delete("fullfillmentVLAccount.delete", vlAccountsVO);
		}catch(Exception e){
			logger.error("ValueLinkDao-->deleteVLAccountsWhenAcNoisZero()-->EXCEPTION-->", e);
			throw new DataServiceException("ValueLinkDao-->deleteVLAccountsWhenAcNoisZero()-->EXCEPTION-->", e);
}
	}

}
