package com.newco.marketplace.vo;

public class ServiceInfoReschedule {
	AttemptReschedule attempt;
	String rescheduleFlag;
	String requestedStartTime;
	String requestedDate;
	Integer attemptCount;
	String promiseDate;
	Technician technician;
	String requestedEndTime;
	Remittance remittance;

	public AttemptReschedule getAttempt() {
		return attempt;
	}

	public void setAttempt(AttemptReschedule attempt) {
		this.attempt = attempt;
	}

	public String getRescheduleFlag() {
		return rescheduleFlag;
	}

	public void setRescheduleFlag(String rescheduleFlag) {
		this.rescheduleFlag = rescheduleFlag;
	}

	public String getRequestedStartTime() {
		return requestedStartTime;
	}

	public void setRequestedStartTime(String requestedStartTime) {
		this.requestedStartTime = requestedStartTime;
	}

	public String getRequestedDate() {
		return requestedDate;
	}

	public void setRequestedDate(String requestedDate) {
		this.requestedDate = requestedDate;
	}

	public Integer getAttemptCount() {
		return attemptCount;
	}

	public void setAttemptCount(Integer attemptCount) {
		this.attemptCount = attemptCount;
	}

	public String getPromiseDate() {
		return promiseDate;
	}

	public void setPromiseDate(String promiseDate) {
		this.promiseDate = promiseDate;
	}

	public Technician getTechnician() {
		return technician;
	}

	public void setTechnician(Technician technician) {
		this.technician = technician;
	}

	public String getRequestedEndTime() {
		return requestedEndTime;
	}

	public void setRequestedEndTime(String requestedEndTime) {
		this.requestedEndTime = requestedEndTime;
	}

	public Remittance getRemittance() {
		return remittance;
	}

	public void setRemittance(Remittance remittance) {
		this.remittance = remittance;
	}
	
}
