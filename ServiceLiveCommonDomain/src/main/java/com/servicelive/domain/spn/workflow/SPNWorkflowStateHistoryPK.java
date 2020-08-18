/**
 * 
 */
package com.servicelive.domain.spn.workflow;


import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * @author mrameez
 *
 */
@Embeddable
public class SPNWorkflowStateHistoryPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7428077710366820814L;

	@Column (name = "id", unique = true, insertable = true, updatable = true)
	private Integer id;	
	
	@Column (name = "entity_id", nullable = false)
	private Integer entityId;
	
	@Column (name="wf_entity_id", nullable = false)
	private String wfEntityId;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEntityId() {
		return entityId;
	}

	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}

	public String getWfEntityId() {
		return wfEntityId;
	}

	public void setWfEntityId(String wfEntityId) {
		this.wfEntityId = wfEntityId;
	}
	
	
}
