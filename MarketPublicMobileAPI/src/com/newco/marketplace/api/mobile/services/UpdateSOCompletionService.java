package com.newco.marketplace.api.mobile.services;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.ErrorResult;
import com.newco.marketplace.api.beans.Result;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.UpdateSOCompletionRequest;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.UpdateSOCompletionResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.api.utils.mappers.UpdateSOCompletionMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerFeatureSetBO;
import com.newco.marketplace.business.iBusiness.document.IDocumentBO;
import com.newco.marketplace.business.iBusiness.mobile.IMobileSOActionsBO;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.BuyerFeatureConstants;
import com.newco.marketplace.mobile.api.utils.validators.UpdateSOCompletionValidator;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.utils.MoneyUtil;
import com.newco.marketplace.vo.login.LoginCredentialVO;
import com.newco.marketplace.vo.mobile.CustomReferenceVO;
import com.newco.marketplace.vo.mobile.ProviderHistoryVO;
import com.newco.marketplace.vo.mobile.SOPartLaborPriceReasonVO;
import com.servicelive.orderfulfillment.client.OFHelper;
import com.servicelive.orderfulfillment.domain.SOContact;
import com.servicelive.orderfulfillment.domain.SOLocation;
import com.servicelive.orderfulfillment.domain.SOTask;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.Parameter;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;

/**
 * This class would act as a Servicer class for Provider so Completion
 * 
 * @author Infosys
 * @version 1.0
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
	public static final int ORDER_CLOSED = 180;
	public static final int ORDER_CANCELELD = 120;
	public static final int ORDER_PENDINGCANCEL = 165;
	public static final int ORDER_PENDINGREVIEW = 68;
	public static final int ORDER_PENDINGESPONSE = 69;
	private OFHelper ofHelper = new OFHelper();

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
			updateSOCompletionValidator.setProviderId(apiVO
					.getProviderResourceId());
			if(securityContext!=null){
				updateSOCompletionValidator.setFirmId(securityContext
						.getCompanyId());
				userName = securityContext.getUsername();
				entityId = securityContext.getVendBuyerResId();
				roleId = securityContext.getRoleId();
				LoginCredentialVO lvRoles = securityContext.getRoles();
				createdBy = lvRoles.getFirstName() +" "+lvRoles.getLastName();
			}
			updateSOCompletionResponse.setSoId(soId);
			updateSOCompletionValidator.setSoId(soId);
			updateSOCompletionValidator.setAddonSum(0.00);
			updateSOCompletionValidator
					.setValidationErrors(new ArrayList<ErrorResult>());
      
			//R12.0 SLM-5 : call for retrieving buyer id and wf state id of service order
			
			HashMap<String, Object> map = mobileSOActionsBO.getBuyerAndWfStateForSO(soId);
			buyerId =(int)(long)(Long)map.get("buyerId");
			wfStateId=(int)(long)(Long)map.get("wfStateId");
			
			updateSOCompletionValidator.setBuyerId(buyerId.toString());
			List<ErrorResult> validationErrors = updateSOCompletionValidator
					.validate(updateSOCompletionRequest);
			if(!validationErrors.isEmpty()){
				results = new Results();
				results.setError(validationErrors);
				updateSOCompletionResponse.setResults(results);
				return updateSOCompletionResponse;
			}
			String resolutionComments = updateSOCompletionRequest
					.getResolutionComments();
			//(PARTIAL & (WORK NOT STARTED||WORK STARTED))
			if (updateSOCompletionRequest.getCompletionStatus().equals(
					MPConstants.SERVICE_PARTIAL)
					&& (MPConstants.WORK_NOT_STARTED
							.equals(updateSOCompletionRequest.getUpdateAction()) || MPConstants.WORK_STARTED
							.equals(updateSOCompletionRequest.getUpdateAction()))) {
				long startPartial = System.currentTimeMillis();		
				
				//R12.0 SLM-5 : passing buyer id and wf state id for NPS Notification in update so for REVISIT_NEEDED
			
				mobileSOActionsBO.updateSoCompletion(soId,
						MPConstants.PARTIAL_SUBSTATUS_ID, resolutionComments, buyerId, wfStateId);
				//Logging history
				historyLogging(roleId,soId,userName,entityId,MPConstants.REVISIT_NEEDED,createdBy);
				
				List<Result> sucesses = new ArrayList<Result>();
				sucesses = addSuccess(sucesses,
						ResultsCode.UPDATE_SO_COMPLETION_PARTIAL.getCode(),
						ResultsCode.UPDATE_SO_COMPLETION_PARTIAL.getMessage());
				results = new Results();
				results.setResult(sucesses);
				updateSOCompletionResponse.setSoId(soId);
				updateSOCompletionResponse.setResults(results);
				
				long endPartial = System.currentTimeMillis();
				logger.info("Time taken for the partial completion in ms ::"+(endPartial-startPartial));
			} else if (updateSOCompletionRequest.getCompletionStatus().equals(
					MPConstants.CANCEL)
					&& (MPConstants.WORK_NOT_STARTED
							.equals(updateSOCompletionRequest.getUpdateAction()) || MPConstants.WORK_STARTED
							.equals(updateSOCompletionRequest.getUpdateAction()))) {//CANCEL & WORK NOT STARTED
				
				long startCancel = System.currentTimeMillis();
				
				//R12.0 SLM-5 : passing buyer id and wf state id for NPS Notification in update so for CANCELLATION_REQUESTED 
				mobileSOActionsBO.updateSoCompletion(soId,
						MPConstants.CANCEL_SUBSTATUS_ID, resolutionComments, buyerId, wfStateId);
				
				//Logging history
				historyLogging(roleId,soId,userName,entityId,MPConstants.CANCELLATION_REQUESTED,createdBy);
				
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
				}*/
				
				// resolution comments update
				if(StringUtils.isNotBlank(resolutionComments)){
						mobileSOActionsBO.updateResolutionComments(
								updateSOCompletionRequest
										.getResolutionComments(), soId);
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
					}
					//update permit tasks
					if(null != updateSOCompletionRequest.getPermits().getPermitTasks()
							&& updateSOCompletionRequest.getPermits().getPermitTasks().getPermitTask()!=null 
							&& !updateSOCompletionRequest.getPermits().getPermitTasks().getPermitTask().isEmpty()){
						mobileSOActionsBO.updatePermitTasks(
								updateSOCompletionRequest.getPermits().getPermitTasks(), soId);
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
						mobileSOActionsBO
								.mapPaymentDetails(updateSOCompletionRequest
										.getAddOnPaymentDetails(), userName);
						resetMobilePDFGenerationStatus(soId,false);

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

				}
				// Parts tracking update
					if (null != updateSOCompletionRequest.getPartsTracking()
							&& null != updateSOCompletionRequest
									.getPartsTracking().getPart()
							&& !updateSOCompletionRequest.getPartsTracking()
									.getPart().isEmpty()) {
						mobileSOActionsBO.updatePartsDetails(
								updateSOCompletionRequest, soId);
				}
				
				// Parts Update only for HSR
					
					if(null != updateSOCompletionRequest.getParts() 
							&& null!= updateSOCompletionRequest.getParts().getDeleteAllInd() 
							&& updateSOCompletionRequest.getParts().getDeleteAllInd().equals("Y")){
						mobileSOActionsBO.deleteInvoicePartsDetails(soId);
						 //R12.0 updates parts indicator in so_workflow_controls for UpdateSoCompletion v1.0 flow
						 //On Delete invoice details, update the indicator to NO_PARTS_ADDED
						mobileSOActionsBO.updateInvoicePartsInd(soId,MPConstants.INDICATOR_NO_PARTS_ADDED);
					}
					else{
					if (null != updateSOCompletionRequest.getParts()
							&& null != updateSOCompletionRequest.getParts().getPart()
							&& !updateSOCompletionRequest.getParts().getPart().isEmpty() 
							&& buyerId.intValue() == Integer.parseInt(MPConstants.HSR_BUYER_ID) ){
						mobileSOActionsBO.insertInvoicePartsDetails(
								updateSOCompletionRequest, soId,userName);
						//R12.0 updates parts indicator in so_workflow_controls for UpdateSoCompletion v1.0 flow
						//On Parts added, update the indicator to PARTS_ADDED
						mobileSOActionsBO.updateInvoicePartsInd(soId,MPConstants.INDICATOR_PARTS_ADDED);
				}
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
				//Logging history
				historyLogging(roleId,soId,userName,entityId,MPConstants.PENDING_CLAIM,createdBy);
		
				boolean pendingClaimInd = true;
				if(null!=serviceOrder){
					validationErrors = updateSOCompletionValidator
							.preValidateBeforeCompeltion(serviceOrder,
									pendingClaimInd);
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
				}
				

				com.servicelive.orderfulfillment.domain.ServiceOrder serviceOrder = ofHelper.getServiceOrder(soId);
				
				boolean isautocloseOn=false;
				isautocloseOn=buyerFeatureSetBO.validateFeature(buyerId, BuyerFeatureConstants.AUTO_CLOSE);
				boolean pendingClaimInd = false;
				String autoClose = "";
				parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_AUTOCLOSE,autoClose));
			validationErrors = updateSOCompletionValidator.preValidateBeforeCompeltion(serviceOrder,pendingClaimInd);
			
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
				for(SOTask soTask :serviceOrder.getTasks()){
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
				updateSOCompletionResponse=createUpdateSOCompletionResponse(ordrFlflResponse, ResultsCode.AUTOCLOSE_COMPLETESO_SUCCESS, ResultsCode.COMPLETESO_FAILURE, serviceOrder.getSoId());
			}
			else
			{
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
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
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
}