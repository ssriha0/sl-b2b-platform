package com.newco.marketplace.web.utils;


import com.newco.marketplace.vo.provider.ForgotPasswordVO;
import com.newco.marketplace.web.dto.provider.ForgotPasswordDto;


public class ForgotPasswordMapper extends ObjectMapper {


	public Object convertDTOtoVO(Object objectDto, Object objectVO) {
		ForgotPasswordDto forgotPasswordDto = (ForgotPasswordDto) objectDto;
		ForgotPasswordVO forgotPasswordVO = (ForgotPasswordVO) objectVO;
		
		if (forgotPasswordDto != null && forgotPasswordVO!=null) {
			forgotPasswordVO.setEmail((forgotPasswordDto.getEmail()));
			forgotPasswordVO.setUserName((forgotPasswordDto.getUserName()));
		}
		return forgotPasswordVO;
	}

	public Object convertVOtoDTO(Object objectVO, Object objectDto) {
		ForgotPasswordVO forgotPasswordVO = (ForgotPasswordVO) objectVO;
		ForgotPasswordDto forgotPasswordDto = (ForgotPasswordDto) objectDto;
		
		if (forgotPasswordDto != null && forgotPasswordVO!=null) {
			forgotPasswordDto.setEmail((forgotPasswordVO.getEmail()));
			forgotPasswordDto.setUserName((forgotPasswordVO.getUserName()));
		}
		return forgotPasswordDto;	
	}
}
