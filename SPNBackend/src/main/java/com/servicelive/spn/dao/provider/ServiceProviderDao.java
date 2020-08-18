package com.servicelive.spn.dao.provider;

import java.util.List;

import com.newco.marketplace.exception.core.DataServiceException;
import com.servicelive.domain.lookup.LookupSPNWorkflowState;
import com.servicelive.domain.provider.ServiceProvider;
import com.servicelive.spn.dao.BaseDao;

/**
 * 
 * @author svanloon
 *
 */
public interface ServiceProviderDao extends BaseDao {

	/**
	 * 
	 * @param providerFirmId
	 * @return List<ServiceProvider>
	 */
	List<ServiceProvider> findAdmin(Integer providerFirmId);

	/**
	 * 
	 * @param spnId
	 * @param wfStates
	 * @return List<ServiceProvider>
	 */
	List<ServiceProvider> findAdminsBySpnIdAndWfStates(Integer spnId, List<LookupSPNWorkflowState> wfStates);
	/**
	 * This method would find all the Service pro who can work in the market Place and who are having Market Ready State for List of the Provider Firm Ids supplied.
	 * @param proFirmIds
	 * @return
	 */
	List<ServiceProvider> findMarketReadyServiceProForProviderFirms(List<Integer> proFirmIds);
	
	/**
	 * This method fetches spns for a particular provider
	 * @param providerFirmId
	 * @param requirementType
	 * @return List<Object[]>
	 */
	List<Object[]> getSPNForProvider(Integer providerFirmId, Integer buyerId, Integer requirementType)throws DataServiceException;
	
	/**
	 * This method fetches spns for a particular provider
	 * @param providerFirmId
	 * @param spnId
	 * @return List<Object[]>
	 */
	List<Object[]> getRequirementType(Integer providerFirmId, Integer buyerId, Integer spnId)throws DataServiceException;
	
	/**
	 * This method fetches spns for a particular provider
	 * @param providerFirmId
	 * @param spnId
	 * @param requirementType
	 * @return List<ExpiryNotification>
	 */
	List<Object[]> getExpirationDetailsForProvider(Integer providerFirmId, Integer buyerId, Integer spnId, Integer requirementType)throws DataServiceException;

	// Code Added for Jira SL-19728
	// For Sending emails specific to NON FUNDED buyer when a
	// provider is approved in a SPN
	// As per new requirements

	/**
	 * 
	 * @param buyerId
	 * @param templateId
	 * @return
	 * @throws DataServiceException
	 */
	public Integer getEmailTemplateSpecificToBuyer(Integer buyerId,Integer templateId)throws DataServiceException;

}
