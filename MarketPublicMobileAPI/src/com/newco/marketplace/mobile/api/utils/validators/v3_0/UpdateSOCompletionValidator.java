package com.newco.marketplace.mobile.api.utils.validators.v3_0;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.ErrorResult;
import com.newco.marketplace.api.beans.so.soDetails.vo.SoDetailsVO;
import com.newco.marketplace.api.mobile.beans.sodetails.PermitAddon;
import com.newco.marketplace.api.mobile.beans.sodetails.PermitTask;
import com.newco.marketplace.api.mobile.beans.sodetails.Permits;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.AddOn;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.AddOnPaymentDetails;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.AddOns;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.PartTracking;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.PartsTracking;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.Pricing;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.Reference;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.References;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.Signature;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.Task;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.Tasks;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.v3_0.UpdateSOCompletionRequest;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.constants.PublicMobileAPIConstant;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.business.iBusiness.mobile.IAuthenticateUserBO;
import com.newco.marketplace.business.iBusiness.mobile.IMobileGenericBO;
import com.newco.marketplace.business.iBusiness.mobile.IMobileSOActionsBO;
import com.newco.marketplace.business.iBusiness.mobile.IMobileSOManagementBO;
import com.newco.marketplace.business.iBusiness.provider.IW9RegistrationBO;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.buyer.BuyerReferenceVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.CreditCardConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.util.PropertiesUtils;
import com.newco.marketplace.utils.CreditCardValidatonUtil;
import com.newco.marketplace.utils.MoneyUtil;
import com.newco.marketplace.utils.UIUtils;
import com.servicelive.orderfulfillment.domain.SOAdditionalPayment;
import com.servicelive.orderfulfillment.domain.SOAddon;
import com.servicelive.orderfulfillment.domain.SOCustomReference;
import com.servicelive.orderfulfillment.domain.SOProviderInvoiceParts;
import com.servicelive.orderfulfillment.domain.SOTask;
import com.servicelive.orderfulfillment.domain.SOWorkflowControls;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

/**
 * The class acts as the entry point for the validation done for update SO
 * Completion
 * 
 */
public class UpdateSOCompletionValidator {

	private Logger logger = Logger.getLogger(UpdateSOCompletionValidator.class);

	private static final Double MAX_ADDON_CUSTOMER_CHARGE = 2500.00;
	private static final String UPSELL_PAYMENT_TYPE_CHECK = "Check";
	private static final String UPSELL_PAYMENT_TYPE_CREDIT = "Credit Card";
	private IW9RegistrationBO w9RegistrationBO;
	
	private IAuthenticateUserBO authenticateUserBO;
	private IMobileGenericBO mobileGenericBO;
	
	//SL-21811
	private static final Double RETAIL_MAX_PRICE = Double.parseDouble(PropertiesUtils.getPropertyValue("RETAIL_MAX_PRICE"));

	/**
	 * @return the authenticateUserBO
	 */
	public IAuthenticateUserBO getAuthenticateUserBO() {
		return authenticateUserBO;
	}

	/**
	 * @param authenticateUserBO the authenticateUserBO to set
	 */
	public void setAuthenticateUserBO(IAuthenticateUserBO authenticateUserBO) {
		this.authenticateUserBO = authenticateUserBO;
	}

	/**
	 * @return the mobileGenericBO
	 */
	public IMobileGenericBO getMobileGenericBO() {
		return mobileGenericBO;
	}

	/**
	 * @param mobileGenericBO the mobileGenericBO to set
	 */
	public void setMobileGenericBO(IMobileGenericBO mobileGenericBO) {
		this.mobileGenericBO = mobileGenericBO;
	}

	public IW9RegistrationBO getW9RegistrationBO() {
		return w9RegistrationBO;
	}

	public void setW9RegistrationBO(IW9RegistrationBO w9RegistrationBO) {
		this.w9RegistrationBO = w9RegistrationBO;
	}

	private Double addonSum = 0.0;
	public UpdateSOCompletionValidator() {
		this.addonSum = 0.00;

	}

	public Double getAddonSum() {
		return addonSum;
	}

	public void setAddonSum(Double addonSum) {
		this.addonSum = addonSum;
	}

	private IMobileSOActionsBO mobileSOActionsBO;
	private IMobileSOManagementBO mobileSOManagementBO;

	public IMobileSOManagementBO getMobileSOManagementBO() {
		return mobileSOManagementBO;
	}

	public void setMobileSOManagementBO(IMobileSOManagementBO mobileSOManagementBO) {
		this.mobileSOManagementBO = mobileSOManagementBO;
	}

	public IMobileSOActionsBO getMobileSOActionsBO() {
		return mobileSOActionsBO;
	}

	public void setMobileSOActionsBO(IMobileSOActionsBO mobileSOActionsBO) {
		this.mobileSOActionsBO = mobileSOActionsBO;
	}

	/**
	 * @param completionRequest
	 * @return
	 * @throws BusinessServiceException 
	 */
	public List<ErrorResult> validate(
			UpdateSOCompletionRequest completionRequest, String serviceOrderId,
			Integer proId, Integer vendorId,String buyer) throws BusinessServiceException {
		boolean errorOccured = false;
		List<ErrorResult> validationErrors = new ArrayList<ErrorResult>();
		
		//this.errorOccured = false;
		errorOccured = validateProviderAndSO(completionRequest,serviceOrderId,proId,vendorId,errorOccured,validationErrors);
		errorOccured = validateGeneralRequirements(completionRequest,errorOccured,validationErrors);
		//R12.0 SPRINT 2
		//R12.3: Modified the validate method for SL-20673 Edit Completion Details
		errorOccured = validateTrip(completionRequest,serviceOrderId,errorOccured,validationErrors);
		if (PublicMobileAPIConstant.COMPLETION_STATUS_COMPLETED
				.equals(completionRequest.getCompletionStatus()) && MPConstants.WORK_STARTED
				.equals(completionRequest.getUpdateAction())) {

			errorOccured= validateTasks(completionRequest.getTasks(),serviceOrderId,errorOccured,validationErrors);
			errorOccured= validateAddOnPanel(completionRequest.getAddOns(),errorOccured,validationErrors);
			// Validate Check information
			errorOccured= validateAddOnPaymentDetails(completionRequest.getAddOnPaymentDetails(),errorOccured,validationErrors);
			errorOccured= validatePermits(completionRequest.getPermits(),serviceOrderId,errorOccured,validationErrors);
			//TODO:R12_0- commenting as this is no longer part of updateCompleteion API 
			//validateParts(completionRequest.getParts());
			errorOccured= validateBuyerReferencePanel(completionRequest.getReferences(),serviceOrderId,errorOccured,validationErrors);	
			errorOccured= validatePartsTracking(completionRequest.getPartsTracking(),errorOccured,validationErrors);
		}
		else if (PublicMobileAPIConstant.COMPLETION_STATUS_COMPLETED
				.equals(completionRequest.getCompletionStatus()) && MPConstants.SERVICE_COMPLETED
				.equals(completionRequest.getUpdateAction())) {
			errorOccured= validateGeneralCompletionInfoPanel(completionRequest,buyer,errorOccured,validationErrors);
			errorOccured= validateW9Registration(completionRequest,vendorId,errorOccured,validationErrors);
		}
		return validationErrors;
	}

	//validate carrier type only if tracking number is present
	private boolean validatePartsTracking(PartsTracking tracking,boolean errorOccured,List<ErrorResult> validationErrors){
		if(!errorOccured && null!=tracking && !tracking.getPart().isEmpty()){
			List<PartTracking> part = tracking.getPart();
			for(PartTracking partTracking:part){
				if(StringUtils.isNotBlank(partTracking.getTrackingNumber()) && StringUtils.isBlank(partTracking.getCarrier())){
					errorOccured=true;
					addError(
							ResultsCode.UPDATE_SO_COMPLETION_INVALID_CARRIER
							.getMessage(),
							ResultsCode.UPDATE_SO_COMPLETION_INVALID_CARRIER
							.getCode(),validationErrors);
				}else if(StringUtils.isNotBlank(partTracking.getTrackingNumber()) && StringUtils.isNotBlank(partTracking.getCarrier())){
					if(!Arrays.asList(MPConstants.CARRIER_TYPES).contains(partTracking.getCarrier())){
						errorOccured=true;
						addError(
								ResultsCode.UPDATE_SO_COMPLETION_INVALID_CARRIER
								.getMessage(),
								ResultsCode.UPDATE_SO_COMPLETION_INVALID_CARRIER
								.getCode(),validationErrors);
					}
				}
			}
		}
		return errorOccured;
	}

	/**
	 * @param tasks
	 */
	private boolean validateTasks(Tasks tasks,String serviceOrderId,boolean errorOccured,List<ErrorResult> validationErrors) {

		if(!errorOccured && tasks!=null && tasks.getTask()!=null && !tasks.getTask().isEmpty()){
			List<Integer> taskIds= null;
			try {
				taskIds = mobileSOActionsBO.getSOTasks(serviceOrderId);
				boolean found = false;
				if(taskIds!=null && !taskIds.isEmpty()){
					for(Task task : tasks.getTask()){
						if(task!=null && task.getTaskId()!=null){
							if(!taskIds.contains(task.getTaskId())){
								errorOccured=true;
								addError(
										ResultsCode.UPDATE_SO_COMPLETION_TASKS_ERROR1
										.getMessage(),
										ResultsCode.UPDATE_SO_COMPLETION_TASKS_ERROR1
										.getCode(),validationErrors);
								break;
							}

						}
					}
				}

			} catch (BusinessServiceException e) {
				// TODO Auto-generated catch block
				logger.error("Error inside validateTasks"
						+ e.getMessage());
			}
		}
		return errorOccured;
	}

	/**
	 * @param completionRequest
	 */
	private boolean validateGeneralRequirements(
			UpdateSOCompletionRequest completionRequest,boolean errorOccured,List<ErrorResult> validationErrors) {

		if (!errorOccured) {
			if (PublicMobileAPIConstant.COMPLETION_STATUS_CANCEL
					.equals(completionRequest.getCompletionStatus())) {
				if (StringUtils.isBlank(completionRequest
						.getResolutionComments())) {
					errorOccured=true;
					addError(
							ResultsCode.UPDATE_SO_COMPLETION_RES_COMMENTS_INVALID
							.getMessage(),
							ResultsCode.UPDATE_SO_COMPLETION_RES_COMMENTS_INVALID
							.getCode(),validationErrors);
					

				} 
			}

			else if (PublicMobileAPIConstant.SERVICE_PARTIAL
					.equals(completionRequest.getCompletionStatus())) {
			}

		}
		return errorOccured;
	}
	/**
	 * @param completionRequest
	 * @return
	 * @throws BusinessServiceException 
	 */
	private boolean validateProviderAndSO(
			UpdateSOCompletionRequest completionRequest,String serviceOId,Integer provider, Integer vendorId, boolean errorOccured,List<ErrorResult> validationErrors ) throws BusinessServiceException {

		Integer providerId;
		try {
			providerId = mobileSOManagementBO.validateProviderId(provider.toString());

			com.newco.marketplace.dto.vo.serviceorder.ServiceOrder so = mobileSOActionsBO.getServiceOrder(serviceOId);
			String serviceOrderId = so.getSoId();
			

			boolean checkIfAssignedToFirm = mobileSOActionsBO.checkIfAssignedToFirm(serviceOId);

			boolean soActveInd = mobileSOActionsBO.checkIfSOIsActive(serviceOId);


			if (null == providerId) {
				errorOccured=true;
				addError(ResultsCode.INVALID_PRO.getMessage(),
						ResultsCode.INVALID_PRO.getCode(),validationErrors);
			} else if (null == serviceOrderId
					|| StringUtils.isBlank(serviceOrderId)) {
				errorOccured=true;
				addError(ResultsCode.INVALID_SOID.getMessage(),
						ResultsCode.INVALID_SOID.getCode(),validationErrors);
			}/* else if (null == firmForSO || StringUtils.isBlank(firmForSO)) { //removed this validation and moved it inside validateSOAuthorized()
				errorOccured=true;
				addError(ResultsCode.SO_NOT_ASSOCIATED.getMessage(),
						ResultsCode.SO_NOT_ASSOCIATED.getCode(),validationErrors);
			}*/
			else if(checkIfAssignedToFirm){
				errorOccured=true;
				addError(ResultsCode.UPDATE_SO_COMPLETION_NOT_ASSIGNED_SO.getMessage(),
						ResultsCode.UPDATE_SO_COMPLETION_NOT_ASSIGNED_SO.getCode(),validationErrors);
			}
			else if(!soActveInd){
				errorOccured=true;
				addError(ResultsCode.UPDATE_SO_COMPLETION_NOT_ACTIVE_ORDER.getMessage(),
						ResultsCode.UPDATE_SO_COMPLETION_NOT_ACTIVE_ORDER.getCode(),validationErrors);
			}
			//R14.0 validation for SO authorization
			if(!errorOccured){
				errorOccured = validateSOAuthorized(completionRequest, provider, vendorId, serviceOId,validationErrors);
			}
			
		} catch (BusinessServiceException e) {
			logger.error("Error in validateProviderAndSO" + e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return errorOccured;
	}

	/**
	 * R14.0 validation change
	 * Method validates whether the provider is authorized to perform action on the SO
	 * If the logged in provider belongs to Level 1 or Level 2, check whether accepted resource id = logged in resource, else error
	 * If the logged in provider belongs to Level 3 and flow is not Complete-Completed, check whether accepted resource id = logged in resource, else error
	 * If the logged in provider belongs to Level 3, check whether the logged in resource is in routed list, else error
	 * 
	 * @param provider
	 * @param vendorId
	 * @param serviceOId
	 * @param validationErrors
	 * @return errorOccured
	 * @throws BusinessServiceException
	 */
	private boolean validateSOAuthorized(UpdateSOCompletionRequest completionRequest, Integer provider, Integer vendorId, String serviceOId, List<ErrorResult> validationErrors) throws BusinessServiceException{
		
		boolean errorOccured = false;
		try{
			Integer resourceRoleLevel = authenticateUserBO.getRoleOfResource(null, provider);
			
			if(MPConstants.ROLE_LEVEL_ONE.equals(resourceRoleLevel) || MPConstants.ROLE_LEVEL_TWO.equals(resourceRoleLevel)
					|| (MPConstants.ROLE_LEVEL_THREE.equals(resourceRoleLevel) && !(completionRequest.getCompletionStatus()
							.equalsIgnoreCase(MPConstants.SERVICE_COMPLETE)
							&& completionRequest.getUpdateAction()
									.equalsIgnoreCase(MPConstants.SERVICE_COMPLETED)))){

				Map<String, String> validateMap = new HashMap<String, String>();
				validateMap.put("soId", serviceOId);
				validateMap.put("resourceId",
						(provider).toString());
				String soIdForAcceptedProvider = mobileSOActionsBO.toValidateSoIdForProvider(validateMap);
				if (null == soIdForAcceptedProvider || StringUtils.isBlank(soIdForAcceptedProvider)) {
					errorOccured=true;
					addError(ResultsCode.PERMISSION_ERROR.getMessage(),
							ResultsCode.PERMISSION_ERROR.getCode(),validationErrors);
				}

			}else if(MPConstants.ROLE_LEVEL_THREE.equals(resourceRoleLevel)){
				if(null!=vendorId){
					 if(completionRequest.getCompletionStatus()
								.equalsIgnoreCase(MPConstants.SERVICE_COMPLETE)
								&& completionRequest.getUpdateAction()
								.equalsIgnoreCase(MPConstants.SERVICE_COMPLETED)){
						 SoDetailsVO detailsVO = new SoDetailsVO();
						 detailsVO.setSoId(serviceOId);
						 detailsVO.setFirmId(vendorId.toString());
						 detailsVO.setProviderId(provider);
						 detailsVO.setRoleId(resourceRoleLevel);
						 boolean authSuccess = mobileGenericBO.isAuthorizedToViewBeyondPosted(detailsVO);
						 if(!authSuccess){
								errorOccured=true;
								addError(ResultsCode.PERMISSION_ERROR.getMessage(),
										ResultsCode.PERMISSION_ERROR.getCode(),validationErrors);
						 }
					 }
					 
				}
				// incorrect validation
				/*List<ProviderResultVO> routedProviders = mobileGenericBO.getEligibleProviders(vendorId.toString(), serviceOId);
				boolean isRouted = false;
				if(completionRequest.getCompletionStatus()
						.equalsIgnoreCase(MPConstants.SERVICE_COMPLETE)
						&& completionRequest.getUpdateAction()
						.equalsIgnoreCase(MPConstants.SERVICE_COMPLETED)){
					if(null != routedProviders){
						isRouted = true;
					}
				}else{
					if(null != routedProviders){
						for(ProviderResultVO providerResultVO: routedProviders){
							if(provider.intValue() == providerResultVO.getResourceId()){
								isRouted = true;
								break;
							}
						}
					}
				}

				if(!isRouted){
					errorOccured=true;
					addError(ResultsCode.PERMISSION_ERROR.getMessage(),
							ResultsCode.PERMISSION_ERROR.getCode(),validationErrors);
				}*/
				//}
			}else{
				throw new BusinessServiceException("Invalid Role");
			}
		}catch (Exception e) {
			logger.error("Error in validateSOAuthorized" + e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		
		
		return errorOccured;
	}
	/**
	 * @param completionRequest
	 */
	private boolean validateW9Registration(
			UpdateSOCompletionRequest completionRequest, Integer vendorId, boolean errorOccured,List<ErrorResult> validationErrors) {
		if (!errorOccured && null != completionRequest.getPricing()) {

			boolean isW9ExistsFlag = false;
			boolean w9isExistWithSSNInd = false;
			boolean w9isDobNotAvailable = false;
			Double providerThreshold = 0.00;
			Double completeSoAmount = 0.00;
			Double totalAmount = 0.00;

			try {
				isW9ExistsFlag = w9RegistrationBO.isW9Exists(vendorId);
				if (isW9ExistsFlag) {
					w9isExistWithSSNInd = w9RegistrationBO
							.isAvailableWithSSNInd(vendorId);
					providerThreshold = w9RegistrationBO
							.getProviderThreshold();
					completeSoAmount = w9RegistrationBO
							.getCompleteSOAmount(vendorId);
					w9isDobNotAvailable = w9RegistrationBO
							.isDobNotAvailableWithSSN(vendorId);

					if (null != completionRequest.getPricing()
							&& StringUtils.isNotEmpty(completionRequest
									.getPricing().getLabour())) {
						totalAmount = 1.00 * Double
								.parseDouble(completionRequest.getPricing()
										.getLabour());
						if (completeSoAmount != null) {
							totalAmount = totalAmount + 1.00 * completeSoAmount;
							totalAmount = MoneyUtil
									.getRoundedMoney(totalAmount);
						}
					}
				}
				if (!isW9ExistsFlag
						|| !w9isExistWithSSNInd
						|| (providerThreshold != null
						&& totalAmount > providerThreshold && w9isDobNotAvailable)) {
					errorOccured=true;
					addError(
							ResultsCode.PROVIDER_INCOMPLETE_W9_REGISTRATION
							.getMessage(),
							ResultsCode.PROVIDER_INCOMPLETE_W9_REGISTRATION
							.getCode(),validationErrors);
				}
			} catch (Exception e) {
				logger.error("Error in validateW9Registration" + e.getMessage());
			}
		}
		return errorOccured;
	}

	/**
	 * @param addOns
	 * @param addOnPaymentDetails
	 */
	private boolean validateAddOnPanel(AddOns addOns,boolean errorOccured,List<ErrorResult> validationErrors) {
		if (!errorOccured) {
			if (addOns == null || addOns.getAddOn() == null || addOns.getAddOn().isEmpty()) {
				return errorOccured;
			}

			// None of the quantities can be greater than 5
			addonSum = 0.0;
			boolean errorPresent = false;
			for (AddOn addOn : addOns.getAddOn()) {
				Double endCustomerCharge = 0.00;

				if (StringUtils.isNotEmpty(addOn.getCustomerCharge())) {
					endCustomerCharge = Double.valueOf(addOn
							.getCustomerCharge());
				}
				if (addOn.getQty() == null) {
					// not permit
					if (("Y").equals(addOn.getMiscInd()) && endCustomerCharge > 0.0) {
						errorOccured=true;
						addError(
								ResultsCode.UPDATE_SO_COMPLETION_ADDON_ERROR1
								.getMessage(),
								ResultsCode.UPDATE_SO_COMPLETION_ADDON_ERROR1
								.getCode(),validationErrors);
					}
					addOn.setQty(0);
				} else if (addOn.getQty().intValue() > 5) {

					errorOccured=true;
					addError(
							ResultsCode.UPDATE_SO_COMPLETION_ADDON_ERROR2
							.getMessage(),
							ResultsCode.UPDATE_SO_COMPLETION_ADDON_ERROR2
							.getCode(),validationErrors);
				}
				// not permit
				else if (addOn.getQty().intValue() > 0
						&& (null == endCustomerCharge || endCustomerCharge == 0.0)) {
					if (!errorPresent) {
						errorOccured=true;
						addError(
								ResultsCode.UPDATE_SO_COMPLETION_ADDON_ERROR3
								.getMessage(),
								ResultsCode.UPDATE_SO_COMPLETION_ADDON_ERROR3
								.getCode(),validationErrors);
						errorPresent = true;
					}
				}

				// If quantity is 1 or greater for a Misc SKU, there must be a
				// description entered not permit
				else if (("Y").equals(addOn.getMiscInd()) && addOn.getQty() > 0) {
					if (StringUtils.isBlank(addOn.getDescription())) {
						errorOccured=true;
						addError(
								ResultsCode.UPDATE_SO_COMPLETION_ADDON_ERROR4
								.getMessage(),
								ResultsCode.UPDATE_SO_COMPLETION_ADDON_ERROR4
								.getCode(),validationErrors);
					} else if (null == endCustomerCharge
							|| endCustomerCharge == 0.0) {
						errorOccured=true;
						addError(
								ResultsCode.UPDATE_SO_COMPLETION_ADDON_ERROR5
								.getMessage(),
								ResultsCode.UPDATE_SO_COMPLETION_ADDON_ERROR5
								.getCode(),validationErrors);
					}
				}
				// not permit
				else if (("Y").equals(addOn.getMiscInd()) && addOn.getQty() <= 0
						&& endCustomerCharge > 0.0) {
					errorOccured=true;
					addError(
							ResultsCode.UPDATE_SO_COMPLETION_ADDON_ERROR1
							.getMessage(),
							ResultsCode.UPDATE_SO_COMPLETION_ADDON_ERROR1
							.getCode(),validationErrors);
				}

				if (addOn.getQty() > 0 && endCustomerCharge > 0.0) {
					addonSum += (addOn.getQty() * endCustomerCharge);
				}
				//SLT1323
				if(addOn.getDescription()!=null && CreditCardValidatonUtil.validateCCNumbers(addOn.getDescription())){
					errorOccured=true;
					addError(
							ResultsCode.UPDATE_SO_COMPLETION_ADDON_CC_ERRORS
							.getMessage(),
							ResultsCode.UPDATE_SO_COMPLETION_ADDON_CC_ERRORS
							.getCode(),validationErrors);
				}
			}
		}
		return errorOccured;
	}


	/**
	 * @param addOnPaymentDetails
	 */
	private boolean validateAddOnPaymentDetails(AddOnPaymentDetails addOnPaymentDetails,boolean errorOccured,List<ErrorResult> validationErrors){
		if(null!=addOnPaymentDetails && !errorOccured){
			if (null != addOnPaymentDetails.getPaymentType()
					&& addOnPaymentDetails.getPaymentType().equals(
							UPSELL_PAYMENT_TYPE_CHECK)) {
				if (addOnPaymentDetails.getCheckNumber() == null) {
					errorOccured=true;
					addError(
							ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR1
							.getMessage(),
							ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR1
							.getCode(),validationErrors);
				} 
				else if(StringUtils.isNotBlank(addOnPaymentDetails.getCheckNumber())){
					try {
						Long chkNum = Long.parseLong(addOnPaymentDetails.getCheckNumber());
						if (chkNum <= 0) {
							errorOccured=true;
							addError(
									ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR2
									.getMessage(),
									ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR2
									.getCode(),validationErrors);
						}
					} catch (NumberFormatException nf) {
						errorOccured=true;
						addError(
								ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR2
								.getMessage(),
								ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR2
								.getCode(),validationErrors);

					}
				}

				else if (addOnPaymentDetails.getAmountAuthorized() == null) {
					errorOccured=true;
					addError(
							ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR3
							.getMessage(),
							ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR3
							.getCode(),validationErrors);

				} 
			}

			// Validate Credit Card information
			else if (null != addOnPaymentDetails.getPaymentType()
					&& addOnPaymentDetails.getPaymentType().equals(
							UPSELL_PAYMENT_TYPE_CREDIT)) {
				// validate credit card (mod10 validation)
				errorOccured = isCreditCardInfoValidate(addOnPaymentDetails,errorOccured,validationErrors);
			}
		}
		return errorOccured;
	}

	// }
	/**
	 * @param addOnPaymentDetails
	 */
	private boolean isCreditCardInfoValidate(
			AddOnPaymentDetails addOnPaymentDetails,boolean errorOccured,List<ErrorResult> validationErrors) {
		if (!errorOccured) {
			String cardNumber = addOnPaymentDetails.getCcNumber();
			if (addOnPaymentDetails.getCcType() == null) {
				errorOccured=true;
				addError(
						ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR4
						.getMessage(),
						ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR4
						.getCode(),validationErrors);

			}

			Integer cardType = -1;
			try {
				cardType = mobileSOActionsBO.getCardType(addOnPaymentDetails
						.getCcType());
				if(cardType == null){
					cardType=-1;
				}
			} catch (BusinessServiceException e) {
				logger.error("Error inside isCreditCardInfoValidate"
						+ e.getMessage());

			}
			boolean formatError = false;

			if (!errorOccured && cardNumber != null) {
				if (cardNumber != null && cardNumber.length() > 0) {

					try {
						Long.parseLong(cardNumber);
					} catch (NumberFormatException nf) {
						formatError = true;
						errorOccured=true;
						addError(
								ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR5
								.getMessage(),
								ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR5
								.getCode(),validationErrors);

					}
				}
				if (!errorOccured && (cardType.intValue() == -1)) {
					errorOccured=true;
					addError(
							ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR6
							.getMessage(),
							ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR6
							.getCode(),validationErrors);

				} else if (!errorOccured && StringUtils.isBlank(cardNumber)) {
					errorOccured=true;
					addError(
							ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR7
							.getMessage(),
							ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR7
							.getCode(),validationErrors);

				}
				//Removing Sears commercial card not valid validation as part of R16_0 CC-1069
				// Sears Commercial Card (not allowed at present)
				/*else if (!errorOccured && (cardNumber.length() == 16)
						&& (cardNumber.substring(0, 6)
								.equals(CreditCardConstants.SEARS_COMMERCIAL_CARD_FIRST_SIX_DIGITS))) {
					errorOccured=true;
					addError(
							ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR15
							.getMessage(),
							ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR15
							.getCode(),validationErrors);

				}*/
				//
				else if (!errorOccured && (!formatError)
						&& !UIUtils.isCreditCardValid(cardNumber, cardType.intValue())) {
					errorOccured=true;
					addError(
							ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR7
							.getMessage(),
							ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR7
							.getCode(),validationErrors);

				}

				boolean ifSearsWhiteCard = UIUtils.checkForSearsWhiteCard(
						cardNumber, cardType.intValue());
				if (ifSearsWhiteCard) {
					// For Sears White card expiry date is not required so set
					// it to
					// a default value
					addOnPaymentDetails.setExpirationDate("12/2049");
					// getNewCreditCard().setExpirationYear("49");
					// SearsWhiteCard indicator :For frontend display
					// getNewCreditCard().setIsSearsWhiteCard(true);
				} else {
					String[] expiration = addOnPaymentDetails
							.getExpirationDate().split("/");
					if (!errorOccured && StringUtils.isBlank(addOnPaymentDetails
							.getExpirationDate())) {
						errorOccured=true;
						addError(
								ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR8
								.getMessage(),
								ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR8
								.getCode(),validationErrors);

					} else if (!errorOccured && StringUtils.isBlank(expiration[0])) {
						errorOccured=true;
						addError(
								ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR9
								.getMessage(),
								ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR9
								.getCode(),validationErrors);

					} else if (!errorOccured && StringUtils.isBlank(expiration[1])) {
						errorOccured=true;
						addError(
								ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR10
								.getMessage(),
								ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR10
								.getCode(),validationErrors);

					}

					// Check if expiration date entered is in the past. Made by
					// Carlos
					else {
						if (expiration[1] != null
								&& StringUtils.isNotEmpty(expiration[1])
								&& expiration[0] != null
								&& StringUtils.isNotEmpty(expiration[0])) {

							Calendar calendar = Calendar.getInstance();
							int current_year = calendar.get(Calendar.YEAR);
							int current_month = calendar.get(Calendar.MONTH);

							int dropdown_year = Integer.parseInt(expiration[1]);
							if (dropdown_year == current_year) {
								int dropdown_month = Integer
										.parseInt(expiration[0]);
								if (!errorOccured && (dropdown_month < current_month)) {
									errorOccured=true;
									addError(
											ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR11
											.getMessage(),
											ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR11
											.getCode(),validationErrors);

								}

							} else if (!errorOccured && (dropdown_year < current_year)) {
								errorOccured=true;
								addError(
										ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR12
										.getMessage(),
										ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR12
										.getCode(),validationErrors);

							}
						}
					}
				}

				if (!errorOccured && StringUtils.isBlank(addOnPaymentDetails.getPreAuthNumber())) {
					errorOccured=true;
					addError(
							ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR13
							.getMessage(),
							ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR13
							.getCode(),validationErrors);

				}

				else if (!errorOccured && (addOnPaymentDetails.getAmountAuthorized() == null
						|| Double.parseDouble(addOnPaymentDetails
								.getAmountAuthorized()) <= 0)) {
					errorOccured=true;
					addError(
							ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR14
							.getMessage(),
							ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR14
							.getCode(),validationErrors);

				}
			}
		}
		return errorOccured;
	}

	/**
	 * @param permits
	 */
	private boolean validatePermits(Permits permits, String serviceOrderId,boolean errorOccured,List<ErrorResult> validationErrors) {

		if(!errorOccured && null!=permits){
			if(null != permits.getPermitTasks()){
				List<Integer> soTaskIds = new ArrayList<Integer>();
				for(PermitTask permit : permits.getPermitTasks().getPermitTask()){
					if(null != permit && StringUtils.isBlank(permit.getPermitType())) {
						errorOccured=true;
						addError(
								ResultsCode.UPDATE_SO_COMPLETION_PERMITS_ERROR1
								.getMessage(),
								ResultsCode.UPDATE_SO_COMPLETION_PERMITS_ERROR1
								.getCode(),validationErrors);
						break;
					}
					else{
						soTaskIds.add(permit.getTaskId());
					}
				}

				if(!errorOccured){
					List<Integer> permitTaskIds = new ArrayList<Integer>();
					try {
						permitTaskIds = mobileSOActionsBO.getSOPermitTaks(serviceOrderId);
					} catch (BusinessServiceException e) {

						logger.error("Error in fetching permit tasks:"+e.getMessage());
					}
					if(permitTaskIds!=null && !permitTaskIds.isEmpty() && !soTaskIds.isEmpty()){
						if(!soTaskIds.containsAll(permitTaskIds)){
							errorOccured=true;
							addError(
									ResultsCode.UPDATE_SO_COMPLETION_PERMITS_ERROR3
									.getMessage(),
									ResultsCode.UPDATE_SO_COMPLETION_PERMITS_ERROR3
									.getCode(),validationErrors);
						}
					}
				}
			}
			if(null != permits.getPermitAddons()){
				for(PermitAddon permit :  permits.getPermitAddons().getPermitAddon()){
					if (null != permit && permit.getQty() > 0) {
						if (null == permit.getCustomerCharge() || 
								(null != permit.getCustomerCharge() && 0.00 == permit.getCustomerCharge())) {
							errorOccured=true;
							addError(
									ResultsCode.UPDATE_SO_COMPLETION_PERMITS_ERROR2
									.getMessage(),
									ResultsCode.UPDATE_SO_COMPLETION_PERMITS_ERROR2
									.getCode(),validationErrors);
						}
					}
					//SLT1323
					if(permit.getDescription()!=null && CreditCardValidatonUtil.validateCCNumbers(permit.getDescription())){
						errorOccured=true;
						addError(
								ResultsCode.UPDATE_SO_COMPLETION_PERMITS_ADDON_CC_ERRORS
								.getMessage(),
								ResultsCode.UPDATE_SO_COMPLETION_PERMITS_ADDON_CC_ERRORS
								.getCode(),validationErrors);						
					}
				}
			}
		}
		return errorOccured;
	}

	/**
	 * @param message
	 * @param code
	 */
	private void addError(String message, String code,List<ErrorResult> validationErrors) {
		ErrorResult result = new ErrorResult();
		result.setCode(code);
		result.setMessage(message);
		//errorOccured = true;
		validationErrors.add(result);
	}

	/**
	 * @param completionRequest
	 */
	private boolean validateGeneralCompletionInfoPanel(
			UpdateSOCompletionRequest completionRequest,String buyer,boolean errorOccured,List<ErrorResult> validationErrors) {

		if (!errorOccured) {
			Pricing pricing = completionRequest.getPricing();

			if (pricing != null) {
				String finalPartPrice = pricing.getMaterials();
				Double userEnteredPartsPrices = 0.00;
				if (StringUtils.isNotEmpty(pricing.getMaterials())) {
					userEnteredPartsPrices = Double.parseDouble(pricing
							.getMaterials());
				}

				Double partSpLimit = 0.00;
				if (StringUtils.isNotEmpty(pricing.getOriginalMaterials())) {
					partSpLimit = Double.parseDouble(pricing
							.getOriginalMaterials());
				}
				String soMaxLabor = pricing.getLabour();
				Double soInitialMaxLabor = 0.00;
				Double userEnteredLaborPrices = 0.00;

				if (StringUtils.isNotEmpty(soMaxLabor)) {
					userEnteredLaborPrices = Double.parseDouble(soMaxLabor);
				}

				if (StringUtils.isNotEmpty(pricing.getOriginalLabour())) {
					soInitialMaxLabor = Double.parseDouble(pricing
							.getOriginalLabour());
				}

				if (!PublicMobileAPIConstant.HSR_BUYER.equalsIgnoreCase(buyer)) {

					if (!errorOccured && finalPartPrice!=null && !IsParsableNumber(finalPartPrice)) {
						errorOccured=true;
						addError(
								ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR1
								.getMessage(),
								ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR1
								.getCode(),validationErrors);

					} else if(!errorOccured && finalPartPrice!=null && IsParsableNumber(finalPartPrice)){
						// Final parts price should be a valid amount and should
						// not
						// be greater than order's current final parts price

						Double userEnteredPartsPrice = Double
								.parseDouble(finalPartPrice);
						if (!errorOccured && userEnteredPartsPrice > partSpLimit) {
							errorOccured=true;
							addError(
									ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR2
									.getMessage(),
									ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR2
									.getCode(),validationErrors);

						} else if (!errorOccured && userEnteredPartsPrice < 0) {
							errorOccured=true;
							addError(
									ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR3
									.getMessage(),
									ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR3
									.getCode(),validationErrors);
							// errorOccured=true;addError("FinalPartsPrice",
							// PARTSFINALPRICE_CANNOT_BE_LESS_THAN_ZERO ,
							// OrderConstants.SOW_TAB_ERROR);
						}

					}
					if (!errorOccured && IsParsableNumber(soMaxLabor) == false) {
						errorOccured=true;
						addError(
								ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR4
								.getMessage(),
								ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR4
								.getCode(),validationErrors);

					} else {
						// Final labor price should be a valid amount and should
						// not
						// be greater than order's current final labor price

						Double userEnteredLaborPrice = Double
								.parseDouble(soMaxLabor);
						if (!errorOccured && userEnteredLaborPrice > soInitialMaxLabor) {
							errorOccured=true;
							addError(
									ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR5
									.getMessage(),
									ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR5
									.getCode(),validationErrors);

						} else if (!errorOccured && userEnteredLaborPrice < 0) {
							errorOccured=true;
							addError(
									ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR6
									.getMessage(),
									ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR6
									.getCode(),validationErrors);

						}

					}
					boolean nonMaxPriceForLaborCheck = false;
					boolean nonMaxPriceForPartCheck = false;
					if (userEnteredPartsPrices.doubleValue() != partSpLimit.doubleValue()) {
						nonMaxPriceForPartCheck = true;
					}
					if (userEnteredLaborPrices.doubleValue() != soInitialMaxLabor.doubleValue()) {
						nonMaxPriceForLaborCheck = true;
					}

					if (!errorOccured && nonMaxPriceForLaborCheck) {
						if (null == pricing.getLabourChangeReasonCodeId()
								|| pricing.getLabourChangeReasonCodeId().intValue() <=0) {
							errorOccured=true;
							addError(
									ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR7
									.getMessage(),
									ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR7
									.getCode(),validationErrors);

						} else if (!errorOccured && pricing.getLabourChangeReasonCodeId().intValue() == MPConstants.OTHER_REASON
								&& (StringUtils.isBlank(pricing
										.getLabourChangeReasonComments()))) {

							errorOccured=true;
							addError(
									ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR8
									.getMessage(),
									ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR8
									.getCode(),validationErrors);

						}
					}

					if (!errorOccured && nonMaxPriceForPartCheck) {
						if (!errorOccured && null == pricing.getPartChangeReasonCodeId()
								|| pricing.getPartChangeReasonCodeId().intValue() <=0) {
							errorOccured=true;
							addError(
									ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR9
									.getMessage(),
									ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR9
									.getCode(),validationErrors);

						} else if (!errorOccured && pricing.getPartChangeReasonCodeId().intValue() == MPConstants.OTHER_REASON
								&& (StringUtils.isBlank(pricing
										.getPartChangeReasonComments()))) {
							errorOccured=true;
							addError(
									ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR10
									.getMessage(),
									ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR10
									.getCode(),validationErrors);

						}
					}
				} else {
					// for HSR

					boolean nonMaxPriceForLaborCheck = false;
					boolean nonMaxPriceForPartCheck = false;
					if (userEnteredPartsPrices.doubleValue() != partSpLimit.doubleValue()) {
						nonMaxPriceForPartCheck = true;
					}
					if (userEnteredLaborPrices.doubleValue() != soInitialMaxLabor.doubleValue()) {
						nonMaxPriceForLaborCheck = true;
					}

					if (nonMaxPriceForLaborCheck) {
						if (!errorOccured && null == pricing.getLabourChangeReasonCodeId()
								|| pricing.getLabourChangeReasonCodeId().intValue() <=0) {
							errorOccured=true;
							addError(
									ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR7
									.getMessage(),
									ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR7
									.getCode(),validationErrors);

						} else if (!errorOccured && pricing.getLabourChangeReasonCodeId().intValue() == MPConstants.OTHER_REASON
								&& (StringUtils.isBlank(pricing
										.getLabourChangeReasonComments()))) {

							errorOccured=true;
							addError(
									ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR8
									.getMessage(),
									ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR8
									.getCode(),validationErrors);

						}
					}

					if (nonMaxPriceForPartCheck) {
						if (!errorOccured && null == pricing.getPartChangeReasonCodeId()
								|| pricing.getPartChangeReasonCodeId().intValue() <=0) {
							errorOccured=true;
							addError(
									ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR9
									.getMessage(),
									ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR9
									.getCode(),validationErrors);

						} else if (!errorOccured && pricing.getPartChangeReasonCodeId().intValue() == MPConstants.OTHER_REASON
								&& (StringUtils.isBlank(pricing
										.getPartChangeReasonComments()))) {
							errorOccured=true;
							addError(
									ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR10
									.getMessage(),
									ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR10
									.getCode(),validationErrors);

						}
					}

					if (nonMaxPriceForLaborCheck || nonMaxPriceForPartCheck) {
						if (!errorOccured && IsParsableNumber(soMaxLabor) == false) {
							errorOccured=true;
							addError(
									ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR4
									.getMessage(),
									ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR4
									.getCode(),validationErrors);

						} else {
							Double userEnteredLaborPrice = Double
									.parseDouble(soMaxLabor);
							if (!errorOccured && userEnteredLaborPrice > soInitialMaxLabor) {
								errorOccured=true;
								addError(
										ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR5
										.getMessage(),
										ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR5
										.getCode(),validationErrors);

							} else if (!errorOccured && userEnteredLaborPrice < 0) {
								errorOccured=true;
								addError(
										ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR6
										.getMessage(),
										ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR6
										.getCode(),validationErrors);

							}
						}
						if (!errorOccured && finalPartPrice!=null && IsParsableNumber(finalPartPrice) == false) {
							errorOccured=true;
							addError(
									ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR1
									.getMessage(),
									ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR1
									.getCode(),validationErrors);

						} else if (!errorOccured && finalPartPrice!=null && IsParsableNumber(finalPartPrice)) {
							Double userEnteredPartsPrice = Double
									.parseDouble(finalPartPrice);
							if (!errorOccured && userEnteredPartsPrice > partSpLimit) {
								errorOccured=true;
								addError(
										ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR2
										.getMessage(),
										ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR2
										.getCode(),validationErrors);

							} else if (!errorOccured && userEnteredPartsPrice < 0) {
								errorOccured=true;
								addError(
										ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR3
										.getMessage(),
										ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR3
										.getCode(),validationErrors);

							}

						}
					} else {
						// Setting the final Labor price to Initial value
						pricing.setLabour(soInitialMaxLabor.toString());
						if (partSpLimit > 0) {
							finalPartPrice = String.valueOf(partSpLimit);
						} else {
							finalPartPrice = "00.00";
						}
						pricing.setMaterials(finalPartPrice);
					}

				}
			}

		}
		return errorOccured;
	}

	/**
	 * @param references
	 */
	private boolean validateBuyerReferencePanel(References references, String serviceOrderId,boolean errorOccured,List<ErrorResult> validationErrors) {

		if (!errorOccured && null!=references && null!=references.getReference() && !references.getReference().isEmpty()) {
			List<BuyerReferenceVO> requiredBuyerReferneces = null;
			try {
				boolean mandatoryInd = true;
				requiredBuyerReferneces = mobileSOActionsBO
						.getRequiredBuyerReferences(serviceOrderId,mandatoryInd);
			} catch (BusinessServiceException e) {
				// TODO Auto-generated catch block
				logger.error("Error inside validateBuyerReferencePanel"
						+ e.getMessage());
			}
			if (requiredBuyerReferneces != null
					&& !requiredBuyerReferneces.isEmpty()) {
				boolean found = false;
				for (BuyerReferenceVO buyerReferenceVO : requiredBuyerReferneces) {
					if (null != buyerReferenceVO) {
						String dbReferenceName = StringUtils.trim(buyerReferenceVO.getReferenceType());
						for (Reference reference : references.getReference()) {
							if(null!=reference){
								String referenceName = StringUtils.trim(reference.getReferenceName());
								String referenceValue = StringUtils.trim(reference.getReferenceValue());
								if(!errorOccured && StringUtils.isBlank(referenceName)){
									errorOccured=true;
									addError(
											ResultsCode.UPDATE_SO_COMPLETION_CUSTOM_REFERENCES_ERROR3
											.getMessage(),
											ResultsCode.UPDATE_SO_COMPLETION_CUSTOM_REFERENCES_ERROR3
											.getCode(),validationErrors);
									break;

								}
								//SPM-1172 prevent validation error for optional reference
								if(!errorOccured && referenceName.equals(dbReferenceName) && StringUtils.isBlank(referenceValue)){
									errorOccured=true;
									addError(
											ResultsCode.UPDATE_SO_COMPLETION_CUSTOM_REFERENCES_ERROR5
											.getMessage(),
											ResultsCode.UPDATE_SO_COMPLETION_CUSTOM_REFERENCES_ERROR5
											.getCode(),validationErrors);
									break;

								}

								if (!errorOccured && dbReferenceName.equals(
										referenceName)) {
									if (StringUtils.isNotEmpty(reference
											.getReferenceValue())) {
										found = true;
										if (referenceName != null) {
											if ("Serial Number"
													.equalsIgnoreCase(referenceName)
													|| "Model Number"
													.equalsIgnoreCase(referenceName)) {
												if (StringUtils
														.isNotBlank(referenceValue)
														&& !StringUtils
														.isAlphanumericSpace(referenceValue)) {

													// error not valid value
													// reference
													errorOccured=true;
													addError(
															ResultsCode.UPDATE_SO_COMPLETION_CUSTOM_REFERENCES_ERROR2
															.getMessage(),
															ResultsCode.UPDATE_SO_COMPLETION_CUSTOM_REFERENCES_ERROR2
															.getCode(),validationErrors);

												}
											}
										}
										break;
									}
									else{
										found = false;
									}
								}
								else{
									found = false;
								}
							}
						}

						if(!found && !errorOccured){
							errorOccured=true;
							addError(
									ResultsCode.UPDATE_SO_COMPLETION_CUSTOM_REFERENCES_ERROR1
									.getMessage(),
									ResultsCode.UPDATE_SO_COMPLETION_CUSTOM_REFERENCES_ERROR1
									.getCode(),validationErrors);

						}

					}
				}
			}

		}
		return errorOccured;
	}

	/**
	 * @param str
	 * @return
	 */
	private boolean IsParsableNumber(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (Exception nfe) {
			return false;
		}
	}

	/**
	 * @param serviceOrder
	 * @param pendingClaimInd 
	 * @return
	 */
	public List<ErrorResult> preValidateBeforeCompeltion(
			ServiceOrder serviceOrder, boolean pendingClaimInd, String noPartsRequiredInd,
			String serviceOrderId,String buyer) {
		boolean errorOccured = false;
		List<ErrorResult> validationErrors = new ArrayList<ErrorResult>();
		if(serviceOrder!=null){
			if (StringUtils.isBlank(serviceOrder
					.getResolutionDs())) {
				errorOccured=true;
				addError(
						ResultsCode.UPDATE_SO_COMPLETION_RES_COMMENTS_INVALID
						.getMessage(),
						ResultsCode.UPDATE_SO_COMPLETION_RES_COMMENTS_INVALID
						.getCode(),validationErrors);

			} 
			// removing validation as part of SL-20525

			/*if(!errorOccured){
				validateTasks(serviceOrder.getTasks());
			}*/
			logger.info("serviceOrderId:::"+serviceOrderId);
			if(!errorOccured){
				errorOccured =validateAddOns(serviceOrder.getAddons(),errorOccured,validationErrors);
			}
			if(!errorOccured && serviceOrder.getBuyerId().intValue() == Integer.parseInt(PublicMobileAPIConstant.HSR_BUYER)){
				errorOccured =validateInvoiceParts(serviceOrder.getSoProviderInvoiceParts(),noPartsRequiredInd,errorOccured,validationErrors);
			}
			
			//validate documents signature and RI Permit Document and invoice document
			
			if(!errorOccured){
				errorOccured =validateDocuments(serviceOrderId,buyer,serviceOrder,noPartsRequiredInd,errorOccured,validationErrors);
			}
			
			// validate references
			//validate documents signature and RI Permit Document
			if(!errorOccured){
				errorOccured =validateMandatoryReferences(serviceOrderId,serviceOrder.getCustomReferences(),errorOccured,validationErrors);
			}
			if(!errorOccured && !pendingClaimInd){
				errorOccured =validatePricing(serviceOrder,errorOccured,validationErrors);
			}
			// the amount provided should be equal to the amount due from customer
			if(!errorOccured && null!=serviceOrder.getAdditionalPayment()){
				errorOccured =validateAdditionalPaymentAmt(serviceOrder,errorOccured,validationErrors);
			}

		}

		return validationErrors;
	}

	/**
	 * @param customReferences
	 */
	private boolean validateMandatoryReferences(String serviceOrderId,
			List<SOCustomReference> customReferences,boolean errorOccured,List<ErrorResult> validationErrors) {

		List<BuyerReferenceVO> requiredBuyerReferneces = null;
		try {
			boolean mandatoryInd = true;
			requiredBuyerReferneces = mobileSOActionsBO
					.getRequiredBuyerReferences(serviceOrderId,mandatoryInd);
			if((requiredBuyerReferneces!=null && !requiredBuyerReferneces.isEmpty()) && (customReferences == null || customReferences.isEmpty())){
				// custom ref manadatory
				errorOccured=true;
				addError(
						ResultsCode.UPDATE_SO_COMPLETION_CUSTOM_REFERENCES_ERROR4
						.getMessage(),
						ResultsCode.UPDATE_SO_COMPLETION_CUSTOM_REFERENCES_ERROR4
						.getCode(),validationErrors);	
			}

			if(!errorOccured && (requiredBuyerReferneces!=null && !requiredBuyerReferneces.isEmpty())){
				boolean found = false;
				for (BuyerReferenceVO buyerReferenceVO : requiredBuyerReferneces) {
					if (null != buyerReferenceVO) {
						String reqBuyerReferenceName = StringUtils.trim(buyerReferenceVO.getReferenceType());
						if(customReferences!=null && !customReferences.isEmpty()){
							for (SOCustomReference reference : customReferences) {
								if(reference!=null){
									String refName = StringUtils.trim(reference.getBuyerRefTypeName());
									if (null!= reqBuyerReferenceName && reqBuyerReferenceName.equals(refName)) {
										if (StringUtils.isNotEmpty(reference.getBuyerRefValue())) {
											found=true;
											break;
										}
										else{
											found = false;
										}
									}
									else{
										found = false;
									}
								}
							}
						}

						if(!found && !errorOccured){
							// custom ref manadatory

							errorOccured=true;
							addError(
									ResultsCode.UPDATE_SO_COMPLETION_CUSTOM_REFERENCES_ERROR4
									.getMessage(),
									ResultsCode.UPDATE_SO_COMPLETION_CUSTOM_REFERENCES_ERROR4
									.getCode(),validationErrors);

						}

					}
				}
			}
		} catch (BusinessServiceException e) {
			logger.error("Error inside validateMandatoryReferences"
					+ e.getMessage());
		}
		return errorOccured;
	}

	/**
	 * @param soId
	 * @param buyerId
	 * @param noPartsRequiredInd 
	 */
	private boolean validateDocuments(String serviceOrderId, String buyer,ServiceOrder serviceOrder, String noPartsRequiredInd,boolean errorOccured,List<ErrorResult> validationErrors) {

		List<DocumentVO> documentVoList = new ArrayList<DocumentVO>();	
		List<Signature> signaturesList = new ArrayList<Signature>();	
		List<String> soDocTitles = new ArrayList<String>();
		List<String> resourceInds = new ArrayList<String>();

		try {
			documentVoList = mobileSOActionsBO.getServiceOrderDocuments(serviceOrderId);
			if(documentVoList!=null){
				for(DocumentVO obj:documentVoList)
				{	
					if(obj!=null && obj.getTitle()!=null){
						soDocTitles.add(obj.getTitle().toLowerCase());
					}
				}

				if(serviceOrder.getNonDeletedTasks()!=null && !serviceOrder.getNonDeletedTasks().isEmpty() && (MPConstants.SEARS_BUYER).equals(buyer)){
					for(SOTask task : serviceOrder.getNonDeletedTasks()){
						if(OrderConstants.PERMIT_TASK.equals(task.getTaskType())) {
							if(!soDocTitles.contains(MPConstants.PROOF_OF_PERMIT)){		
								// need to upload proof of permit
								errorOccured=true;
								addError(
										ResultsCode.UPDATE_SO_COMPLETION_DOCUMENT_VALIDATION_ERROR1
										.getMessage(),
										ResultsCode.UPDATE_SO_COMPLETION_DOCUMENT_VALIDATION_ERROR1
										.getCode(),validationErrors);	
								break;
							}

						}
					}
					if(!errorOccured && serviceOrder.getAddons()!=null && !serviceOrder.getAddons().isEmpty()){
						for(SOAddon soAddon :serviceOrder.getAddons()){
							if((MPConstants.PERMIT_SKU).equals(soAddon.getSku()) && soAddon.getQuantity().intValue()>0){
								if(!soDocTitles.contains(MPConstants.PROOF_OF_PERMIT)){		
									// need to upload proof of permit
									errorOccured=true;
									addError(
											ResultsCode.UPDATE_SO_COMPLETION_DOCUMENT_VALIDATION_ERROR1
											.getMessage(),
											ResultsCode.UPDATE_SO_COMPLETION_DOCUMENT_VALIDATION_ERROR1
											.getCode(),validationErrors);	
									break;
								}
							}
						}
					}

				}

				if(!errorOccured){
					if(!soDocTitles.contains(MPConstants.CUSTOMER_SIGNATURE) || !soDocTitles.contains(MPConstants.PROVIDER_SIGNATURE)){		
						// need to upload both customer and provider signature for completion
						errorOccured=true;
						addError(
								ResultsCode.UPDATE_SO_COMPLETION_DOCUMENT_VALIDATION_ERROR2
								.getMessage(),
								ResultsCode.UPDATE_SO_COMPLETION_DOCUMENT_VALIDATION_ERROR2
								.getCode(),validationErrors);	
					}

					else{
						signaturesList = mobileSOActionsBO.getServiceOrderSignature(serviceOrderId);

						if(signaturesList!=null && !signaturesList.isEmpty()){
							for(Signature signature : signaturesList){
								resourceInds.add(signature.getResourceInd());
							}
						}

						if(!resourceInds.contains(MPConstants.CUSTOMER_SIGNATURE_IND) || !resourceInds.contains(MPConstants.PROVIDER_SIGNATURE_IND)){
							// need to complete signature details before completion
							errorOccured=true;
							addError(
									ResultsCode.UPDATE_SO_COMPLETION_DOCUMENT_VALIDATION_ERROR3
									.getMessage(),
									ResultsCode.UPDATE_SO_COMPLETION_DOCUMENT_VALIDATION_ERROR3
									.getCode(),validationErrors);	
						}
					}
				}
				if(!errorOccured){
					List<SOProviderInvoiceParts> invoiceParts = serviceOrder.getSoProviderInvoiceParts();
					boolean nonTruckStockInd =false;
					if(null !=  invoiceParts && !invoiceParts.isEmpty()){
						for(SOProviderInvoiceParts providerInvoiceParts : invoiceParts){
							String source = providerInvoiceParts.getSource();
							if(PublicAPIConstant.INSTALLED.equalsIgnoreCase(providerInvoiceParts.getPartStatus()) 
									&& null != source && !MPConstants.TRUCK_STOCK.equals(source)){
								nonTruckStockInd = true;
								break;
							}
						}
					}

					if(nonTruckStockInd && !soDocTitles.contains(MPConstants.PARTS_INVOICE)){		
						// need to upload invoice document if there is at least one invoice part other than truck stock
						errorOccured=true;
						addError(
								ResultsCode.UPDATE_SO_COMPLETION_DOCUMENT_VALIDATION_ERROR5
								.getMessage(),
								ResultsCode.UPDATE_SO_COMPLETION_DOCUMENT_VALIDATION_ERROR5
								.getCode(),validationErrors);	
					}
				}

			}
			else{
				// upload necessary documents
				errorOccured=true;
				addError(
						ResultsCode.UPDATE_SO_COMPLETION_DOCUMENT_VALIDATION_ERROR4
						.getMessage(),
						ResultsCode.UPDATE_SO_COMPLETION_DOCUMENT_VALIDATION_ERROR4
						.getCode(),validationErrors);	
			}
		} catch (BusinessServiceException e) {
			logger.error("Exception in fetching documents"+e.getMessage());
		}
		return errorOccured;
	}

	/**
	 * @param serviceOrder
	 */
	private boolean validateAdditionalPaymentAmt(ServiceOrder serviceOrder,boolean errorOccured,List<ErrorResult> validationErrors) {
		boolean addonIndicator=false;
		SOAdditionalPayment paymentDetails = serviceOrder.getAdditionalPayment();
		BigDecimal amountAuthorized = paymentDetails.getPaymentAmount();
		List<SOAddon> addons = serviceOrder.getAddons();
		BigDecimal addonAmt = BigDecimal.ZERO;
		for(SOAddon addon:addons ){
			if(addon.getQuantity()>0){
				BigDecimal qty = new BigDecimal(addon.getQuantity());
				BigDecimal currentAddonAmt = addon.getRetailPrice().multiply(qty);
				addonAmt = addonAmt.add(currentAddonAmt);
				//SL-20652:Code change for preventing the error message displayed when addon quantity is zero and order is completed  ,by introducing an indicator for addon
				addonIndicator = true;
			}
		}
		//SL-20652:Code change for preventing the error message displayed when addon quantity is zero and order is completed,by introducing an indicator for addon
		if(addonIndicator && !amountAuthorized.equals(addonAmt)){
			errorOccured=true;
			addError(
					ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENT_VALIDATION_ERROR
					.getMessage(),
					ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENT_VALIDATION_ERROR
					.getCode(),validationErrors);	
		}
		return errorOccured;
	}

	/**
	 * Method to validate the total retail price of parts
	 * @param soInvoiceParts
	 */

	public boolean validateRetailPrice(List<SOProviderInvoiceParts> soInvoiceParts,boolean errorOccured,List<ErrorResult> validationErrors) {
		Double totalRetailPrice = 0.0;			
		Double retailPricePart = 0.0; 	

		try{
			if(null!=soInvoiceParts && !(soInvoiceParts.isEmpty())){
				for(SOProviderInvoiceParts invoicePart : soInvoiceParts){
					if(invoicePart!=null && invoicePart.getRetailPrice()!=null && null!= invoicePart.getQty() && 
							StringUtils.isNotBlank(invoicePart.getPartStatus()) && 
							invoicePart.getPartStatus().equalsIgnoreCase(MPConstants.PARTS_STATUS_INSTALLED)){
						retailPricePart = invoicePart.getRetailPrice().doubleValue();
						/*	if(retailPricePart == null){
										retailPricePart = 0.0;
										}*/
						retailPricePart = retailPricePart * invoicePart.getQty();
						totalRetailPrice = totalRetailPrice + retailPricePart;															
					}
				}
				//SL-21811
				if(totalRetailPrice > RETAIL_MAX_PRICE) {
					errorOccured=true;
					addError(
							ResultsCode.UPDATE_SO_COMPLETION_PARTS_MAX_RETAIL_PRICE_VALIDATION
							.getMessage(),
							ResultsCode.UPDATE_SO_COMPLETION_PARTS_MAX_RETAIL_PRICE_VALIDATION
							.getCode(),validationErrors);
					return errorOccured;
				}
			}
		}catch(Exception e){
			logger.error("Exception in validateRetailPrice: "+e.getMessage());
			//throw e;
		}
		return errorOccured;
	}



	/**
	 * 
	 * @param paymentDetails
	 * @param soId
	 * @return
	 */

	public List<ErrorResult> validatePreAuthAmt(AddOnPaymentDetails paymentDetails, String serviceOrderId) {
		List<ErrorResult> validationErrors = new ArrayList<ErrorResult>();
		//SOAdditionalPayment paymentDetails = serviceOrder.getAdditionalPayment();
		BigDecimal preAuthAmt = new BigDecimal(paymentDetails.getAmountAuthorized());
		List<AddOn> addons = new ArrayList<AddOn>();
		try {
			addons = mobileSOActionsBO.getSOAddonDetails(serviceOrderId);
		} catch (BusinessServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BigDecimal addonAmt = BigDecimal.ZERO;
		for(AddOn addon:addons ){
			BigDecimal qty = new BigDecimal(addon.getQty());
			BigDecimal retailPrice = new BigDecimal(addon.getCustomerCharge());
			BigDecimal currentAddonAmt = retailPrice.multiply(qty);
			addonAmt = addonAmt.add(currentAddonAmt);
		}
		if(preAuthAmt.doubleValue()!=(addonAmt.doubleValue())){
			addError(
					ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENT_VALIDATION_ERROR
					.getMessage(),
					ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENT_VALIDATION_ERROR
					.getCode(),validationErrors);	
		}
		if (addonAmt.doubleValue() > MAX_ADDON_CUSTOMER_CHARGE) {
			addError(
					ResultsCode.UPDATE_SO_COMPLETION_PERMITS_ADDON_TOTAL_INVALID
					.getMessage(),
					ResultsCode.UPDATE_SO_COMPLETION_PERMITS_ADDON_TOTAL_INVALID
					.getCode(),validationErrors);
		}
		return validationErrors;
	}

	public List<ErrorResult> validateTokenizeResponse() {
		List<ErrorResult> validationErrors = new ArrayList<ErrorResult>();
		
		addError(
				ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENT_INVALID_TOKENIZE_RESPONSE
				.getMessage(),
				ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENT_INVALID_TOKENIZE_RESPONSE
				.getCode(),validationErrors);
		return validationErrors;
	}
	
	/**
	 * @param serviceOrder
	 */
	private boolean validatePricing(ServiceOrder serviceOrder,boolean errorOccured,List<ErrorResult> validationErrors) {
		SOWorkflowControls soWorkflowControls = serviceOrder.getSOWorkflowControls();

		if(serviceOrder.getSpendLimitLabor()!=null && soWorkflowControls!=null && soWorkflowControls.getFinalPriceLabor()==null){
			errorOccured=true;
			addError(
					ResultsCode.UPDATE_SO_COMPLETION_PRICING_LABOR_FAILURE
					.getMessage(),
					ResultsCode.UPDATE_SO_COMPLETION_PRICING_LABOR_FAILURE
					.getCode(),validationErrors);		
		}

		else if(serviceOrder.getSpendLimitParts()!=null && soWorkflowControls!=null && soWorkflowControls.getFinalPriceParts()==null){
			errorOccured=true;
			addError(
					ResultsCode.UPDATE_SO_COMPLETION_PRICING_PARTS_FAILURE
					.getMessage(),
					ResultsCode.UPDATE_SO_COMPLETION_PRICING_PARTS_FAILURE
					.getCode(),validationErrors);		
		}
		return errorOccured;
	}

	/**
	 * @param soProviderInvoiceParts
	 */
	private boolean validateInvoiceParts(
			List<SOProviderInvoiceParts> soProviderInvoiceParts, String noPartsRequiredInd,boolean errorOccured,List<ErrorResult> validationErrors) {
		Double netPayment = 0.00;
		Double finalPayment = 0.00;
		Double finalPaymentTotal = 0.00;
		Double payment = 0.00;
		String maxValueForInvoicePartsHSR = PropertiesUtils
				.getPropertyValue(PublicMobileAPIConstant.HSR_MAX_VALUE_INVOICE_PARTS);
		Double maxValue = Double
				.parseDouble(maxValueForInvoicePartsHSR);

		//R12_0 Sprint 4: If parts indicator from request is PARTS_ADDED then validate if part details are present
		//if not present, throw error message to provide complete part details
		if(null!= noPartsRequiredInd && noPartsRequiredInd.equals(PublicMobileAPIConstant.INDICATOR_PARTS_ADDED)){

			if(null!=soProviderInvoiceParts && !soProviderInvoiceParts.isEmpty()){
				for(SOProviderInvoiceParts invoicePart : soProviderInvoiceParts){
					if(invoicePart!=null){
						try {
							ArrayList<Object> partNoArgumentList = new ArrayList<Object>();
							partNoArgumentList.add(invoicePart.getPartNo());
							partNoArgumentList.add(invoicePart.getPartStatus());

							ArrayList<Object> partNameArgumentList = new ArrayList<Object>();
							partNameArgumentList.add(invoicePart.getDescription());
							
							if ((!PublicAPIConstant.INSTALLED.equalsIgnoreCase(invoicePart.getPartStatus()) && !PublicAPIConstant.NOT_INSTALLED
									.equalsIgnoreCase(invoicePart.getPartStatus()))){
								errorOccured=true;
								addError(
										ResultsCode.UPDATE_SO_INVOICE_PARTS_STATUS_ERROR
										.getMessage(partNoArgumentList),
										
										ResultsCode.UPDATE_SO_INVOICE_PARTS_STATUS_ERROR
										.getCode(),validationErrors);
								return errorOccured;
							}
							else{
								if((PublicAPIConstant.INSTALLED.equalsIgnoreCase(invoicePart.getPartStatus()))){						

									if(StringUtils.isBlank(invoicePart.getPartCoverage())){

										errorOccured=true;
										addError(
												ResultsCode.UPDATE_SO_INVOICE_PARTS_COVERAGE_ERROR
												.getMessage(partNoArgumentList),												
												ResultsCode.UPDATE_SO_INVOICE_PARTS_COVERAGE_ERROR
												.getCode(),validationErrors);	

									}
									else if(StringUtils.isBlank(invoicePart.getSource())){
										errorOccured=true;
										addError(
												ResultsCode.UPDATE_SO_INVOICE_PARTS_SOURCE_ERROR.getMessage(partNoArgumentList),

												ResultsCode.UPDATE_SO_INVOICE_PARTS_SOURCE_ERROR
												.getCode(),validationErrors);										
									}
									else if( StringUtils.isBlank(invoicePart.getPartNo())){
										errorOccured=true;
										addError(
												ResultsCode.UPDATE_SO_INVOICE_PARTS_PART_NUMBER_ERROR.getMessage(partNameArgumentList),											
												ResultsCode.UPDATE_SO_INVOICE_PARTS_PART_NUMBER_ERROR
												.getCode(),validationErrors);	
									}
									else if(StringUtils.isBlank(invoicePart.getDescription())){
										errorOccured=true;
										addError(
												ResultsCode.UPDATE_SO_INVOICE_PARTS_PART_NAME_ERROR.getMessage(partNoArgumentList),											
												ResultsCode.UPDATE_SO_INVOICE_PARTS_PART_NAME_ERROR
												.getCode(),validationErrors);	
									}
									else if(StringUtils.isBlank(invoicePart.getInvoiceNo())){
										errorOccured=true;
										addError(
												ResultsCode.UPDATE_SO_INVOICE_PARTS_INVOICE_NUMBER_ERROR.getMessage(partNoArgumentList),											
												ResultsCode.UPDATE_SO_INVOICE_PARTS_INVOICE_NUMBER_ERROR
												.getCode(),validationErrors);
									}
									else if(null != invoicePart.getUnitCost() && 0.00 >= invoicePart
											.getUnitCost().doubleValue()){
										errorOccured=true;
										addError(
												ResultsCode.UPDATE_SO_INVOICE_PARTS_UNIT_COST_ERROR.getMessage(partNoArgumentList),											
												ResultsCode.UPDATE_SO_INVOICE_PARTS_UNIT_COST_ERROR
												.getCode(),validationErrors);
									}
									else if(null != invoicePart.getRetailPrice() && 0.00 >= invoicePart
											.getRetailPrice().doubleValue()){
										errorOccured=true;
										addError(
												ResultsCode.UPDATE_SO_INVOICE_PARTS_RETAIL_PRICE_ERROR.getMessage(partNoArgumentList),											
												ResultsCode.UPDATE_SO_INVOICE_PARTS_RETAIL_PRICE_ERROR
												.getCode(),validationErrors);
									}
									else if(0 == invoicePart.getQty()){
										errorOccured=true;
										addError(
												ResultsCode.UPDATE_SO_INVOICE_PARTS_QTY_ERROR.getMessage(partNoArgumentList),											
												ResultsCode.UPDATE_SO_INVOICE_PARTS_QTY_ERROR
												.getCode(),validationErrors);
									}
									else if(StringUtils.isBlank(invoicePart.getDivisionNumber())){
										errorOccured=true;
										addError(
												ResultsCode.UPDATE_SO_INVOICE_PARTS_DIV_NO_ERROR.getMessage(partNoArgumentList),											
												ResultsCode.UPDATE_SO_INVOICE_PARTS_DIV_NO_ERROR
												.getCode(),validationErrors);
									}
									else if (StringUtils.isBlank(invoicePart.getSourceNumber())){
										errorOccured=true;
										addError(
												ResultsCode.UPDATE_SO_INVOICE_PARTS_SOURCE_NO_ERROR.getMessage(partNoArgumentList),											
												ResultsCode.UPDATE_SO_INVOICE_PARTS_SOURCE_NO_ERROR
												.getCode(),validationErrors);
									}										
									if(validationErrors.size()>0){
										return errorOccured;
									}
								}
							}

						} catch (Exception e) {
							logger.error("Exception in validating invoice parts"
									+ e.getMessage());
						}
					}
				}
			}
		}

		if(null!=soProviderInvoiceParts && !soProviderInvoiceParts.isEmpty()){
			//adding validation for retail price for 10,000 Max	
			errorOccured=validateRetailPrice(soProviderInvoiceParts,errorOccured,validationErrors);
			// removing validation for 1500 max limit
		}
		return errorOccured;
	}

	/**
	 * @param addons
	 */
	private boolean validateAddOns(List<SOAddon> addOns,boolean errorOccured,List<ErrorResult> validationErrors) {

		if(addOns!=null && !addOns.isEmpty()){
			for(SOAddon addon :addOns){
				if(addon.getQuantity()!=null && addon.getQuantity().intValue()>0){
					if(addon.getRetailPrice()!=null){
						addonSum = addonSum + addon.getRetailPrice().doubleValue()*addon.getQuantity();
					}
				}
			}
		}

		if (!errorOccured && addonSum > MAX_ADDON_CUSTOMER_CHARGE) {
			errorOccured=true;
			addError(
					ResultsCode.UPDATE_SO_COMPLETION_PERMITS_ADDON_TOTAL_INVALID
					.getMessage(),
					ResultsCode.UPDATE_SO_COMPLETION_PERMITS_ADDON_TOTAL_INVALID
					.getCode(),validationErrors);
		}
		return errorOccured;
	}

	/**
	 * R12.0 SPRINT 2
	 * R12.3 Edit Completion: 
	 * changed the validation logic to validate if the tripNo is associated with the SO or not.
	 * validate whether current trip is not null or not 0
	 * @param completionRequest
	 */
	private boolean validateTrip(UpdateSOCompletionRequest completionRequest,String serviceOrderId, boolean errorOccured,List<ErrorResult> validationErrors) {
		try{
			if (logger.isDebugEnabled()) {
				logger.debug("Entering UpdateSOCompletionValidator validateTrip().");
			}
			/*if(null == mobileSOActionsBO.validateLatestOpenTrip(completionRequest.getTripNo(), serviceOrderId)){
				errorOccured=true;
				addError(ResultsCode.TIMEONSITE_INVALID_TRIP.getMessage(),ResultsCode.TIMEONSITE_INVALID_TRIP.getCode(),validationErrors);
			}*/
			if(null == mobileSOActionsBO.getTripId(completionRequest.getTripNo(), serviceOrderId)){
				errorOccured=true;
				addError(ResultsCode.TIMEONSITE_INVALID_TRIP.getMessage(),ResultsCode.TIMEONSITE_INVALID_TRIP.getCode(),validationErrors);
			}
		}catch(BusinessServiceException e){
			logger.error("Exception in validateTrip()."+e);
		}
		return errorOccured;
	}
	
}
