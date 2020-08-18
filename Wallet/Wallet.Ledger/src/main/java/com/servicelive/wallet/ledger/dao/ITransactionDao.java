package com.servicelive.wallet.ledger.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.servicelive.common.exception.DataServiceException;
import com.servicelive.wallet.ledger.vo.LedgerBalanceVO;
import com.servicelive.wallet.ledger.vo.LedgerBusinessTransactionVO;
import com.servicelive.wallet.ledger.vo.TransactionEntryVO;
import com.servicelive.wallet.ledger.vo.TransactionHistoryVO;
import com.servicelive.wallet.ledger.vo.TransactionRuleVO;
import com.servicelive.wallet.ledger.vo.TransactionVO;
import com.servicelive.wallet.serviceinterface.vo.ProviderWithdrawalErrorVO;
import com.servicelive.wallet.serviceinterface.vo.ReceiptVO;

public interface ITransactionDao {

	public Integer countVoidCancelCloseTransactions(int busTransId, String soId) throws DataServiceException;

	public Double getAvailableBalanceByEntityId(long ledgerEntityId, long ledgerEntityTypeId, boolean lockForUpdate) throws DataServiceException;
    
	public Double getAvailableBalanceByEntityIdFromLedger(long ledgerEntityId, long ledgerEntityTypeId, boolean lockForUpdate) throws DataServiceException;
	
	//SL-21117: Revenue Pull Code change Starts
	
	public List <String> getPermittedUsers(long ind) throws DataServiceException;
		
	public Double getAvailableBalanceForRevenuePull(long ledgerEntityId, long ledgerEntityTypeId) throws DataServiceException;
		
	public boolean getAvailableDateCheckForRevenuePull(Date calendarOnDate) throws DataServiceException;
		
	public void insertEntryForRevenuePull(double amount,Date revenuePullDate,String note,String user,long ind,Date todayDate,String status) throws DataServiceException;
		
	public List <String> getPermittedUsersEmail(long ind) throws DataServiceException;
		
	//Code change ends
    
	public TransactionVO getLedgerEntryByEntryId(long ledgerId, long ledgerEntityTypeId) throws DataServiceException;

	public Map<String, String> getLedgerRulePricingExpressionsMap(long busTransId) throws DataServiceException;

	public Double getPostSOLedgerAmount(String soId) throws DataServiceException;

	public double getProjectBalanceByEntityId(long ledgerEntityId, long ledgerEntityTypeId) throws DataServiceException;

	public List<TransactionEntryVO> getSLOperationTransactionEntries();

	public Double getTotalBalanceByEntityId(long ledgerEntityId, long ledgerEntityTypeId) throws DataServiceException;

	public double getTotalDepositByEntityId(long ledgerEntityId, long ledgerEntityTypeId) throws DataServiceException;

	public List<TransactionEntryVO> getTransactionEntriesForPendingRequests(long ledgerEntityId);

	public List<TransactionHistoryVO> getTransactionHistory(TransactionHistoryVO thVO) throws DataServiceException;

	public List<TransactionHistoryVO> getTransactionHistoryDetail(TransactionHistoryVO thVO) throws DataServiceException;
	
	

	public LedgerBusinessTransactionVO loadTransactionEntries(LedgerBusinessTransactionVO busTransVO) throws DataServiceException;

	public void markLedgerEntryReconciled(long ledgerEntryId) throws DataServiceException;

	public List<TransactionRuleVO> queryTransactionRule(TransactionRuleVO vo) throws DataServiceException;

	public void updateLedgerTransEntryModifiedDate(Date modifiedDate, long ledgerEntryId) throws DataServiceException;

	public List<TransactionVO> getPastWithdrawals(Long accountId) throws DataServiceException;

	public Double getValueLinkBalanceByEntityId(long entityId);
	
	public Long getPastWithdrawalsWithSameAmt(Long accountId,Integer timeIntervalSec,Double transactionAmount);

	public ReceiptVO getTransactionReceipt(ReceiptVO receiptInput) throws DataServiceException;
	
	public void insertWithdrawalErrorLogging(ProviderWithdrawalErrorVO providerWithdrawalErrorVO) throws DataServiceException;
	
	public String getLedgerEntryNonce(long busTransId) throws DataServiceException;
	
	public LedgerBalanceVO lockEntityBalance(LedgerBalanceVO ledgerBalanceVO) throws DataServiceException;
	
	public void createLedgerBalanceEntry(TransactionEntryVO tranVO,LedgerBalanceVO existingLedgerBalanceVO) throws DataServiceException;
	
	public ArrayList<TransactionEntryVO> getTransactionEntriesByLedgerEntry(Long ledgerEntryId) throws DataServiceException;
	
	public Double getTransactionAmountById(Long transactionId) throws DataServiceException;
	
	public Double getCreditAmountBySO(String soId) throws DataServiceException;
	
	public List<Long> getDebitRulesListForVarianceCheck() throws DataServiceException;

}
