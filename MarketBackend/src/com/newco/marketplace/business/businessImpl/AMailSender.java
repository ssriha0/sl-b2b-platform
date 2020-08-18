package com.newco.marketplace.business.businessImpl;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.persistence.iDao.template.TemplateDao;
import com.newco.marketplace.webservices.base.Template;

public abstract class AMailSender {

	private JavaMailSenderImpl mailSender;
	private String emailImgPath;
	private Template emailTemplate;
	private TemplateDao templateDao;
	private VelocityEngine velocityEngine;

	abstract public void send(String recipient, Object obj)
			throws BusinessServiceException;
	
	// lookup the email template from the DB
	// results are cached using IBATIS with a 24 hour flush interval...
	protected void setupTemplate() throws Exception {
		Integer typeId = emailTemplate.getTemplateTypeId();
		String name = emailTemplate.getTemplateName();  
		emailTemplate = getTemplateDao().query(getEmailTemplate());
		if (emailTemplate == null)
			throw new Exception(
					"Could not retrieve the template from DB for template type id: "
							+ typeId
							+ " template name: "
							+ name);
	}

	public Template getEmailTemplate() {
		return emailTemplate;
	}

	public void setEmailTemplate(Template emailTemplate) {
		this.emailTemplate = emailTemplate;
	}

	public JavaMailSenderImpl getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}

	public TemplateDao getTemplateDao() {
		return templateDao;
	}

	public void setTemplateDao(TemplateDao templateDao) {
		this.templateDao = templateDao;
	}

	public VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	public String getEmailImgPath() {
		return emailImgPath;
	}

	public void setEmailImgPath(String emailImgPath) {
		this.emailImgPath = emailImgPath;
	}
	
	protected ClassPathResource getEmailImg() throws Exception {
		try {
			ClassPathResource res = new ClassPathResource(emailImgPath);
			return res;
		}
		catch(Exception e) {
			throw new Exception("Cannot load email image resource: " + emailImgPath);
		}
	}
}
