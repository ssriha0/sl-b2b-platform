package com.servicelive.lookup.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.servicelive.common.ABaseDao;
import com.servicelive.common.exception.DataServiceException;
import com.servicelive.common.vo.SLAccountVO;
import com.servicelive.common.vo.SLCreditCardVO;
import com.servicelive.lookup.vo.ValueLinkAccountsVO;


/**
 * Class AccountDao.
 */
public class AccountDao extends ABaseDao implements IAccountDao {

	/** logger. */
	static final Logger logger = Logger.getLogger(AccountDao.class);
	

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.profile.dao.IAccountDao#deactivateAccountInfo(com.servicelive.wallet.profile.vo.AccountVO)
	 */
	public boolean deactivateAccountInfo(SLAccountVO account) throws DataServiceException {

		update("deactivate_account.update", account);
		return true;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.lookup.dao.IAccountDao#getAutoFundingIndicator(java.lang.Integer)
	 */
	public SLAccountVO getAutoFundingIndicator(Integer vendorId) throws DataServiceException {

		SLAccountVO acct = new SLAccountVO();
		acct = (SLAccountVO) queryForObject("getAutoFundingIndicator.query", vendorId);

		if (acct == null) {
			acct = new SLAccountVO();
			acct.setEnabledInd(false);
			acct.setAccountId(new Long(0));
		}
		return acct;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.profile.dao.IAccountDao#getV1V2AccountsForBalanceEnquiry()
	 */
	public List<Long> getV1V2AccountsForBalanceEnquiry() throws DataServiceException {

		try {
			return queryForList("v1v2AccountsBalanceEnquiry.query", null);
		} catch (Exception ex) {
			logger.error("AccountDao-->getV1V2AccountsForBalanceEnquiry()-->EXCEPTION-->", ex);
			throw new DataServiceException("AccountDao-->getV1V2AccountsForBalanceEnquiry-->EXCEPTION", ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.servicelive.lookup.dao.IAccountDao#insertAutoFundingDetails(com.servicelive.lookup.vo.AccountVO)
	 */
	public void insertAutoFundingDetails(SLAccountVO account) throws DataServiceException {
		insert("save_autoFunding.insert", account);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.lookup.dao.IAccountDao#isAutoFundedAccount(com.servicelive.lookup.vo.AccountVO)
	 */
	public boolean isAutoFundedAccount(SLAccountVO account) throws DataServiceException {

		int count = 0;
		try {
			count = (Integer) queryForObject("getAutoFundingCount.query", account);
		} catch (Exception ex) {
			logger.info("[TransactionDaoImpl.getAutoFundingDetails - Exception] " + ex.getStackTrace());
		}
		return count > 0;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.profile.dao.IAccountDao#saveAccountInfo(com.servicelive.wallet.profile.vo.AccountVO)
	 */
	public boolean saveAccountInfo(SLAccountVO account) {

		insert("save_account.insert", account);
		return true;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.lookup.dao.IAccountDao#updateAutoFundingDetails(com.servicelive.lookup.vo.AccountVO)
	 */
	public void updateAutoFundingDetails(SLAccountVO account) throws DataServiceException {

		try {
			insert("save_autoFunding.update", account);
		} catch (Exception e) {
			logger.error("Problem occured while updating the auto_funding_service");
			throw new DataServiceException("insertAutoFundingDetails", e);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.servicelive.lookup.dao.IAccountDao#createVLAcountEntry(com.servicelive.lookup.vo.ValueLinkAccountsVO)
	 */
	public void createVLAcountEntry(ValueLinkAccountsVO vlAccountVO) throws DataServiceException{
		try{
			insert("createVLAccounts.insert", vlAccountVO);
		}catch(Exception e){
			logger.error("AccountDao-->createVLAcountEntry()-->EXCEPTION-->", e);
			throw new DataServiceException("AccountDao-->createVLAcountEntry()-->EXCEPTION-->", e);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.servicelive.lookup.dao.IAccountDao#updateVLAccount(com.servicelive.lookup.vo.ValueLinkAccountsVO)
	 */
	public void updateVLAccount(ValueLinkAccountsVO vlAccountVO) throws DataServiceException{
		try{
			update("setv2accountno.update", vlAccountVO);
		}catch(Exception e){
			logger.error("AccountDao-->updateVLAccount()-->EXCEPTION-->", e);
			throw new DataServiceException("AccountDao-->updateVLAccount()-->EXCEPTION-->", e);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.servicelive.lookup.dao.IAccountDao#checkVLAccountValidity(java.lang.Long)
	 */
	public boolean checkVLAccountValidity(Long ledgerEntityId) throws DataServiceException{
		boolean flag = false;
		int cnt;
		try{
			cnt = (Integer)queryForObject("SLAccountCount.query", ledgerEntityId);
			if(cnt > 0){
				flag = true;
			}
		}catch(Exception e){
			logger.error("AccountDao-->checkVLAccountValidity()-->EXCEPTION-->", e);
			throw new DataServiceException("AccountDao-->checkVLAccountValidity()-->EXCEPTION-->", e);
		}
		return flag;
	}	
	 
	public List<SLAccountVO> getAccountDetailsAll(int entityId) {
		List<SLAccountVO> accounts = null;
		try {
			return (List<SLAccountVO>)queryForList("getAccountDetailsAll",new Integer(entityId));
		} 
		catch (Exception e) {
			logger.error("Exception thrown while inserting/updating account details", e);
		}
		return accounts;
	}
	

	public void moveFullfillmentVLAccountToHistory(ValueLinkAccountsVO vlAccountsVO) throws DataServiceException {
		try{
			insert("moveFullfillmentVLAccountToHistory.insert", vlAccountsVO);
			delete("fullfillmentVLAccount.delete", vlAccountsVO);
		}catch(Exception e){
			logger.error("FullfillmentDaoImpl-->moveFullfillmentVLAccountToHistory()-->EXCEPTION-->", e);
			throw new DataServiceException("FullfillmentDaoImpl-->moveFullfillmentVLAccountToHistory()-->EXCEPTION-->", e);
		}
	}	

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.profile.dao.IAccountDao#getAccountDetails(java.lang.Long)
	 */
	public SLAccountVO getAccountDetails(Long accountId) throws DataAccessException{
		logger.debug("LookupFinanceDao-->getAccountDetails()-->START");
		SLAccountVO accountObj = (SLAccountVO) queryForObject("account_hdr.query", accountId);
		if (accountObj == null) {
			accountObj = new SLAccountVO();
			accountObj.setAccountId(null);
		}
		return accountObj;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.profile.dao.IAccountDao#getActiveAccountDetails(java.lang.Long)
	 */
	public SLAccountVO getActiveAccountDetails(Long entityId) throws DataServiceException {
		logger.debug("LookupFinanceDao-->getActiveAccountDetails()-->START");
		SLAccountVO accountObj = (SLAccountVO) queryForObject("account_hdr_active.query", entityId.intValue());
		if (accountObj == null) {
			accountObj = new SLAccountVO();
			accountObj.setAccountId(null);
		}
		return accountObj;
	}


	/* (non-Javadoc)
	 * @see com.servicelive.lookup.dao.ILookupDao#getCreditCardById(java.lang.Long)
	 */
	public SLCreditCardVO getCreditCardById(Long cardId) throws DataServiceException {
		logger.debug("LookupFinanceDao-->getCreditCardById()-->START");
		return (SLCreditCardVO) queryForObject("getCreditCardById", cardId);
	}


	/* (non-Javadoc)
	 * @see com.servicelive.lookup.dao.ILookupDao#getValueLinkAccounts(com.servicelive.lookup.vo.ValueLinkAccountsVO)
	 */
	public ValueLinkAccountsVO getValueLinkAccounts(ValueLinkAccountsVO criteria)throws DataServiceException {
		logger.debug("LookupFinanceDao-->getValueLinkAccounts()-->START");
		return (ValueLinkAccountsVO) queryForObject("getValueLinkAccounts", criteria);
	}
	

	/** dataMap. */
	Map<String,ValueLinkAccountsVO> dataMap = new HashMap<String,ValueLinkAccountsVO>();
	

	/* (non-Javadoc)
	 * @see com.servicelive.lookup.dao.ILookupDao#getFullfillmentSLAccounts()
	 */
	public Map<String,ValueLinkAccountsVO> getSLValueLinkAccounts() throws DataServiceException {
		logger.debug("LookupFinanceDao-->getSLValueLinkAccounts()-->START");
		if(dataMap != null && dataMap.size() > 0){
			return dataMap;
		}
		List<ValueLinkAccountsVO> accountsVO = (ArrayList<ValueLinkAccountsVO>)queryForList("fullfillment_SLAccounts.query", null);
		
		for (ValueLinkAccountsVO data : accountsVO) {
			dataMap.put(data.getAccountCode(), data);
		}
		return dataMap;
	}
	
	public List<String> getV1V2AccountsForBalanceEnquiry(Integer vendorId) throws DataServiceException
 	{
 		try
 		{
 			return queryForList("vendorV1AccountsBalanceEnquiry.query", vendorId);
 		}
		catch (Exception ex) {
			logger.error("FullfillmentDaoImpl-->getV1V2AccountsForBalanceEnquiry()-->EXCEPTION-->", ex);
			throw new DataServiceException("FullfillmentDaoImpl-->getV1V2AccountsForBalanceEnquiry-->EXCEPTION", ex);
		}
 	}
	public long getCreditCardAccountNo(Long entityId)throws DataServiceException{
		Long creditCardAccountId=null;
		try{
			creditCardAccountId=(Long) queryForObject("getCreditCardAccNo.query", entityId);
			}
		catch (Exception ex) {
			logger.error("Exception in getting Account no for Non auto funded buyer"+ entityId+"Exception is:"+ex);
			throw new DataServiceException("Exception getting account no for Non auto funded buyer",ex);
		}
		return creditCardAccountId;
		
	}
	
	
}
