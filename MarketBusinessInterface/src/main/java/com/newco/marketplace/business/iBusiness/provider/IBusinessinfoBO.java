package com.newco.marketplace.business.iBusiness.provider;

import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.vo.provider.BusinessinfoVO;
import com.newco.marketplace.vo.provider.ProviderRegistrationVO;


public interface IBusinessinfoBO {
	
	/**
	 * @param objBusinessinfoVO TODO
	 * @return
	 * @throws Exceptiona
	 */
	public boolean save(BusinessinfoVO objBusinessinfoVO) throws Exception;
	
	public BusinessinfoVO getData(BusinessinfoVO objBusinessinfoVO) throws Exception; 
	public BusinessinfoVO loadZipSet(BusinessinfoVO businessinfoVO)throws BusinessServiceException;

}
