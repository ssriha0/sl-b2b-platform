package com.newco.marketplace.vo.hi.provider;

import java.util.List;


public class SkillDetailVO extends ProviderSkillVO{

	private String rootSkillName;
	
	private String skillName;
	
	private String skillCategoryName;
	
	private List<String> serviceType;

	public List<String> getServiceType() {
		return serviceType;
	}

	public void setServiceType(List<String> serviceType) {
		this.serviceType = serviceType;
	}
	public String getRootSkillName() {
		return rootSkillName;
	}

	public void setRootSkillName(String rootSkillName) {
		this.rootSkillName = rootSkillName;
	}

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	public String getSkillCategoryName() {
		return skillCategoryName;
	}

	public void setSkillCategoryName(String skillCategoryName) {
		this.skillCategoryName = skillCategoryName;
	}

	
}
