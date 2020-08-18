package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("Reviews")
public class Reviewes {
	@XStreamImplicit(itemFieldName = "Review")
	private List<PostReview> review;

	public List<PostReview> getReview() {
		return review;
	}

	public void setReview(List<PostReview> review) {
		this.review = review;
	}

	
	



}
