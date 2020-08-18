package com.servicelive.domain.lookup;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="lu_spnet_status_action_mapper")
public class LookupSPNStatusActionMapper {

	//action_mapping_id	int(5)	NO	PRI	(null)
	@Id
	@Column ( name = "action_mapping_id", unique = true, insertable = false, length = 5, updatable = false , nullable = false)
	private Integer id;

	//modified_by_id	varchar(30)	NO	MUL	(null)
	@ManyToOne
	@JoinColumn(name="modified_by_id", unique = false, insertable = false, updatable = false, nullable = false)
	private LookupSPNStatusActionModifier lookupSPNStatusActionModifier;

	//spnet_status_id	varchar(30)	NO	MUL	(null)
	@ManyToOne
	@JoinColumn(name="spnet_status_id", unique = false, insertable = false, updatable = false, nullable = false)
	private LookupSPNWorkflowState lookupSPNWorkflowState;

	//descr	varchar(255)	YES		(null)
	@Column ( name = "descr", unique = false, insertable = false, length = 255, updatable = false , nullable = false)
	private String description;

	//action_descr	varchar(255)	YES
	@Column ( name = "action_descr", unique = false, insertable = false, length = 255, updatable = false , nullable = false)
	private String actionDescription;

	//displayable_comments	varchar(255)	YES
	@Column ( name = "displayable_comments", unique = false, insertable = false, length = 255, updatable = false , nullable = true)
	private String displayableComments;

	//action_type	varchar(255)	YES
	@Column ( name = "action_type", unique = false, insertable = false, length = 255, updatable = false , nullable = false)
	private String actionType;

	/**
	 * This is a helper method
	 * @param id
	 */
	public LookupSPNStatusActionMapper(Integer id) {
		this.id = id;
	}

	/**
	 * 
	 */
	public LookupSPNStatusActionMapper() {
		super();
	}

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
	 * @return the actionDescription
	 */
	public String getActionDescription() {
		return actionDescription;
	}

	/**
	 * @param actionDescription the actionDescription to set
	 */
	public void setActionDescription(String actionDescription) {
		this.actionDescription = actionDescription;
	}

	/**
	 * @return the displayableComments
	 */
	public String getDisplayableComments() {
		return displayableComments;
	}

	/**
	 * @param displayableComments the displayableComments to set
	 */
	public void setDisplayableComments(String displayableComments) {
		this.displayableComments = displayableComments;
	}

	/**
	 * @return the actionType
	 */
	public String getActionType() {
		return actionType;
	}

	/**
	 * @param actionType the actionType to set
	 */
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	/**
	 * @return the lookupSPNStatusActionModifier
	 */
	public LookupSPNStatusActionModifier getLookupSPNStatusActionModifier() {
		return lookupSPNStatusActionModifier;
	}

	/**
	 * @param lookupSPNStatusActionModifier the lookupSPNStatusActionModifier to set
	 */
	public void setLookupSPNStatusActionModifier(
			LookupSPNStatusActionModifier lookupSPNStatusActionModifier) {
		this.lookupSPNStatusActionModifier = lookupSPNStatusActionModifier;
	}

	/**
	 * @return the lookupSPNWorkflowState
	 */
	public LookupSPNWorkflowState getLookupSPNWorkflowState() {
		return lookupSPNWorkflowState;
	}

	/**
	 * @param lookupSPNWorkflowState the lookupSPNWorkflowState to set
	 */
	public void setLookupSPNWorkflowState(
			LookupSPNWorkflowState lookupSPNWorkflowState) {
		this.lookupSPNWorkflowState = lookupSPNWorkflowState;
	}
}
