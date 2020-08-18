package com.newco.marketplace.dto.vo;

public class SkuTaskVO {
	private String taskName;
	private Integer nodeId;
	private String taskComments;
	private Integer skillType;
	private SkillTreeVo skillVo;
	
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskComments() {
		return taskComments;
	}
	public void setTaskComments(String taskComments) {
		this.taskComments = taskComments;
	}
	public SkillTreeVo getSkillVo() {
		return skillVo;
	}
	public void setSkillVo(SkillTreeVo skillVo) {
		this.skillVo = skillVo;
	}
	public Integer getNodeId() {
		return nodeId;
	}
	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}
	public Integer getSkillType() {
		return skillType;
	}
	public void setSkillType(Integer skillType) {
		this.skillType = skillType;
	}

	

}
