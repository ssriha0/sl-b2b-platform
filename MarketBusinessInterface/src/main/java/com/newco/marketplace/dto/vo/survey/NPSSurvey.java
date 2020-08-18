package com.newco.marketplace.dto.vo.survey;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("NPS")
@XmlAccessorType(XmlAccessType.FIELD)
public class NPSSurvey {
	
	@XStreamAlias("rating")
	Integer rating;
	@XStreamAlias("comments")
	String comments;
	
	public Integer getRating() {
		return rating;
	}
	public void setRating(Integer rating) {
		this.rating = rating;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
}
