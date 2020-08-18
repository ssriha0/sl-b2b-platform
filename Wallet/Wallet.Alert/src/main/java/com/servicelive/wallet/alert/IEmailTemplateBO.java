/**
 * 
 */
package com.servicelive.wallet.alert;

// TODO: Auto-generated Javadoc
/**
 * Interface IEmailTemplateBO.
 */
public interface IEmailTemplateBO {

	/**
	 * sendAchAckResponseEmail.
	 * 
	 * @param sentTo 
	 * @param sentFrom 
	 * @param templateId 
	 * @param filePath 
	 * @param reasonCode 
	 * @param result 
	 * 
	 * @return void
	 */
	public void sendAchAckResponseEmail(String sentTo, String sentFrom, int templateId, String filePath, String reasonCode, String result);

	/**
	 * sendACHFailureEmailToBuyer.
	 * 
	 * @param buyerId 
	 * @param transId 
	 * @param amount 
	 * @param returnDesc 
	 * @param templateId 
	 * 
	 * @return void
	 */
	public void sendACHFailureEmailToBuyer(long buyerId, String transId, Double amount, String returnDesc, int templateId);

	/**
	 * sendAcknowledgmentNotificationEmail.
	 * 
	 * @param sentToAddress 
	 * 
	 * @return void
	 */
	public void sendAcknowledgmentNotificationEmail(String sentToAddress, String mailBody);

	/**
	 * sendFailureEmail.
	 * 
	 * @param bodyText 
	 * 
	 * @return void
	 */
	public void sendFailureEmail(String bodyText);

	/**
	 * sendGenericEmailWithAttachment.
	 * 
	 * @param sentTo 
	 * @param sentFrom 
	 * @param subject 
	 * @param body 
	 * @param filePath 
	 * @param fileName 
	 * 
	 * @return void
	 */
	public void sendGenericEmailWithAttachment(String[] sentTo, String sentFrom, String subject, String body, String filePath, String fileName);

	/**
	 * sendNotificationEmail.
	 * 
	 * @param sentToAddress 
	 * @param subject 
	 * @param text 
	 * 
	 * @return void
	 */
	public void sendNotificationEmail(String sentToAddress, String subject, String text);

	/**
	 * sendPTDFileProcessAlert.
	 * 
	 * @param alertToAddress 
	 * @param alertCCAddress 
	 * @param subject 
	 * @param bodyText 
	 * 
	 * @return void
	 */
	public void sendPTDFileProcessAlert(String alertToAddress, String alertCCAddress, String subject, String bodyText);

	/**
	 * sendTemplateEmail.
	 * 
	 * @param sentTo 
	 * @param sentFrom 
	 * @param templateId 
	 * 
	 * @return void
	 */
	public void sendTemplateEmail(String sentTo, String sentFrom, int templateId);

	/**
	 * sendTemplateEmail.
	 * 
	 * @param sentTo 
	 * @param sentFrom 
	 * @param subject 
	 * @param evaluateAs 
	 * @param templateId 
	 * 
	 * @return void
	 */
	public void sendTemplateEmail(String sentTo, String sentFrom, String subject, String evaluateAs, int templateId);
	
	public void sendFinanceBatchAlert(String emailSubject,String emailBody);

	public void sendWithdrawConfirmationEmailAndSMS(Integer integer, double doubleValue, Integer integer2, boolean b);
}
