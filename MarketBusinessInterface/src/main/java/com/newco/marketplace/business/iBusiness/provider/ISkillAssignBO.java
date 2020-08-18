package com.newco.marketplace.business.iBusiness.provider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.dto.vo.skillTree.SkillNodeIdsVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.util.ActivityCheckList;
import com.newco.marketplace.vo.provider.CheckedSkillsVO;
import com.newco.marketplace.vo.provider.LanguageVO;
import com.newco.marketplace.vo.provider.ServiceTypesVO;
import com.newco.marketplace.vo.provider.SkillAssignRequest;
import com.newco.marketplace.vo.provider.SkillAssignResponse;
import com.newco.marketplace.vo.provider.SkillAssignVO;
import com.newco.marketplace.vo.provider.SkillCompletionVO;
import com.newco.marketplace.vo.provider.SkillNodeVO;


public interface ISkillAssignBO  {
	public SkillAssignResponse assignSkills(SkillAssignVO  skillAssignVO) throws BusinessServiceException;
	public SkillAssignResponse getSkills(SkillAssignVO assignRequest)throws BusinessServiceException;
	public ArrayList<CheckedSkillsVO> getCheckedSkills(CheckedSkillsVO checkedSkills) throws BusinessServiceException;
	public SkillAssignResponse getGeneralSkills(SkillAssignRequest assignRequest)throws BusinessServiceException;
	public boolean getSkillsComplete(SkillCompletionVO skillComplete) throws BusinessServiceException;
	public ActivityCheckList updateActivity(Long activityLinkKey, String activityName, String actLinkKeyType, Long optionalVendorId) throws BusinessServiceException;
	public ActivityCheckList retrieveActivityList(int activityLinkKey, Long optionalVendorId)throws BusinessServiceException;
	public ArrayList<LanguageVO> getCheckedLanguages(LanguageVO languageVO) throws BusinessServiceException;
	public SkillAssignResponse updateSkillsActivity(SkillAssignVO  assignRequest) throws BusinessServiceException;
	public List<SkillNodeVO> getSkillsForResource(Integer resourceId) throws BusinessServiceException;
	public List<CheckedSkillsVO> getAllCheckedSkills(Integer resourceId) throws BusinessServiceException;
	public Map <Integer, List<SkillNodeVO>> getSkillTree(List<Integer> nodeIds) throws BusinessServiceException;
	public List<ServiceTypesVO> getServiceTypeTree(List<Integer> nodeIds)throws BusinessServiceException;
}
