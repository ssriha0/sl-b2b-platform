package com.newco.marketplace.mobile.api.utils.validators;

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
import com.newco.marketplace.api.mobile.beans.sodetails.PermitAddon;
import com.newco.marketplace.api.mobile.beans.sodetails.PermitTask;
import com.newco.marketplace.api.mobile.beans.sodetails.Permits;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.AddOn;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.AddOnPaymentDetails;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.AddOns;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.Part;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.PartTracking;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.Parts;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.PartsTracking;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.Pricing;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.Reference;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.References;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.Signature;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.Task;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.Tasks;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.UpdateSOCompletionRequest;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.constants.PublicMobileAPIConstant;
import com.newco.marketplace.business.iBusiness.mobile.IMobileSOActionsBO;
import com.newco.marketplace.business.iBusiness.mobile.IMobileSOManagementBO;
import com.newco.marketplace.business.iBusiness.provider.IW9RegistrationBO;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.buyer.BuyerReferenceVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.CreditCardConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.util.PropertiesUtils;
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

	public IW9RegistrationBO getW9RegistrationBO() {
		return w9RegistrationBO;
	}

	public void setW9RegistrationBO(IW9RegistrationBO w9RegistrationBO) {
		this.w9RegistrationBO = w9RegistrationBO;
	}

	private String soId = "";
	private Double addonSum = 0.0;
	private String buyerId = "";
	private Integer firmId = 0;
	private boolean errorOccured = false;
	private Integer providerId = 0;

	public UpdateSOCompletionValidator() {
		this.soId = "";
		this.addonSum = 0.00;
		this.buyerId = "";
		this.firmId = 0;
		this.providerId = 0;
		this.validationErrors = new ArrayList<ErrorResult>();
		this.errorOccured = false;

	}

	
	public Integer getProviderId() {
		return providerId;
	}

	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}

	public boolean isErrorOccured() {
		return errorOccured;
	}

	public void setErrorOccured(boolean errorOccured) {
		this.errorOccured = errorOccured;
	}

	public Integer getFirmId() {
		return firmId;
	}

	public void setFirmId(Integer firmId) {
		this.firmId = firmId;
	}

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public Double getAddonSum() {
		return addonSum;
	}

	public void setAddonSum(Double addonSum) {
		this.addonSum = addonSum;
	}

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	List<ErrorResult> validationErrors = new ArrayList<ErrorResult>();
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
	

	public List<ErrorResult> getValidationErrors() {
		return validationErrors;
	}

	public void setValidationErrors(List<ErrorResult> validationErrors) {
		this.validationErrors = validationErrors;
	}

	/**
	 * @param completionRequest
	 * @return
	 */
	public List<ErrorResult> validate(
			UpdateSOCompletionRequest completionRequest) {
		this.errorOccured = false;
		validateProviderAndSO(completionRequest);
		validateGeneralRequirements(completionRequest);
		if (PublicMobileAPIConstant.COMPLETION_STATUS_COMPLETED
				.equals(completionRequest.getCompletionStatus()) && MPConstants.WORK_STARTED
				.equals(completionRequest.getUpdateAction())) {
		
			validateTasks(completionRequest.getTasks());
			validateAddOnPanel(completionRequest.getAddOns());
			// Validate Check information
			validateAddOnPaymentDetails(completionRequest.getAddOnPaymentDetails());
			validatePermits(completionRequest.getPermits());
			validateParts(completionRequest.getParts());
			validateBuyerReferencePanel(completionRequest.getReferences());	
			validatePartsTracking(completionRequest.getPartsTracking());
		}
		else if (PublicMobileAPIConstant.COMPLETION_STATUS_COMPLETED
				.equals(completionRequest.getCompletionStatus()) && MPConstants.SERVICE_COMPLETED
				.equals(completionRequest.getUpdateAction())) {
			validateGeneralCompletionInfoPanel(completionRequest);
			validateW9Registration(completionRequest);
		}
		return this.validationErrors;
	}
	
	//validate carrier type only if tracking number is present
	private void validatePartsTracking(PartsTracking tracking){
		if(!errorOccured && null!=tracking && !tracking.getPart().isEmpty()){
		List<PartTracking> part = tracking.getPart();
		for(PartTracking partTracking:part){
			if(StringUtils.isNotBlank(partTracking.getTrackingNumber()) && StringUtils.isBlank(partTracking.getCarrier())){
				addError(
						ResultsCode.UPDATE_SO_COMPLETION_INVALID_CARRIER
								.getMessage(),
						ResultsCode.UPDATE_SO_COMPLETION_INVALID_CARRIER
								.getCode());
			}else if(StringUtils.isNotBlank(partTracking.getTrackingNumber()) && StringUtils.isNotBlank(partTracking.getCarrier())){
				if(!Arrays.asList(MPConstants.CARRIER_TYPES).contains(partTracking.getCarrier())){
					addError(
							ResultsCode.UPDATE_SO_COMPLETION_INVALID_CARRIER
									.getMessage(),
							ResultsCode.UPDATE_SO_COMPLETION_INVALID_CARRIER
									.getCode());
				}
			}
		}
		}
	}

	/**
	 * @param tasks
	 */
	private void validateTasks(Tasks tasks) {

		if(!errorOccured && tasks!=null && tasks.getTask()!=null && !tasks.getTask().isEmpty()){
			List<Integer> taskIds= null;
			try {
				taskIds = mobileSOActionsBO
						.getSOTasks(soId);
				boolean found = false;
				if(taskIds!=null && !taskIds.isEmpty()){
					for(Task task : tasks.getTask()){
						if(task!=null && task.getTaskId()!=null){
								if(!taskIds.contains(task.getTaskId())){
									addError(
											ResultsCode.UPDATE_SO_COMPLETION_TASKS_ERROR1
													.getMessage(),
											ResultsCode.UPDATE_SO_COMPLETION_TASKS_ERROR1
													.getCode());
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
	}

	/**
	 * @param completionRequest
	 */
	private void validateGeneralRequirements(
			UpdateSOCompletionRequest completionRequest) {

		if (!errorOccured) {
			if (PublicMobileAPIConstant.COMPLETION_STATUS_CANCEL
					.equals(completionRequest.getCompletionStatus())) {
				if (StringUtils.isBlank(completionRequest
						.getResolutionComments())) {
					addError(
							ResultsCode.UPDATE_SO_COMPLETION_RES_COMMENTS_INVALID
									.getMessage(),
							ResultsCode.UPDATE_SO_COMPLETION_RES_COMMENTS_INVALID
									.getCode());

				} /*else if (null == completionRequest
						.getCancellationOrRevisitReasonCode()
						|| -1 == completionRequest
								.getCancellationOrRevisitReasonCode()
								.intValue()) {
					addError(
							ResultsCode.UPDATE_SO_CANCELLATION_REASON_INVALID
									.getMessage(),
							ResultsCode.UPDATE_SO_CANCELLATION_REASON_INVALID
									.getCode());

				}*/

			}

			else if (PublicMobileAPIConstant.SERVICE_PARTIAL
					.equals(completionRequest.getCompletionStatus())) {
				/*if (null == completionRequest
						.getCancellationOrRevisitReasonCode()
						|| -1 == completionRequest
								.getCancellationOrRevisitReasonCode()
								.intValue()) {
					addError(
							ResultsCode.UPDATE_SO_REVISIT_REASON_INVALID
									.getMessage(),
							ResultsCode.UPDATE_SO_REVISIT_REASON_INVALID
									.getCode());

				}*/
			}

		}
	}

	/**
	 * @param parts
	 */
	private void validateParts(Parts parts) {
		if (!errorOccured) {

			if (PublicMobileAPIConstant.HSR_BUYER.equals(this.buyerId) && null != parts
					&& null != parts.getPart() && !parts.getPart().isEmpty()) {
				for (Part part : parts.getPart()) {
					if (StringUtils.isNotEmpty(part.getUnitCost())
							&& Double.parseDouble(part.getUnitCost()) > 999) {
						addError(
								ResultsCode.UPDATE_SO_PARTS_INFO_ERROR1
										.getMessage(),
								ResultsCode.UPDATE_SO_PARTS_INFO_ERROR1
										.getCode());
						break;
						// Unit Price should not exceed $999.00.
					} else if (StringUtils.isNotEmpty(part.getRetailPrice())
							&& Double.parseDouble(part.getRetailPrice()) > 999) {
						addError(
								ResultsCode.UPDATE_SO_PARTS_INFO_ERROR2
										.getMessage(),
								ResultsCode.UPDATE_SO_PARTS_INFO_ERROR2
										.getCode());
						break;

						// Retail Price should not exceed $999.00.
					}

					else if (StringUtils.isNotEmpty(part.getPartSource())
							&& (MPConstants.NON_SEARS).equals(part.getPartSource())
							&& StringUtils.isBlank(part.getNonSearsSource())) {
						addError(
								ResultsCode.UPDATE_SO_PARTS_INFO_ERROR3
										.getMessage(),
								ResultsCode.UPDATE_SO_PARTS_INFO_ERROR3
										.getCode());
						break;

						// Please enter the name of local part supplier.
					}

				}
			}
		}
	}

	/**
	 * @param completionRequest
	 * @return
	 */
	private void validateProviderAndSO(
			UpdateSOCompletionRequest completionRequest) {

		Integer providerId;
		try {
			//providerId = mobileSOActionsBO.validateResourceId(this.providerId);
			
			providerId = mobileSOManagementBO.validateProviderId(this.providerId.toString());

			com.newco.marketplace.dto.vo.serviceorder.ServiceOrder so = mobileSOActionsBO.getServiceOrder(soId);
			String serviceOrderId = so.getSoId();
			Map<String, String> validateMap = new HashMap<String, String>();
			validateMap.put("soId", soId);
			validateMap.put("resourceId",
					(this.providerId).toString());
			String firmForSO = mobileSOActionsBO
					.toValidateSoIdForProvider(validateMap);

			boolean checkIfAssignedToFirm = mobileSOActionsBO.checkIfAssignedToFirm(soId);
			
			boolean soActveInd = mobileSOActionsBO.checkIfSOIsActive(soId);

			
			if (null == providerId) {
				addError(ResultsCode.INVALID_PRO.getMessage(),
						ResultsCode.INVALID_PRO.getCode());
			} else if (null == serviceOrderId
					|| StringUtils.isBlank(serviceOrderId)) {
				addError(ResultsCode.INVALID_SOID.getMessage(),
						ResultsCode.INVALID_SOID.getCode());
			} else if (null == firmForSO || StringUtils.isBlank(firmForSO)) {
				addError(ResultsCode.SO_NOT_ASSOCIATED.getMessage(),
						ResultsCode.SO_NOT_ASSOCIATED.getCode());
			}
			else if(checkIfAssignedToFirm){
				addError(ResultsCode.UPDATE_SO_COMPLETION_NOT_ASSIGNED_SO.getMessage(),
						ResultsCode.UPDATE_SO_COMPLETION_NOT_ASSIGNED_SO.getCode());
			}
			else if(!soActveInd){
				addError(ResultsCode.UPDATE_SO_COMPLETION_NOT_ACTIVE_ORDER.getMessage(),
						ResultsCode.UPDATE_SO_COMPLETION_NOT_ACTIVE_ORDER.getCode());
			}
 
		} catch (BusinessServiceException e) {
			// TODO Auto-generated catch block
			logger.error("Error in validateProviderAndSO" + e.getMessage());
		}
	}

	/**
	 * @param completionRequest
	 */
	private void validateW9Registration(
			UpdateSOCompletionRequest completionRequest) {
		if (!errorOccured && null != completionRequest.getPricing()) {

			boolean isW9ExistsFlag = false;
			boolean w9isExistWithSSNInd = false;
			boolean w9isDobNotAvailable = false;
			Double providerThreshold = 0.00;
			Double completeSoAmount = 0.00;
			Double totalAmount = 0.00;

			try {
				isW9ExistsFlag = w9RegistrationBO.isW9Exists(firmId);
				if (isW9ExistsFlag) {
					w9isExistWithSSNInd = w9RegistrationBO
							.isAvailableWithSSNInd(firmId);
					providerThreshold = w9RegistrationBO
							.getProviderThreshold();
					completeSoAmount = w9RegistrationBO
							.getCompleteSOAmount(firmId);
					w9isDobNotAvailable = w9RegistrationBO
							.isDobNotAvailableWithSSN(firmId);
					
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
					addError(
							ResultsCode.PROVIDER_INCOMPLETE_W9_REGISTRATION
									.getMessage(),
							ResultsCode.PROVIDER_INCOMPLETE_W9_REGISTRATION
									.getCode());
				}
			} catch (Exception e) {
				logger.error("Error in validateW9Registration" + e.getMessage());
			}
		}
	}

	/**
	 * @param addOns
	 * @param addOnPaymentDetails
	 */
	private void validateAddOnPanel(AddOns addOns) {
		if (!errorOccured) {
			if (addOns == null || addOns.getAddOn() == null || addOns.getAddOn().isEmpty()) {
				return;
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
						addError(
								ResultsCode.UPDATE_SO_COMPLETION_ADDON_ERROR1
										.getMessage(),
								ResultsCode.UPDATE_SO_COMPLETION_ADDON_ERROR1
										.getCode());
					}
					addOn.setQty(0);
				} else if (addOn.getQty().intValue() > 5) {

					addError(
							ResultsCode.UPDATE_SO_COMPLETION_ADDON_ERROR2
									.getMessage(),
							ResultsCode.UPDATE_SO_COMPLETION_ADDON_ERROR2
									.getCode());
				}
				// not permit
				else if (addOn.getQty().intValue() > 0
						&& (null == endCustomerCharge || endCustomerCharge == 0.0)) {
					if (!errorPresent) {
						addError(
								ResultsCode.UPDATE_SO_COMPLETION_ADDON_ERROR3
										.getMessage(),
								ResultsCode.UPDATE_SO_COMPLETION_ADDON_ERROR3
										.getCode());
						errorPresent = true;
					}
				}

				// If quantity is 1 or greater for a Misc SKU, there must be a
				// description entered not permit
				else if (("Y").equals(addOn.getMiscInd()) && addOn.getQty() > 0) {
					if (StringUtils.isBlank(addOn.getDescription())) {
						addError(
								ResultsCode.UPDATE_SO_COMPLETION_ADDON_ERROR4
										.getMessage(),
								ResultsCode.UPDATE_SO_COMPLETION_ADDON_ERROR4
										.getCode());
					} else if (null == endCustomerCharge
							|| endCustomerCharge == 0.0) {
						addError(
								ResultsCode.UPDATE_SO_COMPLETION_ADDON_ERROR5
										.getMessage(),
								ResultsCode.UPDATE_SO_COMPLETION_ADDON_ERROR5
										.getCode());
					}
				}
				// not permit
				else if (("Y").equals(addOn.getMiscInd()) && addOn.getQty() <= 0
						&& endCustomerCharge > 0.0) {
					addError(
							ResultsCode.UPDATE_SO_COMPLETION_ADDON_ERROR1
									.getMessage(),
							ResultsCode.UPDATE_SO_COMPLETION_ADDON_ERROR1
									.getCode());
				}

				if (addOn.getQty() > 0 && endCustomerCharge > 0.0) {
					addonSum += (addOn.getQty() * endCustomerCharge);
				}
			}
			//checks to determine whether Customer Collect is needed
/*			if(!(StringUtils.isBlank(addonDTO.getAddonCheckbox())|| addonDTO.getAddonCheckbox().equals("0") || addonDTO.getAddonCheckbox().equals("false"))
					&& (addonSum>0.0)){
				validateCC=true;           
		    }
			double totalPermitPriceDifference=0.00;
		    if(!validateCC){
		    	if(getEndCustomerDueFinalTotal() <= 0.0){//code to handle edit completion issue
			        for(SOTaskDTO permitTask : getPermitTaskList()){
			        	if(null != permitTask.getFinalPrice() && null != permitTask.getSellingPrice()){
				        	if(permitTask.getFinalPrice()>permitTask.getSellingPrice()){
				        		validateCC=true;
				        		totalPermitPriceDifference+=permitTask.getFinalPrice()-permitTask.getSellingPrice();
				        	}
			        	}
			        }
		    	}else{
		    		validateCC=true;
		    	}	           
		    }

			if(validateCC){
				//In case of Edit Completion when EndCustomerDueFinalTotal() becomes 0.00 restoring from Permit difference and Addon Prices
				if(getEndCustomerDueFinalTotal() <= 0.0){
					double custDue=0.00;
					custDue+=totalPermitPriceDifference+addonSum;
					setEndCustomerDueFinalTotal(custDue);
				}
				// No need to go further if quantities are all zero
				boolean quantityChanged = false;
				for(AddonServiceRowDTO dto : addonDTO.getAddonServicesList()) {
					if(dto.getQuantity() > 0) {
						quantityChanged = true;
						break;
					}
				}
			 */


		}
	}
	
	
	/**
	 * @param addOnPaymentDetails
	 */
	private void validateAddOnPaymentDetails(AddOnPaymentDetails addOnPaymentDetails){
		if(null!=addOnPaymentDetails && !errorOccured){
			if (null != addOnPaymentDetails.getPaymentType()
					&& addOnPaymentDetails.getPaymentType().equals(
							UPSELL_PAYMENT_TYPE_CHECK)) {
				if (addOnPaymentDetails.getCheckNumber() == null) {
					addError(
							ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR1
									.getMessage(),
							ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR1
									.getCode());
				} 
				else if(StringUtils.isNotBlank(addOnPaymentDetails.getCheckNumber())){
					try {
						Long chkNum = Long.parseLong(addOnPaymentDetails.getCheckNumber());
						if (chkNum <= 0) {
							addError(
									ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR2
											.getMessage(),
									ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR2
											.getCode());
						}
					} catch (NumberFormatException nf) {
						addError(
								ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR2
										.getMessage(),
								ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR2
										.getCode());

					}
				}

				else if (addOnPaymentDetails.getAmountAuthorized() == null) {
					addError(
							ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR3
									.getMessage(),
							ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR3
									.getCode());

				} /*else if (null != addOnPaymentDetails.getAmountAuthorized() 
																			 * &&
																			 * getEndCustomerDueFinalTotal
																			 * (
																			 * )
																			 * !=
																			 * addonDTO
																			 * .
																			 * getCheckAmount
																			 * (
																			 * )
																			 ) {
					addError(
							ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR3
									.getMessage(),
							ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR3
									.getCode());
				}*/
			}

			// Validate Credit Card information
			else if (null != addOnPaymentDetails.getPaymentType()
					&& addOnPaymentDetails.getPaymentType().equals(
							UPSELL_PAYMENT_TYPE_CREDIT)) {
				// validate credit card (mod10 validation)
				isCreditCardInfoValidate(addOnPaymentDetails);
			}
		}

	}

	// }
	/**
	 * @param addOnPaymentDetails
	 */
	private void isCreditCardInfoValidate(
			AddOnPaymentDetails addOnPaymentDetails) {
		if (!errorOccured) {
			String cardNumber = addOnPaymentDetails.getCcNumber();
			if (addOnPaymentDetails.getCcType() == null) {
				addError(
						ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR4
								.getMessage(),
						ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR4
								.getCode());

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
						addError(
								ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR5
										.getMessage(),
								ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR5
										.getCode());

					}
				}
				if (!errorOccured && (cardType.intValue() == -1)) {
					addError(
							ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR6
									.getMessage(),
							ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR6
									.getCode());

				} else if (!errorOccured && StringUtils.isBlank(cardNumber)) {
					addError(
							ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR7
									.getMessage(),
							ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR7
									.getCode());

				}
				// Sears Commercial Card (not allowed at present)
				else if (!errorOccured && (cardNumber.length() == 16)
						&& (cardNumber.substring(0, 6)
								.equals(CreditCardConstants.SEARS_COMMERCIAL_CARD_FIRST_SIX_DIGITS))) {
					addError(
							ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR15
									.getMessage(),
							ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR15
									.getCode());

				}
				//
				else if (!errorOccured && (!formatError)
						&& !UIUtils.isCreditCardValid(cardNumber, cardType.intValue())) {
					addError(
							ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR7
									.getMessage(),
							ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR7
									.getCode());

				}

				/*
				 * if(StringUtils.isBlank(getNewCreditCard().getCreditCardHolderName
				 * ( ))){ addError(getTheResourceBundle().getString(
				 * "Credit_Card_Holders_Name" ),
				 * getTheResourceBundle().getString(
				 * "Credit_Card_Holders_Name_Validation"),
				 * OrderConstants.SOW_TAB_ERROR); }
				 */

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
						addError(
								ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR8
										.getMessage(),
								ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR8
										.getCode());

					} else if (!errorOccured && StringUtils.isBlank(expiration[0])) {
						addError(
								ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR9
										.getMessage(),
								ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR9
										.getCode());

					} else if (!errorOccured && StringUtils.isBlank(expiration[1])) {
						addError(
								ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR10
										.getMessage(),
								ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR10
										.getCode());

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
									addError(
											ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR11
													.getMessage(),
											ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR11
													.getCode());

								}

							} else if (!errorOccured && (dropdown_year < current_year)) {
								addError(
										ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR12
												.getMessage(),
										ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR12
												.getCode());

							}
						}
					}
				}

				if (!errorOccured && StringUtils.isBlank(addOnPaymentDetails.getPreAuthNumber())) {
					addError(
							ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR13
									.getMessage(),
							ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR13
									.getCode());

				}

				else if (!errorOccured && (addOnPaymentDetails.getAmountAuthorized() == null
						|| Double.parseDouble(addOnPaymentDetails
								.getAmountAuthorized()) <= 0)) {
					addError(
							ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR14
									.getMessage(),
							ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR14
									.getCode());

				}
			}
		}
	}

	/**
	 * @param permits
	 */
	private void validatePermits(Permits permits) {
		
		if(!errorOccured && null!=permits){
			if(null != permits.getPermitTasks()){
				List<Integer> soTaskIds = new ArrayList<Integer>();
				for(PermitTask permit : permits.getPermitTasks().getPermitTask()){
					if(null != permit && StringUtils.isBlank(permit.getPermitType())) {
						addError(
								ResultsCode.UPDATE_SO_COMPLETION_PERMITS_ERROR1
										.getMessage(),
								ResultsCode.UPDATE_SO_COMPLETION_PERMITS_ERROR1
										.getCode());
						break;
					}
					else{
						soTaskIds.add(permit.getTaskId());
					}
				}
				
				if(!errorOccured){
					List<Integer> permitTaskIds = new ArrayList<Integer>();
					try {
						permitTaskIds = mobileSOActionsBO.getSOPermitTaks(soId);
					} catch (BusinessServiceException e) {
					
						logger.error("Error in fetching permit tasks:"+e.getMessage());
					}
					if(permitTaskIds!=null && !permitTaskIds.isEmpty() && !soTaskIds.isEmpty()){
						if(!soTaskIds.containsAll(permitTaskIds)){
							addError(
									ResultsCode.UPDATE_SO_COMPLETION_PERMITS_ERROR3
											.getMessage(),
									ResultsCode.UPDATE_SO_COMPLETION_PERMITS_ERROR3
											.getCode());
						}
					}
				}
			}
			if(null != permits.getPermitAddons()){
				for(PermitAddon permit :  permits.getPermitAddons().getPermitAddon()){
					if (null != permit && permit.getQty() > 0) {
						if (null == permit.getCustomerCharge() || 
								(null != permit.getCustomerCharge() && 0.00 == permit.getCustomerCharge())) {
							addError(
									ResultsCode.UPDATE_SO_COMPLETION_PERMITS_ERROR2
											.getMessage(),
									ResultsCode.UPDATE_SO_COMPLETION_PERMITS_ERROR2
											.getCode());
						}
					}
				}
			}
		}

	}

	/**
	 * @param message
	 * @param code
	 */
	private void addError(String message, String code) {
		ErrorResult result = new ErrorResult();
		result.setCode(code);
		result.setMessage(message);
		this.errorOccured = true;
		this.validationErrors.add(result);
	}

	/**
	 * @param completionRequest
	 */
	private void validateGeneralCompletionInfoPanel(
			UpdateSOCompletionRequest completionRequest) {

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

				if (!PublicMobileAPIConstant.HSR_BUYER.equalsIgnoreCase(buyerId)) {

					if (!errorOccured && finalPartPrice!=null && !IsParsableNumber(finalPartPrice)) {
						addError(
								ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR1
										.getMessage(),
								ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR1
										.getCode());
						
					} else if(!errorOccured && finalPartPrice!=null && IsParsableNumber(finalPartPrice)){
						// Final parts price should be a valid amount and should
						// not
						// be greater than order's current final parts price

						Double userEnteredPartsPrice = Double
								.parseDouble(finalPartPrice);
						if (!errorOccured && userEnteredPartsPrice > partSpLimit) {
							addError(
									ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR2
											.getMessage(),
									ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR2
											.getCode());
						
						} else if (!errorOccured && userEnteredPartsPrice < 0) {
							addError(
									ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR3
											.getMessage(),
									ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR3
											.getCode());
							// addError("FinalPartsPrice",
							// PARTSFINALPRICE_CANNOT_BE_LESS_THAN_ZERO ,
							// OrderConstants.SOW_TAB_ERROR);
						}

					}
					if (!errorOccured && IsParsableNumber(soMaxLabor) == false) {
						addError(
								ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR4
										.getMessage(),
								ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR4
										.getCode());
					
					} else {
						// Final labor price should be a valid amount and should
						// not
						// be greater than order's current final labor price

						Double userEnteredLaborPrice = Double
								.parseDouble(soMaxLabor);
						if (!errorOccured && userEnteredLaborPrice > soInitialMaxLabor) {
							addError(
									ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR5
											.getMessage(),
									ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR5
											.getCode());
							
						} else if (!errorOccured && userEnteredLaborPrice < 0) {
							addError(
									ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR6
											.getMessage(),
									ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR6
											.getCode());
						
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
							addError(
									ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR7
											.getMessage(),
									ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR7
											.getCode());
	
						} else if (!errorOccured && pricing.getLabourChangeReasonCodeId().intValue() == MPConstants.OTHER_REASON
								&& (StringUtils.isBlank(pricing
										.getLabourChangeReasonComments()))) {

							addError(
									ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR8
											.getMessage(),
									ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR8
											.getCode());

						}
					}

					if (!errorOccured && nonMaxPriceForPartCheck) {
						if (!errorOccured && null == pricing.getPartChangeReasonCodeId()
								|| pricing.getPartChangeReasonCodeId().intValue() <=0) {
							addError(
									ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR9
											.getMessage(),
									ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR9
											.getCode());

						} else if (!errorOccured && pricing.getPartChangeReasonCodeId().intValue() == MPConstants.OTHER_REASON
								&& (StringUtils.isBlank(pricing
										.getPartChangeReasonComments()))) {
							addError(
									ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR10
											.getMessage(),
									ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR10
											.getCode());

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
							addError(
									ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR7
											.getMessage(),
									ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR7
											.getCode());
	
						} else if (!errorOccured && pricing.getLabourChangeReasonCodeId().intValue() == MPConstants.OTHER_REASON
								&& (StringUtils.isBlank(pricing
										.getLabourChangeReasonComments()))) {

							addError(
									ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR8
											.getMessage(),
									ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR8
											.getCode());

						}
					}

					if (nonMaxPriceForPartCheck) {
						if (!errorOccured && null == pricing.getPartChangeReasonCodeId()
								|| pricing.getPartChangeReasonCodeId().intValue() <=0) {
							addError(
									ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR9
											.getMessage(),
									ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR9
											.getCode());

						} else if (!errorOccured && pricing.getPartChangeReasonCodeId().intValue() == MPConstants.OTHER_REASON
								&& (StringUtils.isBlank(pricing
										.getPartChangeReasonComments()))) {
							addError(
									ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR10
											.getMessage(),
									ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR10
											.getCode());

						}
					}

					if (nonMaxPriceForLaborCheck || nonMaxPriceForPartCheck) {
						if (!errorOccured && IsParsableNumber(soMaxLabor) == false) {
							addError(
									ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR4
											.getMessage(),
									ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR4
											.getCode());

						} else {
							Double userEnteredLaborPrice = Double
									.parseDouble(soMaxLabor);
							if (!errorOccured && userEnteredLaborPrice > soInitialMaxLabor) {
								addError(
										ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR5
												.getMessage(),
										ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR5
												.getCode());

							} else if (!errorOccured && userEnteredLaborPrice < 0) {
								addError(
										ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR6
												.getMessage(),
										ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR6
												.getCode());

							}
						}
						if (!errorOccured && finalPartPrice!=null && IsParsableNumber(finalPartPrice) == false) {
							addError(
									ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR1
											.getMessage(),
									ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR1
											.getCode());

						} else if (!errorOccured && finalPartPrice!=null && IsParsableNumber(finalPartPrice)) {
							Double userEnteredPartsPrice = Double
									.parseDouble(finalPartPrice);
							if (!errorOccured && userEnteredPartsPrice > partSpLimit) {
								addError(
										ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR2
												.getMessage(),
										ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR2
												.getCode());

							} else if (!errorOccured && userEnteredPartsPrice < 0) {
								addError(
										ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR3
												.getMessage(),
										ResultsCode.UPDATE_SO_GENERAL_INFO_ERROR3
												.getCode());

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

			/*
			 * } /* for(SOTaskDTO task : getPermitTaskList()){
			 * if(task.getFinalPrice()==null){ addError("PermitTaskType",
			 * "Please enter the final permit price" ,
			 * OrderConstants.SOW_TAB_ERROR); break; }
			 * if(task.getPermitType()==null ||task.getPermitType()<1){
			 * addError("PermitTaskType", "Please enter permit type" ,
			 * OrderConstants.SOW_TAB_ERROR); break; } }
			 */
		}
	}

	/**
	 * @param references
	 */
	private void validateBuyerReferencePanel(References references) {

		if (!errorOccured && null!=references && null!=references.getReference() && !references.getReference().isEmpty()) {
			List<BuyerReferenceVO> requiredBuyerReferneces = null;
			try {
				boolean mandatoryInd = true;
				requiredBuyerReferneces = mobileSOActionsBO
						.getRequiredBuyerReferences(soId,mandatoryInd);
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
									addError(
											ResultsCode.UPDATE_SO_COMPLETION_CUSTOM_REFERENCES_ERROR3
											.getMessage(),
											ResultsCode.UPDATE_SO_COMPLETION_CUSTOM_REFERENCES_ERROR3
											.getCode());
									break;

								}
								//SPM-1172 prevent validation error for optional reference
								if(!errorOccured && referenceName.equals(dbReferenceName) && StringUtils.isBlank(referenceValue)){
									addError(
											ResultsCode.UPDATE_SO_COMPLETION_CUSTOM_REFERENCES_ERROR5
											.getMessage(),
											ResultsCode.UPDATE_SO_COMPLETION_CUSTOM_REFERENCES_ERROR5
											.getCode());
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
													addError(
															ResultsCode.UPDATE_SO_COMPLETION_CUSTOM_REFERENCES_ERROR2
															.getMessage(),
															ResultsCode.UPDATE_SO_COMPLETION_CUSTOM_REFERENCES_ERROR2
															.getCode());

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
							addError(
									ResultsCode.UPDATE_SO_COMPLETION_CUSTOM_REFERENCES_ERROR1
											.getMessage(),
									ResultsCode.UPDATE_SO_COMPLETION_CUSTOM_REFERENCES_ERROR1
											.getCode());

						}
						
					}
				}
			}

		}
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
			ServiceOrder serviceOrder, boolean pendingClaimInd) {
		if(serviceOrder!=null){
			if (StringUtils.isBlank(serviceOrder
					.getResolutionDs())) {
				addError(
						ResultsCode.UPDATE_SO_COMPLETION_RES_COMMENTS_INVALID
								.getMessage(),
						ResultsCode.UPDATE_SO_COMPLETION_RES_COMMENTS_INVALID
								.getCode());

			} 
			// removing validation as part of SL-20525
			/*if(!errorOccured){
				validateTasks(serviceOrder.getTasks());
			}*/
			
			if(!errorOccured){
				validateAddOns(serviceOrder.getAddons());
			}
			
			if(!errorOccured && serviceOrder.getBuyerId().intValue() == Integer.parseInt(PublicMobileAPIConstant.HSR_BUYER)){
				validateInvoiceParts(serviceOrder.getSoProviderInvoiceParts());
			}
			
			//validate documents signature and RI Permit Document
			if(!errorOccured){
				validateDocuments(soId,buyerId,serviceOrder);
			}
			
			// validate references
			//validate documents signature and RI Permit Document
			if(!errorOccured){
				validateMandatoryReferences(serviceOrder.getCustomReferences());
			}
			
			if(!errorOccured && !pendingClaimInd){
				validatePricing(serviceOrder);
			}
			
			// the amount provided should be equal to the amount due from customer
			if(!errorOccured && null!=serviceOrder.getAdditionalPayment()){
				validateAdditionalPaymentAmt(serviceOrder);
			}
		}
		
		return this.validationErrors;
	}
	
	/**
	 * @param customReferences
	 */
	private void validateMandatoryReferences(
			List<SOCustomReference> customReferences) {
		
		List<BuyerReferenceVO> requiredBuyerReferneces = null;
		try {
			boolean mandatoryInd = true;
			requiredBuyerReferneces = mobileSOActionsBO
					.getRequiredBuyerReferences(soId,mandatoryInd);
			if((requiredBuyerReferneces!=null && !requiredBuyerReferneces.isEmpty()) && (customReferences == null || customReferences.isEmpty())){
				// custom ref manadatory
				addError(
						ResultsCode.UPDATE_SO_COMPLETION_CUSTOM_REFERENCES_ERROR4
								.getMessage(),
						ResultsCode.UPDATE_SO_COMPLETION_CUSTOM_REFERENCES_ERROR4
								.getCode());	
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

							addError(
									ResultsCode.UPDATE_SO_COMPLETION_CUSTOM_REFERENCES_ERROR4
											.getMessage(),
									ResultsCode.UPDATE_SO_COMPLETION_CUSTOM_REFERENCES_ERROR4
											.getCode());

						}
						
					}
				}
			}
		} catch (BusinessServiceException e) {
			// TODO Auto-generated catch block
			logger.error("Error inside validateMandatoryReferences"
					+ e.getMessage());
		}
		
	}

	/**
	 * @param soId
	 * @param buyerId
	 */
	private void validateDocuments(String soId, String buyerId,ServiceOrder serviceOrder) {
		
		List<DocumentVO> documentVoList = new ArrayList<DocumentVO>();	
		List<Signature> signaturesList = new ArrayList<Signature>();	
		List<String> soDocTitles = new ArrayList<String>();
		List<String> resourceInds = new ArrayList<String>();

		try {
			documentVoList = mobileSOActionsBO.getServiceOrderDocuments(soId);
			if(documentVoList!=null){
				for(DocumentVO obj:documentVoList)
				{
					if(obj!=null && obj.getTitle()!=null){
						soDocTitles.add(obj.getTitle().toLowerCase());
					}
				}
				
				if(serviceOrder.getNonDeletedTasks()!=null && !serviceOrder.getNonDeletedTasks().isEmpty() && (MPConstants.SEARS_BUYER).equals(buyerId)){
					for(SOTask task : serviceOrder.getNonDeletedTasks()){
						if(OrderConstants.PERMIT_TASK.equals(task.getTaskType())) {
							if(!soDocTitles.contains(MPConstants.PROOF_OF_PERMIT)){		
								// need to upload proof of permit
								addError(
										ResultsCode.UPDATE_SO_COMPLETION_DOCUMENT_VALIDATION_ERROR1
												.getMessage(),
										ResultsCode.UPDATE_SO_COMPLETION_DOCUMENT_VALIDATION_ERROR1
												.getCode());	
								break;
							}
							
						}
					}
					if(!errorOccured && serviceOrder.getAddons()!=null && !serviceOrder.getAddons().isEmpty()){
						for(SOAddon soAddon :serviceOrder.getAddons()){
							if((MPConstants.PERMIT_SKU).equals(soAddon.getSku()) && soAddon.getQuantity().intValue()>0){
								if(!soDocTitles.contains(MPConstants.PROOF_OF_PERMIT)){		
									// need to upload proof of permit
									addError(
											ResultsCode.UPDATE_SO_COMPLETION_DOCUMENT_VALIDATION_ERROR1
													.getMessage(),
											ResultsCode.UPDATE_SO_COMPLETION_DOCUMENT_VALIDATION_ERROR1
													.getCode());	
									break;
								}
							}
						}
					}
					
				}
				
				if(!errorOccured){
					if(!soDocTitles.contains(MPConstants.CUSTOMER_SIGNATURE) || !soDocTitles.contains(MPConstants.PROVIDER_SIGNATURE)){		
						// need to upload both customer and provider signature for completion
						addError(
								ResultsCode.UPDATE_SO_COMPLETION_DOCUMENT_VALIDATION_ERROR2
										.getMessage(),
								ResultsCode.UPDATE_SO_COMPLETION_DOCUMENT_VALIDATION_ERROR2
										.getCode());	
					}
					
					else{
						signaturesList = mobileSOActionsBO.getServiceOrderSignature(soId);

						if(signaturesList!=null && !signaturesList.isEmpty()){
							for(Signature signature : signaturesList){
								resourceInds.add(signature.getResourceInd());
							}
						}
					
						if(!resourceInds.contains(MPConstants.CUSTOMER_SIGNATURE_IND) || !resourceInds.contains(MPConstants.PROVIDER_SIGNATURE_IND)){
							// need to complete signature details before completion
							addError(
									ResultsCode.UPDATE_SO_COMPLETION_DOCUMENT_VALIDATION_ERROR3
											.getMessage(),
									ResultsCode.UPDATE_SO_COMPLETION_DOCUMENT_VALIDATION_ERROR3
											.getCode());	
						}
					}
				}

			}
			else{
				// upload necessary documents
				addError(
						ResultsCode.UPDATE_SO_COMPLETION_DOCUMENT_VALIDATION_ERROR4
								.getMessage(),
						ResultsCode.UPDATE_SO_COMPLETION_DOCUMENT_VALIDATION_ERROR4
								.getCode());	
			}
		} catch (BusinessServiceException e) {
				logger.error("Exception in fetching documents"+e.getMessage());
		}
	}

	/**
	 * @param serviceOrder
	 */
	private void validateAdditionalPaymentAmt(ServiceOrder serviceOrder) {
		SOAdditionalPayment paymentDetails = serviceOrder.getAdditionalPayment();
		BigDecimal amountAuthorized = paymentDetails.getPaymentAmount();
		List<SOAddon> addons = serviceOrder.getAddons();
		BigDecimal addonAmt = BigDecimal.ZERO;
		for(SOAddon addon:addons ){
			if(addon.getQuantity()>0){
				BigDecimal qty = new BigDecimal(addon.getQuantity());
				BigDecimal currentAddonAmt = addon.getRetailPrice().multiply(qty);
				addonAmt = addonAmt.add(currentAddonAmt);
			}
		}
		if(!amountAuthorized.equals(addonAmt)){
			addError(
					ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENT_VALIDATION_ERROR
							.getMessage(),
					ResultsCode.UPDATE_SO_COMPLETION_ADDON_PAYMENT_VALIDATION_ERROR
							.getCode());	
		}
	}
	
	public List<ErrorResult> validatePreAuthAmt(AddOnPaymentDetails paymentDetails, String soId) {
		//SOAdditionalPayment paymentDetails = serviceOrder.getAdditionalPayment();
		BigDecimal preAuthAmt = new BigDecimal(paymentDetails.getAmountAuthorized());
		List<AddOn> addons = new ArrayList<AddOn>();
		try {
			addons = mobileSOActionsBO.getSOAddonDetails(soId);
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
							.getCode());	
		}
		if (addonAmt.doubleValue() > MAX_ADDON_CUSTOMER_CHARGE) {
			addError(
					ResultsCode.UPDATE_SO_COMPLETION_PERMITS_ADDON_TOTAL_INVALID
							.getMessage(),
					ResultsCode.UPDATE_SO_COMPLETION_PERMITS_ADDON_TOTAL_INVALID
							.getCode());
		}
		return this.validationErrors;
	}

	/**
	 * @param serviceOrder
	 */
	private void validatePricing(ServiceOrder serviceOrder) {
		SOWorkflowControls soWorkflowControls = serviceOrder.getSOWorkflowControls();

		if(serviceOrder.getSpendLimitLabor()!=null && soWorkflowControls!=null && soWorkflowControls.getFinalPriceLabor()==null){
			addError(
					ResultsCode.UPDATE_SO_COMPLETION_PRICING_LABOR_FAILURE
							.getMessage(),
					ResultsCode.UPDATE_SO_COMPLETION_PRICING_LABOR_FAILURE
							.getCode());		
		}
		
		else if(serviceOrder.getSpendLimitParts()!=null && soWorkflowControls!=null && soWorkflowControls.getFinalPriceParts()==null){
			addError(
					ResultsCode.UPDATE_SO_COMPLETION_PRICING_PARTS_FAILURE
							.getMessage(),
					ResultsCode.UPDATE_SO_COMPLETION_PRICING_PARTS_FAILURE
							.getCode());		
		}
	}

	/**
	 * @param soProviderInvoiceParts
	 */
	private void validateInvoiceParts(
			List<SOProviderInvoiceParts> soProviderInvoiceParts) {
		Double netPayment = 0.00;
		Double finalPayment = 0.00;
		Double finalPaymentTotal = 0.00;
		Double payment = 0.00;
		String maxValueForInvoicePartsHSR = PropertiesUtils
				.getPropertyValue(PublicMobileAPIConstant.HSR_MAX_VALUE_INVOICE_PARTS);
		Double maxValue = Double
				.parseDouble(maxValueForInvoicePartsHSR);

			if(null!=soProviderInvoiceParts && !soProviderInvoiceParts.isEmpty()){
				for(SOProviderInvoiceParts invoicePart : soProviderInvoiceParts){
					if(invoicePart!=null){

						try {

							Integer quantity = invoicePart.getQty();
							HashMap<String, Double> buyerPriceCalcValues = mobileSOActionsBO
									.getReimbursementRate(
											invoicePart.getPartCoverage(),
											invoicePart.getSource());
							if (null != buyerPriceCalcValues
									&& !buyerPriceCalcValues.isEmpty()) {
								String reImbRate = buyerPriceCalcValues
										.get("reImbursementRate").toString();
								String grossValue = buyerPriceCalcValues
										.get("grossupVal").toString();
								if (StringUtils.isNotEmpty(reImbRate)
										&& StringUtils.isNotEmpty(grossValue)
										&& null!=invoicePart
												.getRetailPrice()) {

									Double reimbursementRate = Double
											.parseDouble(reImbRate);
									Double slGrossUpValue = Double
											.parseDouble(grossValue);
									Double retailPrice = invoicePart.getRetailPrice().doubleValue();

									netPayment = MoneyUtil
											.getRoundedMoney(retailPrice
													* (reimbursementRate / 100)
													* 100) / 100;
									netPayment = netPayment * quantity;

									finalPayment = MoneyUtil
											.getRoundedMoney(retailPrice
													* slGrossUpValue * 100) / 100;
									finalPayment = finalPayment * quantity;
									finalPaymentTotal = finalPayment
											+ finalPaymentTotal;
									payment = payment + netPayment;

								}
							}

						} catch (BusinessServiceException e) {
							logger.error("Exception in fetching reimbursement rate"
									+ e.getMessage());
						}
					
					}
					if (finalPaymentTotal > maxValue) {
						addError(
								ResultsCode.UPDATE_SO_COMPLETION_PARTS_MAX_PRICE_EXCEEDED
										.getMessage(),
								ResultsCode.UPDATE_SO_COMPLETION_PARTS_MAX_PRICE_EXCEEDED
										.getCode());
						return;
					}

				}
			}
	}

	/**
	 * @param addons
	 */
	private void validateAddOns(List<SOAddon> addOns) {
		
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
			addError(
					ResultsCode.UPDATE_SO_COMPLETION_PERMITS_ADDON_TOTAL_INVALID
							.getMessage(),
					ResultsCode.UPDATE_SO_COMPLETION_PERMITS_ADDON_TOTAL_INVALID
							.getCode());
		}
	}

	/**
	 * @param tasks
	 */
	private void validateTasks(List<SOTask> tasks) {
		
		// TODO Auto-generated method stub
			if(null!=tasks && !tasks.isEmpty()){
				for(SOTask soTask :tasks){
					if(null!=soTask){
						//exclude permit tasks and deleted tasks for completion check
						if(PublicMobileAPIConstant.TASK_ACTIVE.equals(soTask.getTaskStatus())){
							addError(
									ResultsCode.UPDATE_SO_COMPLETION_TASKS_INCOMPLETE
											.getMessage(),
									ResultsCode.UPDATE_SO_COMPLETION_TASKS_INCOMPLETE
											.getCode());
							return;
						}
					}
				}
			}
	}
}
