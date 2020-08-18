package com.newco.marketplace.web.delegatesImpl.provider;

import java.util.ArrayList;

import com.newco.marketplace.business.iBusiness.provider.ISkillAssignBO;
import com.newco.marketplace.vo.provider.CheckedSkillsVO;
import com.newco.marketplace.vo.provider.SkillAssignRequest;
import com.newco.marketplace.vo.provider.SkillAssignResponse;
import com.newco.marketplace.vo.provider.SkillAssignVO;
import com.newco.marketplace.vo.provider.SkillCompletionVO;
import com.newco.marketplace.web.delegates.provider.ISkillAssignDelegate;
import com.newco.marketplace.web.dto.provider.ResourceSkillAssignDto;
import com.newco.marketplace.web.utils.ActivityCheckList;
import com.newco.marketplace.web.utils.SkillAssignMapper;
/**
 * @author LENOVO USER
 * 
 */
public class SkillAssignDelegateImpl implements ISkillAssignDelegate {

	private ISkillAssignBO iSkillAssignBO;
//	private SkillAssignVO  skillAssignVO;
	private SkillAssignMapper skillAssignMapper;
	
	public SkillAssignDelegateImpl(ISkillAssignBO iSkillAssignBO,
			SkillAssignMapper skillAssignMapper){
		this.iSkillAssignBO = iSkillAssignBO;
//		this.skillAssignVO = skillAssignVO;
		this.skillAssignMapper = skillAssignMapper;
	}
	/**
	 * @param skillRequest
	 * @return SkillAssignResponse
	 * @throws Exception
	 */
	public ResourceSkillAssignDto assignSkills(ResourceSkillAssignDto skillAssignDto) throws Exception {
		SkillAssignVO  skillAssignVO = new SkillAssignVO();
		skillAssignVO = skillAssignMapper.convertDTOtoVO(skillAssignDto, skillAssignVO);
		SkillAssignResponse sr = iSkillAssignBO.assignSkills(skillAssignVO); 
		return skillAssignDto;
	}
	
	/**
	 * @param skillRequest
	 * @return SkillAssignResponse
	 * @throws Exception
	 */
	public ResourceSkillAssignDto getSkills(ResourceSkillAssignDto skillAssignDto) throws Exception {
		SkillAssignVO  skillAssignVO = new SkillAssignVO();
		skillAssignVO = skillAssignMapper.convertDTOtoVO(skillAssignDto, skillAssignVO);
		SkillAssignResponse sr = iSkillAssignBO.getSkills(skillAssignVO);
		skillAssignDto.setServiceTypes(sr.getServiceList());
		skillAssignDto.setSkillTreeList(sr.getSkillTreeList());
		return skillAssignDto;
	}
	
	/**
	 * @param skillRequest
	 * @return SkillAssignResponse
	 * @throws Exception
	 */
	public ResourceSkillAssignDto getGeneralSkills(ResourceSkillAssignDto skillAssignDto) throws Exception {
		
		SkillAssignRequest skillRequest = new SkillAssignRequest();
		skillRequest.setResourceId(skillAssignDto.getResourceId());
		SkillAssignResponse sr = iSkillAssignBO.getGeneralSkills(skillRequest);
		skillAssignDto.setSkillTreeList(sr.getSkillTreeList());
		skillAssignDto.setLanguageList(sr.getLanguageList());
		skillAssignDto.setResourceName(sr.getResourceName());
		return skillAssignDto;
	}
	
	/**
	 * @param checkedSkills
	 * @return ArrayList of CheckedSkillsVO
	 * @throws Exception 
	 */
	public ArrayList<CheckedSkillsVO> getCheckedSkills(CheckedSkillsVO checkedSkills) throws Exception {
	
		return iSkillAssignBO.getCheckedSkills(checkedSkills);
	}
	
	/**
	 * @param skillComplete
	 * @return boolean isComplete
	 * @throws Exception
	 */
	public boolean getSkillsComplete(SkillCompletionVO skillComplete) throws Exception {
		
		return iSkillAssignBO.getSkillsComplete(skillComplete);
	}

	/*
	 *  
	 * @see com.newco.marketplace.web.delegates.provider.ISkillAssignDelegate#updateActivity(java.lang.Long, java.lang.String, java.lang.String, java.lang.Long)
	 */
	public ActivityCheckList updateActivity(Long actLinkKey,
			String activityName, String actLinkKeyType, Long optionalVendorId)
			 throws Exception {
		ActivityCheckList acl= new ActivityCheckList();
		return acl;
	}
	
	/*
	 *  
	 * @see com.newco.marketplace.web.delegates.provider.ISkillAssignDelegate#getActivityCheckList(java.lang.Long, java.lang.Long)
	 */
	public ActivityCheckList getActivityCheckList(Long activityLinkKey,
			Long optionalVendorId) throws Exception {
		ActivityCheckList acl= new ActivityCheckList();
		return acl;
	}
	
	/*
	 *  
	 * @see com.newco.marketplace.web.delegates.provider.ISkillAssignDelegate#updateSkillsActivity(com.newco.marketplace.web.dto.provider.ResourceSkillAssignDto)
	 */
	public void updateSkillsActivity(ResourceSkillAssignDto skillAssignDto) throws Exception{
		SkillAssignVO  skillAssignVO = new SkillAssignVO();
		skillAssignVO.setResourceId(skillAssignDto.getResourceId());
		iSkillAssignBO.updateSkillsActivity(skillAssignVO);
	}
	
}
