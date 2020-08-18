package com.servicelive.esb.actions;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.ConfigurationException;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.listeners.ListenerTagNames;
import org.jboss.soa.esb.message.Message;

import com.servicelive.esb.integration.BusinessException;
import com.servicelive.esb.integration.domain.Batch;
import com.servicelive.esb.integration.domain.IntegrationName;
import com.servicelive.esb.mapforce.NPSCallCloseMapToNPSCallCloseUpdate;
import com.servicelive.esb.service.ExceptionHandler;

public class NPSCallCloseIntegrationFileGeneratorAction extends
		AbstractMapforceIntegrationAction {
	
	private static final Logger logger = Logger.getLogger(NPSCallCloseIntegrationFileGeneratorAction.class);
	private static final String CLASS_NAME = NPSCallCloseIntegrationFileGeneratorAction.class.getName();
	private static final String BATCH_ID_MESSAGE_KEY = String.format("%s.batchId", CLASS_NAME);
	private static final String OUTPUT_FILE_NAME_MESSAGE_KEY = String.format("%s.outputFileName", CLASS_NAME);
	
	private String outputFileLocation;
	private String outputFileNamePrefix = "CLSINSSO";

	public NPSCallCloseIntegrationFileGeneratorAction() {
		super();
	}

	public NPSCallCloseIntegrationFileGeneratorAction(ConfigTree configTree) throws ConfigurationException {
		super(configTree);
		setOutputFileLocation(configTree.getRequiredAttribute(ListenerTagNames.FILE_INPUT_DIR_TAG));
	}

	public Message processMessage(Message message) throws ActionProcessingException {
		final String methodName = "processMessage";
		logger.info(String.format("Entered %s", methodName));

		Batch batchToBeProcessed = this.getIntegrationServiceCoordinator().getIntegrationBO().getCurrentAndPrepareNewOutgoingBatchFor(IntegrationName.RI_OUTBOUND);
		if (batchToBeProcessed == null) {
			logger.debug(String.format("Unable to find an open batch with unprocessed transactions for the '%s' integration. There is no work to do.", IntegrationName.RI_OUTBOUND));
			return message; // need to let subsequent actions execute
		}
		
		logger.debug(String.format("Processing transactions for batch: %s", batchToBeProcessed));
		message.getBody().add(BATCH_ID_MESSAGE_KEY, batchToBeProcessed.getBatchId());
		
		String outputFileName = generateOutputFileName();
		logger.debug(String.format("Attempting to generate output file '%s'.", outputFileName));
		message.getBody().add(OUTPUT_FILE_NAME_MESSAGE_KEY, outputFileName);
		
		try {
			Connection connection = this.getIntegrationServiceCoordinator().getIntegrationBO().getSqlConnection();
			NPSCallCloseMapToNPSCallCloseUpdate callCloseMap = new NPSCallCloseMapToNPSCallCloseUpdate();
			com.altova.io.Output outputFile = new com.altova.io.FileOutput(outputFileName);
			callCloseMap.run(connection, batchToBeProcessed.getBatchId(), outputFile);
		} catch (Exception e) {
			throw new ActionProcessingException(String.format("Error occurred while trying to generate output file '%s' for batch: %s", outputFileName, batchToBeProcessed), e);
		}
		logger.debug(String.format("Output file '%s' created successfully.", outputFileName));
		
		try {
			logger.debug(String.format("Attempting to mark batch with batchId %d as processed.", batchToBeProcessed.getBatchId()));
			this.getIntegrationServiceCoordinator().markBatchAndTransactionsAsProcessed(batchToBeProcessed.getBatchId(), outputFileName);
		}
		catch (BusinessException e) {
			throw new ActionProcessingException(String.format("Unable to update processing status for batch: %s", batchToBeProcessed), e);
		}
		
		logger.info(String.format("Exiting %s", methodName));
		return message;
	}

	private String generateOutputFileName() {
		String timestamp = new SimpleDateFormat("yyyyMMdd.HHmmssss").format(Calendar.getInstance().getTime());
		String outputFileName = String.format("%s/%s.%s.xml", outputFileLocation, outputFileNamePrefix, timestamp);
		return outputFileName;
	}
	
	@Override
	public void exceptionHandler(Message message, Throwable throwable) {
		String outputFileName = (String)message.getBody().get(OUTPUT_FILE_NAME_MESSAGE_KEY);
		Long batchId = (Long)message.getBody().get(BATCH_ID_MESSAGE_KEY);

		if (logger.isDebugEnabled()) {
			logger.debug(
				String.format(
					"Error occurred while trying to generate NPS Call Close file '%s' from batch with id %d. The ESB message being processed was: %s",
					outputFileName, batchId, message.toString()
				), throwable);
		}
		this.recordBatchError(outputFileName, throwable, batchId);
		ExceptionHandler.handleNPSException(message, throwable);
	}

	@Override
	protected Long getIntegrationId(String fileName) {
		return IntegrationName.RI_OUTBOUND.getId();
	}

	@Override
	protected String getIntegrationName(String fileName) {
		return IntegrationName.RI_OUTBOUND.toString();
	}
	
	public void setOutputFileLocation(String outputFileLocation) {
		this.outputFileLocation = outputFileLocation;
		if (this.outputFileLocation.endsWith("/") || this.outputFileLocation.endsWith("\\")) {
			this.outputFileLocation = this.outputFileLocation.substring(0, this.outputFileLocation.length() - 1);
		}
	}

	public String getOutputFileLocation() {
		return outputFileLocation;
	}

	public void setOutputFileNamePrefix(String outputFileNamePrefix) {
		this.outputFileNamePrefix = outputFileNamePrefix;
		if (this.outputFileNamePrefix.endsWith(".")) {
			this.outputFileNamePrefix = this.outputFileNamePrefix.substring(0, this.outputFileNamePrefix.length() - 1);
		}
	}

	public String getOutputFileNamePrefix() {
		return outputFileNamePrefix;
	}
}
