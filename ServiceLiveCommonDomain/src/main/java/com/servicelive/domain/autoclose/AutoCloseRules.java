package com.servicelive.domain.autoclose;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * AutoCloseRules entity.
 * 
 */
@Entity
@Table(name = "auto_close_rules")
public class AutoCloseRules {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -2673240227196673541L;

	/**
	 * 
	 */
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "auto_close_rule_id", unique = true, nullable = false)
	private Integer autoCloseRuleId;
	
	@Column(name = "rule_name", length = 20, nullable=true)
	private String ruleName;
	
	@Column(name = "rule_description", length = 40, nullable=true)
	private String ruleDescription;
	
	@Column(name = "sort_order")
	private Integer sortOrder;

	// Constructors

	/** default constructor */
	public AutoCloseRules() {
		super();
	}

	public Integer getAutoCloseRuleId() {
		return autoCloseRuleId;
	}

	public void setAutoCloseRuleId(Integer autoCloseRuleId) {
		this.autoCloseRuleId = autoCloseRuleId;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getRuleDescription() {
		return ruleDescription;
	}

	public void setRuleDescription(String ruleDescription) {
		this.ruleDescription = ruleDescription;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	// Property accessors
	
	
}