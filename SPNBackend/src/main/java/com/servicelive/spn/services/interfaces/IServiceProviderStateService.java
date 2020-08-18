package com.servicelive.spn.services.interfaces;

import java.util.List;

import com.servicelive.domain.lookup.LookupPerformanceLevel;
import com.servicelive.domain.lookup.LookupSPNStatusOverrideReason;
import com.servicelive.domain.lookup.LookupSPNWorkflowState;
import com.servicelive.domain.spn.detached.SPNProviderDetailsFirmHistoryRowDTO;
import com.servicelive.domain.spn.network.SPNServiceProviderState;

public interface IServiceProviderStateService {

	/**
	 * 
	 * @param spnId
	 * @param serviceProviderId
	 * @param performanceLevelChangeComment
	 * @param performanceLevel
	 * @param modifiedBy
	 * @throws Exception
	 */
	public void updateServiceProviderStatusPerformanceLevel(Integer spnId, Integer serviceProviderId, String performanceLevelChangeComment, LookupPerformanceLevel performanceLevel, String modifiedBy) throws Exception;

	/**
	 * 
	 * @param spnId
	 * @param serviceProviderId
	 * @param overrideComment
	 * @param reason
	 * @param state
	 * @param modifiedBy
	 * @throws Exception
	 */
	public void updateServiceProviderStatusOverride(List<Integer> spnId, Integer serviceProviderId, String overrideComment, LookupSPNStatusOverrideReason reason, LookupSPNWorkflowState state, String modifiedBy, String validityDate) throws Exception; 

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
	 * @param serviceProviderId
	 * @return List<SPNServiceProviderStateHistory>
	 */
	public List<SPNProviderDetailsFirmHistoryRowDTO> findServiceProviderStateHistoryAsDto(Integer spnId, Integer serviceProviderId );
}
