/**
 * 
 */
package com.newco.marketplace.web.utils;

import com.newco.marketplace.vo.provider.LoginVO;
import com.newco.marketplace.web.dto.provider.LoginDto;


/**
 * @author KSudhanshu
 *
 */
public class LoginMapper extends ObjectMapper {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.marketplace.web.utils.ObjectMapper#convertDTOtoVO(java.lang.Object,
	 *      java.lang.Object)
	 */
	public LoginVO convertDTOtoVO(Object objectDto, Object objectVO) {
		LoginDto loginDto = (LoginDto) objectDto;
		LoginVO loginVO = (LoginVO) objectVO;
		if (objectDto != null && objectVO!=null) {
			loginVO.setId(loginDto.getId());
			loginVO.setUsername(loginDto.getUsername());
			loginVO.setPassword(loginDto.getPassword());
			loginVO.setIsTempPassword(loginDto.getIsTempPassword());
		}
		return loginVO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.marketplace.web.utils.ObjectMapper#convertVOtoDTO(java.lang.Object,
	 *      java.lang.Object)
	 */
	public LoginDto convertVOtoDTO(Object objectVO, Object objectDto) {
		LoginVO loginVO = (LoginVO) objectVO;
		LoginDto loginDto = (LoginDto) objectDto;
		if (objectVO != null && objectDto!= null) {
			loginDto.setId(loginVO.getId());
			loginDto.setUsername(loginVO.getUsername());
			loginDto.setPassword(loginVO.getPassword());
			loginDto.setIsTempPassword(loginVO.getIsTempPassword());
		}
		return loginDto;
	}

}
