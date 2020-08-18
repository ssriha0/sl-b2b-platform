package com.servicelive.esb.actions;

import java.sql.Connection;

import org.jboss.soa.esb.ConfigurationException;
import org.jboss.soa.esb.helpers.ConfigTree;

import com.altova.io.Input;
import com.servicelive.esb.integration.domain.IntegrationName;
import com.servicelive.esb.mapforce.NPSCloseAuditMapToServiceLiveIntegrationDb8;

public class NPSCloseAuditMapInputAction extends AbstractMapforceInputAction {

	public NPSCloseAuditMapInputAction() { super(); }
	public NPSCloseAuditMapInputAction(ConfigTree config) throws ConfigurationException { super(config); }
	
	@Override
	protected void doMapping(Input mapInput, Connection connection)
			throws Exception {
		final String methodName = "doMapping";
		logger.info(String.format("Entered %s", methodName));
		
		NPSCloseAuditMapToServiceLiveIntegrationDb8 mapper = new NPSCloseAuditMapToServiceLiveIntegrationDb8();

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
		return IntegrationName.NPS_CLOSE_AUDIT.getId();
	}

	@Override
	protected String getIntegrationName(String fileName) {
		return IntegrationName.NPS_CLOSE_AUDIT.name();
	}

}
