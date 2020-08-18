package com.newco.marketplace.persistence.iDao.financeManager;

import com.newco.marketplace.dto.vo.ledger.Account;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.vo.provider.FinancialProfileVO;

public interface IFinanceManagerDAO{

	public void saveFinancialProfile(FinancialProfileVO financialProfileVO)
			throws DataServiceException;

	public FinancialProfileVO getVendorDetails(String vendorId)
			throws DataServiceException;

	public void saveAccountDetails(Account account) throws DataServiceException;
	
	public Integer returnAccountCount(Long accountId) throws DataServiceException;
	
	public String getAppPropertiesValue(String key) throws DataServiceException;
}
