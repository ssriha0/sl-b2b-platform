/**
 * 
 */
package com.servicelive.domain.spn.workflow;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

import com.servicelive.domain.LoggableBaseDomain;
import com.servicelive.domain.lookup.LookupSPNWorkflowState;

/**
 * @author hoza
 *
 */
@Entity
@Table (name = "spnet_workflow_status")
public class SPNWorkflowState extends LoggableBaseDomain {


	/**
	 * 
	 */
	private static final long serialVersionUID = 20090203L;

	@ManyToOne(cascade = {CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "wf_entity_state", unique = false, nullable = false, insertable = true, updatable = true)
	@ForeignKey(name="FK_WF_ENTITY_STATE_ID")
	private LookupSPNWorkflowState workflowState;

	@EmbeddedId
	private SPNWorkflowStatePK spnWorkflowPk; 


	@Column (name = "comments", length=150 , nullable = true)
	private String comments;

	/**
	 * @return the spnWorkflowPk
	 */
	public SPNWorkflowStatePK getSpnWorkflowPk() {
		return spnWorkflowPk;
	}

	/**
	 * @param spnWorkflowPk the spnWorkflowPk to set
	 */
	public void setSpnWorkflowPk(SPNWorkflowStatePK spnWorkflowPk) {
		this.spnWorkflowPk = spnWorkflowPk;
	}

	/**
	 * @return the workflowState
	 */
	public LookupSPNWorkflowState getWorkflowState() {
		return workflowState;
	}

	/**
	 * @param workflowState the workflowState to set
	 */
	public void setWorkflowState(LookupSPNWorkflowState workflowState) {
		this.workflowState = workflowState;
	}



	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}
}
