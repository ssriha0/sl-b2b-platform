package com.newco.marketplace.business.businessImpl.vibePostAPI;

import com.thoughtworks.xstream.annotations.XStreamAlias;


/**
 * @author karthik_hariharan01
 * SL-18979 Create Event API Request
 *
 */
@XStreamAlias("event_data")
public class EventData {
	
	@XStreamAlias("mdn")
	private String mdn;
	
	@XStreamAlias("so_id")
	private String so_id;
	
	@XStreamAlias("service_city")
	private String service_city;
	
	@XStreamAlias("service_datetime")
	private String service_datetime;
	
	@XStreamAlias("service_spend_limit")
	private String service_spend_limit;
	
	@XStreamAlias("deepLinkURL")
	private String deepLinkURL;
	
	public String getMdn() {
		return mdn;
	}
	public void setMdn(String mdn) {
		this.mdn = mdn;
	}
	public String getSo_id() {
		return so_id;
	}
	public void setSo_id(String so_id) {
		this.so_id = so_id;
	}
	public String getService_city() {
		return service_city;
	}
	public void setService_city(String service_city) {
		this.service_city = service_city;
	}
	public String getService_datetime() {
		return service_datetime;
	}
	public void setService_datetime(String service_datetime) {
		this.service_datetime = service_datetime;
	}
	public String getService_spend_limit() {
		return service_spend_limit;
	}
	public void setService_spend_limit(String service_spend_limit) {
		this.service_spend_limit = service_spend_limit;
	}
	public String getDeepLinkURL() {
		return deepLinkURL;
	}
	public void setDeepLinkURL(String deepLinkURL) {
		this.deepLinkURL = deepLinkURL;
	}
	
	

}
