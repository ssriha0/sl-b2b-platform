package com.servicelive.orderfulfillment.signal;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.servicelive.orderfulfillment.common.OFConstants;
import com.servicelive.orderfulfillment.domain.SOElement;
import com.servicelive.orderfulfillment.domain.SONote;
import com.servicelive.orderfulfillment.domain.SOPriceChangeHistory;
import com.servicelive.orderfulfillment.domain.SOTask;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.SOFieldsChangedType;
import com.servicelive.orderfulfillment.domain.util.PricingUtil;

public class RIScopeChangeSignal extends EditSignal {

    @Override
	protected void update(SOElement soe, ServiceOrder so){
    	logger.info("SL-16136:Inside RIScopeChangeSignal.update() ");
		logger.debug("Inside EditSignal.update()");
		
		ServiceOrder source = (ServiceOrder)soe;
		
		List<SOFieldsChangedType> changes = so.compareTo(source);
        if (changes.contains(SOFieldsChangedType.PRIMARY_SKILL_CATEGORY)){
        	logger.debug("primary skill category change");
        	so.setPrimarySkillCatId(source.getPrimarySkillCatId());
        }
 
        if (changes.contains(SOFieldsChangedType.TASKS_ADDED) || changes.contains(SOFieldsChangedType.TASKS_DELETED)){
        	logger.debug("task change: RI Scope Chnage Signal");
        	/*if(so.getTasks() != null) deleteChildren(so.getTasks());
        	if(source.getTasks() != null) so.setTasks(source.getTasks());*/
        	
        	

        	//To handle one to many mapping of tasks for a sku
        	
        	int targetSeqNo=-1;
    		int targetTaskNo=0;
    		for (SOTask task : so.getNonDeletedTasks()) {
    		
    			if(null!=task.getSequenceNumber() && task.getSequenceNumber().intValue()==targetSeqNo )
    			{    				
    				targetTaskNo=targetTaskNo +1;
    			
    			}else{
    				targetTaskNo=1;
    			}
    			task.setTaskNo(targetTaskNo);
    			if(null!=task.getSequenceNumber())
    			{
    			targetSeqNo=task.getSequenceNumber().intValue();
    			}
    		}
    		
    		int seqNo=-1;
    		int taskNo=0;
    		
    		for (SOTask task : source.getNonDeletedTasks()) {
        		
    			if(null!=task.getSequenceNumber() && task.getSequenceNumber().intValue()==seqNo )
    			{    				
    				taskNo=taskNo +1;
    			}else{
    				taskNo=1;
    			}
    			task.setTaskNo(taskNo);
    			if(null!=task.getSequenceNumber())
    			{
    			seqNo=task.getSequenceNumber().intValue();
    			}
    		}
    		
    		
        	
        	super.updateTask(source, so, true);
        }
        
        //also remove the final price
        so.setFinalPriceLabor(PricingUtil.ZERO);
        so.setFinalPriceParts(PricingUtil.ZERO);
        
        if(changes.contains(SOFieldsChangedType.SPEND_LIMIT_LABOR_CHANGED) || changes.contains(SOFieldsChangedType.SPEND_LIMIT_PARTS_CHANGED)){
        	logger.info("Inside price calculation of RIScopeChangeSignal");
        	// Calculate the difference between old and new price.  Do not use ServiceOrder.getTotalSpendLimit, since only the target
    		// object takes the counter-offer price into consideration. 
    		BigDecimal sourceInitialSpendLimit = getInitialSpendLimit(source);
    		BigDecimal targetInitialSpendLimit = getInitialSpendLimit(so);
    		BigDecimal priceDifference = sourceInitialSpendLimit.subtract(targetInitialSpendLimit);
    		BigDecimal newSpendLimitLabor = so.getSpendLimitLabor().add(priceDifference);
    		BigDecimal oldSpendLimitParts = so.getSpendLimitParts();
    		
    		//create note that the spend limit changed
    		Map<String, Object> dataMap = new HashMap<String, Object>();
    		dataMap.put("OLD_AMOUNT", so.getTotalSpendLimit());
    		dataMap.put("NEW_AMOUNT", so.getTotalSpendLimit().add(priceDifference));
    		dataMap.put("DIFFERENCE", priceDifference.abs());
    		dataMap.put("TYPE", (priceDifference.compareTo(PricingUtil.ZERO) > 0)? "increased" : "decreased");
    		SONote note = noteUtil.getNewNote(so, "SpendLimitChange", dataMap);
            serviceOrderDao.save(note);
           
    		super.updatePricing(source, so);
    		
    		// super.updatePricing does not take counter-offer into account when setting the new spend limit
    		so.setSpendLimitLabor(newSpendLimitLabor);
    		if (oldSpendLimitParts != null && oldSpendLimitParts.compareTo(BigDecimal.ZERO) > 0) {
            	// Since spend limit for parts is not part of the injected file, keep the existing spend limit for parts
    			so.setSpendLimitParts(oldSpendLimitParts);
    }
    		/**
			 * SL-18007 setting so price change history if there is a scope change for SOs in completed state
			 * 
			 */
			if (null != so.getSoPriceChangeHistory()) {

				logger.info("18007: so price change history contins: " + so.getSoPriceChangeHistory());
				
				SOPriceChangeHistory newSOPriceChangeHistory = new SOPriceChangeHistory();
						
				// set specific components before passing to the generic method
				newSOPriceChangeHistory.setAction(OFConstants.SCOPE_UPDATED);
				newSOPriceChangeHistory.setReasonComment(null);
				so.setFromRIScopeChangeFlowWhileCompleted(true);
				// call generic method to save so price change history			
				PricingUtil.addSOPriceChangeHistory(so, newSOPriceChangeHistory, PricingUtil.SYSTEM, PricingUtil.SYSTEM);
			} 	
}
        
    }

	private BigDecimal getInitialSpendLimit(ServiceOrder so) {
		BigDecimal initialSpendLimit = PricingUtil.ZERO;
		if (so.getPrice() != null) {
        	if (so.getPrice().getInitPostedSpendLimitLabor() != null) {
        		initialSpendLimit = initialSpendLimit.add(so.getPrice().getInitPostedSpendLimitLabor());
        	}
        	if (so.getPrice().getInitPostedSpendLimitParts() != null) {
        		initialSpendLimit = initialSpendLimit.add(so.getPrice().getInitPostedSpendLimitParts());
        	}
        }
		return initialSpendLimit;
	}
}
