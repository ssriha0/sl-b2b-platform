package com.newco.marketplace.dto.vo.providerSearch;

import java.util.List;

import com.sears.os.vo.SerializableBaseVO;


public class ProviderSkillRequestVO extends SerializableBaseVO{

	List<Integer> resourceIdsList;
	List<Integer> skillNodeList;
	List<Integer> skillTypesList;
	public List<Integer> getResourceIdsList() {
		return resourceIdsList;
	}
	public void setResourceIdsList(List<Integer> resourceIdsList) {
		this.resourceIdsList = resourceIdsList;
	}
	public List<Integer> getSkillNodeList() {
		return skillNodeList;
	}
	public void setSkillNodeList(List<Integer> skillNodeList) {
		this.skillNodeList = skillNodeList;
	}
	public List<Integer> getSkillTypesList() {
		return skillTypesList;
	}
	public void setSkillTypesList(List<Integer> skillTypesList) {
		this.skillTypesList = skillTypesList;
	}
}
