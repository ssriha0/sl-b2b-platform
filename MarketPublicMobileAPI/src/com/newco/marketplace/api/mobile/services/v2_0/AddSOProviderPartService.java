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
import com.newco.marketplace.api.mobile.beans.addsoproviderpart.AddSOProviderPartRequest;
import com.newco.marketplace.api.mobile.beans.addsoproviderpart.AddSOProviderPartResponse;
import com.newco.marketplace.api.mobile.beans.addsoproviderpart.ProviderPartDetails;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.constants.PublicMobileAPIConstant;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.mobile.IMobileSOActionsBO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.inhomeoutboundnotification.service.INotificationService;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.utils.MoneyUtil;
import com.newco.marketplace.vo.mobile.InvoicePartsVO;
/**
* This class would act as a Servicer class for adding provider part
* 
* @author Infosys
* @version 2.0
*/

@APIRequestClass(AddSOProviderPartRequest.class)
@APIResponseClass(AddSOProviderPartResponse.class)
public class AddSOProviderPartService extends BaseService {
    
	private static Logger logger =Logger.getLogger(AddSOProviderPartService.class);
	private IMobileSOActionsBO mobileSOActionsBO;
	private INotificationService notificationService;
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		logger.info("Entering execute method of AddSOProviderPartService");
		AddSOProviderPartResponse addSOProviderPartResponse =new AddSOProviderPartResponse();
		/* List<Integer> invoicePartIdList = new ArrayList<Integer>(); */
		List<ProviderPartDetails> providerPartList = null;
		AddSOProviderPartRequest addSOProviderPartRequest = (AddSOProviderPartRequest) apiVO
				.getRequestFromPostPut();
		String soId = (String) apiVO.getSOId();
		String currentTripNo = addSOProviderPartRequest.getCurrentTripNo();
		String tripStatus = addSOProviderPartRequest.getTripStatus();
		Integer currentTripId =null;	
		String userName = null;
		Integer providerId = apiVO.getProviderResourceId();
		String invoicePartsPricingModel=null;
		SecurityContext securityContext = getSecurityContextForVendor(providerId);
		if(null != securityContext){
			userName = securityContext.getUsername();
		}
		
		// Validating buyerId and So id Status.
		if (null != addSOProviderPartRequest.getProviderPartsList()
				&& !(addSOProviderPartRequest.getProviderPartsList().getProviderPart().isEmpty())) {
			try {
				addSOProviderPartRequest = validate(soId,addSOProviderPartRequest);
				if (ResultsCode.SUCCESS != addSOProviderPartRequest.getValidationCode()) {
					
					return createErrorResponse(addSOProviderPartRequest.getValidationCode().getMessage(),
							                   addSOProviderPartRequest.getValidationCode().getCode());
				}else{					 				
					List<InvoicePartsVO> partlist = new ArrayList<InvoicePartsVO>();
                    providerPartList = addSOProviderPartRequest.getProviderPartsList().getProviderPart();
                 
                    //R12_1:
        			//If a new part is created for a service order and if the parts_invoice_pricing model column has no value
        			//and if the adjudication is on , then  update the so_work_flowcontrols  parts_invoice_pricing_type column  as 'cost plus model'.
        	        Boolean autoAdjudicationInd= mobileSOActionsBO.getAutoAdjudicationInd();
                    invoicePartsPricingModel=mobileSOActionsBO.getInvoicePartsPricingModel(soId);
                    if(null == invoicePartsPricingModel){
                    	if(true==autoAdjudicationInd){
                    		mobileSOActionsBO.saveInvoicePartPricingModel(soId, MPConstants.INVOICE_PARTS_COSTPLUS_PRICING_MODEL);
                    		invoicePartsPricingModel=MPConstants.INVOICE_PARTS_COSTPLUS_PRICING_MODEL;
                    	}
                    	else{
                    		mobileSOActionsBO.saveInvoicePartPricingModel(soId, MPConstants.INVOICE_PARTS_REIMBURSEMENT_PRICING_MODEL);
                    		invoicePartsPricingModel=MPConstants.INVOICE_PARTS_REIMBURSEMENT_PRICING_MODEL;
                    	}
                    }
                 mapProviderPart(providerPartList, partlist,autoAdjudicationInd,soId,invoicePartsPricingModel);
                    
                       
                    //Saving parts details
                    if(StringUtils.isNotBlank(currentTripNo)){
                    	//R12.3 - SL-230763 - START 
			             //openTrip = mobileSOActionsBO.validateLatestOpenTrip(Integer.parseInt(currentTrip), soId);
			             currentTripId = mobileSOActionsBO.getTripId(Integer.parseInt(currentTripNo), soId);
			              if(null!=currentTripId) {
			            //R12.3 - SL-230763 - END
			            	   mobileSOActionsBO.addTripDetails(currentTripId,MPConstants.PART_CHANGE_TYPE, MPConstants.PART_ADDED,userName);
			            	   mobileSOActionsBO.insertInhomePartDetails(partlist, soId,userName);
			            	   //R12_0 SLM-88 Send Message API on adding part
			            	   for(ProviderPartDetails details:providerPartList){
			            		   	updateNPSForAddSOProviderPart(details,soId);
			            	   }
			            	   //R12_0 Sprint 4
			            	   mobileSOActionsBO.insertPartsRequiredInd(soId,MPConstants.INDICATOR_PARTS_ADDED);
			               }
			               else{
			            	   return createErrorResponse(ResultsCode.INVALID_TRIP_NUMBER.getMessage(),ResultsCode.INVALID_TRIP_NUMBER.getCode());			   				              
			               }			            	   
			 		  }
					//Associating with trip Number if trip present.					
					addSOProviderPartResponse.setSoId(soId);					
					addSOProviderPartResponse.setResults(Results.getSuccess());	
					return addSOProviderPartResponse;
				}
			} catch (Exception e) {
				logger.error("Exception in processing the request"+ e.getMessage());
				return createErrorResponse(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(),ResultsCode.INTERNAL_SERVER_ERROR.getCode());
			}
		}else {
			return createErrorResponse(ResultsCode.UNABLE_TO_PROCESS_THE_REQUEST.getMessage(),ResultsCode.UNABLE_TO_PROCESS_THE_REQUEST.getCode());
         }
	}
	//R12_0 SLM-88 Send Message API on adding parts
	private void updateNPSForAddSOProviderPart(ProviderPartDetails details, String soId) {
		boolean isEligibleForNPSNotification = false;
		String partMessage = null;
		
		try{
    		//call validation method for NPS notification
			isEligibleForNPSNotification = notificationService.validateNPSNotificationEligibility(MPConstants.INHOME_BUYER,soId);
    		
			if(isEligibleForNPSNotification){
	    		//Creating message for NPS update
				partMessage= details.getPartNumber()+MPConstants.WHITE_SPACE+MPConstants.HYPHEN+MPConstants.WHITE_SPACE+details.getPartDescription()+MPConstants.PARTS_ADD_MESSAGE;
				//Call Insertion method for NPS notification
				notificationService.insertNotification(soId,partMessage);
			}
    	}catch(Exception e){
			logger.info("Caught Exception while insertNotification"+e);
		}
	}

	private AddSOProviderPartRequest validate(String soId,AddSOProviderPartRequest addSOProviderPartRequest) throws BusinessServiceException {
		ResultsCode validationResponse = ResultsCode.SUCCESS;
		try {
			ServiceOrder serviceOrder = mobileSOActionsBO.getServiceOrder(soId);
			boolean isValidBuyer=false;
			
			if(null!=serviceOrder){
			Integer wfStateId = serviceOrder.getWfStateId();
			Integer buyerId = serviceOrder.getBuyerId();
			if(null!= buyerId &&MPConstants.INHOME_BUYER .equals(buyerId)){
				isValidBuyer = true;
			}else{
				addSOProviderPartRequest.setValidationCode(ResultsCode.INVALID_BUYER);
				return addSOProviderPartRequest;
			}
            if(isValidBuyer && null != wfStateId && !PublicMobileAPIConstant.ORDER_ACTIVE.equals(wfStateId)){
            	addSOProviderPartRequest.setValidationCode(ResultsCode.INVALID_SO_STATUS);
            	return addSOProviderPartRequest;
			} 
           else{
				addSOProviderPartRequest.setValidationCode(validationResponse);
			}}
			
		} catch (BusinessServiceException e) {
			logger.error("Exception in validating so buyer association"+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		
		return addSOProviderPartRequest;
	}
	private AddSOProviderPartResponse createErrorResponse(String message, String code){
		AddSOProviderPartResponse createResponse = new AddSOProviderPartResponse();
		Results results = Results.getError(message, code);
		createResponse.setResults(results);
		return createResponse;
	}
	
	/**
	 * @param Partlist
	 * Mapping the request inputs into corresponding  parts associated with SO
	 * @param existingInvoicePartlist */
	
	private void mapProviderPart(List<ProviderPartDetails> providerPartList,
			List<InvoicePartsVO> partlist,boolean autoAdjudicationInd,String soId,String invoicePartsPricingModel) throws BusinessServiceException{
			//Fetching the percentage value from DB
			Double adjudicationCommPricePercentage=null;
			adjudicationCommPricePercentage=Double.parseDouble((mobileSOActionsBO.getConstantValueFromDB(MPConstants.ADJUDICATION_PRICE_PERC_KEY)));
			String coverage = null;
		try {
				if(null!=providerPartList && providerPartList.size()>0){
					coverage = mobileSOActionsBO.mapPartCoverage(soId);
				}
		} catch (BusinessServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (ProviderPartDetails providerPartDetails : providerPartList) {
			if (null != providerPartDetails) {
				InvoicePartsVO partsVO = new InvoicePartsVO();
				Double retailPrice=null;
				Double commercialPrice=null;
				
				partsVO.setCategory(providerPartDetails.getCategory());
				partsVO.setDivisionNumber(providerPartDetails
						.getDivisionNumber());
				partsVO.setPartDescription(providerPartDetails
						.getPartDescription());
				partsVO.setPartNumber(providerPartDetails.getPartNumber());
				partsVO.setQty(providerPartDetails.getQty());
				partsVO.setSourceNumber(providerPartDetails.getSourceNumber());
				partsVO.setPartStatus(MPConstants.ADDED);
				if(MPConstants.INVOICE_PARTS_COSTPLUS_PRICING_MODEL.equals(invoicePartsPricingModel)){
					partsVO.setAutoAdjudicationInd(true);
					partsVO.setRetailPrice(providerPartDetails.getRetailPrice());
					//R12_1: changes to add value to column commercial_price
					retailPrice=Double.parseDouble(providerPartDetails.getRetailPrice());
					commercialPrice=MoneyUtil.getRoundedMoney(retailPrice - (retailPrice*(adjudicationCommPricePercentage/100)));
					partsVO.setCommercialPrice(commercialPrice.toString());
	
				}else{
					partsVO.setAutoAdjudicationInd(false);	
					partsVO.setRetailPrice(providerPartDetails.getRetailPrice());
					partsVO.setCommercialPrice(providerPartDetails.getRetailPrice());
				}
				
				// Default value as MANUAL
				partsVO.setPartNoSource(MPConstants.PART_SOURCE_MANUAL);
				if(StringUtils.isNotBlank(providerPartDetails.getIsManual()) && 
						MPConstants.MANUAL_NO.equalsIgnoreCase(providerPartDetails.getIsManual())){
				    partsVO.setPartNoSource(MPConstants.PART_SOURCE_LIS);
				}
				if(null!=partsVO){
					partsVO.setPartCoverage(coverage);
				}
				partlist.add(partsVO);
			}
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
