package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Ratings")
public class Ratings {

	@XStreamAlias("Quality")
	private Integer quality;
	
	@XStreamAlias("Communication")
	private Integer communication;
	
	@XStreamAlias("Timeliness")
	private Integer timeliness;
	
	@XStreamAlias("Professionalism")
	private Integer professionalism;
	
	@XStreamAlias("Value")
	private Integer value;
	
	@XStreamAlias("Cleanliness")
	private Integer cleanliness;
	
	@XStreamAlias("comments")
	private String comments;

	public Integer getQuality() {
		return quality;
	}

	public void setQuality(Integer quality) {
		this.quality = quality;
	}

	public Integer getCommunication() {
		return communication;
	}

	public void setCommunication(Integer communication) {
		this.communication = communication;
	}

	public Integer getTimeliness() {
		return timeliness;
	}

	public void setTimeliness(Integer timeliness) {
		this.timeliness = timeliness;
	}

	public Integer getProfessionalism() {
		return professionalism;
	}

	public void setProfessionalism(Integer professionalism) {
		this.professionalism = professionalism;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Integer getCleanliness() {
		return cleanliness;
	}

	public void setCleanliness(Integer cleanliness) {
		this.cleanliness = cleanliness;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	

}
