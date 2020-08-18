package com.newco.marketplace.business.businessImpl.skillTree;

import java.util.List;

/**
 * @author zizrale
 *
 */
public class SearchResult {
//	private SkillNode treeBranch[];
	private List treeBranch;

	/**
	 * @return List of all the nodes in a branch
	 */
	public List getTreeBranch() {
		return treeBranch;
	}

	/**
	 * @param treeBranch
	 */
	public void setTreeBranch(List treeBranch) {
		this.treeBranch = treeBranch;
	}
	

}
