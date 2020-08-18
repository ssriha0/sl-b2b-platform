package com.servicelive.esb.integration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DeadlockLoserDataAccessException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.newco.marketplace.tokenize.HsTokenizeResponse;
import com.newco.marketplace.tokenize.IHSTokenizeServiceCreditCardBO;
import com.servicelive.bus.event.order.ServiceOrderEvent;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.common.transaction.Retryable;
import com.servicelive.esb.constant.MarketESBConstant;
import com.servicelive.esb.integration.bo.IIntegrationBO;
import com.servicelive.esb.integration.domain.Batch;
import com.servicelive.esb.integration.domain.IntegrationName;
import com.servicelive.esb.integration.domain.ProcessingStatus;
import com.servicelive.esb.integration.domain.TransactionType;
import com.servicelive.orderfulfillment.domain.SOAdditionalPayment;
import com.servicelive.orderfulfillment.domain.SOAddon;
import com.servicelive.orderfulfillment.domain.SOPart;
import com.servicelive.orderfulfillment.domain.SOSchedule;
import com.servicelive.orderfulfillment.domain.SOTask;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.PartSupplierType;
import com.servicelive.orderfulfillment.domain.type.PaymentType;

/**
 * This class is used to create a transactional boundary for cases when a
 * sequence of DB updates need to be made through the {@link IIntegrationBO}.
 * 
 * @author sahmed
 * 
 */
public class IntegrationServiceCoordinator implements IIntegrationServiceCoordinator {
	private static final String DEFAULT_RI_ADD_ON_TYPE = "N";
	private static final String RI_SKU_COVERAGE_COLLECT_CALL = "CC";
	private static final String DEFAULT_RI_ADD_ON_CHARGE_CODE = "Z";
	private static final String SCOPE_CHANGE_COVERAGE_TYPE = "PT";
	private static final Logger logger = Logger.getLogger(IntegrationServiceCoordinator.class);
	private static final String HSR_TECH_ID = "0000003";	

	private IIntegrationBO integrationBO;
	private IHSTokenizeServiceCreditCardBO   hsTokenServiceCreditCardBo;
	public String performAddNpsCallCloseRetriesToBatch(Double retryHours,
			Long maxDbTransactions) {
		this.integrationBO.addCallCloseRequestsWithoutResponsesToBatch(retryHours, maxDbTransactions);
		String resendErrorMessage = this.integrationBO.addAuditRequestsMarkedAsResendToBatch(maxDbTransactions);
		return resendErrorMessage;
	}
	
	@Transactional
	@Retryable(maximumRetryAttempts=5, unlimitedRetriesAllowed=false, retryExceptions={DeadlockLoserDataAccessException.class})
	public Batch createBatchForFileUpload(String inputFilefeedName,
			List<Map<String, String>> orders, Long buyerResourceId, String fileUploadId) {
		Batch batch = this.integrationBO.createBatch(inputFilefeedName, IntegrationName.FILE_UPLOAD.getId(), ProcessingStatus.CREATED, null);
		try {
			this.integrationBO.createAllOrders(buyerResourceId, fileUploadId, orders, batch.getBatchId());
			batch.setProcessingStatus(ProcessingStatus.SUCCESS);
		} catch (Exception e) {
			batch.setProcessingStatus(ProcessingStatus.ERROR);
			batch.setException(e.getMessage());
			logger.error("Error while attempting to create file upload errors", e);
		}
		this.integrationBO.updateBatch(batch);
		return batch;
	}

	@Transactional
	@Retryable(maximumRetryAttempts=5, unlimitedRetriesAllowed=false, retryExceptions={DeadlockLoserDataAccessException.class})
	public Batch recordHsrOrderAcceptedEventAndRelatedData(
			String orderEventName, ServiceOrder serviceOrder) {
		Batch newBatch = this.integrationBO.createBatch(null,
				IntegrationName.HSR_OUTBOUND.getId(), ProcessingStatus.CREATED,
				null);
		
		String externalOrderNumber = getSearsExternalOrderNumber(serviceOrder);
		Long integrationServiceOrderId = null;
		com.servicelive.esb.integration.domain.ServiceOrder integrationServiceOrder =
			this.integrationBO.findLatestServiceOrderByExternalOrderNumber(externalOrderNumber);
		if (integrationServiceOrder == null) {
			logger.info(String.format("Unable to find incoming service order request for externalOrderNumber '%s'. Notification for order event '%s' will be recorded as an orphan.", externalOrderNumber, orderEventName));
		}
		else {
			integrationServiceOrderId = integrationServiceOrder.getServiceOrderId();
		}

		Long transactionId = this.integrationBO
				.createNewBuyerNotificationTransaction(newBatch.getBatchId(),
						externalOrderNumber,
						serviceOrder.getSoId());

		Long buyerNotificationId =
			this.integrationBO.createNewBuyerNotification(transactionId, orderEventName,
						null, integrationServiceOrderId);

		Long hsrBuyerNotificationId = this.integrationBO.createNewHsrBuyerNotification(
				buyerNotificationId,
				serviceOrder.getCustomRefValue(MarketESBConstant.CUSTOM_REF_UNIT_NUM_HSR),
				serviceOrder.getCustomRefValue(MarketESBConstant.CUSTOM_REF_ORDER_NUM_HSR),
				serviceOrder.getRoutedDate(), HSR_TECH_ID);

		logger.debug(String.format("created new batch: %s", newBatch.toString()));
		logger.debug(String.format("created new transaction record with id: %d", transactionId));
		logger.debug(String.format("created new buyer notification record with id: %d", buyerNotificationId));
		logger.debug(String.format("created new hsr buyer notification record with id: %d", hsrBuyerNotificationId));
		return newBatch;
	}

	
	@Transactional
	@Retryable(maximumRetryAttempts=5, unlimitedRetriesAllowed=false, retryExceptions={DeadlockLoserDataAccessException.class})
	public void recordSuccessfulFileGeneration(Long batchId,
			long transactionId, String updateLocationAndFileName) {
		ArrayList<Long> transactionIds = new ArrayList<Long>();
		transactionIds.add(transactionId);
		this.integrationBO.updateBatchFileNameById(batchId, updateLocationAndFileName);
		this.integrationBO.markTransactionsAsProcessed(transactionIds);
	}
	
	@Transactional
	@Retryable(maximumRetryAttempts=5, unlimitedRetriesAllowed=false, retryExceptions={DeadlockLoserDataAccessException.class})
	public Batch recordAssurantServiceOrderEventData(ServiceOrder serviceOrder, String orderEventName, String subStatusName, String incidentActionDescription) {
		Batch batch = this.integrationBO.createBatch(null, IntegrationName.ASSURANT_OUTBOUND.getId(), 
				ProcessingStatus.CREATED, null);

		String externalOrderNumber = serviceOrder.getCustomRefValue(MarketESBConstant.CUSTOM_REF_INCIDENT_ID);
		Long relatedIntegrationServiceOrderId = null;
		com.servicelive.esb.integration.domain.ServiceOrder relatedIntegrationServiceOrder =
			this.integrationBO.findLatestServiceOrderByExternalOrderNumber(externalOrderNumber);
		if (relatedIntegrationServiceOrder == null) {
			logger.info(String.format("Unable to find incoming service order request for incident id '%s'. Notification for order event '%s' and sub status '%s' will be recorded as an orphan.", externalOrderNumber, orderEventName, subStatusName));
		}
		else {
			relatedIntegrationServiceOrderId = relatedIntegrationServiceOrder.getServiceOrderId();
		}
		Long transactionId = this.integrationBO.createNewBuyerNotificationTransaction(batch.getBatchId(), externalOrderNumber, serviceOrder.getSoId());

		if (orderEventName == null) {
			orderEventName = "UNKNOWN";
		}
		
		Long buyerNotificationId =
			this.integrationBO.createNewBuyerNotification(
					transactionId, orderEventName, subStatusName,
					relatedIntegrationServiceOrderId);
		
		Date etaDate = serviceOrder.getServiceEndDateTimeCalendar().getTime();
		String shipAirBill = "";
		String coreReturnAirBill = "";
		String replacementOEM = "";
		String replacementModel = "";
		if (serviceOrder.getParts() != null && serviceOrder.getParts().size() > 0) {
			SOPart firstPart =  serviceOrder.getParts().get(0);
			if (firstPart!= null) {
				shipAirBill = getNonNullValueOrDefaultFrom(firstPart.getShipTrackNo(), shipAirBill);
				coreReturnAirBill = getNonNullValueOrDefaultFrom(firstPart.getReturnTrackNo(), coreReturnAirBill);
				replacementOEM = getNonNullValueOrDefaultFrom(firstPart.getManufacturer(), replacementOEM);
				replacementModel = getNonNullValueOrDefaultFrom(firstPart.getModelNumber(), replacementModel);
				etaDate = getNonNullValueOrDefaultFrom(firstPart.getShipDate(), etaDate);
			}
		}
		
		Long assurantNotificationId =
		this.integrationBO.createNewAssurantBuyerNotification(
				buyerNotificationId,
				etaDate, shipAirBill, coreReturnAirBill, incidentActionDescription);
		logger.debug("Buyer Notification Id = " + buyerNotificationId);
		logger.debug("Assurant Notification Id = " + assurantNotificationId);
		
		long newIntegrationServiceOrderId = this.integrationBO
				.createServiceOrder(null, transactionId, null, null, null,
						null, null, null, null, null, null, null, null, null,
						null, null, null);
		
		for (SOPart part : serviceOrder.getParts()) {
			createPart(part, newIntegrationServiceOrderId);
		}
		
		return batch;
	}

	
	private <T> T getNonNullValueOrDefaultFrom(T value, T defaultValue) {
		T returnValue = defaultValue;
		if (value != null) {
			returnValue = value;
		}
		return returnValue;
	}
		
	private void createPart(SOPart part, long serviceOrderId) {
		com.servicelive.esb.integration.domain.Part integrationPart = 
			new com.servicelive.esb.integration.domain.Part();
		
		integrationPart.setServiceOrderId(serviceOrderId);
		integrationPart.setClassCode(part.getAlternatePartReference1());
		integrationPart.setClassComments(replaceNewLineChars(part.getPartDs(), " "));
		integrationPart.setManufacturer(part.getManufacturer());
		integrationPart.setModelNumber(part.getModelNumber());
		if (part.getQuantity() != null && part.getQuantity().length() > 0) {
			integrationPart.setQuantity(Integer.parseInt(part.getQuantity()));
		}
		this.integrationBO.createPart(integrationPart);
		
	}
	
	private String replaceNewLineChars(String source, String replacement) {
		String replaced = StringUtils.replace(source, "\r\n", replacement);
		replaced = StringUtils.replace(replaced, "\r", replacement);
		replaced = StringUtils.replace(replaced, "\n", replacement);
		return replaced;
	}
	
	@Transactional
	@Retryable(maximumRetryAttempts=5, unlimitedRetriesAllowed=false, retryExceptions={DeadlockLoserDataAccessException.class})
	public void recordSearsRiOrderClosedEventAndRelatedData(ServiceOrderEvent serviceOrderEvent,
			ServiceOrder serviceOrder) {
		String orderEventName = serviceOrderEvent.getEventHeader().get(ServiceOrderEvent.ORDER_EVENT);
		if (orderEventName == null) {
			orderEventName = "UNKNOWN";
		}
		
		Batch openBatch = this.integrationBO.findCurrentOrCreateNewOpenBatchFor(IntegrationName.RI_OUTBOUND);
		String externalOrderNumber = getSearsExternalOrderNumber(serviceOrder);
		Long integrationServiceOrderId = null;
		com.servicelive.esb.integration.domain.ServiceOrder integrationServiceOrder =
			this.integrationBO.findLatestServiceOrderByExternalOrderNumber(externalOrderNumber);
		if (integrationServiceOrder == null) {
			throw new RuntimeException(String.format("Unable to find incoming service order request for externalOrderNumber '%s' while processing order event '%s' will be recorded as an orphan.", externalOrderNumber, orderEventName));
		}
		else {
			integrationServiceOrderId = integrationServiceOrder.getServiceOrderId();
		}
		
		Long transactionId = this.integrationBO.createNewBuyerNotificationTransaction(openBatch.getBatchId(), externalOrderNumber, serviceOrder.getSoId());
		
		Long buyerNotificationId = this.integrationBO.createNewBuyerNotification(transactionId, orderEventName, null, integrationServiceOrderId);
		Long omsBuyerNotificationId = createOmsBuyerNotification(serviceOrder, buyerNotificationId);
		Long serviceOrderId = createServiceOrder(serviceOrder, transactionId);
		Map<String, BigDecimal> skuPriceMap = createSkuPriceMap(serviceOrder);
		this.integrationBO.copyTasks(integrationServiceOrderId, serviceOrderId, skuPriceMap);
		Integer maxSequenceNumber = getmaxSequenceNumber(serviceOrder);
		List<Long> mfeAddedTaskIds = createScopeChangeTasks(serviceOrder, serviceOrderId, maxSequenceNumber);
		maxSequenceNumber += mfeAddedTaskIds.size();
		List<Long> taskIds = createTasksForAddOns(serviceOrder, serviceOrderId,maxSequenceNumber);

		logger.info(String.format("found open batch: %s", openBatch));
		logger.info(String.format("created new transaction record with id: %d", transactionId));
		logger.info(String.format("created new buyer notification record with id: %d", buyerNotificationId));
		logger.info(String.format("created new oms buyer notification record with id: %d", omsBuyerNotificationId));
		logger.info(String.format("created new service order record with id: %d", serviceOrderId));
		logger.info(String.format("created new mfe task records with ids: %s", StringUtils.collectionToCommaDelimitedString(mfeAddedTaskIds)));
		logger.info(String.format("created new add on task records with ids: %s", StringUtils.collectionToCommaDelimitedString(taskIds)));
	}
	


	@Transactional
	@Retryable(maximumRetryAttempts=5, unlimitedRetriesAllowed=false, retryExceptions={DeadlockLoserDataAccessException.class})
	public void recordSearsRiOrderCancelEventAndRelatedData(ServiceOrderEvent serviceOrderEvent,
			ServiceOrder serviceOrder) {
		String orderEventName = serviceOrderEvent.getEventHeader().get(ServiceOrderEvent.ORDER_EVENT);
		if (orderEventName == null) {
			orderEventName = "UNKNOWN";
		}
		Batch openBatch = this.integrationBO.findCurrentOrCreateNewOpenBatchFor(IntegrationName.RI_CANCEL_OUTBOUND);
		String externalOrderNumber = getSearsExternalOrderNumber(serviceOrder);
		Long integrationServiceOrderId = null;
		com.servicelive.esb.integration.domain.ServiceOrder integrationServiceOrder =
			this.integrationBO.findLatestServiceOrderByExternalOrderNumber(externalOrderNumber);
		if (integrationServiceOrder == null) {
			throw new RuntimeException(String.format("Unable to find incoming service order request for externalOrderNumber '%s' while processing order event '%s' will be recorded as an orphan.", externalOrderNumber, orderEventName));
		}
		else {
			Long transactnId = integrationServiceOrder.getTransactionId();
			int transactionType = this.integrationBO.getTransactionTypeById(transactnId);
			
			if(TransactionType.CANCEL.getId() == transactionType) {
				return;				
			}
			integrationServiceOrderId = integrationServiceOrder.getServiceOrderId();
			Long transactionId = this.integrationBO.createNewBuyerNotificationTransaction(openBatch.getBatchId(), externalOrderNumber, serviceOrder.getSoId());			
			Long buyerNotificationId = this.integrationBO.createNewBuyerNotification(transactionId, orderEventName, null, integrationServiceOrderId);
			//Long omsBuyerNotificationId = createOmsBuyerNotification(serviceOrder, buyerNotificationId);
			Long serviceOrderId = createServiceOrder(serviceOrder, transactionId);
			Map<String, BigDecimal> skuPriceMap = createSkuPriceMap(serviceOrder);
			this.integrationBO.copyTasksForCancellation(integrationServiceOrderId, serviceOrderId, skuPriceMap);
			Integer maxSequenceNumber = getmaxSequenceNumber(serviceOrder);
			List<Long> taskIds = createScopeChangeTasks(serviceOrder, serviceOrderId,maxSequenceNumber);

			logger.debug(String.format("found open batch: %s", openBatch, "for so id :",serviceOrder.getSoId()));
			logger.debug(String.format("created new transaction record with id: %d", transactionId));
			logger.debug(String.format("created new buyer notification record with id: %d", buyerNotificationId));
			//logger.debug(String.format("created new oms buyer notification record with id: %d", omsBuyerNotificationId));
			logger.debug(String.format("created new service order record with id: %d", serviceOrderId));
			logger.debug(String.format("created new task records with ids: %s", StringUtils.collectionToCommaDelimitedString(taskIds)));
		}		
	}
	private Map<String, BigDecimal> createSkuPriceMap(ServiceOrder serviceOrder) {
		List<SOTask> tasks = serviceOrder.getActiveTasks();
		if (tasks == null || tasks.isEmpty()) return Collections.emptyMap();
		Map<String, BigDecimal> skuPricesMap = new HashMap<String, BigDecimal>(tasks.size());
		for (SOTask task : tasks) {
			BigDecimal taskPrice = extractTaskPrice(serviceOrder, task);
			String taskName = task.getTaskName();
			if (StringUtils.hasText(taskName)) {
				String[] parts = taskName.split("-");
				String sku = parts[0];
				skuPricesMap.put(sku, taskPrice);
		}
		}

		return skuPricesMap;
	}

	private BigDecimal extractTaskPrice(ServiceOrder serviceOrder, SOTask task) {
		//we have to return selling price back to NPS from SL
		BigDecimal taskPrice = getNonNullValueOrDefaultFrom(task.getSellingPrice(), BigDecimal.ZERO);
		if (StringUtils.hasText(task.getTaskName())) {
			logger.info(String.format("Found SOTask with taskId %d and taskName '%s'.", task.getTaskId(), task.getTaskName()));
			// CUSTOM_REF_FINAL_PERMIT_PRICE is no longer used after SL-8937
			/*if (task.getTaskName().trim().startsWith(MarketESBConstant.PERMIT_SKU)) {
				logger.debug(String.format("SOTask with taskId %d and taskName '%s' represents a Permit SKU.", task.getTaskId(), task.getTaskName()));
				String permitPriceCustomReferenceValue = serviceOrder.getCustomRefValue(MarketESBConstant.CUSTOM_REF_FINAL_PERMIT_PRICE);
				try {
					if (StringUtils.hasText(permitPriceCustomReferenceValue)) {
						logger.debug(String.format("Found '%s' as value for custom reference with key '%s'.", permitPriceCustomReferenceValue, MarketESBConstant.CUSTOM_REF_FINAL_PERMIT_PRICE));
						taskPrice = new BigDecimal(permitPriceCustomReferenceValue);
					}
					else {
						logger.debug(String.format("Found empty value for custom reference with key '%s'.", MarketESBConstant.CUSTOM_REF_FINAL_PERMIT_PRICE));
					}
				}
				catch (NumberFormatException e) {
					logger.warn(String.format("Ignoring permit price from custom reference field '%s' as it could not be parsed as a double value.", permitPriceCustomReferenceValue), e);
					taskPrice = BigDecimal.ZERO;
				}
			}
			else {
				logger.debug(String.format("SOTask with taskId %d and taskName '%s' is not a Permit Task.", task.getTaskId(), task.getExternalSku()));
			}*/
		}
		else {
			logger.debug(String.format("Found SOTask with taskId %d and empty taskName.", task.getTaskId()));
		}
		return taskPrice;
	}

	private List<Long> createTasksForAddOns(ServiceOrder serviceOrder,
			Long serviceOrderId,Integer maxSequenceNumber) {
		List<Long> taskIds = new ArrayList<Long>();
		logger.info(String.format("max SequenceNumber for tasks", maxSequenceNumber));
		for (SOAddon addOn : serviceOrder.getAddons()) {
			double price = extractAddOnPrice(serviceOrder, addOn);			
			for (int i = 0; i < addOn.getQuantity(); i++) {
				maxSequenceNumber=maxSequenceNumber+1;
				Long taskId = this.integrationBO.createTask(
					serviceOrderId,
					addOn.getDescription(),
					addOn.getScopeOfWork(),
					false, // defaultTask
					addOn.getSku(),
					null, // specialtyCode
					price,
					null, // category
					null, // subCategory
					null, // serviceType
					maxSequenceNumber
				);
				taskIds.add(taskId);
				
				@SuppressWarnings("unused")
				Long omsTaskId = this.integrationBO.createOmsTask(
						taskId,
						DEFAULT_RI_ADD_ON_CHARGE_CODE, // chargeCode
						RI_SKU_COVERAGE_COLLECT_CALL, // coverage
						DEFAULT_RI_ADD_ON_TYPE, // type
						addOn.getDescription());
				logger.info(String.format("new SequenceNumber for addon", maxSequenceNumber));
			}
		}

		return taskIds;
	}
	
	private List<Long> createScopeChangeTasks(ServiceOrder serviceOrder,
			Long serviceOrderId,Integer maxSequenceNumber) {
		List<Long> taskIds = new ArrayList<Long>();
		logger.info(String.format("max SequenceNumber for tasks", maxSequenceNumber));
		for(SOTask task : serviceOrder.getManageScopeTasks()) {
			if(null != task.getManageScopeId()){
				maxSequenceNumber=maxSequenceNumber+1;
				Long taskId = this.integrationBO.createTask(
						serviceOrderId,
						task.getTaskName(),
						task.getTaskComments(),
						false, // defaultTask //what is default task: Need confirmation
						task.getExternalSku(),
						null, // specialtyCode
						0.00d,
						null, // category
						null, // subCategory
						null, // serviceType // What should be serviceType
						maxSequenceNumber
					);
				taskIds.add(taskId);
				 
				@SuppressWarnings("unused")
				Long omsTaskId = this.integrationBO.createOmsTask(
						taskId,
						DEFAULT_RI_ADD_ON_CHARGE_CODE, // chargeCode
						SCOPE_CHANGE_COVERAGE_TYPE, // coverage
						DEFAULT_RI_ADD_ON_TYPE, // type
						task.getTaskName());
				logger.debug(String.format("new SequenceNumber for addon", maxSequenceNumber));
			}
		}

		return taskIds;
		
	}

	private double extractAddOnPrice(ServiceOrder serviceOrder, SOAddon addOn) {
		BigDecimal addOnRetailPrice = getNonNullValueOrDefaultFrom(addOn.getRetailPrice(), BigDecimal.ZERO);
		double price = addOnRetailPrice.doubleValue();
		return price;
	}

	private Long createServiceOrder(ServiceOrder serviceOrder,
			Long transactionId) {
		BigDecimal soLaborSpendLimit = serviceOrder.getSpendLimitLabor();
		Double laborSpendLimit = soLaborSpendLimit == null ? null : soLaborSpendLimit.doubleValue();
		BigDecimal soPartsSpendLimit = serviceOrder.getSpendLimitParts();
		Double partsSpendLimit = soPartsSpendLimit == null ? null : soPartsSpendLimit.doubleValue();
		SOSchedule soSchedule = serviceOrder.getSchedule();
		Date serviceDate1 = soSchedule == null ? null : soSchedule.getServiceDate1();
		Date serviceDate2 = soSchedule == null ? null : soSchedule.getServiceDate2();
		String serviceTimeStart = soSchedule == null ? null : soSchedule.getServiceTimeStart();
		String serviceTimeEnd = soSchedule == null ? null : soSchedule.getServiceTimeEnd();
		boolean requestedServiceTimeTypeIsSingleDay = soSchedule == null ? false : soSchedule.isRequestedServiceTimeTypeSingleDay();
		Integer soTemplateId = serviceOrder.getTemplateId();
		Long templateId = soTemplateId == null ? null : soTemplateId.longValue();
		PartSupplierType soPartsSupplier = serviceOrder.getPartsSupplier();
		String partsSupplier = soPartsSupplier == null ? null : soPartsSupplier.toString();
		Integer soProviderServiceConfirmInd = serviceOrder.getProviderServiceConfirmInd();
		Boolean providerServiceConfirmInd = soProviderServiceConfirmInd == null ? null : BooleanUtils.toBoolean(soProviderServiceConfirmInd);
		
		long integrationServiceOrderId =
			this.integrationBO.createServiceOrder(
				null,
				transactionId,
				laborSpendLimit,
				partsSpendLimit,
				serviceOrder.getSowTitle(),
				serviceOrder.getSowDs(),
				serviceOrder.getProviderInstructions(),
				serviceDate1,
				serviceTimeStart,
				serviceDate2,
				serviceTimeEnd,
				templateId,
				providerServiceConfirmInd,
				partsSupplier,
				requestedServiceTimeTypeIsSingleDay,
				serviceOrder.getPrimarySkillCategory(),
				serviceOrder.getBuyerTermsCond()
			);
		return integrationServiceOrderId;
	}

	private String getSearsExternalOrderNumber(ServiceOrder serviceOrder) {
		String unitNumber = serviceOrder.getCustomRefValue(MarketESBConstant.CUSTOM_REF_UNIT_NUM);
		String orderNumber = serviceOrder.getCustomRefValue(MarketESBConstant.CUSTOM_REF_ORDER_NUM);
		return unitNumber + orderNumber;
	}
	private Integer getmaxSequenceNumber(ServiceOrder serviceOrder) {
		List<SOTask> tasks = serviceOrder.getTasks();
		Integer maxSequenceNum=0;
		//if (tasks == null || tasks.isEmpty()) return Collections.emptyMap();
		//Map<String, BigDecimal> skuPricesMap = new HashMap<String, BigDecimal>(tasks.size());
		for (SOTask task : tasks) {
			//Long taskPrice = extractTaskPrice(serviceOrder, task);
			Integer maxSequenceNumTemp = task.getSequenceNumber();
			if (maxSequenceNumTemp != null && maxSequenceNum<maxSequenceNumTemp) {
				maxSequenceNum=maxSequenceNumTemp;
			}
		}
		return maxSequenceNum;
	}
	
	

	/**
	 * @param serviceOrder
	 * @param buyerNotificationId
	 * @return
	 */
	private Long createOmsBuyerNotification(ServiceOrder serviceOrder, Long buyerNotificationId) {
		SOAdditionalPayment additionalPayment = serviceOrder.getAdditionalPayment();
		BigDecimal additionalPaymentAmount = BigDecimal.ZERO;
		PaymentType additionalPaymentType = null;
		HsTokenizeResponse hsResponse = null;
		String additionalPaymentTypeShortName = null;
		String additionalPaymentCreditCardNumber = null;
		Integer additionalPaymentExpirationDateMonth = null;
		Integer additionalPaymentExpirationDateYear = null;
		String additionalPaymentExpirationDate = null;
		String additionalPaymentAuthorizationNumber = null;
		String maskedAccountNo = null;
		String token = null;
		if (additionalPayment != null) {
			additionalPaymentAmount = additionalPayment.getPaymentAmount();
			additionalPaymentType = additionalPayment.getPaymentType();
			if (additionalPaymentType != null) {
				additionalPaymentTypeShortName = additionalPaymentType.getShortName();
			}
			additionalPaymentCreditCardNumber = additionalPayment.getCreditCardNumber();
			additionalPaymentExpirationDateMonth = additionalPayment.getExpirationDateMonth();
			additionalPaymentExpirationDateYear = additionalPayment.getExpirationDateYear();
			if (additionalPaymentExpirationDateMonth != null && additionalPaymentExpirationDateYear != null) {
				additionalPaymentExpirationDate = String.format("%02d%02d",
					additionalPaymentExpirationDateMonth,
					additionalPaymentExpirationDateYear % 100);
			}
			additionalPaymentAuthorizationNumber = additionalPayment.getAuthorizationNumber();
			maskedAccountNo = additionalPayment.getMaskedAccountNumber();
		    token = additionalPayment.getToken();
		}
	    //Invoking tokenizing web service to get token and masked account number.
	    if(null!= additionalPayment && (org.apache.commons.lang.StringUtils.isBlank(maskedAccountNo) 
	    		|| org.apache.commons.lang.StringUtils.isBlank(token))){
	    	try {
				hsResponse = hsTokenServiceCreditCardBo.tokenizeHSSTransaction(serviceOrder.getSoId(),additionalPaymentCreditCardNumber, serviceOrder.getAcceptedProviderResourceId());
			    if(null!= hsResponse){
			    	logger.info("Masked Account No :"+ hsResponse.getMaskedAccountNo());
			    	logger.info("Token :"+ hsResponse.getToken());
			    }
			    
			    
	    	} catch (SLBusinessServiceException e) {
	    		logger.info("Caught Exception and Ignoring");
				e.printStackTrace();
				
			}
	    	catch (Exception e) {
	    		logger.info("Caught Exception and Ignoring");
				e.printStackTrace();
			}
	    }
		// Code added to set masked account no and token in oms table for call close file generation.
		if(null!= hsResponse && org.apache.commons.lang.StringUtils.isBlank(maskedAccountNo)){
			maskedAccountNo = hsResponse.getMaskedAccountNo();
		}
		if(null!= hsResponse&& org.apache.commons.lang.StringUtils.isBlank(token)){
			token = hsResponse.getToken();
		}
		// Above code is added as part of SL-20853.
		String modelNumber = serviceOrder.getCustomRefValue(MarketESBConstant.CUSTOM_REF_Model_Number);
		String serialNumber = serviceOrder.getCustomRefValue(MarketESBConstant.CUSTOM_REF_Serial_Number);

		Long omsBuyerNotificationId = this.integrationBO.createNewOmsBuyerNotification(
				buyerNotificationId,
				serviceOrder.getResolutionDs(),
				modelNumber,
				serialNumber,
				serviceOrder.getCompletedDate(),
				additionalPaymentAmount,
				additionalPaymentTypeShortName,
				additionalPaymentCreditCardNumber,
				additionalPaymentExpirationDate,
				additionalPaymentAuthorizationNumber,
				maskedAccountNo,
				token
				);
		return omsBuyerNotificationId;
	}
	
	public IIntegrationBO getIntegrationBO() {
		return this.integrationBO;
	}
	
	public void setIntegrationBO(IIntegrationBO integrationBO) {
		this.integrationBO = integrationBO;
	}

	@Transactional
	@Retryable(maximumRetryAttempts=5, unlimitedRetriesAllowed=false, retryExceptions={DeadlockLoserDataAccessException.class})
	public void createNewUnsuccessfulBatch(String batchFileName,
			Long integrationId, String errorMessage) {
		this.integrationBO.createNewUnsuccessfulBatch(batchFileName, integrationId, errorMessage);
		return;
	}

	@Transactional
	@Retryable(maximumRetryAttempts=5, unlimitedRetriesAllowed=false, retryExceptions={DeadlockLoserDataAccessException.class})
	public void recordBatchError(String batchFileName, Long batchId,
			String errorMessage) {
		this.integrationBO.recordBatchError(batchFileName, batchId, errorMessage);
		return;
	}

	@Transactional
	@Retryable(maximumRetryAttempts=5, unlimitedRetriesAllowed=false, retryExceptions={DeadlockLoserDataAccessException.class})
	public void markTransactionAsSkipped(long transactionId, String skipReason) {
		this.integrationBO.markTransactionAsSkipped(transactionId, skipReason);
		return;
	}

	@Transactional
	@Retryable(maximumRetryAttempts=5, unlimitedRetriesAllowed=false, retryExceptions={DeadlockLoserDataAccessException.class})
	public boolean acquireLock(String lockName, Double lockExpirationMinutes) {
		return this.integrationBO.acquireLock(lockName, lockExpirationMinutes);
	}

	@Transactional
	@Retryable(maximumRetryAttempts=5, unlimitedRetriesAllowed=false, retryExceptions={DeadlockLoserDataAccessException.class})
	public void releaseLock(String lockName) {
		this.integrationBO.releaseLock(lockName);
		return;
	}

	@Transactional
	@Retryable(maximumRetryAttempts=5, unlimitedRetriesAllowed=false, retryExceptions={DeadlockLoserDataAccessException.class})
	public void setCustomReferencesByInputFile(String inputFileName) {
		this.integrationBO.setCustomReferencesByInputFile(inputFileName);
		return;
	}

	@Transactional
	@Retryable(maximumRetryAttempts=5, unlimitedRetriesAllowed=false, retryExceptions={DeadlockLoserDataAccessException.class})
	public void markTransactionsCompleteByInputFile(String inputFileName) {
		this.integrationBO.markTransactionsCompleteByInputFile(inputFileName);
		return;		
	}

	@Transactional
	@Retryable(maximumRetryAttempts=5, unlimitedRetriesAllowed=false, retryExceptions={DeadlockLoserDataAccessException.class})
	public void markNonUniqueNewTransactionsAsFailed(String inputFileName) {
		this.integrationBO.markNonUniqueNewTransactionsAsFailed(inputFileName);
	}

	@Transactional
	@Retryable(maximumRetryAttempts=5, unlimitedRetriesAllowed=false, retryExceptions={DeadlockLoserDataAccessException.class})
	public void markBatchAndTransactionsAsProcessed(long batchId,
			String outputFileName) {

		this.integrationBO.markBatchAndTransactionsAsProcessed(batchId, outputFileName);
		return;		
	}

	@Transactional
	@Retryable(maximumRetryAttempts=5, unlimitedRetriesAllowed=false, retryExceptions={DeadlockLoserDataAccessException.class})
	public void updateBatchFileName(String originalFileName,
			String newFileName) {
		
		this.integrationBO.updateBatchFileName(originalFileName, newFileName);
	}
	
	public String performAddNpsCancellationRetriesToBatch(Double retryHours,
			Long maxDbTransactions) {
		this.integrationBO.addCancellationRequestsWithoutResponsesToBatch(retryHours, maxDbTransactions);
		String resendErrorMessage = this.integrationBO.addCancelAuditRequestsMarkedAsResendToBatch(maxDbTransactions);
		return resendErrorMessage;
	}
	public IHSTokenizeServiceCreditCardBO getHsTokenServiceCreditCardBo() {
		return hsTokenServiceCreditCardBo;
	}

	public void setHsTokenServiceCreditCardBo(
			IHSTokenizeServiceCreditCardBO hsTokenServiceCreditCardBo) {
		this.hsTokenServiceCreditCardBo = hsTokenServiceCreditCardBo;
	}

}
