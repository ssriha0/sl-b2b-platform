package com.newco.marketplace.business.iBusiness.ledger;

import com.newco.marketplace.exception.BusinessServiceException;

public interface ILedgerTransactionBO {
	
	/**
	 * If a buyer has already deposited a total of $7500 (configurable)
	 * or more, this will return true.
	 * 
	 * Only Cash Deposits (transaction type - 100) and Credit Deposit
	 * (transaction type - 1200) are used in calculating the sum.
	 * 
	 * @param buyerId
	 * @return
	 */
	public boolean isDepositOverLimit(int buyerId, double fundingAmount)  throws BusinessServiceException;
	
}
