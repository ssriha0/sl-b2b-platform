
package com.newco.marketplace.web.delegatesImpl.provider;

import com.newco.marketplace.business.iBusiness.provider.ILicensesAndCertBO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.vo.provider.LicensesAndCertVO;
import com.newco.marketplace.web.delegates.provider.ILicensesAndCertDelegate;
import com.newco.marketplace.web.dto.provider.LicensesAndCertDto;
import com.newco.marketplace.web.utils.LicensesAndCertMapper;

/**
 * @author LENOVO USER
 *
 */
public class LicensesAndCertDelegateImpl implements ILicensesAndCertDelegate {
	
	private ILicensesAndCertBO iLicensesAndCertBO;
	private LicensesAndCertMapper licensesAndCertMapper;

	/**
	 * @param licensesAndCertBO
	 */
	public LicensesAndCertDelegateImpl(ILicensesAndCertBO licensesAndCertBO, 
			LicensesAndCertMapper licensesAndCertMapper) {
		iLicensesAndCertBO = licensesAndCertBO;
		this.licensesAndCertMapper = licensesAndCertMapper;
		
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.ILicensesAndCertDelegate#save(com.newco.marketplace.web.dto.LicensesAndCertDto)
	 */
	public LicensesAndCertDto save(LicensesAndCertDto objLicensesAndCertDto) throws Exception {
		LicensesAndCertVO licensesAndCertVO = new LicensesAndCertVO();
		licensesAndCertVO =  licensesAndCertMapper.convertDTOtoVO(objLicensesAndCertDto, licensesAndCertVO);
		licensesAndCertVO =  iLicensesAndCertBO.save(licensesAndCertVO);
		objLicensesAndCertDto = licensesAndCertMapper.convertVOtoDTO(licensesAndCertVO, objLicensesAndCertDto);
		return objLicensesAndCertDto;
	}
	
	

	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.ILicensesAndCertDelegate#getData(com.newco.marketplace.web.dto.LicensesAndCertDto)
	 */ 
	public LicensesAndCertDto getData(LicensesAndCertDto objLicensesAndCertDto) throws Exception {
		LicensesAndCertVO licensesAndCertVO = new LicensesAndCertVO();
		licensesAndCertVO =  licensesAndCertMapper.convertDTOtoVO(objLicensesAndCertDto, licensesAndCertVO);
		return converVoToDto(iLicensesAndCertBO.getData(licensesAndCertVO),new LicensesAndCertDto());
	}

	public LicensesAndCertDto getDataList(LicensesAndCertDto objLicensesAndCertDto) throws Exception {
		LicensesAndCertVO licensesAndCertVO = new LicensesAndCertVO();
		licensesAndCertVO =  licensesAndCertMapper.convertDTOtoVO(objLicensesAndCertDto, licensesAndCertVO);
		return converVoToDto(iLicensesAndCertBO.getDataList(licensesAndCertVO),new LicensesAndCertDto());
	}
	
	public LicensesAndCertVO converDtoToVo(LicensesAndCertDto licensesAndCertDto, LicensesAndCertVO licensesAndCertVO) {
		return  (LicensesAndCertVO)licensesAndCertMapper.convertDTOtoVO(licensesAndCertDto, licensesAndCertVO);
	}

	public LicensesAndCertDto converVoToDto(LicensesAndCertVO licensesAndCertVO, LicensesAndCertDto licensesAndCertDto) {
		return   (LicensesAndCertDto)licensesAndCertMapper.convertVOtoDTO(licensesAndCertVO, licensesAndCertDto);
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.ILicensesDelegate#update(com.newco.marketplace.vo.LicensesVO)
	 */
	public void update(LicensesAndCertDto objLicensesAndCertDto) {
		//Map Dto to VO
		LicensesAndCertVO licensesAndCertVO = new LicensesAndCertVO();
		licensesAndCertVO = licensesAndCertMapper.convertDTOtoVO(objLicensesAndCertDto, licensesAndCertVO);
		
		//call BO method
		iLicensesAndCertBO.update(licensesAndCertVO);		
	}
	
	public LicensesAndCertDto removeDocumentDetails(LicensesAndCertDto objLoginDto) throws DelegateException
	{
		try
		{
			LicensesAndCertVO licensesAndCertVO = new LicensesAndCertVO();
			licensesAndCertVO = licensesAndCertMapper.convertDTOtoVO(objLoginDto, licensesAndCertVO);
			iLicensesAndCertBO.removeDocument(licensesAndCertVO);
			
		}catch (BusinessServiceException ex) {
			throw new DelegateException(
					"Business Service @LicensesAndCertDelegateImpl.removeDocumentDetails() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			throw new DelegateException(
					"General Exception @LicensesAndCertDelegateImpl.removeDocumentDetails() due to "
							+ ex.getMessage());
		}
		return objLoginDto;
	}
	
	public LicensesAndCertDto viewDocumentDetails(LicensesAndCertDto objLoginDto) throws DelegateException
	{
		try
		{	
			LicensesAndCertVO licensesAndCertVO = new LicensesAndCertVO();
			licensesAndCertVO = licensesAndCertMapper.convertDTOtoVO(objLoginDto, licensesAndCertVO);
			licensesAndCertVO = iLicensesAndCertBO.viewDocument(licensesAndCertVO);
			objLoginDto = licensesAndCertMapper.convertVOtoDTO4Display(licensesAndCertVO, objLoginDto);
			
		}catch (BusinessServiceException ex) {
			throw new DelegateException(
					"Business Service @LicensesAndCertDelegateImpl.viewDocumentDetails() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			throw new DelegateException(
					"General Exception @LicensesAndCertDelegateImpl.viewDocumentDetails() due to "
							+ ex.getMessage());
		}
		return objLoginDto;
	}
	
}
