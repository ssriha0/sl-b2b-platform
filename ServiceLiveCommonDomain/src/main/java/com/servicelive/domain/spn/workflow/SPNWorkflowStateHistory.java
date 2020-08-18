/**
 * 
 */
package com.servicelive.domain.spn.workflow;


import java.util.Date;


import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.servicelive.domain.LoggableBaseDomain;

/**
 * @author mrameez
 *
 */
@Entity
@Table (name = "spnet_workflow_status_history")
public class SPNWorkflowStateHistory extends LoggableBaseDomain {


	/**
	 * 
	 */
	private static final long serialVersionUID = 5556452242455018299L;


	@EmbeddedId
	private SPNWorkflowStateHistoryPK spnWorkflowHistoryPk; 


	@Column (name = "comments", length=150 , nullable = true)
	private String comments;
	
	@Column (name = "wf_entity_state", nullable = false)
	private String wfEntityState;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name= "archive_date", nullable = false)
	private Date archiveDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column (name = "effective_date", unique = false, nullable = true, insertable = true, updatable = true)
	private Date effectiveDate;

	public SPNWorkflowStateHistoryPK getSpnWorkflowHistoryPk() {
		return spnWorkflowHistoryPk;
	}

	public void setSpnWorkflowHistoryPk(
			SPNWorkflowStateHistoryPK spnWorkflowHistoryPk) {
		this.spnWorkflowHistoryPk = spnWorkflowHistoryPk;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getWfEntityState() {
		return wfEntityState;
	}

	public void setWfEntityState(String wfEntityState) {
		this.wfEntityState = wfEntityState;
	}

	public Date getArchiveDate() {
		return archiveDate;
	}

	public void setArchiveDate(Date archiveDate) {
		this.archiveDate = archiveDate;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	
}
