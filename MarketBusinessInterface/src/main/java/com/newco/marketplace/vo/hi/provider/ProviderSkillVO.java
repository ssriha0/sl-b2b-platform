package com.newco.marketplace.vo.hi.provider;

import java.util.List;

import com.newco.marketplace.api.beans.Results;

public class ProviderSkillVO {
	
	private String providerId=null;
	private List<SkillDetailVO> skill=null;
	private Results results; 
	

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public Results getResults() {
		return results;
	}

	public void setResults(Results results) {
		this.results = results;
	}

	
	public List<SkillDetailVO> getSkill() {
		return skill;
	}

	public void setSkill(List<SkillDetailVO> skill) {
		this.skill = skill;
	}
}
