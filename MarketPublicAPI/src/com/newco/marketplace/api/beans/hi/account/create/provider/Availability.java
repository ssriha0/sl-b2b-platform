package com.newco.marketplace.api.beans.hi.account.create.provider;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
@XStreamAlias("availability")
public class Availability {
    
	@XStreamImplicit(itemFieldName="week")
    private List<Week> week;

	public List<Week> getWeek() {
		return week;
	}

	public void setWeek(List<Week> week) {
		this.week = week;
	}

   
    

}
