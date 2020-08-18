package com.servicelive.esb.actions;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Body;
import org.jboss.soa.esb.message.Message;

import com.newco.marketplace.translator.business.IStagingService;
import com.newco.marketplace.translator.util.SpringUtil;
import com.newco.marketplace.webservices.dto.StagingDetails;
import com.servicelive.esb.constant.MarketESBConstant;
import com.servicelive.esb.constant.NPSConstants;
import com.servicelive.esb.service.ExceptionHandler;

public class NPSProcessorAction extends AbstractEsbSpringAction {

	private Logger logger = Logger.getLogger(NPSProcessorAction.class);
	
	/**
	 * Method to process the closed orders in staging
	 * @param Message message
	 * @return message
	 * @throws Exception
	 */
	public Message processClosedOrders(Message message) {				
		
		// Get the staging details object(closed orders list) and generated nps call close file name from ESB session
		Body body = message.getBody();
		StagingDetails stagingDetails = (StagingDetails) body.get(NPSConstants.CLOSED_ORDERS_MSG_KEY);
		String npsCallCloseFileName = (String) body.get(NPSConstants.CALL_CLOSE_FILE_NAME_MSG_KEY);
		if (null == stagingDetails || null == stagingDetails.getStageServiceOrder() || stagingDetails.getStageServiceOrder().size() == 0 ||
				null == npsCallCloseFileName) {
			return message;
		}
		
		// Populate staging details for closed orders
		IStagingService stagingService = (IStagingService) SpringUtil.factory.getBean(MarketESBConstant.SL_STAGING_SERVICE);
		int npsProcessId = stagingService.createNPSProcessLog(stagingDetails, npsCallCloseFileName);
		logger.info("Process log successfully created, closed orders updated with new process id = {" + npsProcessId + "}");	
		
		if (npsProcessId > 0) {
			body.add(NPSConstants.NPS_PROCESS_ID_MSG_KEY, npsProcessId);
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
	 * The Constructor which is called by ESB runtime to configure actions
	 * @param config
	 */
	public NPSProcessorAction(ConfigTree config) {
		super.configTree = config;
	}
	
	/**
	 * Default Constructor for JUnit test cases
	 */
	public NPSProcessorAction() {
		logger.warn("***** This constructor is for Test Cases only *****");
	}
}
