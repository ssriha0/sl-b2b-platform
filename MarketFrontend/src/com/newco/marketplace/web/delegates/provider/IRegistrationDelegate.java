package com.newco.marketplace.web.delegates.provider;

import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.exception.DuplicateUserException;
import com.newco.marketplace.vo.provider.ProviderRegistrationVO;
import com.newco.marketplace.web.dto.provider.ProviderRegistrationDto;

/**
 * @author LENOVO USER
 *
 */
public interface IRegistrationDelegate {
	public ProviderRegistrationDto loadZipSet(ProviderRegistrationDto providerRegistrationDto) throws DelegateException; 
	public ProviderRegistrationDto loadRegistration(ProviderRegistrationDto providerRegistrationDto) throws DelegateException;
	public ProviderRegistrationDto saveRegistration(ProviderRegistrationDto providerRegistrationDto)throws DelegateException,DuplicateUserException;
	
	public ProviderRegistrationDto converVoToDto(ProviderRegistrationVO providerRegistrationVO, ProviderRegistrationDto providerRegistrationDto);
	
	
	public ProviderRegistrationVO converDtoToVo(ProviderRegistrationDto providerRegistrationDto,ProviderRegistrationVO providerRegistrationVO);
	public boolean validateUserName(String userName);

}
