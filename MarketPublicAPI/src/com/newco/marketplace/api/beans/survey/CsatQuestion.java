package com.newco.marketplace.api.beans.survey;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("questions")
public class CsatQuestion {
	@XStreamAlias("general")
	String general;
	@XStreamAlias("belowAverage")
	String belowAverage;
	@XStreamAlias("aboveAverage")
	String aboveAverage;
	
	public String getGeneral() {
		return general;
	}
	public void setGeneral(String general) {
		this.general = general;
	}
	public String getBelowAverage() {
		return belowAverage;
	}
	public void setBelowAverage(String belowAverage) {
		this.belowAverage = belowAverage;
	}
	public String getAboveAverage() {
		return aboveAverage;
	}
	public void setAboveAverage(String aboveAverage) {
		this.aboveAverage = aboveAverage;
	}
}
