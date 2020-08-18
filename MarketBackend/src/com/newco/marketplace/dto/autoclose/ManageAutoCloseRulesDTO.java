package com.newco.marketplace.dto.autoclose;

import java.util.List;

import com.newco.marketplace.dto.autoclose.AutoCloseRuleDTO;

public class ManageAutoCloseRulesDTO {
	

	private List<AutoCloseRuleDTO> autoCloseRuleDTOList;
	
	private List<AutoCloseRuleExclusionDTO> autoCloseRuleExclusionList;

	public List<AutoCloseRuleDTO> getAutoCloseRuleDTOList() {
		return autoCloseRuleDTOList;
	}

	public void setAutoCloseRuleDTOList(List<AutoCloseRuleDTO> autoCloseRuleDTOList) {
		this.autoCloseRuleDTOList = autoCloseRuleDTOList;
	}

	public List<AutoCloseRuleExclusionDTO> getAutoCloseRuleExclusionList() {
		return autoCloseRuleExclusionList;
	}

	public void setAutoCloseRuleExclusionList(
			List<AutoCloseRuleExclusionDTO> autoCloseRuleExclusionList) {
		this.autoCloseRuleExclusionList = autoCloseRuleExclusionList;
	}
	
}
