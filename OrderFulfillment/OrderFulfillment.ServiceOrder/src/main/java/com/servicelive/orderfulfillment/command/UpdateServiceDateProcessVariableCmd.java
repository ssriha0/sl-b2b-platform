package com.servicelive.orderfulfillment.command;

import java.util.Calendar;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

/**
 * User: Mustafa Motiwala
 * Date: Apr 29, 2010
 * Time: 2:47:32 PM
 */
public class UpdateServiceDateProcessVariableCmd extends SOCommand{
    private static Log log = LogFactory.getLog(UpdateServiceDateProcessVariableCmd.class);
    @Override
    public void execute(Map<String, Object> processVariables) {
        log.debug("Creating ServiceDate variable.");
        ServiceOrder so = getServiceOrder(processVariables);
        Calendar serviceDate = Calendar.getInstance();
        if(so.getServiceStartDateTimeCalendar()!=null){
	        serviceDate.setTimeInMillis(so.getServiceStartDateTimeCalendar().getTimeInMillis());
	        String serviceDateAsString = ProcessVariableUtil.convertToJPDLDueDate(serviceDate);
	        ProcessVariableUtil.addServiceDate(processVariables, serviceDateAsString);
        }
        try{
        if(so.getServiceEndDateTimeCalendar() != null){
            serviceDate.setTimeInMillis(so.getServiceEndDateTimeCalendar().getTimeInMillis());
            String serviceDateAsString = ProcessVariableUtil.convertToJPDLDueDate(serviceDate);
            ProcessVariableUtil.addServiceExpireDate(processVariables, serviceDateAsString);            
    }
        }catch(Exception e){
        	log.info("Exception in updating service end Time"+e);
        }
    }
}
