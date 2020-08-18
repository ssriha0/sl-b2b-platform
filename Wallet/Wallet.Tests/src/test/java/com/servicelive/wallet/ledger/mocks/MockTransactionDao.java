package com.servicelive.wallet.ledger.mocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.servicelive.common.CommonConstants;
import com.servicelive.common.exception.DataServiceException;
import com.servicelive.common.vo.SLAccountVO;
import com.servicelive.wallet.ledger.dao.ITransactionDao;
import com.servicelive.wallet.ledger.vo.LedgerBalanceVO;
import com.servicelive.wallet.ledger.vo.LedgerBusinessTransactionVO;
import com.servicelive.wallet.ledger.vo.TransactionEntryVO;
import com.servicelive.wallet.ledger.vo.TransactionHistoryVO;
import com.servicelive.wallet.ledger.vo.TransactionRuleVO;
import com.servicelive.wallet.ledger.vo.TransactionVO;
import com.servicelive.wallet.serviceinterface.vo.ProviderWithdrawalErrorVO;
import com.servicelive.wallet.serviceinterface.vo.ReceiptVO;

// TODO: Auto-generated Javadoc
/**
 * Class MockTransactionDao.
 */
public class MockTransactionDao implements ITransactionDao {

	/** transTypes. */
	private static final Integer[] transTypes = { 100, 150, 110, 111, 115, 120, 160 };

	/** alTransTypes. */
	private static final ArrayList<Integer> alTransTypes = new ArrayList<Integer>(Arrays.asList(transTypes));

	/** transaction. */
	private TransactionVO transaction;

	/** transactionDao. */
	private ITransactionDao transactionDao;

	/** transactionEntries. */
	private List<TransactionEntryVO> transactionEntries;

	private double availableBalance;

	private double valueLinkBalance;

	/**
	 * MockTransactionDao.
	 */
	public MockTransactionDao() {
		reset();
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.ledger.dao.ITransactionDao#getAvailableBalanceByEntityId(long, long)
	 */
	public Double getAvailableBalanceByEntityId(long ledgerEntityId, long ledgerEntityTypeId, boolean lockForUpdate) throws DataServiceException {
		return availableBalance;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.ledger.dao.ITransactionDao#getLedgerEntryByEntryId(java.lang.Long)
	 */
	public TransactionVO getLedgerEntryByEntryId(long ledgerId, long ledgerEntityTypeId) throws DataServiceException {
		return transactionDao.getLedgerEntryByEntryId(ledgerId, ledgerEntityTypeId);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.ledger.dao.ITransactionDao#getLedgerRulePricingExpressionsMap(long)
	 */
	public Map<String, String> getLedgerRulePricingExpressionsMap(long busTransId) throws DataServiceException {

		return transactionDao.getLedgerRulePricingExpressionsMap(busTransId);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.ledger.dao.ITransactionDao#getPostSOLedgerAmount(com.servicelive.wallet.ledger.vo.TransactionVO)
	 */
	public Double getPostSOLedgerAmount(String soId) throws DataServiceException {

		return transactionDao.getPostSOLedgerAmount(soId);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.ledger.dao.ITransactionDao#getProjectBalanceByEntityId(long, long)
	 */
	public double getProjectBalanceByEntityId(long ledgerEntityId, long ledgerEntityTypeId) throws DataServiceException {

		return transactionDao.getProjectBalanceByEntityId(ledgerEntityId, ledgerEntityTypeId);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.ledger.dao.ITransactionDao#getSLOperationTransactionEntries()
	 */
	public List<TransactionEntryVO> getSLOperationTransactionEntries() {

		return transactionDao.getSLOperationTransactionEntries();
	}

	// //// DELEGATE METHODS

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.ledger.dao.ITransactionDao#getTotalBalanceByEntityId(long, long)
	 */
	public Double getTotalBalanceByEntityId(long ledgerEntityId, long ledgerEntityTypeId) throws DataServiceException {

		return transactionDao.getTotalBalanceByEntityId(ledgerEntityId, ledgerEntityTypeId);
	}


	/* (non-Javadoc)
	 * @see com.servicelive.wallet.ledger.dao.ITransactionDao#getTotalDepositByEntityId(long, long)
	 */
	public double getTotalDepositByEntityId(long ledgerEntityId, long ledgerEntityTypeId) throws DataServiceException {

		return transactionDao.getTotalDepositByEntityId(ledgerEntityId, ledgerEntityTypeId);
	}

	/**
	 * getTransaction.
	 * 
	 * @return TransactionVO
	 */
	public TransactionVO getTransaction() {

		return transaction;
	}

	/**
	 * getTransactionEntries.
	 * 
	 * @return List<TransactionEntryVO>
	 */
	public List<TransactionEntryVO> getTransactionEntries() {

		return transactionEntries;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.ledger.dao.ITransactionDao#getTransactionEntriesForPendingRequests(long)
	 */
	public List<TransactionEntryVO> getTransactionEntriesForPendingRequests(long ledgerEntityId) {

		return transactionDao.getTransactionEntriesForPendingRequests(ledgerEntityId);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.ledger.dao.ITransactionDao#getTransactionHistory(com.servicelive.wallet.ledger.vo.TransactionHistoryVO)
	 */
	public List<TransactionHistoryVO> getTransactionHistory(TransactionHistoryVO thVO) throws DataServiceException {

		return transactionDao.getTransactionHistory(thVO);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.ledger.dao.ITransactionDao#getTransactionHistoryDetail(com.servicelive.wallet.ledger.vo.TransactionHistoryVO)
	 */
	public List<TransactionHistoryVO> getTransactionHistoryDetail(TransactionHistoryVO thVO) throws DataServiceException {

		return transactionDao.getTransactionHistoryDetail(thVO);
	}

	/**
	 * insertTransaction.
	 * 
	 * @param vo 
	 * 
	 * @return TransactionVO
	 * 
	 * @throws DataServiceException 
	 */
	protected TransactionVO insertTransaction(TransactionVO vo) throws DataServiceException {

		try {
			transaction = vo;
		} catch (Exception ex) {
			throw new DataServiceException("thrown by insertTransaction", ex);
		}
		return vo;
	}

	/**
	 * insertTransactionEntry.
	 * 
	 * @param vo 
	 * 
	 * @return TransactionEntryVO
	 * 
	 * @throws DataServiceException 
	 */
	protected TransactionEntryVO insertTransactionEntry(TransactionEntryVO vo) throws DataServiceException {

		try {
			transactionEntries.add(vo);
		} catch (Exception ex) {
			throw new DataServiceException("thrown by insertTransactionEntry", ex);
		}
		return vo;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.ledger.dao.ITransactionDao#loadTransactionEntries(com.servicelive.wallet.ledger.vo.LedgerBusinessTransactionVO)
	 */
	public LedgerBusinessTransactionVO loadTransactionEntries(LedgerBusinessTransactionVO busTransVO) throws DataServiceException {

		try {
			ArrayList<TransactionVO> tranVOList = busTransVO.getTransactions();
			for (int i = 0; i < tranVOList.size(); i++) {
				TransactionVO tranVO = tranVOList.get(i);

				if (busTransVO.getFundingTypeId() == CommonConstants.FUNDING_TYPE_NON_FUNDED && alTransTypes.contains(busTransVO.getBusinessTransId())) {
					tranVO.setAffectsBalanceInd(0);

				}
				insertTransaction(tranVO); // insert into ledger_entry
				ArrayList<TransactionEntryVO> tranEntryVOList = tranVO.getTransactions();
				if (tranEntryVOList != null && !tranEntryVOList.isEmpty() ) {
					for (int j = 0; j < tranEntryVOList.size(); j++) {
						insertTransactionEntry(tranEntryVOList.get(j)); // insert into ledger_transaction_entry
					}
				}
			}
		} catch (Exception ex) {
			throw new DataServiceException("thrown by loadTransactionEntries", ex);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.ledger.dao.ITransactionDao#markLedgerEntryReconciled(long)
	 */
	public void markLedgerEntryReconciled(long ledgerEntryId) throws DataServiceException {

		transactionDao.markLedgerEntryReconciled(ledgerEntryId);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.ledger.dao.ITransactionDao#queryTransactionRule(com.servicelive.wallet.ledger.vo.TransactionRuleVO)
	 */
	public List<TransactionRuleVO> queryTransactionRule(TransactionRuleVO vo) throws DataServiceException {

		return transactionDao.queryTransactionRule(vo);
	}

	/**
	 * reset.
	 * 
	 * @return void
	 */
	public final void reset() {

		transaction = null;
		transactionEntries = new ArrayList<TransactionEntryVO>();
	}

	/**
	 * setTransactionDao.
	 * 
	 * @param transactionDao 
	 * 
	 * @return void
	 */
	public void setTransactionDao(ITransactionDao transactionDao) {

		this.transactionDao = transactionDao;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.ledger.dao.ITransactionDao#updateLedgerTransEntryModifiedDate(java.util.Date, long)
	 */
	public void updateLedgerTransEntryModifiedDate(Date modifiedDate, long ledgerEntryId) throws DataServiceException {

		transactionDao.updateLedgerTransEntryModifiedDate(modifiedDate, ledgerEntryId);
	}

	public List<TransactionVO> getPastWithdrawals(Long accountId) throws DataServiceException {

		// TODO Auto-generated method stub
		return null;
	}

	public Double getValueLinkBalanceByEntityId(long entityId) {
		return valueLinkBalance;
	}

	
	public void setAvailableBalance(double availableBalance) {
	
		this.availableBalance = availableBalance;
	}

	
	public void setValueLinkBalance(double valueLinkBalance) {
	
		this.valueLinkBalance = valueLinkBalance;
	}

	public double getBuyerTotalDeposit(int buyerId) throws DataServiceException {
		// TODO Auto-generated method stub
		return 0;
	}

	public SLAccountVO getAutoFundingIndicator(Integer vendorId) {
		// TODO Auto-generated method stub
		return null;
	}

	public SLAccountVO getAccountDetails(Integer entityId) {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer countVoidCancelCloseTransactions(int busTransId, String soId) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Long getPastWithdrawalsWithSameAmt(Long accountId,
			Integer timeIntervalSec, Double transactionAmount) {
		// TODO Auto-generated method stub
		return null;
	}

	public ReceiptVO getTransactionReceipt(ReceiptVO receiptInput)
			throws DataServiceException {
		return transactionDao.getTransactionReceipt(receiptInput);
	}

	public void insertWithdrawalErrorLogging(
			ProviderWithdrawalErrorVO providerWithdrawalErrorVO)
			throws DataServiceException {
		// TODO Auto-generated method stub
		
	}
	
	public String getLedgerEntryNonce(long busTransId) throws DataServiceException {
	    return null;
	}
	public LedgerBalanceVO lockEntityBalance(LedgerBalanceVO ledgerBalanceVO) throws DataServiceException {
		return null;
	}
	
	public void createLedgerBalanceEntry(TransactionEntryVO tranVO,LedgerBalanceVO existingLedgerBalanceVO) throws DataServiceException{
		
		
	}
	
	public ArrayList<TransactionEntryVO> getTransactionEntriesByLedgerEntry(Long ledgerEntryId) throws DataServiceException {
		
		return null;
	}

	public Double getTransactionAmountById(Long transactionId)
			throws DataServiceException {
		// TODO Auto-generated method stub
		return null;
	}

}
