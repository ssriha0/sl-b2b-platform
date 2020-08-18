package com.newco.marketplace.persistence.daoImpl.mobile;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.mobile.beans.sodetails.CompletionDetails;
import com.newco.marketplace.api.mobile.beans.sodetails.DocumentType;
import com.newco.marketplace.api.mobile.beans.sodetails.Price;
import com.newco.marketplace.api.mobile.beans.sodetails.ProviderReference;
import com.newco.marketplace.api.mobile.beans.sodetails.ServiceOrderDetails;
import com.newco.marketplace.api.mobile.beans.sodetails.v2_0.LatestTripDetails;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.mobile.IMobileSOManagementDao;
import com.newco.marketplace.vo.mobile.FeedbackVO;
import com.newco.marketplace.vo.mobile.MobileSOLoggingVO;
import com.newco.marketplace.vo.mobile.ProviderSOSearchVO;
import com.newco.marketplace.vo.mobile.SOStatusVO;
import com.newco.marketplace.vo.mobile.v2_0.SOSearchCriteriaVO;
import com.newco.marketplace.vo.mobile.v2_0.SOSearchResultVO;
import com.sears.os.dao.impl.ABaseImplDao;
import com.newco.marketplace.vo.mobile.v2_0.SOSearchCriteriaVO;
import com.newco.marketplace.vo.mobile.v2_0.SOSearchResultVO;

public class MobileSOManagementDAOImpl extends ABaseImplDao  implements
		IMobileSOManagementDao {

	private static final Logger logger = Logger
			.getLogger(MobileSOManagementDAOImpl.class.getName());

	/**
	 * Get the Service orders for the provider based on the criteria
	 * 
	 * @param params
	 * @return List<ProviderSOSearchVO> resultList
	 * @throws DataServiceException
	 */
	@SuppressWarnings("unchecked")
	public List<ProviderSOSearchVO> getProviderSOSearchList(
			HashMap<String, Object> params) throws DataServiceException {
		List<ProviderSOSearchVO> resultList = new ArrayList<ProviderSOSearchVO>();
		try {
			resultList = (List<ProviderSOSearchVO>) queryForList(
					"providerSearchSO.query", params);
		} catch (Exception ex) {
			logger.error("Exception Occured in MobileSOManagementDAOImpl-->getProviderSOSearchList()");
			throw new DataServiceException(ex.getMessage());
		}
		return resultList;
	}

	/**
	 * Get the count of Service orders for the provider based on the criteria
	 * 
	 * @param params
	 * @return count
	 * @throws DataServiceException
	 */
	public Integer getProviderSOSearchCount(HashMap<String, Object> params)
			throws DataServiceException {
		Integer count;
		try {
			count = (Integer) queryForObject("providerSearchSOCount.query",
					params);
		} catch (Exception ex) {
			logger.error("Exception Occured in MobileSOManagementDAOImpl-->getProviderSOSearchCount()");
			throw new DataServiceException(ex.getMessage());
		}
		return count;
	}

	/**
	 * check if the provided status is valid or not
	 * 
	 * @param statusString
	 * @return List<Integer> wfStateIds
	 * @throws DataServiceException
	 */
	@SuppressWarnings("unchecked")
	public List<SOStatusVO> getSOStatus() throws DataServiceException {
		List<SOStatusVO> statusVO = new ArrayList<SOStatusVO>();
		try {
			statusVO = (List<SOStatusVO>) queryForList("providerSearchSOStatus.query");
		} catch (Exception ex) {
			logger.error("Exception Occured in MobileSOManagementDAOImpl-->getSOStatus()");
			throw new DataServiceException(ex.getMessage());
		}
		return statusVO;
	}

	/**
	 *
	 * 
	 * @param param	
	 * @return
	 * @throws DataServiceException
	 */
	
	public ServiceOrderDetails getServiceOrderDetails(Map<String, Object> param) throws DataServiceException{
		ServiceOrderDetails	serviceOrderDetails	=null;	
			try {
				// getting serviceOrderDetails			
							
				serviceOrderDetails = (ServiceOrderDetails) queryForObject("getServiceOrderDetails.query", param);
												
			} catch (Exception ex) {
				logger.error(ex.getMessage(), ex);
				throw new DataServiceException(ex.getMessage(), ex);
			}
			return serviceOrderDetails;
		}
	/**
	 *
	 * 
	 * @param param	
	 * @return
	 * @throws DataServiceException
	 */
	
	public CompletionDetails getCompletionDetails(Map<String, Object> param) throws DataServiceException{
		CompletionDetails	completionDetails	=null;	
			try {
				
				completionDetails = (CompletionDetails) queryForObject("getCompletionDetails.query", param);
												
			} catch (Exception ex) {
				logger.error(ex.getMessage(), ex);
				throw new DataServiceException(ex.getMessage(), ex);
			}
			return completionDetails;
		}
	
	
	@SuppressWarnings("unchecked")
	public List<ProviderReference> getMandatoryProviderRefs(Integer buyerId) throws DataServiceException {
		List<ProviderReference> providerReferences	= new ArrayList<ProviderReference>();	
			try {
				
				providerReferences = queryForList("mandatoryProviderReference.query", buyerId);
												
			} catch (Exception ex) {
				logger.error(ex.getMessage(), ex);
			}
			return providerReferences;
		}

	/**
	 * To check if given firm is authorized to view so
	 * 
	 * @param soId
	 * @param firmId
	 * @return boolean
	 * @throws DataServiceException
	 */
	
	public boolean isAuthorizedInViewSODetails(String soId,String resourceId) throws DataServiceException{
		// TODO Auto-generated method stub
		boolean isAuthorized = Boolean.FALSE;
		int count = 0;
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("soId", soId);
			param.put("resourceId", resourceId);
			count = (Integer) queryForObject("isProviderAuthToViewSO.query",
					param);
			if (count > 0) {
				isAuthorized = true;
			}
		} catch (Exception e) {
			logger.error("Exception occured in isAuthorizedInViewSODetls() due to "
					+ e.getMessage());
		}
		return isAuthorized;
	}

	/**
	 * To check if given provider id is valid or not
	 * 
	 * @param providerId
	 * @return firmId
	 * @throws DataServiceException
	 */
	public Integer validateProviderId(String providerId)
			throws DataServiceException {
		// TODO Auto-generated method stub
		
        if(!StringUtils.isNumeric(providerId)){
        	return null;
        }
        Map<String, Object> param = new HashMap<String, Object>();		
		Integer firmId = 0;
		int resourceId = Integer.parseInt(providerId);
		param.put("resourceId", resourceId);
		try {

			firmId = (Integer) queryForObject("getFirmIdForResource.query",
					param);
			if (null == firmId) {
				return null;
			}

		} catch (Exception e) {
			logger.error("Exception Occured in MobileSOManagementDAOImpl-->validateProviderId()");
			throw new DataServiceException(e.getMessage());
		}
		return firmId;
	}
	
	
	/**
	 * To check if given firm is a valid provider firm id or not
	 * @param providerId
	 * @return
	 * @throws BusinessServiceException
	 */
	public boolean isValidProvider(String firmId) throws DataServiceException
	{
		// TODO Auto-generated method stub
				boolean isAuthorized =  Boolean.FALSE;
				int count = 0;
				try{
					count = (Integer)queryForObject("isValidProviderFirm.query", firmId);
					if(count > 0){
						isAuthorized = true;
					}
				}catch(Exception e){
					logger.error("Exception occured in isAuthorizedInViewSODetls() due to "+e.getMessage());
				}
				return isAuthorized;
		
	}
	
	/**
	 * To check if given service order is a valid one or not
	 * @param providerId
	 * @return
	 * @throws BusinessServiceException
	 */
	public boolean isValidServiceOrder(String soId) throws DataServiceException
	{
		// TODO Auto-generated method stub
		boolean isAuthorized =  Boolean.FALSE;
		int count = 0;
		try{
			// code change for SLT-2112
			Map<String, String> parameter = new HashMap<String, String>();
					parameter.put("soId", soId);
			count = (Integer)queryForObject("isValidServiceOrder.query", parameter);
			if(count > 0){
				isAuthorized = true;
			}
		}catch(Exception e){
			logger.error("Exception occured in isAuthorizedInViewSODetls() due to "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return isAuthorized;
	}

	/**
	 * To check if provider can upload document in the current status of service order
	 * @param providerId
	 * @return
	 * @throws BusinessServiceException
	 */
	public Integer getValidServiceOrderWfStatus(String soId) throws DataServiceException
	{
		Integer wfStatus = null;
		try{
			
			wfStatus = (Integer)queryForObject("wfStatusServiceOrder.query", soId);
			
		}catch(Exception e){
			logger.error("Exception occured in isAuthorizedInViewSODetls() due to "+e.getMessage());
		}
		return wfStatus;
	}
	/**
	 * @Description:To get the file path for the given document.
	 * @param documentId
	 * @return
	 * @throws DataServiceException
	 */
	public String getDocumentPath(String documentId) throws DataServiceException {
		String filePath="";
		try{
			// code change for SLT-2112
			Map<String, String> parameter = new HashMap<String, String>();
					parameter.put("documentId", documentId);
			filePath=(String) queryForObject("getDocumentPath.query", parameter);
		}catch(Exception e){
			logger.info("Exception in getting file path"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return filePath;
	}
	/**
	 * @Description:validate Resource id.
	 * @param providerId
	 * @return
	 * @throws DataServiceException
	 */
	public boolean isValidProviderResource(String providerId) throws DataServiceException {
		boolean isValid=false;
		int count=0;
		Integer providerIdInt= 0;
		try{
			if (StringUtils.isNumeric(providerId)){
				providerIdInt=Integer.parseInt(providerId);
			}
			// code change for SLT-2112
			Map<String, Integer> parameter = new HashMap<String, Integer>();
					parameter.put("providerIdInt", providerIdInt);
			count=(Integer) queryForObject("validateResourceIdDoc.query",parameter);
			if(count > 0){
				isValid=true;
			}
		}catch(Exception e){
			logger.info("Exception in validating provider id"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return isValid;
	}

	public boolean isAuthorizedToViewDoc(Map<String, String> param) throws DataServiceException {
		boolean isValid=false;
		int count=0;
		try{
			count=(Integer) queryForObject("isValidDocumentAssociation.query",param);
			if( count  > 0){
				isValid=true;
			}
			
		}catch(Exception e){
			logger.info("Exception in validating document id association"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return isValid;
	}
    public boolean isValidDocument(String documentId)throws DataServiceException {
    	boolean isValid=false;
		int count=0;
		Integer documentIdInt;
		try{
			documentIdInt=new Integer(documentId);
			count=(Integer) queryForObject("isValidDocument.query",documentIdInt);
			if( count  > 0){
				isValid=true;
			}
			
		}catch(Exception e){
			logger.info("Exception in validating document id"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return isValid;
	}
	public Integer getFirmId(String providerId) throws DataServiceException{
		Integer firmId = null;
		try{
			firmId = (Integer) queryForObject("getFirmId.query", providerId);
			
		}catch(Exception e){
			logger.info("Exception in MobileSOManagementBOImpl.getFirmId() "+e.getMessage());
		}
		return firmId;
	}
	/**
	 * @Description:To get tdocumentTypeList.
	 * @param buyerId
	 * @return List<DocumentType> 
	 * @throws DataServiceException
	 */
	public List<DocumentType> documentTypeList(Integer buyerId) throws DataServiceException{
		List<DocumentType> docTypeList=null;
		
		try{
			docTypeList = (List<DocumentType>) queryForList("docTypeList.query", buyerId);
			
		}catch(Exception e){
			logger.info("Exception in MobileSOManagementBOImpl.documentTypeList() "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return docTypeList;
	}
	/**
	 * @Description:To get so contact phone details.
	 * @param soContactId
	 * @return String
	 * @throws DataServiceException
	 */
	public String getContactPrimaryPhone(Map<String, String> param)
			throws DataServiceException {
		String phone=null;
		
		try{
			phone = (String) queryForObject("getSoContactPrimaryPhone.query", param);
			
		}catch(Exception e){
			logger.info("Exception in MobileSOManagementBOImpl.getContactPrimaryPhone() "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return phone;
	}
	/**
	 * @Description:To get so contact Alternate phone details.
	 * @param soContactId
	 * @return String
	 * @throws DataServiceException
	 */
	public String getContactAlternatePhone(Map<String, String> param)
			throws DataServiceException {
		String phone=null;
		
		try{
			phone = (String) queryForObject("getSoContactalternatePhone.query", param);
			
		}catch(Exception e){
			logger.info("Exception in MobileSOManagementBOImpl.getContactAlternatePhone() "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return phone;
	}
	
	public HashMap<String, Object> getDocumentDetails(String soId,String documentName)throws DataServiceException{
		HashMap<String, Object> docDetails = new HashMap<String, Object>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("soId", soId);
		params.put("documentName", documentName);
		
		try{
			docDetails = (HashMap<String, Object>) queryForObject("getDocumentId.query", params);
			
		}catch(Exception e){
			logger.info("Exception in ProviderUploadDocumentService.getFirmId() "+e.getMessage());
		}
		return docDetails;
	}

	//To insert logging  into mobile_so_logging
	public Integer logSOMobileHistory(String request,Integer actionId, Integer resourceId,String soId,String httpMethod) throws DataServiceException {
		
		MobileSOLoggingVO mobileSOLoggingVO=new MobileSOLoggingVO();
		mobileSOLoggingVO.setActionId(actionId);
		mobileSOLoggingVO.setCreatedBy(resourceId);
		mobileSOLoggingVO.setRequest(request);
		mobileSOLoggingVO.setSoId(soId);
		mobileSOLoggingVO.setHttpMethod(httpMethod);
		Integer loggingId = (Integer) insert("logSOMobileHistory.insert", mobileSOLoggingVO);
		return loggingId; 
		
	}		
	

	// update response of the SOMobile request
	public void updateSOMobileResponse(Integer loggingId,String response) throws DataServiceException{
		try{
			MobileSOLoggingVO mobileSOLoggingVO=new MobileSOLoggingVO();
			mobileSOLoggingVO.setLoggingId(loggingId);
			mobileSOLoggingVO.setResponse(response);
			update("mobile.updateResponse.query", mobileSOLoggingVO); 		
		}
		catch(Exception e){
			logger.error("Exception occured in updateSOMobileResponse() due to "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}	 
	}
		
	public Integer getResourceId(String userName) throws DataServiceException{
		Integer resourceId; 
		try{
			resourceId = (Integer) queryForObject("mobile.getResourceIdFromName", userName);
			return resourceId;
			
		}catch(Exception e){
			logger.info("Exception in getting resourceId "+e.getMessage());
			return null;
		}
	}
	
	/**
	 * Get the completion details v2.0
	 */
	public com.newco.marketplace.api.mobile.beans.sodetails.v2_0.CompletionDetails getCompletionDetailsWithTrip(
			Map<String, Object> param) throws DataServiceException {
		com.newco.marketplace.api.mobile.beans.sodetails.v2_0.CompletionDetails completionDetails = null;
		try {

			completionDetails = (com.newco.marketplace.api.mobile.beans.sodetails.v2_0.CompletionDetails) queryForObject(
					"getCompletionDetailsWithTrip.query", param);
			
			Map<String, BigDecimal> taxDiscountPrice = getTaxDiscountPrice(param);
			
			if(null != taxDiscountPrice){
				
				if(null != taxDiscountPrice.get("partsTax")){
					//completionDetails.getPrice().setPartsTax(taxDiscountPrice.get("partsTax").toString());
					completionDetails.getPrice().setPartsTax(Double.valueOf( taxDiscountPrice.get("partsTax").toString()).toString());
				}else{
					completionDetails.getPrice().setPartsTax("0.0");
				}
				
				if(null != taxDiscountPrice.get("partsDiscount")){
					completionDetails.getPrice().setPartsDiscount(Double.valueOf( taxDiscountPrice.get("partsDiscount").toString()).toString());
				}else{
					completionDetails.getPrice().setPartsDiscount("0.0");
				}
				
				if(null != taxDiscountPrice.get("laborTax")){
					completionDetails.getPrice().setLaborTax(Double.valueOf( taxDiscountPrice.get("laborTax").toString()).toString());
				}else{
					completionDetails.getPrice().setLaborTax("0.0");
				}
				
				if(null != taxDiscountPrice.get("laborDiscount")){
					completionDetails.getPrice().setLaborDiscount(Double.valueOf( taxDiscountPrice.get("laborDiscount").toString()).toString());
				}else{
					completionDetails.getPrice().setLaborDiscount("0.0");	
				}
			
			}
		
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new DataServiceException(ex.getMessage(), ex);
		}
		return completionDetails;
	}
	
	
	/**
	 * Get the service order details v2.0
	 */
	public com.newco.marketplace.api.mobile.beans.sodetails.v2_0.ServiceOrderDetails getServiceOrderDetailsWithTrip(
			Map<String, Object> param) throws DataServiceException {
		com.newco.marketplace.api.mobile.beans.sodetails.v2_0.ServiceOrderDetails serviceOrderDetails= null;
		try {

			serviceOrderDetails = (com.newco.marketplace.api.mobile.beans.sodetails.v2_0.ServiceOrderDetails) queryForObject(
					"getServiceOrderDetailsWithTrip.query", param);

		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new DataServiceException(ex.getMessage(), ex);
		}
		return serviceOrderDetails;
	}
	
	/**
	 * getCustomReference
	 */
	public String getCustomReference(String soId,String custRefType) throws DataServiceException{
		String refValue = null;
		try {
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("soId", soId);
			params.put("custRefType", custRefType);
			
			refValue = (String) queryForObject("getCovType.query", params);

		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new DataServiceException(ex.getMessage(), ex);
		}
		return refValue;
	}

	/**
	 * @param feedbackVO
	 * @throws DataServiceException
	 * 
	 * insert feedback details
	 * 
	 */
	public void insertFeedbackDetails(FeedbackVO feedbackVO)
			throws DataServiceException {

		try {
			if(null!=feedbackVO){
				insert("appFeedbackDetails.insert", feedbackVO);
			}
		} catch (Exception e) {
			logger.error("Exception in insertFeedbackDetails"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
	}
	
	
	/**
	 * @param param
	 * @throws DataServiceException
	 * 
	 * retrieve latest trip details
	 * 
	 */
	public LatestTripDetails getLatestTripDetails(Map<String, Object> param)
			throws DataServiceException {
		LatestTripDetails latestTrip = null;
		try {
			if (null != param) {
				latestTrip = (LatestTripDetails) queryForObject(
						"getLatestTripDetails.query", param);
			}
		} catch (Exception e) {
			logger.error("Exception in getLatestTripDetails" + e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return latestTrip;
	}


	public Integer logSOMobileHistory(MobileSOLoggingVO loggingVo)throws DataServiceException {
		Integer loggingId =null;
		try{
		    loggingId = (Integer) insert("logSOMobileHistory.insert", loggingVo);
		}catch (Exception e) {
			logger.error("Exception in logging mobile api request for the order"+loggingVo.getSoId()+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return loggingId; 
	}
	

	
	//SL-21927
	@SuppressWarnings("unchecked")
	public  HashMap<String, BigDecimal> getTaxDiscountPrice(Map<String, Object> param) throws DataServiceException {
		HashMap<String, BigDecimal> taxDiscountPrice = null;
		try {

			taxDiscountPrice = (HashMap<String, BigDecimal>) queryForObject("getTaxDiscountPrice.query", param);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new DataServiceException(ex.getMessage(), ex);
		}
		return taxDiscountPrice;
	}
}

