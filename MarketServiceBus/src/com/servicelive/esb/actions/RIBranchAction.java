package com.servicelive.esb.actions;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.ConfigurationException;
import org.jboss.soa.esb.actions.ActionLifecycleException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.listeners.ListenerTagNames;
import org.jboss.soa.esb.message.Message;

import com.newco.marketplace.translator.business.IStagingService;
import com.newco.marketplace.webservices.dao.ShcOrder;
import com.servicelive.esb.constant.MarketESBConstant;
import com.servicelive.esb.dto.Address;
import com.servicelive.esb.dto.JobCode;
import com.servicelive.esb.dto.JobCodes;
import com.servicelive.esb.dto.Logistics;
import com.servicelive.esb.dto.LogisticsMerchandise;
import com.servicelive.esb.dto.LogisticsOrder;
import com.servicelive.esb.dto.Merchandise;
import com.servicelive.esb.dto.Messages;
import com.servicelive.esb.dto.RepairLocation;
import com.servicelive.esb.dto.SalesCheck;
import com.servicelive.esb.dto.SalesCheckItem;
import com.servicelive.esb.dto.SalesCheckItems;
import com.servicelive.esb.dto.ServiceOrder;
import com.servicelive.esb.dto.ServiceOrderCustomer;
import com.servicelive.esb.dto.ServiceOrders;
import com.servicelive.esb.integration.domain.IntegrationName;
import com.servicelive.util.MarketESBRoutingUtil;
import com.servicelive.util.RolloutUnits;
import com.thoughtworks.xstream.XStream;

public class RIBranchAction extends AbstractIntegrationSpringAction {
	private Logger logger = Logger.getLogger(RIBranchAction.class);
	
	public static final Class<?>[] classes = new Class[] { Address.class, JobCode.class, JobCodes.class, 
		Logistics.class, LogisticsOrder.class,
		LogisticsMerchandise.class,
		Merchandise.class, Messages.class,
		RepairLocation.class,
		ServiceOrders.class, ServiceOrder.class,
		ServiceOrderCustomer.class, SalesCheck.class,
		SalesCheckItem.class, SalesCheckItems.class };
		
	private MarketESBRoutingUtil routingUtil = new MarketESBRoutingUtil();
	private IStagingService stagingService;
	
	private String inputFileSuffix = "";
	
	public RIBranchAction() {
		super();
	}
	public RIBranchAction(ConfigTree config) throws ConfigurationException { 
		super.configTree = config;
		
		this.inputFileSuffix = config.getRequiredAttribute(ListenerTagNames.FILE_INPUT_SFX_TAG);
		if (this.inputFileSuffix.startsWith(".")) this.inputFileSuffix = this.inputFileSuffix.substring(1);
	}
	
	@Override
	public void initialise() throws ActionLifecycleException {
		final String methodName = "initialise";
		logger.info(String.format("Entered %s", methodName));
	
		super.initialise();
		if (this.stagingService == null) {
			this.stagingService = (IStagingService) super.getBeanFactory().getBean(MarketESBConstant.SL_STAGING_SERVICE);
		}
		
		logger.info(String.format("Exiting %s", methodName));
	}
	
	
	public Message branch(Message message) {
		logger.info("Inside RIBranchAction.branch() method>>>>>>>>>>>");
		ServiceOrders serviceOrders = (ServiceOrders) message.getBody().get(MarketESBConstant.UNMARSHALLED_OBJ_GRAPH);
		String fileName = 	(String)message.getBody().get(MarketESBConstant.FILE_FEED_NAME);
		
		List<String> legacyOrders = new ArrayList<String>();
		if (routingUtil.isOrderFulfillmentEnabled()) {
			removeNewOrdersAndCallNewService(serviceOrders, fileName, legacyOrders);
		}
		
		if (serviceOrders.getServiceOrders().size() > 0) {
			message.getBody().add(MarketESBConstant.UNMARSHALLED_OBJ_GRAPH, serviceOrders);
			message.getProperties().setProperty(MarketESBConstant.LEGACY_ORDERS, legacyOrders);
			return message;
		} else {
			return null;
		}	
	}
	
	private void removeNewOrdersAndCallNewService(
			ServiceOrders serviceOrders, String fileName, List<String> legacyOrders) {
		List<ServiceOrder> serviceOrderList = serviceOrders.getServiceOrders();
		List<ServiceOrder> newServiceOrderList = new ArrayList<ServiceOrder>();
		
		boolean maintainLegacyStaging = routingUtil.isMaintainLegacyStagingData();
		boolean allowNewOFProcessForNewOrders = routingUtil.isOrderFulfillmentAllowedForNewOrders() 
				&& routingUtil.isOrderFulfillmentAllowedForNewOMSOrders();
		
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
			
			newStagingLookupResults = getIntegrationServiceCoordinator().getIntegrationBO().getExistingExternalServiceOrdersIds(orderLookupIds);
			legacyStagingOrders = stagingService.findShcOrders(orderLookupIds);
			
			
			for (ShcOrder eachLegacyStagingOrder : legacyStagingOrders) {
				legacyStagingLookupResults.add(String.format("%s%s", eachLegacyStagingOrder.getUnitNo(), eachLegacyStagingOrder.getOrderNo()));
			}
		}
				
		RolloutUnits rolloutUnits = null;
		
		
		if (allowNewOFProcessForNewOrders) {
			rolloutUnits = routingUtil.getRolloutUnits();
		}
		
		// Add all orders that meet the criteria to be sent to the new OF process to the 'new' list, and remove them from the 'old list' 
		for (Iterator<ServiceOrder> i = serviceOrderList.iterator(); i.hasNext(); ) {
			ServiceOrder serviceOrder = i.next();
			
			if (isSendToNewOFProcess(serviceOrder, newStagingLookupResults, legacyStagingLookupResults, 
					allowNewOFProcessForNewOrders, rolloutUnits)) {
				
				
					if (maintainLegacyStaging) {
						// If we are maintaining legacy staging data, do not remove the item from the list for the old pipeline,
						// but add a record to the legacy list so we can stop the legacy records from going all the way through
						// the old order fulfillment process.
						legacyOrders.add(serviceOrder.getUniqueKey());
						
					} else {
						i.remove();
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
	
	private void writeFileForNewService(String newXml, String fileName) {
    	try {
    		StringBuilder fileNameBuilder = new StringBuilder();
    		
    		// prepend timestamp to filename
    		Calendar currentTime = Calendar.getInstance();
    		DecimalFormat twoDigitFormatter = new DecimalFormat("00");
    		DecimalFormat threeDigitFormatter = new DecimalFormat("000");
    		
    		String timeStamp = twoDigitFormatter.format(currentTime.get(Calendar.HOUR_OF_DAY)) +
				twoDigitFormatter.format(currentTime.get(Calendar.MINUTE)) +
				twoDigitFormatter.format(currentTime.get(Calendar.SECOND)) +
				threeDigitFormatter.format(currentTime.get(Calendar.MILLISECOND));
    		fileNameBuilder.append(timeStamp);
    		fileNameBuilder.append("-");
    		
    		fileNameBuilder.append(fileName);
    		    		
    		if (!fileName.endsWith(inputFileSuffix)) {
    			fileNameBuilder.append(".");
    			fileNameBuilder.append(inputFileSuffix);
    		}
			routingUtil.writeFileForNewService(MarketESBConstant.Client.OMS, newXml, fileNameBuilder.toString());
		} catch (Exception e) {
			logger.error("Error while generating the XML file for the new OMS service.", e);
		}
    }
	
	private boolean isSendToNewOFProcess(ServiceOrder serviceOrder, List<String> newStagingLookupResults, List<String> legacyStagingLookupResults, 
			boolean allowNewOFProcessForNewOrders, RolloutUnits rollOutUnits) {
		
		if (!isNewTransaction(serviceOrder)) {
			// If this is an Update or Cancel transaction, then only send it to the new OF Process if we found that order number
			// in the list of previously processed orders
			// if the external order number is not found in legacy staging, send to new OF process
			// as this will be an out-of-order update
			
			// send to new staging if order already exists in new staging, or else if order doesn't already exist in legacy staging and roll-out conditions for new staging are satisfied
			String externalOrderId = getExternalOrderId(serviceOrder);
			boolean newStagingContainsOrder = newStagingLookupResults.contains(externalOrderId);
			boolean legacyStagingContainsOrder = legacyStagingLookupResults.contains(externalOrderId);
			return newStagingContainsOrder || (!legacyStagingContainsOrder && allowNewOFProcessForNewOrders && checkRollOutCondition(serviceOrder, rollOutUnits));
		} else if (allowNewOFProcessForNewOrders) {
			// If this is a New transaction, then check the roll-out conditions
			return checkRollOutCondition(serviceOrder, rollOutUnits);
		} else {
			// If this is a New transaction, but we are not allowing new orders to be processed through the new OF, then return False
			return false;
		}
    }
	
	private String marshalAction(ServiceOrders serviceOrders) {
		XStream xstream = new XStream();
		xstream.processAnnotations(RIBranchAction.classes);
		String xml = xstream.toXML(serviceOrders);

		if (logger.isDebugEnabled())
		  logger.debug("Marshalled the xml payload for the new service from ServiceOrders obj="+serviceOrders);
		
		return xml;
		
	}
	
	private boolean checkRollOutCondition(ServiceOrder serviceOrder, RolloutUnits rollOutUnits) {
		String unitNumber = routingUtil.removeLeading0sFromUnitNumber(serviceOrder.getServiceUnitNumber());
		return rollOutUnits.containsUnit(unitNumber);
	}
	
	private boolean isNewTransaction(ServiceOrder serviceOrder) {
		return serviceOrder.getTransactionType().equalsIgnoreCase("NEW");
	}
	
	private String getExternalOrderId(ServiceOrder serviceOrder) {
		return serviceOrder.getServiceUnitNumber() + serviceOrder.getServiceOrderNumber();
	}
	
	
	
	
	

	@Override
	protected Long getIntegrationId(String fileName) {
		return IntegrationName.RI_INBOUND.getId();
	}

	@Override
	protected String getIntegrationName(String fileName) {
		return IntegrationName.RI_INBOUND.name();
	}
}
