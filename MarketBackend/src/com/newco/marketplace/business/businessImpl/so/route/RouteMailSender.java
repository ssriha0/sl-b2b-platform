package com.newco.marketplace.business.businessImpl.so.route;

import java.io.StringWriter;

import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.newco.marketplace.business.businessImpl.AMailSender;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.exception.core.BusinessServiceException;

public class RouteMailSender extends AMailSender {

	private static final Logger logger = Logger
			.getLogger(RouteMailSender.class.getName());

	@Override
	public void send(String recipient, Object obj)
			throws BusinessServiceException {
		
		ServiceOrder so = (ServiceOrder)obj;
		
		StringWriter sw = new StringWriter();
		
		try {
			//setup the template data
			setupTemplate();
			
			VelocityContext vContext = new VelocityContext();
			
			//generate the dynamic text for the email
			vContext.put("soId", so.getSoId());
			
			getVelocityEngine().evaluate(vContext, sw, "velocity template", getEmailTemplate().getSource());
			if (sw == null)
				throw new Exception("Could not generate the 'Service Order Routed' email text from template!!!");
			String text = sw.getBuffer().toString();
	
			//clear the contents of the last evaluate
			sw = new StringWriter(); 
			
			//generate the dynamic data for the subject
			getVelocityEngine().evaluate(vContext, sw, "velocity template", getEmailTemplate().getSubject());
			if (sw == null)
				throw new Exception("Could not generate the 'Service Order Route' email subject from template!!!");
			String subject = sw.getBuffer().toString();
			
			//construct the message
			MimeMessage message = getMailSender().createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(getEmailTemplate().getTemplateFrom());
			helper.setTo(recipient);
			helper.setSubject(subject);
			helper.setText(text, true);
			helper.addInline("emailLogo", getEmailImg());
			getMailSender().send(message);
			
		} catch (MailException mailEx) {
			// simply log it and go on...
			String eMsg = "There was a MailException sending the 'Service Order Route' email for Service Order id "
					+ so.getSoId() + " to " + recipient
					+ " error: " + mailEx.getMessage();
			logger.error(eMsg, mailEx);
			
			throw new BusinessServiceException(eMsg);
		} catch (Exception e) {
			// simply log it and go on...
			String eMsg = "There was an Exception sending the 'Service Order Route' email for Service Order id "
				+ so.getSoId() + " to " + recipient
				+ " error: " + e.getMessage();
			logger.error(eMsg, e);
			
			throw new BusinessServiceException(eMsg);
		}
	}
}
