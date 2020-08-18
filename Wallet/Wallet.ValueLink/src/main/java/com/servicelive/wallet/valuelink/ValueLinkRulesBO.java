package com.servicelive.wallet.valuelink;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.servicelive.common.CommonConstants;
import com.servicelive.common.exception.DataServiceException;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.lookup.vo.ValueLinkAccountsVO;
import com.servicelive.wallet.common.IIdentifierDao;
import com.servicelive.wallet.serviceinterface.vo.ValueLinkEntryVO;
import com.servicelive.wallet.serviceinterface.vo.ValueLinkVO;
import com.servicelive.wallet.valuelink.dao.IValueLinkDao;
import com.servicelive.wallet.valuelink.vo.ValueLinkRuleVO;

// TODO: Auto-generated Javadoc
/**
 * Class ValueLinkRulesBO.
 */
public class ValueLinkRulesBO implements IValueLinkRulesBO {

	private Logger logger = Logger.getLogger(ValueLinkRulesBO.class);
	
	/** identifierDao. */
	private IIdentifierDao identifierDao;

	//
	// TODO - This needs to be replaced with proper key generation in a later phase
	//

	/** valueLinkDao. */
	private IValueLinkDao valueLinkDao;

	/**
	 * autoReconcileZeroDollarTransactions.
	 * 
	 * @param vlEntries 
	 * 
	 * @return void
	 */
	private void autoReconcileZeroDollarTransactions(List<ValueLinkEntryVO> vlEntries) {

		for (ValueLinkEntryVO vlEntry : vlEntries) {
			if (vlEntry.getTransAmount() == 0.0d) {
				vlEntry.setPtdReconsiledInd(CommonConstants.PTD_RECONCILATION_STATUS_SUCCESS);
				java.util.Date today = new java.util.Date();
				vlEntry.setPtdReconsiledDate(new java.sql.Timestamp(today.getTime()));
			}
		}
	}

	/**
	 * convertRequestToCreateBuyer.
	 * 
	 * @param request 
	 * 
	 * @return ValueLinkVO
	 */
	private ValueLinkVO convertRequestToCreateBuyer(ValueLinkVO request) {

		ValueLinkVO copy = new ValueLinkVO(request);
		copy.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_NEW_BUYER);
		copy.setAdminCredit(request.isAdminCredit());
		copy.setBuyerV2CreditAmount(0.0d);
		copy.setBuyerV2DebitAmount(0.0d);
		return copy;
	}

	/**
	 * convertRequestToCreateProvider.
	 * 
	 * @param request 
	 * 
	 * @return ValueLinkVO
	 */
	private ValueLinkVO convertRequestToCreateProvider(ValueLinkVO request) {

		ValueLinkVO copy = new ValueLinkVO(request);
		copy.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_NEW_PROVIDER);
		copy.setProviderV1CreditAmount(0.0d);
		return copy;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.valuelink.IValueLinkRulesBO#createBusinessTransaction(com.servicelive.wallet.valuelink.vo.ValueLinkVO, com.servicelive.wallet.valuelink.IValueLinkRulesBO.AutoCreateAccountBehavior)
	 */
	public List<ValueLinkEntryVO> createBusinessTransaction(ValueLinkVO request, AutoCreateAccountBehavior autoCreateBehavior) throws SLBusinessServiceException {

		List<ValueLinkEntryVO> entries = null;
		switch (autoCreateBehavior) {
		default:
		case NoAutoCreate:
			entries = createBusinessTransaction(request, false, false);
			break;
		case AutoCreateBuyer:
			entries = createBusinessTransaction(request, true, false);
			break;
		case AutoCreateProvider:
			entries = createBusinessTransaction(request, false, true);
			break;
		case AutoCreateBuyerAndProvider:
			entries = createBusinessTransaction(request, true, true);
			break;
		case AutoCreateBuyerOnly:
			entries = createBusinessTransactionOrAutoCreateBuyer(request);
			break;
		}
		return entries;
	}

	/**
	 * createBusinessTransaction.
	 * 
	 * @param request 
	 * @param autoCreateBuyer 
	 * @param autoCreateProvider 
	 * 
	 * @return List<ValueLinkEntryVO>
	 * 
	 * @throws SLBusinessServiceException 
	 */
	List<ValueLinkEntryVO> createBusinessTransaction(ValueLinkVO request, boolean autoCreateBuyer, boolean autoCreateProvider) throws SLBusinessServiceException {

		logger.info("creating business transaction for " + request.getBusinessTransactionId());
		List<ValueLinkEntryVO> vlEntries = new ArrayList<ValueLinkEntryVO>();

		try {
			if (autoCreateBuyer && !isBuyerCreated(request)) {
				vlEntries.addAll(createBusinessTransaction(convertRequestToCreateBuyer(request), AutoCreateAccountBehavior.NoAutoCreate));
			}
			if (autoCreateProvider && !isProviderCreated(request)) {
				vlEntries.addAll(createBusinessTransaction(convertRequestToCreateProvider(request), AutoCreateAccountBehavior.NoAutoCreate));
			}

			vlEntries.addAll(createValueLinkEntries(request));

			autoReconcileZeroDollarTransactions(vlEntries);
			generateTransactionIds(vlEntries);

		} catch (Exception e) {
			throw new SLBusinessServiceException(e.getMessage(), e);
		}

		return vlEntries;
	}

	/**
	 * createBusinessTransactionOrAutoCreateBuyer.
	 * 
	 * @param request 
	 * 
	 * @return List<ValueLinkEntryVO>
	 * 
	 * @throws SLBusinessServiceException 
	 */
	List<ValueLinkEntryVO> createBusinessTransactionOrAutoCreateBuyer(ValueLinkVO request) throws SLBusinessServiceException {

		List<ValueLinkEntryVO> vlEntries = null;

		try {
			if (!isBuyerCreated(request)) {
				vlEntries = createBusinessTransaction(convertRequestToCreateBuyer(request), AutoCreateAccountBehavior.NoAutoCreate);
			} else {
				vlEntries = createValueLinkEntries(request);
			}
			autoReconcileZeroDollarTransactions(vlEntries);
			generateTransactionIds(vlEntries);

		} catch (Exception e) {
			throw new SLBusinessServiceException(e.getMessage(), e);
		}

		return vlEntries;
	}

	/**
	 * createValueLinkEntries.
	 * 
	 * @param request 
	 * 
	 * @return List<ValueLinkEntryVO>
	 * 
	 * @throws SLBusinessServiceException 
	 */
	private List<ValueLinkEntryVO> createValueLinkEntries(ValueLinkVO request) throws SLBusinessServiceException {

		List<ValueLinkEntryVO> entries = new ArrayList<ValueLinkEntryVO>();
		logger.info("creating value link entries for " + request.getBusinessTransactionId());

		try {
			List<ValueLinkRuleVO> rules = valueLinkDao.getValueLinkRules(request.getBusinessTransactionId(), request.getFundingTypeId());

			for (ValueLinkRuleVO rule : rules) {

				ValueLinkEntryVO entry = new ValueLinkEntryVO();
				entry.setSoId(request.getServiceOrderId());
				entry.setReferenceNo(request.getServiceOrderId());

				entry.setBusTransId(request.getBusinessTransactionId());
				entry.setFullfillmentEntryRuleId(rule.getValueLinkRuleId());

				entry.setEntryTypeId(rule.getEntryTypeId());
				entry.setEntityTypeId(rule.getEntityTypeId());

				entry.setSortOrder(rule.getSortOrder());
				entry.setPromoCode(rule.getPromoCode());
				entry.setPromoCodeId(rule.getPromoCodeId());
				if (request.getFundingTypeId() != null){
					entry.setFundingTypeId(request.getFundingTypeId());
				}else{
					entry.setFundingTypeId(0l);
				}

				setEntityId(request, entry, rule);
				setTransactionAmount(request, entry, rule);
				setPanAndStateCode(request, entry, rule);
				setMessageProperties(entry, rule);

				entries.add(entry);
			}
		} catch (DataServiceException e) {
			throw new SLBusinessServiceException(e.getMessage(), e);
		}

		return entries;
	}

	private void generateTransactionIds(List<ValueLinkEntryVO> vlEntries) throws Exception {

		long groupId = valueLinkDao.insertFullfillmentGroup();
		long i = 1;
		for (ValueLinkEntryVO vlEntry : vlEntries) {
			String stanId = getSTANId();

			vlEntry.setFullfillmentGroupId(groupId);
			vlEntry.setStanId(stanId);
			vlEntry.setSortOrder(i++);
		}
	}

	/**
	 * getPanByEntityAndAccountType.
	 * 
	 * @param entityType 
	 * @param accountType 
	 * @param request 
	 * 
	 * @return Long
	 * @throws SLBusinessServiceException 
	 */
	private Long getPanByEntityAndAccountType(long entityType, String accountType, ValueLinkVO request) throws SLBusinessServiceException {

		Long pan = null;
		if (entityType == CommonConstants.LEDGER_ENTITY_TYPE_BUYER || entityType == CommonConstants.LEDGER_ENTITY_TYPE_SERVICELIVE_ESCROW) {
			if (accountType.equals(CommonConstants.VL_ACCOUNT_BUYER_V1)) {
				pan = request.getBuyerV1AccountNumber();
			} else if (accountType.equals(CommonConstants.VL_ACCOUNT_BUYER_V2)) {
				pan = request.getBuyerV2AccountNumber();
			}
		} else if (entityType == CommonConstants.LEDGER_ENTITY_TYPE_PROVIDER) {
			if (accountType.equals(CommonConstants.VL_ACCOUNT_PROVIDER_V1)) {
				pan = request.getProviderV1AccountNumber();
			}
		} else if (entityType == CommonConstants.LEDGER_ENTITY_TYPE_SERVICELIVE_MAIN || entityType == CommonConstants.LEDGER_ENTITY_TYPE_SERVICELIVE_OPERATION) {
			if (accountType.equals(CommonConstants.VL_ACCOUNT_POSTING_FEE)) {
				pan = getSLAccountNumber(CommonConstants.VL_ACCOUNT_POSTING_FEE);
				//pan = request.getServiceLiveSL1AccountNumber();
			} else if (accountType.equals(CommonConstants.VL_ACCOUNT_PREFUNDING_ACCOUNT)) {
				pan = getSLAccountNumber(CommonConstants.VL_ACCOUNT_PREFUNDING_ACCOUNT);
				//pan = request.getServiceLiveSL3AccountNumber();
			}
		}
		return pan;
	}
	
	private Long getSLAccountNumber(String slType) throws SLBusinessServiceException{
		try {
			return valueLinkDao.getSLValueLinkAccounts(slType).getV1AccountNo();
		} catch (DataServiceException e) {
			throw new SLBusinessServiceException(e);
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

	/**
	 * getStateByEntityType.
	 * 
	 * @param entityType 
	 * @param request 
	 * 
	 * @return String
	 */
	private String getStateByEntityType(long entityType, ValueLinkVO request) {

		String stateCode = null;

		if (entityType == CommonConstants.LEDGER_ENTITY_TYPE_BUYER || entityType == CommonConstants.LEDGER_ENTITY_TYPE_SERVICELIVE_ESCROW) {
			stateCode = request.getBuyerState();
		} else if (entityType == CommonConstants.LEDGER_ENTITY_TYPE_PROVIDER) {
			stateCode = request.getProviderState();
		} else if (entityType == CommonConstants.LEDGER_ENTITY_TYPE_SERVICELIVE_MAIN || entityType == CommonConstants.LEDGER_ENTITY_TYPE_SERVICELIVE_OPERATION) {
			stateCode = "IL";
		}
		return stateCode;
	}

	/**
	 * isBuyerCreated.
	 * 
	 * @param request 
	 * 
	 * @return boolean
	 * @throws SLBusinessServiceException 
	 */
	private boolean isBuyerCreated(ValueLinkVO request) throws SLBusinessServiceException {
		
		// if we have V account # then we're good
		if( request.getBuyerV1AccountNumber() != null || request.getBuyerV2AccountNumber() != null ) {
			return true;
		}
		//check in the database if the vl ids are present
		ValueLinkAccountsVO accounts = getVLAccounts(request.getBuyerId(), CommonConstants.LEDGER_ENTITY_TYPE_BUYER);
		//delete the fullfillment_entry when v1AccountNo is zero
		if(null!= accounts && 0 == accounts.getV1AccountNo()){
			try{
				valueLinkDao.deleteVLAccountsWhenAcNoisZero(accounts); 
			}
			catch(Exception e){
				throw new SLBusinessServiceException(e);
			}
		}
		
		if(accounts != null && 0!= accounts.getV1AccountNo() && 0!= accounts.getV2AccountNo() && 
				(accounts.getV1AccountNo() != null || accounts.getV2AccountNo() != null)){
			request.setBuyerV1AccountNumber(accounts.getV1AccountNo());
			request.setBuyerV2AccountNumber(accounts.getV2AccountNo());
			return true;
		}

		// otherwise, check to see if there is a pending activation request
		return isActivationRequestPending(request.getBuyerId());
	}

	private ValueLinkAccountsVO getVLAccounts(long entityId, long entityTypeId) throws SLBusinessServiceException{
		ValueLinkAccountsVO criteria = new ValueLinkAccountsVO();
		criteria.setLedgerEntityId(entityId);
		criteria.setEntityTypeId(entityTypeId);
		try{
			return valueLinkDao.getValueLinkAccounts(criteria);
		}catch(Exception e){
			throw new SLBusinessServiceException(e);
		}
	}
	private boolean isActivationRequestPending(Long entityId) throws SLBusinessServiceException{
		boolean isActivationRequestPending = false;
		try {
			isActivationRequestPending = valueLinkDao.isActivationRequestPendingForEntity(entityId);
		} catch (DataServiceException e) {
			throw new SLBusinessServiceException(e);
		}
		
		return ( isActivationRequestPending );
	}
	/**
	 * isBuyerRule.
	 * 
	 * @param rule 
	 * 
	 * @return boolean
	 */
	private boolean isBuyerRule(ValueLinkRuleVO rule) {

		return (rule.getDestEntityType() == CommonConstants.LEDGER_ENTITY_TYPE_BUYER || rule.getDestEntityType() == CommonConstants.LEDGER_ENTITY_TYPE_SERVICELIVE_ESCROW);
	}

	/**
	 * isCreditRule.
	 * 
	 * @param rule 
	 * 
	 * @return boolean
	 */
	private boolean isCreditRule(ValueLinkRuleVO rule) {

		return (rule.getEntryTypeId() == CommonConstants.ENTRY_TYPE_CREDIT);
	}

	/**
	 * isDebitRule.
	 * 
	 * @param rule 
	 * 
	 * @return boolean
	 */
	private boolean isDebitRule(ValueLinkRuleVO rule) {

		return (rule.getEntryTypeId() == CommonConstants.ENTRY_TYPE_DEBIT);
	}

	/**
	 * isProviderCreated.
	 * 
	 * @param request 
	 * 
	 * @return boolean
	 * @throws SLBusinessServiceException 
	 */
	private boolean isProviderCreated(ValueLinkVO request) throws SLBusinessServiceException {

		// if we have V account # then we're good
		if(request.getProviderV1AccountNumber() != null){
			return true;
		}
		
		//check in the database if the vl id is present
		ValueLinkAccountsVO accounts = getVLAccounts(request.getProviderId(), CommonConstants.LEDGER_ENTITY_TYPE_PROVIDER);
		
		//delete the fullfillment_entry when v1AccountNo is zero
		if(null!= accounts &&  0 == accounts.getV1AccountNo()){
			try{
				valueLinkDao.deleteVLAccountsWhenAcNoisZero(accounts); 
			}
			catch(Exception e){
				throw new SLBusinessServiceException(e);
			}
		}
		if(accounts != null && 0!= accounts.getV1AccountNo() && 
				accounts.getV1AccountNo() != null){
		
			request.setProviderV1AccountNumber(accounts.getV1AccountNo());
			return true;
		}
		// otherwise, check to see if there is a pending activation request
		return isActivationRequestPending(request.getProviderId());
		
	}

	/**
	 * isProviderRule.
	 * 
	 * @param rule 
	 * 
	 * @return boolean
	 */
	private boolean isProviderRule(ValueLinkRuleVO rule) {

		return (rule.getDestEntityType() == CommonConstants.LEDGER_ENTITY_TYPE_PROVIDER);
	}

	/**
	 * isServiceLiveMainRule.
	 * 
	 * @param rule 
	 * 
	 * @return boolean
	 */
	private boolean isServiceLiveMainRule(ValueLinkRuleVO rule) {

		return (rule.getDestEntityType() == CommonConstants.LEDGER_ENTITY_TYPE_SERVICELIVE_MAIN);
	}

	/**
	 * isServiceLiveOpsRule.
	 * 
	 * @param rule 
	 * 
	 * @return boolean
	 */
	private boolean isServiceLiveOpsRule(ValueLinkRuleVO rule) {

		return (rule.getDestEntityType() == CommonConstants.LEDGER_ENTITY_TYPE_SERVICELIVE_OPERATION);
	}

	/**
	 * isSL1Rule.
	 * 
	 * @param rule 
	 * 
	 * @return boolean
	 */
	private boolean isSL1Rule(ValueLinkRuleVO rule) {

		return (rule.getDestAcctType().equals(CommonConstants.VL_ACCOUNT_POSTING_FEE));
	}

	/**
	 * isSL3Rule.
	 * 
	 * @param rule 
	 * 
	 * @return boolean
	 */
	private boolean isSL3Rule(ValueLinkRuleVO rule) {

		return (rule.getDestAcctType().equals(CommonConstants.VL_ACCOUNT_PREFUNDING_ACCOUNT));
	}

	/**
	 * isV1Rule.
	 * 
	 * @param rule 
	 * 
	 * @return boolean
	 */
	private boolean isV1Rule(ValueLinkRuleVO rule) {

		return (rule.getDestAcctType().equals(CommonConstants.VL_ACCOUNT_BUYER_V1));
	}

	/**
	 * isV2Rule.
	 * 
	 * @param rule 
	 * 
	 * @return boolean
	 */
	private boolean isV2Rule(ValueLinkRuleVO rule) {

		return (rule.getDestAcctType().equals(CommonConstants.VL_ACCOUNT_BUYER_V2));
	}

	/**
	 * setEntityId.
	 * 
	 * @param request 
	 * @param entry 
	 * @param rule 
	 * 
	 * @return void
	 */
	private void setEntityId(ValueLinkVO request, ValueLinkEntryVO entry, ValueLinkRuleVO rule) {

		if (rule.getEntityTypeId() == CommonConstants.LEDGER_ENTITY_TYPE_BUYER) {
			entry.setLedgerEntityId(request.getBuyerId());
		} else if (rule.getEntityTypeId() == CommonConstants.LEDGER_ENTITY_TYPE_SERVICELIVE_ESCROW) {
			entry.setLedgerEntityId(request.getBuyerId());
		} else if (rule.getEntityTypeId() == CommonConstants.LEDGER_ENTITY_TYPE_PROVIDER) {
			entry.setLedgerEntityId(request.getProviderId());
		} else if (rule.getEntityTypeId() == CommonConstants.LEDGER_ENTITY_TYPE_SERVICELIVE_MAIN) {
			entry.setLedgerEntityId(Long.valueOf(CommonConstants.ENTITY_ID_SERVICELIVE));
		} else if (rule.getEntityTypeId() == CommonConstants.LEDGER_ENTITY_TYPE_SERVICELIVE_OPERATION) {
			entry.setLedgerEntityId(Long.valueOf(CommonConstants.ENTITY_ID_SERVICELIVE_OPERATION));
		}
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

	/**
	 * setMessageProperties.
	 * 
	 * @param entry 
	 * @param rule 
	 * 
	 * @return void
	 */
	private void setMessageProperties(ValueLinkEntryVO entry, ValueLinkRuleVO rule) {

		entry.setMessageTypeId(rule.getMessageTypeId());
		if (entry.getMessageTypeId() == CommonConstants.MESSAGE_TYPE_RELOAD) {
			entry.setMessageDescId(CommonConstants.MESSAGE_DESC_ID_ACTIVATION_RELOAD);
			entry.setMessageIdentifier(CommonConstants.ACTIVATION_RELOAD_REQUEST);
		} else if (entry.getMessageTypeId() == CommonConstants.MESSAGE_TYPE_ACTIVATION) {
			entry.setMessageDescId(CommonConstants.MESSAGE_DESC_ID_ACTIVATION_RELOAD);
			entry.setMessageIdentifier(CommonConstants.ACTIVATION_RELOAD_REQUEST);
		} else if (entry.getMessageTypeId() == CommonConstants.MESSAGE_TYPE_REDEMPTION) {
			entry.setMessageDescId(CommonConstants.MESSAGE_DESC_ID_REDEMPTION);
			entry.setMessageIdentifier(CommonConstants.REDEMPTION_REQUEST);
		}
	}

	/**
	 * setPanAndStateCode.
	 * 
	 * @param request 
	 * @param entry 
	 * @param rule 
	 * 
	 * @return void
	 * @throws SLBusinessServiceException 
	 */
	private void setPanAndStateCode(ValueLinkVO request, ValueLinkEntryVO entry, ValueLinkRuleVO rule) throws SLBusinessServiceException {

		Long originatingPan = getPanByEntityAndAccountType(rule.getOrigEntityType(), rule.getOrigAcctType(), request);
		Long destinationPan = getPanByEntityAndAccountType(rule.getDestEntityType(), rule.getDestAcctType(), request);
		String originatingState = getStateByEntityType(rule.getOrigEntityType(), request);
		String destinationState = getStateByEntityType(rule.getDestEntityType(), request);

		entry.setOriginatingPan(originatingPan);
		entry.setDestinationPan(destinationPan);
		entry.setOrigStateCode(originatingState);
		entry.setDestStateCode(destinationState);
		entry.setPrimaryAccountNumber(destinationPan);
	}

	/**
	 * setTransactionAmount.
	 * 
	 * @param request 
	 * @param entry 
	 * @param rule 
	 * 
	 * @return void
	 */
	private void setTransactionAmount(ValueLinkVO request, ValueLinkEntryVO entry, ValueLinkRuleVO rule) {

		entry.setTransAmount(0.0d);
		if (isBuyerRule(rule)) {
			if (isV1Rule(rule)) {
				// if originating entity is SL1, amount to be posting fee
				if (isCreditRule(rule)) {
					if (request.getBusinessTransactionId() == CommonConstants.BUSINESS_TRANSACTION_NEW_BUYER &&	request.isAdminCredit()){
						entry.setTransAmount(0d);
					}else if (CommonConstants.VL_ACCOUNT_POSTING_FEE.equals(rule.getOrigAcctType())){
						entry.setTransAmount(request.getServiceLiveSL1DebitAmount());
					}else{
						entry.setTransAmount(request.getBuyerV1CreditAmount());
					}
				} else if (isDebitRule(rule)) {
					entry.setTransAmount(request.getBuyerV1DebitAmount());
				}
			} else if (isV2Rule(rule)) {
				if (isCreditRule(rule)) {
					entry.setTransAmount(request.getBuyerV2CreditAmount());
				} else if (isDebitRule(rule)) {
					entry.setTransAmount(request.getBuyerV2DebitAmount());
				}
			}
		} else if (isProviderRule(rule)) {
			if (isV1Rule(rule)) {
				if (isCreditRule(rule)) {
					entry.setTransAmount(request.getProviderV1CreditAmount());
				} else if (isDebitRule(rule)) {
					entry.setTransAmount(request.getProviderV1DebitAmount());
				}
			}
		} else if (isServiceLiveMainRule(rule)) {
			if (isSL1Rule(rule)) {
				if (isCreditRule(rule)) {
					entry.setTransAmount(request.getServiceLiveSL1CreditAmount());
				} else if (isDebitRule(rule)) {
					entry.setTransAmount(request.getServiceLiveSL1DebitAmount());
				}
			}
		} else if (isServiceLiveOpsRule(rule)) {
			if (isSL3Rule(rule)) {
				if (isCreditRule(rule)) {
					entry.setTransAmount(request.getServiceLiveSL3CreditAmount());
				} else if (isDebitRule(rule)) {
					entry.setTransAmount(request.getServiceLiveSL3DebitAmount());
				}
			}
		}
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
}
