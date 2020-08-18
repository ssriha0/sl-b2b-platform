package com.servicelive.wallet.valuelink;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.servicelive.common.CommonConstants;
import com.servicelive.common.exception.DataServiceException;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.wallet.common.IIdentifierDao;
import com.servicelive.wallet.serviceinterface.vo.ValueLinkEntryVO;
import com.servicelive.wallet.serviceinterface.vo.ValueLinkVO;
import com.servicelive.wallet.valuelink.IValueLinkRulesBO.AutoCreateAccountBehavior;
import com.servicelive.wallet.valuelink.dao.IValueLinkDao;
import com.servicelive.wallet.valuelink.dao.IValueLinkQueueDao;
import com.servicelive.wallet.valuelink.sharp.ISharpGateway;
import com.servicelive.wallet.valuelink.sharp.iso.IIsoRequestProcessor;
import com.servicelive.wallet.valuelink.vo.ValueLinkQueueEntryVO;

/**
 * Class ValueLinkBO.
 */
public class ValueLinkBO implements IValueLinkBO {

	/** logger. */
	private static final Logger logger = Logger.getLogger(ValueLinkBO.class);

	/** identifierDao. */
	private IIdentifierDao identifierDao;

	/** mapTransactionToAutoCreateBehavior. */
	private Map<Long, AutoCreateAccountBehavior> mapTransactionToAutoCreateBehavior = new HashMap<Long, AutoCreateAccountBehavior>();

	/** valueLinkDao. */
	private IValueLinkDao valueLinkDao;

	/** valueLinkRules. */
	IValueLinkRulesBO valueLinkRules;

	IValueLinkQueueDao valueLinkQueueDao;
	
	/** requestProcessor. */
	private IIsoRequestProcessor requestProcessor;
	
	/** sharpGateway. */
	private ISharpGateway sharpGateway;
	/**
	 * ValueLinkBO.
	 */
	public ValueLinkBO() {
		initializeTransactionToAutoCreateBehaviorMap();
	}
	
	public boolean isACHTransPending(String soId) throws SLBusinessServiceException {
		
		boolean vlEntryExists = true;
		try{
			Integer countOfRecords = valueLinkDao.isACHTransPending(soId); 
			if (countOfRecords != null &&  countOfRecords.intValue() > 0 )
			{
				vlEntryExists = false;	
			}
			return vlEntryExists;
		}
		catch (DataServiceException e) {
			e.printStackTrace();
			String msg = e.getMessage();
			if (StringUtils.isBlank(msg)) {
				throw new SLBusinessServiceException("An Exception has occurred in processing you adjustment", e);
			} else {
				throw new SLBusinessServiceException(msg, e);
			}
		}
	}
	
	
    public boolean hasPreviousAddOn(String serviceOrderId) throws SLBusinessServiceException {
    	boolean vlEntryExists = true;
		try{
			Integer countOfRecords = valueLinkDao.hasPreviousAddOn(serviceOrderId); 
			if (countOfRecords != null &&  countOfRecords.intValue() > 0 )
			{
				vlEntryExists = false;	
			}
			return vlEntryExists;
		}
		catch (DataServiceException e) {
			e.printStackTrace();
			String msg = e.getMessage();
			if (StringUtils.isBlank(msg)) {
				throw new SLBusinessServiceException("An Exception has occurred in processing you adjustment", e);
			} else {
				throw new SLBusinessServiceException(msg, e);
			}
		}
    	
    	
    }

	
	
	
	
	
	

	public boolean checkValueLinkReconciledIndicator(String soId) throws SLBusinessServiceException {
		boolean vlEntryExists = true;
		try{
			Integer countOfRecords = valueLinkDao.getCountOfValueLinkRecordsForSO(soId); 
			if (countOfRecords != null &&  countOfRecords.intValue() > 0 )
			{
				vlEntryExists = false;	
			}
			return vlEntryExists;
		}
		catch (DataServiceException e) {
			e.printStackTrace();
			String msg = e.getMessage();
			if (StringUtils.isBlank(msg)) {
				throw new SLBusinessServiceException("An Exception has occurred in processing you adjustment", e);
			} else {
				throw new SLBusinessServiceException(msg, e);
			}
		}
	}
	
	
	public double getCompletedAmount(long vendorId) throws SLBusinessServiceException
	{
		
		try{
			Double amount = valueLinkDao.getCompletedSOLedgerAmount(vendorId); 
			Double slaCreditAmt=valueLinkDao.getSLACreditAmount(vendorId);
			
			if(amount!=null)
			{
			amount=amount.doubleValue();
			}
			
			else
			{
			amount=0.0;	
			}
			
			if(slaCreditAmt!=null)
			{
				slaCreditAmt=slaCreditAmt.doubleValue();
			}
			
			else
			{
				slaCreditAmt=0.0;	
			}
			
			return amount+slaCreditAmt;	
		}
		catch (DataServiceException e) {
			e.printStackTrace();
			String msg = e.getMessage();
			if (StringUtils.isBlank(msg)) {
				throw new SLBusinessServiceException("An Exception has occurred in processing you adjustment", e);
			} else {
				throw new SLBusinessServiceException(msg, e);
			}
		}	
		
		
	}

	/**
	 * getSTANId.
	 * 
	 * @return String
	 */
	private String getSTANId() {

		Long nextSTAN = identifierDao.getNextIdentifier(CommonConstants.STAN_ID);
		return nextSTAN.toString();
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.valuelink.IValueLinkBO#getValueLinkMessageDetail(java.lang.Long)
	 */
	public ValueLinkEntryVO getValueLinkMessageDetail(Long fullfillmentEntryId) throws SLBusinessServiceException {

		ValueLinkEntryVO fullfillmentEntryVO = null;
		ValueLinkEntryVO nextFullfillmentEntryVO = null;

		try {
			logger.debug("FullfillmentTransactionBO-->getValueLinkMessageDetail()-->START");
			fullfillmentEntryVO = valueLinkDao.getValueLinkMessageDetail(fullfillmentEntryId);
			populateMessageIdentifier(fullfillmentEntryVO);
			if (fullfillmentEntryVO != null) {
				nextFullfillmentEntryVO = valueLinkDao.getNextValueLinkEntryDetails(fullfillmentEntryVO);
				while (true) {
					if ((nextFullfillmentEntryVO != null) && (nextFullfillmentEntryVO.getFullfillmentEntryId() != null)) {
						if (((nextFullfillmentEntryVO.getTransAmount() != null) && (nextFullfillmentEntryVO.getTransAmount().doubleValue() > 0.0))
							|| (CommonConstants.BUSINESS_TRANSACTION_NEW_PROVIDER == nextFullfillmentEntryVO.getBusTransId().intValue())
							|| (CommonConstants.BUSINESS_TRANSACTION_NEW_BUYER == nextFullfillmentEntryVO.getBusTransId().intValue())) {
							fullfillmentEntryVO.setNextFullfillmentEntryId(nextFullfillmentEntryVO.getFullfillmentEntryId());
							break;
						} else {
							nextFullfillmentEntryVO = valueLinkDao.getNextValueLinkEntryDetails(nextFullfillmentEntryVO);
							if (nextFullfillmentEntryVO != null) {
								fullfillmentEntryVO.setNextFullfillmentEntryId(nextFullfillmentEntryVO.getFullfillmentEntryId());
							}
						}
					} else {
						break;
					}
				}
			}
		} catch (DataServiceException dae) {
			logger.error("getValueLinkMessageDetail-->DataServiceException-->", dae);
			throw new SLBusinessServiceException("getValueLinkMessageDetail-->EXCEPTION-->", dae);
		} catch (Exception e) {
			logger.error("getValueLinkMessageDetail-->Exception-->", e);
			throw new SLBusinessServiceException("getValueLinkMessageDetail-->EXCEPTION-->", e);
		}
		return fullfillmentEntryVO;
	}

	/**
	 * initializeTransactionToAutoCreateBehaviorMap.
	 * 
	 * @return void
	 */
	private void initializeTransactionToAutoCreateBehaviorMap() {

		final long[] autoCreateBuyerOnlyTransactions =
			new long[] { CommonConstants.BUSINESS_TRANSACTION_DEPOSITS_FUNDS_BUYER, CommonConstants.BUSINESS_TRANSACTION_BUYER_DEPOSITS_FROM_AMEX,
				CommonConstants.BUSINESS_TRANSACTION_BUYER_DEPOSITS_FROM_SEARS, CommonConstants.BUSINESS_TRANSACTION_BUYER_DEPOSITS_FROM_V_MX,
				CommonConstants.BUSINESS_TRANSACTION_BUYER_INSTANT_ACH_DEPOSIT, CommonConstants.BUSINESS_TRANSACTION_WITHDRAW_FUNDS_BUYER,
				CommonConstants.BUSINESS_TRANSACTION_SLA_REFUNDS_TO_BUYERS_V_MC, CommonConstants.BUSINESS_TRANSACTION_SLA_REFUNDS_TO_BUYERS_AMEX };

		final long[] autoCreateBuyerTransactions =
			new long[] { CommonConstants.BUSINESS_TRANSACTION_SLA_CREDITS_SLB_TO_BUYER, CommonConstants.BUSINESS_TRANSACTION_SLA_DEBITS_SLB_FROM_BUYER,
				CommonConstants.BUSINESS_TRANSACTION_CANCEL_SO_WO_PENALTY, CommonConstants.BUSINESS_TRANSACTION_VOID_SO, CommonConstants.BUSINESS_TRANSACTION_DECREASE_SO_ESCROW,
				CommonConstants.BUSINESS_TRANSACTION_INCREASE_SO_ESCROW, CommonConstants.BUSINESS_TRANSACTION_POST_SO, CommonConstants.BUSINESS_TRANSACTION_COMPLETION,
				CommonConstants.BUSINESS_TRANSACTION_SLA_ESCHEATMENT_SLB_FROM_BUYER,CommonConstants.BUSINESS_TRANSACTION_ESCHEATMENT_BUYER_DEBIT_REVERSAL}; 

		final long[] autoCreateProviderTransactions =
			new long[] { CommonConstants.BUSINESS_TRANSACTION_SLA_CREDITS_SLB_TO_PROVIDER, CommonConstants.BUSINESS_TRANSACTION_SLA_DEBITS_SLB_FROM_PROVIDER,
				CommonConstants.BUSINESS_TRANSACTION_WITHDRAW_FUNDS_PROVIDER, CommonConstants.BUSINESS_TRANSACTION_WITHDRAW_FUNDS_PROVIDER_REVERSAL, 
				CommonConstants.BUSINESS_TRANSACTION_SLA_ESCHEATMENT_SLB_FROM_PROVIDER};   

		final long[] autoCreateBuyerAndProviderTransactions =
			new long[] { CommonConstants.BUSINESS_TRANSACTION_CANCEL_SO, CommonConstants.BUSINESS_TRANSACTION_RELEASE_SO_PAYMENT };

		final long[] noAutoCreateTransactions =
			new long[] { CommonConstants.BUSINESS_TRANSACTION_NEW_BUYER, CommonConstants.BUSINESS_TRANSACTION_NEW_PROVIDER,
				CommonConstants.BUSINESS_TRANSACTION_SLA_DEPOSITS_TO_OPERATIONS, CommonConstants.BUSINESS_TRANSACTION_SLA_WITHDRAWS_FROM_OPERATIONS,
				CommonConstants.BUSINESS_TRANSACTION_MARKETPLACE_WITHDRAW_FUNDS };

		for (long id : noAutoCreateTransactions) {
			mapTransactionToAutoCreateBehavior.put(id, AutoCreateAccountBehavior.NoAutoCreate);
		}
		for (long id : autoCreateBuyerTransactions) {
			mapTransactionToAutoCreateBehavior.put(id, AutoCreateAccountBehavior.AutoCreateBuyer);
		}
		for (long id : autoCreateProviderTransactions) {
			mapTransactionToAutoCreateBehavior.put(id, AutoCreateAccountBehavior.AutoCreateProvider);
		}
		for (long id : autoCreateBuyerAndProviderTransactions) {
			mapTransactionToAutoCreateBehavior.put(id, AutoCreateAccountBehavior.AutoCreateBuyerAndProvider);
		}
		for (long id : autoCreateBuyerOnlyTransactions) {
			mapTransactionToAutoCreateBehavior.put(id, AutoCreateAccountBehavior.AutoCreateBuyerOnly);
		}
	}
		
	private boolean checkMessageForZeroTransAmt(ValueLinkEntryVO entry) throws SLBusinessServiceException
	{
		try
		{
			if(CommonConstants.BUSINESS_TRANSACTION_NEW_PROVIDER == entry.getBusTransId() ||   
				CommonConstants.BUSINESS_TRANSACTION_NEW_BUYER == entry.getBusTransId() || entry.getTransAmount().doubleValue()>0.0)
			{
				return true;
			}
			return false;
		}
		catch(Exception e)
		{
			logger.error("checkMessageForZeroTransAmt-->Exception-->", e);
			throw new SLBusinessServiceException("checkMessageForZeroTransAmt-->EXCEPTION-->", e);
		}
	}

	/**
	 * populateMessageIdentifier.
	 * 
	 * @param fullfillmentEntryVO 
	 * 
	 * @return void
	 * 
	 * @throws Exception 
	 */
	private void populateMessageIdentifier(ValueLinkEntryVO fullfillmentEntryVO) throws Exception {

		if ((fullfillmentEntryVO != null) && (fullfillmentEntryVO.getMessageTypeId() != null)) {
			if (CommonConstants.MESSAGE_TYPE_ACTIVATION == fullfillmentEntryVO.getMessageTypeId().intValue()) {
				fullfillmentEntryVO.setMessageIdentifier(CommonConstants.ACTIVATION_RELOAD_REQUEST);
			} else if (CommonConstants.MESSAGE_TYPE_RELOAD == fullfillmentEntryVO.getMessageTypeId().intValue()) {
				fullfillmentEntryVO.setMessageIdentifier(CommonConstants.ACTIVATION_RELOAD_REQUEST);
			} else if (CommonConstants.MESSAGE_TYPE_REDEMPTION == fullfillmentEntryVO.getMessageTypeId().intValue()) {
				fullfillmentEntryVO.setMessageIdentifier(CommonConstants.REDEMPTION_REQUEST);
			} else if (CommonConstants.MESSAGE_TYPE_BALANCE_ENQ == fullfillmentEntryVO.getMessageTypeId().intValue()) {
				fullfillmentEntryVO.setMessageIdentifier(CommonConstants.BALANCE_ENQUIRY_REQUEST);
			} else if (CommonConstants.MESSAGE_TYPE_HEARTBEAT == fullfillmentEntryVO.getMessageTypeId().intValue()) {
				fullfillmentEntryVO.setMessageIdentifier(CommonConstants.SHARP_HEARTBEAT_REQUEST);
			} else if (CommonConstants.MESSAGE_TYPE_VOID == fullfillmentEntryVO.getMessageTypeId().intValue()) {
				fullfillmentEntryVO.setMessageIdentifier(CommonConstants.REDEMPTION_REQUEST_PARTIAL_APPROVAL_VOID);
			}
		}
	}

	/**
	 * sendValueLinkEntries.
	 * 
	 * @param entries 
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	private void sendValueLinkEntries(List<ValueLinkEntryVO> entries) throws SLBusinessServiceException {

		try {
			if (entries != null && !entries.isEmpty()) {
				//ValueLinkEntryVO firstMessage =  entries.get(0);
				// only send the messages if this is not a zero dollar transaction
				//if(checkMessageForZeroTransAmt(firstMessage)) {
				// write entries to the database
				valueLinkDao.loadTransactionEntries(entries);
			}
		} catch (Exception e) {
			logger.error("sendValueLinkEntries", e);
			throw new SLBusinessServiceException(e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.valuelink.IValueLinkBO#sendValueLinkRequest(com.servicelive.wallet.valuelink.vo.ValueLinkVO)
	 */
	public void sendValueLinkRequest(ValueLinkVO request) throws SLBusinessServiceException {

		AutoCreateAccountBehavior behavior = this.mapTransactionToAutoCreateBehavior.get(request.getBusinessTransactionId());
		logger.info("inside sendValueLinkRequest");
		logger.info("behavior"+behavior);
		logger.info("request"+request);
		List<ValueLinkEntryVO> entries = this.valueLinkRules.createBusinessTransaction(request, behavior);
		sendValueLinkEntries(entries);
	}

	/**
	 * setIdentifierDao.
	 * 
	 * @param identifierDao 
	 * 
	 * @return void
	 */
	public void setIdentifierDao(IIdentifierDao identifierDao) {

		this.identifierDao = identifierDao;
	}

//	/**
//	 * setRemoteSharpGateway.
//	 * 
//	 * @param remoteSharpGateWay 
//	 * 
//	 * @return void
//	 */
//	public void setRemoteSharpGateway(ISharpGateway remoteSharpGateWay) {
//
//		this.remoteSharpGateway = remoteSharpGateWay;
//	}


	/**
	 * setValueLinkDao.
	 * 
	 * @param valueLinkDao 
	 * 
	 * @return void
	 */
	public void setValueLinkDao(IValueLinkDao valueLinkDao) {

		this.valueLinkDao = valueLinkDao;
	}

	/**
	 * setValueLinkRules.
	 * 
	 * @param valueLinkRules 
	 * 
	 * @return void
	 */
	public void setValueLinkRules(IValueLinkRulesBO valueLinkRules) {

		this.valueLinkRules = valueLinkRules;
	}

	public List<ValueLinkEntryVO> getValueLinkEntries(String[] valueLinkEntryIds, Boolean groupId) throws SLBusinessServiceException {
		List<ValueLinkEntryVO> feVOs = new ArrayList<ValueLinkEntryVO>();
		List<ValueLinkEntryVO> feVOsGroup = new ArrayList<ValueLinkEntryVO>();
		ValueLinkEntryVO valueLinkEntryVO = null;
		try{
			for (String fEntryId : valueLinkEntryIds) {
				if (groupId){
					feVOsGroup = valueLinkDao.getValueLinkMessageDetailByGroupId(new Long(fEntryId));
					if (feVOsGroup.isEmpty()){
						throw new SLBusinessServiceException(CommonConstants.NO_FULFILLMENT_ENTRY_FOUND);
					}else{
						feVOs.addAll(feVOsGroup);	
				}
				
				}else{
					valueLinkEntryVO = valueLinkDao.getValueLinkMessageDetail(new Long(fEntryId));
					if (valueLinkEntryVO != null) {
						feVOs.add(valueLinkEntryVO);
					} else {
						throw new SLBusinessServiceException(CommonConstants.NO_FULFILLMENT_ENTRY_FOUND);
					}
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw new SLBusinessServiceException(CommonConstants.NUMERIC_ENTRIES_ALLOWED, e);
		} catch (DataServiceException e) {
			e.printStackTrace();
			throw new SLBusinessServiceException(CommonConstants.DATABASE_ERROR_OCCURED, e);
		} catch (Exception e) {
			e.printStackTrace();
			String msg = e.getMessage();
			if (StringUtils.isBlank(msg)) {
				throw new SLBusinessServiceException(CommonConstants.GENERIC_ERROR_OCCURED, e);
			} else {
				throw new SLBusinessServiceException(msg, e);
			}
		}
		return feVOs;
	}
	
	private void markValueLinkQueueForResend(Long valueLinkEntryId){
		ValueLinkQueueEntryVO queueEntry = new ValueLinkQueueEntryVO();
		queueEntry.setQueueStatusId((int)ValueLinkQueueEntryVO.QueueStatus.WAITING.intValue());
		queueEntry.setFulfillmentEntryId(valueLinkEntryId);
		queueEntry.setSendCount(0);
		valueLinkQueueDao.updateQueueEntry(queueEntry);
	}
	
	public List<ValueLinkEntryVO> processGroupResend(String[] groupIds, String comments, String userName) throws SLBusinessServiceException {
		String errorGroups = "";
		String correctGroups = "";
		List<ValueLinkEntryVO> feVOs = new ArrayList<ValueLinkEntryVO>();
		try {
			for (String fullfillmentGroupId : groupIds) {
				ValueLinkEntryVO valueLinkEntryVO = valueLinkDao.getFirstUnReconciledTrans(new Long(fullfillmentGroupId));
				if (valueLinkEntryVO != null) {
					if ((valueLinkEntryVO.getReconsiledInd() == null) || 
						(valueLinkEntryVO.getReconsiledInd() == 3 || 
						 valueLinkEntryVO.getReconsiledInd() > 5)) {
						// remove the process response log
						valueLinkDao.deleteProcessResponseLogging(valueLinkEntryVO.getFullfillmentEntryId());
						// clear the reconciled indicator and reconciled date
						valueLinkDao.updateReconcileStatusToNull(valueLinkEntryVO.getFullfillmentEntryId());
						// insert an admin log
						valueLinkDao.insertAdminToolLogging(valueLinkEntryVO.getFullfillmentEntryId().toString(), comments, userName);
						feVOs.add(valueLinkEntryVO);
						correctGroups = correctGroups + " "	+ fullfillmentGroupId;
					}
				} else {
					errorGroups = errorGroups + " " + fullfillmentGroupId;
				}
			}
			if (StringUtils.isNotBlank(errorGroups)) {
				if (StringUtils.isNotBlank(correctGroups))
					errorGroups = CommonConstants.RESENT_SUCCESS_MESSAGE
							+ correctGroups + ".<br>"
							+ CommonConstants.NONRECONCILED_ENTRIES_ERROR
							+ errorGroups + ".<br>";
				else
					errorGroups = CommonConstants.NONRECONCILED_ENTRIES_ERROR
							+ errorGroups + ".<br>";
				throw new Exception(errorGroups);
			}

		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw new SLBusinessServiceException(CommonConstants.NUMERIC_ENTRIES_ALLOWED, e);
		} catch (DataServiceException e) {
			e.printStackTrace();
			throw new SLBusinessServiceException(CommonConstants.DATABASE_ERROR_OCCURED, e);
		} catch (Exception e) {
			e.printStackTrace();
			String msg = e.getMessage();
			if (StringUtils.isBlank(msg)) {
				throw new SLBusinessServiceException(CommonConstants.GENERIC_ERROR_OCCURED, e);
			} else {
				throw new SLBusinessServiceException(msg, e);
			}
		}
		return feVOs;
	}
	
	public Map<String, Long> reverseValueLinkTransaction(String[] valueLinkIds, String comments, String userName) throws SLBusinessServiceException{
		Map<String, Long> origIdsToNew = new HashMap<String, Long>();
		try{
			for(String vlId : valueLinkIds){
				ValueLinkEntryVO valueLinkEntryVO = valueLinkDao.getValueLinkMessageDetail(new Long(vlId));
				if (valueLinkEntryVO != null) {
					
					valueLinkEntryVO.setFullfillmentGroupId(valueLinkDao.insertFullfillmentGroup());
					
					// change from credit to debit and vice versa
					if (valueLinkEntryVO.getEntryTypeId() == null) {
						throw new Exception(CommonConstants.ENTRY_TYPE_MISSING);
					}
					if (valueLinkEntryVO.getEntryTypeId() == CommonConstants.ENTRY_TYPE_CREDIT) {
						valueLinkEntryVO.setEntryTypeId((long)CommonConstants.ENTRY_TYPE_DEBIT);
						valueLinkEntryVO.setMessageIdentifier(CommonConstants.BALANCE_ADJUSTMENT_REDEEM_REQUEST);
					} else {
						valueLinkEntryVO.setEntryTypeId((long)CommonConstants.ENTRY_TYPE_CREDIT);
						valueLinkEntryVO.setMessageIdentifier(CommonConstants.BALANCE_ADJUSTMENT_RELOAD_REQUEST);
					}
					if (valueLinkEntryVO.getMessageTypeId() == CommonConstants.MESSAGE_TYPE_RELOAD) {
						valueLinkEntryVO.setMessageTypeId((long)CommonConstants.MESSAGE_TYPE_REDEMPTION);
						valueLinkEntryVO.setMessageDescId(CommonConstants.MESSAGE_DESC_ID_BALANCE_DEBIT);
					} else {
						valueLinkEntryVO.setMessageTypeId((long)CommonConstants.MESSAGE_TYPE_RELOAD);
						valueLinkEntryVO.setMessageDescId(CommonConstants.MESSAGE_DESC_ID_BALANCE_CREDIT);
					}
					
					//reverse PAN and State
					String originState = valueLinkEntryVO.getOrigStateCode();
					Long originPan = valueLinkEntryVO.getOriginatingPan();
					valueLinkEntryVO.setOriginatingPan(valueLinkEntryVO.getDestinationPan());
					valueLinkEntryVO.setOrigStateCode(valueLinkEntryVO.getDestStateCode());
					valueLinkEntryVO.setDestinationPan(originPan);
					valueLinkEntryVO.setDestStateCode(originState);
					
					// Indicate original fulfillment entry that was adjusted.
					valueLinkEntryVO.setEntryRemark("SL Admin Adjustment: " + vlId);
					initializeValueLinkVoInsert(valueLinkEntryVO, userName);
					valueLinkDao.insertFullfillmentEntry(valueLinkEntryVO);
					valueLinkDao.insertAdminToolLogging(vlId, comments, userName);
				} else {
					throw new SLBusinessServiceException(CommonConstants.NO_FULFILLMENT_ENTRY_FOUND);
				}
				origIdsToNew.put(vlId, valueLinkEntryVO.getFullfillmentEntryId());
			}		
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw new SLBusinessServiceException(CommonConstants.NUMERIC_ENTRIES_ALLOWED, e);
		} catch (DataServiceException e) {
			e.printStackTrace();
			throw new SLBusinessServiceException(CommonConstants.DATABASE_ERROR_OCCURED, e);
		} catch (Exception e) {
			e.printStackTrace();
			String msg = e.getMessage();
			if (StringUtils.isBlank(msg)) {
				throw new SLBusinessServiceException(CommonConstants.GENERIC_ERROR_OCCURED, e);
			} else {
				throw new SLBusinessServiceException(msg, e);
			}
		}
		return origIdsToNew;
	}
	
	public Map<String, Long> createValueLinkWithNewAmount(String fulfillmentEntryId, Double newAmount, String comments, String userName) throws SLBusinessServiceException{
		Map<String, Long> origIdsToNew = new HashMap<String, Long>();
		try {
			ValueLinkEntryVO fullfillmentEntryVO = valueLinkDao.getValueLinkMessageDetail(new Long(fulfillmentEntryId));
			if (fullfillmentEntryVO != null) {
				fullfillmentEntryVO.setFullfillmentGroupId(valueLinkDao.insertFullfillmentGroup());
				
				//set the new value
				fullfillmentEntryVO.setTransAmount(newAmount);
				
				// Indicates original fulfillment entry that was adjusted.
				fullfillmentEntryVO.setEntryRemark("SL Admin Created from: " + fulfillmentEntryId);
				
				//reset a few values to null
				initializeValueLinkVoInsert(fullfillmentEntryVO, userName);
				
				Long newFeId = valueLinkDao.insertFullfillmentEntry(fullfillmentEntryVO);
				valueLinkDao.insertAdminToolLogging(fulfillmentEntryId, comments, userName);
                origIdsToNew.put(fulfillmentEntryId, newFeId);
            } else {
                throw new SLBusinessServiceException(CommonConstants.NO_FULFILLMENT_ENTRY_FOUND);
            }
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw new SLBusinessServiceException(CommonConstants.NUMERIC_ENTRIES_ALLOWED, e);
		} catch (DataServiceException e) {
			e.printStackTrace();
			throw new SLBusinessServiceException(CommonConstants.DATABASE_ERROR_OCCURED, e);
		} catch (Exception e) {
			e.printStackTrace();
			String msg = e.getMessage();
			if (StringUtils.isBlank(msg)) {
				throw new SLBusinessServiceException(CommonConstants.GENERIC_ERROR_OCCURED, e);
			} else {
				throw new SLBusinessServiceException(msg, e);
			}
		}	
		return origIdsToNew;
	}
	
	private void initializeValueLinkVoInsert(ValueLinkEntryVO valueLinkEntryVO, String userName) {
		valueLinkEntryVO.setStanId(getSTANId());
		valueLinkEntryVO.setPtdReconsiledInd(10);
		valueLinkEntryVO.setModifiedBy(userName);
		valueLinkEntryVO.setPromoCodeId(null);
		valueLinkEntryVO.setNextFullfillmentEntryId(null);
		valueLinkEntryVO.setPtdReconsiledInd(null);
		valueLinkEntryVO.setPtdReconsiledDate(null);
		valueLinkEntryVO.setAchReconsiledInd(null);
		valueLinkEntryVO.setAchReconsiledDate(null);
		valueLinkEntryVO.setCcAuthorizationNo(null);
		valueLinkEntryVO.setCcAuthorizedDate(null);
		valueLinkEntryVO.setCcAuthorizedInd(null);
		valueLinkEntryVO.setRetrievalRefId(null);
		valueLinkEntryVO.setReconsiledDate(null);
		valueLinkEntryVO.setReconsiledInd(null);
	}

	
	public void setValueLinkQueueDao(IValueLinkQueueDao valueLinkQueueDao) {
		this.valueLinkQueueDao = valueLinkQueueDao;
	}
	
	public IIsoRequestProcessor getRequestProcessor() {
		return requestProcessor;
	}

	public void setRequestProcessor(IIsoRequestProcessor requestProcessor) {
		this.requestProcessor = requestProcessor;
	}

	public ISharpGateway getSharpGateway() {
		return sharpGateway;
	}

	public void setSharpGateway(ISharpGateway sharpGateway) {
		this.sharpGateway = sharpGateway;
	}
}
