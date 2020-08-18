package com.newco.marketplace.business.iBusiness.provider;

import java.util.List;

import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.vo.common.InterimPasswordVO;
import com.newco.marketplace.vo.provider.LostUsernameVO;

public interface IForgotUsernameBO {

	public List<LostUsernameVO> loadLostUsernameList(String email, int roleId) throws BusinessServiceException;	
	public LostUsernameVO loadLitLostUsereProfile(String email, String userName) throws BusinessServiceException;
	public LostUsernameVO getSecQuestionForUserName(LostUsernameVO lostUserNameVO)throws BusinessServiceException;
	public LostUsernameVO validateAns(LostUsernameVO lostUserNameVO)throws BusinessServiceException;
	public boolean sendForgotUsernameMail(LostUsernameVO lostUserNameVO);
	public boolean resetPassword(LostUsernameVO lostUsernameVO) throws BusinessServiceException;
	public String getUserFromInterimPassword(String  temppasword) throws BusinessServiceException;
	
	public void lockProfile(String userName) throws BusinessServiceException;
	public void invalidatePassword(String password) throws BusinessServiceException;
	public String getUserNameFromResourceId(String resourceId, int roleId) throws BusinessServiceException;
	public int getVerificationCount(String userName) throws BusinessServiceException;
	public void updateVerificationCount(String userName, int count) throws BusinessServiceException;
	public LostUsernameVO loadLostUsername(String userName, String resourceId, int roleId) throws BusinessServiceException;
	public boolean doValidatePhoneAndZip(LostUsernameVO lostUsernameVO, String userPhoneNumber, 
			String userZipCode, String userCompanyName) throws Exception;
	public InterimPasswordVO getTempPasswordFromInterim(String userName) throws BusinessServiceException;	
	public String getResourceIdFromUserName(String username) throws BusinessServiceException ;
}

