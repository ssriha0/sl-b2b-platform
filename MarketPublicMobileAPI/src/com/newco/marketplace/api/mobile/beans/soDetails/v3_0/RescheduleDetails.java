package com.newco.marketplace.api.mobile.beans.soDetails.v3_0;

import java.sql.Timestamp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing Reschedule information.
 * @author Infosys
 *
 */

@XStreamAlias("rescheduleDetails")
@XmlAccessorType(XmlAccessType.FIELD)
public class RescheduleDetails {

	@XStreamAlias("rescheduleServiceDate1")
	private String rescheduleServiceDate1;
	
	@XStreamAlias("rescheduleServiceTime1")
	private String rescheduleServiceTime1;
	
	@XStreamAlias("rescheduleServiceDate2")
	private String rescheduleServiceDate2;
	
	@XStreamAlias("rescheduleServiceTime2")
	private String rescheduleServiceTime2;
	
	@XStreamAlias("timeZone")
	private String timeZone;
	
	@XStreamAlias("reasonCodeDescription")
	private String reasonCodeDescription;
	
	@XStreamAlias("rescheduleInitiatedBy")
	private String rescheduleInitiatedBy;
	
	@XStreamAlias("rescheduleInitiatedDate")
	private String rescheduleInitiatedDate;
	
	@XStreamAlias("roleId")
	private Integer roleId;

	

	public String getRescheduleServiceTime1() {
		return rescheduleServiceTime1;
	}

	public void setRescheduleServiceTime1(String rescheduleServiceTime1) {
		this.rescheduleServiceTime1 = rescheduleServiceTime1;
	}


	

	public String getRescheduleServiceDate1() {
		return rescheduleServiceDate1;
	}

	public void setRescheduleServiceDate1(String rescheduleServiceDate1) {
		this.rescheduleServiceDate1 = rescheduleServiceDate1;
	}

	public String getRescheduleServiceDate2() {
		return rescheduleServiceDate2;
	}

	public void setRescheduleServiceDate2(String rescheduleServiceDate2) {
		this.rescheduleServiceDate2 = rescheduleServiceDate2;
	}

	public String getRescheduleServiceTime2() {
		return rescheduleServiceTime2;
	}

	public void setRescheduleServiceTime2(String rescheduleServiceTime2) {
		this.rescheduleServiceTime2 = rescheduleServiceTime2;
	}

	public String getReasonCodeDescription() {
		return reasonCodeDescription;
	}

	public void setReasonCodeDescription(String reasonCodeDescription) {
		this.reasonCodeDescription = reasonCodeDescription;
	}

	public String getRescheduleInitiatedBy() {
		return rescheduleInitiatedBy;
	}

	public void setRescheduleInitiatedBy(String rescheduleInitiatedBy) {
		this.rescheduleInitiatedBy = rescheduleInitiatedBy;
	}

	public String getRescheduleInitiatedDate() {
		return rescheduleInitiatedDate;
	}

	public void setRescheduleInitiatedDate(String rescheduleInitiatedDate) {
		this.rescheduleInitiatedDate = rescheduleInitiatedDate;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	

	
	
}