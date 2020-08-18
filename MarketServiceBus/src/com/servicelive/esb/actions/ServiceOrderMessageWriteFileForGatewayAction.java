package com.servicelive.esb.actions;

import java.net.URI;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.ConfigurationException;
import org.jboss.soa.esb.actions.AbstractSpringAction;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Body;
import org.jboss.soa.esb.message.Message;

import com.servicelive.esb.constant.MarketESBConstant;
import com.servicelive.util.MarketESBRoutingUtil;

public class ServiceOrderMessageWriteFileForGatewayAction extends AbstractSpringAction {
	private Logger logger = Logger.getLogger(ServiceOrderMessageWriteFileForGatewayAction.class);
	
	private MarketESBRoutingUtil routingUtil = new MarketESBRoutingUtil();
	
	private final String INPUT_SUFFIX = "input-suffix";
	
	private String gatewayLocation = ".";
	private String gatewayInputSuffix = "";
	
	public ServiceOrderMessageWriteFileForGatewayAction() {
		super();
	}
	public ServiceOrderMessageWriteFileForGatewayAction(ConfigTree config) throws ConfigurationException { 
		super.configTree = config;
		
		this.gatewayLocation = config.getRequiredAttribute(MarketESBConstant.GATEWAY_DIR);
		if (this.gatewayLocation.endsWith("/") || this.gatewayLocation.endsWith("\\")) {
			this.gatewayLocation = this.gatewayLocation.substring(0, this.gatewayLocation.length() - 1);
		}
		
		this.gatewayInputSuffix = config.getRequiredAttribute(INPUT_SUFFIX);
	}
	
	public Message writeFileForGateway(Message message) {
		logger.info("Inside ServiceOrderMessageWriteFileForGatewayAction.writeFileForGateway() method>>>>>>>>>>>");
		
		Body body = message.getBody();
		Object xmlFeed = body.get();
		
		URI inuptmessageid =  message.getHeader().getCall().getMessageID();
		if(inuptmessageid == null ) {
			//TODO  handle the Message ID not found scenario
			/*ExceptionHandler.handle((String) message.getBody().get(MarketESBConstant.CLIENT_KEY), 
					xmlFeed.toString(), 
					inputFilefeedName,
					errorMsg, 
					 xmlFeed,
					e);*/
			return null; // This will prevent message to propogate
		}
		String inputFilefeedName = getStrippedOffIDString(inuptmessageid.toString()) + gatewayInputSuffix;
		
		writeFileForOldService(xmlFeed.toString(), inputFilefeedName);
		
		return null;
	}
	
	private void writeFileForOldService(String newXml, String fileName) {
    	try {
    		routingUtil.writeFileToNewLocation(newXml, gatewayLocation, fileName);
		} catch (Exception e) {
			logger.error("Error while generating the XML file for the OMS service.", e);
		}
    }
	
	private String getStrippedOffIDString(String originalMessageID) {
		StringBuffer sb = new StringBuffer(originalMessageID);
		int startIndex = originalMessageID.indexOf("ID:");
		sb.replace(startIndex, startIndex+ "ID:".length(), "MQID_");
		return sb.toString();
	}

}
