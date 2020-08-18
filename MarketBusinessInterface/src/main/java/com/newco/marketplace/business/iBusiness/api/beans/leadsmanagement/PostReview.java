package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;

import java.util.Date;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Review")
public class PostReview {
	
	@XStreamAlias("Author")
	private String author;
	
	@XStreamAlias("Comment")
	private String comment;
	
	@XStreamAlias("Rating")
	private Double rating;
	
	@XStreamAlias("Date")
	private Date date;

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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	

	

}
