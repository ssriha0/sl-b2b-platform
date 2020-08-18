package com.servicelive.spn.services.interfaces;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.servicelive.domain.lookup.LookupSPNStatusActionMapper;
import com.servicelive.domain.lookup.LookupSPNStatusOverrideReason;
import com.servicelive.domain.lookup.LookupSPNWorkflowState;
import com.servicelive.domain.spn.detached.SPNProviderDetailsFirmHistoryRowDTO;
import com.servicelive.domain.spn.network.SPNProviderFirmState;

public interface IProviderFirmStateService
{

	/**
	 * 
	 * @param buyerId
	 * @param providerFirmId
	 * @return List<SPNProviderFirmState>
	 */
	@Transactional ( propagation = Propagation.REQUIRED)
    public abstract List<SPNProviderFirmState> findSearchableProviderFirmStatus(Integer buyerId, Integer providerFirmId);

	/**
	 * 
	 * @param buyerId
	 * @param providerFirmId
	 * @return List<SPNProviderFirmStateHistory>
	 */
    @Transactional ( propagation = Propagation.REQUIRED)
    public abstract List<SPNProviderDetailsFirmHistoryRowDTO> findProviderFirmStatusHistoryAsDto(Integer buyerId, Integer providerFirmId );

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
    @Transactional ( propagation = Propagation.REQUIRED)
    public abstract void updateProviderFirmStatusOverride(Integer providerFirmId, String overrideComment, LookupSPNStatusOverrideReason reason, LookupSPNWorkflowState state, String modifiedBy,LookupSPNStatusActionMapper lookupSPNStatusActionMapper,List<Integer> spnIds, String validityDate) throws Exception;

	/**
	 * 
	 * @param spnId
	 * @param providerFirmId
	 * @param reviewedByUserName
	 * @param originalModifiedDate 
	 * @return SPNProviderFirmState
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public abstract SPNProviderFirmState getAndLockSPNProviderFirmState(Integer spnId, Integer providerFirmId, String reviewedByUserName, Date originalModifiedDate) throws Exception;

	/**
	 * This method finds the ProviderFirmState (SPNHeader + ProviderFirm) that corresponds with buyerId and spnId.
	 * It updates the review_date of the providerFirmState and reviewsByUserName. 
	 * 
	 * @param buyerId
	 * @param state
	 * @param reviewedByUserName 
	 * @return SPNProviderFirmState
	 * @throws Exception 
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public abstract SPNProviderFirmState getAndLockSPNProviderFirmState(Integer buyerId, LookupSPNWorkflowState state, String reviewedByUserName) throws Exception;

	/**
	 * 
	 * @param spnId
	 * @param providerFirmId
	 * @param state
	 * @param modifiedBy 
	 * @return SPNProviderFirmState
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public abstract SPNProviderFirmState update(Integer spnId, Integer providerFirmId, LookupSPNWorkflowState state, String modifiedBy,	String comment, LookupSPNStatusActionMapper lookupSPNStatusActionMapper) throws Exception;

	/**
	 * 
	 * @param spnId
	 * @param providerFirmId
	 * @return SPNProviderFirmState
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public abstract SPNProviderFirmState unlock(Integer spnId, Integer providerFirmId) throws Exception;

	/**
	 * Find the associated providerFirmState associated with the spn and the providerFirm.
	 * 
	 * @param spnId
	 * @param providerFirmId
	 * @return SPNProviderFirmState
	 * @throws Exception 
	 */
	public abstract SPNProviderFirmState findProviderFirmState(Integer spnId, Integer providerFirmId) throws Exception;

	/**
	 * Alernate implementation 
	 * 
	 * @param spnId
	 * @param providerFirmId
	 * @return SPNProviderFirmState
	 * @throws Exception 
	 */
	public abstract SPNProviderFirmState findProviderFirmStateByprimaryKey(Integer spnId, Integer providerFirmId) throws Exception;
	
	/**
	 * 
	 * @param spnId
	 * @param providerFirmId
	 * @return Integer 
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public abstract Integer getCountsOfImpactedServiceProOnFirmStatusOverride(Integer spnId, Integer providerFirmId) throws Exception;
	
	/**
	 * 
	 * @param spnId
	 * @param providerFirmId
	 * @return Integer 
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer getCountsOfImpactedServiceProForFirmBuyer(Integer buyerId,Integer providerFirmId) throws Exception;
	
	/**
	 * @param buyerId
	 * @param providerFirmId
	 * @return
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer getCountOfImpactedServiceProForFirmBuyer(Integer buyerId,Integer providerFirmId) throws Exception;

	/**
	 * @param spnId
	 * @return
	 */
	public abstract Map<String, Object> getSPNEditInfo(Integer spnId);

}