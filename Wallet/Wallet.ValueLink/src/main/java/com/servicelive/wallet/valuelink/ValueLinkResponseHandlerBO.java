package com.servicelive.wallet.valuelink;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.servicelive.common.CommonConstants;
import com.servicelive.common.exception.DataServiceException;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.lookup.vo.ValueLinkAccountsVO;
import com.servicelive.wallet.alert.IAlertBO;
import com.servicelive.wallet.serviceinterface.vo.ValueLinkEntryVO;
import com.servicelive.wallet.serviceinterface.vo.ValueLinkVO;
import com.servicelive.wallet.valuelink.dao.IValueLinkDao;
import com.servicelive.wallet.valuelink.dao.IValueLinkQueueDao;
import com.servicelive.wallet.valuelink.sharp.ISharpCallback;
import com.servicelive.wallet.valuelink.sharp.ISharpGateway;
import com.servicelive.wallet.valuelink.sharp.iso.IIsoResponseProcessor;
import com.servicelive.wallet.valuelink.sharp.iso.vo.IsoMessageVO;
import com.servicelive.wallet.valuelink.vo.ValueLinkQueueEntryVO;
import com.servicelive.wallet.valuelink.vo.ValueLinkQueueEntryVO.QueueStatus;
// TODO: Auto-generated Javadoc
/**
 * Class ValueLinkResponseHandlerBO.
 */
public class ValueLinkResponseHandlerBO implements ISharpCallback {

	/** logger. */
	private static final Logger logger = Logger.getLogger(ValueLinkResponseHandlerBO.class);

	/** isoResponseProcessor. */
	private IIsoResponseProcessor isoResponseProcessor;

	/** sharpGateway. */
	private ISharpGateway sharpGateway;

	/** valueLink. */
	private IValueLinkBO valueLink;

	/** valueLinkDao. */
	private IValueLinkDao valueLinkDao;
	
	/** valueLinkQueueDao. */
	private IValueLinkQueueDao valueLinkQueueDao;
	
	/** accountDao. */
	//private IAccountDao accountDao;
	
	/** alert. */
	private IAlertBO alert;


	private int sendCountLimit;
	
	public void setSendCountLimit(int sendCountLimit) {
		this.sendCountLimit = sendCountLimit;
	}

	/**
	 * ValueLinkResponseHandlerBO.
	 * 
	 * @param sharpGateway 
	 */
	public ValueLinkResponseHandlerBO(ISharpGateway sharpGateway) {

		this.sharpGateway = sharpGateway;
		this.sharpGateway.setDelegate(this);
	}
	
	public ValueLinkResponseHandlerBO() { }

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.valuelink.sharp.ISharpCallback#handleMessage(byte[])
	 */
	public void handleMessage(byte[] message) throws SLBusinessServiceException {
		if(message==null){
			logger.info("getting nulls from IP Socket and returning");
			return;
		}
		logger.info("Response handler received message.... "+new String(message));
		logger.info("message length = "+message.length);
		IsoMessageVO response = isoResponseProcessor.processResponse(message);
		if( response != null ) {
			handleResponse(response,message);
		} else {
			logger.error("Unable to parse response : " + new String(message));
		}
	}

	/**
	 * handleResponse.
	 * 
	 * @param response 
	 * @param message 
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	private void handleResponse(IsoMessageVO response, byte [] message) throws SLBusinessServiceException {
		try {
						
			valueLinkDao.insertProcessResponseLogging(response.getFullfillmentEntryId(), new String(message)); 
			logger.info("response.isBalanceInquiryResponse() "+response.isBalanceInquiryResponse());
			logger.info("response.getFullfillmentEntryId()) "+response.getFullfillmentEntryId());
			logger.info("response.getMessageIdentifier()) "+response.getMessageIdentifier());
			logger.info("response.getMessageTypeId()) "+response.getMessageTypeId());
			logger.info("response.getAdditionalAmount()) "+response.getAdditionalAmount());
			logger.info("response.getPartiallyAppvdAmount()) "+response.getPartiallyAppvdAmount());
			logger.info("response.getTransAmount()) "+response.getTransAmount());
			logger.info("response.getPrimaryAccountNumber())" +response.getPrimaryAccountNumber());
			byte[] bytes=message;
			String s = new String(bytes);
			logger.info("SENDING RESPONSE "+s);
            if( response.isBalanceInquiryResponse() ) {
				handleBalanceInquiryResponse(response);
			} else if( response.isHeartbeatResponse() ) {
				handleHeartbeatResponse(response);
			} else {
				handleIsoResponse(response);
				/**SL-20723 : Made code change to update the v1_account_balacnce & v2_account_balacnce
				 * in the fullfillment_vlaccounts table during value link interaction */
				handleBalanceUpdate(response);
			}
		} catch (DataServiceException e) {
			logger.error(e.getMessage(), e);
			
			throw new SLBusinessServiceException(e);
		}
	}
	
	private void handleHeartbeatResponse(IsoMessageVO response) throws DataServiceException {

		logger.info("Heartbeat response");
		
		Map<String, Object> actionCodeAndStatus = getActionCodeAndStatus(response);
		boolean status = (Boolean)actionCodeAndStatus.get("status");
		if( status ) {

			valueLinkDao.updateValueLinkStatus(true);
			
		} else {

			valueLinkDao.updateValueLinkStatus(false);
			
		}
	}
	
	/**
	 * handleIsoResponse.
	 * 
	 * @param response 
	 * 
	 * @return void
	 * 
	 * @throws DataServiceException 
	 * @throws SLBusinessServiceException 
	 */
	private void handleIsoResponse(IsoMessageVO response) throws DataServiceException, SLBusinessServiceException {
		logger.info("Enter handleIsoResponse method");
		Map<String, Object> actionCodeAndStatus = getActionCodeAndStatus(response);
		boolean status = (Boolean)actionCodeAndStatus.get("status");
		long actionCodeId = (Long)actionCodeAndStatus.get("actionCodeId");
		double vlBalance = getBalance(response);
		ValueLinkEntryVO details = getValueLinkMessageDetail(response.getFullfillmentEntryId());
		
		if( details == null ) {
			logger.error("Could not find fulfillment entry " + response.getFullfillmentEntryId());
			return; 
		}
		
		details.setPrimaryAccountNumber( response.getPrimaryAccountNumber() );
		details.setActionCode( response.getActionCode() );
		if( status ) {
			handleIsoSuccessResponse(details, vlBalance, actionCodeId);
		} else {
			logger.info("Going to handle failure for fulfillment entry" + response.getFullfillmentEntryId());
			// set timestamp and partially approved amount
			details.setTimeStamp(response.getTimeStamp());
			details.setPartiallyAppvdAmount(response.getPartiallyAppvdAmount());
			handleIsoFailureResponse(details, vlBalance, actionCodeId);
		}
		deleteValueLinkQueueEntry(details);
	}
	
	/**
	 * handleIsoSuccessResponse.
	 * 
	 * @param details 
	 * @param vlBalance 
	 * @param actionCodeId 
	 * 
	 * @return void
	 * 
	 * @throws DataServiceException 
	 */
	private void handleIsoSuccessResponse(ValueLinkEntryVO details, double vlBalance, long actionCodeId ) throws DataServiceException {
		if (details.getMessageTypeId() == CommonConstants.MESSAGE_TYPE_ACTIVATION) {
			manageVLAccounts(details);
			valueLinkDao.updateReconcileStatus(details.getFullfillmentEntryId(), actionCodeId, details.getPrimaryAccountNumber(), vlBalance);
			
			logger.info("Activation ---> " + details.getLedgerEntityId() + " " + details.getEntityTypeId() );
			valueLinkDao.updateFullfillmentVLAccount(details);
		} else {
			valueLinkDao.updateReconcileStatus(details.getFullfillmentEntryId(), actionCodeId, null, vlBalance);
		}
	}

	/**
	 * deleteValueLinkQueueEntry.
	 * 
	 * @param details 
	 * 
	 * @return void
	 */
	private void deleteValueLinkQueueEntry(ValueLinkEntryVO details) {
		ValueLinkQueueEntryVO criteria = new ValueLinkQueueEntryVO();
		criteria.setFulfillmentEntryId(details.getFullfillmentEntryId());
		criteria.setFulfillmentGroupId(details.getFullfillmentGroupId());
		valueLinkQueueDao.deleteQueueEntry(criteria);
	}
	
	/**
	 * handleIsoFailureResponse.
	 * 
	 * @param details 
	 * @param vlBalance 
	 * @param actionCodeId 
	 * 
	 * @return void
	 * 
	 * @throws DataServiceException 
	 * @throws SLBusinessServiceException 
	 */
	private void handleIsoFailureResponse(ValueLinkEntryVO details, double vlBalance, long actionCodeId) throws DataServiceException, SLBusinessServiceException {
		logger.info("Failure response");
		
		valueLinkDao.updateReconcileStatus(details.getFullfillmentEntryId(), actionCodeId, null, vlBalance);
		valueLinkDao.updateFullfillmentLogStatus(details.getFullfillmentGroupId(), CommonConstants.FAILURE);
		
		// generic error processing
		// 1. send email
		// 2. update queue status to error (2)
		processVLError(details);

		// situational error processing
		if (CommonConstants.RESPONSE_ACTION_CODE_VERIFYCARD.equals(details.getActionCode())) {
			processResponseVerifyCard(details.clone());
		} else if (CommonConstants.RESPONSE_ACTION_CODE_AUTHORIZER_UNAVAILABLE.equals(details.getActionCode())) {
			processResponseAuthorizerUnavailable(details.clone());
		} else if (CommonConstants.RESPONSE_ACTION_CODE_CANNOT_PARSE_MESSAGE.equals(details.getActionCode())) {
			// no specific error handling
		} else if (CommonConstants.RESPONSE_ACTION_CODE_PARTIAL_APPROVAL.equals(details.getActionCode())) {
			processResponsePartialApprovalVoid(details.clone());
		} else if (CommonConstants.RESPONSE_ACTION_CODE_DECLINE.equals(details.getActionCode())) {
			// no specific error handling
		} else if (CommonConstants.RESPONSE_ACTION_CODE_CARD_NOT_EFFECTIVE.equals(details.getActionCode())) {
			// no specific error handling
		} else if (CommonConstants.RESPONSE_ACTION_CODE_SUSPECT.equals(details.getActionCode())) {
			// no specific error handling
		} else if (CommonConstants.RESPONSE_ACTION_CODE_INTERNAL_FORMAT_ERROR.equals(details.getActionCode())) {
			// no specific error handling
		} else if (CommonConstants.RESPONSE_ACTION_CODE_EXTERNAL_FORMAT_ERROR.equals(details.getActionCode())) {
			// no specific error handling
		}
	}

	/**
	 * processResponsePartialApprovalVoid.
	 * 
	 * @param details 
	 * @param actionCode 
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	private void processResponsePartialApprovalVoid(ValueLinkEntryVO details) throws SLBusinessServiceException {
		try {				
			// create a new request to "undo" the partially approved amount
			
			// store in reference as we need later to handle response
			details.setReferenceNo(details.getFullfillmentEntryId().toString());
			details.setFullfillmentGroupId(valueLinkDao.insertFullfillmentGroup());

			details.setMessageTypeId((long)CommonConstants.MESSAGE_TYPE_VOID);
			details.setMessageDescId(CommonConstants.MESSAGE_DESC_ID_VOID);
			details.setMessageIdentifier(CommonConstants.REDEMPTION_REQUEST_PARTIAL_APPROVAL_VOID); // jj
			details.setEntryTypeId((long)CommonConstants.ENTRY_TYPE_CREDIT);
			
			// overwrite the Trans Amount with Partially Approved amount
			details.setTransAmount(details.getPartiallyAppvdAmount());
			
			details.setNextFullfillmentEntryId(null);
			
			//Only if there is some partially approved amount , send it else do not
			if(details.getPartiallyAppvdAmount() > 0.0)
			{
				logger.info("Void partial approval : " + details.getFullfillmentEntryId());
				valueLinkDao.insertFullfillmentEntry(details);
			}
			
		} catch (Exception e) {
			logger.error("processResponsePartialApprovalVoid-->Exception-->", e);
			throw new SLBusinessServiceException("processResponsePartialApprovalVoid-->EXCEPTION-->", e);
		}
	}

	/**
	 * processResponseVerifyCard.
	 * 
	 * @param details 
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	private void processResponseVerifyCard(ValueLinkEntryVO details) throws SLBusinessServiceException {
		logger.info("processResponseActionCode-->Error verifying credit card");
		try {
			// Move V1 and V2 to history table and delete the entries
			ValueLinkAccountsVO fullfillmentVLAccountsVO = new ValueLinkAccountsVO();
			fullfillmentVLAccountsVO.setEntityTypeId(details.getEntityTypeId());
			fullfillmentVLAccountsVO.setLedgerEntityId(details.getLedgerEntityId());
			valueLinkDao.moveFullfillmentVLAccountToHistory(fullfillmentVLAccountsVO);
			if ((CommonConstants.LEDGER_ENTITY_TYPE_BUYER == details.getEntityTypeId().intValue()) || (CommonConstants.LEDGER_ENTITY_TYPE_SERVICELIVE_ESCROW == details.getEntityTypeId().intValue())) {
				ValueLinkVO request = new ValueLinkVO();
				request.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_NEW_BUYER);
				request.setFundingTypeId(CommonConstants.FUNDING_TYPE_PRE_FUNDED);
				request.setBuyerId(details.getLedgerEntityId());
				valueLink.sendValueLinkRequest(request);
			} else if (CommonConstants.LEDGER_ENTITY_TYPE_PROVIDER == details.getEntityTypeId().intValue()) {
				ValueLinkVO request = new ValueLinkVO();
				request.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_NEW_PROVIDER);
				request.setFundingTypeId(CommonConstants.FUNDING_TYPE_PRE_FUNDED);
				request.setBuyerId(details.getLedgerEntityId());
				valueLink.sendValueLinkRequest(request);
			}
		} catch (Exception e) {
			logger.error("processResponseVerifyCard-->Exception-->", e);
			throw new SLBusinessServiceException("processResponseVerifyCard-->EXCEPTION-->", e);
		}
	}

	/**
	 * processResponseAuthorizerUnavailable.
	 * 
	 * @param details 
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 * @throws DataServiceException 
	 */
	private void processResponseAuthorizerUnavailable(ValueLinkEntryVO details) throws SLBusinessServiceException, DataServiceException {
		logger.info("authorizer unavailable");
		// indicate that VL is down
		valueLinkDao.updateValueLinkStatus(false);
	}

	private void processVLError(ValueLinkEntryVO details)
			throws DataServiceException, SLBusinessServiceException {
		// send a failure message
		sendEmailVLMessageFailure(details);
		// load the queue entry (valuelink_entry_queue)
		ValueLinkQueueEntryVO queueEntry = valueLinkQueueDao.getQueueEntryById(details.getFullfillmentEntryId());
		if( queueEntry == null ) {
			logger.error("Could not find value link queue entry " + details.getFullfillmentEntryId());
			return;
		}
		logger.info("updating queue status to error (2)");
		// set queue status to Error (2)
		queueEntry.setQueueStatus(QueueStatus.ERROR);
		valueLinkQueueDao.updateQueueEntry(queueEntry);
	}

	/**
	 * sendEmailVLMessageFailure.
	 * 
	 * @param details 
	 * @param actionCode 
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	private void sendEmailVLMessageFailure(ValueLinkEntryVO details) throws SLBusinessServiceException {
		try {
			logger.info("send VL failure message");
			String actionCodeDesc = valueLinkDao.getActionCodeDesc(details.getActionCode());
			String buyerRole = null;
			details.setActionCodeDesc(actionCodeDesc);
			if ((details.getEntityTypeId() != null) && (details.getEntityTypeId().intValue() == CommonConstants.LEDGER_ENTITY_TYPE_BUYER)) {
				buyerRole = CommonConstants.BUYER_ROLE;
			} else if ((details.getEntityTypeId() != null) && (details.getEntityTypeId().intValue() == CommonConstants.LEDGER_ENTITY_TYPE_PROVIDER)) {
				buyerRole = CommonConstants.PROVIDER_ROLE;
			} else if ((details.getEntityTypeId() != null) && (details.getEntityTypeId().intValue() == CommonConstants.LEDGER_ENTITY_TYPE_SERVICELIVE_ESCROW)) {
				buyerRole = CommonConstants.SERVICELIVE_ROLE;
			}
			alert.sendEmailVLMessageFailure( 
				details.getLedgerEntityId(),
				buyerRole,
				details.getActionCode(),
				details.getActionCodeDesc(),
				details.getFullfillmentGroupId(),
				details.getFullfillmentEntryId(),
				details.getEntryDate(),
				details.getMessageIdentifier(),
				details.getPrimaryAccountNumber(),
				details.getEntityTypeDesc(),
				details.getTransAmount(),
				details.getSoId(),
				CommonConstants.EMAIL_TEMPLATE_VL_RESPONSE_FAILURE);
		} catch (Exception e) {
			logger.error("sendEmailVLResponseFailure-->Exception-->", e);
			throw new SLBusinessServiceException("sendEmailVLResponseFailure-->EXCEPTION-->", e);
		}
	}
	
	/**
	 * handleBalanceInquiryResponse.
	 * 
	 * @param response 
	 * 
	 * @return void
	 * 
	 * @throws DataServiceException 
	 * @throws SLBusinessServiceException 
	 */
	private void handleBalanceInquiryResponse(IsoMessageVO response) throws DataServiceException, SLBusinessServiceException {
		logger.info("Balance inquiry response");
		double vlBalance = getBalance(response);
		ValueLinkEntryVO vlEntry = new ValueLinkEntryVO();
		vlEntry.setVlBalance(vlBalance);
		vlEntry.setPrimaryAccountNumber(response.getPrimaryAccountNumber());
		valueLinkDao.updateV1V2AccountBalanceEnquiry(vlEntry);
		ValueLinkQueueEntryVO queueEntry = valueLinkQueueDao.getQueueEntryById(response.getFullfillmentEntryId());
		if( queueEntry == null ) {
			logger.error("Could not find value link queue entry " + response.getFullfillmentEntryId());
			return;
		}
		
		ValueLinkQueueEntryVO criteria = new ValueLinkQueueEntryVO();
		criteria.setFulfillmentEntryId(response.getFullfillmentEntryId());
		criteria.setFulfillmentGroupId(response.getFullfillmentGroupId());
		valueLinkQueueDao.deleteQueueEntry(criteria);
		
	}

	
	/**
	 * handleBalanceUpdate.
	 * 
	 * @param response 
	 * 
	 * @return void
	 * 
	 * @throws DataServiceException 
	 */
	private void handleBalanceUpdate(IsoMessageVO response) throws DataServiceException {
		double vlBalance = getBalance(response);
		ValueLinkEntryVO vlEntry = new ValueLinkEntryVO();
		vlEntry.setVlBalance(vlBalance);
		vlEntry.setPrimaryAccountNumber(response.getPrimaryAccountNumber());
		valueLinkDao.updateV1V2AccountBalance(vlEntry);
	}
	
	
	/**
	 * getActionCodeAndStatus.
	 * 
	 * @param message 
	 * 
	 * @return Map<String,Object>
	 * 
	 * @throws DataServiceException 
	 */
	private Map<String,Object> getActionCodeAndStatus(IsoMessageVO message) throws DataServiceException {

		Map<String, Object> rval = new HashMap<String, Object>();
		Map<String, Object> returnMap = valueLinkDao.getActionCodeIdAndStatus(message.getActionCode());
		if (returnMap == null) {
			rval.put("status", false);
			rval.put("actionCodeId", CommonConstants.RESPONSE_ACTION_CODE_UNMAPPED);
		} else {
			rval.put("status", (Boolean)returnMap.get("status"));
			rval.put("actionCodeId", (Long) returnMap.get("actionCodeId"));
		}
		logger.info("Status : " + rval.get("status") + " " + rval.get("actionCodeId"));
		return rval;
	}
	
	/**
	 * getBalance.
	 * 
	 * @param message 
	 * 
	 * @return double
	 */
	private double getBalance(IsoMessageVO message) {
		double vlBalance = 0.0d;
		if ((message.getAdditionalAmount() != null) && (message.getAdditionalAmount().length() > 16)) {
			vlBalance = Double.parseDouble(message.getAdditionalAmount().substring(5, 17)) / 100;
		}
		return vlBalance;
	}


	/**
	 * getValueLinkMessageDetail.
	 * 
	 * @param valueLinkEntryId 
	 * 
	 * @return ValueLinkEntryVO
	 * 
	 * @throws SLBusinessServiceException 
	 */
	public ValueLinkEntryVO getValueLinkMessageDetail(Long valueLinkEntryId) throws SLBusinessServiceException {
		ValueLinkEntryVO valueLinkEntryVO = null;
		ValueLinkEntryVO nextValueLinkEntryVO = null;

		try {
			logger.debug("ValueLinkTransactionBO-->getValueLinkMessageDetail()-->START");
			valueLinkEntryVO = valueLinkDao.getValueLinkMessageDetail(valueLinkEntryId);
			populateMessageIdentifier(valueLinkEntryVO);
			if (valueLinkEntryVO != null) {
				nextValueLinkEntryVO = valueLinkDao.getNextValueLinkEntryDetails(valueLinkEntryVO);
				while (true) {
					if ((nextValueLinkEntryVO != null) && (nextValueLinkEntryVO.getFullfillmentEntryId() != null)) {
						if (((nextValueLinkEntryVO.getTransAmount() != null) && (nextValueLinkEntryVO.getTransAmount().doubleValue() > 0.0)) || (CommonConstants.BUSINESS_TRANSACTION_NEW_PROVIDER == nextValueLinkEntryVO.getBusTransId().intValue()) || (CommonConstants.BUSINESS_TRANSACTION_NEW_BUYER == nextValueLinkEntryVO.getBusTransId().intValue())) {
							valueLinkEntryVO.setNextFullfillmentEntryId(nextValueLinkEntryVO.getFullfillmentEntryId());
							break;
						} else {
							nextValueLinkEntryVO = valueLinkDao.getNextValueLinkEntryDetails(nextValueLinkEntryVO);
							if (nextValueLinkEntryVO != null) {
								valueLinkEntryVO.setNextFullfillmentEntryId(nextValueLinkEntryVO.getFullfillmentEntryId());
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
		
		return valueLinkEntryVO;
	}

	/**
	 * populateMessageIdentifier.
	 * 
	 * @param valueLinkEntryVO 
	 * 
	 * @return void
	 * 
	 * @throws Exception 
	 */
	private void populateMessageIdentifier(ValueLinkEntryVO valueLinkEntryVO) throws Exception {
		if ((valueLinkEntryVO != null) && (valueLinkEntryVO.getMessageTypeId() != null)) {
			if (CommonConstants.MESSAGE_TYPE_ACTIVATION == valueLinkEntryVO.getMessageTypeId().intValue()) {
				valueLinkEntryVO.setMessageIdentifier(CommonConstants.ACTIVATION_RELOAD_REQUEST);
			} else if (CommonConstants.MESSAGE_TYPE_RELOAD == valueLinkEntryVO.getMessageTypeId().intValue()) {
				valueLinkEntryVO.setMessageIdentifier(CommonConstants.ACTIVATION_RELOAD_REQUEST);
			} else if (CommonConstants.MESSAGE_TYPE_REDEMPTION == valueLinkEntryVO.getMessageTypeId().intValue()) {
				valueLinkEntryVO.setMessageIdentifier(CommonConstants.REDEMPTION_REQUEST);
			} else if (CommonConstants.MESSAGE_TYPE_BALANCE_ENQ == valueLinkEntryVO.getMessageTypeId().intValue()) {
				valueLinkEntryVO.setMessageIdentifier(CommonConstants.BALANCE_ENQUIRY_REQUEST);
			} else if (CommonConstants.MESSAGE_TYPE_HEARTBEAT == valueLinkEntryVO.getMessageTypeId().intValue()) {
				valueLinkEntryVO.setMessageIdentifier(CommonConstants.SHARP_HEARTBEAT_REQUEST);
			} else if (CommonConstants.MESSAGE_TYPE_VOID == valueLinkEntryVO.getMessageTypeId().intValue()) {
				valueLinkEntryVO.setMessageIdentifier(CommonConstants.REDEMPTION_REQUEST_PARTIAL_APPROVAL_VOID);
			}
		}
	}

	/**
	 * manageVLAccounts.
	 * 
	 * @param entry 
	 * 
	 * @return void
	 * 
	 * @throws DataServiceException 
	 */
	private void manageVLAccounts(ValueLinkEntryVO entry) throws DataServiceException {
		boolean flag = valueLinkDao.checkVLAccountValidity(entry.getLedgerEntityId());
		ValueLinkAccountsVO vlAccountVO = new ValueLinkAccountsVO();
		if (!flag) {
			vlAccountVO.setLedgerEntityId(entry.getLedgerEntityId());
			vlAccountVO.setEntityTypeId(entry.getEntityTypeId());
			vlAccountVO.setV1AccountNo(entry.getPrimaryAccountNumber());
			valueLinkDao.createVLAcountEntry(vlAccountVO);
		} else {
			vlAccountVO.setLedgerEntityId(entry.getLedgerEntityId());
			vlAccountVO.setV2AccountNo(entry.getPrimaryAccountNumber());
			valueLinkDao.updateVLAccount(vlAccountVO);
		}
	}
	
	/**
	 * setIsoResponseProcessor.
	 * 
	 * @param isoResponseProcessor 
	 * 
	 * @return void
	 */
	public void setIsoResponseProcessor(IIsoResponseProcessor isoResponseProcessor) {

		this.isoResponseProcessor = isoResponseProcessor;
	}

	/**
	 * setValueLink.
	 * 
	 * @param valueLink 
	 * 
	 * @return void
	 */
	public void setValueLink(IValueLinkBO valueLink) {

		this.valueLink = valueLink;
	}

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
	 * setValueLinkQueueDao.
	 * 
	 * @param valueLinkQueueDao 
	 * 
	 * @return void
	 */
	public void setValueLinkQueueDao(IValueLinkQueueDao valueLinkQueueDao) {
	
		this.valueLinkQueueDao = valueLinkQueueDao;
	}

	public void setAlert(IAlertBO alert) {
		this.alert = alert;
	}

}
