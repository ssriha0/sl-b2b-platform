package com.servicelive.domain.routingrules;

import java.math.BigDecimal;

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

/**
 * RoutingRulePrice entity.
 * 
 */
@Entity
@Table(name = "routing_rule_price")
public class RoutingRulePrice extends LoggableBaseDomain{

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -6186127295615900662L;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "routing_rule_price_id", unique = true, nullable = false)
	private Integer routingRulePriceId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="routing_rule_hdr_id")
    @ForeignKey(name="fk_routing_rule_hdr_id")
	private RoutingRuleHdr routingRuleHdr;
	
	@Column(name = "jobcode")
	private String jobcode;
	
	@Column(name = "price")
	private BigDecimal price;

	// Constructors

	/** default constructor */
	public RoutingRulePrice() {
		super();
	}


	// Property accessors
	
	public Integer getRoutingRulePriceId() {
		return this.routingRulePriceId;
	}

	public void setRoutingRulePriceId(Integer routingRulePriceId) {
		this.routingRulePriceId = routingRulePriceId;
	}

	public String getJobcode() {
		return this.jobcode;
	}

	public void setJobcode(String jobcode) {
		this.jobcode = jobcode;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}


	public RoutingRuleHdr getRoutingRuleHdr() {
		return routingRuleHdr;
	}


	public void setRoutingRuleHdr(RoutingRuleHdr routingRuleHdr) {
		this.routingRuleHdr = routingRuleHdr;
	}


}