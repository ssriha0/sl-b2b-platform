package com.newco.marketplace.vo.provider;

import com.sears.os.vo.SerializableBaseVO;

/**
 * @author zizrale
 *
 */
public class SkillCompletionVO extends SerializableBaseVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1045127874127714872L;
	private long resourceId;
	private boolean complete;
	private int nodeId;
	private int rootNodeId;
	
	/**
	 * @return int node id
	 */
	public int getNodeId() {
		return nodeId;
	}
	/**
	 * @param nodeId
	 */
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
	/**
	 * @return int rootNode id
	 */
	public int getRootNodeId() {
		return rootNodeId;
	}
	/**
	 * @param rootNodeId
	 */
	public void setRootNodeId(int rootNodeId) {
		this.rootNodeId = rootNodeId;
	}
	/**
	 * @return boolean isComplete
	 */
	public boolean isComplete() {
		return complete;
	}
	/**
	 * @param complete
	 */
	public void setComplete(boolean complete) {
		this.complete = complete;
	}
	/**
	 * @return long resource id
	 */
	public long getResourceId() {
		return resourceId;
	}
	/**
	 * @param resourceId
	 */
	public void setResourceId(long resourceId) {
		this.resourceId = resourceId;
	}
}
