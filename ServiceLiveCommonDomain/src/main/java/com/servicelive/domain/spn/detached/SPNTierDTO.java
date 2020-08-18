package com.servicelive.domain.spn.detached;

public class SPNTierDTO {

	private Integer spnId;
	private Integer tierId;
	private Integer minutes = 0;
	private Integer hours = 0;
	private Integer days = 0;
	private Integer noOfMembers = 0;
	private Integer time = 0;
	private String unit;
	private String modifiedBy;
	
	public Integer getSpnId() {
		return spnId;
	}
	public void setSpnId(Integer spnId) {
		this.spnId = spnId;
	}
	public Integer getTierId() {
		return tierId;
	}
	public void setTierId(Integer tierId) {
		this.tierId = tierId;
	}
	public Integer getMinutes() {
		return minutes;
	}
	public void setMinutes(Integer minutes) {
		this.minutes = minutes;
	}
	public Integer getHours() {
		return hours;
	}
	public void setHours(Integer hours) {
		this.hours = hours;
	}
	public Integer getDays() {
		return days;
	}
	public void setDays(Integer days) {
		this.days = days;
	}
	public Integer getNoOfMembers() {
		return noOfMembers;
	}
	public void setNoOfMembers(Integer noOfMembers) {
		this.noOfMembers = noOfMembers;
	}
	public Integer getTime() {
		return time;
	}
	public void setTime(Integer time) {
		this.time = time;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	
	
}
