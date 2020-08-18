/**
 * 
 */
package com.newco.marketplace.dto.vo.systemgeneratedemail;

import java.sql.Timestamp;

/**
 * @author rkumari
 *
 */
public class SOScheduleDetailsVO {
	private Timestamp serviceStartDate;
	private String serviceStartTime;
	private Timestamp serviceEndDate;
	private String  serviceEndTime;
	
	public Timestamp getServiceStartDate() {
		return serviceStartDate;
	}
	public void setServiceStartDate(Timestamp serviceStartDate) {
		this.serviceStartDate = serviceStartDate;
	}
	public String getServiceStartTime() {
		return serviceStartTime;
	}
	public void setServiceStartTime(String serviceStartTime) {
		this.serviceStartTime = serviceStartTime;
	}
	public Timestamp getServiceEndDate() {
		return serviceEndDate;
	}
	public void setServiceEndDate(Timestamp serviceEndDate) {
		this.serviceEndDate = serviceEndDate;
	}
	public String getServiceEndTime() {
		return serviceEndTime;
	}
	public void setServiceEndTime(String serviceEndTime) {
		this.serviceEndTime = serviceEndTime;
	}

}
