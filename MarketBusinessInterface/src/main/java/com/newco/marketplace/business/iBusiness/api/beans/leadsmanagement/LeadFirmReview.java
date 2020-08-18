package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XStreamAlias("firmReview")
public class LeadFirmReview {

	@XStreamOmitField
	NumberFormat numberFormat = new DecimalFormat("#.##");
	
	@XStreamAlias("feviewerName")
	private String reviewerName;

	@XStreamAlias("comment")
	private String comment;

	@XStreamAlias("rating")
	private Double rating;

	@XStreamAlias("date")
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
