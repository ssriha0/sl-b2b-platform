package com.newco.marketplace.dto.vo.providerSearch;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.dto.vo.skillTree.SkillNodeVO;
import com.sears.os.vo.SerializableBaseVO;


public class ProviderSkillServiceTypeRequestVO extends SerializableBaseVO{

	List<SkillNodeVO> skillNodeList;
	List<Integer> skillTypesList;
	List<Integer> resourcesList;
	List<Integer> theSkillNodeIdList = null;  // addded to save programm time
	
	
	public List<SkillNodeVO> getSkillNodeList() {
		return skillNodeList;
	}
	public void setSkillNodeList(List<SkillNodeVO> skillNodeList) {
		this.skillNodeList = skillNodeList;
	}
	public List<Integer> getSkillTypesList() {
		return skillTypesList;
	}
	public void setSkillTypesList(List<Integer> skillTypesList) {
		this.skillTypesList = skillTypesList;
	}
	public List<Integer> getAListOfTheSkillNodeIds(){
		if (theSkillNodeIdList == null){
			theSkillNodeIdList = new ArrayList<Integer>();
			for(SkillNodeVO aSkillNodeVO : skillNodeList)
				theSkillNodeIdList.add(aSkillNodeVO.getNodeId());
		}
		return theSkillNodeIdList;
	}
	public List<Integer> getResourcesList() {
		return resourcesList;
	}
	public void setResourcesList(List<Integer> resourcesList) {
		this.resourcesList = resourcesList;
	}
}
