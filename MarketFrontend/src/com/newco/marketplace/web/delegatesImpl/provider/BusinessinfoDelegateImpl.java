package com.newco.marketplace.web.delegatesImpl.provider;

import com.newco.marketplace.business.iBusiness.provider.IBusinessinfoBO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.vo.provider.BusinessinfoVO;
import com.newco.marketplace.web.delegates.provider.IBusinessinfoDelegate;
import com.newco.marketplace.web.dto.provider.BusinessinfoDto;
import com.newco.marketplace.web.utils.BusinessinfoMapper;

public class BusinessinfoDelegateImpl implements IBusinessinfoDelegate {

	private IBusinessinfoBO iBusinessinfoBO;
	private BusinessinfoMapper businessinfoMapper;
//	private BusinessinfoVO businessinfoVO;

	
	/**
	 * @param businessinfoBO
	 */
	public BusinessinfoDelegateImpl(IBusinessinfoBO businessinfoBO, 
			BusinessinfoMapper businessinfoMapper) {
		iBusinessinfoBO = businessinfoBO;
		
		this.businessinfoMapper = businessinfoMapper;
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.marketplace.web.delegates.IBusinessinfoDelegate#save(com.newco.marketplace.web.dto.Businessinfo)
	 */
	public boolean save(BusinessinfoDto objBusinessinfoDto) throws Exception {
		BusinessinfoVO businessinfoVO = new BusinessinfoVO();
		businessinfoVO = businessinfoMapper.convertDTOtoVO(objBusinessinfoDto, businessinfoVO);
		return iBusinessinfoBO.save(businessinfoVO);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.marketplace.web.delegates.IBusinessinfoDelegate#getData(com.newco.marketplace.web.dto.Businessinfo)
	 */
	public BusinessinfoDto getData(BusinessinfoDto objBusinessinfoDto) throws Exception {
		BusinessinfoVO businessinfoVO = new BusinessinfoVO();
		businessinfoVO = businessinfoMapper.convertDTOtoVO(objBusinessinfoDto, businessinfoVO);
		return converVoToDto(iBusinessinfoBO.getData(businessinfoVO),new BusinessinfoDto());
	}
	public BusinessinfoDto loadZipSet(
			BusinessinfoDto businessinfoDto)
	throws DelegateException {
		
		try {
			BusinessinfoVO businessinfoVO = new BusinessinfoVO();
			businessinfoVO = businessinfoMapper.convertDTOtoVO(businessinfoDto,businessinfoVO);
			businessinfoVO = iBusinessinfoBO
										.loadZipSet(businessinfoVO);
			businessinfoDto = businessinfoMapper.convertVOtoDTO(businessinfoVO,businessinfoDto); 
			
		} catch (BusinessServiceException ex) {
			throw new DelegateException(
					"Business Service @GeneralInfoDelegateImpl.loadZipSet() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			throw new DelegateException(
					"General Exception @GeneralInfoDelegateImpl.loadZipSet() due to "
							+ ex.getMessage());
		}
		
		return businessinfoDto;
	}
	public BusinessinfoVO converDtoToVo(BusinessinfoDto businessinfoDto, BusinessinfoVO businessinfoVO) {
		return (BusinessinfoVO) businessinfoMapper.convertDTOtoVO(businessinfoDto, businessinfoVO);
	}

	public BusinessinfoDto converVoToDto(BusinessinfoVO businessinfoVO, BusinessinfoDto businessinfoDto) {
		return (BusinessinfoDto) businessinfoMapper.convertVOtoDTO(businessinfoVO, businessinfoDto);
	}

}
