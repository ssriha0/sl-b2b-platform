package com.servicelive.wallet.valuelink.mocks;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.servicelive.common.CommonConstants;
import com.servicelive.common.exception.DataServiceException;
import com.servicelive.lookup.vo.ValueLinkAccountsVO;
import com.servicelive.wallet.serviceinterface.vo.ValueLinkEntryVO;
import com.servicelive.wallet.valuelink.dao.IValueLinkDao;
import com.servicelive.wallet.valuelink.vo.ValueLinkRuleVO;

// TODO: Auto-generated Javadoc
/**
 * Class MockValueLinkDao.
 */
public class MockValueLinkDao implements IValueLinkDao {

	/** valueLinkDao. */
	private IValueLinkDao valueLinkDao;

	/** valueLinkEntries. */
	private List<ValueLinkEntryVO> valueLinkEntries = new ArrayList<ValueLinkEntryVO>();

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.valuelink.dao.IValueLinkDao#getNextValueLinkEntryDetails(com.servicelive.wallet.valuelink.vo.ValueLinkEntryVO)
	 */
	public ValueLinkEntryVO getNextValueLinkEntryDetails(ValueLinkEntryVO fullfillmentEntryVO) throws DataServiceException {

		return this.valueLinkDao.getNextValueLinkEntryDetails(fullfillmentEntryVO);
	}


	/**
	 * getValueLinkEntries.
	 * 
	 * @return List<ValueLinkEntryVO>
	 */
	public List<ValueLinkEntryVO> getValueLinkEntries() {

		return valueLinkEntries;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.valuelink.dao.IValueLinkDao#getValueLinkMessageDetail(java.lang.Long)
	 */
	public ValueLinkEntryVO getValueLinkMessageDetail(Long fullfillmentEntryId) throws DataServiceException {

		return this.valueLinkDao.getValueLinkMessageDetail(fullfillmentEntryId);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.valuelink.dao.IValueLinkDao#getValueLinkRules(java.lang.Long, java.lang.Long)
	 */
	public List<ValueLinkRuleVO> getValueLinkRules(Long businessTransaction, Long fundingTypeId) throws DataServiceException {

		return valueLinkDao.getValueLinkRules(businessTransaction, fundingTypeId);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.valuelink.dao.IValueLinkDao#loadTransactionEntries(java.util.List)
	 */
	public void loadTransactionEntries(List<ValueLinkEntryVO> entries) throws DataServiceException {

		try {
			int iSize = entries.size();
			for (int i = 0; i < iSize; i++) {
				ValueLinkEntryVO entry = entries.get(i);
				// If the transaction amount is 0 we can set the reconsiled ind as 0 to indicate it does not have to be reconsiled.
				if (entry.getTransAmount() == 0.0 && CommonConstants.BUSINESS_TRANSACTION_NEW_PROVIDER != entry.getBusTransId().intValue()
					&& CommonConstants.BUSINESS_TRANSACTION_NEW_BUYER != entry.getBusTransId().intValue()) {
					entry.setReconsiledInd(0);
					entry.setReconsiledDate(new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()));
				}
			}

			valueLinkEntries.addAll(entries);
		} catch (Exception ex) {
			throw new DataServiceException("FullfillmentDaoImpl-->loadTransactionEntries-->EXCEPTION", ex);
		}
	}

	/**
	 * reset.
	 * 
	 * @return void
	 */
	public void reset() {

		valueLinkEntries.clear();
	}

	/**
	 * setValueLinkDao.
	 * 
	 * @param valueLinkDao 
	 * 
	 * @return void
	 */
	public void setValueLinkDao(IValueLinkDao valueLinkDao) {

		this.valueLinkDao = valueLinkDao;
	}


	/* (non-Javadoc)
	 * @see com.servicelive.wallet.valuelink.dao.IValueLinkDao#insertProcessResponseLogging(java.lang.Long, java.lang.String)
	 */
	public void insertProcessResponseLogging(Long valueLinkEntryId, String responseStr) throws DataServiceException {
		this.valueLinkDao.insertProcessResponseLogging(valueLinkEntryId, responseStr);
	}


	/* (non-Javadoc)
	 * @see com.servicelive.wallet.valuelink.dao.IValueLinkDao#insertProcessRequestLogging(java.lang.Long, java.lang.String)
	 */
	public void insertProcessRequestLogging(Long valueLinkEntryId, String requestStr) throws DataServiceException {
		this.valueLinkDao.insertProcessRequestLogging(valueLinkEntryId, requestStr);
	}


	/* (non-Javadoc)
	 * @see com.servicelive.wallet.valuelink.dao.IValueLinkDao#getActionCodeIdAndStatus(java.lang.String)
	 */
	public Map<String, Object> getActionCodeIdAndStatus(String actionCode) throws DataServiceException {
		return this.valueLinkDao.getActionCodeIdAndStatus(actionCode);
	}


	/* (non-Javadoc)
	 * @see com.servicelive.wallet.valuelink.dao.IValueLinkDao#updateReconcileStatus(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Double)
	 */
	public void updateReconcileStatus(Long valueLinkEntryId, Long reconciledInd, Long primaryAccountNo, Double vlBalance) throws DataServiceException {
		this.updateReconcileStatus(valueLinkEntryId, reconciledInd, primaryAccountNo, vlBalance);
	}


	/* (non-Javadoc)
	 * @see com.servicelive.wallet.valuelink.dao.IValueLinkDao#updateFullfillmentVLAccount(com.servicelive.wallet.valuelink.vo.ValueLinkEntryVO)
	 */
	public void updateFullfillmentVLAccount(ValueLinkEntryVO valueLinkEntryVO) throws DataServiceException {

	}


	/* (non-Javadoc)
	 * @see com.servicelive.wallet.valuelink.dao.IValueLinkDao#updateFullfillmentLogStatus(java.lang.Long, java.lang.Integer)
	 */
	public void updateFullfillmentLogStatus(Long fullfillment_grp_id, Integer status) throws DataServiceException {

	}


	/* (non-Javadoc)
	 * @see com.servicelive.wallet.valuelink.dao.IValueLinkDao#updateV1V2AccountBalanceEnquiry(com.servicelive.wallet.valuelink.vo.ValueLinkEntryVO)
	 */
	public void updateV1V2AccountBalanceEnquiry(ValueLinkEntryVO valueLinkEntryVO) throws DataServiceException {

	}
	
	/* (non-Javadoc)
	 * @see com.servicelive.wallet.valuelink.dao.IValueLinkDao#updateV1V2AccountBalance(com.servicelive.wallet.valuelink.vo.ValueLinkEntryVO)
	 */
	public void updateV1V2AccountBalance(ValueLinkEntryVO valueLinkEntryVO) throws DataServiceException{
		
	}



	/* (non-Javadoc)
	 * @see com.servicelive.wallet.valuelink.dao.IValueLinkDao#getActionCodeDesc(java.lang.String)
	 */
	public String getActionCodeDesc(String actionCode) {

		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.valuelink.dao.IValueLinkDao#moveFullfillmentVLAccountToHistory(com.servicelive.lookup.vo.ValueLinkAccountsVO)
	 */
	public void moveFullfillmentVLAccountToHistory(ValueLinkAccountsVO fullfillmentVLAccountsVO) {

		// TODO Auto-generated method stub
		
	}


	public boolean isValueLinkUp() throws DataServiceException {
		return false;
	}


	public void updateValueLinkStatus(boolean up)
			throws DataServiceException {
	}


	public Integer checkValueLinkReconciledIndicator(String soId)
			throws DataServiceException {
		// TODO Auto-generated method stub
		return null;
	}


	public Integer getCountOfValueLinkRecordsForSO(String soId)
			throws DataServiceException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Integer isACHTransPending(String soId)
	throws DataServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Integer hasPreviousAddOn(String serviceOrderId)throws DataServiceException
	
	{
		return null;
	}

	public ValueLinkEntryVO getFirstUnReconciledTrans(Long groupId)
			throws DataServiceException {
		// TODO Auto-generated method stub
		return null;
	}


	public List<ValueLinkEntryVO> getValueLinkMessageDetailByGroupId(
			Long groupId) throws DataServiceException {
		// TODO Auto-generated method stub
		return null;
	}


	public void insertAdminToolLogging(ArrayList<String> valueLinkIds,
			String comments, String userName) throws DataServiceException {
		// TODO Auto-generated method stub
		
	}


	public void insertAdminToolLogging(String valueLinkEntryId,
			String comments, String userName) throws DataServiceException {
		// TODO Auto-generated method stub
		
	}


	public boolean isActivationRequestPendingForEntity(Long entityId)
			throws DataServiceException {
		// TODO Auto-generated method stub
		return false;
	}


	public void deleteProcessResponseLogging(Long valueLinkEntryId)
			throws DataServiceException {
		// TODO Auto-generated method stub
		
	}


	public void updateReconcileStatusToNull(Long fulfillmentEntryId)
			throws DataServiceException {
		// TODO Auto-generated method stub
		
	}


	public boolean checkVLAccountValidity(Long ledgerEntityId)
			throws DataServiceException {
		// TODO Auto-generated method stub
		return false;
	}


	public void createVLAcountEntry(ValueLinkAccountsVO vlAccountVO)
			throws DataServiceException {
		// TODO Auto-generated method stub
		
	}


	public ValueLinkAccountsVO getValueLinkAccounts(ValueLinkAccountsVO criteria)
			throws DataServiceException {
		// TODO Auto-generated method stub
		return null;
	}


	public void updateVLAccount(ValueLinkAccountsVO vlAccountVO)
			throws DataServiceException {
		// TODO Auto-generated method stub
		
	}


	public ValueLinkAccountsVO getSLValueLinkAccounts(String accountName)
			throws DataServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	public Long insertFullfillmentGroup() throws DataServiceException {
		// TODO Auto-generated method stub
		return null;
	}


	public Long insertFullfillmentEntry(ValueLinkEntryVO details)
			throws DataServiceException {
		// TODO Auto-generated method stub
		return null;
	}
}
