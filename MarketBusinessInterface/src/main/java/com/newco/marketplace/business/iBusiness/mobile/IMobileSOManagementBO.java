package com.newco.marketplace.business.iBusiness.mobile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.api.mobile.beans.sodetails.RetrieveServiceOrderMobile;
import com.newco.marketplace.api.mobile.beans.sodetails.v2_0.CompletionDetails;
import com.newco.marketplace.api.mobile.beans.sodetails.v2_0.RetrieveSOCompletionDetailsMobile;
import com.newco.marketplace.api.mobile.beans.sodetails.v2_0.RetrieveSODetailsMobile;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.vo.mobile.FeedbackVO;
import com.newco.marketplace.vo.mobile.ProviderSOSearchVO;
import com.newco.marketplace.vo.mobile.SOStatusVO;
import com.newco.marketplace.vo.mobile.v2_0.SOSearchCriteriaVO;
import com.newco.marketplace.vo.mobile.v2_0.SOSearchResultVO;

public interface IMobileSOManagementBO {

	/**
	 * To get the list of SOs from the Database available to the provider.
	 * 
	 * @param params
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<ProviderSOSearchVO> getProviderSOSearchList(
			HashMap<String, Object> params) throws BusinessServiceException;

	/**
	 * To get the count of SOs from the Database available to the provider.
	 * 
	 * @param params
	 * @return
	 * @throws BusinessServiceException
	 */
	public Integer getProviderSOSearchCount(HashMap<String, Object> params)
			throws BusinessServiceException;

	/**
	 * To get the available SO statuses from the Database.
	 * 
	 * @param statusString
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<Integer> validateSOStatus(String statusString)
			throws BusinessServiceException;

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
	 * Get the response based on the response filter
	 * 
	 * @param Map
	 *            <String, Object> param
	 * @param responseFilters
	 * @return
	 * @throws BusinessServiceException
	 */
	public RetrieveServiceOrderMobile getServiceOrderDetails(
			Map<String, Object> param, String responseFilter)
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
	 * @param documentId
	 * @return
	 * @throws BusinessServiceException
	 */
	public String getDocumentPath(String documentId)
			throws BusinessServiceException;

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
	 * @param param
	 * @return
	 */
	public boolean isAuthorizedToViewDoc(Map<String, String> param)throws BusinessServiceException;
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

	
	/**
	 * To get the documentId
	 * @param providerId
	 * @return
	 * @throws BusinessServiceException
	 */
	public HashMap<String, Object> getDocumentDetails(String soId,String documentName)throws BusinessServiceException;

	/**
	 * @param documentId
	 * @return
	 * @throws BusinessServiceException
	 */
	public boolean isValidDocument(String documentId)throws BusinessServiceException;
	//for decrypting credit card number
	public String decryptCreditCardInfo(String ccNumber) ;
	
	/**
	 * To get service Order Completion Details
	 * @param param
	 * @return RetrieveSOCompletionDetailsMobile
	 * @throws BusinessServiceException
	 */
	public RetrieveSOCompletionDetailsMobile getCompletionDetails(
			Map<String, Object> param) throws BusinessServiceException;;
			
	/**
	 * Get Service order details for mobile v2.0
	 * @param param
	 * @return
	 * @throws BusinessServiceException
	 */		
	public RetrieveSODetailsMobile getServiceOrderDetails(
					Map<String, Object> param)
					throws BusinessServiceException;

	/**
	 * @param feedbackVO
	 * 
	 * to insert feedback details
	 * 
	 */
	public void insertFeedbackDetails(FeedbackVO feedbackVO)throws BusinessServiceException;

	/**@Description: Getting all available status for a service order
	 * @return List<SOStatusVO>
	 * @throws BusinessServiceException
	 */
	public List<SOStatusVO> getServiceOrderStatus()throws BusinessServiceException;

	/**@Description : Convert GMT service Date to So Location Date
	 * @param result
	 * @return ProviderSOSearchVO
	 * @throws BusinessServiceException
	 */
	public ProviderSOSearchVO convertServiceDate(ProviderSOSearchVO result)throws BusinessServiceException;;

}
