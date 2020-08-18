package com.newco.marketplace.persistence.daoImpl.trans;

import java.util.List;

import com.newco.marketplace.dto.vo.ach.TransactionBatchVO;
import com.newco.marketplace.dto.vo.trans.TransactionProcessLogVO;
import com.newco.marketplace.dto.vo.trans.TransactionRecordVO;
import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.persistence.iDao.trans.TransFileDao;
import com.sears.os.dao.impl.ABaseImplDao;

public class TransFileDaoImpl extends ABaseImplDao implements TransFileDao {

	public List<TransactionRecordVO> getAllCreditCardTransactions() throws DataServiceException {
		List<TransactionRecordVO> transRecordDTOList = null;

		try {
			transRecordDTOList = queryForList("transCreditTransactions.select", null);
		} catch (Exception ex) {
			throw new DataServiceException("Exception occurred in TransFileDaoImpl.getAllCreditCardTransactions()", ex);
		}
		return transRecordDTOList;
	}

	public int updateTransactionStatus(TransactionBatchVO transactionBatchVO) throws DataServiceException {
		int q = 0;
		try {
			if (transactionBatchVO.getAchProcessIds() != null && transactionBatchVO.getAchProcessIds().size() > 0) {

				q = update("transRecord.update", transactionBatchVO);

			}
		} catch (Exception ex) {
			throw new DataServiceException("Exception occurred in TransFileDaoImpl.updatePTDInfo()", ex);
		}
		return q;
	}


	public int updateCCRollupRecords(){
		int q = 0;
		q = update("transCCRollupRecord.update",null);
		return q;
	}

	public Long updateTransLog(TransactionProcessLogVO transactionProcessLogVO) throws DataServiceException {
		Long id = null;
		try {
			id = (Long) insert("transprocessLog.insert", transactionProcessLogVO);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new DataServiceException("Exception occurred in TransFileDaoImpl.updateTransLog()", ex);
		}
		return id;
	}

	public Double getConsolidatedDepositEntryAmountFromAchProcessQueue() throws DataServiceException {

		try {
			return (Double) queryForObject("achProcessQueueCreditCardDepositRollUp.select", null);

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new DataServiceException("Exception occurred in TransFileDaoImpl.getConsolidatedDepositEntryAmountFromAchProcessQueue()", ex);
		}

	}

	public Double getConsolidatedRefundEntryAmountFromAchProcessQueue() throws DataServiceException {
		try {
			return (Double) queryForObject("achProcessQueueCreditCardRefundRollUp.select", null);

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new DataServiceException("Exception occurred in TransFileDaoImpl.getConsolidatedRefundEntryAmountFromAchProcessQueue()", ex);
		}

	}

}
