package com.newco.marketplace.web.delegatesImpl.provider;

import com.newco.marketplace.business.iBusiness.provider.IForgotPasswordBO;
import com.newco.marketplace.vo.provider.ForgotPasswordVO;
import com.newco.marketplace.web.delegates.provider.IForgotPasswordDelegate;
import com.newco.marketplace.web.dto.provider.ForgotPasswordDto;
import com.newco.marketplace.web.utils.ForgotPasswordMapper;

/**
 * $Revision: 1.8 $ $Author: glacy $ $Date: 2008/04/26 01:13:51 $
 */

/*
 * Maintenance History
 * $Log: ForgotPasswordDelegateImpl.java,v $
 * Revision 1.8  2008/04/26 01:13:51  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.6.14.1  2008/04/23 11:41:42  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.7  2008/04/23 05:19:44  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.6  2008/02/05 22:24:23  mhaye05
 * removed commented out code
 *
 */
public class ForgotPasswordDelegateImpl implements IForgotPasswordDelegate {

	private ForgotPasswordMapper forgotPasswordMapper;
	private IForgotPasswordBO iForgotPasswordBO;


	/**
	 * @param iForgotPasswordBO
	 * @param forgotPasswordMapper
	 */
	public ForgotPasswordDelegateImpl(
			IForgotPasswordBO iForgotPasswordBO,
			ForgotPasswordMapper forgotPasswordMapper) {
		this.iForgotPasswordBO = iForgotPasswordBO;
		this.forgotPasswordMapper = forgotPasswordMapper;
	}

	public ForgotPasswordVO convertDtoToVo(ForgotPasswordDto forgotPasswordDto,
			ForgotPasswordVO forgotPasswordVO) {
		
		return (ForgotPasswordVO) forgotPasswordMapper.convertDTOtoVO(
				forgotPasswordDto, forgotPasswordVO);
	}

	public ForgotPasswordDto convertVoToDto(ForgotPasswordVO forgotPasswordVO,
			ForgotPasswordDto forgotPasswordDto) {
		
		return (ForgotPasswordDto) forgotPasswordMapper.convertVOtoDTO(
				forgotPasswordVO, forgotPasswordDto);
		
	}
	
	public Integer getSecretQuestionId(ForgotPasswordDto forgotPasswordDto) throws Exception 
	{
		ForgotPasswordVO forgotPasswordVO = new ForgotPasswordVO();
		forgotPasswordVO = convertDtoToVo(forgotPasswordDto, forgotPasswordVO);
		return iForgotPasswordBO.getSecretQuestionId(forgotPasswordVO);
	}
	/**
	 * @return the iForgotPasswordBO
	 */
	public IForgotPasswordBO getiForgotPasswordBO() {
		return iForgotPasswordBO;
	}

	/**
	 * @param forgotPasswordBO the iForgotPasswordBO to set
	 */
	public void setiForgotPasswordBO(IForgotPasswordBO forgotPasswordBO) {
		iForgotPasswordBO = forgotPasswordBO;
	}
	
}
