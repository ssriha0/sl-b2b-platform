package com.servicelive.esb.integration.bo;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Connection;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.servicelive.bus.event.order.OrderEventType;
import com.servicelive.esb.integration.BusinessException;
import com.servicelive.esb.integration.domain.AssurantBuyerNotification;
import com.servicelive.esb.integration.domain.Batch;
import com.servicelive.esb.integration.domain.BuyerNotification;
import com.servicelive.esb.integration.domain.HsrBuyerNotification;
import com.servicelive.esb.integration.domain.IntegrationName;
import com.servicelive.esb.integration.domain.OmsBuyerNotificationResponse;
import com.servicelive.esb.integration.domain.Part;
import com.servicelive.esb.integration.domain.ProcessingStatus;
import com.servicelive.esb.integration.domain.ServiceOrder;
import com.servicelive.esb.integration.domain.Transaction;
import com.newco.marketplace.dto.vo.serviceorder.Buyer;

public interface IIntegrationBO {
	public Batch findCurrentOrCreateNewOpenBatchFor(
			IntegrationName integrationName) throws BusinessException;

	public Batch getCurrentAndPrepareNewOutgoingBatchFor(
			IntegrationName integrationName) throws BusinessException;

	public ServiceOrder findLatestServiceOrderByExternalOrderNumber(
			String externalOrderNumber) throws BusinessException;

	public Long createNewUnsuccessfulBatch(String fileName,
			Long integrationId, String errorMessage) throws BusinessException;
	
	public Batch createBatch(String inputFileName, Long integrationId, ProcessingStatus processingStatus, String errorMessage);
	public void updateBatch(Batch batch);
	public void updateBatchFileName(String originalFileName, String newFileName);
	public void updateBatchFileNameById(Long batchId, String updateFileName);
	
	public long createServiceOrder(Blob customRefs, Long transactionId,
			Double laborSpendLimit, Double partsSpendLimit, String title,
			String description, String providerInstructions, Date startDate,
			String startTime, Date endDate, String endTime, Long templateId,
			Boolean providerServiceConfirmInd, String partsSuppliedBy,
			Boolean serviceWindowTypeFixed, String mainServiceCategory,
			String buyerTermsAndConditions);

	public void createPart(Part part);
	
	public void recordBatchError(String fileName, Long batchId, String errorMessage);
	public void markBatchAndTransactionsAsProcessed(Long batchId, String outputFileName) throws BusinessException;
	public void markTransactionsAsProcessed(Collection<Long> transactionIds) throws BusinessException;
	public void markTransactionAsSkipped(Long transactionId, String skipReason);

	public void setCustomReferencesByInputFile(String inputFile) throws BusinessException;
	
	public void markTransactionsCompleteByInputFile(String inputFile) throws BusinessException;

	public Connection getSqlConnection() throws BusinessException;

	public List<String> getExistingExternalServiceOrdersIds(
			List<String> serviceOrderIds) throws BusinessException;

	public List<String> getLatestExistingExternalServiceOrderNumbersThatMatchBeforeTestSuffix(
			List<String> orderLookupIds, String testSuffixNew) throws BusinessException;

	public Long createNewBuyerNotificationTransaction(Long batchId,
			String externalOrderNumber, String serviceLiveOrderId) throws BusinessException;

	public Long createNewBuyerNotification(Long transactionId,
			String notificationEvent, String notificationEventSubType,
			Long relatedServiceOrderId) throws BusinessException;

	public Long createNewOmsBuyerNotification(Long buyerNotificationId,
			String techComment, String modelNumber, String serialNumber,
			Date installationDate, BigDecimal amountCollected,
			String paymentMethod, String paymentAccountNumber,
			String paymentExpirationDate, String paymentAuthorizationNumber)
			throws BusinessException;
	public Long createNewOmsBuyerNotification(Long buyerNotificationId,
			String techComment, String modelNumber, String serialNumber,
			Date installationDate, BigDecimal amountCollected,
			String paymentMethod, String paymentAccountNumber,
			String paymentExpirationDate, String paymentAuthorizationNumber,
			String maskedAccountNo,String token)
			throws BusinessException;
	
	public Long createNewAssurantBuyerNotification(Long buyerNotificationId,
			Date etaOrShippingDate, String shippingAirBillNumber, 
			String returnAirBillNumber, String incidentActionDescription) throws BusinessException;

	public Long createNewHsrBuyerNotification(Long buyerNotificationId,
			String unitNumber, String orderNumber, Date routedDate,
			String techId) throws BusinessException;

	public List<Transaction> getTransactionsByBatchAndProcessingStatus(Long batchId,
			ProcessingStatus processingStatus) throws BusinessException;
			
	public long getTransactionCountByBatchAndProcessingStatus(Long batchId,
			ProcessingStatus processingStatus) throws BusinessException;
	
	public long getSuccessfulBuyerNotificationTransactionCount(
			String serviceLiveOrderId, OrderEventType orderEventType) throws BusinessException;
	
	public void createAllOrders(Long buyerResourceId, String fileUploadId, List<Map<String, String>> orderFieldsList, Long batchId) throws Exception;
	
	public List<Transaction> getTransactionsByBatchId(Long batchId) throws BusinessException;
	public List<Transaction> getTransactionsByBatchFileName(String fileName) throws BusinessException;
	public BuyerNotification getBuyerNotificationByTransactionId(Long transactionId) throws BusinessException;
	public AssurantBuyerNotification getAssurantBuyerNotificationByBuyerNotificationId(Long buyerNotificationId) throws BusinessException;
	public HsrBuyerNotification getHsrBuyerNotificationByBuyerNotificationId(
			Long buyerNotificationId) throws BusinessException;
	public ServiceOrder getServiceOrderByTransactionId(Long transactionId) throws BusinessException;
	public List<Part> getPartsByServiceOrderId(Long serviceOrderId) throws BusinessException;

	public List<OmsBuyerNotificationResponse> getOmsBuyerNotificationResponsesByTransactions(
			List<Transaction> transactions);
	
	public void addCallCloseRequestsWithoutResponsesToBatch(Double retryHours, Long transactionLimit);
	public String addAuditRequestsMarkedAsResendToBatch(Long transactionLimit);
	
	public void markNonUniqueNewTransactionsAsFailed(String inputFile);

	public boolean acquireLock(String lockName, Double lockExpirationMinutes);
	public void releaseLock(String lockName);
	
	public Long createTask(Long serviceOrderId, String title,
			String comments, boolean defaultTask, String externalSku, String specialtyCode,
			double amount, String category, String subCategory, String serviceType, Integer sequenceNumber);

	public Long createOmsTask(long taskId, String chargeCode, String coverage,
			String type, String description);
	
	public boolean wasCancellationRequestReceived(String serviceOrderId);

	public void copyTasks(long sourceServiceOrderId, Long targetServiceOrderId, Map<String, BigDecimal> skuPriceMap);
	public void copyTasksForCancellation(long sourceServiceOrderId, Long targetServiceOrderId, Map<String, BigDecimal> skuPriceMap);
	
	public void addCancellationRequestsWithoutResponsesToBatch(Double retryHours, Long transactionLimit);
	public String addCancelAuditRequestsMarkedAsResendToBatch(Long transactionLimit);

	public int getTransactionTypeById(Long transactionId);
	
	// SL-21931
	public String getBuyerUserNameByBuyerId(Integer buyerId);
}