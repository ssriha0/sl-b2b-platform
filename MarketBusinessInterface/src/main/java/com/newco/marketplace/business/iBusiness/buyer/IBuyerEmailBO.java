/**
 * 
 */
package com.newco.marketplace.business.iBusiness.buyer;

import java.io.IOException;

import javax.mail.MessagingException;

import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.vo.provider.TemplateVo;

/**
 * @author paugus2
 * 
 */
public interface IBuyerEmailBO {
	public void sendConfirmationMail(String username, String password,String email) throws MessagingException, IOException;
	public void sendConfirmationMail(TemplateVo templateVO, String username, String password, String deeplinkKey, String email1,String email2) throws MessagingException, IOException;
	public void sendForgotUsernameMail(String username, String email,String [] ccArr) throws BusinessServiceException, IOException;
	public void sendGenericEmail(String recipient, String from, String text) throws BusinessServiceException;
	public void sendConfirmationMailTeamMember(String username, String password,
			String email) throws MessagingException, IOException;
	public void sendConfirmationPasswordMail(String username, String password,
			String email,String ccArr[]) throws MessagingException, IOException ;
	public void sendConfirmationMailForInValidState(String username, String password,String email) throws MessagingException, IOException;
}
