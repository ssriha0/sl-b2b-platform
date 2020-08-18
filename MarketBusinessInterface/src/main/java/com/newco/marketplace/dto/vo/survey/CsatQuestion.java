package com.newco.marketplace.dto.vo.survey;

public class CsatQuestion {
	String general;
	String belowAverage;
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
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CsatQuestion [general=").append(general).append(", belowAverage=").append(belowAverage)
				.append(", aboveAverage=").append(aboveAverage).append("]");
		return builder.toString();
	}
	
	
}
