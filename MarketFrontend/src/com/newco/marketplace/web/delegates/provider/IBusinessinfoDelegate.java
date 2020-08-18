package com.newco.marketplace.web.delegates.provider;

import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.vo.provider.BusinessinfoVO;
import com.newco.marketplace.web.dto.provider.BusinessinfoDto;

public interface IBusinessinfoDelegate {
	
	/**
	 * @param objBusinessinfoDto
	 * @return
	 * @throws Exception
	 */
	public boolean save(BusinessinfoDto objBusinessinfoDto) throws Exception;
	
	public BusinessinfoDto getData(BusinessinfoDto objBusinessinfoDto) throws Exception;
	
	/**
	 * @param businessinfoVO
	 * @param businessinfoDto
	 * @return
	 */
	public BusinessinfoDto converVoToDto(BusinessinfoVO businessinfoVO, BusinessinfoDto businessinfoDto);
	
	/**
	 * @param businessinfoDto
	 * @param businessinfoVO
	 * @return
	 */
	public BusinessinfoVO converDtoToVo(BusinessinfoDto businessinfoDto,BusinessinfoVO businessinfoVO);
	public BusinessinfoDto loadZipSet(BusinessinfoDto businessinfoDto) throws DelegateException; 

}
