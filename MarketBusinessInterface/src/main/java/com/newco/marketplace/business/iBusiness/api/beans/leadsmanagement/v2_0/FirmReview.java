package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XStreamAlias("FirmReview")
@XmlAccessorType(XmlAccessType.FIELD)
public class FirmReview {

	@XStreamAlias("ReviewerName")
	@XmlElement(name="ReviewerName")
	private String reviewerName;

	@XStreamAlias("Comment")
	@XmlElement(name="Comment")
	private String comment;

	@XStreamAlias("Rating")
	@XmlElement(name="Rating")
	private Double rating;

	@XStreamAlias("Date")
	@XmlElement(name="Date")
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
			NumberFormat numberFormat = new DecimalFormat("#.##");
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
