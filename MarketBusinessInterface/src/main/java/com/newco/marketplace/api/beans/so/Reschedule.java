package com.newco.marketplace.api.beans.so;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * This is a generic bean class for storing schedule information.
 * @author Infosys
 *
 */
@XStreamAlias("reschedule")
public class Reschedule {
	
	@XStreamAlias("rescheduleType")
	private String rescheduleType;
	
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
	
	
}
