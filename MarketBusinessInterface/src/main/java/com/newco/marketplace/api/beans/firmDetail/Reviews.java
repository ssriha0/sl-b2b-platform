package com.newco.marketplace.api.beans.firmDetail;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("reviews")
@XmlAccessorType(XmlAccessType.FIELD)
public class Reviews {
		
	@XStreamImplicit(itemFieldName="review")
	private List<Review> review;

	public List<Review> getReview() {
		return review;
	}

	public void setReview(List<Review> review) {
		this.review = review;
	}

}
