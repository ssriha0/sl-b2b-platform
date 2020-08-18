package com.servicelive.wallet.batch.trans.dao;

import java.util.List;

import com.servicelive.common.exception.DataServiceException;
import com.servicelive.wallet.batch.trans.vo.TransactionBatchVO;
import com.servicelive.wallet.batch.trans.vo.TransactionProcessLogVO;
import com.servicelive.wallet.batch.trans.vo.TransactionRecordVO;

// TODO: Auto-generated Javadoc
/**
 * The Interface ITransFileDao.
 */
public interface ITransFileDao {

	/**
	 * Gets the all credit card transactions.
	 * 
	 * @return the all credit card transactions
	 * 
	 * @throws DataServiceException 
	 */
	public List<TransactionRecordVO> getAllCreditCardTransactions() throws DataServiceException;

	/**
	 * Gets the consolidated deposit entry amount from ach process queue.
	 * 
	 * @return the consolidated deposit entry amount from ach process queue
	 * 
	 * @throws DataServiceException 
	 */
	public Double getConsolidatedDepositEntryAmountFromAchProcessQueue() throws DataServiceException;

	/**
	 * Gets the consolidated refund entry amount from ach process queue.
	 * 
	 * @return the consolidated refund entry amount from ach process queue
	 * 
	 * @throws DataServiceException 
	 */
	public Double getConsolidatedRefundEntryAmountFromAchProcessQueue() throws DataServiceException;

	/**
	 * Update transaction status.
	 * 
	 * @param transactionBatchVO 
	 * 
	 * @return the int
	 * 
	 * @throws DataServiceException 
	 */
	public int updateTransactionStatus(TransactionBatchVO transactionBatchVO) throws DataServiceException;

	/**
	 * Update trans log.
	 * 
	 * @param transactionProcessLogVO 
	 * 
	 * @return the long
	 * 
	 * @throws DataServiceException 
	 */
	public Long updateTransLog(TransactionProcessLogVO transactionProcessLogVO) throws DataServiceException;

	/**
	 * updateCCRollupRecords
	 * 
	 */
	public int updateCCRollupRecords();
	
    /**
     * SL-20853 
     * Method to fetch the details for the transaction file generation
     * @return List<TransactionRecordVO>
     * @throws DataServiceException
     */
	public List<TransactionRecordVO> getTransactions() throws DataServiceException;
    
	/**
	 * SL-20853 Trans file value fetch from applications_properties table
	 * @return boolean
	 * @throws DataServiceException
	 */
	public boolean isPCIVersion() throws DataServiceException;

	
}
