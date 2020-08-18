package com.servicelive.orderfulfillment.decision;

import org.jbpm.api.jpdl.DecisionHandler;
import org.jbpm.api.model.OpenExecution;

import com.servicelive.domain.spn.network.SPNHeader;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.dao.IServiceOrderDao;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

public abstract class AbstractServiceOrderDecision implements DecisionHandler {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1550997313191734957L;
	IServiceOrderDao serviceOrderDao;

    public void setServiceOrderDao(IServiceOrderDao serviceOrderDao) {
        this.serviceOrderDao = serviceOrderDao;
    }

    protected ServiceOrder getServiceOrder(OpenExecution execution) {
        String serviceOrderId = (String) execution.getVariable(ProcessVariableUtil.PVKEY_SVC_ORDER_ID);
        return serviceOrderDao.getServiceOrder(serviceOrderId);
    }
    protected boolean getCarFeatureOfBuyer(OpenExecution execution)
    {
    	 String serviceOrderId = (String) execution.getVariable(ProcessVariableUtil.PVKEY_SVC_ORDER_ID);
    	 ServiceOrder so=serviceOrderDao.getServiceOrder(serviceOrderId);
    	 boolean conditionalRuleStatus=serviceOrderDao.getCarFeatureOfBuyer(Integer.parseInt(so.getBuyerId().toString()));
         return conditionalRuleStatus;
    }
    protected boolean getAutoAcceptFeatureOfBuyer(OpenExecution execution)
    {
    	String serviceOrderId = (String) execution.getVariable(ProcessVariableUtil.PVKEY_SVC_ORDER_ID);
   	 	ServiceOrder so=serviceOrderDao.getServiceOrder(serviceOrderId);
   	 	boolean AutoAcceptStatus=serviceOrderDao.getAutoAcceptFeatureOfBuyer(Integer.parseInt(so.getBuyerId().toString()));
        return AutoAcceptStatus;
    	
    }

    protected SPNHeader getSPNTierDetails(Integer spnId){
    	SPNHeader hdr = serviceOrderDao.fetchSpnDetails(spnId);
    	return hdr;
    }
}
