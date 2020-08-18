package com.newco.marketplace.web.delegates.provider;

import com.newco.marketplace.web.dto.provider.ForgotPasswordDto;
import com.newco.marketplace.vo.provider.ForgotPasswordVO;


public interface IForgotPasswordDelegate {
	
	public ForgotPasswordVO convertDtoToVo(ForgotPasswordDto forgotPasswordDto,ForgotPasswordVO forgotPasswordVO);
	
	public ForgotPasswordDto convertVoToDto(ForgotPasswordVO forgotPasswordVO, ForgotPasswordDto forgotPasswordDto);

	public Integer getSecretQuestionId(ForgotPasswordDto forgotPasswordDto) throws Exception;
	
}
