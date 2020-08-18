package com.newco.marketplace.vo;

public class Attempt {
	String arriveTime;
	String endTime;
	String transitTime;
	String techComments;
	Integer callCode;
	Technician technician;

	public String getTechComments() {
		return techComments;
	}

	public void setTechComments(String techComments) {
		this.techComments = techComments;
	}

	public Integer getCallCode() {
		return callCode;
	}

	public void setCallCode(Integer callCode) {
		this.callCode = callCode;
	}

	public String getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getTransitTime() {
		return transitTime;
	}

	public void setTransitTime(String transitTime) {
		this.transitTime = transitTime;
	}

	public Technician getTechnician() {
		return technician;
	}

	public void setTechnician(Technician technician) {
		this.technician = technician;
	}
	
}
