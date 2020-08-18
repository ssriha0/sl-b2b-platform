/**
 *
 */
package com.servicelive.domain.lookup;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.servicelive.domain.AbstractLookupDomain;

/**
 * @author hoza
 *
 */

@Entity
@Table (name = "skill_tree")
public class LookupSkills extends AbstractLookupDomain {
	/**
	 *
	 */
	private static final long serialVersionUID = -1786008551887508519L;

	@Id
	@Column(name= "node_id")
	private Integer id;

	@Column(name= "node_name")
	private String description;

	@Column(name= "level")
	private Integer level;

	@Column(name= "leaf")
	private Boolean isLeaf;

	@Column(name= "root")
	private Boolean isRootNode;

	@Column(name= "description_content")
	private String descriptionContent;

	@Column(name= "match_weight_factor")
	private Float matchWeightFactor;

	@Column(name= "depth_limit")
	private Integer depthLimit;

	@Column(name= "root_node_id")
	private Integer rootNodeId;

	@Column(name= "parent_node")
	private Integer parentNodeId;

	@Column(name= "modified_by")
	private String modifiedBy;



	 @OneToMany(mappedBy="skillNodeId",targetEntity=LookupServiceType.class,
		       fetch=FetchType.EAGER)
	private List<LookupServiceType> serviceTypes ;





	/**
	 * @return the level
	 */
	public Integer getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}

	/**
	 * @return the isLeaf
	 */
	public Boolean getIsLeaf() {
		return isLeaf;
	}

	/**
	 * @param isLeaf the isLeaf to set
	 */
	public void setIsLeaf(Boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	/**
	 * @return the isRootNode
	 */
	public Boolean getIsRootNode() {
		return isRootNode;
	}

	/**
	 * @param isRootNode the isRootNode to set
	 */
	public void setIsRootNode(Boolean isRootNode) {
		this.isRootNode = isRootNode;
	}

	/**
	 * @return the descriptionContent
	 */
	public String getDescriptionContent() {
		return descriptionContent;
	}

	/**
	 * @param descriptionContent the descriptionContent to set
	 */
	public void setDescriptionContent(String descriptionContent) {
		this.descriptionContent = descriptionContent;
	}

	/**
	 * @return the matchWeightFactor
	 */
	public Float getMatchWeightFactor() {
		return matchWeightFactor;
	}

	/**
	 * @param matchWeightFactor the matchWeightFactor to set
	 */
	public void setMatchWeightFactor(Float matchWeightFactor) {
		this.matchWeightFactor = matchWeightFactor;
	}

	/**
	 * @return the depthLimit
	 */
	public Integer getDepthLimit() {
		return depthLimit;
	}

	/**
	 * @param depthLimit the depthLimit to set
	 */
	public void setDepthLimit(Integer depthLimit) {
		this.depthLimit = depthLimit;
	}

	/**
	 * @return the rootNodeId
	 */
	public Integer getRootNodeId() {
		return rootNodeId;
	}

	/**
	 * @param rootNodeId the rootNodeId to set
	 */
	public void setRootNodeId(Integer rootNodeId) {
		this.rootNodeId = rootNodeId;
	}

	/**
	 * @return the parentNodeId
	 */
	public Integer getParentNodeId() {
		return parentNodeId;
	}

	/**
	 * @param parentNodeId the parentNodeId to set
	 */
	public void setParentNodeId(Integer parentNodeId) {
		this.parentNodeId = parentNodeId;
	}

	/**
	 * @return the modifiedBy
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * @return the serviceTypes
	 */


	public List<LookupServiceType> getServiceTypes() {
		return serviceTypes;
	}

	/**
	 * @param serviceTypes the serviceTypes to set
	 */
	public void setServiceTypes(List<LookupServiceType> serviceTypes) {
		this.serviceTypes = serviceTypes;
	}

	/**
	 * @return the id
	 */
	@Override
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the description
	 */
	@Override
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}



}
