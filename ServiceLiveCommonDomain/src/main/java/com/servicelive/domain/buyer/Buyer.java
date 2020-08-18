package com.servicelive.domain.buyer;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.servicelive.domain.common.BuyerFeatureSetEnum;
import com.servicelive.domain.common.Contact;
import com.servicelive.domain.common.Location;
import com.servicelive.domain.lookup.LookupBusinessType;
import com.servicelive.domain.lookup.LookupCompanySize;
import com.servicelive.domain.lookup.LookupFundingType;
import com.servicelive.domain.lookup.LookupSalesVolume;
import com.servicelive.domain.userprofile.User;


/**
 * Buyer entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="buyer" )
@XmlRootElement()
@XmlAccessorType(XmlAccessType.FIELD)
public class Buyer implements java.io.Serializable, Cloneable {


    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy=IDENTITY)
    @Column(name="buyer_id", unique=true, nullable=false)
    private Integer buyerId;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="contact_id")
    private Contact contact;

    @Column(name="cancellation_fee")
    private BigDecimal cancellationFee;

    @XmlElement
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="company_size_id")
    private LookupCompanySize lookupCompanySize;

    @XmlElement
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="business_type_id")
    private LookupBusinessType lookupBusinessType;

    @XmlElement
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="user_name", nullable=false)
    private User user;

    @XmlElement
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="funding_type_id")
    private LookupFundingType lookupFundingType;

    @XmlElement
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="bill_locn_id")
    private Location locationByBillLocnId;

    @XmlElement
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="pri_locn_id")
    private Location locationByPriLocnId;

    @XmlElement
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="sales_volume_id")
    private LookupSalesVolume lookupSalesVolume;

    @Column(name="business_name", length=100)
    private String businessName;

    @Temporal(TemporalType.DATE)
    @Column(name="business_start_date", length=19)
    private Date businessStartDate;


    @Column(name="bus_phone_no", length=10)
    private String busPhoneNo;

    @Column(name="bus_fax_no", length=10)
    private String busFaxNo;
    
    @Column(name="buyer_source_id", length=3)
    private String buyerSourceId;

    @Column(name="default_access_fee_id")
    private Integer defaultAccessFeeId;


    @Column(name="modified_by", length=30)
    private String modifiedBy;

    @Column(name="primary_industry_id")
    private Integer primaryIndustryId;

    @Column(name="web_address")
    private String webAddress;

    @Column(name = "posting_fee")
    private BigDecimal postingFee;

    @Column(name="promo_cd", length=100)
    private String promoCd;

    @Column(name="total_so_completed")
    private Integer totalSoCompleted;

    @Column(name="aggregate_rating_count")
    private Integer aggregateRatingCount;

    @Column(name="aggregate_rating_score", precision=5, scale=3)
    private Double aggregateRatingScore;

    @Column(name="terms_cond_ind")
    private Byte termsCondInd;

    @Temporal(TemporalType.DATE)
    @Column(name="term_cond_date_accepted", length=19)
    private Date termCondDateAccepted;

    @Column(name="ein_no", length=64)
    private String einNo;

    @Column(name="account_contact_id")
    private Integer accountContactId;

    @OneToMany( fetch=FetchType.LAZY, cascade= { CascadeType.ALL} , mappedBy="buyerId")
    @JoinColumn(name = "buyer_id",nullable = true )
    private List<BuyerFeatureSet> buyerfeatureSet;


    @Column(name="email_reqd_ind")
    private Byte buyer_email_reqd_ind;

    // Constructors

    /** default constructor */
    public Buyer() {
        super();
    }

    /**
     * minimal constructor
     * @param user
     */
    public Buyer(User user) {
        this.user = user;
    }

    /**
     * @param buyerId
     */
    public Buyer(Integer buyerId) {
        this.buyerId = buyerId;
    }


    // Property accessors
    /**
     *
     * @return Integer
     */
    public Integer getBuyerId() {
        return this.buyerId;
    }
    /**
     *
     * @param buyerId
     */
    public void setBuyerId(Integer buyerId) {
        this.buyerId = buyerId;
    }

    /**
     *
     * @return Contact
     */
    public Contact getContact() {
        return this.contact;
    }
    /**
     *
     * @param contact
     */
    public void setContact(Contact contact) {
        this.contact = contact;
    }

    /**
     *
     * @return User
     */
    public User getUser() {
        return this.user;
    }
    /**
     *
     * @param User
     */
    public void setUser(User User) {
        this.user = User;
    }

    /**
     *
     * @return LookupCompanySize
     */
    public LookupCompanySize getLookupCompanySize() {
        return this.lookupCompanySize;
    }
    /**
     *
     * @param lookupCompanySize
     */
    public void setLookupCompanySize(LookupCompanySize lookupCompanySize) {
        this.lookupCompanySize = lookupCompanySize;
    }
    /**
     *
     * @return LookupBusinessType
     */
    public LookupBusinessType getLookupBusinessType() {
        return this.lookupBusinessType;
    }
    /**
     *
     * @param lookupBusinessType
     */
    public void setLookupBusinessType(LookupBusinessType lookupBusinessType) {
        this.lookupBusinessType = lookupBusinessType;
    }
    /**
     *
     * @return LookupFundingType
     */
    public LookupFundingType getLookupFundingType() {
        return this.lookupFundingType;
    }
    /**
     *
     * @param lookupFundingType
     */
    public void setLookupFundingType(LookupFundingType lookupFundingType) {
        this.lookupFundingType = lookupFundingType;
    }

    /**
     *
     * @return Location
     */
    public Location getLocationByBillLocnId() {
        return this.locationByBillLocnId;
    }
    /**
     *
     * @param locationByBillLocnId
     */
    public void setLocationByBillLocnId(Location locationByBillLocnId) {
        this.locationByBillLocnId = locationByBillLocnId;
    }

    /**
     *
     * @return Location
     */
    public Location getLocationByPriLocnId() {
        return this.locationByPriLocnId;
    }
    /**
     *
     * @param locationByPriLocnId
     */
    public void setLocationByPriLocnId(Location locationByPriLocnId) {
        this.locationByPriLocnId = locationByPriLocnId;
    }

    /**
     *
     * @return LookupSalesVolume
     */
    public LookupSalesVolume getLookupSalesVolume() {
        return this.lookupSalesVolume;
    }
    /**
     *
     * @param lookupSalesVolume
     */
    public void setLookupSalesVolume(LookupSalesVolume lookupSalesVolume) {
        this.lookupSalesVolume = lookupSalesVolume;
    }

    /**
     *
     * @return String
     */
    public String getBusinessName() {
        return this.businessName;
    }
    /**
     *
     * @param businessName
     */
    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    /**
     *
     * @return Date
     */
    public Date getBusinessStartDate() {
        return this.businessStartDate;
    }
    /**
     *
     * @param businessStartDate
     */
    public void setBusinessStartDate(Date businessStartDate) {
        this.businessStartDate = businessStartDate;
    }

    /**
     *
     * @return String
     */
    public String getBusPhoneNo() {
        return this.busPhoneNo;
    }
    /**
     *
     * @param busPhoneNo
     */
    public void setBusPhoneNo(String busPhoneNo) {
        this.busPhoneNo = busPhoneNo;
    }

    /**
     *
     * @return String
     */
    public String getBusFaxNo() {
        return this.busFaxNo;
    }
    /**
     *
     * @param busFaxNo
     */
    public void setBusFaxNo(String busFaxNo) {
        this.busFaxNo = busFaxNo;
    }

    /**
     *
     * @return String
     */
    public String getBuyerSourceId() {
        return this.buyerSourceId;
    }
    /**
     *
     * @param buyerSourceId
     */
    public void setBuyerSourceId(String buyerSourceId) {
        this.buyerSourceId = buyerSourceId;
    }
    /**
     *
     * @return Integer
     */
    public Integer getDefaultAccessFeeId() {
        return this.defaultAccessFeeId;
    }
    /**
     *
     * @param defaultAccessFeeId
     */
    public void setDefaultAccessFeeId(Integer defaultAccessFeeId) {
        this.defaultAccessFeeId = defaultAccessFeeId;
    }
    /**
     *
     * @return String
     */
    public String getModifiedBy() {
        return this.modifiedBy;
    }
    /**
     *
     * @param modifiedBy
     */
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
    /**
     *
     * @return Integer
     */
    public Integer getPrimaryIndustryId() {
        return this.primaryIndustryId;
    }
    /**
     *
     * @param primaryIndustryId
     */
    public void setPrimaryIndustryId(Integer primaryIndustryId) {
        this.primaryIndustryId = primaryIndustryId;
    }
    /**
     *
     * @return String
     */
    public String getWebAddress() {
        return this.webAddress;
    }
    /**
     *
     * @param webAddress
     */
    public void setWebAddress(String webAddress) {
        this.webAddress = webAddress;
    }
    /**
     *
     * @return String
     */
    public String getPromoCd() {
        return this.promoCd;
    }
    /**
     *
     * @param promoCd
     */
    public void setPromoCd(String promoCd) {
        this.promoCd = promoCd;
    }
    /**
     *
     * @return Integer
     */
    public Integer getTotalSoCompleted() {
        return this.totalSoCompleted;
    }
    /**
     *
     * @param totalSoCompleted
     */
    public void setTotalSoCompleted(Integer totalSoCompleted) {
        this.totalSoCompleted = totalSoCompleted;
    }
    /**
     *
     * @return Integer
     */
    public Integer getAggregateRatingCount() {
        return this.aggregateRatingCount;
    }
    /**
     *
     * @param aggregateRatingCount
     */
    public void setAggregateRatingCount(Integer aggregateRatingCount) {
        this.aggregateRatingCount = aggregateRatingCount;
    }
    /**
     *
     * @return Double
     */
    public Double getAggregateRatingScore() {
        return this.aggregateRatingScore;
    }
    /**
     *
     * @param aggregateRatingScore
     */
    public void setAggregateRatingScore(Double aggregateRatingScore) {
        this.aggregateRatingScore = aggregateRatingScore;
    }
    /**
     *
     * @return Byte
     */
    public Byte getTermsCondInd() {
        return this.termsCondInd;
    }
    /**
     *
     * @param termsCondInd
     */
    public void setTermsCondInd(Byte termsCondInd) {
        this.termsCondInd = termsCondInd;
    }

    /**
     *
     * @return Date
     */
    public Date getTermCondDateAccepted() {
        return this.termCondDateAccepted;
    }
    /**
     *
     * @param termCondDateAccepted
     */
    public void setTermCondDateAccepted(Date termCondDateAccepted) {
        this.termCondDateAccepted = termCondDateAccepted;
    }
    /**
     *
     * @return String
     */
    public String getEinNo() {
        return this.einNo;
    }
    /**
     *
     * @param einNo
     */
    public void setEinNo(String einNo) {
        this.einNo = einNo;
    }
    /**
     *
     * @return Integer
     */
    public Integer getAccountContactId() {
        return this.accountContactId;
    }
    /**
     *
     * @param accountContactId
     */
    public void setAccountContactId(Integer accountContactId) {
        this.accountContactId = accountContactId;
    }

    /**
     * @return the buyerfeatureSet
     */
    public List<BuyerFeatureSet> getBuyerfeatureSet() {
        return buyerfeatureSet;
    }

    /**
     * @param buyerfeatureSet the buyerfeatureSet to set
     */
    public void setBuyerfeatureSet(List<BuyerFeatureSet> buyerfeatureSet) {
        this.buyerfeatureSet = buyerfeatureSet;
    }

    public Boolean hasAutoRouting(){
        return hasFeature(BuyerFeatureSetEnum.AUTO_ROUTE.name());
    }

    public Boolean hasOrderGrouping(){
        return hasFeature(BuyerFeatureSetEnum.ORDER_GROUPING.name());
    }

    public Boolean hasTierRouting(){
        return hasFeature(BuyerFeatureSetEnum.TIER_ROUTE.name());
    }

    public Boolean hasConditionalRouting(){
        return hasFeature(BuyerFeatureSetEnum.CONDITIONAL_ROUTE.name());
    }

    public Boolean hasFeature(String featureString) {
        if (buyerfeatureSet != null){
            for(BuyerFeatureSet feature : buyerfeatureSet){
                if (feature.getIsActive() && feature.getFeatureSet().equalsIgnoreCase(featureString)){
                    return true;
                }
            }
        }
        return false;
    }

    public void setCancellationFee(BigDecimal cancellationFee) {
        this.cancellationFee = cancellationFee;
    }

    public BigDecimal getCancellationFee() {
        return cancellationFee;
    }

    public void setPostingFee(BigDecimal postingFee) {
        this.postingFee = postingFee;
    }

    public BigDecimal getPostingFee() {
        return postingFee;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

	public Byte getBuyer_email_reqd_ind() {
		return buyer_email_reqd_ind;
	}

	public void setBuyer_email_reqd_ind(Byte buyer_email_reqd_ind) {
		this.buyer_email_reqd_ind = buyer_email_reqd_ind;
	}
}