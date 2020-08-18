package com.servicelive.orderfulfillment.integration;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DeadlockLoserDataAccessException;

import com.servicelive.orderfulfillment.common.ServiceOrderDeadLockException;
import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.integration.domain.Transaction;
import com.servicelive.orderfulfillment.integration.domain.TransactionType;
/**
 * User: Mustafa Motiwala
 * Date: Apr 8, 2010
 * Time: 2:35:06 PM
 */
public class IntegrationProcessor {
    private IntegrationBO integrationBO;

    private static final Log log = LogFactory.getLog(IntegrationProcessor.class);
    
    private static final String cancellationException = "The order cannot be created for cancellation status codes (CA or CV)";

    public void setIntegrationBO(IntegrationBO integrationBO) {
        this.integrationBO = integrationBO;
    }

    /**
     * 1> Get All Transactions that were marked for us.
     * 2> in a single TX call SOBO Then update Transaction Status
     *
     * @param uuidWorker The worker to be processed.
     */
    public void process(String uuidWorker) {
        List<Transaction> transactions = integrationBO.getAvailableTransactions(uuidWorker);
        log.debug("Need to load transactions: " + transactions);
        sortTransactionsByType(transactions);
        for (Transaction transaction:transactions) {
            try {
            	processWithDeadLockDetection(transaction);
            } catch(AncestorNotFoundException e){
                log.info("Could not find ancestor for transaction. Aborting.", e);
                integrationBO.release(transaction);
                exceptionHandler(e , transaction);
                //SL-19776
                if(cancellationException.equals(e.getMessage())){
                	//update the exception in transactions table
            		integrationBO.updateException(transaction, cancellationException);
                }//SL-19776 : end
            }
            // SL-18883: CLONE - Duplicate Service Orders is being created for a single external order number
            // Adding catch block to catch exception in case unique composite key constraint fails
            catch(DataIntegrityViolationException e)
            {
            	 log.error("Exception while trying to insert data into processed_external_order_number table!", e);
                 log.info("Exception description"+e.getStackTrace());
                 integrationBO.saveException(transaction, e);
                 dataIntegrityViolationExceptionHandler(e , transaction);
            }
           catch(Exception e){
                log.error("Exception when trying send order to OrderFulfillment!", e);
                integrationBO.saveException(transaction, e);
                exceptionHandler(e , transaction);
            }
        }
    }


    private void sortTransactionsByType(List<Transaction> transactions) {
        Collections.sort(transactions, new Comparator<Transaction>() {
            public int compare(Transaction o1, Transaction o2) {
                TransactionType o2Type = o2.getType();
                switch (o1.getType()) {
                    case NEW:
                        if (TransactionType.NEW!= o2Type) return -1;
                    case UPDATE:
                    case ACKNOWLEDGE_UPDATE:
                    case CLOSE_ACKNOWLEDGEMENT:
                        if (TransactionType.CANCEL == o2Type) return -1;
                        else if (TransactionType.NEW == o2Type) return 1;
                        break;
                    case CANCEL:
                        if (TransactionType.CANCEL != o2Type) return 1;
                }
                return 0;
            }
        });
    }
    
    private void processWithDeadLockDetection(Transaction transaction){
    	long start = System.currentTimeMillis();
        boolean deadLockDetected = false;
        int attempts = 0;
        do {
        	deadLockDetected = false;
            try {
				switch (transaction.getType()) {
                case NEW:
                	integrationBO.createServiceOrder(transaction);
                    break;
                case UPDATE:
                    integrationBO.processUpdateTypeTransaction(transaction);
                    break;
                case CANCEL:
                    integrationBO.cancelServiceOrder(transaction);
                    break;
                case INFO:
                	integrationBO.addServiceOrderNote(transaction);
                    break;
                case ACKNOWLEDGE_UPDATE:
                    integrationBO.acknowledgeBatchUpdate(transaction);
                    break;
                case ACKNOWLEDGEMENT:
					// TODO : call processingBO.closeAcknowledge();
                    break;
                case CLOSE_ACKNOWLEDGEMENT:
                    integrationBO.addServiceOrderNote(transaction);
                    break;
				}                
			} catch (ServiceOrderDeadLockException e) {
                deadLockDetected = true;
                attempts++;
                log.warn("Deadlock detected. Retrying transaction. Atempt no.:" + attempts);
			} catch (DeadlockLoserDataAccessException e) {
				deadLockDetected = true;
				attempts++;
				log.warn("Deadlock detected. Retrying transaction. Atempt no.:" + attempts);
            }
			
            if (attempts > 5) {
                deadLockDetected = false;
                String msgGiveUp = String.format("Couldn't resolve deadlock after %1$d retries. Giving up.", attempts);
                throw new ServiceOrderException(msgGiveUp);
            }
		} while (deadLockDetected);
        long end = System.currentTimeMillis();
        log.info("Inside IntegrationProcessor..>> Time Taken for creation>>"+(end-start));

    }
    
    public boolean acquireWork(String uuidWorker){
    	//broken up acquire work because if there is no work found the
    	//thread locks the whole table and when it is time for the second 
    	//query it always deadlocks
    	return integrationBO.acquireWork(uuidWorker) || integrationBO.acquireExpiredWork(uuidWorker);
    }
    
    public void exceptionHandler(Exception ex , Transaction t) {
		// Email subject
		StringBuilder emailSubject = new StringBuilder("SL transaction failure alert");		
		// Email body
		StringBuilder emailBody = new StringBuilder("Message:\r\n");
		emailBody.append(ex.getMessage());
		emailBody.append("\r\n\r\nTransaction Id :\r\n");
		emailBody.append(t.getId());
		emailBody.append("\r\n\r\nTransaction Type :\r\n");
		emailBody.append(t.getType());
		emailBody.append("\r\n\r\nExternalOrderNumber :\r\n");
		emailBody.append(t.getExternalOrderNumber());		
		emailBody.append("\r\n\r\nStack Trace:\r\n");
		emailBody.append(convertThrowableToString(ex));
		
		// Send email
		log.error(emailSubject, ex);
		//EmailSender.sendMessage(emailSubject, emailBody);
	}
   
    //SL-18883:Adding method to send mail in case unique composite key constraint  fails
    public void dataIntegrityViolationExceptionHandler(Exception ex , Transaction t) {
		// Email subject
		StringBuilder emailSubject = new StringBuilder("SL transaction failure from duplicate Service Order processing");		
		// Email body
		StringBuilder emailBody = new StringBuilder("Message:\r\n");
		emailBody.append(ex.getMessage());
		emailBody.append("\r\n\r\nTransaction Id :\r\n");
		emailBody.append(t.getId());
		emailBody.append("\r\n\r\nTransaction Type :\r\n");
		emailBody.append(t.getType());
		emailBody.append("\r\n\r\nExternalOrderNumber :\r\n");
		emailBody.append(t.getExternalOrderNumber());		
		emailBody.append("\r\n\r\nStack Trace:\r\n");
		emailBody.append(convertThrowableToString(ex));
		
		// Send email
		log.error(emailSubject, ex);
		//EmailSender.sendMessage(emailSubject, emailBody);
	}
    
    protected String convertThrowableToString(Exception exc) {
		StringWriter stringWriter = new StringWriter();
		exc.printStackTrace(new PrintWriter(stringWriter));
		return stringWriter.toString();
	}
}
