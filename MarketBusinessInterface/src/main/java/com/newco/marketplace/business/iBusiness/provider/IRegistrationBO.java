/**
 * 
 */
package com.newco.marketplace.business.iBusiness.provider;

import java.util.List;

import com.newco.marketplace.exception.DuplicateUserException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.vo.provider.ProviderRegistrationVO;

/**
 * @author LENOVO USER
 * 
 */
public interface IRegistrationBO extends IAuditStates {

	public ProviderRegistrationVO loadRegistration(
			ProviderRegistrationVO providerRegistrationVO)
			throws BusinessServiceException;

	public ProviderRegistrationVO saveRegistration(
			ProviderRegistrationVO providerRegistrationVO)
			throws BusinessServiceException,DuplicateUserException;
	
	//TODO - un comment once CRM details are finalized
	//public CreateProviderAccountResponse doCrmRegistration(String request);
	
	public ProviderRegistrationVO loadZipSet(
			ProviderRegistrationVO providerRegistrationVO) 
			throws BusinessServiceException;
	public boolean validateUserName(String userName);

	public List getStates();
}
