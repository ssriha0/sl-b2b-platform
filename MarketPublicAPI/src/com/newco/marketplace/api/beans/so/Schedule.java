package com.newco.marketplace.api.beans.so;

import com.newco.marketplace.api.annotation.OptionalParam;
import com.newco.marketplace.utils.TimezoneMapper;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing schedule information.
 * @author Infosys
 *
 */
@XStreamAlias("schedule")
public class Schedule {
	
	@XStreamAlias("scheduleType")
	private String scheduleType;
	
	@XStreamAlias("serviceDateTime1")
	private String serviceDateTime1;
	
	@XStreamAlias("serviceDateTime2")
	private String serviceDateTime2;
	
	@XStreamAlias("confirmWithCustomer")
	private String confirmWithCustomer;
	
	@XStreamAlias("reschedule")
	private Reschedule reschedule;
	
	@OptionalParam
	@XStreamAlias("serviceLocationTimezone") 
	private String serviceLocationTimezone;
	
	@OptionalParam
	@XStreamAlias("serviceDateTimezoneOffset") 
	private String serviceDateTimezoneOffset;
	
	public String getScheduleType() {
		return scheduleType;
	}

	public void setScheduleType(String scheduleType) {
		this.scheduleType = scheduleType;
	}

	public String getServiceDateTime1() {
		return serviceDateTime1;
	}

	public void setServiceDateTime1(String serviceDateTime1) {
		this.serviceDateTime1 = serviceDateTime1;
	}

	public String getServiceDateTime2() {
		return serviceDateTime2;
	}

	public void setServiceDateTime2(String serviceDateTime2) {
		this.serviceDateTime2 = serviceDateTime2;
	}

	public String getConfirmWithCustomer() {
		return confirmWithCustomer;
	}

	public void setConfirmWithCustomer(String confirmWithCustomer) {
		this.confirmWithCustomer = confirmWithCustomer;
	}

	public Reschedule getReschedule() {
		return reschedule;
	}

	public void setReschedule(Reschedule reschedule) {
		this.reschedule = reschedule;
	}

	public String getServiceLocationTimezone() {
		return serviceLocationTimezone;
	}

	public void setServiceLocationTimezone(String serviceLocationTimezone) {
		this.serviceLocationTimezone = serviceLocationTimezone;
	}

	public String getServiceDateTimezoneOffset() {
		return serviceDateTimezoneOffset;
	}

	public void setServiceDateTimezoneOffset(String serviceDateTimezoneOffset) {
		this.serviceDateTimezoneOffset = serviceDateTimezoneOffset;
	}

}
