package com.newco.batch.westsurvey;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.newco.marketplace.dto.vo.survey.WestSurveyErrorVO;
import com.newco.marketplace.interfaces.SurveyConstants;

/**
 * This class is responsible for generating and sending an email notification to Prod Support group in case of failure
 * in West Survey Spreadsheet import job.
 */
public class WestSurveyErrorHandler {

	private Logger logger = Logger.getLogger(WestSurveyErrorHandler.class);

	/**
	 * Method to send West Survey error notification email to production support
	 * @param Map<String,List<WestSurveyErrorVO>> erroredFilesRecords
	 */
	public void notifyError(Map<String,List<WestSurveyErrorVO>> erroredFilesRecords, String emailHostName, String to, String from,
								String errFilePath, String archFilePath) {
		String directoryPath = null;
		for (Entry<String, List<WestSurveyErrorVO>> errorMapEntry : erroredFilesRecords.entrySet()) {
			String fileName = errorMapEntry.getKey();
			List<WestSurveyErrorVO> westSurveyErrorVOList = errorMapEntry.getValue();
			String subject = getSubject(fileName, westSurveyErrorVOList);
			String messagaeBody = getMessageBody(fileName, westSurveyErrorVOList);
			if (null != westSurveyErrorVOList) {
				directoryPath = archFilePath;
			} else {
				directoryPath = errFilePath;
			}
			File file = new File(directoryPath + fileName);
			sendEmail(emailHostName, to, from, subject, messagaeBody, file);
		}
	}

	/**
	 * Method to get the subject for West Survey error notification email
	 * @param String fileName
	 * @param List<WestSurveyErrorVO> westSurveyErrorVOList
	 * @return String subject
	 */
	private String getSubject(String fileName, List<WestSurveyErrorVO> westSurveyErrorVOList) {
		String subject = null;
		StringBuilder sb = new StringBuilder();
		if (null != westSurveyErrorVOList && !westSurveyErrorVOList.isEmpty()) {
			sb.append(SurveyConstants.WEST_MAIL_SUBJECT_FILE_RECORDS_FAILED);
		} else {
			sb.append(SurveyConstants.WEST_MAIL_SUBJECT_ENTIRE_FILE_FAILED);
		}
		sb.append(fileName)
			.append(SurveyConstants.HYPHEN)
			.append(SurveyConstants.WEST_MAIL_SUBJECT_ENV_DESCR)
			.append(System.getenv("sl_app_lifecycle"));		
		subject = sb.toString();
		return subject;
	}

	/**
	 * Method to get the message body for West Survey error notification email
	 * @param String fileName
	 * @param List<WestSurveyErrorVO> westSurveyErrorVOList
	 * @return String
	 */
	private String getMessageBody(String fileName, List<WestSurveyErrorVO> westSurveyErrorVOList) {
		StringBuilder sb = new StringBuilder();
		if (null != westSurveyErrorVOList && !westSurveyErrorVOList.isEmpty()) {
			int counter = 1;
			sb.append(SurveyConstants.WEST_MAIL_BODY_FILE_RECORDS_FAILED).append(SurveyConstants.NEWLINE_CHAR)
			  .append(SurveyConstants.WEST_MAIL_BODY_FILES_FAILED_DESCR).append(fileName).append(SurveyConstants.NEWLINE_CHAR)
			  .append(SurveyConstants.WEST_MAIL_BODY_RECORDS_FAILED_DESCR).append(SurveyConstants.NEWLINE_CHAR);

			for (WestSurveyErrorVO westSurveyErrorVO : westSurveyErrorVOList) {					
				String errorMsg = westSurveyErrorVO.getErrorMessage();
				String unitNo = westSurveyErrorVO.getWestSurveyVO().getUnitNo();
				String orderNo = westSurveyErrorVO.getWestSurveyVO().getOrderNo();
				sb.append(counter + ". ").append(SurveyConstants.UNIT_NO).append(unitNo).append(SurveyConstants.COMMA)
					.append(SurveyConstants.SERVICE_ORDER).append(orderNo).append(SurveyConstants.NEWLINE_CHAR)
					.append(SurveyConstants.WEST_MAIL_BODY_ERROR_DESCR).append(errorMsg).append(SurveyConstants.NEWLINE_CHAR);
				if (westSurveyErrorVO.getException() != null) {
					StringWriter stackTraceWriter = new StringWriter();
					westSurveyErrorVO.getException().printStackTrace(new PrintWriter(stackTraceWriter));
					sb.append(SurveyConstants.WEST_MAIL_BODY_EXCEPTION_STACKTRACE).append(stackTraceWriter.toString());
				}
				counter++;
			}
		} else {
			sb.append(SurveyConstants.WEST_MAIL_BODY_ENTIRE_FILE_FAILED).append(SurveyConstants.NEWLINE_CHAR)
				.append(SurveyConstants.WEST_MAIL_BODY_FILES_FAILED_DESCR).append(fileName).append(SurveyConstants.NEWLINE_CHAR)
				.append(SurveyConstants.WEST_MAIL_BODY_REFER_APP_LOGS);
		}
		return sb.toString();
	}

	/**
	 * Private Method to send West Survey error notification email to production support
	 * @param String messageBody
	 * @param String subject
	 * @param String fileName
	 * @param File file
	 */
	private void sendEmail(String emailHostName, String to, String from, String subject, String messageBody, File file) {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(emailHostName);
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		try {
			MimeMessageHelper mimeHelper = new MimeMessageHelper(mimeMessage, true);
			mimeHelper.setText(messageBody, false);
			mimeHelper.setSubject(subject);
			mimeHelper.setTo(to);
			mimeHelper.setFrom(from);
			mimeHelper.addAttachment(file.getName(), file);
			mailSender.send(mimeMessage);
		} catch (MessagingException msgEx) {
			logger.error("Unexpected error while sending email", msgEx);
		}
		logger.info("West Survey Error notification email sent to: " + to);
	}
}
