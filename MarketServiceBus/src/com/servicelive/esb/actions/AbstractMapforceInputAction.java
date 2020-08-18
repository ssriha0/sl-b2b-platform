package com.servicelive.esb.actions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger; 
import org.jboss.soa.esb.ConfigurationException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.listeners.ListenerTagNames;
import org.jboss.soa.esb.message.Body;
import org.jboss.soa.esb.message.Message;

import com.servicelive.esb.constant.MarketESBConstant;
import com.servicelive.util.FileArchiveException;
import com.servicelive.util.ArchiveFileUtil;

/**
 * An abstract ESB Action responsible for reading the input file feed, and using
 * MapForce to process transactions
 *  
 * @author Dave Mitzel, Samir Ahmed
 *
 */

public abstract class AbstractMapforceInputAction extends AbstractMapforceIntegrationAction {
	static final Logger logger = Logger.getLogger(AbstractMapforceInputAction.class);
	private String workingFileSuffix = "inProcess";
	private String inputFileLocation = ".";
	private String inputFileSuffix = "startProcess";
	private String archiveFileLocation = ".";
	private String errorFileLocation = ".";
	private String searsFileExt = "startProcessGW";
	
	protected abstract void doMapping(com.altova.io.Input mapInput, Connection connection) throws Exception;
	
	public AbstractMapforceInputAction() { super(); }
	public AbstractMapforceInputAction(ConfigTree config) throws ConfigurationException {
		super(config);
		logger.info(String.format("Initializing class '%s' with configuration '%s'", this.getClass().getName(), config));
		this.inputFileLocation = config.getRequiredAttribute(ListenerTagNames.FILE_INPUT_DIR_TAG);
		this.inputFileSuffix = config.getRequiredAttribute(ListenerTagNames.FILE_INPUT_SFX_TAG);
		this.workingFileSuffix = config.getRequiredAttribute(ListenerTagNames.FILE_WORK_SFX_TAG);
		this.archiveFileLocation = config.getRequiredAttribute(MarketESBConstant.ARCHIVE_DIR);
		this.errorFileLocation = config.getRequiredAttribute(ListenerTagNames.FILE_ERROR_DIR_TAG);
		if (this.inputFileLocation.endsWith("/") || this.inputFileLocation.endsWith("\\")) {
			this.inputFileLocation = this.inputFileLocation.substring(0, this.inputFileLocation.length() - 1);
		}
		if (this.inputFileSuffix.startsWith(".")) this.inputFileSuffix = this.inputFileSuffix.substring(1);
		if (this.workingFileSuffix.startsWith(".")) this.workingFileSuffix = this.workingFileSuffix.substring(1);
		if (this.archiveFileLocation.endsWith("/") || this.archiveFileLocation.endsWith("\\")) {
			this.archiveFileLocation = this.archiveFileLocation.substring(0, this.archiveFileLocation.length() - 1);
		}
		if (this.errorFileLocation.endsWith("/") || this.errorFileLocation.endsWith("\\")) {
			this.errorFileLocation = this.errorFileLocation.substring(0, this.errorFileLocation.length() - 1);
		}
	}

	
	public Message mapInput(Message message) throws Exception {
		final String methodName = "mapInput";
		long start = System.currentTimeMillis();
		logger.info("***Entering map Input method***time of action***"+new Date()+"Thread: "+Thread.currentThread().getId());
		logger.info(String.format("Entered %s", methodName));
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("processing message: %s", message.getBody().get()));
		}
		
		//Capture the input file feed name
		Object fileFeedPropertyValue = message.getProperties().getProperty(MarketESBConstant.ORIGINAL_FILE_FEED_NAME);
		String originalFileName = String.valueOf(fileFeedPropertyValue == null ? "" : fileFeedPropertyValue);		
		String workingFileName = originalFileName;

		/***** This will be added later when we create the batch in the Pre-Map step *****/
		//Object batchIdValue = message.getProperties().getProperty(MarketESBConstant.INTEGRATION_BATCH_ID);
		//Long batchId = batchIdValue != null ? (Long)batchIdValue : null;
		
		boolean complete = false;
		Throwable throwable = null;
		com.altova.io.Input mapInput = null;
		String movedFileName = "";
		Connection connection = null;
		
		try {
			connection = this.getIntegrationServiceCoordinator().getIntegrationBO().getSqlConnection();
			logger.info("originalFileName:::"+originalFileName);
			logger.info("message:::"+message);
			workingFileName = createTempFileIfPayloadExistsAndGetFileName(message, originalFileName);
			
			if(null == workingFileName){
				logger.info("No payload available.Exiting the process::::");
				return message;
			}
			logger.info("workingFileName:::"+workingFileName);
			mapInput = com.altova.io.StreamInput.createInput(workingFileName);
			doMapping(mapInput, connection);
			complete = true;
			logger.info("complete:::"+complete);
		} catch (Exception e) {
			logger.error("Error while mapping the input file.", e);
			throwable = e;
			
			if (mapInput != null) {
				try {
					mapInput.close();
				} catch (Exception e1) {
					logger.error("Error while trying to close the input file connection.", e1);
				}
			}
			recordBatchErrorAndArchiveErrorFile(workingFileName, throwable);
			issueErrorNotificationForFile(workingFileName, throwable, errorFileLocation, errorFileSuffix);
		}finally{
			if(null!=connection){
				connection.close();
			}
		}
		
		// Delete the input file once the mapping is complete
		if (complete) {
			if (fileExists(originalFileName)) {
				try {
					movedFileName = ArchiveFileUtil.archiveFile(originalFileName, this.archiveFileLocation, null);
					this.getIntegrationServiceCoordinator().updateBatchFileName(originalFileName, movedFileName);
				} catch (FileArchiveException e) {
					logger.error(e.getMessage()); //FIXME why is it okay to continue?
				}
				
			} else {
				try {
					movedFileName = ArchiveFileUtil.archiveFile(workingFileName, this.archiveFileLocation, null);
					this.getIntegrationServiceCoordinator().updateBatchFileName(workingFileName, movedFileName);
				} catch (FileArchiveException e) {
					logger.error(e.getMessage()); //FIXME why is it okay to continue?
				}
				
			}
			
		}
		
		deleteFile(originalFileName);
		deleteFile(workingFileName);
		
		if (!complete) {
			throw new Exception(String.format("Error occurred while trying to parse file '%s'.", workingFileName), throwable);
		}
		
		message.getProperties().setProperty(MarketESBConstant.ORIGINAL_FILE_FEED_NAME, movedFileName);
		return message;
	}
	
	private void recordBatchErrorAndArchiveErrorFile(String inputFileName, Throwable throwable) {
		String moveFileName = inputFileName;
		try {
			moveFileName = ArchiveFileUtil.archiveFile(inputFileName, errorFileLocation, this.errorFileSuffix);
		} catch (FileArchiveException e) {
			logger.error(e.getMessage());
		}
		
		this.recordBatchError(moveFileName, throwable);
		
	}
	
	private String createTempFileIfPayloadExistsAndGetFileName(Message message,
			String inputFileName) throws IOException, FileNotFoundException {
		
		Object payload = message.getBody().get(Body.DEFAULT_LOCATION);
		String tempFilename = inputFileName;
		logger.info("tempFilename:::"+tempFilename);
		
		int pos = tempFilename.lastIndexOf(".");              
        String fileNameExt = tempFilename.substring(pos + 1); 
        
        int payLoadLength = 0;
		byte[] payLoadArray = null;
        // Only for sears input files
		
		if (payload != null ) {
			
			payLoadArray = (byte[])payload;
			payLoadLength = payLoadArray.length;	
			
			/* For sears input file if there is no pay load, please exists
			 *  */
			
			if(!StringUtils.isBlank(fileNameExt) &&
					fileNameExt.equalsIgnoreCase(searsFileExt) &&
					payLoadLength == 0){
				tempFilename = null;				
			}else{
				tempFilename = String.format("%s.%s", inputFileName, this.workingFileSuffix);
				tempFilename = tempFilename.replaceAll(String.format("\\.%s", this.inputFileSuffix), "");
				
				File tempFile = new File(this.inputFileLocation, tempFilename);
				tempFilename = tempFile.getCanonicalPath();

				FileOutputStream tempFileWriter = null;
				
				try {
					tempFileWriter = new FileOutputStream(tempFile);
					tempFileWriter.write((byte[])payload);
					message.getBody().remove(Body.DEFAULT_LOCATION);
				}
				finally {
					try {
						if (tempFileWriter != null) {
							tempFileWriter.close();
						}
					}
					catch (IOException e) {
						logger.error(String.format("Error occurred while trying to close file stream for '%s'.", tempFile.getCanonicalPath()), e);
					}
				}
			}
		}
		return tempFilename;
	}
}
