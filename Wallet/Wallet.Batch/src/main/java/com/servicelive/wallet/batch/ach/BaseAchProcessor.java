package com.servicelive.wallet.batch.ach;

import com.servicelive.common.exception.DataServiceException;
import com.servicelive.common.vo.SLAccountVO;
import com.servicelive.lookup.dao.IAccountDao;
import com.servicelive.wallet.batch.BaseFileProcessor;

public abstract class BaseAchProcessor extends BaseFileProcessor {
	
	protected IAccountDao accountDao;
	
	public void setAccountDao(IAccountDao accountDao) {
	
		this.accountDao = accountDao;
	}
	
	protected void deactivateAccount( long accountId ) throws DataServiceException {
		// updating the account info in account_hdr table
		SLAccountVO account = new SLAccountVO();
		account.setAccountId(accountId);
		account.setEnabledInd(false);
		accountDao.deactivateAccountInfo(account);
	}

}
