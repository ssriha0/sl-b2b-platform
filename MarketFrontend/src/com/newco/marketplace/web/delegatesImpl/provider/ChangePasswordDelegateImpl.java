package com.newco.marketplace.web.delegatesImpl.provider;

import java.util.Map;

import com.newco.marketplace.business.iBusiness.provider.ILoginBO;
import com.newco.marketplace.vo.provider.ChangePasswordVO;
import com.newco.marketplace.web.delegates.provider.IChangePasswordDelegate;
import com.newco.marketplace.web.dto.provider.ChangePasswordDto;
import com.newco.marketplace.web.utils.ChangePasswordMapper;

/**
 * @author KSudhanshu
 *
 */
public class ChangePasswordDelegateImpl implements IChangePasswordDelegate {

	private ILoginBO iLoginBO;
	private ChangePasswordMapper changePasswordMapper;
//	private ChangePasswordVO changePasswordVO;

	/**
	 * @param loginBO
	 * @param changePasswordMapper
	 * @param changePasswordVO
	 */
	public ChangePasswordDelegateImpl(ILoginBO loginBO,
			ChangePasswordMapper changePasswordMapper) {
		this.iLoginBO = loginBO;
		this.changePasswordMapper = changePasswordMapper;
//		this.changePasswordVO = changePasswordVO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.marketplace.web.delegates.IChangePasswordDelegate#converDtoToVo(com.newco.marketplace.web.dto.ChangePasswordDto,
	 *      com.newco.marketplace.vo.ChangePasswordVO)
	 */
	public ChangePasswordVO convertDtoToVo(ChangePasswordDto changePasswordDto,
			ChangePasswordVO changePasswordVO) {
		return (ChangePasswordVO) changePasswordMapper.convertDTOtoVO(
				changePasswordDto, changePasswordVO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.marketplace.web.delegates.IChangePasswordDelegate#converVoToDto(com.newco.marketplace.vo.ChangePasswordVO,
	 *      com.newco.marketplace.web.dto.ChangePasswordDto)
	 */
	public ChangePasswordDto convertVoToDto(ChangePasswordVO changePasswordVO,
			ChangePasswordDto changePasswordDto) {
		return (ChangePasswordDto) changePasswordMapper.convertVOtoDTO(
				changePasswordVO, changePasswordDto);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.marketplace.web.delegates.IChangePasswordDelegate#updatePassword(com.newco.marketplace.web.dto.ChangePasswordDto)
	 */
	public boolean updatePassword(ChangePasswordDto objChangePasswordDto)
			throws Exception {
		ChangePasswordVO changePasswordVO = new ChangePasswordVO();
		changePasswordVO = convertDtoToVo(objChangePasswordDto, changePasswordVO);
		return iLoginBO.updatePassword(changePasswordVO);
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.ILoginDelegate#getSecretQuestionList()
	 */
	public Map getSecretQuestionList() {
		return iLoginBO.getSecretQuestionList();
	}
	
	/*public ForgotUsernameDto validateAns(ForgotUsernameDto forgotUsernameDto)throws DelegateException{
		
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
	}*/
}
