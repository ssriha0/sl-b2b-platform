package com.servicelive.orderfulfillment.command;

import java.util.Map;

import com.servicelive.orderfulfillment.command.util.SOCommandArgHelper;

public class RemoveFieldsCmd extends SOCommand {

	@Override
	public void execute(Map<String, Object> processVariables) {
		int i = 1;
		while(null != processVariables.get(SOCommandArgHelper.resolveArgName(i))){
            String key = (String) processVariables.get(SOCommandArgHelper.resolveArgName(i));
            if(processVariables.containsKey(key))
			    processVariables.put(key, null);
			i++;
		}
	}

}
