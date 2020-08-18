package com.servicelive.domain.lookup;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * LookupWFStates entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="wf_states" )


public class LookupWfStates implements java.io.Serializable{

	private static final long serialVersionUID = 20090203;

	@Id 
	@Column(name="wf_state_id", unique=true, nullable=false)
	private Integer id;

	@Column(name="wf_descr", nullable=true, length=100)
	private String description;

	@Column(name="wf_state", nullable=false, length=255)
	private String wfState;

	@Column(name="wf_entity", nullable=false, length=50)
	private String wfEntity;
	
	@Column(name="sort_order", nullable=true)
	private Integer sortOrder;
	
	@Column(name="audit_link_id", nullable=true)
	private Integer auditLinkId;

	/**
	 * @return the id
	 */
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
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the wfState
	 */
	public String getWfState() {
		return wfState;
	}

	/**
	 * @param wfState the wfState to set
	 */
	public void setWfState(String wfState) {
		this.wfState = wfState;
	}

	/**
	 * @return the wfEntity
	 */
	public String getWfEntity() {
		return wfEntity;
	}

	/**
	 * @param wfEntity the wfEntity to set
	 */
	public void setWfEntity(String wfEntity) {
		this.wfEntity = wfEntity;
	}

	/**
	 * @return the sortOrder
	 */
	public Integer getSortOrder() {
		return sortOrder;
	}

	/**
	 * @param sortOrder the sortOrder to set
	 */
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	/**
	 * @return the auditLinkId
	 */
	public Integer getAuditLinkId() {
		return auditLinkId;
	}

	/**
	 * @param auditLinkId the auditLinkId to set
	 */
	public void setAuditLinkId(Integer auditLinkId) {
		this.auditLinkId = auditLinkId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "LookupWfStates [auditLinkId=" + auditLinkId + ", description=" + description + ", id=" + id + ", sortOrder=" + sortOrder
				+ ", wfEntity=" + wfEntity + ", wfState=" + wfState + "]";
	}


}
