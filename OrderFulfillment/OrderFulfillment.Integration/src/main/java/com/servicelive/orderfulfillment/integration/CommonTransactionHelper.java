package com.servicelive.orderfulfillment.integration;

import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.SOCustomReference;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.RoleType;
import com.servicelive.orderfulfillment.integration.domain.Transaction;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonTransactionHelper {
    private static final Log log = LogFactory.getLog(CommonTransactionHelper.class);

    private IntegrationDao dao;

    public void populateSLOrderId(Transaction transaction) throws AncestorNotFoundException {
        String externalOrderNumber = transaction.getExternalOrderNumber();
        String slOrderId = dao.findLatestSLOrderIdForExternalOrderNum(externalOrderNumber, transaction.getId());
        if(StringUtils.isBlank(slOrderId)){
            String errMsg = String.format("Can not get SLOrderId. No preceding transaction found for order (%s).", externalOrderNumber);
            log.debug(errMsg);
            throw new AncestorNotFoundException(errMsg);
        }else{
            transaction.setServiceLiveOrderId(slOrderId);
        }
    }

    public Map<String, Serializable> createProcessVars(ServiceOrder serviceOrder){
        Map<String, Serializable> proVar = new HashMap<String, Serializable>();
        Map<String, String> customRef = new HashMap<String, String>();
        if(null != serviceOrder.getCustomReferences()){
            for(SOCustomReference cr : serviceOrder.getCustomReferences()){
                customRef.put(cr.getBuyerRefTypeName(),cr.getBuyerRefValue());
            }
            proVar.put(ProcessVariableUtil.CUSTOM_REFERENCE, (Serializable) customRef);
        }
        if (serviceOrder.getCancellationComment() != null && serviceOrder.getCancellationComment().length() > 0) {
        	proVar.put(OrderfulfillmentConstants.PVKEY_CANCELLATION_COMMENT, serviceOrder.getCancellationComment());
        }
        
        return proVar;
    }

    public Map<String, Object> createProcessVarsForTxOrder(Transaction transaction) {
        Map<String,Object> processVariables = new HashMap<String, Object>();
        processVariables.put(ProcessVariableUtil.PVKEY_BUYER_STATE_CODE,transaction.getBuyerState());
        //see if we have documents and pass them to the order fulfillment
        List<String> documentTitles = dao.getDocumentTitles(transaction.getServiceOrderId());
        if(documentTitles != null && documentTitles.size() > 0)
            processVariables.put(OrderfulfillmentConstants.PVKEY_DOCUMENT_TITLES, (Serializable)documentTitles);
        return processVariables;
    }

    public Identification createBuyerIdentification(Transaction transaction){
        Identification returnVal = new Identification();
        returnVal.setEntityType(Identification.EntityType.BUYER);
        returnVal.setRoleId(RoleType.System.getId());
        returnVal.setCompanyId(transaction.getPlatformBuyerId());
        returnVal.setResourceId(null);
        returnVal.setFullname("System");
        returnVal.setUsername("system");
        return returnVal;
    }

    public void setDao(IntegrationDao dao) {
        this.dao = dao;
    }
}
