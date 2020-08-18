package com.newco.marketplace.web.delegates.provider;

import java.util.ArrayList;

import com.newco.marketplace.vo.provider.CheckedSkillsVO;
import com.newco.marketplace.vo.provider.SkillCompletionVO;
import com.newco.marketplace.web.dto.provider.ResourceSkillAssignDto;
import com.newco.marketplace.web.utils.ActivityCheckList;

/**
 * @author LENOVO USER
 *
 */
public interface ISkillAssignDelegate { 
	public ResourceSkillAssignDto assignSkills(ResourceSkillAssignDto skillAssignDto) throws Exception;
	public ResourceSkillAssignDto getSkills(ResourceSkillAssignDto skillAssignDto) throws Exception;
	public ResourceSkillAssignDto getGeneralSkills(ResourceSkillAssignDto skillAssignDto) throws Exception;
	public ArrayList<CheckedSkillsVO> getCheckedSkills(CheckedSkillsVO checkedSkills) throws Exception;
	public boolean getSkillsComplete(SkillCompletionVO skillComplete) throws Exception;
	public ActivityCheckList updateActivity(Long actLinkKey,String activityName, String actLinkKeyType, Long optionalVendorId) throws Exception;
	public ActivityCheckList getActivityCheckList(Long activityLinkKey,	Long optionalVendorId) throws Exception; 
	public void updateSkillsActivity(ResourceSkillAssignDto skillAssignDto) throws Exception;
}
