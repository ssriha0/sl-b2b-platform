/**
 * 
 */
package com.servicelive.domain.so;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

import com.servicelive.domain.LoggableBaseDomain;
import com.servicelive.domain.provider.ProviderFirm;
import com.servicelive.domain.routingrules.RoutingRuleHdr;

/**
 * @author madhup_chand
 *
 */
@Entity
@Table(name = "routing_rule_vendor")
public class RoutingRuleVendorAssoc extends LoggableBaseDomain {
	private static final long serialVersionUID = -20090823;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "routing_rule_vendor_id", unique = true, nullable = false)
	private Integer routingRuleVendorId;
	
	@Column(name="routing_rule_hdr_id")
	private Integer routingRuleid;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "auto_accept_status")
	private String autoAcceptStatus;
	
	@Column(name="vendor_id")
	private Integer vendorId;
	
	public String getAutoAcceptStatus() {
		return autoAcceptStatus;
	}

	public void setAutoAcceptStatus(String autoAcceptStatus) {
		this.autoAcceptStatus = autoAcceptStatus;
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
	 * @return the routingRuleid
	 */
	public Integer getRoutingRuleid() {
		return routingRuleid;
	}

	/**
	 * @param routingRuleid the routingRuleid to set
	 */
	public void setRoutingRuleid(Integer routingRuleid) {
		this.routingRuleid = routingRuleid;
	}

	/**
	 * @return the vendorId
	 */
	public Integer getVendorId() {
		return vendorId;
	}

	/**
	 * @param vendorId the vendorId to set
	 */
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	

}
