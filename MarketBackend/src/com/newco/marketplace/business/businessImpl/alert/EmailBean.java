package com.newco.marketplace.business.businessImpl.alert;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.newco.marketplace.business.businessImpl.MPBaseBusinessBean;
import com.newco.marketplace.dto.vo.provider.TMBackgroundCheckVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.util.PropertiesUtils;
import com.newco.marketplace.utils.StackTraceHelper;

public class EmailBean extends MPBaseBusinessBean {

    private JavaMailSenderImpl mailSender;
    private SimpleMailMessage message;
    private Resource fileResource;
    private String plusOneURL;
    private SimpleMailMessage backgroundCheckEmailMessage;
    private String backgroundCheckEmailSubject;
    private String backgroundCheckEmailFrom;

    private static final Logger logger = Logger.getLogger(EmailBean.class
            .getName());

    public void setMessage(SimpleMailMessage message) {

        this.message = message;
    }

    public void sendEmail(String email) throws MessagingException {

        // user and position objects looked up...
        // SimpleMailMessage msg = new SimpleMailMessage(this.message);

        message.setTo(email);

        StringBuffer txt = new StringBuffer();
        txt.append("Coming soon");
        message.setText(txt.toString());
        try {
            mailSender.send(message);
        } catch (MailException ex) {
            ex.printStackTrace();
        }
    }
/*
    public void sendConfirmationMail(String username, String password,
            String email) throws MessagingException, IOException {

        logger.info("entering email ************************");

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        // use the true flag to indicate you need a multipart message
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setTo(email);
        helper.setFrom("registration@searsprovider.com");
        StringBuilder txt = new StringBuilder();
        txt.append("<html><body><table><tr><td align =\"left\">");
        txt
                .append("<img src='cid:logo'></td></tr><tr><td align =\"left\"><br/><br/>");
        txt
                .append("<strong><b><font size=1 color=\"#505050\" face=Arial><span style='font-size:8.5pt;font-family:Arial;color:#505050'>Thank you for registering "
                        + " on SearsProvider.com. We look forward to working with you and adding value to your business.</span></font></b></strong>");
        txt
                .append("<font size=1 color=\"#505050\" face=Arial><span style='font-size:8.5pt;font-family:Arial;color:#505050'> <br> <br>"
                        + "Here is the information we received from you:<br> <br>");
        txt.append("&nbsp;&nbsp;&nbsp;&nbsp; username: " + username + " <br/><br/>&nbsp;&nbsp;&nbsp;&nbsp; password: "
                + password + " <br/><br/>");
        txt
        .append("&nbsp;&nbsp;&nbsp;&nbsp; Keep your login information in a safe place. <br /><br /> " );

        txt.append("We appreciate the time you spent to provide us with your primary profile information. At this point, we need you to complete your <u>Business ");
        txt.append("Profile</u>  and <u>Team Member</u> Profile. Completing the application process and passing the background check will allow you to target and ");
        txt.append("acquire new, profitable customers. By participating in the Sears Authorized Service Provider program, you will have the ");
        txt.append("opportunity to become a leading provider in the service industry. By providing world class service, you will qualify for additional business leads.<br/><br/> ");
        txt.append(" Through this program, participating service providers can: <br /><br />");
        txt.append("&nbsp;&nbsp; &bull; Increase sales opportunities. Service Providers can benefit from Sears trained sales associates who will offer your skills to our customers. 	<br />");
        txt.append("&nbsp;&nbsp; &bull; Increase Utilization.  Excess service capacity can be deployed. 	<br />");
        txt.append("&nbsp;&nbsp; &bull; Extend market reach. As a primary service provider to Sears customers, Service Providers can build revenue, sales momentum, and market differentiation.	<br />");
        txt.append("&nbsp;&nbsp; &bull; Extend service offerings. Service Providers can deepen their services and solutions to respond to more complex service opportunities.	<br />");
        txt.append(" <br />");
        txt.append(" <u>Now that you are registered</u>, please return to the SearsProvider.com site to log in and complete the business application process and enter your team members. <br />");
        txt.append("Please confirm your registration and complete the application process to become a Sears Preferred Provider by clicking the link below or pasting it into your browser's address bar. <br/><br/>");
        txt.append("<a href=\"http://151.149.116.196:8080/market/\">http://www.searsprovider.com</a> <br/><br/>");
        txt.append("If you have any questions about using SearsProvider.com or providing service for Sears, please read our Service Provider FAQs. ");
        txt.append("If you have questions concerning the use of your information by SearsProvider.com, please read our privacy statement. <br />");

        txt.append("</td></tr></body></html>");

        if (logger.isDebugEnabled())
          logger.debug("confirmationEmail : " + txt.toString());
        
        helper.setText(txt.toString(), true);
        helper.setSubject("Thank you for Registering");
        
        ClassPathResource res = new ClassPathResource("/images/icon_logo.gif");
        helper.addInline("logo", res);
        mailSender.send(mimeMessage);
        logger.info("email SENT************************");
    }*/
    // public static void main(String [] args) throws MessagingException{
    // new EmailBean().email();
    // }

    // public void oldsendConfirmationMail(String username, String password,
    // String email) throws MessagingException{
    // System.out.println("calling email ************************");
    // //email();
    // SimpleMailMessage msg = new SimpleMailMessage(this.message);
    // msg.setTo(email);
    // msg.setSubject("Registration Confirmation");
    // StringBuffer txt = new StringBuffer();
    // txt.append("Thankyou for Registering \n");
    // txt.append("You can log in using the following details to access your
    // account \n");
    // txt.append("username: "+ username + " \n password: " + password +" \n");
    // txt.append("http://151.149.116.196:8080/market/login.jsp");
    // msg.setText(txt.toString());
    // try {
    // mailSender.send(msg);
    // } catch (MailException ex) {
    // ex.printStackTrace();
    // }
    // }

    /*
    public String cachedBackGroundCheckEmailText = "<html><head></head><body>"
            + "Good-day,<br/><p> <br/>"
            + "This is a background check request from your provider.  SEARS cares about its customers.  "
            + "We know you, as a service professional, care about them too.  As part of our continued effort to "
            + "ensure the highest levels of safety, security and satisfaction during an in-home service experience, "
            + "we require that all of our service providers complete a thorough background check. <br/> </p>"
            + "<p><br/> Your professionalism and willingness to comply with this request "
            + "shows your commitment to our efforts to keep our"
            + " Sears respects your privacy, and does not intend to compromise your trust and goodwill. <br/></p>"
            + "<p><br/>Please click the link below to complete the quick and simple background screening application.</p> <br/>";
*/
    
    public String cachedBackGroundCheckEmailText = "<html><head></head><body> <strong><b><font size=1 color=\"#505050\" face=Arial><span style='font-size:8.5pt;font-family:Arial;color:#505050'>"
            + "Good-day,</span></font></b></strong><br/><p> <br/> <font size=1 color=\"#505050\" face=Arial><span style='font-size:8.5pt;font-family:Arial;color:#505050'>"
            + "This is a background check request from your provider. SEARS cares about its customers. "
            + "We know you, as a service professional, care about them too. As part of our continued effort "
            + "to ensure the highest levels of safety, security and satisfaction during an in-home service "
            + "experience, we require that all of our service providers complete a thorough background check."
            + "<br><p>"
            + "Your professionalism and willingness to comply with this request shows your commitment to our "
            + "efforts to keep our customers smiling.  Sears respects your privacy and does not wish to "
            + "compromise your trust and goodwill in any way. <br>"
            + "Please click the link below to complete the quick and simple background screening application.<br><br> ";
        
    public void sendBackgroundCheckEmail(TMBackgroundCheckVO tmbcVO)
            throws BusinessServiceException {
        
        // user and position objects looked up...
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeHelper = new MimeMessageHelper(mimeMessage,
                    true);
            
            StringBuffer txt = new StringBuffer();
            txt.append(cachedBackGroundCheckEmailText);
            txt.append("<P></P>");
            txt.append("<B><a href='" + this.getPlusOneURL() + tmbcVO.getResourceId() + "'>Background Check</a></B>");
            txt.append("<br/><br/><img src='cid:backgroundCheckLogo'>" + "<br/><br/>");
            txt.append("</body></html>");
            mimeHelper.setText(txt.toString(), true);
            mimeHelper.setSubject(this.getBackgroundCheckEmailSubject());
            ClassPathResource res = new ClassPathResource(
                    "/images/backgroundCheckLogo.jpg");
            mimeHelper.addInline("backgroundCheckLogo", res);
            
            if (null != tmbcVO.getResourceEmail()
                    && (!("".equals(tmbcVO.getResourceEmail().trim())))) {
                mimeHelper.setTo(tmbcVO.getResourceEmail());
            }
            if (tmbcVO.getVendorEmail() != null
                    && !tmbcVO.getVendorEmail().trim().equals("")) {
                mimeHelper.setCc(tmbcVO.getVendorEmail());
            }
            logger.info("helper txt: " + mimeHelper.toString());
            try {
                mimeHelper.setFrom(this.getBackgroundCheckEmailFrom());
                mailSender.send(mimeMessage);
            } catch (MailException ex) {
                throw new BusinessServiceException(ex.getMessage(), ex);
            }
        } catch (MailException e) {
            logger.info(StackTraceHelper.getStackTrace(e));
            throw new BusinessServiceException(e.getMessage(), e);
        } catch (MessagingException e) {
            logger.debug(StackTraceHelper.getStackTrace(e));
            throw new BusinessServiceException(e.getMessage(), e);
        }

    }// sendBackGroundCheckEMail

    public void sendForgotUsernameMail(String username, String email) throws MessagingException, IOException {

        logger.info("EmailBean: sendForgotUsernameMail");

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        // use the true flag to indicate you need a multipart message
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setTo(email);
        helper.setFrom("registration@searsprovider.com");
        StringBuffer txt = new StringBuffer();
        txt.append("<html><body><table><tr><td align =\"left\">");
        txt
                .append("<img src='cid:logo'></td></tr><tr><td align =\"left\"><br/><br/>");
        txt
                .append("<font size=1 color=\"#505050\" face=Arial><span style='font-size:8.5pt;font-family:Arial;color:#505050'> <br> <br>"
                        + "Below is the Username information you requested:<br> <br>");
        //txt.append("&nbsp;&nbsp;&nbsp;&nbsp; Username: " + username + "<br><br>");
        
        
        	txt.append(username + "<br><br>");
        
        
        
        txt
        .append("Please keep your login information in a safe place. <br /><br /> " );

        txt.append("<a href=\"http://151.149.116.196:8080/market/\">http://www.searsprovider.com</a> <br/><br/>");

        txt.append("If you have any questions about using SearsProvider.com or providing service for Sears, please read our Service Provider FAQs. ");
        txt.append("If you have questions concerning the use of your information by SearsProvider.com, please read our privacy statement. <br />");

        txt.append("</td></tr></body></html>");

        helper.setText(txt.toString(), true);
        helper.setSubject("Sears Preferred Provider Network: Your Username Request");
        
        ClassPathResource res = new ClassPathResource("/images/icon_logo.gif");
        helper.addInline("logo", res);
        mailSender.send(mimeMessage);
    }
    
    public void sendGenericEmail(String recipient, String from, String text) throws BusinessServiceException {
    	
    
    try {
    	JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    	mailSender.setHost(PropertiesUtils.getPropertyValue("smtp_server"));
	    MimeMessage mimeMessage = mailSender.createMimeMessage();
	
	    MimeMessageHelper mimeHelper = new MimeMessageHelper(mimeMessage,
	            true);
	    mimeHelper.setText(text, true);
	    mimeHelper.setSubject("Service Order Notification");
//	    ClassPathResource res = new ClassPathResource(
//	            "/images/newco_logo.jpg");
//	    mimeHelper.addInline("newco_logo", res);
	    
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
	    logger.info(StackTraceHelper.getStackTrace(e));
	    throw new BusinessServiceException(e.getMessage(), e);
	} catch (MessagingException e) {
	    logger.debug(StackTraceHelper.getStackTrace(e));
	    throw new BusinessServiceException(e.getMessage(), e);
	}

}// sendBackGroundCheckEMail
    
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

    public SimpleMailMessage getBackgroundCheckEmailMessage() {

        return backgroundCheckEmailMessage;
    }

    public void setBackgroundCheckEmailMessage(
            SimpleMailMessage backgroundCheckEmailMessage) {

        this.backgroundCheckEmailMessage = backgroundCheckEmailMessage;
    }

    public String getBackgroundCheckEmailSubject() {

        return backgroundCheckEmailSubject;
    }

    public void setBackgroundCheckEmailSubject(
            String backgroundCheckEmailSubject) {

        this.backgroundCheckEmailSubject = backgroundCheckEmailSubject;
    }

    public String getBackgroundCheckEmailFrom() {

        return backgroundCheckEmailFrom;
    }

    public void setBackgroundCheckEmailFrom(String backgroundCheckEmailFrom) {

        this.backgroundCheckEmailFrom = backgroundCheckEmailFrom;
    }

}
