package com.servicelive.orderfulfillment.command;

import java.util.Map;

import org.apache.commons.lang.BooleanUtils;

import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;

public class CheckAndSendAlertCmd extends AlertCmd {
    @Override
    public void execute(Map<String, Object> processVariables) {
        Object isSendEmail = processVariables.get(OrderfulfillmentConstants.PVKEY_SEND_EMAIL);
        Object isSendEmailToProvider = processVariables.get(OrderfulfillmentConstants.PVKEY_SEND_PROVIDER_EMAIL);
        
        //if there is no flag in the process variable send the email 
        //or only send email if the flag is true
        //also do not send if the assignment type is firm
        if ((isSendEmail == null || BooleanUtils.toBoolean(isSendEmail.toString()))){
        	super.execute(processVariables);
        }

        //now remove the flag since in one transition we are only calling this command once
        //so it does not stay for the other transitions
        if (isSendEmail != null)
        	processVariables.put(OrderfulfillmentConstants.PVKEY_SEND_EMAIL, null);
    }
}
