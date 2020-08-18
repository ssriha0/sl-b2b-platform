package com.servicelive.lookup.dao;

import java.util.HashMap;
import java.util.Map;

import com.servicelive.common.ABaseDao;
import com.servicelive.common.exception.DataServiceException;
import com.servicelive.lookup.vo.EntityLookupVO;


/**
 * Class LookupSupplierDao.
 */
public class LookupSupplierDao extends ABaseDao implements ILookupSupplierDao {
	
	/* (non-Javadoc)
	 * @see com.servicelive.lookup.dao.ILookupSupplierDao#getBuyerInfo(java.lang.Long)
	 */
	public EntityLookupVO getBuyerInfo(Long entityId) throws DataServiceException
	{
		try
		{
			// code change for SLT-2112
			Map<String, Long> parameter = new HashMap<String, Long>();
					parameter.put("entityId", entityId);
			return (EntityLookupVO)queryForObject("getBuyerInfo", parameter);
		}
		catch(Exception e)
		{
			logger.info("getBuyerInfo failed.",e);
			throw new DataServiceException(e.getMessage(),e);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.servicelive.lookup.dao.ILookupSupplierDao#getProviderInfo(java.lang.Long)
	 */
	public EntityLookupVO getProviderInfo(Long entityId) throws DataServiceException
	{
		try
		{
			return (EntityLookupVO)queryForObject("getProviderInfo", entityId);
		}
		catch(Exception e)
		{
			logger.info("getProviderInfo failed.",e);
			throw new DataServiceException(e.getMessage(),e);
		}
	}
}
