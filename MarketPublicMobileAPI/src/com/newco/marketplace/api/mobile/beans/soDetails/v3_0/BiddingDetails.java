package com.newco.marketplace.api.mobile.beans.soDetails.v3_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing bid information.
 * @author Infosys
 *
 */

@XStreamAlias("bidDetails")
@XmlRootElement(name = "bidDetails")
@XmlAccessorType(XmlAccessType.FIELD)
public class BiddingDetails {

	@XStreamAlias("sealedInd")
	private boolean sealedInd;
	
	@XStreamAlias("totalBids")
	private Integer totalBids;
	
	@XStreamAlias("minimumBid")
	private String minimumBid;
	
	@XStreamAlias("maximumBid")
	private String maximumBid;
	
	@XStreamAlias("currentBid")
	@XmlElement(name="currentBid")
	private Bid currentBid;
	
	@XStreamAlias("previousBid")
	@XmlElement(name="previousBid")
	private Bid previousBid;
	
	@XStreamAlias("otherBidsFromMyCompany")
	@XmlElement(name="otherBidsFromMyCompany")
	private BidList otherBidsFromMyCompany;
	
	@XStreamAlias("allOtherBids")
	@XmlElement(name="allOtherBids")
	private BidList allOtherBids;


	public boolean isSealedInd() {
		return sealedInd;
	}

	public void setSealedInd(boolean sealedInd) {
		this.sealedInd = sealedInd;
	}

	public Integer getTotalBids() {
		return totalBids;
	}

	public void setTotalBids(Integer totalBids) {
		this.totalBids = totalBids;
	}

	public String getMinimumBid() {
		return minimumBid;
	}

	public void setMinimumBid(String minimumBid) {
		this.minimumBid = minimumBid;
	}

	public String getMaximumBid() {
		return maximumBid;
	}

	public void setMaximumBid(String maximumBid) {
		this.maximumBid = maximumBid;
	}

	public Bid getCurrentBid() {
		return currentBid;
	}

	public void setCurrentBid(Bid currentBid) {
		this.currentBid = currentBid;
	}

	public Bid getPreviousBid() {
		return previousBid;
	}

	public void setPreviousBid(Bid previousBid) {
		this.previousBid = previousBid;
	}

	public BidList getOtherBidsFromMyCompany() {
		return otherBidsFromMyCompany;
	}

	public void setOtherBidsFromMyCompany(BidList otherBidsFromMyCompany) {
		this.otherBidsFromMyCompany = otherBidsFromMyCompany;
	}

	public BidList getAllOtherBids() {
		return allOtherBids;
	}

	public void setAllOtherBids(BidList allOtherBids) {
		this.allOtherBids = allOtherBids;
	}

	
	
}
