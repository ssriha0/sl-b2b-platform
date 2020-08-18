/**
 * 
 */
package com.newco.marketplace.business.businessImpl.vibePostAPI;

/**
 * @author karthik_hariharan01
 * SL-18979 sms_alert_status insert POJO
 *
 */
public class VibeAlertStatusVO {
	private String phoneNo;
	private String soId;
	private String eventId;
	private String eventType;
	private String status;
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
