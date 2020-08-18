package com.newco.marketplace.business.businessImpl.provider;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.businessImpl.common.EmailBOUtil;
import com.newco.marketplace.business.iBusiness.provider.IForgotUsernameBO;
import com.newco.marketplace.business.iBusiness.provider.IProviderEmailBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.iDao.common.IInterimPasswordDao;
import com.newco.marketplace.persistence.iDao.provider.IUserProfileDao;
import com.newco.marketplace.util.PropertiesUtils;
import com.newco.marketplace.util.StemmingSearch;
import com.newco.marketplace.vo.common.InterimPasswordVO;
import com.newco.marketplace.vo.provider.LostUsernameVO;
import com.newco.marketplace.vo.provider.UserProfile;

public class ForgotUserNameBOImpl implements IForgotUsernameBO {
	
	private IInterimPasswordDao iInterimPasswordDao;
	private IUserProfileDao iUserProfileDao;
	private IProviderEmailBO iProviderEmailBO;

	private static final Logger logger = Logger.getLogger(ForgotUserNameBOImpl.class);
	
	private static int _maxSecretQuestionAttempts = Integer.MIN_VALUE;
	private static int _interim_pwd_exp = Integer.MIN_VALUE;
	private static int _lockingPeriod = Integer.MIN_VALUE;
	
	private int getMaxSecretQuestionAttempts() {
		if(_maxSecretQuestionAttempts == Integer.MIN_VALUE) {
			_maxSecretQuestionAttempts = Integer.parseInt(PropertiesUtils.getPropertyValue(Constants.AppPropConstants.MAX_SECRET_QUESTION_ATTEMPTS_LIMIT));
		}
		return _maxSecretQuestionAttempts;
	}

	private int getInterimPwdExp() {
		if(_interim_pwd_exp == Integer.MIN_VALUE) {
			_interim_pwd_exp = Integer.parseInt(PropertiesUtils.getPropertyValue(Constants.AppPropConstants.INTERIM_PWD_EXPIRATION_TIME));
		}
		return _interim_pwd_exp;
	}

	private int getLockingPeriod() {
		if(_lockingPeriod == Integer.MIN_VALUE) {
			_lockingPeriod = Integer.parseInt(PropertiesUtils.getPropertyValue(Constants.AppPropConstants.ACCOUNT_LOCK_TIME));
		}
		return _lockingPeriod;
	}

	public ForgotUserNameBOImpl(IUserProfileDao iUserProfileDao,
								IProviderEmailBO iProviderEmailBO, 
								IInterimPasswordDao iInterimPasswordDao){
		this.iUserProfileDao = iUserProfileDao;
		this.iProviderEmailBO = iProviderEmailBO;
		this.iInterimPasswordDao = iInterimPasswordDao;

	}
	 
	/***
	 * Used to get username given email address.
	 * @param lostUsernameVO
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<LostUsernameVO> loadLostUsernameList(String email, int roleId)	throws BusinessServiceException
	{
		System.out.println("-----------------loadLostUsernameBO-------------");	
		List<LostUsernameVO> result= null;
		
	        try {
	        	result = (ArrayList<LostUsernameVO>) iUserProfileDao.loadLostUsernameList(email, roleId);
	        }catch (DBException dae) {
				logger
				.info("SQL Exception @ForgotUserNameBOImpl.loadLostUsername() while retriving the lostUserNamedetails"
						+ dae.getMessage());
				throw new BusinessServiceException("SQL Exception @ForgotUserNameBOImpl.loadLostUsername() while retriving the lostUserNamedetails");
			}// end catch
		
		return result;
	}
	
    /**
     * 
     * @param resourceId
     * @param roleId
     * @return
     * @throws BusinessServiceException
     */
	
	public LostUsernameVO loadLostUsername(String userName, String resourceId, int roleId) throws BusinessServiceException {
		System.out.println("-----------------loadLostUsernameBO-------------");	
		LostUsernameVO result = null;
		
	        try {
	        	if (userName == null)
	        		result =  iUserProfileDao.loadLostUsernameByResourceId(resourceId, roleId);
	        	else
	        		result = iUserProfileDao.loadLostUsername(userName, roleId);
	        	
	        	if(null != result){
	        		updateLockInd(result);
	        	}
	        	return result;
	        }catch (DBException dae) {
				logger
				.info("SQL Exception @ForgotUserNameBOImpl.loadLostUsername() while retriving the lostUserNamedetails"
						+ dae.getMessage());
				throw new BusinessServiceException("SQL Exception @ForgotUserNameBOImpl.loadLostUsername() while retriving the lostUserNamedetails");
			}// end catch
	}
	
	private void updateLockInd(LostUsernameVO lostUsernameVO) throws DBException{
		if (lostUsernameVO.getLockedInd() == 1) {
			Date now = new Date();
			if (now.getTime() - lostUsernameVO.getModifiedDate().getTime() > getLockingPeriod())  {
				lostUsernameVO.setLockedInd(0);
				iUserProfileDao.unlockProfile(lostUsernameVO.getUserName());
			}
		}	
	}
	
	/***
	 * 
	 * @param lostUsernameVO
	 * @return
	 * @throws BusinessServiceException
	 */
	public LostUsernameVO loadLitLostUsereProfile(String email, String userName) throws BusinessServiceException {
		
		LostUsernameVO  result= null;
	        try {
	        	//result = (ArrayList<LostUsernameVO>) iUserProfileDao.validateEmailUsername(lostUsernameVO);
	        	result = (LostUsernameVO) iUserProfileDao.loadLitLostUsereProfile(email, userName);	        
	        	if (result != null)
	        		updateLockInd(result);
	        }catch (DBException dae) {
				logger
				.info("SQL Exception @ForgotUserNameBOImpl.validateEmailUsername() while retriving the lostUserNamedetails"
						+ dae.getMessage());
				throw new BusinessServiceException("SQL Exception @ForgotUserNameBOImpl.validateEmailUsername() while retriving the lostUserNamedetails");
			}// end catch
		
		return result;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.provider.IForgotUsernameBO#sendForgotUsernameMail(java.lang.String, java.lang.String)
	 */
	
	//public boolean sendForgotUsernameMail(String userName, String email, Integer roleId) {
	public boolean sendForgotUsernameMail(LostUsernameVO lostUserNameVO) {
		// TODO Auto-generated method stub
		String userName = lostUserNameVO.getUserName();
		int roleId = lostUserNameVO.getUserId();
		String email = lostUserNameVO.getEmailAddress();
		String deepLinkUrl = EmailBOUtil.createDeepLinkForUserName();
		lostUserNameVO.setDeepLinkUrl(deepLinkUrl);
		
		System.out.println("I am in sendForgotUsernameMail() of FORGOTUSERNAMEBOIMPL");
		System.out.println("userName == "+  userName);
		String provEmail = null;
		try{
			if(roleId==1)
				provEmail = iUserProfileDao.getAdminAddress(userName);
			String ccArr[] =null;
			if(provEmail!=null && provEmail!= "" && !email.equals(provEmail))
			{
				ccArr = new String[1];
				ccArr[0] = new String(provEmail);
			}
			// Code commented to send mail through cheetah mail.
			//iProviderEmailBO.sendForgotUsernameMail(userName, email,ccArr);
			return true;
		}catch (Exception e ){
			return false;
		}
	}
	
	
	public LostUsernameVO validateAns(LostUsernameVO lostUserNameVO)throws BusinessServiceException{ 
		List result;
		try{
			String ccArr[] = null;
			String provEmail = null;
			if (lostUserNameVO == null)
				return null;
			
			if(lostUserNameVO.getPwdInd()==1) {
				logger.info("First time user check"+lostUserNameVO.getEmailAddress());

				UserProfile userProfile = new UserProfile();
				userProfile.setUserName(lostUserNameVO.getUserName());
				userProfile = iUserProfileDao.queryWithName(userProfile);
				//To fetch provider email id (to be cced)
				if(userProfile.getRoleId()==1)
					provEmail = iUserProfileDao.getAdminAddress(lostUserNameVO.getUserName());
				if(provEmail!=null && provEmail!= "" && !lostUserNameVO.getEmailAddress().equals(provEmail))
				{
					ccArr = new String[1];
					ccArr[0] = new String(provEmail);
				}

				
				boolean flag = resetPasswordAndSendEMail(lostUserNameVO, 
						userProfile.getFirstName()+" "+userProfile.getLastName(), ccArr);
				
				if (flag) 
					lostUserNameVO.setSuccessAns(true);
				else
					lostUserNameVO.setSuccessAns(false);
				
				return lostUserNameVO;
			}
			
			UserProfile userProfile = new UserProfile();
			userProfile.setUserName(lostUserNameVO.getUserName());
			userProfile = iUserProfileDao.queryWithName(userProfile);
			
			//use stemming to validate answer
			boolean flag = StemmingSearch.compare(userProfile.getAnswerTxt(), lostUserNameVO.getQuestionTxtAnswer());
			
			if (flag == true){
				
				//To fetch provider email id (to be cced)
				provEmail = iUserProfileDao.getAdminAddress(lostUserNameVO.getEmailAddress());
				if((null != provEmail) && !(lostUserNameVO.getEmailAddress().equals(provEmail))) {
					ccArr = new String[1];
					ccArr[0] = new String(provEmail);
				}
				//Send the e-mail
				
				flag = resetPasswordAndSendEMail(lostUserNameVO, 
						userProfile.getFirstName()+" "+userProfile.getLastName(),
						ccArr);
				
				if (flag) 
					lostUserNameVO.setSuccessAns(true);
				else
					lostUserNameVO.setSuccessAns(true);
			} else {
				
				//GGanapathy
				//Get the secret question txt to re-validate the secret answer
				LostUsernameVO lostUserNameVO1 = iUserProfileDao.getSecQuestionForUserName(lostUserNameVO);
				lostUserNameVO.setQuestionTxt(lostUserNameVO1.getQuestionTxt());
				lostUserNameVO.setSuccessAns(false);
			}
		}catch (DBException dae) {
			logger
			.info("SQL Exception @ForgotUserNameBOImpl.validateAns() while retriving the sec question for user name"
					+ dae.getMessage());
			throw new BusinessServiceException("SQL Exception @ForgotUserNameBOImpl.validateAns() while retriving the sec question for user name");
		}// end catch
		return lostUserNameVO;
	}
	

	
	public boolean doValidatePhoneAndZip(LostUsernameVO lostUsernameVO, String userPhoneNumber,
			String userZipCode, String userCompanyName) throws Exception{
		
		if (lostUsernameVO == null) {
			return false;
		}
		
		String userName = lostUsernameVO.getUserName();
	
		boolean flag = matchPhone(lostUsernameVO.getPhoneNo(), userPhoneNumber);
		if (!flag) {
			flag = matchPhone(lostUsernameVO.getPhoneNoBiz(), userPhoneNumber);
		}
		
		if (!flag)
			return false;
	
		if (!userZipCode.equals(lostUsernameVO.getZip())) {
			return false;
		}
		
		//Check for company name here
		int roleId = lostUsernameVO.getUserId();		
		if (roleId == OrderConstants.PROVIDER_ROLEID || roleId == OrderConstants.BUYER_ROLEID) {
			return StemmingSearch.compare(lostUsernameVO.getBusinessName(), userCompanyName);
		}
		
		//make it 3 to indicate that user has user has forgot secret Question
		updateVerificationCount(lostUsernameVO.getUserName(), getMaxSecretQuestionAttempts()); 
		return true;		
	}
	
	private boolean matchPhone(String phone, String userProvidedPhoneNumber) {
		if (phone == null || userProvidedPhoneNumber == null)
			return false;
		
		userProvidedPhoneNumber = userProvidedPhoneNumber.trim();
		int len  = phone.length();
		if (len < 4)
			return false;
		
		String partPhone = phone.substring(len - 4);
		
		if (partPhone.equals(userProvidedPhoneNumber)) {
			return true;
		}
		
		return false;
	}

	
	private boolean sendPasswordByMail(String userName, String password, String email,String ccArr[])
	{
		try
		{
			iProviderEmailBO.sendConfirmationPasswordMail(userName, password, email,ccArr);
		}catch(Exception a_Ex)
		{
			a_Ex.printStackTrace();
			return false;
		}
		return true;
	}
	
	private void updatePassword(UserProfile userProfile)throws DBException{
		try{
			iUserProfileDao.resetPassword(userProfile);
		}catch (DBException dae) {
			logger
			.info("SQL Exception @ForgotUserNameBOImpl.updatePassword() while updating the password"
					+ dae.getMessage());
			throw dae;
		}// end catch
	}
	
	/**
	 * This Function will reset password and send email with deep link to the user.
	 */
	public boolean resetPassword(LostUsernameVO lostUserNameVO) throws BusinessServiceException {				
		 return resetPasswordAndSendEMail(lostUserNameVO, null, null);
	}
	
	private boolean resetPasswordAndSendEMail(LostUsernameVO lostUsernameVO, String name, String ccArr[]) throws BusinessServiceException {	
		String password = null;
		String userName = lostUsernameVO.getUserName();
		String emailAddress = lostUsernameVO.getEmailAddress();
		if (userName == null)  {
			throw new BusinessServiceException("Exception (UserName is null) at @ForgotUserNameBOImpl.resetPasswordAndSendEMail() while updating the password");
		}
		
		UserProfile userProfile = new UserProfile();
		userProfile.setUserName(userName);
		
		try{
			if (emailAddress == null) {
				userProfile = iUserProfileDao.queryWithName(userProfile);
				emailAddress = userProfile.getEmail();
				lostUsernameVO.setEmailAddress(emailAddress);				
				name = userProfile.getFirstName()+" "+ userProfile.getLastName();
				lostUsernameVO.setFirstName(userProfile.getFirstName());
				lostUsernameVO.setLastName(userProfile.getLastName());
			}
			
			InterimPasswordVO interimPassword = iInterimPasswordDao.resetPassword(userName);
			password = interimPassword.getPassword();
			String deepLink = EmailBOUtil.createDeepLinkForPassword(null, password);
			lostUsernameVO.setDeepLinkUrl(deepLink);
			lostUsernameVO.setPassword(password);
					
			if (interimPassword.getPlainTextPassword() != null) {
				userProfile.setPassword(interimPassword.getPlainTextPassword());				
				iUserProfileDao.resetPassword(userProfile);
			}
		} catch (DBException dae) {
			logger
			.info("SQL Exception @ForgotUserNameBOImpl.createInterimPassword() while updating the password"
					+ dae.getMessage());
			throw new BusinessServiceException("SQL Exception @ForgotUserNameBOImpl.resetPasswordAndSendEMail() while updating the password");			
		}// end catch		
		
		// Code changed to send the mail through Cheetah and not through velocity context.
		//return sendPasswordByMail(name, password, emailAddress, ccArr);
		return true;
	}
	
	public LostUsernameVO getSecQuestionForUserName(LostUsernameVO lostUserNameVO)throws BusinessServiceException{ 
		LostUsernameVO result;
		try{
			result = (LostUsernameVO)iUserProfileDao.getSecQuestionForUserName(lostUserNameVO);
			
			if (result != null)
			{
				lostUserNameVO.setQuestionTxt(result.getQuestionTxt());
				lostUserNameVO.setQuestionId(result.getQuestionId());
			}
			
		}catch (DBException dae) {
			logger
			.info("SQL Exception @ForgotUserNameBOImpl.getSecQuestionForUserName() while retriving the sec question for user name"
					+ dae.getMessage());
			throw new BusinessServiceException("SQL Exception @ForgotUserNameBOImpl.getSecQuestionForUserName() while retriving the sec question for user name");
		}// end catch
		return lostUserNameVO;
	}
	
	/* 
	 * 
	 */
	public String getUserFromInterimPassword(String  tempPassword) throws BusinessServiceException {
		InterimPasswordVO interimPassword = InterimPasswordVO.createNewInstance(null, tempPassword);
		Date expTime = new Date();
		Date currTime = new Date();
		try {
			InterimPasswordVO result = iInterimPasswordDao.getUserFromInterimPassword(interimPassword);
			if(result != null){
				//expTime.setTime(result.getStartTime().getTime() + 604800000);
				expTime.setTime(result.getStartTime().getTime() + getInterimPwdExp());
				//Check the date here.
				if(currTime.after(expTime) || result.getValid() == 0){
					return "Expired";
				}	
				return result.getUserName();
			}
		} catch (DBException dae) {
			logger
			.info("SQL Exception @ForgotUserNameBOImpl.getUserFromInterimPassword() while retriving the sec question for user name"
					+ dae.getMessage());
			throw new BusinessServiceException("SQL Exception @ForgotUserNameBOImpl.getUserFromInterimPassword() while retriving the user name");
		}// end catch
		return null;
	}	
	
	
	public int getVerificationCount(String userName) throws BusinessServiceException {
		try {
		  return iUserProfileDao.getVerificationCount(userName);
		} catch (DBException dae) {
			logger
			.info("SQL Exception @ForgotUserNameBOImpl.getVerificationCount() while retriving the sec question count user name"
					+ dae.getMessage());
			throw new BusinessServiceException("SQL Exception @ForgotUserNameBOImpl.getVerificationCount() while retriving the count for user name");
		}// end catch
	}
	
	public void updateVerificationCount(String userName, int count) throws BusinessServiceException {
		try {
		  iUserProfileDao.updateVerificationCount(userName, count);
		} catch (DBException dae) {
			logger
			.info("SQL Exception @ForgotUserNameBOImpl.updateVerificationCount() while updating the sec question count user name"
					+ dae.getMessage());
			throw new BusinessServiceException("SQL Exception @ForgotUserNameBOImpl.updateVerificationCount() while updating the count for user name");
		}// end catch 
	}
	
	public void lockProfile(String userName) throws BusinessServiceException {
		try {
		  iUserProfileDao.lockProfile(userName);
		} catch (DBException dae) {
			logger
			.info("SQL Exception @ForgotUserNameBOImpl.lockProfile() while updating the profile for user name"
					+ dae.getMessage());
			throw new BusinessServiceException("SQL Exception @ForgotUserNameBOImpl.lockProfile() while updating profile for user name");
		}// end catch 
	}
	
	public void invalidatePassword(String password) throws BusinessServiceException {
		try {
			iInterimPasswordDao.invalidatePassword(password);
		} catch (DBException dae) {
			logger
			.info("SQL Exception @ForgotUserNameBOImpl.lockProfile() while invalidating user password"
					+ dae.getMessage());
			throw new BusinessServiceException("SQL Exception @ForgotUserNameBOImpl.lockProfile() while invalidating user password");
		}// end catch 
	}
	
	public String getUserNameFromResourceId(String resourceId, int roleId) throws BusinessServiceException {
		try {
		return iUserProfileDao.getUserNameFromResourceId(resourceId, roleId);
		} catch (DBException dae) {
			logger
			.info("SQL Exception @ForgotUserNameBOImpl.getUserNameFromResourceId() while getting user name"
					+ dae.getMessage());
			throw new BusinessServiceException("SQL Exception @ForgotUserNameBOImpl.getUserNameFromResourceId() while getting user name");
		}// end catch  
	}
	
	
	public String getResourceIdFromUserName(String username) throws BusinessServiceException {
		try {
		return iUserProfileDao.getResourceIdFromUserName(username);
		} catch (DBException dae) {
			logger
			.info("SQL Exception @ForgotUserNameBOImpl.getResourceIdFromUserName() while getting user name"
					+ dae.getMessage());
			throw new BusinessServiceException("SQL Exception @ForgotUserNameBOImpl.getResourceIdFromUserName() while getting user name");
		}// end catch  
	}
	
	
	/**
	 * @return the iUserProfileDao
	 */
	public IUserProfileDao getiUserProfileDao() {
		return iUserProfileDao;
	}

	/**
	 * @param userProfileDao the iUserProfileDao to set
	 */
	public void setiUserProfileDao(IUserProfileDao userProfileDao) {
		iUserProfileDao = userProfileDao;
	}
	
	public InterimPasswordVO getTempPasswordFromInterim(String userName) throws BusinessServiceException{	
		String password = null;
		InterimPasswordVO interimPasswordVO = new InterimPasswordVO();
	
		try{
			//InterimPasswordVO interimPassword = InterimPasswordVO.createNewInstance(userName, null);
			InterimPasswordVO interimPassword = iInterimPasswordDao.resetPassword(userName);
			password = interimPassword.getPassword();
			interimPasswordVO.setPassword(password);
					
		}catch (DBException dae) {
			logger
			.info("SQL Exception @ForgotUserNameBOImpl.createInterimPassword() while updating the password"
					+ dae.getMessage());
			//throw dae;
		}// end catch		
		
		// Code changed to send the mail through Cheetah and not through velocity context.
		//return sendPasswordByMail(name, password, emailAddress, ccArr);
		return interimPasswordVO;
	}
}


