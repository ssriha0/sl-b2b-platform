package com.newco.marketplace.api.mobile.services.v2_0;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.beans.sodetails.v2_0.RetrieveSOCompletionDetailsMobile;
import com.newco.marketplace.api.mobile.beans.sodetails.v2_0.SOGetMobileCompletionDetailsResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.api.mobile.utils.mappers.SODetailsRetrieveMapper;
import com.newco.marketplace.business.iBusiness.mobile.IMobileSOManagementBO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.business.iBusiness.mobile.IAuthenticateUserBO;
import com.newco.marketplace.business.iBusiness.mobile.IMobileGenericBO;
import com.newco.marketplace.business.iBusiness.mobile.IMobileSOActionsBO;
import com.newco.marketplace.api.beans.so.soDetails.vo.SoDetailsVO;

/**
 * This class would act as a Servicer class for Provider Retrieve Service Order
 * 
 * @author Infosys
 * @version 1.0
 */
@APIResponseClass(SOGetMobileCompletionDetailsResponse.class)
public class SOCompletionDetailsRetrieveService extends BaseService {

	private Logger logger = Logger.getLogger(SOCompletionDetailsRetrieveService.class);
	private SODetailsRetrieveMapper detailsRetrieveMapper;
	private IMobileSOManagementBO mobileSOManagementBO;
	//R14.0
	private IAuthenticateUserBO authenticateUserBO;
	private IMobileGenericBO mobileGenericBO;
	private IMobileSOActionsBO mobileSOActionsBO;

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

	public IMobileSOActionsBO getMobileSOActionsBO() {
		return mobileSOActionsBO;
	}

	public void setMobileSOActionsBO(IMobileSOActionsBO mobileSOActionsBO) {
		this.mobileSOActionsBO = mobileSOActionsBO;
	}

	/**
	 * Constructor
	 */

	public SOCompletionDetailsRetrieveService() {
		super();
	}

	/**
	 * 
	 */
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		logger.info("Entering execute method");	
		boolean isAuthorizedToViewSODetails = true;
		SOGetMobileCompletionDetailsResponse completionDetailsResponse = new SOGetMobileCompletionDetailsResponse();
		RetrieveSOCompletionDetailsMobile retrieveSOCompletionDetailsMobile= null;
		Results results = new Results();
		String timezone = null;
		try {

			String soId = (String) apiVO.getSOId();
			Integer provider= Integer.parseInt(apiVO.getResourceId());

			if (StringUtils.isBlank(apiVO.getResourceId())) {
				logger.info(
						"resource id blank-->"	);
				results = Results.getError(
						ResultsCode.INVALID_RESOURCE_ID
						.getMessage(),
						ResultsCode.INVALID_RESOURCE_ID.getCode());
				completionDetailsResponse.setResults(results);
				return completionDetailsResponse;
			}
			// To validate if the resource Id is valid
			//if a firmId corresponding to the resourceId is obtained, then the resourceId is valid.
			Integer firmId = mobileSOManagementBO
					.validateProviderId(apiVO.getResourceId());
			if (null == firmId) {
				logger.info(
						"firmIdid blank-->"	);
				results = Results.getError(
						ResultsCode.SO_SEARCH_INVALID_PROVIDER
						.getMessage(),
						ResultsCode.SO_SEARCH_INVALID_PROVIDER
						.getCode());
				completionDetailsResponse.setResults(results);
				return completionDetailsResponse;
			}
			
			logger.info("before authorised in view so-->"	);
			// checking if the given is authorized to view the Service Order

			isAuthorizedToViewSODetails = validateProviderForSO(provider,firmId,soId);
			
			if (isAuthorizedToViewSODetails) {
				logger.info("authorised in view so-->"	);
				// If vendor is authorized to view the Service Order,
				// fetching the
				// Service Order Completion details.
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("soId", soId);
				param.put("resourceId", apiVO.getResourceId());				
				retrieveSOCompletionDetailsMobile = mobileSOManagementBO.getCompletionDetails(param);
				
				timezone = retrieveSOCompletionDetailsMobile.getCompletionDetails().getAppointment().getTimeZone();
				if(retrieveSOCompletionDetailsMobile != null && null!= retrieveSOCompletionDetailsMobile.getCompletionDetails()){	
					if(null!=retrieveSOCompletionDetailsMobile.getCompletionDetails().getAddons()&&
							null!=retrieveSOCompletionDetailsMobile.getCompletionDetails().getAddons().getAddonList()&&
							null!=retrieveSOCompletionDetailsMobile.getCompletionDetails().getAddons().getAddonList().getAddon()) {
						detailsRetrieveMapper.validateCoverage(retrieveSOCompletionDetailsMobile.
								getCompletionDetails().getAddons().getAddonList().getAddon());
					}

					if(retrieveSOCompletionDetailsMobile.getCompletionDetails().getAppointment() != null&&
							retrieveSOCompletionDetailsMobile.getCompletionDetails().getDocuments()!=null){
						detailsRetrieveMapper.GMTToGivenTimeZone(retrieveSOCompletionDetailsMobile.getCompletionDetails().
								getAppointment(), retrieveSOCompletionDetailsMobile.getCompletionDetails().getDocuments());
					}

					if(retrieveSOCompletionDetailsMobile.getCompletionDetails().getAppointment() != null&&
							retrieveSOCompletionDetailsMobile.getCompletionDetails().getSignatures()!=null){
						detailsRetrieveMapper.GMTToGivenTimeZone(retrieveSOCompletionDetailsMobile.getCompletionDetails().
								getAppointment(), retrieveSOCompletionDetailsMobile.getCompletionDetails().getSignatures());
					}
					//SL-20673: Edit Completion Details
					//Time zone conversion for the list of trip details present in the response
					if(null != retrieveSOCompletionDetailsMobile.getCompletionDetails() && null != retrieveSOCompletionDetailsMobile.getCompletionDetails().getAppointment()
							&& null != retrieveSOCompletionDetailsMobile.getCompletionDetails().getTripDetails()){
						detailsRetrieveMapper.GMTToGivenTimeZone(retrieveSOCompletionDetailsMobile.getCompletionDetails().getTripDetails(),
								timezone,retrieveSOCompletionDetailsMobile.getCompletionDetails().getServiceLocation());
					}
					//SL-20673: Edit Completion Details
					//Time zone conversion for the Latest trip Details present in the response
					if(null != retrieveSOCompletionDetailsMobile.getCompletionDetails() && null != retrieveSOCompletionDetailsMobile.getCompletionDetails().getAppointment()
							&& null != retrieveSOCompletionDetailsMobile.getCompletionDetails().getLatestTrip()){
						detailsRetrieveMapper.GMTToGivenTimeZone(retrieveSOCompletionDetailsMobile.getCompletionDetails().getLatestTrip(),
								timezone,retrieveSOCompletionDetailsMobile.getCompletionDetails().getServiceLocation());
					}
				}
				
				logger.info("START: calculate ACTUAL_PRICE before adding tax(reverse flow)");
				// START: calculate ACTUAL_PRICE before adding tax(reverse flow)
				if (null != (retrieveSOCompletionDetailsMobile.getCompletionDetails())) {
					if (null != (retrieveSOCompletionDetailsMobile.getCompletionDetails()).getPrice()) {
						
						// START: buyer_id check
						logger.info("BuyerId: " + (retrieveSOCompletionDetailsMobile.getCompletionDetails()).getBuyerId());
						if (null != (retrieveSOCompletionDetailsMobile.getCompletionDetails()).getBuyerId()) {
							
							if ((retrieveSOCompletionDetailsMobile.getCompletionDetails()).getBuyerId().trim().equals("3333")
									|| (retrieveSOCompletionDetailsMobile.getCompletionDetails()).getBuyerId().trim().equals("7777")) {
								
								Double priceBeforeTax = 0.0;
								
								// START: calculate LABOR pricing before adding tax(reverse flow)
								if (null != (retrieveSOCompletionDetailsMobile.getCompletionDetails()).getPrice().getTotalLaborMaximumPrice()) {
									
									if (null != (retrieveSOCompletionDetailsMobile.getCompletionDetails()).getPrice().getLaborTax()) {
										
										priceBeforeTax = getPriceBeforeTaxing(
												(retrieveSOCompletionDetailsMobile.getCompletionDetails()).getPrice().getTotalLaborMaximumPrice(),
												Double.valueOf((retrieveSOCompletionDetailsMobile.getCompletionDetails()).getPrice().getLaborTax()));
										
										logger.info("TotalLabor__MaximumPrice: " + (retrieveSOCompletionDetailsMobile.getCompletionDetails()).getPrice().getTotalLaborMaximumPrice());
										logger.info("Labor_TAX: " + (retrieveSOCompletionDetailsMobile.getCompletionDetails()).getPrice().getLaborTax());
										logger.info("LaborPrice BEFORE_TAX: " + priceBeforeTax);
										
										(retrieveSOCompletionDetailsMobile.getCompletionDetails()).getPrice().setLaborPriceBeforeTax(priceBeforeTax);
									}
								}
								// END: calculate LABOR pricing before adding tax(reverse flow)
								
								
								priceBeforeTax = 0.0;
								// START: calculate PARTS pricing before adding tax(reverse flow)
								if (null != (retrieveSOCompletionDetailsMobile.getCompletionDetails()).getPrice().getTotalPartsMaximumPrice()) {
									
									if (null != (retrieveSOCompletionDetailsMobile.getCompletionDetails()).getPrice().getPartsTax()) {
										
										priceBeforeTax = getPriceBeforeTaxing(
												(retrieveSOCompletionDetailsMobile.getCompletionDetails()).getPrice().getTotalPartsMaximumPrice(),
												Double.valueOf((retrieveSOCompletionDetailsMobile.getCompletionDetails()).getPrice().getPartsTax()));
										
										logger.info("TotalParts__MaximumPrice: " + (retrieveSOCompletionDetailsMobile.getCompletionDetails()).getPrice().getTotalPartsMaximumPrice());
										logger.info("Parts_TAX: " + (retrieveSOCompletionDetailsMobile.getCompletionDetails()).getPrice().getPartsTax());
										logger.info("Parts BEFORE_TAX: " + priceBeforeTax);
										
										(retrieveSOCompletionDetailsMobile.getCompletionDetails()).getPrice().setPartsPriceBeforeTax(priceBeforeTax);
									}
								}
								// END: calculate PARTS pricing before adding tax(reverse flow)
							}
							
						} // END: buyer_id check
						
					} // null != completionDetails.getPrice()
				} // null != completionDetails
				// END: calculate ACTUAL_PRICE before adding tax(reverse flow)
				logger.info("END: calculate ACTUAL_PRICE before adding tax(reverse flow)");
				// ////////////////////// --------------------------------------------------------------------------------------------------------------------------------------------------------
				
				
				// Returning error if Service Order is not found.
				if (null == retrieveSOCompletionDetailsMobile.getCompletionDetails()) {
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
						ResultsCode.PERMISSION_ERROR
						.getMessage(), ResultsCode.PERMISSION_ERROR.getCode());
			}
			completionDetailsResponse.setServiceOrder(retrieveSOCompletionDetailsMobile);
			completionDetailsResponse.setResults(results);
		} catch (Exception e) {
			logger.info(
					"Exception in execute method of SOCompletionDetailsRetrieveService-->"
							+ e.getMessage(), e);
			results = Results.getError(
					ResultsCode.INTERNAL_SERVER_ERROR
					.getMessage(),ResultsCode.INTERNAL_SERVER_ERROR.getCode());
			completionDetailsResponse.setResults(results);
		}
		logger.info("Exiting execute method");
		return completionDetailsResponse;
	}
	
	private Double getPriceBeforeTaxing(Double priceWithTax, Double taxPer) {
		Double priceBeforeTax = 0.0;
		priceBeforeTax = ((100 * priceWithTax) / ((100 * taxPer) + 100));
		return priceBeforeTax;
	}

	/**
	 * @param provider
	 * @param firmId
	 * @param soId
	 * @return
	 * to validate Service Order accessibility for provider based on role.
	 * @throws BusinessServiceException 
	 */
	private boolean validateProviderForSO(Integer provider, Integer firmId,
			String soId) throws BusinessServiceException{
		boolean isAuthorizedToViewSODetails = true;
		Integer resourceRoleLevel = authenticateUserBO.getRoleOfResource(null,provider);

			if(MPConstants.ROLE_LEVEL_ONE.equals(resourceRoleLevel) || MPConstants.ROLE_LEVEL_TWO.equals(resourceRoleLevel)){
				Map<String, String> validateMap = new HashMap<String, String>();
				validateMap.put("soId", soId);
				validateMap.put("resourceId",
						(provider).toString());
				String soIdForAcceptedProvider = mobileSOActionsBO.toValidateSoIdForProvider(validateMap);
				if (null == soIdForAcceptedProvider || StringUtils.isBlank(soIdForAcceptedProvider)) {
					isAuthorizedToViewSODetails = false;
				}
			}
		else if(MPConstants.ROLE_LEVEL_THREE.equals(resourceRoleLevel)){
			if(null!=firmId){
					SoDetailsVO detailsVO = new SoDetailsVO();
					detailsVO.setSoId(soId);
					detailsVO.setFirmId(firmId.toString());
					detailsVO.setProviderId(provider);
					detailsVO.setRoleId(resourceRoleLevel);
					boolean authSuccess = mobileGenericBO.isAuthorizedToViewBeyondPosted(detailsVO);
					if(!authSuccess){
						isAuthorizedToViewSODetails = false;
					}
				}

			}
		else{
			isAuthorizedToViewSODetails = false;
		}
		return isAuthorizedToViewSODetails;	
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