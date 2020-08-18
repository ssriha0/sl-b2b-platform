package com.newco.marketplace.persistence.iDao.ledger;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.newco.marketplace.dto.vo.ach.NachaProcessQueueVO;
import com.newco.marketplace.dto.vo.ajax.AjaxCacheVO;
import com.newco.marketplace.dto.vo.financemanger.AdminPaymentVO;
import com.newco.marketplace.dto.vo.financemanger.BuyerSOReportVO;
import com.newco.marketplace.dto.vo.financemanger.ExportStatusVO;
import com.newco.marketplace.dto.vo.financemanger.FMReportVO;
import com.newco.marketplace.dto.vo.financemanger.FMW9ProfileVO;
import com.newco.marketplace.dto.vo.financemanger.ProviderSOReportVO;
import com.newco.marketplace.dto.vo.fullfillment.GLDetailVO;
import com.newco.marketplace.dto.vo.ledger.Account;
import com.newco.marketplace.dto.vo.ledger.AccountHistoryVO;
import com.newco.marketplace.dto.vo.ledger.BAIFileVO;
import com.newco.marketplace.dto.vo.ledger.GLSummaryVO;
import com.newco.marketplace.dto.vo.ledger.GlProcessLogVO;
import com.newco.marketplace.dto.vo.ledger.LedgerBusinessTransactionAccountVO;
import com.newco.marketplace.dto.vo.ledger.LedgerBusinessTransactionVO;
import com.newco.marketplace.dto.vo.ledger.LedgerIdsVO;
import com.newco.marketplace.dto.vo.ledger.LedgerSummaryVO;
import com.newco.marketplace.dto.vo.ledger.TransactionEntryVO;
import com.newco.marketplace.dto.vo.ledger.TransactionRuleVO;
import com.newco.marketplace.dto.vo.ledger.TransactionVO;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.vo.receipts.SOReceiptsVO;

public interface TransactionDao{
	
	public List queryTransactionRule(TransactionRuleVO vo) throws DataServiceException;
	    
	public LedgerBusinessTransactionVO selectTransactionEntries(int busTransId) throws DataServiceException;
    
	public LedgerBusinessTransactionVO loadTransactionEntries(LedgerBusinessTransactionVO busTransVO) throws DataServiceException;
    
	public List loadTransactionEntryType() throws DataServiceException;
    
	public LedgerBusinessTransactionVO insert(LedgerBusinessTransactionVO ledgerBusinessTransVO) throws DataServiceException;
    
	public List<TransactionEntryVO> getTransactionEntriesByEntityId(AjaxCacheVO avo);
    
	public List<GLSummaryVO> getLedgerSummary();
	
	public List<GLSummaryVO> getLedgerSummary(Date glDate);
	
	public List<GLSummaryVO> getAllTAccounts();
    
	public TransactionVO getLedgerEntryByEntryId(Long entryId);
    
	public List<LedgerSummaryVO> getLedgerEntryIdByRuleId(int ruleId); 	
    
	public boolean updateLedgerEntryGLProcessed(List<GLSummaryVO> ledgerEntries);
    
	public Integer createGLProcessLog(GlProcessLogVO glProcessLogVO);
    
	public List<Integer> getLedgerEntriesByTransactionTypes(LedgerIdsVO ledgerIdVO);
    
	public List<LedgerSummaryVO> getNachaLedgerDetails(LedgerSummaryVO vo) throws DataServiceException; 
    
	public List<TransactionEntryVO> getTransactionEntriesByEntityIdForCurrent(AjaxCacheVO vo);
	
	public List<TransactionEntryVO> getTransactionEntriesForPendingRequests(AjaxCacheVO vo);
    
	public  List<Account> accountdetails(int entityId);
    
	public Account getAccountDetails(Integer entityId);
	
	public Account getEscheatAccountDetails(Integer entityId);
	
	public  List<TransactionVO> withdrawfunds(Long accountId);
    
	public boolean saveAccountInfo(Account account);
    
	public boolean updateDeactivateAccountInfo(Account account)throws DataServiceException;
    
	public GLSummaryVO getGL_T_Account(GLSummaryVO glSummaryVO);
    
	public Integer returnAccountCount(Account account) throws DataServiceException;
    
	public List<NachaProcessQueueVO> getLedgerEntryIdForOldAccount(Account account)throws DataServiceException;
    
	public boolean updateLedgerTransactionEntriesWithNewAccount(LedgerBusinessTransactionAccountVO ledgerTransactionAccountVo);
    
	public boolean updateAchProcessQueue(LedgerBusinessTransactionAccountVO ledgerTransactionAccountVo);
    
    
    public boolean updateAccoutEnableInd(Long accountId, Boolean enabledInd);
    
    public List<AccountHistoryVO> getAccountHistory(AccountHistoryVO ahVO) throws DataServiceException;
    public List<AccountHistoryVO> getAccountHistoryFMOverview(AccountHistoryVO ahVO) throws DataServiceException;
    public int getAccountHistoryCount(AccountHistoryVO ahVO) throws DataServiceException;
    public List<Account> accountdetailsall(int entityId);
    public boolean updateReconciledSchedularData(Integer processLogId) 
	throws DataServiceException;
   	public List<NachaProcessQueueVO> getReconciledSchedularData() 
   	throws DataServiceException;
   	
   	public void updateLedgerEntryReconcilation(BAIFileVO baiFileVO) throws DataServiceException;
   	public void updateLedgerTransEntryModifiedDate(BAIFileVO baiFileVO) throws DataServiceException;
	public List<TransactionEntryVO> getSLOperationTransactionEntries();
	
	// E-Wallet Enhancement
	public double getWalletHoldBalance(Integer entityId) throws DataServiceException;

	public void updateEntityWalletRemainingBalance(double remainingHoldBalance, Integer entityId, Integer walletControlId) throws DataServiceException;
	
	public Account getAccountDetailsAllData(Long accountId);
	
	public List<Account> accountDetailsIncludeCC(int entityId);

	public SOReceiptsVO getSOTransactionReceiptInfo(SOReceiptsVO vo) throws DataAccessException;
	
	public Integer getWalletControlId(Integer entityId);
	
	public String getWalletControlBanner(Integer walletControlId);
	
	public SOReceiptsVO getSLBucksTransactionReceiptInfo(SOReceiptsVO vo) throws DataAccessException;
	
	public SOReceiptsVO getBuyerPayAndCloseReceiptInfo(String soId) throws DataAccessException;
	
	public Map<Long, BigDecimal> getAvailableBalanceByEntityId(AjaxCacheVO avo);
	
	public Map<Integer, BigDecimal> getProjectBalanceByEntityId(AjaxCacheVO avo);
	
	public Map<Long, BigDecimal> getPendingBalanceByEntityId(AjaxCacheVO avo);
	public int getAutoFundingDetails(Long buyerId) throws DataAccessException;
	public boolean updateAutoFundingDetails(Account account) throws DataAccessException;
	public boolean insertAutoFundingDetails(Account account) throws DataAccessException;
	public boolean updateBuyerFundingTypeId(Account account )throws DataAccessException;
	public int getBuyerFundingTypeId(long buyer_id) throws DataServiceException;

	public Integer getLatestGLProcessLogId() throws DataServiceException;
	public SOReceiptsVO getRepostSOTransactionReceiptInfo(SOReceiptsVO vo) throws DataAccessException;
	public void runGLDetails(Integer glProcessLogId) throws DataServiceException;
	public List<GLDetailVO> getGLDetails(Integer glProcessLogId) throws DataServiceException;
	public Double getVLBCBalanceByEntityId(AjaxCacheVO avo);

	public List<ProviderSOReportVO> getProviderPaymentByServiceOrder(FMReportVO reportVO) throws DataServiceException;;
	public List<Integer> validateBuyerIDs(List<String> buyerIds)  throws DataServiceException;;
	public List<Integer> validateProviderIDs(List<String> providerIds) throws DataServiceException;;
	public List<BuyerSOReportVO> getBuyerPaymentByServiceOrder(FMReportVO reportVO)  throws DataServiceException;;
	public int getBuyerSOReportCount(FMReportVO reportVO);

	public List<BuyerSOReportVO> getBuyerReportByTaxPayerID(FMReportVO reportVO)  throws DataServiceException;;

/*	To be removed
 * public int getBuyerTaxIDReportCount(FMReportVO reportVO);
	public int getProviderSOReportCount(FMReportVO reportVO);
	public int getBuyerSOReportCount(FMReportVO reportVO);
	public int getProviderNetSummaryReportCount(FMReportVO reportVO);*/

	public List<ProviderSOReportVO> getProviderNetSummaryReport(
			FMReportVO reportVO)  throws DataServiceException;

	public boolean saveInputsForReportScheduler(FMReportVO fmReportVO);
	
	public List<FMReportVO> getCompletedAndFailedRecords() throws DataServiceException;

	public void updateDeletedStatus(List<Integer> reportIds) throws DataServiceException;
	
	public List<FMReportVO> getQueuedRecords(Integer numberOfReportsToBeProcessed) throws DataServiceException;

	public List<ExportStatusVO> getReportStatus(int slId) throws DataServiceException;

	public List<AdminPaymentVO> getAdminPaymentReport(FMReportVO reportVO) throws DataServiceException;

	public List<FMW9ProfileVO> getFMW9History(FMReportVO reportVO) throws DataServiceException;
	
	public List<FMW9ProfileVO> getFMW9HistoryForAddress(
			FMReportVO reportVO) 	throws  DataServiceException;
	
	public void updateStatusOfExport(FMReportVO reportVO) throws DataServiceException;

	public void updateExportDetails(FMReportVO reportVO)  throws DataServiceException;

	public FMReportVO getReportCritirea(Integer reportId) throws DataServiceException;
	public int deleteReport(FMReportVO fmReportVO) throws DataServiceException;
	public int checkAndUpdateInProcessReport(int maxTimeIntervalForBatchSec) throws DataServiceException;

	public void updateAccountId(Account account)throws DataAccessException;

	public int getEnabledIndCount(Long  entityId)throws DataAccessException;

	//code change for SLT-2228
	public Integer getTemplateIdByReasoncode(Integer reasonCodeId)throws DataAccessException, DBException, SQLException;
}
