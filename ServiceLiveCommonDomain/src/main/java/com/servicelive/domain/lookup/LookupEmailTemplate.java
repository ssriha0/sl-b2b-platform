package com.servicelive.domain.lookup;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

import com.servicelive.domain.BaseDomain;

/**
 * 
 * @author svanloon
 *
 */
@Entity
@Table(name="spnet_email_template")
public class LookupEmailTemplate extends BaseDomain implements java.io.Serializable {

	private static final long serialVersionUID = 20081215L;

	@Id
	@Column(name="template_id", unique=true, nullable=false, length=10)
    private Integer templateId;

	@Column(name="template_name", nullable=false, length=255)
    private String templateName;

	@Column(name="template_subject", nullable=false, length=255)
    private String templateSubject;

	@Column(name="template_source", nullable=false, length=4000)
    private String templateSource;

	@Column(name="template_from", nullable=true, length=255)
    private String templateFrom;

	@Column(name="template_to", nullable=true, length=255)
    private String templateTo;

	@Column(name="priority", nullable=false, length=5)
    private Integer priority;

	@Column(name="eid", nullable=true, length=10)
    private Integer eid;

	@Column(name="action_cd", nullable=true, length=50)
    private String action;

	@ManyToOne(cascade = {}, fetch = FetchType.EAGER)
	@JoinColumn(name = "wf_entity_state", unique = false, nullable = false, insertable = true, updatable = true)
	@ForeignKey(name="FK_EMAIL_WF_ENTITY_STATE_ID")
	private LookupSPNWorkflowState workflowState;

	/**
	 * 
	 */
	public LookupEmailTemplate() {
		super();
	}

	/**
	 * @return the templateId
	 */
	public Integer getTemplateId() {
		return templateId;
	}

	/**
	 * @param templateId the templateId to set
	 */
	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	/**
	 * @return the templateName
	 */
	public String getTemplateName() {
		return templateName;
	}

	/**
	 * @param templateName the templateName to set
	 */
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	/**
	 * @return the templateSubject
	 */
	public String getTemplateSubject() {
		return templateSubject;
	}

	/**
	 * @param templateSubject the templateSubject to set
	 */
	public void setTemplateSubject(String templateSubject) {
		this.templateSubject = templateSubject;
	}

	/**
	 * @return the templateSource
	 */
	public String getTemplateSource() {
		return templateSource;
	}

	/**
	 * @param templateSource the templateSource to set
	 */
	public void setTemplateSource(String templateSource) {
		this.templateSource = templateSource;
	}

	/**
	 * @return the templateFrom
	 */
	public String getTemplateFrom() {
		return templateFrom;
	}

	/**
	 * @param templateFrom the templateFrom to set
	 */
	public void setTemplateFrom(String templateFrom) {
		this.templateFrom = templateFrom;
	}

	/**
	 * @return the priority
	 */
	public Integer getPriority() {
		return priority;
	}

	/**
	 * @param priority the priority to set
	 */
	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	/**
	 * @return the eid
	 */
	public Integer getEid() {
		return eid;
	}

	/**
	 * @param eid the eid to set
	 */
	public void setEid(Integer eid) {
		this.eid = eid;
	}

	/**
	 * @return the templateTo
	 */
	public String getTemplateTo() {
		return templateTo;
	}

	/**
	 * @param templateTo the templateTo to set
	 */
	public void setTemplateTo(String templateTo) {
		this.templateTo = templateTo;
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
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}
}
