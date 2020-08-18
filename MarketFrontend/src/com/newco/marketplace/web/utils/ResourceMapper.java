/**
 * 
 */
package com.newco.marketplace.web.utils;

import com.newco.marketplace.vo.provider.ResourceVO;
import com.newco.marketplace.web.dto.provider.ResourceDto;

/**
 * @author LENOVO USER
 * 
 */
public class ResourceMapper extends ObjectMapper {

	public ResourceVO convertDTOtoVO(Object objectDto, Object objectVO) {
		
		ResourceDto resourceDto = (ResourceDto)objectDto;		
		ResourceVO resourceVO = (ResourceVO) objectVO;
		if(resourceDto != null)
		{
			resourceVO.setResourceId(resourceDto.getResourceId());
			resourceVO.setResourceName(resourceDto.getResourceName());
		}
		return resourceVO;
	}
	
	public ResourceDto convertVOtoDTO(Object objectVO, Object objectDto) {
		ResourceDto resourceDto = (ResourceDto)objectDto;		
		ResourceVO resourceVO = (ResourceVO) objectVO;
		resourceDto.setResourceId(resourceVO.getResourceId());
		resourceDto.setResourceName(resourceVO.getResourceName());
		return resourceDto;
	}
	
}
