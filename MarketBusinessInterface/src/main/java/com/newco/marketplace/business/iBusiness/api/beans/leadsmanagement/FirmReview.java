package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XStreamAlias("FirmReview")
public class FirmReview {

	@XStreamOmitField
	NumberFormat numberFormat = new DecimalFormat("#.##");
	
	@XStreamAlias("ReviewerName")
	private String reviewerName;

	@XStreamAlias("Comment")
	private String comment;

	@XStreamAlias("Rating")
	private Double rating;

	@XStreamAlias("Date")
	private Date date;

	public String getReviewerName() {
		return reviewerName;
	}

	public void setReviewerName(String reviewerName) {
		this.reviewerName = reviewerName;
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
		if(null != rating){
			rating = Double.parseDouble(numberFormat.format(rating));
		}			
		this.rating = rating;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
