package com.newco.marketplace.web.delegates.provider;

import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.vo.provider.LoginVO;
import com.newco.marketplace.web.dto.provider.LoginDto;


/**
 * @author KSudhanshu
 *
 */
public interface ILoginDelegate {
	
	/**
	 * @param objLoginDto
	 * @return
	 * @throws Exception
	 */
	public int validatePassword(LoginDto objLoginDto) throws Exception;
	
	/**
	 * @param loginVO
	 * @param loginDto
	 * @return
	 */
	public LoginDto converVoToDto(LoginVO loginVO, LoginDto loginDto);
	
	/**
	 * @param loginDto
	 * @param loginVO
	 * @return
	 */
	public LoginVO converDtoToVo(LoginDto loginDto,LoginVO loginVO);
	
	/**
	 * @return
	 * @throws Exception
	 */
	public String getVendorId(String username) throws Exception;
	
	/**
	 * @return
	 * @throws Exception
	 */
	public String getProviderName(String username) throws Exception;
	
	public com.newco.marketplace.web.dto.provider.VendorHdrDto getVendorDetails(String vendorID) throws DelegateException;
	
	/**
	 * 
	 * @param vendorId
	 * @return
	 * @throws DelegateException
	 */
	
	public String getPrimaryIndicator(Integer vendorId) throws DelegateException;

	/**
	 * Description:  Retrieves resource ID from the admin_resource table for use in 
	 * the workflow monitor.
	 * @param username
	 * @return <code>Integer</code> which is the admin's resource id.
	 */
	public Integer retrieveResourceIDByUserName(String username);
	
	/**
	 * Description - Get temporary password indicator from username
	 * @return <code>boolean</code>
	 */
	public boolean getTempPasswordIndicator(String username);
	
	/**
	 * @param username
	 * @return
	 */
	public LoginVO getPassword(String username);

}
