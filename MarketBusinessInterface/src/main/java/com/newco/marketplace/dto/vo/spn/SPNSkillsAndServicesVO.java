package com.newco.marketplace.dto.vo.spn;

import java.io.Serializable;
import java.util.Map; 

public class SPNSkillsAndServicesVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2490965920327029848L;
	private String mainService;
	private Map<String, SPNProviderRequirementsVO> skills;
	public String getMainService() {
		return mainService;
	}
	public void setMainService(String mainService) {
		this.mainService = mainService;
	}
	public Map<String, SPNProviderRequirementsVO> getSkills() {
		return skills;
	}
	public void setSkills(Map<String, SPNProviderRequirementsVO> skills) {
		this.skills = skills;
	}
	
	
}
