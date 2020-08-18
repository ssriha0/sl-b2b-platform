package com.servicelive.esb.actions;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Body;
import org.jboss.soa.esb.message.Message;

import com.newco.marketplace.translator.business.IStagingService;
import com.newco.marketplace.translator.util.SpringUtil;
import com.newco.marketplace.webservices.dao.ShcOrder;
import com.newco.marketplace.webservices.dto.StagingDetails;
import com.servicelive.esb.constant.MarketESBConstant;
import com.servicelive.esb.constant.NPSConstants;
import com.servicelive.esb.service.ExceptionHandler;

public class NPSCallCloseSchedulerAction extends AbstractEsbSpringAction {

	private Logger logger = Logger.getLogger(NPSCallCloseSchedulerAction.class);
	
	/**
	 * Retrieve the closed orders with "NPS call close" not been processed yet; put them in ESB session for subsequent action to process
	 * 
	 * @param message
	 * @return Message
	 * @throws Exception - in case of an unexpected error
	 */
	public Message retrieveClosedOrders(Message message) {
		
		logger.info("NPS Call Close Scheduler invoked");
		
		// Retrieve all unprocessed closed orders 
		IStagingService stagingService = (IStagingService) SpringUtil.factory.getBean(MarketESBConstant.SL_STAGING_SERVICE);
		ShcOrder[] closedOrders = stagingService.retrieveClosedOrders();
		
		// Store result in ESB session
		if (closedOrders.length > 0) {
			logger.info("Retrieved {" + closedOrders.length + "} unprocessed closed orders for call close process");
			StagingDetails stagingDetails = new StagingDetails();
			stagingDetails.setStageServiceOrder(Arrays.asList(closedOrders));
			Body body = message.getBody();		
			body.add(NPSConstants.CLOSED_ORDERS_MSG_KEY, stagingDetails);
		} else {
			logger.info("There are no closed orders to be processed.");
		}
		
		return message;
	}

	/* (non-Javadoc)
	 * @see org.jboss.soa.esb.actions.AbstractSpringAction#exceptionHandler(org.jboss.soa.esb.message.Message, java.lang.Throwable)
	 */
	@Override
	public void exceptionHandler(Message msg, Throwable th) {
		ExceptionHandler.handleNPSException(msg, th);
		super.exceptionHandler(msg, th);
	}

	/**
	 * Default Constructor for JUnit test cases
	 */
	public NPSCallCloseSchedulerAction() {
		logger.warn("***** This constructor is for Test Cases only *****");
	}
	
	/**
	 * The Constructor which is called by ESB runtime to configure actions
	 * @param configTree
	 */
	public NPSCallCloseSchedulerAction(ConfigTree configTree) {
		super.configTree = configTree;
	}
}
