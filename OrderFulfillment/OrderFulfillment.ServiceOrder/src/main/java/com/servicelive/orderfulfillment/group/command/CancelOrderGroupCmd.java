package com.servicelive.orderfulfillment.group.command;

import java.util.Map;

import com.servicelive.orderfulfillment.common.OFConstants;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.SOGroup;
import com.servicelive.orderfulfillment.domain.SOPriceChangeHistory;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.ServiceOrderProcess;
import com.servicelive.orderfulfillment.domain.util.PricingUtil;

public class CancelOrderGroupCmd extends GroupSignalSOCmd {

	@Override
	public void handleGroup(SOGroup soGroup, Map<String, Object> processVariables) {
		ServiceOrder eSO = getEffectiveServiceOrder(processVariables);
		int size = soGroup.getServiceOrders().size();

		//There are two orders in the group if we are removing one we need to delete the group record
		if (size == 2){
			String activeSoId = deleteGroup(soGroup, eSO.getSoId());
			//remove groupId from the process variable 
			//and put the serviceOrder id that is still active
			processVariables.put(ProcessVariableUtil.PVKEY_GROUP_ID, null);
			processVariables.put(ProcessVariableUtil.PVKEY_SERVICE_ORDER_IDS_FOR_GROUP_PROCESS, activeSoId);
		}else{
			removeServiceOrderFromGroup(eSO, false);
            //also remove from soGroup
            soGroup.getServiceOrders().remove(eSO);
				}
		resetProcessVariables(size - 1, processVariables);
	}

	@Override
	public void handleServiceOrder(ServiceOrder so, Map<String, Object> processVariables) {
		//This is the only service order so lets make the number of service order 0
		resetProcessVariables(0, processVariables);
	}

	private String deleteGroup(SOGroup soGroup, String effectedSOId){
		String activeSoId = null;
		for(ServiceOrder so : soGroup.getServiceOrders()){
			if (so.getSoId().equals(effectedSOId)){
				so.setFromUngroupSOFlowCancellation(true);
				removeServiceOrderFromGroup(so, false);
			} else {
				removeServiceOrderFromGroup(so, true);
				activeSoId = so.getSoId();
			}
            serviceOrderDao.update(so);
		}
		serviceOrderDao.refresh(soGroup);
		serviceOrderDao.delete(soGroup);
		return activeSoId;
	}
	
	private void removeServiceOrderFromGroup(ServiceOrder so, boolean keepSearchable){
        if(null != so.getSoGroup()){
            //remove trip charge discount
            so.setSpendLimitLabor(so.getPrice().getOrigSpendLimitLabor());
            so.setSpendLimitParts(so.getPrice().getOrigSpendLimitParts());
            //delete the group from so
            so.setSoGroup(null);
            so.getPrice().reset();
            
            /**
    		 * SL-18007 setting so price change history if there is a change in the
    		 * so spend limit labour during cancellation of service order from a group order.
    		 */
    		if (null != so.getSoPriceChangeHistory()) {

    			logger.info("18007: so price change history contins: " + so.getSoPriceChangeHistory());
    			
    			SOPriceChangeHistory newSOPriceChangeHistory = new SOPriceChangeHistory();
    					
    			// set specific components before passing to the generic method
    			newSOPriceChangeHistory.setAction(OFConstants.ORDER_REMOVED_FROM_GROUP);
    			newSOPriceChangeHistory.setReasonComment(null);
    			
    			
    			// call generic method to save so price change history			
    			PricingUtil.addSOPriceChangeHistory(so, newSOPriceChangeHistory,null, null);
    		}
    		
            serviceOrderDao.update(so);
        }
		ServiceOrderProcess sop = serviceOrderProcessDao.getServiceOrderProcess(so.getSoId());
		sop.setGroupId(null);
		if (!keepSearchable){
			sop.setGroupProcessId(null);
			sop.setGroupingSearchable(false);
		}
		serviceOrderProcessDao.update(sop);
	}
	
	private void resetProcessVariables(int soNumber, Map<String, Object> processVariables){
		processVariables.put(ProcessVariableUtil.PVKEY_NUMBER_OF_SERVICE_ORDER, soNumber);
		//clear out the effective service order
		processVariables.put(ProcessVariableUtil.PVKEY_EFFECTIVE_SERVICE_ORDER, null);	
	}
}
