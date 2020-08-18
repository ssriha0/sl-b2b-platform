/**
 * 
 */
package com.newco.marketplace.web.utils;

import com.newco.marketplace.vo.provider.SkillAssignVO;
import com.newco.marketplace.web.dto.provider.ResourceSkillAssignDto;

/**
 * @author LENOVO USER
 * 
 */
public class SkillAssignMapper extends ObjectMapper {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.marketplace.web.utils.ObjectMapper#convertDTOtoVO(java.lang.Object,
	 *      java.lang.Object)
	 */
	public SkillAssignVO convertDTOtoVO(Object objectDto, Object objectVO) {
		
		ResourceSkillAssignDto skillAssignDto = (ResourceSkillAssignDto)objectDto;		
		SkillAssignVO skillAssignVO = (SkillAssignVO) objectVO;
		if(skillAssignDto != null && skillAssignVO != null)
		{
			skillAssignVO.setNodeId(skillAssignDto.getNodeId());
			skillAssignVO.setResourceId(skillAssignDto.getResourceId());
			skillAssignVO.setResourceName(skillAssignDto.getResourceName());
			skillAssignVO.setSkillServiceList(skillAssignDto.getSkillServiceList());
			skillAssignVO.setSkillServiceListToDelete(skillAssignDto.getSkillServiceListToDelete());
			skillAssignVO.setLanguageList(skillAssignDto.getLanguageList());
			skillAssignVO.setRootNodeId(skillAssignDto.getRootNodeId());
		}
	return skillAssignVO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.marketplace.web.utils.ObjectMapper#convertVOtoDTO(java.lang.Object,
	 *      java.lang.Object)
	 */
	public ResourceSkillAssignDto convertVOtoDTO(Object objectVO, Object objectDto) {
		ResourceSkillAssignDto skillAssignDto = (ResourceSkillAssignDto)objectDto;		
		SkillAssignVO skillAssignVO = (SkillAssignVO) objectVO;
		skillAssignDto.setNodeId(skillAssignVO.getNodeId());
		skillAssignDto.setResourceId(skillAssignVO.getResourceId());
		skillAssignDto.setModifiedBy(skillAssignVO.getModifiedBy());
		skillAssignDto.setResourceName(skillAssignVO.getResourceName());
		return skillAssignDto;
	}
	
}
