package com.servicelive.esb.actions;

import java.sql.Connection;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.ConfigurationException;
import org.jboss.soa.esb.helpers.ConfigTree;

import com.servicelive.esb.constant.HSRFieldNameConstants;
import com.servicelive.esb.integration.domain.IntegrationName;
import com.servicelive.esb.mapforce.HSRNewRequestMapToServiceLiveIntegrationDb3;
import com.servicelive.esb.mapforce.HSRUpdateRequestMapToServiceLiveIntegrationDb5;

/**
 * ESB Action that invokes Mapforce to process an HSR input file
 *  
 * @author sahmed
 */
public class HSRMapInputAction extends AbstractMapforceInputAction {
	
	private static final Logger logger = Logger.getLogger(HSRMapInputAction.class);
	private String newRequestFilenameRegex = "(?i)^.*" + HSRFieldNameConstants.NEW_FILE_PREFIX + ".*$";
	private String updateRequestFilenameRegex = "(?i)^.*" + HSRFieldNameConstants.UPDATE_FILE_PREFIX + ".*$";
	
	public HSRMapInputAction() { super(); }
	public HSRMapInputAction(ConfigTree config) throws ConfigurationException { super(config); }	

	@Override
	protected void doMapping(com.altova.io.Input mapInput, Connection connection) throws Exception  {
		final String methodName = "doMapping";
		logger.info(String.format("Entered %s", methodName));
		
		HSRNewRequestMapToServiceLiveIntegrationDb3 newRequestMapper = new HSRNewRequestMapToServiceLiveIntegrationDb3();
		HSRUpdateRequestMapToServiceLiveIntegrationDb5 updateRequestMapper = new HSRUpdateRequestMapToServiceLiveIntegrationDb5();
		
		String filename = mapInput.getFilename();
		if (filename.matches(this.newRequestFilenameRegex)) {
			newRequestMapper.run(mapInput, connection,
					IntegrationName.HSR_INBOUND_NEW_REQUESTS.getId(),
					connection);
		}
		else if (filename.matches(this.updateRequestFilenameRegex)) {
			updateRequestMapper.run(mapInput, connection,
					IntegrationName.HSR_INBOUND_UPDATE_REQUESTS.getId(),
					connection);
		}
		else {
			throw new RuntimeException(String.format("Unable to process file with name '%s'. Please make sure that it matches the '%s' (for New Request files) or '%s' (for Update Request files) regular expressions.",
					filename, this.newRequestFilenameRegex, this.updateRequestFilenameRegex));
		}

		logger.info(String.format("Exiting %s", methodName));
	}
	
	@Override
	protected Long getIntegrationId(String fileName) {
		return fileIsForNewRequest(fileName)
			? IntegrationName.HSR_INBOUND_NEW_REQUESTS.getId()
			: IntegrationName.HSR_INBOUND_UPDATE_REQUESTS.getId();
	}

	@Override
	protected String getIntegrationName(String fileName) {
		return fileIsForNewRequest(fileName)
			? IntegrationName.HSR_INBOUND_NEW_REQUESTS.name()
			: IntegrationName.HSR_INBOUND_UPDATE_REQUESTS.name();
	}
	
	private boolean fileIsForNewRequest(String fileName) {
		return fileName == null
			? true
			: fileName.toLowerCase().startsWith(this.newRequestFilenameRegex.toLowerCase());
	}
	
	public void setNewRequestFilenameRegex(String newRequestFilenameRegex) {
		if (newRequestFilenameRegex == null) {
			throw new IllegalArgumentException("newRequestFilenameRegex cannot be null.");
		}
		
		this.newRequestFilenameRegex = newRequestFilenameRegex;
	}

	public void setUpdateRequestFilenameRegex(
			String updateRequestFilenameRegex) {

		if (updateRequestFilenameRegex == null) {
			throw new IllegalArgumentException("updateRequestFilenameRegex cannot be null.");
		}
		
		this.updateRequestFilenameRegex = updateRequestFilenameRegex;
	}
}
