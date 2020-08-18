package com.newco.marketplace.dto.vo.skillTree;

import com.sears.os.vo.SerializableBaseVO;

/**
 * @author zizrale
 */
public class CheckedSkillsVO extends SerializableBaseVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7213592773177193809L;
	private long resourceId;
	private int nodeId;
	private int serviceTypeId;
	private int rootNodeId;
	
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
	/** @return int serviceTypeId */
	public int getServiceTypeId() {
		return serviceTypeId;
	}
	/** @param serviceTypeId */
	public void setServiceTypeId(int serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}
	/** @return int root node id */
	public int getRootNodeId() {
		return rootNodeId;
	}
	/** @param rootNodeId */
	public void setRootNodeId(int rootNodeId) {
		this.rootNodeId = rootNodeId;
	}
}
