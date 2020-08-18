package com.newco.marketplace.dto.vo;

import java.util.List;

import com.sears.os.vo.SerializableBaseVO;

public class BidDetailsVO extends SerializableBaseVO{
	
		// Total Number of Bids
		private Integer numberOfBids;
		
		// Bid Range	
		private Double highBid;
		private Double lowBid;
		
		// My Current Bid
		private ServiceOrderBidModel myCurrentBid;
		
		private ServiceOrderBidModel myPreviousBid;
		
		private List<ServiceOrderBidModel> myPreviousBids;
			
		// Other bids from my company
		private List<ServiceOrderBidModel> otherBidsFromMyCompany;
		
		// Other bids from other companies
		private List<ServiceOrderBidModel> allOtherBids;
		
		private Boolean sealedInd =false;

		public Integer getNumberOfBids() {
			return numberOfBids;
		}

		public void setNumberOfBids(Integer numberOfBids) {
			this.numberOfBids = numberOfBids;
		}

		public Double getHighBid() {
			return highBid;
		}

		public void setHighBid(Double highBid) {
			this.highBid = highBid;
		}

		public Double getLowBid() {
			return lowBid;
		}

		public void setLowBid(Double lowBid) {
			this.lowBid = lowBid;
		}

		public ServiceOrderBidModel getMyCurrentBid() {
			return myCurrentBid;
		}

		public void setMyCurrentBid(ServiceOrderBidModel myCurrentBid) {
			this.myCurrentBid = myCurrentBid;
		}

		public ServiceOrderBidModel getMyPreviousBid() {
			return myPreviousBid;
		}

		public void setMyPreviousBid(ServiceOrderBidModel myPreviousBid) {
			this.myPreviousBid = myPreviousBid;
		}

		public List<ServiceOrderBidModel> getMyPreviousBids() {
			return myPreviousBids;
		}

		public void setMyPreviousBids(List<ServiceOrderBidModel> myPreviousBids) {
			this.myPreviousBids = myPreviousBids;
		}

		public List<ServiceOrderBidModel> getOtherBidsFromMyCompany() {
			return otherBidsFromMyCompany;
		}

		public void setOtherBidsFromMyCompany(
				List<ServiceOrderBidModel> otherBidsFromMyCompany) {
			this.otherBidsFromMyCompany = otherBidsFromMyCompany;
		}

		public List<ServiceOrderBidModel> getAllOtherBids() {
			return allOtherBids;
		}

		public void setAllOtherBids(List<ServiceOrderBidModel> allOtherBids) {
			this.allOtherBids = allOtherBids;
		}

		public Boolean getSealedInd() {
			return sealedInd;
		}

		public void setSealedInd(Boolean sealedInd) {
			this.sealedInd = sealedInd;
		}
		
		

}
