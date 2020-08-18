package com.newco.marketplace.web.delegates.provider;

import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.web.dto.provider.ForgotUsernameDto;

public interface IForgetUsernameDelegate {
	public ForgotUsernameDto loadLostUsernameList(ForgotUsernameDto forgotUsernameDto) throws DelegateException;
	public ForgotUsernameDto loadLiteLostUsereProfile(ForgotUsernameDto forgotUsernameDto) throws DelegateException;
	public ForgotUsernameDto getSecQuestionForUserName(ForgotUsernameDto forgotUsernameDto)throws DelegateException;
	public ForgotUsernameDto validateAns(ForgotUsernameDto forgotUsernameDto)throws DelegateException;
	public boolean sendForgotUsernameMail(ForgotUsernameDto forgotUsernameDto); 
	public String getUserFromInterimPassword(String  temppasword) throws DelegateException;	
	public int getVerificationCount(String userName) throws DelegateException;		
	public void updateVerificationCount(String userName, int count) throws DelegateException;
	public void lockProfile(String userName) throws DelegateException;
	public void invalidatePassword(String password) throws DelegateException;
	public boolean doValidatePhoneAndZip(ForgotUsernameDto forgotUsernameDto, String userPhoneNumber,
			String userZipCode, String userCompanyName) throws Exception;
	public ForgotUsernameDto loadLostUsername(ForgotUsernameDto forgotUsernameDto) throws DelegateException;
	public boolean resetPassword(String userName, String name, String emailAddress, String ccArr[]);
	
	public String getResourceIdFromUsername(String  username) throws DelegateException ;
}