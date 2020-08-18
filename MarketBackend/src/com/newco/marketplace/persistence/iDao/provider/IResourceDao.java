package com.newco.marketplace.persistence.iDao.provider;

import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.vo.provider.ResourceVO;

public interface IResourceDao 
{
	public ResourceVO getResourceName(ResourceVO resourceVO) throws DBException;
	public Integer getVendorId(String resourceId) throws DBException;
}
