package com.servicelive.esb.integration.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.servicelive.esb.integration.DataException;
import com.servicelive.esb.integration.domain.AssurantBuyerNotification;
import com.servicelive.esb.integration.domain.Batch;
import com.servicelive.esb.integration.domain.BuyerNotification;
import com.servicelive.esb.integration.domain.Contact;
import com.servicelive.esb.integration.domain.Document;
import com.servicelive.esb.integration.domain.HsrBuyerNotification;
import com.servicelive.esb.integration.domain.Location;
import com.servicelive.esb.integration.domain.Lock;
import com.servicelive.esb.integration.domain.OmsBuyerNotification;
import com.servicelive.esb.integration.domain.OmsBuyerNotificationResponse;
import com.servicelive.esb.integration.domain.OmsBuyerNotificationResponseMessage;
import com.servicelive.esb.integration.domain.OmsTask;
import com.servicelive.esb.integration.domain.Part;
import com.servicelive.esb.integration.domain.Phone;
import com.servicelive.esb.integration.domain.ProcessingStatus;
import com.servicelive.esb.integration.domain.ServiceOrder;
import com.servicelive.esb.integration.domain.Task;
import com.servicelive.esb.integration.domain.Transaction;
import com.servicelive.esb.integration.domain.TransactionType;
import com.newco.marketplace.dto.vo.serviceorder.Buyer;

public interface IIntegrationDao {
	
	public void createBatch(Batch batch) throws DataException;
	public void updateBatch(Batch batch) throws DataException;
	public void updateBatchFileName(Batch batch) throws DataException;
	public void updateStatusForTransactionsList(Collection<Long> transactionIds, ProcessingStatus processingStatus, String statusReason) throws DataException;
	public List<Batch> findBatchesFor(Long integrationId, ProcessingStatus processingStatus) throws DataException;
	public List<ServiceOrder> getServiceOrdersByBatchInputFile(String inputFile) throws DataException;
	
	public Batch getBatchById(long batchId) throws DataException;
	public Batch getBatchbyInputFile(String fileName) throws DataException;
	
	public List<Map<String, String>> getOMSCustomReferencesByServiceOrderId(Long serviceOrderId) throws DataException;
	public List<Map<String, String>> getAssurantCustomReferencesByServiceOrderId(Long serviceOrderId) throws DataException;	
	public List<Map<String, String>> getHSRCustomReferencesByServiceOrderId(Long serviceOrderId) throws DataException;	
	public void saveServiceOrderCustomRefs(ServiceOrder serviceOrder, Map<String, String> customRefs) throws DataException;
	public ServiceOrder findLatestServiceOrderByExternalOrderNumber(String externalOrderNumber) throws DataException;
	
	public void markTransactionsCompleteByInputFile(String inputFile) throws DataException;
	
	public List<String> getExistingExternalServiceOrdersIds(List<String> serviceOrderIds) throws DataException;

	public List<Transaction> getServiceOrdersThatMatchOrderNumberWithTestSuffix(
			String externalOrderNumber, String testSuffix) throws DataException;
	public void createTransaction(Transaction transaction) throws DataException;
	public void createServiceOrder(ServiceOrder serviceOrder) throws DataException;
	public void createContact(Contact contact) throws DataException;
	public void createPhone(Phone phone) throws DataException;
	public void createLocation(Location location) throws DataException;
	public void createTask(Task task) throws DataException;
	public void createPart(Part part) throws DataException;
	public void createDocument(Document document) throws DataException;
	public void createBuyerNotification(BuyerNotification buyerNotification) throws DataException;
	public void createOmsBuyerNotification(OmsBuyerNotification omsBuyerNotification) throws DataException;
	public void createAssurantBuyerNotification(AssurantBuyerNotification notification) throws DataException;
	public void createHsrBuyerNotification(HsrBuyerNotification hsrBuyerNotification) throws DataException;
	public long getTransactionCountByBatchAndProcessingStatus(Long batchId, ProcessingStatus processingStatus) throws DataException;
	public long getBuyerNotificationTransactionCount(String serviceLiveOrderId, ProcessingStatus processingStatus, String notificationEvent) throws DataException;
	public List<Transaction> getTransactionsByBatchId(Long batchId) throws DataException;
	public BuyerNotification getBuyerNotificationByTransactionId(Long transactionId) throws DataException;
	public List<BuyerNotification> getBuyerNotificationsByTransactionIds(List<Long> transactionIds) throws DataException;
	public AssurantBuyerNotification getAssurantBuyerNotificationByBuyerNotificationId(Long buyerNotificationId) throws DataException;
	public HsrBuyerNotification getHsrBuyerNotificationByBuyerNotificationId(Long buyerNotificationId) throws DataException;
	public List<OmsBuyerNotification> getOmsBuyerNotificationsByBuyerNotificationIds(List<Long> buyerNotificationIds) throws DataException;
	public ServiceOrder getServiceOrderByTransactionId(Long transactionId) throws DataException;
	public List<Part> getPartsByServiceOrderId(Long serviceOrderId) throws DataException;
	public List<Transaction> getTransactionsByBatchAndProcessingStatus(Long batchId, ProcessingStatus processingStatus) throws DataException;
	public List<OmsBuyerNotificationResponse> getOmsBuyerNotificationResponsesByTransactionIds(
			List<Long> transactionIds);
	public List<OmsBuyerNotificationResponseMessage> getOmsBuyerNotificationResponseMessagesByTransactionIds(
			List<Long> transactionIds);
	
	public List<Transaction> getCallCloseTransationsWithoutResponses(Long retryMinutes, Long transactionLimit);
	public List<Transaction> getCloseAuditTransationsMarkedForResend(Long transactionLimit);
	public Transaction getCallCloseTransactionByAuditTransactionId(Long auditTransactionId) throws DataException;
	
	public List<Transaction> getNonUniqueNewTransactionsByInputFile(String inputFile);
	
	public boolean acquireLock(String lockName, String acquiredBy, Double lockExpirationMinutes);
	public boolean createAndAcquireLock(String lockName, String acquiredBy);
	public boolean releaseLock(String lockName);
	public Lock getLockInformation(String lockName) throws DataException;
	public List<Transaction> findTransactions(String serviceLiveOrderId, TransactionType transactionType);
	public void createOmsTask(OmsTask omsTask) throws DataException;
	public List<Task> getTasksByServiceOrderIdExcludingCoverage(long serviceOrderId, String coverage) throws DataException;
	public List<Task> getTasksByServiceOrderId(long serviceOrderId) throws DataException;
	public List<OmsTask> getOmsTasksByTaskIds(List<Long> taksIds) throws DataException;
	public List<ServiceOrder> getServiceOrdersByTransactionIds(List<Long> transactionIdsList) throws DataException;
	public List<Task> getTasksByServiceOrderIdsList(List<Long> serviceOrderIdsList) throws DataException;
	public Transaction getCancelTransactionByAuditTransactionId(Long auditTransactionId) throws DataException;
	public List<Transaction> getCancellationTransationsWithoutResponses(Long retryMinutes, Long transactionLimit);
	public List<Transaction> getCancelAuditTransationsMarkedForResend(Long transactionLimit);
	public int getTransactionTypeById(Long transactionId);
	// SL-21931
	public String getBuyerUserNameByBuyerId(Integer buyerId);		
}
