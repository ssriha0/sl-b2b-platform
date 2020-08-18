package com.newco.marketplace.api.mobile.services.v2_0;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.beans.sodetails.ServiceLocation;
import com.newco.marketplace.api.mobile.beans.sodetails.v2_0.RetrieveSODetailsMobile;
import com.newco.marketplace.api.mobile.beans.sodetails.v2_0.SOGetMobileDetailsResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.api.mobile.utils.mappers.SODetailsRetrieveMapper;
import com.newco.marketplace.business.iBusiness.mobile.IMobileSOManagementBO;

/**
 * This class would act as a Servicer class for Provider Retrieve Service Order
 * 
 * @author Infosys
 * @version 1.0
 */
@APIResponseClass(SOGetMobileDetailsResponse.class)
public class SODetailsRetrieveService extends BaseService {

	private Logger logger = Logger.getLogger(SODetailsRetrieveService.class);
	private SODetailsRetrieveMapper detailsRetrieveMapper;
	private IMobileSOManagementBO mobileSOManagementBO;
	

	/**
	 * Constructor
	 */

	public SODetailsRetrieveService() {
				super();
	}
    
	/**
	 * 
	 */
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		logger.info("Entering execute method");
		SOGetMobileDetailsResponse soGetResponse = new SOGetMobileDetailsResponse();
		boolean isAuthorizedToViewSODetails = true;
		RetrieveSODetailsMobile serviceOrderResponse =null;
		Results results = new Results();
		
		String timezone = null;
		try {

			String soId = (String) apiVO.getSOId();
			if (StringUtils.isBlank(apiVO.getResourceId())) {
				logger.info(
						"resource id blank-->"	);
				results = Results.getError(
						ResultsCode.INVALID_RESOURCE_ID
								.getMessage(),
						ResultsCode.INVALID_RESOURCE_ID.getCode());
				soGetResponse.setResults(results);
				return soGetResponse;
			}
			// To validate if the resource Id is valid
			//if a firmId corresponding to the resourceId is obtained, then the resourceId is valid.
			Integer firmId = mobileSOManagementBO.validateProviderId(apiVO.getResourceId());
			if (null == firmId) {
				logger.info(
						"firmIdid blank-->"	);
				results = Results.getError(
						ResultsCode.SO_SEARCH_INVALID_PROVIDER
								.getMessage(),
						ResultsCode.SO_SEARCH_INVALID_PROVIDER
								.getCode());
				soGetResponse.setResults(results);
				return soGetResponse;
			}
			logger.info(
					"before authorised in view so-->"	);
			// checking if the given is authorized to view the Service Order

			isAuthorizedToViewSODetails = mobileSOManagementBO
					.isAuthorizedInViewSODetails(soId,
							apiVO.getResourceId());
			if (isAuthorizedToViewSODetails) {
				logger.info(
						"authorised in view so-->"	);
				// If vendor is authorized to view the Service Order,
				// fetching the
				// Service Order details.
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("soId", soId);
				param.put("resourceId", apiVO.getResourceId());
				logger.info(
						"before going to bo-->"+param.get(soId)	);
				serviceOrderResponse = mobileSOManagementBO 
						.getServiceOrderDetails(param);
				
				 timezone = serviceOrderResponse.getSoDetails().getAppointment().getTimeZone();
				if (serviceOrderResponse.getSoDetails()!=null){
					detailsRetrieveMapper.GMTToGivenTimeZone(serviceOrderResponse.getSoDetails().
						getAppointment(),serviceOrderResponse.getSoDetails().getServiceLocation());
					
				}
				if (serviceOrderResponse.getSoDetails() != null && serviceOrderResponse.getSoDetails().getAppointment() != null
						&& serviceOrderResponse.getSoDetails().getNotes() != null){
					detailsRetrieveMapper.GMTToGivenTimeZone(serviceOrderResponse.getSoDetails().
						getAppointment(),serviceOrderResponse.getSoDetails().getNotes(),serviceOrderResponse.getSoDetails().getServiceLocation());
					
				}
				if(serviceOrderResponse.getSoDetails() != null && serviceOrderResponse.getSoDetails().getAppointment() != null&&
						serviceOrderResponse.getSoDetails().getDocuments()!=null){
					detailsRetrieveMapper.GMTToGivenTimeZone(serviceOrderResponse.getSoDetails().
							getAppointment(), serviceOrderResponse.getSoDetails().getDocuments());
				}
				if(null != serviceOrderResponse.getSoDetails() && null != serviceOrderResponse.getSoDetails().getAppointment()
						&& null != serviceOrderResponse.getSoDetails().getTripDetails()){
					detailsRetrieveMapper.GMTToGivenTimeZone(serviceOrderResponse.getSoDetails().getTripDetails(),
							timezone,serviceOrderResponse.getSoDetails().getServiceLocation());
				}
				//SL-20673: Edit Completion Details
				//Time zone conversion for the Latest trip Details present in the response
				if(null != serviceOrderResponse.getSoDetails() && null != serviceOrderResponse.getSoDetails().getAppointment()
						&& null != serviceOrderResponse.getSoDetails().getLatestTrip()){
					detailsRetrieveMapper.GMTToGivenTimeZone(serviceOrderResponse.getSoDetails().getLatestTrip(),
							timezone,serviceOrderResponse.getSoDetails().getServiceLocation());
				}
				// Returning error if Service Order is not found.
				if (null == serviceOrderResponse) {
					results = Results.getError(
							ResultsCode.INVALID_SO_ID
									.getMessage(),ResultsCode.INVALID_SO_ID.getCode());
				} else {
					results = Results
							.getSuccess(ResultsCode.RETRIEVE_RESULT_CODE_SUCCESS
									.getMessage());
				}					
			} else {
				// if vendor is not authorized to view Service Order
				// details, we are returning the error response.
				results = Results.getError(
						ResultsCode.INVALID_SO_PROVIDER_ASSOCIATION
								.getMessage(), ResultsCode.INVALID_SO_PROVIDER_ASSOCIATION.getCode());
			}
			soGetResponse.setServiceOrder(serviceOrderResponse);
			soGetResponse.setResults(results);

		} catch (Exception e) {
			logger.info(
					"Exception in execute method of SODetailsRetrieveService-->"
							+ e.getMessage(), e);
			
		}
		logger.info("Exiting execute method");
		return soGetResponse;
	}

	public SODetailsRetrieveMapper getDetailsRetrieveMapper() {
		return detailsRetrieveMapper;
	}

	public void setDetailsRetrieveMapper(
			SODetailsRetrieveMapper detailsRetrieveMapper) {
		this.detailsRetrieveMapper = detailsRetrieveMapper;
	}

	public IMobileSOManagementBO getMobileSOManagementBO() {
		return mobileSOManagementBO;
	}

	public void setMobileSOManagementBO(IMobileSOManagementBO mobileSOManagementBO) {
		this.mobileSOManagementBO = mobileSOManagementBO;
	}

}