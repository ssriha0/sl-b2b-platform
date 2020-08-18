package com.newco.marketplace.translator.dao;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * SkillTree entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "skill_tree",  uniqueConstraints = {})
public class SkillTree extends AbstractSkillTree implements
		java.io.Serializable {

	// Constructors

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** default constructor */
	public SkillTree() {
		// intentionally blank
	}

	/** minimal constructor */
	public SkillTree(Integer nodeId, String nodeName, String modifiedBy) {
		super(nodeId, nodeName, modifiedBy);
	}

	/** full constructor */
	public SkillTree(Integer nodeId, Integer level, Integer leaf,
			Byte jobClassifier, Byte root, String nodeName,
			String descriptionContent, Double matchWeightFactor,
			Integer depthLimit, Integer parentNode, Integer rootNodeId, String modifiedBy,
			Integer sortOrder, Date createdDate, Date modifiedDate) {
		super(nodeId, level, leaf, jobClassifier, root, nodeName,
				descriptionContent, matchWeightFactor, depthLimit, parentNode,
				rootNodeId, modifiedBy, sortOrder, createdDate, modifiedDate);
	}

}
