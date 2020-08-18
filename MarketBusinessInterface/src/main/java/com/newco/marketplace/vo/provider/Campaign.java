package com.newco.marketplace.vo.provider;


public class Campaign {

	private Integer campaign_id;
	private String deleted;
	private String campaign_name;
	private String start_date;
	private Integer of_Ports;
	private String caller_id;
	private Integer allow_lead_routing;
	private Integer skill;
	private String default_email;
	private String default_fax;
	private Integer pacing_factor;
	private double minutes_between_calls;
	private String description;
	private String lead_sources;
	private String attendees;
	public Campaign(Integer campaign_id, String deleted, String campaign_name,
			String start_date, Integer of_Ports, String caller_id,
			Integer allow_lead_routing, Integer skill, String default_email,
			String default_fax, Integer pacing_factor,
			double minutes_between_calls, String description,
			String lead_sources, String attendees) {
		super();
		this.campaign_id = campaign_id;
		this.deleted = deleted;
		this.campaign_name = campaign_name;
		this.start_date = start_date;
		this.of_Ports = of_Ports;
		this.caller_id = caller_id;
		this.allow_lead_routing = allow_lead_routing;
		this.skill = skill;
		this.default_email = default_email;
		this.default_fax = default_fax;
		this.pacing_factor = pacing_factor;
		this.minutes_between_calls = minutes_between_calls;
		this.description = description;
		this.lead_sources = lead_sources;
		this.attendees = attendees;
	}
	public Integer getCampaign_id() {
		return campaign_id;
	}
	public void setCampaign_id(Integer campaign_id) {
		this.campaign_id = campaign_id;
	}
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	public String getCampaign_name() {
		return campaign_name;
	}
	public void setCampaign_name(String campaign_name) {
		this.campaign_name = campaign_name;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public Integer getOf_Ports() {
		return of_Ports;
	}
	public void setOf_Ports(Integer of_Ports) {
		this.of_Ports = of_Ports;
	}
	public String getCaller_id() {
		return caller_id;
	}
	public void setCaller_id(String caller_id) {
		this.caller_id = caller_id;
	}
	public Integer getAllow_lead_routing() {
		return allow_lead_routing;
	}
	public void setAllow_lead_routing(Integer allow_lead_routing) {
		this.allow_lead_routing = allow_lead_routing;
	}
	public Integer getSkill() {
		return skill;
	}
	public void setSkill(Integer skill) {
		this.skill = skill;
	}
	public String getDefault_email() {
		return default_email;
	}
	public void setDefault_email(String default_email) {
		this.default_email = default_email;
	}
	public String getDefault_fax() {
		return default_fax;
	}
	public void setDefault_fax(String default_fax) {
		this.default_fax = default_fax;
	}
	public Integer getPacing_factor() {
		return pacing_factor;
	}
	public void setPacing_factor(Integer pacing_factor) {
		this.pacing_factor = pacing_factor;
	}
	public double getMinutes_between_calls() {
		return minutes_between_calls;
	}
	public void setMinutes_between_calls(double minutes_between_calls) {
		this.minutes_between_calls = minutes_between_calls;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLead_sources() {
		return lead_sources;
	}
	public void setLead_sources(String lead_sources) {
		this.lead_sources = lead_sources;
	}
	public String getAttendees() {
		return attendees;
	}
	public void setAttendees(String attendees) {
		this.attendees = attendees;
	}
	
	
}
