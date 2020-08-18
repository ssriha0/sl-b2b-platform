package com.newco.marketplace.api.mobile.services;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.constants.PublicMobileAPIConstant;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.soDetails.vo.SoDetailsVO;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.mobile.beans.DownloadErrorResult;
import com.newco.marketplace.business.iBusiness.mobile.IAuthenticateUserBO;
import com.newco.marketplace.business.iBusiness.mobile.IMobileGenericBO;
import com.newco.marketplace.business.iBusiness.mobile.IMobileSOManagementBO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.mobile.constants.MPConstants;

public class DocumentDownloadService{
	private Logger logger = Logger.getLogger(DocumentDownloadService.class);
	private IMobileSOManagementBO mobileSOManagementBO;
	private IAuthenticateUserBO authenticateUserBO;
	private IMobileGenericBO mobileGenericBO;
	
	public IAuthenticateUserBO getAuthenticateUserBO() {
		return authenticateUserBO;
	}
	public void setAuthenticateUserBO(IAuthenticateUserBO authenticateUserBO) {
		this.authenticateUserBO = authenticateUserBO;
	}
	public IMobileGenericBO getMobileGenericBO() {
		return mobileGenericBO;
	}
	public void setMobileGenericBO(IMobileGenericBO mobileGenericBO) {
		this.mobileGenericBO = mobileGenericBO;
	}
	public DownloadErrorResult execute(APIRequestVO apiVO) {
		String soId=apiVO.getSOId();
		String providerId=apiVO.getProviderId();
		String documentId=apiVO.getDocumentId();
	    String filePath="";
	    DownloadErrorResult validateResult = validateParams(soId, providerId, documentId,filePath);
		filePath=validateResult.getFilePath();
		if(null!=validateResult && StringUtils.isNotBlank(filePath)){
			DownloadErrorResult result=new DownloadErrorResult();
	    	result.setCode("0000");
			result.setMessage("Document downloaded successfully");
			result.setFilePath(filePath);
			return result;
		}else{
			DownloadErrorResult result=new DownloadErrorResult();
	    	result.setCode(validateResult.getCode());
			result.setMessage(validateResult.getMessage());
			result.setFilePath(null);
			return result;
		  }
		}
	private DownloadErrorResult  validateParams(String soId, String providerId,String documentId, String filePath) {

		boolean isValidSo=false;
		boolean isValidProvider=false;
		boolean isValidSoAssociation=false;
		boolean isValidDoc=false;
		boolean isValidSoDocAssociation=false;
		DownloadErrorResult validateResult=null;
		DownloadErrorResult errorCode = null;
		//TO DO:Replace message,code while creating error response using RESULT CODE
		if(StringUtils.isNotBlank(soId)){
			try {
				isValidSo=mobileSOManagementBO.isValidServiceOrder(soId);
				if(!isValidSo){
					return errorResponse(ResultsCode. INVALID_SO_ID.getCode(),ResultsCode.INVALID_SO_ID.getMessage());
				}
			} catch (BusinessServiceException e) {
				logger.info("Exception in validating service order"+ e.getMessage());
			}
		}
		if(StringUtils.isNotBlank(providerId) &&(!StringUtils.isNumeric(providerId))){
			return errorResponse(ResultsCode.INVALID_RESOURCE_ID.getCode(),ResultsCode.INVALID_RESOURCE_ID.getMessage());
		}
		if(StringUtils.isNotBlank(providerId) &&(isValidSo)){
			try{
			      isValidProvider=mobileSOManagementBO.isValidProviderResource(providerId);
			     if(!isValidProvider){
			    	 return errorResponse(ResultsCode.INVALID_RESOURCE_ID.getCode(),ResultsCode.INVALID_RESOURCE_ID.getMessage());
			      }
		       }catch (BusinessServiceException e) {
			    logger.info("Exception in validating service order"+ e.getMessage());
		     }
			
		}if(StringUtils.isNotBlank(soId)&& StringUtils.isNotBlank(providerId) &&(isValidProvider)){
			try{
				isValidSoAssociation=mobileSOManagementBO.isAuthorizedInViewSODetails(soId, providerId);
				// new validation based on role levels as part of R14_0
				errorCode = validateProviderForSO(soId, providerId, isValidSoAssociation);
				if(null != errorCode){
					return errorCode;
				}
				/*if(!isValidSoAssociation){
				    return errorResponse(ResultsCode.INVALID_SO_PROVIDER_ASSOCIATION.getCode(),ResultsCode.INVALID_SO_PROVIDER_ASSOCIATION.getMessage());
			      }*/
				
			}catch (BusinessServiceException e) {
			    logger.info("Exception in validating service order and resource id association"+ e.getMessage());
		     }
		}
		if(StringUtils.isNotBlank(documentId) && !StringUtils.isNumeric(documentId)){
		    return errorResponse(ResultsCode.DOWNLOAD_DOC_INVALID_DOCUMENT.getCode(),ResultsCode.DOWNLOAD_DOC_INVALID_DOCUMENT.getMessage());
		}
		if(StringUtils.isNotBlank(documentId) &&(null == errorCode)){
			try{
			  isValidDoc=mobileSOManagementBO.isValidDocument(documentId);
			  if(!isValidDoc){
			    return errorResponse(ResultsCode.DOWNLOAD_DOC_INVALID_DOCUMENT.getCode(),ResultsCode.DOWNLOAD_DOC_INVALID_DOCUMENT.getMessage());
		      }
			}catch (BusinessServiceException e) {
		    logger.info("Exception in validating documnet"+ e.getMessage());
			}
			
		}
		if(StringUtils.isNotBlank(documentId) && StringUtils.isNotBlank(soId) && (isValidDoc)){
			try{
				Map<String,String>param=new HashMap<String, String>();
				param.put("docId", documentId);
				param.put("soId", soId);
				isValidSoDocAssociation=mobileSOManagementBO.isAuthorizedToViewDoc(param);
				if(!isValidSoDocAssociation){
				    return errorResponse(ResultsCode.DOWNLOAD_DOC_SO_DOC_NOT_ASSOCIATED.getCode(),ResultsCode.DOWNLOAD_DOC_SO_DOC_NOT_ASSOCIATED.getMessage());
			      }
			}catch(BusinessServiceException e){
				logger.info("Exception in validating service order and document id association"+ e.getMessage());
			}
		}
		if(StringUtils.isNotBlank(documentId)&& (isValidSoDocAssociation)){
		try {
			   filePath=mobileSOManagementBO.getDocumentPath(documentId);
		     }catch(BusinessServiceException e){
			        logger.info("Exception in getting file path"+ e.getMessage());
			        e.printStackTrace();
			   }
		}
		if(StringUtils.isNotBlank(filePath)){
			validateResult=new DownloadErrorResult();
			validateResult.setFilePath(filePath);
		}else{
			
			validateResult=new DownloadErrorResult();
			validateResult.setMessage(ResultsCode.DOWNLOAD_DOC_NOT_AUTHORIZED_TO_VIEW.getMessage());
			validateResult.setCode(ResultsCode.DOWNLOAD_DOC_NOT_AUTHORIZED_TO_VIEW.getCode());
			validateResult.setFilePath(null);
		}
		return validateResult;
	}
    private DownloadErrorResult errorResponse(String errorCode,String errorMessage) {
    	   DownloadErrorResult result=new DownloadErrorResult();
		   result.setCode(errorCode);
		   result.setMessage(errorMessage);
		   result.setFilePath(null);
		   return result;
		   
	}
    
    /**
	 * @param soId
	 * @param providerId
	 * @param validServiceOrderProviderAssoc
	 * @return
	 * method to validate provider permission based on role
	 */
	private DownloadErrorResult validateProviderForSO(String soId, String providerId,
			boolean validServiceOrderProviderAssoc) {

		
		 
		DownloadErrorResult errorResult = null;
		try{
			Integer resourceRoleLevel = authenticateUserBO.getRoleOfResource(null, Integer.parseInt(providerId));
			
			
			Integer firmId = mobileSOManagementBO.validateProviderId(providerId); 
			if(null!=firmId && null !=providerId){
				SoDetailsVO detailsVO = new SoDetailsVO();
			 detailsVO.setSoId(soId);
			 detailsVO.setFirmId(firmId.toString());
			 detailsVO.setProviderId(Integer.parseInt(providerId));
			 detailsVO.setRoleId(resourceRoleLevel);
			 // SL-21816 provider can see details even when the SO is in posted state 
			 // boolean authSuccess = mobileGenericBO.isAuthorizedToViewBeyondPosted(detailsVO);
			 boolean authSuccess = mobileGenericBO.isAuthorizedToViewSODetails(detailsVO);
			 if(!authSuccess){
					errorResult = errorResponse(ResultsCode.PERMISSION_ERROR.getCode(),ResultsCode.PERMISSION_ERROR.getMessage());			
			 }
			}
			
			List<Integer> roleIdValues = Arrays.asList(
					PublicMobileAPIConstant.ROLE_LEVEL_ONE,
					PublicMobileAPIConstant.ROLE_LEVEL_TWO,
					PublicMobileAPIConstant.ROLE_LEVEL_THREE);
			if (null == resourceRoleLevel || !roleIdValues.contains(resourceRoleLevel)) {
				errorResult = errorResponse(ResultsCode.INVALID_ROLE.getCode(),ResultsCode.INVALID_ROLE.getMessage());			
			}
		}
		catch(Exception e){
			logger.error("Exception inside validateProviderForSO inside Download Document");
			e.printStackTrace();
		}
		
		
		return errorResult;
	
		
	}

	public IMobileSOManagementBO getMobileSOManagementBO() {
		return mobileSOManagementBO;
	}

	public void setMobileSOManagementBO(IMobileSOManagementBO mobileSOManagementBO) {
		this.mobileSOManagementBO = mobileSOManagementBO;
	}

}
