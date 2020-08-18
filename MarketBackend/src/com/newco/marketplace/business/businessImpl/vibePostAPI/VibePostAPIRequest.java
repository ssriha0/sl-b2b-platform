package com.newco.marketplace.business.businessImpl.vibePostAPI;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author karthik_hariharan01
 * SL-18979 Create Event API Request
 *
 */
public class VibePostAPIRequest {

	@XStreamAlias("event_type")
	private String event_type;
	
	@XStreamAlias("event_date")
	private String event_date;
	
	@XStreamAlias("event_data")
	private EventData event_data;
	
	@XStreamAlias("created_at")
	private String created_at;
	
	@XStreamAlias("updated_at")
	private String updated_at;
	
	public String getEvent_type() {
		return event_type;
	}
	public void setEvent_type(String event_type) {
		this.event_type = event_type;
	}
	public String getEvent_date() {
		return event_date;
	}
	public void setEvent_date(String event_date) {
		this.event_date = event_date;
	}
	public EventData getEvent_data() {
		return event_data;
	}
	public void setEvent_data(EventData event_data) {
		this.event_data = event_data;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public String getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}
	
	
}
