package com.newco.marketplace.web.delegatesImpl.provider;

import com.newco.marketplace.business.iBusiness.provider.IResourceBO;
import com.newco.marketplace.vo.provider.ResourceVO;
import com.newco.marketplace.web.delegates.provider.IResourceDelegate;
import com.newco.marketplace.web.dto.provider.ResourceDto;
import com.newco.marketplace.web.utils.ResourceMapper;

public class ResourceDelegateImpl implements IResourceDelegate {

	private IResourceBO iResourceBO;
	private ResourceMapper resourceMapper;

	public ResourceDelegateImpl(IResourceBO iResourceBO, ResourceMapper resourceMapper) {
		this.iResourceBO = iResourceBO;
		this.resourceMapper = resourceMapper;
	}

	public ResourceDto getResourceName(ResourceDto resourceDto) throws Exception {
		ResourceVO  resourceVO = new ResourceVO();
		resourceVO = resourceMapper.convertDTOtoVO(resourceDto, resourceVO);
		resourceVO = iResourceBO.getResourceName(resourceVO); 
		resourceDto.setResourceId(resourceVO.getResourceId());
		resourceDto.setResourceName(resourceVO.getResourceName());
		return resourceDto;
	}

	/**
	 * Calls the BO to check whether a resource is associated with the logged in/adopted user
	 * @param resourceId
	 * @param loggedInUserCompanyId
	 * @return boolean
	 * @throws Exception
	 */
	public boolean isResourceAssociatedWithLoggedUser(String resourceId, Integer loggedInUserCompanyId) throws Exception {
		boolean result = iResourceBO.isResourceAssociatedWithLoggedUser(resourceId, loggedInUserCompanyId);
		return result;
	}

}
