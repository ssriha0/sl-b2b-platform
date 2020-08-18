package com.newco.marketplace.persistence.iDao.trans;

import java.util.List;

import com.newco.marketplace.dto.vo.ach.TransactionBatchVO;
import com.newco.marketplace.dto.vo.trans.TransactionProcessLogVO;
import com.newco.marketplace.exception.DataServiceException;

public interface TransFileDao {
public List getAllCreditCardTransactions() throws DataServiceException;
public int updateTransactionStatus(TransactionBatchVO transactionBatchVO) throws DataServiceException;
public Long updateTransLog(TransactionProcessLogVO transactionProcessLogVO) throws DataServiceException;
public Double getConsolidatedDepositEntryAmountFromAchProcessQueue()throws DataServiceException;
public Double getConsolidatedRefundEntryAmountFromAchProcessQueue()throws DataServiceException;
public int updateCCRollupRecords();

}

