package com.servicelive.esb.integration.bo;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.servicelive.bus.event.order.OrderEventType;
import com.servicelive.esb.constant.MarketESBConstant;
import com.servicelive.esb.integration.BusinessException;
import com.servicelive.esb.integration.DataException;
import com.servicelive.esb.integration.dao.IIntegrationDao;
import com.servicelive.esb.integration.domain.AssurantBuyerNotification;
import com.servicelive.esb.integration.domain.Batch;
import com.servicelive.esb.integration.domain.BuyerNotification;
import com.servicelive.esb.integration.domain.Contact;
import com.servicelive.esb.integration.domain.Document;
import com.servicelive.esb.integration.domain.HsrBuyerNotification;
import com.servicelive.esb.integration.domain.IntegrationName;
import com.servicelive.esb.integration.domain.Location;
import com.servicelive.esb.integration.domain.Lock;
import com.servicelive.esb.integration.domain.OmsBuyerNotification;
import com.servicelive.esb.integration.domain.OmsBuyerNotificationResponse;
import com.servicelive.esb.integration.domain.OmsBuyerNotificationResponseMessage;
import com.servicelive.esb.integration.domain.Part;
import com.servicelive.esb.integration.domain.Phone;
import com.servicelive.esb.integration.domain.ProcessingStatus;
import com.servicelive.esb.integration.domain.ServiceOrder;
import com.servicelive.esb.integration.domain.Task;
import com.servicelive.esb.integration.domain.OmsTask;
import com.servicelive.esb.integration.domain.Transaction;
import com.servicelive.esb.integration.domain.TransactionType;
import com.sun.corba.se.spi.ior.MakeImmutable;
import com.newco.marketplace.dto.vo.serviceorder.Buyer;

public class IntegrationBO implements IIntegrationBO {
	private static final Logger logger = Logger.getLogger(IntegrationBO.class);
	
	private IIntegrationDao integrationDao;
	private DataSource dataSource;
	
	@Transactional
	public Long createNewUnsuccessfulBatch(String fileName, Long integrationId, String errorMessage) throws BusinessException {
		Batch batch = createBatch(fileName, integrationId, ProcessingStatus.ERROR, errorMessage); 
		return batch.getBatchId();
	}
	
	@Transactional
	public void recordBatchError(String fileName, Long batchId, String errorMessage) throws BusinessException {
		Batch batch = new Batch(batchId, null, fileName, null, ProcessingStatus.ERROR, errorMessage);
		this.integrationDao.updateBatch(batch);
		return;
	}
	
	@Transactional
	public void markBatchAndTransactionsAsProcessed(Long batchId, String fileName) throws BusinessException {
		logger.debug(String.format("Updating processing status of batch with batchId %d to %s", batchId, ProcessingStatus.SUCCESS.toString()));
		Batch batch = new Batch(batchId, null, fileName, null, ProcessingStatus.SUCCESS, null);
		this.integrationDao.updateBatch(batch);

		logger.debug(String.format("Attempting to find transactions with batchId %d and processing status %s", batchId, ProcessingStatus.READY_TO_PROCESS.toString()));
		List<Transaction> transactions = this.integrationDao.getTransactionsByBatchAndProcessingStatus(batchId, ProcessingStatus.READY_TO_PROCESS);

		logger.debug(String.format("Extracting transactionIds for %d transactions found having batchId %d and processing status %s", transactions.size(), batchId, ProcessingStatus.READY_TO_PROCESS.toString()));
		List<Long> transactionIds = new ArrayList<Long>(transactions.size());
		for (Transaction transaction : transactions) {
			transactionIds.add(transaction.getTransactionId());
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Updating processing status of transactions with transactionId IN (%s) to %s", StringUtils.collectionToCommaDelimitedString(transactionIds) ,ProcessingStatus.SUCCESS.toString()));
		}
		this.integrationDao.updateStatusForTransactionsList(transactionIds, ProcessingStatus.SUCCESS, null);
		return;
	}
	
	@Transactional
	public void updateBatchFileName(String originalFileName, String newFileName) {
		logger.debug("Entering updateBatchFileName");
		Batch batch = this.integrationDao.getBatchbyInputFile(originalFileName);
		batch.setFileName(newFileName);
		this.integrationDao.updateBatch(batch);
	}
	
	@Transactional
	public void updateBatchFileNameById(Long batchId, String updateFileName) {
		logger.debug("Entering updateBatchFileNameById");
		Batch batch = this.integrationDao.getBatchById(batchId);
		batch.setFileName(updateFileName);
		this.integrationDao.updateBatch(batch);
	}
	
	@Transactional
	public long createServiceOrder(Blob customRefs, Long transactionId,
			Double laborSpendLimit, Double partsSpendLimit, String title,
			String description, String providerInstructions, Date startDate,
			String startTime, Date endDate, String endTime, Long templateId,
			Boolean providerServiceConfirmInd, String partsSuppliedBy,
			Boolean serviceWindowTypeFixed, String mainServiceCategory,
			String buyerTermsAndConditions) {
		
		ServiceOrder serviceOrder = new ServiceOrder(
				0,
				customRefs,
				transactionId,
				laborSpendLimit,
				partsSpendLimit,
				title,
				description,
				providerInstructions,
				startDate,
				startTime,
				endDate,
				endTime,
				templateId,
				providerServiceConfirmInd,
				partsSuppliedBy,
				serviceWindowTypeFixed,
				mainServiceCategory,
				buyerTermsAndConditions);
		this.integrationDao.createServiceOrder(serviceOrder);
		return serviceOrder.getServiceOrderId();
	}
	
	@Transactional
	public void createPart(Part part) {
		this.integrationDao.createPart(part);
	}
	
	@Transactional
	public void markTransactionsAsProcessed(Collection<Long> transactionIds) throws BusinessException {
		this.integrationDao.updateStatusForTransactionsList(transactionIds, ProcessingStatus.SUCCESS, null);
		return;
	}
	
	@Transactional
	public void markTransactionAsSkipped(Long transactionId, String skipReason) throws BusinessException {
		Collection<Long> transactionIds = new ArrayList<Long>();
		transactionIds.add(transactionId);
		this.integrationDao.updateStatusForTransactionsList(transactionIds, ProcessingStatus.SKIPPED, skipReason);
	}
	
	@Transactional
	public void setCustomReferencesByInputFile(String inputFile)  throws BusinessException {
		List<ServiceOrder> serviceOrders = getIntegrationDao().getServiceOrdersByBatchInputFile(inputFile);
		int numberOfServiceOrders = serviceOrders == null ? 0 : serviceOrders.size();
		logger.info(String.format("Found %d serviceOrders belonging to batch for filename '%s'", numberOfServiceOrders, inputFile));

		for (ServiceOrder so : serviceOrders) {
			setCustomReferences(so);
		}	
	}
	
	@Transactional
	public void markTransactionsCompleteByInputFile(String inputFile)  throws BusinessException {
		this.integrationDao.markTransactionsCompleteByInputFile(inputFile);
	}
	
	private void setCustomReferences(ServiceOrder serviceOrder) {	
		//FIXME this needs to be cleaned up and made more generic...
		Map<String, String> customRefHashMap = null;
		List<Map<String, String>> omsCustomRefs = integrationDao.getOMSCustomReferencesByServiceOrderId(serviceOrder.getServiceOrderId());
		if (!CollectionUtils.isEmpty(omsCustomRefs)) {
			customRefHashMap = omsCustomRefs.get(0); // There should only be one custom references row
		}
		else {
			List<Map<String, String>> assurantCustomRefs = integrationDao.getAssurantCustomReferencesByServiceOrderId(serviceOrder.getServiceOrderId());
			if (!CollectionUtils.isEmpty(assurantCustomRefs)) {
				customRefHashMap = assurantCustomRefs.get(0); // There should only be one custom references row
			}
			else {
				List<Map<String, String>> hsrCustomRefs = integrationDao.getHSRCustomReferencesByServiceOrderId(serviceOrder.getServiceOrderId());
				if (!CollectionUtils.isEmpty(hsrCustomRefs)) {
					customRefHashMap = hsrCustomRefs.get(0); // There should only be one custom references row
				}
			}
		}
		
		if (customRefHashMap != null) {
			// save the service order with the custom ref hash map
			integrationDao.saveServiceOrderCustomRefs(serviceOrder, customRefHashMap);
		}
	}
	
	@Transactional(readOnly = true, propagation=Propagation.SUPPORTS)
	public List<String> getExistingExternalServiceOrdersIds(List<String> serviceOrderIds) throws BusinessException {
		return integrationDao.getExistingExternalServiceOrdersIds(serviceOrderIds);
	}
	
	@Transactional(readOnly = true, propagation=Propagation.SUPPORTS)
	public List<String> getLatestExistingExternalServiceOrderNumbersThatMatchBeforeTestSuffix(
			List<String> orderLookupIds, String testSuffix) throws BusinessException {
		List<String> resultList = new ArrayList<String>();
		for (String externalOrderNumber : orderLookupIds) {
			List<Transaction> transactions = integrationDao.getServiceOrdersThatMatchOrderNumberWithTestSuffix(externalOrderNumber, testSuffix);
			//FIXME Push down finding the latest externalOrderNumber to the query executed by the DAO
			// e.g. ORDER BY transaction.createdOn DESC Limit 1 should produce the right row.
			if (!CollectionUtils.isEmpty(transactions)) {
				Transaction latestTransaction = transactions.get(0);
				for (int i = 1; i < transactions.size(); i++) {
					Transaction transaction = transactions.get(i);
					if (transaction.getCreatedOn() != null && transaction.getCreatedOn().after(latestTransaction.getCreatedOn())) {
						latestTransaction = transaction;
					}
				}
				resultList.add(latestTransaction.getExternalOrderNumber());
			}
		}		
		return resultList;
	}
	
	@Transactional
	public Batch findCurrentOrCreateNewOpenBatchFor(IntegrationName integrationName) throws BusinessException {
		if (integrationName == null) {
			throw new IllegalArgumentException("The integrationName parameter was null. This method must be called with a non-null integrationName.");
		}
		
		List<Batch> batches = this.integrationDao.findBatchesFor(integrationName.getId(), ProcessingStatus.CREATED);
		Batch openBatch;
		if (CollectionUtils.isEmpty(batches)) {
			openBatch = this.createBatch(null, integrationName.getId(), ProcessingStatus.CREATED, null);
		}
		else if (batches.size() > 1) {
			throw new BusinessException(
					String.format(
							"Found %d batches for integration %s and processingStatus %s, when expecting at most 1 open batch",
							batches.size(), integrationName, ProcessingStatus.CREATED));
		}
		else {
			openBatch = batches.get(0);
		}
		
		return openBatch;
	}
	
	@Transactional(rollbackFor=Throwable.class)
	public void createAllOrders(Long buyerResourceId, String fileUploadId, List<Map<String, String>> orderFieldsList, Long batchId) throws Exception {
		for (Map<String, String> order : orderFieldsList) {
			createFileUploadOrder(buyerResourceId, fileUploadId, order, batchId);
		}
	}
	
	private void createFileUploadOrder(Long buyerResourceId, String fileUploadId, Map<String, String> orderFields, Long batchId) throws Exception {
		
		Transaction transaction = new Transaction();
		transaction.setBatchId(batchId);
		Calendar now = Calendar.getInstance();
		transaction.setTransactionType(TransactionType.NEW);
		//transaction.setExternalOrderNumber(?);
		transaction.setProcessAfter(now.getTime());
		transaction.setCreatedOn(now.getTime());
		transaction.setProcessingStatus(ProcessingStatus.CREATED);
		transaction.setBuyerResourceId(buyerResourceId);
		transaction.setExternalOrderNumber(String.format("%s_%s", fileUploadId, orderFields.get("Row Number")));
		this.integrationDao.createTransaction(transaction);
		
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		
		ServiceOrder serviceOrder = new ServiceOrder();
		serviceOrder.setTransactionId(transaction.getTransactionId());
		String laborPrice = orderFields.get("Maximum Price for Labor");
		try {
		serviceOrder.setLaborSpendLimit(convertPriceStringToDouble(laborPrice));
		} catch (Exception e) {
			logger.info("Error trying to set the Labor Price.  Leaving as NULL in the database", e);
		}
		String partsPrice = orderFields.get("Maximum Price for Materials");
		try {
		serviceOrder.setPartsSpendLimit(convertPriceStringToDouble(partsPrice));
		} catch (Exception e) {
			logger.info("Error trying to set the Parts Price.  Leaving as NULL in the database", e);
		}
		serviceOrder.setTitle(orderFields.get("Title"));
		serviceOrder.setDescription(orderFields.get("Overview (Public)"));
		serviceOrder.setProviderInstructions(orderFields.get("Special Instructions (Private)"));
		try {
			serviceOrder.setStartDate(df.parse(orderFields.get("Service Date")));
		} catch (ParseException e) {
			logger.info("Error trying to parse the Start Date.  Leaving as NULL in the database", e);
		}
		serviceOrder.setStartTime(orderFields.get("Service Time"));
		String endDate = orderFields.get("Service End Date");
		if (endDate != null && endDate.length() > 0) {
			try {
				serviceOrder.setEndDate(df.parse(endDate));
			} catch (ParseException e) {
				logger.info("Error trying to parse the End Date.  Leaving as NULL in the database", e);
			}
			serviceOrder.setEndTime(orderFields.get("Service End Time"));
		}
		String templateField = orderFields.get("Template Id");
		if (templateField != null && templateField.length() > 0) {
			try {
			serviceOrder.setTemplateId(Long.parseLong(templateField));
			} catch (Exception e) {
				logger.info("Error trying to set the Template Id.  Leaving as NULL in the database", e);
		}
		}
		boolean providerConfirm = orderFields.get("Provider To Confirm Time").equals("Yes");
		serviceOrder.setProviderServiceConfirmInd(providerConfirm);
        if(orderFields.get("Part And Material").toLowerCase().contains("required"))
            serviceOrder.setPartsSuppliedBy("PartsNotRequired");
        else
		    serviceOrder.setPartsSuppliedBy(orderFields.get("Part And Material"));
		boolean serviceWindowTypeFixed = orderFields.get("Service Date Type").equals("Fixed");
		serviceOrder.setServiceWindowTypeFixed(serviceWindowTypeFixed);
		serviceOrder.setMainServiceCategory(orderFields.get("Main Service Category"));
		serviceOrder.setBuyerTermsAndConditions(orderFields.get("Buyer T & C (Public)"));
		this.integrationDao.createServiceOrder(serviceOrder);
		
		Contact contact = new Contact();
		contact.setServiceOrderId(serviceOrder.getServiceOrderId());
		contact.setFirstName(orderFields.get("First Name"));
		contact.setLastName(orderFields.get("Last Name"));
		contact.setBusinessName(orderFields.get("Business Name"));
		contact.setEmail(orderFields.get("Email"));
		this.integrationDao.createContact(contact);
		
		String phone1Field = orderFields.get("Phone Number");
		if (phone1Field != null && phone1Field.length() > 0) {
			Phone phone1 = new Phone();
			phone1.setContactId(contact.getContactId());
			phone1.setPrimary(true);
			phone1.setPhoneNumber(phone1Field.replaceAll("-", "").trim());
			String phoneTypeField = orderFields.get("Phone Type");
			if (phoneTypeField != null) {
				phone1.setPhoneType(phoneTypeField.toUpperCase());
			}
			this.integrationDao.createPhone(phone1);
		}
		
		String phone2Field = orderFields.get("Alternate Phone Number");
		if (phone2Field != null && phone2Field.length() > 0) {
			Phone phone2 = new Phone();
			phone2.setContactId(contact.getContactId());
			phone2.setPrimary(false);
			phone2.setPhoneNumber(phone2Field.replaceAll("-", "").trim());
			String phoneTypeField = orderFields.get("Alternate Phone Type");
			if (phoneTypeField != null) {
				phone2.setPhoneType(phoneTypeField.toUpperCase());
			}
			this.integrationDao.createPhone(phone2);
		}
		
		String faxField = orderFields.get("Fax");
		if (faxField != null && faxField.length() > 0) {
			Phone fax = new Phone();
			fax.setContactId(contact.getContactId());
			fax.setPrimary(false);
			fax.setPhoneNumber(faxField);
			fax.setPhoneType("FAX");
			this.integrationDao.createPhone(fax);
		}
		
		Location location = new Location();
		location.setServiceOrderId(serviceOrder.getServiceOrderId());
		location.setLocationNotes(orderFields.get("Service Location Notes"));
		String locationClassificationField = orderFields.get("Location Type");
		if (locationClassificationField != null) {
			location.setLocationClassification(locationClassificationField.toUpperCase());
		}
		location.setAddressLine1(orderFields.get("Street Name1"));
		String addressLine2 = "";
		if (orderFields.get("Street Name2") != null) {
			addressLine2 += orderFields.get("Street Name2");
		}
		if (orderFields.get("Apt#") != null) {
			addressLine2 += orderFields.get("Apt#");
		}
		location.setAddressLine2(orderFields.get("Street Name2"));
		location.setCity(orderFields.get("City"));
		location.setState(orderFields.get("State"));
		location.setZipCode(orderFields.get("Zip Code"));
		this.integrationDao.createLocation(location);
		
		// Tasks
		String taskPrefix = "Task One_";
		createTask(orderFields, serviceOrder.getServiceOrderId(), taskPrefix, true);
		taskPrefix = "Task Two_";
		createTask(orderFields, serviceOrder.getServiceOrderId(), taskPrefix, false);
		taskPrefix = "Task Three_";
		createTask(orderFields, serviceOrder.getServiceOrderId(), taskPrefix, false);

		// Parts
		String partPrefix = "Part One_";
		createPart(orderFields, serviceOrder.getServiceOrderId(), partPrefix);
		partPrefix = "Part Two_";
		createPart(orderFields, serviceOrder.getServiceOrderId(), partPrefix);
		partPrefix = "Part Three_";
		createPart(orderFields, serviceOrder.getServiceOrderId(), partPrefix);
		
		// Documents
		String documentSuffix = "1?";
		createDocument(orderFields, serviceOrder.getServiceOrderId(), documentSuffix);
		documentSuffix = "2?";
		createDocument(orderFields, serviceOrder.getServiceOrderId(), documentSuffix);
		documentSuffix = "3?";
		createDocument(orderFields, serviceOrder.getServiceOrderId(), documentSuffix);
		String logoTitle = orderFields.get("Branding Information (Optional)?");
		if (logoTitle  != null && logoTitle.length() > 0) {
			Document logo = new Document();
			logo.setServiceOrderId(serviceOrder.getServiceOrderId());
			logo.setDocumentTitle(logoTitle);
			logo.setLogo(true);
			this.integrationDao.createDocument(logo);
		}
		
		// Create Custom Refs
		Map<String, String> customRefMap = new TreeMap<String, String>();
		for (int i = 0; i <= 9; i++) {
			String key = "Custom Reference Value " + String.valueOf(i);
			String value = orderFields.get(key);
			if (value != null && value.length() > 0) {
				customRefMap.put(key, value);
			}
		}
		integrationDao.saveServiceOrderCustomRefs(serviceOrder, customRefMap);
	}
	
	private void createDocument(Map<String, String> orderFields,
			long serviceOrderId, String documentSuffix) {
		String attachmentField = orderFields.get("Attachment" + documentSuffix);
		if (attachmentField != null && attachmentField.length() > 0) {
			Document document = new Document();
			document.setServiceOrderId(serviceOrderId);
			document.setDocumentTitle(attachmentField);
			document.setDescription(orderFields.get("Desription" + documentSuffix));
			document.setLogo(false);
			this.integrationDao.createDocument(document);
		}
	}

	private void createPart(Map<String, String> orderFields,
			long serviceOrderId, String partPrefix) {
		String partManufacturerField = orderFields.get(partPrefix + "Manufacturer");
		if (partManufacturerField != null && partManufacturerField.length() > 0) {
			Part part = new Part();
			part.setServiceOrderId(serviceOrderId);
			part.setManufacturer(partManufacturerField);
			part.setPartName(orderFields.get(partPrefix + "Part Name"));
			part.setModelNumber(orderFields.get(partPrefix + "Model Number"));
			part.setDescription(orderFields.get(partPrefix + "Description"));
			try {
			part.setQuantity(Integer.parseInt(orderFields.get(partPrefix + "Quantity")));
			} catch (Exception e) {
				logger.info("Error trying to set the Part Quantity.  Leaving as NULL in the database", e);
			}
			String carrierField = orderFields.get(partPrefix + "Inbound Carrier");
			if (carrierField != null && carrierField.length() > 0) {
				part.setInboundCarrier(carrierField);
			}
			part.setInboundTrackingNumber(orderFields.get(partPrefix + "Inbound Tracking#"));
			carrierField = orderFields.get(partPrefix + "Outbound Carrier");
			if (carrierField != null && carrierField.length() > 0) {
				part.setOutboundCarrier(carrierField);
			}
			part.setOutboundTrackingNumber(orderFields.get(partPrefix + "Outbound Tracking#"));
			this.integrationDao.createPart(part);
		}
		
	}

	private void createTask(Map<String, String> orderFields,
			long serviceOrderId, String taskPrefix, boolean defaultTask) {
		
		String taskField = orderFields.get(taskPrefix + "Task Name");
		if (taskField != null && taskField.length() > 0) {
			Task task = new Task();
			task.setServiceOrderId(serviceOrderId);
			task.setTitle(taskField);
			task.setCategory(orderFields.get(taskPrefix + "Category"));
			task.setSubCategory(orderFields.get(taskPrefix + "Sub Category"));
			task.setServiceType(orderFields.get(taskPrefix + "Skill"));
			task.setComments(orderFields.get(taskPrefix + "Task Comments"));
			task.setDefaultTask(defaultTask);
			this.integrationDao.createTask(task);
		}
		
	}

	private Double convertPriceStringToDouble(String amount) {
		String amountString = null;
		if (amount != null) {
			amountString = amount.replace("$", "");
		}
		
		if (amountString != null && amountString.length() > 0) {
			return Double.parseDouble(amountString);
		} else {
			return null;
		}
	}

	@Transactional
	public Batch createBatch(String inputFileName, Long integrationId, ProcessingStatus processingStatus, String errorMessage) {
		Batch batch = new Batch();
		batch.setIntegrationId(integrationId);
		batch.setFileName(inputFileName);
		batch.setCreatedOn(Calendar.getInstance().getTime());
		batch.setProcessingStatus(processingStatus);
		if (errorMessage != null) {
			batch.setException(errorMessage);
		}
		
		this.integrationDao.createBatch(batch);
		return batch;
	}
	
	@Transactional
	public void updateBatch(Batch batch) {
		this.integrationDao.updateBatch(batch);
	}
	
	@Transactional
	public Batch getCurrentAndPrepareNewOutgoingBatchFor(IntegrationName integrationName) throws BusinessException {
		if (integrationName == null) {
			throw new IllegalArgumentException("The integrationName parameter was null. This method must be called with a non-null integrationName.");
		}
		
		List<Batch> batches = this.integrationDao.findBatchesFor(integrationName.getId(), ProcessingStatus.CREATED);
		Batch currentBatch;
		if (CollectionUtils.isEmpty(batches)) {
			logger.warn(String.format("Unable to find any batch with integrationId %d and ProcessingStatus '%s'.", integrationName.getId(), ProcessingStatus.CREATED));
			currentBatch = null;
		}
		else if (batches.size() > 1) {
			throw new BusinessException(
					String.format(
							"Found %d batches for integration %s with processingStatus %s, when expecting at most 1 open batch",
							batches.size(), integrationName, ProcessingStatus.CREATED));
		}
		else {
			currentBatch = batches.get(0);
		}

		if (currentBatch != null) {
			long countOfPendingCloseNotifications = this.integrationDao.getTransactionCountByBatchAndProcessingStatus(currentBatch.getBatchId(), ProcessingStatus.READY_TO_PROCESS);
			if (countOfPendingCloseNotifications == 0) {
				logger.debug(String.format("No unprocessed transactions found for batch %d.", currentBatch.getBatchId()));
				return null;
			}
			
			// put current batch in PROCESSING state and create a new batch in CREATED state
			currentBatch.setProcessingStatus(ProcessingStatus.PROCESSING);
			this.integrationDao.updateBatch(currentBatch);
			
			@SuppressWarnings("unused")
			Batch newBatch = 
				this.createBatch(null, integrationName.getId(), ProcessingStatus.CREATED, null);
		}
		return currentBatch;
	}
	
	@Transactional(readOnly = true, propagation=Propagation.SUPPORTS)
	public ServiceOrder findLatestServiceOrderByExternalOrderNumber(String externalOrderNumber) throws BusinessException {
		if (externalOrderNumber == null) {
			throw new IllegalArgumentException("The externalOrderNumber parameter was null. This method must be called with a non-null externalOrderNumber.");
		}
		
		ServiceOrder serviceOrder = null;
		try {
		serviceOrder = this.integrationDao
				.findLatestServiceOrderByExternalOrderNumber(externalOrderNumber);
		}
		catch (DataException e) {
			Throwable cause = e.getCause();
			if (cause instanceof EmptyResultDataAccessException) {
				logger.warn(String.format("Unable to find incoming service order request for externalOrderNumber '%s'.", externalOrderNumber));
			}
			else {
				throw e;
			}
		}

		return serviceOrder;
	}

	@Transactional
	public Long createNewBuyerNotificationTransaction(Long batchId,
			String externalOrderNumber, String serviceLiveOrderId) throws BusinessException {
		Transaction transaction = new Transaction(0, batchId,
				TransactionType.BUYER_NOTIFICATION, externalOrderNumber,
				serviceLiveOrderId, new Date(), ProcessingStatus.READY_TO_PROCESS,
				new Date(), null, null);
		
		logger.info("transaction.processAfter() : " + transaction.getProcessAfter());
		logger.info("transaction.createdOn() : " + transaction.getCreatedOn());
		
		this.integrationDao.createTransaction(transaction);
		return transaction.getTransactionId();
	}

	@Transactional
	public Long createNewBuyerNotification(Long transactionId,
			String notificationEvent, String notificationEventSubType,
			Long relatedServiceOrderId) throws BusinessException {
		BuyerNotification notification = new BuyerNotification(0,
				transactionId, notificationEvent, notificationEventSubType,
				relatedServiceOrderId);
		this.integrationDao.createBuyerNotification(notification);
		return notification.getBuyerNotificationId();
	}
	
	@Transactional
	public Long createNewOmsBuyerNotification(Long buyerNotificationId,
			String techComment, String modelNumber, String serialNumber,
			Date installationDate, BigDecimal amountCollected,
			String paymentMethod, String paymentAccountNumber,
			String paymentExpirationDate, String paymentAuthorizationNumber)
			throws BusinessException {
		
		OmsBuyerNotification notification = new OmsBuyerNotification(0,
				buyerNotificationId, techComment, modelNumber, serialNumber,
				installationDate, amountCollected, paymentMethod,
				paymentAccountNumber, paymentExpirationDate,
				paymentAuthorizationNumber);
		this.integrationDao.createOmsBuyerNotification(notification);
		return notification.getOmsBuyerNotificationId();

	}
	@Transactional
	public Long createNewOmsBuyerNotification(Long buyerNotificationId,
			String techComment, String modelNumber, String serialNumber,
			Date installationDate, BigDecimal amountCollected,
			String paymentMethod, String paymentAccountNumber,
			String paymentExpirationDate, String paymentAuthorizationNumber,
			String maskedAccountNo,String token)
			throws BusinessException {
		
		OmsBuyerNotification notification = new OmsBuyerNotification(0,
				buyerNotificationId, techComment, modelNumber, serialNumber,
				installationDate, amountCollected, paymentMethod,
				paymentAccountNumber, paymentExpirationDate,
				paymentAuthorizationNumber,maskedAccountNo,token);
		this.integrationDao.createOmsBuyerNotification(notification);
		return notification.getOmsBuyerNotificationId();

	}
	@Transactional()
	public Long createNewAssurantBuyerNotification(Long buyerNotificationId,
			Date etaOrShippingDate, String shippingAirBillNumber, 
			String returnAirBillNumber, String incidentActionDescription) throws BusinessException {
		
		AssurantBuyerNotification notification = new AssurantBuyerNotification(0, 
				buyerNotificationId, etaOrShippingDate, shippingAirBillNumber, 
				returnAirBillNumber, incidentActionDescription);
		this.integrationDao.createAssurantBuyerNotification(notification);
		return notification.getAssurantBuyerNotificationId();
		
	}
	
	@Transactional()
	public Long createNewHsrBuyerNotification(Long buyerNotificationId,
			String unitNumber, String orderNumber, Date routedDate,
			String techId) throws BusinessException {
		
		HsrBuyerNotification notification = new HsrBuyerNotification(0,
				buyerNotificationId, unitNumber, orderNumber, routedDate,
				techId);
		this.integrationDao.createHsrBuyerNotification(notification);
		return notification.getHsrBuyerNotificationId();		
	}

	@Transactional(readOnly = true, propagation=Propagation.SUPPORTS)
	public long getTransactionCountByBatchAndProcessingStatus(Long batchId,
			ProcessingStatus processingStatus) throws BusinessException {
		long count = this.integrationDao.getTransactionCountByBatchAndProcessingStatus(batchId, processingStatus);
		return count;
	}
	
	@Transactional(readOnly = true, propagation=Propagation.SUPPORTS)
	public long getSuccessfulBuyerNotificationTransactionCount(
			String externalOrderNumber, OrderEventType orderEventType) 
			throws BusinessException {
		if (externalOrderNumber == null || externalOrderNumber.length() == 0) {
			return 0;
		}
		return this.integrationDao.getBuyerNotificationTransactionCount(externalOrderNumber, ProcessingStatus.SUCCESS, orderEventType.toString());
	}

	@Transactional(readOnly = true, propagation=Propagation.SUPPORTS)
	public List<Transaction> getTransactionsByBatchAndProcessingStatus(Long batchId,
			ProcessingStatus processingStatus) throws BusinessException {

		List<Transaction> transactions = this.integrationDao.getTransactionsByBatchAndProcessingStatus(batchId, processingStatus);
		return transactions;
	}
	
	@Transactional(readOnly = true, propagation=Propagation.SUPPORTS)
	public List<Transaction> getTransactionsByBatchId(Long batchId) throws BusinessException {
		return this.integrationDao.getTransactionsByBatchId(batchId);
	}
	
	@Transactional(readOnly = true, propagation=Propagation.SUPPORTS)
	public List<Transaction> getTransactionsByBatchFileName(String fileName) throws BusinessException {
		Batch batch = this.integrationDao.getBatchbyInputFile(fileName);
		if (batch != null) {
			return this.getTransactionsByBatchId(batch.getBatchId());
		} else {
			return new ArrayList<Transaction>();
		}
	}
	
	@Transactional(readOnly = true, propagation=Propagation.SUPPORTS)
	public BuyerNotification getBuyerNotificationByTransactionId(Long transactionId) throws BusinessException {
		return this.integrationDao.getBuyerNotificationByTransactionId(transactionId);
	}
	
	@Transactional(readOnly = true, propagation=Propagation.SUPPORTS)
	public AssurantBuyerNotification getAssurantBuyerNotificationByBuyerNotificationId(Long buyerNotificationId) throws BusinessException {
		return this.integrationDao.getAssurantBuyerNotificationByBuyerNotificationId(buyerNotificationId);
	}
	
	@Transactional(readOnly = true, propagation=Propagation.SUPPORTS)
	public HsrBuyerNotification getHsrBuyerNotificationByBuyerNotificationId(
			Long buyerNotificationId) throws BusinessException {
		return this.integrationDao.getHsrBuyerNotificationByBuyerNotificationId(buyerNotificationId);
	}
	
	@Transactional(readOnly = true, propagation=Propagation.SUPPORTS)
	public ServiceOrder getServiceOrderByTransactionId(Long transactionId) throws BusinessException {
		return this.integrationDao.getServiceOrderByTransactionId(transactionId);
	}
	
	@Transactional(readOnly = true, propagation=Propagation.SUPPORTS)
	public List<Part> getPartsByServiceOrderId(Long serviceOrderId) throws BusinessException {
		return this.integrationDao.getPartsByServiceOrderId(serviceOrderId);
	}
	
	@Transactional(readOnly = true, propagation=Propagation.SUPPORTS)
	public List<OmsBuyerNotificationResponse> getOmsBuyerNotificationResponsesByTransactions(
			List<Transaction> transactions) {
		
		List<Long> transactionIds = new ArrayList<Long>();
		for (Transaction transaction : transactions) {
			transactionIds.add(transaction.getTransactionId());
		}
		
		List<OmsBuyerNotificationResponse> responses = integrationDao.getOmsBuyerNotificationResponsesByTransactionIds(transactionIds);
		List<OmsBuyerNotificationResponseMessage> messages = integrationDao.getOmsBuyerNotificationResponseMessagesByTransactionIds(transactionIds);
		addMessagesToOmsBuyerNotificationResponses(responses, messages);
		
		return responses;
	}

	private void addMessagesToOmsBuyerNotificationResponses(
			List<OmsBuyerNotificationResponse> responses,
			List<OmsBuyerNotificationResponseMessage> messages) {
		
		for (OmsBuyerNotificationResponseMessage message : messages) {
			
			for (OmsBuyerNotificationResponse response : responses) {
				if (message.getBuyerNotificationResponseId().equals(response.getBuyerNotificationResponseId())) {
					response.addMessage(message);
					break;
				}
			}
		}	
	}
	
	@Transactional
	public void addCallCloseRequestsWithoutResponsesToBatch(Double retryHours, Long transactionLimit) {
		try {
			Long retryMinutes = Math.round(retryHours * 60D);
			// Removing looping for retry logic, only 1000 transactions to be retried per batch
			
			
			List<Transaction> transactionsToRetry = integrationDao.getCallCloseTransationsWithoutResponses(retryMinutes, transactionLimit);
			List<Long> retriedTransactionIds  = null;
			
			if (!CollectionUtils.isEmpty(transactionsToRetry)) {
				retriedTransactionIds = retryCallCloseTransactions(transactionsToRetry);
				
				// Mark Call Close transactions as resent
				if (!CollectionUtils.isEmpty(retriedTransactionIds)) {
					integrationDao.updateStatusForTransactionsList(retriedTransactionIds, ProcessingStatus.RESENT, null);
				}
			}
			
		} catch (Exception e) {
			logger.error("An error occurred while attempting to add call close requests without responses to the outgoing RI batch.  The transaction will be rolled back.", e);
			throw new RuntimeException("An error occurred while attempting to add call close requests without responses to the outgoing RI batch.  The transaction will be rolled back.", e);
		}
	}
	
	@Transactional
	public String addAuditRequestsMarkedAsResendToBatch(Long transactionLimit) {
		try {
			StringBuilder errorMessageSb = new StringBuilder();
			errorMessageSb.append("The following call close audit transactions were not able to be resent:");
			boolean sendErrorMessage = false;
			
			boolean workRemainsToBeDone = true;
			do {
				List<Transaction> auditTransactionsMarkedForResend = integrationDao.getCloseAuditTransationsMarkedForResend(transactionLimit);
				List<Transaction> callCloseTransactionsToRetry = new ArrayList<Transaction>();
				List<Long> retriedCallCloseTransactionIds = null;
				
				if (!CollectionUtils.isEmpty(auditTransactionsMarkedForResend)) {					
					List<Long> resendAuditTransactionIds = new ArrayList<Long>();
					Map<Long, Long> auditTransactionToCallCloseTransactionMap = new HashMap<Long, Long>();
			
					for (Transaction auditTransaction : auditTransactionsMarkedForResend) {
						Transaction transactionToRetry = null;
						try {
							transactionToRetry = integrationDao.getCallCloseTransactionByAuditTransactionId(auditTransaction.getTransactionId());
						} catch (DataException e) {
							logger.error(String.format("Not able to find a call close transaction to match audit transaction with ID %d", auditTransaction.getTransactionId()), e);
						}	
						if (transactionToRetry != null) {
							callCloseTransactionsToRetry.add(transactionToRetry);				
							resendAuditTransactionIds.add(auditTransaction.getTransactionId());
							auditTransactionToCallCloseTransactionMap.put(auditTransaction.getTransactionId(), transactionToRetry.getTransactionId());
						}
					}
					
					retriedCallCloseTransactionIds = retryCallCloseTransactions(callCloseTransactionsToRetry);
					
					// Remove any transactionIds from the resentAuditTransactionIds list that do not 
					// have corresponding transactions in the retriedCallCloseTransactionIds list
					for (Iterator<Long> i = resendAuditTransactionIds.iterator(); i.hasNext(); ) {
						Long auditTransactionId = i.next();
						Long callCloseTransactionId = auditTransactionToCallCloseTransactionMap.get(auditTransactionId);
						if (!retriedCallCloseTransactionIds.contains(callCloseTransactionId)) {
							i.remove();
							errorMessageSb.append(auditTransactionId);
							errorMessageSb.append(", ");
							sendErrorMessage = true;
						}				
					}
					
					// Mark the Audit transactions as resent, since that's where they were originally marked as ready to resend.
					if (!CollectionUtils.isEmpty(resendAuditTransactionIds)) {
						integrationDao.updateStatusForTransactionsList(resendAuditTransactionIds, ProcessingStatus.RESENT, null);
					}
				}
				
				// Keep looping if we hit the transaction limit.  Also make sure that we retried at least 1 transaction, 
				// otherwise it will continue looping indefinitely on the same transactions
				workRemainsToBeDone = callCloseTransactionsToRetry != null && callCloseTransactionsToRetry.size() == transactionLimit && !CollectionUtils.isEmpty(retriedCallCloseTransactionIds);
			} while (workRemainsToBeDone);
			
			if (sendErrorMessage) {
				return errorMessageSb.toString();
			}
			
		} catch (Exception e) {
			logger.error("An error occurred while attempting to add call close requests whose audit responses have been marked for resending to the outgoing RI batch.  The transaction will be rolled back.", e);
			throw new RuntimeException("An error occurred while attempting to add call close requests whose audit responses have been marked for resending to the outgoing RI batch.  The transaction will be rolled back.", e);
		}
		return "";
	}
	
	private List<Long> retryCallCloseTransactions(List<Transaction> callCloseTransactionsToRetry) {
		final String methodName = "retryCallCloseTransactions";
		logger.info(String.format("entered %s", methodName));

		if (CollectionUtils.isEmpty(callCloseTransactionsToRetry)) {
			logger.debug("The list of transactions to be retried is empty.");
			logger.info(String.format("exiting %s", methodName));
			return Collections.emptyList();
		}
			
		if (logger.isDebugEnabled()) logger.debug(String.format("%d transactions will be retried", callCloseTransactionsToRetry.size()));

			// Create new transactions on the open batch to retry these transactions
			Batch openBatch = findCurrentOrCreateNewOpenBatchFor(IntegrationName.RI_OUTBOUND);
		if (logger.isDebugEnabled()) logger.debug(String.format("Retries will be placed in batch: %s", openBatch.toString()));
			
			List<Long> transactionIdsToRetry = convertTransactionListToIdList(callCloseTransactionsToRetry);
		if (logger.isDebugEnabled()) logger.debug(String.format("Transactions to be retried have the follwoing transactionIds: ", StringUtils.collectionToCommaDelimitedString(transactionIdsToRetry)));
		
			List<BuyerNotification> buyerNotificationsToRetry = integrationDao.getBuyerNotificationsByTransactionIds(transactionIdsToRetry);
		Map<Long, BuyerNotification> buyerNotificationsToRetryParentIdMap = new HashMap<Long, BuyerNotification>();
		List<Long> buyerNotificationIdsToRetry = convertBuyerNotificationListToIdListAndParentIdMap(buyerNotificationsToRetry, buyerNotificationsToRetryParentIdMap);
		if (logger.isDebugEnabled()) logger.debug(String.format("BuyerNotifications to be retried have the follwoing buyerNotificationIds: ", StringUtils.collectionToCommaDelimitedString(buyerNotificationIdsToRetry)));
		
			List<OmsBuyerNotification> omsBuyerNotificationsToRetry = integrationDao.getOmsBuyerNotificationsByBuyerNotificationIds(buyerNotificationIdsToRetry);
		Map<Long, OmsBuyerNotification> omsBuyerNotificationsToRetryParentIdMap = new HashMap<Long, OmsBuyerNotification>();
		convertOmsBuyerNotificationListToParentIdMap(omsBuyerNotificationsToRetry, omsBuyerNotificationsToRetryParentIdMap);
		if (logger.isDebugEnabled()) logger.debug(String.format("OmsBuyerNotifications to be retried have the follwoing omsBuyerNotificationIds: ", StringUtils.collectionToCommaDelimitedString(omsBuyerNotificationsToRetryParentIdMap.keySet())));

		List<ServiceOrder> serviceOrdersToRetry = integrationDao.getServiceOrdersByTransactionIds(transactionIdsToRetry);
		Map<Long, ServiceOrder> serviceOrdersToRetryParentIdMap = new HashMap<Long, ServiceOrder>();
		List<Long> serviceOrderIdsToRetry = convertServiceOrderListToIdListAndParentIdMap(serviceOrdersToRetry, serviceOrdersToRetryParentIdMap);
		if (logger.isDebugEnabled()) logger.debug(String.format("ServiceOrder to be retried have the follwoing serviceOrderIds: ", StringUtils.collectionToCommaDelimitedString(serviceOrderIdsToRetry)));
			
		List<Task> tasksToRetry = integrationDao.getTasksByServiceOrderIdsList(serviceOrderIdsToRetry);
		Map<Long, List<Task>> tasksToRetryParentIdMap = new HashMap<Long, List<Task>>();
		List<Long> taskIdsToRetry = convertTaskListToIdListAndParentIdMap(tasksToRetry, tasksToRetryParentIdMap);
		if (logger.isDebugEnabled()) logger.debug(String.format("Tasks to be retried have the follwoing taskIds: ", StringUtils.collectionToCommaDelimitedString(taskIdsToRetry)));
		
		List<OmsTask> omsTasksToRetry = integrationDao.getOmsTasksByTaskIds(taskIdsToRetry);
		Map<Long, List<OmsTask>> omsTasksToRetryParentIdMap = new HashMap<Long, List<OmsTask>>();
		convertOmsTaskListToParentIdMap(omsTasksToRetry, omsTasksToRetryParentIdMap);
		if (logger.isDebugEnabled()) logger.debug(String.format("OmsTasks to be retried have the follwoing omsTaskIds: ", StringUtils.collectionToCommaDelimitedString(omsTasksToRetryParentIdMap.keySet())));
		
		List<Long> retriedTransactionIds = new ArrayList<Long>();
			for (Transaction transactionToRetry : callCloseTransactionsToRetry) {
			if (logger.isDebugEnabled()) logger.debug(String.format("Ready to process the following transaction: %s", transactionToRetry));
			BuyerNotification buyerNotificationToRetryForTransaction =  buyerNotificationsToRetryParentIdMap.get(transactionToRetry.getTransactionId());
			if (buyerNotificationToRetryForTransaction == null) {
				logger.error(String.format("Transaction with transactionId %d does not have a buyer notification record associated with it.", transactionToRetry.getTransactionId()));
					continue;
				}
			if (logger.isDebugEnabled()) logger.debug(String.format("Ready to process the following BuyerNotification for retry: %s", buyerNotificationToRetryForTransaction));
			
			OmsBuyerNotification omsBuyerNotificationToRetryForTransaction =  omsBuyerNotificationsToRetryParentIdMap.get(buyerNotificationToRetryForTransaction.getBuyerNotificationId());
			if (omsBuyerNotificationToRetryForTransaction == null) {
				logger.error(String.format("BuyerNotification with buyerNotificationId %d does not have an oms buyer notification record associated with it.", buyerNotificationToRetryForTransaction.getBuyerNotificationId()));
					continue;
				}
			if (logger.isDebugEnabled()) logger.debug(String.format("Ready to process the following OmsBuyerNotification for retry: %s", omsBuyerNotificationToRetryForTransaction));
				
			ServiceOrder serviceOrderToRetryForTransaction = serviceOrdersToRetryParentIdMap.get(transactionToRetry.getTransactionId());
			if (serviceOrderToRetryForTransaction == null) {
				logger.error(String.format("Transaction with transactionId %d does not have a service order record associated with it.", transactionToRetry.getTransactionId()));
				continue;
			}
			if (logger.isDebugEnabled()) logger.debug(String.format("Ready to process the following ServiceOrder for retry: %s", serviceOrderToRetryForTransaction));
			
			List<Task> tasksToRetryForTransaction = tasksToRetryParentIdMap.get(serviceOrderToRetryForTransaction.getServiceOrderId());
			if (CollectionUtils.isEmpty(tasksToRetryForTransaction)) {
				logger.error(String.format("ServiceOrder with serviceOrderId %d does not have any task records associated with it.", serviceOrderToRetryForTransaction.getServiceOrderId()));
				continue;
			}
			
			boolean isOmsTaskMissing = false;
			for (Task taskToRetryForTransaction : tasksToRetryForTransaction) {
				if (logger.isDebugEnabled()) logger.debug(String.format("Ready to process the following Task for retry: %s", taskToRetryForTransaction));

				List<OmsTask> omsTasksToRetryForTransactionTask = omsTasksToRetryParentIdMap.get(taskToRetryForTransaction.getTaskId());
				if (CollectionUtils.isEmpty(omsTasksToRetryForTransactionTask)) {
					logger.error(String.format("Task with taskId %d does not have any task_oms records associated with it.", taskToRetryForTransaction.getTaskId()));
					isOmsTaskMissing = true;
					continue;
				}
				if (logger.isDebugEnabled()) logger.debug(String.format("Ready to process %d OmsTasks for retry for this transactions.", omsTasksToRetryForTransactionTask.size()));
			}
			
			if (isOmsTaskMissing) {
				logger.error("Transaction will be skipped as tasks with no corresponding oms task records were found.");
				continue;
			}

				Long newTransactionId = createNewBuyerNotificationTransaction(openBatch.getBatchId(), transactionToRetry.getExternalOrderNumber(), transactionToRetry.getServiceLiveOrderId());
			if (logger.isDebugEnabled()) logger.debug(String.format("created new buyer notification transaction with transactionId %d", newTransactionId));

			Long newBuyerNotificationId = createNewBuyerNotification(newTransactionId, buyerNotificationToRetryForTransaction.getNotificationEvent(), buyerNotificationToRetryForTransaction.getNotificationEventSubType(), buyerNotificationToRetryForTransaction.getRelatedServiceOrderId());
			if (logger.isDebugEnabled()) logger.debug(String.format("created new buyerNotification record with buyerNotificationId %d", newBuyerNotificationId));
				
			Long newOmsBuyerNotificationId = createNewOmsBuyerNotification(newBuyerNotificationId, omsBuyerNotificationToRetryForTransaction.getTechComment(), omsBuyerNotificationToRetryForTransaction.getModelNumber(), omsBuyerNotificationToRetryForTransaction.getSerialNumber(), omsBuyerNotificationToRetryForTransaction.getInstallationDate(), omsBuyerNotificationToRetryForTransaction.getAmountCollected(), omsBuyerNotificationToRetryForTransaction.getPaymentMethod(), omsBuyerNotificationToRetryForTransaction.getPaymentAccountNumber(), omsBuyerNotificationToRetryForTransaction.getPaymentExpirationDate(), omsBuyerNotificationToRetryForTransaction.getPaymentAuthorizationNumber(),
					omsBuyerNotificationToRetryForTransaction.getMaskedAccountNo(),omsBuyerNotificationToRetryForTransaction.getToken());
			if (logger.isDebugEnabled()) logger.debug(String.format("created new omsBuyerNotification record with omsBuyerNotificationId %d", newOmsBuyerNotificationId));

			Long newServiceOrderId = createServiceOrder(null,
					newTransactionId, serviceOrderToRetryForTransaction.getLaborSpendLimit(),
					serviceOrderToRetryForTransaction.getPartsSpendLimit(),
					serviceOrderToRetryForTransaction.getTitle(),
					serviceOrderToRetryForTransaction.getDescription(),
					serviceOrderToRetryForTransaction.getProviderInstructions(),
					serviceOrderToRetryForTransaction.getStartDate(),
					serviceOrderToRetryForTransaction.getStartTime(),
					serviceOrderToRetryForTransaction.getEndDate(),
					serviceOrderToRetryForTransaction.getEndTime(),
					serviceOrderToRetryForTransaction.getTemplateId(),
					serviceOrderToRetryForTransaction.getProviderServiceConfirmInd(),
					serviceOrderToRetryForTransaction.getPartsSuppliedBy(),
					serviceOrderToRetryForTransaction.getServiceWindowTypeFixed(),
					serviceOrderToRetryForTransaction.getMainServiceCategory(),
					serviceOrderToRetryForTransaction.getBuyerTermsAndConditions());
			if (logger.isDebugEnabled()) logger.debug(String.format("created new serviceOrder record with serviceOrderId %d", newServiceOrderId));

			for (Task taskToRetryForTransaction : tasksToRetryForTransaction) {
				List<OmsTask> omsTasksToRetryForTransactionTask = omsTasksToRetryParentIdMap.get(taskToRetryForTransaction.getTaskId());
				Long newTaskId = createTask(newServiceOrderId,
						taskToRetryForTransaction.getTitle(),
						taskToRetryForTransaction.getComments(),
						taskToRetryForTransaction.getDefaultTask(),
						taskToRetryForTransaction.getExternalSku(),
						taskToRetryForTransaction.getSpecialtyCode(),
						taskToRetryForTransaction.getAmount(),
						taskToRetryForTransaction.getCategory(),
						taskToRetryForTransaction.getSubCategory(),
						taskToRetryForTransaction.getServiceType(),
						taskToRetryForTransaction.getSequenceNumber());
				if (logger.isDebugEnabled()) logger.debug(String.format("created new task record with taskId %d", newTaskId));
				
				for (OmsTask omsTaskToRetryForTransactionTask : omsTasksToRetryForTransactionTask) {
					Long newOmsTaskId = createOmsTask(newTaskId,
							omsTaskToRetryForTransactionTask.getChargeCode(),
							omsTaskToRetryForTransactionTask.getCoverage(),
							omsTaskToRetryForTransactionTask.getType(),
							omsTaskToRetryForTransactionTask.getDescription());
					if (logger.isDebugEnabled()) logger.debug(String.format("created new omsTask record with omsTaskId %d", newOmsTaskId));
				}
			}
			
				retriedTransactionIds.add(transactionToRetry.getTransactionId());
			if (logger.isDebugEnabled()) logger.debug(String.format("Successfully processed transaction with transactionId %d", transactionToRetry.getTransactionId()));
			}
		return retriedTransactionIds;
	}
	
	private void convertOmsTaskListToParentIdMap(List<OmsTask> omsTasks,
			Map<Long, List<OmsTask>> parentIdMap) {
		
		if (CollectionUtils.isEmpty(omsTasks)) {
			return;
				}
		
		for (OmsTask omsTask : omsTasks) {
			List<OmsTask> siblings = parentIdMap.get(omsTask.getTaskId());
			if (siblings == null) {
				siblings = new ArrayList<OmsTask>();
				parentIdMap.put(omsTask.getTaskId(), siblings);
			}
			siblings.add(omsTask);
		}
	}

	private void convertOmsBuyerNotificationListToParentIdMap(
			List<OmsBuyerNotification> omsBuyerNotifications,
			Map<Long, OmsBuyerNotification> parentIdMap) {
	
		if (CollectionUtils.isEmpty(omsBuyerNotifications)) {
			return;
		}
		
		for (OmsBuyerNotification omsBuyerNotification : omsBuyerNotifications) {
			parentIdMap.put(omsBuyerNotification.getBuyerNotificationId(), omsBuyerNotification);
				}
			}

	private List<Long> convertTaskListToIdListAndParentIdMap(List<Task> tasks,
			Map<Long, List<Task>> parentIdMap) {
		
		if (CollectionUtils.isEmpty(tasks)) {
			return Collections.emptyList();
		}
		
		List<Long> idList = new ArrayList<Long>(tasks.size());
		for (Task task : tasks) {
			idList.add(task.getTaskId());
			List<Task> siblings = parentIdMap.get(task.getServiceOrderId());
			if (siblings == null) {
				siblings = new ArrayList<Task>();
				parentIdMap.put(task.getServiceOrderId(), siblings);
	}
			siblings.add(task);
		}
		return idList;
	}

	private List<Long> convertServiceOrderListToIdListAndParentIdMap(
			List<ServiceOrder> serviceOrders, Map<Long, ServiceOrder> parentIdMap) {

		if (CollectionUtils.isEmpty(serviceOrders)) {
			return Collections.emptyList();
		}
		
		List<Long> idList = new ArrayList<Long>(serviceOrders.size());
		for (ServiceOrder serviceOrder : serviceOrders) {
			idList.add(serviceOrder.getServiceOrderId());
			parentIdMap.put(serviceOrder.getTransactionId(), serviceOrder);
		}
		return idList;
	}

	private List<Long> convertTransactionListToIdList(List<Transaction> transactions) {
		if (CollectionUtils.isEmpty(transactions)) {
			return Collections.emptyList();
		}
		
		List<Long> ids = new ArrayList<Long>();
		for (Transaction transaction : transactions) {
			ids.add(transaction.getTransactionId());
		}
		return ids;
	}
	
	private List<Long> convertBuyerNotificationListToIdListAndParentIdMap(
			List<BuyerNotification> buyerNotifications,
			Map<Long, BuyerNotification> parentIdMap) {
		
		if (CollectionUtils.isEmpty(buyerNotifications)) {
			return Collections.emptyList();
		}
		
		List<Long> ids = new ArrayList<Long>();
		for (BuyerNotification buyerNotification : buyerNotifications) {
			ids.add(buyerNotification.getBuyerNotificationId());
			parentIdMap.put(buyerNotification.getTransactionId(), buyerNotification);
		}
		return ids;
	}
	
	@Transactional
	public void markNonUniqueNewTransactionsAsFailed(String inputFile) {
		List<Transaction> nonUniqueNewTransactions = null;
		try {
			nonUniqueNewTransactions = integrationDao.getNonUniqueNewTransactionsByInputFile(inputFile);
		} catch (Exception e) {
			logger.error("An exception was thrown while attempting to get Non-Unique new transactions by input file", e);
		}
		if (nonUniqueNewTransactions != null) {
			List<Long> transactionIds = new ArrayList<Long>();
			for (Transaction transaction : nonUniqueNewTransactions) {
				transactionIds.add(transaction.getTransactionId());
			}
			if (!CollectionUtils.isEmpty(transactionIds)) {
				integrationDao.updateStatusForTransactionsList(transactionIds, ProcessingStatus.ERROR, "A NEW transaction with the same external order number has already been processed.");
			}
		}
	}
	
	@Transactional
	public boolean acquireLock(String lockName, Double lockExpirationMinutes) {
		String threadId = String.valueOf(Thread.currentThread().getId());
		
		Lock lock = null;
		try {
			lock = integrationDao.getLockInformation(lockName);
		} catch (Exception e) {
			lock = null;
		}
		
		try {
			if (lock == null) {
				return integrationDao.createAndAcquireLock(lockName, threadId);
			} else {
				return integrationDao.acquireLock(lockName, threadId, lockExpirationMinutes);
			} 
		} catch (Exception e) {
			logger.warn(String.format("Unknown error occurred while trying to acquire lock with name '%s' for threadId '%s'", lockName, threadId), e);
			return false;
		}
	}
	
	@Transactional
	public void releaseLock(String lockName) {
		integrationDao.releaseLock(lockName);
	}
	
	public Connection getSqlConnection() throws BusinessException {
		try {
			return dataSource.getConnection();
		}
		catch (SQLException e) {
			throw new DataException("Unable to obtain a database connection", e);
		}
	}

	public void setIntegrationDao(IIntegrationDao integrationDao) {
		this.integrationDao = integrationDao;
	}

	public IIntegrationDao getIntegrationDao() {
		return integrationDao;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	@Transactional
	public Long createTask(Long serviceOrderId, String title, String comments,
			boolean defaultTask, String externalSku, String specialtyCode,
			double amount, String category, String subCategory,
			String serviceType, Integer sequenceNumber) {
		Task task = new Task(
				0, serviceOrderId, title, comments, defaultTask, externalSku, specialtyCode,
				amount, category, subCategory, serviceType, sequenceNumber
				);
		this.integrationDao.createTask(task);
		return task.getTaskId();
	}

	@Transactional(readOnly = true, propagation=Propagation.SUPPORTS)
	public boolean wasCancellationRequestReceived(String serviceOrderId) {
		List<Transaction> transactions = this.integrationDao.findTransactions(serviceOrderId, TransactionType.CANCEL);
		boolean wasCancellationRequestReceived = !CollectionUtils.isEmpty(transactions);
		return wasCancellationRequestReceived;
	}

	@Transactional
	public void copyTasks(long sourceServiceOrderId, Long targetServiceOrderId, Map<String, BigDecimal> skuPriceMap) {
		List<Task> tasks = this.integrationDao.getTasksByServiceOrderIdExcludingCoverage(sourceServiceOrderId, "CC");
		List<Long> taksIds = getTaskIdsFromTasks(tasks);
		List<OmsTask> omsTasksList = this.integrationDao.getOmsTasksByTaskIds(taksIds);
		Map<Long, OmsTask> omsTasksMap = createOmsTaskMapFromListKeyedByTaskId(omsTasksList);
		doTaskCopying(skuPriceMap, tasks, omsTasksMap, targetServiceOrderId);
	}
	
	@Transactional
	public void copyTasksForCancellation(long sourceServiceOrderId, Long targetServiceOrderId, Map<String, BigDecimal> skuPriceMap) {
		List<Task> tasks = this.integrationDao.getTasksByServiceOrderId(sourceServiceOrderId);
		List<Long> taksIds = getTaskIdsFromTasks(tasks);
		List<OmsTask> omsTasksList = this.integrationDao.getOmsTasksByTaskIds(taksIds);
		Map<Long, OmsTask> omsTasksMap = createOmsTaskMapFromListKeyedByTaskId(omsTasksList);
		doTaskCopying(skuPriceMap, tasks, omsTasksMap, targetServiceOrderId);
	}
	
	private void doTaskCopying(Map<String, BigDecimal> skuPriceMap,
			List<Task> tasks, Map<Long, OmsTask> omsTasksMap, Long targetServiceOrderId) {
		for (Task task : tasks) {
			Double amount = task.getAmount();
			OmsTask omsTask = omsTasksMap.get(task.getTaskId());
			//TODO::have to check whether permit sku check is required or not
			if ("CC".equalsIgnoreCase(omsTask.getCoverage()) || MarketESBConstant.PERMIT_SKU.equals(task.getExternalSku())) {
				BigDecimal skuPrice = skuPriceMap.get(task.getExternalSku());
				if (skuPrice != null) {
					amount = skuPrice.doubleValue();
				}
			}
			
			long taskId = this.createTask(targetServiceOrderId, task.getTitle(),
					task.getComments(), task.getDefaultTask(),
					task.getExternalSku(), task.getSpecialtyCode(), amount,
					task.getCategory(), task.getSubCategory(), task.getServiceType(), task.getSequenceNumber());
			
			@SuppressWarnings("unused")
			long omsTaskId = this.createOmsTask(taskId,
					omsTask.getChargeCode(), omsTask.getCoverage(), omsTask.getType(),
					omsTask.getDescription());
		}
	}

	@Transactional
	public Long createOmsTask(long taskId, String chargeCode, String coverage,
			String type, String description) {

		OmsTask omsTask = new OmsTask(0, taskId, chargeCode, coverage, type, description);
		this.integrationDao.createOmsTask(omsTask);
		return omsTask.getOmsTaskId();
	}

	private Map<Long, OmsTask> createOmsTaskMapFromListKeyedByTaskId(List<OmsTask> list) {
		if (CollectionUtils.isEmpty(list)) {
			return Collections.emptyMap();
		}
		
		Map<Long, OmsTask> map = new HashMap<Long, OmsTask>(list.size());
		for (OmsTask omsTask : list) {
			map.put(omsTask.getTaskId(), omsTask);
		}

		return map;
	}

	private List<Long> getTaskIdsFromTasks(List<Task> tasks) {
		if (CollectionUtils.isEmpty(tasks)) {
			return Collections.emptyList();
		}
		
		List<Long> taskIds = new ArrayList<Long>(tasks.size());
		for (Task task : tasks) {
			taskIds.add(task.getTaskId());
		}

		return taskIds;
	}
	
	@Transactional
	public void addCancellationRequestsWithoutResponsesToBatch(Double retryHours, Long transactionLimit) {
		try {
			Long retryMinutes = Math.round(retryHours * 60D);
			// Removing looping for retry logic, only 1000 transactions to be retried per batch
			
			
			List<Transaction> transactionsToRetry = integrationDao.getCancellationTransationsWithoutResponses(retryMinutes, transactionLimit);
			List<Long> retriedTransactionIds  = null;
			
			if (!CollectionUtils.isEmpty(transactionsToRetry)) {
				retriedTransactionIds = retryCancellationTransactions(transactionsToRetry);
				
				// Mark Cancellation transactions as resent
				if (!CollectionUtils.isEmpty(retriedTransactionIds)) {
					integrationDao.updateStatusForTransactionsList(retriedTransactionIds, ProcessingStatus.RESENT, null);
				}
			}
			
		} catch (Exception e) {
			logger.error("An error occurred while attempting to add cancellation requests without responses to the outgoing RI batch.  The transaction will be rolled back.", e);
			throw new RuntimeException("An error occurred while attempting to add cancellation requests without responses to the outgoing RI batch.  The transaction will be rolled back.", e);
		}
	}
	
	private List<Long> retryCancellationTransactions(List<Transaction> cancellationTransactionsToRetry) {
		final String methodName = "retryCancellationTransactions";
		logger.info(String.format("entered %s", methodName));
			
		if (CollectionUtils.isEmpty(cancellationTransactionsToRetry)) {
			logger.debug("The list of transactions to be retried is empty.");
			logger.info(String.format("exiting %s", methodName));
			return Collections.emptyList();
		}
			
		if (logger.isDebugEnabled()) logger.debug(String.format("%d transactions will be retried", cancellationTransactionsToRetry.size()));

			// Create new transactions on the open batch to retry these transactions
		Batch openBatch = findCurrentOrCreateNewOpenBatchFor(IntegrationName.RI_CANCEL_OUTBOUND);
		if (logger.isDebugEnabled()) logger.debug(String.format("Retries will be placed in batch: %s", openBatch.toString()));
			
			List<Long> transactionIdsToRetry = convertTransactionListToIdList(cancellationTransactionsToRetry);
		if (logger.isDebugEnabled()) logger.debug(String.format("Transactions to be retried have the follwoing transactionIds: ", StringUtils.collectionToCommaDelimitedString(transactionIdsToRetry)));
		
			List<BuyerNotification> buyerNotificationsToRetry = integrationDao.getBuyerNotificationsByTransactionIds(transactionIdsToRetry);
		Map<Long, BuyerNotification> buyerNotificationsToRetryParentIdMap = new HashMap<Long, BuyerNotification>();
		List<Long> buyerNotificationIdsToRetry = convertBuyerNotificationListToIdListAndParentIdMap(buyerNotificationsToRetry, buyerNotificationsToRetryParentIdMap);
		if (logger.isDebugEnabled()) logger.debug(String.format("BuyerNotifications to be retried have the follwoing buyerNotificationIds: ", StringUtils.collectionToCommaDelimitedString(buyerNotificationIdsToRetry)));
		
		/* May not be required as we are not adding any oms buyer notification while generating cancel file 
		List<OmsBuyerNotification> omsBuyerNotificationsToRetry = integrationDao.getOmsBuyerNotificationsByBuyerNotificationIds(buyerNotificationIdsToRetry);
		Map<Long, OmsBuyerNotification> omsBuyerNotificationsToRetryParentIdMap = new HashMap<Long, OmsBuyerNotification>();
		convertOmsBuyerNotificationListToParentIdMap(omsBuyerNotificationsToRetry, omsBuyerNotificationsToRetryParentIdMap);
		if (logger.isDebugEnabled()) logger.debug(String.format("OmsBuyerNotifications to be retried have the follwoing omsBuyerNotificationIds: ", StringUtils.collectionToCommaDelimitedString(omsBuyerNotificationsToRetryParentIdMap.keySet())));
		*/
		
		List<ServiceOrder> serviceOrdersToRetry = integrationDao.getServiceOrdersByTransactionIds(transactionIdsToRetry);
		Map<Long, ServiceOrder> serviceOrdersToRetryParentIdMap = new HashMap<Long, ServiceOrder>();
		List<Long> serviceOrderIdsToRetry = convertServiceOrderListToIdListAndParentIdMap(serviceOrdersToRetry, serviceOrdersToRetryParentIdMap);
		if (logger.isDebugEnabled()) logger.debug(String.format("ServiceOrder to be retried have the follwoing serviceOrderIds: ", StringUtils.collectionToCommaDelimitedString(serviceOrderIdsToRetry)));
			
		List<Task> tasksToRetry = integrationDao.getTasksByServiceOrderIdsList(serviceOrderIdsToRetry);
		Map<Long, List<Task>> tasksToRetryParentIdMap = new HashMap<Long, List<Task>>();
		List<Long> taskIdsToRetry = convertTaskListToIdListAndParentIdMap(tasksToRetry, tasksToRetryParentIdMap);
		if (logger.isDebugEnabled()) logger.debug(String.format("Tasks to be retried have the follwoing taskIds: ", StringUtils.collectionToCommaDelimitedString(taskIdsToRetry)));
		
		List<OmsTask> omsTasksToRetry = integrationDao.getOmsTasksByTaskIds(taskIdsToRetry);
		Map<Long, List<OmsTask>> omsTasksToRetryParentIdMap = new HashMap<Long, List<OmsTask>>();
		convertOmsTaskListToParentIdMap(omsTasksToRetry, omsTasksToRetryParentIdMap);
		if (logger.isDebugEnabled()) logger.debug(String.format("OmsTasks to be retried have the follwoing omsTaskIds: ", StringUtils.collectionToCommaDelimitedString(omsTasksToRetryParentIdMap.keySet())));
		
		List<Long> retriedTransactionIds = new ArrayList<Long>();
			for (Transaction transactionToRetry : cancellationTransactionsToRetry) {
			if (logger.isDebugEnabled()) logger.debug(String.format("Ready to process the following transaction: %s", transactionToRetry));
			BuyerNotification buyerNotificationToRetryForTransaction =  buyerNotificationsToRetryParentIdMap.get(transactionToRetry.getTransactionId());
			if (buyerNotificationToRetryForTransaction == null) {
				logger.error(String.format("Transaction with transactionId %d does not have a buyer notification record associated with it.", transactionToRetry.getTransactionId()));
					continue;
				}
			if (logger.isDebugEnabled()) logger.debug(String.format("Ready to process the following BuyerNotification for retry: %s", buyerNotificationToRetryForTransaction));
			
			/*
			OmsBuyerNotification omsBuyerNotificationToRetryForTransaction =  omsBuyerNotificationsToRetryParentIdMap.get(buyerNotificationToRetryForTransaction.getBuyerNotificationId());
			if (omsBuyerNotificationToRetryForTransaction == null) {
				logger.error(String.format("BuyerNotification with buyerNotificationId %d does not have an oms buyer notification record associated with it.", buyerNotificationToRetryForTransaction.getBuyerNotificationId()));
					continue;
				}
			if (logger.isDebugEnabled()) logger.debug(String.format("Ready to process the following OmsBuyerNotification for retry: %s", omsBuyerNotificationToRetryForTransaction));
			*/	
			ServiceOrder serviceOrderToRetryForTransaction = serviceOrdersToRetryParentIdMap.get(transactionToRetry.getTransactionId());
			if (serviceOrderToRetryForTransaction == null) {
				logger.error(String.format("Transaction with transactionId %d does not have a service order record associated with it.", transactionToRetry.getTransactionId()));
				continue;
			}
			if (logger.isDebugEnabled()) logger.debug(String.format("Ready to process the following ServiceOrder for retry: %s", serviceOrderToRetryForTransaction));
			
			List<Task> tasksToRetryForTransaction = tasksToRetryParentIdMap.get(serviceOrderToRetryForTransaction.getServiceOrderId());
			if (CollectionUtils.isEmpty(tasksToRetryForTransaction)) {
				logger.error(String.format("ServiceOrder with serviceOrderId %d does not have any task records associated with it.", serviceOrderToRetryForTransaction.getServiceOrderId()));
				continue;
			}
			
			boolean isOmsTaskMissing = false;
			for (Task taskToRetryForTransaction : tasksToRetryForTransaction) {
				if (logger.isDebugEnabled()) logger.debug(String.format("Ready to process the following Task for retry: %s", taskToRetryForTransaction));

				List<OmsTask> omsTasksToRetryForTransactionTask = omsTasksToRetryParentIdMap.get(taskToRetryForTransaction.getTaskId());
				if (CollectionUtils.isEmpty(omsTasksToRetryForTransactionTask)) {
					logger.error(String.format("Task with taskId %d does not have any task_oms records associated with it.", taskToRetryForTransaction.getTaskId()));
					isOmsTaskMissing = true;
					continue;
				}
				if (logger.isDebugEnabled()) logger.debug(String.format("Ready to process %d OmsTasks for retry for this transactions.", omsTasksToRetryForTransactionTask.size()));
			}
			
			if (isOmsTaskMissing) {
				logger.error("Transaction will be skipped as tasks with no corresponding oms task records were found.");
				continue;
			}

				Long newTransactionId = createNewBuyerNotificationTransaction(openBatch.getBatchId(), transactionToRetry.getExternalOrderNumber(), transactionToRetry.getServiceLiveOrderId());
			if (logger.isDebugEnabled()) logger.debug(String.format("created new buyer notification transaction with transactionId %d", newTransactionId));

			Long newBuyerNotificationId = createNewBuyerNotification(newTransactionId, buyerNotificationToRetryForTransaction.getNotificationEvent(), buyerNotificationToRetryForTransaction.getNotificationEventSubType(), buyerNotificationToRetryForTransaction.getRelatedServiceOrderId());
			if (logger.isDebugEnabled()) logger.debug(String.format("created new buyerNotification record with buyerNotificationId %d", newBuyerNotificationId));
				
			/*Long newOmsBuyerNotificationId = createNewOmsBuyerNotification(newBuyerNotificationId, omsBuyerNotificationToRetryForTransaction.getTechComment(), omsBuyerNotificationToRetryForTransaction.getModelNumber(), omsBuyerNotificationToRetryForTransaction.getSerialNumber(), omsBuyerNotificationToRetryForTransaction.getInstallationDate(), omsBuyerNotificationToRetryForTransaction.getAmountCollected(), omsBuyerNotificationToRetryForTransaction.getPaymentMethod(), omsBuyerNotificationToRetryForTransaction.getPaymentAccountNumber(), omsBuyerNotificationToRetryForTransaction.getPaymentExpirationDate(), omsBuyerNotificationToRetryForTransaction.getPaymentAuthorizationNumber());
			if (logger.isDebugEnabled()) logger.debug(String.format("created new omsBuyerNotification record with omsBuyerNotificationId %d", newOmsBuyerNotificationId));*/

			Long newServiceOrderId = createServiceOrder(null,
					newTransactionId, serviceOrderToRetryForTransaction.getLaborSpendLimit(),
					serviceOrderToRetryForTransaction.getPartsSpendLimit(),
					serviceOrderToRetryForTransaction.getTitle(),
					serviceOrderToRetryForTransaction.getDescription(),
					serviceOrderToRetryForTransaction.getProviderInstructions(),
					serviceOrderToRetryForTransaction.getStartDate(),
					serviceOrderToRetryForTransaction.getStartTime(),
					serviceOrderToRetryForTransaction.getEndDate(),
					serviceOrderToRetryForTransaction.getEndTime(),
					serviceOrderToRetryForTransaction.getTemplateId(),
					serviceOrderToRetryForTransaction.getProviderServiceConfirmInd(),
					serviceOrderToRetryForTransaction.getPartsSuppliedBy(),
					serviceOrderToRetryForTransaction.getServiceWindowTypeFixed(),
					serviceOrderToRetryForTransaction.getMainServiceCategory(),
					serviceOrderToRetryForTransaction.getBuyerTermsAndConditions());
			if (logger.isDebugEnabled()) logger.debug(String.format("created new serviceOrder record with serviceOrderId %d", newServiceOrderId));

			for (Task taskToRetryForTransaction : tasksToRetryForTransaction) {
				List<OmsTask> omsTasksToRetryForTransactionTask = omsTasksToRetryParentIdMap.get(taskToRetryForTransaction.getTaskId());
				Long newTaskId = createTask(newServiceOrderId,
						taskToRetryForTransaction.getTitle(),
						taskToRetryForTransaction.getComments(),
						taskToRetryForTransaction.getDefaultTask(),
						taskToRetryForTransaction.getExternalSku(),
						taskToRetryForTransaction.getSpecialtyCode(),
						taskToRetryForTransaction.getAmount(),
						taskToRetryForTransaction.getCategory(),
						taskToRetryForTransaction.getSubCategory(),
						taskToRetryForTransaction.getServiceType(),
						taskToRetryForTransaction.getSequenceNumber());
				if (logger.isDebugEnabled()) logger.debug(String.format("created new task record with taskId %d", newTaskId));
				
				for (OmsTask omsTaskToRetryForTransactionTask : omsTasksToRetryForTransactionTask) {
					Long newOmsTaskId = createOmsTask(newTaskId,
							omsTaskToRetryForTransactionTask.getChargeCode(),
							omsTaskToRetryForTransactionTask.getCoverage(),
							omsTaskToRetryForTransactionTask.getType(),
							omsTaskToRetryForTransactionTask.getDescription());
					if (logger.isDebugEnabled()) logger.debug(String.format("created new omsTask record with omsTaskId %d", newOmsTaskId));
				}
			}
			
				retriedTransactionIds.add(transactionToRetry.getTransactionId());
			if (logger.isDebugEnabled()) logger.debug(String.format("Successfully processed transaction with transactionId %d", transactionToRetry.getTransactionId()));
			}
		return retriedTransactionIds;
	}
	
	@Transactional
	public String addCancelAuditRequestsMarkedAsResendToBatch(Long transactionLimit) {
		try {
			StringBuilder errorMessageSb = new StringBuilder();
			errorMessageSb.append("The following cancellation audit transactions were not able to be resent:");
			boolean sendErrorMessage = false;
			
			boolean workRemainsToBeDone = true;
			do {
				List<Transaction> auditTransactionsMarkedForResend = integrationDao.getCancelAuditTransationsMarkedForResend(transactionLimit);
				List<Transaction> cancellationTransactionsToRetry = new ArrayList<Transaction>();
				List<Long> retriedCancellationTransactionIds = null;
				
				if (!CollectionUtils.isEmpty(auditTransactionsMarkedForResend)) {					
					List<Long> resendAuditTransactionIds = new ArrayList<Long>();
					Map<Long, Long> auditTransactionToCancellationTransactionMap = new HashMap<Long, Long>();
			
					for (Transaction auditTransaction : auditTransactionsMarkedForResend) {
						Transaction transactionToRetry = null;
						try {
							transactionToRetry = integrationDao.getCancelTransactionByAuditTransactionId(auditTransaction.getTransactionId());
						} catch (DataException e) {
							logger.error(String.format("Not able to find a cancellation transaction to match audit transaction with ID %d", auditTransaction.getTransactionId()), e);
						}	
						if (transactionToRetry != null) {
							cancellationTransactionsToRetry.add(transactionToRetry);				
							resendAuditTransactionIds.add(auditTransaction.getTransactionId());
							auditTransactionToCancellationTransactionMap.put(auditTransaction.getTransactionId(), transactionToRetry.getTransactionId());
						}
					}
					
					retriedCancellationTransactionIds = retryCancellationTransactions(cancellationTransactionsToRetry);
					
					// Remove any transactionIds from the resentAuditTransactionIds list that do not 
					// have corresponding transactions in the retriedCancellationTransactionIds list
					for (Iterator<Long> i = resendAuditTransactionIds.iterator(); i.hasNext(); ) {
						Long auditTransactionId = i.next();
						Long cancellationTransactionId = auditTransactionToCancellationTransactionMap.get(auditTransactionId);
						if (!retriedCancellationTransactionIds.contains(cancellationTransactionId)) {
							i.remove();
							errorMessageSb.append(auditTransactionId);
							errorMessageSb.append(", ");
							sendErrorMessage = true;
						}				
					}
					
					// Mark the Audit transactions as resent, since that's where they were originally marked as ready to resend.
					if (!CollectionUtils.isEmpty(resendAuditTransactionIds)) {
						integrationDao.updateStatusForTransactionsList(resendAuditTransactionIds, ProcessingStatus.RESENT, null);
					}
				}
				
				// Keep looping if we hit the transaction limit.  Also make sure that we retried at least 1 transaction, 
				// otherwise it will continue looping indefinitely on the same transactions
				workRemainsToBeDone = cancellationTransactionsToRetry != null && cancellationTransactionsToRetry.size() == transactionLimit && !CollectionUtils.isEmpty(retriedCancellationTransactionIds);
			} while (workRemainsToBeDone);
			
			if (sendErrorMessage) {
				return errorMessageSb.toString();
			}
			
		} catch (Exception e) {
			logger.error("An error occurred while attempting to add cancellation requests whose audit responses have been marked for resending to the outgoing RI batch.  The transaction will be rolled back.", e);
			throw new RuntimeException("An error occurred while attempting to add cancellation requests whose audit responses have been marked for resending to the outgoing RI batch.  The transaction will be rolled back.", e);
		}
		return "";
	}
	
	public int getTransactionTypeById(Long transactionId){
		int transactionType = integrationDao.getTransactionTypeById(transactionId);
		return transactionType;
	}

	// SL-21931
	public String getBuyerUserNameByBuyerId(Integer buyerId){
		String buyer = integrationDao.getBuyerUserNameByBuyerId(buyerId);
		return buyer;
	}
	
		
}
