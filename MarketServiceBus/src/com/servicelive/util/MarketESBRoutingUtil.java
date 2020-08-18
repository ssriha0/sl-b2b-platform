package com.servicelive.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import org.apache.commons.lang.BooleanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;

import com.newco.marketplace.translator.business.IApplicationPropertiesService;
import com.newco.marketplace.translator.util.SpringUtil;

public class MarketESBRoutingUtil {
	private static String appLifeCycleIndicator = System.getProperty("sl_app_lifecycle");
	private static Logger logger = Logger.getLogger(MarketESBRoutingUtil.class);
	private static ResourceBundle resourceBundle = ResourceBundle.getBundle("servicelive_esb_" + appLifeCycleIndicator);
	
	private final String OF_ENABLED_PROPERTY="orderfulfillment.enabled";
	private final String OMS_ENABLED_NEW_ORDERS_PROPERTY="orderfulfillment.oms.neworders.enabled";
	private final String ASSURANT_ENABLED_NEW_ORDERS_PROPERTY="orderfulfillment.assurant.neworders.enabled";
	private final String HSR_ENABLED_NEW_ORDERS_PROPERTY="orderfulfillment.hsr.neworders.enabled";
	
	public boolean isOrderFulfillmentEnabled(){
        Boolean enabled = BooleanUtils.toBoolean(System.getProperty(OF_ENABLED_PROPERTY));
        if (enabled == null) {
        	return false;
        } else {
        	return enabled;
        }
    }
	
	public boolean isOrderFulfillmentAllowedForNewOrders() {
		try {
		Boolean allowNewOFProcessForNewOrders = ((IApplicationPropertiesService)SpringUtil.factory.getBean("ApplicationPropertiesService")).getUseNewOFProcess();
			if (allowNewOFProcessForNewOrders == null) {
				return false;
			} else {
				return allowNewOFProcessForNewOrders;
			}
		}
		catch (BeansException e) {
			logger.info("Ignoring error encountered while trying to retrieve 'use_new_orderfulfillment_process' application property, and assuming the property to be not set.", e);
			return false;
		}
		catch (IncorrectResultSizeDataAccessException e) {
			logger.info("Ignoring error encountered while trying to retrieve 'use_new_orderfulfillment_process' application property, and assuming the property to be not set. The error was: " + e.getMessage());
			return false;
		}
		catch (DataAccessException e) {
			logger.info("Ignoring error encountered while trying to retrieve 'use_new_orderfulfillment_process' application property, and assuming the property to be not set.", e);
			return false;
		}
	}
	
	public boolean isOrderFulfillmentAllowedForNewOMSOrders() {
		Boolean enabled = BooleanUtils.toBoolean(System.getProperty(OMS_ENABLED_NEW_ORDERS_PROPERTY));
        if (enabled == null) {
        	return false;
        } else {
        	return enabled;
        }
	}
	
	public boolean isOrderFulfillmentAllowedForNewAssurantOrders() {
		Boolean enabled = BooleanUtils.toBoolean(System.getProperty(ASSURANT_ENABLED_NEW_ORDERS_PROPERTY));
        if (enabled == null) {
        	return false;
        } else {
        	return enabled;
        }
	}
	
	public boolean isOrderFulfillmentAllowedForNewHSROrders() {
		Boolean enabled = BooleanUtils.toBoolean(System.getProperty(HSR_ENABLED_NEW_ORDERS_PROPERTY));
        if (enabled == null) {
        	return false;
        } else {
        	return enabled;
        }
	}
	
	public boolean isMaintainLegacyStagingData() {
		try {
			Boolean maintainLegacyStagingData = ((IApplicationPropertiesService)SpringUtil.factory.getBean("ApplicationPropertiesService")).getMaintainLegacyStagingData();
			if (maintainLegacyStagingData == null) {
				return false;
			} else {
				return maintainLegacyStagingData;
			}
		}
		catch (BeansException e) {
			logger.info("Ignoring error encountered while trying to retrieve 'maintain_legacy_staging_data' application property, and assuming the property to be not set.", e);
			return false;
		}
		catch (IncorrectResultSizeDataAccessException e) {
			logger.info("Ignoring error encountered while trying to retrieve 'maintain_legacy_staging_data' application property, and assuming the property to be not set. The error was: " + e.getMessage());
			return false;
		}
		catch (DataAccessException e) {
			logger.info("Ignoring error encountered while trying to retrieve 'maintain_legacy_staging_data' application property, and assuming the property to be not set.", e);
			return false;
		}
	}
	
	public boolean isTestMode() {
		try {
			Boolean allowNewOFProcessForNewOrders = ((IApplicationPropertiesService)SpringUtil.factory.getBean("ApplicationPropertiesService")).getOFTestMode();
			if (allowNewOFProcessForNewOrders == null) {
				return false;
			} else {
				return allowNewOFProcessForNewOrders;
			}
		}
		catch (BeansException e) {
			logger.info("Ignoring error encountered while trying to retrieve 'orderfulfillment_test_mode' application property, and assuming the property to be not set.", e);
			return false;
		}
		catch (IncorrectResultSizeDataAccessException e) {
			logger.info("Ignoring error encountered while trying to retrieve 'orderfulfillment_test_mode' application property, and assuming the property to be not set. The error was: " + e.getMessage());
			return false;
		}
		catch (DataAccessException e) {
			logger.info("Ignoring error encountered while trying to retrieve 'orderfulfillment_test_mode' application property, and assuming the property to be not set.", e);
			return false;
		}
	}
	
	public RolloutUnits getRolloutUnits() {
		String rolloutUnitsString = "";
		try {
			rolloutUnitsString = ((IApplicationPropertiesService)SpringUtil.factory.getBean("ApplicationPropertiesService")).getRolloutUnits();
		}
		catch (BeansException e) {
			logger.info("Ignoring error encountered while trying to retrieve 'orderfulfillment_rollout_stores' application property, and assuming the property to be not set.", e);
		}
		catch (IncorrectResultSizeDataAccessException e) {
			logger.info("Ignoring error encountered while trying to retrieve 'orderfulfillment_rollout_stores' application property, and assuming the property to be not set. The error was: " + e.getMessage());
		}
		catch (DataAccessException e) {
			logger.info("Ignoring error encountered while trying to retrieve 'orderfulfillment_rollout_stores' application property, and assuming the property to be not set.", e);
		}
		HashMap<String, Boolean> rolloutUnitsMap = null;
		List<RolloutUnitRange> rolloutUnitRanges = null;
		boolean rolloutAll = false;
		
		if (rolloutUnitsString.equalsIgnoreCase("all")) {
			rolloutAll = true;
		} else {
			String[] rolloutUnitArray = rolloutUnitsString.split(",");
			rolloutUnitsMap = new HashMap<String, Boolean>();
			rolloutUnitRanges = new ArrayList<RolloutUnitRange>();
			for (int i = 0; i < rolloutUnitArray.length; i++) {
				String rolloutEntry = rolloutUnitArray[i];
				int dashIndex = rolloutEntry.indexOf("-");
				if (dashIndex > -1) {
					String lowerBound = removeLeading0sFromUnitNumber(rolloutEntry.substring(0, dashIndex).trim());
					String upperBound = removeLeading0sFromUnitNumber(rolloutEntry.substring(dashIndex + 1).trim());
					try {
						RolloutUnitRange range = new RolloutUnitRange(lowerBound, upperBound);
						rolloutUnitRanges.add(range);						
					} catch (Exception e) {
						logger.error("Error while trying to parse " + rolloutEntry + " as a range of rollout units. This entry will be ignored.", e);
					}
					
				} else {
					rolloutUnitsMap.put(removeLeading0sFromUnitNumber(rolloutEntry.trim()), Boolean.TRUE);
				}
			}
		}
		
		RolloutUnits rolloutUnits = new RolloutUnits();
		rolloutUnits.setAllUnits(rolloutAll);
		rolloutUnits.setRolloutUnitsMap(rolloutUnitsMap);
		rolloutUnits.setRolloutUnitRanges(rolloutUnitRanges);
		return rolloutUnits;
	}
	
	public String removeLeading0sFromUnitNumber(String rawUnit) {
		try {
			Long unitLong = Long.parseLong(rawUnit);
			return unitLong.toString();
		} catch (NumberFormatException nfe) {
			logger.error("IGNORING NumberFormatException while trying to parse unit number "+rawUnit+" to a Long.");
			return rawUnit;
		}
	}
	
	public void writeFileForNewService(String client, String fileContents, String fileName) throws Exception {
		final String methodName = "writeFileForNewService";
		logger.info(String.format("entering %s", methodName));
		
		String newProcessInputDir = resourceBundle.getString(client + "_NEW_INPUT_PATH");
		writeFileToNewLocation(fileContents, newProcessInputDir, fileName);
		logger.info(String.format("exiting %s", methodName));
	}
	
	public void writeFileToNewLocation(String fileContents, String inputDir, String fileName) throws Exception {
		final String methodName = "writeFileToNewLocation";
		logger.info(String.format("entering %s", methodName));
		
		
		String rightMost = "";
		rightMost = inputDir.substring(inputDir.length() - 1);
		if (rightMost != "/" && rightMost != "\\") {
			inputDir = String.format("%s%s", inputDir, "/");
		}
		
		if (inputDir != null) {
	    	FileWriter out = null;
			try {
				File file = new File(inputDir + fileName);
				out = new FileWriter(file);
				out.write(fileContents);
			} catch (IOException ioe) {
				logger.info(String.format("exiting %s due to exception %s", methodName, ioe.getMessage()));
				throw new Exception ("Problem generating the input file.", ioe);
			} finally {
				if (out != null) {
					try {
						out.close();
					} catch (IOException ioe) {
						logger.info(String.format("exiting %s due to exception %s", methodName, ioe.getMessage()));
						throw new Exception ("Error closing outputstream when generating the input file.", ioe);
					}
				}
			}
		}
		
		logger.info(String.format("exiting %s", methodName));
	}

	public String generateRandomTestSuffix() {
		return generateRandomTestSuffix(5);
	}
	
	public String generateRandomTestSuffix(int numberOfCharacters) {
		char[] symbols = new char[36];
	    for (int idx = 0; idx < 10; ++idx)
	      symbols[idx] = (char) ('0' + idx);
	    for (int idx = 10; idx < 36; ++idx)
	      symbols[idx] = (char) ('a' + idx - 10);

		Random random = new Random();
		
		char[] buf = new char[numberOfCharacters];
		for (int idx = 0; idx < buf.length; ++idx) { 
			buf[idx] = symbols[random.nextInt(symbols.length)];
		}
		return new String(buf);
	}
}
