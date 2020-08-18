package com.newco.marketplace.search.vo;

public class SkillRequestVO extends BaseRequestVO{
	
	String skillTree;
	boolean spellCheck=true;

	public String getSkillTree() {
		return skillTree;
	}

	public void setSkillTree(String skillTree) {
		this.skillTree = skillTree;
	}

	public boolean isSpellCheck() {
		return spellCheck;
	}

	public void setSpellCheck(boolean spellCheck) {
		this.spellCheck = spellCheck;
	}
	
}
