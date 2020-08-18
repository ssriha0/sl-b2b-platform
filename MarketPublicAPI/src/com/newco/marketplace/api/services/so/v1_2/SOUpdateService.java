/*
 *	Date        Project       Author         Version

 * -----------  --------- 	-----------  	---------
 * 28-Jun-2012	KMSRTVST   Infosys				1.0
 * 
 * 
 */

/*
 * 
 * This is the service class for new API call update
 * 
 */
package com.newco.marketplace.api.services.so.v1_2;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.update.SOUpdateRequest;
import com.newco.marketplace.api.beans.so.update.SOUpdateResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.exceptions.ValidationException;
import com.newco.marketplace.api.services.so.v1_1.SOBaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.so.v1_2.OFServiceOrderMapper;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.serviceorder.BuyerResource;
import com.newco.marketplace.interfaces.OrderConstants;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentRequest;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;

@APIRequestClass(SOUpdateRequest.class)
@APIResponseClass(SOUpdateResponse.class)
public class SOUpdateService extends SOBaseService {
	private Logger logger = Logger.getLogger(SOUpdateService.class);
	private OFServiceOrderMapper ofServiceOrderMapper; 
	private IServiceOrderBO serviceOrderBO;
	
	public SOUpdateService() {
		super(PublicAPIConstant.UPDATE_XSD, PublicAPIConstant.SOUPDATERESPONSE_XSD,
				PublicAPIConstant.SORESPONSE_NAMESPACE,
				PublicAPIConstant.SO_RESOURCES_SCHEMAS_V1_2,
				PublicAPIConstant.SORESPONSE_SCHEMALOCATION,
				SOUpdateRequest.class, SOUpdateResponse.class);

	}

	/**
	 * This method does either increase funds or update customerReference.
	 */
	/* (non-Javadoc)
	 * @see com.newco.marketplace.api.services.so.v1_1.SOBaseService#executeOrderFulfillmentService(com.newco.marketplace.api.common.APIRequestVO)
	 */
	@Override
	protected IAPIResponse executeOrderFulfillmentService(APIRequestVO apiVO) {
		logger.info("Entering SOUpdateService");
		String svcOrdrId = apiVO.getSOId();
		int buyerId = apiVO.getBuyerIdInteger();
		SOUpdateRequest soUpdateRequest = (SOUpdateRequest) apiVO.getRequestFromPostPut();
		
		logger.info("The buyer resource ::"+soUpdateRequest.getIdentification().getId());		
		Integer resourceId = soUpdateRequest.getIdentification().getId();
		try{
			// Validate the buyer resource id in the identification tag		
			BuyerResource buyerResource = ofServiceOrderMapper.getBuyerResource(buyerId,resourceId);
			if(null==buyerResource){
				List<String> validationErrors = new ArrayList<String>();
				validationErrors.add(resourceId + " "+ CommonUtility.getMessage(PublicAPIConstant.INVALID_BUYER_RESOURCE));		
				if(!validationErrors.isEmpty()){
					throw new ValidationException(validationErrors);
				}
			}		
			SecurityContext securityContext = getSecCtxtForBuyerAdmin(buyerId);
			OrderFulfillmentResponse ofResponse=new OrderFulfillmentResponse();

			// Use the buyer resource id for further processing. 
			// Get the max spend limit
			// Check if the buyer resource is administrator
			
			boolean isAdmin = ofServiceOrderMapper.isResourceAbuyerAdmin(buyerResource.getUserName());
			logger.info("isAdmin ::"+isAdmin);
			
			OrderFulfillmentRequest request = ofServiceOrderMapper.updateServiceOrder(svcOrdrId, soUpdateRequest,
					securityContext,buyerResource,isAdmin);
			com.servicelive.orderfulfillment.domain.ServiceOrder so =(com.servicelive.orderfulfillment.domain.ServiceOrder)request.getElement();
			if(null!=soUpdateRequest.getCustomReferences()&& (so.getWfStateId()==OrderConstants.CLOSED_STATUS
					|| so.getWfStateId()==OrderConstants.DELETED_STATUS
					|| so.getWfStateId()==OrderConstants.VOIDED_STATUS
					|| so.getWfStateId()==OrderConstants.CANCELLED_STATUS)){
				ofResponse = ofServiceOrderMapper.updateSOCustRefLogging(so, request);
				
			}else{
				ofResponse = ofHelper.runOrderFulfillmentProcess(svcOrdrId, SignalType.UPDATE_ORDER, request);
			}
			
			return ofServiceOrderMapper.updateSOResponseMapping(ofResponse);
		}
        catch(ValidationException slb){ 
       	 	return createErrorResponse(slb.getErrorMessages(), ResultsCode.INVALID_OR_MISSING_REQUIRED_PARAMS.getCode());
		}
	}

	
	/**
	 * @param message
	 * @param code
	 * @return
	 */
	private SOUpdateResponse createErrorResponse(String message, String code){
		SOUpdateResponse updateResponse=new SOUpdateResponse();
		Results results = Results.getError(message, code);
		updateResponse.setResults(results);
		return updateResponse;
	}
	
	/**
	 * This method dispatches the Edit Service order request.
	 * 
	 * @param apiVO
	 *            APIRequestVO
	 * @return IAPIResponse
	 */
	public IAPIResponse executeLegacyService(APIRequestVO apiVO) {
		return null;

	}

	public void setOfServiceOrderMapper(OFServiceOrderMapper ofServiceOrderMapper) {
		this.ofServiceOrderMapper = ofServiceOrderMapper;
	}

	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}

}
