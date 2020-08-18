package com.newco.marketplace.web.delegatesImpl.provider;


import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.provider.IRegistrationBO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.exception.DuplicateUserException;
import com.newco.marketplace.persistence.daoImpl.provider.ContactDaoImpl;
import com.newco.marketplace.vo.provider.ProviderRegistrationVO;
import com.newco.marketplace.web.delegates.provider.IRegistrationDelegate;
import com.newco.marketplace.web.dto.provider.ProviderRegistrationDto;
import com.newco.marketplace.web.utils.ProviderRegistrationMapper;

/**
 * @author LENOVO USER
 * 
 */
public class RegistrationDelegateImpl implements IRegistrationDelegate {

	private IRegistrationBO iRegistrationBO;
	private static final Logger localLogger = Logger
			.getLogger(ContactDaoImpl.class.getName());
//	private ProviderRegistrationVO providerRegistrationVO;
	private ProviderRegistrationMapper providerRegistrationMapper;

	public RegistrationDelegateImpl(
			IRegistrationBO iRegistrationBO,
			ProviderRegistrationMapper providerRegistrationMapper) {
		this.iRegistrationBO = iRegistrationBO;
		this.providerRegistrationMapper = providerRegistrationMapper;
	
	}

	public ProviderRegistrationDto loadRegistration(
			ProviderRegistrationDto providerRegistrationDto)
			throws DelegateException {
		try {
			ProviderRegistrationVO providerRegistrationVO = new ProviderRegistrationVO();
			providerRegistrationVO = providerRegistrationMapper.convertDTOtoVO(providerRegistrationDto,providerRegistrationVO);
			providerRegistrationVO = iRegistrationBO.loadRegistration(providerRegistrationVO);
			providerRegistrationDto =  providerRegistrationMapper.convertVOtoDTO(providerRegistrationVO, providerRegistrationDto);
			
		} catch (BusinessServiceException ex) {
			localLogger
					.info("Business Service Exception @RegistrationDelegateImpl.loadRegistration() due to"
							+ ex.getMessage());
			throw new DelegateException(
					"Business Service @RegistrationDelegateImpl.loadRegistration() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			localLogger
					.info("General Exception @RegistrationDelegateImpl.loadRegistration() due to"
							+ ex.getMessage());
			throw new DelegateException(
					"General Exception @RegistrationDelegateImpl.loadRegistration() due to "
							+ ex.getMessage());
		}
		return providerRegistrationDto;
	}

	
	public ProviderRegistrationDto loadZipSet(
			ProviderRegistrationDto providerRegistrationDto)
			throws DelegateException {
		
		try {
			ProviderRegistrationVO providerRegistrationVO = new ProviderRegistrationVO();
			providerRegistrationVO = providerRegistrationMapper.convertDTOtoVO(providerRegistrationDto,providerRegistrationVO);
			providerRegistrationVO = iRegistrationBO
										.loadZipSet(providerRegistrationVO);
			providerRegistrationDto = providerRegistrationMapper.convertVOtoDTO(providerRegistrationVO,providerRegistrationDto); 
			
		} catch (BusinessServiceException ex) {
			localLogger
					.info("Business Service Exception @RegistrationDelegateImpl.loadZipSet() due to"
							+ ex.getMessage());
			throw new DelegateException(
					"Business Service @RegistrationDelegateImpl.loadZipSet() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			localLogger
					.info("General Exception @RegistrationDelegateImpl.loadZipSet() due to"
							+ ex.getMessage());
			throw new DelegateException(
					"General Exception @RegistrationDelegateImpl.loadZipSet() due to "
							+ ex.getMessage());
		}
		
		return providerRegistrationDto;
	}
	
	public ProviderRegistrationDto saveRegistration(
			ProviderRegistrationDto providerRegistrationDto)
			throws DelegateException, DuplicateUserException {
		try {
			ProviderRegistrationVO providerRegistrationVO = new ProviderRegistrationVO();			
			providerRegistrationVO = providerRegistrationMapper.convertDTOtoVO(providerRegistrationDto,providerRegistrationVO);
			providerRegistrationVO =  iRegistrationBO.saveRegistration(providerRegistrationVO);
			providerRegistrationDto = providerRegistrationMapper.convertVOtoDTO(providerRegistrationVO,providerRegistrationDto);
			
		} catch (BusinessServiceException ex) {
			localLogger
					.info("Business Service Exception @RegistrationDelegateImpl.saveRegistration() due to"
							+ ex.getMessage());
			throw new DelegateException(
					"Business Service @RegistrationDelegateImpl.saveRegistration() due to "
							+ ex.getMessage());
		}catch (DuplicateUserException ex) {
			localLogger
			.info("Business Service Exception @RegistrationDelegateImpl.saveRegistration() due to"
					+ ex.getMessage());
			throw ex;
		} catch (Exception ex) {
			localLogger
					.info("General Exception @RegistrationDelegateImpl.saveRegistration() due to"
							+ ex.getMessage());
			throw new DelegateException(
					"General Exception @RegistrationDelegateImpl.saveRegistration() due to "
							+ ex.getMessage());
		}
		return providerRegistrationDto;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.marketplace.web.delegates.IProviderRegistrationDelegate#converDtoToVo(com.newco.marketplace.web.dto.ProviderRegistrationDto,
	 *      com.newco.marketplace.vo.ProviderRegistrationVO)
	 */
	public ProviderRegistrationVO converDtoToVo(ProviderRegistrationDto providerRegistrationDto, ProviderRegistrationVO providerRegistrationVO) {
		return (ProviderRegistrationVO) providerRegistrationMapper.convertDTOtoVO(providerRegistrationDto , providerRegistrationVO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.marketplace.web.delegates.IProviderRegistrationDelegate#converVoToDto(com.newco.marketplace.vo.ProviderRegistrationVO,
	 *      com.newco.marketplace.web.dto.ProviderRegistrationDto)
	 */
	public ProviderRegistrationDto converVoToDto(ProviderRegistrationVO providerRegistrationVO, ProviderRegistrationDto providerRegistrationDto) {
		return (ProviderRegistrationDto) providerRegistrationMapper.convertVOtoDTO(providerRegistrationVO, providerRegistrationDto);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.marketplace.web.delegates.IProviderRegistrationDelegate#validateUserName(String)
	 */
	public boolean validateUserName(String userName) {
		return iRegistrationBO.validateUserName(userName);
	}

	/**
	 * @return the iRegistrationBO
	 */
	public IRegistrationBO getiRegistrationBO() {
		return iRegistrationBO;
	}

	/**
	 * @param registrationBO the iRegistrationBO to set
	 */
	public void setiRegistrationBO(IRegistrationBO registrationBO) {
		iRegistrationBO = registrationBO;
	}

}
