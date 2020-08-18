/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 08-SEP-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.beans.closedOrders;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a bean class for storing all information of 
 * the Review 
 * @author Infosys
 *
 */
@XStreamAlias("review")
@XmlAccessorType(XmlAccessType.FIELD)
public class ClosedOrderReview {

	@XStreamAlias("rating")
	private String rating;
	
	@XStreamAlias("date")
	private String date;
	
	@XStreamAlias("comment")
	private String comment;

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
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
