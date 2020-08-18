package com.newco.marketplace.dto.vo.skillTree;

import com.sears.os.vo.SerializableBaseVO;

/**
 * @author zizrale
 *
 */
public class SkillTreeRequestVO extends SerializableBaseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9085226637963899016L;
	private int rootNode;

	/**
	 * @return int rootNode
	 */
	public int getRootNode() {
		return rootNode;
	}

	/**
	 * @param rootNode
	 */
	public void setRootNode(int rootNode) {
		this.rootNode = rootNode;
	}
	
}
