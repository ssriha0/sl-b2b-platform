/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 21-Oct-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.services.so.v1_1;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.reschedule.SORejectRescheduleResponse;
import com.newco.marketplace.api.beans.so.reschedule.SORescheduleRequest;
import com.newco.marketplace.api.beans.so.reschedule.SORescheduleResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.so.v1_1.OFMapper;
import com.newco.marketplace.api.utils.mappers.so.v1_1.SORescheduleMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.vo.ordermanagement.so.RescheduleVO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.servicelive.orderfulfillment.domain.SOSchedule;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;

/**
 * This class is a service class for cancel Service Order
 * 
 * 
 * @author Infosys
 * @version 1.0
 */
public class SOCancelRescheduleService extends SOBaseService{

	private Logger logger = Logger.getLogger(SORescheduleService.class);
	private IServiceOrderBO serviceOrderBO;
	private SORescheduleMapper rescheduleMapper;

	/**
	 * Constructor
	 */

	public SOCancelRescheduleService () {
		super (null,
				PublicAPIConstant.SORESPONSE_XSD, 
				PublicAPIConstant.SORESPONSE_NAMESPACE, 
				PublicAPIConstant.SO_RESOURCES_SCHEMAS_V1_1,
				PublicAPIConstant.SORESPONSE_SCHEMALOCATION,	
				SORescheduleRequest.class,
				SORejectRescheduleResponse.class);
	}	



	/**
	 * This method dispatches the reschedule Service order request.
	 * 
	 * @param apiVO APIRequestVO
	 * @return IAPIResponse
	 */
    @Override
    protected IAPIResponse executeLegacyService(APIRequestVO apiVO) {		
		logger.info("Entering SORescheduleService.execute()");	
		SORescheduleResponse rescheduleResponse = new SORescheduleResponse();
		int buyerId=0;
		Integer providerId=0;
		String soId = apiVO.getSOId();
		if(null!=apiVO.getProviderIdInteger()){
			providerId = apiVO.getProviderIdInteger();
			super.responseXsd=PublicAPIConstant.SOPROVIDERRESPONSE_XSD;
			super.namespace=PublicAPIConstant.SORESPONSE_PRO_NAMESPACE;
			super.schemaLocationReq=PublicAPIConstant.SO_RESOURCES_SCHEMAS_PRO;
			super.schemaLocationRes=PublicAPIConstant.SO_RESOURCES_SCHEMAS_PRO;
		}
		if(null!=apiVO.getBuyerIdInteger()){
			buyerId = apiVO.getBuyerIdInteger();
		}
		SecurityContext securityContext = null;
		try {
			if(((String)apiVO.getProperty(PublicAPIConstant.ServiceOrder.RESOURCE_TYPE)).
					equals(PublicAPIConstant.ServiceOrder.RESOURCE_TYPE_BUYER)){
				securityContext = getSecurityContextForBuyerAdmin(buyerId);
			}else{
				securityContext = super.getSecurityContextForVendorAdmin(providerId);
			}
			
			if(securityContext==null){
				throw (new NumberFormatException());
			}
			ServiceOrder serviceOrder = serviceOrderBO.getServiceOrder(soId);
 
			ProcessResponse processResponse = new ProcessResponse();
			Integer userRole = securityContext.getRoleId();
			processResponse = serviceOrderBO.cancelRescheduleRequest(soId,
						userRole, securityContext.getCompanyId(), securityContext);
			rescheduleResponse = rescheduleMapper.cancelRescheduleSOResponseMapping(
					processResponse, serviceOrder);
		}
		catch(NumberFormatException nme){
			logger.error("SORescheduleService.execute(): Number Format Exception "
								+"occurred for resourceId:");
			rescheduleResponse.setResults( Results.getError(
					ResultsCode.INVALID_RESOURCE_ID
					.getMessage(), ResultsCode.INVALID_RESOURCE_ID.getCode()));
		}
		catch (BusinessServiceException e ) {
			rescheduleResponse.setResults(
					Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(), 
							ResultsCode.INTERNAL_SERVER_ERROR.getCode()));
		}
		if(logger.isInfoEnabled()){
			logger.info("Exiting  SORescheduleService.execute()");
		}	
		return rescheduleResponse;



	}

	/**
	 * This method is for setting error messages
	 * 
	 * @param processResponse ProcessResponse
	 * @param msg  String
	 * @return ProcessResponse
	 */
	public ProcessResponse setErrorMsg(ProcessResponse processResponse,
			String msg) {
		logger.error("Error Occurred" + msg);
	//	processResponse.setCode(USER_ERROR_RC);
	//	processResponse.setSubCode(USER_ERROR_RC);
		List<String> arr = new ArrayList<String>();
		arr.add(msg);
		processResponse.setMessages(arr);
		return processResponse;
	}


	public IServiceOrderBO getServiceOrderBO() {
		return serviceOrderBO;
	}

	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}

	public SORescheduleMapper getRescheduleMapper() {
		return rescheduleMapper;
	}

	public void setRescheduleMapper(SORescheduleMapper rescheduleMapper) {
		this.rescheduleMapper = rescheduleMapper;
	}



    @Override
    protected IAPIResponse executeOrderFulfillmentService(APIRequestVO apiVO) {
        Integer buyerId=0;
        Integer providerId=0;
        try{
            String soId = apiVO.getSOId();
            SignalType signal;
            Identification ident;
            
            if(((String)apiVO.getProperty(PublicAPIConstant.ServiceOrder.RESOURCE_TYPE)).
                    equals(PublicAPIConstant.ServiceOrder.RESOURCE_TYPE_BUYER)){
            	if (StringUtils.isNotBlank(apiVO.getBuyerId())){
            		buyerId=apiVO.getBuyerIdInteger();
            		}
            	
            	//SLT-1037 : Fetching reschedule info to check if there is any pending reschedule request.
				RescheduleVO soRescheduleInfo = serviceOrderBO.getRescheduleInfo(soId);
				if (soRescheduleInfo.getRescheduleServiceStartDate() == null) {
					SORejectRescheduleResponse returnVal = new SORejectRescheduleResponse();
					returnVal.setResults(Results.getError(
							"Cancellation request discarded. There is no reschedule request pending for this order.",
							ResultsCode.FAILURE.getCode()));
					return returnVal;
				}
            	//SLT-1037 : End
            	
                SecurityContext securityContext = getSecurityContextForBuyerAdmin(buyerId);
                ident = OFMapper.createBuyerIdentFromSecCtx(securityContext);
                signal=SignalType.BUYER_CANCEL_RESCHEDULE;
            }else{
            	if (StringUtils.isNotBlank(apiVO.getProviderId())){
            		providerId=apiVO.getProviderIdInteger();
            	}
                SecurityContext securityContext = super.getSecurityContextForVendorAdmin(providerId);
                ident = OFMapper.createProviderIdentFromSecCtx(securityContext);
                signal=SignalType.PROVIDER_CANCEL_RESCHEDULE;
            }
            
            OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentProcess(soId, signal, new SOSchedule(), ident);
            return mapOFResponse(response);
        }
        catch (Exception e) {			
			logger.error("Exception in Cancel Reschedule Service"+ e.getMessage());
			SORejectRescheduleResponse returnVal = new SORejectRescheduleResponse();
			returnVal.setResults(Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(),ResultsCode.INTERNAL_SERVER_ERROR.getCode()));
			return returnVal;
		}
    }
    
    private IAPIResponse mapOFResponse(OrderFulfillmentResponse response){
        SORejectRescheduleResponse returnVal = new SORejectRescheduleResponse();
        if(!response.getErrors().isEmpty()){
            returnVal.setResults(Results.getError(response.getErrorMessage(), ResultsCode.FAILURE.getCode()));
        }else{
            returnVal.setResults(Results.getSuccess(ResultsCode.CANCEL_RESCHEDULE_REQUEST_SUBMITTED.getMessage()));
        }
        return returnVal;
    }
}
