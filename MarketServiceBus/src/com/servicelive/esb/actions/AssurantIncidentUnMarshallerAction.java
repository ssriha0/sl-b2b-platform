package com.servicelive.esb.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jboss.soa.esb.actions.AbstractSpringAction;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Body;
import org.jboss.soa.esb.message.Message;

import com.newco.marketplace.translator.business.IApplicationPropertiesService;
import com.newco.marketplace.translator.business.IClientService;
import com.newco.marketplace.translator.business.IIncidentService;
import com.newco.marketplace.translator.dao.Client;
import com.newco.marketplace.translator.dao.Incident;
import com.newco.marketplace.translator.dao.IncidentAck;
import com.newco.marketplace.translator.dao.IncidentEvent;
import com.newco.marketplace.translator.dao.IncidentNote;
import com.newco.marketplace.translator.util.Constants;
import com.newco.marketplace.translator.util.SpringUtil;
import com.servicelive.esb.constant.AssurantIncidentConstants;
import com.servicelive.esb.constant.MarketESBConstant;
import com.servicelive.esb.integration.bo.IIntegrationBO;
import com.servicelive.esb.mapper.AssurantCancelMapper;
import com.servicelive.esb.mapper.AssurantIncidentAckMapper;
import com.servicelive.esb.mapper.AssurantIncidentEventMapper;
import com.servicelive.esb.mapper.AssurantIncidentNoteMapper;
import com.servicelive.esb.mapper.Mapper;
import com.servicelive.esb.service.ExceptionHandler;
import com.servicelive.util.MarketESBRoutingUtil;

/**
 * An ESB Action responsible for reading Assurant String Payload from the input
 * file feed to unmarshal it into a java object graph and set it into the
 * message body for other actions to use.
 * 
 * @author gjackson
 * 
 */

public class AssurantIncidentUnMarshallerAction extends AbstractSpringAction {

	private Logger logger = Logger
			.getLogger(AssurantIncidentUnMarshallerAction.class);

	public Mapper incidentMapper = new AssurantIncidentEventMapper();
	public Mapper incidentNoteMapper = new AssurantIncidentNoteMapper();
	public Mapper incidentCancelMapper = new AssurantCancelMapper();
	public Mapper incidentAckMapper = new AssurantIncidentAckMapper();
	
	private IIncidentService incidentService;
	private IIntegrationBO integrationBO;
	private IClientService clientService;
	
	private MarketESBRoutingUtil routingUtil = new MarketESBRoutingUtil();

	/** Message property name for original filename */

	/**
	 * Method to process the message that contains the XML Payload
	 * 
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public Message unmarshalPayload(Message message) throws Exception {

		// Please do not remove following lines because they are required for debugging locally
		//Constants.MARKET_WS_URL = "http://localhost:8080/marketws/services/ServiceOrderSEI";
		Constants.MARKET_WS_URL = ((IApplicationPropertiesService)SpringUtil.factory.getBean("ApplicationPropertiesService")).getServiceLiveEndpoint();
		
		Object payload = message.getBody().get(Body.DEFAULT_LOCATION);
		byte[] bytes = (byte[]) payload;
		String incidentString = new String(bytes);

		// Capture the input file feed name
		Object fileFeedPropertyValue = message.getProperties().getProperty(
				MarketESBConstant.ORIGINAL_FILE_FEED_NAME);
		Object fileFeedEntryTime1 = message.getProperties().getProperty(MarketESBConstant.ORIGINAL_FILE_ENTRY_TIME);
		
        String inputFilefeedName = String
				.valueOf(fileFeedPropertyValue == null ? ""
						: fileFeedPropertyValue);
		logger.info("\n*********** Assurant file feed: \""
				+ inputFilefeedName + "\""
        		+"\n*********** File time is: "
        		+ fileFeedEntryTime1.toString()
        		+"\n*********** File contents are: "
        		+ incidentString);
		
		boolean maintainLegacyStaging = routingUtil.isMaintainLegacyStagingData();
		
		boolean testMode = routingUtil.isTestMode();
		String testSuffix = "";
		String testSuffixNew = "";
		String testSuffixOld = "";
		String testAddressSuffix = "";
		if (testMode) {
			testSuffix = routingUtil.generateRandomTestSuffix();
			testSuffixNew = "new";
			testSuffixOld = "old";
			testAddressSuffix = "xby";
			incidentString = replaceOrderNumberAndAddressForTestMode(incidentString, testSuffix, testAddressSuffix, testSuffixNew, testSuffixOld);
		}
		
		if (sendToNewOFProcess(incidentString, inputFilefeedName, testMode, testSuffix, testAddressSuffix, testSuffixNew, testSuffixOld)) {
						
			if (testMode) {
				if (maintainLegacyStaging) {
					// Maintain Legacy Staging mode trumps Test mode (for now), so let's set the values back to the same values that were sent to the
					// new pipeline, and send it on as a legacy order.
					// If Legacy Staging mode should not trump Test Mode, then take out all the code in this if-block.
					
					// This replaces the old suffix with the new suffix for External Order Number
					incidentString = incidentString.replaceFirst(testSuffixOld, testSuffixNew);
					// This replaces the old suffix with the new suffix for Street Address Line 1
					incidentString = incidentString.replaceFirst(testAddressSuffix + testSuffixOld, testAddressSuffix + testSuffixNew);
					
					message.getProperties().setProperty(MarketESBConstant.IS_LEGACY_ORDER, Boolean.TRUE);
				}
			} else {
				if (maintainLegacyStaging) {
					// If we are maintaining legacy staging data, then we need to continue with this pipeline, but add a flag that 
					// sets this as a legacy order so the draft is not created.
					message.getProperties().setProperty(MarketESBConstant.IS_LEGACY_ORDER, Boolean.TRUE);
				} else {
					// This prevents the pipeline from continuing.  Only do this if we are not in Test mode or in Maintain Legacy Staging mode. 
					return null;
				}
			}
		} 

		try {
			Body body = message.getBody();
			if (incidentString.indexOf(MarketESBConstant.ASSURANT_ACK_KEY) == 0) {
				processAckFile(message, incidentString, inputFilefeedName);
			}
			// files will be incident events or info events the first field will
			// be the key for which
			// parser to use
			else if (incidentString.indexOf(MarketESBConstant.ASSURANT_INCIDENT_NOTE_KEY) == 0) {
				IncidentNote incidentNote = (IncidentNote) incidentNoteMapper.mapData(incidentString);
				body.add(MarketESBConstant.UNMARSHALLED_OBJ_GRAPH_NOTE, incidentNote);
				logger.info("Unmarshalled the incident note: " + incidentNote.getIncident().getClientIncidentID() + " from the string payload and set into message body...");
			}
			// I had 1 hour to complete and test this code - yes it sucks, but
			// Greg says it's ok ;)
			else if (incidentString.indexOf(MarketESBConstant.ASSURANT_INCIDENT_CANCEL_KEY) == 0) {
				String[] cancelFields = StringUtils.splitPreserveAllTokens(incidentString, '~');
				IIncidentService incidentBO = (IIncidentService) SpringUtil.factory.getBean("IncidentService");
				IClientService clientService = (IClientService) SpringUtil.factory.getBean("ClientService");
				Incident incident = incidentBO.getIncidentByClientIncident(cancelFields[AssurantIncidentConstants.NOTE_INCIDENT_ID], clientService.getClient(MarketESBConstant.Client.ASSURANT));
				if (null != incident) {
					IncidentEvent incidentEvent = null;
					Date lastCreatedDate = null;
					for (IncidentEvent ievent : incident.getIncidentEvents()) {
						if (null != ievent.getCreatedDate() && (null == lastCreatedDate || lastCreatedDate.before(ievent.getCreatedDate()))) {
							incidentEvent = ievent;
							lastCreatedDate = ievent.getCreatedDate();
						}
					}
					incidentEvent.setStatus(MarketESBConstant.ASSURANT_INCIDENT_CANCEL_KEY);
					body.add(MarketESBConstant.UNMARSHALLED_OBJ_GRAPH, incidentEvent);
					logger.info("Unmarshalled the incident cancellation: " + incidentEvent.getIncident().getClientIncidentID() + " from the string payload and set into message body...");
					incidentCancelMapper.mapData(incidentString);
				} else {
					logger.error("Error on cancel, incident string:" + incidentString);
					ExceptionHandler.handleInvalidPayload(MarketESBConstant.Client.ASSURANT, incidentString, inputFilefeedName);
				}
			} else {
				// Unmarshal the input string payload from the file feed
				IncidentEvent incident = (IncidentEvent) incidentMapper.mapData(incidentString);
				// Set the unmarshalled object onto message body with a key
				body.add(MarketESBConstant.UNMARSHALLED_OBJ_GRAPH, incident);
				logger.info("Unmarshalled the incident: " + incident.getIncident().getClientIncidentID() + " from the string payload and set into message body...");
			}
			// Capture the input file feed name for future use (in event of an
			// error)
			body.add(MarketESBConstant.FILE_FEED_NAME, inputFilefeedName);

			// Add the client key to the message to drive specific translation
			// and mapping rules
			body.add(MarketESBConstant.CLIENT_KEY, MarketESBConstant.Client.ASSURANT);
		} catch (Exception e) {
			logger.error("Error while parsing the given file feed - " + inputFilefeedName);
			ExceptionHandler.handle(MarketESBConstant.Client.ASSURANT, incidentString, inputFilefeedName, getClass().getName() + " reports: File cannot be parsed", incidentString);
			throw e;
		}
		return message;
		
	}

	private String replaceOrderNumberAndAddressForTestMode(String incidentString,
			String testSuffix, String testAddressSuffix, String testSuffixNew, String testSuffixOld) {
		
		String newIncidentString = incidentString;
		String[] incidentArray = incidentString.split("~");
		
		if (incidentArray.length > 1) { 
			String externalOrderId = incidentArray[1];
			newIncidentString = incidentString.replaceFirst(externalOrderId, externalOrderId + testSuffixOld + testSuffix);
		}
		
		if (incidentArray.length > 6) {
	
			String address = incidentArray[6];
			if (address.contains(testAddressSuffix)) {
				if (address.contains(testAddressSuffix + testSuffixNew)) {
					// Make sure the last name has the old suffix.  The new suffix will be substituted in for the new pipeline later.
					newIncidentString = newIncidentString.replaceFirst(testAddressSuffix + testSuffixNew, testAddressSuffix + testSuffixOld);
				}
			} else {
				newIncidentString = newIncidentString.replaceFirst(address, address + testAddressSuffix + testSuffixOld + testSuffix);
			}
		}
		 
		return newIncidentString;
	}

	private boolean sendToNewOFProcess(String incidentString, String fileName, 
			boolean testMode, String testSuffix, String testAddressSuffix, String testSuffixNew, String testSuffixOld) {
		
		if (!routingUtil.isOrderFulfillmentEnabled() && !testMode) {
			return false;
		}
		
		String[] incidentArray = incidentString.split("~");
		String status = incidentArray[0];
		
		if (testMode) {
			// This replaces the old suffix with the new suffix for External Order Number
			incidentString = incidentString.replaceFirst(testSuffixOld, testSuffixNew);
			// This replaces the old suffix with the new suffix for Street Address Line 1
			incidentString = incidentString.replaceFirst(testAddressSuffix + testSuffixOld, testAddressSuffix + testSuffixNew);			
		}
		
		if (status.equalsIgnoreCase("new")) {
			// If this is a new order, first check if order fulfillment is allowed to be run on new orders
			if (!(routingUtil.isOrderFulfillmentAllowedForNewOrders() && routingUtil.isOrderFulfillmentAllowedForNewAssurantOrders()) && !testMode) {
				return false;
			} 
		} else {
			// If this is not a NEW transaction, then we just need to see if this order is already in our transaction table
			// or it doesn't already exist in the legacy staging, in which case it should also go to the new staging since it's
			// an out of order non-NEW transaction
			String externalOrderId = "";
			
			List<String> newStagingLookupResults = new ArrayList<String>();
			List<Incident> legacyStagingLookupResults = new ArrayList<Incident>();
			Client client = clientService.getClient(MarketESBConstant.Client.ASSURANT);
			if (testMode) {
				List<String> orderLookupIds = new ArrayList<String>();
				externalOrderId = incidentArray[1];
				int oldSuffixIndex = externalOrderId.indexOf(testSuffixOld);
				externalOrderId = externalOrderId.substring(0, oldSuffixIndex);
				orderLookupIds.add(externalOrderId);
				newStagingLookupResults = integrationBO.getLatestExistingExternalServiceOrderNumbersThatMatchBeforeTestSuffix(orderLookupIds, testSuffixNew);
//				legacyStagingOrders = stagingService.findLatestShcOrdersWithOrderNumberMatchingBeforeTestSuffix(orderLookupIds, testSuffixNew);
				legacyStagingLookupResults = incidentService.findLatestIncidentWithClientIncidentIdMatchingBeforeTestSuffix(client, orderLookupIds, testSuffixNew);
			} else {
				List<String> orderLookupIds = new ArrayList<String>();
				externalOrderId = incidentArray[1];
				orderLookupIds.add(externalOrderId);
				newStagingLookupResults = integrationBO.getExistingExternalServiceOrdersIds(orderLookupIds);
				legacyStagingLookupResults = incidentService.findAllIncidents(client, orderLookupIds);
			}
			
			// lookup in new staging didn't produce a match, but lookup in legacy staging did
			if (newStagingLookupResults.isEmpty() && !legacyStagingLookupResults.isEmpty()) {
				return false;
			} else {
				if (testMode) {
					incidentString = incidentString.replaceFirst(externalOrderId + testSuffixNew + testSuffix, newStagingLookupResults.get(0));
				}
			} 
		}
		
		// If we made it this far, then this order needs to go to the new OF process, so 
		// Write the file to the new process's input folder
    	try {
			routingUtil.writeFileForNewService(MarketESBConstant.Client.ASSURANT, incidentString, fileName);
		} catch (Exception e) {
			logger.error("Error while generating the input file for the new Assurant process.", e);
		}
		return true;
	}
	
	

	/**
	 * @param message
	 * @param incidentString
	 * @param inputFilefeedName
	 * @throws Exception
	 */
	private void processAckFile(Message message, String incidentString, String inputFilefeedName) throws Exception {
		Object fileFeedEntryTime = message.getProperties().getProperty(MarketESBConstant.ORIGINAL_FILE_ENTRY_TIME);
		Hashtable<String,String> ackInfo = new Hashtable<String,String>();
		ackInfo.put(MarketESBConstant.INCIDENT_STRING,incidentString);
		ackInfo.put(MarketESBConstant.FILE_FEED_NAME, inputFilefeedName);
		ackInfo.put(MarketESBConstant.FILE_FEED_TIME, fileFeedEntryTime.toString());

		IncidentAck incidentAck = (IncidentAck) incidentAckMapper.mapData(ackInfo);
		message.getBody().add(MarketESBConstant.UNMARSHALLED_OBJ_GRAPH_ACK, incidentAck);
		if (incidentAck != null && incidentAck.getIncident() != null) {
			logger.info("Unmarshalled the incident acknowledgement for incident " + incidentAck.getIncident().getClientIncidentID() + " from the string payload and set into message body...");
		} else {
			logger.info("Unmarshalled incident acknowledgement...");
		}
		
	}

	/**
	 * The Constructor which is called by ESB runtime to configure actions
	 * 
	 * @param config
	 */
	public AssurantIncidentUnMarshallerAction(ConfigTree config) {
		super.configTree = config;
		init();
	}

	/**
	 * Default Constructor for JUnit test cases
	 */
	public AssurantIncidentUnMarshallerAction() {
		logger.warn("***** This constructor is for Test Cases only *****");
	}

	public Mapper getIncidentMapper() {
		return incidentMapper;
	}

	public void setIncidentMapper(Mapper incidentMapper) {
		this.incidentMapper = incidentMapper;
	}
	
	private void init() {
		if (this.incidentService == null) {
			this.incidentService = (IIncidentService) SpringUtil.factory.getBean("IncidentService");
		}
		
		if (this.integrationBO == null) {
			this.integrationBO = (IIntegrationBO)SpringUtil.factory.getBean("integrationBO");
		}
		
		if (this.clientService == null) {
			this.clientService = (IClientService) SpringUtil.factory.getBean("ClientService");
		}
	}
}
