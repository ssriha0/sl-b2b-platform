package com.servicelive.domain.routingrules;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

import com.servicelive.domain.LoggableBaseDomain;
import com.servicelive.domain.lookup.LookupRoutingAlertType;

/**
 * RoutingRuleAlert entity.
 * 
 */
@Entity
@Table(name = "routing_rule_alert")
public class RoutingRuleAlert extends LoggableBaseDomain {

	private static final long serialVersionUID = 20090921L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "routing_rule_alert_id", unique = true, nullable = false)
	private Integer routingRuleAlertId;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.REFRESH)
    @JoinColumn(name="routing_rule_hdr_id")
    @ForeignKey(name="FK_routing_rule_alert_routing_rule_hdr_id")
	private RoutingRuleHdr routingRuleHdr;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="alert_type_id")
    @ForeignKey(name="FK_routing_rule_alert_alert_type_id")
	private LookupRoutingAlertType alertType;
	
	@Column(name = "alert_status")
	private String alertStatus;

	/** default constructor */
	public RoutingRuleAlert() {
		super();
	}

	/**
	 * @return the routingRuleAlertId
	 */
	public Integer getRoutingRuleAlertId() {
		return routingRuleAlertId;
	}

	/**
	 * @param routingRuleAlertId the routingRuleAlertId to set
	 */
	public void setRoutingRuleAlertId(Integer routingRuleAlertId) {
		this.routingRuleAlertId = routingRuleAlertId;
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
	 * @return the alertType
	 */
	public LookupRoutingAlertType getAlertType() {
		return alertType;
	}

	/**
	 * @param alertType the alertType to set
	 */
	public void setAlertType(LookupRoutingAlertType alertType) {
		this.alertType = alertType;
	}

	/**
	 * @return the alertStatus
	 */
	public String getAlertStatus() {
		return alertStatus;
	}

	/**
	 * @param alertStatus the alertStatus to set
	 */
	public void setAlertStatus(String alertStatus) {
		this.alertStatus = alertStatus;
	}


}