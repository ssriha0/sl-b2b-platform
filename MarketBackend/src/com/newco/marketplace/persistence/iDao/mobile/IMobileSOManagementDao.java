package com.newco.marketplace.persistence.iDao.mobile;



import java.util.HashMap;

import java.util.List;
import java.util.Map;

import com.newco.marketplace.api.mobile.beans.sodetails.CompletionDetails;
import com.newco.marketplace.api.mobile.beans.sodetails.DocumentType;
import com.newco.marketplace.api.mobile.beans.sodetails.ProviderReference;
import com.newco.marketplace.api.mobile.beans.sodetails.ServiceOrderDetails;
import com.newco.marketplace.api.mobile.beans.sodetails.v2_0.LatestTripDetails;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.vo.mobile.FeedbackVO;
import com.newco.marketplace.vo.mobile.MobileSOLoggingVO;
import com.newco.marketplace.vo.mobile.ProviderSOSearchVO;
import com.newco.marketplace.vo.mobile.SOStatusVO;
import com.newco.marketplace.vo.mobile.v2_0.SOTripVO;
import com.newco.marketplace.vo.mobile.v2_0.SOSearchCriteriaVO;
import com.newco.marketplace.vo.mobile.v2_0.SOSearchResultVO;
public interface IMobileSOManagementDao {

	/**
	 * Get the Service orders for the provider based on the criteria.
	 * 
	 * @param params
	 * @return
	 * @throws DataServiceException
	 */
	public List<ProviderSOSearchVO> getProviderSOSearchList(
			HashMap<String, Object> params) throws DataServiceException;

	/**
	 * To get the count of SOs from the Database available to the provider.
	 * 
	 * @param params
	 * @return
	 * @throws DataServiceException
	 */
	public Integer getProviderSOSearchCount(HashMap<String, Object> params)
			throws DataServiceException;

	/**
	 * To get the available SO statuses from the Database.
	 * 
	 * @return
	 * @throws DataServiceException
	 */
	public List<SOStatusVO> getSOStatus() throws DataServiceException;

	/**
	 * To get the firm matching the resource from the Database.
	 * 
	 * @param providerId
	 * @return
	 * @throws DataServiceException
	 */
	public Integer validateProviderId(String providerId)
			throws DataServiceException;
	/**
	 *
	 * 
	 * @param param	
	 * @return
	 * @throws DataServiceException
	 */
	
	public ServiceOrderDetails getServiceOrderDetails(Map<String, Object> param) throws DataServiceException;
	/**
	 * 
	 * 
	 * @param param	
	 * @return
	 * @throws DataServiceException
	 */
	
	public CompletionDetails getCompletionDetails(Map<String, Object> param) throws DataServiceException;
	

	/**
	 * To check if given firm is authorized to view so
	 * 
	 * @param soId
	 * @param providerId
	 * @return
	 * @throws DataServiceException
	 */
	public boolean isAuthorizedInViewSODetails(String soId,String resourceId) throws DataServiceException;
	
	public boolean isValidProvider(String providerId) throws DataServiceException;
	/**
	 * To check if given service order is a valid one or not
	 * @param providerId
	 * @return
	 * @throws BusinessServiceException
	 */
	public boolean isValidServiceOrder(String soId) throws DataServiceException;

	/**
	 * @param documentId
	 * @return
	 * @throws DataServiceException
	 */
	public String getDocumentPath(String documentId)throws DataServiceException;
	/**
	 * To check if provider can upload document in the current status of service order
	 * @param providerId
	 * @return
	 * @throws BusinessServiceException
	 */
	public Integer getValidServiceOrderWfStatus(String soId) throws DataServiceException;

	/**
	 * @param providerId
	 * @return
	 * @throws DataServiceException
	 */
	public boolean isValidProviderResource(String providerId)throws DataServiceException;

	/**
	 * @param param
	 * @return
	 * @throws DataServiceException
	 */
	public boolean isAuthorizedToViewDoc(Map<String, String> param)throws DataServiceException;

	/**
	 * To get the firmId of the provider
	 * @param providerId
	 * @return
	 * @throws BusinessServiceException
	 */
	public Integer getFirmId(String providerId)throws DataServiceException;
	/**
	 * @Description:To get tdocumentTypeList.
	 * @param buyerId
	 * @return List<DocumentType> 
	 * @throws DataServiceException
	 */
	public List<DocumentType> documentTypeList(Integer buyerId) throws DataServiceException;
	
	/**
	 * To get the documentId
	 * @param providerId
	 * @return
	 * @throws BusinessServiceException
	 */
	public HashMap<String, Object> getDocumentDetails(String soId,
			String documentName) throws DataServiceException;
	/**
	 * @Description:To get so contact phone details.
	 * @param soContactId
	 * @return String
	 * @throws DataServiceException
	 */
	public String getContactPrimaryPhone(Map<String, String> param) throws DataServiceException;
	/**
	 * @Description:To get so contact Alternate phone details.
	 * @param soContactId
	 * @return String
	 * @throws DataServiceException
	 */
	public String getContactAlternatePhone(Map<String, String> param)throws DataServiceException;

	/**
	 * @param documentId
	 * @return
	 * @throws DataServiceException
	 */
	public boolean isValidDocument(String documentId)throws DataServiceException;

	public List<ProviderReference> getMandatoryProviderRefs(Integer buyerId)throws DataServiceException;
	//To insert logging  into mobile_so_logging
	public Integer logSOMobileHistory(String request, Integer actionId,	Integer resourceId, String soId, String httpMethod) throws DataServiceException;
	//To insert logging  into mobile_so_logging with roleId
	public Integer logSOMobileHistory(MobileSOLoggingVO loggingVo)throws DataServiceException;
	// update response of the SOMobile request
	public void updateSOMobileResponse(Integer loggingId,String response) throws DataServiceException;
	// get resource If from username
	public Integer getResourceId(String userName) throws DataServiceException;

	/**
	 * Get the completion details v2.0
	 * @param param
	 * @return
	 * @throws DataServiceException
	 */
	public com.newco.marketplace.api.mobile.beans.sodetails.v2_0.CompletionDetails getCompletionDetailsWithTrip
			(Map<String, Object> param) throws DataServiceException;
	
	
	/**
	 * Get the service order details v2.0
	 * @param param
	 * @return
	 * @throws DataServiceException
	 */
	public com.newco.marketplace.api.mobile.beans.sodetails.v2_0.ServiceOrderDetails getServiceOrderDetailsWithTrip
			(Map<String, Object> param) throws DataServiceException;
	
	/**
	 * Get the custom ref value
	 * @param soId
	 * @param custRefType
	 * @return
	 * @throws DataServiceException
	 */
	public String getCustomReference(String soId,String custRefType) throws DataServiceException;

	/**
	 * @param feedbackVO
	 * @throws DataServiceException
	 * 
	 * insert feedback details
	 * 
	 */
	public void insertFeedbackDetails(FeedbackVO feedbackVO)throws DataServiceException;
	
	/**
	 * @param param
	 * @throws DataServiceException
	 * 
	 * retrieve latest trip details
	 * 
	 */
	public LatestTripDetails getLatestTripDetails(Map<String, Object> param)throws DataServiceException;
}

