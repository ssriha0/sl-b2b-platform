package com.servicelive.domain.lookup;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table (name = "lu_spnet_status_override_reasons")
public class LookupSPNStatusOverrideReason {
	
	//reason_id	int(5)	NO	PRI
	@Id
	@Column ( name = "reason_id", unique = true, insertable = false, length = 5, updatable = false , nullable = false)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="current_spnet_status_id", unique=true, nullable=false, updatable = false, insertable = false)
	private LookupSPNWorkflowState currentlookupSPNWorkflowState;
	
	//spnet_status_id	varchar(30)	NO	MUL
	@ManyToOne
	@JoinColumn(name="new_spnet_status_id", unique=true, nullable=false, updatable = false, insertable = false)
	private LookupSPNWorkflowState newlookupSPNWorkflowState;

	@Column ( name = "descr", length = 255, updatable = false, insertable = false, nullable = false)
	private String description;

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

	public LookupSPNWorkflowState getCurrentlookupSPNWorkflowState() {
		return currentlookupSPNWorkflowState;
	}

	public void setCurrentlookupSPNWorkflowState(
			LookupSPNWorkflowState currentlookupSPNWorkflowState) {
		this.currentlookupSPNWorkflowState = currentlookupSPNWorkflowState;
	}

	public LookupSPNWorkflowState getNewlookupSPNWorkflowState() {
		return newlookupSPNWorkflowState;
	}

	public void setNewlookupSPNWorkflowState(
			LookupSPNWorkflowState newlookupSPNWorkflowState) {
		this.newlookupSPNWorkflowState = newlookupSPNWorkflowState;
	}

}
