package com.servicelive.orderfulfillment.group.command;

import java.util.List;
import java.util.Map;

import com.servicelive.orderfulfillment.common.OFConstants;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.GroupRoutedProvider;
import com.servicelive.orderfulfillment.domain.RoutedProvider;
import com.servicelive.orderfulfillment.domain.SOCounterOfferReason;
import com.servicelive.orderfulfillment.domain.SOGroup;
import com.servicelive.orderfulfillment.domain.SOPriceChangeHistory;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.ServiceOrderProcess;
import com.servicelive.orderfulfillment.domain.type.ProviderResponseType;
import com.servicelive.orderfulfillment.domain.type.SOFieldsChangedType;
import com.servicelive.orderfulfillment.domain.util.PricingUtil;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;

public class BatchUpdateCmd extends GroupSignalSOCmd {

	@SuppressWarnings("unchecked")
	@Override
	protected void handleGroup(SOGroup soGroup, Map<String, Object> processVariables) {
		ServiceOrder eSO = getEffectiveServiceOrder(processVariables);
		logger.info(" eso service order Id *(***"+eSO.getSoId());
		List<SOFieldsChangedType> changes = (List<SOFieldsChangedType>) processVariables.get(ProcessVariableUtil.SERVICE_ORDER_CHANGES);

        //check if the criteria has changed and if it has than set the number of orders to zero this will end the workflow 
		//To be consistent we will use the changes passed in to the JBPM to determine if we need to abandon the group and start new one
        if (changes.contains(SOFieldsChangedType.PRIMARY_SKILL_CATEGORY)
				|| changes.contains(SOFieldsChangedType.SERVICE_LOCATION)
				|| changes.contains(SOFieldsChangedType.SCHEDULE)
				|| changes.contains(SOFieldsChangedType.TASKS_ADDED) 
        		|| changes.contains(SOFieldsChangedType.TASKS_DELETED)){
    		int size = soGroup.getServiceOrders().size();
			//There are two orders in the group if we are removing one we need to delete the group record
			if (size == 2){
				logger.info(" for group with 2 members...*(***");
				String activeSoId = deleteGroup(soGroup, eSO.getSoId());
				//remove groupId from the process variable 
				//and put the serviceOrder id that is still active
				processVariables.put(ProcessVariableUtil.PVKEY_GROUP_ID, null);
				processVariables.put(ProcessVariableUtil.PVKEY_SERVICE_ORDER_IDS_FOR_GROUP_PROCESS, activeSoId);
                if(sendSpendLimitChange(processVariables)){
                //send spend limit changed signal to the remaining service order
                logger.debug("Calling spend limit changed for so " + activeSoId);
                ServiceOrder so = serviceOrderDao.getServiceOrder(activeSoId);
                this.sendSignalToSOProcess(so, SignalType.SPEND_LIMIT_CHANGED, null, getProcessVariableForSendEmail(false));
                }
			}else{
				logger.info(" for group not equal to 2 members...*(***");
				for(ServiceOrder so : soGroup.getServiceOrders()){
					if (so.getSoId().equals(eSO.getSoId())){
						logger.info(" removeServiceOrderFromGroup *(***");
					
						removeServiceOrderFromGroup(so, false, true);
					}
				}			
			}
			processVariables.put(ProcessVariableUtil.PVKEY_NUMBER_OF_SERVICE_ORDER, size - 1);
			
        } else if (changes.contains(SOFieldsChangedType.SPEND_LIMIT_LABOR_CHANGED)
				|| changes.contains(SOFieldsChangedType.SPEND_LIMIT_PARTS_CHANGED)) {
			expireGroupConditionalOffers(soGroup);
			for(ServiceOrder so : soGroup.getServiceOrders()){
				expireServiceOrderConditionalOffers(so);
        }
	}
		
		clearEffectiveServiceOrder(processVariables);
	}

	private void expireServiceOrderConditionalOffers(ServiceOrder so) {
		for(RoutedProvider rp : so.getRoutedResources()){
			if (rp.getProviderResponse() == ProviderResponseType.CONDITIONAL_OFFER){
				
				for(SOCounterOfferReason reason: rp.getCounterOffers()){
		            reason.setRoutedProvider(null);
		            serviceOrderDao.delete(reason);
		        }
				
				rp.expireConditionalOffer();
			}
		}
	}

	private void expireGroupConditionalOffers(SOGroup soGroup) {
		soGroup.getGroupRoutedProviders();
		for(GroupRoutedProvider rp : soGroup.getGroupRoutedProviders()){
			if (rp.getProviderResponse() == ProviderResponseType.CONDITIONAL_OFFER){
				rp.expireConditionalOffer();
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void handleServiceOrder(ServiceOrder so, Map<String, Object> processVariables) {
        ServiceOrder eSO = getEffectiveServiceOrder(processVariables);
		List<SOFieldsChangedType> changes = (List<SOFieldsChangedType>) processVariables.get(ProcessVariableUtil.SERVICE_ORDER_CHANGES);
        //check if the criteria has changed and if it has than set the number of orders to zero this will end the workflow 
		//To be consistent we will use the changes passed in to the JBPM to determine if we need to abandon the group and start new one
        if (changes.contains(SOFieldsChangedType.PRIMARY_SKILL_CATEGORY)
				|| changes.contains(SOFieldsChangedType.SERVICE_LOCATION)
				|| changes.contains(SOFieldsChangedType.SCHEDULE)
				|| changes.contains(SOFieldsChangedType.TASKS_ADDED) 
        		|| changes.contains(SOFieldsChangedType.TASKS_DELETED)){
            processVariables.put(ProcessVariableUtil.PVKEY_NUMBER_OF_SERVICE_ORDER, Integer.valueOf(0));
            removeServiceOrderFromGroup(eSO, false, false);
        }
        clearEffectiveServiceOrder(processVariables);
    }
	
	private void removeServiceOrderFromGroup(ServiceOrder so, boolean keepSearchable, boolean deleteRP){
		if(null != so.getSoGroup() || deleteRP){
		if(null != so.getSoGroup()){
            //remove trip charge discount
            so.setSpendLimitLabor(so.getPrice().getOrigSpendLimitLabor());
            so.setSpendLimitParts(so.getPrice().getOrigSpendLimitParts());
            so.getPrice().reset();
                //delete the group from so
                so.setSoGroup(null);
                
            /**
    		 * SL-18007 setting so price change history if there is a change in the
    		 * so spend limit labour during batch update of service order from a group order.
    		 */
    		if (null != so.getSoPriceChangeHistory()) {

    			logger.info("18007: so price change history contins: " + so.getSoPriceChangeHistory());
    			
    			SOPriceChangeHistory newSOPriceChangeHistory = new SOPriceChangeHistory();
    					
    			// set specific components before passing to the generic method
    			newSOPriceChangeHistory.setAction(OFConstants.SCOPE_UPDATED);
    			newSOPriceChangeHistory.setReasonComment(null);
    			
    			
    			// call generic method to save so price change history			
    			PricingUtil.addSOPriceChangeHistory(so, newSOPriceChangeHistory,so.getModifiedBy(), so.getModifiedByName());
    		}
        }
        if(deleteRP)
            deleteRoutedProviders(so.getRoutedResources());
        logger.info("removing group ***)))"+so.getSoId());
		    serviceOrderDao.update(so);
        }
        //also update service order process map
		ServiceOrderProcess sop = serviceOrderProcessDao.getServiceOrderProcess(so.getSoId());
		sop.setGroupId(null);
		if (!keepSearchable){
			sop.setGroupProcessId(null);
			sop.setGroupingSearchable(false);
		}
		serviceOrderProcessDao.update(sop);
	}
	
	private void deleteRoutedProviders(List<RoutedProvider> routedResources) {
		if(routedResources != null){
			for(RoutedProvider rp: routedResources){
				rp.setServiceOrder(null);
				serviceOrderDao.delete(rp);
			}
			routedResources.clear();
		}
	}

	private String deleteGroup(SOGroup soGroup, String effectedSOId){
		String activeSoId = null;
		for(ServiceOrder so : soGroup.getServiceOrders()){
			if (so.getSoId().equals(effectedSOId)){
				removeServiceOrderFromGroup(so, false, true);
			} else {
				removeServiceOrderFromGroup(so, true, false);
				activeSoId = so.getSoId();
			}
            serviceOrderDao.update(so);
		}
		serviceOrderDao.refresh(soGroup);
		serviceOrderDao.delete(soGroup);
		return activeSoId;
	}
	
    
}
