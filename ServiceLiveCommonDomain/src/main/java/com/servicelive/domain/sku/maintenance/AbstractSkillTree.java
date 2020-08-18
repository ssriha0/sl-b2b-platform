package com.servicelive.domain.sku.maintenance;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * AbstractSkillTree entity provides the base persistence definition of the
 * SkillTree entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractSkillTree implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -6718508834986698889L;
	private Integer nodeId;
	private Integer level;
	private Integer leaf;
	private Byte jobClassifier;
	private Byte root;
	private String nodeName;
	private String descriptionContent;
	private Double matchWeightFactor;
	private Integer depthLimit;
	private Integer parentNode;
	private Integer rootNodeId;
	private String modifiedBy;
	private Integer sortOrder;
	private Date createdDate;
	private Date modifiedDate;

	// Constructors

	/** default constructor */
	public AbstractSkillTree() {
		// intentionally blank
	}

	/** minimal constructor */
	public AbstractSkillTree(Integer nodeId, String nodeName, String modifiedBy) {
		this.nodeId = nodeId;
		this.nodeName = nodeName;
		this.modifiedBy = modifiedBy;
	}

	/** full constructor */
	public AbstractSkillTree(Integer nodeId, Integer level, Integer leaf,
			Byte jobClassifier, Byte root, String nodeName,
			String descriptionContent, Double matchWeightFactor,
			Integer depthLimit, Integer parentNode, Integer rootNodeId, String modifiedBy,
			Integer sortOrder, Date createdDate, Date modifiedDate) {
		this.nodeId = nodeId;
		this.level = level;
		this.leaf = leaf;
		this.jobClassifier = jobClassifier;
		this.root = root;
		this.nodeName = nodeName;
		this.descriptionContent = descriptionContent;
		this.matchWeightFactor = matchWeightFactor;
		this.depthLimit = depthLimit;
		this.parentNode = parentNode;
		this.rootNodeId = rootNodeId;
		this.modifiedBy = modifiedBy;
		this.sortOrder = sortOrder;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
	}

	// Property accessors
	@Id
	@Column(name = "node_id", unique = true, nullable = false, insertable = true, updatable = true)
	public Integer getNodeId() {
		return this.nodeId;
	}

	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}

	@Column(name = "level", unique = false, nullable = true, insertable = true, updatable = true)
	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Column(name = "leaf", unique = false, nullable = true, insertable = true, updatable = true)
	public Integer getLeaf() {
		return this.leaf;
	}

	public void setLeaf(Integer leaf) {
		this.leaf = leaf;
	}

	@Column(name = "job_classifier", unique = false, nullable = true, insertable = true, updatable = true)
	public Byte getJobClassifier() {
		return this.jobClassifier;
	}

	public void setJobClassifier(Byte jobClassifier) {
		this.jobClassifier = jobClassifier;
	}

	@Column(name = "root", unique = false, nullable = true, insertable = true, updatable = true)
	public Byte getRoot() {
		return this.root;
	}

	public void setRoot(Byte root) {
		this.root = root;
	}

	@Column(name = "node_name", unique = false, nullable = false, insertable = true, updatable = true, length = 50)
	public String getNodeName() {
		return this.nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	@Column(name = "description_content", unique = false, nullable = true, insertable = true, updatable = true)
	public String getDescriptionContent() {
		return this.descriptionContent;
	}

	public void setDescriptionContent(String descriptionContent) {
		this.descriptionContent = descriptionContent;
	}

	@Column(name = "match_weight_factor", unique = false, nullable = true, insertable = true, updatable = true, precision = 10, scale = 3)
	public Double getMatchWeightFactor() {
		return this.matchWeightFactor;
	}

	public void setMatchWeightFactor(Double matchWeightFactor) {
		this.matchWeightFactor = matchWeightFactor;
	}

	@Column(name = "depth_limit", unique = false, nullable = true, insertable = true, updatable = true)
	public Integer getDepthLimit() {
		return this.depthLimit;
	}

	public void setDepthLimit(Integer depthLimit) {
		this.depthLimit = depthLimit;
	}

	@Column(name = "parent_node", unique = false, nullable = true, insertable = true, updatable = true)
	public Integer getParentNode() {
		return this.parentNode;
	}

	public void setParentNode(Integer parentNode) {
		this.parentNode = parentNode;
	}
	
	/**
	 * @return the rootNodeId
	 */
	@Column(name = "root_node_id", unique = false, nullable = true, insertable = true, updatable = true)
	public Integer getRootNodeId() {
		return rootNodeId;
	}

	/**
	 * @param rootNodeId the rootNodeId to set
	 */
	public void setRootNodeId(Integer rootNodeId) {
		this.rootNodeId = rootNodeId;
	}

	@Column(name = "modified_by", unique = false, nullable = false, insertable = true, updatable = true, length = 30)
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "sort_order", unique = false, nullable = true, insertable = true, updatable = true)
	public Integer getSortOrder() {
		return this.sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "created_date", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "modified_date", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	

}