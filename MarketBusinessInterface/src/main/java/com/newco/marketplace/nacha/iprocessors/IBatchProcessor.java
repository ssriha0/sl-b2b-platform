package com.newco.marketplace.nacha.iprocessors;

import java.sql.Timestamp;

import com.newco.marketplace.dto.vo.ach.NachaProcessLogVO;
import com.newco.marketplace.dto.vo.ach.TransactionBatchVO;
import com.newco.marketplace.exception.BusinessServiceException;
/**
 * @author spatsa
 */
public interface IBatchProcessor {
	
	public TransactionBatchVO processBatches(long nachaProcessLogId) throws BusinessServiceException;
	public TransactionBatchVO processBatches() throws BusinessServiceException;
	public void updateAchRecordsStatus(TransactionBatchVO transactionBatchVO) throws BusinessServiceException;
	public void updateNachaProcessLog(NachaProcessLogVO nachaProcessLogVO ) throws BusinessServiceException;
	public Long insertNachaProcessLog(String machineName, String fileName, Timestamp timestamp)
	throws BusinessServiceException;
	public Long insertNachaProcessHistoryLog(long achProcessLogId, int achProcessStatusId, Timestamp updatedTimestamp,
			String comments, String updatedBy) throws BusinessServiceException;
	public void updateNachaProcessHistoryLog(long achProcessLogHistoryId, String comments, String updatedBy)
	throws BusinessServiceException;
	public void rollBackQueueTransactions(TransactionBatchVO transactionBatchVO);
	
	
	
	

}
