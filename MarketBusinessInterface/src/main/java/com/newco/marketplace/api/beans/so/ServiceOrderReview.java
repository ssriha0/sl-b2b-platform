package com.newco.marketplace.api.beans.so;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("review")
@XmlAccessorType(XmlAccessType.FIELD)
public class ServiceOrderReview {
					
	@XStreamAlias("cleanliness")
	private Integer cleanliness;
	
	@XStreamAlias("timeliness")
	private Integer timeliness;
	
	@XStreamAlias("communication")
	private Integer communication;
	
	@XStreamAlias("professionalism")
	private Integer professionalism;
	
	@XStreamAlias("quality")
	private Integer quality;
	
	@XStreamAlias("value")
	private Integer value;
	
	@XStreamAlias("overallRating")
	private String overallRating;
	
	@XStreamAlias("date")
	private String date;
	
	@XStreamAlias("comment")
	private String comment;

	public Integer getCleanliness() {
		return cleanliness;
	}

	public void setCleanliness(Integer cleanliness) {
		this.cleanliness = cleanliness;
	}

	public Integer getTimeliness() {
		return timeliness;
	}

	public void setTimeliness(Integer timeliness) {
		this.timeliness = timeliness;
	}

	public Integer getCommunication() {
		return communication;
	}

	public void setCommunication(Integer communication) {
		this.communication = communication;
	}

	public Integer getProfessionalism() {
		return professionalism;
	}

	public void setProfessionalism(Integer professionalism) {
		this.professionalism = professionalism;
	}

	public Integer getQuality() {
		return quality;
	}

	public void setQuality(Integer quality) {
		this.quality = quality;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getOverallRating() {
		return overallRating;
	}

	public void setOverallRating(String overallRating) {
		this.overallRating = overallRating;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}


	
	

	
	
}
