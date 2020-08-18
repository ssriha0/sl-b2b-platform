package com.servicelive.orderfulfillment.integration;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DeadlockLoserDataAccessException;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.servicelive.orderfulfillment.ProcessingBO;
import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.integration.domain.Transaction;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;

/**
 * User: Mustafa Motiwala
 * Date: Apr 9, 2010
 * Time: 6:14:38 PM
 */

public class IntegrationBO {
    private static final Log log = LogFactory.getLog(IntegrationBO.class);

    private Integer maxOrders;
    private int expireInMinutes = 1;
    private int processingDelayInMinutes =15;
    private int maxAllowedTransactionAgeInMinutes = 60;

    private int maxExpireThresholdInHours=10;
    
    private String integrationCancellationComment = "Integration framework received a cancellation request from the buyer";
    private  static final String cancellationException = "The order cannot be created for cancellation status codes (CA or CV)";

    private ProcessingBO processingBO;
    private CommonTransactionHelper commonTransactionHelper;
    private TransactionUpdateProcessor transactionUpdateProcessor;

    private IntegrationDao dao;

    @Transactional
    public String createServiceOrder(Transaction transaction) throws AncestorNotFoundException{
    	//SL-19776 : preventing order creation if serviceOrderStatusCode is 'CA' or 'CV'
    	String statusCode = dao.getServiceOrdeStatusCode(transaction.getId());
    	if("CA".equals(statusCode) || "CV".equals(statusCode)){
    		String errMsg = String.format(cancellationException);
            log.info(errMsg);
            throw new AncestorNotFoundException(errMsg);
    	}//SL-19776 end
        //SL-18883: CLONE - Duplicate Service Orders is being created for a single external order number
        //Adding code to prevent processing of data for creating service order object in case unique composite key constraint fails 
    	log.info("Adding entry inside table to prevent duplicate service orders getting created with same external order number");
    	dao.saveExternalOrderNumber(transaction);  
        log.info("Starting to create the service order IntegrationBO.createServiceOrder");
    	//cloning service order object in case we have to retry for deadlocks
        ServiceOrder so = cloneObjectUsingJAXBSerialization(transaction.getServiceOrder());
        //sl-16667
        if(null != so){
        	so.setNewSoInjection(true);
        }
        Map<String,Object> processVariables = commonTransactionHelper.createProcessVarsForTxOrder(transaction);

        Identification buyerIdentification = commonTransactionHelper.createBuyerIdentification(transaction);
        String soId = processingBO.executeCreateWithGroups(buyerIdentification,so,processVariables);
        log.debug("ServiceOrder created successfully: " + soId);
        transaction.setServiceLiveOrderId(soId);

        dao.markCompleted(transaction);
        log.info("IntegrationBO.createServiceOrder Completed!" + transaction.getId());

        return soId;
    }


    @Transactional
    public void processUpdateTypeTransaction(Transaction transaction) throws AncestorNotFoundException {
        log.debug("executing processUpdateTypeTransaction() for transaction " + transaction.getId());
        TransactionUpdateResult updateResult = transactionUpdateProcessor.updateServiceOrder(transaction);

        if (updateResult == TransactionUpdateResult.NEW_ORDER_CREATED) {
            //transactionWFProcessStarter.signalStartProcessPath(transaction, transaction.getServiceLiveOrderId());
        }
    }

    @Transactional
    public void cancelServiceOrder(Transaction transaction) throws AncestorNotFoundException {
    	if (transaction != null && transaction.getServiceOrder() != null) {
    		transaction.getServiceOrder().setCancellationComment(integrationCancellationComment);
    	}
        sendSignalToProcessingBO(transaction, SignalType.INTEGRATION_CANCEL);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void addServiceOrderNote(Transaction transaction) {
    	commonTransactionHelper.populateSLOrderId(transaction);
        Identification buyerIdentification = commonTransactionHelper.createBuyerIdentification(transaction);
        ServiceOrder so = transaction.getServiceOrder();
        String slOrderId = transaction.getServiceLiveOrderId();
        processingBO.processAddBuyerNote(slOrderId, so.getNotes(), buyerIdentification);
        
        dao.markCompleted(transaction);
        log.debug("Transaction Completed:" + transaction);
    }

    @Transactional
    public boolean acquireWork(String workerId) {
		try {
			if (dao.acquireWork(workerId, maxOrders)) {
	        	log.debug("Found new work for worker id " + workerId);
	            return true;
	        }
		} catch (DeadlockLoserDataAccessException e) {
			log.info(String.format("Deadlock detected but it will be ignored. The exception message was: %s", e.getMessage()));
        }
        return false;
    }

    @Transactional
    public void acknowledgeBatchUpdate(Transaction transaction) throws AncestorNotFoundException {
        sendSignalToProcessingBO(transaction, SignalType.ACKNOWLEDGE_BATCH_UPDATE);
    }

    private void sendSignalToProcessingBO(Transaction transaction, SignalType signal) throws AncestorNotFoundException{
        commonTransactionHelper.populateSLOrderId(transaction);
        Identification buyerIdentification = commonTransactionHelper.createBuyerIdentification(transaction);
        ServiceOrder so = transaction.getServiceOrder();
        String slOrderId = transaction.getServiceLiveOrderId();
        if((signal == SignalType.INTEGRATION_CANCEL))
    	{
    	    log.debug("POS CANCELLATION SIGNAL IDENTIFIED for-> "+slOrderId);
            ServiceOrder serviceOrder = processingBO.getServiceOrder(slOrderId);
    		int soStateId = serviceOrder.getWfStateId();
    		Long buyerId = serviceOrder.getBuyerId();
    		log.debug("POS CANCELLATION SIGNAL IDENTIFIED for-> "+slOrderId+"& soStateId == "+soStateId);
    		
    	/*	//SL-20167
    		if(buyerId == 1000){
    			//SL-20167 : updating the purchase amount
    			log.info("SL-20167 : updating purchase amount for canceled task for SO : " +slOrderId);
    			for(SOTask oldTask : serviceOrder.getTasks()){
    				if(null != oldTask){
    					log.info("SL-20167 : Existing Sku :"+oldTask.getExternalSku());
    					for(SOTask newTask : so.getTasks()){ 
        					if(null != newTask && null != oldTask.getExternalSku() && null != newTask.getExternalSku()){
        						log.info("SL-20167 : Compared Sku :"+newTask.getExternalSku());
        						if(oldTask.getExternalSku().equals(newTask.getExternalSku())){
        							log.info("SL-20167 : Compared sku Purchase Amount:"+newTask.getPurchaseAmount());
        							processingBO.updatePurchaseAmount(oldTask.getExternalSku(), newTask.getPurchaseAmount(),slOrderId);
        						}
        					}
        				}
    				}
    				
    			}
    		}*/
    		
    		if(buyerId == 1000 && ((soStateId == 180) || (soStateId == 125) || (soStateId == 120) || (soStateId == 105)||
    			(soStateId == 165)|| (soStateId == 160)))
    		{	
    			log.debug("POS CANCELLATION END STATES, COMPLETED AND PENDING CANCEL");
    			log.debug("STARTED UPDATING POS CANCELLATION INDICATOR FOR RESOLUTION REQUIRED QUEUE");
    			processingBO.updatePOSCancellationIndicator(slOrderId, serviceOrder);
    			log.debug("DONE UPDATING POS CANCELLATION INDICATOR FOR RESOLUTION REQUIRED QUEUE");	
    			processingBO.updateSONotes(slOrderId,  serviceOrder);
    			log.debug("DONE UPDATING SO NOTES");
    			processingBO.updateSOLogging(slOrderId,  serviceOrder);
        		log.debug("DONE UPDATING ORDER HISTORY");
        		dao.markCompleted(transaction);
                log.debug("Transaction Completed:" + transaction);
        		return;
    		}
    		if(!(buyerId == 1000)&&(soStateId == 165)){
    			processingBO.updateSONotes(slOrderId,  serviceOrder);
    			processingBO.updateSOLogging(slOrderId,  serviceOrder);
    			dao.markCompleted(transaction);
    			return;
    		}
	    }
        log.debug("POS CANCELLATION SIGNAL not IDENTIFIED for-> "+slOrderId);
        processingBO.executeProcessSignalWithGroups(slOrderId, signal, buyerIdentification, so, commonTransactionHelper.createProcessVars(so), true);
        log.debug("Transaction Cancelled successfully:" + transaction);
        dao.markCompleted(transaction);
        log.debug("Transaction Completed:" + transaction);
    }

    @Transactional
	public boolean acquireExpiredWork(String workerId) {
		try {
			log.info("expireInMinutes"+expireInMinutes);
			if (dao.acquireExpired(workerId, maxOrders, expireInMinutes)) {
	            log.debug("Found expired work for worker id " + workerId);
	        	return true;
	        }
		} catch (DeadlockLoserDataAccessException e) {
			log.info(String.format("Deadlock detected but it will be ignored. The exception message was: %s", e.getMessage()));
    	}
    	return false;
    }
    
	public void errorOutRecordsPassedThresholdTime() {
    	dao.errorExpired(maxExpireThresholdInHours);
    }
    
    /**
     * Simple pass through method to the Dao.
     * @param workerId
     * @return - A list of Transacitons to be processed by this worker.
     */
    public List<Transaction> getAvailableTransactions(String workerId){
        return dao.getAvailableTransactions(workerId);
    }


    public void saveException(Transaction transaction, Exception e){
        dao.markError(transaction, e);
    }
    
    public void release(Transaction tx){
    	boolean transactionTooOld;
    	if (tx.getCreatedOn() == null) {
    		transactionTooOld = false;
    	}
    	else {
    		Calendar now = Calendar.getInstance();
        	Calendar maxAllowedAgeForTransaction = Calendar.getInstance();
        	maxAllowedAgeForTransaction.setTimeInMillis(tx.getCreatedOn().getTimeInMillis());
        	maxAllowedAgeForTransaction.add(Calendar.MINUTE, maxAllowedTransactionAgeInMinutes);
        	transactionTooOld = now.after(maxAllowedAgeForTransaction);    		
    	}

    	if (transactionTooOld) {
    		dao.markError(tx, new Exception(String.format("Transaction was not successfully processed within %d minutes of being created.", maxAllowedTransactionAgeInMinutes)));
    	}
    	else {
    		dao.release(tx, processingDelayInMinutes);
    	}
    }

    public void failWorker(String workerId, Exception e) {
        dao.markError(workerId, e);
    }

    public void setProcessingBO(ProcessingBO processingBO) {
        this.processingBO = processingBO;
    }

    public void setDao(IntegrationDao dao) {
        this.dao = dao;
    }

    public void setMaxOrders(Integer maxOrders) {
        this.maxOrders = maxOrders;
    }


    public void setExpireInMinutes(int expireInMinutes) {
        this.expireInMinutes = expireInMinutes;
    }

    public void setProcessingDelayInMinutes(int processingDelayInMinutes) {
        this.processingDelayInMinutes = processingDelayInMinutes;
    }

    public void setMaxAllowedTransactionAgeInMinutes(int maxAllowedTransactionAgeInMinutes) {
        this.maxAllowedTransactionAgeInMinutes = maxAllowedTransactionAgeInMinutes;
    }

    public void setMaxExpireThresholdInHours(int maxExpireThresholdInHours) {
        this.maxExpireThresholdInHours = maxExpireThresholdInHours;
    }

    public void setCommonTransactionHelper(CommonTransactionHelper commonTransactionHelper) {
        this.commonTransactionHelper = commonTransactionHelper;
    }

    public void setTransactionUpdateProcessor(TransactionUpdateProcessor transactionUpdateProcessor) {
        this.transactionUpdateProcessor = transactionUpdateProcessor;
    }

    private ServiceOrder cloneObjectUsingJAXBSerialization(ServiceOrder serviceOrder) throws ServiceOrderException {
		try {
			JAXBContext jc = JAXBContext.newInstance(ServiceOrder.class);
	        Marshaller marshaller = jc.createMarshaller();
	        StringWriter stringWriter = new StringWriter();
	        marshaller.marshal(serviceOrder, stringWriter);
	        Unmarshaller unmarshaller = jc.createUnmarshaller();
	        StringReader stringReader = new StringReader(stringWriter.toString());
	        return (ServiceOrder) unmarshaller.unmarshal(stringReader);
	    } catch (JAXBException e) {
			throw new ServiceOrderException(e);
		}
    }

    /**
     * SL-19776: update the exception in transactions table
     * @param transaction
     * @param exception
     */
	public void updateException(Transaction transaction, String exception) {
		try {
			dao.updateException(transaction, exception);
	    } catch (Exception e) {
	    	log.error("Exception trying to updateException",e);
		}
		
	}

}