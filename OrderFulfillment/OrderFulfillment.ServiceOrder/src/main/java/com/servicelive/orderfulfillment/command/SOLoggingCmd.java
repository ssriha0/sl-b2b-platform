package com.servicelive.orderfulfillment.command;

import com.servicelive.orderfulfillment.command.util.SOCommandArgHelper;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.common.SOLoggingCmdHelper;
import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.jbpm.TransientVariable;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class SOLoggingCmd extends SOCommand {
    SOLoggingCmdHelper soLoggingCmdHelper;

	@Override
	public void execute(Map<String, Object> processVariables) 
	{
		// logging object name is assumed to be passed in the first command argument
		String loggingCfgMapKey = SOCommandArgHelper.extractStringArg(processVariables, 1);
		String soId = getSoId(processVariables);
		logger.info("Getting the object name"+ loggingCfgMapKey);
        if (!soLoggingCmdHelper.soLoggingObjectExists(loggingCfgMapKey)) {
            logger.error("IServiceOrderLogging object could not been resolved for key - "+ loggingCfgMapKey);
            return;
        }
        logger.debug("SOLoggingCmd is logging service order activity for logging config " + loggingCfgMapKey);
        /** This will check already a logging done for Draft Order Creation while executing 
            logging for No Matching CAR Rule or Recall provider Not available*/
        if(StringUtils.equals(loggingCfgMapKey, "createWarrantyDraftSOLogging")){
        	try{
        	    boolean draftLogged = serviceOrderDao.checkDraftLogExists(soId);
	        	/** Remove the existing log for Draft Order Creation if exists prior to Draft logging for 
	        	    No Matching CAR Rule or Recall provider Not available*/
	        	if(draftLogged){
	        		serviceOrderDao.deleteDraftLogging(soId);
	              }
        	}catch (Exception e) {
				logger.error("Exception in validationg Normal Draft Logging and Deletion"+ e);
				throw new ServiceOrderException(e);
			}
        	
        }
        soLoggingCmdHelper.logServiceOrderActivity(this.getServiceOrder(processVariables), loggingCfgMapKey, createSOLogDataMap(processVariables));
	}

    protected HashMap<String,Object> createSOLogDataMap(Map<String, Object> processVariables) {
        HashMap<String,Object> soLogDataMap = new HashMap<String,Object>();
        //Convert all process Variables to values for our data-map.
        for(Map.Entry<String,Object> entry : processVariables.entrySet()){
            Object processVariableValue = entry.getValue();
            logger.info("Value of different object"+processVariableValue+":Key For map:"+entry.getKey());
            if(processVariableValue instanceof TransientVariable){
                processVariableValue = ((TransientVariable) processVariableValue).getObject();
            }
            soLogDataMap.put(entry.getKey(), processVariableValue);
        }
        logger.info("A createSOLogDataMap");
        addValuesToDataMap(soLogDataMap, processVariables);
        logger.info("B createSOLogDataMap");
        return soLogDataMap;
    }

    protected void addValuesToDataMap(HashMap<String,Object> soLogDataMap, Map<String, Object> processVariables) {
        soLogDataMap.put("SO_ID", getSoId(processVariables));
        //Non w2 priority 2 :Added for logging Original So Id
        soLogDataMap.put("ORIGINAL_SO_ID",processVariables.get(ProcessVariableUtil.ORIGINAL_SO_ID));
        //SL-10863.adding previous and current status of SO for pos cancellation
        //SL 15642 Making changes to have the firm id and firm name for so logging cmd
        String loggingCfgMapKey = SOCommandArgHelper.extractStringArg(processVariables, 1);
        if(loggingCfgMapKey.equalsIgnoreCase("autoAcceptUpdateLogging")){
        	logger.info("size of process variable"+processVariables.size());
        	logger.info("before caliing the so commane helper for firm id");
        	String firmId=SOCommandArgHelper.extractStringArg(processVariables, 2);
        	Object firmIdTesting=processVariables.get("AUTO_ACCEPT_LOGGING_FIRM_NAME");
        	if(null!=firmIdTesting){
        	String firmIdString=firmIdTesting.toString();
        	logger.info("firmIdString:-->"+firmIdString);
        	}
        	logger.info("Firm id:"+firmId);
        	String firmName=SOCommandArgHelper.extractStringArg(processVariables, 3);
        	logger.info("Firm Name:"+firmName);
     	    logger.info("Putting of info successfull for SL 15642");
        }else{
	        String oldStatus = SOCommandArgHelper.extractStringArg(processVariables, 2);
	        logger.info("Old status"+oldStatus);
			String newStatus = SOCommandArgHelper.extractStringArg(processVariables, 3);
			logger.info("New status"+newStatus);
			if(null != oldStatus){
				soLogDataMap.put(OrderfulfillmentConstants.OLD_STATUS, oldStatus);
			}
			if(null != newStatus){
				soLogDataMap.put(OrderfulfillmentConstants.NEW_STATUS, newStatus);
			}
			logger.info("End of adding value");
        }
	      
		       
    }

    public void setSoLoggingCmdHelper(SOLoggingCmdHelper soLoggingCmdHelper) {
        this.soLoggingCmdHelper = soLoggingCmdHelper;
    }
}
