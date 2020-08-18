package com.newco.marketplace.persistence.daoImpl.ledger;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import com.newco.marketplace.dto.vo.ledger.Account;
import com.newco.marketplace.criteria.PagingCriteria;
import com.newco.marketplace.dto.vo.ach.NachaProcessQueueVO;
import com.newco.marketplace.dto.vo.ajax.AjaxCacheVO;
import com.newco.marketplace.dto.vo.financemanger.AdminPaymentVO;
import com.newco.marketplace.dto.vo.financemanger.BuyerSOReportVO;
import com.newco.marketplace.dto.vo.financemanger.ExportStatusVO;
import com.newco.marketplace.dto.vo.financemanger.FMReportVO;
import com.newco.marketplace.dto.vo.financemanger.FMW9ProfileVO;
import com.newco.marketplace.dto.vo.financemanger.ProviderSOReportVO;
import com.newco.marketplace.dto.vo.fullfillment.GLDetailVO;
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
import com.newco.marketplace.dto.vo.serviceorder.CriteriaMap;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.LedgerConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.iDao.ledger.TransactionDao;
import com.newco.marketplace.utils.AchConstants;
import com.newco.marketplace.utils.StackTraceHelper;
import com.newco.marketplace.vo.activity.WalletBalanceVO;
import com.newco.marketplace.vo.receipts.SOReceiptsVO;
import com.sears.os.dao.impl.ABaseImplDao;
public class TransactionDaoImpl extends ABaseImplDao implements TransactionDao {
	private static final Logger localLogger = Logger.getLogger(TransactionDaoImpl.class);

	// converting an array to an ArrayList, without Generics
	private final Integer[] transTypes = {100, 150, 110, 111, 115, 120, 160};
	private final ArrayList<Integer> alTransTypes = new ArrayList<Integer>( Arrays.asList( transTypes ) );

	public List queryTransactionRule(TransactionRuleVO vo)
			throws DataServiceException {
		try {
			return queryForList("transaction_rule.query", vo);
		} catch (Exception ex) {
			throw new DataServiceException("TEST", ex);
		}
	}

	public LedgerBusinessTransactionVO selectTransactionEntries(int busTransId)
			throws DataServiceException {

		try {
			// ledger_business_transaction
			LedgerBusinessTransactionVO lbtVo = (LedgerBusinessTransactionVO) queryForObject(
					"ledger_business_trans.query", new Integer(busTransId));
			// ledger_entry
			TransactionVO tVo = new TransactionVO();
			tVo.setBusinessTransId(busTransId);
			ArrayList tVoAL = (ArrayList) queryForList("ledger_entry.query",
					tVo);
			for (int i = 0; i < tVoAL.size(); i++) {
				// ledger_transaction_entry
				TransactionVO tranVO = (TransactionVO) tVoAL.get(i);
				ArrayList<TransactionEntryVO> teVOAL = (ArrayList<TransactionEntryVO>) queryForList(
						"ledger_transaction_entry.query", tranVO
								.getLedgerEntryId());
				// teVOAL.add(teVO);
				tranVO.setTransactions(teVOAL);
			}
			lbtVo.add_transactions(tVoAL);
			return lbtVo;
		} catch (Exception ex) {
			localLogger
					.info("[TransactionDAOImpl.selectTransactionEntries - Exception] "
							+ StackTraceHelper.getStackTrace(ex));
			// final String error = messages.getMessage("dataaccess.select");
			throw new DataServiceException("TEST", ex);
		}
	}

	public LedgerBusinessTransactionVO loadTransactionEntries(
			LedgerBusinessTransactionVO busTransVO) throws DataServiceException {

		try {
			ArrayList<TransactionVO> tranVOList = busTransVO.get_transactions();
			for (int i = 0; i < tranVOList.size(); i++) {
				TransactionVO tranVO = tranVOList.get(i);
				
				if (busTransVO.getFundingTypeId() == LedgerConstants.FUNDING_TYPE_NON_FUNDED && 
						alTransTypes.contains(busTransVO.getBusinessTransId()))	
				{
					tranVO.setAffectsBalanceInd(0);
					
				}
				
				insertTransaction(tranVO); // insert into ledger_entry
				ArrayList<TransactionEntryVO> tranEntryVOList = tranVO
						.getTransactions();
				if (tranEntryVOList != null) {
					if (tranEntryVOList.size() > 0)
						for (int j = 0; j < tranEntryVOList.size(); j++)
							insertTransactionEntry(tranEntryVOList.get(j)); // insert
																			// into
																			// ledger_transaction_entry
				}
			}
		} catch (Exception ex) {
			localLogger
					.info("[TransactionDAOImpl.loadTransactionEntries - Exception] "
							+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("TEST", ex);
		}
		return null;
	}

	public List loadTransactionEntryType() throws DataServiceException {
		try {
			return queryForList("ledger_trans_entry_type.query", null);
		} catch (Exception ex) {
			localLogger
					.info("[TransactionDAOImpl.loadTransactionEntryType - Exception] "
							+ StackTraceHelper.getStackTrace(ex));
			// final String error = messages.getMessage("dataaccess.select");
			// throw new DataServiceException(error, ex);
			throw new DataServiceException("TEST", ex);
		}
	}

	public LedgerBusinessTransactionVO insert(LedgerBusinessTransactionVO vo)
			throws DataServiceException {
		try {
			vo = (LedgerBusinessTransactionVO) insert(
					"ledger_business_transaction.insert", vo);
		} catch (Exception ex) {
			localLogger.info("[TransactionDAOImpl.insert - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			// final String error = messages.getMessage("dataaccess.update");
		}
		return vo;
	}

	public TransactionVO insertTransaction(TransactionVO vo)
			throws DataServiceException {
		try {
			vo = (TransactionVO) insert("ledger_entry.insert", vo);
		} catch (Exception ex) {
			localLogger.info("[TransactionDAOImpl.insert - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			// final String error = messages.getMessage("dataaccess.update");
			throw new DataServiceException("TEST", ex);
		}
		return vo;
	}
		
	public TransactionEntryVO insertTransactionEntry(TransactionEntryVO vo)
			throws DataServiceException {
		try {
			vo = (TransactionEntryVO) insert("ledger_transaction_entry.insert",
					vo);
		} catch (Exception ex) {
			localLogger.info("[TransactionDAOImpl.insert - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			// final String error = messages.getMessage("dataaccess.update");
			throw new DataServiceException("TEST", ex);
		}
		return vo;
	}

	public List<TransactionEntryVO> getTransactionEntriesByEntityId(
			AjaxCacheVO avo) {
		return this.queryForList("ledger_transaction_entry_byEntityId.query",
				avo);
	}
	
	public List<TransactionEntryVO> getSLOperationTransactionEntries() {
		return this.queryForList("ledger_transactions_by_sloperation.query", null);
	}
	

	public List<TransactionEntryVO> getTransactionEntriesByEntityIdForCurrent(
			AjaxCacheVO avo) {
		return this.queryForList(
				"ledger_transaction_entry_byEntityId_for_current.query", avo);
	}
	
	public List<TransactionEntryVO> getTransactionEntriesForPendingRequests(
			AjaxCacheVO avo) {
		return this.queryForList(
				"ledger_transaction_entry_byEntityId_for_pendingRequests.query", avo);
	}

	public List<GLSummaryVO> getLedgerSummary() {
		List<GLSummaryVO> glSummaryVO = this.queryForList("gl_summary.query", null);
		List<GLSummaryVO> glSummaryVOUpdated = new ArrayList<GLSummaryVO>();
		Date currentDate = new Date();
		currentDate.setDate(currentDate.getDate()- 30);
		if(glSummaryVO != null)
			for(int i=0; i<glSummaryVO.size(); i++){
				if((currentDate.before(glSummaryVO.get(i).getModifiedDate()))){
					glSummaryVOUpdated.add(glSummaryVO.get(i));
				}
			}
		return glSummaryVOUpdated;
	}
	
	public List<GLSummaryVO> getLedgerSummary(Date glDate) {

		Map map = new HashMap();
		map.put("glDate", glDate);
		List<GLSummaryVO> glSummaryVOs = this.queryForList("gl_summary.query", map);
		return glSummaryVOs;
	}	

	public List<LedgerSummaryVO> getNachaLedgerDetails(LedgerSummaryVO vo)
			throws DataServiceException {
		try {
			return queryForList("nacha_fee_details.query", vo);
		} catch (Exception e) {
			localLogger
					.info("[TransactionDAOImpl.nacha_fee_details.query - Exception] "
							+ StackTraceHelper.getStackTrace(e));
			throw new DataServiceException("TEST", e);

		}

	}

	public TransactionVO getLedgerEntryByEntryId(Long entryId) {
		return (TransactionVO) queryForObject("ledger_entry_glprocessed.query",
				entryId);

	}

	public List<LedgerSummaryVO> getLedgerEntryIdByRuleId(int ruleId) {
		return this.queryForList("ledger_entry_ruleid.query", new Integer(
				ruleId));
	}

	public Integer createGLProcessLog(GlProcessLogVO glProcessLogVO) {
		Integer glProcessLogId = (Integer) this.insert("gl_feed_long.insert", glProcessLogVO);
		return glProcessLogId;
	}

	public List<Integer> getLedgerEntriesByTransactionTypes(
			LedgerIdsVO ledgerIdVO) {
		List<Integer> entriesList = new ArrayList<Integer>();
		entriesList = this.queryForList(
				"ledger_entries_transaction_types.query", ledgerIdVO);
		return entriesList;
	}

	public boolean updateLedgerEntryGLProcessed(List<GLSummaryVO> ledgerEntries){
		boolean success = false;
		try{
			Integer latestProcessLogId = getLatestGLProcessLogId();
			for (GLSummaryVO glSummaryVO : ledgerEntries) {
				glSummaryVO.setGl_process_id(latestProcessLogId);
				continue;
			}
			update("ledger_entry_glprocessed.update", ledgerEntries);
			success = true;
		}catch(Exception e){
			localLogger.error("Problem occured in the update of GL related ledgerEntryIds");
			e.printStackTrace();
		}
		return success;
	}

	public List<TransactionVO> withdrawfunds(Long accountId) {
		List<TransactionVO> accounts = queryForList(
				"ledger_entry_for_withdraw.query", accountId);
		return accounts;
	}

	public List<Account> accountdetails(int entityId) {
		// code change for SLT-2112
				Map<String, Integer> parameter = new HashMap<String, Integer>();
						parameter.put("entityId", entityId);
				List<Account> accounts = queryForList("account_hdr_active.query", parameter);
		return accounts;
	}
	
	public List<Account> accountDetailsIncludeCC(int entityId) {
		List<Account> accounts = queryForList("account_hdr_active_include_cc.query", entityId);
		return accounts;
	}
	

	public List<Account> accountdetailsall(int entityId) {
		List<Account> accounts = queryForList("account_hdr.query", entityId);
		return accounts;
	}
	
	public Account getAccountDetails(Integer entityId){
		Account accountObj = null;
		// code change for SLT-2112
		Map<String, Integer> parameter = new HashMap<String, Integer>();
				parameter.put("entityId", entityId);
		List<Account> accounts = queryForList("account_hdr_active.query", parameter);
		if(accounts!=null && accounts.size()>0){
			accountObj = accounts.get(0);
		}
		if(accountObj == null)
		{
			accountObj = new Account();
			accountObj.setAccount_id(null);
		}
		return accountObj;
	}

	public Account getEscheatAccountDetails(Integer entityId){
		Account accountObj = null;
		List<Account> accounts = queryForList("account_hdr_escheat_active.query", entityId);
		if(accounts!=null && accounts.size()>0){
			accountObj = accounts.get(0);
		}
		if(accountObj == null)
		{
			accountObj = new Account();
			accountObj.setAccount_id(null);
		}
		return accountObj;
	}

	public boolean saveAccountInfo(Account account) {
		/*int count = 0;
		count = update("save_account.update", account);
		if (count == 0) {
			insert("save_account.insert", account);
		}*/
		insert("save_account.insert", account);
		return true;
	}
	public boolean updateDeactivateAccountInfo(Account account)throws DataServiceException {
		update("deactivate_account.update", account);
		return true;
	}
	
	public GLSummaryVO getGL_T_Account(GLSummaryVO glSummaryVO){
		
		return (GLSummaryVO) queryForObject("gl_t_account.query", glSummaryVO);
	}

	public Integer getLatestGLProcessLogId() throws DataServiceException{
		
		return (Integer) queryForObject("gl_process_log_id.query", null);
	}
	

	public List<AccountHistoryVO> getAccountHistoryFMOverview(AccountHistoryVO ahVO) throws DataServiceException{
			
		List<AccountHistoryVO> accountHistory = null;

		if(ahVO.isSlAdminInd() || ahVO.isBuyerAdminInd())
			accountHistory = queryForList("account_history_sladmin_record.query",ahVO);
		else
			accountHistory = queryForList("account_history_record.query",ahVO);
		return accountHistory;
	}	
	
	
	private PagingCriteria getPagingCritera( CriteriaMap criteraMap )
	{
		if(criteraMap != null)
		{
			Object pageObj = criteraMap.get(OrderConstants.PAGING_CRITERIA_KEY);		
			return(PagingCriteria) pageObj;
		}
		return null;
	}
	public int getAccountHistoryCount(AccountHistoryVO ahVO) throws DataServiceException{
		Integer count = null;
		//SL-18850 : setting time stamp of start date as 00:00:00 and 
		//the end date as (end date + 1) 00:00:00
		//as the date function is removed from the below queries
		if(null != ahVO && null != ahVO.getFromDate()){
			Calendar fromDate = Calendar.getInstance();
			fromDate.setTime(ahVO.getFromDate());
			fromDate.set(Calendar.HOUR_OF_DAY, 0);
			fromDate.set(Calendar.MINUTE, 0);
			fromDate.set(Calendar.SECOND, 0);
			ahVO.setFromDate(new java.sql.Date(fromDate.getTime().getTime()));
		}
		if(null != ahVO && null != ahVO.getToDate()){
			Calendar toDate = Calendar.getInstance();
			toDate.setTime(ahVO.getToDate());
			//adding 1 to end date to get the all the transactions till previous day 23:59:59
			toDate.add(Calendar.DAY_OF_MONTH,1);
			toDate.set(Calendar.HOUR_OF_DAY, 0);
			toDate.set(Calendar.MINUTE, 0);
			toDate.set(Calendar.SECOND, 0);
			ahVO.setToDate(new java.sql.Date(toDate.getTime().getTime()));
		}
		if(ahVO.isSlAdminInd() || ahVO.isBuyerAdminInd())
			count = (Integer)queryForObject("account_history_sladmin_count.query",ahVO);
		else
			count =(Integer)queryForObject("account_history_count.query",ahVO);
		return count;
	}

	public List<AccountHistoryVO> getAccountHistory(AccountHistoryVO ahVO) throws DataServiceException{
		List<AccountHistoryVO> accountHistory = null;
		//SL-18850 : setting time stamp of start date as 00:00:00 and 
		//the end date as (end date + 1) 00:00:00
		//as the date function is removed from the below queries
		if(null != ahVO && null != ahVO.getFromDate()){
			Calendar fromDate = Calendar.getInstance();
			fromDate.setTime(ahVO.getFromDate());
			fromDate.set(Calendar.HOUR_OF_DAY, 0);
			fromDate.set(Calendar.MINUTE, 0);
			fromDate.set(Calendar.SECOND, 0);
			ahVO.setFromDate(new java.sql.Date(fromDate.getTime().getTime()));
		}
		if(null != ahVO && null != ahVO.getToDate()){
			Calendar toDate = Calendar.getInstance();
			toDate.setTime(ahVO.getToDate());
			//adding 1 to end date to get the all the transactions till previous day 23:59:59
			toDate.add(Calendar.DAY_OF_MONTH,1);
			toDate.set(Calendar.HOUR_OF_DAY, 0);
			toDate.set(Calendar.MINUTE, 0);
			toDate.set(Calendar.SECOND, 0);
			ahVO.setToDate(new java.sql.Date(toDate.getTime().getTime()));
		}
		if(ahVO.isSlAdminInd() || ahVO.isBuyerAdminInd())
			accountHistory = queryForList("account_history_sladmin.query",ahVO);
		else
			accountHistory = queryForList("account_history.query",ahVO);
		return accountHistory;
	}

	public Integer returnAccountCount(Account account) throws DataServiceException{
		Integer accountCount = null;
		try {
			accountCount = (Integer) queryForObject("returnAccountCount.query", account);
		}catch (Exception ex) {
			localLogger.info("[TransactionDAOImpl.returnAccountCount.query - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			// final String error = messages.getMessage("dataaccess.update");
			throw new DataServiceException("Error", ex);
		} 
		return accountCount;
	}
	 public List<NachaProcessQueueVO> getLedgerEntryIdForOldAccount(Account account)throws DataServiceException{
		 List<NachaProcessQueueVO> accountLedgerList = new ArrayList<NachaProcessQueueVO>();
		 try {
			 accountLedgerList = queryForList("ledgerIdsForOldAccount.query", account);
			} catch (Exception e) {
				localLogger
						.info("[TransactionDAOImpl.getLedgerIdForOldAccount() - Exception] "
								+ StackTraceHelper.getStackTrace(e));
				throw new DataServiceException("TEST", e);

			}
		 return accountLedgerList;
	 }
	 
	 public boolean updateLedgerTransactionEntriesWithNewAccount(LedgerBusinessTransactionAccountVO ledgerTransactionAccountVo){
		  try {
			 update("updateledgerTransactionEntries.query", ledgerTransactionAccountVo);
			} catch (Exception e) {
				localLogger.error("Problem occured in the update of ledger transaction entries with new account id");
				e.printStackTrace();
				return false;
			}
		 return true;
	 }
	 public boolean updateAchProcessQueue(LedgerBusinessTransactionAccountVO ledgerTransactionAccountVo){
		 try {
			 update("updateAchProcessQueue.query", ledgerTransactionAccountVo);
			} catch (Exception e) {
				localLogger.error("Problem occured in the update of Ach Process Queue with new account id");
				e.printStackTrace();
				return false;
			}
		 return true;
	 }
	 
	 public boolean updateAccoutEnableInd(Long accountId, Boolean enabledInd){
		 HashMap map = new HashMap();
	     map.put("accountId", accountId);
	     map.put("enabledInd", enabledInd);
		 try {
			 update("updateAccountEnableInd.query", map);
			} catch (Exception e) {
				localLogger.error("Problem occured while updating the enable indicator of account");
				e.printStackTrace();
				return false;
			}
		 return true;
	 }

	public List<NachaProcessQueueVO> getReconciledSchedularData() throws DataServiceException{
		List<NachaProcessQueueVO> list = new ArrayList<NachaProcessQueueVO>();
		
		try{
			list = (List<NachaProcessQueueVO>)queryForList("reconciledScheduler.query", AchConstants.ORIGINATION_FILE_PROCESSED);
			
		}catch(Exception e){
			localLogger.error("NachaDaoImpl.getReconciledSchedularData", e);
			throw new DataServiceException("Error",e);
		}
		return list;
	}
	public boolean updateReconciledSchedularData(Integer processLogId) throws DataServiceException{
		boolean flag = false;
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("statusSuccess", AchConstants.ACH_RECORD_SUCCESSFULLY_PROCESSED);
		map.put("StatusComplete", AchConstants.ORIGINATION_FILE_PROCESSED);
		map.put("reconsiledInd", AchConstants.ACH_RECONCILED);
		map.put("processLogId", processLogId);
		try{
			update("reconciledScheduler.update", map);
			flag = true;
		}catch(Exception e){
			localLogger.error("FinanceManagerDAOImpl.updateReconciledSchedularData", e);
			throw new DataServiceException("Error", e);
		}
		return flag;
	}

	public List<GLSummaryVO> getAllTAccounts() {
		List<GLSummaryVO> glTAccounts = new ArrayList<GLSummaryVO>();
		
		try{
			glTAccounts = (ArrayList<GLSummaryVO>)queryForList("query.getAllGLTAccounts", null);

		}catch(Exception e){
			localLogger.error(e.getMessage());
			localLogger.debug(e.getCause());
		}
		return glTAccounts;
	}
	
	public void updateLedgerEntryReconcilation(BAIFileVO baiFileVO) throws DataServiceException{
		try{
			update("bai_reconsile.update", baiFileVO);
		}catch(Exception e){
			localLogger.error("TransactionDaoImpl.updateLedgerEntryReconcilation", e);
			throw new DataServiceException("TransactionDaoImpl.updateLedgerEntryReconcilation", e);
		}
	}

	public void updateLedgerTransEntryModifiedDate(BAIFileVO baiFileVO) throws DataServiceException{
		try{
			int updated = update("lte_modified_date.update", baiFileVO);
			System.out.print(updated);
		}catch(Exception e){
			localLogger.error("TransactionDaoImpl.updateLedgerTransEntryModifiedDate", e);
			throw new DataServiceException("TransactionDaoImpl.updateLedgerTransEntryModifiedDate", e);
		}
	}
	

	public Account getAccountDetailsAllData(Long accountId) {
		Account account = (Account) queryForObject("account_hdr_all.query", accountId);
		return account;
	}

	public SOReceiptsVO getSOTransactionReceiptInfo(SOReceiptsVO vo) throws DataAccessException {
		return (SOReceiptsVO) queryForObject("select_so_transaction_receipt.query", vo);
	}
	
	public SOReceiptsVO getRepostSOTransactionReceiptInfo(SOReceiptsVO vo) throws DataAccessException {
		return (SOReceiptsVO) queryForObject("select_so_transaction_receipt_repost.query", vo);
	}
	
	public SOReceiptsVO getSLBucksTransactionReceiptInfo(SOReceiptsVO vo) throws DataAccessException {
		return (SOReceiptsVO) queryForObject("select_sl_bucks_transaction_info.query", vo);		
	}
	
	public SOReceiptsVO getBuyerPayAndCloseReceiptInfo(String soId) throws DataAccessException {
		return (SOReceiptsVO) queryForObject("select_buyer_payandclose_receipt.query", soId);		
	}
	
	
	public Map<Long, BigDecimal> getAvailableBalanceByEntityId(AjaxCacheVO avo){
	    Map result = new HashMap<Integer, BigDecimal>();
			try {
				result = getSqlMapClient().queryForMap(
						"ledger_transaction_entry_byEntityId_for_availablebalance.query", avo,
						"buyer_id", "available_balance");
				} catch (Exception ex) {
				localLogger.info("[TransactionDaoImpl.getAvailableBalanceByEntityId - Exception] "
								+ ex.getStackTrace());
				ex.printStackTrace();
			}
			return result;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.ledger.TransactionDao#getVLBCBalanceByEntityId(com.newco.marketplace.dto.vo.ajax.AjaxCacheVO)
	 */
	public Double getVLBCBalanceByEntityId(AjaxCacheVO avo){
		WalletBalanceVO wbvoFe = (WalletBalanceVO)queryForObject("getVLBalanceFromFullfillmentEntry.query", avo.getCompanyId());
		WalletBalanceVO wbvoFvl = (WalletBalanceVO)queryForObject("getVLBalanceFromFullfillmentVLAccnts.query", avo.getCompanyId());
		Double vlBalanceAmt = 0.0;
		if (wbvoFe == null &&  wbvoFvl!=null){
			vlBalanceAmt = wbvoFvl.getValidatedAmount();
		}
		else if(wbvoFe != null && wbvoFvl==null)
		{
			vlBalanceAmt = wbvoFe.getValidatedAmount();
		}
		else if(wbvoFe != null && wbvoFvl!=null)
		{
			if(wbvoFe.getStartDate().compareTo(wbvoFvl.getStartDate()) >=0){
				vlBalanceAmt = wbvoFe.getValidatedAmount();
			}
			else{
				vlBalanceAmt = wbvoFvl.getValidatedAmount();
			}
				
		}
		return vlBalanceAmt;
	}

	public Map<Integer, BigDecimal> getProjectBalanceByEntityId(AjaxCacheVO avo){
		Map result = new HashMap<Integer, BigDecimal>();
	 		try {
				result = getSqlMapClient().queryForMap(
						"ledger_transaction_entry_byEntityId_for_projectbalance.query", avo,
						"buyer_id", "project_balance");
				} catch (Exception ex) {
				localLogger.info("[TransactionDaoImpl.getProjectBalanceByEntityId - Exception] "
								+ ex.getStackTrace());
				ex.printStackTrace();
			}
			return result;
	}
	
	
	public Map<Long, BigDecimal> getPendingBalanceByEntityId(AjaxCacheVO avo){
	    Map result = new HashMap<Long, BigDecimal>();
			try {
				result = getSqlMapClient().queryForMap(
						"ledger_transaction_entry_byEntityId_for_pendingbalance.query", avo,
						"buyer_id", "pending_balance");
				} catch (Exception ex) {
				localLogger.info("[TransactionDaoImpl.getPendingBalanceByEntityId - Exception] "
								+ ex.getStackTrace());
				ex.printStackTrace();
			}
			return result;
	}
	
	/**
	 * This method  retrieves the count of the buyer record from 
	 * AUTO_FUNDING_SERVICE table to check whether the record is already 
	 * present or not
	 * @param  Long buyerId 
	 * @return int count
	 * @throws Exception
	 */
	public int getAutoFundingDetails(Long buyerId) throws DataAccessException{
		int count=0;
		try {
			count = (Integer)queryForObject(
					"getAutoFundingCount.query", buyerId);
			} catch (Exception ex) {
				localLogger.info("[TransactionDaoImpl.getAutoFundingDetails - Exception] "
							+ ex.getStackTrace());			
			}
			return count;
	}
	
	/**
	 * This method  inserts the auto funding details of the buyer into 
	 * AUTO_FUNDING_SERVICE table
	 * @param  Account account 
	 * @return boolean 
	 * @throws Exception
	 */
	public boolean insertAutoFundingDetails(Account account) {
		try{
			insert("save_autoFunding.insert", account);
		} catch (Exception e) {
			localLogger.error("Problem occured while inserting the auto_funding_service");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * This method  updates the auto funding details of the buyer in 
	 * AUTO_FUNDING_SERVICE table
	 * @param  Account account 
	 * @return boolean 
	 * @throws Exception
	 */
	public boolean updateAutoFundingDetails(Account account) {
		try{
			update("save_autoFunding.update", account);
		} catch (Exception e) {
			localLogger.error("Problem occured while updating the auto_funding_service");			
			return false;
		}
		return true;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.ledger.TransactionDao#updateBuyer(int)
	 * This method is for updating Buyer table.
	 */
	public boolean updateBuyerFundingTypeId(Account account) throws DataAccessException {
		try{
			update("buyer_FundingTypeId.update",account);
		}
		catch(Exception e)
		{
			localLogger.error("Problem occured while updating the buyer");
			return false;
		}
	
		return true;
	}
	
	public int getBuyerFundingTypeId(long buyer_id) throws DataAccessException {
		int buyerId = (int) buyer_id;
		int funding_type_id = 0;
		try {
			funding_type_id = (int) queryForObject("BuyerFundingTypeId.select", buyerId);
		} catch (Exception e) {
			logger.error("Exception in Fetching Buyer Funding Type Id " + e);
			e.printStackTrace();
		}
		return funding_type_id;
	}

	public void runGLDetails(Integer glProcessLogId) throws DataServiceException
	{
		insert("runGLDetails.storeproc",glProcessLogId);
	}
	
	public List<GLDetailVO> getGLDetails(Integer glProcessLogId) throws DataServiceException
	{
		return (List<GLDetailVO>)queryForList("getGLDetails.query",glProcessLogId);
	}

	public List<ProviderSOReportVO> getProviderPaymentByServiceOrder(FMReportVO reportVO) throws DataServiceException {
		List<ProviderSOReportVO> listOfProviderSO=null;
	   try{
		    if(reportVO.getRoleType().equalsIgnoreCase(OrderConstants.NEWCO_ADMIN)){
		    	listOfProviderSO=(List<ProviderSOReportVO>)queryForList("getProviderPaymentBySOAdmin.query",reportVO);
		    }else{
		    	listOfProviderSO=(List<ProviderSOReportVO>)queryForList("getProviderPaymentBySO.query",reportVO);
		    }
			
	   }catch (DataAccessException e) {
		   localLogger.error(e);
         throw new DataServiceException("Error getting Provider report by so",e);
	   }
        return listOfProviderSO;
	}
	public List<Integer> validateBuyerIDs(List<String> buyerIds) throws DataServiceException{
		List<Integer> listOfBuyers=null;
		try{
		  listOfBuyers=(List<Integer>)queryForList("getValidBuyerIds.query", buyerIds);
		}catch (DataAccessException e) {
	         throw new DataServiceException("Error validatin buyers",e);
		   }
		 return listOfBuyers;
	}
	public List<Integer> validateProviderIDs(List<String> providerIds) throws DataServiceException{
		List<Integer> listOfBuyers=null;
		try{
		 listOfBuyers=(List<Integer>)queryForList("getValidProviderIds.query", providerIds);
		}catch (DataAccessException e) {
	         throw new DataServiceException("Error validating providers",e);
		   }
		 return listOfBuyers;
	}
	
	public int getBuyerSOReportCount(FMReportVO reportVO) {
		Integer count = null;
		if(OrderConstants.NEWCO_ADMIN.equalsIgnoreCase(reportVO.getRoleType())){
			count = (Integer)queryForObject("buyerso_report_count_admin.query",reportVO);
		}else{
			count = (Integer)queryForObject("buyerso_report_count.query",reportVO);
		}
		return count;
	}
	
	public List<BuyerSOReportVO> getBuyerPaymentByServiceOrder(FMReportVO reportVO) throws DataServiceException{
		List<BuyerSOReportVO> listOfBuyers=null;
		try{
			 if(reportVO.getRoleType().equalsIgnoreCase(OrderConstants.NEWCO_ADMIN)){
				 listOfBuyers=(List<BuyerSOReportVO>)queryForList("getBuyerPaymentBySOAdmin.query", reportVO);
			 }else{
				 listOfBuyers=(List<BuyerSOReportVO>)queryForList("getBuyerPaymentBySO.query", reportVO);
			 }
				
		}catch (DataAccessException e) {
	         throw new DataServiceException("Error getting buyer payment by SO",e);
		   }
		return listOfBuyers;
	} 
	public List<BuyerSOReportVO> getBuyerReportByTaxPayerID(FMReportVO reportVO) throws DataServiceException{
		List<BuyerSOReportVO> listOfBuyers =null;
		try {
			if(OrderConstants.NEWCO_ADMIN.equalsIgnoreCase(reportVO.getRoleType())){
				listOfBuyers = (List<BuyerSOReportVO>) queryForList("getBuyerReportByTaxPayerAdmin.query", reportVO);
			}else{
				listOfBuyers = (List<BuyerSOReportVO>) queryForList("getBuyerReportByTaxPayer.query", reportVO);
			}
		} catch (DataAccessException e) {
			localLogger.error(e);
			throw new DataServiceException("Error getting buyer payment by tax payer", e);
		}
		return listOfBuyers;

	}
/* To be removed
 * 	public int getProviderSOReportCount(FMReportVO reportVO){
		Integer count = null;
		count =(Integer)queryForObject("providerso_report_count.query",reportVO);
		return count;

}

	public int getBuyerSOReportCount(FMReportVO reportVO) {
		Integer count = null;
		count = (Integer)queryForObject("buyerso_report_count.query",reportVO);
		return count;
	}

	public int getBuyerTaxIDReportCount(FMReportVO reportVO) {
		Integer count = null;
		count =(Integer)queryForObject("buyertax_report_count.query",reportVO);
		return count;
	}
	public int getProviderNetSummaryReportCount(FMReportVO reportVO) {
		Integer count =(Integer)queryForObject("getProviderNetSummaryCount.query",reportVO);
		return count;
	}*/
	public List<ProviderSOReportVO> getProviderNetSummaryReport (
			FMReportVO reportVO) throws DataServiceException {
		List<ProviderSOReportVO> listOfProviders=null;
		try{
			if(reportVO.getRoleType().equalsIgnoreCase(OrderConstants.NEWCO_ADMIN)){
				 listOfProviders=(List<ProviderSOReportVO>)queryForList("getProviderNetPaymentSummaryAdmin.query",reportVO);

			}else{
				 listOfProviders=(List<ProviderSOReportVO>)queryForList("getProviderNetPaymentSummary.query",reportVO);
			}
		}catch (DataAccessException e) {
          throw new DataServiceException("Error in accessing provider net summary",e);
		}
		return listOfProviders;
	}


	
	public List<AdminPaymentVO> getAdminPaymentReport(FMReportVO reportVO)throws DataServiceException{
		List<AdminPaymentVO> adminPaymentVOs = null;
		try{
			adminPaymentVOs = (List<AdminPaymentVO>)queryForList("getAdminPaymentReport.query",reportVO);
		}catch(DataAccessException e){
			localLogger.error(e);
			throw new DataServiceException("Error getting admin reports",e);
		}
		return adminPaymentVOs;
	}
	
	public boolean saveInputsForReportScheduler(FMReportVO fmReportVO){
		try{
			insert("report_scheduler.insert", fmReportVO);
		} catch (DataAccessException e) {
			localLogger.error("Problem occured while inserting the report_scheduler");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public List<ExportStatusVO> getReportStatus(int slId) throws DataServiceException{
		 List<ExportStatusVO> exportVOList=null;
		try{
			exportVOList=queryForList("getStatusOfReportExport.query", Integer.valueOf(slId) );
		}catch (DataAccessException e) {
        throw new DataServiceException("Error getting report status",e);
      }
		return exportVOList;
	}
	
	public List<FMReportVO> getCompletedAndFailedRecords() throws DataServiceException{
		List <FMReportVO> reportVO =null;
		try{
			reportVO = (List<FMReportVO>)queryForList("getCompletedAndFailedRecords.query");
		}catch (DataAccessException e) {
			throw new DataServiceException("Error getting Completed Records",e);	
			}
		return reportVO;
	}
	
	public void updateDeletedStatus(List<Integer> reportIds)throws DataServiceException{
		List <FMReportVO> reportVO = null;
		if(null != reportIds && reportIds.size()>0){
			//reportVO = (List<FMReportVO>)queryForList("getCompletedExportRecords.query",reportId);
			try{
				update("updateDeletedStatus.query",reportIds);
			}catch (DataAccessException e) {
				throw new DataServiceException("Error Updating deleted status",e);	
			}
		}else{
			localLogger.warn("Status not updated.");
		}
		
	}
	public void updateStatusOfExport(FMReportVO reportVO) throws DataServiceException{
		try{
			update("updateStatus.query",reportVO);
		}catch (DataAccessException e) {
			throw new DataServiceException("Error Updating  status",e);	
		}
	}
	public List<FMReportVO> getQueuedRecords(Integer numberOfReportsToBeProcessed) throws DataServiceException{
		List <FMReportVO> reportVO=null;
		try{
		reportVO = (List<FMReportVO>)queryForList("getQueuedRecords.query",numberOfReportsToBeProcessed);
		}catch (DataAccessException e) {
			throw new DataServiceException("Error getting Queued Records",e);	
		}
		return reportVO;
	}
	

	public List<FMW9ProfileVO> getFMW9History(FMReportVO reportVO)  throws DataServiceException{
		List <FMW9ProfileVO> listVO =null;
		try{
			if(OrderConstants.NEWCO_ADMIN.equalsIgnoreCase(reportVO.getRoleType())){
				listVO=(List <FMW9ProfileVO>)queryForList("getFMW9HistoryAdmin.query",reportVO);
			}else{
				listVO=(List <FMW9ProfileVO>)queryForList("getFMW9History.query",reportVO);
			}
			
		}catch (DataAccessException e) {
			throw new DataServiceException("Error getting W9 History",e);	
		}
		return listVO;		
	}
	
	public List<FMW9ProfileVO> getFMW9HistoryForAddress(
			FMReportVO reportVO) 	throws  DataServiceException{
		List <FMW9ProfileVO> listVO =null; 
		try{
			if(OrderConstants.NEWCO_ADMIN.equalsIgnoreCase(reportVO.getRoleType())){
				listVO=(List <FMW9ProfileVO>)queryForList("getFMW9HistoryAdminAddress.query",reportVO);
			}else{
				listVO=(List <FMW9ProfileVO>)queryForList("getFMW9HistoryAddress.query",reportVO);
			}
			
		}catch (DataAccessException e) {
			throw new DataServiceException("Error getting W9 History",e);	
		}
		return listVO;		
	}
	
	public void updateExportDetails(FMReportVO reportVO)  throws DataServiceException{
		try{
		update("updateExportDetails.query",reportVO);
		}catch (DataAccessException e) {
			throw new DataServiceException("Error updating export details",e);	
		}
	}
	public FMReportVO getReportCritirea(Integer reportId) throws DataServiceException{
		FMReportVO fmReportVO = null;
		try{
			fmReportVO = (FMReportVO) queryForObject("getReportCritirea.query", reportId);
		}catch (DataAccessException e) {
			localLogger.error(this.getClass().getName()+":- getReportCritirea() :"+e.getMessage());
			throw new DataServiceException("Exception while fetching report critirea from db. ", e);
		}		
		return fmReportVO;
	}
	
	public int deleteReport(FMReportVO fmReportVO) throws DataServiceException{	
		int countRow =0;
		try{
			 countRow = update("deleteReportFromFrontEnd.query", fmReportVO);
		}catch (DataAccessException e) {
			localLogger.error(this.getClass().getName()+":-deleteReport() :"+e.getMessage());
			throw new DataServiceException("Exception while deleting report critirea from db. ", e);
		}		
		return countRow;
	}
	public int checkAndUpdateInProcessReport(int timeInSec) throws DataServiceException{
		int countRow =0;
		try{
			countRow = update("checkAndUpdateInProcessReport.query",timeInSec);
		}catch (DataAccessException e) {
			localLogger.error(this.getClass().getName()+":-checkAndUpdateInProcessReport() :"+e.getMessage());
			throw new DataServiceException("Exception while Checking and updating In Process Report. ", e);
		}
		return countRow;
	}




	public void updateAccountId(Account account) throws DataAccessException {
		try{
			update("autoFundingSeviceFundingTypeId.update",account);
		}
		catch(Exception e)
		{
			localLogger.error("Problem occured while updating the auto_Funding_service");
			
	
		}
	}


	public int getEnabledIndCount(Long entityId)
			throws DataAccessException {
		int countInd=0;
		try {
			countInd = (Integer)queryForObject(
					"getEnableIndCount.query", entityId);
			} catch (Exception ex) {
				localLogger.info("[TransactionDaoImpl.getEnableIndCount - Exception] "
							+ ex.getStackTrace());			
			}
			return countInd;
	}
		
	//code change for SLT-2228
			public Integer getTemplateIdByReasoncode(Integer reasonCodeId) throws DBException, SQLException,DataAccessException {
				// TODO Auto-generated method stub
				Integer templateId = null;
				try {
					Map<String, Integer> parameter = new HashMap<String, Integer>();
					parameter.put("reasonCodeId", reasonCodeId);
					 templateId= (Integer) getSqlMapClient()
							.queryForObject("getTemplateIdByReasoncode",
									parameter);
				}catch (SQLException ex) {
		            ex.printStackTrace();
				     localLogger.info("SQL Exception @TransactionDaoImpl.getTemplateIdByReasoncode() due to"+ex.getMessage());
				     throw new SQLException("SQL Exception @TransactionDaoImpl.getTemplateIdByReasoncode() due to "+ex.getMessage());
				}catch (Exception ex) {
			           ex.printStackTrace();
					     localLogger.info("General Exception @TransactionDaoImpl.getTemplateIdByReasoncode() due to"+ex.getMessage());
					     throw new DBException("General Exception @TransactionDaoImpl.getTemplateIdByReasoncode() due to "+ex.getMessage());
				}
				
				return templateId;
			}

			@Override
			public double getWalletHoldBalance(Integer entityId) throws DataServiceException {
				double walletHoldBalance = 0.0;
				try {
					walletHoldBalance=(Double)queryForObject("walletHoldBalance.select", entityId);
				} catch (DataAccessException e) {
					logger.error("Error in walletHoldBalance.select query : "+e);
					e.printStackTrace();
				}
				return walletHoldBalance;
			}

			@Override
			public void updateEntityWalletRemainingBalance(double remainingHoldBalance, Integer entityId, Integer walletControlId) throws DataServiceException {
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("remainingHoldBal", remainingHoldBalance);
				params.put("entityId", entityId);
				params.put("walletControlId", walletControlId);
				try {
					update("entityRemainingBalance.update",params);
				} catch (DataAccessException e) {
					logger.error("Error in entityRemainingBalance.update query : "+e);
					e.printStackTrace();
				}
		  }	
			
			public Integer getWalletControlId(Integer entityId){
				int walletControlId = 0;
				try {
					walletControlId=(Integer)queryForObject("walletControlId.entity.select", entityId);
				} catch (DataAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return walletControlId;
			}
			
			public String getWalletControlBanner(Integer walletControlId){
				String walletBanner = "";
				try {
					walletBanner=(String) queryForObject("entityWalletControl.banner.Id", walletControlId);
				} catch (DataAccessException e) {
					e.printStackTrace();
				}
				return walletBanner;		
			}
	}
		
			
	

	

