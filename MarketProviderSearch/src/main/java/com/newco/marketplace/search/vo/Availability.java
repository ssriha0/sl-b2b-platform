/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 08-SEP-2009	MarketPublicAPI   SHC				1.0
 * 
 */

package com.newco.marketplace.search.vo;

import com.newco.marketplace.search.solr.dto.ProviderDto;


/**
 * This is a bean class for storing all information of 
 * the Skills 
 * @author pgangra
 *
 */

public class Availability {
	 
	private String availMonday;
	private String availTuesday;
	private String availWednesday;
	private String availThursday;
	private String availFriday;
	private String availSaturday;
	private String availSunday;
	private String timeZone;


	public Availability(ProviderDto dto) {
		this.availMonday = dto.getAvailMonday();
		this.availTuesday = dto.getAvailTuesday();
		this.availWednesday = dto.getAvailWednesday();
		this.availThursday = dto.getAvailThursday();
		this.availFriday = dto.getAvailFriday();
		this.availSaturday = dto.getAvailSaturday();
		this.availSunday = dto.getAvailSunday();
		this.timeZone = dto.getTimeZone();
	}	

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
