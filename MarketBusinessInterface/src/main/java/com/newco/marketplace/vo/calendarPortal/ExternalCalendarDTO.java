package com.newco.marketplace.vo.calendarPortal;

import java.io.Serializable;
import java.util.Date;

public class ExternalCalendarDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer firmId;
	private Integer personId;
	private String cronofyAccId;
	private String calendarId;
	private String emailId;
	private String calendarSource;
	private String access_token;
	private String refresh_token;
	private Date createdDate;
	private Date modifiedDate;
	private String createdBy;
	private String modifiedBy;
	private Boolean synced;
	
	public Integer getFirmId() {
		return firmId;
	}
	public void setFirmId(Integer firmId) {
		this.firmId = firmId;
	}
	public Integer getPersonId() {
		return personId;
	}
	public void setPersonId(Integer personId) {
		this.personId = personId;
	}
	public String getCalendarId() {
		return calendarId;
	}
	public void setCalendarId(String calendarId) {
		this.calendarId = calendarId;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getCalendarSource() {
		return calendarSource;
	}
	public void setCalendarSource(String calendarSource) {
		this.calendarSource = calendarSource;
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getRefresh_token() {
		return refresh_token;
	}
	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Boolean isSynced() {
		return synced;
	}
	public void setSynced(Boolean synced) {
		this.synced = synced;
	}
	public String getCronofyAccId() {
		return cronofyAccId;
	}
	public void setCronofyAccId(String cronofyAccId) {
		this.cronofyAccId = cronofyAccId;
	}
	@Override
	public String toString() {
		return "ExternalCalendarDTO [firmId=" + firmId + ", personId="
				+ personId + ", cronofyAccId=" + cronofyAccId + ", calendarId="
				+ calendarId + ", emailId=" + emailId + ", calendarSource="
				+ calendarSource + ", access_token=" + access_token
				+ ", refresh_token=" + refresh_token + ", createdDate="
				+ createdDate + ", modifiedDate=" + modifiedDate
				+ ", createdBy=" + createdBy + ", modifiedBy=" + modifiedBy
				+ ", synced=" + synced + "]";
	}
}
