package com.servicelive.esb.integration.domain;

import java.util.Date;

public class Lock {
	private String lockName;
	private boolean acquired;
	private String acquiredBy;
	private Date acquiredOn;
	
	public String getLockName() {
		return lockName;
	}
	public void setLockName(String lockName) {
		this.lockName = lockName;
	}
	public boolean isAcquired() {
		return acquired;
	}
	public void setAcquired(boolean acquired) {
		this.acquired = acquired;
	}
	public String getAcquiredBy() {
		return acquiredBy;
	}
	public void setAcquiredBy(String acquiredBy) {
		this.acquiredBy = acquiredBy;
	}
	public Date getAcquiredOn() {
		return acquiredOn;
	}
	public void setAcquiredOn(Date acquiredOn) {
		this.acquiredOn = acquiredOn;
	}
	
}
