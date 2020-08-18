package com.newco.marketplace.api.beans.so.update;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("schedule")
public class SOSchedule {

	@XStreamAlias("scheduleType")
	private String scheduleType;
	
	@XStreamAlias("serviceDateTime1")
	private String serviceDateTime1;
	
	@XStreamAlias("serviceDateTime2")
	private String serviceDateTime2;
	
	
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
}
