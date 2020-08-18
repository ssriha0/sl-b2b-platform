package com.servicelive.orderfulfillment.signal;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

import com.servicelive.orderfulfillment.domain.RoutedProvider;
import com.servicelive.orderfulfillment.domain.SOCounterOfferReason;
import com.servicelive.orderfulfillment.domain.SONote;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.LegacySOSubStatus;
import com.servicelive.orderfulfillment.domain.type.ProviderResponseType;
import com.servicelive.orderfulfillment.domain.type.SOFieldsChangedType;

public class UpdateRIBatchSignal extends UpdateBatchSignal {

	@Override
	protected void updateScope(ServiceOrder source, ServiceOrder target, boolean primaryCategoryChanged, boolean tasksChanged) {
		target.setWfSubStatusId(LegacySOSubStatus.SCOPE_CHANGE.getId());
		serviceOrderDao.update(target);
		logger.info("primaryCategoryChanged::"+primaryCategoryChanged);
		if (primaryCategoryChanged){
			//create a note that the scope changed
			String oldCategories = getCategories(target);
			String newCategories = getCategories(source);
			logger.info("oldCategories::"+oldCategories);
			logger.info("newCategories::"+newCategories);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("OLD_CATEGORIES", oldCategories);
			dataMap.put("NEW_CATEGORIES", newCategories);
			SONote note = noteUtil.getNewNote(target, "TemplateRemapped", dataMap);
            serviceOrderDao.save(note);
    	}
		super.updateScope(source, target, primaryCategoryChanged, tasksChanged);
	}

    @Override
    protected void updatePricing(ServiceOrder source, ServiceOrder target){
    	
    	logger.info("Preventing price update if no scope change as part of SL-16136");
    	
		List<SOFieldsChangedType> changes = target.compareTo(source);
		boolean repost=false;
		repost=target.isRepost();
    		logger.info("Inside RI batch signal>>"+repost);
    		boolean isRePost = target.isRepost();
            logger.info("UpdateRIBatchSignal.getProviders()>>"+isRePost);
            boolean isUpdate = target.isUpdate();
            logger.info("UpdateRIBatchSignal.getProviders()>>"+isUpdate);
            
            logger.info("target.getWfStateId()"+target.getWfStateId());
            
            if (null!=target.getWfStateId() && target.getWfStateId().intValue()<150){
            
            
        if (null!=target.getWfStateId() && target.getWfStateId()<150  && null!=changes && changes.contains(SOFieldsChangedType.PRIMARY_SKILL_CATEGORY)
        		|| changes.contains(SOFieldsChangedType.TASKS_ADDED) 
        		|| changes.contains(SOFieldsChangedType.TASKS_DELETED) || repost){        
        	logger.info("Inside RI batch signal>>Repost scenario");
        	super.updatePricing(source, target);
        	logger.info("ABT TO DELETE");
        	logger.info("serviceOrder.getRoutedResources().size() befor delete>>"+target.getRoutedResources().size());
        	//delete so_routed_providers to enable rerouting
        	serviceOrderDao.deleteSoRoutedProviders(target.getSoId());
        	//delete counter offer deatils if any
        	serviceOrderDao.deleteSoCounterOffers(target.getSoId());
        	target.setRoutedResources(new ArrayList<RoutedProvider>());
        	logger.info("serviceOrder.getRoutedResources().size() after delete>>"+target.getRoutedResources().size());
        }else{
        	
        	 if (null!=target.getWfStateId() && target.getWfStateId()<150){
        	logger.info("Inside Else");
        	  //clear conditional offers
    		for(RoutedProvider rp : target.getRoutedResources()){
    			if (rp.getProviderResponse() == ProviderResponseType.CONDITIONAL_OFFER){
    				for(SOCounterOfferReason reason: rp.getCounterOffers()){
    		            reason.setRoutedProvider(null);
    		            serviceOrderDao.delete(reason);
    		        }
    				rp.expireConditionalOffer();
    			}
    		}
          }
        }
            }
        
        //do we need to do this since for RI the only time price will change
        //when the task will change therefore it will be rerouted anyway
    }
    
    
    
}
