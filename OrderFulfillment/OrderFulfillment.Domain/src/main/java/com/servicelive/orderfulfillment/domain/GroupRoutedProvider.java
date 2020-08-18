package com.servicelive.orderfulfillment.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;

import com.servicelive.orderfulfillment.domain.type.ProviderResponseType;

@Entity
@Table(name = "so_group_routed_providers")
@XmlRootElement
public class GroupRoutedProvider extends SOBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7443816566632762951L;

	@Column(name = "condl_offer_price")
	private BigDecimal condOfferPrice;
	
	@Column(name = "offer_expiration_date")
	private Date offerExpirationDate;
	
	@Column(name = "offer_expiration_sdate")
	private Date offerExpirationSDate;
	
	@Column(name = "resource_id")
	private Long providerResourceId;

	@Column(name = "provider_resp_comment")
	private String providerRespComment;
	
	@Column(name = "provider_resp_date")
	private Date providerRespDate;
	
	@Column(name = "provider_resp_id")
	private Integer providerRespId;
	
	@Column(name = "resp_reason_id")
	private Integer providerRespReasonId;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "so_group_routed_provider_id")
	private Integer routedProviderId;
	
	@Embedded
	private SOScheduleDate schedule;
	
	@ManyToOne(optional = false, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "so_group_id")
	private SOGroup soGroup;
	
	@Column(name = "vendor_id")
	private Integer vendorId;

	@Column(name = "routed_date")
	private Date routedDate;
	
	public BigDecimal getCondOfferPrice() {
		return condOfferPrice;
	}

	public Date getOfferExpirationDate() {
		return offerExpirationDate;
	}

	public Date getOfferExpirationSDate() {
		return offerExpirationSDate;
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

	public Integer getRoutedProviderId() {
		return routedProviderId;
	}

	public SOGroup getSoGroup() {
		return soGroup;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	@XmlElement()
	public void setCondOfferPrice(BigDecimal condOfferPrice) {
		this.condOfferPrice = condOfferPrice;
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
	public void setRoutedProviderId(Integer routedProviderId) {
		this.routedProviderId = routedProviderId;
	}

	@Transient()
	public void setSoGroup(SOGroup soGroup) {
		this.soGroup = soGroup;
	}

	@XmlElement()
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	
	public void afterUnmarshal(Unmarshaller  target, Object parent){
	    if(parent instanceof SOGroup)
	        this.soGroup = (SOGroup)parent;
	}

	@XmlElement()
	public void setSchedule(SOScheduleDate schedule) {
		this.schedule = schedule;
		if(this.schedule != null 
				&& this.getSoGroup() != null 
				&& this.getSoGroup().getFirstServiceOrder() != null
				&& StringUtils.isNotBlank(this.getSoGroup().getFirstServiceOrder().getServiceLocationTimeZone())){
			schedule.populateGMT(this.getSoGroup().getFirstServiceOrder().getServiceLocationTimeZone());
		}
	}

	public SOScheduleDate getSchedule() {
		if(this.schedule != null 
				&& this.getSoGroup() != null 
				&& this.getSoGroup().getFirstServiceOrder() != null
				&& StringUtils.isNotBlank(this.getSoGroup().getFirstServiceOrder().getServiceLocationTimeZone())){
			schedule.populateLocalTime(this.getSoGroup().getFirstServiceOrder().getServiceLocationTimeZone());
		}
		return schedule;
	}
	
	public void expireConditionalOffer(){
    	removeConditionalOffer();
        this.setProviderResponse(ProviderResponseType.EXPIRED);
    }
	
    public void removeConditionalOffer() {
    	
        //this.getCounterOffers().clear(); 
        this.providerRespId = null;
        this.providerRespDate = null;
        this.providerRespComment = null;
        this.condOfferPrice = null;
        this.providerRespReasonId = null;
        this.schedule = null;
        this.offerExpirationDate = null;
        this.offerExpirationSDate = null;
    }
    
	public Date getRoutedDate() {
		return routedDate;
	}

	@XmlElement()
	public void setRoutedDate(Date routedDate) {
		this.routedDate = routedDate;
	}

}
