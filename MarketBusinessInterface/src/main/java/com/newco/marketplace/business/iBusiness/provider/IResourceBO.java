package com.newco.marketplace.business.iBusiness.provider;

import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.vo.provider.ResourceVO;

public interface IResourceBO  {

	public ResourceVO getResourceName(ResourceVO resourceVO) throws BusinessServiceException;
	public boolean isResourceAssociatedWithLoggedUser(String resourceId, Integer loggedInUserCompanyId) throws BusinessServiceException;
	public Contact getVendorResourceContact(int resourceId) throws BusinessServiceException;
}
