package com.newco.marketplace.persistence.daoImpl.financeManager;

import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.ledger.Account;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.financeManager.IFinanceManagerDAO;
import com.newco.marketplace.utils.StackTraceHelper;
import com.newco.marketplace.vo.provider.FinancialProfileVO;
import com.sears.os.dao.impl.ABaseImplDao;

public class FinanceManagerDAOImpl extends ABaseImplDao implements IFinanceManagerDAO {
	private static final Logger logger = Logger.getLogger(FinanceManagerDAOImpl.class.getName());
	
	
	public void saveFinancialProfile(FinancialProfileVO financialProfileVO) throws DataServiceException{
		if (logger.isDebugEnabled())
			logger.debug("inserting financial Profile: " + financialProfileVO);
		insert("financialprofile.insert", financialProfileVO);
	}
	public FinancialProfileVO getVendorDetails(String vendorId) throws DataServiceException{
		FinancialProfileVO financialProfileVO = new FinancialProfileVO();
		try {
			financialProfileVO = (FinancialProfileVO) queryForObject("fmVendorDetails.query",
					vendorId);
		} catch (Exception ex) {
			logger.info("[FinanceManagerDAOImpl.query - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
		return financialProfileVO;
	}
	public void saveAccountDetails(Account account) throws DataServiceException{
		if (logger.isDebugEnabled())
			logger.debug("inserting account details : " + account);
		insert("save_account.insert", account);
	}
	public Integer returnAccountCount(Long accountId) throws DataServiceException{
		Integer accountCount = null;
		try {
			accountCount = (Integer) queryForObject("fmReturnAccountCount.query", accountId);
		} catch (Exception ex) {
			logger.info("[FinanceManagerDAOImpl.saveAccountDetails.query - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
		return accountCount;
	}
	
	@Override
	public String getAppPropertiesValue(String key) throws DataServiceException{
		String value = null;
		try{
			value = (String)queryForObject("application_propertiesbykey.query", key);
		}catch(Exception e){
			throw new DataServiceException("Error", e);
		}
		return value;
	}

}
