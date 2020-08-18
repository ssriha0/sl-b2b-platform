package com.servicelive.esb.actions;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Body;
import org.jboss.soa.esb.message.Message;

import com.newco.marketplace.translator.business.IHSRStagingService;
import com.newco.marketplace.translator.util.SpringUtil;
import com.servicelive.esb.constant.HSRFieldNameConstants;
import com.servicelive.esb.constant.MarketESBConstant;
import com.servicelive.esb.dto.HSRServiceOrder;
import com.servicelive.esb.dto.HSRServiceOrders;
import com.servicelive.esb.integration.bo.IIntegrationBO;
import com.servicelive.esb.mapper.HSRNewFileDataMapper;
import com.servicelive.esb.mapper.HSRUpdateFileDataMapper;
import com.servicelive.esb.mapper.Mapper;
import com.servicelive.esb.service.ExceptionHandler;
import com.servicelive.staging.domain.hsr.HSRStageOrder;
import com.servicelive.staging.domain.hsr.HSRStageOrders;
import com.servicelive.util.MarketESBRoutingUtil;
import com.servicelive.util.MarketESBUtil;


public class HSRServiceOrdersUnMarshallerAction extends AbstractEsbSpringAction {
	
	private static final int LEGACY_OR_OF_INDICATOR_SUFFIX_LENGTH = 1;
	private static final int RANDOM_TEST_SUFFIX_LENGTH = 3;
	private static final int ORDER_NUMBER_LENGTH = 8;
	private static final String FIELD_DELIMITER_REGEX = "\\|";
	private static final String FIELD_DELIMITER_STRING = "|";
	private static final Logger logger = Logger.getLogger(HSRServiceOrdersUnMarshallerAction.class);
	private Mapper newFileMapper = new HSRNewFileDataMapper();
	private Mapper updateFileMapper = new HSRUpdateFileDataMapper();
	
	private MarketESBRoutingUtil routingUtil = new MarketESBRoutingUtil();
	private IIntegrationBO integrationBO;
	private IHSRStagingService stagingService;
	
	public Message unmarshalPayload(Message message) {
		
		logger.info("HSRServiceOrdersUnMarshallerAction umarshalling the file");
		Body body = message.getBody();
		Object payload = message.getBody().get(Body.DEFAULT_LOCATION);
		Object fileFeedPropertyValue = message.getProperties().getProperty(MarketESBConstant.ORIGINAL_FILE_FEED_NAME);
		String inputFileName = String.valueOf(fileFeedPropertyValue == null ? "" : fileFeedPropertyValue);
		/*Add the client key to the message to drive specific translation and mapping rules */
		body.add(MarketESBConstant.CLIENT_KEY, MarketESBConstant.Client.HSR);
		
		byte[] bytes = (byte[]) payload;
		String dataString = new String(bytes);
		
		try{
			String[] hsrOrders = dataString.split(System.getProperty("line.separator"));
			if (hsrOrders.length > 0 && hsrOrders[0].trim().equalsIgnoreCase(MarketESBConstant.HSR_NO_NEW_DATE)) {
				return null;
			}
			//If the Zero Orders came in the File We should send the email to PROD support and STOP ESB processing for that file
			HSRServiceOrders hsrServiceOrders = null;
			if(hsrOrders!= null & hsrOrders.length>0){
				List<String> legacyOrderIds = new ArrayList<String>();
				/* process new file */
				if(StringUtils.isNotBlank(inputFileName) &&  (inputFileName.startsWith(HSRFieldNameConstants.NEW_FILE_PREFIX) )){
					hsrServiceOrders = processNewFile(message, inputFileName,
							dataString, hsrOrders, legacyOrderIds);
				}
				/* process update file */
				else if(StringUtils.isNotBlank(inputFileName) &&  (inputFileName.startsWith(HSRFieldNameConstants.UPDATE_FILE_PREFIX) )){
					hsrServiceOrders = processUpdateFile(message,
							inputFileName, dataString, hsrOrders, legacyOrderIds);
				}
				
				if (hsrServiceOrders == null || hsrServiceOrders.getServiceOrders() == null || hsrServiceOrders.getServiceOrders().size() == 0) {
					// All the service orders were sent to the new process, so there is nothing left to send to the legacy process.  Return NULL 
					// to stop processing of this pipeline.
					return null;
				}
				
				if (legacyOrderIds.size() > 0) {
					message.getProperties().setProperty(MarketESBConstant.LEGACY_ORDERS, legacyOrderIds);
				}
			
			}
			else {				
				String errorMsg = "Zero Orders Found in input file : " + inputFileName;
				handleException(message, inputFileName, dataString, errorMsg, new Exception("ZERO ORDERS FILE"));
				//Retruning NULL as the message will STOP the 
				return null;
			}
			
			/* populate message*/
			hsrServiceOrders.setInputFileName(inputFileName);
			body.add(MarketESBConstant.FILE_FEED_NAME, inputFileName);
			body.add(MarketESBConstant.UNMARSHALLED_OBJ_GRAPH, hsrServiceOrders);
			
		} catch(Exception e) {
			String errorMsg = "Exception caught Parsing file: " + inputFileName;
			handleException(message, inputFileName, dataString, errorMsg, e);
			return null;
		} 
		
		return message;
	}


	/**
	 * @param message
	 * @param inputFileName
	 * @param dataString
	 * @param errorMsg
	 */
	private void handleException(Message message, String inputFileName,
			String dataString, String errorMsg, Exception ex) {
		logger.error( errorMsg );
		if (ex == null) {
			ExceptionHandler.handle((String) message.getBody().get(MarketESBConstant.CLIENT_KEY), 
					new String((byte[]) message.getBody().get()), inputFileName,
					errorMsg, dataString);
		}
		else{
			ExceptionHandler.handle((String) message.getBody().get(MarketESBConstant.CLIENT_KEY), 
					new String((byte[]) message.getBody().get()), inputFileName,
					errorMsg, dataString, ex);
		}
		
	}


	private HSRServiceOrders processUpdateFile(Message message,
			String inputFileName, String dataString, String[] hsrOrders, List<String> legacyOrderIds)
			throws Exception {
		
		boolean maintainLegacyStaging = routingUtil.isMaintainLegacyStagingData();
		boolean ofEnabled = routingUtil.isOrderFulfillmentEnabled() || routingUtil.isTestMode();
		List<String> newProcessOrders = new ArrayList<String>();
		
		boolean testMode = routingUtil.isTestMode();
		String testSuffix = "";
		String testSuffixNew = "";
		String testSuffixOld = "";
		String testAddressSuffix = "";
		if (testMode) {
			testSuffix = routingUtil.generateRandomTestSuffix(RANDOM_TEST_SUFFIX_LENGTH);
			testSuffixNew = "n";
			testSuffixOld = "o";
			testAddressSuffix = "xby";
		}
		
		List<String> newStagingLookupResults = new ArrayList<String>();
		List<String> legacyStagingLookupResults = new ArrayList<String>();
		if (ofEnabled) {
			// First loop through all the orders and create a list of all the
			// order numbers.
			List<List<String>> orderLookupIds = new ArrayList<List<String>>();
			for (String orderString : hsrOrders) {
				String[] fields = orderString.split(FIELD_DELIMITER_REGEX);
				String unitNumber = fields[HSRFieldNameConstants.SERVICE_UNIT_NUMBER].trim();
				String orderNumber = fields[HSRFieldNameConstants.SERVICE_ORDER_NUMBER].trim();
				List<String> orderId = new ArrayList<String>();
				orderId.add(unitNumber);
				orderId.add(orderNumber);
				orderLookupIds.add(orderId);
			}

			// Next lookup these orders in the integration DB to determine which
			// of them have been created through the new
			// OF process
			if (orderLookupIds.size() > 0) {
				HSRStageOrders legacyStagingOrders = null;
				if (testMode) {
					List<String> massagedOrderLookupIds = massageTestOrderLookupIds(orderLookupIds);
					newStagingLookupResults = integrationBO
							.getLatestExistingExternalServiceOrderNumbersThatMatchBeforeTestSuffix(
									massagedOrderLookupIds, testSuffixNew);
					legacyStagingOrders = stagingService.findLatestHsrOrdersWithOrderNumberMatchingBeforeTestSuffix(orderLookupIds, testSuffixNew);
				} else {
					List<String> fullOrderLookupIds = concatenateOrderLookupIds(orderLookupIds);
					newStagingLookupResults = integrationBO
							.getExistingExternalServiceOrdersIds(fullOrderLookupIds);

					legacyStagingOrders = stagingService.findHsrOrders(orderLookupIds);
				}
				
				if (legacyStagingOrders != null && legacyStagingOrders.getStageOrders() != null) {
					for (HSRStageOrder eachOrder : legacyStagingOrders.getStageOrders()) {
						legacyStagingLookupResults.add(getExternalOrderNumber(eachOrder));
					}
				}
			}
		}
		
		HSRServiceOrders hsrServiceOrders;
		hsrServiceOrders = new HSRServiceOrders();
		List<HSRServiceOrder> serviceOrdersList = new ArrayList<HSRServiceOrder>();
		for(int i=0 ; i< hsrOrders.length; i++){
			String eachOrder = hsrOrders[i];
			
			if (testMode) {
				eachOrder = addTestSuffixes(eachOrder, testSuffixOld + testSuffix, testAddressSuffix + testSuffixOld + testSuffix );
			}
			
			HSRServiceOrder hsrUpdateServiceOrder = (HSRServiceOrder)updateFileMapper.mapData((Object)eachOrder);
			if(hsrUpdateServiceOrder == null){
				String errorMsg = "Exception caught Parsing" + (i+1)  +"  order in file: "  + inputFileName;
				handleException(message, inputFileName, dataString, errorMsg,null);
				continue;
			}
			
			if (testMode) {
				
				if (isSendUpdateToNewOFProcess(hsrUpdateServiceOrder, newStagingLookupResults, legacyStagingLookupResults, testMode, testSuffixNew, testSuffixOld, testSuffix)) {
				
					// Get the actual suffix that we're using for the update put that in the order string
					int index = hsrUpdateServiceOrder.getServiceOrderNumber().indexOf(testSuffixNew);
					String updateSuffix = hsrUpdateServiceOrder.getServiceOrderNumber().substring(index);
					
					eachOrder = replaceOldSuffixesWithNewSuffixes(eachOrder, testSuffixOld + testSuffix, updateSuffix);
															
					if (maintainLegacyStaging) {
						// Order number in eachOrder has now been modified to have the 'new' string added to the end.
						HSRServiceOrder hsrLegacyServiceOrder = (HSRServiceOrder)newFileMapper.mapData((Object)eachOrder);
						serviceOrdersList.add(hsrLegacyServiceOrder);
						legacyOrderIds.add(MarketESBUtil.constructOrderIdUtil(hsrLegacyServiceOrder.getServiceOrderNumber(), hsrLegacyServiceOrder.getServiceUnitNumber()));
					}
					
					newProcessOrders.add(eachOrder);
				}
				setTestSuffixToOldInOrderNumber(testSuffixOld,
						hsrUpdateServiceOrder);
				serviceOrdersList.add(hsrUpdateServiceOrder);
				
			} else {
				if (ofEnabled && isSendUpdateToNewOFProcess(hsrUpdateServiceOrder, newStagingLookupResults, legacyStagingLookupResults, testMode, testSuffixNew, testSuffixOld, testSuffix)) {
					newProcessOrders.add(eachOrder);
					
					if (maintainLegacyStaging) {
						serviceOrdersList.add(hsrUpdateServiceOrder);
						legacyOrderIds.add(MarketESBUtil.constructOrderIdUtil(hsrUpdateServiceOrder.getServiceOrderNumber(), hsrUpdateServiceOrder.getServiceUnitNumber()));
					}
					
				} else {
					serviceOrdersList.add(hsrUpdateServiceOrder);
				}
			}
		}
		
		if (newProcessOrders.size() > 0) {
			writeFileForNewService(newProcessOrders, inputFileName);
		}
		
		hsrServiceOrders.setServiceOrders(serviceOrdersList);
		return hsrServiceOrders;
	}

	private void setTestSuffixToOldInOrderNumber(String suffix,
			HSRServiceOrder serviceOrder) {
		String orderNumber = serviceOrder.getServiceOrderNumber();
		String orderNumberWithReplacedSuffix = orderNumber.substring(0, LEGACY_OR_OF_INDICATOR_SUFFIX_LENGTH + RANDOM_TEST_SUFFIX_LENGTH)
			+ suffix + orderNumber.substring(orderNumber.length() - RANDOM_TEST_SUFFIX_LENGTH);
		serviceOrder.setServiceOrderNumber(orderNumberWithReplacedSuffix);
	}
	
	private List<String> massageTestOrderLookupIds(List<List<String>> orderLookupIds) {
		// Because order numbers can only be 8 characters long, and we use a 4-character test suffix, we need to have only the 
		// part of the original order number that appears in the test order number, which is the last 4 digits. 
		// So, for each orderLookupId, only leave the last 4 digits.
		List<String> massagedLookupIds = new ArrayList<String>();
		for (List<String> lookupIdPair : orderLookupIds) {
			String unitNumber = lookupIdPair.get(0);
			String orderNumber = lookupIdPair.get(1);
			massagedLookupIds.add(unitNumber + orderNumber.substring(orderNumber.length() - LEGACY_OR_OF_INDICATOR_SUFFIX_LENGTH - RANDOM_TEST_SUFFIX_LENGTH));
		}
		return massagedLookupIds;
	}

	private List<String> concatenateOrderLookupIds(List<List<String>> orderLookupIds) {
		List<String> concatenatedLookupIds = new ArrayList<String>();
		for (List<String> lookupId : orderLookupIds) {
			concatenatedLookupIds.add(lookupId.get(0) + lookupId.get(1));
		}
		return concatenatedLookupIds;
	}
	
	private HSRServiceOrders processNewFile(Message message,
			String inputFileName, String dataString, String[] hsrOrders, List<String> legacyOrderIds)
			throws Exception {
		
		boolean maintainLegacyStaging = routingUtil.isMaintainLegacyStagingData();
		
		boolean allowNewOFProcessForNewOrders = (routingUtil.isOrderFulfillmentEnabled() && routingUtil.isOrderFulfillmentAllowedForNewOrders() 
			&& routingUtil.isOrderFulfillmentAllowedForNewHSROrders()) || routingUtil.isTestMode();
		List<String> newProcessOrders = new ArrayList<String>();
		
		boolean testMode = routingUtil.isTestMode();
		String testSuffix = "";
		String testSuffixNew = "";
		String testSuffixOld = "";
		String testAddressSuffix = "";

		if (testMode) {
			testSuffix = routingUtil.generateRandomTestSuffix(3);
			testSuffixNew = "n";
			testSuffixOld = "o";
			testAddressSuffix = "xby";
		}
		
		HSRServiceOrders hsrServiceOrders;
		hsrServiceOrders = new HSRServiceOrders();
		List<HSRServiceOrder> serviceOrdersList = new ArrayList<HSRServiceOrder>();

		for(int i=0 ; i< hsrOrders.length; i++){
			String eachOrder = hsrOrders[i];
			
			if (testMode) {
				eachOrder = addTestSuffixes(eachOrder, testSuffixOld + testSuffix, testAddressSuffix + testSuffixOld + testSuffix );
			}
			
			HSRServiceOrder hsrServiceOrder = (HSRServiceOrder)newFileMapper.mapData((Object)eachOrder);
			if(hsrServiceOrder == null || StringUtils.isBlank(hsrServiceOrder.getServiceOrderNumber()) 
					|| StringUtils.isBlank(hsrServiceOrder.getServiceUnitNumber())){
				String errorMsg = "Exception caught Parsing" + (i+1)  +"  order in file: " + inputFileName
									+ ".\nEither the line is blank or no order number no unit number.";
				handleException(message, inputFileName, dataString, errorMsg, null);
				continue;
			}
			
			if (testMode) {
				eachOrder = replaceOldSuffixesWithNewSuffixes(eachOrder, testSuffixOld + testSuffix, testSuffixNew + testSuffix);
				
				if (maintainLegacyStaging) {
					// Order number in eachOrder has now been modified to have the 'new' string added to the end.
					HSRServiceOrder hsrLegacyServiceOrder = (HSRServiceOrder)newFileMapper.mapData((Object)eachOrder);
					serviceOrdersList.add(hsrLegacyServiceOrder);
					legacyOrderIds.add(MarketESBUtil.constructOrderIdUtil(hsrLegacyServiceOrder.getServiceOrderNumber(), hsrLegacyServiceOrder.getServiceUnitNumber()));
				}
				
				newProcessOrders.add(eachOrder);
				serviceOrdersList.add(hsrServiceOrder);
				
			} else {
				if (allowNewOFProcessForNewOrders) {
					newProcessOrders.add(eachOrder);
					
					if (maintainLegacyStaging) {
						serviceOrdersList.add(hsrServiceOrder);
						legacyOrderIds.add(MarketESBUtil.constructOrderIdUtil(hsrServiceOrder.getServiceOrderNumber(), hsrServiceOrder.getServiceUnitNumber()));
					}
				} else {
					serviceOrdersList.add(hsrServiceOrder);
				}
			}
		}
		
		if (newProcessOrders.size() > 0) {
			writeFileForNewService(newProcessOrders, inputFileName);
		}		
		
		hsrServiceOrders.setServiceOrders(serviceOrdersList);		
		return hsrServiceOrders;
	}
	
	private boolean isSendUpdateToNewOFProcess(HSRServiceOrder serviceOrder, List<String> newStagingLookupResults, 
			List<String> legacyStagingLookupResults, boolean testMode, String testSuffixNew, String testSuffixOld, String testSuffix) {
		
		String externalOrderNumberBeforeTestSuffix = "";
		if (testMode) {
			String externalOrderNumber = getExternalOrderNumber(serviceOrder);
			int index = externalOrderNumber.indexOf(testSuffixOld + testSuffix);
			externalOrderNumberBeforeTestSuffix = externalOrderNumber.substring(0, index) + testSuffixNew;
		}
		
		// If this is an Update or Cancel transaction, then only send it to the new OF Process if we found that order number
		// in the list of previously processed orders
		if (testMode) {
			// If this is test mode, then we have to compare the orderIds without the test suffixes
			if( lookupResultsContainExternalOrderNumber(serviceOrder,
					newStagingLookupResults, testSuffixNew,
					externalOrderNumberBeforeTestSuffix)) {
				return true;
			}
			
			return !lookupResultsContainExternalOrderNumber(serviceOrder,
					legacyStagingLookupResults, testSuffixNew,
					externalOrderNumberBeforeTestSuffix);
			
		} else {
			String externalOrderId = getExternalOrderNumber(serviceOrder);
			boolean newStagingContainsOrder = newStagingLookupResults.contains(externalOrderId);
			boolean legacyStagingContainsOrder = legacyStagingLookupResults != null && legacyStagingLookupResults.contains(externalOrderId);
			// send to new staging if order already exists in new staging, or else if order doesn't already exist in legacy staging and roll-out conditions for new staging are satisfied
			return newStagingContainsOrder || !legacyStagingContainsOrder;
		}
    }


	private boolean lookupResultsContainExternalOrderNumber(
			HSRServiceOrder serviceOrder, List<String> newStagingLookupResults,
			String testSuffixNew, String externalOrderNumberBeforeTestSuffix) {
		for (String lookupResult : newStagingLookupResults) {
			int testSuffixNewIndex = lookupResult.indexOf(testSuffixNew);
			if (testSuffixNewIndex > -1) {
				String lookupResultWithoutTestSuffix = lookupResult.substring(0, testSuffixNewIndex + testSuffixNew.length());
				if (externalOrderNumberBeforeTestSuffix.equalsIgnoreCase(lookupResultWithoutTestSuffix)) {
					// If we find one, then set the order number of the UPDATE transaction to be the same as the 
					// original NEW transaction
					String lookedUpOrderNumber = lookupResult.substring(lookupResult.length() - ORDER_NUMBER_LENGTH);
					serviceOrder.setServiceOrderNumber(lookedUpOrderNumber);
					return true;
				}
			}
		}
		return false;
	}
	
	private String getExternalOrderNumber(HSRStageOrder serviceOrder) {
		return serviceOrder.getUnitNumber() + serviceOrder.getOrderNumber();
	}
	
	private String getExternalOrderNumber(HSRServiceOrder serviceOrder) {
		return serviceOrder.getServiceUnitNumber() + serviceOrder.getServiceOrderNumber();
	}
	
	private void writeFileForNewService(List<String> orders, String fileName) {
		try {
			// Use BufferedWriter
			String lineSeparator = System.getProperty("line.separator");
			StringBuffer fileContents = new StringBuffer();
			for (String order : orders) {
				if (fileContents.length() > 0) {
					fileContents.append(lineSeparator);
				}
				fileContents.append(order);
			}
			
			routingUtil.writeFileForNewService(MarketESBConstant.Client.HSR, fileContents.toString(), fileName);
		} catch (Exception e) {
			logger.error("Error while generating the XML file for the new OMS service.", e);
		}
	}

	private String addTestSuffixes(String orderString, String testOrderSuffix, String testAddressSuffix) {		
		String[] fields = orderString.split(FIELD_DELIMITER_REGEX);
		String orderNumber = fields[HSRFieldNameConstants.SERVICE_ORDER_NUMBER].trim();
		String address = fields[HSRFieldNameConstants.REPAIR_ADDRESS1].trim();
		
		// The new order number has to be the same length as the old order number (currently they are 8 characters long),
		// so we'll just take the last 4 digits of the current order number (the more unique part), and append the 
		// random string after that
		String newOrderNumber = "";
		if (orderNumber.length() > testOrderSuffix.length()) {
			newOrderNumber = orderNumber.substring(orderNumber.length() - testOrderSuffix.length());
		}
		newOrderNumber += testOrderSuffix;
		
		fields[HSRFieldNameConstants.SERVICE_ORDER_NUMBER] = newOrderNumber;
		fields[HSRFieldNameConstants.REPAIR_ADDRESS1] = address + testAddressSuffix;
		
		return StringUtils.join(fields, FIELD_DELIMITER_STRING);
	}
	
	private String replaceOldSuffixesWithNewSuffixes(String orderString,
			String oldTestSuffix, String newTestSuffix) {
		return orderString.replaceAll(oldTestSuffix, newTestSuffix);
	}

	/**
	 * The Constructor which is called by ESB runtime to configure actions
	 * @param config
	 */
	public HSRServiceOrdersUnMarshallerAction(ConfigTree config) {
		super.configTree = config;
		init();
	}
	
	/**
	 * Default Constructor for JUnit test cases
	 */
	public HSRServiceOrdersUnMarshallerAction() {
	}
	
	private void init() {
		if (this.integrationBO == null) {
			this.integrationBO = (IIntegrationBO) SpringUtil.factory.getBean("integrationBO");
		}
		
		if (this.stagingService == null) {
			this.stagingService = (IHSRStagingService) SpringUtil.factory.getBean(MarketESBConstant.HSR_STAGING_SERVICE);
		}
	}
	
	@Override
	public void exceptionHandler(Message msg, Throwable th) {
		ExceptionHandler.handleNPSException(msg, th);
		super.exceptionHandler(msg, th);
	}

	public Mapper getUpdateFileMapper() {
		return updateFileMapper;
	}

	public void setUpdateFileMapper(Mapper updateFileMapper) {
		this.updateFileMapper = updateFileMapper;
	}

	public Mapper getNewFileMapper() {
		return newFileMapper;
	}

	public void setNewFileMapper(Mapper newFileMapper) {
		this.newFileMapper = newFileMapper;
	}


	public void setIntegrationBO(IIntegrationBO integrationBO) {
		this.integrationBO = integrationBO;
	}

	public void setStagingService(IHSRStagingService stagingService) {
		this.stagingService = stagingService;
	}
}
