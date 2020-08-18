package com.servicelive.esb.actions;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.ConfigurationException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

import com.servicelive.esb.constant.NPSConstants;
import com.servicelive.esb.integration.domain.IntegrationName;

public class NPSCallCloseAddRetriesToBatchAction extends AbstractIntegrationSpringAction {
	private static final Logger logger = Logger.getLogger(NPSCallCloseAddRetriesToBatchAction.class);
	private Double retryHours = 4D;
	private Long maxDbTransactions = 1000L;
	
	public NPSCallCloseAddRetriesToBatchAction() { super(); }
	public NPSCallCloseAddRetriesToBatchAction(ConfigTree config) throws ConfigurationException { 
		super(config);
		
		String retryHoursAsString = config.getRequiredAttribute(NPSConstants.RETRY_HOURS_KEY);
		
		try {
			retryHours = Double.parseDouble(retryHoursAsString);
		}
		catch (NumberFormatException e) {
			throw new ConfigurationException(String.format("Unable to parse configured value '%s' for property '%s' as a double value.", retryHoursAsString, NPSConstants.RETRY_HOURS_KEY), e);
		}
		
		
		retryHours = Double.parseDouble(configTree.getRequiredAttribute(NPSConstants.RETRY_HOURS_KEY));
		
	}
	
	public Message addRetriesToBatch(Message message) {
		try {
			String resendErrorMessage = this.getIntegrationServiceCoordinator()
					.performAddNpsCallCloseRetriesToBatch(retryHours,
							maxDbTransactions);
			
			if (resendErrorMessage != null && resendErrorMessage.length() > 0) {
				exceptionHandler(message, new Exception(resendErrorMessage));
			}
			
		} catch (Exception e) {
			logger.error("An error occurred while attempting to add resend transactions to the outgoing RI batch.", e);
			// Allow this to continue so that we don't stop the next action in this integration.
		}
		
		return message;
	}
	
	@Override
	protected Long getIntegrationId(String fileName) {
		return IntegrationName.RI_OUTBOUND.getId();
	}
	@Override
	protected String getIntegrationName(String fileName) {
		return IntegrationName.RI_OUTBOUND.toString();
	}
	
	
}
