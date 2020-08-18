/**
 * 
 */
package com.servicelive.esb.listeners;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.ConfigurationException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.listeners.ScheduledEventMessageComposer;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.format.MessageFactory;
import org.jboss.soa.esb.schedule.SchedulingException;

/**
 * @author <a href="mailto:slbuyeradmin@gmail.com">slbuyeradmin@gmail.com</a>
 *
 */
public class DefaultScheduledEventMessageComposer implements
		ScheduledEventMessageComposer {
	
	private static final Logger logger = Logger.getLogger(DefaultScheduledEventMessageComposer.class);

	/**
	 * Returns a default message object
	 * 
	 * @return a default message object
	 */
	public Message composeMessage() throws SchedulingException {
		final String methodName = "composeMessage";
		logger.info(String.format("Entered %s", methodName));
		
		Message message = MessageFactory.getInstance().getMessage();

		logger.info(String.format("Exiting %s", methodName));
		return message;
	}

	public Message onProcessingComplete(Message message)
			throws SchedulingException {
		final String methodName = "onProcessingComplete";
		logger.info(String.format("Entered %s", methodName));
		
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Received config parameter: %s", message.toString()));
		}

		logger.info(String.format("Exiting %s", methodName));
		return message;
	}

	public void initialize(ConfigTree config) throws ConfigurationException {
		final String methodName = "initialize";
		logger.info(String.format("Entered %s", methodName));
		
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Received config parameter: %s", config.toString()));
		}

		logger.info(String.format("Exiting %s", methodName));
	}

	public void uninitialize() {
		final String methodName = "uninitialize";
		logger.info(String.format("Entered %s", methodName));

		logger.info(String.format("Exiting %s", methodName));
	}

}
