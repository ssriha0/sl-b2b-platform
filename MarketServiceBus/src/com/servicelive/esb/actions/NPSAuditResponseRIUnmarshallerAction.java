package com.servicelive.esb.actions;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Body;
import org.jboss.soa.esb.message.Message;

import com.newco.marketplace.translator.business.IStagingService;
import com.newco.marketplace.translator.util.SpringUtil;
import com.newco.marketplace.webservices.dao.ShcOrder;
import com.servicelive.esb.constant.MarketESBConstant;
import com.servicelive.esb.dto.NPSAuditMessages;
import com.servicelive.esb.dto.NPSAuditRecord;
import com.servicelive.esb.dto.NPSSalesCheck;
import com.servicelive.esb.dto.NPSServiceAudit;
import com.servicelive.esb.integration.domain.IntegrationName;
import com.servicelive.esb.service.ExceptionHandler;
import com.servicelive.util.MarketESBRoutingUtil;
import com.thoughtworks.xstream.XStream;



public class NPSAuditResponseRIUnmarshallerAction extends AbstractIntegrationSpringAction{
	
	private Logger logger = Logger.getLogger(NPSAuditResponseRIUnmarshallerAction.class);
	
	private MarketESBRoutingUtil routingUtil = new MarketESBRoutingUtil();
	private IStagingService stagingService;
	
	public static final Class<?>[] classes = new Class[]{NPSServiceAudit.class, NPSAuditRecord.class,
		                           NPSAuditMessages.class, NPSSalesCheck.class};
	
	public Message getOrdersForAudit(Message message){
		
		logger.info("in NPSAuditResponseRIUnmarshallerAction ..");
				
		Body body = message.getBody();
		Object xmlFeed = body.get();
		
		//Capture the input file feed name
		Object fileFeedPropertyValue = message.getProperties().getProperty(MarketESBConstant.ORIGINAL_FILE_FEED_NAME);
		String inputFilefeedName = String.valueOf(fileFeedPropertyValue == null ? "" : fileFeedPropertyValue);
		//logger.info("*********** Processing the file feed : \""+inputFilefeedName+"\"");
				
		ByteArrayInputStream inputStream = null;
		NPSServiceAudit serviceAudit = null;
		XStream xStream = new XStream();
		
		try{
			//Capture the input file feed name for future use (in event of an error)
			body.add(MarketESBConstant.FILE_FEED_NAME, inputFilefeedName);			
			//Add the client key to the message to drive specific translation and mapping rules
			body.add(MarketESBConstant.CLIENT_KEY, MarketESBConstant.Client.NPS_RI_AUDIT);
			
			xStream.processAnnotations(classes);
			inputStream = new ByteArrayInputStream((byte[])xmlFeed);
			
			serviceAudit = (NPSServiceAudit)xStream.fromXML(inputStream);
			logger.info("Unmarshalled Audit file");
						
		}catch(Exception e) {
			String errorMsg = "Exception caught UnMarshalling NPS Audit file: " + inputFilefeedName;
			logger.error( errorMsg );
			ExceptionHandler.handle((String) message.getBody().get(MarketESBConstant.CLIENT_KEY), 
					new String((byte[]) message.getBody().get()), 
					inputFilefeedName,
					errorMsg, 
					new String((byte[]) xmlFeed),
					e);
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException ioEx) {
				logger.fatal("Could not close the byte array input stream", ioEx);
			}
		}
		
		List<String> legacyOrders = new ArrayList<String>();
		removeNewOrdersAndCallNewService(serviceAudit, inputFilefeedName, legacyOrders);		
		
		
		message.getBody().add(MarketESBConstant.UNMARSHALLED_NPS_AUDIT_OBJ,serviceAudit);
		message.getProperties().setProperty(MarketESBConstant.LEGACY_ORDERS, legacyOrders);
		
		return message;
		
	}
	
	private void removeNewOrdersAndCallNewService(
			NPSServiceAudit serviceAudit, String fileName, List<String> legacyOrders) {
		List<NPSAuditRecord> auditRecordList = serviceAudit.getAuditRecords();
		List<NPSAuditRecord> newAuditRecordList = new ArrayList<NPSAuditRecord>();
		
		boolean maintainLegacyStaging = routingUtil.isMaintainLegacyStagingData();
		
		// First loop through all the orders and create a list of all the order numbers
		List<String> orderLookupIds = new ArrayList<String>();
		for (NPSAuditRecord auditRecord : auditRecordList) {
			orderLookupIds.add(getExternalOrderId(auditRecord));
		}
		
		// Next lookup these orders in the integration DB to determine which of them have been created through the new
		// OF process
		List<String> newStagingLookupResults = new ArrayList<String>();
		List<String> legacyStagingLookupResults = new ArrayList<String>();
		if (orderLookupIds.size() > 0) {
			ShcOrder[] legacyStagingOrders = null;
			
			newStagingLookupResults = this.getIntegrationServiceCoordinator().getIntegrationBO().getExistingExternalServiceOrdersIds(orderLookupIds);
			legacyStagingOrders = stagingService.findShcOrders(orderLookupIds);
			
			for (ShcOrder eachLegacyStagingOrder : legacyStagingOrders) {
				legacyStagingLookupResults.add(String.format("%s%s", eachLegacyStagingOrder.getUnitNo(), eachLegacyStagingOrder.getOrderNo()));
			}
		}
				
		// Add all orders that meet the criteria to be sent to the new OF process to the 'new' list, and remove them from the 'old list' 
		for (Iterator<NPSAuditRecord> i = auditRecordList.iterator(); i.hasNext(); ) {
			NPSAuditRecord auditRecord = i.next();
			
			if (isSendToNewOFProcess(auditRecord, newStagingLookupResults, legacyStagingLookupResults)) {
				
				if (maintainLegacyStaging) {
					// If we are maintaining legacy staging data, do not remove the item from the list for the old pipeline,
					// but add a record to the legacy list so we can stop the legacy records from going all the way through
					// the old order fulfillment process.
					legacyOrders.add(getExternalOrderId(auditRecord));
					
				} else {
					i.remove();
				}
				newAuditRecordList.add(auditRecord);
			}
		}
		
		// Finally write the 'new' list to an XML file that will be picked up by the new OF process
		if (newAuditRecordList.size() > 0) {
			NPSServiceAudit newServiceAudit = new NPSServiceAudit();
			newServiceAudit.setAuditRecords(newAuditRecordList);
			
			String newXml = marshalAction(newServiceAudit);
			writeFileForNewService(newXml, fileName);
		}
		
	}
	
	private void writeFileForNewService(String newXml, String fileName) {
    	try {
			routingUtil.writeFileForNewService(MarketESBConstant.Client.NPS_RI_AUDIT, newXml, fileName);
		} catch (Exception e) {
			logger.error("Error while generating the XML file for the new OMS service.", e);
		}
    }
	
	private String marshalAction(NPSServiceAudit serviceAudit) {
		XStream xstream = new XStream();
		xstream.processAnnotations(NPSAuditResponseRIUnmarshallerAction.classes);
		String xml = xstream.toXML(serviceAudit);

		if (logger.isDebugEnabled())
		  logger.debug("Marshalled the xml payload for the new service from NPSServiceAudit obj="+serviceAudit);
		
		return xml;
		
	}
	
	private boolean isSendToNewOFProcess(NPSAuditRecord auditRecord, List<String> newStagingLookupResults, List<String> legacyStagingLookupResults) {
		// send to new staging if order already exists in new staging, or else if order doesn't already exist in legacy staging
		String externalOrderId = getExternalOrderId(auditRecord);
		boolean newStagingContainsOrder = newStagingLookupResults.contains(externalOrderId);
		boolean legacyStagingContainsOrder = legacyStagingLookupResults.contains(externalOrderId);
		return newStagingContainsOrder || !legacyStagingContainsOrder;
    }
	
	private String getExternalOrderId(NPSAuditRecord auditRecord) {
		return auditRecord.getServiceUnitNumber() + auditRecord.getServiceOrderNumber();
	}

	/**
	 * Default Constructor for JUnit test cases
	 */
	public NPSAuditResponseRIUnmarshallerAction() {
		
	}
	
	/**
	 * The Constructor which is called by ESB runtime to configure actions
	 * @param configTree
	 */
	public NPSAuditResponseRIUnmarshallerAction(ConfigTree configTree) {
		super.configTree = configTree;
		
		stagingService = (IStagingService) SpringUtil.factory.getBean(MarketESBConstant.SL_STAGING_SERVICE);
		
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.soa.esb.actions.AbstractSpringAction#exceptionHandler(org.jboss.soa.esb.message.Message, java.lang.Throwable)
	 */
	@Override
	public void exceptionHandler(Message msg, Throwable th) {
		ExceptionHandler.handleNPSException(msg, th);
		super.exceptionHandler(msg, th);
	}

	@Override
	protected Long getIntegrationId(String fileName) {
		return IntegrationName.NPS_CLOSE_AUDIT.getId();
	}

	@Override
	protected String getIntegrationName(String fileName) {
		return IntegrationName.NPS_CLOSE_AUDIT.name();
	}

}
