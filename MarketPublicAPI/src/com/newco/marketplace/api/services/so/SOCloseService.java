package com.newco.marketplace.api.services.so;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.price.ServiceOrderPriceVO;
import com.newco.marketplace.dto.vo.serviceorder.AdditionalPaymentVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.BuyerFeatureConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.annotation.Namespace;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.CloseSORequest;
import com.newco.marketplace.api.beans.so.CloseSOResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.DataTypes;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerFeatureSetBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderCloseBO;
import com.newco.marketplace.api.services.so.v1_1.SOBaseService;
import com.newco.marketplace.api.utils.mappers.so.v1_1.OFMapper;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;

/**
 * This class would act as a service class for SO close operation.
 * 
 * API     : /providers/zip
 * APIType : Get
 * Request Processor  : {@link SORequestProcessor#closeSO}
 * Spring Bean Name   : SOCloseService
 * Spring Context XML : apiApplicationContext.xml
 * 
 * @author Shekhar Nirkhe(chandra@servicelive.com)
 * @since 15/10/2009
 * @version 1.0
 * 
 */

@Namespace("http://www.servicelive.com/namespaces/closeso")
@APIRequestClass(CloseSORequest.class)
@APIResponseClass(CloseSOResponse.class)
public class SOCloseService extends SOBaseService {

	private Logger logger = Logger.getLogger(SOCloseService.class);
	private IServiceOrderCloseBO serviceOrderCloseBO;
	private IBuyerFeatureSetBO buyerFeatureSetBO;
	static Map<String, ResultsCode> errorCodeMap = new HashMap<String, ResultsCode>();
	static {
		errorCodeMap.put("08", ResultsCode.SO_NOT_FOUND);
		errorCodeMap.put("12", ResultsCode.SO_CLOSE_ERROR);
	}
	
	/**
	 * Constructor for closing Service Orders.
	 * 
	 */	
	public SOCloseService () {
		addRequiredURLParam(APIRequestVO.BUYERID, DataTypes.INTEGER);
		addRequiredURLParam(APIRequestVO.SOID, DataTypes.STRING);
	}	
	
	/**
	 * It calls backend to close requested SO. In case of an error it will convert backend error code into
	 * PublicAPI error coding using errorCodeMap.
	 * @see BaseService
	 * 
	 */	
	@Override
	public IAPIResponse executeLegacyService(APIRequestVO apiVO) {		
		logger.info("Entering execute method");	
		
		CloseSORequest request  = (CloseSORequest) apiVO.getRequestFromPostPut();
		Integer buyerId =  apiVO.getBuyerIdInteger();
		String serviceOrderID =  apiVO.getSOId();	
		Results results = null;
		CloseSOResponse response = null;
		
		if (request == null) {
		  results = Results.getError(ResultsCode.INVALIDXML_ERROR_CODE.getMessage(), ResultsCode.INVALIDXML_ERROR_CODE.getCode());
		}
		
		Integer buyerResourceId = request.getIdentification().getId();	
		SecurityContext securityContext = super.getSecurityContextForBuyer(buyerResourceId);
		
		if(securityContext == null) {
			results = Results.getError(ResultsCode.INVALID_RESOURCE_ID.getMessage(), ResultsCode.INVALID_RESOURCE_ID.getCode());
			return new CloseSOResponse(results);
		}
		
//		if (buyerId.intValue() != securityContext.getCompanyId().intValue()) {
//			String error = "Buyer Id " + buyerId + " is not associated with resource " 
//						+  request.getIdentification().getBuyerResourceId();
//			results = Results.getError(error, ResultsCode.INVALIDXML_ERROR_CODE.getCode());
//			return new CloseSOResponse(results);
//		}
		
		try {
			ProcessResponse pResp = 
				serviceOrderCloseBO.processCloseSO(buyerId, serviceOrderID, request.getFinalPartsPrice(), request.getFinalLaborPrice(), securityContext);

			if (pResp.isError()) {
				String strErrorCode = pResp.getCode();
				
				String strErrorMessage = null;
				if (null!= pResp.getMessages() && !pResp.getMessages().isEmpty()){
					strErrorMessage = pResp.getMessages().get(0);
				}
				
				ResultsCode code = errorCodeMap.get(strErrorCode);
				if (code != null)
					strErrorCode = code.getCode();
				results = Results.getError(strErrorMessage, strErrorCode);
				response = new CloseSOResponse(results);
			} else {
				if (buyerFeatureSetBO.validateFeature(buyerId, BuyerFeatureConstants.SHC_GL_REPORTING)){
					serviceOrderCloseBO.stageUpsellPaymentAndSku(serviceOrderID);
					serviceOrderCloseBO.stageFinalPrice(serviceOrderID, request.getFinalLaborPrice() + request.getFinalPartsPrice());
				}
				results = Results.getSuccess();
			}
		} catch (BusinessServiceException e) {				
			results = Results.getError(e.getMessage(), ResultsCode.INTERNAL_SERVER_ERROR.getCode());
		}		
		response = new CloseSOResponse(results);		
		logger.info("Exiting execute method");	
		return response;
	}

    @Override
    protected IAPIResponse executeOrderFulfillmentService(APIRequestVO apiVO) {
        com.servicelive.orderfulfillment.domain.ServiceOrder serviceOrder = new com.servicelive.orderfulfillment.domain.ServiceOrder();
        CloseSORequest request  = (CloseSORequest) apiVO.getRequestFromPostPut();

        //R16_1_1: SL-21270: Adding validations for finalLaborPrice and finalPartsPrice in the request
        Results results = validate(serviceOrder,apiVO.getSOId(),request);
        
        if(null==results){
        	results = Results.getSuccess();
            serviceOrder.setFinalPriceLabor(new BigDecimal(request.getFinalLaborPrice()).setScale(2, RoundingMode.HALF_EVEN));
            serviceOrder.setFinalPriceParts(new BigDecimal(request.getFinalPartsPrice()).setScale(2, RoundingMode.HALF_EVEN));
            SecurityContext securityContext = getSecCtxtForBuyerAdmin(apiVO.getBuyerIdInteger());
            OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentProcess(apiVO.getSOId(), SignalType.CLOSE_ORDER, serviceOrder, OFMapper.createBuyerIdentFromSecCtx(securityContext));
            
            if (response.isError()){
            	results = Results.getError(response.getErrorMessage(), ResultsCode.FAILURE.getCode());
            }
        }
        
        return new CloseSOResponse(results);
    }
    
    
	private Results validate(ServiceOrder serviceOrder, String soId, CloseSORequest request) {
		Results results = null;
		boolean errorOccured = false;
		try{
        	//fetching finalLaborPrice and finalPartsPrice and priceType from so_hdr
        	ServiceOrderPriceVO serviceOrderPriceVO =  serviceOrderCloseBO.getFinalPrice(soId);	
        	if (null!=serviceOrderPriceVO){
        		//validating whether order is completed
        		if(null!=serviceOrderPriceVO.getWfState() && 
        				serviceOrderPriceVO.getWfState().intValue() != OrderConstants.COMPLETED_STATUS){
        			results = Results.getError(ResultsCode.INVALID_WF_STATE.getMessage(), ResultsCode.INVALID_WF_STATE.getCode());
        			errorOccured = true;
        		}
        		if(!errorOccured){
        			if(!validatePrice(soId,request.getFinalLaborPrice(),request.getFinalPartsPrice(),
                  			 serviceOrderPriceVO.getOrigSpendLimitLabor(), serviceOrderPriceVO.getOrigSpendLimitParts())){
                  		results = Results.getError(ResultsCode.INVALID_SO_FINAL_PRICE.getMessage(), ResultsCode.INVALID_SO_FINAL_PRICE.getCode());
                  		errorOccured = true;
               		}
               		if(serviceOrderPriceVO.getPriceType().equalsIgnoreCase(OrderConstants.TASK_LEVEL_PRICING)){
                   		serviceOrder.setTaskLevelPricing(true);
                   	}
        		}
        	}
        	if(!errorOccured){
            	//checking for addon Issues
            	AdditionalPaymentVO additionalPaymentVO = serviceOrderCloseBO.getAddonDetails(soId);
            	if((null!=additionalPaymentVO) && ((null == additionalPaymentVO.getAddonId() && StringUtils.isNotBlank(additionalPaymentVO.getPaymentType()) || 
            				(null != additionalPaymentVO.getAddonId() && StringUtils.isBlank(additionalPaymentVO.getPaymentType()))))){
            		results = Results.getError(ResultsCode.ADDON_CLOSE_ERROR.getMessage(), ResultsCode.ADDON_CLOSE_ERROR.getCode());
            		errorOccured = true;
            	}
            	
        	}
        }
        catch (BusinessServiceException e) {				
			logger.error("Exception in fetching SO Details"+ e);
			results = Results.getError(e.getMessage(), ResultsCode.INTERNAL_SERVER_ERROR.getCode());
		} 
		return results;
	}

	/** R16_1_1: SL-21270: Adding validations for finalLaborPrice and finalPartsPrice in the request
	 * @param soId
	 * @param finalLaborPrice
	 * @param finalPartsPrice
	 * @param origFinalPartsPrice 
	 * @param origLaborPrice 
	 * @throws BusinessServiceException 
	 */
	private boolean validatePrice(String soId, Double finalLaborPrice,
			Double finalPartsPrice, Double origLaborPrice, Double origFinalPartsPrice) throws BusinessServiceException {
			boolean isValid = true;
			if(!(finalLaborPrice.equals(origLaborPrice)) || !(finalPartsPrice.equals(origFinalPartsPrice))){
				isValid = false;
			}
			return isValid;
	}

	public IServiceOrderCloseBO getServiceOrderCloseBO() {
		return serviceOrderCloseBO;
	}

	public void setServiceOrderCloseBO(IServiceOrderCloseBO serviceOrderCloseBO) {
		this.serviceOrderCloseBO = serviceOrderCloseBO;
	}

	public IBuyerFeatureSetBO getBuyerFeatureSetBO() {
		return buyerFeatureSetBO;
	}

	public void setBuyerFeatureSetBO(IBuyerFeatureSetBO buyerFeatureSetBO) {
		this.buyerFeatureSetBO = buyerFeatureSetBO;
	}
}

