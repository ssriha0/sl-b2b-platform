package com.servicelive.wallet.batch.trans.dao;

import java.util.List; 

import org.apache.commons.lang.StringUtils;

import com.servicelive.common.ABaseDao;
import com.servicelive.common.CommonConstants;
import com.servicelive.common.exception.DataServiceException;
import com.servicelive.wallet.batch.trans.vo.TransactionBatchVO;
import com.servicelive.wallet.batch.trans.vo.TransactionProcessLogVO;
import com.servicelive.wallet.batch.trans.vo.TransactionRecordVO;

/**
 * The Class TransFileDao.
 */
public class TransFileDao extends ABaseDao implements ITransFileDao {

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.trans.dao.ITransFileDao#getAllCreditCardTransactions()
	 */
	public List<TransactionRecordVO> getAllCreditCardTransactions() throws DataServiceException {

		List<TransactionRecordVO> transRecordDTOList = null;

		try {
			transRecordDTOList = queryForList("transCreditTransactions.select", null);
		} catch (Exception ex) {
			throw new DataServiceException("Exception occurred in TransFileDao.getAllCreditCardTransactions()", ex);
		}
		return transRecordDTOList;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.trans.dao.ITransFileDao#getConsolidatedDepositEntryAmountFromAchProcessQueue()
	 */
	public Double getConsolidatedDepositEntryAmountFromAchProcessQueue() throws DataServiceException {

		try {
			return (Double) queryForObject("achProcessQueueCreditCardDepositRollUp.select", null);

		} catch (Exception ex) {
			throw new DataServiceException("Exception occurred in TransFileDao.getConsolidatedDepositEntryAmountFromAchProcessQueue()", ex);
		}

	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.trans.dao.ITransFileDao#getConsolidatedRefundEntryAmountFromAchProcessQueue()
	 */
	public Double getConsolidatedRefundEntryAmountFromAchProcessQueue() throws DataServiceException {

		try {
			return (Double) queryForObject("achProcessQueueCreditCardRefundRollUp.select", null);

		} catch (Exception ex) {
			throw new DataServiceException("Exception occurred in TransFileDaoImpl.getConsolidatedRefundEntryAmountFromAchProcessQueue()", ex);
		}

	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.trans.dao.ITransFileDao#updateTransactionStatus(com.servicelive.wallet.batch.trans.vo.TransactionBatchVO)
	 */
	public int updateTransactionStatus(TransactionBatchVO transactionBatchVO) throws DataServiceException {

		int q = 0;
		try {
			if (transactionBatchVO.getAchProcessIds() != null && transactionBatchVO.getAchProcessIds().size() > 0) {

				q = update("transRecord.update", transactionBatchVO);

			}
		} catch (Exception ex) {
			throw new DataServiceException("Exception occurred in TransFileDao.updatePTDInfo()", ex);
		}
		return q;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.trans.dao.ITransFileDao#updateTransLog(com.servicelive.wallet.batch.trans.vo.TransactionProcessLogVO)
	 */
	public Long updateTransLog(TransactionProcessLogVO transactionProcessLogVO) throws DataServiceException {

		Long id = null;
		try {
			id = (Long) insert("transprocessLog.insert", transactionProcessLogVO);
		} catch (Exception ex) {
			throw new DataServiceException("Exception occurred in TransFileDao.updateTransLog()", ex);
		}
		return id;
	}

	
	public int updateCCRollupRecords(){
		int q = 0;
		q = update("transCCRollupRecord.update",null);
		return q;		
	}
	 /**
     * SL-20853 
     * Method to fetch the details for the transaction file generation
     * @return List<TransactionRecordVO>
     * @throws DataServiceException
     */
	public List<TransactionRecordVO> getTransactions() throws DataServiceException{
		List<TransactionRecordVO> transRecordVOList = null;

		try {
			transRecordVOList = queryForList("transCreditTransactionsPCI.select", null);
		} catch (Exception ex) {
			throw new DataServiceException("Exception occurred in TransFileDao.getTransactions()", ex);
		}
		return transRecordVOList;
	}

	/**
	 * SL-20853 Trans file value fetch from applications_properties table
	 * @return TransactionRecordVO
	 * @throws DataServiceException
	 */
	public boolean isPCIVersion() throws DataServiceException {
		boolean isPCIVersion=false;
		try{
			String value=  (String) queryForObject("getPCIVersion.select", null);
			if(StringUtils.isNotBlank(value) && CommonConstants.TRANS_FILE_EXISTS.equalsIgnoreCase(value))
			{
				isPCIVersion= true;
			}
		}
		catch (Exception ex) {
			throw new DataServiceException("Exception occurred in TransFileDao.isPCIVersion()", ex);
		}
		return isPCIVersion;
	}
}
