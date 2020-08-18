package com.newco.marketplace.vo;

public class ServiceInfo {
	Technician technician;
	Attempt attempt;
	ThirdPartyInfo thirdPartyInfo;
	Remittance remittance;
	String promiseDate;
	Integer attemptCount;

	public ThirdPartyInfo getThirdPartyInfo() {
		return thirdPartyInfo;
	}

	public void setThirdPartyInfo(ThirdPartyInfo thirdPartyInfo) {
		this.thirdPartyInfo = thirdPartyInfo;
	}

	public Technician getTechnician() {
		return technician;
	}

	public void setTechnician(Technician technician) {
		this.technician = technician;
	}

	public Attempt getAttempt() {
		return attempt;
	}

	public void setAttempt(Attempt attempt) {
		this.attempt = attempt;
	}

	public Remittance getRemittance() {
		return remittance;
	}

	public void setRemittance(Remittance remittance) {
		this.remittance = remittance;
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

}
