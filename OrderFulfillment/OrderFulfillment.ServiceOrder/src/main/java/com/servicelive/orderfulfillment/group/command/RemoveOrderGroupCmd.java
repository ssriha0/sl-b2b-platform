package com.servicelive.orderfulfillment.group.command;

import java.math.BigDecimal;
import java.util.Map;

import com.servicelive.orderfulfillment.common.OFConstants;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.domain.SOGroup;
import com.servicelive.orderfulfillment.domain.SOPriceChangeHistory;
import com.servicelive.orderfulfillment.domain.SOTask;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.ServiceOrderProcess;
import com.servicelive.orderfulfillment.domain.util.PricingUtil;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;
/**
 * Created by IntelliJ IDEA.
 * User: Yunus Burhani
 * Date: Sep 18, 2010
 * Time: 10:45:42 AM
 */
public class RemoveOrderGroupCmd extends GroupSignalSOCmd {
    @Override
    protected void handleGroup(SOGroup soGroup, Map<String, Object> processVariables) {
        ServiceOrder eSO = getEffectiveServiceOrder(processVariables);
        //remove all orders from group except the effective order
        String activeSoId = deleteGroup(soGroup, eSO.getSoId());
        //remove groupId from the process variable
        //and put the serviceOrder id that is still active
        processVariables.put(ProcessVariableUtil.PVKEY_GROUP_ID, null);
        processVariables.put(ProcessVariableUtil.PVKEY_SERVICE_ORDER_IDS_FOR_GROUP_PROCESS, activeSoId);
        processVariables.put(ProcessVariableUtil.PVKEY_NUMBER_OF_SERVICE_ORDER, 1);
        if(sendSpendLimitChange(processVariables)){
            //send spend limit changed signal to the remaining service order
            logger.debug("Calling spend limit changed for so " + activeSoId);
            ServiceOrder so = serviceOrderDao.getServiceOrder(activeSoId);
            this.sendSignalToSOProcess(so, SignalType.SPEND_LIMIT_CHANGED, null, getProcessVariableForSendEmail(false));
        }        
        clearEffectiveServiceOrder(processVariables);
    }

    @Override
    protected void handleServiceOrder(ServiceOrder so, Map<String, Object> processVariables) {
        throw new ServiceOrderException("Cannot remove the only order present");
    }

    private String deleteGroup(SOGroup soGroup, String effectedSOId){
		String activeSoId = null;
		for(ServiceOrder so : soGroup.getServiceOrders()){
			if (so.getSoId().equals(effectedSOId)){
				saveServiceOrder(so, true);
			} else {
				saveServiceOrder(so, false);
			}
            //delete the group from so
            so.setSoGroup(null);
            serviceOrderDao.update(so);
		}
		serviceOrderDao.refresh(soGroup);
		serviceOrderDao.delete(soGroup);
		return effectedSOId;
	}

	private void saveServiceOrder(ServiceOrder so, boolean keepProcessId){
        if(null != so.getSoGroup()){
            //remove trip charge discount
            so.setSpendLimitLabor(so.getPrice().getOrigSpendLimitLabor());
            so.setSpendLimitParts(so.getPrice().getOrigSpendLimitParts());
            int taskSize = so.getActiveTasks().size();
            for (SOTask soTask : so.getActiveTasks()) {
            	if (!soTask.getTaskName().trim().startsWith(OFConstants.PERMIT_SKU)) {
    				BigDecimal taskPrice = 	soTask.getFinalPrice();
    			if(taskPrice !=null && taskPrice.doubleValue() != 0.0)
    			{
    				soTask.setFinalPrice(soTask.getRetailPrice());
    			}
              }
            	
            }
            /**
    		 * SL-18007 setting so price change history if there is a change in the
    		 * so spend limit labour during Edition of service order from a group order.
    		 */
    		if (null != so.getSoPriceChangeHistory()) {

    			logger.info("18007: so price change history contins: " + so.getSoPriceChangeHistory());
    			
    			SOPriceChangeHistory newSOPriceChangeHistory = new SOPriceChangeHistory();
    					
    			// set specific components before passing to the generic method
    			newSOPriceChangeHistory.setAction(OFConstants.ORDER_EDITION);
    			newSOPriceChangeHistory.setReasonComment(null);
    			    			
    			// call generic method to save so price change history			
    			PricingUtil.addSOPriceChangeHistory(so, newSOPriceChangeHistory,so.getModifiedBy(),so.getModifiedByName());
    		}
            serviceOrderDao.update(so);
        }
		ServiceOrderProcess sop = serviceOrderProcessDao.getServiceOrderProcess(so.getSoId());
		sop.setGroupId(null);
		sop.setGroupingSearchable(false);
        if(!keepProcessId){
            sop.setGroupProcessId(null);
        }
		serviceOrderProcessDao.update(sop);
	}
}
