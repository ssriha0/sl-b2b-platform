package com.servicelive.spn.dao.network;

import java.util.List;

import com.servicelive.domain.lookup.LookupPerformanceLevel;
import com.servicelive.domain.lookup.LookupSPNStatusActionMapper;
import com.servicelive.domain.lookup.LookupSPNStatusOverrideReason;
import com.servicelive.domain.lookup.LookupSPNWorkflowState;
import com.servicelive.domain.spn.detached.MemberManageSearchCriteriaVO;
import com.servicelive.domain.spn.network.SPNServiceProviderState;
import com.servicelive.spn.dao.BaseDao;

public interface SPNServiceProviderStateDao extends BaseDao {

	/**
	 * 
	 * @param spnId
	 * @param serviceProviderId
	 * @param performanceLevelChangeComment
	 * @param performanceLevel
	 * @param modifiedBy
	 * @throws Exception
	 */
	public void updateServiceProviderStatusPerformanceLevel(Integer spnId, Integer serviceProviderId, String performanceLevelChangeComment, LookupPerformanceLevel performanceLevel, LookupSPNStatusActionMapper lookupSPNStatusActionMapper, String modifiedBy) throws Exception;

	/**
	 * 
	 * @param spnId
	 * @param serviceProviderId
	 * @param overrideComment
	 * @param reason
	 * @param state
	 * @param modifiedBy
	 * @param lookupSPNStatusActionMapper
	 * @throws Exception
	 */
	public void updateServiceProviderStatusOverride(List<Integer> spnIds, Integer serviceProviderId, String overrideComment, LookupSPNStatusOverrideReason reason, LookupSPNWorkflowState state, String modifiedBy, LookupSPNStatusActionMapper lookupSPNStatusActionMapper, String validityDate) throws Exception;

	/**
	 * 
	 * @param buyerId
	 * @param serviceProviderId
	 * @return List<SPNServiceProviderState>
	 */
	public List<SPNServiceProviderState> findServiceProviderState(Integer buyerId, Integer serviceProviderId);

	/**
	 * 
	 * @param spnId
	 * @return List
	 */
	public List<SPNServiceProviderState> findBySpnId(Integer spnId);

	/**
	 * 
	 * @param spnId
	 * @param states
	 * @return List
	 */
	public List<SPNServiceProviderState> findBySpnIdAndSPNWorkflowStates(Integer spnId, List<LookupSPNWorkflowState> states);

	/**
	 * 
	 * @param fromSpnId
	 * @param toSpnId
	 * @param states
	 * @return int
	 * @throws Exception
	 */
	public int update(Integer fromSpnId, Integer toSpnId, List<LookupSPNWorkflowState> states) throws Exception;

	/**
	 * 
	 * @param state
	 * @return SPNServiceProviderState
	 * @throws Exception
	 */
	public SPNServiceProviderState save(SPNServiceProviderState state) throws Exception;

	/**
	 * 
	 * @param state
	 * @return SPNServiceProviderState
	 * @throws Exception
	 */
	public SPNServiceProviderState update(SPNServiceProviderState state) throws Exception;

	/**
	 * 
	 * @param spnId
	 * @return count of deleted records
	 * @throws Exception
	 */
	public int delete(Integer spnId) throws Exception;

	/**
	 * 
	 * @param criteria
	 * @return
	 */
	public List<SPNServiceProviderState> searchForMemberManage(MemberManageSearchCriteriaVO criteria) throws Exception;

	/**
	 * 
	 * @param spnId
	 * @param providerFirmId
	 * @return
	 * @throws Exception
	 */
	public List<SPNServiceProviderState> findAllServiceProForSpnAndFirm(Integer spnId, Integer providerFirmId) throws Exception;
	

	/**
	 * 
	 * @param buyerId
	 * @param providerFirmId
	 * @return
	 * @throws Exception
	 */
	public List<SPNServiceProviderState> findAllServiceProForFirmAndBuyer(Integer buyerId, Integer providerFirmId) throws Exception;
	
	/**
	 * @param buyer
	 * @param providerFirmId
	 * @return
	 * @throws Exception
	 */
	public Integer countOfServiceProForFirmAndBuyer(Integer buyer, Integer providerFirmId) throws Exception;
	
	/**
	 * 
	 * @param spnId
	 * @param providerFirmId
	 * @param overrideComment
	 * @param reason
	 * @param state
	 * @param modifiedBy
	 * @param lookupSPNStatusActionMapper
	 * @param statusOverrideState
	 * @return
	 * @throws Exception
	 */
	public int updateAllServiceProStatusOverrideForFirmOverride(Integer providerFirmId, String overrideComment, LookupSPNStatusOverrideReason reason, LookupSPNWorkflowState state, String modifiedBy, LookupSPNStatusActionMapper lookupSPNStatusActionMapper, LookupSPNWorkflowState statusOverrideState, List<Integer> spnIds) throws Exception;
}
