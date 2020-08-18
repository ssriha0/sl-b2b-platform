package com.newco.marketplace.vo.provider;

import java.util.ArrayList;

public class SkillNodeVO extends BaseVO{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7229438926238205680L;
	protected String nodeName;
	private long parentNodeId;	 
	protected int nodeId;
	private int level;
	private boolean active;
	private ArrayList<ServiceTypesVO> serviceTypes = new ArrayList();
	private int sortOrder;
	private boolean overriderSkillType;
	private Integer rootNodeId;
	
//	protected boolean root;
//	protected boolean leaf;
//	protected String nodeDescription;
//	protected NodeContent myContent;
//	private AWizzardAttribute aWizzardAttribute1;
//	protected AWizzardAttribute nodeAttributes[];
	
	public int getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	/** @return Array List of serviceTypes*/
	public ArrayList<ServiceTypesVO> getServiceTypes() {
		return serviceTypes;
	}
	/** @param serviceTypes */
	public void setServiceTypes(ArrayList<ServiceTypesVO> serviceTypes) {
		this.serviceTypes = serviceTypes;
	}
	/** @return boolean isActive */
	public boolean isActive() {
		return active;
	}
	/** @param active */
	public void setActive(boolean active) {
		this.active = active;
	}
	/**@return long parentNodeId*/
	public long getParentNodeId() {
		return parentNodeId;
	}
	/**@param parentNodeId */
	public void setParentNodeId(long parentNodeId) {
		this.parentNodeId = parentNodeId;
	}
	/**@return int nodeId*/
	public int getNodeId() {
		return nodeId;
	}
	/** @param nodeId 
	 */
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
	
	/** @return String nodeName */
	public String getNodeName() {
		return nodeName;
	}
	
	/** @param nodeName */
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	/** @return int level */
	public int getLevel() {
		return level;
	}
	/** @param level */
	public void setLevel(int level) {
		this.level = level;
	}
	public boolean isOverriderSkillType() {
		return overriderSkillType;
	}
	public void setOverriderSkillType(boolean overriderSkillType) {
		this.overriderSkillType = overriderSkillType;
	}
	/**
	 * @return the rootNodeId
	 */
	public Integer getRootNodeId() {
		return rootNodeId;
	}
	/**
	 * @param rootNodeId the rootNodeId to set
	 */
	public void setRootNodeId(Integer rootNodeId) {
		this.rootNodeId = rootNodeId;
	}

}
