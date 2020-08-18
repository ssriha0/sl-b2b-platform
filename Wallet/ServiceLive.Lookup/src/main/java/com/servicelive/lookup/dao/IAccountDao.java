package com.servicelive.lookup.dao;

import java.util.List;
import java.util.Map;

import com.servicelive.common.exception.DataServiceException;
import com.servicelive.common.vo.SLAccountVO;
import com.servicelive.common.vo.SLCreditCardVO;
import com.servicelive.lookup.vo.ValueLinkAccountsVO;

// TODO: Auto-generated Javadoc
/**
 * Interface IAccountDao.
 */
public interface IAccountDao {

	/**
	 * deactivateAccountInfo.
	 * 
	 * @param account 
	 * 
	 * @return boolean
	 * 
	 * @throws DataServiceException 
	 */
	public boolean deactivateAccountInfo(SLAccountVO account) throws DataServiceException;
	

	/**
	 * getAutoFundingIndicator.
	 * 
	 * @param vendorId 
	 * 
	 * @return AccountVO
	 * 
	 * @throws DataServiceException 
	 */
	public SLAccountVO getAutoFundingIndicator(Integer vendorId) throws DataServiceException;

	/**
	 * getV1V2AccountsForBalanceEnquiry.
	 * 
	 * @return List<Long>
	 * 
	 * @throws DataServiceException 
	 */
	public List<Long> getV1V2AccountsForBalanceEnquiry() throws DataServiceException;

	/**
	 * insertAutoFundingDetails.
	 * 
	 * @param account 
	 * 
	 * @return void
	 * 
	 * @throws DataServiceException 
	 */
	public void insertAutoFundingDetails(SLAccountVO account) throws DataServiceException;

	/**
	 * isAutoFundedAccount.
	 * 
	 * @param account 
	 * 
	 * @return boolean
	 * 
	 * @throws DataServiceException 
	 */
	public boolean isAutoFundedAccount(SLAccountVO account) throws DataServiceException;

	/**
	 * saveAccountInfo.
	 * 
	 * @param account 
	 * 
	 * @return boolean
	 * 
	 * @throws DataServiceException 
	 */
	public boolean saveAccountInfo(SLAccountVO account) throws DataServiceException;

	/**
	 * updateAutoFundingDetails.
	 * 
	 * @param account 
	 * 
	 * @return void
	 * 
	 * @throws DataServiceException 
	 */
	public void updateAutoFundingDetails(SLAccountVO account) throws DataServiceException;
	/**
	 * createVLAcountEntry.
	 * 
	 * @param vlAccountVO 
	 * 
	 * @return void
	 * 
	 * @throws DataServiceException 
	 */
	public void createVLAcountEntry(ValueLinkAccountsVO vlAccountVO) throws DataServiceException;


	/**
	 * updateVLAccount.
	 * 
	 * @param vlAccountVO 
	 * 
	 * @return void
	 * 
	 * @throws DataServiceException 
	 */
	public void updateVLAccount(ValueLinkAccountsVO vlAccountVO) throws DataServiceException;


	/**
	 * checkVLAccountValidity.
	 * 
	 * @param ledgerEntityId 
	 * 
	 * @return boolean
	 * 
	 * @throws DataServiceException 
	 */
	public boolean checkVLAccountValidity(Long ledgerEntityId) throws DataServiceException;


	/**
	 * @param entityId
	 * @return
	 */
	public List<SLAccountVO> getAccountDetailsAll(int entityId);


	/**
	 * @param vlAccountsVO
	 * @throws DataServiceException
	 */
	public void moveFullfillmentVLAccountToHistory(ValueLinkAccountsVO vlAccountsVO) throws DataServiceException;

	/**
	 * getAccountDetails.
	 * 
	 * @param accountId 
	 * 
	 * @return AccountVO
	 */
	public SLAccountVO getAccountDetails(Long accountId) throws DataServiceException;

	/**
	 * getActiveAccountDetails.
	 * 
	 * @param entityId 
	 * 
	 * @return AccountVO
	 */
	public SLAccountVO getActiveAccountDetails(Long entityId) throws DataServiceException;

	/**
	 * getCreditCardById.
	 * 
	 * @param cardId 
	 * 
	 * @return CreditCardAccountVO
	 * 
	 * @throws DataServiceException 
	 */
	public SLCreditCardVO getCreditCardById(Long cardId) throws DataServiceException;

	/**
	 * getValueLinkAccounts.
	 * 
	 * @param criteria 
	 * 
	 * @return ValueLinkAccountsVO
	 * 
	 * @throws DataServiceException 
	 */
	public ValueLinkAccountsVO getValueLinkAccounts(ValueLinkAccountsVO criteria) throws DataServiceException;

	/**
	 * getSLValueLinkAccounts.
	 * 
	 * @return Map<String,ValueLinkAccountsVO>
	 * 
	 * @throws DataServiceException 
	 */
	public Map<String,ValueLinkAccountsVO> getSLValueLinkAccounts() throws DataServiceException ;
	
	/**
	 * @param vendorId
	 * @return List<String>
	 * @throws DataServiceException
	 */
	public List<String> getV1V2AccountsForBalanceEnquiry(Integer vendorId) throws DataServiceException;
	
	public long getCreditCardAccountNo(Long entityId)throws DataServiceException;

}
