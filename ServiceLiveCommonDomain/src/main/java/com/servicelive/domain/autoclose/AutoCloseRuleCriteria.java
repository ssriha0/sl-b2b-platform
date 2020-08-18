package com.servicelive.domain.autoclose;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.servicelive.domain.LoggableBaseDomain;

/**
 * AutoCloseRuleCriteria entity.
 * 
 */
@Entity
@Table(name = "auto_close_rule_criteria")
public class AutoCloseRuleCriteria extends LoggableBaseDomain {

	// Fields
	/**
	 * 
	 */
	private static final long serialVersionUID = -7485413036943297994L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "auto_close_rule_criteria_id", unique = true, nullable = false)
	private Integer autoCloseRuleCriteriaId;
	
    @Column(name = "auto_close_rule_hdr_id")
    private Integer autoCloseRuleHdrId;
	
    @Column(name = "rule_id")
    private Integer autoCloseRuleId;
	
    @Column(name = "criteria_value")
    private String autoCloseRuleCriteriaValue;

	// Constructors

	/** default constructor */
	public AutoCloseRuleCriteria() {
		super();
	}

	public Integer getAutoCloseRuleCriteriaId() {
		return autoCloseRuleCriteriaId;
	}

	public void setAutoCloseRuleCriteriaId(Integer autoCloseRuleCriteriaId) {
		this.autoCloseRuleCriteriaId = autoCloseRuleCriteriaId;
	}

	public String getAutoCloseRuleCriteriaValue() {
		return autoCloseRuleCriteriaValue;
	}

	public void setAutoCloseRuleCriteriaValue(String autoCloseRuleCriteriaValue) {
		this.autoCloseRuleCriteriaValue = autoCloseRuleCriteriaValue;
	}

	public Integer getAutoCloseRuleHdrId() {
		return autoCloseRuleHdrId;
	}

	public void setAutoCloseRuleHdrId(Integer autoCloseRuleHdrId) {
		this.autoCloseRuleHdrId = autoCloseRuleHdrId;
	}

	public Integer getAutoCloseRuleId() {
		return autoCloseRuleId;
	}

	public void setAutoCloseRuleId(Integer autoCloseRuleId) {
		this.autoCloseRuleId = autoCloseRuleId;
	}
	
}