package com.servicelive.orderfulfillment.integration;

import java.util.List;

import com.servicelive.orderfulfillment.integration.domain.Transaction;

/**
 * User: Mustafa Motiwala
 * Date: Apr 12, 2010
 * Time: 10:09:33 AM
 */
public interface IntegrationDao {
    /**
     * Get all Transactions that have been reserved for a worker.
     * @param workerId Id of the worker to get Transactions for.
     * @return
     */
    List<Transaction> getAvailableTransactions(String workerId);

    /**
     * Returns the latest ServiceLive SOID from the list of transactions for a
     * given external order number where the transaction type is NEW or UPDATE.
     * @param externalOrderNumber The externalOrderNumber to lookup.
     * @param currentTransactionId The currentTransaction to be excluded in the search.
     * @return
     */
    String findLatestSLOrderIdForExternalOrderNum(String externalOrderNumber, Long currentTransactionId);
    /**
     * Marks Transactions as Errors.
     * @param t Transaction that failed.
     * @param e Exception to cause this Transaction to fail. This is saved in the Error field of the database. 
     */
    void markError(Transaction t, Exception e);

    /**
     * Mark a worker as failed. If something causes a worker to die this method will be called.
     * @param workerId workerId which needs to be failed.
     * @param e exception that caused the worker to fail.
     */
    void markError(String workerId, Exception e);

    /**
     * Mark a transaction as having completed successfully.
     * @param t Transaction to mark as success.
     */
    void markCompleted(Transaction t);

    /**
     * Release a transaction for processing at a later date.
     * @param t Transaction to be released.
     * @param processingDelayInMinutes The number of minutes to wait before processing
     * this the transaction.
     */
    void release(Transaction t, int processingDelayInMinutes);

    /**
     * Acquire/reserve transactions for a worker.
     * @param id The workerId to use to reserve work
     * @param maxRecords Maximum number of Transactions to reserve.
     * @return True if work was reserved. False otherwise.
     */
    boolean acquireWork(String id, Integer maxRecords);

    /**
     * Attempts to pick up expired Transactions for a (dead) worker.
     * @param id New Id for the worker.
     * @param maxRecords Maximum number of records to reserve.
     * @param expireDelay the delay to use to determine if previous worker has expired.
     * @return True if any expired work was reserved. False otherwise.
     */
    boolean acquireExpired(String id, Integer maxRecords, Integer expireDelay);

    /**
     * If transactions exist that were created more than the threshold values and still
     * have not been run, mark them as error.
     * @param maximumExpireThresholdInHours
     */
    void errorExpired(Integer maximumExpireThresholdInHours);
    
    /**
     * @param serviceOrderId
     * @return
     */
    public List<String> getDocumentTitles(Long serviceOrderId);
    // SL-18883: CLONE - Duplicate Service Orders is being created for a single external order number
    // Method to save buyer id and external order number into table processed_external_order_number
    void saveExternalOrderNumber(Transaction transaction);  
    /**
     * SL-19776: checking the statusCode for transactionId.
     * @param transactionId
     * @return 
     */
	String getServiceOrdeStatusCode(Long transactionId);
	 /**
     * SL-19776: Save exception if cancellation code is injected with transactionType NEW
     * @param transaction
     * @param exception
     */
	void updateException(Transaction transaction, String cancellationException);
}
