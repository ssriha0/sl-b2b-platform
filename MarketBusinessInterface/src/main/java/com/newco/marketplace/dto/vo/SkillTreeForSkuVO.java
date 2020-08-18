package com.newco.marketplace.dto.vo;
/* VO to map  all the information related to skill tree  on the basis of node id 
 * for the selected sku id
*  
* @author Infosys 
*
*
*/
import java.util.Date;

public class SkillTreeForSkuVO {
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
	public Integer getNodeId() {
		return nodeId;
	}
	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Integer getLeaf() {
		return leaf;
	}
	public void setLeaf(Integer leaf) {
		this.leaf = leaf;
	}
	public Byte getJobClassifier() {
		return jobClassifier;
	}
	public void setJobClassifier(Byte jobClassifier) {
		this.jobClassifier = jobClassifier;
	}
	public Byte getRoot() {
		return root;
	}
	public void setRoot(Byte root) {
		this.root = root;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getDescriptionContent() {
		return descriptionContent;
	}
	public void setDescriptionContent(String descriptionContent) {
		this.descriptionContent = descriptionContent;
	}
	public Double getMatchWeightFactor() {
		return matchWeightFactor;
	}
	public void setMatchWeightFactor(Double matchWeightFactor) {
		this.matchWeightFactor = matchWeightFactor;
	}
	public Integer getDepthLimit() {
		return depthLimit;
	}
	public void setDepthLimit(Integer depthLimit) {
		this.depthLimit = depthLimit;
	}
	public Integer getParentNode() {
		return parentNode;
	}
	public void setParentNode(Integer parentNode) {
		this.parentNode = parentNode;
	}
	public Integer getRootNodeId() {
		return rootNodeId;
	}
	public void setRootNodeId(Integer rootNodeId) {
		this.rootNodeId = rootNodeId;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Integer getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	

}
