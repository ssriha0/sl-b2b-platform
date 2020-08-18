package com.servicelive.domain.autoclose;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.servicelive.domain.LoggableBaseDomain;

/**
 * AutoCloseRuleCriteria entity.
 * 
 */
@Entity
@Table(name = "autoclose_rule_criteria_hist")
public class AutoCloseRuleCriteriaHistory extends LoggableBaseDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1589496945496023396L;

	// Fields
	/**
	 * 
	 */
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "autoclose_rule_criteria_hist_id", unique = true, nullable = false)
	private Integer autoCloseRuleCriteriaHistId;
	
	@Column(name = "auto_close_rule_criteria_id")
	private Integer autoCloseRuleCriteriaId;
	
    @Column(name = "auto_close_rule_hdr_id")
    private Integer autoCloseRuleHdrId;
	
    @Column(name = "rule_id")
    private Integer autoCloseRuleId;
	
    @Column(name = "criteria_value")
    private String autoCloseRuleCriteriaValue;
    
    @Transient
	private String modifiedDateFormatted;

	// Constructors

	/** default constructor */
	public AutoCloseRuleCriteriaHistory() {
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

	public Integer getAutoCloseRuleCriteriaHistId() {
		return autoCloseRuleCriteriaHistId;
	}

	public void setAutoCloseRuleCriteriaHistId(Integer autoCloseRuleCriteriaHistId) {
		this.autoCloseRuleCriteriaHistId = autoCloseRuleCriteriaHistId;
	}

	public String getModifiedDateFormatted() {
		return modifiedDateFormatted;
	}

	public void setModifiedDateFormatted(String modifiedDateFormatted) {
		this.modifiedDateFormatted = modifiedDateFormatted;
	}
	
}