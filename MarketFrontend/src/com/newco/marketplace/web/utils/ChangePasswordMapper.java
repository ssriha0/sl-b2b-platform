/**
 * 
 */
package com.newco.marketplace.web.utils;

import com.newco.marketplace.vo.provider.ChangePasswordVO;
import com.newco.marketplace.web.dto.provider.ChangePasswordDto;


/**
 * @author KSudhanshu
 *
 */
public class ChangePasswordMapper extends ObjectMapper {

	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.utils.ObjectMapper#convertDTOtoVO(java.lang.Object, java.lang.Object)
	 */
	public Object convertDTOtoVO(Object objectDto, Object objectVO) {
		ChangePasswordDto changePasswordDto = (ChangePasswordDto) objectDto;
		ChangePasswordVO changePasswordVO = (ChangePasswordVO) objectVO;
		if (changePasswordDto != null && changePasswordVO!=null) {
			changePasswordVO.setUserName(changePasswordDto.getUserName());
			
			changePasswordVO.setPassword(changePasswordDto.getPassword());
			changePasswordVO.setSecretQuestion(changePasswordDto.getSecretQuestion());
			changePasswordVO.setSecretAnswer(changePasswordDto.getSecretAnswer());
			changePasswordVO.setQuestionMap(changePasswordDto.getQuestionMap());
		}
		return changePasswordVO;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.utils.ObjectMapper#convertVOtoDTO(java.lang.Object, java.lang.Object)
	 */
	public Object convertVOtoDTO(Object objectVO, Object objectDto) {
		ChangePasswordVO changePasswordVO = (ChangePasswordVO) objectVO;
		ChangePasswordDto changePasswordDto = (ChangePasswordDto) objectDto;
		if (changePasswordVO != null && changePasswordDto!= null) {
			changePasswordDto.setUserName(changePasswordVO.getUserName());
			changePasswordDto.setPassword(changePasswordVO.getPassword());
			changePasswordDto.setSecretQuestion(changePasswordVO.getSecretQuestion());
			changePasswordDto.setSecretAnswer(changePasswordVO.getSecretAnswer());
			changePasswordDto.setQuestionMap(changePasswordVO.getQuestionMap());
		}
		return changePasswordDto;
	}

}
