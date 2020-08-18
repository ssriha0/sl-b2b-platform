package com.newco.marketplace.dto.vo.alert;

public class TimedAlertVO extends AlertBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8812630115749650454L;
	public TimedAlertVO() {
		super();
		this.alertLevel = ALERT_TASK;
	}
/*
 * In a timed event we can set this up as a task as well as an outbound alert
 * however we cannot make it a non action alert, thats silly this is a lame way of implementaing 
 * that construct but as these are constants I cant in good faith do any comparisons
 */
	public void setAlertLevel(int inAlertLevel) {
		if (inAlertLevel == ALERT_ALERT) {
			this.alertLevel = ALERT_ALERT;
		} else {
			if (inAlertLevel == ALERT_TASK) {
				this.alertLevel = ALERT_TASK;
			}
		}
	}

}
