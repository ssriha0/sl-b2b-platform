package com.servicelive.spn.dao.network;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.servicelive.domain.lookup.LookupSPNStatusActionMapper;
import com.servicelive.domain.lookup.LookupSPNStatusOverrideReason;
import com.servicelive.domain.lookup.LookupSPNWorkflowState;
import com.servicelive.domain.spn.detached.MemberManageSearchCriteriaVO;
import com.servicelive.domain.spn.network.SPNProviderFirmState;
import com.servicelive.spn.dao.BaseDao;

/**
 * 
 * @author svanloon
 *
 */
public interface SPNProviderFirmStateDao  extends BaseDao {

	/**
	 * Returns null if cannot lock
	 *  
	 * @param reviewedByUser
	 * @param spnId
	 * @param providerFirmId
	 * @param originalModifiedDate
	 * @return SPNProviderFirmState
	 * @throws Exception
	 */
	public SPNProviderFirmState lock(String reviewedByUser, Integer spnId, Integer providerFirmId, Date originalModifiedDate) throws Exception;

	/**
	 * 
	 * @param spnId
	 * @param providerFirmId
	 * @return SPNProviderFirmState
	 * @throws Exception
	 */
	public List<SPNProviderFirmState> findBySpnIdAndProviderFirmId(List<Integer> spnIds, Integer providerFirmId) throws Exception;
	
	/**
	 * 
	 * @param spnId
	 * @param providerFirmId
	 * @return SPNProviderFirmState
	 * @throws Exception
	 */
	public SPNProviderFirmState findBySpnIdAndProviderFirmId(Integer spnId, Integer providerFirmId) throws Exception;

	/**
	 * 
	 * @param spnId
	 * @return SPNProviderFirmState
	 * @throws Exception
	 */
	public List<SPNProviderFirmState> findBySpnId(Integer spnId) throws Exception;

	/**
	 * 
	 * @param spnId
	 * @param states
	 * @return SPNProviderFirmState
	 * @throws Exception
	 */
	public List<SPNProviderFirmState> findBySpnIdAndSPNWorkFlowStates(Integer spnId, List<LookupSPNWorkflowState> states) throws Exception;

	/**
	 * 
	 * @param spnProviderFirmState
	 * @return SPNProviderFirmState
	 * @throws Exception
	 */
	public SPNProviderFirmState save(SPNProviderFirmState spnProviderFirmState) throws Exception;

	/**
	 * 
	 * @param spnProviderFirmState
	 * @return SPNProviderFirmState
	 * @throws Exception
	 */
	public SPNProviderFirmState update(SPNProviderFirmState spnProviderFirmState) throws Exception;

	/**
	 * 
	 * @param fromSpnId
	 * @param toSpnId
	 * @param states
	 * @return number or records updated
	 * @throws Exception
	 */
	public int copyProviderFirmState(Integer fromSpnId, Integer toSpnId, List<LookupSPNWorkflowState> states) throws Exception;

	/**
	 * 
	 * @param buyerId
	 * @param providerFirmId
	 * @param states
	 * @return List<SPNProviderFirmState>
	 */
	public List<SPNProviderFirmState> findProviderFirmStatus(Integer buyerId, Integer providerFirmId, List<LookupSPNWorkflowState> states);

	/**
	 * 
	 * @param spnId
	 * @param providerFirmId
	 * @param overrideComment
	 * @param reason
	 * @param state
	 * @param modifiedBy
	 * @throws Exception
	 */
	public void updateProviderFirmStatusOverride(Integer providerFirmId, String overrideComment, LookupSPNStatusOverrideReason reason, LookupSPNWorkflowState state, String modifiedBy,LookupSPNStatusActionMapper lookupSPNStatusActionMapper, List<Integer> spnIds, String validityDate) throws Exception;

	/**
	 * 
	 * @param spnId
	 * @return count
	 * @throws Exception
	 */
	public int delete(Integer spnId) throws Exception;
	
	/**
	 * 
	 * @param spnId
	 * @param providerFirmId
	 * @return SPNProviderFirmState
	 * @throws Exception
	 */
	public SPNProviderFirmState findByPrimaryKey(Integer spnId, Integer providerFirmId) throws Exception;
	
	/**
	 * 
	 * @param criteria
	 * @return SPNProviderFirmState
	 */
	public List<SPNProviderFirmState>  searchForMemberManage(MemberManageSearchCriteriaVO criteria) throws Exception;

	/**
	 * 
	 * @return searchable wf_state values
	 */
	public List<String> getValidSearchableStatusList();

	/**
	 * @param spnId
	 * @return
	 */
	public Map<String, Object> getSPNEditInfo(Integer spnId);
}
