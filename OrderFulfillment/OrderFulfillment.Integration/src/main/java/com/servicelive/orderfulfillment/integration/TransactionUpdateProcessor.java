package com.servicelive.orderfulfillment.integration;

import com.servicelive.orderfulfillment.ProcessingBO;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.OrderStatusOnUpdate;
import com.servicelive.orderfulfillment.integration.domain.Transaction;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

public class TransactionUpdateProcessor {
    private static final Log log = LogFactory.getLog(TransactionUpdateProcessor.class);

    private CommonTransactionHelper commonTransactionHelper;
    private ProcessingBO processingBO;
    private IntegrationDao dao;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public TransactionUpdateResult updateServiceOrder(Transaction transaction)
            throws AncestorNotFoundException
    {
        commonTransactionHelper.populateSLOrderId(transaction);
        transaction.getServiceOrder().setSoId(transaction.getServiceLiveOrderId());
        Identification buyerIdentity = commonTransactionHelper.createBuyerIdentification(transaction);
        ServiceOrder txServiceOrder = transaction.getServiceOrder();
        log.debug("pre processingBO.processUpdateBatch(): " + transaction);
        OrderStatusOnUpdate statusDuringUpdate = processingBO.processUpdateBatch(txServiceOrder, buyerIdentity);
        TransactionUpdateResult updateResult = TransactionUpdateResult.ORDER_UPDATED;
        if (statusDuringUpdate == OrderStatusOnUpdate.NOT_UPDATABLE) {
            Map<String,Object> processVariables = commonTransactionHelper.createProcessVarsForTxOrder(transaction);
            log.debug("pre processingBO.updateClosedOrder(): " + transaction);            
            String updatedSoId = processingBO.updateClosedOrder(buyerIdentity, txServiceOrder, processVariables);
            transaction.setServiceLiveOrderId(updatedSoId);
            if (!updatedSoId.equals(transaction.getServiceLiveOrderId())) {
                 updateResult = TransactionUpdateResult.NEW_ORDER_CREATED;
            }
        }

        log.debug("Transaction updated successfully:" + transaction);
        dao.markCompleted(transaction);
        log.debug("Transaction Completed:" + transaction);
        return updateResult;
    }

    public void setCommonTransactionHelper(CommonTransactionHelper commonTransactionHelper) {
        this.commonTransactionHelper = commonTransactionHelper;
    }

    public void setProcessingBO(ProcessingBO processingBO) {
        this.processingBO = processingBO;
    }

    public void setDao(IntegrationDao dao) {
        this.dao = dao;
    }
}
