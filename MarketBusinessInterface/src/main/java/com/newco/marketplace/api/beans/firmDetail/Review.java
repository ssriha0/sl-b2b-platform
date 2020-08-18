package com.newco.marketplace.api.beans.firmDetail;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("review")
@XmlAccessorType(XmlAccessType.FIELD)
public class Review {
					
	@XStreamAlias("author")
	private String author;
	
	@XStreamAlias("comment")
	private String comment;
	
	@XStreamAlias("rating")
	private Double rating;
	
	@XStreamAlias("date")
	private String date;

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	
	
}
