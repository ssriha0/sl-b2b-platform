package com.newco.marketplace.dto.vo.survey;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("CSAT")
@XmlAccessorType(XmlAccessType.FIELD)
public class CSATSurvey {
	
	@XStreamAlias("rating")
	double rating;
	@XStreamAlias("options")
	Options options;
	@XStreamAlias("comments")
	String comments;
	
	
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Options getOptions() {
		return options;
	}
	public void setOptions(Options options) {
		this.options = options;
	}
	
	
}
