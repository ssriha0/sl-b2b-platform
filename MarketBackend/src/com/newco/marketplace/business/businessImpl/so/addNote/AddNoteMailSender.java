package com.newco.marketplace.business.businessImpl.so.addNote;

import java.io.StringWriter;

import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.newco.marketplace.business.businessImpl.AMailSender;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderNote;
import com.newco.marketplace.exception.core.BusinessServiceException;

public class AddNoteMailSender extends AMailSender {

	private static final Logger logger = Logger
			.getLogger(AddNoteMailSender.class.getName());

	@Override
	public void send(String recipient, Object obj)
			throws BusinessServiceException {
		
		ServiceOrderNote note = (ServiceOrderNote)obj;
		
		StringWriter sw = new StringWriter();
		
		try {
			//setup the template data
			setupTemplate();
			
			VelocityContext vContext = new VelocityContext();
			
			//generate the dynamic text for the email
			vContext.put("soId", note.getSoId());
			vContext.put("note", note.getNote());
			getVelocityEngine().evaluate(vContext, sw, "velocity template", getEmailTemplate().getSource());
			if (sw == null)
				throw new Exception("Could not generate the 'Service Order Add Note' email text from template!!!");
			String text = sw.getBuffer().toString();
	
			//clear the contents of the last evaluate
			sw = new StringWriter(); 
			
			//generate the dynamic data for the subject
			getVelocityEngine().evaluate(vContext, sw, "velocity template", getEmailTemplate().getSubject());
			if (sw == null)
				throw new Exception("Could not generate the 'Service Order Add Note' email subject from template!!!");
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
			String eMsg = "There was a MailException sending the 'Service Order Add Note' email for note id "
					+ note.getNoteId() + " for service order "
					+ note.getSoId() + " to " + recipient
					+ " error: " + mailEx.getMessage();
			logger.error(eMsg, mailEx);
			throw new BusinessServiceException(eMsg);
		} catch (Exception e) {
			// simply log it and go on...
			String eMsg = "There was an Exception sending the 'Service Order Add Note' email for note id "
				+ note.getNoteId() + " for service order "
				+ note.getSoId() + " to " + recipient
				+ " error: " + e.getMessage();
			logger.error(eMsg, e);
			throw new BusinessServiceException(eMsg);
		}
	}
}
