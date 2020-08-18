/**
 * 
 */
package com.servicelive.wallet.valuelink.dao;

import java.util.List;
import java.util.Map;

import com.servicelive.common.exception.DataServiceException;
import com.servicelive.lookup.vo.ValueLinkAccountsVO;
import com.servicelive.wallet.serviceinterface.vo.ValueLinkEntryVO;
import com.servicelive.wallet.valuelink.vo.ValueLinkRuleVO;

// TODO: Auto-generated Javadoc
/**
 * Interface IValueLinkDao.
 */
/**
 * @author yburhani
 *
 */
public interface IValueLinkDao {

	/**
	 * getCountOfVLRecordsForSO.
	 * 
	 * @param soId 
	 * 
	 * @return Integer
	 * 
	 * @throws DataServiceException 
	 */
	public Integer getCountOfValueLinkRecordsForSO(String soId) throws DataServiceException;

	
	/**
	 * getNextValueLinkEntryDetails.
	 * 
	 * @param fullfillmentEntryVO 
	 * 
	 * @return ValueLinkEntryVO
	 * 
	 * @throws DataServiceException 
	 */
	public ValueLinkEntryVO getNextValueLinkEntryDetails(ValueLinkEntryVO fullfillmentEntryVO) throws DataServiceException;


	/**
	 * getValueLinkMessageDetail.
	 * 
	 * @param fullfillmentEntryId 
	 * 
	 * @return ValueLinkEntryVO
	 * 
	 * @throws DataServiceException 
	 */
	public ValueLinkEntryVO getValueLinkMessageDetail(Long fullfillmentEntryId) throws DataServiceException;

	/**
	 * @param groupId
	 * @return
	 * @throws DataServiceException
	 */
	public List<ValueLinkEntryVO> getValueLinkMessageDetailByGroupId(Long groupId) throws DataServiceException;

	/**
	 * @param groupId
	 * @return
	 * @throws DataServiceException
	 */
	public ValueLinkEntryVO getFirstUnReconciledTrans(Long groupId) throws DataServiceException;
	
	/**
	 * getValueLinkRules.
	 * 
	 * @param businessTransaction 
	 * @param fundingTypeId 
	 * 
	 * @return List<ValueLinkRuleVO>
	 * 
	 * @throws DataServiceException 
	 */
	public List<ValueLinkRuleVO> getValueLinkRules(Long businessTransaction, Long fundingTypeId) throws DataServiceException;

	/**
	 * loadTransactionEntries.
	 * 
	 * @param entries 
	 * 
	 * @return void
	 * 
	 * @throws DataServiceException 
	 */
	public void loadTransactionEntries(List<ValueLinkEntryVO> entries) throws DataServiceException;


    public Long insertFullfillmentGroup() throws DataServiceException;

	/**
	 * insertProcessResponseLogging.
	 * 
	 * @param valueLinkEntryId 
	 * @param responseStr 
	 * 
	 * @return void
	 * 
	 * @throws DataServiceException 
	 */
	public void insertProcessResponseLogging(Long valueLinkEntryId, String responseStr) throws DataServiceException;

	/**
	 * deleteProcessResponseLogging
	 * 
	 * @param valueLinkEntryId
	 * 
	 * @return void
	 * 
	 * @throws DataServiceException
	 */
	public void deleteProcessResponseLogging(Long valueLinkEntryId) throws DataServiceException;
	
	/**
	 * insertProcessRequestLogging.
	 * 
	 * @param valueLinkEntryId 
	 * @param requestStr 
	 * 
	 * @return void
	 * 
	 * @throws DataServiceException 
	 */
	public void insertProcessRequestLogging(Long valueLinkEntryId, String requestStr) throws DataServiceException;


	/**
	 * getActionCodeIdAndStatus.
	 * 
	 * @param actionCode 
	 * 
	 * @return Map<String,Object>
	 * 
	 * @throws DataServiceException 
	 */
	public Map<String, Object> getActionCodeIdAndStatus(String actionCode) throws DataServiceException;


	/**
	 * updateReconcileStatus.
	 * 
	 * @param valueLinkEntryId 
	 * @param reconciledInd 
	 * @param primaryAccountNo 
	 * @param vlBalance 
	 * 
	 * @return void
	 * 
	 * @throws DataServiceException 
	 */
	public void updateReconcileStatus(Long valueLinkEntryId, Long reconciledInd, Long primaryAccountNo, Double vlBalance) throws DataServiceException;

	/**
	 * updateReconcileStatusToNull
	 * 
	 * @param fulfillmentEntryId 
	 * 
	 * @return void
	 * 
	 * @throws DataServiceException
	 */
	public void updateReconcileStatusToNull(Long fulfillmentEntryId) throws DataServiceException;
	
	/**
	 * updateFullfillmentVLAccount.
	 * 
	 * @param valueLinkEntryVO 
	 * 
	 * @return void
	 * 
	 * @throws DataServiceException 
	 */
	public void updateFullfillmentVLAccount(ValueLinkEntryVO valueLinkEntryVO) throws DataServiceException;


	/**
	 * updateFullfillmentLogStatus.
	 * 
	 * @param fullfillment_grp_id 
	 * @param status 
	 * 
	 * @return void
	 * 
	 * @throws DataServiceException 
	 */
	public void updateFullfillmentLogStatus(Long fullfillment_grp_id, Integer status) throws DataServiceException;


	/**
	 * updateV1V2AccountBalanceEnquiry.
	 * 
	 * @param valueLinkEntryVO 
	 * 
	 * @return void
	 * 
	 * @throws DataServiceException 
	 */
	public void updateV1V2AccountBalanceEnquiry(ValueLinkEntryVO valueLinkEntryVO) throws DataServiceException;
	
	
	/**
	 * updateV1V2AccountBalance.
	 * 
	 * @param valueLinkEntryVO 
	 * 
	 * @return void
	 * 
	 * @throws DataServiceException 
	 */
	public void updateV1V2AccountBalance(ValueLinkEntryVO valueLinkEntryVO) throws DataServiceException;


	/**
	 * insertFullfillmentEntry.
	 * 
	 * @param details 
	 * 
	 * @return void
	 * 
	 * @throws DataServiceException 
	 */
	public Long insertFullfillmentEntry(ValueLinkEntryVO details) throws DataServiceException;


	/**
	 * getActionCodeDesc.
	 * 
	 * @param actionCode 
	 * 
	 * @return String
	 * 
	 * @throws DataServiceException 
	 */
	public String getActionCodeDesc(String actionCode) throws DataServiceException;


	/**
	 * @return
	 * @throws DataServiceException
	 */
	public boolean isValueLinkUp() throws DataServiceException;


	/**
	 * @param up
	 * @throws DataServiceException
	 */
	public void updateValueLinkStatus(boolean up)throws DataServiceException;

	/**
	 * isActivationRequestPendingForEntity
	 * 
	 * @param entityId
	 * @return
	 * @throws DataServiceException
	 */
	public boolean isActivationRequestPendingForEntity(Long entityId) throws DataServiceException;
	
	public void insertAdminToolLogging(String valueLinkEntryId, String comments, String userName)throws DataServiceException;

	public void createVLAcountEntry(ValueLinkAccountsVO vlAccountVO) throws DataServiceException;
	
	public boolean checkVLAccountValidity(Long ledgerEntityId) throws DataServiceException;
	
	public void moveFullfillmentVLAccountToHistory(ValueLinkAccountsVO vlAccountsVO) throws DataServiceException;

	public ValueLinkAccountsVO getValueLinkAccounts(ValueLinkAccountsVO criteria)throws DataServiceException;

	public void updateVLAccount(ValueLinkAccountsVO vlAccountVO)throws DataServiceException;

	public ValueLinkAccountsVO getSLValueLinkAccounts(String accountName)throws DataServiceException;
	
	public Integer isACHTransPending(String soId) throws DataServiceException;

	public Integer hasPreviousAddOn(String serviceOrderId) throws DataServiceException;
	/**
	 * deleteVLAccountsWhenAcNoisZero
	 * 
	 * deleting the entry in VLAccounts table if the V1account is zero
	 * 
	 * @param entityId
	 * @return
	 * @throws DataServiceException
	 */
	public void deleteVLAccountsWhenAcNoisZero(ValueLinkAccountsVO vlAccountsVO) throws DataServiceException;
	
	public Double getCompletedSOLedgerAmount(long vendorId) throws DataServiceException ;
	
	public Double getSLACreditAmount(long vendorId) throws DataServiceException;
}
