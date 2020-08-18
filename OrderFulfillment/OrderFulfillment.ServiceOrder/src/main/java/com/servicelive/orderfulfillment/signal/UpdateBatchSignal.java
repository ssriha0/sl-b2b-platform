package com.servicelive.orderfulfillment.signal;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.servicelive.orderfulfillment.common.OFConstants;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.RoutedProvider;
import com.servicelive.orderfulfillment.domain.SOContact;
import com.servicelive.orderfulfillment.domain.SOCustomReference;
import com.servicelive.orderfulfillment.domain.SOLocation;
import com.servicelive.orderfulfillment.domain.SONote;
import com.servicelive.orderfulfillment.domain.SOTask;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.ConditionalOfferReason;
import com.servicelive.orderfulfillment.domain.type.LegacySOSubStatus;
import com.servicelive.orderfulfillment.domain.type.ProviderResponseType;
import com.servicelive.orderfulfillment.domain.util.PricingUtil;
import com.servicelive.orderfulfillment.domain.type.SOTaskType;
import com.servicelive.orderfulfillment.notification.INotificationTaskProcessor;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;

public class UpdateBatchSignal extends EditSignal {


	protected INotificationTaskProcessor notificationTaskProcessor;
	@Override
	public void accessMiscParams(Map<String, Serializable> miscParams, Identification id, ServiceOrder so) {	
		/**SL-21285 : Code changes to add expired status to accommodate spend limit reverse change for the order moves to expire 
		   and then to draft because of an update and finally deleted status from
		   FE and the so project balance is stuck with the service order*/
		logger.info("UpdateBatchSignal setting IS_CURRENTLY_POSTED for SOID: "+ so.getSoId()+"with wf state id"+so.getWfStateId());
		if(OFConstants.POSTED_STATUS.equals(so.getWfStateId()) || OFConstants.EXPIRED_STATUS.equals(so.getWfStateId())){
			miscParams.put(ProcessVariableUtil.IS_CURRENTLY_POSTED, true);
		}
		else{
			miscParams.put(ProcessVariableUtil.IS_CURRENTLY_POSTED, false);
		}
	}
	    

	@Override
	protected void changeRoutedProviders(ServiceOrder source, ServiceOrder so){
		//do nothing since we do not change routed providers
		//it will be auto routed
	}

	@Override
    protected void updateServiceContact(SOContact source, ServiceOrder so) {
        super.updateServiceContact(source, so);
        //log that the contact changed in so_logging
        soLoggingCmdHelper.logServiceOrderActivity(so, "contactChangeLogging");
    }

    @Override
    protected void updateDescription(ServiceOrder so, ServiceOrder source) {
    	so.setOldSowDesc(so.getSowDs());
        super.updateDescription(so, source);
        //log that the provider instructions changed in so_logging
        soLoggingCmdHelper.logServiceOrderActivity(so, "descriptionChangeLogging");
        if(so.getWfStateId() == 150 || so.getWfStateId() == 155)    	
        sendEmail(so,"emailProviderForUpdateSODescription");
    }
    
    private void sendEmail(ServiceOrder so, String emailContextName) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(ProcessVariableUtil.PVKEY_SVC_ORDER_ID, so.getSoId());
        notificationTaskProcessor.processNotificationTask(emailContextName, params);     
    }

    @Override
    protected void updateProviderInstruction(ServiceOrder so, ServiceOrder source) {
    	so.setOldProviderInstructions(so.getProviderInstructions());
        super.updateProviderInstruction(so, source);
        //log that the provider instructions changed in so_logging
        soLoggingCmdHelper.logServiceOrderActivity(so, "providerInstructionChangeLogging");
        if(so.getWfStateId() == 150 || so.getWfStateId() == 155 || so.getWfStateId() == 170 || so.getWfStateId() == 165)
        	sendEmail(so,"emailProviderForUpdateInstructions");
    }

	@Override
	protected void updateServiceLocation(SOLocation source, ServiceOrder so, boolean zipChanged){
		//change the sub-status to followup
		//SL-17875 Not overriding the substatus if the Service Order is in Pending Cancel state
		if(!(so.getWfStateId() == 165)){
			so.setWfSubStatusId(LegacySOSubStatus.FOLLOW_UP_NEEDED.getId());
		}
		//maintaining the value for email
		SOLocation oldLocation = new SOLocation();
		SOLocation oldLocObj = so.getServiceLocation();
		oldLocation.setStreet1(oldLocObj.getStreet1());
		oldLocation.setStreet2(oldLocObj.getStreet2());
		oldLocation.setState(oldLocObj.getState());
		oldLocation.setCity(oldLocObj.getCity());
		oldLocation.setZip(oldLocObj.getZip());
		oldLocation.setCountry(oldLocObj.getCountry());
		oldLocation.setSoLocationClassId(oldLocObj.getSoLocationClassId()); 
		so.setOldLocation(oldLocation);
		super.updateServiceLocation(source, so, zipChanged);
        //log that the provider instructions changed in so_logging
        soLoggingCmdHelper.logServiceOrderActivity(so, "locationChangeLogging");
	}
	
	@Override
	protected void updateSchedule(ServiceOrder source, ServiceOrder target) {
        //check if the target has counter offers or not
		//if not update the schedule otherwise ignore
		//in case of order in other states we should not be changing the schedule
		for(RoutedProvider rp : target.getRoutedResources()){
			if (rp.getProviderResponse() == ProviderResponseType.CONDITIONAL_OFFER
					&& (rp.getProviderRespReasonId() == ConditionalOfferReason.RESCHEDULE_SERVICE_DATE.getId()
							|| rp.getProviderRespReasonId() == ConditionalOfferReason.SERVICE_DATE_AND_SPEND_LIMIT.getId())){
				return;
			}
		}
		super.updateSchedule(source, target);
    }
	
	@Override
	protected void updatePricing(ServiceOrder source, ServiceOrder target){
		logger.info("SL-16136:Inside UpdateBatchSignal.updatePricing() ");
		// Calculate the difference between old and new price.  Do not use ServiceOrder.getTotalSpendLimit, since only the target
		// object takes the counter-offer price into consideration. 
		BigDecimal sourceInitialSpendLimit = getInitialSpendLimit(source);
		BigDecimal targetInitialSpendLimit = getInitialSpendLimit(target);
		BigDecimal priceDifference = sourceInitialSpendLimit.subtract(targetInitialSpendLimit);
		
		BigDecimal newSpendLimitLabor = target.getSpendLimitLabor().add(priceDifference);
		BigDecimal oldSpendLimitParts = target.getSpendLimitParts();
		
		 //Making changes for Price calculation for RI
		BigDecimal newTotalSpendLimitNotes = PricingUtil.ZERO;
		BigDecimal newNotesDiff  = PricingUtil.ZERO;
        if(source.getBuyerId()==1000){        	
        	logger.info("inside RI specific code in UpdateBatchSignal");
        	List<SOTask> injectedOldTasks=target.getTasksForScopeChangeComparison();
            List<SOTask> newTasks=source.getTasks();
            List<SOTask> oldManageScopeTasks=target.getManageScopeTasks();           
            BigDecimal newAmtForNotesLabor = calculateNewLaborPrice(injectedOldTasks,newTasks,oldManageScopeTasks);
            logger.info("newAmtForNotesLabor in UpdateBatchSignal: " + newAmtForNotesLabor);
            BigDecimal newAmtForNotesParts = source.getSpendLimitParts();
            BigDecimal targetPartsPrice = target.getSpendLimitParts();
            if(null!=newAmtForNotesParts && newAmtForNotesParts.equals(PricingUtil.ZERO)
            		&& null!=targetPartsPrice && !(targetPartsPrice.equals(PricingUtil.ZERO))){
            	newAmtForNotesParts=newAmtForNotesParts.add(targetPartsPrice);
            }
            logger.info("newAmtForNotesParts in UpdateBatchSignal: " + newAmtForNotesParts);
            newTotalSpendLimitNotes = newAmtForNotesLabor.add(newAmtForNotesParts);            
            newNotesDiff = newTotalSpendLimitNotes.subtract(target.getTotalSpendLimit());
            logger.info("newNotesDiff in UpdateBatchSignal: " + newNotesDiff);
        }
		
		//create note that the spend limit changed
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("OLD_AMOUNT", target.getTotalSpendLimit());
		if(source.getBuyerId()==1000){
			dataMap.put("NEW_AMOUNT", newTotalSpendLimitNotes);
			dataMap.put("DIFFERENCE", newNotesDiff.abs());
			dataMap.put("TYPE", (newNotesDiff.compareTo(PricingUtil.ZERO) > 0)? "increased" : "decreased");
			logger.info("Done Notes setting for RI in UpdateBatchSignal: ");
		}else{
			dataMap.put("NEW_AMOUNT", target.getTotalSpendLimit().add(priceDifference));
			dataMap.put("DIFFERENCE", priceDifference.abs());
			dataMap.put("TYPE", (priceDifference.compareTo(PricingUtil.ZERO) > 0)? "increased" : "decreased");
		}		
		SONote note = noteUtil.getNewNote(target, "SpendLimitChange", dataMap);
        serviceOrderDao.save(note);
       
		super.updatePricing(source, target);
		
		logger.info("SL-16136:Inside UpdateBatchSignal.updatePricing() before:"+target.getSpendLimitLabor());
		// super.updatePricing does not take counter-offer into account when setting the new spend limit
		target.setSpendLimitLabor(newSpendLimitLabor);
		logger.info("SL-16136:Inside UpdateBatchSignal.updatePricing() after:"+target.getSpendLimitLabor());
		if (oldSpendLimitParts != null && oldSpendLimitParts.compareTo(BigDecimal.ZERO) > 0) {
        	// Since spend limit for parts is not part of the injected file, keep the existing spend limit for parts
        	target.setSpendLimitParts(oldSpendLimitParts);
	}
	}
	// New method to calculate the total labor price for RI tasks
    private BigDecimal calculateNewLaborPrice(List<SOTask> injectedOldTasks, List<SOTask> newTasks,List<SOTask> oldManageScopeTasks){
    	BigDecimal newAmountForLabor = PricingUtil.ZERO;
    	try{    		
            for(SOTask newTask : newTasks){
            	if(!(newTask.getTaskType().equals(SOTaskType.Delivery.getId()) || newTask.getTaskType().equals(SOTaskType.NonPrimary.getId()))){
            		for(SOTask oldTask: injectedOldTasks){
            			if(newTask.getExternalSku().equals(oldTask.getExternalSku()) && newTask.getSequenceNumber().equals(oldTask.getSequenceNumber())){
            				if(null!=oldTask.getFinalPrice()){
            					newTask.setFinalPrice(oldTask.getFinalPrice());
                            }else if(null==oldTask.getFinalPrice() && oldTask.getTaskStatus().equals("CANCELED")){
                                newTask.setFinalPrice(PricingUtil.ZERO);
                            }
                        }
                    }
            	}
            }
            for(SOTask newTask : newTasks){
            	if(null!=newTask.getFinalPrice()){
            		newAmountForLabor=newAmountForLabor.add(newTask.getFinalPrice());
            	}                    
            }
            for(SOTask msTask :oldManageScopeTasks){
            	if(!(msTask.getTaskStatus().equals("CANCELED"))){
            		newAmountForLabor=newAmountForLabor.add(msTask.getFinalPrice());
            	}                    
            }
    		
    	}catch(Exception e){
    		logger.info("Exception in calculateNewLaborPrice of UpdateBatchSignal: " +e.getStackTrace());
    		newAmountForLabor=PricingUtil.ZERO;
    		return newAmountForLabor;
    	}
        return newAmountForLabor;
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


	@Override
	protected void updateCustomRef(ServiceOrder source, ServiceOrder target) {
		boolean changed = false;
		
		StringBuilder oldReferences = new StringBuilder();
		StringBuilder newReferences = new StringBuilder();
		int i = 0;
		for (SOCustomReference ref : source.getCustomReferences()) {
			boolean found = false;
			for (SOCustomReference soRef : target.getCustomReferences()) {
				if (soRef.getBuyerRefTypeId().equals(ref.getBuyerRefTypeId())) {
					found = true;
					if (soRef.isUpdatable() && !soRef.getBuyerRefValue().equalsIgnoreCase(ref.getBuyerRefValue())) {
						if(i != 0)
						{
							oldReferences.append(" , ");
							newReferences.append(" , ");
						}
						oldReferences.append(soRef.getBuyerRefTypeName() + " - " + soRef.getBuyerRefValue());					
						soRef.setBuyerRefValue(ref.getBuyerRefValue());
						newReferences.append(soRef.getBuyerRefTypeName() + " - " + soRef.getBuyerRefValue());
                        changed = true;
                        i++;
					}
					break;
				}
			}
			if(!found){//add
				if(i != 0)
				{
					newReferences.append(" , ");
				}
				newReferences.append(ref.getBuyerRefTypeName() + " - " + ref.getBuyerRefValue());
				target.addCustomReference(ref);			
                changed = true;
                i++;
			}
		}
        //log that the custom references changed in so_logging
        if(changed)
        {
        	soLoggingCmdHelper.logServiceOrderActivity(target, "customRefChangeLogging");
        	target.setOldCustomRefs(oldReferences.toString());
        	target.setUpdateCustomRefs(newReferences.toString());
        	if(target.getWfStateId() == 150 || target.getWfStateId() == 155 || target.getWfStateId() == 170)
        	sendEmail(target,"emailProviderForUpdateSOCustomRef");
        }
	}
	
	 public void setNotificationTaskProcessor(INotificationTaskProcessor notificationTaskProcessor) {
	        this.notificationTaskProcessor = notificationTaskProcessor;
	    }

}
