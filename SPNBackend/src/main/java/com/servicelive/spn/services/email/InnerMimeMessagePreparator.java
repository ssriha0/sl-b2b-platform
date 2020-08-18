package com.servicelive.spn.services.email;

import static com.servicelive.spn.common.SPNBackendConstants.EMAIL_TO_ADDRESS_SEPERATOR;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Map.Entry;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import com.servicelive.domain.spn.detached.Email;
import com.servicelive.domain.spn.detached.EmailClassPathResource;

public class InnerMimeMessagePreparator implements MimeMessagePreparator {
	protected final   Log _logger  =  LogFactory.getLog(this.getClass());
	private Email _email;
	private VelocityEngine _velocityEngine;
	/**
	 * 
	 * @param email
	 * @param velocityEngine 
	 */
	public InnerMimeMessagePreparator(Email email, VelocityEngine velocityEngine) {
		this._email = email;
		this._velocityEngine = velocityEngine;
	}

	public void prepare(MimeMessage mimeMessage) throws MessagingException {
		MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
		try {
			populateEmailDetails(message, _email);
		} catch (Exception e) {
			_logger.debug(e);
		}
	}

	private void populateEmailDetails(MimeMessageHelper message, Email email) throws Exception{
		String from  = email.getFrom();
		List<String> toList = email.getToList();
		String subject = email.getSubject();
		String txtMsg = email.getMsg();

		if(email.getTemplate() != null) {
			from = StringUtils.isNotBlank(from ) ? from : email.getTemplate().getTemplateFrom() ;
			subject = StringUtils.isNotBlank(subject ) ? subject : email.getTemplate().getTemplateSubject() ;
			txtMsg  = StringUtils.isNotBlank(txtMsg ) ? txtMsg : email.getTemplate().getTemplateSource() ;
			toList  = toList.size() > 0 ? toList : getListOfToAddress(email);
		}


		//Either to or from is empty throw an exception get out this ..
		if(StringUtils.isBlank(from) || toList == null || toList.size() == 0) {throw  new Exception("During sending email *From* or *To* value is missing "); }
		message.setFrom(from);
		for(String to : toList) {
			message.addTo(to);
		}
		//Populate inline elements now
		Map<String, String> model = email.getParams();
		String subjectText = evaluate(subject, model);
		String text =  evaluate(txtMsg, model);
		message.setSubject(subjectText);
		message.setText(text, true);
		populateInLineResources(message,email);
	}

	private String evaluate(String templateString, Map<String, String> model) throws Exception {
		VelocityContext context = new VelocityContext();
		for ( Entry<String, String> entry : model.entrySet()) {
			context.put(entry.getKey(), entry.getValue());
		}
		java.io.StringWriter writer = new java.io.StringWriter();
		_velocityEngine.evaluate(context, writer, "velocity template", templateString);
		return writer.toString();
	}

	/**
	 * 
	 * @param email
	 * @return all the to addresses stored in the database using ';' semicolon as the separator
	 */
	private List<String> getListOfToAddress(Email email) {
		List<String> result = new ArrayList<String> ();
		String src = email.getTemplate().getTemplateTo();
		StringTokenizer tokenizer = new StringTokenizer(src, EMAIL_TO_ADDRESS_SEPERATOR); 
		while(tokenizer.hasMoreTokens()) {
			String toaddress = tokenizer.nextToken();
			result.add(toaddress);
		}
		return result;
	}

	private void populateInLineResources(MimeMessageHelper message, Email email) throws Exception {
		List<EmailClassPathResource> classPathResources = email.getClassPathResources(); 
		for (EmailClassPathResource resource : classPathResources ) {
			ClassPathResource cpresource = new ClassPathResource(resource.getLocationOfResource());
			message.addInline(resource.getNameOfResourceInTemplate(), cpresource);
		}
	}
}

