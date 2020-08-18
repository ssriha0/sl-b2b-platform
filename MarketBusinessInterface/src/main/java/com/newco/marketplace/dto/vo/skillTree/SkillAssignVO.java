/*
 * SillAssignVO     1.0     2007/05/21
 */
package com.newco.marketplace.dto.vo.skillTree;

import java.sql.Date;

import com.sears.os.vo.SerializableBaseVO;

/**
 * Skill Assign Value Object(VO)
 * 
 * @version
 * @author zizrale
 *
 */
public class SkillAssignVO extends SerializableBaseVO
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -3295749515475439035L;
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

}