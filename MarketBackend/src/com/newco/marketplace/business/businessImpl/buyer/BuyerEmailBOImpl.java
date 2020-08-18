package com.newco.marketplace.business.businessImpl.buyer;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.newco.marketplace.business.businessImpl.common.EmailBOUtil;
import com.newco.marketplace.business.businessImpl.provider.ABaseBO;
import com.newco.marketplace.business.businessImpl.provider.EmailTemplateBOImpl;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerEmailBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.buyer.BuyerUserProfile;
import com.newco.marketplace.email.EmailService;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.persistence.iDao.provider.ITemplateDao;
import com.newco.marketplace.util.PropertiesUtils;
import com.newco.marketplace.vo.provider.TMBackgroundCheckVO;
import com.newco.marketplace.vo.provider.TemplateVo;

public class BuyerEmailBOImpl extends ABaseBO implements IBuyerEmailBO {
	private JavaMailSenderImpl mailSender;
	private SimpleMailMessage message;
	private Resource fileResource;
	private String plusOneURL;
	private String websiteHomePage;
	private VelocityEngine velocityEngine = null;
	private ITemplateDao templateDao = null;
	private EmailTemplateBOImpl emailTemplateBean = null;
	ClassPathResource emailImg = new ClassPathResource("/images/icon_logo.gif");
	ClassPathResource backgroundCheckImg = new ClassPathResource(
			"/images/backgroundCheckLogo.jpg");   

	private static String _serviceLiveURL = null;
	
	private static final Logger logger = Logger.getLogger(BuyerEmailBOImpl.class);
	EmailService emailService;

	private String getServiceLiveUrl() {
		if(_serviceLiveURL == null) {
			_serviceLiveURL = PropertiesUtils.getPropertyValue(Constants.AppPropConstants.SERVICELIVE_URL);
		}
		return _serviceLiveURL;
	}

	public void setMessage(SimpleMailMessage message) {
		this.message = message;
	}

	public BuyerEmailBOImpl() {

	}

	public void sendConfirmationMail(TemplateVo template, String username, String password, String deeplinkKey,
			String email1,String email2) throws MessagingException, IOException {
		if (template.getTemplateEid() == null) {
			StringWriter sw = new StringWriter();
			VelocityContext vContext = new VelocityContext();
			vContext.put("Username", username);
			vContext.put("Password", password);
			vContext.put("WebsiteURL", getServiceLiveUrl());
			getVelocityEngine().evaluate(vContext, sw, "Buyer Registration Email",
					template.getTemplateSource());
			getEmailTemplateBean().sendGenericEmailWithLogoWithCc(email1,
					template.getTemplateFrom(), template.getTemplateSubject(),
					sw.toString(),email2);
		}
		else{
			Map<String, String>parameters = new  HashMap<String, String>();
			parameters.put("USERNAME", username);
			parameters.put("PASSWORD", password);
			parameters.put("email", email1);
			//parameters.put("WEBSITEURL", serviceLiveURL);
			String deeplink = EmailBOUtil.createDeepLinkForPassword(getServiceLiveUrl(), deeplinkKey);
			parameters.put("WEBSITEURL", deeplink);
			emailService.send(template.getTemplateEid(), parameters);
		}
		logger.info("Registration email confirmation sent to " + email1);
		
	}
	
	public void sendConfirmationMailForInValidState(String username, String password,
			String email) throws MessagingException, IOException {
		// logger.debug ("entering email ************************");
		TemplateVo template = null;
		try {
			template = getTemplateDao().getTemplate(
					MPConstants.TEMPLATE_INVALID_STATE_CONFIRM_EMAIL);
			// logger.debug("****************************** " + template);
		} catch (DBException e) {
			new BusinessServiceException(e);
		}
		StringWriter sw = new StringWriter();
		VelocityContext vContext = new VelocityContext();
		vContext.put("Username", username);
		vContext.put("WebsiteURL", getServiceLiveUrl());
		getVelocityEngine().evaluate(vContext, sw, "Registration Email",
				template.getTemplateSource());
		getEmailTemplateBean().sendGenericEmailWithLogo(email,
				template.getTemplateFrom(), template.getTemplateSubject(),
				sw.toString());
		logger.info("email SENT************************");
	}
	public void sendConfirmationPasswordMail(String username, String password,
			String email,String ccArr[]) throws MessagingException, IOException {
		TemplateVo template = null;
		try {
			template = getTemplateDao().getTemplate(
					MPConstants.TEMPLATE_NAME_RESET_PASSWORD);
		} catch (DBException e) {
			new BusinessServiceException(e);
		}
		StringWriter sw = new StringWriter();
		VelocityContext vContext = new VelocityContext();
		vContext.put("UserName", username);
		vContext.put("Password", password);
		vContext.put("WebsiteURL", getServiceLiveUrl());
		getVelocityEngine().evaluate(vContext, sw, "Registration Email",
				template.getTemplateSource());
		if(ccArr != null)
			getEmailTemplateBean().sendGenericEmailWithLogoWithCc(email,
					template.getTemplateFrom(), template.getTemplateSubject(),
					sw.toString(),ccArr);
		else
			getEmailTemplateBean().sendGenericEmailWithLogo(email,
					template.getTemplateFrom(), template.getTemplateSubject(),
					sw.toString());

		logger.info("email SENT************************");
	}


	public void sendBackgroundCheckEmail(TMBackgroundCheckVO tmbcVO)
			throws BusinessServiceException, IOException {
		TemplateVo template = null;
		String [] temp = null;
		try {
			template = getTemplateDao().getTemplate(
					MPConstants.TEMPLATE_NAME_BACKGROUND_CHECK_EMAIL);
		} catch (DBException e) {
			new BusinessServiceException(e);
		}
		StringWriter sw = new StringWriter();
		VelocityContext vContext = new VelocityContext();
		vContext.put("tmBackgroundVO", tmbcVO);
		vContext.put("plusOneURL", plusOneURL);
		getVelocityEngine().evaluate(vContext, sw, "Background Check Email",
				template.getTemplateSource());
		temp = tmbcVO.getCcArr();
		/*
		 * paugus2 - If there is a CC email address then email will be sent with CC.
		 */
		if(tmbcVO != null && temp != null)
			getEmailTemplateBean().sendGenericEmailWithLogoWithCc(tmbcVO.getResourceEmail(), template.getTemplateFrom(), template.getTemplateSubject(), sw.toString(), tmbcVO.getCcArr());
		else
			getEmailTemplateBean().sendGenericEmailWithLogoWithoutCc(tmbcVO.getResourceEmail(), template.getTemplateFrom(),template.getTemplateSubject(), sw.toString());
	}

	public void sendForgotUsernameMail(String username, String email,String ccArr[])
			throws BusinessServiceException, IOException {
		logger.info("EmailBean: sendForgotUsernameMail");
		TemplateVo template = null;
		try {
			template = getTemplateDao().getTemplate(
					MPConstants.TEMPLATE_NAME_FORGOT_USERNAME_EMAIL);
			// logger.debug("****************************** " + template);
		} catch (DBException e) {
			throw new BusinessServiceException(e);
		}
		StringWriter sw = new StringWriter();
		VelocityContext vContext = new VelocityContext();
		vContext.put("username", username);
		vContext.put("WebsiteURL", getServiceLiveUrl());
		getVelocityEngine().evaluate(vContext, sw, "Forgot Username",
				template.getTemplateSource());
		if(ccArr !=null)
		{
			getEmailTemplateBean().sendGenericEmailWithLogoWithCc(email,
				template.getTemplateFrom(), template.getTemplateSubject(),
				sw.toString(),ccArr);
		}
		else
		{
			getEmailTemplateBean().sendGenericEmailWithLogo(email,
					template.getTemplateFrom(), template.getTemplateSubject(),
					sw.toString());
		}
	}// sendForgotUsernameMail

	public void sendGenericEmail(String recipient, String from, String text)
			throws BusinessServiceException {
		try {
			JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
			mailSender.setHost(PropertiesUtils.getPropertyValue("smtp_server"));
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper mimeHelper = new MimeMessageHelper(mimeMessage,
					true);
			mimeHelper.setText(text, true);
			mimeHelper.setSubject("Service Order Notification");
			if (recipient != null) {
				mimeHelper.setTo(recipient);
			}
			logger.info("helper txt: " + mimeHelper.toString());
			try {
				mimeHelper.setFrom(from);
				mailSender.send(mimeMessage);
			} catch (MailException ex) {
				throw new BusinessServiceException(ex.getMessage(), ex);
			}
		} catch (MailException e) {
			throw new BusinessServiceException(e.getMessage(), e);
		} catch (MessagingException e) {
			throw new BusinessServiceException(e.getMessage(), e);
		}

	}// sendBackGroundCheckEMail

	public void sendBackgroundCheckEmailWithLogo(String emailTo,
			String emailFrom, String emailSubject, String emailText) {
		MimeMessage message = null;
		try {
			message = getMailSender().createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(emailFrom);
			helper.setTo(emailTo);
			helper.setSubject(emailSubject);
			helper.setText(emailText, true);
			helper.addInline("backgroundCheckLogo", backgroundCheckImg);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		getMailSender().send(message);
	}
	
	

	/*
	 * Added by paugus2 for service Buyer registration confirmation email
	 */
	public void sendServiceProviderRegistrationConfirmationMail(String username, String password,
			String email) throws MessagingException, IOException {
		TemplateVo template = null;
		try {
			template = getTemplateDao().getTemplate(
					MPConstants.TEMPLATE_NAME_REGISTRATION_EMAIL_TEAMMEMBER);
		} catch (DBException e) {
			new BusinessServiceException(e);
		}
		StringWriter sw = new StringWriter();
		VelocityContext vContext = new VelocityContext();
		vContext.put("Username", username);
		vContext.put("Password", password);
		vContext.put("WebsiteURL", getServiceLiveUrl());
		getVelocityEngine().evaluate(vContext, sw, "Registration Email",
				template.getTemplateSource());
		getEmailTemplateBean().sendGenericEmailWithLogo(email,
				template.getTemplateFrom(), template.getTemplateSubject(),
				sw.toString());
		logger.info("email SENT************************");
	}	
	
	public JavaMailSenderImpl getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}

	public Resource getFileResource() {
		return fileResource;
	}

	public void setFileResource(Resource fileResource) {
		this.fileResource = fileResource;
	}

	public String getPlusOneURL() {
		return plusOneURL;
	}

	public void setPlusOneURL(String plusOneURL) {
		this.plusOneURL = plusOneURL;
	}

	public ITemplateDao getTemplateDao() {
		return templateDao; 
	}

	public void setTemplateDao(ITemplateDao templateDao) {
		this.templateDao = templateDao;
	}

	public EmailTemplateBOImpl getEmailTemplateBean() {
		return emailTemplateBean;
	}

	public void setEmailTemplateBean(EmailTemplateBOImpl emailTemplateBean) {
		this.emailTemplateBean = emailTemplateBean;
	}

	public VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	public String getWebsiteHomePage() {
		return websiteHomePage;
	}

	public void setWebsiteHomePage(String websiteHomePage) {
		this.websiteHomePage = websiteHomePage;
	}
	
	//Added by paugus2 for sending EMAIL when TEAM MEMBER registration is done
	public void sendConfirmationMailTeamMember(String username, String password,
			String email) throws MessagingException, IOException {
		TemplateVo template = null;
		try {
			template = getTemplateDao().getTemplate(
					MPConstants.TEMPLATE_NAME_REGISTRATION_EMAIL_TEAMMEMBER);
		} catch (DBException e) {
			new BusinessServiceException(e);
		}
		StringWriter sw = new StringWriter();
		VelocityContext vContext = new VelocityContext();
		vContext.put("Username", username);
		vContext.put("Password", password);
		vContext.put("WebsiteURL", getServiceLiveUrl());
		getVelocityEngine().evaluate(vContext, sw, "Registration Email",
				template.getTemplateSource());
		getEmailTemplateBean().sendGenericEmailWithLogo(email,
				template.getTemplateFrom(), template.getTemplateSubject(),
				sw.toString());
		logger.info("email SENT************************");
	}
	
	/*
	 * Added to send email for new Buyer User.
	 * It will send the new user's UserName, Password, ServiceLive Id(Resource Id).
	 * Company Name, Company Id, User's firm administrator's First Name, Last Name, Email and his ServiceLive Id
	 * Changes to incorporate alternate email of resource also to be copied
	 */
	public void sendProviderMemberRegistrationConfirmationMail(String username, String password, 
			String email,String altEmail, Integer resourceId, BuyerUserProfile userProfile) throws BusinessServiceException, IOException
	{
		TemplateVo template = null;
		String ccEmail = null;
		try
		{
			template = getTemplateDao().getTemplate(
					MPConstants.TEMPLATE_NAME_PROVIDER_MEMBER_CONF_MAIL);
			if (null != userProfile)
			{
				StringWriter sw = new StringWriter();
				VelocityContext vContext = new VelocityContext();
				vContext.put("CompanyName", userProfile.getBusinessName());
				vContext.put("CompanyId", userProfile.getVendorId());
				vContext.put("ProvAdminFirstName", userProfile.getFirstName());
				vContext.put("ProvAdminLastName", userProfile.getLastName());
				vContext.put("ProvAdminUserId", userProfile.getResourceId());
				vContext.put("ProvAdminEmail", userProfile.getEmail());
				vContext.put("UserName", username);
				vContext.put("password", password);
				vContext.put("UserId", resourceId);
				vContext.put("WebsiteURL", getServiceLiveUrl());
				
				getVelocityEngine().evaluate(vContext, sw, "Provider Team Member Registration Email",
						template.getTemplateSource());
				
				//Gets the Buyer Admin's Email Id
				ccEmail = userProfile.getEmail().trim();
				//If the Buyer Email id and New User Id is same then 
				//there will be only TO else
				//new user email id will be in TO and Buyer Email id is in CC
				if (ccEmail.equalsIgnoreCase(email))
				{	
					if(altEmail.equalsIgnoreCase(email))
					{
						getEmailTemplateBean().sendGenericEmailWithLogo(email,
								template.getTemplateFrom(), template.getTemplateSubject(),
								sw.toString());
					}
					else
					{
						String ccArr[] = new String[1];
						ccArr[0]=new String(altEmail);
						getEmailTemplateBean().sendGenericEmailWithLogoWithCc(email, 
								template.getTemplateFrom(), template.getTemplateSubject(), 
								sw.toString(), ccArr);
					}
				}
				else
				{
					if(altEmail.equalsIgnoreCase(email))
					{
						String ccArr[] = new String[1];
						ccArr[0]=new String(email);
						getEmailTemplateBean().sendGenericEmailWithLogoWithCc(email, 
							template.getTemplateFrom(), template.getTemplateSubject(), 
							sw.toString(), ccArr);
					}
					else
					{
						String ccArr[] = new String[2];
						ccArr[0]=new String(ccEmail);
						ccArr[1]=new String(altEmail);
						getEmailTemplateBean().sendGenericEmailWithLogoWithCc(email, 
								template.getTemplateFrom(), template.getTemplateSubject(), 
								sw.toString(), ccArr);
						
					}
				}
			}
		}catch(DBException a_Ex)
		{
			throw new BusinessServiceException(a_Ex.getMessage());
		}catch(Exception a_Ex)
		{
			throw new BusinessServiceException(a_Ex.getMessage());
		}
	}

	public void sendConfirmationMail(String username, String password,
			String email) throws MessagingException, IOException {
		
	}

	/**
	 * @return the emailService
	 */
	public EmailService getEmailService() {
		return emailService;
	}

	/**
	 * @param emailService the emailService to set
	 */
	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}
}
