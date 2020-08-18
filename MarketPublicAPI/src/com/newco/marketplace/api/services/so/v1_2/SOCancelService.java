package com.newco.marketplace.api.services.so.v1_2;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.cancel.v1_2.CancelSKU;
import com.newco.marketplace.api.beans.so.cancel.v1_2.SOCancelRequest;
import com.newco.marketplace.api.beans.so.cancel.v1_2.SOCancelResponse;
import com.newco.marketplace.api.beans.so.cancel.v1_2.SOStatusInfo;
import com.newco.marketplace.api.beans.so.cancel.v1_2.WorkOrderSKU;

import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.so.v1_1.SOBaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.so.v1_1.OFMapper;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerBO;
import com.newco.marketplace.business.iBusiness.financeManager.IFinanceManagerBO;
import com.newco.marketplace.business.iBusiness.reasonCode.IManageReasonCodeBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.ManageTaskVO;
import com.newco.marketplace.dto.vo.ajax.AjaxCacheVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.vo.buyer.WorkOrderVO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.service.ServiceConstants;
import com.servicelive.common.properties.IApplicationProperties;
import com.servicelive.orderfulfillment.domain.SOElementCollection;
import com.servicelive.orderfulfillment.domain.SOTask;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentRequest;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;

/**
 * This is the service class for executing the Cancel Service Order request.
 * 
 * @author Infosys
 * @version 1.2
 */
@APIRequestClass(SOCancelRequest.class)
@APIResponseClass(SOCancelResponse.class)
public class SOCancelService extends SOBaseService {
	private IBuyerBO buyerBO;
	// Private variables
	private static final Logger LOGGER = Logger.getLogger(SOCancelService.class);
	private IManageReasonCodeBO manageReasonCodeBO;
	private IApplicationProperties applicationProperties;
	private IFinanceManagerBO financeManagerBO;

	//reason code for the cancel reason provided in request xml.
	private int reasonCodeId;
	private IServiceOrderBO serviceOrderBO;
	//private SOCancelMapper soCancelMapper; 

	/** Public Constructor for SOCancelService
	 * @param xsd for request xml, <b> xsd for response xml,
	 * response xml namespace, resource Shema, schema location, request class,
	 * response class.	 * 
	 */
	public SOCancelService() {
		super(PublicAPIConstant.SO_CANCEL_XSD,
				PublicAPIConstant.SO_CANCEL_RESPONSE_XSD,
				PublicAPIConstant.SORESPONSE_NAMESPACE,
				PublicAPIConstant.SO_RESOURCES_SCHEMAS_V1_2,
				PublicAPIConstant.SO_RESOURCES_SCHEMAS_V1_2,
				SOCancelRequest.class, SOCancelResponse.class);
	}
	
	/**
	 * Creates a Cancel response when all validations are success and cancell
	 * signaal process is finished successfully.
	 * 
	 * @param ServiceOrder
	 * @return {@link SOCancelResponse}
	 * */
	private SOCancelResponse createCancelResponse(
			com.servicelive.orderfulfillment.domain.ServiceOrder serviceOrder) {
		SOCancelResponse soCancelResponse = new SOCancelResponse();
		setOrderStatusInCancelResponse(soCancelResponse, serviceOrder);
		//Results results = Results.getSuccess(ServiceConstants.SYSTEM_ERROR_RC);
		Results results = Results
				.getSuccess(PublicAPIConstant.cancelSO.SO_CANCELLATION_SUCCESS);
		if(OrderConstants.PENDING_CANCEL_STATUS == serviceOrder.getWfStateId().intValue()){
			//Success message for Pending Cancel State
			results = Results
					.getSuccess(PublicAPIConstant.cancelSO.SO_PENDING_CANCELLATION_SUCCESS);
		}
		soCancelResponse.setResults(results);
		return soCancelResponse;
	}

	/**
	 * Creates Error response for Cancellation. This is called when something
	 * went wrong during signal processing.
	 * @param ordrFlflmntResponse
	 *            {@link OrderFulfillmentResponse}
	 * @return soCancelResponse {@link SOCancelResponse}
	 * */
	private SOCancelResponse createErrorResponse(
			OrderFulfillmentResponse ordrFlflmntResponse) {
		SOCancelResponse soCancelResponse = new SOCancelResponse();
		Results results = Results.getError(
				ordrFlflmntResponse.getErrorMessage(),
				ServiceConstants.USER_ERROR_RC);
		soCancelResponse.setResults(results);
		return soCancelResponse;
	}

	/**
	 * Creates Error Response for Cancellation. This is called when validation
	 * of request attributes failed.
	 * 
	 * @param cancelResponse
	 *            : {@link ProcessResponse}
	 * @return SOCancelResponse with error message and code.
	 * */
	private SOCancelResponse createErrorResponse(ProcessResponse cancelResponse) {
		SOCancelResponse soCancelResponse = new SOCancelResponse();
		Results results = Results.getError(cancelResponse.getMessage(),
				ServiceConstants.USER_ERROR_RC);
		if (results == null) {
			results = Results.getError(cancelResponse.getMessage(),
					ServiceConstants.SYSTEM_ERROR_RC);
		}
		soCancelResponse.setResults(results);
		return soCancelResponse;
	}

	/**
	 * Create So Cancellation response when Signal is invalid.
	 * @return {@link SOCancelResponse}
	 * */
	private SOCancelResponse createInvalidStateResponse() {
		SOCancelResponse soCancelResponse = new SOCancelResponse();
		Results results = Results.getError(CommonUtility
				.getMessage(PublicAPIConstant.INVALID_STATE_ERROR_CODE),
				ResultsCode.FAILURE.getCode());
		soCancelResponse.setResults(results);
		return soCancelResponse;
	}

	/**
	 * Create OF Request for Cancellation. This method sets all required Misc
	 * parameters required for signal processing.
	 * 
	 * @param ServiceOrder
	 *            {@link ServiceOrder}, SecurityContext {@link SecurityContext}
	 *            and SOCancelRequest {@link SOCancelRequest}
	 * @return OrderFulfillmentRequest
	 ***/
	private OrderFulfillmentRequest createOrderFulfillmentCancelSORequest(
			ServiceOrder so, SecurityContext securityContext,
			SOCancelRequest soCancelRequest) {
		OrderFulfillmentRequest cancelOfrequest = new OrderFulfillmentRequest();
		NumberFormat formatter = new DecimalFormat(OrderConstants.PRICE_FORMAT_DB);
		Date date = (new GregorianCalendar()).getTime();
		DateFormat formatterDate = new SimpleDateFormat(OrderfulfillmentConstants.DATE_FORMAT);
		String cancelDate = formatterDate.format(date);
		cancelOfrequest.setIdentification(OFMapper.createBuyerIdentFromSecCtx(securityContext));
		cancelOfrequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_CANCELLATION_API_CODE,
				OrderfulfillmentConstants.CANCELLATION_API);
		cancelOfrequest = setMiscParameters(OrderfulfillmentConstants.PVKEY_CANCELLATION_REASON_CODE,
					Integer.toString(this.reasonCodeId), cancelOfrequest);
		cancelOfrequest = setMiscParameters(OrderfulfillmentConstants.PVKEY_CANCELLATION_REASON,
				soCancelRequest.getCancelReasonCode(), cancelOfrequest);
		cancelOfrequest = setMiscParameters(OrderfulfillmentConstants.PVKEY_CANCELLATION_COMMENT,
				soCancelRequest.getCancelComment(), cancelOfrequest);
		cancelOfrequest = setMiscParameters(OrderfulfillmentConstants.PVKEY_RQSTD_ACTIVE_CANCELLATION_AMT,
				formatter.format(soCancelRequest.getCancelAmount()),
				cancelOfrequest);

		cancelOfrequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_PENDING_CANCEL_STATUS, 
				PublicAPIConstant.cancelSO.PENDING_CANCEL_STATUS);
		cancelOfrequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_PENDING_CANCEL_STATE, 
				PublicAPIConstant.cancelSO.PENDING_CANCEL_STATE);
		cancelOfrequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_CANCEL_DATE, cancelDate);
		
		//Set providerPaymentReq as Yes when values other than yes/no is given
		String providerPaymentReq = PublicAPIConstant.cancelSO.NO.equalsIgnoreCase(soCancelRequest.getProviderPaymentReqd())?"false":"true";
		//Set providerPaymentAck as No when values other than yes/no is given
		cancelOfrequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_PAY_PROVIDER_IND,providerPaymentReq);
		String providerPaymentAck = PublicAPIConstant.cancelSO.YES.equalsIgnoreCase(soCancelRequest.getProviderPaymentAck())?"true":"false";
		cancelOfrequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_CANCELLATION_PROVIDER_ACKNOWLEDGEMENT_IND,providerPaymentAck);
		
		cancelOfrequest = setMiscParameters(OrderfulfillmentConstants.PVKEY_SO_PRICING_METHOD,
				so.getPriceType(), cancelOfrequest);
		cancelOfrequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_USER_NAME,
				securityContext.getUsername());
		cancelOfrequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_MODIFIED_USER_NAME,
				securityContext.getUsername());
		cancelOfrequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_MODIFIED_USER_ID,
				securityContext.getVendBuyerResId().toString());
		cancelOfrequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_MANAGESCOPE_REASON_CODE, OrderConstants.MANAGESCOPE_REASON_CODE);
		cancelOfrequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_MANAGESCOPE_REASON_CODE_COMMENTS, OrderConstants.MANAGESCOPE_REASON_COMMENT);
		
		
		// For TASK_LEVEL pricing SO which is not in DRAFT/POSTED/EXPIRED map
		// the work order SKU to task.
		SOElementCollection collection = new SOElementCollection();
		boolean workOrderSkuNotRequired = PublicAPIConstant.cancelSO.NO.equalsIgnoreCase(soCancelRequest.getProviderPaymentReqd());
		if (OrderConstants.TASK_LEVEL_PRICING.equalsIgnoreCase(so.getPriceType())
				&& SignalType.CANCEL_ORDER_API == getSignalType(so.getWfStateId()) && !workOrderSkuNotRequired) {
			List<SOTask> workOrderTasks;
			//Task mapping for work order SKUs
			workOrderTasks = populateTaskFromSKUs(soCancelRequest.getCancelSkus().getWorkOrderSKUs(),
											securityContext.getCompanyId());
			if(null == workOrderTasks || workOrderTasks.isEmpty()){
				LOGGER.error(this.getClass().getName()+" - createOrderFulfillmentCancelSORequest() - No work order SKU for given SKUs!");
				return null;
			}
			collection.addAllElements(workOrderTasks);
			Integer manageReasonCodeId = Integer.valueOf(0);
			try{
				manageReasonCodeId = manageReasonCodeBO.getDefaultReasonCodeId(OrderConstants.TYPE_CANCEL, OrderConstants.MANAGESCOPE_REASON_CODE);
			}catch (Exception e) {
				LOGGER.error(this.getClass().getName()+"->createOrderFulfillmentCancelSORequest()",e);
				return null;
			}
			if(null != manageReasonCodeId){
				cancelOfrequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_MANAGESCOPE_REASON_CODE_ID, manageReasonCodeId.toString());
			}
		}
		cancelOfrequest.setElement(collection);
		return cancelOfrequest;
	}

	/**
	 * Legacy code to be eventually supplanted by
	 * executeOrderFulfillmentService().
	 */
	@Override
	protected IAPIResponse executeLegacyService(APIRequestVO apiVO) {
		SOCancelResponse cancelResponse = new SOCancelResponse();
		return cancelResponse;
	}

	/**
	 * This method dispatches the Cancel Service Order request (V1_2).
	 * Called from SOBaseService.execute()
	 */
	@Override
	protected IAPIResponse executeOrderFulfillmentService(APIRequestVO apiVO) {
		ProcessResponse cancelResponse = new ProcessResponse();
		OrderFulfillmentRequest ofRequest = null;		
		String soId = apiVO.getSOId();
		int buyerId = apiVO.getBuyerIdInteger();
		SOCancelRequest soCancelRequest = (SOCancelRequest) apiVO.getRequestFromPostPut();
		cancelResponse.setCode(PublicAPIConstant.ONE);
		//Ensure that all the mandatory fields have actual values (excluding white spaces)
		trimRequestElements(soCancelRequest);
		cancelResponse = validateMandatoryTags(soCancelRequest);
		if(!PublicAPIConstant.ONE.equals(cancelResponse.getCode())){
			//Create error response when request parameter mandatory validation failed.
			return createErrorResponse(cancelResponse);
		}
		SecurityContext securityContext; 
		if(PublicAPIConstant.BUYER_RESOURCE_ID.equalsIgnoreCase(soCancelRequest.getIdentification().getType())){
			securityContext = getSecurityContextForBuyer(apiVO.getBuyerResourceId());
		}else{
			securityContext = getSecCtxtForBuyerAdmin(buyerId);
		}
		ServiceOrder so;
		try {
			so = serviceOrderBO.getServiceOrder(soId);
			 /* Validates the cancellation request.*/
			cancelResponse = validateCancelRequest(so, securityContext, soCancelRequest);
		} catch (Exception be) {
			LOGGER.error(be);
			cancelResponse.setMessage(be.getMessage());
			cancelResponse.setCode(ServiceConstants.SYSTEM_ERROR_RC);
			return createErrorResponse(cancelResponse);
		}
		
		setProviderAcknowledgement(soCancelRequest);
		//Use appropriate Signal based on the SO State (For draft/routed/expired use
		//cancellation through front specific signal SL_CANCEL_ORDER)
		SignalType signalType = getSignalType(so.getWfStateId().intValue());
		if ((PublicAPIConstant.ONE).equalsIgnoreCase(cancelResponse.getCode())) {
			//If request parameters are validated then create OF Request
			try{
				ofRequest = createOrderFulfillmentCancelSORequest(so, securityContext, soCancelRequest);
			}catch(Exception e){
				LOGGER.error(e);
				cancelResponse.setMessage(e.getMessage());
				cancelResponse.setCode(ServiceConstants.SYSTEM_ERROR_RC);
				return createErrorResponse(cancelResponse);
			}
			
			if(null == ofRequest){
				LOGGER.error("Error in fetching data from DB.");
				cancelResponse.setMessage(CommonUtility
						.getMessage(PublicAPIConstant.cancelSO.DEFAULT_SYS_ERROR_MESSAGE));
				cancelResponse.setCode(ServiceConstants.SYSTEM_ERROR_RC);
				return createErrorResponse(cancelResponse);
			}
		} else {
			//Create error response when request parameter validation failed.
			return createErrorResponse(cancelResponse);
		}
		//OF call
		OrderFulfillmentResponse ofResponse = ofHelper
				.runOrderFulfillmentProcess(soId, signalType, ofRequest);
		if (!ofResponse.isSignalAvailable()) {
			//Create So Cancellation response when Signal is invalid.
			return createInvalidStateResponse();
		}
		if (ofResponse.isError()) {
			//Create Error response for Cancellation when error
			//Occurred while OF processing
			return createErrorResponse(ofResponse);
		}
		return createCancelResponse(ofHelper.getServiceOrder(soId));
	}

	/**
	 * If the request elements, ProviderPaymentReqired and Acknowledged are not set or have
	 * values other than Yes/No, this method will set default values for these elements. 
	 * */
	private void trimRequestElements(SOCancelRequest soCancelRequest) {
		
		String provPayReqrd =  soCancelRequest.getProviderPaymentReqd();
		if(checkForEmpty(provPayReqrd)){
			soCancelRequest.setProviderPaymentReqd(PublicAPIConstant.cancelSO.YES);
		}else{
			provPayReqrd = provPayReqrd.trim();
			if(!provPayReqrd.equalsIgnoreCase(PublicAPIConstant.cancelSO.YES) && !provPayReqrd.equalsIgnoreCase(PublicAPIConstant.cancelSO.NO)){
				soCancelRequest.setProviderPaymentReqd(PublicAPIConstant.cancelSO.YES);
			}else{
				soCancelRequest.setProviderPaymentReqd(provPayReqrd);
			}
		}
		
		String provPayAck =  soCancelRequest.getProviderPaymentAck();
		if(checkForEmpty(provPayAck)){
			soCancelRequest.setProviderPaymentAck(PublicAPIConstant.cancelSO.NO);
		}else{
			provPayAck = provPayAck.trim();
			if(!provPayAck.equalsIgnoreCase(PublicAPIConstant.cancelSO.YES) && !provPayAck.equalsIgnoreCase(PublicAPIConstant.cancelSO.NO)){
				soCancelRequest.setProviderPaymentAck(PublicAPIConstant.cancelSO.NO);
			}else{
				soCancelRequest.setProviderPaymentAck(provPayAck);
			}
		}
	}

	/**
	 * This method checks whether provider payment and Provider ack is set or not.
	 * If not, then set to default values.
	 * @param soCancelRequest : 
	 * 
	 * */
	
	private void setProviderAcknowledgement(SOCancelRequest soCancelRequest) {
		if(StringUtils.isEmpty(soCancelRequest.getProviderPaymentAck())){
			soCancelRequest.setProviderPaymentAck(PublicAPIConstant.cancelSO.NO);
		}
		if(StringUtils.isEmpty(soCancelRequest.getProviderPaymentAck())){
			soCancelRequest.setProviderPaymentReqd(PublicAPIConstant.cancelSO.YES);
		}
	}

	public IBuyerBO getBuyerBO() {
		return buyerBO;
	}

	public IManageReasonCodeBO getManageReasonCodeBO() {
		return manageReasonCodeBO;
	}

	public IServiceOrderBO getServiceOrderBO() {
		return serviceOrderBO;
	}

	/**
	 * @param wfStateId
	 *            : State Id of the SO.
	 * @return {@link SignalType}: returns "SL_CANCEL_ORDER" for draft, posted
	 *         and expired SOs. And CANCEL_ORDER_API for other valid status. End
	 *         states is not considered in the method as this is handled in in
	 *         validation.
	 * */
	private SignalType getSignalType(int wfStateId) {
		switch (wfStateId) {
		case OrderConstants.DRAFT_STATUS:
		case OrderConstants.ROUTED_STATUS:
		case OrderConstants.EXPIRED_STATUS:
			return SignalType.SL_CANCEL_ORDER;
		default:
			return SignalType.CANCEL_ORDER_API;
		}
	}

	
	/**
	 * For TASK_LEVEL pricing SOs, this method validates the Work order SKUs, and adds
	 * default work order SKUs if no SKUs are provided.
	 * 
	 * @param soCancelRequest {@link SOCancelRequest}
	 * @param securityContext {@link SecurityContext}
	 * @return list of invalid work order SKUs and when no work order SKUs
	 * are provided in which case default skus are added <br>
	 * */
	private List<String> handleTaskLevelPricing(SOCancelRequest soCancelRequest, SecurityContext securityContext) {
		List<WorkOrderSKU> workOrderSKUs = (null == soCancelRequest.getCancelSkus()) ? new ArrayList<WorkOrderSKU>()
				: soCancelRequest.getCancelSkus().getWorkOrderSKUs();
		if(!isWorkOrderSKUProvided(soCancelRequest.getCancelSkus())){
		/* Set default SKU for TASK_LEVEL SO when no work order SKU
		 * is provided. 
		 */
			workOrderSKUs = new ArrayList<WorkOrderSKU>();
			WorkOrderSKU defaultWorkOrder = new WorkOrderSKU();
			defaultWorkOrder.setCancellationSKU(PublicAPIConstant.cancelSO.DEFAULT_WORK_ORDER_SKU);
			//Set amount as 0$ if no amount is provided otherwise cancel amount itself is used.
			//TODO attention needed!!
			defaultWorkOrder.setCancellationAmount(soCancelRequest.getCancelAmount() == null ? 0
									: soCancelRequest.getCancelAmount());
			workOrderSKUs.add(defaultWorkOrder);
			CancelSKU cancelSKU = new CancelSKU();
			cancelSKU.setWorkOrderSKUs(workOrderSKUs);
			soCancelRequest.setCancelSkus(cancelSKU);
		} else {
			// Assumed that for default SKU we don't need validation
			// since it is fetched from SL DB.
			return validateSKUs(workOrderSKUs, securityContext.getCompanyId());				
		}
		return new ArrayList<String>();
	}

	/**
	 * Checks whether at least one work order SKU is provided. 
	 * @param cancelSkus : {@link CancelSKU}
	 * @return true: If there is at least one work order sku is provided by user. <br>
	 *         false : If no sku is provided.
	 * 
	 * */
	private boolean isWorkOrderSKUProvided(CancelSKU cancelSkus) {
		if(null == cancelSkus || cancelSkus.getWorkOrderSKUs() == null || cancelSkus.getWorkOrderSKUs().isEmpty()){
			return false;
		}else{
			List<WorkOrderSKU> workOrderSKUs = cancelSkus.getWorkOrderSKUs();
			boolean atleastOneSKUExists = Boolean.FALSE;
			for(WorkOrderSKU workOrderSKU : workOrderSKUs){
				if(!StringUtils.isEmpty(workOrderSKU.getCancellationSKU())){
					atleastOneSKUExists = Boolean.TRUE;
					break;
				}
			}
			return atleastOneSKUExists;
		}
	}

	/**
	 * For each work order SKU this method will fetch the corresponding task details
	 * and do the task mapping.
	 * @param cancelSkus : list of {@link WorkOrderSKU}
	 * @return List<SOTask> {@link SOTask}
	 * */
	private List<SOTask> populateTaskFromSKUs(List<WorkOrderSKU> cancelSkus,
			int buyerId) {
		List<SOTask> soTaskList = null;
		List<ManageTaskVO> workOrderTasks = new ArrayList<ManageTaskVO>();
		WorkOrderVO workOrder = new WorkOrderVO();
		workOrder.setBuyerId(buyerId);
		if(null != cancelSkus && cancelSkus.get(0).getCancellationSKU().equalsIgnoreCase(PublicAPIConstant.cancelSO.DEFAULT_WORK_ORDER_SKU)){
			soTaskList = createTaskForDefaultSKU(cancelSkus.get(0));
			return soTaskList;
		}
		List<String> skus = new ArrayList<String>();
		for (WorkOrderSKU cancelSku : cancelSkus) {
			skus.add(cancelSku.getCancellationSKU()); //Populate VO object
		}
		workOrder.setWorkOrderSKUs(skus);
		try {
			workOrderTasks = buyerBO.populateTasksFromSKUs(workOrder);
		} catch (Exception e) {
			LOGGER.error(e);
		}
		soTaskList = new ArrayList<SOTask>();
		if (null != workOrderTasks && !workOrderTasks.isEmpty()) {
			// for(ManageTaskVO task : workOrderTasks){
			for (int loop = 0; loop < workOrderTasks.size(); ++loop) {
				ManageTaskVO task = workOrderTasks.get(loop);
				SOTask soTask = new SOTask();
				soTask.setTaskName(task.getTaskName());
				soTask.setTaskComments(task.getTaskComments());
				soTask.setServiceTypeId(task.getServiceTypeTemplateId()
						.intValue());
				soTask.setExternalSku(task.getSku());
				//Final price will be the cancel amount provided in request xml
				BigDecimal taskPrice = BigDecimal.valueOf(cancelSkus.get(loop).getCancellationAmount());
				soTask.setFinalPrice(taskPrice);
				//price will be the final price of work order SKU
				//(TODO needs clarification)				
				soTask.setPrice(taskPrice);
				if (null != task.getSkillNodeId()) {
					soTask.setSkillNodeId(task.getSkillNodeId().intValue());
				}
				soTask.setTaskStatus(OrderConstants.ACTIVE_TASK);
				soTask.setTaskType(OrderConstants.PRIMARY_TASK);
				soTask.setAutoInjectedInd(0);
				if (!StringUtils.isEmpty(task.getSkuId())) {
					soTask.setSkuId(Integer.valueOf(task.getSkuId()));
				}
				soTaskList.add(soTask);
			}
		}
		return ((null == soTaskList) ? new ArrayList<SOTask>() : soTaskList);
	}


	/**
	 *  Creates a default SOTask from application property table.
	 *  @return List of SOTask which contain one default work order task
	 *  @param workOrderSKU : contains cancellation amount for default task.
	 * */
	private List<SOTask> createTaskForDefaultSKU(WorkOrderSKU workOrderSKU) {
		String defaultSku 			= "";
		String defaultTaskComment 	= "";
		String defaultTaskName		= "";
		List<SOTask> defaultTaskList = new ArrayList<SOTask>();
		try{
			defaultSku 			= applicationProperties.getPropertyFromDB(MPConstants.DEFAULT_WORK_ORDER_SKU);
			defaultTaskComment 	= applicationProperties.getPropertyFromDB(MPConstants.DEFAULT_WORK_ORDER_SKU_COMMENT);
			defaultTaskName 	= applicationProperties.getPropertyFromDB(MPConstants.DEFAULT_WORK_ORDER_TASK);
			SOTask defaultTask 	= new SOTask();
			defaultTask.setExternalSku(defaultSku);
			defaultTask.setTaskName(defaultTaskName);
			defaultTask.setTaskComments(defaultTaskComment);
			defaultTask.setTaskStatus(OrderConstants.ACTIVE_TASK);
			defaultTask.setTaskType(OrderConstants.PRIMARY_TASK);
			defaultTask.setAutoInjectedInd(0);
			defaultTask.setSkuId(null);
			defaultTask.setSkillNodeId(null);
			defaultTask.setFinalPrice(BigDecimal.valueOf(workOrderSKU.getCancellationAmount()));
			defaultTask.setPrice(defaultTask.getFinalPrice());
			defaultTaskList.add(defaultTask);
		}catch (Exception e) {
			LOGGER.error(this.getClass().getName()+"->createTaskForDefaultSKU()->", e);
		}
		return defaultTaskList;
	}

	public void setBuyerBO(IBuyerBO buyerBO) {
		this.buyerBO = buyerBO;
	}

	
	public void setManageReasonCodeBO(IManageReasonCodeBO manageReasonCodeBO) {
		this.manageReasonCodeBO = manageReasonCodeBO;
	}

	/**
	 * Setting Misc parameters for the OF request
	 * */
	private OrderFulfillmentRequest setMiscParameters(String paramName,
			String val, OrderFulfillmentRequest ofRequest) {
		if (!StringUtils.isEmpty(val)) {
			ofRequest.addMiscParameter(paramName, val.trim());
		}
		return ofRequest;
	}

	/**
	 * Creates Order Status that should be included in Response.
	 * */
	private void setOrderStatusInCancelResponse(
			SOCancelResponse soCancelResponse,
			com.servicelive.orderfulfillment.domain.ServiceOrder serviceOrder) {
		SOStatusInfo soStatusInfo = new SOStatusInfo();
		soStatusInfo.setSoId(serviceOrder.getSoId());
		soStatusInfo.setStatus(serviceOrder.getWfStatus() == null ? "" : serviceOrder
				.getWfStatus());
		soStatusInfo.setSubstatus(serviceOrder.getWfSubStatus() == null ? ""
				: serviceOrder.getWfSubStatus());
		soCancelResponse.setSoStatusInfo(soStatusInfo);
	}

	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}

	

	/**
	 * validateCancelAmount : Validates the requested cancel amount. This method
	 * validates that whether requested cancellation amount exceeds the spend
	 * limit for the SO and for no-auto funded buyers, checks whether it exceeds
	 * the max spend limit labor. For task level SOs, this method will calculate and
	 * set the cancellation Amount.
	 * 
	 * @return : returns the error message. empty string when no error
	 *         encountered.
	 * @param soCancelRequest
	 *            : The cancel request made through API (v1_2) securityContext :
	 *            SecurityContext for the buyer ServiceOrder : Service order
	 *            details for the SO requested to cancel.
	 * **/
	private String validateCancelAmount(SOCancelRequest soCancelRequest,
			SecurityContext securityContext, ServiceOrder so) {
		BigDecimal cancelAmount = BigDecimal.ZERO;
		StringBuilder warningMessage = new StringBuilder("");
		if (OrderConstants.TASK_LEVEL_PRICING.equalsIgnoreCase(so.getPriceType())) {
			/* If SO pricing is TASK_LEVEL then add all the work order SKUs to
			 * calculate the cancellation amount.
			 */
			for (WorkOrderSKU workOrderSKU : soCancelRequest.getCancelSkus()
					.getWorkOrderSKUs()) {
				cancelAmount = cancelAmount.add(BigDecimal.valueOf(workOrderSKU.getCancellationAmount() == null ? 0.0d 
						: workOrderSKU.getCancellationAmount()));
			}
		} else {
			/* If SO pricing is SO_LEVEL then cancellation price is the price
			 * provided by buyer as Cancel Amount.
			 */
			cancelAmount = BigDecimal.valueOf(soCancelRequest.getCancelAmount() == null ? 0.0d
							: soCancelRequest.getCancelAmount());
		}
		// validate Cancel amount against spend limit labor.
		if (cancelAmount.compareTo(BigDecimal.valueOf(so.getSpendLimitLabor())) > 0) {
			warningMessage.append(CommonUtility.getMessage(PublicAPIConstant.cancelSO.SO_CANCELLATION_AMOUNT_GREATER));
		}
		
		// validate Cancel Amount against max spend limit per SO.
		if( 0.0 != securityContext.getMaxSpendLimitPerSO() && cancelAmount.compareTo(BigDecimal.valueOf(securityContext.getMaxSpendLimitPerSO())) > 0){
			warningMessage.append(CommonUtility
					.getMessage(PublicAPIConstant.cancelSO.SO_CANCELLATION_AMOUNT_EXCEEDS_MAX_LIMIT));
		}
		
		//Validate cancel amount against available balance for non auto funded buyers.
		BigDecimal availableBalance = BigDecimal.ZERO;	
		AjaxCacheVO ajaxVo = new AjaxCacheVO();
		ajaxVo.setCompanyId(securityContext.getCompanyId());
		ajaxVo.setRoleType(securityContext.getRole());
		availableBalance = BigDecimal.valueOf(financeManagerBO.getavailableBalance(ajaxVo));
		
		
		if (null!=so.getFundingTypeId() && !(so.getFundingTypeId().intValue()== 40)  && cancelAmount.compareTo(availableBalance)>0) {
			warningMessage.append(CommonUtility
					.getMessage(PublicAPIConstant.cancelSO.SO_CANCELLATION_AMOUNT_INSUFFICIENT_BALANCE));
		}
		/**This validation is introduced as a part of Non-Funded Buyers Cancellation.Non funded buyers
		  can cancel service order for Zero price Only.*/
	    Boolean isNonFundedOrder = Boolean.FALSE;
	    if(null!= so && null!= so.getSoWrkFlowControls()){
	    	isNonFundedOrder = so.getSoWrkFlowControls().getNonFundedInd();
	    }
	    LOGGER.info("isNonFundedOrder value:"+ isNonFundedOrder);
	    if(isNonFundedOrder && null!= soCancelRequest.getCancelAmount() ){
	    	if(soCancelRequest.getCancelAmount().doubleValue() > 0){
	    		warningMessage.append(CommonUtility
						.getMessage(PublicAPIConstant.cancelSO.SO_CANCELLATION_AMOUNT_NON_FUNDED_BUYER_AMOUNT));
	    	}
	    }
		soCancelRequest.setCancelAmount(cancelAmount.doubleValue());
		return warningMessage.toString();
	}

	
	/**
	 * Validates Cancel API request. This is the main method that will handle
	 * all the specific validation for cancellation API.
	 * 
	 * @param so
	 *            : service order. SecurityContext : Security context for buyer.
	 *            soCancelRequest : Cancel request object.
	 * @return ProcessResponse
	 * */

	private ProcessResponse validateCancelRequest(ServiceOrder so,
			SecurityContext securityContext, SOCancelRequest soCancelRequest) {
		ProcessResponse response = new ProcessResponse();
		// Set response code as 1 for success (default)
		response.setCode(PublicAPIConstant.ONE);
		List<String> message = new ArrayList<String>();		
		boolean validReasonCode = Boolean.FALSE;
		// wf state ID of the SO
		int stateId = so.getWfStateId().intValue();
		switch (stateId) {
			/* For invalid states */	
			case OrderConstants.CANCELLED_STATUS:
			case OrderConstants.COMPLETED_STATUS:
			case OrderConstants.PENDING_CANCEL_STATUS:
			case OrderConstants.CLOSED_STATUS:
			case OrderConstants.VOIDED_STATUS:
			case OrderConstants.DELETED_STATUS:
				message.add(CommonUtility
						.getMessage(PublicAPIConstant.INVALID_STATE_ERROR_CODE));
				response.setCode(ServiceConstants.USER_ERROR_RC);
				break;
			case OrderConstants.DRAFT_STATUS:
			case OrderConstants.ROUTED_STATUS:
			case OrderConstants.EXPIRED_STATUS:
				// Validate cancel reason code
				validReasonCode = validateReasonCodes(soCancelRequest.getCancelReasonCode(), securityContext.getCompanyId());
				if (!validReasonCode) {
					message.add(CommonUtility.getMessage(PublicAPIConstant.cancelSO.INVALID_CANCEL_REASONCODE_ERROR_CODE));
					response.setCode(ServiceConstants.USER_ERROR_RC);
				} else {
					// Handle the possible scenario where request xml contains
					// amount for these state SOs.
					soCancelRequest.setCancelAmount(Double.valueOf(0));
				}
				break;
			case OrderConstants.ACCEPTED_STATUS:
			case OrderConstants.ACTIVE_STATUS:
			case OrderConstants.PROBLEM_STATUS:
				boolean isAmountValidationNeeded = Boolean.TRUE;
				// Validate cancel reason code
				validReasonCode = validateReasonCodes(soCancelRequest.getCancelReasonCode(), securityContext.getCompanyId());
				if (!validReasonCode) {
					message.add(CommonUtility.getMessage(PublicAPIConstant.cancelSO.INVALID_CANCEL_REASONCODE_ERROR_CODE));
					response.setCode(ServiceConstants.USER_ERROR_RC);
				} else {
					 /* If Compensation is not required, task_level check is not
					 * needed. SO is to be cancelled for 0.0$.
					 */
					if (PublicAPIConstant.cancelSO.NO.equalsIgnoreCase(soCancelRequest.getProviderPaymentReqd())) {
						soCancelRequest.setCancelSkus(new CancelSKU());
						soCancelRequest.setCancelAmount(Double.valueOf(0));
						isAmountValidationNeeded = Boolean.FALSE;
					} else if (OrderConstants.TASK_LEVEL_PRICING.equalsIgnoreCase(so.getPriceType())) {
						/* For TASK_LEVEL SO consider work order SKU for validation
						 * and for calculating the cancellation amount.
						 */
						List<String> invalidSKUs = handleTaskLevelPricing(soCancelRequest, securityContext);
						if (null != invalidSKUs && !invalidSKUs.isEmpty()) {
							StringBuilder warning = new StringBuilder(CommonUtility.getMessage(PublicAPIConstant.cancelSO.INVALID_WORK_ORDER_SKU_ERROR_CODE));
							warning.append("Invalid SKU(s): ");
							warning.append(invalidSKUs);
							message.add(warning.toString());
							response.setCode(ServiceConstants.USER_ERROR_RC);
							break;
						}
					}
				}
				
				/* If SO Status, reason code and SKUs (if applicable) are valid then
				 * validate the cancellation amount.
				 */
				if (isAmountValidationNeeded && PublicAPIConstant.ONE.equals(response.getCode())) {
					String warningMsg = validateCancelAmount(soCancelRequest,
							securityContext, so);
					if (!StringUtils.isEmpty(warningMsg)) {
						message.add(warningMsg);
						response.setCode(ServiceConstants.USER_ERROR_RC);
					}
				}
				break;
			default:
				/*Error message on invalid SO State*/
				message.add(CommonUtility
						.getMessage(PublicAPIConstant.INVALID_STATE_ERROR_CODE));
				response.setCode(ServiceConstants.USER_ERROR_RC);
				break;
		}
		
		if (message.isEmpty()) {
			response.setCode(PublicAPIConstant.ONE);
		}
		response.setMessages(message);
		return response;
	}

	/**<p> Checks whether the mandatory fields in request XML are empty tags.
	 * <p> If so returns error response with proper warning.
	 * @param soCancelRequest {@link SOCancelRequest}
	 * @return {@link ProcessResponse} : code = "ONE" when no error encountered.
	 * */
	private ProcessResponse validateMandatoryTags(SOCancelRequest soCancelRequest) {
		ProcessResponse response = new ProcessResponse();
		List<String> message = new ArrayList<String>();
		response.setCode(PublicAPIConstant.ONE);
		if(checkForEmpty(soCancelRequest.getCancelReasonCode())){
			message.add(CommonUtility
					.getMessage(PublicAPIConstant.cancelSO.CANCEL_REASON_CODE_MISSING));
		}
		if(checkForEmpty(soCancelRequest.getCancelComment())){
			message.add(CommonUtility
					.getMessage(PublicAPIConstant.cancelSO.CANCEL_COMMENT_MISSING));
		}
		if(!message.isEmpty()){
			response.setMessages(message);
			response.setCode(ServiceConstants.WARNING_RC);
		}
		return response;
		
	}
	
	/**
	 * Return true is the argument string is null/empty/white spaces
	 * @param String.
	 * @return true : parameter string is empty
	 * @return false : not empty
	 * */
	private boolean checkForEmpty(String str){
		String sourceStr = str;
		if(StringUtils.isBlank(sourceStr)){
			return true;
		}
		return false;
	}

	/**
	 * Validates the cancel reason code submitted through API request.
	 * 
	 * @return true : validation success, false : validation fails.
	 * @param cancelReasonCode
	 *            : The cancel reason code that to be validated.
	 **/
	private boolean validateReasonCodes(String cancelReasonCode, int buyerId) {
		Integer reasonCodeId = null;
		reasonCodeId = manageReasonCodeBO.isAReasonCode(buyerId,
				OrderConstants.TYPE_CANCEL, cancelReasonCode);
		if (null == reasonCodeId
				|| reasonCodeId.intValue() == Integer.valueOf(0)) {
			return false;
		}else{
			//Use this reasonCodeId while setting Misc Parameter. 
			this.reasonCodeId = reasonCodeId.intValue();
		}
		return true;
	}

	/**
	 * Validates the work order SKUs for SO with pricing type TASK_LEVEL.
	 * 
	 * @param cancelSkus
	 *            : list of work order SKUs.
	 * @return List of invalid SKUs if any.
	 * */
	private List<String> validateSKUs(List<WorkOrderSKU> cancelSkus, int buyerId) {
		WorkOrderVO workOrder = new WorkOrderVO();
		workOrder.setBuyerId(buyerId);
		List<String> listSku = new ArrayList<String>();
		for (WorkOrderSKU cancelSku : cancelSkus) {
			listSku.add(cancelSku.getCancellationSKU()); //Populate VO
		}
		workOrder.setWorkOrderSKUs(listSku);
		List<String> invalidSKUs = null;
		try {
			invalidSKUs = buyerBO.validateWorkOrderSKUs(workOrder);
		} catch (Exception e) {
			LOGGER.error(e);
		}
		return invalidSKUs;
	}
	public IApplicationProperties getApplicationProperties() {
		return applicationProperties;
	}

	public void setApplicationProperties(
			IApplicationProperties applicationProperties) {
		this.applicationProperties = applicationProperties;
	}
	
	public IFinanceManagerBO getFinanceManagerBO() {
		return financeManagerBO;
	}

	public void setFinanceManagerBO(IFinanceManagerBO financeManagerBO) {
		this.financeManagerBO = financeManagerBO;
	}
}
