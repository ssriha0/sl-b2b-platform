package com.servicelive.lookup.mocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.servicelive.common.exception.DataServiceException;
import com.servicelive.common.vo.SLAccountVO;
import com.servicelive.common.vo.SLCreditCardVO;
import com.servicelive.lookup.dao.IAccountDao;
import com.servicelive.lookup.vo.ValueLinkAccountsVO;

/**
 * Class MockAccountDao.
 */
public class MockAccountDao implements IAccountDao {

	/* (non-Javadoc)
	 * @see com.servicelive.lookup.dao.IAccountDao#deactivateAccountInfo(com.servicelive.lookup.vo.AccountVO)
	 */
	public boolean deactivateAccountInfo(SLAccountVO account)
			throws DataServiceException {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.lookup.dao.IAccountDao#getAutoFundingIndicator(java.lang.Integer)
	 */
	public SLAccountVO getAutoFundingIndicator(Integer vendorId)
			throws DataServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.lookup.dao.IAccountDao#getV1V2AccountsForBalanceEnquiry()
	 */
	public List<Long> getV1V2AccountsForBalanceEnquiry()
			throws DataServiceException {
		List<Long> accounts = new ArrayList<Long>();
		accounts.add(new Long(7777007861326189L));
		accounts.add(new Long(7777008374704775L));
		accounts.add(new Long(7777008405952446L));
		accounts.add(new Long(7777008405963761L));

		return accounts;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.lookup.dao.IAccountDao#insertAutoFundingDetails(com.servicelive.lookup.vo.AccountVO)
	 */
	public void insertAutoFundingDetails(SLAccountVO account)
			throws DataServiceException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.servicelive.lookup.dao.IAccountDao#isAutoFundedAccount(com.servicelive.lookup.vo.AccountVO)
	 */
	public boolean isAutoFundedAccount(SLAccountVO account)
			throws DataServiceException {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.lookup.dao.IAccountDao#saveAccountInfo(com.servicelive.lookup.vo.AccountVO)
	 */
	public boolean saveAccountInfo(SLAccountVO account)
			throws DataServiceException {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.lookup.dao.IAccountDao#updateAutoFundingDetails(com.servicelive.lookup.vo.AccountVO)
	 */
	public void updateAutoFundingDetails(SLAccountVO account)
			throws DataServiceException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.servicelive.lookup.dao.IAccountDao#updateVLAccount(com.servicelive.lookup.vo.ValueLinkAccountsVO)
	 */
	public void updateVLAccount(ValueLinkAccountsVO vlAccountVO) throws DataServiceException {
		//
	}

	/* (non-Javadoc)
	 * @see com.servicelive.lookup.dao.IAccountDao#createVLAcountEntry(com.servicelive.lookup.vo.ValueLinkAccountsVO)
	 */
	public void createVLAcountEntry(ValueLinkAccountsVO vlAccountVO) throws DataServiceException {
		//
	}

	/* (non-Javadoc)
	 * @see com.servicelive.lookup.dao.IAccountDao#checkVLAccountValidity(java.lang.Long)
	 */
	public boolean checkVLAccountValidity(Long ledgerEntityId) throws DataServiceException {
		return true;
	}

	public List<SLAccountVO> getAccountDetailsAll(int entityId) {
		// TODO Auto-generated method stub
		return null;
	}

	public void moveFullfillmentVLAccountToHistory(
			ValueLinkAccountsVO vlAccountsVO) throws DataServiceException {
		// TODO Auto-generated method stub
		
	}

	public SLAccountVO getAccountDetails(Long accountId)
			throws DataServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	public SLAccountVO getActiveAccountDetails(Long entityId)
			throws DataServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	public SLCreditCardVO getCreditCardById(Long cardId)
			throws DataServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<String, ValueLinkAccountsVO> getSLValueLinkAccounts()
			throws DataServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	public ValueLinkAccountsVO getValueLinkAccounts(ValueLinkAccountsVO criteria)
			throws DataServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> getV1V2AccountsForBalanceEnquiry(Integer vendorId)
			throws DataServiceException {
		// TODO Auto-generated method stub
		return null;
	}

}
