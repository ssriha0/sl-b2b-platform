package com.servicelive.marketplatform.provider.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.servicelive.marketplatform.common.domain.AbstractDomainEntity;

@Entity
@Table(name = "skill_tree")
public class SkillNode extends AbstractDomainEntity {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="node_id")
    private Long id;

	@Column(name="node_name")
	private String nodeName;
	
	@Column(name="parent_node")
	private Long parentNodeId;
	
	@Column(name="root_node_id")
	private Long rootNodeId;
	
    @Column(name= "level")
    private Integer level;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setParentNodeId(Long parentNodeId) {
		this.parentNodeId = parentNodeId;
	}

	public Long getParentNodeId() {
		return parentNodeId;
	}

	public void setRootNodeId(Long rootNodeId) {
		this.rootNodeId = rootNodeId;
	}

	public Long getRootNodeId() {
		return rootNodeId;
	}

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getLevel() {
        return level;
    }
}
