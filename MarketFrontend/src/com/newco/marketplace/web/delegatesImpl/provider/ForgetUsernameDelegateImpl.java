package com.newco.marketplace.web.delegatesImpl.provider;


import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.provider.IForgotUsernameBO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.vo.provider.LostUsernameVO;
import com.newco.marketplace.web.delegates.provider.IForgetUsernameDelegate;
import com.newco.marketplace.web.dto.provider.ForgotUsernameDto;
import com.newco.marketplace.web.utils.ForgetUsernameMapper;

public class ForgetUsernameDelegateImpl implements IForgetUsernameDelegate {

	private IForgotUsernameBO iForgotUsernameBO;
	private ForgetUsernameMapper forgetUsernameMapper;
	private static final Logger localLogger = Logger
			.getLogger(ForgetUsernameDelegateImpl.class.getName());
	
	
	
	public ForgetUsernameDelegateImpl(IForgotUsernameBO iForgotUsernameBOImpl,ForgetUsernameMapper forgetUsernameMapper){
		this.iForgotUsernameBO=iForgotUsernameBOImpl;
		this.forgetUsernameMapper=forgetUsernameMapper;
	}
	
	/*
	 * Used to get username given email address.
	 * (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.provider.IForgetUsernameDelegate#loadLostUsername(com.newco.marketplace.web.dto.provider.ForgotUsernameDto)
	 */
	public ForgotUsernameDto loadLostUsernameList(ForgotUsernameDto forgotUsernameDto) throws DelegateException{
		LostUsernameVO lostUsernameVO=null;
		try {
			
			//lostUsernameVO=(LostUsernameVO)forgetUsernameMapper.convertDTOtoVO(forgotUsernameDto, lostUsernameVO);
			String email = forgotUsernameDto.getEmail();
			forgotUsernameDto.setListUsers(iForgotUsernameBO.loadLostUsernameList(email, forgotUsernameDto.getRoleId()));			
			//forgotUsernameDto = forgetUsernameMapper.convertVOtoDTO(lostUsernameVO, forgotUsernameDto);//MTedder
			//get QuestionTxt & QuestionTxtAnswer from VO list and set in DTO
			if (forgotUsernameDto.getListUsers() != null && forgotUsernameDto.getListUsers().size() > 0){
				LostUsernameVO lostObj = forgotUsernameDto.getListUsers().get(0);
				forgotUsernameDto = forgetUsernameMapper.convertVOtoDTO(lostObj, forgotUsernameDto);//Shekhar				
			}
			if (email != null)
					forgotUsernameDto.setEmail(email);
			
		} catch(BusinessServiceException bse){
			throw new DelegateException("Exception occured while processing ForgetUsernameDelegateImpl.loadLostUsername()"+bse.getMessage());
		}
		return forgotUsernameDto;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.provider.IForgetUsernameDelegate#sendForgotUsernameMail(com.newco.marketplace.web.dto.provider.ForgotUsernameDto)
	 */
	public boolean sendForgotUsernameMail(ForgotUsernameDto forgotUsernameDto) {
		// TODO Auto-generated method stub
		LostUsernameVO lostUserNameVO  = new LostUsernameVO();
		lostUserNameVO=(LostUsernameVO)forgetUsernameMapper.convertDTOtoVO(forgotUsernameDto, lostUserNameVO);		
		boolean statusEmail = iForgotUsernameBO.sendForgotUsernameMail(lostUserNameVO);
		return statusEmail;
	}
	
	public ForgotUsernameDto loadLiteLostUsereProfile(ForgotUsernameDto forgotUsernameDto) throws DelegateException{
		LostUsernameVO lostUsernameVO=null;
		LostUsernameVO lostUsernameVO2=null;
		ForgotUsernameDto usernameDto = null;
		try{
			//lostUsernameVO=new LostUsernameVO();
			//lostUsernameVO=(LostUsernameVO)forgetUsernameMapper.convertDTOtoVO(forgotUsernameDto, lostUsernameVO);
			lostUsernameVO2 = iForgotUsernameBO.loadLitLostUsereProfile(forgotUsernameDto.getEmail(),
					forgotUsernameDto.getUserName());
			//forgotUsernameDto.setListUsers(iForgotUsernameBO.validateEmailUsername(lostUsernameVO));
			
			//If the Email and User Name does not match then the VO returned is NULL
			//Added by bnatarajan (Covansys - Offshore
			if (lostUsernameVO2 != null)
				forgotUsernameDto= forgetUsernameMapper.convertVOtoDTO(lostUsernameVO2, forgotUsernameDto);
		
		} catch(BusinessServiceException bse) {
			throw new DelegateException("Exception occured while processing ForgetUsernameDelegateImpl.validateEmailUsername()"+bse.getMessage());
		}
		return forgotUsernameDto;
	}
	
	public ForgotUsernameDto getSecQuestionForUserName(ForgotUsernameDto forgotUsernameDto)throws DelegateException{
		
		LostUsernameVO lostUsernameVO=null;
		try{
			lostUsernameVO=new LostUsernameVO();
			lostUsernameVO=(LostUsernameVO)forgetUsernameMapper.convertDTOtoVO(forgotUsernameDto, lostUsernameVO);
			lostUsernameVO=(LostUsernameVO)iForgotUsernameBO.getSecQuestionForUserName(lostUsernameVO);
			forgotUsernameDto=(ForgotUsernameDto)forgetUsernameMapper.convertVOtoDTO(lostUsernameVO,forgotUsernameDto);
		}catch(BusinessServiceException bse){
			throw new DelegateException("Exception occured while processing ForgetUsernameDelegateImpl.getSecQuestionForUserName()"+bse.getMessage());
		}
		return forgotUsernameDto;
		
	}
	
	public ForgotUsernameDto validateAns(ForgotUsernameDto forgotUsernameDto)throws DelegateException{
		
		LostUsernameVO lostUsernameVO=null;
		try{
			lostUsernameVO=new LostUsernameVO();
			if(forgotUsernameDto.getPwdInd()==1)
			{
				lostUsernameVO.setUserName(forgotUsernameDto.getUserName());
				lostUsernameVO.setPwdInd(forgotUsernameDto.getPwdInd());
				lostUsernameVO.setEmailAddress(forgotUsernameDto.getEmail());
				lostUsernameVO=(LostUsernameVO)iForgotUsernameBO.validateAns(lostUsernameVO);
				forgotUsernameDto.setSuccess(lostUsernameVO.getSuccessAns());
			}
			else
			{
				lostUsernameVO=(LostUsernameVO)forgetUsernameMapper.convertDTOtoVO(forgotUsernameDto, lostUsernameVO);
				lostUsernameVO=(LostUsernameVO)iForgotUsernameBO.validateAns(lostUsernameVO);
				forgotUsernameDto=(ForgotUsernameDto)forgetUsernameMapper.convertVOtoDTO(lostUsernameVO,forgotUsernameDto);
			}
			
			
		}catch(BusinessServiceException bse){
			throw new DelegateException("Exception occured while processing ForgetUsernameDelegateImpl.validateAns()"+bse.getMessage());
		}
		return forgotUsernameDto;
	}
	
	public String getUserFromInterimPassword(String  temppasword) throws DelegateException {
		try {
			return iForgotUsernameBO.getUserFromInterimPassword(temppasword);
		} catch(BusinessServiceException bse){
			throw new DelegateException("Exception occured while processing ForgetUsernameDelegateImpl.getUserFromInterimPassword()"+bse.getMessage());
		}
	}
	
	
	public int getVerificationCount(String userName) throws DelegateException {
		try {
			return iForgotUsernameBO.getVerificationCount(userName);
		} catch(BusinessServiceException bse){
			throw new DelegateException("Exception occured while processing ForgetUsernameDelegateImpl.getVerificationCount"+bse.getMessage());
		}
	}
	
	public void updateVerificationCount(String userName, int count) throws DelegateException {
		try {
			iForgotUsernameBO.updateVerificationCount(userName, count);
		} catch(BusinessServiceException bse){
			throw new DelegateException("Exception occured while processing ForgetUsernameDelegateImpl.updateVerificationCount"+bse.getMessage());
		}
	}
	
	public void lockProfile(String userName) throws DelegateException {
		try {
			iForgotUsernameBO.lockProfile(userName);
		} catch(BusinessServiceException bse){
			throw new DelegateException("Exception occured while processing ForgetUsernameDelegateImpl.lockProfile()"+bse.getMessage());
		}
	}
	
	public void invalidatePassword(String password) throws DelegateException {
		try {
			iForgotUsernameBO.invalidatePassword(password);
		} catch(BusinessServiceException bse){
			throw new DelegateException("Exception occured while processing ForgetUsernameDelegateImpl.lockProfile()"+bse.getMessage());
		}
	}
	
	public boolean doValidatePhoneAndZip(ForgotUsernameDto forgotUsernameDto, String userPhoneNumber,
			String userZipCode, String userCompanyName) throws Exception {
		LostUsernameVO lostUsernameVO2 = new LostUsernameVO();
		lostUsernameVO2= (LostUsernameVO)forgetUsernameMapper.convertDTOtoVO(forgotUsernameDto, lostUsernameVO2);
		return iForgotUsernameBO.doValidatePhoneAndZip(lostUsernameVO2, userPhoneNumber,
				userZipCode, userCompanyName);
	}

	
	public boolean resetPassword(String userName, String name, String emailAddress, String ccArr[]) {
		LostUsernameVO lostUsernameVO= new LostUsernameVO();
		lostUsernameVO.setUserName(userName);
		try {
			return iForgotUsernameBO.resetPassword(lostUsernameVO);
		} catch (Exception e) {
			return false;
		}
	}
	
	public ForgotUsernameDto loadLostUsername(ForgotUsernameDto forgotUsernameDto) throws DelegateException {
		try {
			LostUsernameVO lostUsernameVO2 = iForgotUsernameBO.loadLostUsername(forgotUsernameDto.getUserName(), 
					forgotUsernameDto.getResourceId(), forgotUsernameDto.getRoleId());
			forgotUsernameDto= forgetUsernameMapper.convertVOtoDTO(lostUsernameVO2, forgotUsernameDto);
			return forgotUsernameDto;
		} catch(BusinessServiceException bse){
			throw new DelegateException("Exception occured while processing ForgetUsernameDelegateImpl.loadLostUsername()"+bse.getMessage());
		}
	}
	

	public String getResourceIdFromUsername(String  username) throws DelegateException {
		try {
			return iForgotUsernameBO.getResourceIdFromUserName(username);
		} catch(BusinessServiceException bse){
			throw new DelegateException("Exception occured while processing ForgetUsernameDelegateImpl.getResourceIdFromUsername()"+bse.getMessage());
		}
	}
	
	/**
	 * @return the iForgotUsernameBO
	 */
	public IForgotUsernameBO getiForgotUsernameBO() {
		return iForgotUsernameBO;
	}

	/**
	 * @param forgotUsernameBO the iForgotUsernameBO to set
	 */
	public void setiForgotUsernameBO(IForgotUsernameBO forgotUsernameBO) {
		iForgotUsernameBO = forgotUsernameBO;
	}
}
