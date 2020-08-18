package com.newco.marketplace.search.types;

import java.util.ArrayList;
import java.util.List;

public class Category {
	private String root;
	private Integer rootId;
	private List<Skill> skillList;
	
	public Category() {
		// intentionally blank
	}
	
	public Category(String root) {
		this.root = root;		
	}
	public String getRoot() {
		return root;
	}
	public void setRoot(String root) {
		this.root = root;
	}
	public List<Skill> getSkillList() {
		return skillList;
	}
	public void setSkillList(List<Skill> skillList) {
		this.skillList = skillList;
	}
	
	
	public void addSkill(Skill child) {
		if (skillList == null)
			skillList = new ArrayList<Skill>();
		skillList.add(child);
	}

	public Integer getRootId() {
		return rootId;
	}

	public void setRootId(Integer rootId) {
		this.rootId = rootId;
	}
}
