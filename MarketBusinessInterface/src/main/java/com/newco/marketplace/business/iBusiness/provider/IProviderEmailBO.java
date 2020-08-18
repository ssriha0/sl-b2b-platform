/**
 * 
 */
package com.newco.marketplace.business.iBusiness.provider;

import java.io.IOException;

import javax.mail.MessagingException;

import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.vo.provider.TMBackgroundCheckVO;
import com.newco.marketplace.vo.provider.UserProfile;

/**
 * @author LENOVO USER
 * 
 */
public interface IProviderEmailBO {

	public void sendEmail(String email) throws MessagingException;
	public void sendConfirmationMail(String username, String password,String email,String firstName) throws MessagingException, IOException;
	public void sendBackgroundCheckEmail(TMBackgroundCheckVO tmbcVO) throws BusinessServiceException, IOException;
	public void sendForgotUsernameMail(String username, String email,String [] ccArr) throws BusinessServiceException, IOException;
	public void sendGenericEmail(String recipient, String from, String text) throws BusinessServiceException;
	public void sendBackgroundCheckEmailWithLogo (String emailTo, String emailFrom, String emailSubject, String emailText);
	public void sendConfirmationMailTeamMember(String username, String password,
			String email) throws MessagingException, IOException;
	public void sendConfirmationPasswordMail(String username, String password,
			String email,String ccArr[]) throws MessagingException, IOException ;
	public void sendServiceProviderRegistrationConfirmationMail(String username, String password, String email) throws MessagingException, IOException;
	public void sendConfirmationMailForInValidState(String username, String password,String email) throws MessagingException, IOException;
	public void sendProviderMemberRegistrationConfirmationMail(String username, String password, 
			String email, String altEmail, Integer resourceId, UserProfile userProfile) throws BusinessServiceException, IOException;
}
