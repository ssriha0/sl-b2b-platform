package com.newco.marketplace.persistence.iDao.provider;

import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.vo.provider.VendorResource;

public interface IResourceStatusDao {
	public String getResourceStatus(VendorResource vendorResource) throws DBException;

}
