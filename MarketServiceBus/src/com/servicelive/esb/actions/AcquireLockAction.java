package com.servicelive.esb.actions;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jboss.soa.esb.ConfigurationException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

import com.servicelive.esb.integration.domain.IntegrationName;

public class AcquireLockAction extends AbstractIntegrationSpringAction {

	private static final Logger logger = Logger.getLogger(AcquireLockAction.class);
	private static final String INTEGRATION_ID_PROPERTY_NAME = "integrationId";
	private static final String LOCK_NAME_PROPERTY_NAME = "lockName";
	private static final String LOCK_EXPIRATION_PROPERTY_NAME = "lockExpirationInMinutes";
	private Long integrationId;
	private IntegrationName integrationName;
	private String lockName;
	private Double lockExpirationMinutes;
	
	public AcquireLockAction() { super(); }
	public AcquireLockAction(ConfigTree config) throws ConfigurationException {
		super(config);
		String integrationIdAsString = config.getRequiredAttribute(INTEGRATION_ID_PROPERTY_NAME);
		Long integrationId;
		IntegrationName integrationName;
		
		
		try {
			integrationId = Long.parseLong(integrationIdAsString);
		}
		catch (NumberFormatException e) {
			throw new ConfigurationException(String.format("Unable to parse configured value '%s' for property '%s' as a long value.", integrationIdAsString, INTEGRATION_ID_PROPERTY_NAME), e);
		}
		
		try {
			integrationName = IntegrationName.fromId(integrationId);
		}
		catch (IllegalArgumentException e) {
			throw new ConfigurationException(String.format("Unable to understand configuration value %d supplied for property '%s'.", integrationId, INTEGRATION_ID_PROPERTY_NAME), e);
		}

		this.integrationId = integrationId;
		this.integrationName = integrationName;
		
		lockName = config.getRequiredAttribute(LOCK_NAME_PROPERTY_NAME);
		if (StringUtils.isEmpty(lockName)) {
			throw new ConfigurationException(String.format("Missing configuration value for property '%s'.", LOCK_NAME_PROPERTY_NAME));
		}
		
		String lockExpirationAsString = config.getRequiredAttribute(LOCK_EXPIRATION_PROPERTY_NAME);
		try {
			lockExpirationMinutes = Double.parseDouble(lockExpirationAsString);
		}
		catch (NumberFormatException e) {
			throw new ConfigurationException(String.format("Unable to parse configured value '%s' for property '%s' as a long value.", integrationIdAsString, INTEGRATION_ID_PROPERTY_NAME), e);
		}
		
	}
	
	public Message acquireLock(Message message) {
		if (!this.getIntegrationServiceCoordinator().acquireLock(lockName, lockExpirationMinutes)) {
			logger.error(String.format("Unable to acquire lock %s", lockName));
			return null;
		}
		return message;
	}
	
	@Override
	protected Long getIntegrationId(String fileName) {
		return this.integrationId;
	}

	@Override
	protected String getIntegrationName(String fileName) {
		return this.integrationName == null ? null : this.integrationName.name();
	}

}
