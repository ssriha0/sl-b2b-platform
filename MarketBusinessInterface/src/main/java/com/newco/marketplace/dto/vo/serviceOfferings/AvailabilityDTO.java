package com.newco.marketplace.dto.vo.serviceOfferings;

import java.io.Serializable;

public class AvailabilityDTO implements Serializable 
{
	private static final long serialVersionUID = -1527770441184997995L;
	
    private String day;
    private String allInd;
    private String earlyMorningInd;
    private String morningInd;
    private String afterNoonInd;
    private String eveningInd;
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getAllInd() {
		return allInd;
	}
	public void setAllInd(String allInd) {
		this.allInd = allInd;
	}
	public String getEarlyMorningInd() {
		return earlyMorningInd;
	}
	public void setEarlyMorningInd(String earlyMorningInd) {
		this.earlyMorningInd = earlyMorningInd;
	}
	public String getMorningInd() {
		return morningInd;
	}
	public void setMorningInd(String morningInd) {
		this.morningInd = morningInd;
	}
	public String getAfterNoonInd() {
		return afterNoonInd;
	}
	public void setAfterNoonInd(String afterNoonInd) {
		this.afterNoonInd = afterNoonInd;
	}
	public String getEveningInd() {
		return eveningInd;
	}
	public void setEveningInd(String eveningInd) {
		this.eveningInd = eveningInd;
	}

	
}
