package com.servicelive.esb.actions;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.message.Message;

public class IntegrationPreMapAction extends AbstractEsbSpringAction {
	private Logger logger = Logger.getLogger(IntegrationPreMapAction.class);
	
	public Message preMap(Message message) {
		final String methodName = "preMap";
		logger.info(String.format("entering %s", methodName));
		/*
		Body body = message.getBody();
		//Capture the input file feed name
		Object fileFeedPropertyValue = message.getProperties().getProperty(MarketESBConstant.ORIGINAL_FILE_FEED_NAME);
		String inputFilefeedName = String.valueOf(fileFeedPropertyValue == null ? "" : fileFeedPropertyValue);
		
		IIntegrationBO integrationBO = null;
		try {
			integrationBO = (IIntegrationBO) getBeanFactory().getBean("integrationBO");
		} catch (ActionLifecycleException e) {
			logger.error("Exception while getting the integrationBO.", e);
		}
				
		Long batchId = null;
		try {
			batchId = integrationBO.createNewBatch(inputFilefeedName);
		} catch (Exception e1) {
			logger.error("Exception while creating the new batch record");
		}
		
		if (batchId != null) {
			message.getProperties().setProperty(MarketESBConstant.INTEGRATION_BATCH_ID, batchId);
		}
		*/
		return message;
	}

}
