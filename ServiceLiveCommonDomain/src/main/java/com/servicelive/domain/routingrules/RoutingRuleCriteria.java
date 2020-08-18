package com.servicelive.domain.routingrules;

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
import com.servicelive.domain.lookup.LookupRoutingRuleType;

/**
 * RoutingRuleCriteria entity.
 * 
 */
@Entity
@Table(name = "routing_rule_criteria")
public class RoutingRuleCriteria extends LoggableBaseDomain {

	private static final long serialVersionUID = 20091005L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "routing_rule_criteria_id", unique = true, nullable = false)
	private Integer routingRuleCriteriaId;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="routing_rule_hdr_id", nullable = false)
    @ForeignKey(name="FK_routing_rule_criteria_routing_rule_hdr_id")
	private RoutingRuleHdr routingRuleHdr;
	
	@Column(name = "criteria_name", length = 30, nullable = false)	
	private String criteriaName;
	
	@Column(name = "criteria_value", length = 30, nullable = false)
	private String criteriaValue;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="rule_type_id", nullable = false)
    @ForeignKey(name="FK_routing_rule_criteria_rule_type_id")
	private LookupRoutingRuleType ruleType;

	// Constructors

	/** default constructor */
	public RoutingRuleCriteria() {
		super();
	}


	// Property accessors

	public Integer getRoutingRuleCriteriaId() {
		return this.routingRuleCriteriaId;
	}

	public void setRoutingRuleCriteriaId(Integer routingRuleCriteriaId) {
		this.routingRuleCriteriaId = routingRuleCriteriaId;
	}

	public RoutingRuleHdr getRoutingRuleHdr() {
		return this.routingRuleHdr;
	}

	public void setRoutingRuleHdr(RoutingRuleHdr routingRuleHdr) {
		this.routingRuleHdr = routingRuleHdr;
	}

	public String getCriteriaName() {
		return this.criteriaName;
	}

	public void setCriteriaName(String criteriaName) {
		this.criteriaName = criteriaName;
	}

	public String getCriteriaValue() {
		return this.criteriaValue;
	}

	public void setCriteriaValue(String criteriaValue) {
		this.criteriaValue = criteriaValue;
	}

	public LookupRoutingRuleType getRuleType() {
		return this.ruleType;
	}

	public void setRuleTypeId(LookupRoutingRuleType ruleType) {
		this.ruleType = ruleType;
	}

}