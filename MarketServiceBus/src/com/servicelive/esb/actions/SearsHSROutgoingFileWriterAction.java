package com.servicelive.esb.actions;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.jboss.soa.esb.actions.ActionLifecycleException;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

import com.servicelive.esb.constant.MarketESBConstant;
import com.servicelive.esb.integration.domain.BuyerNotification;
import com.servicelive.esb.integration.domain.HsrBuyerNotification;
import com.servicelive.esb.integration.domain.IntegrationName;
import com.servicelive.esb.integration.domain.Transaction;
import com.servicelive.esb.service.ExceptionHandler;

public class SearsHSROutgoingFileWriterAction extends
		AbstractIntegrationSpringAction {

	private static final String HSR_UPDATE_FILENAME_PREFIX = "SLROUTES";
	private static final Logger logger = Logger.getLogger(SearsHSROutgoingFileWriterAction.class);
	private String updateFileLocation;

	public SearsHSROutgoingFileWriterAction() { super(); }
	public SearsHSROutgoingFileWriterAction(ConfigTree configTree) {
		super(configTree);
	}
	
	@Override
	public void initialise() throws ActionLifecycleException {
		super.initialise();
		this.setUpdateFileLocation(this.getProperty(MarketESBConstant.HSR_NEW_UPDATE_FILE_PATH));
	}
	
	public Message processMessage(Message message) throws ActionProcessingException {
		final String methodName = "processMessage";
		logger.info(String.format("entered %s", methodName));

		Long batchId = extractBatchId(message);
		
		try {
			this.doWriteFileForBatch(batchId);
		}
		catch (Exception e) {
			throw new ActionProcessingException(String.format("Error occurred while processing batch with batchId %d.", batchId), e);
		}
		
		logger.info(String.format("exiting %s", methodName));
		return message;
	}

	void writeFileForBatch(Long batchId) {
		try {
			doWriteFileForBatch(batchId);
		}
		catch (Exception e) {
			this.exceptionHandler(null, e);
			ExceptionHandler.handleWriteOutgoingFileActionEventHandlerException(this.getIntegrationId(null), batchId, e);
			this.recordBatchError(null, e, batchId);
		}
		
	}
	

	private Long extractBatchId(Message message)
			throws ActionProcessingException {

		Object batchIdAsObject =  message.getBody().get(MarketESBConstant.INTEGRATION_BATCH_ID);

		if (null == batchIdAsObject) {
			throw new ActionProcessingException(String.format("Unable to find value for key '%s' in message body.", MarketESBConstant.INTEGRATION_BATCH_ID));
		}
		else if(!(batchIdAsObject instanceof Long)) {
			throw new ActionProcessingException(String.format("Value found under key '%s' in message body has type '%s' which is not an instanceof Long", MarketESBConstant.INTEGRATION_BATCH_ID, batchIdAsObject.getClass().getName()));
		}
		
		return (Long) batchIdAsObject;
	}
	
	private void doWriteFileForBatch(Long batchId) throws IOException {
		List<Transaction> transactionList = this.getIntegrationServiceCoordinator().getIntegrationBO().getTransactionsByBatchId(batchId);
		Transaction transaction = transactionList.get(0);
		long transactionId = transaction.getTransactionId();
		BuyerNotification buyerNotification = this.getIntegrationServiceCoordinator().getIntegrationBO().getBuyerNotificationByTransactionId(transactionId);
		HsrBuyerNotification hsrBuyerNotification = this.getIntegrationServiceCoordinator().getIntegrationBO().getHsrBuyerNotificationByBuyerNotificationId(buyerNotification.getBuyerNotificationId());

		String update = getUpdateString(hsrBuyerNotification);		
		String updateFileName = getFileName();
		writeUpdateFile(update, updateFileName);

		String updateLocationAndFileName = this.updateFileLocation + "/" + updateFileName;
		this.getIntegrationServiceCoordinator().recordSuccessfulFileGeneration(batchId, transactionId, updateLocationAndFileName);
	}
	
	private String getUpdateString(HsrBuyerNotification hsrBuyerNotification) {
		DateFormat hsrUpdateDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		//"${SO_UNIT_NUMBER}|${SO_ORDER_NUMBER}|${SO_ROUTED_DATE}|${HSR_TECH_ID}";
		StringBuilder updateStringBuilder = new StringBuilder(100)
			.append(hsrBuyerNotification.getUnitNumber())
			.append("|")
			.append(hsrBuyerNotification.getOrderNumber())
			.append("|")
			.append(hsrUpdateDateFormat.format(hsrBuyerNotification.getRoutedDate()))
			.append("|")
			.append(hsrBuyerNotification.getTechId());
		return updateStringBuilder.toString();
	}

	private String getFileName() {
		SimpleDateFormat fileNameDateFormat = new SimpleDateFormat("yyyyMMdd'.'HHmmss");
		String fileName = String.format("%s.%s", HSR_UPDATE_FILENAME_PREFIX, fileNameDateFormat.format(new Date()));
		return fileName;
	}

	private void writeUpdateFile(String update, String updateFileName) throws IOException {
		File updateFile = new File(this.updateFileLocation, updateFileName);
		FileUtils.writeStringToFile(updateFile, update);	
	}

	@Override
	protected Long getIntegrationId(String fileName) {
		return IntegrationName.HSR_OUTBOUND.getId();
	}

	@Override
	protected String getIntegrationName(String fileName) {
		return IntegrationName.HSR_OUTBOUND.name();
	}
	
	public void setUpdateFileLocation(String updateFileLocation) {
		if (updateFileLocation == null) {
			throw new IllegalArgumentException("Attempt to set updateFileLocation to null. The supplied updateFileLocation cannot be null");
		}
		if (updateFileLocation.endsWith("\\") || updateFileLocation.endsWith("/")) {
			updateFileLocation = updateFileLocation.substring(0, updateFileLocation.length() - 1);
		}
		this.updateFileLocation = updateFileLocation;
	}
}
