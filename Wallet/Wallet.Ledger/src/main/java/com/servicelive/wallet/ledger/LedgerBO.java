package com.servicelive.wallet.ledger;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.servicelive.common.CommonConstants;
import com.servicelive.common.exception.DataServiceException;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.common.properties.IApplicationProperties;
import com.servicelive.common.util.Cryptography;
import com.servicelive.common.util.Cryptography128;
import com.servicelive.common.util.MoneyUtil;
import com.servicelive.common.util.ServiceLiveStringUtils;
import com.servicelive.common.vo.SLAccountVO;
import com.servicelive.lookup.dao.IAccountDao;
import com.servicelive.wallet.ledger.dao.ITransactionDao;
import com.servicelive.wallet.ledger.vo.LedgerBusinessTransactionVO;
import com.servicelive.wallet.ledger.vo.TransactionEntryVO;
import com.servicelive.wallet.ledger.vo.TransactionHistoryVO;
import com.servicelive.wallet.ledger.vo.TransactionVO;
import com.servicelive.wallet.serviceinterface.vo.LedgerEntryType;
import com.servicelive.wallet.serviceinterface.vo.LedgerVO;
import com.servicelive.wallet.serviceinterface.vo.OrderPricingVO;
import com.servicelive.wallet.serviceinterface.vo.ReceiptVO;
import com.servicelive.wallet.serviceinterface.vo.ProviderWithdrawalErrorVO;

public class LedgerBO implements ILedgerBO {

	public enum TransactionType {
		
		Admin, 
		DepositBuyerCash, 
		DepositBuyerCreditCard, 
		ServiceOrder, 
		WithdrawBuyerCash, 
		WithdrawBuyerCreditCard, 
		WithdrawProvider, 
		WithdrawProviderReversal
	}

	private static final Logger logger = Logger.getLogger(LedgerBO.class);

	private Cryptography cryptography;

	private Map<Long, TransactionType> mapTransactionToTransactionType = new HashMap<Long, TransactionType>();

	private ITransactionBuilder transactionBuilder;

	private ITransactionDao transactionDao;

	private IAccountDao accountDao;
	
	private IApplicationProperties applicationProperties;
	
	private Cryptography128 cryptography128;
	
	
	public IApplicationProperties getApplicationProperties() {
		return applicationProperties;
	}

	public void setApplicationProperties(
			IApplicationProperties applicationProperties) {
		this.applicationProperties = applicationProperties;
	}
	public LedgerBO() {
		initializeTransactionToAutoCreateBehaviorMap();
	}

	public double getBuyerTotalDeposit(Long buyerId) throws SLBusinessServiceException {
		try {
			return transactionDao.getTotalDepositByEntityId(buyerId.intValue(), CommonConstants.LEDGER_ENTITY_TYPE_BUYER);
		} catch (DataServiceException ex) {
			throw new SLBusinessServiceException("getBuyerTotalDeposit", ex);
		}
	}

	public LedgerBusinessTransactionVO adminTransaction(LedgerVO ledgerVO, OrderPricingVO orderPricingVO)
	throws SLBusinessServiceException {
		try {
			long transactionEntryId = 0;
			LedgerBusinessTransactionVO business =
				transactionBuilder.createTransactions(CommonConstants.LEDGER_ENTITY_TYPE_SERVICELIVE_MAIN, ledgerVO.getBusinessTransactionId(), CommonConstants.NO_FUNDING_TYPE_ACTION);

			for (TransactionVO transaction : business.getTransactions()) {

				logger.debug("listOTransactions " + transaction.getBusinessTransId());
				int entryTypeId = 0;
				transaction.setEntryRemark(ledgerVO.getTransactionNote()); // set the note admin enters
				transaction.setTransferReasonCode(ledgerVO.getTransferReasonCode());

				transaction.setReferenceNo("Admin transaction -- " + ledgerVO.getBusinessTransactionId());
				transaction.setUser(ledgerVO.getUserName());

				if (ledgerVO.getBusinessTransactionId() == CommonConstants.BUSINESS_TRANSACTION_SLA_DEPOSITS_TO_OPERATIONS) entryTypeId = CommonConstants.ENTRY_TYPE_CREDIT;
				else if (ledgerVO.getBusinessTransactionId() == CommonConstants.BUSINESS_TRANSACTION_SLA_WITHDRAWS_FROM_OPERATIONS) entryTypeId = CommonConstants.ENTRY_TYPE_DEBIT;

				transactionEntryId = transactionBuilder.populateTransactionEntry(transaction, ledgerVO.getAccountId(), ledgerVO.getBuyerId(), ledgerVO.getProviderId(), false, entryTypeId);
				
				//copy this transactionEntryId for returning to the wallet
				business.setTransactionEntryId(transactionEntryId);
			}
			// populate amount using ledger_transaction_rule_funding table
			transactionBuilder.populateTransactionAmount(business, orderPricingVO);

			// create Ledger Entries and Ledger Transaction Entries
			transactionDao.loadTransactionEntries(business);

			return business;

		} catch (Exception e) {
			logger.error("adminTransaction method", e);
			throw new SLBusinessServiceException(e);
		}

	}

	public LedgerBusinessTransactionVO depositBuyerFundsCash(LedgerVO ledgerVO, OrderPricingVO orderPricingVO) throws SLBusinessServiceException {

		return depositBuyerFunds(ledgerVO, orderPricingVO, false);
	}

	public LedgerBusinessTransactionVO depositBuyerFundsCreditCard(LedgerVO ledgerVO, OrderPricingVO orderPricingVO) throws SLBusinessServiceException {

		return depositBuyerFunds(ledgerVO, orderPricingVO, true);
	}

	private LedgerBusinessTransactionVO depositBuyerFunds(LedgerVO ledgerVO, OrderPricingVO orderPricingVO, boolean ccInd) throws SLBusinessServiceException {

		try {
			LedgerBusinessTransactionVO business =
				transactionBuilder.createTransactions(CommonConstants.LEDGER_ENTITY_TYPE_BUYER, ledgerVO.getBusinessTransactionId(), ledgerVO.getFundingTypeId());

			for (TransactionVO transaction : business.getTransactions()) {
				transaction.setReferenceNo("Deposit");
				transaction.setSoId(ledgerVO.getServiceOrderId());
				transaction.setUser(ledgerVO.getUserName());
				transaction.setEntryRemark(ledgerVO.getTransactionNote());

				//This is happening in Transaction builder since we had few more that does not reconcile automatically
//				if (ledgerVO.getBusinessTransactionId() == CommonConstants.BUSINESS_TRANSACTION_DEPOSITS_FUNDS_BUYER) {
//					transaction.setReconsidedDate(null);
//					transaction.setReconsiledInd(false);
//				}
				if (ccInd) {
					// Credit Card Authorization Details
					transaction.setAuthorizedInd(true);
					transaction.setAuthorizedDate(new Date(System.currentTimeMillis()));
					transaction.setAuthorizationNo(ledgerVO.getCreditCardAuthorizationNumber());
				}

				transactionBuilder.populateTransactionEntry(transaction, ledgerVO.getAccountId(), ledgerVO.getBuyerId(), ledgerVO.getProviderId(), ccInd,
					CommonConstants.ENTRY_TYPE_CREDIT);

				//copy this transactionEntryId for returning to the wallet
				//business.setTransactionEntryId(transactionEntryId);
			}
			// populate amount using ledger_transaction_rule_funding table
			transactionBuilder.populateTransactionAmount(business, orderPricingVO);

			// create Ledger Entries and Ledger Transaction Entries
			transactionDao.loadTransactionEntries(business);

			return business;

		} catch (Exception e) {
			logger.error("depositBuyerFunds method", e);
			throw new SLBusinessServiceException("Error happened while performing transaction", e);
		}
	}


	public List<TransactionHistoryVO> getAccountOverviewHistory(TransactionHistoryVO thVO) throws SLBusinessServiceException {

		try {
			List<TransactionHistoryVO> transHistory = null;

			// If an sladmin/buyerAdmin we need to give the capability to display the card / bank
			// details used for each transaction
			if (thVO.isSlAdminInd() || thVO.isBuyerAdminInd()) {
				transHistory = transactionDao.getTransactionHistoryDetail(thVO);

				try {
					for (TransactionHistoryVO acctHistoryVO : transHistory) {
						if (acctHistoryVO != null && acctHistoryVO.getAccountNumber() != null) {
							//Commenting code for SL-18789
							//acctHistoryVO.setAccountNumber(ServiceLiveStringUtils.maskString(cryptography.decryptKey(acctHistoryVO.getAccountNumber()), 4, "X"));
							acctHistoryVO.setAccountNumber(ServiceLiveStringUtils.maskString(cryptography128.decryptKey128Bit(acctHistoryVO.getAccountNumber()), 4, "X"));
						}
					}
				} catch (Exception e) {
					logger.error("Exception thrown while fetching account details", e);
				}
			} else {
				transHistory = transactionDao.getTransactionHistory(thVO);
			}

			return transHistory;
		} catch (Exception e) {
			logger.error("getAccountOverviewHistory method", e);
			throw new SLBusinessServiceException(e);
		}
	}


    public double getAvailableBalance(long accountOwnerId, long ledgerEntityTypeId, boolean lockForUpdate) throws SLBusinessServiceException {

		double availableBalance = 0.0;
		try {
			availableBalance = transactionDao.getAvailableBalanceByEntityId(accountOwnerId, ledgerEntityTypeId, lockForUpdate);
			if(0==availableBalance){
				availableBalance = transactionDao.getAvailableBalanceByEntityIdFromLedger
						(accountOwnerId,ledgerEntityTypeId,lockForUpdate);
		}
		}
		catch (DataAccessException dae) {
			logger.error("getAvailableBalance-->DataAccessException-->", dae);
			throw new SLBusinessServiceException("getAvailableBalance-->DataAccessException-->", dae);
		} catch (Exception e) {
			logger.error("getAvailableBalance-->Exception-->", e);

			throw new SLBusinessServiceException("getAvailableBalance-->EXCEPTION-->", e);

		}

		logger.debug("getAvailableBalance for id" + accountOwnerId + "-" + availableBalance);
		return availableBalance;
	}

	public double getCurrentBalance(long accountOwnerId, long ledgerEntityTypeId) throws SLBusinessServiceException {

		Double currentBalance = new Double(0.0);
		try {
			currentBalance = transactionDao.getTotalBalanceByEntityId(accountOwnerId, ledgerEntityTypeId);

			currentBalance = MoneyUtil.getRoundedMoney(currentBalance);

			if (ledgerEntityTypeId == CommonConstants.LEDGER_ENTITY_TYPE_BUYER) {
				double pendingBalanceAmt = 0.0;
				List<TransactionEntryVO> transactionEntries = transactionDao.getTransactionEntriesForPendingRequests(accountOwnerId);

				if (transactionEntries != null) {
					int size = transactionEntries.size();
					for (int i = 0; i < size; i++) {
						TransactionEntryVO transactionEntryVO = transactionEntries.get(i);

						if (transactionEntryVO.getEntryTypeId() == CommonConstants.ENTRY_TYPE_CREDIT) {
							pendingBalanceAmt = pendingBalanceAmt + transactionEntryVO.getTransactionAmount();
							logger.debug("Adding " + transactionEntryVO.getTransactionAmount());

						} else {
							pendingBalanceAmt = pendingBalanceAmt - transactionEntryVO.getTransactionAmount();
							logger.debug("Substracting " + transactionEntryVO.getTransactionAmount());
						}
					}
				}

				currentBalance = currentBalance + pendingBalanceAmt;
			}
			currentBalance = MoneyUtil.getRoundedMoney(currentBalance);
		} catch (DataAccessException dae) {
			logger.error("getCurrentBalance-->DataAccessException-->", dae);
			throw new SLBusinessServiceException("getCurrentBalance-->DataAccessException-->", dae);
		} catch (Exception e) {

			logger.error("getCurrentBalance-->Exception-->", e);
			throw new SLBusinessServiceException("getCurrentBalance-->EXCEPTION-->", e);
		}
		return currentBalance;

	}

	public double getPostSOLedgerAmount(String serviceOrderId) throws SLBusinessServiceException {
		try {
			return transactionDao.getPostSOLedgerAmount(serviceOrderId);
		} catch (Exception dse) {
			logger.error("getPostSOLedgerAmount method", dse);
			throw new SLBusinessServiceException("Error happened while getting Post SO amount", dse);
		}
	}

	public double getProjectBalance(long accountOwnerId, long ledgerEntityTypeId) throws SLBusinessServiceException {

		double projectBalance = 0.0;
		try {
			projectBalance = transactionDao.getProjectBalanceByEntityId(accountOwnerId, ledgerEntityTypeId);
		}
		catch (DataAccessException dae) {
			logger.error("getProjectBalance-->DataAccessException-->", dae);
			throw new SLBusinessServiceException("getProjectBalance-->DataAccessException-->", dae);
		} catch (Exception e) {
			logger.error("getProjectBalance-->Exception-->", e);
			throw new SLBusinessServiceException("getAvailableBalance-->EXCEPTION-->", e);
		}

		logger.debug("getProjectBalance for id" + accountOwnerId + "-" + projectBalance);
		return projectBalance;

	}

	public double getSLOperationBalance() throws SLBusinessServiceException {

		double availableBalance = 0.0;
		try {

			List<TransactionEntryVO> transactionEntries = transactionDao.getSLOperationTransactionEntries();

			if (transactionEntries != null) {
				int size = transactionEntries.size();
				for (int i = 0; i < size; i++) {
					TransactionEntryVO transactionEntryVO = transactionEntries.get(i);

					if (transactionEntryVO.getEntryTypeId() == CommonConstants.ENTRY_TYPE_CREDIT) {
						availableBalance = availableBalance + transactionEntryVO.getTransactionAmount();
						logger.debug("Adding " + transactionEntryVO.getTransactionAmount());

					} else {
						availableBalance = availableBalance - transactionEntryVO.getTransactionAmount();
						logger.debug("Substracting " + transactionEntryVO.getTransactionAmount());
					}
				}
			}
			return MoneyUtil.getRoundedMoney(availableBalance);
		} catch (DataAccessException dae) {
			logger.error("getSLOperationBalance-->DataAccessException-->", dae);
			throw new SLBusinessServiceException("getAvailableBalance-->DataAccessException-->", dae);
		} catch (Exception e) {
			logger.error("getSLOperationBalance-->Exception-->", e);
			throw new SLBusinessServiceException("getAvailableBalance-->EXCEPTION-->", e);
		}
	}

	public double getTotalDepositByEntityId(long accountOwnerId, long ledgerEntityTypeId) throws SLBusinessServiceException {

		try {
			return transactionDao.getTotalDepositByEntityId(accountOwnerId, ledgerEntityTypeId);
		} catch (Exception e) {
			logger.error("getTotalDepositByEntityId method", e);
			throw new SLBusinessServiceException(e);
		}
	}
	
	public HashMap<String,Double> getPastWithdrawalsByEntityId( long entityId ) throws SLBusinessServiceException {

		try {
			HashMap<String,Double> rhash = new HashMap<String, Double>();
			
			List<SLAccountVO> accounts = accountDao.getAccountDetailsAll((int)entityId);
			
			double amt = 0.0;
			double count = 0.0;
			
			for (SLAccountVO account : accounts) {
				HashMap<String, Double> hash = this.getSumOfPastWithdrawals(account.getAccountId());
				amt = amt + hash.get(CommonConstants.HASHMAP_AMOUNT); 
				count = count + hash.get(CommonConstants.HASHMAP_COUNT);
			}
			
			rhash.put(CommonConstants.HASHMAP_AMOUNT, amt);
			rhash.put(CommonConstants.HASHMAP_COUNT, count);
			
			return rhash;
		} catch (Exception e) {
			logger.error("getPastWithdrawalsByEntityId method", e);
			throw new SLBusinessServiceException(e);
		}
	}


	private HashMap<String, Double> getSumOfPastWithdrawals(Long accountId) throws DataServiceException {

		HashMap<String,Double> hash = new HashMap<String, Double>();
		
		List<TransactionVO> pastWithdrawals = this.transactionDao.getPastWithdrawals(accountId);

		double count = 0.0;
		double amt = 0.0;
		for( TransactionVO trans : pastWithdrawals ) {
			count++;	
			amt = amt + trans.getTransactionAmount();
		}
		
		hash.put(CommonConstants.HASHMAP_AMOUNT,amt);
		hash.put(CommonConstants.HASHMAP_COUNT,count);
		return hash;
	}
	 
	
	private void initializeTransactionToAutoCreateBehaviorMap() {

		final long[] adminTransactions =
			new long[] { CommonConstants.BUSINESS_TRANSACTION_SLA_CREDITS_SLB_TO_BUYER, CommonConstants.BUSINESS_TRANSACTION_SLA_DEBITS_SLB_FROM_BUYER,
				CommonConstants.BUSINESS_TRANSACTION_SLA_CREDITS_SLB_TO_PROVIDER, CommonConstants.BUSINESS_TRANSACTION_SLA_DEBITS_SLB_FROM_PROVIDER,
				CommonConstants.BUSINESS_TRANSACTION_SLA_DEPOSITS_TO_OPERATIONS, CommonConstants.BUSINESS_TRANSACTION_SLA_WITHDRAWS_FROM_OPERATIONS,
				CommonConstants.BUSINESS_TRANSACTION_MARKETPLACE_WITHDRAW_FUNDS,CommonConstants.BUSINESS_TRANSACTION_SLA_ESCHEATMENT_SLB_FROM_BUYER,
				CommonConstants.BUSINESS_TRANSACTION_SLA_ESCHEATMENT_SLB_FROM_PROVIDER,CommonConstants.BUSINESS_TRANSACTION_ESCHEATMENT_BUYER_DEBIT_REVERSAL }; 

		final long[] depositBuyerCashTransactions =
			new long[] { CommonConstants.BUSINESS_TRANSACTION_DEPOSITS_FUNDS_BUYER, CommonConstants.BUSINESS_TRANSACTION_BUYER_INSTANT_ACH_DEPOSIT};

		final long[] depositBuyerCreditCardTransactions =
			new long[] { CommonConstants.BUSINESS_TRANSACTION_BUYER_DEPOSITS_FROM_AMEX, CommonConstants.BUSINESS_TRANSACTION_BUYER_DEPOSITS_FROM_SEARS,
				CommonConstants.BUSINESS_TRANSACTION_BUYER_DEPOSITS_FROM_V_MX };

		final long[] withdrawBuyerCashTransactions = new long[] { CommonConstants.BUSINESS_TRANSACTION_WITHDRAW_FUNDS_BUYER,
				CommonConstants.BUSINESS_TRANSACTION_BUYER_INSTANT_ACH_REFUND };

		final long[] withdrawBuyerCreditCardTransactions = new long[] { CommonConstants.BUSINESS_TRANSACTION_SLA_REFUNDS_TO_BUYERS_V_MC, CommonConstants.BUSINESS_TRANSACTION_SLA_REFUNDS_TO_BUYERS_AMEX,
		CommonConstants.BUSINESS_TRANSACTION_WITHDRAW_FUNDS_TO_BUYERS_V_MC, CommonConstants.BUSINESS_TRANSACTION_WITHDRAW_FUNDS_TO_BUYERS_AMEX};

		final long[] withdrawProviderTransactions = new long[] { CommonConstants.BUSINESS_TRANSACTION_WITHDRAW_FUNDS_PROVIDER };

		final long[] withdrawProviderReversalTransactions = new long[] { CommonConstants.BUSINESS_TRANSACTION_WITHDRAW_FUNDS_PROVIDER_REVERSAL };

		final long[] serviceOrderTransactions =
			new long[] { CommonConstants.BUSINESS_TRANSACTION_CANCEL_SO_WO_PENALTY, CommonConstants.BUSINESS_TRANSACTION_VOID_SO,
				CommonConstants.BUSINESS_TRANSACTION_DECREASE_SO_ESCROW, CommonConstants.BUSINESS_TRANSACTION_INCREASE_SO_ESCROW, CommonConstants.BUSINESS_TRANSACTION_POST_SO,
				CommonConstants.BUSINESS_TRANSACTION_CANCEL_SO, CommonConstants.BUSINESS_TRANSACTION_RELEASE_SO_PAYMENT, CommonConstants.BUSINESS_TRANSACTION_COMPLETION };


		for (long id : adminTransactions) {
			mapTransactionToTransactionType.put(id, TransactionType.Admin);
		}
		for (long id : depositBuyerCashTransactions) {
			mapTransactionToTransactionType.put(id, TransactionType.DepositBuyerCash);
		}
		for (long id : depositBuyerCreditCardTransactions) {
			mapTransactionToTransactionType.put(id, TransactionType.DepositBuyerCreditCard);
		}
		for (long id : withdrawBuyerCashTransactions) {
			mapTransactionToTransactionType.put(id, TransactionType.WithdrawBuyerCash);
		}
		for (long id : withdrawBuyerCreditCardTransactions) {
			mapTransactionToTransactionType.put(id, TransactionType.WithdrawBuyerCreditCard);
		}
		for (long id : withdrawProviderTransactions) {
			mapTransactionToTransactionType.put(id, TransactionType.WithdrawProvider);
		}
		for (long id : withdrawProviderReversalTransactions) {
			mapTransactionToTransactionType.put(id, TransactionType.WithdrawProviderReversal);
		}
		for (long id : serviceOrderTransactions) {
			mapTransactionToTransactionType.put(id, TransactionType.ServiceOrder);
		}
	}

	public LedgerBusinessTransactionVO postLedgerTransaction(LedgerVO ledgerVO, OrderPricingVO orderPricingVO) throws SLBusinessServiceException {

		LedgerBusinessTransactionVO response = null;
		TransactionType transactionType = mapTransactionToTransactionType.get(ledgerVO.getBusinessTransactionId());
		switch (transactionType) {
		case Admin:
			response = adminTransaction(ledgerVO, orderPricingVO);
			break;
		case DepositBuyerCash:
			response = depositBuyerFundsCash(ledgerVO, orderPricingVO);
			break;
		case DepositBuyerCreditCard:
			response = depositBuyerFundsCreditCard(ledgerVO, orderPricingVO);
			break;
		case WithdrawBuyerCash:
			response = withdrawBuyerFunds(ledgerVO, orderPricingVO);
			break;
		case WithdrawBuyerCreditCard:
			response = withdrawBuyerFundsCreditCard(ledgerVO, orderPricingVO);
			break;
		case WithdrawProvider:
			response = withdrawProviderFunds(ledgerVO, orderPricingVO);
			break;
		case WithdrawProviderReversal:
			response = withdrawProviderFundsReversal(ledgerVO, orderPricingVO);
			break;
		case ServiceOrder:
			response = serviceOrderTransaction(ledgerVO, orderPricingVO);
			break;
		}
		return response;
	}

	public LedgerBusinessTransactionVO serviceOrderTransaction(LedgerVO ledgerVO, OrderPricingVO orderPricingVO)
	throws SLBusinessServiceException {
		LedgerBusinessTransactionVO business = null;
		try {
			int intBusinessTxnId = ledgerVO.getBusinessTransactionId().intValue();
			logger.debug("inside ledgerBO.serviceOrderTransaction, intBusinessTxnId="+intBusinessTxnId);
			if (transactionDao.countVoidCancelCloseTransactions(intBusinessTxnId, ledgerVO.getServiceOrderId()) == 0) {
				
				logger.debug("inside ledgerBO.serviceOrderTransaction, orderPricingVO.isHSR()="+orderPricingVO.isHSR());
				if(155 == intBusinessTxnId && !orderPricingVO.isHSR()){
					
					
					
					logger.debug("inside ledgerBO.serviceOrderTransaction, createTransactionsForNonHSR");
					business = transactionBuilder.createTransactionsForNonHSR(CommonConstants.LEDGER_ENTITY_TYPE_BUYER,
							ledgerVO.getBusinessTransactionId(), ledgerVO.getFundingTypeId());
				
					
					
					
				}
				else if(155 == intBusinessTxnId && orderPricingVO.isHSR()){
					
					
					if(!orderPricingVO.isHasParts())
					{
						logger.debug("inside ledgerBO.serviceOrderTransaction, createTransactionsForNonHSR");
						business = transactionBuilder.createTransactionsForHSRwithOutParts(CommonConstants.LEDGER_ENTITY_TYPE_BUYER,
								ledgerVO.getBusinessTransactionId(), ledgerVO.getFundingTypeId());	
					}
					
					if(!orderPricingVO.isHasAddon())
					{
						logger.debug("inside ledgerBO.serviceOrderTransaction, createTransactionsForNonHSR");
						business = transactionBuilder.createTransactionsForHSRwithOutAddOn(CommonConstants.LEDGER_ENTITY_TYPE_BUYER,
								ledgerVO.getBusinessTransactionId(), ledgerVO.getFundingTypeId());	
					}
					
					if(orderPricingVO.isHasParts() && orderPricingVO.isHasAddon())
					{
					logger.debug("inside ledgerBO.serviceOrderTransaction, createTransactionsForNonHSR");
					business = transactionBuilder.createTransactions(CommonConstants.LEDGER_ENTITY_TYPE_BUYER,
							ledgerVO.getBusinessTransactionId(), ledgerVO.getFundingTypeId());
					
					}
					
					
					
				}
				else{
					logger.debug("inside ledgerBO.serviceOrderTransaction, createTransactions");
					//fetch production date from application properties.
					boolean isNewOrder = false;
					String prodDateVal = applicationProperties.getPropertyFromDB(CommonConstants.PROD_DATE);
					logger.info("production date string value::"+prodDateVal);
					Date prodDate = Date.valueOf(prodDateVal);
					logger.info("production date::"+prodDate);
					if((null != orderPricingVO.getCompletedDate()) && (prodDate.compareTo(orderPricingVO.getCompletedDate()) <= 0)){
						isNewOrder = true;
					}

					//while so closure do not execute rules 2137,2138 and 2140 for non-hsr buyer.'hasparts' indicator will be false for all non-hsr buyers.
					//for hsr buyers 'hasparts' will be true only if the invoice part has some price greater than 0.
					logger.info("orderPricingVO.isHasParts():"+orderPricingVO.isHasParts());
					logger.info("orderPricingVO.isPriceNotValid()::"+orderPricingVO.isPriceNotValid());
					if (120 == intBusinessTxnId && ( (!isNewOrder) || (!orderPricingVO.isHasParts()))) {
						logger.info("inside first");
						business = transactionBuilder.createTransactionsForInvoicePartsEmpty(CommonConstants.LEDGER_ENTITY_TYPE_BUYER,
								ledgerVO.getBusinessTransactionId(), ledgerVO.getFundingTypeId(),orderPricingVO.isPriceNotValid());
					}else if(orderPricingVO.isPriceNotValid() && 120 == intBusinessTxnId){
						logger.info("inside second");
						business = transactionBuilder.createTransactionsForClosure(CommonConstants.LEDGER_ENTITY_TYPE_BUYER,
								ledgerVO.getBusinessTransactionId(), ledgerVO.getFundingTypeId());
					}else{
						logger.info("inside third");
					business = transactionBuilder.createTransactions(CommonConstants.LEDGER_ENTITY_TYPE_BUYER,
							ledgerVO.getBusinessTransactionId(), ledgerVO.getFundingTypeId());
					}

				}
	
				for (TransactionVO transaction : business.getTransactions()) {
					logger.info("ledger rule ids::"+transaction.getLedgerEntryRuleId());
					if (ledgerVO.getServiceOrderId() != null) {
						transaction.setReferenceNo(ledgerVO.getServiceOrderId());
						transaction.setSoId(ledgerVO.getServiceOrderId());
					}
					transaction.setUser(ledgerVO.getUserName());
					transaction.setEntryRemark(ledgerVO.getTransactionNote());
					Long accountId = null;
					if (intBusinessTxnId != CommonConstants.BUSINESS_TRANSACTION_DECREASE_SO_ESCROW && transaction.getAutoAchInd() != 1) {
						accountId = ledgerVO.getAccountId();
					}
	
					long transactionEntryId = transactionBuilder.populateTransactionEntry(transaction, accountId, ledgerVO.getBuyerId(), ledgerVO.getProviderId(), false, 0);
					business.setTransactionEntryId(transactionEntryId);
				}
				// populate amount using ledger_transaction_rule_funding table
				if(ledgerVO.getBuyerId() == 3000){
					transactionBuilder.populateTransactionAmountAdjustPennyVariance(business, orderPricingVO, ledgerVO.getServiceOrderId());
				}else{
					transactionBuilder.populateTransactionAmount(business, orderPricingVO);
				}
	
				// create Ledger Entries and Ledger Transaction Entries
				logger.info("service order transactionDao ID: "+transactionDao);
				transactionDao.loadTransactionEntries(business);
				
				// JIRA SL-12485, this is for logging purpose to find the root cause
				if(business.getBusinessTransId().toString().equals( CommonConstants.BUSINESS_TRANSACTION_COMPLETION)){
					if(orderPricingVO.getAmount()==0.0){
						logger.info("for the service order, bus_trans_id=155 and "+ "so_id= "+ ledgerVO.getServiceOrderId()+ "the addon amount is zero, done by user"
								+ledgerVO.getUserName()+" provider ID "+ledgerVO.getProviderId());
					}
				}
	
			}else{
				business = new LedgerBusinessTransactionVO();
				business.setLedgerEntries(false);
			}
		} catch (Exception e) {
			logger.error("serviceOrderTransaction method", e);
			throw new SLBusinessServiceException("Error happened while performing transaction", e);
		}
		return business;
	}

	public void setCryptography(Cryptography cryptography) {

		this.cryptography = cryptography;
	}

	public void setTransactionBuilder(ITransactionBuilder transactionBuilder) {

		this.transactionBuilder = transactionBuilder;
	}

	public void setTransactionDao(ITransactionDao transactionDao) {

		this.transactionDao = transactionDao;
	}

	public LedgerBusinessTransactionVO withdrawBuyerFunds(LedgerVO ledgerVO, OrderPricingVO orderPricingVO) throws SLBusinessServiceException {

		return withdrawBuyerFunds(ledgerVO, orderPricingVO, false);
	}

	private LedgerBusinessTransactionVO withdrawBuyerFunds(LedgerVO ledgerVO, OrderPricingVO orderPricingVO, boolean ccInd) throws SLBusinessServiceException {

		try {
			long transactionEntryId = 0;
			LedgerBusinessTransactionVO business = transactionBuilder.createTransactions(CommonConstants.LEDGER_ENTITY_TYPE_BUYER, ledgerVO.getBusinessTransactionId(), null);
			for (TransactionVO transaction : business.getTransactions()) {
				transaction.setReferenceNo("withdrawal");
				transaction.setUser(ledgerVO.getUserName());
				transaction.setEntryRemark(ledgerVO.getTransactionNote());

				if (ccInd) { // if CC, as we don't do auth we need to set the auth code
					transaction.setAuthorizedInd(true);
					transaction.setAuthorizedDate(new Date(System.currentTimeMillis()));
					transaction.setAuthorizationNo(ledgerVO.getCreditCardAuthorizationNumber());
					// transaction.setAuthorizationNo("000000");
				}
				transactionEntryId = transactionBuilder.populateTransactionEntry(transaction, ledgerVO.getAccountId(), ledgerVO.getBuyerId(), ledgerVO.getProviderId(), ccInd,
					CommonConstants.ENTRY_TYPE_DEBIT);

				//copy this transactionEntryId for returning to the wallet
				business.setTransactionEntryId(transactionEntryId);
			}

			// populate amount using ledger_transaction_rule_funding table
			transactionBuilder.populateTransactionAmount(business, orderPricingVO);

			// create Ledger Entries and Ledger Transaction Entries
			transactionDao.loadTransactionEntries(business);

			return business;

		} catch (Exception e) {
			logger.error("withdrawBuyerFunds method", e);
			throw new SLBusinessServiceException(e.getMessage(), e);
		}
	}

	public LedgerBusinessTransactionVO withdrawBuyerFundsCreditCard(LedgerVO ledgerVO, OrderPricingVO orderPricingVO) throws SLBusinessServiceException {

		return withdrawBuyerFunds(ledgerVO, orderPricingVO, true);
	}

	public LedgerBusinessTransactionVO withdrawProviderFunds(LedgerVO ledgerVO, OrderPricingVO orderPricingVO) throws SLBusinessServiceException {

		try {
			long transactionEntryId = 0;
			LedgerBusinessTransactionVO business =
				transactionBuilder.createTransactions(CommonConstants.LEDGER_ENTITY_TYPE_PROVIDER, CommonConstants.BUSINESS_TRANSACTION_WITHDRAW_FUNDS_PROVIDER,
					CommonConstants.NO_FUNDING_TYPE_ACTION);
			for (TransactionVO transaction : business.getTransactions()) {
				transaction.setReferenceNo("withdrawal");
				transaction.setUser(ledgerVO.getUserName());
				transaction.setNonce(ledgerVO.getNonce());

				transactionEntryId = transactionBuilder.populateTransactionEntry(transaction, ledgerVO.getAccountId(), ledgerVO.getBuyerId(), ledgerVO.getProviderId(), false,
					CommonConstants.ENTRY_TYPE_DEBIT);
				
				//copy this transactionEntryId for returning to the wallet
				business.setTransactionEntryId(transactionEntryId);
			}
			// populate amount using ledger_transaction_rule_funding table
			transactionBuilder.populateTransactionAmount(business, orderPricingVO);

			// create Ledger Entries and Ledger Transaction Entries
			logger.info("withdrawProviderFunds transactionDao ID: "+transactionDao);
			transactionDao.loadTransactionEntries(business);

			return business;

		} catch (Exception e) {
			logger.error("withdrawProviderFunds method", e);
			throw new SLBusinessServiceException(e.getMessage(), e);
		}
	}

	public LedgerBusinessTransactionVO withdrawProviderFundsReversal(LedgerVO ledgerVO, OrderPricingVO orderPricingVO) throws SLBusinessServiceException {

		try {
			LedgerBusinessTransactionVO business =
				transactionBuilder.createTransactions(CommonConstants.LEDGER_ENTITY_TYPE_SERVICELIVE_MAIN, CommonConstants.BUSINESS_TRANSACTION_WITHDRAW_FUNDS_PROVIDER_REVERSAL,
					CommonConstants.NO_FUNDING_TYPE_ACTION);
			for (TransactionVO transaction : business.getTransactions()) {

				transaction.setReferenceNo("");// ledgerId);

				transaction.setUser(ledgerVO.getUserName());

				transactionBuilder.populateTransactionEntry(transaction, ledgerVO.getAccountId(), ledgerVO.getBuyerId(), ledgerVO.getProviderId(), false,
					CommonConstants.ENTRY_TYPE_DEBIT);

			}
			// populate amount using ledger_transaction_rule_funding table
			transactionBuilder.populateTransactionAmount(business, orderPricingVO);

			// create Ledger Entries and Ledger Transaction Entries
			transactionDao.loadTransactionEntries(business);// adding reversal entries in ledger_transaction_entry table

			return business;

		} catch (Exception e) {
			logger.error("withdrawProviderFundsReversal method", e);
			throw new SLBusinessServiceException(e);
		}
	}
	
	public Double getValueLinkBalanceByEntityId(long entityId) {
		return this.transactionDao.getValueLinkBalanceByEntityId(entityId);
	}
	
	public Long getPastWithdrawalsWithSameAmt(Long accountId,Integer timeIntervalSec,Double transactionAmount ) {
		return this.transactionDao.getPastWithdrawalsWithSameAmt(accountId,timeIntervalSec,transactionAmount);
	}
	
	public void setAccountDao(IAccountDao accountDao) {
		this.accountDao = accountDao;
	}

	public ReceiptVO getTransactionReceipt(Long entityId, Integer entityTypeId,
			LedgerEntryType entryType, String serviceOrderId) throws SLBusinessServiceException {
		try{
			logger.info("getTransactionReceipt in ledger bo");
			ReceiptVO receiptInput = new ReceiptVO();
			receiptInput.setEntityTypeID(entityTypeId);
			receiptInput.setLedgerEntityID(entityId);
			receiptInput.setSoID(serviceOrderId);
			receiptInput.setLedgerEntryRuleID(entryType.getId());
			ReceiptVO receiptVO = transactionDao.getTransactionReceipt(receiptInput);
			logger.info("receiptVO in ledgerbo:"+receiptVO);
			return receiptVO;
		} catch(DataServiceException e){
			logger.error("getTransactionReceipt errored out", e);
			throw new SLBusinessServiceException(e);
		}
	}
	
		public void insertWithdrawalErrorLogging(ProviderWithdrawalErrorVO providerWithdrawalErrorVO) throws SLBusinessServiceException 
	{
		try
		{
			transactionDao.insertWithdrawalErrorLogging(providerWithdrawalErrorVO);
		}catch (Exception e) {
			logger.error("insertWithdrawalErrorLogging method", e);
			throw new SLBusinessServiceException(e);
		}
	}
		
		public Double getTransactionAmountById(Long transactionId) throws DataServiceException {
			return transactionDao.getTransactionAmountById(transactionId);
		}
	
	public String getLedgerEntryNonce(long busTransId) throws SLBusinessServiceException {
		try {
			return transactionDao.getLedgerEntryNonce(busTransId);
		} catch (Exception dse) {
			logger.error("getLedgerEntryNonce method", dse);
			throw new SLBusinessServiceException("Error happened while getting Ledger Entry Nonce", dse);
		}
	}
	
	public double getAvailableBalanceFromLedger(long accountOwnerId, long ledgerEntityTypeId, boolean lockForUpdate) throws SLBusinessServiceException {

		double availableBalance = 0.0;
		try {
			availableBalance = transactionDao.getAvailableBalanceByEntityIdFromLedger
			(accountOwnerId,ledgerEntityTypeId,lockForUpdate);			
		}
		catch (DataAccessException dae) {
			logger.error("getAvailableBalanceFromLedger-->DataAccessException-->", dae);
			throw new SLBusinessServiceException("getAvailableBalanceFromLedger -->DataAccessException-->", dae);
		} catch (Exception e) {
			logger.error("getAvailableBalanceFromLedger -->Exception-->", e);

			throw new SLBusinessServiceException("getAvailableBalanceFromLedger -->EXCEPTION-->", e);

		}

		logger.debug("getAvailableBalanceFromLedger for id" + accountOwnerId + "-" + availableBalance);
		return availableBalance;
	}
	
	
	//SL-21117: Revenue Pull Code change Starts

	public List <String> getPermittedUsers(long ind) throws SLBusinessServiceException{

		List<String> list = new ArrayList<String>();
		try {
			list = transactionDao.getPermittedUsers(ind);
		} catch (DataServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public double getAvailableBalanceForRevenuePull(long accountOwnerId, long ledgerEntityTypeId) throws SLBusinessServiceException {

		double availableBalance = 0.0;
		try {
			availableBalance = transactionDao.getAvailableBalanceForRevenuePull
					(accountOwnerId,ledgerEntityTypeId);			
		}
		catch (DataAccessException dae) {
			logger.error("getAvailableBalanceForRevenuePull-->DataAccessException-->", dae);
			throw new SLBusinessServiceException("getAvailableBalanceForRevenuePull -->DataAccessException-->", dae);
		} catch (Exception e) {
			logger.error("getAvailableBalanceForRevenuePull -->Exception-->", e);

			throw new SLBusinessServiceException("getAvailableBalanceForRevenuePull -->EXCEPTION-->", e);

		}

		logger.debug("getAvailableBalanceForRevenuePull for id" + accountOwnerId + "-" + availableBalance);
		return availableBalance;
	}


	public boolean getAvailableDateCheckForRevenuePull(java.util.Date calendarOnDate) throws SLBusinessServiceException {

		boolean dbRevenuePullDateCheck=false;
		try {
			dbRevenuePullDateCheck = transactionDao.getAvailableDateCheckForRevenuePull(calendarOnDate);			
		}
		catch (DataAccessException dae) {
			logger.error("getAvailableDateCheckForRevenuePull-->DataAccessException-->", dae);
			throw new SLBusinessServiceException("getAvailableDateCheckForRevenuePull -->DataAccessException-->", dae);
		} catch (Exception e) {
			logger.error("getAvailableDateCheckForRevenuePull -->Exception-->", e);

			throw new SLBusinessServiceException("getAvailableDateCheckForRevenuePull -->EXCEPTION-->", e);

		}

		return dbRevenuePullDateCheck;
	}

	public void insertEntryForRevenuePull(double amount,java.util.Date revenuePullDate,String note,String user,long ind,java.util.Date todayDate,String status) throws SLBusinessServiceException {

		try {
			transactionDao.insertEntryForRevenuePull(amount,revenuePullDate,note,user,ind,todayDate,status);
		} catch (DataServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		


	}

	public List <String> getPermittedUsersEmail(long ind) throws SLBusinessServiceException{

		List<String> list = new ArrayList<String>();
		try {
			list = transactionDao.getPermittedUsersEmail(ind);
		} catch (DataServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	//Code change ends
		

	public Cryptography128 getCryptography128() {
		return cryptography128;
	}

	public void setCryptography128(Cryptography128 cryptography128) {
		this.cryptography128 = cryptography128;
	}

	
}
