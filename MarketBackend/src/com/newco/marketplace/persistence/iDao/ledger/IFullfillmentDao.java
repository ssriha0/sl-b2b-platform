/**
 * 
 */
package com.newco.marketplace.persistence.iDao.ledger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.dto.vo.ach.FieldDetailVO;
import com.newco.marketplace.dto.vo.fullfillment.EntityPromoCodesVO;
import com.newco.marketplace.dto.vo.fullfillment.FullfillmentAdminToolVO;
import com.newco.marketplace.dto.vo.fullfillment.FullfillmentEntryVO;
import com.newco.marketplace.dto.vo.fullfillment.FullfillmentRuleVO;
import com.newco.marketplace.dto.vo.fullfillment.FullfillmentVLAccountsVO;
import com.newco.marketplace.dto.vo.fullfillment.VLHeartbeatVO;
import com.newco.marketplace.exception.core.DataServiceException;

/**
 * @author schavda
 *
 */
public interface IFullfillmentDao {
	
	public List getFullfillmentRules(FullfillmentRuleVO fullfillmentRuleVO) throws DataServiceException;
	
	public FullfillmentVLAccountsVO getFullfillmentVLAccounts(FullfillmentVLAccountsVO fullfillmentVLAccountsVO) throws DataServiceException;
	
	public void loadTransactionEntries(ArrayList<FullfillmentEntryVO> fullfillmentEntries) throws DataServiceException;
	
	public Map getFullfillmentSLAccounts() throws DataServiceException;
	
	public FullfillmentEntryVO getFullfillmentMessageDetail(Long fullfillmentEntryId) throws DataServiceException;
	
	public Long getNextFullfillmentEntryId(FullfillmentEntryVO fullfillmentEntryVO) throws DataServiceException;
	
	public FullfillmentEntryVO getNextFullfillmentEntryDetails(FullfillmentEntryVO fullfillmentEntryVO) throws DataServiceException;
	
	public void updateReconcileStatus(Long fullfillmentEntryId, Long reconciledInd, Long primaryAccountNo,Double vlBalance)
	throws DataServiceException;
	
	public FullfillmentEntryVO getFullfillmentEntry(Integer groupId, Integer sortOrder)
	throws DataServiceException;
	
	public Map<String, Object> getActionCodeId(String actionCode) throws DataServiceException;
	
	public void updateFullfillmentLogStatus(Long fullfillment_grp_id, Integer status)
	throws DataServiceException;
	
	public boolean checkVLAccountValidity(Long ledgerEntityId) throws DataServiceException;
	
	public void  createVLAcountEntry(FullfillmentVLAccountsVO vlAccountVO) throws DataServiceException;
	
	public void updateVLAccount(FullfillmentVLAccountsVO vlAccountVO) throws DataServiceException;
	
	public void updateFullfillmentVLAccount(FullfillmentEntryVO fullfillmentEntryVO) throws DataServiceException;
	
	public EntityPromoCodesVO getEntityPromoCodes(EntityPromoCodesVO entityPromoCodesVO) throws DataServiceException;
	
	public VLHeartbeatVO getValuelinkStatuses() throws DataServiceException;
	
	public void updateSharpVLStatus(String system, String status);
	
	public void createHeartBeatMessage(String heartBeatId) throws DataServiceException;
	
	public void deleteHeartBeatMessage(String heartBeatId) throws DataServiceException;
	
	public void moveFullfillmentVLAccountToHistory(FullfillmentVLAccountsVO fullfillmentVLAccountsVO) throws DataServiceException;
	
	public void replaceFullfillmentGroupId(FullfillmentEntryVO fullfillmentEntryVO) throws DataServiceException;
	
	public void sortFullfillmentEntryIds(FullfillmentEntryVO fullfillmentEntryVO) throws DataServiceException;
	
	public Map<String, String> getExternalQueueStatus() throws DataServiceException;
	
	public List<FullfillmentEntryVO> getRequestsWithNoResponses() throws DataServiceException;
	
	public List<FullfillmentEntryVO> getFulfillmentRecordsByGroupId(Long fullfillmentEntryId) throws DataServiceException;
	
	public String getActionCodeDesc(String actionCode) throws DataServiceException;

	public void insertFullfillmentEntry(FullfillmentEntryVO fullfillmentEntryVO)  throws DataServiceException;
	
	public Long checkBuyerAccountReqExists(Integer buyerId)  throws DataServiceException;
	
	public void updateFullfillmentGrpId(Long fullfillmentGrpId, String soId);
	
	public void updateNonReconsiledInd() throws DataServiceException;
	
	public Long getSLAccountForHeartBeat() throws DataServiceException;
	
	public void insertProcessResponseLogging(Long fullfillmentEntryId, String bytes) throws DataServiceException;
	
	public void insertProcessRequestLogging(Long fullfillmentEntryId, String bytes) throws DataServiceException;
	
	public void updateLastPutTimeAndDepth(String queueName,String pan) throws DataServiceException;
	
	public void updateLastTransmittedTimeAndDepth(String queueName) throws DataServiceException;
	
	public void insertAdminToolLogging(FullfillmentAdminToolVO adminToolVO)throws DataServiceException;
	
	public FullfillmentVLAccountsVO getFullfillmentVLAccountsByEntityId(Long entityId) throws DataServiceException;

	
	public Integer checkFullfillmentReconciledIndicator(String soId) throws DataServiceException ;
	
	public Integer getCountOfFullfillmentRecordsForSO(String soId) throws DataServiceException ;
	
	public List<FullfillmentEntryVO> getAllFullfillmentData(int startNumber, int fetchSize) throws DataServiceException ;
	
	public Integer getCountOfFullfillmentData() throws DataServiceException ;
	
 	public ArrayList<FieldDetailVO> retrieveClosedLoopFileTemplateDetais(String identifier)throws DataServiceException ;
 	
 	public void insertNMMResponseLogging(Long fullfillmentEntryId, String bytes) throws DataServiceException;
 	
 	public void insertBalanceInquiryLogging(Long fullfillmentEntryId, String bytes) throws DataServiceException;
 	
 	public Long getSLAccountForSL3() throws DataServiceException;
 	
 	public List<FullfillmentRuleVO> getFullfillmentRulesForGroup(Long fullfillmentGroupId) throws DataServiceException;
 	
 	public void updatePanAndStateCdForActivations(FullfillmentEntryVO fullfillmentEntryVO) throws DataServiceException;
 	
 	public List<String> getV1V2AccountsForBalanceEnquiry() throws DataServiceException;
 	
 	public void updateV1V2AccountBalanceEnquiry(FullfillmentEntryVO fullfillmentEntryVO) throws DataServiceException;
 	
 	public List<Long> getFullfillmentEntryIdsForGroup(Long fullfillmentGrpId) throws DataServiceException;
 	
 	public  FullfillmentRuleVO getFullfillmentRuleData(Integer fullfillmentRuleId) throws DataServiceException;
 	
 	public FullfillmentEntryVO getFirstUnReconciledTrans(Long fullfillmentGroupId) throws DataServiceException;

}
