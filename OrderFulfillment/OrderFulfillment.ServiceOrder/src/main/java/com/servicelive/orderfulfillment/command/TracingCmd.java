package com.servicelive.orderfulfillment.command;

import com.servicelive.orderfulfillment.common.ProcessVariableUtil;

import java.util.Map;
import java.util.Set;

public class TracingCmd extends SOCommand {
	
	public void execute(Map<String,Object> processVariables) {
		logger.debug("*** jBPM Tracing Command ***");
        Set<Map.Entry<String,Object>> entrySet = processVariables.entrySet();
        for(Map.Entry<String,Object> entry:entrySet){
            if(entry.getKey().equals("entityName")){
                String line = String.format("-- %1$s=%2$s for order %3$s", entry.getKey(), entry.getValue(), ProcessVariableUtil.extractServiceOrderId(processVariables));
                logger.info(line);
            }else {
            String line = String.format("-- %1$s=%2$s", entry.getKey(), entry.getValue());
                logger.debug(line);                
        }
        }
		logger.debug("----------------------------");
	}

}
