/*
 * * EmailTemplateBean.java 1.0 2007/06/05
 */
package com.servicelive.wallet.alert;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.servicelive.common.CommonConstants;
import com.servicelive.common.exception.DataServiceException;
import com.servicelive.wallet.alert.dao.IAlertDao;
import com.servicelive.wallet.alert.vo.AlertTaskVO;
import com.servicelive.wallet.alert.vo.ContactVO;
import com.servicelive.wallet.alert.vo.EmailVO;
import com.servicelive.wallet.alert.vo.TemplateVO;
// TODO: Auto-generated Javadoc
/**
 * Class EmailTemplateBO.
 */
public class EmailTemplateBO extends BaseAlert implements IEmailTemplateBO {

	/** logger. */
	private static final Logger logger = Logger.getLogger(EmailTemplateBO.class.getName());

	/**
	 * getEmailAddressFrom.
	 * 
	 * @return String
	 * 
	 * @throws DataServiceException 
	 */
	private String getEmailAddressFrom() throws DataServiceException {

		return applicationProperties.getPropertyValue(CommonConstants.SERVICELIVE_ADMIN);
	}
	
	private String getEmailAddressTo() throws DataServiceException {

		return applicationProperties.getPropertyValue(CommonConstants.SERVICELIVE_ADMIN);
	}
	
	private IAlertDao alertDao;
	
	public IAlertDao getAlertDao() {
		return alertDao;
	}

	public void setAlertDao(IAlertDao alertDao) {
		this.alertDao = alertDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.servicelive.wallet.alert.IEmailTemplateBO#sendAchAckResponseEmail(java.lang.String,
	 *      java.lang.String, int, java.lang.String, java.lang.String,
	 *      java.lang.String)
	 */
	public void sendAchAckResponseEmail(String sentTo, String sentFrom,
			int templateId, String filePath, String reasonCode, String result) {

		try {
			TemplateVO template = templateDao.getTemplateById(templateId);
			StringWriter sw = new StringWriter();
			VelocityContext vContext = new VelocityContext();
			String subject = setLifecycleProperty(template.getSubject());
			vContext.put("reason_code", reasonCode);
			vContext.put("result", result);
			vContext.put("filePath", filePath);
			velocityEngine.evaluate(vContext, sw, "ACH acknowledgment Email",
					template.getSource());
			String emailArray[] = this.splitEmail(sentTo);
			sendGenericEmailWithServiceLiveLogo(emailArray, sentFrom, subject, sw.toString());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.servicelive.wallet.alert.IEmailTemplateBO#sendACHFailureEmailToBuyer(long,
	 *      java.lang.String, java.lang.Double, java.lang.String, int)
	 */
	public void sendACHFailureEmailToBuyer(long buyerId, String transId,
			Double amount, String returnDesc, int templateId) {

		try {
			TemplateVO template = templateDao.getTemplateById(templateId);
			List<ContactVO> contacts = contactDao.getBuyerInformation(buyerId);

			for (ContactVO contact : contacts) {
				try {
					StringWriter sw = new StringWriter();
					Calendar cal = Calendar.getInstance();
					Date date = cal.getTime();

					VelocityContext velocityContext = new VelocityContext();
					velocityContext.put("FNAME", contact.getFirstName());
					velocityContext.put("LNAME", contact.getLastName());
					velocityContext.put("USERNAME", contact.getFirstName()
							+ " " + contact.getLastName());
					velocityContext.put("TRANSACTION_ID", transId);
					velocityContext.put("TRANS_AMOUNT", amount.toString());
					velocityContext.put("RETURN_CODE_DESC", returnDesc);
					velocityContext.put("CURRENT_DATE", date.toString());
					velocityContext
							.put(
									"SERVICE_URL",
									"http://"
											+ getSiteUrl()
											+ "/termsAndConditions_displayBucksAgreement.action");
					velocityEngine.evaluate(velocityContext, sw,
							"ACH Failure Email", template.getSource());
					// send email with velocity for warning
					String emailArray[] = this.splitEmail(contact
							.getEmailAddress());
					sendGenericEmailWithServiceLiveLogo(emailArray, template
							.getTemplateFrom(), template.getSubject(), sw
							.toString());
				} catch (Exception e1) {
					logger.error(e1.getMessage(), e1);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.alert.IEmailTemplateBO#sendAcknowledgmentNotificationEmail(java.lang.String)
	 */
	public void sendAcknowledgmentNotificationEmail(String sentToAddress, String mailBody) {

		MimeMessage message = null;
		try {
			message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(CommonConstants.SERVICE_LIVE_MAILID_SUPPORT);
			helper.setTo(sentToAddress);
			helper.setSubject(setLifecycleProperty(CommonConstants.ACKNOWLEDGMENT_PROCESS_FAILURE_SUBJECT));
			helper.setText(mailBody);
		} catch (MessagingException e) {
			logger.error(e.getMessage(), e);
		}
		mailSender.send(message);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.alert.IEmailTemplateBO#sendFailureEmail(java.lang.String)
	 */
	public void sendFailureEmail(String bodyText) {

		MimeMessage message = null;
		try {
			message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(CommonConstants.SERVICE_LIVE_MAILID);
	        helper.setTo(getEmailAddressTo());
			helper.setSubject(setLifecycleProperty(CommonConstants.EMAIL_PROCESS_FAILURE_SUBJECT));
			helper.setText(bodyText);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		mailSender.send(message);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.servicelive.wallet.alert.IEmailTemplateBO#sendGenericEmailWithAttachment(java.lang.String[],
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public void sendGenericEmailWithAttachment(String[] sentTo,
			String sentFrom, String subject, String body, String filePath,
			String fileName) {

		MimeMessage message = null;
		try {
			message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(sentFrom);
			helper.setTo(sentTo);
			helper.setSubject(subject);
			helper.setText(body, true);
			helper.addAttachment(fileName, new File(filePath));

		} catch (MessagingException e) {
			logger.error(e.getMessage(), e);
		}
		mailSender.send(message);
	}

	/**
	 * sendGenericEmailWithServiceLiveLogo.
	 * 
	 * @param emailTo 
	 * @param emailFrom 
	 * @param emailSubject 
	 * @param emailText 
	 * 
	 * @return void
	 */
	public void sendGenericEmailWithServiceLiveLogo(String emailTo[],
			String emailFrom, String emailSubject, String emailText) {

		MimeMessage message = null;
		try {
			message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(emailFrom);
			helper.setTo(emailTo);
			helper.setSubject(emailSubject);
			helper.setText(emailText, true);
			helper.addInline("emailLogo", getServiceLiveEmailImg());

		} catch (MessagingException e) {
			logger.error(e.getMessage(), e);
		}
		mailSender.send(message);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.servicelive.wallet.alert.IEmailTemplateBO#sendNotificationEmail(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public void sendNotificationEmail(String sentToAddress, String subject, String text) {

		MimeMessage message = null;
		try {
			
			message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(CommonConstants.SERVICE_LIVE_MAILID_SUPPORT);
			helper.setTo(sentToAddress);
			helper.setSubject(setLifecycleProperty(subject));
			helper.setText(text);
		} catch (MessagingException e) {
			logger.error(e.getMessage(), e);
		}
		mailSender.send(message);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.alert.IEmailTemplateBO#sendPTDFileProcessAlert(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void sendPTDFileProcessAlert(String alertToAddress, String alertCCAddress, String subject, String bodyText) {

		MimeMessage message = null;
		try {
			message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(getEmailAddressFrom());
			helper.setTo(alertToAddress);
			helper.setSubject(setLifecycleProperty(subject));
			helper.setText(bodyText);
		} catch (MessagingException e) {
			logger.error(e.getMessage(), e);
		} catch (DataServiceException e) {
			logger.error(e.getMessage(), e);
		}
		mailSender.send(message);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.alert.IEmailTemplateBO#sendTemplateEmail(java.lang.String, java.lang.String, int)
	 */
	public void sendTemplateEmail(String sentTo, String sentFrom, int templateId) {

		try {
			TemplateVO template = templateDao.getTemplateById(templateId);
			Calendar cal = Calendar.getInstance();
			Date date = cal.getTime();
			StringWriter sw = new StringWriter();
			VelocityContext vContext = new VelocityContext();
			
			vContext.put("date", date.toString());
			velocityEngine.evaluate(vContext, sw, "Failure Email", template
					.getSource());
			String emailArray[] = this.splitEmail(sentTo);
			String subject = setLifecycleProperty(template.getSubject());
			sendGenericEmailWithServiceLiveLogo(emailArray, sentFrom, subject, sw.toString());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.servicelive.wallet.alert.IEmailTemplateBO#sendTemplateEmail(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String, int)
	 */
	public void sendTemplateEmail(String sentTo, String sentFrom,
			String subject, String evaluateAs, int templateId) {

		try {
			TemplateVO template = templateDao.getTemplateById(templateId);

			Calendar cal = Calendar.getInstance();
			Date date = cal.getTime();
			StringWriter sw = new StringWriter();
			VelocityContext vContext = new VelocityContext();

			vContext.put("date", date.toString());
			velocityEngine.evaluate(vContext, sw, evaluateAs, template
					.getSource());
			String emailArray[] = this.splitEmail(sentTo);
			sendGenericEmailWithServiceLiveLogo(emailArray, sentFrom, subject,
					sw.toString());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * splitEmail.
	 * 
	 * @param emailId 
	 * 
	 * @return String[]
	 */
	public String[] splitEmail(String emailId) {

		return StringUtils.split(StringUtils.deleteWhitespace(emailId), ';');
	}
	
	public void sendWithdrawConfirmationEmailAndSMS(Integer companyId, double amount, Integer ledgerId, boolean buyerFlag) {
		try {
			EmailVO emailVO = new EmailVO();
			TemplateVO template = new TemplateVO();
			List<ContactVO> contactList = contactDao.getProviderInformation(companyId.longValue());
			ContactVO primaryContact = null;
			ContactVO contactVO = null;
			String toEmailAddress = "";

			template = templateDao.getTemplateById(CommonConstants.EMAIL_TEMPLATE_PROVIDER_WITHDRAW_FUNDS);
			for (int i = 0; i < contactList.size(); i++) {
				contactVO = contactList.get(i);
				if (contactVO.getEmailAddress() != null) {
					if (toEmailAddress != null && toEmailAddress.length() > 0)
						toEmailAddress = toEmailAddress + ";" + contactVO.getEmailAddress();
					else
						toEmailAddress = contactVO.getEmailAddress();
				}
				if (contactVO.getPrimaryInd() == 1)
					primaryContact = contactVO;
			}
			emailVO.setSubject(template.getSubject());
			emailVO.setTo(toEmailAddress);
			emailVO.setFrom(CommonConstants.SERVICELIVE_ADMIN);
			emailVO.setMessage(template.getSource());
			emailVO.setFirstName(primaryContact.getFirstName());
			emailVO.setLastName(primaryContact.getLastName());
			emailVO.setTemplateId(template.getTemplateId());
			sendWithdrawConfirmEmail(template, emailVO, ledgerId.toString(), amount, applicationProperties.getPropertyValue(CommonConstants.FM_RELEASE_DATE), CommonConstants.EMAIL_TEMPLATE_ACH_PROVIDER_WITHDRAW_FUNDS);
		} catch (Exception e) {
			logger.error("Exception thrown while getting template details or Sending Mail", e);
			e.printStackTrace();
		}

	}

	public void sendWithdrawConfirmEmail(TemplateVO template, EmailVO email, String ledgerId, Double amount, String fmReleaseDate, int eid) throws MessagingException, IOException, DataServiceException {
		HashMap<String, Object> vContext = new AlertHashMap();
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		vContext.put("FNAME", email.getFirstName());
		vContext.put("LNAME", email.getLastName());
		vContext.put("USERNAME", email.getFirstName() + " " + email.getLastName());
		vContext.put("LEDGER_ID", ledgerId);
		vContext.put("TRANS_AMOUNT", "$" + amount.toString());
		vContext.put("FM_RELEASE_DATE", fmReleaseDate);
		vContext.put("CURRENT_DATE", date.toString());
		vContext.put("SERVICE_URL", "http://" + getSiteUrl() + "/termsAndConditions_displayBucksAgreement.action");
		vContext.put(CommonConstants.AOP_USER_EMAIL, email.getTo());
		vContext.put(CommonConstants.AOP_TEMPLATE_ID,email.getTemplateId());	
		vContext.put("InputValue",vContext.toString());

		AlertTaskVO alert = getAlertTaskForSendWithdraw(template, vContext);
		alertDao.addAlertToQueue(alert);
	}
	
	
	private AlertTaskVO getAlertTaskForSendWithdraw(TemplateVO template, Map<String, Object> aopHashMap) {
		Integer templateId = template.getTemplateId();
		AlertTaskVO alertTask = new AlertTaskVO();
		Date date = new Date();

		alertTask.setAlertedTimestamp(null);
		alertTask.setAlertTypeId(template.getTemplateTypeId());
		alertTask.setCreatedDate(date);
		alertTask.setModifiedDate(date);
		alertTask.setTemplateId(templateId);
		if (template.getTemplateFrom() != null)
			alertTask.setAlertFrom(template.getTemplateFrom());
		else
			alertTask.setAlertFrom(CommonConstants.SERVICE_LIVE_MAILID);
		alertTask.setAlertTo((String) aopHashMap.get(CommonConstants.AOP_USER_EMAIL));
		alertTask.setAlertBcc("");
		alertTask.setAlertCc("");
		alertTask.setCompletionIndicator(CommonConstants.INCOMPLETE_INDICATOR);
		alertTask.setPriority("1");
		alertTask.setTemplateInputValue((String) aopHashMap.get("InputValue"));
		logger.info(alertTask.getTemplateInputValue().toString());
		return alertTask;
	}
	
	/**
	 * sendFinanceBatchAlert.
	 * 
	 * @param emailSubject 
	 * @param emailBody
	 */
	 public void sendFinanceBatchAlert(String emailSubject,String emailBody) {
	  		MimeMessage message = null;
	        try {
	            message = mailSender.createMimeMessage();
	            MimeMessageHelper helper = new MimeMessageHelper(message, true);
	            helper.setFrom(CommonConstants.SERVICE_LIVE_MAILID);
	            helper.setTo(getEmailAddressTo());
	            helper.setSubject(emailSubject);
	            helper.setText(emailBody);
	            } catch (Exception e) {
	            e.printStackTrace();
	        }
	        mailSender.send(message);
	  	}
	 
	 private String setLifecycleProperty(String subject)
	 {
		 String slAppLifeCycle = System.getenv("sl_app_lifecycle");
		 String appLifeCycleSubject = subject + " in " + slAppLifeCycle + " environment";
		 return appLifeCycleSubject;
	 }
}// end class
