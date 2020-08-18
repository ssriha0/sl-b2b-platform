package com.servicelive.domain.routingrules;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToMany;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

import com.servicelive.domain.LoggableBaseDomain;
import com.servicelive.domain.provider.ProviderFirm;

/**
 * RoutingRuleVendor entity.
 * 
 */
@Entity
@Table(name = "routing_rule_vendor")
public class RoutingRuleVendor extends LoggableBaseDomain {

	private static final long serialVersionUID = 20091005L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "routing_rule_vendor_id", unique = true, nullable = false)
	private Integer routingRuleVendorId;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="routing_rule_hdr_id", nullable = false)
    @ForeignKey(name="FK_routing_rule_vendor_routing_rule_hdr_id")
	private RoutingRuleHdr routingRuleHdr;
	
	@Column(name = "auto_accept_status", unique = false, nullable = true)
	private String autoAcceptStatus;
	
	public String getAutoAcceptStatus() {
		return autoAcceptStatus;
	}

	public void setAutoAcceptStatus(String autoAcceptStatus) {
		this.autoAcceptStatus = autoAcceptStatus;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="vendor_id", nullable = false)
    @ForeignKey(name="FK_routing_rule_vendor_vendor_id")
	private ProviderFirm vendor;
	
	/** 
	 * default constructor
	 */
	public RoutingRuleVendor() {
		super();
	}

	/**
	 * @return the routingRuleVendorId
	 */
	public Integer getRoutingRuleVendorId() {
		return routingRuleVendorId;
	}

	/**
	 * @param routingRuleVendorId the routingRuleVendorId to set
	 */
	public void setRoutingRuleVendorId(Integer routingRuleVendorId) {
		this.routingRuleVendorId = routingRuleVendorId;
	}

	/**
	 * @return the routingRuleHdr
	 */
	public RoutingRuleHdr getRoutingRuleHdr() {
		return routingRuleHdr;
	}

	/**
	 * @param routingRuleHdr the routingRuleHdr to set
	 */
	public void setRoutingRuleHdr(RoutingRuleHdr routingRuleHdr) {
		this.routingRuleHdr = routingRuleHdr;
	}

	/**
	 * @return the vendor
	 */
	public ProviderFirm getVendor() {
		return vendor;
	}

	/**
	 * @param vendor the vendor to set
	 */
	public void setVendor(ProviderFirm vendor) {
		this.vendor = vendor;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "RoutingRuleVendor [routingRuleHdr=" + routingRuleHdr.getRoutingRuleHdrId() 
			+ ", routingRuleVendorId=" + routingRuleVendorId 
			+ ", vendor=" + vendor
			+ ", autoAcceptStatus="+ autoAcceptStatus
			+ ", getModifiedBy()=" + getModifiedBy() 
			+ ", getCreatedDate()=" + getCreatedDate() 
			+ ", getModifiedDate()=" + getModifiedDate()
			+ "]";
	}
	
}