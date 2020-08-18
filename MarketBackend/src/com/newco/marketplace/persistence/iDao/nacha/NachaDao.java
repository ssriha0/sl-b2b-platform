package com.newco.marketplace.persistence.iDao.nacha;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.dao.DataAccessException;

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


public interface NachaDao  {

	
	public List<NachaProcessQueueVO> getNachaExtract(HashMap<String,String> userTransMap) throws DataServiceException;
    public void setProcessedFlag(Integer accountId) throws DataServiceException;
    public void setStatusCompleteFlag(TransactionBatchVO transactionBatchVO) throws DataServiceException;
    public Long insertNachaProcessLog(NachaProcessLogVO nachaProcessLogVO) throws DataServiceException;
    public void updatetNachaProcessLog(NachaProcessLogVO nachaProcessLogVO) throws DataServiceException;
    public NachaProcessQueueVO getTotalCredit() throws DataServiceException;
    public Long insertNachaProcessHistoryLog(NachaProcessLogHistoryVO nachaProcessLogHistoryVO) throws DataServiceException;
    public void updatetNachaProcessHistoryLog(NachaProcessLogHistoryVO nachaProcessLogHistoryVO) throws DataServiceException;
    public void rollBackNachaQueue(TransactionBatchVO transactionBatchVO) throws DataServiceException;
    public void achProcessRerun(TransactionBatchVO transactionBatchVO) throws DataServiceException;
    public void updateTransactionStatusByLedgerId(Integer ledgerEntryId, Integer statusId, Integer balancedInd) throws DataServiceException;
    public Integer getProcessLogCount(Date currentDate) throws DataServiceException;
    public void updateBatchStatus(Integer processLogId, Integer statusId, Integer balancedInd) throws DataServiceException;
    public void deleteBalancedRecord(Integer processLogId) throws DataServiceException;
    public void orginationProcessUpdatesForAchProceesQueue(AchProcessQueueEntryVO achProcessQueueEntryVO) throws DataServiceException;
	public void orginationProcessUpdatesForAchProceesLog(NachaProcessLogVO nachaProcessLogVO,NachaProcessLogHistoryVO nachaProcessLogHistoryVO) throws DataServiceException;
    public Long getAchProcessId(long ledgerEntryId) throws DataServiceException ;
    public void updateBatchBalancedIndicator(Integer processLogId, Integer balancedInd) throws DataServiceException;
    public AchResponseReasonVO getReasonInfo(String rejectReasonCode, Integer fileTypeId) throws DataServiceException ;
    public HashMap<String, AchResponseReasonVO> getAllReasons(Integer fileTypeId) throws DataServiceException ;
    public void adjustConsolidatedRecordOnOrigination(long ledgerEntryId)throws DataServiceException;
    public void achProcessLogStatusUpdate(long ledgerId) throws DataServiceException;
	public void achProcessQueueReturnUpdate(AchProcessQueueEntryVO achRecord) throws DataServiceException;
	public double achqueueEntryTotal() 	throws DataServiceException ;
	public boolean createAchQueueEntry(AchProcessQueueEntryVO queueEntry) throws DataAccessException;
	public long accountIdFromLedgerId(long ledgerId) throws DataServiceException;
	public void changeUnBalancedFlag() throws DataServiceException;
	public Integer batchCountByStatus(Integer processLogId, Integer statusId) throws DataServiceException;
   	public List<NachaProcessQueueVO> getAllUnbalancedTransactions(int batchId) throws DataServiceException;
   	public long  getLedgerIdFromDateTime(String createdDateTime) throws DataServiceException;
   	public Integer getProviderEntityId(Long ledgerId);
   	public boolean getOriginationNotificationInd() throws DataServiceException;
   	public AchResponseReasonVO getReasonCodeDetails(Integer reasonId) 
	throws DataServiceException;
   	public EntityEmailVO getContactEmailFromLedgerEntry(Long ledgerEntryId)
	throws DataServiceException;
	public List<PTDRecordTypeVO> getPTDRecordTypeTemplate(String recordType) throws DataServiceException;
	public double getCreditCardConsolidationAmount(AchProcessQueueEntryVO achProcessQueueVO);
	public void updateCreditCardAuthStatus(AchProcessQueueEntryVO achProcessQueueVO);
	public List<Long> getLedgerIdsForNachaProcess(AchProcessQueueEntryVO achProcessQueueVO);
	public void reconcileCreditCardAuthLedgerEntry(List<Long> ledgerIds);
	public  List<ArrayList> getWithdrawalReversalRejectReasonCodes() throws DataServiceException;
	public List<AchProcessQueueEntryVO> getAutoAchConsolidationAmount(AchProcessQueueEntryVO achProcessQueueVO);
	public Date getNachaProcessLastRanDate() throws DataServiceException;
	public Date getNachaProcessLastRanDate(List processStatusIds) throws DataServiceException;
	public Date getPTDProcessLastRanDate() throws DataServiceException;
	public Date getGeneralLedgerProcessLastRanDate() throws DataServiceException;
	public Date getTransProcessLastRanDate() throws DataServiceException;
	public ReconciliationViewVO getTransProcessView(int transactionTypeId) throws DataServiceException;
	public ReconciliationViewVO getNachaProcessView(int transactionTypeId, int entityTypeId) throws DataServiceException;
	public ReconciliationViewVO getNachaProcessViewForConsolidatedAutoAchEntry() throws DataServiceException;
	public AchProcessQueueEntryVO getAchProcessDataFromLedgerId(Long ledgerEntryId) throws DataServiceException;
	public void updateAchProcessAcctIdForDisabledAccts() throws DataServiceException;
	public HashMap<String,BigDecimal> getSumAchProcessByLogId(long processLogId) throws DataServiceException;
	public void achProcessQueueCreatedDateUpdate(AchProcessQueueEntryVO achProcessQueueEntryVO);
	public Date getAckOrigStatusLastRan(Integer processStatusId) throws DataServiceException;
}
