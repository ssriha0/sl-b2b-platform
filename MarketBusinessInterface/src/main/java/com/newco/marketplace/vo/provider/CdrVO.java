package com.newco.marketplace.vo.provider;


public class CdrVO {

	private Integer id;
	private String datetime;
	private Integer lead_id;
	private Integer employee_user_id;
	private Integer lead_status_id;
	private String lead_status;
	private Integer duration;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public Integer getLead_id() {
		return lead_id;
	}
	public void setLead_id(Integer lead_id) {
		this.lead_id = lead_id;
	}
	public Integer getEmployee_user_id() {
		return employee_user_id;
	}
	public void setEmployee_user_id(Integer employee_user_id) {
		this.employee_user_id = employee_user_id;
	}
	public Integer getLead_status_id() {
		return lead_status_id;
	}
	public void setLead_status_id(Integer lead_status_id) {
		this.lead_status_id = lead_status_id;
	}
	public String getLead_status() {
		return lead_status;
	}
	public void setLead_status(String lead_status) {
		this.lead_status = lead_status;
	}
	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
}
