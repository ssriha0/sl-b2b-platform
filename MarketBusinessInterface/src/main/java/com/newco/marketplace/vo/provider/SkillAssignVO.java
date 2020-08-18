package com.newco.marketplace.vo.provider;

import java.sql.Date;
import java.util.ArrayList;

public class SkillAssignVO extends BaseVO{
    /**
	 * 
	 */
	private static final long serialVersionUID = 2580898853685092027L;
	private int resourceSkillId;
    private long resourceId;
    private int nodeId;
    private int skillTypeId;
    private Date createdDate;
    private Date modifiedDate;
    private String modifiedBy;
    private String resourceName;
    private boolean rootNode;
    private int rootNodeId;
    private int skillServiceTypeId;
    //New fields..
    private ArrayList skillServiceList;
    private ArrayList skillServiceListToDelete;
    private ArrayList languageList;
    
    /** @return int skillServiceTypeId */
    public int getSkillServiceTypeId() {
		return skillServiceTypeId;
	}
	/** @param skillServiceTypeId */
	public void setSkillServiceTypeId(int skillServiceTypeId) {
		this.skillServiceTypeId = skillServiceTypeId;
	}
	
    /** @return int rootNodeId */
    public int getRootNodeId() {
		return rootNodeId;
	}
	/** @param rootNodeId */
	public void setRootNodeId(int rootNodeId) {
		this.rootNodeId = rootNodeId;
	}
	/** @return boolean rootNode */
    public boolean isRootNode() {
		return rootNode;
	}
	/** @param rootNode */
	public void setRootNode(boolean rootNode) {
		this.rootNode = rootNode;
	}
	/** @return resourceName */
    public String getResourceName() {
		return resourceName;
	}
	/** @param resourceName */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	/** @return Date createdDate */
    public Date getCreatedDate() {
		return createdDate;
	}
	/** @param createdDate */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	/** @return String modifiedBy */
	public String getModifiedBy() {
		return modifiedBy;
	}
	/** @param modifiedBy */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	/** @return java.sql.Date modifiedDate */
	public Date getModifiedDate() {
		return modifiedDate;
	}
	/** @param modifiedDate */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	/** @return int nodeId */
	public int getNodeId() {
		return nodeId;
	}
	/** @param nodeId */
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
	/** @return int resourceId */
	public long getResourceId() {
		return resourceId;
	}
	/** @param resourceId */
	public void setResourceId(long resourceId) {
		this.resourceId = resourceId;
	}
	/** @return int getSkillTypeId */
	public int getSkillTypeId() {
		return skillTypeId;
	}
	/** @param skillTypeId */
	public void setSkillTypeId(int skillTypeId) {
		this.skillTypeId = skillTypeId;
	}
	/** @return int resourceSkillId*/
	public int getResourceSkillId() {
		return resourceSkillId;
	}
	/** @param resourceSkillId */
	public void setResourceSkillId(int resourceSkillId) {
		this.resourceSkillId = resourceSkillId;
	}
	public ArrayList getSkillServiceList() {
		return skillServiceList;
	}
	public ArrayList getSkillServiceListToDelete() {
		return skillServiceListToDelete;
	}
	public void setSkillServiceList(ArrayList skillServiceList) {
		this.skillServiceList = skillServiceList;
	}
	public void setSkillServiceListToDelete(ArrayList skillServiceListToDelete) {
		this.skillServiceListToDelete = skillServiceListToDelete;
	}
	public ArrayList getLanguageList() {
		return languageList;
	}
	public void setLanguageList(ArrayList languageList) {
		this.languageList = languageList;
	}

}
