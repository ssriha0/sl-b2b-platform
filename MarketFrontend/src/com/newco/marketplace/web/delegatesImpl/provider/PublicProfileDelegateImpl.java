package com.newco.marketplace.web.delegatesImpl.provider;


import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.provider.IPublicProfileBO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.vo.provider.PublicProfileVO;
import com.newco.marketplace.web.delegates.provider.IPublicProfileDelegate;
import com.newco.marketplace.web.dto.provider.PublicProfileDto;
import com.newco.marketplace.web.utils.PublicProfileMapper;
/**
 * @author LENOVO USER
 * 
 */
public class PublicProfileDelegateImpl implements IPublicProfileDelegate {

	private IPublicProfileBO publicProfileBO;
	private PublicProfileMapper publicProfileMapper;
	private static final Logger localLogger = Logger
			.getLogger(PublicProfileDelegateImpl.class);

	public PublicProfileDelegateImpl(IPublicProfileBO publicProfileBO,PublicProfileMapper publicProfileMapper) {
		this.publicProfileBO = publicProfileBO;
		this.publicProfileMapper = publicProfileMapper;

	}
	
	public PublicProfileDto getPublicProfile(PublicProfileDto publicProfileDto) throws DelegateException
	{
		
		PublicProfileDto profileDto = new PublicProfileDto();
		PublicProfileVO publicProfileVO = new PublicProfileVO();
		try
		{
			publicProfileVO = publicProfileMapper.convertDTOtoVO(publicProfileDto,publicProfileVO);
			
			publicProfileVO = publicProfileBO.getPublicProfile(publicProfileVO);
			
			
			if(publicProfileVO != null)
			{
				publicProfileMapper.convertVOtoDTO(publicProfileVO, profileDto);
				localLogger.info("***********************"+profileDto.getUserId());
				localLogger.info("inisde the delegate*************"+profileDto.getBusStartDt());
			}
			
		}catch (BusinessServiceException bse) {
			throw new DelegateException(bse);
		} catch (Exception ex) {
			ex.printStackTrace();
			localLogger
					.info("[General Exception Occured at PublicProfileDelegateImpl.getPublicProfile]"
							+ ex.getMessage());
			throw new DelegateException(
					"[General Exception Occured at PublicProfileDelegateImpl.getPublicProfile]"
							+ ex.getMessage());
		}
		return profileDto;
	}
	public boolean checkVendorResource(String resourceId, String vendorId) throws DelegateException
	{
		boolean chkStatus = false;
		try
		{
			chkStatus = publicProfileBO.checkVendorResource(resourceId, vendorId);
		}catch (BusinessServiceException bse) {
			throw new DelegateException(bse);
		} catch (Exception ex) {
			ex.printStackTrace();
			localLogger
					.info("[General Exception Occured at PublicProfileDelegateImpl.checkVendorResource]"
							+ ex.getMessage());
			throw new DelegateException(
					"[General Exception Occured at PublicProfileDelegateImpl.checkVendorResource]"
							+ ex.getMessage());
		}
		return chkStatus;
	}
}
