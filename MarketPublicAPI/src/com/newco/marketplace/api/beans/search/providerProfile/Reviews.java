/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 08-SEP-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */

package com.newco.marketplace.api.beans.search.providerProfile;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.api.annotation.OptionalParam;
import com.newco.marketplace.search.types.CustomerFeedback;
import com.newco.marketplace.search.vo.ProviderSearchResponseVO;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This is a bean class for storing all information of 
 * the Reviews 
 * @author Infosys
 *
 */
@XStreamAlias("reviews")
public class Reviews {
	
	@XStreamAlias("reviewsTotal")  
	private Integer reviewsTotal;
	
	@OptionalParam
	@XStreamImplicit(itemFieldName="review")
	private List<Review> reviewList;
	
	public Reviews(){
		
	}
	
	public Reviews(ProviderSearchResponseVO providerSearchResponseVO) {
		this.reviewList = new ArrayList<Review>();
		//reviews.setFilter("");
		List<CustomerFeedback> feedBackList = providerSearchResponseVO
				.getReviews();
		if ((null!=feedBackList)&& (!feedBackList.isEmpty())) {
			for (CustomerFeedback feedBack : feedBackList) {
				Review review = new Review(feedBack);						
				this.reviewList.add(review);
			}			
		}	
		reviewsTotal = providerSearchResponseVO.getReviewCount();
	}

	public List<Review> getReviewList() {
		return reviewList;
	}

	public void setReviewList(List<Review> reviewList) {
		this.reviewList = reviewList;
	}
	
	public void addReview(Review review) {
		if (this.reviewList == null)
		  this.reviewList = new ArrayList<Review>();
		this.reviewList.add(review);
	}

	public Integer getReviewsTotal() {
		return reviewsTotal;
	}

	public void setReviewsTotal(Integer reviewsTotal) {
		this.reviewsTotal = reviewsTotal;
	}
}
