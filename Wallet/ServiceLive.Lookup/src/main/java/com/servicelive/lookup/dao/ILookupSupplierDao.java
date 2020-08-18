package com.servicelive.lookup.dao;

import com.servicelive.common.exception.DataServiceException;
import com.servicelive.lookup.vo.EntityLookupVO;

/**
 * Interface ILookupSupplierDao.
 */
public interface ILookupSupplierDao {

	/**
	 * getBuyerInfo.
	 * 
	 * @param entityId 
	 * 
	 * @return EntityLookupVO
	 * 
	 * @throws DataServiceException 
	 */
	public EntityLookupVO getBuyerInfo(Long entityId) throws DataServiceException;

	/**
	 * getProviderInfo.
	 * 
	 * @param entityId 
	 * 
	 * @return EntityLookupVO
	 * 
	 * @throws DataServiceException 
	 */
	public EntityLookupVO getProviderInfo(Long entityId) throws DataServiceException;

}