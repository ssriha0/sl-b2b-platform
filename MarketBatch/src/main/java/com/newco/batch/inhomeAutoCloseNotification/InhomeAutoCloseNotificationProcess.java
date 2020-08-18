package com.newco.batch.inhomeAutoCloseNotification;

import java.util.ArrayList;

import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.newco.marketplace.business.iBusiness.inhomeautoclose.constants.InHomeConstants;
import com.newco.marketplace.inhomeautoclosenotification.IInhomeAutoCloseBO;
import com.newco.marketplace.inhomeautoclosenotification.vo.InHomeAutoCloseVO;


public class InhomeAutoCloseNotificationProcess {
	
	private static final Logger logger = Logger.getLogger(InhomeAutoCloseNotificationProcess.class);
	//Use inhomeautoCloseBO to fetch details from DB		
		private IInhomeAutoCloseBO inhomeautoCloseBO;
		
		public void process() throws Exception{
			logger.info("InhomeAutoCloseNotificationProcess.processNotification: Inside process");
			String recipientId=inhomeautoCloseBO.getRecipientIdFromDB(InHomeConstants.AUTOCLOSE_FAILURE_NOTIFICATION_RECIPIENT_MAILS);
			List<InHomeAutoCloseVO> notificationList = 
					inhomeautoCloseBO.fetchRecords();
			if(null != notificationList && !notificationList.isEmpty()){
				sendFailureEmails(notificationList, recipientId, InHomeConstants.SUBJECT_INHOME_AUTOCLOSE);	
				
			}
		}
		
		//Method to send failure emails.
			private void sendFailureEmails(List<InHomeAutoCloseVO> failureList, String emailIds, String subject) {
				List<String> failedNotificationIds = new ArrayList<String>();
				logger.info("inJavaMail");
				if(null != emailIds){
					String toRecipients[] = emailIds.split(";");
					StringBuilder content = new StringBuilder(InHomeConstants.EMAIL_CONTENT+"\n\n");
					JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
					mailSender.setHost(InHomeConstants.MAIL_HOST);
					MimeMessage mimeMessage = mailSender.createMimeMessage();
					MimeMessageHelper mimeHelper = null;
					try {
						mimeHelper = new MimeMessageHelper(mimeMessage, true);
						mimeHelper.setSubject(InHomeConstants.NOTIFICATION_SUBJECT_INHOME1+subject+InHomeConstants.NOTIFICATION_SUBJECT_INHOME2);
						mimeHelper.setFrom(InHomeConstants.EMAIL_FROM);
						mimeHelper.setTo(toRecipients);
						if(null != failureList){
							content.append("<br></br>");
							content.append("<html><table border='1px solid black' cellspacing='2' cellpadding='3' layout='fixed'>");
							content.append("<tr><th bgcolor="+"#C4C4C4"+"> Sl No.</th><th bgcolor="+"#C4C4C4"+"> SO ID </th></tr>");
							int count = 1;
							for (InHomeAutoCloseVO  inHomeAutoCloseVO : failureList){
								if(null != inHomeAutoCloseVO){
									failedNotificationIds.add(inHomeAutoCloseVO.getAutoCloseId().toString());
									content.append("<tr><td>");
									content.append(count + "</td><td>"+ inHomeAutoCloseVO.getSoId()+"</td></tr>");
									count++;
								}
							}
							content.append("</table></html>");
						}
					}
					catch (Exception e) {
						logger.error("Caught Exception"+e.getMessage());
					}
					try {
						mimeHelper.setText(content.toString());
						mimeMessage.setContent(content.toString(),"text/html");
						mailSender.send(mimeMessage);
						if(null != failedNotificationIds && !failedNotificationIds.isEmpty()){
							inhomeautoCloseBO.setEmailIndicator(failedNotificationIds);
						}
					}
					catch (MessagingException e) {
						logger.error("Caught Exception", e);
					}
					catch (Exception bse) {
						logger.error("Caught Exception", bse);
					}
				}
			}

			public IInhomeAutoCloseBO getInhomeautoCloseBO() {
				return inhomeautoCloseBO;
			}

			public void setInhomeautoCloseBO(IInhomeAutoCloseBO inhomeautoCloseBO) {
				this.inhomeautoCloseBO = inhomeautoCloseBO;
			}


}
