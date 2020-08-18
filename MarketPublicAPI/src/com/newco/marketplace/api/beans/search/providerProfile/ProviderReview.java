/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 08-SEP-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.beans.search.providerProfile;


import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.newco.marketplace.search.types.CustomerFeedback;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a bean class for storing all information of 
 * the Review 
 * @author Infosys
 *
 */
@XStreamAlias("review")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProviderReview {

	@XStreamAlias("rating")
	private float rating;
	
	@XStreamAlias("date")
	private Date date;
	
	@XStreamAlias("comment")
	private String comment;
	
	@XStreamAlias("reviewerName")
	private String reviewerName;

	@XStreamAlias("id")
	private Long responseHdrId;
	
	public ProviderReview() {
		
	}

	public ProviderReview(CustomerFeedback feedBack) {	
		this.setReviewerName(feedBack.getReviewerName());
		this.setRating(feedBack.getRating());		
		//try {
			//this.date = DateUtils.dateToDefaultFormatString(feedBack.getReviewDate());
			this.date = feedBack.getReviewDate();
		//} catch (ParseException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}
		this.setComment(feedBack.getComment());
		this.setResponseHdrId(feedBack.getResponseHdrId());
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getReviewerName() {
		return reviewerName;
	}

	public void setReviewerName(String reviewerName) {
		this.reviewerName = reviewerName;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public Long getResponseHdrId() {
		return responseHdrId;
	}

	public void setResponseHdrId(Long responseHdrId) {
		this.responseHdrId = responseHdrId;
	}
	
	
}
