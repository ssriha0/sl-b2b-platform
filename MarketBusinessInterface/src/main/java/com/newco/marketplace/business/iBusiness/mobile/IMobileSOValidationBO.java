package com.newco.marketplace.business.iBusiness.mobile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.api.mobile.beans.sodetails.RetrieveServiceOrderMobile;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.vo.mobile.ProviderSOSearchVO;

public interface IMobileSOValidationBO {

	/**
	 * To get the firm matching the resource from the Database.
	 * 
	 * @param providerId
	 * @return
	 * @throws BusinessServiceException
	 */
	public Integer validateProviderId(String providerId)
			throws BusinessServiceException;


	/**
	 * To check if given firm is authorized to view so
	 * 
	 * @param soId
	 * @param resourceId
	 * @return
	 * @throws BusinessServiceException
	 */
	public boolean isAuthorizedInViewSODetails(String soId, String resourceId)
			throws BusinessServiceException; 

	/**
	 * To check if given firm is a valid provider firm id or not
	 * 
	 * @param providerId
	 * @return
	 * @throws BusinessServiceException
	 */
	public boolean isValidProvider(String providerId)
			throws BusinessServiceException;

	/**
	 * To check if given service order is a valid one or not
	 * 
	 * @param providerId
	 * @return
	 * @throws BusinessServiceException
	 */
	public boolean isValidServiceOrder(String soId) throws BusinessServiceException;
	
	/**To check if given providerId exists or not
	 * @param providerId
	 * @return
	 * @throws BusinessServiceException
	 */
	public boolean isValidProviderResource(String providerId)throws BusinessServiceException;

	/**
	 * To check if provider can upload document in the current status of service
	 * order
	 * 
	 * @param providerId
	 * @return
	 * @throws BusinessServiceException
	 */
	public Integer getValidServiceOrderWfStatus(String soId) throws BusinessServiceException;

    /**
	 * To get the firmId of the provider
	 * 
	 * @param providerId
	 * @return
	 * @throws BusinessServiceException
	 */
	public Integer getFirmId(String providerId) throws BusinessServiceException;

	/**
	 * To get the URLs required for buyer logo
	 * 
	 * @return URLs
	 * @throws BusinessServiceException
	 */
	public HashMap<String, Object> getURLs() throws BusinessServiceException;


	
	
}
