/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 08-SEP-2009	MarketPublicAPI   SHC				1.0
 * 
 */

package com.newco.marketplace.api.beans.search.providerProfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a bean class for storing all information of 
 * the Skills 
 *
 */
@XStreamAlias("availability")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProviderAvailability {
	public ProviderAvailability(){
		
	}
	 public ProviderAvailability(com.newco.marketplace.search.vo.Availability value) {		 
			this.availMonday = value.getAvailMonday();
			this.availTuesday = value.getAvailTuesday();
			this.availWednesday = value.getAvailWednesday();
			this.availThursday = value.getAvailThursday();
			this.availFriday = value.getAvailFriday();
			this.availSaturday = value.getAvailSaturday();
			this.availSunday = value.getAvailSunday();
			this.timeZone = value.getTimeZone();
	 }
	@XStreamAlias("monday")
	private String availMonday;
	
	@XStreamAlias("tuesday")
	private String availTuesday;
	
	@XStreamAlias("wednesday")
	private String availWednesday;
	
	@XStreamAlias("thursday")
	private String availThursday;
	
	@XStreamAlias("friday")
	private String availFriday;
	
	@XStreamAlias("saturday")
	private String availSaturday;
	
	@XStreamAlias("sunday")
	private String availSunday;
	
	@XStreamAlias("timezone")
	private String timeZone;
	

	public String getAvailMonday() {
		return availMonday;
	}



	public void setAvailMonday(String availMonday) {
		this.availMonday = availMonday;
	}



	public String getAvailTuesday() {
		return availTuesday;
	}



	public void setAvailTuesday(String availTuesday) {
		this.availTuesday = availTuesday;
	}



	public String getAvailWednesday() {
		return availWednesday;
	}



	public void setAvailWednesday(String availWednesday) {
		this.availWednesday = availWednesday;
	}



	public String getAvailThursday() {
		return availThursday;
	}



	public void setAvailThursday(String availThursday) {
		this.availThursday = availThursday;
	}



	public String getAvailFriday() {
		return availFriday;
	}



	public void setAvailFriday(String availFriday) {
		this.availFriday = availFriday;
	}



	public String getAvailSaturday() {
		return availSaturday;
	}



	public void setAvailSaturday(String availSaturday) {
		this.availSaturday = availSaturday;
	}



	public String getAvailSunday() {
		return availSunday;
	}



	public void setAvailSunday(String availSunday) {
		this.availSunday = availSunday;
	}



	public String getTimeZone() {
		return timeZone;
	}



	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

}
