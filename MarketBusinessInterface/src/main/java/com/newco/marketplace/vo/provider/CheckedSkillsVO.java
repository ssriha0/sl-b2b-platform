package com.newco.marketplace.vo.provider;

import com.sears.os.vo.SerializableBaseVO;

/**
 * @author zizrale
 */
public class CheckedSkillsVO extends SerializableBaseVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4238585727024071485L;
	private long resourceId;
	private int nodeId;
	private String nodeName;
	private int serviceTypeId;
	private String serviceType;
	private int rootNodeId;
	private int level;
	
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
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
}
