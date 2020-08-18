package com.servicelive.wallet.batch.ach.dao;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.servicelive.common.ABaseDao;
import com.servicelive.common.CommonConstants;
import com.servicelive.common.exception.DataServiceException;
import com.servicelive.wallet.ach.vo.AchProcessQueueEntryVO;
import com.servicelive.wallet.batch.ach.vo.AchResponseReasonVO;
import com.servicelive.wallet.batch.ach.vo.NachaProcessLogHistoryVO;
import com.servicelive.wallet.batch.ach.vo.NachaProcessLogVO;
import com.servicelive.wallet.batch.ach.vo.NachaProcessQueueVO;
import com.servicelive.wallet.batch.trans.vo.TransactionBatchVO;

// TODO: Auto-generated Javadoc
/**
 * The Class NachaDao.
 */
public class NachaDao extends ABaseDao implements INachaDao {

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(NachaDao.class);

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.ach.dao.INachaDao#accountIdFromLedgerId(long)
	 */
	public long accountIdFromLedgerId(long ledgerId) {

		long accountId = 0;
		accountId = (Long) queryForObject("accountIdFromLedgerId.select", ledgerId);
		return accountId;
	}

	public long entityIdFromLedgerId(long ledgerId)
	
    {
    	long entityId = 0;
    	entityId = (Long) queryForObject("entityIdFromLedgerId.select", ledgerId);
		return entityId;
    	
    }
	
	public long busTransIdFromLedgerId(long ledgerId){
		long busTransId = 0;
		busTransId = (Long) queryForObject("busTransIdFromLedgerId.select",
				ledgerId);
		return busTransId;
	}
	
	public long entityTypeIdFromLedgerId(long ledgerId)
	{
		long entityTypeId=0;
		entityTypeId= (Long) queryForObject("entityTypeIdFromLedgerId.select", ledgerId);
		return entityTypeId;
	}
	
	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.ach.dao.INachaDao#achProcessQueueReturnUpdate(com.servicelive.wallet.ach.vo.AchProcessQueueEntryVO)
	 */
	public void achProcessQueueReturnUpdate(AchProcessQueueEntryVO achProcessQueueEntryVO) throws DataServiceException {

		try {
			// 1. Updating ach_process_queue table

			update("origProcessQueue.update", achProcessQueueEntryVO);

			// We don't update ach_process_log and history table as in origination because these tables have entries based on a process for file level like when nacha and origination process ran for whole file,
			// and return entries are for individual entries in nacha file not the whole file

		} catch (Exception e) {
			throw new DataServiceException("Exception occured in NachaDao.achProcessQueueStatusUpdate", e);
		}

	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.ach.dao.INachaDao#achProcessRerun(com.servicelive.wallet.batch.trans.vo.TransactionBatchVO)
	 */
	public void achProcessRerun(TransactionBatchVO transactionBatchVO) throws DataServiceException {

		try {
			if (transactionBatchVO != null && transactionBatchVO.getProcessLogId() > 0) {
				delete("achProcessRerun.delete", transactionBatchVO);
				update("achProcessRerun.update", transactionBatchVO);
			}
		} catch (Exception ex) {
			throw new DataServiceException("Exception occurred in NachaDao.achProcessRerun()", ex);
		}

	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.ach.dao.INachaDao#adjustConsolidatedRecordOnOrigination(long)
	 */
	@SuppressWarnings("unchecked")
	public void adjustConsolidatedRecordOnOrigination(long ledgerEntryId) throws DataServiceException {

		try {
			Long batchId = this.getAchProcessId(ledgerEntryId);
			HashMap sumWithdrawalsHash = new HashMap();
			sumWithdrawalsHash.put("batchId", batchId);
			sumWithdrawalsHash.put("transTypeId", 800);

			HashMap sumDepositsHash = new HashMap();
			sumDepositsHash.put("batchId", batchId);
			sumDepositsHash.put("transTypeId", 100);

			Double withdrawalsSum = (Double) queryForObject("sumOfRejectedEntries.query", sumWithdrawalsHash);
			Double depositsSum = (Double) queryForObject("sumOfRejectedEntries.query", sumDepositsHash);
			Double sum = 0.0;
			if (withdrawalsSum != null) {
				sum = withdrawalsSum;
			}
			if (depositsSum != null) {
				sum = sum - depositsSum.doubleValue();
			}
			HashMap sumHash = new HashMap();
			sumHash.put("amount", sum);
			sumHash.put("batchId", batchId);
			update("consolidatedTransAmount.update", sumHash);
		} catch (Exception e) {
			throw new DataServiceException("Exception occured in NachaDao.adjustConsolidatedRecordOnOrigination", e);
		}
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.ach.dao.INachaDao#deleteBalancedRecord(java.lang.Integer)
	 */
	public void deleteBalancedRecord(Integer processLogId) throws DataServiceException {

		try {
			if (processLogId == null) {
				throw new DataServiceException("ACH Process Log Id can not be null");
			}

			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put("transaction_type_id", 1000);
			map.put("process_log_id", processLogId);

			delete("consolidatedBalanceRecord.delete", map);

		} catch (Exception e) {
			throw new DataServiceException("Exception occured in NachaDao.deleteBalancedRecord",e);
		}

	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.ach.dao.INachaDao#getAchProcessDataFromLedgerId(java.lang.Long)
	 */
	public AchProcessQueueEntryVO getAchProcessDataFromLedgerId(Long ledgerEntryId) throws DataServiceException {

		try {
			return (AchProcessQueueEntryVO) queryForObject("achProcessRecordByLedgerId.select", ledgerEntryId);
		} catch (Exception e) {
			throw new DataServiceException("Exception occured in NachaDao.getAchProcessDataFromLedgerId", e);
		}

	}


	public Long getAchtransactionTypeId(Long ledgerEntryId) throws DataServiceException {
        
		Long transactionTypeId = null;
		
		try {
			transactionTypeId = (Long) queryForObject("achTransactionTypeIdByLedgerId.select", ledgerEntryId);
			return transactionTypeId;
		} catch (Exception e) {
			throw new DataServiceException("Exception occured in NachaDao.getAchtransactionTypeId", e);
		}
      
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.ach.dao.INachaDao#getAchProcessId(long)
	 */
	public Long getAchProcessId(long ledgerEntryId) throws DataServiceException {

		Long id = null;
		try {
			id = (Long) queryForObject("processIdForledgerId.query", ledgerEntryId);
			return id.longValue();
		} catch (Exception ex) {
			throw new DataServiceException("Exception occurred in NachaDao.getAchProcessId() for ledger_entry_id: " + ledgerEntryId, ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.ach.dao.INachaDao#getAllReasons(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, AchResponseReasonVO> getAllReasons(Integer fileTypeId) throws DataServiceException {

		List<AchResponseReasonVO> reasonVOList = null;
		HashMap<String, AchResponseReasonVO> rejectReasonHash = null;
		try {
			reasonVOList = (List<AchResponseReasonVO>) queryForList("achAllReasonInfo.query", fileTypeId);
			if (reasonVOList != null && !reasonVOList.isEmpty()) {
				rejectReasonHash = getRejectReasonHash(reasonVOList);

			}
		} catch (Exception e) {
			throw new DataServiceException("Exception occured in NachaDao.getAllRejectReasonInfo", e);
		}
		return rejectReasonHash;

	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.ach.dao.INachaDao#getAutoAchConsolidationAmount(int)
	 */
	@SuppressWarnings("unchecked")
	public List<AchProcessQueueEntryVO> getAutoAchConsolidationAmount(int processStatusId) {

		return (List<AchProcessQueueEntryVO>) queryForList("AutoAchConsolidationAmount.select", processStatusId);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.ach.dao.INachaDao#getCreditCardConsolidationAmount(com.servicelive.wallet.ach.vo.AchProcessQueueEntryVO)
	 */
	public double getCreditCardConsolidationAmount(AchProcessQueueEntryVO achProcessQueueVO) {

		double consolidatedAmt = 0;
		consolidatedAmt = (Double) queryForObject("CCConsolidationAmount.select", achProcessQueueVO);
		return consolidatedAmt;
	}
	
	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.ach.dao.INachaDao#getLedgerIdFromDateTime(java.lang.String)
	 */
	public long getProcessIdFromDateTime(String createdDateTime) throws DataServiceException {

		Long processLogId = null;
		try {
			processLogId = (Long) queryForObject("ledgerIdFromDateTime.select", createdDateTime);
		} catch (Exception e) {
			throw new DataServiceException("Exception occured in NachaDao.getProcessIdFromDateTime", e);
		}
		return processLogId == null ? 0 : processLogId.longValue();
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.ach.dao.INachaDao#getLedgerIdsForNachaProcess(com.servicelive.wallet.ach.vo.AchProcessQueueEntryVO)
	 */
	@SuppressWarnings("unchecked")
	public List<Long> getLedgerIdsForNachaProcess(AchProcessQueueEntryVO achProcessQueueVO) {

		List<Long> ledgerIds = new ArrayList<Long>();
		ledgerIds = queryForList("creditCardAuthLedgerId.select", achProcessQueueVO);
		return ledgerIds;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.ach.dao.INachaDao#getNachaExtract(java.util.HashMap)
	 */
	@SuppressWarnings("unchecked")
	public List<NachaProcessQueueVO> getNachaExtract(HashMap<String, String> userTransMap) throws DataServiceException {

		List<NachaProcessQueueVO> nachaprocessqueue = null;

		try {
			nachaprocessqueue = queryForList("nachaExtract.getNachaExtractByBatchId", userTransMap);
		} catch (Exception ex) {

			throw new DataServiceException("Exception occurred in NachaDao.getNachaExtract()", ex);
		}
		return nachaprocessqueue;

	}


	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.ach.dao.INachaDao#getProcessLogCount(java.sql.Date)
	 */
	public Integer getProcessLogCount(Date currentDate) throws DataServiceException {

		Integer iCount = 0;
		try {
			iCount = (Integer) queryForObject("achProcessLogCount.query", currentDate);

		} catch (Exception e) {
			throw new DataServiceException("Exception occured in NachaDao.getProcessLogCount", e);
		}
		return iCount;
	}


	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.ach.dao.INachaDao#getReasonCodeDetails(java.lang.Integer)
	 */
	public AchResponseReasonVO getReasonCodeDetails(Integer reasonId) throws DataServiceException {

		AchResponseReasonVO reasonCodesVO = null;
		try {
			reasonCodesVO = (AchResponseReasonVO) queryForObject("reasonCodeDetailByCode.select", reasonId);
		} catch (Exception e) {
			throw new DataServiceException("Exception occured in NachaDao.getReasonCodeDetails", e);
		}
		return reasonCodesVO;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.ach.dao.INachaDao#getReconciledData()
	 */
	@SuppressWarnings("unchecked")
	public List<NachaProcessQueueVO> getReconciledData() throws DataServiceException {

		List<NachaProcessQueueVO> list = new ArrayList<NachaProcessQueueVO>();

		try {
			list = (List<NachaProcessQueueVO>) queryForList("reconciledScheduler.query", CommonConstants.ORIGINATION_FILE_PROCESSED);

		} catch (Exception e) {
			logger.error("NachaDao.getReconciledSchedularData", e);
			throw new DataServiceException("Error", e);
		}
		return list;
	}

	/**
	 * Gets the reject reason hash.
	 * 
	 * @param rejectReasonList 
	 * 
	 * @return the reject reason hash
	 */
	private HashMap<String, AchResponseReasonVO> getRejectReasonHash(List<AchResponseReasonVO> rejectReasonList) {

		HashMap<String, AchResponseReasonVO> reasonHash = null;
		if (rejectReasonList != null && !rejectReasonList.isEmpty()) {
			reasonHash = new HashMap<String, AchResponseReasonVO>();
			for (int i = 0; i < rejectReasonList.size(); i++) {
				AchResponseReasonVO reasonVO = (AchResponseReasonVO) rejectReasonList.get(i);
				String code = reasonVO.getReasonCode();
				reasonHash.put(code, reasonVO);

			}
		}
		return reasonHash;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.ach.dao.INachaDao#getSumAchProcessByLogId(long)
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, BigDecimal> getSumAchProcessByLogId(long processLogId) throws DataServiceException {

		HashMap<String, BigDecimal> processLogMap = new HashMap<String, BigDecimal>();
		try {
			processLogMap = (HashMap<String, BigDecimal>) queryForObject("nacha.sumAchProcessByLogId", processLogId);
		} catch (Exception ex) {
			throw new DataServiceException("Exception occurred in NachaDao.getSumAchProcessByLogId()", ex);
		}
		return processLogMap;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.ach.dao.INachaDao#getTotalCredit()
	 */
	public NachaProcessQueueVO getTotalCredit() throws DataServiceException {

		NachaProcessQueueVO nachaProcessQueueVO = null;
		try {
			nachaProcessQueueVO = (NachaProcessQueueVO) queryForObject("nachaExtract.creditSum", null);
			if (nachaProcessQueueVO == null) {
				throw new DataServiceException("No results returned for NachaDao.getTotalCredit()");
			}
		} catch (Exception ex) {
			throw new DataServiceException("Exception occurred in NachaDao.getTotalCredit()", ex);
		}
		return nachaProcessQueueVO;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.ach.dao.INachaDao#insertNachaProcessHistoryLog(com.servicelive.wallet.batch.ach.vo.NachaProcessLogHistoryVO)
	 */
	public Long insertNachaProcessHistoryLog(NachaProcessLogHistoryVO nachaProcessLogHistoryVO) throws DataServiceException {

		Long id = null;
		try {
			id = (Long) insert("achProcessLogHistory.insert", nachaProcessLogHistoryVO);
		} catch (Exception ex) {
			throw new DataServiceException("Exception occurred in NachaDao.insertNachaProcessHistoryLog()", ex);
		}
		return id;

	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.ach.dao.INachaDao#insertNachaProcessLog(com.servicelive.wallet.batch.ach.vo.NachaProcessLogVO)
	 */
	public Long insertNachaProcessLog(NachaProcessLogVO nachaProcessLogVO) throws DataServiceException {

		Long id = null;

		try {
			id = (Long) insert("achProcessLog.insert", nachaProcessLogVO);
		} catch (Exception ex) {
			throw new DataServiceException("Exception occurred in NachaDao.insertNachaProcessLog()", ex);
		}
		return id;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.ach.dao.INachaDao#orginationProcessUpdatesForAchProceesLog(com.servicelive.wallet.batch.ach.vo.NachaProcessLogVO, com.servicelive.wallet.batch.ach.vo.NachaProcessLogHistoryVO)
	 */
	public void orginationProcessUpdatesForAchProceesLog(NachaProcessLogVO nachaProcessLogVO, NachaProcessLogHistoryVO nachaProcessLogHistoryVO) throws DataServiceException {

		try {
			if (nachaProcessLogVO != null && nachaProcessLogHistoryVO != null) {
				update("origProcessLog.update", nachaProcessLogVO);
				insertNachaProcessHistoryLog(nachaProcessLogHistoryVO);
			}
		} catch (Exception ex) {
			throw new DataServiceException("Exception occurred in NachaDao.orginationProcessUpdatesForAchProceesLog()", ex);
		}

	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.ach.dao.INachaDao#orginationProcessUpdatesForAchProceesQueue(com.servicelive.wallet.ach.vo.AchProcessQueueEntryVO)
	 */
	public void orginationProcessUpdatesForAchProceesQueue(AchProcessQueueEntryVO achProcessQueueEntryVO) throws DataServiceException {

		try {
			if (achProcessQueueEntryVO != null) {

				update("origProcessQueue.update", achProcessQueueEntryVO);
			}
		} catch (Exception ex) {
			throw new DataServiceException("Exception occurred in NachaDao.orginationProcessUpdatesForAchProceesQueue()", ex);
		}

	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.ach.dao.INachaDao#reconcileAutoAchLedgerEntries(int)
	 */
	public void reconcileAutoAchLedgerEntries(int processStatusId) throws DataServiceException {

		update("ledger_entry_reconciledForAutoAch.update", processStatusId);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.ach.dao.INachaDao#reconcileCreditCardAuthLedgerEntry(java.util.List)
	 */
	public void reconcileCreditCardAuthLedgerEntry(List<Long> ledgerIds) {

		update("ledger_entry_reconciledForCreditCard.update", ledgerIds);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.ach.dao.INachaDao#setStatusCompleteFlag(com.servicelive.wallet.batch.trans.vo.TransactionBatchVO)
	 */
	public void setStatusCompleteFlag(TransactionBatchVO transactionBatchVO) throws DataServiceException {

		try {
			update("nacha.setStatusCompleteFlag", transactionBatchVO);
		} catch (Exception ex) {
			throw new DataServiceException("Exception occurred in NachaDao.setStatusCompleteFlag()", ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.ach.dao.INachaDao#updateAutoAchStatus(int, int, int)
	 */
//	public void updateAutoAchStatus(int newProcessStatusId, int oldProcessStatusId, int reconciledIndicator, Date createdDate) throws DataServiceException {
	public void updateAutoAchStatus(AchProcessQueueEntryVO achProcessQueueVO){		
		
//			Map<String, Object> parameter = new HashMap<String, Object>();
//			parameter.put("newProcessStatusId", newProcessStatusId);
//			parameter.put("oldProcessStatusId", oldProcessStatusId);
//			parameter.put("reconciledIndicator", reconciledIndicator);
//			parameter.put("createdDate", createdDate.toString());

			update("AutoAchStatus.update", achProcessQueueVO);
	}
	
	public void achProcessQueueCreatedDateUpdate(AchProcessQueueEntryVO achProcessQueueEntryVO){
		update("achProcessQueueCreatedDate.update", achProcessQueueEntryVO);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.ach.dao.INachaDao#updateBatchBalancedIndicator(java.lang.Integer, java.lang.Integer)
	 */
	public void updateBatchBalancedIndicator(Integer processLogId, Integer balancedInd) throws DataServiceException {

		try {
			if (processLogId == null || balancedInd == null) {
				throw new DataServiceException("Nacha Process Log Id/Balanced Indicator should not be null.");
			}
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put("process_log_id", processLogId);
			map.put("balanced_ind", balancedInd);
			update("balancedInd.update", map);

		} catch (Exception e) {
			throw new DataServiceException("Exception occured in NachaDao.updateBatchBalancedIndicator",e);
		}

	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.ach.dao.INachaDao#updateBatchStatus(java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	public void updateBatchStatus(Integer processLogId, Integer statusId, Integer balancedInd) throws DataServiceException {

		try {
			if (processLogId == null || statusId == null) {
				throw new DataServiceException("Nacha Process Log Id/Status Id can not be null");
			}
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put("process_log_id", processLogId);
			map.put("status_id", statusId);
			map.put("balanced_ind", balancedInd);
			update("batchStatus.update", map);

		} catch (Exception e) {
			throw new DataServiceException("Exception occured in NachaDao.updateBatchStatus",e);
		}
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.ach.dao.INachaDao#updateCreditCardAuthStatus(com.servicelive.wallet.ach.vo.AchProcessQueueEntryVO)
	 */
	public void updateCreditCardAuthStatus(AchProcessQueueEntryVO achProcessQueueVO) {

		update("CCAuthReconcilation.update", achProcessQueueVO);

	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.ach.dao.INachaDao#updateReconciledData(java.lang.Integer)
	 */
	public boolean updateReconciledData(Integer processLogId, Long ledgerEntryId) throws DataServiceException {

		boolean flag = false;
		//SL-20168 : Passing ledger_entry_id
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("statusSuccess", CommonConstants.ACH_RECORD_SUCCESSFULLY_PROCESSED);
		map.put("StatusComplete", CommonConstants.ORIGINATION_FILE_PROCESSED);
		map.put("reconsiledInd", CommonConstants.ACH_RECONCILED);
		map.put("processLogId", processLogId);
		map.put("ledgerEntryId", ledgerEntryId);
		try {
			update("reconciledScheduler.update", map);
			flag = true;
		} catch (Exception e) {
			logger.error("NachaDao.updateReconciledData", e);
			throw new DataServiceException("Error", e);
		}
		return flag;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.ach.dao.INachaDao#updatetNachaProcessHistoryLog(com.servicelive.wallet.batch.ach.vo.NachaProcessLogHistoryVO)
	 */
	public void updatetNachaProcessHistoryLog(NachaProcessLogHistoryVO nachaProcessLogHistoryVO) throws DataServiceException {

		try {
			update("achProcessLogHistory.update", nachaProcessLogHistoryVO);
		} catch (Exception ex) {
			throw new DataServiceException("Exception occurred in NachaDao.updatetNachaProcessHistoryLog()", ex);
		}

	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.ach.dao.INachaDao#updatetNachaProcessLog(com.servicelive.wallet.batch.ach.vo.NachaProcessLogVO)
	 */
	public void updatetNachaProcessLog(NachaProcessLogVO nachaProcessLogVO) throws DataServiceException {

		try {
			update("achProcessLog.update", nachaProcessLogVO);
		} catch (Exception ex) {
			throw new DataServiceException("Exception occurred in NachaDao.updatetNachaProcessLog()", ex);
		}

	}	
    public Long insertLedgerEntryIdForConCreditCard() throws DataServiceException {
        logger.debug("NachDAO-->insertLedgerEntryIdForConCreditCard()-->START");
		try {
            return ((Long)insert("consolidatedLedgerEntryId.insert", null)).longValue();
		} catch (Exception ex) {
            logger.error("NachDAO-->insertLedgerEntryIdForConCreditCard-->EXCEPTION-->", ex);
            throw new DataServiceException("NachDAO-->insertLedgerEntryIdForConCreditCard-->EXCEPTION", ex);
		}
	}
    
	@SuppressWarnings("unchecked")
	public HashMap<Integer, Integer> getBuyerExceptionReconciliationMap() throws DataServiceException {

		HashMap<Integer, Integer> resultMap = new HashMap<Integer, Integer>();
		try {
			resultMap = (HashMap<Integer, Integer>) queryForMap("buyerExceptionReconciliationList.query",null,"entityId","reconDays");
		} catch (Exception ex) {
			throw new DataServiceException("Exception occurred in NachaDao.getBuyerExceptionReconciliationMap()", ex);
		}
		return resultMap;
	}
}
