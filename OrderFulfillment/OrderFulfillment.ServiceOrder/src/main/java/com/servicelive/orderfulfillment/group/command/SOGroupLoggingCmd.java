package com.servicelive.orderfulfillment.group.command;

import com.servicelive.orderfulfillment.command.util.SOCommandArgHelper;
import com.servicelive.orderfulfillment.common.SOLoggingCmdHelper;
import com.servicelive.orderfulfillment.domain.SOGroup;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.jbpm.TransientVariable;

import java.util.HashMap;
import java.util.Map;

public class SOGroupLoggingCmd extends SOGroupCmd {
    SOLoggingCmdHelper soLoggingCmdHelper;

    @Override
    protected void handleGroup(SOGroup soGroup, Map<String, Object> processVariables) {
        if (soGroup == null || soGroup.getServiceOrders() == null) {
            return;
        }
        for (ServiceOrder serviceOrder : soGroup.getServiceOrders()) {
            logServiceOrderActivity(serviceOrder, processVariables);
        }
    }

    @Override
    protected void handleServiceOrder(ServiceOrder so, Map<String, Object> processVariables) {
        logServiceOrderActivity(so, processVariables);
    }

    protected void logServiceOrderActivity(ServiceOrder so, Map<String, Object> processVariables) {
        // logging object name is assumed to be passed in the first command argument
        String loggingCfgMapKey = SOCommandArgHelper.extractStringArg(processVariables, 1);
        if (!soLoggingCmdHelper.soLoggingObjectExists(loggingCfgMapKey)) {
            logger.error("IServiceOrderLogging object could not been resolved for key - "+ loggingCfgMapKey);
            return;
        }

        logger.debug("SOGroupLoggingCmd is logging service order activity for logging config " + loggingCfgMapKey);
        soLoggingCmdHelper.logServiceOrderActivity(so, loggingCfgMapKey, createSOLogDataMap(so, processVariables));
    }

    protected HashMap<String,Object> createSOLogDataMap(ServiceOrder so, Map<String, Object> processVariables) {
        HashMap<String,Object> soLogDataMap = new HashMap<String,Object>();
        //Convert all process Variables to values for our data-map.
        for(Map.Entry<String,Object> entry : processVariables.entrySet()){
            Object processVariableValue = entry.getValue();
            if(processVariableValue instanceof TransientVariable){
                processVariableValue = ((TransientVariable) processVariableValue).getObject();
            }
            soLogDataMap.put(entry.getKey(), processVariableValue);
        }

        addValuesToDataMap(so, soLogDataMap, processVariables);
        return soLogDataMap;
    }

    protected void addValuesToDataMap(ServiceOrder so, HashMap<String,Object> soLogDataMap, Map<String, Object> processVariables) {
        soLogDataMap.put("SO_ID", so.getSoId());
    }

    public void setSoLoggingCmdHelper(SOLoggingCmdHelper soLoggingCmdHelper) {
        this.soLoggingCmdHelper = soLoggingCmdHelper;
    }
}
