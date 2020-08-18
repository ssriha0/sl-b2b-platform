package com.servicelive.esb.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ResourceBundle;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.jboss.soa.esb.message.Message;

import com.newco.marketplace.translator.util.EmailSender;
import com.servicelive.esb.constant.MarketESBConstant;
import com.servicelive.esb.integration.domain.IntegrationName;

/**
 * @author pbhinga
 * 
 */
public class ExceptionHandler {
	private static String appLifeCycleIndicator = System.getProperty("sl_app_lifecycle");
	private static Logger logger = Logger.getLogger(ExceptionHandler.class);
	private static ResourceBundle resourceBundle = ResourceBundle.getBundle("servicelive_esb_" + appLifeCycleIndicator);

	/**
	 * @param payload
	 * @param fileName
	 * @param errorMsg
	 */

	public static void handle(String client, String payload, String fileName,
			String errorMsg, Object attachment) {
//		logger.error(errorMsg);
		EmailSender.sendMessage(client, fileName, errorMsg, attachment);
		handleInvalidPayload(client, payload, fileName);
	}

	public static void handle(String client, String payload, String fileName,
			String errorMsg, Object attachmentPart1, Object attachmentPart2) {
//		logger.error(errorMsg);
		EmailSender.sendMessage(client, fileName, errorMsg, attachmentPart1,
				attachmentPart2);
		handleInvalidPayload(client, payload, fileName);
	}

	public static void handle(String client, String payload, String fileName,
			String errorMsg, Object attachment, Exception e) {
//		logger.error(errorMsg, e);
		EmailSender.sendMessage(client, fileName, errorMsg, attachment,
				ExceptionUtils.getStackTrace(e));
		handleInvalidPayload(client, payload, fileName);
	}
	
	public static void handle(String client, String payload, String fileName,
			String errorMsg, Object attachment, Exception e,boolean isMQMessage) {
//		logger.error(errorMsg, e);
		EmailSender.sendMessage(client, fileName, errorMsg, attachment,
				ExceptionUtils.getStackTrace(e),isMQMessage);
		handleInvalidPayload(client, payload, fileName);
	}

	public static void handle(String client, String payload, String fileName,
			String errorMsg, Object attachmentPart1, Object attachmentPart2,
			Exception e) {
//		logger.error(errorMsg, e);
		EmailSender.sendMessage(client, fileName, errorMsg, attachmentPart1,
				attachmentPart2);
		handleInvalidPayload(client, payload, fileName);
	}
	public static void handle( String client, String payload, String fileName,
			StringBuilder errorMsg, Exception e) {
		StringBuilder message = new StringBuilder(fileName)
			.append(MarketESBConstant.NEWLINE_CHAR)
			.append( ExceptionUtils.getStackTrace(e));
		EmailSender.sendMessage(errorMsg, message);
		handleInvalidPayload(client, payload, fileName);
	}

	/**
	 * @param payload
	 */
	public static void handleInvalidPayload(String client, String payload,
			String fileName) {
		// send the invalid payload as a error file
		String errorFilePath = resourceBundle.getString(client
				+ "_ERROR_FILE_PATH");
		String errorFileSuffix = resourceBundle.getString("ERROR_FILE_SUFFIX");

		if (errorFilePath != null && errorFileSuffix != null) {
			FileWriter out = null;
			try {
				File file = new File(errorFilePath + fileName + errorFileSuffix);
				out = new FileWriter(file);
				out.write(payload);
			} catch (IOException ioe) {
				logger.error("Problem generating Error output file: "
						+ ioe.getMessage());
			} finally {

				if (out != null) {
					try {
						out.close();
					} catch (IOException ioe) {
						logger.fatal("Error closing outputstream.", ioe);
					}
				}

			}
		}
	}

	public static void handleNPSException(Message msg, Throwable th) {
		
		// Email subject
		StringBuilder emailSubject = new StringBuilder("NPS Call Close process failed in environment: " + appLifeCycleIndicator);
		
		// Email body
		String errMessage = th.getMessage();
		StringWriter stackTraceWriter = new StringWriter();
		th.printStackTrace(new PrintWriter(stackTraceWriter));
		StringBuilder emailBody = new StringBuilder("Message:\r\n");
		emailBody.append(errMessage);
		emailBody.append("\r\n\r\nStack Trace:\r\n");
		emailBody.append(stackTraceWriter.toString());
		
		// Send email
		EmailSender.sendMessage(emailSubject, emailBody);
		
	}
	
	public static void handleAssurantServiceOrderEventHandlerException(String serviceOrderString, Throwable th) {
		// Email subject
		StringBuilder emailSubject = new StringBuilder("AssurantServiceOrderEventHandler process failed in environment: " + appLifeCycleIndicator);
		
		// Email body
		String errMessage = th.getMessage();
		StringWriter stackTraceWriter = new StringWriter();
		th.printStackTrace(new PrintWriter(stackTraceWriter));
		StringBuilder emailBody = new StringBuilder("Message:\r\n");
		emailBody.append(errMessage);
		emailBody.append("\r\n\r\nServiceOrder:\r\n");
		emailBody.append(serviceOrderString);
		emailBody.append("\r\n\r\nStack Trace:\r\n");
		emailBody.append(stackTraceWriter.toString());
		
		// Send email
		EmailSender.sendMessage(emailSubject, emailBody);
	}

	public static void handleWriteOutgoingFileActionEventHandlerException(Long integrationId,
			Long batchId, Throwable th) {
		// Email subject
		StringBuilder emailSubject = new StringBuilder("Integration ")
			.append(IntegrationName.fromId(integrationId))
			.append(" failed in environment: ")
			.append(appLifeCycleIndicator);
		
		// Email body
		String errMessage = th.getMessage();
		StringWriter stackTraceWriter = new StringWriter();
		th.printStackTrace(new PrintWriter(stackTraceWriter));
		StringBuilder emailBody = new StringBuilder("Message:\r\n");
		emailBody.append(errMessage);
		emailBody.append("\r\n\r\nBatch Id:\r\n");
		emailBody.append(batchId);
		emailBody.append("\r\n\r\nStack Trace:\r\n");
		emailBody.append(stackTraceWriter.toString());
		
		// Send email
		EmailSender.sendMessage(emailSubject, emailBody);
	}
}
