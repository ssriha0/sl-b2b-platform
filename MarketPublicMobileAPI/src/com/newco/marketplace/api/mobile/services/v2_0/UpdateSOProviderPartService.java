package com.newco.marketplace.api.mobile.services.v2_0;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.beans.addsoproviderpart.InvoiceSupplier;
import com.newco.marketplace.api.mobile.beans.addsoproviderpart.UpdateSOProviderPartRequest;
import com.newco.marketplace.api.mobile.beans.addsoproviderpart.UpdateSOProviderPartResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.constants.PublicMobileAPIConstant;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.mobile.IMobileSOActionsBO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.inhomeoutboundnotification.service.INotificationService;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.vo.mobile.v2_0.SupplierVO;

@APIRequestClass(UpdateSOProviderPartRequest.class)
@APIResponseClass(UpdateSOProviderPartResponse.class)
public class UpdateSOProviderPartService extends BaseService {
	
	private static Logger logger =Logger.getLogger(UpdateSOProviderPartService.class);
	private IMobileSOActionsBO mobileSOActionsBO;
	private INotificationService notificationService;
	
	
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		logger.info("Entering execute method of UpdateSOProviderPartService");
		UpdateSOProviderPartResponse updateSOProviderPartResponse = new UpdateSOProviderPartResponse();
		UpdateSOProviderPartRequest updateSOProviderPartRequest = (UpdateSOProviderPartRequest) apiVO
				.getRequestFromPostPut();
		String soId = (String) apiVO.getSOId();
		String userName = null;
		Integer providerId = apiVO.getProviderResourceId();
		SecurityContext securityContext = getSecurityContextForVendor(providerId);
		if(null != securityContext){
			userName = securityContext.getUsername();
		}
		
		String currentTrip = updateSOProviderPartRequest.getCurrentTripNo();
		String tripStatus = updateSOProviderPartRequest.getTripStatus();
		Integer currentTripId = null;
	    //Validating buyerId,So id Status and invoicePartId
		try {
			if (null != updateSOProviderPartRequest.getPartList()&& null != updateSOProviderPartRequest.getPartList().getPartSupplier()
					&& !(updateSOProviderPartRequest.getPartList().getPartSupplier().isEmpty())) {
				updateSOProviderPartRequest = validate(updateSOProviderPartRequest, soId);
				if (ResultsCode.SUCCESS != updateSOProviderPartRequest.getValidationCode()) {
					return createErrorResponse(updateSOProviderPartRequest.getValidationCode().getMessage(),
							updateSOProviderPartRequest.getValidationCode().getCode());
				} else {					  
					List<InvoiceSupplier> supplierPartlist = updateSOProviderPartRequest.getPartList().getPartSupplier();
					//This will soft delete the existing so_invoice_part_locations.
					List<SupplierVO> supplierPartVOlist = mapSupplierPart(supplierPartlist,soId);
					
					 if(StringUtils.isNotBlank(currentTrip)){
						 
						 //R12.0 Sprint7 adding condition to check whether trip number is '999'
						 if(MPConstants.TRIP_NUMBER_DEFAULT.equals(currentTrip))
						 {
							//R12_0 SLM-88 Send Message API on change of part status
			            	 // Get the existing part details of SO
			       			List<SupplierVO> existingPartStatusList = new ArrayList<SupplierVO>();
			       			existingPartStatusList = mobileSOActionsBO.getExistingInvoicePartStatusList(soId);
			       			
							 mobileSOActionsBO.updateInhomePartDetails(supplierPartVOlist, soId,userName);	
							 
								// Check if there are changes in part status
				       			if(null!=existingPartStatusList && !(existingPartStatusList.isEmpty())){
				            	  for (InvoiceSupplier requestPartVO : supplierPartlist) {
				       					if(null!=requestPartVO && StringUtils.isNotEmpty(requestPartVO.getPartStatus())){
				       						for(SupplierVO existPartVO:existingPartStatusList){
				       							if(null!=existPartVO && existPartVO.getInvoiceId().equals(requestPartVO.getInvoiceId()) 
				       									&& StringUtils.isNotEmpty(existPartVO.getPartStatus()) 
				       									&& !(existPartVO.getPartStatus().equalsIgnoreCase(requestPartVO.getPartStatus()))){
				       								
				       								updateNPSForUpdateSOProviderPart(soId,MPConstants.INHOME_BUYER,requestPartVO,existPartVO);
				       							}
				       						}
				       						}
				       				}
				       			}
						 }
						 else
						 {
							//R12.3 - SL-230763 - START 
			               //openTrip = mobileSOActionsBO.validateLatestOpenTrip(Integer.parseInt(currentTrip), soId);
			               currentTripId = mobileSOActionsBO.getTripId(Integer.parseInt(currentTrip), soId);
			               if(null!=currentTripId) {
				            //R12.3 - SL-230763 - END
			            	 //R12_0 SLM-88 Send Message API on change of part status
			            	 	// Get the existing part details of SO
			       			List<SupplierVO> existingPartStatusList = new ArrayList<SupplierVO>();
			       			existingPartStatusList = mobileSOActionsBO.getExistingInvoicePartStatusList(soId);
			            	  
			       			mobileSOActionsBO.updateInhomePartDetails(supplierPartVOlist, soId,userName);
			       				// Check if there are changes in part status
			       			if(null!=existingPartStatusList && !(existingPartStatusList.isEmpty())){
			            	  for (InvoiceSupplier requestPartVO : supplierPartlist) {
			       					if(null!=requestPartVO && StringUtils.isNotEmpty(requestPartVO.getPartStatus())){
			       						for(SupplierVO existPartVO:existingPartStatusList){
			       							if(null!=existPartVO && existPartVO.getInvoiceId().equals(requestPartVO.getInvoiceId()) 
			       									&& StringUtils.isNotEmpty(existPartVO.getPartStatus()) 
			       									&& !(existPartVO.getPartStatus().equalsIgnoreCase(requestPartVO.getPartStatus()))){
			       								
			       								updateNPSForUpdateSOProviderPart(soId,MPConstants.INHOME_BUYER,requestPartVO,existPartVO);
			       							}
			       						}
			       						}
			       				}
			       			}
			                   mobileSOActionsBO.addTripDetails(currentTripId,MPConstants.PART_CHANGE_TYPE, MPConstants.PART_UPDATED,userName);
			            	   }
			               else
			               {
			            	 	 return createErrorResponse(ResultsCode.INVALID_TRIP_NUMBER.getMessage(),
			            	 			 					ResultsCode.INVALID_TRIP_NUMBER.getCode());
			               }
			            		   
			 		  }
						 
					 }
								
					updateSOProviderPartResponse.setResults(Results.getSuccess());
				
				}
			} else {
				return createErrorResponse(
						ResultsCode.UNABLE_TO_PROCESS_THE_REQUEST.getMessage(),
						   ResultsCode.UNABLE_TO_PROCESS_THE_REQUEST.getCode());
			}
		}catch (Exception e) {
			 logger.error("Exception in processing the request"+ e.getMessage());
			   return createErrorResponse(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(),
					   ResultsCode.INTERNAL_SERVER_ERROR.getCode());
		}
		
		return updateSOProviderPartResponse;
	}

	private UpdateSOProviderPartRequest validate(UpdateSOProviderPartRequest updateSOProviderPartRequest, String soId) throws BusinessServiceException {
		ResultsCode validationResponse = ResultsCode.SUCCESS;
	
		try {
			ServiceOrder serviceOrder = mobileSOActionsBO.getServiceOrder(soId);
			boolean isValidBuyer=false;
			Integer wfStateId = serviceOrder.getWfStateId();
			Integer buyerId = serviceOrder.getBuyerId();
			if (null != buyerId && MPConstants.INHOME_BUYER.equals(buyerId)) {
				isValidBuyer = true;
			} else {
				updateSOProviderPartRequest.setValidationCode(ResultsCode.INVALID_BUYER);
				return updateSOProviderPartRequest;
			}
			if (isValidBuyer && null != wfStateId&&!PublicMobileAPIConstant.ORDER_ACTIVE.equals(wfStateId)) {
				updateSOProviderPartRequest.setValidationCode(ResultsCode.INVALID_SO_STATUS);
				return updateSOProviderPartRequest;
			}
			
			// Check the existence of invoicePartIds in DB
			List<Integer> invoicePartIdExisting = new ArrayList<Integer>();
			List<InvoiceSupplier> list = updateSOProviderPartRequest.getPartList().getPartSupplier();
				invoicePartIdExisting = mobileSOActionsBO.getAvailableInvoicePartIdList(soId);
				for (InvoiceSupplier supplier : list) {
					if(null!=supplier){
					
						if (null!=invoicePartIdExisting&&invoicePartIdExisting.contains(supplier.getInvoiceId())) {
							updateSOProviderPartRequest.setValidationCode(validationResponse);							
						} else {
							updateSOProviderPartRequest.setValidationCode(ResultsCode.PART_NOT_ASSOCIATED_SO_OR_DELETED);
							break;
						}}
				}

			

		} catch (BusinessServiceException e) {
			logger.error("Exception in validating so buyer association"+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		
		return updateSOProviderPartRequest;
	}
	/**
	 * @param supplierPartlist
	 * Mapping the request inputs into corresponding  parts associated with SO
	 * @param existingInvoicePartlist */
	
	private   List<SupplierVO>  mapSupplierPart(List<InvoiceSupplier> supplierPartlist,String soId) {

		List<SupplierVO> supplierPartVOlist=new ArrayList<SupplierVO>();
		for(InvoiceSupplier supplierPart :supplierPartlist){
			if(null!=supplierPart){
				SupplierVO supplierVO = new SupplierVO();
				supplierVO.setSoId(soId);
				supplierVO.setInvoiceId(supplierPart.getInvoiceId());
				supplierVO.setPartStatus(supplierPart.getPartStatus());
				if(StringUtils.isNotEmpty(supplierPart.getAddress1()) || StringUtils.isNotEmpty(supplierPart.getAddress2())
						|| StringUtils.isNotEmpty(supplierPart.getCity()) || StringUtils.isNotEmpty(supplierPart.getState()) 
						|| StringUtils.isNotEmpty(supplierPart.getZip()) || StringUtils.isNotEmpty(supplierPart.getPhone())
						|| StringUtils.isNotEmpty(supplierPart.getFax()) || null != supplierPart.getLatitude() 
						|| null != supplierPart.getLongitude() || null != supplierPart.getDistance()){
							supplierVO.setLocationDetailPresent(true);
							supplierVO.setAddress1(supplierPart.getAddress1());
							supplierVO.setAddress2(supplierPart.getAddress2());
							supplierVO.setCity(supplierPart.getCity());
							supplierVO.setDistance(supplierPart.getDistance());
							supplierVO.setFax(supplierPart.getFax());							
							supplierVO.setLatitude(supplierPart.getLatitude());
							supplierVO.setLongitude(supplierPart.getLongitude());							
							supplierVO.setPhone(supplierPart.getPhone());
							supplierVO.setState(supplierPart.getState());
							supplierVO.setSupplierName(supplierPart.getSupplierName());
							supplierVO.setZip(supplierPart.getZip());							
							supplierVO.setCreatedDate(new java.util.Date());
							supplierVO.setModifiedDate(new java.util.Date());
							//supplierVO.setDeleteInd(false);
							} else{
								supplierVO.setLocationDetailPresent(false);
							}
				supplierPartVOlist.add(supplierVO);
			}

		}
		return supplierPartVOlist;
	}
	private UpdateSOProviderPartResponse createErrorResponse(String message, String code){
		UpdateSOProviderPartResponse createResponse = new UpdateSOProviderPartResponse();
		Results results = Results.getError(message, code);
		createResponse.setResults(results);
		return createResponse;
	}
	//R12_0 SLM-88 Send Message API
	private void updateNPSForUpdateSOProviderPart(String soId, Integer buyerId,
			InvoiceSupplier supplier,SupplierVO supplierVO) {
		boolean isEligibleForNPSNotification = false;
		String partMessage = null;
		
		try{
    		//call validation method for NPS notification
			isEligibleForNPSNotification = notificationService.validateNPSNotificationEligibility(buyerId,soId);
    		
			if(isEligibleForNPSNotification){
	    		//Creating message for NPS update
				partMessage= MPConstants.PARTS_MESSAGE1+MPConstants.WHITE_SPACE+supplierVO.getPartNumber()+MPConstants.WHITE_SPACE+MPConstants.HYPHEN+MPConstants.WHITE_SPACE+supplierVO.getPartDesc()+MPConstants.PARTS_MESSAGE2+supplierVO.getPartStatus()+MPConstants.TO+supplier.getPartStatus()+MPConstants.DOT;
				//Call Insertion method for NPS notification
				notificationService.insertNotification(soId,partMessage);
			}
    	}catch(Exception e){
			logger.info("Caught Exception while insertNotification"+e);
		}
	}
		
	public IMobileSOActionsBO getMobileSOActionsBO() {
		return mobileSOActionsBO;
	}

	public void setMobileSOActionsBO(IMobileSOActionsBO mobileSOActionsBO) {
		this.mobileSOActionsBO = mobileSOActionsBO;
	}

	public INotificationService getNotificationService() {
		return notificationService;
	}

	public void setNotificationService(INotificationService notificationService) {
		this.notificationService = notificationService;
	}

}
