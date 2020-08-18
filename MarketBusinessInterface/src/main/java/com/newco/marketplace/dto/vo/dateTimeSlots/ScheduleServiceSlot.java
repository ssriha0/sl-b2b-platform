package com.newco.marketplace.dto.vo.dateTimeSlots;

import com.newco.marketplace.api.annotation.OptionalParam;
import com.newco.marketplace.api.beans.so.Reschedule;
import com.newco.marketplace.utils.TimezoneMapper;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing schedule information.
 * @author Infosys
 *
 */
@XStreamAlias("scheduleServiceSlot")
public class ScheduleServiceSlot {
	
	@XStreamAlias("scheduleType")
	private String scheduleType;
	
	/*@XStreamAlias("serviceDateTime1")
	private String serviceDateTime1;
	
	@XStreamAlias("serviceDateTime2")
	private String serviceDateTime2;*/
	
	@XStreamAlias("serviceDatetimeSlots")
	private ServiceDateTimeSlots serviceDatetimeSlots;
	
	@XStreamAlias("confirmWithCustomer")
	private String confirmWithCustomer;
	
	@XStreamAlias("reschedule")
	private Reschedule reschedule;
	
	@OptionalParam
	@XStreamAlias("serviceLocationTimezone") 
	private String serviceLocationTimezone;
	
	public String getScheduleType() {
		return scheduleType;
	}

	public void setScheduleType(String scheduleType) {
		this.scheduleType = scheduleType;
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

	public ServiceDateTimeSlots getServiceDatetimeSlots() {
		return serviceDatetimeSlots;
	}

	public void setServiceDatetimeSlots(ServiceDateTimeSlots serviceDatetimeSlots) {
		this.serviceDatetimeSlots = serviceDatetimeSlots;
	}

}
