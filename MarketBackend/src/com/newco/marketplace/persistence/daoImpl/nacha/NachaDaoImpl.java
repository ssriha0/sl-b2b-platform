package com.newco.marketplace.persistence.daoImpl.nacha;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.newco.marketplace.dao.impl.MPBaseDaoImpl;
import com.newco.marketplace.dto.vo.ach.AchProcessQueueEntryVO;
import com.newco.marketplace.dto.vo.ach.AchResponseReasonVO;
import com.newco.marketplace.dto.vo.ach.EntityEmailVO;
import com.newco.marketplace.dto.vo.ach.NachaProcessLogHistoryVO;
import com.newco.marketplace.dto.vo.ach.NachaProcessLogVO;
import com.newco.marketplace.dto.vo.ach.NachaProcessQueueVO;
import com.newco.marketplace.dto.vo.ach.ReconciliationViewVO;
import com.newco.marketplace.dto.vo.ach.TransactionBatchVO;
import com.newco.marketplace.dto.vo.ptd.PTDRecordTypeVO;
import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.persistence.iDao.nacha.NachaDao;

public class NachaDaoImpl extends MPBaseDaoImpl implements NachaDao {

	public List<NachaProcessQueueVO> getNachaExtract(HashMap<String,String> userTransMap)
			throws DataServiceException {
		List<NachaProcessQueueVO> nachaprocessqueue = null;

		try {
			nachaprocessqueue = queryForList(
					"nachaExtract.getNachaExtractByBatchId", userTransMap);
		} catch (Exception ex) {

			throw new DataServiceException(
					"Exception occurred in NachaDaoImpl.getNachaExtract()", ex);
		}
		return nachaprocessqueue;

	}

	public void setProcessedFlag(Integer accountId) throws DataServiceException {
		try {
			update("nachaExtract.setProcessStatus", accountId);
		} catch (Exception ex) {
			throw new DataServiceException(
					"Exception occurred in NachaDaoImpl.setProcessedFlag()", ex);
		}
	}

	public void setStatusCompleteFlag(TransactionBatchVO transactionBatchVO)
			throws DataServiceException {
		try {
			update("nacha.setStatusCompleteFlag", transactionBatchVO);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new DataServiceException(
					"Exception occurred in NachaDaoImpl.setStatusCompleteFlag()",
					ex);
		}
	}
	
	public HashMap<String,BigDecimal> getSumAchProcessByLogId(long processLogId)
	throws DataServiceException {
		HashMap<String, BigDecimal> processLogMap= new HashMap<String, BigDecimal>();
		try {
			processLogMap = (HashMap<String,BigDecimal>)queryForObject("nacha.sumAchProcessByLogId", processLogId);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new DataServiceException(
					"Exception occurred in NachaDaoImpl.getSumAchProcessByLogId()",
					ex);
		}
		return processLogMap;
	}
	
	

	public Long insertNachaProcessLog(NachaProcessLogVO nachaProcessLogVO)
			throws DataServiceException {
		Long id = null;

		try {
			id = (Long) insert("achProcessLog.insert", nachaProcessLogVO);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new DataServiceException(
					"Exception occurred in NachaDaoImpl.insertNachaProcessLog()",
					ex);
		}
		return id;
	}

	public void updatetNachaProcessLog(NachaProcessLogVO nachaProcessLogVO)
			throws DataServiceException {
		try {
			update("achProcessLog.update", nachaProcessLogVO);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new DataServiceException(
					"Exception occurred in NachaDaoImpl.updatetNachaProcessLog()",
					ex);
		}

	}

	public Long insertNachaProcessHistoryLog(
			NachaProcessLogHistoryVO nachaProcessLogHistoryVO)
			throws DataServiceException {
		Long id = null;  
		try {
			id = (Long) insert("achProcessLogHistory.insert",
					nachaProcessLogHistoryVO);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new DataServiceException(
					"Exception occurred in NachaDaoImpl.insertNachaProcessHistoryLog()",
					ex);
		}
		return id;

	}

	public void updatetNachaProcessHistoryLog(
			NachaProcessLogHistoryVO nachaProcessLogHistoryVO)
			throws DataServiceException {
		try {
			update("achProcessLogHistory.update", nachaProcessLogHistoryVO);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new DataServiceException(
					"Exception occurred in NachaDaoImpl.updatetNachaProcessHistoryLog()",
					ex);
		}

	}

	public NachaProcessQueueVO getTotalCredit() throws DataServiceException {
		NachaProcessQueueVO nachaProcessQueueVO = null;
		try {
			nachaProcessQueueVO = (NachaProcessQueueVO) queryForObject(
					"nachaExtract.creditSum", null);
			if (nachaProcessQueueVO == null) {
				throw new DataServiceException(
						"No results returned for NachaDaoImpl.getTotalCredit()");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new DataServiceException(
					"Exception occurred in NachaDaoImpl.getTotalCredit()", ex);
		}
		return nachaProcessQueueVO;
	}

	public void rollBackNachaQueue(TransactionBatchVO transactionBatchVO)
			throws DataServiceException {
		try {
			if (transactionBatchVO != null) {
				if (transactionBatchVO.getTotalCreditProcessId() != 0) {
					delete("achProcessQueueRollBack.delete", transactionBatchVO);
				}
				if (transactionBatchVO.getAchProcessIds() != null
						&& transactionBatchVO.getAchProcessIds().size() > 0) {
					update("achProcessQueueRollBack.update", transactionBatchVO);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new DataServiceException(
					"Exception occurred in NachaDaoImpl.rollBackNachaQueue()",
					ex);
		}

	}

	public void achProcessRerun(TransactionBatchVO transactionBatchVO)
			throws DataServiceException {
		try {
			if (transactionBatchVO != null) {
				if (transactionBatchVO.getProcessLogId() > 0) {
					delete("achProcessRerun.delete", transactionBatchVO);
					update("achProcessRerun.update", transactionBatchVO);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new DataServiceException(
					"Exception occurred in NachaDaoImpl.achProcessRerun()", ex);
		}

	}

	public void orginationProcessUpdatesForAchProceesQueue(
			AchProcessQueueEntryVO achProcessQueueEntryVO)
			throws DataServiceException {
		
		try {
			if (achProcessQueueEntryVO != null) {

				update("origProcessQueue.update", achProcessQueueEntryVO);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new DataServiceException(
					"Exception occurred in NachaDaoImpl.orginationProcessUpdatesForAchProceesQueue()", ex);
		}

	}

	public void orginationProcessUpdatesForAchProceesLog(
			NachaProcessLogVO nachaProcessLogVO,
			NachaProcessLogHistoryVO nachaProcessLogHistoryVO)
			throws DataServiceException {
		
		try {
			if (nachaProcessLogVO != null && nachaProcessLogHistoryVO != null) {
				update("origProcessLog.update", nachaProcessLogVO);
				insertNachaProcessHistoryLog(nachaProcessLogHistoryVO);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new DataServiceException(
					"Exception occurred in NachaDaoImpl.orginationProcessUpdatesForAchProceesLog()", ex);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.marketplace.persistence.iDao.nacha.NachaDao#updateTransactionStatusByLedgerId(java.lang.Integer,
	 *      java.lang.Integer) This method is used to set the reconciled_ind
	 *      column of the ach_process_queue table with the reference of
	 *      ledger_entry_id
	 */
	public void updateTransactionStatusByLedgerId(Integer ledgerEntryId,
			Integer statusId, Integer balancedInd) throws DataServiceException {
		try {
			if (ledgerEntryId == null || statusId == null) {
				throw new DataServiceException(
						"Ledger Entry Id/Status Id can not be null");
			}
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put("ledger_entry_id", ledgerEntryId);
			map.put("status_id", statusId);
			map.put("balanced_ind", balancedInd);
			update("reconciledByLedgerId.update", map);

		} catch (Exception e) {
			e.printStackTrace();
			throw new DataServiceException(
					"Exception occured in NachaDaoImpl.updateTransactionStatus");
		}

	}

	public Long getAchProcessId(long ledgerEntryId) throws DataServiceException {
		Long id = null;
		try {
			id = (Long) queryForObject("processIdForledgerId.query", new Long(ledgerEntryId));
		} catch (Exception ex) {
			throw new DataServiceException("Exception occurred in NachaDaoImpl.getAchProcessId()", ex);
		}
		return id;
	}

	public void updateBatchStatus(Integer processLogId, Integer statusId,
			Integer balancedInd) throws DataServiceException {
		try {
			if (processLogId == null || statusId == null) {
				throw new DataServiceException(
						"Nacha Process Log Id/Status Id can not be null");
			}
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put("process_log_id", processLogId);
			map.put("status_id", statusId);
			map.put("balanced_ind", balancedInd);
			update("batchStatus.update", map);

		} catch (Exception e) {
			e.printStackTrace();
			throw new DataServiceException(
					"Exception occured in NachaDaoImpl.updateBatchStatus");
		}
	}

	public void deleteBalancedRecord(Integer processLogId)
			throws DataServiceException {
		try {
			if (processLogId == null) {
				throw new DataServiceException(
						"ACH Process Log Id can not be null");
			}

			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put("transaction_type_id", new Integer(1000));
			map.put("process_log_id", processLogId);

			delete("consolidatedBalanceRecord.delete", map);

		} catch (Exception e) {
			e.printStackTrace();
			throw new DataServiceException(
					"Exception occured in NachadaoImpl.deleteBalancedRecord");
		}

	}

	public Integer getProcessLogCount(Date currentDate)
			throws DataServiceException {
		Integer iCount = new Integer(0);
		try {
			iCount = (Integer) queryForObject("achPorcessLogCount.query",
					currentDate);

		} catch (Exception e) {
			throw new DataServiceException(
					"Exception occured in NachaDaoImpl.getProcessLogCount", e);
		}
		return iCount;
	}

	public AchResponseReasonVO getReasonInfo(String reasonCode,
			Integer fileTypeId) throws DataServiceException {
		AchResponseReasonVO reasonVO = new AchResponseReasonVO();
		reasonVO.setReasonCode(reasonCode);
		reasonVO.setFileTypeId(fileTypeId);
		try {
			reasonVO = (AchResponseReasonVO) queryForObject(
					"achReasonInfo.query", reasonVO);

		} catch (Exception e) {
			throw new DataServiceException(
					"Exception occured in NachaDaoImpl.getRejectReasonInfo", e);
		}
		return reasonVO;

	}

	public HashMap<String, AchResponseReasonVO> getAllReasons(Integer fileTypeId)
			throws DataServiceException {
		List<AchResponseReasonVO> reasonVOList = null;
		HashMap<String, AchResponseReasonVO> rejectReasonHash = null;
		try {
			reasonVOList = (List<AchResponseReasonVO>) queryForList(
					"achAllReasonInfo.query", fileTypeId);
			if (reasonVOList != null && reasonVOList.size() > 0) {
				rejectReasonHash = getRejectReasonHash(reasonVOList);

			}
		} catch (Exception e) {
			throw new DataServiceException(
					"Exception occured in NachaDaoImpl.getAllRejectReasonInfo",
					e);
		}
		return rejectReasonHash;

	}

	private HashMap<String, AchResponseReasonVO> getRejectReasonHash(
			List<AchResponseReasonVO> rejectReasonList) {
		HashMap<String, AchResponseReasonVO> reasonHash = null;
		if (rejectReasonList != null && rejectReasonList.size() > 0) {
			reasonHash = new HashMap<String, AchResponseReasonVO>();
			for (int i = 0; i < rejectReasonList.size(); i++) {
				AchResponseReasonVO reasonVO = (AchResponseReasonVO) rejectReasonList
						.get(i);
				String code = reasonVO.getReasonCode();
				reasonHash.put(code, reasonVO);

			}
		}
		return reasonHash;
	}

	public void updateBatchBalancedIndicator(Integer processLogId,
			Integer balancedInd) throws DataServiceException {
		try {
			if (processLogId == null || balancedInd == null) {
				throw new DataServiceException(
						"Nacha Process Log Id/Balanced Indicator should not be null.");
			}
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put("process_log_id", processLogId);
			map.put("balanced_ind", balancedInd);
			update("balancedInd.update", map);

		} catch (Exception e) {
			e.printStackTrace();
			throw new DataServiceException(
					"Exception occured in NachaDaoImpl.updateBatchBalancedIndicator");
		}

	}

	public void adjustConsolidatedRecordOnOrigination(long ledgerEntryId)
			throws DataServiceException {
		try {
			Long batchId = this.getAchProcessId(ledgerEntryId);
			HashMap sumWithdrawalsHash = new HashMap();
			sumWithdrawalsHash.put("batchId", batchId);
			sumWithdrawalsHash.put("transTypeId", new Integer(800));

			HashMap sumDepositsHash = new HashMap();
			sumDepositsHash.put("batchId", batchId);
			sumDepositsHash.put("transTypeId", new Integer(100));

			Double withdrawalsSum = (Double) queryForObject(
					"sumOfRejectedEntries.query", sumWithdrawalsHash);
			Double depositsSum = (Double) queryForObject(
					"sumOfRejectedEntries.query", sumDepositsHash);
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
			throw new DataServiceException(
					"Exception occured in NachaDaoImpl.preProcessCheck", e);
		}
	}

	public void achProcessLogStatusUpdate(long ledgerId)
			throws DataServiceException {
		try {
			update("achProcessLogReturn.update", ledgerId);
		} catch (Exception e) {
			throw new DataServiceException(
					"Exception occured in NachaDaoImpl.achProcessLogStatusUpdate",
					e);
		}

	}

	public void achProcessQueueReturnUpdate(AchProcessQueueEntryVO achProcessQueueEntryVO)
			throws DataServiceException {

		try {
			// 1. Updating ach_process_queue table
			
			update("origProcessQueue.update", achProcessQueueEntryVO);

			// We don't update ach_process_log and history table as in origination because these tables have entries based on a process for file level like when nacha and origination process ran for whole file,
			// and return entries are for individual entries in nacha file not the whole file
			
		} catch (Exception e) {
			throw new DataServiceException(
					"Exception occured in NachaDaoImpl.achProcessQueueStatusUpdate",
					e);
		}

	}

	public void achProcessQueueCreatedDateUpdate(AchProcessQueueEntryVO achProcessQueueEntryVO){
			update("achProcessQueueCreatedDate.update", achProcessQueueEntryVO);
	}
	
	public double achqueueEntryTotal() throws DataServiceException {
		double amt = 0;
		try {
			Object amtObj = queryForObject("achqueueEntryTotal.query", null);
			if(amtObj!=null){
				amt = (Double)amtObj;	
			}
			 
		} catch (Exception e) {
			throw new DataServiceException(
					"Exception occured in NachaDaoImpl.achqueueEntryTotal", e);
		}

		return amt;
	}

	public long accountIdFromLedgerId(long ledgerId) {
		long accountId = 0;
		accountId = (Long)queryForObject("accountIdFromLedgerId.select",ledgerId);
		return accountId;
	}

	public boolean createAchQueueEntry(AchProcessQueueEntryVO queueEntry)
			throws DataAccessException {

		try {

			insert("achqueueEntry.query", queueEntry);
		} catch (DataAccessException de) {
			throw de;

		}

		return true;
	}
	public void changeUnBalancedFlag() throws DataServiceException{

		try {
			update("achUnbalancedFlag.update", null);
		} catch (Exception e) {
			throw new DataServiceException(
					"Exception occured in NachaDaoImpl.achProcessQueueStatusUpdate",
					e);
		}
	}
	
	public Integer batchCountByStatus(Integer processLogId, Integer statusId) throws DataServiceException{
		Integer cnt = 0;
		try{
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put("process_log_id", processLogId);
			map.put("status_id", statusId);
			cnt = (Integer)queryForObject("processLogId.count", map);
			if(cnt == null){
				cnt = 0;
			}
			
		}catch(DataAccessException dse){
			throw new DataServiceException("Exception occured in NachaDaoImpl.batchCountByStatus", dse);
		}
		return cnt;
	}
   	public List<NachaProcessQueueVO> getAllUnbalancedTransactions(int batchId) throws DataServiceException {
   	   	List<NachaProcessQueueVO> nachaprocessqueue=null;

   			try {
   				nachaprocessqueue = queryForList("allUnbalancedAchQueue.query",new Integer(batchId));
   	      }
   	       catch (Exception ex) {
   	       	
   	           throw new DataServiceException("Exception occurred in NachaDaoImpl.getAllUnbalancedTransactions()", ex);
   	       }
   	       return nachaprocessqueue;
   	       
   	   }
   	
   	public long getLedgerIdFromDateTime(String createdDateTime)	throws DataServiceException {
   		Long processLogId = null;
		try {
			processLogId = (Long)queryForObject("ledgerIdFromDateTime.select",createdDateTime);
		} 
		catch (Exception e) {
				throw new DataServiceException(
						"Exception occured in NachaDaoImpl.achProcessQueueStatusUpdate",e);
		}
		return processLogId != null?processLogId.longValue():0;
   	}
	public Integer getProviderEntityId(Long ledgerId){
		Integer id = (Integer) queryForObject("headerDetails.query", ledgerId);
		return id;

	}
	
	public boolean getOriginationNotificationInd() throws DataServiceException {
	
		boolean orgInd = true;
		try
		{
			Integer count = (Integer)queryForObject("countRecordsSent.select",null);
			if(count <= 0)
				orgInd = false;
				
		}
		catch (Exception e) {
			throw new DataServiceException(
					"Exception occured in NachaDaoImpl.getOriginationNotificationInd",e);
		}
		return orgInd;
	}
	
	public AchResponseReasonVO getReasonCodeDetails(Integer reasonId) 
		throws DataServiceException{
		AchResponseReasonVO reasonCodesVO = null;
		try{
			reasonCodesVO = (AchResponseReasonVO)queryForObject("reasonCodeDetailByCode.select", reasonId);
		}catch(Exception e){
			throw new DataServiceException(
					"Exception occured in NachaDaoImpl.getReasonCodeDetails", e);
		}
		return reasonCodesVO;
	}
	
	public AchProcessQueueEntryVO getAchProcessDataFromLedgerId(Long ledgerEntryId)
	throws DataServiceException{
		
	try{
		return (AchProcessQueueEntryVO)queryForObject("achProcessRecordByLedgerId.select", ledgerEntryId);
	}catch(Exception e){
		throw new DataServiceException(
				"Exception occured in NachaDaoImpl.getAchProcessDataFromLedgerId", e);
	}
	
}
	
	public EntityEmailVO getContactEmailFromLedgerEntry(Long ledgerEntryId)
		throws DataServiceException{
		EntityEmailVO entityEmailVO = null;
		try{
			entityEmailVO = (EntityEmailVO)queryForObject("contactEmailByLedgerId.select", ledgerEntryId);
		}catch(Exception e){
			throw new DataServiceException(
					"Exception occured in NachaDaoImpl.getContactEmailFromLedgerEntry", e);
		}
		return entityEmailVO;
	}

   	public List<PTDRecordTypeVO> getPTDRecordTypeTemplate(String recordType) throws DataServiceException {
   	   	List<PTDRecordTypeVO> ptdRecordType=null;

   			try {
   				ptdRecordType = queryForList("ptdRecordTypeQuery.select",recordType);
   	      }
   	       catch (Exception ex) {
   	           throw new DataServiceException("Exception occurred in NachaDaoImpl.getPTDRecordTypeTemplate()", ex);
   	       }
   	       return ptdRecordType;
   	       
   	   }

	public double getCreditCardConsolidationAmount(
			AchProcessQueueEntryVO achProcessQueueVO) {
		double consolidatedAmt = 0;
		consolidatedAmt = (Double)queryForObject("CCConsolidationAmount.select", achProcessQueueVO);
		return consolidatedAmt;
	}
	
	public List<AchProcessQueueEntryVO> getAutoAchConsolidationAmount(AchProcessQueueEntryVO achProcessQueueVO) {
		
		List<AchProcessQueueEntryVO> consolidatedEntryList = queryForList("AutoAchConsolidationAmount.select", achProcessQueueVO);
		return consolidatedEntryList;
	}
	
	public void updateCreditCardAuthStatus(
			AchProcessQueueEntryVO achProcessQueueVO) {
		update("CCAuthReconcilation.update", achProcessQueueVO);
		
	}
	
	public List<Long> getLedgerIdsForNachaProcess(AchProcessQueueEntryVO achProcessQueueVO){
		List<Long> ledgerIds = new ArrayList<Long>();
		ledgerIds = queryForList("creditCardAuthLedgerId.select", achProcessQueueVO);
		return ledgerIds;
	}
	
	public void reconcileCreditCardAuthLedgerEntry(List<Long> ledgerIds){
		update("ledger_entry_reconciledForCreditCard.update", ledgerIds);
	}
	
	/**
	 * Used to retrieve Map containg rejection reason codes and reason description
	 * for withdrawal reversal during ACH reject
	 * @return Map
	 * @throws DataServiceException
	 */	
	public  List<ArrayList> getWithdrawalReversalRejectReasonCodes() throws DataServiceException {
		List<ArrayList> rejectReasonList ;

		try {
			rejectReasonList = getSqlMapClient().queryForList("rejectReasonCodes.select",null);
		} catch (Exception ex) {
			throw new DataServiceException(
					"Exception occurred in NachaDaoImpl.getNachaExtract()", ex);
		}
		return rejectReasonList;

	}
	
	public Date getNachaProcessLastRanDate()
			throws DataServiceException {
		Date nachaProcessRanDate = null;
		try {

			nachaProcessRanDate = (java.sql.Date) queryForObject(
					"dailyReconciliation.Nacha.lastRan", null);
		} catch (Exception e) {
			throw new DataServiceException(
					"Exception occurred in NachaDaoImpl.getNachaProcessLastRanDate()",
					e);
		}

		return nachaProcessRanDate;
	}

	public Date getNachaProcessLastRanDate(List processStatusIds)
			throws DataServiceException {
		Date nachaProcessRanDate = null;

		try {

			nachaProcessRanDate = (java.sql.Date) queryForObject(
					"dailyReconciliation.Nacha.status.lastRan",
					processStatusIds);

		} catch (Exception e) {
			throw new DataServiceException(
					"Exception occurred in NachaDaoImpl.getNachaProcessLastRanDate()",
					e);
		}

		return nachaProcessRanDate;
	}

	public Date getPTDProcessLastRanDate()
			throws DataServiceException {

		Date ptdProcessRanDate = null;

		try {

			ptdProcessRanDate = (java.sql.Date) queryForObject(
					"dailyReconciliation.ptd.lastRan", null);

		} catch (Exception e) {
			throw new DataServiceException(
					"Exception occurred in NachaDaoImpl.getPTDProcessLastRanDate()",
					e);
		}

		return ptdProcessRanDate;
	}

	public Date getGeneralLedgerProcessLastRanDate()
			throws DataServiceException {
		Date glProcessRanDate = null;

		try {

			glProcessRanDate = (java.sql.Date) queryForObject(
					"dailyReconciliation.GeneralLedger.lastRan", null);

		} catch (Exception e) {
			throw new DataServiceException(
					"Exception occurred in NachaDaoImpl.getGeneralLedgerProcessLastRanDate()",
					e);
		}

		return glProcessRanDate;

	}

	public Date getTransProcessLastRanDate()
			throws DataServiceException {
		Date transProcessLastRanDate = null;

		try {

			transProcessLastRanDate = (java.sql.Date) queryForObject(
					"dailyReconciliation.Tran.lastRan", null);

		} catch (Exception e) {
			throw new DataServiceException(
					"Exception occurred in NachaDaoImpl.getTransProcessLastRanDate()",
					e);
		}

		return transProcessLastRanDate;

	}
	
	public ReconciliationViewVO getTransProcessView(int transactionTypeId) throws DataServiceException{
		ReconciliationViewVO reconciliationViewVO = new ReconciliationViewVO();

		try {

			reconciliationViewVO = (ReconciliationViewVO) queryForObject(
					"dailyReconciliation.transfile.view", new Integer(transactionTypeId));

		} catch (Exception e) {
			throw new DataServiceException(
					"Exception occurred in NachaDaoImpl.getTransProcessView()",
					e);
		}

		return reconciliationViewVO;
	}
	
	public ReconciliationViewVO getNachaProcessView(int transactionTypeId, int entityTypeId) throws DataServiceException{
		
		ReconciliationViewVO reconciliationViewVO = new ReconciliationViewVO();

		try {
			Map paramMap = new HashMap<String, Integer>();
			paramMap.put("transactionTypeId", new Integer(transactionTypeId));
			paramMap.put("entityTypeId", new Integer(entityTypeId));
			reconciliationViewVO = (ReconciliationViewVO) queryForObject(
					"dailyReconciliation.Nacha.view", paramMap);

		} catch (Exception e) {
			throw new DataServiceException(
					"Exception occurred in NachaDaoImpl.getNachaProcessView()",
					e);
		}

		return reconciliationViewVO;
		
	}
	
	public ReconciliationViewVO getNachaProcessViewForConsolidatedAutoAchEntry() throws DataServiceException{
		
		ReconciliationViewVO reconciliationViewVO = new ReconciliationViewVO();

		try {
			reconciliationViewVO = (ReconciliationViewVO) queryForObject(
					"dailyReconciliation.AutoAchConsolidated.Nacha.view",null);

		} catch (Exception e) {
			throw new DataServiceException(
					"Exception occurred in NachaDaoImpl.getNachaProcessViewForConsolidatedAutoAchEntry()",
					e);
		}

		return reconciliationViewVO;
		
	}
	
	public void updateAchProcessAcctIdForDisabledAccts() throws DataServiceException
	{
		try {
			update("nacha.updateAchProcessAcIdForDisabledAccts",null);
					

		} catch (Exception e) {
			throw new DataServiceException(
					"Exception occurred in NachaDaoImpl.updateAchProcessAcctIdForDisabledAccts()",
					e);
		}
		
	}
	public Date getAckOrigStatusLastRan(Integer processStatusId) throws DataServiceException
	{
		Date lastRanDate = null;
		try {
			 lastRanDate = (java.sql.Date) queryForObject(
					"dailyReconciliation.AckOrg.status.lastRan",processStatusId);
		}
		 catch (Exception e) {
				throw new DataServiceException(
						"Exception occurred in NachaDaoImpl.getAckOrigStatusLastRan()",
						e);
		}
		 return lastRanDate;
	}
}
