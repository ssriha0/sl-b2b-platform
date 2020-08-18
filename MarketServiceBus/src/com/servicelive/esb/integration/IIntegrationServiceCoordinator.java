package com.servicelive.esb.integration;

import java.util.List;
import java.util.Map;

import com.servicelive.bus.event.order.ServiceOrderEvent;
import com.servicelive.esb.integration.bo.IIntegrationBO;
import com.servicelive.esb.integration.domain.Batch;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

public interface IIntegrationServiceCoordinator {
	public void recordSearsRiOrderClosedEventAndRelatedData(
			ServiceOrderEvent serviceOrderEvent, ServiceOrder serviceOrder);
	
	public void recordSearsRiOrderCancelEventAndRelatedData(
			ServiceOrderEvent serviceOrderEvent, ServiceOrder serviceOrder);

	public IIntegrationBO getIntegrationBO();

	public void setIntegrationBO(IIntegrationBO bean);

	public void createNewUnsuccessfulBatch(String batchFileName,
			Long integrationId, String errorMessage);

	public void recordBatchError(String batchFileName, Long batchId,
			String errorMessage);

	public Batch recordAssurantServiceOrderEventData(ServiceOrder serviceOrder,
			String orderEventName, String subStatusName, String incidentActionDescription);

	public void recordSuccessfulFileGeneration(Long batchId,
			long transactionId, String updateLocationAndFileName);

	public void markTransactionAsSkipped(long transactionId, String skipReason);

	public Batch recordHsrOrderAcceptedEventAndRelatedData(
			String orderEventName, ServiceOrder serviceOrder);

	public Batch createBatchForFileUpload(String inputFilefeedName,
			List<Map<String, String>> orders, Long buyerResourceId, String fileUploadId);

	public boolean acquireLock(String lockName, Double lockExpirationMinutes);

	public void releaseLock(String lockName);

	public void setCustomReferencesByInputFile(String inputFileName);

	public void markTransactionsCompleteByInputFile(String inputFileName);

	public void markNonUniqueNewTransactionsAsFailed(String inputFileName);

	public String performAddNpsCallCloseRetriesToBatch(Double retryHours,
			Long maxDbTransactions);

	public void markBatchAndTransactionsAsProcessed(long batchId,
			String outputFileName);

	public void updateBatchFileName(String originalFileName,
			String newFileName);
	
	public String performAddNpsCancellationRetriesToBatch(Double retryHours,
			Long maxDbTransactions);

	
}
