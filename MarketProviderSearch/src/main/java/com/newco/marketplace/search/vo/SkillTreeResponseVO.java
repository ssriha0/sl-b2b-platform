package com.newco.marketplace.search.vo;

import java.util.ArrayList;
import java.util.List;


/**
 * @author ndixit
 *
 */
public class SkillTreeResponseVO extends BaseResponseVO{
	
	private String nodeName;
	private int nodeId;
	private int parentNodeId;
	private String parentNodeName;
	private int level;
	private String collate;
	private String originalTerm;
	private String score;
	
	
	public SkillTreeResponseVO(){}
	
	public SkillTreeResponseVO(String origTerm, String collate) {
		this.originalTerm = origTerm;
		this.collate = collate;
	}

	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public int getNodeId() {
		return nodeId;
	}
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
	public int getParentNodeId() {
		return parentNodeId;
	}
	public void setParentNodeId(int parentNodeId) {
		this.parentNodeId = parentNodeId;
	}
	public String getParentNodeName() {
		return parentNodeName;
	}
	public void setParentNodeName(String parentNodeName) {
		this.parentNodeName = parentNodeName;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getCollate() {
		return collate;
	}
	public void setCollate(String collate) {
		this.collate = collate;
	}
	public String getOriginalTerm() {
		return originalTerm;
	}
	public void setOriginalTerm(String originalTerm) {
		this.originalTerm = originalTerm;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	
	public String toString() {
        return "nodeName:" + this.nodeName + ",, "
                + "nodeId:" + this.nodeId + ",, " + "parentNodeId:"
                + this.parentNodeId + ",, " + "parentNodeName" + this.parentNodeName + ",, "
                + "level:" + this.level + ",, " + "collate:" + ",, "
                + this.collate + ",, " + "originalTerm" + this.originalTerm + ",, " + "score" + this.score;
    }
	
	
}
