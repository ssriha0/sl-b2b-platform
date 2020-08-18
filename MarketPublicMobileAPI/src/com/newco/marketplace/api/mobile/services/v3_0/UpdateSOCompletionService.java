package com.newco.marketplace.api.mobile.services.v3_0;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.ErrorResult;
import com.newco.marketplace.api.beans.Result;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.beans.sodetails.PermitAddon;
import com.newco.marketplace.api.mobile.beans.sodetails.PermitTask;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.AddOn;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.PartTracking;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.Reference;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.Task;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.v3_0.UpdateSOCompletionRequest;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.v3_0.UpdateSOCompletionResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.api.mobile.utils.mappers.v3_0.UpdateSOCompletionMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.businessImpl.vibePostAPI.PushNotificationAlertTask;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerFeatureSetBO;
import com.newco.marketplace.business.iBusiness.document.IDocumentBO;
import com.newco.marketplace.business.iBusiness.mobile.IMobileSOActionsBO;
import com.newco.marketplace.business.techtalk.ITeclTalkBuyerPortalBO;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.BuyerFeatureConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.mobile.api.utils.validators.v3_0.UpdateSOCompletionValidator;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.relayservicesnotification.service.IRelayServiceNotification;
import com.newco.marketplace.utils.MoneyUtil;
import com.newco.marketplace.vo.login.LoginCredentialVO;
import com.newco.marketplace.vo.mobile.CustomReferenceVO;
import com.newco.marketplace.vo.mobile.ProviderHistoryVO;
import com.newco.marketplace.vo.mobile.SOPartLaborPriceReasonVO;
import com.servicelive.orderfulfillment.client.OFHelper;
import com.servicelive.orderfulfillment.domain.SOContact;
import com.servicelive.orderfulfillment.domain.SOLocation;
import com.servicelive.orderfulfillment.domain.SOTask;
import com.servicelive.orderfulfillment.domain.util.PricingUtil;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.Parameter;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;

/**
 * This class would act as a Servicer class for Provider so Completion
 * 
 * @author Infosys
 * @version 3.0
 */
@APIRequestClass(UpdateSOCompletionRequest.class)
@APIResponseClass(UpdateSOCompletionResponse.class)
public class UpdateSOCompletionService extends BaseService {

	private Logger logger = Logger.getLogger(UpdateSOCompletionService.class);
	private UpdateSOCompletionMapper updateSoCompletionMapper;
	private IMobileSOActionsBO mobileSOActionsBO;
	private IBuyerFeatureSetBO buyerFeatureSetBO;
	private UpdateSOCompletionValidator updateSOCompletionValidator;
	private IDocumentBO documentBO;
	private ITeclTalkBuyerPortalBO techTalkByerBO;
	public static final int ORDER_CLOSED = 180;
	public static final int ORDER_CANCELELD = 120;
	public static final int ORDER_PENDINGCANCEL = 165;
	public static final int ORDER_PENDINGREVIEW = 68;
	public static final int ORDER_PENDINGESPONSE = 69;
	private OFHelper ofHelper = new OFHelper();
	
	private IRelayServiceNotification relayNotificationService;
	private PushNotificationAlertTask pushNotificationAlertTask;
	

	public PushNotificationAlertTask getPushNotificationAlertTask() {
		return pushNotificationAlertTask;
	}

	public void setPushNotificationAlertTask(PushNotificationAlertTask pushNotificationAlertTask) {
		this.pushNotificationAlertTask = pushNotificationAlertTask;
	}

	public OFHelper getOfHelper() {
		return ofHelper;
	}

	public void setOfHelper(OFHelper ofHelper) {
		this.ofHelper = ofHelper;
	}

	public UpdateSOCompletionValidator getUpdateSOCompletionValidator() {
		return updateSOCompletionValidator;
	}

	public void setUpdateSOCompletionValidator(
			UpdateSOCompletionValidator updateSOCompletionValidator) {
		this.updateSOCompletionValidator = updateSOCompletionValidator;
	}

	public IBuyerFeatureSetBO getBuyerFeatureSetBO() {
		return buyerFeatureSetBO;
	}

	public void setBuyerFeatureSetBO(IBuyerFeatureSetBO buyerFeatureSetBO) {
		this.buyerFeatureSetBO = buyerFeatureSetBO;
	}

	public UpdateSOCompletionMapper getUpdateSoCompletionMapper() {
		return updateSoCompletionMapper;
	}

	public void setUpdateSoCompletionMapper(
			UpdateSOCompletionMapper updateSoCompletionMapper) {
		this.updateSoCompletionMapper = updateSoCompletionMapper;
	}

	public IMobileSOActionsBO getMobileSOActionsBO() {
		return mobileSOActionsBO;
	}

	public void setMobileSOActionsBO(IMobileSOActionsBO mobileSOActionsBO) {
		this.mobileSOActionsBO = mobileSOActionsBO;
	}

	public ITeclTalkBuyerPortalBO getTechTalkByerBO() {
		return techTalkByerBO;
	}

	public void setTechTalkByerBO(ITeclTalkBuyerPortalBO techTalkByerBO) {
		this.techTalkByerBO = techTalkByerBO;
	}

	/**
	 * This method is for updating completion details of a SO
	 * 
	 * @param fromDate
	 *            String,toDate String
	 * @return String
	 */

	public UpdateSOCompletionService() {
		super();
	}

	/**
	 * 
	 */
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		logger.info("Entering execute method of UpdateSOCompletionService");
		long startTime = System.currentTimeMillis();		
		UpdateSOCompletionResponse updateSOCompletionResponse =new UpdateSOCompletionResponse();
		Results results = null;
		UpdateSOCompletionRequest updateSOCompletionRequest = (UpdateSOCompletionRequest) apiVO
				.getRequestFromPostPut();
		String soId = (String) apiVO.getSOId();
		//R12.0 SPRINT 2
		Integer currentTripNo =  updateSOCompletionRequest.getTripNo();
		logger.info("Current Trip#"+currentTripNo);
		
		String dispositionCode = updateSOCompletionRequest.getDispositionCode();
		logger.info("dispositionCode#"+dispositionCode);
		
		
		boolean relayServicesNotifyFlag = false;
		
		List<Parameter> parameters = new ArrayList<Parameter>();
		
		//R12.0 SLM-5 : declaring variables for retrieving buyerId and wfStateId
				Integer buyerId=0;
				Integer wfStateId=0;
		try {
			SecurityContext securityContext = getSecurityContextForVendor(apiVO
					.getProviderResourceId());
			String userName="";
			Integer entityId= 0;
			Integer roleId=0;
			String createdBy="";
			Integer providerId=0;
			Integer firmId=0;
			
			/*updateSOCompletionValidator.setProviderId(apiVO
					.getProviderResourceId());*/
			
			providerId = apiVO.getProviderResourceId();
			
			if(securityContext!=null){
				/*updateSOCompletionValidator.setFirmId(securityContext
						.getCompanyId());*/
				userName = securityContext.getUsername();
				entityId = securityContext.getVendBuyerResId();
				roleId = securityContext.getRoleId();
				LoginCredentialVO lvRoles = securityContext.getRoles();
				createdBy = lvRoles.getFirstName() +" "+lvRoles.getLastName();
				firmId = securityContext.getCompanyId();
			}
			updateSOCompletionResponse.setSoId(soId);
			//updateSOCompletionValidator.setSoId(soId);
			updateSOCompletionValidator.setAddonSum(0.00);
			/*updateSOCompletionValidator
					.setValidationErrors(new ArrayList<ErrorResult>());*/
      
			//R12.0 SLM-5 : call for retrieving buyer id and wf state id of service order
			
			HashMap<String, Object> map = mobileSOActionsBO.getBuyerAndWfStateForSO(soId);
			buyerId =(int)(long)(Long)map.get("buyerId");
			wfStateId=(int)(long)(Long)map.get("wfStateId");

			String buyerIdValue = "";
			if(null!=buyerId && 0!=buyerId){
				buyerIdValue = Integer.toString(buyerId.intValue());
			}
			
			
			//updateSOCompletionValidator.setBuyerId(buyerId.toString());
			List<ErrorResult> validationErrors = updateSOCompletionValidator
					.validate(updateSOCompletionRequest,soId,providerId,firmId,buyerIdValue);
		
			if(!validationErrors.isEmpty()){
				results = new Results();
				results.setError(validationErrors);
				updateSOCompletionResponse.setResults(results);
				return updateSOCompletionResponse;
			}
			
			
			//R12.3 - SL-20673 - START 
			//1)Remove the latest open trip validation.
			//2) Only validate if the trip is associated with the SO 
			// Integer currentTripId = mobileSOActionsBO.validateTrip(currentTripNo,soId);
			Integer currentTripId = mobileSOActionsBO.getTripId(currentTripNo, soId);
			//TODO: Check if the below check is required.
			if(null==currentTripId){
				updateSOCompletionResponse
				.setResults(Results
						.getError(
								ResultsCode.TIMEONSITE_INVALID_TRIP
										.getMessage(),
								ResultsCode.TIMEONSITE_INVALID_TRIP
										.getCode()));
				return updateSOCompletionResponse;
			}
			//R12.3 - SL-20673 - END
			
			//persisting dispositionCode
			if(null!=dispositionCode){
				techTalkByerBO.insertOrUpdateDispositionCode(soId, dispositionCode);
			}
			
			
			String resolutionComments =  updateSOCompletionRequest
					.getResolutionComments();
			// R12.0: Removing Revisit Needed SubStatus change code(PARTIAL & (WORK NOT STARTED||WORK STARTED))
			
			
			if (updateSOCompletionRequest.getCompletionStatus().equals(
					MPConstants.CANCEL)
					&& (MPConstants.WORK_NOT_STARTED
							.equals(updateSOCompletionRequest.getUpdateAction()) || MPConstants.WORK_STARTED
							.equals(updateSOCompletionRequest.getUpdateAction()))) {//CANCEL & WORK NOT STARTED
				
				long startCancel = System.currentTimeMillis();
				
				//R12.0 SLM-5 : passing buyer id and wf state id for NPS Notification in update so for CANCELLATION_REQUESTED 
				mobileSOActionsBO.updateSoCompletion(soId,
						MPConstants.CANCEL_SUBSTATUS_ID, resolutionComments, buyerId, wfStateId);
				
				 //R12.0: Update the trip No details in the so_trip_details table when resolution comment is added
				 insertTripDetails(MPConstants.TRIP_RESOLUTIONCOMMENT, currentTripNo,currentTripId,createdBy,updateSOCompletionRequest);
				
				//Logging history
				historyLogging(roleId,soId,userName,entityId,MPConstants.CANCELLATION_REQUESTED,createdBy);
				
				//push notifications code				
				if(roleId < 3){					
					pushNotificationAlertTask.AddAlert(soId, OrderConstants.PROVIDER_CANCELLATION_PUSH_NOTIFICATION_TEMPLATE);					
				}
				
				
				
				// Senting Notification for Relay Services
				relayServicesNotifyFlag=relayNotificationService.isRelayServicesNotificationNeeded(buyerId,soId);
				
				if(relayServicesNotifyFlag){
					Map<String, String> params = new HashMap<String, String>();
					// params.put("cancellationCharge", (null != updateSOCompletionRequest.getCancellationCharge() ? updateSOCompletionRequest.getCancellationCharge() : "0"));
					params.put("resolutionComments", (null != resolutionComments ? resolutionComments : ""));
					relayNotificationService.sentNotificationRelayServices(MPConstants.CANCELLATION_REQUESTED_API_EVENT, soId, params);
				}
				
				List<Result> sucesses = new ArrayList<Result>();
				sucesses = addSuccess(sucesses,
						ResultsCode.UPDATE_SO_COMPLETION_CANCELLED.getCode(),
						ResultsCode.UPDATE_SO_COMPLETION_CANCELLED.getMessage());
				results = new Results();
				results.setResult(sucesses);
				updateSOCompletionResponse.setSoId(soId);
				updateSOCompletionResponse.setResults(results);
				long endCancel = System.currentTimeMillis();
				logger.info("Time taken for the the cancellation in ms ::"+(endCancel-startCancel));
			}
			
			else if (updateSOCompletionRequest.getCompletionStatus()
					.equalsIgnoreCase(MPConstants.SERVICE_COMPLETE)
					&& updateSOCompletionRequest.getUpdateAction()
							.equalsIgnoreCase(MPConstants.WORK_STARTED)) {//COMPLETED AND WORK STARTED
				try {

				long startWork = System.currentTimeMillis();
				//updating signature
				if(null!=updateSOCompletionRequest.getProviderSignature()||null!=updateSOCompletionRequest.getCustomerSignature()){
					mobileSOActionsBO.insertSignatureDetails(updateSOCompletionRequest,soId);
					
					// SLM-117 : This will make sure that the batch will
					// pick the record to regenerate and send the email
					// for any update in the signature					
					resetMobilePDFGenerationStatus(soId,true);
					
					// R12.0: Update the trip No details in the so_trip_details table when signature is added
					insertTripDetails(MPConstants.TRIP_SIGNATURE, currentTripNo,currentTripId,createdBy,updateSOCompletionRequest);
				}
					
				//Tasks update
				// removing task status update as part of SL-20525

				/*if (null != updateSOCompletionRequest.getTasks()
							&& null != updateSOCompletionRequest.getTasks()
									.getTask()
							&& !updateSOCompletionRequest.getTasks().getTask()
									.isEmpty()) {
						mobileSOActionsBO.updateTaskDetails(
								updateSOCompletionRequest, soId);
						
						// R12.0: Update the trip No details in the so_trip_details table in case of task is updated.
						insertTripDetails(MPConstants.TRIP_TASK, currentTripNo,currentTripId,createdBy,updateSOCompletionRequest);
				}*/
				
				// resolution comments update
				if(StringUtils.isNotBlank(resolutionComments)){
						mobileSOActionsBO.updateResolutionComments(
								updateSOCompletionRequest
										.getResolutionComments(), soId);
						
						
						resetMobilePDFGenerationStatus(soId,false);	
					// R12.0: Update the trip No details in the so_trip_details table when resolution comment is added
					insertTripDetails(MPConstants.TRIP_RESOLUTIONCOMMENT, currentTripNo,currentTripId,createdBy,updateSOCompletionRequest);

				}
				
		        //R12_0 Sprint 4: Adding invoice part indicator in so_workflow_controls
					if (StringUtils.isNotBlank(updateSOCompletionRequest
							.getNoPartsRequired())) {
						for (String partType : MPConstants.INVOICE_PARTS_TYPES) {
							if (updateSOCompletionRequest
											.getNoPartsRequired().equals(
													partType)) {
								mobileSOActionsBO.insertPartsRequiredInd(soId,
										partType);
								break;
							}
						}
					}
		        
				
				if((null != updateSOCompletionRequest.getTasks()
						&& null != updateSOCompletionRequest.getTasks()
						.getTask()
				&& !updateSOCompletionRequest.getTasks().getTask()
						.isEmpty())||(StringUtils.isNotBlank(resolutionComments))){
					//R12.0 SLM-5 : passing buyer id and wf state id for NPS Notification in update so for WORK_STARTED 
					mobileSOActionsBO.updateSoCompletion(soId,
							MPConstants.WORK_STARTED_SUBSTATUS_ID, null, buyerId, wfStateId);
					//Logging history
					historyLogging(roleId,soId,userName,entityId,MPConstants.WORK_STARTED,createdBy);			
				}
				//permits update
				if(null != updateSOCompletionRequest.getPermits()){
					//update permit addons
					if(null != updateSOCompletionRequest.getPermits().getPermitAddons() && updateSOCompletionRequest.getPermits().getPermitAddons().getPermitAddon()!=null
							&& !updateSOCompletionRequest.getPermits().getPermitAddons().getPermitAddon().isEmpty()){
						mobileSOActionsBO.updateAddonPermits(
								updateSOCompletionRequest.getPermits(), soId);
						resetMobilePDFGenerationStatus(soId,false);
				
						// R12.0: Update the trip No details in the so_trip_details table in case of permit addon update
						insertTripDetails(MPConstants.TRIP_PERMIT_ADDON, currentTripNo,currentTripId,createdBy,updateSOCompletionRequest);
					}
					//update permit tasks
					if(null != updateSOCompletionRequest.getPermits().getPermitTasks()
							&& updateSOCompletionRequest.getPermits().getPermitTasks().getPermitTask()!=null 
							&& !updateSOCompletionRequest.getPermits().getPermitTasks().getPermitTask().isEmpty()){
						mobileSOActionsBO.updatePermitTasks(
								updateSOCompletionRequest.getPermits().getPermitTasks(), soId);

						// R12.0: Update the trip No details in the so_trip_details table in case of permit task update
						insertTripDetails(MPConstants.TRIP_PERMIT_TASK, currentTripNo,currentTripId,createdBy,updateSOCompletionRequest);
					}
				}

				//addon update
				if (null != updateSOCompletionRequest.getAddOns()
							&& updateSOCompletionRequest.getAddOns().getAddOn()!=null &&
									updateSOCompletionRequest.getAddOns().getAddOn().size() != 0) {
					
						mobileSOActionsBO.updateAddons(
								updateSOCompletionRequest.getAddOns()
										.getAddOn(), soId,userName);
						resetMobilePDFGenerationStatus(soId,false);
						
						// R12.0: Update the trip No details in the so_trip_details table in case of Addon update
						insertTripDetails(MPConstants.TRIP_ADDON, currentTripNo,currentTripId,createdBy,updateSOCompletionRequest);
				}
				// AddOnPaymentDetails update
					if (null != updateSOCompletionRequest
							.getAddOnPaymentDetails()
						&& StringUtils.isNotBlank(updateSOCompletionRequest
								.getAddOnPaymentDetails().getPaymentType())) {
						validationErrors = updateSOCompletionValidator.validatePreAuthAmt(updateSOCompletionRequest
								.getAddOnPaymentDetails(), soId);
						if(validationErrors.isEmpty()){
						updateSOCompletionRequest.getAddOnPaymentDetails()
								.setSoId(soId);
						boolean isTokenizeResponseValid = mobileSOActionsBO
								.mapPaymentDetails(updateSOCompletionRequest
										.getAddOnPaymentDetails(), updateSOCompletionRequest.gethSCreditCardResponse(),userName);
						if(!isTokenizeResponseValid){
							validationErrors = updateSOCompletionValidator.validateTokenizeResponse();
							results = new Results();
							results.setError(validationErrors);
							updateSOCompletionResponse.setResults(results);
							return updateSOCompletionResponse;
						}
						resetMobilePDFGenerationStatus(soId,false);
						
						//R12.0: Add(should we?) code to enter trip details in so_trip_detail table in case of pricing change.
						insertTripDetails(MPConstants.TRIP_ADDON_PAYMENT, currentTripNo,currentTripId,createdBy,updateSOCompletionRequest);
						}
						else{
							results = new Results();
							results.setError(validationErrors);
							updateSOCompletionResponse.setResults(results);
							return updateSOCompletionResponse;
						}
						
				}
				
				// Custom reference update
					if (updateSOCompletionRequest.getReferences() != null
							&& updateSOCompletionRequest.getReferences()
									.getReference() != null
							&& !updateSOCompletionRequest.getReferences()
									.getReference().isEmpty()) {
						List<CustomReferenceVO> customReferenceVOs = updateSoCompletionMapper
								.mapCustomReferences(updateSOCompletionRequest
										.getReferences(), soId, userName);
						if(customReferenceVOs!=null && !customReferenceVOs.isEmpty()){
							mobileSOActionsBO.updateBuyerReferences(
									customReferenceVOs, soId);
							resetMobilePDFGenerationStatus(soId,false);
						}
						
					 //R12.0: Update the trip No details in the so_trip_details table in case of Custom reference update
					insertTripDetails(MPConstants.TRIP_REFERENCE, currentTripNo,currentTripId,createdBy,updateSOCompletionRequest);
				}
				// Parts tracking update
					if (null != updateSOCompletionRequest.getPartsTracking()
							&& null != updateSOCompletionRequest
									.getPartsTracking().getPart()
							&& !updateSOCompletionRequest.getPartsTracking()
									.getPart().isEmpty()) {
						mobileSOActionsBO.updatePartsDetails(
								updateSOCompletionRequest, soId);
						
						
					// R12.0: Update the trip No details in the so_trip_details table in case of Parts update
					insertTripDetails(MPConstants.TRIP_PARTS, currentTripNo,currentTripId,createdBy,updateSOCompletionRequest);
				}
									
				updateSOCompletionResponse
				.setResults(Results
						.getSuccess(ResultsCode.UPDATE_SO_COMPLETION_DATA_UPDATE_SUCCESS
								.getMessage()));
				updateSOCompletionResponse.setSoId(soId);
				
				long endWork = System.currentTimeMillis();
				logger.info("Time taken for completing the work Started in ms ::"+(endWork-startWork));	
				return updateSOCompletionResponse;
				} catch (BusinessServiceException e) {
					logger.error("Exception thrown in execute of UpdateSOCompletionService Data Update"
							+ e.getMessage());
					updateSOCompletionResponse
							.setResults(Results
									.getError(
											ResultsCode.UPDATE_SO_COMPLETION_DATA_UPDATE_FAILURE
													.getMessage(),
											ResultsCode.UPDATE_SO_COMPLETION_DATA_UPDATE_FAILURE
													.getCode()));
					return updateSOCompletionResponse;
				}
				
			}
		
			else if (updateSOCompletionRequest.getCompletionStatus()
					.equalsIgnoreCase(MPConstants.SERVICE_COMPLETE)
					&& updateSOCompletionRequest.getUpdateAction()
							.equalsIgnoreCase(MPConstants.PENDING_CLAIM)) {
				
				long pendClaimStart = System.currentTimeMillis();
				com.servicelive.orderfulfillment.domain.ServiceOrder serviceOrder = ofHelper
						.getServiceOrder(soId);
				
				// SPM-1345 : Update the pending claim sub status before the validation 
				
				//R12.0 SLM-5 : passing buyer id and wf state id for NPS Notification in update so for PENDING_CLAIM 
				mobileSOActionsBO.updateSoCompletion(soId,
						MPConstants.PENDING_CLAIM_SUBSTATUS_ID, null, buyerId, wfStateId);
				
				// Senting Notification for Relay Services
				relayServicesNotifyFlag=relayNotificationService.isRelayServicesNotificationNeeded(buyerId,soId);
				
				if(relayServicesNotifyFlag){
					relayNotificationService.sentNotificationRelayServices(MPConstants.ACTIVE_PENDING_CLAIM_API_EVENT,soId);
				}
				
				//Logging history
				historyLogging(roleId,soId,userName,entityId,MPConstants.PENDING_CLAIM,createdBy);
		
				boolean pendingClaimInd = true;
				String invoicePartsInd = null;
				if(null!=serviceOrder){
				if(null != serviceOrder.getSOWorkflowControls()){
						invoicePartsInd = serviceOrder.getSOWorkflowControls().getInvoicePartsInd();
					}
					validationErrors = updateSOCompletionValidator
							.preValidateBeforeCompeltion(serviceOrder,
									pendingClaimInd,invoicePartsInd,soId,buyerIdValue);
				}

				if(!validationErrors.isEmpty()){
					
					// SPM-1345 : Create history with the details of error
					// Extract the error messages
					
					String errorMessage = MPConstants.SO_COMPLETION_ERROR;
					for(int count = 0; count < validationErrors.size(); count++){
						errorMessage = errorMessage+validationErrors.get(count).getMessage();
					}
					
					errorHistoryLogging(roleId,soId,userName,entityId,errorMessage,createdBy);
					
					results = new Results();
					results.setError(validationErrors);
					updateSOCompletionResponse.setResults(results);
					return updateSOCompletionResponse;
				}
				

				List<Result> sucesses = new ArrayList<Result>();
				sucesses = addSuccess(sucesses,
						ResultsCode.UPDATE_SO_COMPLETION_PENDING_CLAIM
								.getCode(),
						ResultsCode.UPDATE_SO_COMPLETION_PENDING_CLAIM
								.getMessage());
				results = new Results();
				results.setResult(sucesses);
				updateSOCompletionResponse.setSoId(soId);
				updateSOCompletionResponse.setResults(results);	
				
				long pendClaimEnd = System.currentTimeMillis();
				logger.info("Time taken for completing the pending Claim in ms ::"+(pendClaimEnd-pendClaimStart));
				
			}
			//COMPLETE AND COMPLETED
			else if (updateSOCompletionRequest.getCompletionStatus()
					.equalsIgnoreCase(MPConstants.SERVICE_COMPLETE)
					&& updateSOCompletionRequest.getUpdateAction()
							.equalsIgnoreCase(MPConstants.SERVICE_COMPLETED)) {
				
				long completeStart = System.currentTimeMillis();
				// Pricing update
				if (null != updateSOCompletionRequest.getPricing()) {
					updateSOCompletionRequest.getPricing().setSoId(soId);

						mobileSOActionsBO
								.updatePrisingDetails(updateSOCompletionRequest
										.getPricing());

					//update labour/part price change reasons
						List<SOPartLaborPriceReasonVO> reasonVOs = updateSoCompletionMapper
								.mapLaborPartPriceChangeReasons(
										updateSOCompletionRequest.getPricing(),
										soId, userName);
					if(reasonVOs!=null && !reasonVOs.isEmpty()){
							mobileSOActionsBO
									.updateLabourPartPriceReasons(reasonVOs);
					}
					
					
					 //R12.0: Update the trip No details in the so_trip_details table in case of Pricing update
					insertTripDetails(MPConstants.TRIP_PAYMENT, currentTripNo,currentTripId,createdBy,updateSOCompletionRequest);
				}
				

				com.servicelive.orderfulfillment.domain.ServiceOrder serviceOrder = ofHelper.getServiceOrder(soId);
				
				boolean isautocloseOn=false;
				isautocloseOn=buyerFeatureSetBO.validateFeature(buyerId, BuyerFeatureConstants.AUTO_CLOSE);
				boolean pendingClaimInd = false;
				String autoClose = "";
				parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_AUTOCLOSE,autoClose));
				// SL-20456 issue fix, get the noPartsReqd indicator from so_workflow_controls table instead of expecting the same from request
				String invoicePartsInd = null;
				if(null != serviceOrder.getSOWorkflowControls()){
					invoicePartsInd = serviceOrder.getSOWorkflowControls().getInvoicePartsInd();
				}
				validationErrors = updateSOCompletionValidator.preValidateBeforeCompeltion(serviceOrder,pendingClaimInd,invoicePartsInd,soId,buyerIdValue);
			
			
			
			if(!validationErrors.isEmpty()){
				results = new Results();
				results.setError(validationErrors);
				updateSOCompletionResponse.setResults(results);
				return updateSOCompletionResponse;
			}
			BigDecimal finalLaborIncludingPermits = new BigDecimal("0.00");
			Double permitFinalPrice=0.0;
			Double finalLaborPrice = 0.0;
			if (serviceOrder.getSOWorkflowControls().getFinalPriceLabor() != null ) {
				finalLaborIncludingPermits = serviceOrder.getSOWorkflowControls().getFinalPriceLabor();
			}
			if(serviceOrder.getTasks()!=null && !serviceOrder.getTasks().isEmpty()){
				// changes to fir prod issue considering deleted permit tasks for price calculation
				for(SOTask soTask :serviceOrder.getActiveTasks()){
					if(soTask!=null){
						if(soTask.getTaskType().intValue() == MPConstants.PERMIT_TASK.intValue() && null!= soTask.getSellingPrice() && null!= soTask.getFinalPrice()){
							if(soTask.getSellingPrice().doubleValue()>soTask.getFinalPrice().doubleValue()){
								permitFinalPrice = permitFinalPrice + soTask.getFinalPrice().doubleValue();
							}else if(soTask.getSellingPrice().doubleValue()<=soTask.getFinalPrice().doubleValue()){
								permitFinalPrice = permitFinalPrice + soTask.getSellingPrice().doubleValue();
							}
						}
						
					}
				}
			}
			
			finalLaborPrice = permitFinalPrice+finalLaborIncludingPermits.doubleValue();
			finalLaborIncludingPermits = MoneyUtil.getRoundedMoneyBigDecimal(finalLaborPrice);
			serviceOrder.setFinalPriceLabor(finalLaborIncludingPermits);
			if(serviceOrder.getSpendLimitParts()!=null){
				serviceOrder.setFinalPriceParts(serviceOrder.getSOWorkflowControls().getFinalPriceParts());
			}
			if(null!=serviceOrder.getPriceType() && serviceOrder.getPriceType().equals(MPConstants.TASK_LEVEL)){
				logger.info("PRICE TYPE:"+serviceOrder.getPriceType());
				if (serviceOrder.getSOWorkflowControls().getFinalPriceLabor() != null ) {
					serviceOrder.setSoMaxLabor(serviceOrder.getSOWorkflowControls().getFinalPriceLabor());
					//double initialMaxLabor = serviceOrder.getSpendLimitLabor().doubleValue();
					double totalFinalPriceOfTasks=0.0;
					int nonPermitTaskCount=0;
					double taskAmount=0;
					for (com.servicelive.orderfulfillment.domain.SOTask task : serviceOrder.getActiveTasks()) {

						//Task except Permit & Delivery
						if((MPConstants.PRIMARY_TASK.equals(task.getTaskType()) || MPConstants.NON_PRIMARY_TASK.equals(task.getTaskType())) && task.getFinalPrice()!=null){
							nonPermitTaskCount++;
							totalFinalPriceOfTasks=totalFinalPriceOfTasks + task.getFinalPrice().doubleValue();
						}
					}
					if(totalFinalPriceOfTasks==0 && nonPermitTaskCount!=0){
						taskAmount = serviceOrder.getSOWorkflowControls().getFinalPriceLabor().doubleValue()/nonPermitTaskCount;

					}

					for (com.servicelive.orderfulfillment.domain.SOTask task : serviceOrder.getActiveTasks()) {
						if(null!=task.getTaskHistory()){
							task.setTaskHistory(task.getTaskHistory());
						}
						if(!MPConstants.PERMIT_TASK.equals(task.getTaskType())){
							if(serviceOrder.getSOWorkflowControls().getFinalPriceLabor().doubleValue()!=totalFinalPriceOfTasks){
								double amount=0;
								if(totalFinalPriceOfTasks==0 && !(MPConstants.DELIVERY_TASK.equals(task.getTaskType()))){
									task.setFinalPrice(BigDecimal.valueOf(taskAmount));
								}

								//divide new somaxlabor(the total price for tasks alone excluding permit task) among tasks.
								if(null!=task.getFinalPrice() && null!=serviceOrder.getSOWorkflowControls().getFinalPriceLabor() && totalFinalPriceOfTasks !=0){
									double percentageShare=task.getFinalPrice().doubleValue()/totalFinalPriceOfTasks*100;
									amount =  serviceOrder.getSOWorkflowControls().getFinalPriceLabor().doubleValue()* percentageShare /100;
									task.setFinalPrice(BigDecimal.valueOf(amount));
								}
							} 
						}

					}


				}
			}else if (serviceOrder.getSOWorkflowControls().getFinalPriceLabor() != null ) {
        	serviceOrder.setSoMaxLabor(serviceOrder.getSOWorkflowControls().getFinalPriceLabor());
        }
        
			long startOF = System.currentTimeMillis();
			updateSOCompletionRequest = mobileSOActionsBO.preProcessUpdateSOCompletion(updateSOCompletionRequest);
		
	        assignProviderState(serviceOrder,new Integer(serviceOrder.getAcceptedProviderResourceId().intValue()),parameters);
	        
	       	if(null!=securityContext && null!=securityContext.getRoles()){
			parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_CREATED_BY,securityContext.getRoles().getLastName() + ", " + securityContext.getRoles().getFirstName()));
			parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_MODIFIED_BY,securityContext.getRoles().getUsername()));
			 if(null!=securityContext.getRoles().getRoleId()){
			 parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_ROLE_ID,securityContext.getRoles().getRoleId().toString()));
			 }
	       	}
	       	if(null!=securityContext && null!=securityContext.getVendBuyerResId()){
			parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_RESOURCE_ID,securityContext.getVendBuyerResId().toString()));
	       	}
	       	//SL-21927
			if(null != updateSOCompletionRequest.getPricing()){
				
				String laborTax = updateSOCompletionRequest.getPricing().getLaborTax();
				String partsTax = updateSOCompletionRequest.getPricing().getPartsTax();
				String laborDiscount = updateSOCompletionRequest.getPricing().getLaborDiscount();
				String partsDiscount = updateSOCompletionRequest.getPricing().getPartsDiscount();
				
				serviceOrder.getPrice().setTaxPercentLaborSL(StringUtils.isEmpty(laborTax) ? PricingUtil.ZERO : new BigDecimal(laborTax) );
				serviceOrder.getPrice().setTaxPercentPartsSL(StringUtils.isEmpty(partsTax) ? PricingUtil.ZERO : new BigDecimal(partsTax) );
				serviceOrder.getPrice().setDiscountPercentLaborSL(StringUtils.isEmpty(laborDiscount) ? PricingUtil.ZERO : new BigDecimal(laborDiscount) );
				serviceOrder.getPrice().setDiscountPercentPartsSL(StringUtils.isEmpty(partsDiscount) ? PricingUtil.ZERO : new BigDecimal(partsDiscount) );
				
			}
			
	        OrderFulfillmentResponse ordrFlflResponse = ofHelper.runOrderFulfillmentProcess(
					serviceOrder.getSoId(),
					SignalType.COMPLETE_ORDER,
					serviceOrder,
					updateSoCompletionMapper.createOFIdentityFromSecurityContext(getSecurityContextForVendor(apiVO.getProviderResourceId())),parameters);
	        

	        
			ServiceOrder so = mobileSOActionsBO.getServiceOrder(soId);
			if(so.getWfStateId().intValue()==ORDER_CLOSED)
			{
				updateSOCompletionResponse=createUpdateSOCompletionResponse(ordrFlflResponse, ResultsCode.AUTOCLOSE_SO_SUCCESS, ResultsCode.COMPLETESO_FAILURE, serviceOrder.getSoId());
			}
			
			else if(so.getWfStateId().intValue()==ORDER_CANCELELD)
			{
				updateSOCompletionResponse=createUpdateSOCompletionResponse(ordrFlflResponse, ResultsCode.CANCELLED_SO_SUCCESS, ResultsCode.COMPLETESO_FAILURE, serviceOrder.getSoId());
			}
			else if(isautocloseOn)
			{
				//SL-20926
				updateSOCompletionResponse = createRespForAddonIssues(ordrFlflResponse, serviceOrder.getSoId());
				if(null != updateSOCompletionResponse){
					return updateSOCompletionResponse; 
				}
				updateSOCompletionResponse=createUpdateSOCompletionResponse(ordrFlflResponse, ResultsCode.COMPLETESO_SUCCESS, ResultsCode.COMPLETESO_FAILURE, serviceOrder.getSoId());
			}
			else
			{
				//SL-20926
				updateSOCompletionResponse = createRespForAddonIssues(ordrFlflResponse, serviceOrder.getSoId());
				if(null != updateSOCompletionResponse){
					return updateSOCompletionResponse; 
				}
				updateSOCompletionResponse=createUpdateSOCompletionResponse(ordrFlflResponse, ResultsCode.COMPLETESO_SUCCESS, ResultsCode.COMPLETESO_FAILURE, serviceOrder.getSoId());
			}

			long endOF = System.currentTimeMillis();
			logger.info("Time Taken for OF Transition in ms ::"+(endOF-startOF));	
			
			long completeEnd = System.currentTimeMillis();
			logger.info("Time Taken for Completion in ms ::"+ (completeEnd-completeStart));
			}
			
			else{
				validationErrors = addError(validationErrors,
						ResultsCode.UPDATE_SO_COMPLETION_INCORRECT_REQUEST
								.getCode(),
						ResultsCode.UPDATE_SO_COMPLETION_INCORRECT_REQUEST
								.getMessage());
				results = new Results();
				results.setError(validationErrors);
				updateSOCompletionResponse.setResults(results);
			}
			return updateSOCompletionResponse; 

		} catch (BusinessServiceException e) {
			logger.info("Exception thrown in execute of UpdateSOCompletionService"
					+ e.getMessage());
			updateSOCompletionResponse.setResults(Results.getError(
					ResultsCode.UPDATE_SO_COMPLETION_FAILURE.getMessage(),
					ResultsCode.UPDATE_SO_COMPLETION_FAILURE.getCode()));
		}
		
		long endTime = System.currentTimeMillis();
		logger.info("Time taken for completion execute method in ms :::"+(endTime-startTime));
		return updateSOCompletionResponse;
	}

	public IDocumentBO getDocumentBO() {
		return documentBO;
	}

	public void setDocumentBO(IDocumentBO documentBO) {
		this.documentBO = documentBO;
	}

	private void historyLogging(Integer roleId, String soId, String userName,
			Integer entityId,String desc,String createdBy) {
		// Auto-generated method stub
		try{
			Date d=new Date(System.currentTimeMillis());
			Timestamp date = new Timestamp(d.getTime());
			ProviderHistoryVO hisVO=new ProviderHistoryVO();
			hisVO.setSoId(soId);
			hisVO.setActionId(MPConstants.SUBSTATUS_ACTION_ID);
			hisVO.setDescription(MPConstants.SUBSTATUS_DESCRIPTION+" "+desc);
			hisVO.setCreatedDate(date);
			hisVO.setModifiedDate(date);
			hisVO.setCreatedBy(createdBy);
			hisVO.setRoleId(roleId);
			hisVO.setModifiedBy(userName);
			hisVO.setEnitityId(entityId);
			mobileSOActionsBO.historyLogging(hisVO);
		}
		catch (Exception ex) {
			logger.error("Exception Occured in UpdateSOCompletionService-->historyLogging()");

		}
	}
	
	private void errorHistoryLogging(Integer roleId, String soId, String userName,
			Integer entityId,String desc,String createdBy) {
		// Auto-generated method stub
		try{
			Date d=new Date(System.currentTimeMillis());
			Timestamp date = new Timestamp(d.getTime());
			ProviderHistoryVO hisVO=new ProviderHistoryVO();
			hisVO.setSoId(soId);
			hisVO.setActionId(MPConstants.COMPLETION_ACTION_ID);
			hisVO.setDescription(desc);
			hisVO.setCreatedDate(date);
			hisVO.setModifiedDate(date);
			hisVO.setCreatedBy(createdBy);
			hisVO.setRoleId(roleId);
			hisVO.setModifiedBy(userName);
			hisVO.setEnitityId(entityId);
			mobileSOActionsBO.historyLogging(hisVO);
		}
		catch (Exception ex) {
			logger.error("Exception Occured in UpdateSOCompletionService-->errorHistoryLogging()");

		}
	}

	private UpdateSOCompletionResponse createUpdateSOCompletionResponse(
			OrderFulfillmentResponse ordrFlflResponse, ResultsCode onSuccess,
			ResultsCode onFailure, String soId) {
		UpdateSOCompletionResponse completionResponse = new UpdateSOCompletionResponse();
		List<ErrorResult> validationErrors = new ArrayList<ErrorResult>();
		List<Result> successes = new ArrayList<Result>();
		completionResponse.setSoId(soId);
		if (ordrFlflResponse.isError()) {
			validationErrors = addError(validationErrors, onFailure.getCode(),
					onFailure.getMessage());
			Results results = new Results();
			results.setError(validationErrors);
			completionResponse.setResults(results);
		} else {
			successes = addSuccess(successes, onSuccess.getCode(),
					onSuccess.getMessage());
			Results results = new Results();
			results.setResult(successes);
			completionResponse.setResults(results);
		}
		return completionResponse;
	}

	private List<ErrorResult> addError(List<ErrorResult> validationErrors,
			String code, String message) {
		ErrorResult result = new ErrorResult();
		result.setCode(code);
		result.setMessage(message);
		validationErrors.add(result);
		return validationErrors;
	}

	private List<Result> addSuccess(List<Result> sucesses, String code,
			String message) {
		Result result = new Result();
		result.setCode(code);
		result.setMessage(message);
		sucesses.add(result);
		return sucesses;
	}

	/**
	 * @param soId
	 * tp reset the pdf generation status as data is updated
	 * @param boolean fromSignatureUpdateInd
	 */
	private void resetMobilePDFGenerationStatus(String soId, boolean fromSignatureUpdateInd){

		try{
			// prepare for regenerating SO signature PDF
			boolean exists = documentBO.checkIfMobileSignatureDocumentExists(soId);
			if(exists){
				logger.info("check if PDF has to be regenerated.");
				Integer documentId = documentBO.checkIfSignaturePDFDocumentExisits(soId);
				if(null != documentId){
					logger.info("PDF has to be regenerated.");
					logger.debug("Remove document from so_document");
					documentBO.deleteSODocumentMapping(soId, documentId);
					
					logger.debug("Remove document from document");
					documentBO.deleteResourceDocument(documentId);
					
					if(!fromSignatureUpdateInd){
						logger.debug("Update mobile so signature");
						documentBO.updatePDFBatchParamaters(soId);
					}
				}
			}
		} catch (BusinessServiceException e){
			logger.error("Error while preparing for regenerating PDF : " + e);
		} catch (DataServiceException e) {
			logger.error("Error while preparing for regenerating PDF : " + e);
		}
	
	}
	

    
	/**
	 * @param serviceOrder
	 * @param providerId
	 * @param parameters
	 */
	private void assignProviderState(
			com.servicelive.orderfulfillment.domain.ServiceOrder serviceOrder,
			Integer providerId, List<Parameter> parameters) {
	  Contact vendorContact =null;
		
		if(null != providerId){
				Long resourceId = new Long(providerId.longValue());
				serviceOrder.setAssignmentType("PROVIDER");
				serviceOrder.setAcceptedProviderResourceId(resourceId);
			  try {
				vendorContact = mobileSOActionsBO
						.getVendorResourceContact(providerId.intValue());
	          } catch (BusinessServiceException bse) {
	        	  logger.error("Error in assign provider"+ bse.getMessage());
	          }
			SOContact contact = updateSoCompletionMapper
					.mapContact(vendorContact);
	          serviceOrder.addContact(contact);
			SOLocation soLocation = updateSoCompletionMapper
					.mapLocation(vendorContact);
	          serviceOrder.addLocation(soLocation);
			parameters.add(new Parameter(
					OrderfulfillmentConstants.PVKEY_ACCEPTED_PROVIDER_STATE,
					vendorContact.getStateCd()));
		}
	}
	
	
	/**
	 * This method is for saving trip details in case of a completion update.
	 * 
	 * @param changeType:{ADD_ON,PARTS,INVOICE_PARTS,PAYMENT,ADDON_PAYMENT,REFERENCE,RESOLUTION_COMMENT,SIGNATURE,PERMIT_TASK,TASK,PERMIT_ADDON}
	 * @param currentTripNo
	 * @param currentTripId
	 * @param createdBy
	 * @param request: updateSOCompletionRequest
	 */
	private void insertTripDetails(String changeType, Integer currentTripNo,
			Integer currentTripId, String createdBy,
			UpdateSOCompletionRequest request) {

		Integer tripDetailsId = 0;
		String changeComment = "";
		try {
			if (0 != currentTripNo && 0 != currentTripId) {
				if (changeType.equals(MPConstants.TRIP_ADDON)) {
					changeComment = MPConstants.UPDATED + " addons:";
					for (AddOn addon : request.getAddOns().getAddOn()) {
						// comma separated part names as trip comment
						if (null != addon.getSoAddonId()) {
							changeComment = changeComment.concat(addon.getSoAddonId() + ",");
						}

					}
					if (request.getAddOns().getAddOn().size() > 0) {
						// remove the last ','
						changeComment = changeComment.substring(0,
								changeComment.length() - 1);
					}

				} else if (changeType.equals(MPConstants.TRIP_PARTS)) {

					changeComment = MPConstants.UPDATED + " parts:";
					for (PartTracking part : request.getPartsTracking()
							.getPart()) {
						// comma separated part names as trip comment
						if (null != part.getPartName()) {
							changeComment = changeComment.concat(part
									.getPartName() + ",");
						}

					}
					if (request.getPartsTracking().getPart().size() > 0) {
						// remove the last ','
						changeComment = changeComment.substring(0,
								changeComment.length() - 1);
					}

				} else if (changeType.equals(MPConstants.TRIP_PAYMENT)) {
					changeComment = "Price Updated";

				} else if (changeType.equals(MPConstants.TRIP_REFERENCE)) {
					changeComment = MPConstants.UPDATED + " custom references:";
					for (Reference ref : request.getReferences().getReference()) {
						// comma separated part names as trip comment
						if (null != ref.getReferenceName()) {
							changeComment = changeComment.concat(ref
									.getReferenceName() + ",");
						}

					}
					if (request.getReferences().getReference().size() > 0) {
						// remove the last ','
						changeComment = changeComment.substring(0,
								changeComment.length() - 1);
					}

				} else if (changeType
						.equals(MPConstants.TRIP_RESOLUTIONCOMMENT)) {
					changeComment = MPConstants.ADDED + " resolution comment";

				} else if (changeType.equals(MPConstants.TRIP_SIGNATURE)) {
					if (null != request.getProviderSignature()) {
						changeComment = MPConstants.ADDED+ " " + MPConstants.PROVIDER
								+ " signature";
						tripDetailsId = mobileSOActionsBO.addTripDetails(currentTripId,
								changeType, changeComment, createdBy);
						
					} if (null != request.getCustomerSignature()) {
						changeComment = MPConstants.ADDED+ " " + MPConstants.CUSTOMER
								+ " signature";
						tripDetailsId = mobileSOActionsBO.addTripDetails(currentTripId,
								changeType, changeComment, createdBy);
					
					}
					
					return;

				} else if (changeType.equals(MPConstants.TRIP_ADDON_PAYMENT)) {
					changeComment = "Addon price updated";
				} else if (changeType.equals(MPConstants.TRIP_TASK)) {

					changeComment = MPConstants.UPDATED + " tasks:";
					for (Task task : request.getTasks().getTask()) {
						// comma separated taskids as trip comment
						if (null != task.getTaskId()) {
							changeComment = changeComment.concat(task
									.getTaskId() + ",");
						}

					}
					if (request.getTasks().getTask().size() > 0) {
						// remove the last ','
						changeComment = changeComment.substring(0,
								changeComment.length() - 1);
					}

				} else if (changeType.equals(MPConstants.TRIP_PERMIT_ADDON)) {
					changeComment = MPConstants.UPDATED + " permit addons:";
					for (PermitAddon addon : request.getPermits()
							.getPermitAddons().getPermitAddon()) {
						// comma separated addon ids as trip comment
						if (null != addon.getSoAddonId()) {
							changeComment = changeComment.concat(addon
									.getSoAddonId() + ",");
						}

					}
					if (request.getPermits().getPermitAddons().getPermitAddon()
							.size() > 0) {
						// remove the last ','
						changeComment = changeComment.substring(0,
								changeComment.length() - 1);
					}
				} else if (changeType.equals(MPConstants.TRIP_PERMIT_TASK)) {
					changeComment = MPConstants.UPDATED + " permit tasks:";
					for (PermitTask pt : request.getPermits().getPermitTasks()
							.getPermitTask()) {
						// comma separated addon ids as trip comment
						if (null != pt.getTaskId()) {
							changeComment = changeComment.concat(pt
									.getTaskId() + ",");
						}

					}
					if (request.getPermits().getPermitTasks().getPermitTask()
							.size() > 0) {
						// remove the last ','
						changeComment = changeComment.substring(0,
								changeComment.length() - 1);
					}
				}
			}

			// Save trip details to DB
			tripDetailsId = mobileSOActionsBO.addTripDetails(currentTripId,
					changeType, changeComment, createdBy);
			logger.info(changeType + "::tripDetailsId::" + tripDetailsId);
		} catch (BusinessServiceException e) {
			logger.error("Error while inserting trip details : " + e);
		} catch (Exception e) {
			logger.error("Error while inserting trip details : " + e);
		}

	}
	
	/**
	 * SL-20926 changes
	 * Create error response if addon issues exists
	 * @return ProcessResponse
	 */
	private UpdateSOCompletionResponse createRespForAddonIssues(OrderFulfillmentResponse ordrFlflResponse, String soId) {
		
		UpdateSOCompletionResponse completionResponse = null;
        if (null != ordrFlflResponse && ordrFlflResponse.isError() && 
        		OrderConstants.ADDON_COMPLETE_ERROR.equalsIgnoreCase(ordrFlflResponse.getErrors().get(0))) {
        		
        	completionResponse = new UpdateSOCompletionResponse();
        	List<ErrorResult> validationErrors = new ArrayList<ErrorResult>();
        	completionResponse.setSoId(soId);
        	validationErrors = addError(validationErrors, ResultsCode.FAILURE.getCode(), OrderConstants.ADDON_COMPLETE_ERROR);
    		Results results = new Results();
    		results.setError(validationErrors);
    		completionResponse.setResults(results);
        } 
        return completionResponse;
	}

	public IRelayServiceNotification getRelayNotificationService() {
		return relayNotificationService;
	}

	public void setRelayNotificationService(
			IRelayServiceNotification relayNotificationService) {
		this.relayNotificationService = relayNotificationService;
	}
	
	
	
}


