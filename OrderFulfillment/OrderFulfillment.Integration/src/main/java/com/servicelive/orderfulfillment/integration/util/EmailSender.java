package com.servicelive.orderfulfillment.integration.util;

import java.util.ResourceBundle;

import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class EmailSender {
	private static Log logger = LogFactory.getLog(EmailSender.class);	
	private static ResourceBundle resourceBundle = ResourceBundle.getBundle("servicelive_of_"  + System.getProperty("sl_app_lifecycle"));
	private static final String MAIL_HOST = resourceBundle.getString("MAIL_HOST");
	private static final String TO_ADDRESS = resourceBundle.getString("MAIL_TO"); //hssomcs@searshc.com
	private static final String FROM_ADDRESS = resourceBundle.getString("MAIL_FROM");
	
	
	public static Boolean sendMessage(StringBuilder subject, StringBuilder body) {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(MAIL_HOST);
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper mimeHelper;
		try {
			mimeHelper = new MimeMessageHelper(mimeMessage, true);
			mimeHelper.setText(body.toString(), false);
			mimeHelper.setSubject(subject.toString());
			mimeHelper.setTo(TO_ADDRESS);
			mimeHelper.setFrom(FROM_ADDRESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return Boolean.FALSE;
		}
		mailSender.send(mimeMessage);
		logger.info("Error email sent to: " + TO_ADDRESS);
		
		return Boolean.TRUE;
	}
	
}
