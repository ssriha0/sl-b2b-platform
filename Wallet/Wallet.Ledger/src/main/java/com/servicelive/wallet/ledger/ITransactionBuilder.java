package com.servicelive.wallet.ledger;

import com.servicelive.wallet.ledger.vo.LedgerBusinessTransactionVO;
import com.servicelive.wallet.ledger.vo.TransactionVO;
import com.servicelive.wallet.serviceinterface.vo.OrderPricingVO;

// TODO: Auto-generated Javadoc
/**
 * Interface ITransactionBuilder.
 */
public interface ITransactionBuilder {

	/**
	 * createTransactions.
	 * 
	 * @param orginator 
	 * @param businessTransId 
	 * @param fundingTypeId 
	 * 
	 * @return LedgerBusinessTransactionVO
	 * 
	 * @throws Exception 
	 */
	LedgerBusinessTransactionVO createTransactions(Integer entityTypeId, Long businessTransId, Long fundingTypeId) throws Exception;

	/**
	 * populateTransactionAmount.
	 * 
	 * @param businessTransaction 
	 * @param ledgerVO 
	 * 
	 * @return void
	 * 
	 * @throws Exception 
	 */
	void populateTransactionAmount(LedgerBusinessTransactionVO businessTransaction, OrderPricingVO ledgerVO) throws Exception;

	/**
	 * populateTransactionEntry.
	 * 
	 * @param transaction 
	 * @param accountId 
	 * @param buyerId 
	 * @param providerId 
	 * @param ccInd 
	 * @param returnEntryType 
	 * 
	 * @return long
	 */
	long populateTransactionEntry(TransactionVO transaction, Long accountId, Long buyerId, Long providerId, boolean ccInd, int returnEntryType);

	/**
	 * createTransactionsForNonHSR.
	 * 
	 * @param orginator 
	 * @param businessTransId 
	 * @param fundingTypeId 
	 * 
	 * @return LedgerBusinessTransactionVO
	 * 
	 * @throws Exception 
	 */
	LedgerBusinessTransactionVO createTransactionsForNonHSR(Integer entityTypeId, Long businessTransId,Long fundingTypeId) throws Exception;
	
	LedgerBusinessTransactionVO createTransactionsForHSRwithOutParts(Integer entityTypeId, Long businessTransId,Long fundingTypeId) throws Exception;
	
	
	LedgerBusinessTransactionVO createTransactionsForHSRwithOutAddOn(Integer entityTypeId, Long businessTransId,Long fundingTypeId) throws Exception;
	
	LedgerBusinessTransactionVO createTransactionsForInvoicePartsEmpty(Integer entityTypeId, Long businessTransId,Long fundingTypeId,boolean priceNotValid) throws Exception;
	LedgerBusinessTransactionVO createTransactionsForClosure(Integer entityTypeId, Long businessTransId,Long fundingTypeId) throws Exception;
	
	/**
	 * Populate TransactionAmount and Adjust Variance
	 * @param businessTrans
	 * @param ledgerVO
	 * @param soId
	 * @throws Exception
	 */
	void populateTransactionAmountAdjustPennyVariance(LedgerBusinessTransactionVO businessTrans, OrderPricingVO ledgerVO, String soId) throws Exception;
	
}
