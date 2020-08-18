package com.newco.marketplace.search.types;

import java.util.SortedSet;
import java.util.TreeSet;

import com.newco.marketplace.vo.provider.CheckedSkillsVO;

public class Skill {
	String name;
	int level;
	SortedSet<String> type; // one of the ServiceTypes
	int nodeId;
	
	public Skill() {
		// intentionally blank
	}
	
	public Skill(CheckedSkillsVO vo) {
		this.name = vo.getNodeName();
		this.type = new TreeSet<String>();
		this.type.add(vo.getServiceType());
		this.level = vo.getLevel();
		//R 16_2_0_1: SL-21376: Adding nodeId
		this.nodeId = vo.getNodeId();
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
	public void addServiceType(String type2) {
		this.type.add(type2);
	}

	public SortedSet<String> getType() {
		return type;
	}

	public void setType(SortedSet<String> type) {
		this.type = type;
	}
	
	public int getNodeId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
	

}
