package com.servicelive.esb.actions;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

import com.servicelive.esb.constant.MarketESBConstant;

public class AssurantRemoveLegacyOrdersAction extends AbstractEsbSpringAction {

private Logger logger = Logger.getLogger(AssurantRemoveLegacyOrdersAction.class);
	
	/**
	 * The Constructor which is called by ESB runtime to configure actions
	 * 
	 * @param config
	 */
	public AssurantRemoveLegacyOrdersAction(ConfigTree config) {
		super.configTree = config;
	}

	/**
	 * Default Constructor for JUnit test cases
	 */
	public AssurantRemoveLegacyOrdersAction() {
		logger.warn("***** This constructor is for Test Cases only *****");
	}
	
	public Message removeLegacyOrders(Message message) {
		Boolean isLegacyOrder = false;
		if (message.getProperties().getProperty(MarketESBConstant.IS_LEGACY_ORDER) != null) {
			isLegacyOrder = (Boolean) message.getProperties().getProperty(MarketESBConstant.IS_LEGACY_ORDER);
		}
		
		if (isLegacyOrder) {
			return null;
		} else {
			return message;
		}
	}

}
