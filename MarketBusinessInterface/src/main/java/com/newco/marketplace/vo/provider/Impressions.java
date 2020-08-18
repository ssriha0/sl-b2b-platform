package com.newco.marketplace.vo.provider;

import java.sql.Timestamp;

public class Impressions {
	
	private Integer impression_id;
	private String lead_id;
	private String contactt_id;
	private Timestamp time_stamp;
	private Integer employee_id;
	private String called_lead_contact;
	private String type;
	private String source;
	private Integer task_type_id;
	private Integer call_duration;
	private Integer total_talk_time;
	private Integer total_ring_time;
	private Integer dial_attempt;	
	private String phone;
	private Integer dialer_initiative_id;
	private String dialer_initiative_name;
	private String call_date_time;
	private String call_subject;
	private String call_description;
	private String call_result;
	private String call_type;
	private String recording_url;
	private Integer cdr_id;
	private Integer impression_type_id;
	private Integer task_id;
	
	
	public Impressions(Integer impression_id, String lead_id,
			String contactt_id, Timestamp time_stamp, Integer employee_id,
			String called_lead_contact, String type, String source,
			Integer task_type_id, Integer call_duration,
			Integer total_talk_time, Integer total_ring_time,
			Integer dial_attempt, String phone, Integer dialer_initiative_id,
			String dialer_initiative_name, String call_date_time,
			String call_subject, String call_description, String call_result,
			String call_type, String recording_url, Integer cdr_id,
			Integer impression_type_id, Integer task_id) {
		super();
		this.impression_id = impression_id;
		this.lead_id = lead_id;
		this.contactt_id = contactt_id;
		this.time_stamp = time_stamp;
		this.employee_id = employee_id;
		this.called_lead_contact = called_lead_contact;
		this.type = type;
		this.source = source;
		this.task_type_id = task_type_id;
		this.call_duration = call_duration;
		this.total_talk_time = total_talk_time;
		this.total_ring_time = total_ring_time;
		this.dial_attempt = dial_attempt;
		this.phone = phone;
		this.dialer_initiative_id = dialer_initiative_id;
		this.dialer_initiative_name = dialer_initiative_name;
		this.call_date_time = call_date_time;
		this.call_subject = call_subject;
		this.call_description = call_description;
		this.call_result = call_result;
		this.call_type = call_type;
		this.recording_url = recording_url;
		this.cdr_id = cdr_id;
		this.impression_type_id = impression_type_id;
		this.task_id = task_id;
	}
	public Integer getImpression_id() {
		return impression_id;
	}
	public void setImpression_id(Integer impression_id) {
		this.impression_id = impression_id;
	}
	public String getLead_id() {
		return lead_id;
	}
	public void setLead_id(String lead_id) {
		this.lead_id = lead_id;
	}
	public String getContactt_id() {
		return contactt_id;
	}
	public void setContactt_id(String contactt_id) {
		this.contactt_id = contactt_id;
	}	
	public Timestamp getTime_stamp() {
		return time_stamp;
	}
	public void setTime_stamp(Timestamp time_stamp) {
		this.time_stamp = time_stamp;
	}
	public Integer getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(Integer employee_id) {
		this.employee_id = employee_id;
	}
	public String getCalled_lead_contact() {
		return called_lead_contact;
	}
	public void setCalled_lead_contact(String called_lead_contact) {
		this.called_lead_contact = called_lead_contact;
	}	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Integer getTask_type_id() {
		return task_type_id;
	}
	public void setTask_type_id(Integer task_type_id) {
		this.task_type_id = task_type_id;
	}
	public Integer getCall_duration() {
		return call_duration;
	}
	public void setCall_duration(Integer call_duration) {
		this.call_duration = call_duration;
	}
	public Integer getTotal_talk_time() {
		return total_talk_time;
	}
	public void setTotal_talk_time(Integer total_talk_time) {
		this.total_talk_time = total_talk_time;
	}
	public Integer getTotal_ring_time() {
		return total_ring_time;
	}
	public void setTotal_ring_time(Integer total_ring_time) {
		this.total_ring_time = total_ring_time;
	}
	public Integer getDial_attempt() {
		return dial_attempt;
	}
	public void setDial_attempt(Integer dial_attempt) {
		this.dial_attempt = dial_attempt;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getDialer_initiative_id() {
		return dialer_initiative_id;
	}
	public void setDialer_initiative_id(Integer dialer_initiative_id) {
		this.dialer_initiative_id = dialer_initiative_id;
	}
	public String getDialer_initiative_name() {
		return dialer_initiative_name;
	}
	public void setDialer_initiative_name(String dialer_initiative_name) {
		this.dialer_initiative_name = dialer_initiative_name;
	}
	public String getCall_date_time() {
		return call_date_time;
	}
	public void setCall_date_time(String call_date_time) {
		this.call_date_time = call_date_time;
	}
	public String getCall_subject() {
		return call_subject;
	}
	public void setCall_subject(String call_subject) {
		this.call_subject = call_subject;
	}
	public String getCall_description() {
		return call_description;
	}
	public void setCall_description(String call_description) {
		this.call_description = call_description;
	}
	public String getCall_result() {
		return call_result;
	}
	public void setCall_result(String call_result) {
		this.call_result = call_result;
	}
	public String getCall_type() {
		return call_type;
	}
	public void setCall_type(String call_type) {
		this.call_type = call_type;
	}
	public String getRecording_url() {
		return recording_url;
	}
	public void setRecording_url(String recording_url) {
		this.recording_url = recording_url;
	}
	public Integer getCdr_id() {
		return cdr_id;
	}
	public void setCdr_id(Integer cdr_id) {
		this.cdr_id = cdr_id;
	}
	public Integer getImpression_type_id() {
		return impression_type_id;
	}
	public void setImpression_type_id(Integer impression_type_id) {
		this.impression_type_id = impression_type_id;
	}
	public Integer getTask_id() {
		return task_id;
	}
	public void setTask_id(Integer task_id) {
		this.task_id = task_id;
	}
}
