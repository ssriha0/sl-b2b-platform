package com.newco.marketplace.dto.vo.incident;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.sears.os.vo.SerializableBaseVO;

public class IncidentResponseVO extends SerializableBaseVO {
	
	private static final long serialVersionUID = 2327227014282515466L;
	
	private String soId;
	private Integer incidentId;
	private String clientIncidentId;
	private Integer resourceId;
	private Integer roleId;
	private String action;
	private String subject;
	private String noteDescription;
	private Integer noteTypeId;
	private String createdByName;
	private String modifiedBy;
	private Integer entityId;
	private String privateInd;
	
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public Integer getIncidentId() {
		return incidentId;
	}
	public void setIncidentId(Integer incidentId) {
		this.incidentId = incidentId;
	}
	public String getClientIncidentId() {
		return clientIncidentId;
	}
	public void setClientIncidentId(String clientIncidentId) {
		this.clientIncidentId = clientIncidentId;
	}
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getNoteDescription() {
		return noteDescription;
	}
	public void setNoteDescription(String noteDescription) {
		this.noteDescription = noteDescription;
	}
	public Integer getNoteTypeId() {
		return noteTypeId;
	}
	public void setNoteTypeId(Integer noteTypeId) {
		this.noteTypeId = noteTypeId;
	}
	public String getCreatedByName() {
		return createdByName;
	}
	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Integer getEntityId() {
		return entityId;
	}
	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}
	public String getPrivateInd() {
		return privateInd;
	}
	public void setPrivateInd(String privateInd) {
		this.privateInd = privateInd;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
	}
	public String getAction()
	{
		return action;
	}
	public void setAction(String action)
	{
		this.action = action;
	}
}
