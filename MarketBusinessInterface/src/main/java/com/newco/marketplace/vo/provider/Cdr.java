package com.newco.marketplace.vo.provider;


public class Cdr {

	private Integer cdr_id;
	private Integer impression_id;
	private String lead_id;
	private String contact_id;
	private String ani;
	private String dnis;
	private String call_time;
	private double bill_duration;
	private double bill;
	private Integer employee_id;
	private String called_lead_contact;
	private String origin;
	private String call_leg;
	private String call_origin;
	
	public Cdr(Integer cdr_id, Integer impression_id, String lead_id,
			String contact_id, String ani, String dnis, String call_time,
			double bill_duration, double bill, Integer employee_id,
			String called_lead_contact, String origin, String call_leg,
			String call_origin) {
		super();
		this.cdr_id = cdr_id;
		this.impression_id = impression_id;
		this.lead_id = lead_id;
		this.contact_id = contact_id;
		this.ani = ani;
		this.dnis = dnis;
		this.call_time = call_time;
		this.bill_duration = bill_duration;
		this.bill = bill;
		this.employee_id = employee_id;
		this.called_lead_contact = called_lead_contact;
		this.origin = origin;
		this.call_leg = call_leg;
		this.call_origin = call_origin;
	}
	public Integer getCdr_id() {
		return cdr_id;
	}
	public void setCdr_id(Integer cdr_id) {
		this.cdr_id = cdr_id;
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
	public String getContact_id() {
		return contact_id;
	}
	public void setContact_id(String contact_id) {
		this.contact_id = contact_id;
	}
	public String getAni() {
		return ani;
	}
	public void setAni(String ani) {
		this.ani = ani;
	}
	public String getDnis() {
		return dnis;
	}
	public void setDnis(String dnis) {
		this.dnis = dnis;
	}
	public String getCall_time() {
		return call_time;
	}
	public void setCall_time(String call_time) {
		this.call_time = call_time;
	}
	public double getBill_duration() {
		return bill_duration;
	}
	public void setBill_duration(double bill_duration) {
		this.bill_duration = bill_duration;
	}
	public double getBill() {
		return bill;
	}
	public void setBill(double bill) {
		this.bill = bill;
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
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getCall_leg() {
		return call_leg;
	}
	public void setCall_leg(String call_leg) {
		this.call_leg = call_leg;
	}
	public String getCall_origin() {
		return call_origin;
	}
	public void setCall_origin(String call_origin) {
		this.call_origin = call_origin;
	}
	
	
	
	
	
	
}
