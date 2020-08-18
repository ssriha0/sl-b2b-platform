package com.servicelive.orderfulfillment.command;


import com.servicelive.orderfulfillment.domain.SONote;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class SOCompletionNoteCmd extends SOCommand {
	
	public void execute(Map<String,Object> processVariables) { 
		logger.info("Starting SOCompletionNoteCmd.execute");
		ServiceOrder serviceOrder = getServiceOrder(processVariables);
		if(StringUtils.isNotBlank(serviceOrder.getResolutionDs())){
		SONote note = new SONote();
 		Object createdBy = processVariables.get(OrderfulfillmentConstants.PVKEY_CREATED_BY);
        if(null!=createdBy){
		note.setCreatedByName(createdBy.toString());
        }
 		Object modifiedBy = processVariables.get(OrderfulfillmentConstants.PVKEY_MODIFIED_BY);
        if(null!=modifiedBy){
		note.setModifiedBy(modifiedBy.toString());
        }
 		Date date=new Date(System.currentTimeMillis());
 		Timestamp timestamp = new Timestamp(date.getTime());
 		note.setCreatedDate(timestamp);
 		note.setModifiedDate(timestamp);
 		note.setPrivate(false); 
 		Object roleId = processVariables.get(OrderfulfillmentConstants.PVKEY_ROLE_ID);
		if(roleId!=null)
		{
 		note.setRoleId(new Integer(Integer.parseInt(roleId.toString())));
		}
 		note.setServiceOrder(serviceOrder);     	
 		Object entityId = processVariables.get(OrderfulfillmentConstants.PVKEY_RESOURCE_ID);
		if(entityId!=null)
		{
 		note.setEntityId(new Long(Integer.parseInt(entityId.toString())));  
		}
 		note.setNoteTypeId(new Integer(OrderfulfillmentConstants.NOTE_TYPE_GENERAL_TWO));
 		note.setSubject(OrderfulfillmentConstants.PVKEY_RES_COMMENTS);
		note.setNote(serviceOrder.getResolutionDs());
		note.setReadInd(OrderfulfillmentConstants.READ_IND_ZER0);
 		serviceOrderDao.save(note); 
 		
	}
		}
}

