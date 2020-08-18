package com.servicelive.wallet.batch.ach.dao;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;

import com.servicelive.common.exception.DataServiceException;
import com.servicelive.wallet.ach.vo.AchProcessQueueEntryVO;
import com.servicelive.wallet.batch.ach.vo.AchResponseReasonVO;
import com.servicelive.wallet.batch.ach.vo.NachaProcessLogHistoryVO;
import com.servicelive.wallet.batch.ach.vo.NachaProcessLogVO;
import com.servicelive.wallet.batch.ach.vo.NachaProcessQueueVO;
import com.servicelive.wallet.batch.trans.vo.TransactionBatchVO;

// TODO: Auto-generated Javadoc
/**
 * The Interface INachaDao.
 */
public interface INachaDao {

	/**
	 * Account id from ledger id.
	 * 
	 * @param ledgerId 
	 * 
	 * @return the long
	 * 
	 * @throws DataServiceException 
	 */
	public long accountIdFromLedgerId(long ledgerId) throws DataServiceException;

	
	
	public long entityIdFromLedgerId(long ledgerId) throws DataServiceException;
	
	/**
	 * Business Trans id from ledger id.
	 * 
	 * @param ledgerId 
	 * 
	 * @return the long
	 * 
	 * @throws DataServiceException 
	 */
	public long busTransIdFromLedgerId(long ledgerId) throws DataServiceException;
	
	public long entityTypeIdFromLedgerId(long ledgerId) throws DataServiceException;
	
	
	/**
	 * Ach process queue return update.
	 * 
	 * @param achRecord 
	 * 
	 * @throws DataServiceException 
	 */
	
	public void achProcessQueueReturnUpdate(AchProcessQueueEntryVO achRecord) throws DataServiceException;

	/**
	 * Ach process rerun.
	 * 
	 * @param transactionBatchVO 
	 * 
	 * @throws DataServiceException 
	 */
	public void achProcessRerun(TransactionBatchVO transactionBatchVO) throws DataServiceException;

	/**
	 * Adjust consolidated record on origination.
	 * 
	 * @param ledgerEntryId 
	 * 
	 * @throws DataServiceException 
	 */
	public void adjustConsolidatedRecordOnOrigination(long ledgerEntryId) throws DataServiceException;

	/**
	 * Delete balanced record.
	 * 
	 * @param processLogId 
	 * 
	 * @throws DataServiceException 
	 */
	public void deleteBalancedRecord(Integer processLogId) throws DataServiceException;

	/**
	 * Gets the ach process data from ledger id.
	 * 
	 * @param ledgerEntryId 
	 * 
	 * @return the ach process data from ledger id
	 * 
	 * @throws DataServiceException 
	 */
	public AchProcessQueueEntryVO getAchProcessDataFromLedgerId(Long ledgerEntryId) throws DataServiceException;

	/**
	 * Gets the ach process id.
	 * 
	 * @param ledgerEntryId 
	 * 
	 * @return the ach process id
	 * 
	 * @throws DataServiceException 
	 */
	public Long getAchProcessId(long ledgerEntryId) throws DataServiceException;

	/**
	 * Gets the all reasons.
	 * 
	 * @param fileTypeId 
	 * 
	 * @return the all reasons
	 * 
	 * @throws DataServiceException 
	 */
	public HashMap<String, AchResponseReasonVO> getAllReasons(Integer fileTypeId) throws DataServiceException;

	/**
	 * Gets the auto ach consolidation amount.
	 * 
	 * @param processStatusId 
	 * 
	 * @return the auto ach consolidation amount
	 */
	public List<AchProcessQueueEntryVO> getAutoAchConsolidationAmount(int processStatusId);

	/**
	 * Gets the credit card consolidation amount.
	 * 
	 * @param achProcessQueueVO 
	 * 
	 * @return the credit card consolidation amount
	 */
	public double getCreditCardConsolidationAmount(AchProcessQueueEntryVO achProcessQueueVO);

	/**
	 * Gets the Process id from date time.
	 * 
	 * @param createdDateTime 
	 * 
	 * @return the ledger id from date time
	 * 
	 * @throws DataServiceException 
	 */
	public long getProcessIdFromDateTime(String createdDateTime) throws DataServiceException;

	/**
	 * Gets the ledger ids for nacha process.
	 * 
	 * @param achProcessQueueVO 
	 * 
	 * @return the ledger ids for nacha process
	 */
	public List<Long> getLedgerIdsForNachaProcess(AchProcessQueueEntryVO achProcessQueueVO);

	/**
	 * Gets the nacha extract.
	 * 
	 * @param userTransMap 
	 * 
	 * @return the nacha extract
	 * 
	 * @throws DataServiceException 
	 */
	public List<NachaProcessQueueVO> getNachaExtract(HashMap<String, String> userTransMap) throws DataServiceException;

	/**
	 * Gets the process log count.
	 * 
	 * @param currentDate 
	 * 
	 * @return the process log count
	 * 
	 * @throws DataServiceException 
	 */
	public Integer getProcessLogCount(Date currentDate) throws DataServiceException;

	/**
	 * Gets the reason code details.
	 * 
	 * @param reasonId 
	 * 
	 * @return the reason code details
	 * 
	 * @throws DataServiceException 
	 */
	public AchResponseReasonVO getReasonCodeDetails(Integer reasonId) throws DataServiceException;

	/**
	 * Gets the reconciled data.
	 * 
	 * @return the reconciled data
	 * 
	 * @throws DataServiceException 
	 */
	public List<NachaProcessQueueVO> getReconciledData() throws DataServiceException;

	/**
	 * Gets the sum ach process by log id.
	 * 
	 * @param processLogId 
	 * 
	 * @return the sum ach process by log id
	 * 
	 * @throws DataServiceException 
	 */
	public HashMap<String, BigDecimal> getSumAchProcessByLogId(long processLogId) throws DataServiceException;

	/**
	 * Gets the total credit.
	 * 
	 * @return the total credit
	 * 
	 * @throws DataServiceException 
	 */
	public NachaProcessQueueVO getTotalCredit() throws DataServiceException;

	/**
	 * Insert nacha process history log.
	 * 
	 * @param nachaProcessLogHistoryVO 
	 * 
	 * @return the long
	 * 
	 * @throws DataServiceException 
	 */
	public Long insertNachaProcessHistoryLog(NachaProcessLogHistoryVO nachaProcessLogHistoryVO) throws DataServiceException;

	/**
	 * Insert nacha process log.
	 * 
	 * @param nachaProcessLogVO 
	 * 
	 * @return the long
	 * 
	 * @throws DataServiceException 
	 */
	public Long insertNachaProcessLog(NachaProcessLogVO nachaProcessLogVO) throws DataServiceException;

	/**
	 * Orgination process updates for ach procees log.
	 * 
	 * @param nachaProcessLogVO 
	 * @param nachaProcessLogHistoryVO 
	 * 
	 * @throws DataServiceException 
	 */
	public void orginationProcessUpdatesForAchProceesLog(NachaProcessLogVO nachaProcessLogVO, NachaProcessLogHistoryVO nachaProcessLogHistoryVO) throws DataServiceException;

	/**
	 * Orgination process updates for ach procees queue.
	 * 
	 * @param achProcessQueueEntryVO 
	 * 
	 * @throws DataServiceException 
	 */
	public void orginationProcessUpdatesForAchProceesQueue(AchProcessQueueEntryVO achProcessQueueEntryVO) throws DataServiceException;

	/**
	 * Reconcile auto ach ledger entries.
	 * 
	 * @param processStatusId 
	 * 
	 * @throws DataServiceException 
	 */
	public void reconcileAutoAchLedgerEntries(int processStatusId) throws DataServiceException;

	/**
	 * Reconcile credit card auth ledger entry.
	 * 
	 * @param ledgerIds 
	 * 
	 * @throws DataServiceException 
	 */
	public void reconcileCreditCardAuthLedgerEntry(List<Long> ledgerIds) throws DataServiceException;

	/**
	 * Sets the status complete flag.
	 * 
	 * @param transactionBatchVO the new status complete flag
	 * 
	 * @throws DataServiceException 
	 */
	public void setStatusCompleteFlag(TransactionBatchVO transactionBatchVO) throws DataServiceException;


	/**
	 * Update batch balanced indicator.
	 * 
	 * @param processLogId 
	 * @param balancedInd 
	 * 
	 * @throws DataServiceException 
	 */
	public void updateBatchBalancedIndicator(Integer processLogId, Integer balancedInd) throws DataServiceException;

	/**
	 * Update batch status.
	 * 
	 * @param processLogId 
	 * @param statusId 
	 * @param balancedInd 
	 * 
	 * @throws DataServiceException 
	 */
	public void updateBatchStatus(Integer processLogId, Integer statusId, Integer balancedInd) throws DataServiceException;

	/**
	 * Update credit card auth status.
	 * 
	 * @param achProcessQueueVO 
	 * 
	 * @throws DataServiceException 
	 */
	public void updateCreditCardAuthStatus(AchProcessQueueEntryVO achProcessQueueVO) throws DataServiceException;

	/**
	 * Update reconciled data.
	 * 
	 * @param processLogId 
	 * 
	 * @param ledgerEntryId 
	 * 
	 * @return true, if successful
	 * 
	 * @throws DataServiceException 
	 * //SL-20168 : Passing ledger_entry_id
	 */
	public boolean updateReconciledData(Integer processLogId, Long ledgerEntryId) throws DataServiceException;

	/**
	 * Updatet nacha process history log.
	 * 
	 * @param nachaProcessLogHistoryVO 
	 * 
	 * @throws DataServiceException 
	 */
	public void updatetNachaProcessHistoryLog(NachaProcessLogHistoryVO nachaProcessLogHistoryVO) throws DataServiceException;

	/**
	 * Updatet nacha process log.
	 * 
	 * @param nachaProcessLogVO 
	 * 
	 * @throws DataServiceException 
	 */
	public void updatetNachaProcessLog(NachaProcessLogVO nachaProcessLogVO) throws DataServiceException;

	public void achProcessQueueCreatedDateUpdate(AchProcessQueueEntryVO achProcessQueueEntryVO);

	/**
	 * Description:  Updates the unprocessed instant ACH entries to 999 temp status based on created date, and 
	 * process_status_id being 10.
	 * 
	 * @param achProcessQueueVO
	 */
	public void updateAutoAchStatus(AchProcessQueueEntryVO achProcessQueueVO);
	
	
	/**
	 * select transactionType id
	 * 
	 * @param ledgerEntryId 
	 * 
	 * @throws DataServiceException 
	 */
	public Long getAchtransactionTypeId(Long ledgerEntryId) throws DataServiceException;
	
	/**
	 * Gets the unique Ledger entry Id from sequence for consolidated Records while creating NACHA file
	 * 
	 * @return the unique ledger entry Id
	 * 
	 * @throws DataServiceException 
	 */
	
	public Long insertLedgerEntryIdForConCreditCard() throws DataServiceException;

	public HashMap<Integer, Integer> getBuyerExceptionReconciliationMap() throws DataServiceException;
}
