package com.servicelive.esb.actions;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jboss.soa.esb.ConfigurationException;
import org.jboss.soa.esb.actions.ActionLifecycleException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

import com.servicelive.bus.event.order.OrderEventType;
import com.servicelive.bus.event.order.ServiceOrderEvent;
import com.servicelive.esb.constant.MarketESBConstant;
import com.servicelive.esb.integration.domain.AssurantBuyerNotification;
import com.servicelive.esb.integration.domain.BuyerNotification;
import com.servicelive.esb.integration.domain.IntegrationName;
import com.servicelive.esb.integration.domain.Part;
import com.servicelive.esb.integration.domain.Transaction;
import com.servicelive.esb.service.ExceptionHandler;

public class AssurantWriteOutgoingFileAction extends AbstractIntegrationSpringAction {
	private static final Logger logger = Logger.getLogger(AssurantWriteOutgoingFileAction.class);

	private static final String ASSURANT_SP_REOPEN_STATUS = "SP ReOpen"; 
	private static final Map<OrderEventType, String> serviceLiveEventToAssurantUpdateMap;
	private static final Map<OrderEventType, Integer> serviceLiveEventToUpdateCountMap;
	static {
		serviceLiveEventToAssurantUpdateMap = new HashMap<OrderEventType, String>(10);
		serviceLiveEventToAssurantUpdateMap.put(OrderEventType.CREATED, "Dispatch Received");
		serviceLiveEventToAssurantUpdateMap.put(OrderEventType.POSTED, "Dispatched");
		serviceLiveEventToAssurantUpdateMap.put(OrderEventType.ACCEPTED, "Dispatched Target");
		serviceLiveEventToAssurantUpdateMap.put(OrderEventType.COMPLETED, "Closed");
		serviceLiveEventToAssurantUpdateMap.put(OrderEventType.VOIDED, "Cancelled");
		serviceLiveEventToAssurantUpdateMap.put(OrderEventType.DELETED, "Cancelled");
		serviceLiveEventToAssurantUpdateMap.put(OrderEventType.PARTS_SHIPPED, "Part Shipped");
		
		serviceLiveEventToUpdateCountMap = new HashMap<OrderEventType, Integer>(10);
		serviceLiveEventToUpdateCountMap.put(OrderEventType.CREATED, 1);
		serviceLiveEventToUpdateCountMap.put(OrderEventType.POSTED, 1);
		serviceLiveEventToUpdateCountMap.put(OrderEventType.ACCEPTED, 1);
		serviceLiveEventToUpdateCountMap.put(OrderEventType.COMPLETED, 1);
	}
	
	private String updateFileLocation;
	
	public AssurantWriteOutgoingFileAction() {
		super();
	}
	
	public AssurantWriteOutgoingFileAction(ConfigTree configTree) throws ConfigurationException {
		super(configTree);
	}
	
	@Override
	public void initialise() throws ActionLifecycleException {
		super.initialise();
		this.setUpdateFileLocation(this.getProperty(MarketESBConstant.ASSURANT_NEW_UPDATE_FILE_PATH));
	}
	
	@Override
	protected Long getIntegrationId(String fileName) {
		return IntegrationName.ASSURANT_OUTBOUND.getId();
	}

	@Override
	protected String getIntegrationName(String fileName) {
		return IntegrationName.ASSURANT_OUTBOUND.name();
	}
	
	public Message writeOutgoingFile(Message message) {
		final String methodName = "writeOutgoingFile";
		logger.info(String.format("entered %s", methodName));
		
		try {
			//FIXME This will need to be correctly implemented when Assurant notifications
			// are handled via ESB's jms-listener instead of via a ServiceLiveEventListener
			writeOutgoingFileByBatch(null, null, null, null,null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info(String.format("exiting %s", methodName));
		return message;
	}
	
	public void writeOutgoingFile(Long batchId, OrderEventType orderEventType, String subStatusName, String incidentActionDescription,String resolutionComments) {
		final String methodName = "writeOutgoingFile";
		logger.info(String.format("entered %s", methodName));
		
		try {
			writeOutgoingFileByBatch(batchId, orderEventType, subStatusName, incidentActionDescription,resolutionComments);
		} catch (Exception e) {
			this.exceptionHandler(null, e);
			ExceptionHandler.handleWriteOutgoingFileActionEventHandlerException(
					this.getIntegrationId(null), batchId, e);
			this.recordBatchError(null, e, batchId);
		}
		
		logger.info(String.format("exiting %s", methodName));
	}
		
	private void writeOutgoingFileByBatch(Long batchId, OrderEventType orderEventType, String subStatusName, String incidentActionDescription,String resolutionComments) throws IOException {
		List<Transaction> transactionList = this.getIntegrationServiceCoordinator().getIntegrationBO().getTransactionsByBatchId(batchId);
		Transaction transaction = transactionList.get(0);
		long transactionId = transaction.getTransactionId();
		BuyerNotification buyerNotification = this.getIntegrationServiceCoordinator().getIntegrationBO().getBuyerNotificationByTransactionId(transactionId);
		
		boolean skipWritingFile = false;
		if (serviceLiveEventToUpdateCountMap.containsKey(orderEventType)
				|| serviceLiveEventToUpdateCountMap.containsKey(subStatusName)) {
			long max = serviceLiveEventToUpdateCountMap.get(orderEventType);
			long count = this.getIntegrationServiceCoordinator().getIntegrationBO().getSuccessfulBuyerNotificationTransactionCount(transaction.getServiceLiveOrderId(), orderEventType);
			skipWritingFile = count >= max;
		}
		
		if (!skipWritingFile) {
			com.servicelive.esb.integration.domain.ServiceOrder serviceOrder = this.getIntegrationServiceCoordinator().getIntegrationBO().getServiceOrderByTransactionId(transactionId);
			AssurantBuyerNotification assurantBuyerNotification = this.getIntegrationServiceCoordinator().getIntegrationBO().getAssurantBuyerNotificationByBuyerNotificationId(buyerNotification.getBuyerNotificationId());
			List<Part> parts = this.getIntegrationServiceCoordinator().getIntegrationBO().getPartsByServiceOrderId(serviceOrder.getServiceOrderId());
			
			String update = getUpdateString(transaction, serviceOrder, buyerNotification, 
					assurantBuyerNotification, parts, orderEventType, subStatusName, incidentActionDescription,resolutionComments);
			
			String updateFileName = getFileNameFromExternalOrderNumber(transaction.getExternalOrderNumber());
			
			writeUpdateFile(update, updateFileName, this.updateFileLocation);

			String updateLocationAndFileName = this.updateFileLocation + "/" + updateFileName;
			
			this.getIntegrationServiceCoordinator().recordSuccessfulFileGeneration(batchId, transactionId,
					updateLocationAndFileName);
		} else {
			String skipReason = String.format("The maximum number of files for external ordernumber %s and event type %s have already been written.", transaction.getExternalOrderNumber(), orderEventType.toString());
			this.getIntegrationServiceCoordinator().markTransactionAsSkipped(transactionId, skipReason);
		}
	}

	private String getUpdateString(Transaction transaction,
			com.servicelive.esb.integration.domain.ServiceOrder serviceOrder,
			BuyerNotification buyerNotification,
			AssurantBuyerNotification assurantBuyerNotification,
			List<Part> parts, OrderEventType orderEventType, String subStatusName, String incidentActionDescription,
			String resolutionComments) {
		final int MAX_INCIDENT_ID_LENGTH = 10;
		String incidentId = getNonNullTrimmedAndTruncatedStringFrom(
				transaction.getExternalOrderNumber(), MAX_INCIDENT_ID_LENGTH);
		String shipAirBill = assurantBuyerNotification.getShippingAirBillNumber();
		String coreReturnAirBill = assurantBuyerNotification.getReturnAirBillNumber();
		String replacementOEM = "";
		String replacementModel = "";
		Date etaDate = assurantBuyerNotification.getEtaOrShippingDate();
		
		if (parts != null && parts.size() > 0) {
			Part firstPart =  parts.get(0);
			if (firstPart != null) {
				replacementOEM = getNonNullValueOrDefaultFrom(firstPart.getManufacturer(), replacementOEM);
				replacementModel = getNonNullValueOrDefaultFrom(firstPart.getModelNumber(), replacementModel);
			}
		}
		

		// Create ~ separated string from the following elements in given order:
		// status
		// incident id
		// comment - it includes the resolution comments for closure
		// service order id
		// status date
		// ETA or shipping date
		// class (^ separated class list, 1 per part)
		// part # (^ separated part # list, 1 per part)
		// part description (^ separated part description list, 1 per part)
		// quantity (^ separated quantity list, 1 per part)
		// price (^ separated price list, 1 per part)
		// shipping air bill number
		// core return air bill number
		// core return number
		// shipping charge
		// replacement OEM
		// replacement Model
		// replacement Serial Number
		// sales tax
		
		Collection<String> fields = new ArrayList<String>(15);
		fields.add(this.getMappedStatus(orderEventType, subStatusName));
		fields.add(incidentId);
		if(orderEventType == OrderEventType.COMPLETED){
			fields.add(StringUtils.defaultString(resolutionComments, StringUtils.EMPTY));
		}else{
			fields.add(StringUtils.defaultString(incidentActionDescription, StringUtils.EMPTY));
		}
		fields.add(transaction.getServiceLiveOrderId());
		fields.add(getFormattedDate(new Date()));
		fields.add(getFormattedDate(etaDate));
		fields.add(getPartsString(parts));
		fields.add(shipAirBill);
		fields.add(coreReturnAirBill);
		fields.add("");
		fields.add("0");
		fields.add(replacementOEM);
		fields.add(replacementModel);
		fields.add("");
		fields.add("0.00");
		
		return StringUtils.join(fields.iterator(), "~");
	}

	private String writeUpdateFile(String update, String updateFileName, String updateFileLocation) throws IOException {
		File outputFile = new File(updateFileLocation, updateFileName);
		FileUtils.writeStringToFile(outputFile, update);
		return outputFile.getName();
	}
	
	private String getFileNameFromExternalOrderNumber(String externalOrderNumber) {
		final int MAX_INCIDENT_ID_LENGTH = 10;
		String incidentId = getNonNullTrimmedAndTruncatedStringFrom(
				externalOrderNumber, MAX_INCIDENT_ID_LENGTH);
		Date updateDate = new Date();
		int randomNumber = (int) (Math.random() * 1000);
		SimpleDateFormat dateFormatter = new SimpleDateFormat("ddMMyy_HHmmss");
		String updateDateString = dateFormatter.format(updateDate);
		String fileName = String.format("%s_%s_%d.OUT", incidentId,
				updateDateString, randomNumber);
		return fileName;
	}
	
	private <T> T getNonNullValueOrDefaultFrom(T value, T defaultValue) {
		T returnValue = defaultValue;
		if (value != null) {
			returnValue = value;
		}
		return returnValue;
	}
	
	private String getNonNullTrimmedAndTruncatedStringFrom(String source, int maximumLength) {
		String returnValue = "";
		if (source != null) {
			source = source.trim();
			if (source.length() < maximumLength) {
				returnValue = source;
			}
			else {
				returnValue = source.substring(0, maximumLength);
			}
		}
		return returnValue;
	}
	
	private String getMappedStatus(OrderEventType event, String subStatusName) {
		if (event == null) return "Unmapped status: " + null;

		if (event == OrderEventType.BUYER_NOTIFICATION && subStatusName != null) {
			return subStatusName;
		} else if (event == OrderEventType.SUBSTATUS_CHANGE && subStatusName != null) {
			return subStatusName;
		} else if (event == OrderEventType.CREATED) {
			if (subStatusName != null && subStatusName.equals(ServiceOrderEvent.ORDER_CREATION_TYPE_REOPEN)) {
				return ASSURANT_SP_REOPEN_STATUS;
		}
			else {
				return serviceLiveEventToAssurantUpdateMap.get(event);
	}
		}
		else if (serviceLiveEventToAssurantUpdateMap.containsKey(event)) {
			return serviceLiveEventToAssurantUpdateMap.get(event);
		}

		return "Unmapped status: " + event.name();
	}
	
	private String getFormattedDate(Date date) {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(date);
	}
	
	private String getPartsString(List<Part> parts) {
		final int MAX_PARTS_CLASS_LENGTH = 8;
		final int MAX_PARTS_DESCRIPTION_LENGTH = 35;
		final int MAX_PARTS_QUANTITY_LENGTH = 5;
		StringBuilder partsClasses = new StringBuilder(100);
		StringBuilder partsNumbers = new StringBuilder(100);
		StringBuilder partsDescriptions = new StringBuilder(100);
		StringBuilder partsQuantities = new StringBuilder(100);
		StringBuilder partsPrices = new StringBuilder(100);
		String separator = "";
		for (Part part : parts) {
			partsClasses.append(separator).append(
					getNonNullTrimmedAndTruncatedStringFrom(part
							.getClassCode(),
							MAX_PARTS_CLASS_LENGTH));

			partsDescriptions.append(separator).append(
					getNonNullTrimmedAndTruncatedStringFrom(
							replaceNewLineChars(part.getClassComments(), " "),
							MAX_PARTS_DESCRIPTION_LENGTH));

			partsQuantities.append(separator).append(
					getNonNullTrimmedAndTruncatedStringFrom(part.getQuantity().toString(),
							MAX_PARTS_QUANTITY_LENGTH));
			partsPrices.append(separator).append(0);
			separator = "^";
		}
		
		// generate string in the following form:
		// classes~numbers~descriptions~quantities~prices
		StringBuilder partsSB = new StringBuilder(500)
			.append(partsClasses)
			.append("~")
			.append(partsNumbers)
			.append("~")
			.append(partsDescriptions)
			.append("~")
			.append(partsQuantities)
			.append("~")
			.append(partsPrices)
		;
		return partsSB.toString();
	}
		
	private String replaceNewLineChars(String source, String replacement) {
		String replaced = StringUtils.replace(source, "\r\n", replacement);
		replaced = StringUtils.replace(replaced, "\r", replacement);
		replaced = StringUtils.replace(replaced, "\n", replacement);
		return replaced;
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

	public String getUpdateFileLocation() {
		return this.updateFileLocation;
	}

}
