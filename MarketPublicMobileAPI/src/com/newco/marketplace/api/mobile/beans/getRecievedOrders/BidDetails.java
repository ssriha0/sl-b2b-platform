package com.newco.marketplace.api.mobile.beans.getRecievedOrders;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("bidDetails") 
@XmlAccessorType(XmlAccessType.FIELD)
public class BidDetails {
	@XStreamAlias("sealedBidInd")
	private Boolean sealedBidInd;
	
	@XStreamAlias("bidRangeMin")
	private Double bidRangeMin;

	@XStreamAlias("bidRangeMax")
	private Double bidRangeMax;
	
	@XStreamAlias("bidPrice")
	private Double bidPrice;
	
	@XStreamAlias("totalBids")
	private Integer totalBids;

	public Boolean getSealedBidInd() {
		return sealedBidInd;
	}

	public void setSealedBidInd(Boolean sealedBidInd) {
		this.sealedBidInd = sealedBidInd;
	}

	public Double getBidRangeMin() {
		return bidRangeMin;
	}

	public void setBidRangeMin(Double bidRangeMin) {
		this.bidRangeMin = bidRangeMin;
	}

	public Double getBidRangeMax() {
		return bidRangeMax;
	}

	public void setBidRangeMax(Double bidRangeMax) {
		this.bidRangeMax = bidRangeMax;
	}

	public Double getBidPrice() {
		return bidPrice;
	}

	public void setBidPrice(Double bidPrice) {
		this.bidPrice = bidPrice;
	}

	public Integer getTotalBids() {
		return totalBids;
	}

	public void setTotalBids(Integer totalBids) {
		this.totalBids = totalBids;
	}
	
	
}
