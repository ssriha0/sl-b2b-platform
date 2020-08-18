package com.servicelive.wallet.ledger.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.servicelive.common.ABaseDao;
import com.servicelive.common.CommonConstants;
import com.servicelive.common.exception.DataServiceException;
import com.servicelive.wallet.ledger.vo.LedgerBalanceVO;
import com.servicelive.wallet.ledger.vo.LedgerBusinessTransactionVO;
import com.servicelive.wallet.ledger.vo.TransactionEntryVO;
import com.servicelive.wallet.ledger.vo.TransactionHistoryVO;
import com.servicelive.wallet.ledger.vo.TransactionRuleVO;
import com.servicelive.wallet.ledger.vo.TransactionVO;
import com.servicelive.wallet.ledger.vo.WalletBalanceVO;
import com.servicelive.wallet.serviceinterface.vo.ProviderWithdrawalErrorVO;
import com.servicelive.wallet.serviceinterface.vo.ReceiptVO;


public class TransactionDao extends ABaseDao implements ITransactionDao {

	TransactionDao()
	{

	}
	private static final Logger logger = Logger.getLogger(TransactionDao.class);

	private static final Integer[] transTypes = { 100, 150, 110, 111, 115, 120, 160 };

	private static final ArrayList<Integer> alTransTypes = new ArrayList<Integer>(Arrays.asList(transTypes));

	public Integer countVoidCancelCloseTransactions(int busTransId, String soId) {
		int count = 0;
		switch (busTransId) {
		case (int) CommonConstants.BUSINESS_TRANSACTION_CANCEL_SO:
		case (int) CommonConstants.BUSINESS_TRANSACTION_CANCEL_SO_WO_PENALTY:
			count = (Integer) queryForObject("cancellationCount.query", soId);
		break;
		case (int) CommonConstants.BUSINESS_TRANSACTION_VOID_SO:
			count = (Integer) queryForObject("voidCount.query", soId);
		break;
		case (int) CommonConstants.BUSINESS_TRANSACTION_RELEASE_SO_PAYMENT:
			count = (Integer) queryForObject("closeCount.query", soId);
		break;
		}
		return count;
	}

	public Double getAvailableBalanceByEntityId(long ledgerEntityId, long ledgerEntityTypeId, 
			boolean lockForUpdate) throws DataServiceException {
		Double result = new Double(0);
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("ledgerEntityId", ledgerEntityId);
		parameter.put("ledgerEntityTypeId", ledgerEntityTypeId);
		if (lockForUpdate) {
			parameter.put("locked", Boolean.TRUE);
		}
		try {
			result = (Double) getSqlMapClient().queryForObject("ledger_transaction_entry_byEntityId_for_availablebalance.query", parameter);
		} catch (Exception ex) {
			throw new DataServiceException("thrown by getAvailableBalanceByEntityId", ex);
		}
		return (result == null ? 0 : result);
	}

	public Double getAvailableBalanceByEntityIdFromLedger(long ledgerEntityId, long ledgerEntityTypeId, 
			boolean lockForUpdate) throws DataServiceException {
		Double result = new Double(0);
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("ledgerEntityId", ledgerEntityId);
		parameter.put("ledgerEntityTypeId", ledgerEntityTypeId);
		if (lockForUpdate) {
			parameter.put("locked", Boolean.TRUE);
		}
		try {
			result = (Double) getSqlMapClient().queryForObject("ledger_transaction_entry_byEntityId_for_availablebalance.fromLedger",
					parameter);
		} catch (Exception ex) {
			throw new DataServiceException("thrown by getAvailableBalanceByEntityIdFromLedger", ex);
		}
		return (result == null ? 0 : result);
	}


	//SL-21117: Revenue Pull Code change Starts

	public List <String> getPermittedUsers(long ind) throws DataServiceException{

		Map<String, Object> parameter = new HashMap<String, Object>();

		parameter.put("indicator", ind);

		return this.queryForList("revenue_pull_permitted_users.query", parameter);
	}

	public Double getAvailableBalanceForRevenuePull(long ledgerEntityId, long ledgerEntityTypeId) throws DataServiceException {
		Double result = new Double(0);
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("ledgerEntityId", ledgerEntityId);
		parameter.put("ledgerEntityTypeId", ledgerEntityTypeId);

		try {
			result = (Double) getSqlMapClient().queryForObject("available_balance_for_revenue_pull.fromLedger",
					parameter);
		} catch (Exception ex) {
			throw new DataServiceException("thrown by getAvailableBalanceForRevenuePull", ex);
		}
		return (result == null ? 0 : result);
	}


	public boolean getAvailableDateCheckForRevenuePull(Date calendarOnDate) throws DataServiceException {
		boolean dbRevenuePullDateCheck=false;

		int count = 0;

		java.sql.Date today_date = new java.sql.Date(calendarOnDate.getTime());

		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("revenuePullDate", today_date);

		try {
			count = (Integer) queryForObject("available_date_check_for_revenue_pull.fromRevenuePull",parameter);
			if (count > 0){
				dbRevenuePullDateCheck = true;
			}
		} catch (Exception ex) {
			throw new DataServiceException("thrown by getAvailableBalanceForRevenuePull", ex);
		}
		return dbRevenuePullDateCheck;
	}


	public void insertEntryForRevenuePull(double amount,Date revenuePullDate,String note,String user,long ind,Date todayDate,String status) throws DataServiceException {

		Map<String, Object> parameter = new HashMap<String, Object>();

		parameter.put("revenuepullamount", amount);
		parameter.put("revenuepulldate", revenuePullDate);
		parameter.put("revenuepullnote", note);
		parameter.put("revenuepulluser", user);
		parameter.put("revenuepullstatus", status);
		parameter.put("revenuepullindicator", ind);
		parameter.put("revenuepullcreateddate", todayDate);
		parameter.put("revenuepullmodifieddate", todayDate);

		insert("revenue_pull.insert", parameter);

	}

	public List <String> getPermittedUsersEmail(long ind) throws DataServiceException{

		Map<String, Object> parameter = new HashMap<String, Object>();

		parameter.put("indicator", ind);

		return this.queryForList("revenue_pull_permitted_users_email.query", parameter);
	}

	//Code change ends


	public TransactionVO getLedgerEntryByEntryId(long ledgerEntryId,long ledgerEntityTypeId) throws DataServiceException {
		try {
			Map<String, Long> parameter = new HashMap<String, Long>();
			parameter.put("ledgerEntryId", ledgerEntryId);
			parameter.put("ledgerEntityTypeId", ledgerEntityTypeId);
			return (TransactionVO) queryForObject("ledger_entry_glprocessed.query", parameter);
		} catch (Exception ex) {
			throw new DataServiceException("thrown by getLedgerEntryByEntryId", ex);
		}
	}

	public Map<String, String> getLedgerRulePricingExpressionsMap(long busTransId) throws DataServiceException {
		Map<String, String> result = new HashMap<String, String>();
		try {
			result = getSqlMapClient().queryForMap("ledger_rule_pricing_expression.query", busTransId, "ruleId", "expression");
		} catch (Exception ex) {
			throw new DataServiceException("thrown by getLedgerRulePricingExpressionsMap", ex);
		}
		return result;
	}

	public Double getPostSOLedgerAmount(String soId) throws DataServiceException {
		Double result = new Double(0);
		// code change for SLT-2112
		Map<String, String> parameter = new HashMap<String, String>();
		parameter.put("soId", soId);
		try {
			result = (Double) queryForObject("getPostSOLedgerAmount.query", parameter);
		} catch (Exception e) {
			throw new DataServiceException("thrown by getPostSOLedgerAmount", e);
		}
		return (result == null ? 0 : result);
	}

	public double getProjectBalanceByEntityId(long ledgerEntityId, long ledgerEntityTypeId) throws DataServiceException {

		Double result = new Double(0);
		Map<String, Long> parameter = new HashMap<String, Long>();
		parameter.put("ledgerEntityId", ledgerEntityId);
		parameter.put("ledgerEntityTypeId", ledgerEntityTypeId);
		try {
			result = (Double) getSqlMapClient().queryForObject("ledger_transaction_entry_byEntityId_for_availablebalance.query", parameter);
		} catch (Exception ex) {
			throw new DataServiceException("thrown by getProjectBalanceByEntityId", ex);
		}
		return (result == null ? 0 : result);
	}

	public List<TransactionEntryVO> getSLOperationTransactionEntries() {

		return this.queryForList("ledger_transactions_by_sloperation.query", null);
	}

	public Double getTotalBalanceByEntityId(long ledgerEntityId, long ledgerEntityTypeId) throws DataServiceException {

		Double result = new Double(0);
		Map<String, Long> parameter = new HashMap<String, Long>();
		parameter.put("ledgerEntityId", ledgerEntityId);
		parameter.put("ledgerEntityTypeId", ledgerEntityTypeId);
		try {
			result = (Double) getSqlMapClient().queryForObject("ledger_transaction_entry_byEntityId_for_totalbalance.query", parameter);
			if (result == null) {
				result = new Double(0);
			}
		} catch (Exception ex) {
			throw new DataServiceException("thrown by getTotalBalanceByEntityId", ex);
		}
		return result;
	}

	public double getTotalDepositByEntityId(long ledgerEntityId, long ledgerEntityTypeId) throws DataServiceException {
		try {
			Map<String, Long> parameter = new HashMap<String, Long>();
			parameter.put("ledgerEntityId", ledgerEntityId);
			parameter.put("ledgerEntityTypeId", ledgerEntityTypeId);
			Double value = (Double) this.queryForObject("ledger_transaction_deposit_sum.query", parameter);
			if (value == null) {
				return 0.0;
			} else {
				return value.doubleValue();
			}
		} catch (Exception e) {
			throw new DataServiceException("thrown by getTotalDepositByEntityId", e);
		}
	}

	public List<TransactionEntryVO> getTransactionEntriesForPendingRequests(long ledgerEntityId) {

		return this.queryForList("ledger_transaction_entry_byEntityId_for_pendingRequests.query", ledgerEntityId);
	}

	public List<TransactionHistoryVO> getTransactionHistory(TransactionHistoryVO thVO) throws DataServiceException {

		try {
			return (List<TransactionHistoryVO>) queryForList("trans_history.query", thVO);
		} catch (Exception e) {
			throw new DataServiceException("thrown by getTransactionHistory ", e);
		}
	}

	public List<TransactionHistoryVO> getTransactionHistoryDetail(TransactionHistoryVO thVO) throws DataServiceException {

		try {
			return (List<TransactionHistoryVO>) queryForList("trans_history_detail.query", thVO);
		} catch (Exception e) {
			throw new DataServiceException("thrown by getTransactionHistory ", e);
		}
	}

	protected Long insertTransaction(TransactionVO vo) throws DataServiceException {

		return (Long) insert("ledger_entry.insert", vo);
	}

	protected Long insertTransactionEntry(TransactionEntryVO vo) throws DataServiceException {
		return (Long) insert("ledger_transaction_entry.insert", vo);
	}
	
	// SL-20987: Wallet Dirty Read...
	// Withdraw money by provider and SO Transaction(eg. Agree SO Cancel), multiple object were getting created. 
	// so, removed object level locking technique(synchronization at method level).
	// added changes for acquiring class level lock(static locking).
	private static final Object loadTransactionEntriesObjectClassStaticLock = new Object();
	
	public LedgerBusinessTransactionVO loadTransactionEntries(LedgerBusinessTransactionVO busTransVO) throws DataServiceException {
		
		synchronized (loadTransactionEntriesObjectClassStaticLock) {
			
			try {
				Long ledgerEntryId,transactionId=null;
				TransactionEntryVO transactionVO = null;
				ArrayList<TransactionVO> tranVOList = busTransVO.getTransactions();
				for (int i = 0; i < tranVOList.size(); i++) {
					TransactionVO tranVO = tranVOList.get(i);
	
					if (busTransVO.getFundingTypeId() == CommonConstants.FUNDING_TYPE_NON_FUNDED && alTransTypes.contains(busTransVO.getBusinessTransId())) {
						tranVO.setAffectsBalanceInd(0);
	
					}
	
					// insert into ledger_entry
					LedgerBalanceVO existingLedgerBalanceVO = new LedgerBalanceVO();;
					Integer reconsiledInd = 0;
					// insert into ledger_entry , entity ledger balance is not locked at this point
					ledgerEntryId = insertTransaction(tranVO);
					tranVO.setLedgerEntryId(ledgerEntryId.longValue());
					if(tranVO.isReconsiledInd() && tranVO.getAffectsBalanceInd()==1)
						reconsiledInd = 1;
					//insert into ledger_transaction_entry
					ArrayList<TransactionEntryVO> tranEntryVOList = tranVO.getTransactions();
					if (tranEntryVOList != null && !tranEntryVOList.isEmpty()) {
						for (int j = 0; j < tranEntryVOList.size(); j++) {
							transactionVO = tranEntryVOList.get(j);
							transactionVO.setLedgerEntryId(ledgerEntryId.longValue());
							transactionId = insertTransactionEntry(tranEntryVOList.get(j));
							transactionVO.setTransactionId(transactionId);
							tranEntryVOList.set(j,transactionVO);
							logger.info("##reconsiledInd.intValue():"+reconsiledInd.intValue());
							logger.info("##transactionVO.getTransactionTypeId():"+transactionVO.getTransactionTypeId());
							if(reconsiledInd.intValue()==1 || transactionVO.getTransactionTypeId() == CommonConstants.TRANSACTION_TYPE_ID_WITHDRAW_CASH
									|| transactionVO.getTransactionTypeId() == CommonConstants.TRANSACTION_TYPE_ID_ESCHEAT_FUNDS)
							{
								existingLedgerBalanceVO.setLedgerEntityId(transactionVO.getLedgerEntityId());
								existingLedgerBalanceVO.setLedgerEntityTypeId(new Long(transactionVO.getLedgerEntityTypeId()).intValue());
								if(transactionVO.getLedgerEntityTypeId()==30)
								{
									existingLedgerBalanceVO.setLedgerEntityId(transactionVO.getOriginatingBuyerId());
									existingLedgerBalanceVO.setLedgerEntityTypeId(10);
								}
								existingLedgerBalanceVO = lockEntityBalance(existingLedgerBalanceVO);
								if(existingLedgerBalanceVO == null)
								{
									existingLedgerBalanceVO = new LedgerBalanceVO();
									existingLedgerBalanceVO.setLedgerEntityId(transactionVO.getLedgerEntityId());
									existingLedgerBalanceVO.setLedgerEntityTypeId(new Long(transactionVO.getLedgerEntityTypeId()).intValue());
								}
								createLedgerBalanceEntry(transactionVO,existingLedgerBalanceVO);
							}
	
						}
						tranVO.replaceTransactions(tranEntryVOList);
					}
					tranVOList.set(i, tranVO);
				}
				busTransVO.setTransactionEntryId(transactionId);
				busTransVO.setTransactions(tranVOList);
			} catch (Exception ex) {
				logger.error("TransactionDaoImpl.updateLedgerTransEntryModifiedDate", ex);
				throw new DataServiceException("thrown by loadTransactionEntries", ex);
			}
			
			return busTransVO;
			
		}// end: synchronized (loadTransactionEntriesObjectClassStaticLock)
		
	}

	public void markLedgerEntryReconciled(long ledgerEntryId) throws DataServiceException {

		update("markLedgerEntryReconciled.update", ledgerEntryId);
	}

	public List<TransactionRuleVO> queryTransactionRule(TransactionRuleVO vo) throws DataServiceException {
		try {
			return queryForList("transaction_rule.query", vo);
		} catch (Exception ex) {
			throw new DataServiceException("thrown by queryTransactionRule", ex);
		}
	}

	public void updateLedgerTransEntryModifiedDate(Date modifiedDate, long ledgerEntryId) throws DataServiceException {

		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("ledgerEntryId", ledgerEntryId);
		parameter.put("modifiedDate", modifiedDate);
		update("lte_modified_date.update", parameter);

	}

	public List<TransactionVO> getPastWithdrawals(Long accountId) {
		List<TransactionVO> accounts = (List<TransactionVO>) queryForList("ledger_entry_for_withdraw.query", accountId);
		return accounts;
	}

	public Double getValueLinkBalanceByEntityId(long entityId) {
		WalletBalanceVO wbvo = (WalletBalanceVO) queryForObject("getVLBalanceFromFullfillmentEntry.query", entityId);
		if( wbvo == null ) {
			return 0.0d;
		}
		return wbvo.getValidatedAmount();
	}

	public Long getPastWithdrawalsWithSameAmt(Long accountId,Integer timeIntervalSec,Double transactionAmount ) {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("accountId", accountId);
		parameter.put("timeInterval", timeIntervalSec);
		parameter.put("transactionAmount", transactionAmount);
		Long count = (Long) queryForObject("ledger_entry_record_amount_withdraw.query", parameter);
		return count;
	}

	public ReceiptVO getTransactionReceipt(ReceiptVO receiptInput)
			throws DataServiceException {
		try{
			return (ReceiptVO)queryForObject("select_transaction_receipt.query", receiptInput);
		}catch(Exception e){
			logger.error("", e);
			throw new DataServiceException("thrown by getTransactionReceipt", e);
		}
	}

	public void insertWithdrawalErrorLogging(ProviderWithdrawalErrorVO providerWithdrawalErrorVO) throws DataServiceException
	{
		try {
			insert("provider_withdrawal_error_log.insert", providerWithdrawalErrorVO);
		} catch (Exception ex) {
			throw new DataServiceException("Exception in insertWithdrawalErrorLogging", ex);
		}
	}

	public Double getTransactionAmountById(Long transactionId) throws DataServiceException {
		Double amount = (Double) queryForObject("ledger_transaction_amount_by_id.query", transactionId);
		if( amount == null ) {
			return 0.0d;
		}
		return amount;
	}

	public String getLedgerEntryNonce(long busTransId) throws DataServiceException {
		String result = "";
		try {
			result = (String) queryForObject("ledgerentry_nonce.query", busTransId);
		} catch (Exception e) {
			throw new DataServiceException("thrown by ledgerentry_nonce.query", e);
		}
		return (result == null ? "" : result);
	}
	public void createLedgerBalanceEntry(TransactionEntryVO tranVO,LedgerBalanceVO existingLedgerBalanceVO) throws DataServiceException
	{
		int flag = 1;
		try
		{
			LedgerBalanceVO insertLedgerBalanceVO = new LedgerBalanceVO();
			insertLedgerBalanceVO.setLedgerEntityId(existingLedgerBalanceVO.getLedgerEntityId());
			insertLedgerBalanceVO.setLedgerEntityTypeId(existingLedgerBalanceVO.getLedgerEntityTypeId());
			if(existingLedgerBalanceVO.getLedgerBalanceId()== null)
			{
				existingLedgerBalanceVO.setAvailableBalance(0D);
				existingLedgerBalanceVO.setProjectBalance(0D);
				existingLedgerBalanceVO.setLedgerEntryId(0L);
				insertLedgerBalanceVO.setLedgerEntryId(0L);
				insertLedgerBalanceVO.setLedgerBalanceId(0L);
				insertLedgerBalanceVO.setBalanceAffectedInd("none");
			}
			int creditDebitInd = 1;
			if(tranVO.getEntryTypeId()==1)
			{
				creditDebitInd = -1;
			}

			if(tranVO.getLedgerEntityTypeId()==30)
			{
				insertLedgerBalanceVO.setAvailableBalance(existingLedgerBalanceVO.getAvailableBalance());
				insertLedgerBalanceVO.setProjectBalance
				(existingLedgerBalanceVO.getProjectBalance() + tranVO.getTransactionAmount()*creditDebitInd);
				insertLedgerBalanceVO.setBalanceAffectedInd("project");
			}else
			{
				flag=2;
				insertLedgerBalanceVO.setProjectBalance(existingLedgerBalanceVO.getProjectBalance());
				insertLedgerBalanceVO.setAvailableBalance
				(existingLedgerBalanceVO.getAvailableBalance() + tranVO.getTransactionAmount()*creditDebitInd);
				insertLedgerBalanceVO.setBalanceAffectedInd("available");
			}
			insertLedgerBalanceVO.setLedgerEntryId(tranVO.getLedgerEntryId());
			if(tranVO.getLedgerEntryId()==existingLedgerBalanceVO.getLedgerEntryId().longValue())
			{
				insertLedgerBalanceVO.setLedgerBalanceId(existingLedgerBalanceVO.getLedgerBalanceId());
				update("ledger_balance.update",insertLedgerBalanceVO);
			}
			else
			{
				insert("ledger_balance.insert",insertLedgerBalanceVO);
			}
			//Update entry in fulfillment_vlaccounts if record is present for the entity
			//Else create a new entry.
			if(flag==1)
			{
				insertLedgerBalanceVO.setAvailableBalance(null);
			}
			else
			{
				insertLedgerBalanceVO.setProjectBalance(null);
			}
			insert("fulfillment_vlaccounts.insert",insertLedgerBalanceVO);

		}catch (Exception ex) {
			throw new DataServiceException("Exception in createLedgerBalanceEntry", ex);
		}
	}

	public LedgerBalanceVO lockEntityBalance(LedgerBalanceVO ledgerBalanceVO) throws DataServiceException{
		try
		{
			ledgerBalanceVO = (LedgerBalanceVO) this.queryForObject("select_ledger_balance.query", ledgerBalanceVO);
		}catch (Exception ex) {
			throw new DataServiceException("Exception in lockEntityBalance", ex);
		}
		return ledgerBalanceVO;
	}

	public ArrayList<TransactionEntryVO> getTransactionEntriesByLedgerEntry(Long ledgerEntryId) throws DataServiceException{
		try
		{
			ArrayList<TransactionEntryVO> tranEntryArrayList = (ArrayList<TransactionEntryVO>) queryForList(
					"ledger_transaction_entries_by_ledger_entry.query",ledgerEntryId);
			return tranEntryArrayList;
		}
		catch (Exception ex) {
			throw new DataServiceException("Exception in getTransactionEntriesByLedgerEntry", ex);
		}
	}
	
	public List<Long> getDebitRulesListForVarianceCheck() throws DataServiceException {
		
		List<Long> debitRulesList = (List<Long>) queryForList("get_debitrules_for_variancecheck.query");
		if( debitRulesList == null ) {
			debitRulesList =  new ArrayList<Long>();
		}
		return debitRulesList;
	}
	
	public Double getCreditAmountBySO(String soId) throws DataServiceException {
		
		Double amount = (Double) queryForObject("get_creditamt_for_variancecheck.query", soId);
		if( amount == null ) {
			return 0.0d;
		}
		return amount;
	}

}
