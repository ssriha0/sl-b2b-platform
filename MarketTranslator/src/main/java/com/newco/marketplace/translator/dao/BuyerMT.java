package com.newco.marketplace.translator.dao;

import java.util.Date;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Buyer entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "buyer", uniqueConstraints = {})
public class BuyerMT extends AbstractBuyer implements java.io.Serializable {

	// Constructors

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** default constructor */
	public BuyerMT() {
		// intentionally blank
	}

	/** minimal constructor */
	public BuyerMT(Integer buyerId, String userName) {
		super(buyerId, userName);
	}

	/** full constructor */
	public BuyerMT(Integer buyerId, LuFundingType luFundingType,
			Double postingFee, Double cancellationFee, String businessName,
			Date businessStartDate, String busPhoneNo, String busFaxNo,
			String buyerSourceId, Integer contactId, String userName,
			Integer priLocnId, Integer billLocnId, Integer defaultAccessFeeId,
			Date createdDate, Date modifiedDate, String modifiedBy,
			Integer businessTypeId, Integer primaryIndustryId,
			Integer companySizeId, Integer salesVolumeId, String webAddress,
			String promoCd, Integer totalSoCompleted,
			Integer aggregateRatingCount, Double aggregateRatingScore,
			Byte termsCondInd, Date termCondDateAccepted, Integer termsCondId,
			Set<BuyerMarketAdjustment> buyerMarketAdjustments,
			Set<BuyerLocations> buyerLocationses,
			Set<BuyerDocument> buyerDocuments,
			Set<BuyerReferenceTypeMT> buyerReferenceTypes,
			Set<BuyerSku> buyerSkus, Set<BuyerRetailPrice> buyerRetailPrices) {
		super(buyerId, luFundingType, postingFee, cancellationFee,
				businessName, businessStartDate, busPhoneNo, busFaxNo,
				buyerSourceId, contactId, userName, priLocnId, billLocnId,
				defaultAccessFeeId, createdDate, modifiedDate, modifiedBy,
				businessTypeId, primaryIndustryId, companySizeId,
				salesVolumeId, webAddress, promoCd, totalSoCompleted,
				aggregateRatingCount, aggregateRatingScore, termsCondInd,
				termCondDateAccepted, termsCondId, buyerMarketAdjustments,
				buyerLocationses, buyerDocuments, buyerReferenceTypes,
				buyerSkus, buyerRetailPrices);
	}

}
