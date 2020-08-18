package com.servicelive.esb.actions;

import java.sql.Connection;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.ConfigurationException;
import org.jboss.soa.esb.helpers.ConfigTree;

import com.servicelive.esb.constant.MarketESBConstant;
import com.servicelive.esb.integration.domain.IntegrationName;
import com.servicelive.esb.mapforce.AssurantMapToServiceLiveIntegrationDb;

public class AssurantMapInputAction extends AbstractMapforceInputAction {

	private static final Logger logger = Logger.getLogger(AssurantMapInputAction.class);
	
	private Long buyerResourceId;
	
	public AssurantMapInputAction() { super(); }
	public AssurantMapInputAction(ConfigTree config) throws ConfigurationException { 
		super(config);
	
		this.buyerResourceId = Long.parseLong(config.getRequiredAttribute(MarketESBConstant.BUYER_RESOURCE_ID));
	}
	
	@Override
	protected void doMapping(com.altova.io.Input mapInput, Connection connection) throws Exception {
		final String methodName = "doMapping";
		logger.info(String.format("Entered %s", methodName));
		
		AssurantMapToServiceLiveIntegrationDb mapper = new AssurantMapToServiceLiveIntegrationDb();

		mapper.run(
				mapInput,
				connection,
				getIntegrationId(null),
				buyerResourceId,
				connection);
		
		logger.info(String.format("Exiting %s", methodName));
		return;
	}
	
	@Override
	protected Long getIntegrationId(String fileName) {
		return IntegrationName.ASSURANT_INBOUND.getId();
	}

	@Override
	protected String getIntegrationName(String fileName) {
		return IntegrationName.ASSURANT_INBOUND.name();
	}
	
}
