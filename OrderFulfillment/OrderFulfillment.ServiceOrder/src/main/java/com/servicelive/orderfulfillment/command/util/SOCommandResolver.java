package com.servicelive.orderfulfillment.command.util;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.servicelive.orderfulfillment.command.ISOCommand;
import com.servicelive.orderfulfillment.common.IWalletGateway;
import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.dao.IServiceOrderDao;
import com.servicelive.orderfulfillment.dao.IServiceOrderProcessDao;
	
//
// SOCommandResolver provides a level of indirection between jBPM commands and Spring beans
// Simplest strategy assumes that command implementation is defined as Spring bean with the same command name
// We may need more complex strategies to introduce different implementations based on buyer or order properties
//
public class SOCommandResolver implements ApplicationContextAware {
	protected final Logger logger = Logger.getLogger(getClass());
	
	ApplicationContext context;
	private IServiceOrderDao serviceOrderDao;
	private IServiceOrderProcessDao serviceOrderProcessDao;
	private IWalletGateway walletGateway;

    public ISOCommand resolve(String commandName, Map<String,Object> processVariables) {
		
		if (commandName == null || commandName.length() == 0) return null;

		// first let's determine if we have order name in the variables
		if (!processVariables.containsKey("serviceOrderType")) {
			throw new ServiceOrderException("No service order info received in the process variables");
		}
		String soType = (String)processVariables.get("serviceOrderType");
		if (soType == null || soType.length() == 0) {
			throw new ServiceOrderException("No service order type defined for: " + soType);
		}
		if (commandName.indexOf("_") > 0) { // Is a type specific command being invoked?
			if (commandName.startsWith(soType)) {  // Does current SO type match command type?
                //First check if the specific command is present than use it
                //otherwise use the generic command but this command is only going to be called if the
                //soType matches otherwise ignored
				if (context.containsBean(commandName)) { // lookup specific name only
					return createCommand(commandName);
				}
                String gen_commandName = commandName.split("_")[1];
                if (context.containsBean(gen_commandName)){  // Found a generic command
				    return createCommand(gen_commandName);
			    }
				throw new ServiceOrderException("Specific Command: " + commandName + " does not exist for type: " + soType);
			}
			return null; // if current SO type is different - just ignore the command
		} else { // if a generic name being invoked then let's try to find specific command first
			String type_commandName = soType + "_" + commandName;
			if (context.containsBean(type_commandName)) {  // Found a specific command for the order type
				return createCommand(type_commandName);
			}
			if (context.containsBean(commandName)){  // Found a generic command
				return createCommand(commandName);
			}
			throw new ServiceOrderException("Generic Command: " + commandName + " does not exist for type: " + soType);
		}
	}
	private ISOCommand createCommand(String commandName) {
		ISOCommand command = (ISOCommand)context.getBean(commandName);
		command.setServiceOrderDao(serviceOrderDao);
		command.setServiceOrderProcessDao(serviceOrderProcessDao);
		command.setGateWay(walletGateway);
		return command;
	}
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context=applicationContext;
	}
	public void setServiceOrderDao(IServiceOrderDao soDao) {
		serviceOrderDao=soDao;
	}
	public void setServiceOrderProcessDao(IServiceOrderProcessDao serviceOrderProcessDao) {
		this.serviceOrderProcessDao = serviceOrderProcessDao;
	}
	public void setGateWay(IWalletGateway gateWay) {
		this.walletGateway = gateWay;
	}
}
