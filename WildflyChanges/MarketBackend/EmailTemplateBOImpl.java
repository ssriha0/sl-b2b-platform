/*
 * * EmailTemplateBean.java 1.0 2007/06/05
 */
package com.newco.marketplace.business.businessImpl.provider;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import com.newco.marketplace.aop.AOPHashMap;
import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.newco.marketplace.business.iBusiness.provider.IAuditEmailTemplateBO;
import com.newco.marketplace.business.iBusiness.provider.IAuditStates;
import com.newco.marketplace.business.iBusiness.provider.IEmailTemplateBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.EmailVO;
import com.newco.marketplace.dto.vo.fullfillment.FullfillmentEntryVO;
import com.newco.marketplace.dto.vo.ledger.CreditCardVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.email.EmailService;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.AOPConstants;
import com.newco.marketplace.interfaces.AlertConstants;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.daoImpl.alert.AlertDaoImpl;
import com.newco.marketplace.persistence.daoImpl.template.TemplateDaoImpl;
import com.newco.marketplace.util.PropertiesUtils;
import com.newco.marketplace.util.constants.SystemGeneratedEmailConstants;
import com.newco.marketplace.utils.AchConstants;
import com.newco.marketplace.utils.ServiceLiveStringUtils;
import com.newco.marketplace.utils.UIUtils;
import com.newco.marketplace.vo.provider.AuditEmailVo;
import com.newco.marketplace.vo.provider.TemplateVo;
import com.newco.marketplace.webservices.base.Template;
import com.sears.os.vo.SerializableBaseVO;
import com.servicelive.domain.routingrules.detached.RoutingRuleEmailVO;
import com.newco.marketplace.dto.vo.systemgeneratedemail.EmailDataVO;

public class EmailTemplateBOImpl extends ABaseBO  implements IAuditStates, IAuditEmailTemplateBO,IEmailTemplateBO {
	private VelocityEngine velocityEngine = null;
	private JavaMailSender mailSender = null;
	private String logoPath = null;
	private TemplateDaoImpl templateDao;
	private AlertDaoImpl alertDao;
	private static HashMap<Integer, Template> templateCache;
	private EmailService emailService = null;

	ClassPathResource emailImg = new ClassPathResource("resources/images/icon_logo.gif");
	ClassPathResource ServiceLiveEmailImg = new ClassPathResource("resources/images/service_live_logo.gif");
	ClassPathResource verifiedEmailImg = new ClassPathResource("resources/images/verified.gif");
	private static String _emailAddressTo = null;

	private static String _emailAddressFrom = null;

	private static final Logger LOGGER = Logger.getLogger(EmailTemplateBOImpl.class.getName());

	private static String _siteUrl = null;



	public EmailTemplateBOImpl() {
		if (templateCache == null)
			templateCache = new HashMap<Integer, Template>();
	}

	public void sendEmail(final AuditEmailVo auditEmailVo, final String emailText) {
		if (logger.isDebugEnabled()) {
			LOGGER.debug("[AuditEmailTemplateBean]*************************************************"
					+ " Entered sendEmail for AuditEmailtemplate");
		}
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				// MimeMessage mimeMessage = mailSender.createMimeMessage();
				MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
				mimeMessageHelper.setTo(auditEmailVo.getTo());
				mimeMessageHelper.setSubject(auditEmailVo.getSubject());
				mimeMessageHelper.setFrom(auditEmailVo.getFrom());
				mimeMessageHelper.setText(emailText, true);
				mimeMessageHelper.addInline("emailLogo", getEmailImg());
			}
		};
		mailSender.send(preparator);
	}

	public void sendGenericEmailWithLogo (String emailTo, String emailFrom, String emailSubject, String emailText) {
		LOGGER.debug("------------ EmailTemplateBean -----sendGenericEmailWithLogo() START");
		MimeMessage message = null;
		try {
			message = getMailSender().createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(emailFrom);
			helper.setTo(emailTo);
			helper.setSubject(emailSubject);
			helper.setText(emailText, true);
			helper.addInline("emailLogo", getEmailImg());
			if(StringUtils.contains(emailText, "verifiedLogo")){
				helper.addInline("verifiedLogo", getVerifiedEmailImg());
			}
		} catch (MessagingException e) {
			LOGGER.error(e.getMessage(), e);
		}
		getMailSender().send(message);
		LOGGER.debug("------------ EmailTemplateBean -----sendGenericEmailWithLogo() START");
	}

	public void sendGenericEmailWithoutLogo (String emailTo, String emailFrom, String emailSubject, String emailText) {
		LOGGER.debug("------------ EmailTemplateBean -----sendGenericEmailWithoutLogo() START");
		MimeMessage message = null;
		try {
			message = getMailSender().createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			String emailArray[] = this.splitEmail(emailTo);
			helper.setFrom(emailFrom);
			helper.setTo(emailArray);
			helper.setSubject(emailSubject);
			helper.setText(emailText, true);
		} catch (MessagingException e) {
			LOGGER.error(e.getMessage(), e);
		}
		getMailSender().send(message);
		LOGGER.debug("------------ EmailTemplateBean -----sendGenericEmailWithoutLogo() START");
	}

	public void sendNachaFailureEmail (String bodyText) {
		MimeMessage message = null;
		try {
			message = getMailSender().createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(getEmailAddressFrom());
			helper.setTo(getEmailAddressTo());
			helper.setSubject(Constants.EMAIL_ADDRESSES.EMAIL_PROCESS_FAILURE_SUBJECT);
			helper.setText(bodyText);
		} catch (MessagingException e) {
			LOGGER.error(e.getMessage(), e);
		}
		getMailSender().send(message);
	}

	public void sendClosedLoopFileWritingFailureEmail (String bodyText) {
		MimeMessage message = null;
		try {
			message = getMailSender().createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(getEmailAddressFrom());
			helper.setTo(getEmailAddressTo());
			helper.setSubject(Constants.EMAIL_ADDRESSES.CLOSED_LOOP_FILE_WRITE_FAILURE_SUBJECT);
			helper.setText(bodyText);
		} catch (MessagingException e) {
			LOGGER.error(e.getMessage(), e);
		}
		getMailSender().send(message);
	}
	public void sendGenericEmailWithLogoWithCc (String emailTo, String emailFrom, String emailSubject, String emailText,String emailCc) {

		String[] emailArr = new String[] {emailCc};
		sendGenericEmailWithLogoWithCc(emailTo, emailFrom, emailSubject, emailText, emailArr);
	}

	public void sendGenericEmailWithLogoWithCc (String emailTo, String emailFrom, String emailSubject, String emailText,String ccArr[]) {
		LOGGER.debug("------------ EmailTemplateBean -----sendGenericEmailWithLogoWithCc() START");
		MimeMessage message = null;
		try {
			message = getMailSender().createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(emailFrom);
			helper.setTo(emailTo);
			List<String> cleansedEmails = scrubEmails(ccArr);
			if (!cleansedEmails.isEmpty()) {
				helper.setCc(cleansedEmails.toArray(new String[]{}));
			}
			helper.setSubject(emailSubject);
			helper.setText(emailText, true);
			helper.addInline("emailLogo", getEmailImg());
		} catch (MessagingException e) {
			LOGGER.error(e.getMessage(), e);
		}
		getMailSender().send(message);
		LOGGER.debug("------------ EmailTemplateBean -----sendGenericEmailWithLogoWithCc() START");
	}

	private List<String> scrubEmails(String[] ccArr) {
		List<String> emails = new ArrayList<String>();
		if (ccArr != null) {
			for (String ccElem : ccArr) {
				if (StringUtils.isNotBlank(ccElem)) {
					emails.add(ccElem);
				}
			}
		}
		return emails;
	}

	/*
	 * Mayank & MTedder
	 */
	public void sendGenericEmailWithLogoWithoutCc (String emailTo, String emailFrom, String emailSubject, String emailText) {
		String[] emailArr = null ;
		sendGenericEmailWithLogoWithCc(emailTo, emailFrom, emailSubject, emailText, emailArr);
	}

	public void sendFailureGL(EmailVO email)throws MessagingException, IOException
	{
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		StringWriter sw = new StringWriter();
		VelocityContext vContext = new VelocityContext();

		vContext.put("date", date.toString());
		getVelocityEngine().evaluate(vContext, sw, "Failure Email",
				email.getMessage());
		String emailArray[] = this.splitEmail(email.getTo());
		sendGenericEmailWithServiceLiveLogo(emailArray,email.getFrom(), email.getSubject(),sw.toString());

	}


	public void sendACHFailureEmail(EmailVO email, String transId, Double amount, String returnDesc) throws MessagingException, IOException {

		// logger.debug ("entering email ************************");
		StringWriter sw = new StringWriter();
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		NumberFormat formatter = new DecimalFormat("#0.00");
		if(AchConstants.EMAIL_TEMPLATE_PROVIDER_ACH_WITHDRAW_FUNDS_FAILURE == email.getTemplateId()){ 
			//The below three lines are used to send an e-mail using Cheetah for reject code
			/*Map<String, String> emailMap =getEmailMap(vContext);   		
			emailMap.put("email", email.getTo());
			cheetahEmailService.send(AchConstants.EMAIL_ACH_FAILURE_RETURN_CODE, emailMap);*/
			HashMap<String, Object> vContext = new AOPHashMap();
			vContext.put("FNAME", email.getFirstName());
			vContext.put("LNAME", email.getLastName());
			vContext.put("USERNAME", email.getFirstName()+ " " + email.getLastName());
			vContext.put("TRANSACTION_ID", transId);
			vContext.put("TRANS_AMOUNT", formatter.format(amount));
			vContext.put("RETURN_CODE_DESC", returnDesc);
			vContext.put("CURRENT_DATE",date.toString());
			vContext.put("SERVICE_URL","http://"+getSiteUrl()+"/termsAndConditions_displayBucksAgreement.action");

			vContext.put(AOPConstants.AOP_USER_EMAIL, email.getTo());
			vContext.put(AOPConstants.AOP_TEMPLATE_ID,email.getTemplateId());	
			vContext.put("InputValue",vContext.toString());

			AlertTask alertTask = getEmailAlertTask(vContext);
			try {
				alertDao.addAlertToQueue(alertTask);
			} catch (DataServiceException e) {
				e.printStackTrace();
				logger.error("AlertAdvice-->sendACHFailureEmail-->DataServiceException-->", e);
			}		
		}else{
			VelocityContext velocityContext = new VelocityContext();
			velocityContext.put("FNAME", email.getFirstName());
			velocityContext.put("LNAME", email.getLastName());
			velocityContext.put("USERNAME", email.getFirstName()+ " " + email.getLastName());
			velocityContext.put("TRANSACTION_ID", transId);
			velocityContext.put("TRANS_AMOUNT", formatter.format(amount));
			velocityContext.put("RETURN_CODE_DESC", returnDesc);
			velocityContext.put("CURRENT_DATE",date.toString());
			velocityContext.put("SERVICE_URL","http://"+getSiteUrl()+"/termsAndConditions_displayBucksAgreement.action");
			getVelocityEngine().evaluate(velocityContext, sw, "VL Response Failure Email", email.getMessage());
			// send email with velocity for warning
			String emailArray[] = this.splitEmail(email.getTo());
			sendGenericEmailWithServiceLiveLogo(emailArray,email.getFrom(), email.getSubject(),sw.toString());
		}  		

	}
	public void sendACHWarningEmail(EmailVO email, String transId, Double amount, String returnCode) throws MessagingException, IOException {
		// logger.debug ("entering email ************************");
		StringWriter sw = new StringWriter();
		Calendar cal = Calendar.getInstance();
		NumberFormat formatter = new DecimalFormat("#0.00");
		Date date = cal.getTime();
		VelocityContext vContext = new VelocityContext();
		vContext.put("FNAME", email.getFirstName());
		vContext.put("LNAME", email.getLastName());
		vContext.put("TRANSACTION_ID", transId);
		vContext.put("TRANS_AMOUNT", "$"+formatter.format(amount));
		vContext.put("RETURN_CODE_DESC", returnCode);
		vContext.put("CURRENT_DATE",date.toString());
		vContext.put("SERVICE_URL","http://"+getSiteUrl()+"/termsAndConditions_displayBucksAgreement.action");

		getVelocityEngine().evaluate(vContext, sw, "Failure Email",
				email.getMessage());
		String emailArray[] = this.splitEmail(email.getTo());
		sendGenericEmailWithServiceLiveLogo(emailArray,email.getFrom(), email.getSubject(),sw.toString());
		logger.info("email SENT************************");
	}


	public void sendWithdrawConfirmEmail(TemplateVo template,EmailVO email, String ledgerId, Double amount, String fmReleaseDate,int eid, String roleType) 
			throws MessagingException, IOException,DataServiceException {
		HashMap<String, Object> vContext = new AOPHashMap();
		NumberFormat formatter = new DecimalFormat("#0.00");
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		vContext.put("FNAME", email.getFirstName());
		vContext.put("LNAME", email.getLastName());
		vContext.put("USERNAME",email.getFirstName()+" "+ email.getLastName());
		vContext.put("LEDGER_ID", ledgerId);
		vContext.put("TRANS_AMOUNT", formatter.format(amount));
		vContext.put("FM_RELEASE_DATE", fmReleaseDate);
		vContext.put("CURRENT_DATE",date.toString());		
		vContext.put("SERVICE_URL","http://"+getSiteUrl()+"/termsAndConditions_displayBucksAgreement.action");
		//Code for getting SERVICE_URL based on role
		try{
			if(null != roleType){
				if(OrderConstants.BUYER.equalsIgnoreCase(roleType)||
						OrderConstants.PROVIDER.equalsIgnoreCase(roleType)){
					vContext.put("SERVICE_URL",getSiteUrlForRole(Constants.AppPropConstants.SL_BUCKS_PROFESSIONAL));
					vContext.put("ROLE_IND",AlertConstants.ROLE_PROFESSIONAL_BUYER);
				}else{
					vContext.put("SERVICE_URL",getSiteUrlForRole(Constants.AppPropConstants.SL_BUCKS_SIMPLE));
					vContext.put("ROLE_IND",AlertConstants.ROLE_CONSUMER_BUYER);
				}	
			}
		}catch(Exception e){
			logger.error("Exception occured while getting value from appl prop");
		}	
		vContext.put(AOPConstants.AOP_USER_EMAIL, email.getTo());
		vContext.put(AOPConstants.AOP_TEMPLATE_ID,email.getTemplateId());	
		vContext.put("InputValue",vContext.toString());

		AlertTask alert = getAlertTaskForSendWithdraw(template,vContext);
		alertDao.addAlertToQueue(alert);		
	}


	//SL-21117: Revenue Pull code change starts

	public void sendRevenuePullConfirmationEmail(EmailVO email,double amount,String revenuePullDate) {

		MimeMessage message = null;
		NumberFormat formatter = new DecimalFormat("#0.00");

		StringWriter subject = new StringWriter();
		StringWriter text = new StringWriter();
		VelocityContext vContext = new VelocityContext();

		vContext.put("FIRST_NAME", email.getFirstName());
		vContext.put("LAST_NAME", email.getLastName());
		vContext.put("REVENUE_AMOUNT", formatter.format(amount));
		vContext.put("REVENUEPULL_DATE", revenuePullDate);


		try {

			getVelocityEngine().evaluate(vContext, subject, "Revenue Pull Subject", email.getSubject());
			getVelocityEngine().evaluate(vContext, text, "Revenue Pull Text", email.getMessage());

		} catch (ParseErrorException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (MethodInvocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ResourceNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}    	 

		try {
			message = getMailSender().createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			String emailArray[] = this.splitEmail(email.getTo());
			String emailCcArray[] = this.splitEmail(email.getCc());
			helper.setFrom(email.getFrom());
			helper.setTo(emailArray);
			helper.setCc(emailCcArray);
			helper.setSubject(subject.toString());
			helper.setText(text.toString(), true);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		getMailSender().send(message);
	}

	//Code Change ends

	private void sendEmails(int eid, String eMails, Map emailMap) {
		StringTokenizer  toTokens = new StringTokenizer(eMails,";");
		while (toTokens.hasMoreElements()) {
			String toEmail = toTokens.nextToken(); 
			if(!((toEmail == null)|| (StringUtils.isEmpty(toEmail)) || (!toEmail.contains("@")))){
				emailMap.put("email", toEmail);
				//getEmailService().send(eid, emailMap);  		
			}	
		}

	}

	private AlertTask getAlertTaskForSendWithdraw(TemplateVo template,Map<String, Object> aopHashMap) {		
		Integer templateId  = template.getTemplateId();//(Integer)(aopHashMap.get(AOPConstants.AOP_TEMPLATE_ID));
		AlertTask alertTask = new AlertTask();
		Date date = new Date();

		alertTask.setAlertedTimestamp(null); 
		alertTask.setAlertTypeId(template.getTemplateTypeId());
		alertTask.setCreatedDate(date);
		alertTask.setModifiedDate(date);
		alertTask.setTemplateId(templateId);
		if(template.getTemplateFrom()!=null)
			alertTask.setAlertFrom(template.getTemplateFrom());//AlertConstants.SERVICE_LIVE_MAILID);
		else
			alertTask.setAlertFrom(AlertConstants.SERVICE_LIVE_MAILID);			
		alertTask.setAlertTo((String)aopHashMap.get(AOPConstants.AOP_USER_EMAIL));
		alertTask.setAlertBcc("");
		alertTask.setAlertCc("");
		alertTask.setCompletionIndicator(AlertConstants.INCOMPLETE_INDICATOR);
		alertTask.setPriority(template.getPriority());
		alertTask.setTemplateInputValue((String)aopHashMap.get("InputValue"));
		logger.info(alertTask.getTemplateInputValue().toString());
		return alertTask; 	
	}    

	public void sendCCWithdrawConfirmEmail(TemplateVo template,EmailVO email, CreditCardVO ccVo,  Integer transactionID, Double amount, String fmReleaseDate, String roleType) 
			throws MessagingException, IOException ,DataServiceException {
		HashMap<String, Object> vContext = new AOPHashMap();
		NumberFormat formatter = new DecimalFormat("#0.00");
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		vContext.put("FNAME", email.getFirstName());
		vContext.put("LNAME", email.getLastName());
		vContext.put("BUYERUSERNAME", email.getFirstName()+" "+email.getLastName());

		vContext.put("LEDGER_ID", transactionID);
		vContext.put("TRANS_AMOUNT",formatter.format(amount));
		vContext.put("CARD_TYPE", UIUtils.getCardName(ccVo.getCardTypeId()));

		String maskCardNo = ccVo.getCardNo();
		if(ccVo.getCardNo() != null ){
			if(ccVo.getCardNo().length() > 5){
				maskCardNo = ServiceLiveStringUtils.maskString(ccVo.getCardNo() , 4, "*");
			}
			else{
				maskCardNo = ServiceLiveStringUtils.maskString(ccVo.getCardNo() , (ccVo.getCardNo().length()/2), "*");
			}
		}
		vContext.put("CARD_NUM", maskCardNo);
		vContext.put("FM_RELEASE_DATE", fmReleaseDate);
		vContext.put("CURRENT_DATE",date.toString());
		vContext.put("SERVICE_URL","http://"+getSiteUrl()+"/termsAndConditions_displayBucksAgreement.action");
		//Code for getting SERVICE_URL based on role
		try{
			if(null != roleType){
				if(OrderConstants.BUYER.equalsIgnoreCase(roleType)||
						OrderConstants.PROVIDER.equalsIgnoreCase(roleType)){
					vContext.put("SERVICE_URL",getSiteUrlForRole(Constants.AppPropConstants.SL_BUCKS_PROFESSIONAL));
				}else{
					vContext.put("SERVICE_URL",getSiteUrlForRole(Constants.AppPropConstants.SL_BUCKS_SIMPLE));
				}	
			}
		}catch(Exception e){
			logger.error("Exception occured while getting value from appl prop");
		}	
		vContext.put(AOPConstants.AOP_USER_EMAIL, email.getTo());
		vContext.put(AOPConstants.AOP_TEMPLATE_ID,email.getTemplateId());	
		vContext.put("InputValue",vContext.toString());

		AlertTask alert = getAlertTaskForSendWithdraw(template,vContext);
		alertDao.addAlertToQueue(alert);    	 

		logger.info("email SENT************************");
	}


	/**
	 * This method extracts the key-values from the velocity context and prepares a map instance. 
	 * @param context
	 * @return
	 */
	public Map<String, String> getEmailMap(VelocityContext context) {

		Object keys[] = context.getKeys();
		Map<String, String> map = new HashMap<String, String>();
		for(Object key:keys){
			if(key instanceof String){
				String value = context.get(key.toString()).toString();
				map.put(key.toString(), value);
			}
		}

		return map;
	}

	public void sendOriginationResponseEmail(EmailVO email) throws MessagingException, IOException {
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();

		StringWriter sw = new StringWriter();
		VelocityContext vContext = new VelocityContext();
		vContext.put("date", date.toString());
		getVelocityEngine().evaluate(vContext, sw, "orgination Email",
				email.getMessage());
		sendGenericEmailWithServiceLiveLogo(email.getTo(),email.getFrom(), email.getSubject(),sw.toString());
		logger.info("email SENT************************");
	}

	public void sendAchAckResponseEmail(EmailVO email, String reason,String filePath, String result) throws MessagingException, IOException {

		StringWriter sw = new StringWriter();
		VelocityContext vContext = new VelocityContext();
		vContext.put("reason_code",reason);
		vContext.put("result",result);
		vContext.put("filePath",filePath);
		getVelocityEngine().evaluate(vContext, sw, "ACH acknowledgment Email",
				email.getMessage());
		sendGenericEmailWithServiceLiveLogo(email.getTo(),email.getFrom(), email.getSubject(),sw.toString());
		logger.info("email SENT************************");
	}



	public void sendGenericEmailWithServiceLiveLogo(String emailTo, String emailFrom,
			String emailSubject, String emailText) {
		MimeMessage message = null;
		try {
			message = getMailSender().createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(emailFrom);
			helper.setTo(emailTo);
			helper.setSubject(emailSubject);
			helper.setText(emailText, true);
			helper.addInline("emailLogo", getServiceLiveEmailImg());

		} catch (MessagingException e) {
			LOGGER.error(e.getMessage(), e);
		}
		getMailSender().send(message);
	}

	public void sendGenericEmailWithAttachment(EmailVO email, File fileObj, String attachmentName) throws MessagingException, IOException {
		MimeMessage message = null;
		try {
			message = getMailSender().createMimeMessage();
			MimeMessageHelper helper = getSettingForAttachementMails(message, email);
			helper.addAttachment(attachmentName, fileObj);
		} catch (MessagingException e) {
			LOGGER.error(e.getMessage(), e);
		}
		getMailSender().send(message);
	}

	public MimeMessageHelper getSettingForAttachementMails(MimeMessage message,EmailVO email){
		MimeMessageHelper helper =null;
		try {
		helper = new MimeMessageHelper(message, true);
		helper.setFrom(email.getFrom());
		helper.setTo(email.getTo());
		helper.setSubject(email.getSubject());
		helper.setText(email.getMessage(), true);
		} catch (MessagingException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return helper;
	}
	
	//code changes for SLT-2231
	public void sendGenericEmailWithMutipleAttachment(EmailVO email, ArrayList<DocumentVO> attachmentVOs)
			throws MessagingException, IOException {
		MimeMessage message = null;
		try {
			message = getMailSender().createMimeMessage();
			MimeMessageHelper helper = getSettingForAttachementMails(message, email);
			for (DocumentVO doc : attachmentVOs) {
				File file = new File(doc.getDocPath());
				helper.addAttachment(doc.getFileName(), file);
			}

		} catch (MessagingException e) {
			LOGGER.error(e.getMessage(), e);
		}
		getMailSender().send(message);
	}
	
	public void sendGenericEmailWithServiceLiveLogo(String emailTo[], String emailFrom,
			String emailSubject, String emailText) {
		MimeMessage message = null;
		try {
			message = getMailSender().createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(emailFrom);
			helper.setTo(emailTo);
			helper.setSubject(emailSubject);
			helper.setText(emailText, true);
			helper.addInline("emailLogo", getServiceLiveEmailImg());

		} catch (MessagingException e) {
			LOGGER.error(e.getMessage(), e);
		}
		getMailSender().send(message);
	}

	//To send a mail if origination file is not present when the scheduler job runs
	public void sendSettlementConfirmationEmail(EmailVO email, String ledgerTransactionId, Double amount, String roleType) 
			throws MessagingException, IOException, DataServiceException {
		Calendar cal = Calendar.getInstance();
		NumberFormat formatter = new DecimalFormat("#0.00");
		Date date = cal.getTime();
		HashMap<String, Object> vContext = new AOPHashMap();
		vContext.put("FNAME", email.getFirstName());
		vContext.put("LNAME", email.getLastName());
		vContext.put("LEDGER_ID", ledgerTransactionId);// constant says ledger_id but it is actually ledgerTransactionId
		vContext.put("TRANS_AMOUNT", "$"+formatter.format(amount));
		vContext.put("CURRENT_DATE",date.toString());
		vContext.put("SERVICE_URL","http://"+getSiteUrl()+"/termsAndConditions_displayBucksAgreement.action");
		//Code for getting SERVICE_URL based on role
		try{
			if(null != roleType){
				if(OrderConstants.BUYER.equalsIgnoreCase(roleType)||
						OrderConstants.PROVIDER.equalsIgnoreCase(roleType)){
					vContext.put("SERVICE_URL",getSiteUrlForRole(Constants.AppPropConstants.SL_BUCKS_PROFESSIONAL));
				}else{
					vContext.put("SERVICE_URL",getSiteUrlForRole(Constants.AppPropConstants.SL_BUCKS_SIMPLE));
				}	
			}
		}catch(Exception e){
			logger.error("Exception occured while getting value from appl prop");
		}	
		vContext.put(AOPConstants.AOP_USER_EMAIL, email.getTo());
		vContext.put(AOPConstants.AOP_TEMPLATE_ID,email.getTemplateId());	
		vContext.put("InputValue",vContext.toString());

		AlertTask alert = getEmailAlertTask(vContext);
		alertDao.addAlertToQueue(alert); 
		logger.info("email SENT************************");
	}

	//insert email into alert task
	public void sendConfirmationEmail(Map<String, Object> aopHashMap) throws MessagingException, IOException, DataServiceException {
		AlertTask alert = getEmailAlertTask(aopHashMap);
		alertDao.addAlertToQueue(alert); 
		logger.info("email SENT************************");
	}

	public void sendFinanceBatchAlert(String emailSubject,String emailBody) {
		MimeMessage message = null;
		try {
			message = getMailSender().createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(getEmailAddressFrom());
			helper.setTo(getEmailAddressTo());
			helper.setSubject(Constants.EMAIL_ADDRESSES.ORIGINATION_PROCESS_FAILURE_SUBJECT);
			helper.setText(Constants.EMAIL_ADDRESSES.ORIGINATION_PROCESS_FAILURE_BODY);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		getMailSender().send(message);
	}

	public void sendEmailVLMessageFailure(EmailVO email, FullfillmentEntryVO fullfillmentEntryVO) throws Exception {
		logger.info("sendEmailVLResponseFailure-->START");
		StringWriter sw1 = new StringWriter();
		StringWriter sw2 = new StringWriter();
		VelocityContext vContext = new VelocityContext();
		vContext.put("ACTION_CODE", fullfillmentEntryVO.getActionCode());
		vContext.put("ACTION_CODE_DESC", fullfillmentEntryVO.getActionCodeDesc());
		vContext.put("FULLFILLMENT_GROUP_ID", fullfillmentEntryVO.getFullfillmentGroupId());
		vContext.put("FULLFILLMENT_ENTRY_ID", fullfillmentEntryVO.getFullfillmentEntryId());
		vContext.put("ENTRY_DATE", fullfillmentEntryVO.getEntryDate());
		vContext.put("ENTITY_INFO", fullfillmentEntryVO.getEntityTypeDesc() + " - " + fullfillmentEntryVO.getLedgerEntityId());
		vContext.put("MESSAGE_TYPE", fullfillmentEntryVO.getMessageIdentifier());
		vContext.put("PRIMARY_ACCOUNT_NUMBER", fullfillmentEntryVO.getPrimaryAccountNumber());
		vContext.put("TRANS_AMOUNT", fullfillmentEntryVO.getTransAmount());
		vContext.put("SO_ID", fullfillmentEntryVO.getSoId());
//		vContext.put("ENV", System.getenv("sl_app_lifecycle"));
		vContext.put("ENV", System.getProperty("sl_app_lifecycle"));
		getVelocityEngine().evaluate(vContext, sw1, "VL Response Failure Email", email.getMessage());
		getVelocityEngine().evaluate(vContext, sw2, "VL Response Failure Email", email.getSubject());
		sendGenericEmailWithServiceLiveLogo(getEmailAddressTo(),getEmailAddressFrom(), sw2.toString(),sw1.toString());

	}

	public void sendBuyerPostingFeeEmail(
			EmailVO email,
			String soId,
			String ledgerTransIdPost,
			Double transAmtPost,
			String ledgerTranIdRes,
			Double transAmtRes
			) throws MessagingException, IOException {

		// logger.debug ("entering email ************************");
		StringWriter sw = new StringWriter();
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		Map<String, Object> alertMap = new AOPHashMap();
		alertMap.put("SO_ID", soId);
		alertMap.put("FNAME", email.getFirstName());
		alertMap.put("LNAME", email.getLastName());

		alertMap.put("LEDGER_TRANID_POST", ledgerTransIdPost);
		alertMap.put("TRANS_AMOUNT_POST", "$"+transAmtPost.toString());

		alertMap.put("LEDGER_TRANID_RES", ledgerTranIdRes);
		alertMap.put("TRANS_AMOUNT_RES", "$"+transAmtRes.toString());

		alertMap.put("CURRENT_DATE",date.toString());
		alertMap.put("SERVICE_URL","http://"+getSiteUrl()+"/termsAndConditions_displayBucksAgreement.action");

		alertMap.put(AOPConstants.AOP_USER_EMAIL, email.getTo());
		alertMap.put(AOPConstants.AOP_TEMPLATE_ID, AchConstants.EMAIL_TEMPLATE_BUYER_POSTING_FEE);

		try{
			AlertTask alertTask = getEmailAlertTask(alertMap);
			alertDao.addAlertToQueue(alertTask);
			logger.info("buyer posting fee email SENT************************");   			
		}catch(DataServiceException e){
			logger.info("Error while sending buyer posting fee email  ****\n"+e);
		}
		//TODO - Find out if the following is necessary and correct 'Failure Email'.
		/*	getVelocityEngine().evaluate(vContext, sw, "Failure Email",
    				email.getMessage());

    		String emailArray[] = this.splitEmail(email.getTo());
    		sendGenericEmailWithServiceLiveLogo(emailArray,email.getFrom(), email.getSubject(),sw.toString());*/
	}

	public void sendFailedToAcceptSOEmail(EmailVO email, ServiceOrder so)
			throws MessagingException, IOException {

		// logger.debug ("entering email ************************");
		StringWriter sw = new StringWriter();
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		Map<String, Object> alertMap = new AOPHashMap();
		alertMap.put("SO_ID", so.getSoId());
		alertMap.put("FNAME", email.getFirstName());
		alertMap.put("LNAME", email.getLastName());
		alertMap.put("SO_TITLE", so.getSowTitle());

		alertMap.put("SERVICE_DATE", so.getServiceDate1());
		alertMap.put("TRANS_AMOUNT_RES", "$" + (so.getSpendLimitLabor() + so.getSpendLimitParts()));

		alertMap.put("CURRENT_DATE", date.toString());
		alertMap.put(AOPConstants.AOP_TEMPLATE_ID, email.getTemplateId());
		alertMap.put(AOPConstants.AOP_USER_EMAIL, email.getTo());
		alertMap.put(AOPConstants.AOP_TEMPLATE_ID,
				email.getTemplateId());

		try {
			AlertTask alertTask = getEmailAlertTask(alertMap);
			alertDao.addAlertToQueue(alertTask);
			logger.info("Failed to accept SO email SENT with template ID:" + email.getTemplateId() + "   ************************");
		} catch (DataServiceException e) {
			logger.info("Error while sending buyer posting fee email  ****\n"
					+ e);
		}
	}

	public void sendBuyerCancellationEmail(EmailVO emailVO, String soId,
			Integer vendorId, String ledgerTransId, Double transAmount,
			String roleType) throws MessagingException, IOException {
		StringWriter sw = new StringWriter();
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		HashMap<String, Object> vContext = new AOPHashMap();

		vContext.put("SO_ID", soId);
		vContext.put("FNAME", emailVO.getFirstName());
		vContext.put("LNAME", emailVO.getLastName());
		vContext.put("VENDOR_ID", vendorId);
		vContext.put("BUYERUSERNAME", emailVO.getFirstName() + " "
				+ emailVO.getLastName());
		vContext.put("LEDGER_TRANID_CANCL_PNLTY", ledgerTransId);
		DecimalFormat twoPlaces = new DecimalFormat("0.00");
		String amount = twoPlaces.format(transAmount);
		vContext.put("TRANS_AMOUNT_CANCL_PNLTY", amount);
		vContext.put("CURRENT_DATE", date.toString());
		vContext.put("SERVICE_URL", "http://" + getSiteUrl()
		+ "/termsAndConditions_displayBucksAgreement.action");
		// code for getting the site url based on role
		try {
			if (OrderConstants.BUYER.equalsIgnoreCase(roleType)
					|| OrderConstants.PROVIDER.equalsIgnoreCase(roleType)) {
				vContext
				.put(
						"SERVICE_URL",
						getSiteUrlForRole(Constants.AppPropConstants.SL_BUCKS_PROFESSIONAL));
			} else {
				vContext
				.put(
						"SERVICE_URL",
						getSiteUrlForRole(Constants.AppPropConstants.SL_BUCKS_SIMPLE));
			}
		} catch (Exception e) {
			logger
			.info("Error while getting properties for siteUrl ****\n"
					+ e);
		}

		vContext.put(AOPConstants.AOP_USER_EMAIL, emailVO.getTo());
		vContext.put(AOPConstants.AOP_TEMPLATE_ID,
				AchConstants.EMAIL_TEMPLATE_BUYER_CANCEL_PENALTY);
		AlertTask alertTask = getEmailAlertTask(vContext);
		try {
			alertDao.addAlertToQueue(alertTask);
		} catch (DataServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger
			.error(
					"AlertAdvice-->sendBuyerCancellationEmail-->DataServiceException-->",
					e);
		}

		logger.info("buyer cancellation email SENT************************");
	}

	public void sendSLBucksCreditEmail(EmailVO emailVO, String ledgerTransId,
			Double transAmount, String roleType) throws MessagingException,
			IOException {
		StringWriter sw = new StringWriter();
		NumberFormat formatter = new DecimalFormat("#0.00");
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();

		// code change for SLT-2228
		List<EmailDataVO> emailData = new ArrayList<EmailDataVO>();
		emailData.add(new EmailDataVO(AOPConstants.AOP_EMAIL_FNAME, emailVO.getFirstName()));
		emailData.add(new EmailDataVO(AOPConstants.AOP_EMAIL_LNAME, emailVO.getLastName()));
		emailData.add(
				new EmailDataVO(AOPConstants.AOP_EMAIL_USERNAME, emailVO.getFirstName() + "" + emailVO.getLastName()));
		emailData.add(new EmailDataVO(AOPConstants.AOP_EMAIL_LEDGER_TRANID, ledgerTransId));
		emailData.add(new EmailDataVO(AOPConstants.AOP_EMAIL_TRANS_AMOUNT, formatter.format(transAmount)));
		emailData.add(new EmailDataVO(AOPConstants.AOP_EMAIL_CURRENT_DATE, date.toString()));
		emailData.add(new EmailDataVO(AOPConstants.AOP_EMAIL_SERVICE_URL,
				"http://" + getSiteUrl() + "/termsAndConditions_displayBucksAgreement.action"));
		emailData.add(new EmailDataVO(AOPConstants.AOP_USER_EMAIL, emailVO.getTo()));

		Map<String, Object> alertMap = new AOPHashMap();
		for (EmailDataVO emailDatum : emailData) {
			alertMap.put(emailDatum.getParamKey(), emailDatum.getParamValue());
		}
		// code for getting the site url based on role
		try {
			if (OrderConstants.BUYER.equalsIgnoreCase(roleType) || OrderConstants.PROVIDER.equalsIgnoreCase(roleType)) {
				alertMap.put("SERVICE_URL", getSiteUrlForRole(Constants.AppPropConstants.SL_BUCKS_PROFESSIONAL));
			} else {
				alertMap.put("SERVICE_URL", getSiteUrlForRole(Constants.AppPropConstants.SL_BUCKS_SIMPLE));
			}
		} catch (Exception e) {
			logger.info("Error while getting properties for siteUrl ****\n" + e);
		}
		alertMap.put(AOPConstants.AOP_TEMPLATE_ID, emailVO.getTemplateId());
		try {
			AlertTask alertTask = getEmailAlertTask(alertMap);
			alertDao.addAlertToQueue(alertTask);
			logger.info("SLBucks Credit email SENT************************");
		} catch (DataServiceException e) {
			logger.info("Error while sending SLBucks Credit email SENT ****\n" + e);
		}

		logger.info("SLBucks Credit email SENT************************");
	}

	public void sendSLBucksDebitEmail(EmailVO emailVO, String ledgerTransId,
			Double transAmount, String roleType,boolean isEscheatment) throws MessagingException,
			IOException {
		StringWriter sw = new StringWriter();
		Calendar cal = Calendar.getInstance();
		NumberFormat formatter = new DecimalFormat("#0.00");
		Date date = cal.getTime();
		// code change for SLT-2228
		List<EmailDataVO> emailData = new ArrayList<EmailDataVO>();
		emailData.add(new EmailDataVO(AOPConstants.AOP_EMAIL_FNAME, emailVO.getFirstName()));
		emailData.add(new EmailDataVO(AOPConstants.AOP_EMAIL_LNAME, emailVO.getLastName()));
		emailData.add(
				new EmailDataVO(AOPConstants.AOP_EMAIL_USERNAME, emailVO.getFirstName() + "" + emailVO.getLastName()));
		emailData.add(new EmailDataVO(AOPConstants.AOP_EMAIL_LEDGER_TRANID, ledgerTransId));
		emailData.add(new EmailDataVO(AOPConstants.AOP_EMAIL_TRANS_AMOUNT, formatter.format(transAmount)));
		emailData.add(new EmailDataVO(AOPConstants.AOP_EMAIL_CURRENT_DATE, date.toString()));
		emailData.add(new EmailDataVO(AOPConstants.AOP_EMAIL_SERVICE_URL,
				"http://" + getSiteUrl() + "/termsAndConditions_displayBucksAgreement.action"));
		emailData.add(new EmailDataVO(AOPConstants.AOP_USER_EMAIL, emailVO.getTo()));

		Map<String, Object> alertMap = new AOPHashMap();
		for (EmailDataVO emailDatum : emailData) {
			alertMap.put(emailDatum.getParamKey(), emailDatum.getParamValue());
		}

		// code for getting the site url based on role
		try {
			if (OrderConstants.BUYER.equalsIgnoreCase(roleType) || OrderConstants.PROVIDER.equalsIgnoreCase(roleType)) {
				alertMap.put("SERVICE_URL", getSiteUrlForRole(Constants.AppPropConstants.SL_BUCKS_PROFESSIONAL));
			} else {
				alertMap.put("SERVICE_URL", getSiteUrlForRole(Constants.AppPropConstants.SL_BUCKS_SIMPLE));
			}
		} catch (Exception e) {
			logger.info("Error while getting properties for siteUrl ****\n" + e);
		}

		// TODO - Find out if the following is necessary and correct 'Failure
		// Email'.
		/*
		 * sendGenericEmailWithServiceLiveLogo(emailArray,emailVO.getFrom(),
		 * emailVO.getSubject(),sw.toString());
		 */
		/*
		 * getVelocityEngine().evaluate(vContext, sw, "Failure Email",
		 * emailVO.getMessage());
		 * 
		 * Map<String, String> emailMap = getEmailMap(vContext);
		 * emailMap.put("email", emailVO.getTo());
		 * getCheetahEmailService().send(AchConstants.
		 * TEMPLATE_BUYER_SLBUCKS_DEBIT, emailMap);
		 */

		if (isEscheatment) {
			alertMap.put(AOPConstants.AOP_TEMPLATE_ID, AchConstants.EMAIL_TEMPLATE_SLBUCKS_ESCHEATMENT);
		} else {
			alertMap.put(AOPConstants.AOP_TEMPLATE_ID, emailVO.getTemplateId());
		}
		try {
			AlertTask alertTask = getEmailAlertTask(alertMap);
			alertDao.addAlertToQueue(alertTask);
			logger.info("SLBucks Debit email SENT************************");
		} catch (DataServiceException e) {
			logger.info("Error while sending SLBucks Debit email SENT ****\n" + e);
		}
	}


	public void sendSLBucksEscheatmentEmail(EmailVO emailVO, String ledgerTransId,
			Double transAmount, String roleType,Double availableBalance,String createdDate) throws MessagingException,
			IOException {
		StringWriter sw = new StringWriter();
		Calendar cal = Calendar.getInstance();
		NumberFormat formatter = new DecimalFormat("#0.00");
		Date date = cal.getTime();
		// code change for SLT-2228
		List<EmailDataVO> emailData = new ArrayList<EmailDataVO>();
		emailData.add(new EmailDataVO(AOPConstants.AOP_EMAIL_FNAME, emailVO.getFirstName()));
		emailData.add(new EmailDataVO(AOPConstants.AOP_EMAIL_LNAME, emailVO.getLastName()));
		emailData.add(
				new EmailDataVO(AOPConstants.AOP_EMAIL_USERNAME, emailVO.getFirstName() + "" + emailVO.getLastName()));
		emailData.add(new EmailDataVO(AOPConstants.AOP_EMAIL_LEDGER_TRANID, ledgerTransId));
		emailData.add(new EmailDataVO(AOPConstants.AOP_EMAIL_TRANS_AMOUNT, formatter.format(transAmount)));
		emailData.add(new EmailDataVO(AOPConstants.AOP_EMAIL_CURRENT_DATE, date.toString()));
		emailData.add(new EmailDataVO(AOPConstants.AOP_EMAIL_SERVICE_URL,
				"http://" + getSiteUrl() + "/termsAndConditions_displayBucksAgreement.action"));
		emailData.add(new EmailDataVO(AOPConstants.AOP_EMAIL_CREATED_DATE, createdDate));
		emailData.add(new EmailDataVO(AOPConstants.AOP_EMAIL_AVAILABLE_BALANCE, availableBalance.toString()));
		emailData.add(new EmailDataVO(AOPConstants.AOP_USER_EMAIL, emailVO.getTo()));

		Map<String, Object> alertMap = new AOPHashMap();

		for (EmailDataVO emailDatum : emailData) {
			alertMap.put(emailDatum.getParamKey(), emailDatum.getParamValue());
		}

		// code for getting the site url based on role
		try {
			if (OrderConstants.BUYER.equalsIgnoreCase(roleType) || OrderConstants.PROVIDER.equalsIgnoreCase(roleType)) {
				alertMap.put("SERVICE_URL", getSiteUrlForRole(Constants.AppPropConstants.SL_BUCKS_PROFESSIONAL));
			} else {
				alertMap.put("SERVICE_URL", getSiteUrlForRole(Constants.AppPropConstants.SL_BUCKS_SIMPLE));
			}
		} catch (Exception e) {
			logger.info("Error while getting properties for siteUrl ****\n" + e);
		}

		// TODO - Find out if the following is necessary and correct 'Failure
		// Email'.
		/*
		 * sendGenericEmailWithServiceLiveLogo(emailArray,emailVO.getFrom(),
		 * emailVO.getSubject(),sw.toString());
		 */
		/*
		 * getVelocityEngine().evaluate(vContext, sw, "Failure Email",
		 * emailVO.getMessage());
		 * 
		 * Map<String, String> emailMap = getEmailMap(vContext);
		 * emailMap.put("email", emailVO.getTo());
		 * getCheetahEmailService().send(AchConstants.
		 * TEMPLATE_BUYER_SLBUCKS_DEBIT, emailMap);
		 */
		alertMap.put(AOPConstants.AOP_TEMPLATE_ID, emailVO.getTemplateId());

		try {
			AlertTask alertTask = getEmailAlertTask(alertMap);
			alertDao.addAlertToQueue(alertTask);
			logger.info("SLBucks Debit email SENT************************");
		} catch (DataServiceException e) {
			logger.info("Error while sending SLBucks Debit email SENT ****\n" + e);
		}
	}

	public void sendProviderSOCancelledEmail(EmailVO emailVO, Integer buyerId, String soId, String ledgerTransId, Double transAmount)throws MessagingException, IOException
	{
		StringWriter sw = new StringWriter();
		Calendar cal = Calendar.getInstance();
		NumberFormat formatter = new DecimalFormat("#0.00");
		Date date = cal.getTime();
		VelocityContext vContext = new VelocityContext();
		vContext.put("ACCEPTED_VENDOR_RESOURCE_FNAME", emailVO.getFirstName());
		vContext.put("ACCEPTED_VENDOR_RESOURCE_LNAME", emailVO.getLastName());
		vContext.put("SO_CANCEL_DATE", date.toString());
		vContext.put("SO_ID", soId);

		vContext.put("LEDGER_TRANID_CANCL_PNLTY", ledgerTransId);
		vContext.put("TRANS_AMOUNT_CANCL_PNLTY", "$"+formatter.format(transAmount));
		vContext.put("BUYER_ID", buyerId);

		vContext.put("CURRENT_DATE",date.toString());
		vContext.put("SERVICE_URL","http://"+getSiteUrl()+"/termsAndConditions_displayBucksAgreement.action");

		//TODO - Find out if the following is necessary and correct 'Failure Email'.
		getVelocityEngine().evaluate(vContext, sw, "Failure Email",
				emailVO.getMessage());

		String emailArray[] = this.splitEmail(emailVO.getTo());
		sendGenericEmailWithServiceLiveLogo(emailArray,emailVO.getFrom(), emailVO.getSubject(),sw.toString());
		logger.info("SO Cancelled email SENT************************");
	}


	public void sendBuyerSOClosedEmail(EmailVO emailVO, Integer buyerId, Integer vendorId, String soId, String ledgerTransId,  Double transAmount, String providerFirstName, String providerLastName, String consumerFlag,Integer providerId)throws MessagingException, IOException
	{
		StringWriter sw = new StringWriter();
		Calendar cal = Calendar.getInstance();
		NumberFormat formatter = new DecimalFormat("#0.00");
		Date date = cal.getTime();
		//VelocityContext vContext = new VelocityContext();
		HashMap<String, Object> vContext = new AOPHashMap();
		vContext.put("FNAME", emailVO.getFirstName());
		vContext.put("LNAME", emailVO.getLastName());
		vContext.put("SO_CANCEL_DATE", date.toString());
		vContext.put("SO_ID", soId);
		vContext.put("BUYERUSERNAME", emailVO.getFirstName()+" "+emailVO.getLastName());
		vContext.put("LEDGER_TRANID", ledgerTransId);
		vContext.put("TRANS_AMOUNT",formatter.format(transAmount));
		vContext.put("VENDOR_ID", vendorId);

		vContext.put("CURRENT_DATE",date.toString());
		vContext.put("SERVICE_URL","http://"+getSiteUrl()+"/termsAndConditions_displayBucksAgreement.action");

		vContext.put("PROVIDER_FN", providerFirstName);
		vContext.put("PROVIDER_LN", providerLastName);
		vContext.put("PROVIDER_ID", providerId);
		vContext.put("CONSUMER", consumerFlag);

		vContext.put(AOPConstants.AOP_USER_EMAIL, emailVO.getTo());
		vContext.put(AOPConstants.AOP_TEMPLATE_ID, AchConstants.TEMPLATE_ID_BUYER_CLOSE_EMAIL);
		AlertTask alertTask = getEmailAlertTask(vContext);

		try {
			alertDao.addAlertToQueue(alertTask);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("SO Closed by Buyer email SENT************************");
	}


	private Template getTemplateDetail(Integer templateId){
		Template template = templateCache.get(templateId);
		if ( template == null) {
			TemplateDaoImpl templateDaoImpl = getTemplateDao();

			try {
				template = new Template();
				template.setTemplateId(templateId);
				template = templateDaoImpl.query(template);
				templateCache.put(templateId, template);
			}
			catch(DataServiceException dse){
				logger.error("AlertAdvice-->getTemplateDetail-->DataServiceException-->", dse);
			}
		} 		
		return template;
	}

	/**
	 * Description: This method sends the provider soft delete confirmation mail
	 * @param resourceId
	 * @param userName
	 * @param firstName
	 * @param lastName
	 * @param adminUserName
	 * @param toAddress
	 * @throws MessagingException
	 * @throws IOException
	 */
	public void sendRemoveUserMailConfirmation(String resourceId, String userName, String firstName, String lastName, String adminUserName, String toAddress) throws MessagingException, IOException {

		HashMap<String, Object> vContext = new AOPHashMap();
		vContext.put("PROVIDERADMIN", adminUserName);
		vContext.put("PROVIDERFIRSTNAME", firstName);
		vContext.put("PROVIDERLASTNAME", lastName);
		vContext.put("USERNAME", userName);
		vContext.put("USERID", resourceId);

		vContext.put(AOPConstants.AOP_USER_EMAIL, toAddress);
		vContext.put(AOPConstants.AOP_TEMPLATE_ID, AchConstants.TEMPLATE_ID_PROVIDER_USER_REMOVED_EMAIL);
		AlertTask alertTask = getEmailAlertTask(vContext);
		try {
			alertDao.addAlertToQueue(alertTask);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Provider Soft delete mail sent");
	}

	public void sendProviderSOClosedEmail(EmailVO emailVO, Integer buyerId, String soId, String ledgerTransIdPay, String ledgerTransIdFee, Double transAmountPay, Double transAmountFee)throws MessagingException, IOException
	{
		StringWriter sw = new StringWriter();
		Calendar cal = Calendar.getInstance();
		NumberFormat formatter = new DecimalFormat("#0.00");
		Date date = cal.getTime();
		//VelocityContext vContext = new VelocityContext();
		HashMap<String, Object> vContext = new AOPHashMap();
		vContext.put("FNAME", emailVO.getFirstName());
		vContext.put("LNAME", emailVO.getLastName());
		vContext.put("SO_CLOSED_DATE", date.toString());
		vContext.put("SO_ID", soId);

		vContext.put("LEDGER_TRANID_PAY", ledgerTransIdPay);
		vContext.put("LEDGER_TRANID_FEE", ledgerTransIdFee);
		vContext.put("TRANS_AMOUNT_PAY", "$"+formatter.format(transAmountPay));
		vContext.put("TRANS_AMOUNT_FEE", "$"+transAmountFee.toString());
		vContext.put("BUYER_ID", buyerId);

		vContext.put("CURRENT_DATE",date.toString());
		vContext.put("SERVICE_URL","http://"+getSiteUrl()+"/termsAndConditions_displayBucksAgreement.action");

		vContext.put(AOPConstants.AOP_USER_EMAIL, emailVO.getTo());
		vContext.put(AOPConstants.AOP_TEMPLATE_ID, AchConstants.TEMPLATE_ID_PROVIDER_CLOSE_EMAIL);
		AlertTask alertTask = getEmailAlertTask(vContext);
		try {
			alertDao.addAlertToQueue(alertTask);
		} catch (Exception e) {
			e.printStackTrace();
		}

		//TODO - Find out if the following is necessary and correct 'Failure Email'.
		//getVelocityEngine().evaluate(vContext, sw, "Failure Email",
		//		emailVO.getMessage());

		//String emailArray[] = this.splitEmail(emailVO.getTo());
		//sendGenericEmailWithServiceLiveLogo(emailArray,emailVO.getFrom(), emailVO.getSubject(),sw.toString());

		logger.info("SO Closed by Buyer email SENT to provider************************");
	}

	public AlertTask getEmailAlertTask(Map<String, Object> aopHashMap) {		
		Integer templateId  = (Integer)(aopHashMap.get(AOPConstants.AOP_TEMPLATE_ID));
		Template template = getTemplateDetail(templateId);
		AlertTask alertTask = new AlertTask();
		Date date = new Date();
		alertTask.setAlertedTimestamp(null); 
		alertTask.setAlertTypeId(template.getTemplateTypeId());
		alertTask.setCreatedDate(date);
		alertTask.setModifiedDate(date);
		alertTask.setModifiedBy((String)aopHashMap.get(AOPConstants.AOP_MODIFIED_BY));
		alertTask.setTemplateId(templateId);
		if(template.getTemplateFrom()!=null)
			alertTask.setAlertFrom(template.getTemplateFrom());//AlertConstants.SERVICE_LIVE_MAILID);
		else
			alertTask.setAlertFrom(AlertConstants.SERVICE_LIVE_MAILID);
		alertTask.setAlertTo((String)aopHashMap.get(AOPConstants.AOP_USER_EMAIL));
		alertTask.setAlertBcc("");
		alertTask.setAlertCc("");
		alertTask.setCompletionIndicator(AlertConstants.INCOMPLETE_INDICATOR);
		alertTask.setPriority(template.getPriority());
		alertTask.setTemplateInputValue(aopHashMap.toString());
		return alertTask; 	
	}
	public void sendErrorEmail(SerializableBaseVO vo, String errorMessage,
			String emailTo, String emailFrom)
	{
		MimeMessage message = null;
		try {
			message = getMailSender().createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(emailFrom);
			helper.setTo(emailTo);
			helper.setSubject(errorMessage);
			helper.setText(vo.toString(), true);
		} catch (MessagingException e) {
			LOGGER.error(e.getMessage(), e);
		}
		getMailSender().send(message);
		return;
	}

	public void sendNoteOrQuestionEmail(EmailVO email, String soID, String soTitle, String roleInd) throws MessagingException, IOException
	{

		StringWriter sw = new StringWriter();
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		// if(AchConstants.EMAIL_TEMPLATE_PROVIDER_ACH_WITHDRAW_FUNDS_FAILURE == email.getTemplateId())
		if (13 == email.getTemplateId())
		{
			HashMap<String, Object> vContext = new AOPHashMap();
			vContext.put("SO_ID", soID);
			vContext.put("SO_TITLE", soTitle);
			vContext.put("NOTE", email.getMessage());

			vContext.put(AOPConstants.AOP_USER_EMAIL, email.getTo());
			vContext.put(AOPConstants.AOP_TEMPLATE_ID, email.getTemplateId());
			//vContext.put("InputValue", vContext.toString());
			vContext.put(AOPConstants.AOP_ROLE_IND, roleInd);

			AlertTask alertTask = getEmailAlertTask(vContext);
			try
			{
				alertDao.addAlertToQueue(alertTask);
			}
			catch (DataServiceException e)
			{
				e.printStackTrace();
				logger.error("AlertAdvice-->sendNoteOrQuestionEmail-->DataServiceException-->", e);
			}
		}
		else
		{
			VelocityContext velocityContext = new VelocityContext();
			velocityContext.put("FNAME", email.getFirstName());
			velocityContext.put("LNAME", email.getLastName());
			velocityContext.put("USERNAME", email.getFirstName() + " " + email.getLastName());
			velocityContext.put("CURRENT_DATE", date.toString());
			velocityContext.put("SERVICE_URL", "http://" + getSiteUrl());
			getVelocityEngine().evaluate(velocityContext, sw, "VL Response Failure Email", email.getMessage());
			// send email with velocity for warning
			String emailArray[] = this.splitEmail(email.getTo());
			sendGenericEmailWithServiceLiveLogo(emailArray, email.getFrom(), email.getSubject(), sw.toString());
		}

	}


	/**
	 * ************ Buyer Email that will not live here after alert task
	 * re-factoring is completed *************
	 */
	public void sendBuyerRoutingRulesNotificationEmail(RoutingRuleEmailVO emailVO) throws MessagingException, IOException  
	{

		HashMap<String, Object> vContext = new AOPHashMap();

		vContext = populateRoutingRuleEmailHeaderInfo(emailVO, vContext);
		vContext = populateRoutingRuleEmailVendorContext(emailVO.getVendors(), vContext);
		vContext = populateRoutingRuleEmailJobCodeContext(emailVO.getJobCodes(), vContext);

		vContext.put(AOPConstants.AOP_USER_EMAIL, emailVO.getRuleContactEmail());
		vContext.put(AOPConstants.AOP_TEMPLATE_ID, MPConstants.EMAIL_TEMPLATE_ROUTING_RULES_ALERT_BUYER_NOTIFICATION_COMBINED);

		addAlertToQueue(vContext);

	}


	private void addAlertToQueue(HashMap<String, Object> vContext) {
		AlertTask alertTask = getEmailAlertTask(vContext);
		try {
			alertDao.addAlertToQueue(alertTask);
		} catch (DataServiceException e) {
			e.printStackTrace();
			logger.error("AlertAdvice-->sendBuyerRoutingRulesNotificationEmail-->DataServiceException-->", e);
		}
	}

	private HashMap<String, Object> populateRoutingRuleEmailVendorContext(
			Map<String, Integer> vendors, HashMap<String, Object> vContext) {
		if(vendors != null){
			StringBuilder sb = new StringBuilder();
			for(String vendor: vendors.keySet()) {
				if(sb.length() != 0) {
					sb.append(", ");
				}
				sb.append(vendor);
				sb.append(" (ID# ");
				sb.append(vendors.get(vendor).toString());
				sb.append(")");
			}
			vContext.put("CompanyList", sb.toString());
		}
		return vContext;
	}

	private HashMap<String, Object> populateRoutingRuleEmailJobCodeContext(
			List<String> jobCodes, HashMap<String, Object> vContext) {
		if(jobCodes != null) {
			StringBuilder sb = new StringBuilder();
			for(String jobCode: jobCodes) {
				if(sb.length() != 0) {
					sb.append(", ");
				}
				sb.append(jobCode);
			}
			vContext.put("JobCode", sb.toString());
		}
		return vContext;
	}

	private HashMap<String, Object> populateRoutingRuleEmailHeaderInfo(
			RoutingRuleEmailVO emailVO, HashMap<String, Object> vContext) {
		vContext.put("RuleContactFN", emailVO.getRuleContactFN());
		vContext.put("RuleContactLN", emailVO.getRuleContactLN());
		vContext.put("CARRuleName", emailVO.getCarRuleName());
		vContext.put("BuyerCompanyName", emailVO.getBuyerCompanyName());
		vContext.put("BuyerCompanyID", emailVO.getBuyerCompanyID());
		vContext.put("MissingPriceFlag", convertBooleanToString(emailVO.getIsMissingPrice()));
		vContext.put("StatusChangeFlag", convertBooleanToString(emailVO.getIsStatusChange()));
		return vContext;
	}

	/**
	 * This method is here to convert the boolean to 
	 * something that Responsys can handle.  They are 
	 * unable to handle true and false and use y and n instead.
	 * @param b
	 * @return y or n
	 */
	private String convertBooleanToString(Boolean b) {
		if(b == null) {
			return null;
		}
		if(b.booleanValue()) {
			return "y";
		}
		return "n";
	}

	public String[] splitEmail(String emailId){
		emailId = StringUtils.deleteWhitespace(emailId);
		return StringUtils.split(emailId, ';');
	}

	// slt-2232-START
	public void sendTransferSLBucksEscheatmentEmail(EmailVO emailVO, Double amount, String roleType, String createdDate)
			throws MessagingException, IOException, BusinessServiceException {
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		Map<String, Object> alertMap = new AOPHashMap();
		alertMap.put(SystemGeneratedEmailConstants.AMOUNT_DEBITED, amount);
		alertMap.put(AOPConstants.AOP_CURRENT_DATE, date.toString());
		alertMap.put(SystemGeneratedEmailConstants.CREATED_DATE, createdDate);
		alertMap.put(AOPConstants.AOP_USER_EMAIL, emailVO.getTo());
		alertMap.put(AOPConstants.AOP_TEMPLATE_ID, emailVO.getTemplateId());
		try {
			AlertTask alertTask = getEmailAlertTask(alertMap);
			alertDao.addAlertToQueue(alertTask);
			logger.info("SLBucks Debit email SENT************************");
		} catch (DataServiceException e) {
			logger.info("Error while sending SLBucks Debit email SENT ****\n" + e);
			throw new BusinessServiceException("Error while sending SLBucks Debit email ");
		}
	}
	// slt-2232-END
		
	public VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	public JavaMailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public ClassPathResource getEmailImg() {
		return emailImg;
	}

	public void setEmailImg(ClassPathResource emailImg) {
		this.emailImg = emailImg;
	}

	public ClassPathResource getServiceLiveEmailImg() {
		return ServiceLiveEmailImg;
	}

	public void setServiceLiveEmailImg(ClassPathResource serviceLiveEmailImg) {
		ServiceLiveEmailImg = serviceLiveEmailImg;
	}

	public void sendGenericEmailWithLogo(EmailVO email) {

		this.sendGenericEmailWithServiceLiveLogo(email.getTo(), email.getFrom(), email.getSubject(), email.getMessage());
	}

	private String getEmailAddressTo() {
		if(_emailAddressTo == null) {
			_emailAddressTo = PropertiesUtils.getPropertyValue(Constants.EMAIL_ADDRESSES.SERVICELIVE_ADMIN);
		}
		return _emailAddressTo;
	}

	private String getEmailAddressFrom() {
		if(_emailAddressFrom == null) {
			_emailAddressFrom = PropertiesUtils.getPropertyValue(Constants.EMAIL_ADDRESSES.SERVICELIVE_ADMIN);
		}
		return _emailAddressFrom;
	}

	private String getSiteUrl() {
		if(_siteUrl == null) {
			_siteUrl = PropertiesUtils.getFMPropertyValue(Constants.AppPropConstants.SERVICE_URL);
		}
		return _siteUrl;
	}
	private String getSiteUrlForRole(String appkey) {
		String url = PropertiesUtils.getFMPropertyValue(appkey);
		return url;
	}
	public ClassPathResource getVerifiedEmailImg() {
		return verifiedEmailImg;
	}

	public void setVerifiedEmailImg(ClassPathResource verifiedEmailImg) {
		this.verifiedEmailImg = verifiedEmailImg;
	}

	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

	public EmailService getEmailService() {
		return emailService;
	}

	public TemplateDaoImpl getTemplateDao() {
		return templateDao;
	}

	public void setTemplateDao(TemplateDaoImpl templateDao) {
		this.templateDao = templateDao;
	}

	public AlertDaoImpl getAlertDao() {
		return alertDao;
	}

	public void setAlertDao(AlertDaoImpl alertDao) {
		this.alertDao = alertDao;
	}
}// end class
