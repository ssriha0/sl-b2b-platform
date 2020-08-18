package com.newco.marketplace.api.beans;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

public class Reschedule {
	@XStreamAlias("rescheduleType")
	private String rescheduleType;
	
	@XStreamAlias("rescheduleCreatedDateTime")
	private String rescheduleCreatedDateTime;
	
	@XStreamAlias("rescheduleServiceDateTime1")
	private String rescheduleServiceDateTime1;
	
	@XStreamAlias("rescheduleServiceDateTime2")
	private String rescheduleServiceDateTime2;
	
	@XStreamAlias("role")
	@XStreamAsAttribute() 
	private String role;
	
	@XStreamAlias("comment")
	private String comment;
	
	public String getRescheduledBy() {
		return role;
	}

	public void setRescheduledBy(String role) {
		this.role = role;
	}

	public String getRescheduleType() {
		return rescheduleType;
	}

	public void setRescheduleType(String rescheduleType) {
		this.rescheduleType = rescheduleType;
	}

	public String getRescheduleServiceDateTime1() {
		return rescheduleServiceDateTime1;
	}

	public void setRescheduleServiceDateTime1(String rescheduleServiceDateTime1) {
		this.rescheduleServiceDateTime1 = rescheduleServiceDateTime1;
	}

	public String getRescheduleServiceDateTime2() {
		return rescheduleServiceDateTime2;
	}

	public void setRescheduleServiceDateTime2(String rescheduleServiceDateTime2) {
		this.rescheduleServiceDateTime2 = rescheduleServiceDateTime2;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getRescheduleCreatedDateTime() {
		return rescheduleCreatedDateTime;
	}

	public void setRescheduleCreatedDateTime(String rescheduleCreatedDateTime) {
		this.rescheduleCreatedDateTime = rescheduleCreatedDateTime;
	}
}
