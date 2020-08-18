/**
 * 
 */
package com.servicelive.domain.spn.workflow;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ForeignKey;

import com.servicelive.domain.lookup.LookupSPNWorkflowEntity;

/**
 * @author hoza
 *
 */
@Embeddable
public class SPNWorkflowStatePK implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4894960318536069028L;


	@ManyToOne(cascade = {CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "wf_entity_id", unique = false, nullable = false, insertable = true, updatable = true)
	@ForeignKey(name="FK_WF_ENTITY_ID")
	private LookupSPNWorkflowEntity workflowEntity;
	
	
	@Column (name = "entity_id")
	private Integer entityId;
	
	/**
	 * @return the workflowEntity
	 */
	public LookupSPNWorkflowEntity getWorkflowEntity() {
		return workflowEntity;
	}

	/**
	 * @param workflowEntity the workflowEntity to set
	 */
	public void setWorkflowEntity(LookupSPNWorkflowEntity workflowEntity) {
		this.workflowEntity = workflowEntity;
	}
	
	/**
	 * @return the entityId
	 */
	public Integer getEntityId() {
		return entityId;
	}

	/**
	 * @param entityId the entityId to set
	 */
	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((entityId == null) ? 0 : entityId.hashCode());
		result = prime * result
				+ ((workflowEntity == null) ? 0 : workflowEntity.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final SPNWorkflowStatePK other = (SPNWorkflowStatePK) obj;
		if (entityId == null) {
			if (other.entityId != null)
				return false;
		} else if (!entityId.equals(other.entityId))
			return false;
		if (workflowEntity == null) {
			if (other.workflowEntity != null)
				return false;
		} else if (!workflowEntity.equals(other.workflowEntity))
			return false;
		return true;
	}

}
