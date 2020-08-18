package com.servicelive.esb.actions;

import java.sql.Connection;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.ConfigurationException;
import org.jboss.soa.esb.helpers.ConfigTree;

import com.servicelive.esb.integration.domain.IntegrationName;
import com.servicelive.esb.mapforce.SearsRIMapToServiceLiveIntegrationDb9;

public class RIMapInputAction extends AbstractMapforceInputAction {
	
	private static final Logger logger = Logger.getLogger(RIMapInputAction.class);
	
	public RIMapInputAction() { super(); }
	public RIMapInputAction(ConfigTree config) throws ConfigurationException { super(config); }

	@Override
	protected void doMapping(com.altova.io.Input mapInput, Connection connection) throws Exception  {
		final String methodName = "doMapping";
		logger.info(String.format("Entered %s", methodName));
						
		SearsRIMapToServiceLiveIntegrationDb9 mapper = new SearsRIMapToServiceLiveIntegrationDb9();
		
		mapper.run(
				mapInput,
				connection,
				getIntegrationId(null),
				connection);
		
		logger.info(String.format("Exiting %s", methodName));
		return;
	}
	
	
	@Override
	protected Long getIntegrationId(String fileName) {
		return IntegrationName.RI_INBOUND.getId(); 
	}

	@Override
	protected String getIntegrationName(String fileName) {
		return IntegrationName.RI_INBOUND.name();
	}
	
}
