package com.servicelive.orderfulfillment.domain;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.servicelive.orderfulfillment.domain.type.PriceModelType;
import com.servicelive.orderfulfillment.domain.type.ProviderResponseType;

@Entity
@Table(name = "so_routed_providers")
@XmlRootElement()
public class RoutedProvider extends SOChild implements Comparable<RoutedProvider>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5658069740402316295L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "so_routed_provider_id")
	private Integer routedProviderId;
	
	@Column(name = "resource_id")
	private Long providerResourceId;

	@Column(name = "provider_resp_id")
	private Integer providerRespId;
	
	@Column(name = "resp_reason_id")
	private Integer providerRespReasonId;
	
	@Column(name = "provider_resp_comment")
	private String providerRespComment;
	
	@Column(name = "provider_resp_date")
	private Date providerRespDate;
	
	@Column(name = "vendor_id")
	private Integer vendorId;
	
	@Column(name = "offer_expiration_date")
	private Date offerExpirationDate;
	
	@Column(name = "incr_spend_limit")
	private BigDecimal increaseSpendLimit;
	
	@Embedded
	private SOScheduleDate schedule;
	
	@Column(name = "offer_expiration_sdate")
	private Date offerExpirationSDate;

	@Column(name = "tier_id")
	private Integer tierId;
	
	@Column(name = "spn_id")
	private Integer spnId;
	
	@Column(name = "email_sent")
	private Boolean emailSent;
	
	@Column(name = "price_model")
	private String priceModel = PriceModelType.UNKNOWN.name();

	@Column(name = "routed_date")
	private Date routedDate;
	
	@Column(name = "total_labor")
	private BigDecimal totalLabor;
	
	@Column(name = "total_hours")
	private Double totalHours;
	
	@Column(name = "parts_materials")
	private BigDecimal partsMaterials;
	
	@Column(name = "routed_time_perf_score")
	private Double perfScore;
	
	@Column(name = "routed_time_firm_perf_score")
	private Double firmPerfScore;
	
	@Transient
	private Integer nextTier;
	
	@OneToMany(mappedBy="routedProvider", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<SOCounterOfferReason> counterOffers = new HashSet<SOCounterOfferReason>();
	
	public int compareTo(RoutedProvider o) {
		CompareToBuilder compareToBuilder = new CompareToBuilder()
			.append(emailSent, o.emailSent).append(increaseSpendLimit, o.increaseSpendLimit)
		 	.append(offerExpirationDate, o.offerExpirationDate)
		 	.append(partsMaterials, o.partsMaterials).append(priceModel, o.priceModel)
		 	.append(providerResourceId, o.providerResourceId).append(providerRespComment, o.providerRespComment)
		 	.append(providerRespDate, o.providerRespDate).append(providerRespId, o.providerRespId)
		 	.append(providerRespReasonId, o.providerRespReasonId)
		 	.append(schedule, o.schedule).append(spnId, o.spnId)
		 	.append(tierId, o.tierId).append(totalHours, o.totalHours)
		 	.append(totalLabor, o.totalLabor).append(vendorId, o.vendorId);
		return compareToBuilder.toComparison();
	}

	public boolean equals(Object aThat){
	    if ( this == aThat ) return true;
	    if ( !(aThat instanceof RoutedProvider) ) return false;
	    RoutedProvider o = (RoutedProvider)aThat;
	    EqualsBuilder equalsBuilder = new EqualsBuilder()
			.append(emailSent, o.emailSent).append(increaseSpendLimit, o.increaseSpendLimit)
		 	.append(offerExpirationDate, o.offerExpirationDate)
		 	.append(partsMaterials, o.partsMaterials).append(priceModel, o.priceModel)
		 	.append(providerResourceId, o.providerResourceId).append(providerRespComment, o.providerRespComment)
		 	.append(providerRespDate, o.providerRespDate).append(providerRespId, o.providerRespId)
		 	.append(providerRespReasonId, o.providerRespReasonId)
		 	.append(schedule, o.schedule).append(spnId, o.spnId)
		 	.append(tierId, o.tierId).append(totalHours, o.totalHours)
		 	.append(totalLabor, o.totalLabor).append(vendorId, o.vendorId);
	    
	    return equalsBuilder.isEquals();
	}

	/**
     * @return the counterOffers
     */
    public Set<SOCounterOfferReason> getCounterOffers() {
        return counterOffers;
    }

	public Boolean getEmailSent() {
		return emailSent;
	}

	public BigDecimal getIncreaseSpendLimit() {
		return increaseSpendLimit;
	}

	public Date getOfferExpirationDate() {
		return offerExpirationDate;
	}

	public Date getOfferExpirationSDate() {
		return offerExpirationSDate;
	}

	public BigDecimal getPartsMaterials() {
		return partsMaterials;
	}

	public PriceModelType getPriceModel() {
		return PriceModelType.valueOf(priceModel);
	}

	public Long getProviderResourceId() {
		return providerResourceId;
	}

	public String getProviderRespComment() {
		return providerRespComment;
	}

	public Date getProviderRespDate() {
		return providerRespDate;
	}

	public ProviderResponseType getProviderResponse() {
		if (providerRespId == null) return null;
		return ProviderResponseType.fromId(providerRespId);
	}


	public Integer getProviderRespReasonId() {
		return providerRespReasonId;
	}

	public Date getRoutedDate() {
		return routedDate;
	}

	public Integer getRoutedProviderId() {
		return routedProviderId;
	}

	public SOScheduleDate getSchedule() {
		if(this.schedule != null 
				&& this.getServiceOrder() != null 
				&& StringUtils.isNotBlank(this.getServiceOrder().getServiceLocationTimeZone())){
			schedule.populateLocalTime(this.getServiceOrder().getServiceLocationTimeZone());
		}
		return schedule;
	}

	public Integer getSpnId() {
		return spnId;
	}

	public Integer getTierId() {
		return tierId;
	}

	public Double getTotalHours() {
		return totalHours;
	}

	public BigDecimal getTotalLabor() {
		return totalLabor;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	/**
   	* A class that overrides equals must also override hashCode.
  	*/
	@Override 
	public int hashCode(){
		HashCodeBuilder hcb = new HashCodeBuilder();
		hcb.append(emailSent).append(increaseSpendLimit)
		 	.append(offerExpirationDate)
		 	.append(partsMaterials).append(priceModel)
		 	.append(providerResourceId).append(providerRespComment)
		 	.append(providerRespDate).append(providerRespId)
		 	.append(providerRespReasonId)
		 	.append(schedule).append(spnId)
		 	.append(tierId).append(totalHours)
		 	.append(totalLabor).append(vendorId);
		return hcb.toHashCode();
	}

	/**
     * @param counterOffers the counterOffers to set
     */
    @XmlElement
    public void setCounterOffers(Set<SOCounterOfferReason> counterOffers) {
        this.counterOffers = counterOffers;
    }

	@XmlElement()
	public void setEmailSent(Boolean emailSent) {
		this.emailSent = emailSent;
	}

	@XmlElement()
	public void setIncreaseSpendLimit(BigDecimal increaseSpendLimit) {
		this.increaseSpendLimit = increaseSpendLimit;
	}

	@XmlElement()
	public void setOfferExpirationDate(Date offerExpirationDate) {
		this.offerExpirationDate = offerExpirationDate;
	}

	@XmlElement()
	public void setOfferExpirationSDate(Date offerExpirationSDate) {
		this.offerExpirationSDate = offerExpirationSDate;
	}

	@XmlElement()
	public void setPartsMaterials(BigDecimal partsMaterials) {
		this.partsMaterials = partsMaterials;
	}

	@XmlElement()
	public void setPriceModel(PriceModelType model) {
		this.priceModel = model.name();
	}

	@XmlElement()
	public void setProviderResourceId(Long providerResourceId) {
		this.providerResourceId = providerResourceId;
	}

	@XmlElement()
	public void setProviderRespComment(String providerRespComment) {
		this.providerRespComment = providerRespComment;
	}

	@XmlElement()
	public void setProviderRespDate(Date providerRespDate) {
		this.providerRespDate = providerRespDate;
	}

	@XmlElement()
	public void setProviderResponse(ProviderResponseType response) {
		this.providerRespId = response.getId();
	}

	@XmlElement()
	public void setProviderRespReasonId(Integer providerRespReasonId) {
		this.providerRespReasonId = providerRespReasonId;
	}

	@XmlElement()
	public void setRoutedDate(Date routedDate) {
		this.routedDate = routedDate;
	}

    @XmlElement()
	public void setRoutedProviderId(Integer routedProviderId) {
		this.routedProviderId = routedProviderId;
	}

    public Double getPerfScore() {
		return perfScore;
	}

	public void setPerfScore(Double perfScore) {
		this.perfScore = perfScore;
	}

	public Double getFirmPerfScore() {
		return firmPerfScore;
	}

	public void setFirmPerfScore(Double firmPerfScore) {
		this.firmPerfScore = firmPerfScore;
	}

	@XmlElement()
    public void setSchedule(SOScheduleDate schedule) {
		this.schedule = schedule;
		if(this.schedule != null 
				&& this.getServiceOrder() != null 
				&& StringUtils.isNotBlank(this.getServiceOrder().getServiceLocationTimeZone())){
			schedule.populateGMT(this.getServiceOrder().getServiceLocationTimeZone());
		}
	}
    @XmlElement()
	public void setSpnId(Integer spnId) {
		this.spnId = spnId;
	}

	@XmlElement()
	public void setTierId(Integer tierId) {
		this.tierId = tierId;
	}
	
	@XmlElement()
	public void setTotalHours(Double totalHours) {
		this.totalHours = totalHours;
	}
	
	@XmlElement()
	public void setTotalLabor(BigDecimal totalLabor) {
		this.totalLabor = totalLabor;
	}
	
	@XmlElement()
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	
	public Integer getNextTier() {
		return nextTier;
	}

	public void setNextTier(Integer nextTier) {
		this.nextTier = nextTier;
	}

	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}

	// validate this
    public List<String> validate() {
        List<String> errors = new ArrayList<String>();
        if (providerResourceId == null){
            errors.add("Please provide Provider's resource id");
        }
        if(vendorId ==null){
            errors.add("Please provide Provider's vendor id");
        }
        
        return errors;
    }
    
    public void widthdrawConditionalOffer(){
    	removeConditionalOffer();
        this.setProviderResponse(ProviderResponseType.WITHDRAW_CONDITIONAL_OFFER);
    }
    
    public void expireConditionalOffer(){
    	this.getCounterOffers().clear();
        this.setProviderResponse(ProviderResponseType.EXPIRED);
    }
    
    public void removeConditionalOffer(){
        this.getCounterOffers().clear(); 
        this.providerRespId = null;
        this.increaseSpendLimit = null;
        this.providerRespReasonId = null;
        this.schedule = null;
        this.offerExpirationDate = null;
        this.offerExpirationSDate = null;
        this.totalHours = null;
        this.totalLabor = null;
        this.partsMaterials = null;
    }

    public Date getConditionalOfferDate(){
        if(null != this.providerRespId && this.providerRespId == ProviderResponseType.CONDITIONAL_OFFER.getId()){
	    	if(null == this.counterOffers || this.counterOffers.size() == 0) return null;
	        return this.counterOffers.iterator().next().getCreatedDate();
        }else{
        	return null;
        }
    }
    
    public void setProviderRespId(Integer providerRespId) {
		this.providerRespId = providerRespId;
	}
}
