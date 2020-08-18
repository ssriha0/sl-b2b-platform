package com.newco.marketplace.business.businessImpl.alert;

import java.util.StringTokenizer;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.newco.marketplace.interfaces.AlertConstants;
import com.newco.marketplace.util.PropertiesUtils;

public class EmailAlert {
	private static final String mailHost = PropertiesUtils.getPropertyValue("smtp_server");
	private static final Logger logger = Logger.getLogger(EmailAlert.class);

	public static void sendMessage(String fromAddress, String toAddress, String bodyMessage, String subject, String ccAddress, String bccAddress) {
		if(toAddress != null && toAddress.trim().equals("") ) {
			if(logger.isInfoEnabled()) {
				// this is normal case for turning off emails in qa.
				logger.info("Unable to send email because the toAddress is blank - subject[" + subject + "]");
			}
			return;
		}

		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(mailHost);
		MimeMessage mimeMessage = mailSender.createMimeMessage();

		MimeMessageHelper mimeHelper;
		try {
			mimeHelper = new MimeMessageHelper(mimeMessage, true);
			mimeHelper.setText(bodyMessage, true);
			mimeHelper.setSubject(subject);
			mimeHelper.setTo(getEmailAddresses(toAddress));
			mimeHelper.setCc(getEmailAddresses(ccAddress));
			mimeHelper.setBcc(getEmailAddresses(bccAddress));
			mimeHelper.setFrom(fromAddress);
			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			e.printStackTrace();
			logger.error("unable to send email - subject[" + subject + "]", e);
		}
	}

	public static String[] getEmailAddresses(String emailString) {
		String emailArray[] = null;
		if (emailString != null) {
			StringTokenizer st = new StringTokenizer(emailString, AlertConstants.EMAIL_DELIMITER);
			int i = st.countTokens();
			int r = 0;
			emailArray = new String[i];
			while (st.hasMoreElements()) {
				String s = (String) st.nextElement();
				if (s != null) {
					emailArray[r] = s;
					r++;
				}

			}

		}
		return emailArray;
	}
}
