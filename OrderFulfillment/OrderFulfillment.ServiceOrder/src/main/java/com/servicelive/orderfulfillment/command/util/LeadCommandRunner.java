package com.servicelive.orderfulfillment.command.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.servicelive.orderfulfillment.command.ILeadCommand;

public class LeadCommandRunner 
{
	
	// LeadCommandRunner is a Spring managed singleton
	// we need access to it from jBPM to get session
	// defining a static list and providing a static access method
	// to accomplish it
	static private List<LeadCommandRunner> runners =  new ArrayList<LeadCommandRunner>();
	public LeadCommandRunner(){
		runners.add(this);
	}
	static public LeadCommandRunner getRunnerInstance(){
		return runners.get(0);
	}
	
	protected final Logger logger = Logger.getLogger(getClass());
	private LeadCommandResolver leadCommandResolver;

	public void runCommands(String[] commandNames, Map<String,Object> processVariables) {
		logger.debug("entering. commands="+commandNames.toString()+ " variables="+processVariables.toString());
		
		for (String cmd : commandNames)	{
			logger.info("ready to resolve and run "+cmd);

			String[] cmd_arg = cmd.split("[(,)]");
			String commandName = cmd_arg[0].trim();
			
			// let's add command arguments to the list of variables
			// these arguments will be only available to the current command
			for (int i=1; i<cmd_arg.length; i++){
				processVariables.put(SOCommandArgHelper.resolveArgName(i), cmd_arg[i].trim());
			}

			runCommand(commandName, processVariables);

			// removing command arguments
			for (int i=1; i<cmd_arg.length; i++){
				processVariables.remove(SOCommandArgHelper.resolveArgName(i));
			}
		}
	}
	
	public void runCommand(String commandName, Map<String,Object> processVariables) {
		ILeadCommand leadCommand = leadCommandResolver.resolve(commandName, processVariables);
		
		if (leadCommand!=null) {
			leadCommand.execute(processVariables);
		} else {
			logger.warn("Unable to resolve command " + commandName);
		}
	}
	public void setLeadCommandResolver(LeadCommandResolver leadCommandResolver) {
		this.leadCommandResolver = leadCommandResolver;
	}

	
}

