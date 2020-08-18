/**
 * 
 */
package com.newco.marketplace.business.iBusiness.provider;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.mail.MessagingException;

import com.newco.marketplace.dto.vo.EmailVO;
import com.newco.marketplace.dto.vo.fullfillment.FullfillmentEntryVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.vo.audit.AuditVO;
import com.newco.marketplace.vo.provider.AuditEmailVo;
import com.sears.os.vo.SerializableBaseVO;

import org.apache.velocity.VelocityContext;


/**
 * @author LENOVO USER
 * @param <VelocityContext>
 * 
 */
public interface IEmailTemplateBO {

	public void sendEmail(final AuditEmailVo auditEmailVo, final String emailText);
	public void sendGenericEmailWithLogo (String emailTo, String emailFrom, String emailSubject, String emailText);
	public void sendGenericEmailWithLogoWithCc (String emailTo, String emailFrom, String emailSubject, String emailText,String emailCc);
	public void sendGenericEmailWithLogoWithoutCc (String emailTo, String emailFrom, String emailSubject, String emailText);
	public void sendGenericEmailWithLogo(EmailVO email);
	public void sendGenericEmailWithAttachment(EmailVO email, File fileObj, String attachmentName) throws MessagingException, IOException;

	public void sendEmailVLMessageFailure(EmailVO email, FullfillmentEntryVO fullfillmentEntryVO) throws Exception;
	
    public void sendBuyerPostingFeeEmail(EmailVO email, String soId, String ledgerTransIdPost, Double transAmtPost, String ledgerTranIdRes, Double transAmtRes) throws MessagingException, IOException;
    public void sendBuyerCancellationEmail(EmailVO emailVO, String soId, Integer vendorId, String ledgerTransId, Double transAmount,String roleType) throws MessagingException, IOException;
    
    public void sendSLBucksCreditEmail(EmailVO emailVO, String ledgerTransId, Double transAmount,String roleType)throws MessagingException, IOException;
    public void sendSLBucksDebitEmail(EmailVO emailVO, String ledgerTransId, Double transAmount,String roleType,boolean isEscheatment)throws MessagingException, IOException;

    public void sendProviderSOCancelledEmail(EmailVO emailVO, Integer buyerId, String soId, String ledgerTransId, Double transAmount)throws MessagingException, IOException;
    
 	public void sendBuyerSOClosedEmail(EmailVO emailVO, Integer buyerId, Integer vendorId, String soId, String ledgerTransId,  Double transAmount, String providerFirstName, String providerLastName, String consumerFlag,Integer providerId)throws MessagingException, IOException;
	public void sendProviderSOClosedEmail(EmailVO emailVO, Integer buyerId, String soId, String ledgerTransIdPay, String ledgerTransIdFee, Double transAmountPay, Double transAmountFee)throws MessagingException, IOException;
	
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
	public void sendRemoveUserMailConfirmation(String resourceId,String userName,String firstName,String lastName,String adminUserName,String toAddress)throws MessagingException, IOException;
	public Map<String, String> getEmailMap(VelocityContext context);
	public void sendErrorEmail(SerializableBaseVO vo, String errorMessage,String emailTo,String emailFrom);
	
    public void sendNoteOrQuestionEmail(EmailVO email, String soID, String soTitle, String roleInd) throws MessagingException, IOException;
    public void sendFailedToAcceptSOEmail(EmailVO email, ServiceOrder so)throws MessagingException, IOException;
}
