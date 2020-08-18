/*
 * SkillAssignRequest.java        1.0     2007/05/21 
 */
package com.newco.marketplace.vo.provider;

import java.util.ArrayList;

import com.sears.os.vo.SerializableBaseVO;

/**
 * SkillAssign action request
 * 
 * @version
 * @author zizrale
 *
 */
public class SkillAssignRequest extends SerializableBaseVO
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 5443174238366467011L;
	private long resourceId;
    private int nodeId;
    private String modifiedBy;
   
    private ArrayList<SkillServiceTypeVO> skillServiceArray;
    private int rootNodeId;
    private ArrayList<SkillServiceTypeVO> skillServiceArrayToDelete;

    
	/** @return ArrayList of skill nodes to delete in DB */
	public ArrayList<SkillServiceTypeVO> getSkillServiceArrayToDelete() {
		return skillServiceArrayToDelete;
	}
	/** @param setSkillServiceArrayToDelete */
	public void setSkillServiceArrayToDelete(
			ArrayList<SkillServiceTypeVO> setSkillServiceArrayToDelete) {
		this.skillServiceArrayToDelete = setSkillServiceArrayToDelete;
	}
	/** @return int rootNodeId */
	public int getRootNodeId() {
		return rootNodeId;
	}
	/** @param rootNodeId */
	public void setRootNodeId(int rootNodeId) {
		this.rootNodeId = rootNodeId;
	}
	/** @return ArrayList of SkillServiceTypeVO*/
	public ArrayList<SkillServiceTypeVO> getSkillServiceArray() {
		return skillServiceArray;
	}
	/** @param skillServiceArray */
	public void setSkillServiceArray(ArrayList<SkillServiceTypeVO> skillServiceArray) {
		this.skillServiceArray = skillServiceArray;
	}
	
	/** @return String modifiedBy */
	public String getModifiedBy() {
		return modifiedBy;
	}

	/** @param modifiedBy */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
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
}