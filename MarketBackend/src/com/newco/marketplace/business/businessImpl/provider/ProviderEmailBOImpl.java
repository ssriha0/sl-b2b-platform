package com.newco.marketplace.business.businessImpl.provider;

import java.io.IOException;
import java.io.StringWriter;
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

import com.newco.marketplace.aop.AOPHashMap;
import com.newco.marketplace.business.businessImpl.common.EmailBOUtil;
import com.newco.marketplace.business.iBusiness.provider.IProviderEmailBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.AOPConstants;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.persistence.iDao.provider.ITemplateDao;
import com.newco.marketplace.util.PropertiesUtils;
import com.newco.marketplace.vo.provider.TMBackgroundCheckVO;
import com.newco.marketplace.vo.provider.TemplateVo;
import com.newco.marketplace.vo.provider.UserProfile;
import com.newco.marketplace.webservices.base.Template;

public class ProviderEmailBOImpl extends ABaseBO implements IProviderEmailBO {
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
	
	private String getServiceLiveUrl() {
		if(_serviceLiveURL == null) {
			_serviceLiveURL = PropertiesUtils.getPropertyValue(Constants.AppPropConstants.SERVICELIVE_URL);
		}
		return _serviceLiveURL;
	}

	private static final Logger logger = Logger
			.getLogger(ProviderEmailBOImpl.class.getName());

	public void setMessage(SimpleMailMessage message) {

		this.message = message;
	}

	public ProviderEmailBOImpl() {

	}

	public void sendEmail(String email) throws MessagingException {
		// user and position objects looked up...
		// SimpleMailMessage msg = new SimpleMailMessage(this.message);
		message.setTo(email);
		System.out
				.println("------------ ProviderEmailBean -----sendEmail() START");
		System.out
				.println("------------ ProviderEmailBean -----sendEmail() START");
		StringBuffer txt = new StringBuffer();
		txt.append("Coming soon");
		message.setText(txt.toString());
		try {
			mailSender.send(message);
		} catch (MailException ex) {
			ex.printStackTrace();
		}
		System.out
				.println("------------ ProviderEmailBean -----sendEmail() END");
		System.out
				.println("------------ ProviderEmailBean -----sendEmail() END");
	}

	public void sendConfirmationMail(String username, String password,
			String email,String firstName) throws MessagingException, IOException {
		// logger.debug ("entering email ************************");
		TemplateVo template = null;
		try {
			template = getTemplateDao().getTemplate(MPConstants.TEMPLATE_NAME_REGISTRATION_EMAIL);
			// logger.debug("****************************** " + template);
		} catch (DBException e) {
			new BusinessServiceException(e);
		}
		StringWriter sw = new StringWriter();
		Map<String, Object> emailMap = new AOPHashMap();
		
		emailMap.put("USERNAME", username);
		emailMap.put("PASSWORD", password);
		emailMap.put("WebsiteURL",getServiceLiveUrl());
		emailMap.put("FIRSTNAME",firstName);

		Template templateAT = new Template();
		templateAT.setTemplateId(template.getTemplateId());
		templateAT.setTemplateTypeId(template.getTemplateTypeId());
		templateAT.setPriority(template.getPriority());
		templateAT.setEid(template.getTemplateEid());
		templateAT.setTemplateFrom(template.getTemplateFrom());
		templateAT.setSource(template.getTemplateSource());
		templateAT.setTemplateFrom(template.getTemplateFrom());
		
		emailMap.put(AOPConstants.AOP_USER_EMAIL, email);
		emailMap.put(AOPConstants.AOP_TEMPLATE_ID,template.getTemplateId());
		//Commenting for Adobe email api issue for eid - 90037 - wrong data in alertask table
		//emailMap.put("InputValue",emailMap.toString());
		//emailMap.put(template.getTemplateId().toString(),templateAT);
		
		try{
			getEmailTemplateBean().sendConfirmationEmail(emailMap);
		}catch(DataServiceException e){
			logger.info(e.getMessage());
			throw (new MessagingException());
		}
		logger.info("email SENT************************");
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
		vContext.put("USERNAME", username);
		vContext.put("WEBSITEURL", getServiceLiveUrl());
		getVelocityEngine().evaluate(vContext, sw, "Registration Email",
				template.getTemplateSource());
		// logger.debug("*********************** template source is : " +
		// template.getTemplateSource());
		getEmailTemplateBean().sendGenericEmailWithLogo(email,
				template.getTemplateFrom(), template.getTemplateSubject(),
				sw.toString());
		logger.info("Registration Emai has been sent");
	}
	public void sendConfirmationPasswordMail(String username, String password,
			String email,String ccArr[]) throws MessagingException, IOException {
		// logger.debug ("entering email ************************");
		TemplateVo template = null;
		try {
			template = getTemplateDao().getTemplate(
					MPConstants.TEMPLATE_NAME_RESET_PASSWORD);
			// logger.debug("****************************** " + template);
		} catch (DBException e) {
			new BusinessServiceException(e);
		}
		StringWriter sw = new StringWriter();
		VelocityContext vContext = new VelocityContext();
		vContext.put("USERNAME", username);
		vContext.put("PASSWORD", password);

		String deeplink = EmailBOUtil.createDeepLinkForPassword(getServiceLiveUrl(), password);
		vContext.put("WEBSITEURL", deeplink);
		getVelocityEngine().evaluate(vContext, sw, "Registration Email",
				template.getTemplateSource());
		// logger.debug("*********************** template source is : " +
		// template.getTemplateSource());
		if(ccArr != null)
			getEmailTemplateBean().sendGenericEmailWithLogoWithCc(email,
					template.getTemplateFrom(), template.getTemplateSubject(),
					sw.toString(),ccArr);
		else
			getEmailTemplateBean().sendGenericEmailWithServiceLiveLogo(email,
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
		 * bnatara - If there is a CC email address then email will be sent with CC.
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
		String deeplink="?key="+username;
		vContext.put("USERNAME", username);
		vContext.put("WEBSITEURL", getServiceLiveUrl() + deeplink);
		getVelocityEngine().evaluate(vContext, sw, "Forgot Username",
				template.getTemplateSource());
		//System.out.println("Calling sendForgotUsernameMail ==========================================>>>>>>> ");
		
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
		// logger.info("email SENT************************");
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
			// logger.info(StackTraceHelper.getStackTrace(e));
			throw new BusinessServiceException(e.getMessage(), e);
		} catch (MessagingException e) {
			// logger.debug(StackTraceHelper.getStackTrace(e));
			throw new BusinessServiceException(e.getMessage(), e);
		}

	}// sendBackGroundCheckEMail

	public void sendBackgroundCheckEmailWithLogo(String emailTo,
			String emailFrom, String emailSubject, String emailText) {
		// System.out.println("------------ ProviderEmailBean
		// -----sendBackgroundCheckEmailWithLogo() START");
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
		// System.out.println("------------ ProviderEmailBean
		// -----sendBackgroundCheckEmailWithLogo() END");
	}
	
	

	/*
	 * Added by MTedder for service provider registration confirmation email
	 */
	public void sendServiceProviderRegistrationConfirmationMail(String username, String password,
			String email) throws MessagingException, IOException {
		// logger.debug ("entering email ************************");
		TemplateVo template = null;
		try {
			template = getTemplateDao().getTemplate(
					MPConstants.TEMPLATE_NAME_REGISTRATION_EMAIL_TEAMMEMBER);
			// logger.debug("****************************** " + template);
		} catch (DBException e) {
			new BusinessServiceException(e);
		}
		StringWriter sw = new StringWriter();
		VelocityContext vContext = new VelocityContext();
		vContext.put("USERNAME", username);
		vContext.put("PASSWORD", password);
		vContext.put("WEBSITEURL", getServiceLiveUrl());
		getVelocityEngine().evaluate(vContext, sw, "Registration Email",
				template.getTemplateSource());
		// logger.debug("*********************** template source is : " +
		// template.getTemplateSource());
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
	
	//Added by Mayank for sending EMAIL when TEAM MEMBER registration is done
	public void sendConfirmationMailTeamMember(String username, String password,
			String email) throws MessagingException, IOException {
		// logger.debug ("entering email ************************");
		System.out.println("IN calling sendEmailTeamMemberRegistrationdEMAIL- ProviderEmailBOImpl");
		TemplateVo template = null;
		try {
			template = getTemplateDao().getTemplate(
					MPConstants.TEMPLATE_NAME_REGISTRATION_EMAIL_TEAMMEMBER);
			// logger.debug("****************************** " + template);
		} catch (DBException e) {
			new BusinessServiceException(e);
		}
		StringWriter sw = new StringWriter();
		VelocityContext vContext = new VelocityContext();
		vContext.put("USERNAME", username);
		vContext.put("PASSWORD", password);
		vContext.put("WEBSITEURL", getServiceLiveUrl());
		getVelocityEngine().evaluate(vContext, sw, "Registration Email",
				template.getTemplateSource());
		// logger.debug("*********************** template source is : " +
		// template.getTemplateSource());
		getEmailTemplateBean().sendGenericEmailWithLogo(email,
				template.getTemplateFrom(), template.getTemplateSubject(),
				sw.toString());
		logger.info("email SENT************************");
	}//ENd of method sendConfirmationMailTeamMember
	//End of changes
	
	/*
	 * Added to send email for new Provider User.
	 * It will send the new user's UserName, Password, ServiceLive Id(Resource Id).
	 * Company Name, Company Id, User's firm administrator's First Name, Last Name, Email and his ServiceLive Id
	 * Changes to incorporate alternate email of resource also to be copied
	 */
	public void sendProviderMemberRegistrationConfirmationMail(String username, String password, 
			String email,String altEmail, Integer resourceId, UserProfile userProfile) throws BusinessServiceException, IOException
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
				
				//Gets the Provider Admin's Email Id
				ccEmail = userProfile.getEmail().trim();
				//If the Provider Email id and New User Id is same then 
				//there will be only TO else
				//new user email id will be in TO and Provider Email id is in CC
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
}
