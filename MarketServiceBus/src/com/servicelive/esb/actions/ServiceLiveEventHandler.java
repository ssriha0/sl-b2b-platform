/**
 * 
 */
package com.servicelive.esb.actions;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.ConfigurationException;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

/**
 * @author sahmed
 * @deprecated This class is only for demonstration purposes
 */
public class ServiceLiveEventHandler extends AbstractServiceOrderEventHandler {
	private static final Logger logger = Logger.getLogger(ServiceLiveEventHandler.class);
	
	public ServiceLiveEventHandler() {
		super();
	}
	public ServiceLiveEventHandler(ConfigTree configTree) throws ConfigurationException {
		super(configTree);
	}

	public Message displayMessage(Message message) throws ActionProcessingException {
		final String methodName = "displayMessage";
		logger.info(String.format("entered %s", methodName));

		Object messageBodyString = String.format("Body: %s", message.getBody()
				.get());
		logger.info(messageBodyString);

		logger.info(String.format("exiting %s", methodName));
		return message;
	}
	@Override
	protected Long getIntegrationId(String fileName) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected String getIntegrationName(String fileName) {
		// TODO Auto-generated method stub
		return null;
	}
}
