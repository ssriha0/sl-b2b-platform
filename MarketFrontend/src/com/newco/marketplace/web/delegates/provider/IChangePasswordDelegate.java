package com.newco.marketplace.web.delegates.provider;

import java.util.Map;

import com.newco.marketplace.vo.provider.ChangePasswordVO;
import com.newco.marketplace.web.dto.provider.ChangePasswordDto;


/**
 * @author KSudhanshu
 *
 */
public interface IChangePasswordDelegate {
	
	
	/**
	 * @param objChangePasswordDto
	 * @return
	 * @throws Exception
	 */
	public boolean updatePassword(ChangePasswordDto objChangePasswordDto) throws Exception;
	
	
	/**
	 * @param changePasswordVO
	 * @param changePasswordDto
	 * @return
	 */
	public ChangePasswordDto convertVoToDto(ChangePasswordVO changePasswordVO, ChangePasswordDto changePasswordDto);
	
	
	/**
	 * @param changePasswordDto
	 * @param changePasswordVO
	 * @return
	 */
	public ChangePasswordVO convertDtoToVo(ChangePasswordDto changePasswordDto,ChangePasswordVO changePasswordVO);
	
	/**
	 * @return
	 * @throws Exception
	 */
	public Map getSecretQuestionList();
		
}
