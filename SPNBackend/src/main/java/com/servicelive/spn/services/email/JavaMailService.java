package com.servicelive.spn.services.email;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

import com.servicelive.domain.spn.detached.Email;
import com.servicelive.spn.services.BaseServices;

/**
 * @author SVANLOO
 * 
 */
public class JavaMailService extends BaseServices implements EmailService {
	private JavaMailSender mailSender;
	private VelocityEngine velocityEngine;

	/**
	 * @param email that is wanted to sends
	 * @return has sent
	 */
	public boolean sendEmail(Email email) {

		boolean hasSent = true;
		if(email == null) return false;
		for(String to: email.getToList()) {
			logger.info("sending email to " + to);
		}

		MimeMessagePreparator preparator =	new InnerMimeMessagePreparator(email, velocityEngine);
		try {
			this.mailSender.send(preparator);
		}
		catch (Exception ex) {
			// simply log it and go on...
			logger.debug(ex); 
			hasSent = false;
		}
		return hasSent;
	}

	/**
	 * 
	 * @param mailSender
	 */
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	@SuppressWarnings("unused")
	@Override
	protected void handleDates(Object entity) {
		// do nothing
	}

	/**
	 * 
	 * @param velocityEngine
	 */
	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}


}
