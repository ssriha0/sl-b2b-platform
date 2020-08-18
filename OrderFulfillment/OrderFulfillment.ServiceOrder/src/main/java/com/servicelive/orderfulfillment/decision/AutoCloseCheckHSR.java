package com.servicelive.orderfulfillment.decision;



import java.sql.Date;
import java.sql.Timestamp;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jbpm.api.model.OpenExecution;

import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.SONote;
import com.servicelive.orderfulfillment.domain.SOProviderInvoiceParts;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;


public class AutoCloseCheckHSR extends AbstractServiceOrderDecision {

	/**
	 * 
	 */
	private static final long serialVersionUID = 100102764695148267L;
	private Logger logger = Logger.getLogger(AutoCloseCheckHSR.class);


    public String decide(OpenExecution execution) {
		ServiceOrder serviceOrder = getServiceOrder(execution);
		String returnValue = "true";
		logger.info("starting AutoCloseCheckHSR ");
		
		//Priority 5B changes
        //If validation error exists for model no. or serial no., prevent auto close
        String modelSerialError = (String)execution.getVariable(OrderfulfillmentConstants.PVKEY_MODEL_SERIAL_ERROR);
        logger.info("modelSerialError: "+modelSerialError);
        //Added this to resolve R16.0 post production issue
        if(StringUtils.isBlank(modelSerialError) ){
        	modelSerialError="false";
        }
        logger.info("modelSerialError after: "+modelSerialError);
        
		 if(null!= serviceOrder && null!=serviceOrder.getSoProviderInvoiceParts() && !serviceOrder.getSoProviderInvoiceParts().isEmpty())
	        {			 
		        
			 for(SOProviderInvoiceParts invoiceParts : serviceOrder.getSoProviderInvoiceParts()){
	        		if(null!=invoiceParts && StringUtils.isNotBlank(invoiceParts.getPartStatus()) && 
	        				StringUtils.isNotBlank(invoiceParts.getClaimStatus())){
	        			if(invoiceParts.getPartStatus().equalsIgnoreCase(OrderfulfillmentConstants.PART_STATUS_INSTALLED) && 
	        					invoiceParts.getClaimStatus().equalsIgnoreCase(OrderfulfillmentConstants.CLAIMSTATUS_APPROVED))
	        			{
	        				  returnValue = "true";
	        			}
	        			else if(invoiceParts.getPartStatus().equalsIgnoreCase(OrderfulfillmentConstants.PART_STATUS_NOT_INSTALLED) &&
	        					(invoiceParts.getClaimStatus().equalsIgnoreCase(OrderfulfillmentConstants.CLAIMSTATUS_APPROVED)||invoiceParts.getClaimStatus().equalsIgnoreCase(OrderfulfillmentConstants.CLAIMSTATUS_PENDING)))
	        			{
	        				  returnValue = "true";
	        			}
	        			else
	        			{
	        				returnValue = "false";
	        				break;
	        			}
	        		
	        	}	        	
			 }
	        }
		 
		 logger.info("returnValue"+returnValue);
		 if(returnValue.equalsIgnoreCase("true") && "false".equalsIgnoreCase(modelSerialError)){
			logger.info("Attribute pending auto close");
			 //insert method_of_closure as "Pending auto Close" into so_workflow_controls 
			 String pendingAutoClose= "PENDING AUTO CLOSE";
			//serviceOrderDao.insertSoWorkflowControl(pendingAutoClose,serviceOrder.getSoId());
			 serviceOrder.getSOWorkflowControls().setMethodOfClosure(pendingAutoClose);
			 serviceOrderDao.save(serviceOrder);
			 //Adding buyer private note			 		 
			setBuyerNote(serviceOrder,true,execution);
			 
		 }
		 else{
			 logger.info("Attribute Manual Review");
			 //insert method_of_closure as "Manual Review" into so_workflow_controls
			 String manualReview= "MANUAL REVIEW";
			// serviceOrderDao.insertSoWorkflowControl(manualReview,serviceOrder.getSoId());
			 serviceOrder.getSOWorkflowControls().setMethodOfClosure(manualReview);
			 serviceOrderDao.save(serviceOrder);
			//Adding buyer private note
			 if("false".equalsIgnoreCase(returnValue)){
				 setBuyerNote(serviceOrder,false,execution);
			 }
			 if(!"false".equalsIgnoreCase(modelSerialError)){
				//Priority 5B
				//TODO: code for note/logging
			 }
		 }
		
		//Added this to resolve R16.0 post production issue
		 execution.removeVariable(OrderfulfillmentConstants.PVKEY_MODEL_SERIAL_ERROR);
		 
		 return returnValue;
	}

	private void setBuyerNote(ServiceOrder serviceOrder, boolean value, OpenExecution execution) {
		if(value){
			Integer partsCount = 0;
			if(null!=serviceOrder.getSoProviderInvoiceParts() && serviceOrder.getSoProviderInvoiceParts().size() >0){
				 partsCount = (Integer)execution.getVariable(ProcessVariableUtil.PVKEY_PARTS_COUNT);
			}
			logger.info("Pending auto close buyer note parts count:"+partsCount);
			try{
				SONote note = new SONote();
				note.setCreatedByName(OrderfulfillmentConstants.SYSTEM);
				note.setModifiedBy(OrderfulfillmentConstants.SYSTEM);
	     		Date date=new Date(System.currentTimeMillis());
	     		Timestamp timestamp = new Timestamp(date.getTime());
	     		note.setCreatedDate(timestamp);
	     		note.setModifiedDate(timestamp);
	     		note.setPrivate(OrderfulfillmentConstants.INDICATOR_PRIVATE);
	     		note.setRoleId(OrderfulfillmentConstants.BUYER_ROLEID);
	     		note.setServiceOrder(serviceOrder);     		
	     		note.setEntityId(OrderfulfillmentConstants.INHOME_BUYER);
	     		note.setNoteTypeId(OrderfulfillmentConstants.NOTE_TYPE_GENERAL_TWO);
	     		if(partsCount != 0){
	     			if(partsCount > 1){
	     				note.setNote(OrderfulfillmentConstants.PA_NOTE1+partsCount+OrderfulfillmentConstants.PA_NOTE2);
	     			}
	     			else{
	     				note.setNote(partsCount+OrderfulfillmentConstants.PA_NOTE3);
	     			}
	     		}
	     		else{
     				note.setNote(OrderfulfillmentConstants.PA_NOTE4);
	     		}
	     		note.setSubject(OrderfulfillmentConstants.PENDING_AUTO_CLOSE);
	     		serviceOrderDao.save(note); 
				}catch(Exception e){
					logger.error("Error happened while inserting notes in Pending auto close", e);
				}				
		}else{
			Integer manualCount = (Integer)execution.getVariable(ProcessVariableUtil.PVKEY_MANUAL_COUNT);
			Integer estMaxLimitCount = (Integer)execution.getVariable(ProcessVariableUtil.PVKEY_ESTMAXLIMIT_COUNT);
			logger.info("Manual Review buyer note manual count:"+manualCount);
			logger.info("Manual Review buyer note estMaxLimitCount count:"+estMaxLimitCount);
			try{
				SONote note = new SONote();
				note.setCreatedByName(OrderfulfillmentConstants.SYSTEM);
				note.setModifiedBy(OrderfulfillmentConstants.SYSTEM);
	     		Date date=new Date(System.currentTimeMillis());
	     		Timestamp timestamp = new Timestamp(date.getTime());
	     		note.setCreatedDate(timestamp);
	     		note.setModifiedDate(timestamp);
	     		note.setPrivate(OrderfulfillmentConstants.INDICATOR_PRIVATE);
	     		note.setRoleId(OrderfulfillmentConstants.BUYER_ROLEID);
	     		note.setServiceOrder(serviceOrder);      		
	     		note.setEntityId(OrderfulfillmentConstants.INHOME_BUYER);
	     		note.setNoteTypeId(OrderfulfillmentConstants.NOTE_TYPE_GENERAL_TWO);
	     		if(estMaxLimitCount != 0){
	     			if(manualCount!=0){
	     				if(estMaxLimitCount > 1 && manualCount >1){
		     				note.setNote(estMaxLimitCount+OrderfulfillmentConstants.MR_NOTE1+" "+manualCount+" "+OrderfulfillmentConstants.MR_NOTE2);
	     				}
	     				note.setNote(estMaxLimitCount+OrderfulfillmentConstants.MR_NOTE5+" "+manualCount+" "+OrderfulfillmentConstants.MR_NOTE6);
	     			}else{
	     				if(estMaxLimitCount > 1){
	     					note.setNote(estMaxLimitCount+OrderfulfillmentConstants.MR_NOTE1);
	     				}
	     				note.setNote(estMaxLimitCount+OrderfulfillmentConstants.MR_NOTE5);
	     			}
	     		}
	     		else{
	     			if(manualCount > 1){
	     				note.setNote(manualCount+OrderfulfillmentConstants.MR_NOTE3);
	     			}
	     				note.setNote(manualCount+OrderfulfillmentConstants.MR_NOTE4);
	     		}
	     		note.setSubject(OrderfulfillmentConstants.MR_NOTE_SUBJECT);
	     		serviceOrderDao.save(note); 
				}catch(Exception e){
					logger.error("Error happened while inserting notes for Manual Review", e);
				}		
		}
		
	}
	
}
