package com.newco.marketplace.dto.response.externalcalendar;

import java.io.Serializable;

public class CronofyEventResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private String calendar_id;
	private String event_uid;
	private String summary;
	private String description;
	private String start;
	private String end;
	private String deleted;
	private String created;
	private String updated;
	private String location;
	private String participation_status;
	private String status;
	private String recurring;

	public String getCalendar_id() {
		return calendar_id;
	}
	public void setCalendar_id(String calendar_id) {
		this.calendar_id = calendar_id;
	}
	public String getEvent_uid() {
		return event_uid;
	}
	public void setEvent_uid(String event_uid) {
		this.event_uid = event_uid;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getUpdated() {
		return updated;
	}
	public void setUpdated(String updated) {
		this.updated = updated;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getParticipation_status() {
		return participation_status;
	}
	public void setParticipation_status(String participation_status) {
		this.participation_status = participation_status;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRecurring() {
		return recurring;
	}
	public void setRecurring(String recurring) {
		this.recurring = recurring;
	}
}
