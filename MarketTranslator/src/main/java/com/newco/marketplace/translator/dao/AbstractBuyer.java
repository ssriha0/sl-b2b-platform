package com.newco.marketplace.translator.dao;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * AbstractBuyer entity provides the base persistence definition of the Buyer
 * entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractBuyer implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 5941777807576329377L;
	private Integer buyerId;
	private LuFundingType luFundingType;
	private Double postingFee;
	private Double cancellationFee;
	private String businessName;
	private Date businessStartDate;
	private String busPhoneNo;
	private String busFaxNo;
	private String buyerSourceId;
	private Integer contactId;
	private String userName;
	private Integer priLocnId;
	private Integer billLocnId;
	private Integer defaultAccessFeeId;
	private Date createdDate;
	private Date modifiedDate;
	private String modifiedBy;
	private Integer businessTypeId;
	private Integer primaryIndustryId;
	private Integer companySizeId;
	private Integer salesVolumeId;
	private String webAddress;
	private String promoCd;
	private Integer totalSoCompleted;
	private Integer aggregateRatingCount;
	private Double aggregateRatingScore;
	private Byte termsCondInd;
	private Date termCondDateAccepted;
	private Integer termsCondId;
	private Set<BuyerMarketAdjustment> buyerMarketAdjustments = new HashSet<BuyerMarketAdjustment>(
			0);
	private Set<BuyerLocations> buyerLocationses = new HashSet<BuyerLocations>(
			0);
	private Set<BuyerDocument> buyerDocuments = new HashSet<BuyerDocument>(0);
	private Set<BuyerReferenceTypeMT> buyerReferenceTypes = new HashSet<BuyerReferenceTypeMT>(
			0);
	private Set<BuyerSku> buyerSkus = new HashSet<BuyerSku>(0);
	private Set<BuyerRetailPrice> buyerRetailPrices = new HashSet<BuyerRetailPrice>(
			0);

	// Constructors

	/** default constructor */
	public AbstractBuyer() {
		// intentionally blank
	}

	/** minimal constructor */
	public AbstractBuyer(Integer buyerId, String userName) {
		this.buyerId = buyerId;
		this.userName = userName;
	}

	/** full constructor */
	public AbstractBuyer(Integer buyerId, LuFundingType luFundingType,
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
		this.buyerId = buyerId;
		this.luFundingType = luFundingType;
		this.postingFee = postingFee;
		this.cancellationFee = cancellationFee;
		this.businessName = businessName;
		this.businessStartDate = businessStartDate;
		this.busPhoneNo = busPhoneNo;
		this.busFaxNo = busFaxNo;
		this.buyerSourceId = buyerSourceId;
		this.contactId = contactId;
		this.userName = userName;
		this.priLocnId = priLocnId;
		this.billLocnId = billLocnId;
		this.defaultAccessFeeId = defaultAccessFeeId;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.modifiedBy = modifiedBy;
		this.businessTypeId = businessTypeId;
		this.primaryIndustryId = primaryIndustryId;
		this.companySizeId = companySizeId;
		this.salesVolumeId = salesVolumeId;
		this.webAddress = webAddress;
		this.promoCd = promoCd;
		this.totalSoCompleted = totalSoCompleted;
		this.aggregateRatingCount = aggregateRatingCount;
		this.aggregateRatingScore = aggregateRatingScore;
		this.termsCondInd = termsCondInd;
		this.termCondDateAccepted = termCondDateAccepted;
		this.termsCondId = termsCondId;
		this.buyerMarketAdjustments = buyerMarketAdjustments;
		this.buyerLocationses = buyerLocationses;
		this.buyerDocuments = buyerDocuments;
		this.buyerReferenceTypes = buyerReferenceTypes;
		this.buyerSkus = buyerSkus;
		this.buyerRetailPrices = buyerRetailPrices;
	}

	// Property accessors
	@Id
	@Column(name = "buyer_id", unique = true, nullable = false, insertable = true, updatable = true)
	public Integer getBuyerId() {
		return this.buyerId;
	}

	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.EAGER)
	@JoinColumn(name = "funding_type_id", unique = false, nullable = true, insertable = true, updatable = true)
	public LuFundingType getLuFundingType() {
		return this.luFundingType;
	}

	public void setLuFundingType(LuFundingType luFundingType) {
		this.luFundingType = luFundingType;
	}

	@Column(name = "posting_fee", unique = false, nullable = true, insertable = true, updatable = true, precision = 9)
	public Double getPostingFee() {
		return this.postingFee;
	}

	public void setPostingFee(Double postingFee) {
		this.postingFee = postingFee;
	}

	@Column(name = "cancellation_fee", unique = false, nullable = true, insertable = true, updatable = true, precision = 9)
	public Double getCancellationFee() {
		return this.cancellationFee;
	}

	public void setCancellationFee(Double cancellationFee) {
		this.cancellationFee = cancellationFee;
	}

	@Column(name = "business_name", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getBusinessName() {
		return this.businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "business_start_date", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getBusinessStartDate() {
		return this.businessStartDate;
	}

	public void setBusinessStartDate(Date businessStartDate) {
		this.businessStartDate = businessStartDate;
	}

	@Column(name = "bus_phone_no", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public String getBusPhoneNo() {
		return this.busPhoneNo;
	}

	public void setBusPhoneNo(String busPhoneNo) {
		this.busPhoneNo = busPhoneNo;
	}

	@Column(name = "bus_fax_no", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public String getBusFaxNo() {
		return this.busFaxNo;
	}

	public void setBusFaxNo(String busFaxNo) {
		this.busFaxNo = busFaxNo;
	}

	@Column(name = "buyer_source_id", unique = false, nullable = true, insertable = true, updatable = true, length = 3)
	public String getBuyerSourceId() {
		return this.buyerSourceId;
	}

	public void setBuyerSourceId(String buyerSourceId) {
		this.buyerSourceId = buyerSourceId;
	}

	@Column(name = "contact_id", unique = false, nullable = true, insertable = true, updatable = true)
	public Integer getContactId() {
		return this.contactId;
	}

	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}

	@Column(name = "user_name", unique = false, nullable = false, insertable = true, updatable = true, length = 30)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "pri_locn_id", unique = false, nullable = true, insertable = true, updatable = true)
	public Integer getPriLocnId() {
		return this.priLocnId;
	}

	public void setPriLocnId(Integer priLocnId) {
		this.priLocnId = priLocnId;
	}

	@Column(name = "bill_locn_id", unique = false, nullable = true, insertable = true, updatable = true)
	public Integer getBillLocnId() {
		return this.billLocnId;
	}

	public void setBillLocnId(Integer billLocnId) {
		this.billLocnId = billLocnId;
	}

	@Column(name = "default_access_fee_id", unique = false, nullable = true, insertable = true, updatable = true)
	public Integer getDefaultAccessFeeId() {
		return this.defaultAccessFeeId;
	}

	public void setDefaultAccessFeeId(Integer defaultAccessFeeId) {
		this.defaultAccessFeeId = defaultAccessFeeId;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "created_date", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "modified_date", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name = "modified_by", unique = false, nullable = true, insertable = true, updatable = true, length = 30)
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "business_type_id", unique = false, nullable = true, insertable = true, updatable = true)
	public Integer getBusinessTypeId() {
		return this.businessTypeId;
	}

	public void setBusinessTypeId(Integer businessTypeId) {
		this.businessTypeId = businessTypeId;
	}

	@Column(name = "primary_industry_id", unique = false, nullable = true, insertable = true, updatable = true)
	public Integer getPrimaryIndustryId() {
		return this.primaryIndustryId;
	}

	public void setPrimaryIndustryId(Integer primaryIndustryId) {
		this.primaryIndustryId = primaryIndustryId;
	}

	@Column(name = "company_size_id", unique = false, nullable = true, insertable = true, updatable = true)
	public Integer getCompanySizeId() {
		return this.companySizeId;
	}

	public void setCompanySizeId(Integer companySizeId) {
		this.companySizeId = companySizeId;
	}

	@Column(name = "sales_volume_id", unique = false, nullable = true, insertable = true, updatable = true)
	public Integer getSalesVolumeId() {
		return this.salesVolumeId;
	}

	public void setSalesVolumeId(Integer salesVolumeId) {
		this.salesVolumeId = salesVolumeId;
	}

	@Column(name = "web_address", unique = false, nullable = true, insertable = true, updatable = true)
	public String getWebAddress() {
		return this.webAddress;
	}

	public void setWebAddress(String webAddress) {
		this.webAddress = webAddress;
	}

	@Column(name = "promo_cd", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getPromoCd() {
		return this.promoCd;
	}

	public void setPromoCd(String promoCd) {
		this.promoCd = promoCd;
	}

	@Column(name = "total_so_completed", unique = false, nullable = true, insertable = true, updatable = true)
	public Integer getTotalSoCompleted() {
		return this.totalSoCompleted;
	}

	public void setTotalSoCompleted(Integer totalSoCompleted) {
		this.totalSoCompleted = totalSoCompleted;
	}

	@Column(name = "aggregate_rating_count", unique = false, nullable = true, insertable = true, updatable = true)
	public Integer getAggregateRatingCount() {
		return this.aggregateRatingCount;
	}

	public void setAggregateRatingCount(Integer aggregateRatingCount) {
		this.aggregateRatingCount = aggregateRatingCount;
	}

	@Column(name = "aggregate_rating_score", unique = false, nullable = true, insertable = true, updatable = true, precision = 5, scale = 3)
	public Double getAggregateRatingScore() {
		return this.aggregateRatingScore;
	}

	public void setAggregateRatingScore(Double aggregateRatingScore) {
		this.aggregateRatingScore = aggregateRatingScore;
	}

	@Column(name = "terms_cond_ind", unique = false, nullable = true, insertable = true, updatable = true)
	public Byte getTermsCondInd() {
		return this.termsCondInd;
	}

	public void setTermsCondInd(Byte termsCondInd) {
		this.termsCondInd = termsCondInd;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "term_cond_date_accepted", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getTermCondDateAccepted() {
		return this.termCondDateAccepted;
	}

	public void setTermCondDateAccepted(Date termCondDateAccepted) {
		this.termCondDateAccepted = termCondDateAccepted;
	}

	@Column(name = "terms_cond_id", unique = false, nullable = true, insertable = true, updatable = true)
	public Integer getTermsCondId() {
		return this.termsCondId;
	}

	public void setTermsCondId(Integer termsCondId) {
		this.termsCondId = termsCondId;
	}

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "buyer")
	public Set<BuyerMarketAdjustment> getBuyerMarketAdjustments() {
		return this.buyerMarketAdjustments;
	}

	public void setBuyerMarketAdjustments(
			Set<BuyerMarketAdjustment> buyerMarketAdjustments) {
		this.buyerMarketAdjustments = buyerMarketAdjustments;
	}

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "buyer")
	public Set<BuyerLocations> getBuyerLocationses() {
		return this.buyerLocationses;
	}

	public void setBuyerLocationses(Set<BuyerLocations> buyerLocationses) {
		this.buyerLocationses = buyerLocationses;
	}

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "buyer")
	public Set<BuyerDocument> getBuyerDocuments() {
		return this.buyerDocuments;
	}

	public void setBuyerDocuments(Set<BuyerDocument> buyerDocuments) {
		this.buyerDocuments = buyerDocuments;
	}

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "buyer")
	public Set<BuyerReferenceTypeMT> getBuyerReferenceTypes() {
		return this.buyerReferenceTypes;
	}

	public void setBuyerReferenceTypes(
			Set<BuyerReferenceTypeMT> buyerReferenceTypes) {
		this.buyerReferenceTypes = buyerReferenceTypes;
	}

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "buyer")
	public Set<BuyerSku> getBuyerSkus() {
		return this.buyerSkus;
	}

	public void setBuyerSkus(Set<BuyerSku> buyerSkus) {
		this.buyerSkus = buyerSkus;
	}

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "buyer")
	public Set<BuyerRetailPrice> getBuyerRetailPrices() {
		return this.buyerRetailPrices;
	}

	public void setBuyerRetailPrices(Set<BuyerRetailPrice> buyerRetailPrices) {
		this.buyerRetailPrices = buyerRetailPrices;
	}

}