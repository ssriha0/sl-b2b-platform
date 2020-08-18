package com.newco.batch.inhomeAutoClose;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderNote;
import com.newco.marketplace.inhomeautoclose.InHomeAutoCloseProcessBO;
import com.newco.marketplace.inhomeautoclose.constants.InHomeAutoCloseProcessConstants;
import com.newco.marketplace.inhomeautoclose.vo.InHomeAutoCloseProcessVO;
import com.servicelive.orderfulfillment.client.OFHelper;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification.EntityType;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.Parameter;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;


public class InHomeAutoCloseProcess {
			
		private InHomeAutoCloseProcessBO inhomeautoCloseProcessBO;
		private OFHelper ofHelper;
		private static final Logger logger = Logger.getLogger(InHomeAutoCloseProcess.class.getName());

		public void process() throws Exception{
			logger.info("InHomeAutoCloseProcess :: Inside process");
			
			//Fetch the service order Information of the HSR buyer whose status is Completed and the sub-status is Pending Auto Close 
			//having records with RETRY or WAITING status in so_inhome_auto_close_table.
			List<InHomeAutoCloseProcessVO> serviceOrderList= new ArrayList<InHomeAutoCloseProcessVO>();
			serviceOrderList=inhomeautoCloseProcessBO.getListOfServiceOrdersToAutoClose();
			List<Integer> autoCloseIds=new ArrayList<Integer>();
			if(null!=serviceOrderList && serviceOrderList.size()>0){
				for(InHomeAutoCloseProcessVO inHomeAutoCloseProcess: serviceOrderList){
					autoCloseIds.add(inHomeAutoCloseProcess.getAutoCloseId());
				}
			}			
			//update the entries as 'IN PROGRESS'
			inhomeautoCloseProcessBO.updateAsInprogress(autoCloseIds); 
			//Fetch the value of the number of retries from the application_properties.
			HashMap<String, String> autoCloseConstantsMap= new HashMap<String, String>();
			autoCloseConstantsMap=inhomeautoCloseProcessBO.getAutoCloseConstants();		
			logger.info("autoCloseConstantsMap"+ autoCloseConstantsMap);
			Integer noOfRetriesAllowed=0;
			if(null!=autoCloseConstantsMap && null!=autoCloseConstantsMap.get("inhome_autoclose_no_of_retries")){
				noOfRetriesAllowed=Integer.parseInt(autoCloseConstantsMap.get("inhome_autoclose_no_of_retries"));
			}
			
			//Iterate through the service order List and process the service orders 				
			if(null!=serviceOrderList && serviceOrderList.size()>0){
				for(InHomeAutoCloseProcessVO inHomeAutoCloseProcess: serviceOrderList){
					try{
								
			Integer noOfRetries=inHomeAutoCloseProcess.getNoOfRetries();
			InHomeAutoCloseProcessVO inHomeAutoCloseProcessVo=new InHomeAutoCloseProcessVO();
			inHomeAutoCloseProcessVo.setSoId(inHomeAutoCloseProcess.getSoId());
	        if(null!=inHomeAutoCloseProcess.getNoOfRetries()){
	        	// increment the number of retries by 1.
	        	noOfRetries=noOfRetries+1;
	        	inHomeAutoCloseProcessVo.setNoOfRetries(noOfRetries);
	        }
	        	// check whether any of the financial transaction is pending        
	        if(inhomeautoCloseProcessBO.isPendingFinanceTransaction(inHomeAutoCloseProcess.getSoId())){
	        	if(noOfRetries< noOfRetriesAllowed){
	        		// If the number of retries has not reached the limit then set the status as 'RETRY'.
	        		inHomeAutoCloseProcessVo.setStatus(InHomeAutoCloseProcessConstants.RETRY);
	        	}else{
	        		// If the number of retries have reached the limit then set the status as 'FAILURE'.
	        		inHomeAutoCloseProcessVo.setStatus(InHomeAutoCloseProcessConstants.FAILURE);
 	        		insertNote(inHomeAutoCloseProcess.getSoId());
	        	}
	        }
	        else{
	        	// set the status as 'SUCESS' which will be autoclosed .
        		inHomeAutoCloseProcessVo.setStatus(InHomeAutoCloseProcessConstants.SUCCESS);
        		ServiceOrder serviceOrder=ofHelper.getServiceOrder(inHomeAutoCloseProcess.getSoId());	
               // set the signal authorization for the buyer and initiate close order signal 
        		Identification id = new Identification();
        		id.setEntityType(EntityType.BUYER);
        		id.setCompanyId(InHomeAutoCloseProcessConstants.INHOME_BUYER);
        		id.setResourceId(InHomeAutoCloseProcessConstants.SYSTEM_ENTITY_ID);
        		id.setUsername(InHomeAutoCloseProcessConstants.SYSTEM);
        		id.setFullname(InHomeAutoCloseProcessConstants.SYSTEM);
        		id.setRoleId(InHomeAutoCloseProcessConstants.BUYER_ROLE);
                List<Parameter> parameters = new ArrayList<Parameter>();
                // set the parameter  'hsrautoclose' as 'true'
                parameters.add(new Parameter(InHomeAutoCloseProcessConstants.HSR_AUTOCLOSE,InHomeAutoCloseProcessConstants.TRUE));
        		OrderFulfillmentResponse ordrFlflResponse = ofHelper.runOrderFulfillmentProcess(
        				inHomeAutoCloseProcess.getSoId(),
        				SignalType.HSR_AUTO_CLOSE_ORDER,
        				serviceOrder,
        				id,parameters); 
        		// If the order is autoclose then set the status as 'retry'  
        		 if (ordrFlflResponse.isError()) {
 	        		inHomeAutoCloseProcessVo.setStatus(InHomeAutoCloseProcessConstants.RETRY);	        		
       		
        		 }else{
        			 //set the so substatus as autoclosed in so_inhome_autolose table
  	        		inHomeAutoCloseProcessVo.setSoSubstatus(InHomeAutoCloseProcessConstants.AUTOCLOSE);	        		
        		 }
        		
	        }	
	        // update the number of retries and status in so_inhome_autoclose table
			inhomeautoCloseProcessBO.updateAutoCloseInfo(inHomeAutoCloseProcessVo);
					
					}catch(Exception e){
						logger.info("error in processing the autoclose of inhome order"+e);
					}
								
				}
			}
		}

		public InHomeAutoCloseProcessBO getInhomeautoCloseProcessBO() {
			return inhomeautoCloseProcessBO;
		}

		public void setInhomeautoCloseProcessBO(
				InHomeAutoCloseProcessBO inhomeautoCloseProcessBO) {
			this.inhomeautoCloseProcessBO = inhomeautoCloseProcessBO;
		}

		public OFHelper getOfHelper() {
			return ofHelper;
		}

		public void setOfHelper(OFHelper ofHelper) {
			this.ofHelper = ofHelper;
		}
		
		public void insertNote(String soId){
			try{
			ServiceOrderNote sonote=new ServiceOrderNote();
     		//sonote.setBuyerId(InHomeAutoCloseProcessConstants.INHOME_BUYER);
     		sonote.setCreatedByName(InHomeAutoCloseProcessConstants.SYSTEM);
     		sonote.setModifiedBy(InHomeAutoCloseProcessConstants.SYSTEM);
     		Date date=new Date(System.currentTimeMillis());
     		Timestamp timestamp = new Timestamp(date.getTime());
     		sonote.setCreatedDate(timestamp);
     		sonote.setModifiedDate(timestamp);
     		sonote.setPrivateId(InHomeAutoCloseProcessConstants.INDICATOR_TRUE);
     		sonote.setRoleId(InHomeAutoCloseProcessConstants.BUYER_ROLE);
     		sonote.setSoId(soId);	        		
     		sonote.setEntityId(InHomeAutoCloseProcessConstants.INHOME_BUYER_ID);
     		sonote.setNoteTypeId(InHomeAutoCloseProcessConstants.NOTE_TYPE_GENERAL_TWO);
     		sonote.setNote(InHomeAutoCloseProcessConstants.NOTE);
     		sonote.setSubject(InHomeAutoCloseProcessConstants.NOTE_SUBJECT);
     		inhomeautoCloseProcessBO.insertAddNote(sonote); 
			}catch(Exception e){
				logger.info(" Exception in adding note "+e);
			}
		}
	



}
