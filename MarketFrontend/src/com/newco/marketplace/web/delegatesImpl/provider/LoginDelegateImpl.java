package com.newco.marketplace.web.delegatesImpl.provider;


import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.provider.ILoginBO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.vo.provider.LoginVO;
import com.newco.marketplace.vo.provider.VendorHdr;
import com.newco.marketplace.web.delegates.provider.ILoginDelegate;
import com.newco.marketplace.web.dto.provider.LoginDto;
import com.newco.marketplace.web.dto.provider.VendorHdrDto;
import com.newco.marketplace.web.utils.LoginMapper;
import com.newco.marketplace.web.utils.VendorHdrMapper;

/**
 * @author KSudhanshu
 *
 * $Revision: 1.9 $ $Author: glacy $ $Date: 2008/04/26 01:13:51 $
 */

/*
 * Maintenance History
 * $Log: LoginDelegateImpl.java,v $
 * Revision 1.9  2008/04/26 01:13:51  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.7.14.1  2008/04/23 11:41:42  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.8  2008/04/23 05:19:44  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.7  2008/02/05 22:25:29  mhaye05
 * removed commented out code
 *
 */
public class LoginDelegateImpl implements ILoginDelegate {

	private ILoginBO iLoginBO;
	private LoginMapper loginMapper;
	private VendorHdrMapper vendorHdrMapper;
	private static final Logger localLogger = Logger
	.getLogger(LoginDelegateImpl.class.getName());

	/**
	 * @param loginBO
	 */
	public LoginDelegateImpl(ILoginBO loginBO, LoginMapper loginMapper, 
						 VendorHdrMapper vendorHdrMapper) {
		this.iLoginBO = loginBO;
		this.loginMapper = loginMapper;
		this.vendorHdrMapper = vendorHdrMapper;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.marketplace.web.delegates.ILoginDelegate#isTempPassword(com.newco.marketplace.web.dto.LoginDto)
	 */
	public int validatePassword(LoginDto objLoginDto) throws Exception {
		LoginVO loginVO = new LoginVO();
		loginVO = loginMapper.convertDTOtoVO(objLoginDto, loginVO);
		return iLoginBO.validatePassword(loginVO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.marketplace.web.delegates.ILoginDelegate#converDtoToVo(com.newco.marketplace.web.dto.LoginDto,
	 *      com.newco.marketplace.vo.LoginVO)
	 */
	public LoginVO converDtoToVo(LoginDto loginDto, LoginVO loginVO) {
		return (LoginVO) loginMapper.convertDTOtoVO(loginDto, loginVO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.marketplace.web.delegates.ILoginDelegate#converVoToDto(com.newco.marketplace.vo.LoginVO,
	 *      com.newco.marketplace.web.dto.LoginDto)
	 */
	public LoginDto converVoToDto(LoginVO loginVO, LoginDto loginDto) {
		return (LoginDto) loginMapper.convertVOtoDTO(loginVO, loginDto);
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.ILoginDelegate#getVendorId(com.newco.marketplace.web.dto.LoginDto)
	 */
	public String getVendorId(String username) throws Exception {
		return iLoginBO.getVendorId(username);
	}
	
	/**
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public String getProviderName(String username) throws Exception {
		return iLoginBO.getProviderName(username);
	}
	public VendorHdr converDtoToVo(VendorHdrDto vendorHdrDto, VendorHdr vendorHdr) {
		return (VendorHdr) vendorHdrMapper.convertDTOtoVO(vendorHdrDto, vendorHdr);
	}
	/**
	 * 
	 * @param vendorHdr
	 * @param vendorHdrDto
	 * @return
	 */
	public VendorHdrDto converVoToDto(VendorHdr vendorHdr, VendorHdrDto vendorHdrDto) {
		return (VendorHdrDto) vendorHdrMapper.convertVOtoDTO(vendorHdr, vendorHdrDto);
	}
	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.provider.ILoginDelegate#getVendorDetails(java.lang.String)
	 */
	public VendorHdrDto getVendorDetails(String vendorId) throws DelegateException
	{
		VendorHdr vendorHdr = new VendorHdr();
		VendorHdrDto vendorHdrDto = new VendorHdrDto();
		
		try
		{ 
		vendorHdr = iLoginBO.getVendorDetails(vendorId);
		vendorHdrDto = vendorHdrMapper.convertVOtoDTO(vendorHdr, vendorHdrDto);
		}
		catch (BusinessServiceException ex) {
			localLogger
					.info("Business Service Exception @LoginDelegateImpl.getVendorDetails() due to"
							+ ex.getMessage());
			throw new DelegateException(
					"Business Service @LoginDelegateImpl.getVendorDetails() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			localLogger.info("General Exception @@LoginDelegateImpl.getVendorDetails() due to"
							+ ex.getMessage());
			throw new DelegateException(
					"General Exception @@LoginDelegateImpl.getVendorDetails() due to "
							+ ex.getMessage());
		}
		return vendorHdrDto;
	
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.provider.ILoginDelegate#getPrimaryIndicator(java.lang.Integer)
	 */
	public String getPrimaryIndicator(Integer vendorId) throws DelegateException
	{
		String primaryIndicator = null;
		try
		{
			primaryIndicator = iLoginBO.getPrimaryIndicator(vendorId);
		}
		catch (BusinessServiceException ex) {
			localLogger
					.info("Business Service Exception @LoginDelegateImpl.getPrimaryIndicator() due to"
							+ ex.getMessage());
			throw new DelegateException(
					"Business Service @LoginDelegateImpl.getPrimaryIndicator() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			localLogger.info("General Exception @@LoginDelegateImpl.getPrimaryIndicator() due to"
							+ ex.getMessage());
			throw new DelegateException(
					"General Exception @@LoginDelegateImpl.getPrimaryIndicator() due to "
							+ ex.getMessage());
		}
		
		return primaryIndicator;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.provider.ILoginDelegate#retrieveResourceIDByUserName(java.lang.String)
	 */
	public Integer retrieveResourceIDByUserName(String username) {
		return iLoginBO.retrieveResourceIDByUserName(username);
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.provider.ILoginDelegate#getTempPasswordIndicator(java.lang.String)
	 */
	public boolean getTempPasswordIndicator(String username) {
		return iLoginBO.getTempPasswordIndicator(username);
	}
	
	/**
	 * Get the password based on the user name.
	 * @param username
	 * @return
	 */
	public LoginVO getPassword(String username) {
		return iLoginBO.getPassword(username);
	}
	
}
