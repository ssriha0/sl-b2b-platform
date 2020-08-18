package com.newco.marketplace.web.delegates.provider;

import com.newco.marketplace.web.dto.provider.ResourceDto;

/**
 * @author LENOVO USER
 *
 */
public interface IResourceDelegate { 

	public ResourceDto getResourceName(ResourceDto ResourceDto)  throws Exception;
	public boolean isResourceAssociatedWithLoggedUser(String resourceId, Integer loggedInUserCompanyId) throws Exception;
}
