package com.newco.marketplace.dto.vo.survey;

/**
 * @author smagar1
 *
 */
public class BuyerRating {

	private Integer id;
	private Integer buyerId;
	private Integer vendorResourceId;
	private Integer totalSoCompleted;
	private Integer ratingCount;
	private String scoreType;
	private Double ratingScore;
	private String modifiedBy;
	
	@Override
	public String toString() {
		return "BuyerRating [id=" + id + ", buyerId=" + buyerId + ", vendorResourceId=" + vendorResourceId
				+ ", totalSoCompleted=" + totalSoCompleted + ", ratingCount=" + ratingCount + ", scoreType=" + scoreType
				+ ", ratingScore=" + ratingScore + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((buyerId == null) ? 0 : buyerId.hashCode());
		result = prime * result + ((scoreType == null) ? 0 : scoreType.hashCode());
		result = prime * result + ((vendorResourceId == null) ? 0 : vendorResourceId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BuyerRating other = (BuyerRating) obj;
		if (buyerId == null) {
			if (other.buyerId != null)
				return false;
		} else if (!buyerId.equals(other.buyerId))
			return false;
		if (scoreType == null) {
			if (other.scoreType != null)
				return false;
		} else if (!scoreType.equals(other.scoreType))
			return false;
		if (vendorResourceId == null) {
			if (other.vendorResourceId != null)
				return false;
		} else if (!vendorResourceId.equals(other.vendorResourceId))
			return false;
		return true;
	}

	public static class Builder {
		final BuyerRating buyerRating;

		public Builder() {
			this.buyerRating = new BuyerRating();
		}

		public Builder setId(Integer id) {
			buyerRating.id = id;
			return this;
		}

		public Builder setBuyerId(Integer buyerId) {
			buyerRating.buyerId = buyerId;
			return this;
		}

		public Builder setVendorResourceId(Integer vendorResourceId) {
			buyerRating.vendorResourceId = vendorResourceId;
			return this;
		}

		public Builder setTotalSoCompleted(Integer totalSoCompleted) {
			buyerRating.totalSoCompleted = totalSoCompleted;
			return this;
		}

		public Builder setRatingCount(Integer ratingCount) {
			buyerRating.ratingCount = ratingCount;
			return this;
		}

		public Builder setratingScore(Double ratingScore) {
			buyerRating.ratingScore = ratingScore;
			return this;
		}
		
		public Builder setScoreType(String scoreType) {
			buyerRating.scoreType = scoreType.trim().isEmpty()?null:scoreType;
			return this;
		}
		
		public Builder setModifiedBy(String modifiedBy) {	
			buyerRating.modifiedBy = modifiedBy.trim().isEmpty()?null:modifiedBy;
			return this;
		}

		public BuyerRating build() {
			return this.buyerRating;
		}
	}

	public Integer getId() {
		return id;
	}

	public Integer getBuyerId() {
		return buyerId;
	}

	public Integer getVendorResourceId() {
		return vendorResourceId;
	}

	public Integer getTotalSoCompleted() {
		return totalSoCompleted;
	}

	public Integer getRatingCount() {
		return ratingCount;
	}

	public Double getRatingScore() {
		return ratingScore;
	}
	
	public String getModifiedBy() {
		return modifiedBy;
	}
	
	public String getScoreType() {
		return scoreType;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public void setTotalSoCompleted(Integer totalSoCompleted) {
		this.totalSoCompleted = totalSoCompleted;
	}

	public void setRatingCount(Integer ratingCount) {
		this.ratingCount = ratingCount;
	}

	public void setRatingScore(Double ratingScore) {
		this.ratingScore = ratingScore;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public void setScoreType(String scoreType) {
		this.scoreType = scoreType;
	}

	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}

	public void setVendorResourceId(Integer vendorResourceId) {
		this.vendorResourceId = vendorResourceId;
	}
}
