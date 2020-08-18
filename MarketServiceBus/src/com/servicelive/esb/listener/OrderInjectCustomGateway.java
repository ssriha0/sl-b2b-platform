package com.servicelive.esb.listener;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.ConfigurationException;
import org.jboss.soa.esb.Service;
import org.jboss.soa.esb.client.ServiceInvoker;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.listeners.ListenerTagNames;
import org.jboss.soa.esb.listeners.lifecycle.AbstractThreadedManagedLifecycle;
import org.jboss.soa.esb.listeners.lifecycle.ManagedLifecycleException;
import org.jboss.soa.esb.listeners.message.MessageDeliverException;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.format.MessageFactory;

import com.newco.marketplace.translator.business.IStagingService;
import com.newco.marketplace.translator.util.SpringUtil;
import com.newco.marketplace.webservices.dao.ShcOrder;
import com.servicelive.esb.actions.ServiceOrdersUnMarshallerAction;
import com.servicelive.esb.constant.MarketESBConstant;
import com.servicelive.esb.dto.ServiceOrder;
import com.servicelive.esb.dto.ServiceOrders;
import com.servicelive.esb.integration.bo.IIntegrationBO;
import com.servicelive.util.MarketESBRoutingUtil;
import com.servicelive.util.RolloutUnits;
import com.thoughtworks.xstream.XStream;

/**
 * This is an ESB custom gateway to process Order Injection file. It parse XML file and
 * convert it into multiple ESB messages.For configuration refer jboss-esb.xml
 * 
 * @author Shekhar Nirkhe (cnirkhe@searshc.com)
 * @since 08/01/2009
 * @see  http://www.jboss.org/community/wiki/WritingCustomListenersforJBossESB4x#Example for more details.
 * 
 * Known Problems : Memory leak with JBoss 4.3.
 * Check : https://jira.jboss.org/jira/browse/JBESB-2605
 *
 */

public class OrderInjectCustomGateway extends AbstractThreadedManagedLifecycle {
	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(OrderInjectCustomGateway.class);
	
	/** Required by ESB
	 * 
	 */
    private ConfigTree listenerConfig;
    private Service service;
    private ServiceInvoker serviceInvoker;
    
    private final long ONE_SECOND = 1000;
    private final int DEFAULT_FREQUENCY = 5;
    private final String FREQUENCY="frequency";
    private final String DIRECTORY = "directory";
    private final String INPUT_SUFFIX = "input-suffix";
    private final String MAXFILES = "maxFiles";
    private final long timeBetween2Msgs = 1000; // in ms
    
    private FileFilter fileFilter, esbFileFilter;
    private final int maxRetryCount = 5;
    
    private final long ONE_HOUR = 3600 * ONE_SECOND; //in ms
    boolean isRunning = true;
    private boolean takeTSfromFileName = true;
    String pattern = "yyyyMMdd.hhmmS";
	SimpleDateFormat format = new SimpleDateFormat(pattern);
	private MarketESBRoutingUtil routingUtil = new MarketESBRoutingUtil();

    
    /**
     * Map to keep the list of files which gave an error recently while parsing.
     * There are two reason for parsing to fail.
     * 1) File is not an Order Injection file.
     * 2) Some XMl syntax error in the file.      
     * 3) File is being copied. It takes sometime to FTP a file. 
     *    If gateway starts processing file in between it will give XML parse error.
     * 	  In this situation  should retry parsing file after sometime. Check removeBadFile method for implementation.
     * 
     */
    HashMap<String, Integer> errorMap = new HashMap<String, Integer>();
    
    private String inFolder, inputSuffix;
    private String esbMessageSuffix = "startProcessESB"; //no period(.) please
    
    private int frequency, maxFiles;

	private IStagingService stagingService;

	private IIntegrationBO integrationBO;
    
    public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	// Just for unit testing	
	/*
	public OrderInjectCustomGateway() throws ConfigurationException   {				
		 inFolder = "/home/sldev/test";	     
	} */
    
	public OrderInjectCustomGateway(final ConfigTree config) throws ConfigurationException {
        super(config);
        this.listenerConfig = config;
 
        String serviceCategory = listenerConfig.getRequiredAttribute(ListenerTagNames.TARGET_SERVICE_CATEGORY_TAG);
        String serviceName = listenerConfig.getRequiredAttribute(ListenerTagNames.TARGET_SERVICE_NAME_TAG);
                
        try {
        	frequency = Integer.parseInt(listenerConfig.getRequiredAttribute(FREQUENCY)); 
        } catch (java.lang.NumberFormatException n) {
        	throw new ConfigurationException("Property frequency is not configured");
        }
        
        try {
        	maxFiles = Integer.parseInt(listenerConfig.getRequiredAttribute(MAXFILES));
        } catch (java.lang.NumberFormatException n) {
        	throw new ConfigurationException("Property maxFiles is not configured");
        }
        
        inFolder = listenerConfig.getRequiredAttribute(DIRECTORY);
        inputSuffix = listenerConfig.getRequiredAttribute(INPUT_SUFFIX);
        
        if (inFolder == null)
        	throw new ConfigurationException("Property directory is not configured");
        
        if (inputSuffix == null)
        	throw new ConfigurationException("Property input-suffix is not configured");
        
        if (inputSuffix.charAt(0) == '.')
        	inputSuffix = inputSuffix.substring(1);
        
        createFileFilter();
        service = new Service(serviceCategory, serviceName);
    }
 
    protected void doInitialise() throws ManagedLifecycleException {
        // Create the ServiceInvoker instance for the target service....
        try {
            serviceInvoker = new ServiceInvoker(service);
        } catch (MessageDeliverException e) {
            throw new ManagedLifecycleException("Failed to create ServiceInvoker for Service '" + service + "'.");
        }
        
		stagingService = (IStagingService) SpringUtil.factory.getBean(MarketESBConstant.SL_STAGING_SERVICE);
		integrationBO = (IIntegrationBO)SpringUtil.factory.getBean("integrationBO");
    }
 
    @Override
    protected void doRun() {
    	while(isRunning()) {
    		// Wait for a message....
    		Map<String, Object> map = waitForPayload();
    		ServiceOrders serviceOrders = (ServiceOrders)map.get("ServiceOrders");
    		String fileName = (String)map.get("FileName");
    		if (Thread.interrupted() == true) {
    			isRunning  = false;    			
    		}
    		
    		// Send the message to the target service's Action Pipeline via
    		// the ServiceInvoker...
    		if (serviceOrders != null) {
    			try {
    				List<String> legacyOrders = new ArrayList<String>();
    				if (routingUtil.isOrderFulfillmentEnabled() || routingUtil.isTestMode()) {
    					removeNewOrdersAndCallNewService(serviceOrders, fileName, legacyOrders);
    				}
    				if (serviceOrders.getServiceOrders().size() > 0) {
    					createEsbMsgs(serviceOrders, fileName, legacyOrders); 
    				}
    			} catch (InterruptedException e) {
    				logger.warn("Recevied InterruptedException:" + e);
    				isRunning = false;
    			} catch (MessageDeliverException e) {
    				e.printStackTrace();
    			}
    		}
    	}
    }

	private void removeNewOrdersAndCallNewService(
			ServiceOrders serviceOrders, String fileName, List<String> legacyOrders) {
		List<ServiceOrder> serviceOrderList = serviceOrders.getServiceOrders();
		List<ServiceOrder> newServiceOrderList = new ArrayList<ServiceOrder>();
		
		boolean maintainLegacyStaging = routingUtil.isMaintainLegacyStagingData();
		boolean allowNewOFProcessForNewOrders = routingUtil.isOrderFulfillmentAllowedForNewOrders() 
				&& routingUtil.isOrderFulfillmentAllowedForNewOMSOrders();
		boolean testMode = routingUtil.isTestMode();
		String testSuffix = "";
		String testSuffixNew = "";
		String testSuffixOld = "";
		String testAddressSuffix = "";
		if (testMode) {
			allowNewOFProcessForNewOrders = true;
			testSuffix = routingUtil.generateRandomTestSuffix();
			testSuffixNew = "new";
			testSuffixOld = "old";
			testAddressSuffix= "xby";
		}
		
		// First loop through all the orders and create a list of all the order numbers whose transaction types are not 'NEW'.
		List<String> orderLookupIds = new ArrayList<String>();
		for (ServiceOrder serviceOrder : serviceOrderList) {
			if (!isNewTransaction(serviceOrder)) {
				orderLookupIds.add(getExternalOrderId(serviceOrder));
			}
		}
		
		// Next lookup these orders in the integration DB to determine which of them have been created through the new
		// OF process
		List<String> newStagingLookupResults = new ArrayList<String>();
		List<String> legacyStagingLookupResults = new ArrayList<String>();
		if (orderLookupIds.size() > 0) {
			ShcOrder[] legacyStagingOrders = null;
			if (testMode) {
				//FIXME start populating the legacyStagingLookupResults
				newStagingLookupResults = integrationBO.getLatestExistingExternalServiceOrderNumbersThatMatchBeforeTestSuffix(orderLookupIds, testSuffixNew);
				legacyStagingOrders = stagingService.findLatestShcOrdersWithOrderNumberMatchingBeforeTestSuffix(orderLookupIds, testSuffixNew);
			} else {
				newStagingLookupResults = integrationBO.getExistingExternalServiceOrdersIds(orderLookupIds);
				legacyStagingOrders = stagingService.findShcOrders(orderLookupIds);
			}
			
			for (ShcOrder eachLegacyStagingOrder : legacyStagingOrders) {
				legacyStagingLookupResults.add(String.format("%s%s", eachLegacyStagingOrder.getUnitNo(), eachLegacyStagingOrder.getOrderNo()));
			}
		}
				
		RolloutUnits rolloutUnits = null;
		
		if (testMode) {
			rolloutUnits = new RolloutUnits();
			rolloutUnits.setAllUnits(true);
		} else {
			if (allowNewOFProcessForNewOrders) {
				rolloutUnits = routingUtil.getRolloutUnits();
			}
		}
		
		// A list of orders that need to be added to the old pipeline if we are running in both Test Mode and
		// Legacy Staging Data Maintenance Mode
		List<ServiceOrder> testModeLegacyList = new ArrayList<ServiceOrder>();
		
		// Add all orders that meet the criteria to be sent to the new OF process to the 'new' list, and remove them from the 'old list' 
		for (Iterator<ServiceOrder> i = serviceOrderList.iterator(); i.hasNext(); ) {
			ServiceOrder serviceOrder = i.next();
			
			if (testMode) {
				// add the testSuffix to the address so they will not be accidentally grouped
				String address = "";
				boolean useRepairLocation = false;
				if (serviceOrder.getRepairLocation() != null && serviceOrder.getRepairLocation().getAddress() != null 
						&& serviceOrder.getRepairLocation().getAddress().getStreetAddress1().length() > 0) {
					address = serviceOrder.getRepairLocation().getAddress().getStreetAddress1();
					useRepairLocation = true;
				} else if (serviceOrder.getCustomer() != null && serviceOrder.getCustomer().getAddress() != null) {
					address = serviceOrder.getCustomer().getAddress().getStreetAddress1();
					useRepairLocation = false;
				}
				
				if (address.length() > 0) {
					if (address.contains(testAddressSuffix)) {
						if (address.contains(testAddressSuffix + testSuffixOld)) {
							// Make sure the last name has the new suffix.  The old suffix will be substituted in for the old pipeline later.
							address = address.replaceFirst(testAddressSuffix + testSuffixOld, testAddressSuffix + testSuffixNew);
						}
					} else {
						address += testAddressSuffix + testSuffixNew + testSuffix;
					}
					if (useRepairLocation) {
						serviceOrder.getRepairLocation().getAddress().setStreetAddress1(address);
					} else {
						serviceOrder.getCustomer().getAddress().setStreetAddress1(address);
					}
				}
			}
			
			if (isSendToNewOFProcess(serviceOrder, newStagingLookupResults, legacyStagingLookupResults, 
					allowNewOFProcessForNewOrders, rolloutUnits, testMode, testSuffixNew, testSuffix)) {
				
				if (testMode) {
					// Do not remove items from the list for the old pipeline in test mode.
					if (maintainLegacyStaging) {
						// If we are maintaining legacy staging data and we are in test mode, then we need to add an additional
						// record to the old pipeline so that the data going to the new pipeline also gets sent as a legacy record
						// to the old pipeline.
						ServiceOrder copyOfServiceOrder = testMode_createCopyOfServiceOrder(serviceOrder);						
						if (copyOfServiceOrder != null) {
							testModeLegacyList.add(copyOfServiceOrder);
						}
					}
				} else {
					if (maintainLegacyStaging) {
						// If we are maintaining legacy staging data, do not remove the item from the list for the old pipeline,
						// but add a record to the legacy list so we can stop the legacy records from going all the way through
						// the old order fulfillment process.
						legacyOrders.add(serviceOrder.getUniqueKey());
						
					} else {
						i.remove();
					}
				}
				newServiceOrderList.add(serviceOrder);
			}
		}
		
		// Finally write the 'new' list to an XML file that will be picked up by the new OF process
		if (newServiceOrderList.size() > 0) {
			ServiceOrders newServiceOrders = new ServiceOrders();
			newServiceOrders.setBuyerId(serviceOrders.getBuyerId());
			newServiceOrders.setIdentification(serviceOrders.getIdentification());
			newServiceOrders.setServiceOrders(newServiceOrderList);
			
			newServiceOrders = stripOriginalScheduledDate(newServiceOrders);
			
			String newXml = marshalAction(newServiceOrders);
			writeFileForNewService(newXml, fileName);
		}
		
		if (testMode) {
			// Add the test suffix to the old orders.  For orders that had "new" + testSuffix added, change "new" to "old"
			for (Iterator<ServiceOrder> i = serviceOrderList.iterator(); i.hasNext(); ) {
				ServiceOrder serviceOrder = i.next();
				if (serviceOrder.getServiceOrderNumber().indexOf(testSuffixNew) == -1) {
					serviceOrder.setServiceOrderNumber(serviceOrder.getServiceOrderNumber() + testSuffixOld + testSuffix);
				} else {
					serviceOrder.setServiceOrderNumber(serviceOrder.getServiceOrderNumber().replaceFirst(testSuffixNew, testSuffixOld));
				}
				
				// Also replace the "new" in the address with "old"
				if (serviceOrder.getRepairLocation() != null && serviceOrder.getRepairLocation().getAddress() != null 
						&& serviceOrder.getRepairLocation().getAddress().getStreetAddress1().length() > 0) {
					String address = serviceOrder.getRepairLocation().getAddress().getStreetAddress1();
					address = address.replaceFirst(testAddressSuffix + testSuffixNew, testAddressSuffix + testSuffixOld);
					serviceOrder.getRepairLocation().getAddress().setStreetAddress1(address);
					
				} else if (serviceOrder.getCustomer() != null && serviceOrder.getCustomer().getAddress() != null) {
					String address = serviceOrder.getCustomer().getAddress().getStreetAddress1();
					address = address.replaceFirst(testAddressSuffix + testSuffixNew, testAddressSuffix + testSuffixOld);
					serviceOrder.getCustomer().getAddress().setStreetAddress1(address);
				}
			}
			
			// Add in any legacy orders (orders that are being sent to the new pipeline, but need to go into the legacy
			// staging tables)
			for (ServiceOrder legacyOrder : testModeLegacyList) {
				serviceOrderList.add(legacyOrder);
				legacyOrders.add(legacyOrder.getUniqueKey());
			}
			
		}
		
	}

	/**
	 * This method is added for a temporary hack for SL-11915. It has to be removed once the issue is handled in Mapforce.
	 * This method cheats the logic in Mapforce by forcing the 'Promised Date' to be considered for processing.
	 * @param newServiceOrders
	 * @return
	 */
	private ServiceOrders stripOriginalScheduledDate(
			ServiceOrders newServiceOrders) {
		for(ServiceOrder order:newServiceOrders.getServiceOrders()){
			//FIXME: This dummy date is added to override the business rule in Mapforce. 
			//This date is not used in the application.
			order.setOriginalScheduledDate("2999-01-01");
		}
		return newServiceOrders;
	}
	
	private ServiceOrder testMode_createCopyOfServiceOrder(
			ServiceOrder serviceOrder) {
		
		ServiceOrder copyOfServiceOrder = null;
		
		// To easily get a new copy of the service order, serialize it to a byte array, and then deserialize to a new
		// service order object.
		ByteArrayOutputStream bos = new ByteArrayOutputStream() ; 
		ObjectOutput out;
		try {
			out = new ObjectOutputStream(bos);
			out.writeObject(serviceOrder);
			out.close();
		} catch (IOException e) {
			logger.error("Error serializing service order object in test mode code.", e);
			e.printStackTrace();
			return null;
		} 
		byte[] buf = bos.toByteArray();
		
		// Deserialize from the byte array 
		ObjectInputStream in;
		try {
			in = new ObjectInputStream(new ByteArrayInputStream(buf));
			copyOfServiceOrder = (ServiceOrder) in.readObject(); 
			in.close(); 
		} catch (IOException e) {
			logger.error("Error deserializing service order object in test mode code.", e);
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			logger.error("Error deserializing service order object in test mode code.", e);
			e.printStackTrace();
		}
		return copyOfServiceOrder;
	}
    
    private void writeFileForNewService(String newXml, String fileName) {
    	try {
			routingUtil.writeFileForNewService(MarketESBConstant.Client.OMS, newXml, fileName);
		} catch (Exception e) {
			logger.error("Error while generating the XML file for the new OMS service.", e);
		}
    }

	private boolean isSendToNewOFProcess(ServiceOrder serviceOrder, List<String> newStagingLookupResults, List<String> legacyStagingLookupResults, 
			boolean allowNewOFProcessForNewOrders, RolloutUnits rollOutUnits,
			boolean testMode, String testSuffixNew, String testSuffix) {
		
		String externalOrderNumberBeforeTestSuffix = "";
		if (testMode) {
			externalOrderNumberBeforeTestSuffix = getExternalOrderId(serviceOrder) + testSuffixNew;
			serviceOrder.setServiceOrderNumber(serviceOrder.getServiceOrderNumber() + testSuffixNew + testSuffix);
		}
		
		if (!isNewTransaction(serviceOrder)) {
			// If this is an Update or Cancel transaction, then only send it to the new OF Process if we found that order number
			// in the list of previously processed orders
			// if the external order number is not found in legacy staging, send to new OF process
			// as this will be an out-of-order update
			if (testMode) {
				// If this is test mode, then we have to compare the orderIds without the test suffixes
				if (lookupResultsContainExternalOrderNumber(serviceOrder,
						newStagingLookupResults, testSuffixNew,
						externalOrderNumberBeforeTestSuffix)) {
					return true;
				}
				
				return !lookupResultsContainExternalOrderNumber(serviceOrder, legacyStagingLookupResults, testSuffixNew, externalOrderNumberBeforeTestSuffix);
				
			} else {
				// send to new staging if order already exists in new staging, or else if order doesn't already exist in legacy staging and roll-out conditions for new staging are satisfied
				String externalOrderId = getExternalOrderId(serviceOrder);
				boolean newStagingContainsOrder = newStagingLookupResults.contains(externalOrderId);
				boolean legacyStagingContainsOrder = legacyStagingLookupResults.contains(externalOrderId);
				return newStagingContainsOrder || (!legacyStagingContainsOrder && allowNewOFProcessForNewOrders && checkRollOutCondition(serviceOrder, rollOutUnits));
			}
		} else if (allowNewOFProcessForNewOrders) {
			// If this is a New transaction, then check the roll-out conditions
			return checkRollOutCondition(serviceOrder, rollOutUnits);
		} else {
			// If this is a New transaction, but we are not allowing new orders to be processed through the new OF, then return False
			return false;
		}
    }

	private boolean lookupResultsContainExternalOrderNumber(
			ServiceOrder serviceOrder, List<String> newStagingLookupResults,
			String testSuffixNew, String externalOrderNumberBeforeTestSuffix) {
		for (String lookupResult : newStagingLookupResults) {
			int testSuffixNewIndex = lookupResult.indexOf(testSuffixNew);
			if (testSuffixNewIndex > -1) {
				String lookupResultWithoutTestSuffix = lookupResult.substring(0, testSuffixNewIndex + testSuffixNew.length());
				if (externalOrderNumberBeforeTestSuffix.equalsIgnoreCase(lookupResultWithoutTestSuffix)) {
					// If we find one, then set the order number of the UPDATE transaction to be the same as the 
					// original NEW transaction
					String lookedUpOrderNumber = lookupResult.substring(serviceOrder.getServiceUnitNumber().length());
					serviceOrder.setServiceOrderNumber(lookedUpOrderNumber);
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean checkRollOutCondition(ServiceOrder serviceOrder, RolloutUnits rollOutUnits) {
		String unitNumber = routingUtil.removeLeading0sFromUnitNumber(serviceOrder.getServiceUnitNumber());
		return rollOutUnits.containsUnit(unitNumber);
	}
	
	private String getExternalOrderId(ServiceOrder serviceOrder) {
		return serviceOrder.getServiceUnitNumber() + serviceOrder.getServiceOrderNumber();
	}
	
	private boolean isNewTransaction(ServiceOrder serviceOrder) {
		return serviceOrder.getTransactionType().equalsIgnoreCase("NEW");
	}

	private void memoryPrint() {
    	long mb = 1024 * 1024;
    	if (logger.isInfoEnabled()) {
    		logger.info("Total Memory(MB):" + Runtime.getRuntime().totalMemory() / mb +
    				" : Free memory(MB):" + Runtime.getRuntime().freeMemory() / mb);
    	}
    }
    
    private void createEsbMsgs(ServiceOrders serviceOrders, String fileName, List<String> legacyOrders) throws MessageDeliverException, InterruptedException {
    	List<List<ServiceOrder>> list = serviceOrders.getServiceOrdersMultiList(maxFiles);
    	int i = 0;
    	//memoryPrint();
    	for (List<ServiceOrder> list1 : list) {    	
    		if (list1 != null && list1.size() > 0) {
    			i++;
    			serviceOrders.setServiceOrders(list1);    					
    			Message esbMessage = MessageFactory.getInstance().getMessage();    			
    			esbMessage.getBody().add(serviceOrders);
    			esbMessage.getProperties().setProperty(MarketESBConstant.ORIGINAL_FILE_FEED_NAME, fileName);
    			esbMessage.getProperties().setProperty(MarketESBConstant.LEGACY_ORDERS, legacyOrders);
    			serviceInvoker.deliverAsync(esbMessage);
    			Thread.sleep(timeBetween2Msgs); //sleeping for some time to avoid race condition.
    		}
    	}
		
    	memoryPrint();
		
		if (logger.isInfoEnabled())
		  logger.info("Created " + i + " messages");	
    }
 
    /**
     * Check hot folder every X seconds. If file present then marshal it.
     * @return
     */
    private Map<String, Object> waitForPayload() {
    	Map<String, Object> map = new HashMap<String, Object> ();
    	ServiceOrders orders = null;
    	String file = null;
    	File ff = null;
    	try {
    		if (frequency == 0)
    			frequency = DEFAULT_FREQUENCY;
    		Thread.sleep(frequency * ONE_SECOND);	
    		
    		if (isESBMessageExist() == false)
    		file = getFileName();
    		
    		if (file != null) {
    			file = inFolder + "/" + file;
    			logger.info("Reading file " + file);
    			byte[] xmlFeed;		
    			 ff = new File(file);
    			xmlFeed = getBytesFromFile(ff);

    			ByteArrayInputStream byteArrayInputStream = 
    				new ByteArrayInputStream((byte[])xmlFeed);    			
    			orders = unmarshalAction(byteArrayInputStream);
    			if (orders != null)
    			  ff.delete();
    		}    		
    		
    	} catch (Exception e) {
    		// It looks like file is not yet copied yet completely. Other reason could be that file is corrupt.    		
    		logger.warn("Error in reading file:" + file);
    		if (ff != null) {
    		   boolean flag = removeBadFile(ff);
    		   if (flag)
    			   e.printStackTrace();
    		}
    	}
    	
    	map.put("ServiceOrders", orders);
    	map.put("FileName", getOnlyFileName(file));
    	return map;
    	// Wait for a message...
    }
    
    /**
     * Remove file from disk if input file system is not able to parse the file even after 5 attempts.
     * We have to make sure that  FTP process to copy file from Sears server does not take more than
     * 5 * frequency time otherwise fill will be deleted without processing.
     * 
     * @param xmlFile
     */
    private boolean removeBadFile(File xmlFile) {
    	if (xmlFile == null)
    		return false;
    	String file = xmlFile.getName();
    	if (errorMap.size() > 1000) { //keep size in check
    		errorMap = new HashMap<String, Integer>();
    	}

    	Integer count = errorMap.get(file);
    	if (count == null) {
    		errorMap.put(file, 1);
    		return false;
    	}

    	if (count  < maxRetryCount) { //retry 5 times
    		count ++;
    		errorMap.put(file, count);
    	} else { //remove file from disk
    		if (xmlFile != null) {
    			logger.info("Removing corrupt file " + file + " without processing");
    			xmlFile.delete();
    		}
    		errorMap.remove(file);
    		return true;
    	}
    	return false;
    } 
    
    /**
     * Parse XML file and convert it into java objects.
     * @param byteArrayInputStream
     * @return
     */
	private ServiceOrders unmarshalAction(ByteArrayInputStream byteArrayInputStream) {
		XStream xstream = new XStream();		
		xstream.processAnnotations(ServiceOrdersUnMarshallerAction.classes);
		ServiceOrders  serviceOrders = (ServiceOrders) xstream.fromXML(byteArrayInputStream);
		if (logger.isDebugEnabled())
		  logger.debug("Unmarshalled the xml payload into ServiceOrders obj="+serviceOrders);		
		return serviceOrders;
	}
	
	private String marshalAction(ServiceOrders serviceOrders) {
		XStream xstream = new XStream();
		xstream.processAnnotations(ServiceOrdersUnMarshallerAction.classes);
		String xml = xstream.toXML(serviceOrders);

		if (logger.isDebugEnabled())
		  logger.debug("Marshalled the xml payload for the new service from ServiceOrders obj="+serviceOrders);
		
		return xml;
		
	}
	
	private String getOnlyFileName(String inputFilefeedName) {
		if (inputFilefeedName == null)
			return inputFilefeedName;
		
		int i = inputFilefeedName.lastIndexOf("/");
		if (i == -1)
			return inputFilefeedName;
		return inputFilefeedName.substring(i+1);
	}
	
    
    private String getFileExtension(String inputFilefeedName) {		
    	String ext = inputFilefeedName.substring(inputFilefeedName.lastIndexOf('.')+1, inputFilefeedName.length());
    	return ext;		
    }

    private void createFileFilter() {    		
    	fileFilter = new FileFilter() {
    		public boolean accept(File f) {
        		if (f.isFile()) {
        			String ext = getFileExtension(f.getName());
        			if (ext.equals(inputSuffix))
    				return true;
    			} 
    			return false;
    		}};
    		
    	esbFileFilter = new FileFilter() {
        	public boolean accept(File f) {
        		if (f.isFile()) {
        			String ext = getFileExtension(f.getName());
        			if (ext.equals(esbMessageSuffix))
        	    		return true;        	    		
    }
        		return false;
        	}};
    }

    /**
     * Get oldest Order Injection file name present on the disk. 
     * @return
     */
    
    private String getFileName() {
		File folder = new File(inFolder);	
		File[] listOfFiles = folder.listFiles(fileFilter);
		String oldestFilename = null;
		long oldestTS = 0;

		for (int i = 0; i < listOfFiles.length; i++) {			
			long newTS = getTS(listOfFiles[i]);
			if (oldestTS == 0 || oldestTS > newTS) {	 
				oldestFilename = listOfFiles[i].getName();
				oldestTS = newTS;
			}
		}
		//System.out.println("Oldest TS:" + oldestTS);
		return oldestFilename;
	}
	
    private long getTS (File file) {
		String oldestFilename = file.getName();
		if (takeTSfromFileName) {
			String [] arr = oldestFilename.split("\\.");
			if (arr.length > 3 && arr[2].length() == 8) {
				try {
					Date dt = format.parse(arr[1]+ "." + arr[2]);
					//System.out.println("DT:" + dt);
					return dt.getTime();					
				} catch (ParseException e) {
					//System.out.println(e.getMessage());
				}
			}
		} 

		logger.warn("File name is non compliant to Sears.");
		return file.lastModified();
	}
    
	private byte[] getBytesFromFile(File file) throws IOException {
		InputStream is = new FileInputStream(file);

		// Get the size of the file
		long length = file.length();
		if (length > Integer.MAX_VALUE) {
			// File is too large
			logger.warn(file.getName() + " is too large");
		}

		// Create the byte array to hold the data
		byte[] bytes = new byte[(int)length];

		// Read in the bytes
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length
				&& (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
			offset += numRead;
		}

		// Ensure all the bytes have been read in
		if (offset < bytes.length) {
			throw new IOException("Could not completely read file "+file.getName());
		}
		// Close the input stream and return bytes
		is.close();
		return bytes;
	}
	
	/**
	 * This method is used to make sure that system is currently not in the 
	 * middle of processing any ESB message.  
	 * 
	 * @return
	 */
	private boolean isESBMessageExist() {		
		File folder = new File(inFolder);	
		File[] listOfFiles = folder.listFiles(esbFileFilter);
		if (listOfFiles != null && listOfFiles.length > 0 && listOfFiles[0] != null) {
			long fileTS = listOfFiles[0].lastModified();
			long now =  Calendar.getInstance().getTime().getTime();
			// delete file if it is older than 2 hours. looks like there is some problem 
			// process ESB message lets delete the file. and move on.
			if (now - fileTS > (ONE_HOUR * 2)) {
				logger.info("Removing corrupt esb file " + listOfFiles[0].getName());
				listOfFiles[0].delete();
}
			logger.info("Not doing anything. ESB message exist:" + listOfFiles[0].getName());			
			return true;			
		}
		return false;
	}
	
	//@Override
	//protected void doStop() throws ManagedLifecycleException {
		// TODO Auto-generated method stub
		
	//}

	//@Override
	//protected void doDestroy() throws ManagedLifecycleException {
		// TODO Auto-generated method stub
		
	//}
}
