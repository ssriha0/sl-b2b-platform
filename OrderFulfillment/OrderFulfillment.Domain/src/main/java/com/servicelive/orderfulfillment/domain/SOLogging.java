package com.servicelive.orderfulfillment.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "so_logging")
@XmlRootElement()
public class SOLogging extends SOChild {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3967694312002920299L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "so_logging_id")
	private Long soLoggingId;
		
	@Column(name = "action_id")
	private Integer actionId;
		
	@Column(name = "old_value")
	private String oldValue;
	
	@Column(name = "new_value")
	private String newValue;
	
	@Column(name = "chg_comment")
	private String chgComment;
	
	@Column(name = "created_by_name")
	private String createdByName;
	
	@Column(name = "role_id")
	private Integer roleId;
	
	@Column(name = "entity_id")
	private Long entityId;

	public Integer getActionId() {
		return actionId;
	}

	public String getChgComment() {
		return chgComment;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public Long getEntityId() {
		return entityId;
	}

	public String getNewValue() {
		return newValue;
	}

	public String getOldValue() {
		return oldValue;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public Long getSoLoggingId() {
		return soLoggingId;
	}

	@XmlElement()
	public void setActionId(Integer actionId) {
		this.actionId = actionId;
	}

	@XmlElement()
	public void setChgComment(String chgComment) {
		this.chgComment = chgComment;
	}

	@XmlElement()
	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	@XmlElement()
	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	@XmlElement()
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	@XmlElement()
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	@XmlElement()
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	@XmlElement()
	public void setSoLoggingId(Long soLoggingId) {
		this.soLoggingId = soLoggingId;
	}

    public SOLogging copy() {
        SOLogging copyCat = new SOLogging();
        copyCat.actionId = this.actionId;
        copyCat.chgComment = this.chgComment;
        copyCat.createdByName = this.createdByName;
        copyCat.entityId = this.entityId;
        copyCat.newValue = this.newValue;
        copyCat.oldValue = this.oldValue;
        copyCat.roleId = this.roleId;
        copyCat.setModifiedBy(this.getModifiedBy());
        return copyCat;  
    }
}
