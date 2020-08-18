package com.servicelive.domain.autoclose;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

import com.servicelive.domain.LoggableBaseDomain;

/**
 * AutoCloseRuleHdr entity.
 * 
 */
@Entity
@Table(name = "auto_close_rule_hdr")
public class AutoCloseRuleHdr extends LoggableBaseDomain {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5405708666300608558L;

	/**
	 * 
	 */
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "auto_close_rule_hdr_id", unique = true, nullable = false)
	private Integer autoCloseRuleHdrId;
	
	@Column(name = "buyer_id")
	private Integer buyerId;
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="auto_close_rule_id")
    @ForeignKey(name="FK_auto_close_rule_hdr")
	private AutoCloseRules AutoCloseRules;
	
    @Column(name = "active_ind")
    private boolean active;
    
	/*@OneToOne(fetch=FetchType.LAZY, cascade= { CascadeType.ALL})
	@JoinColumn(name="auto_close_rule_hdr_id")
	private AutoCloseRuleCriteria autoCloseRuleCriteria;*/

	// Constructors

	/** default constructor */
	public AutoCloseRuleHdr() {
		super();
	}

	public Integer getAutoCloseRuleHdrId() {
		return autoCloseRuleHdrId;
	}

	public void setAutoCloseRuleHdrId(Integer autoCloseRuleHdrId) {
		this.autoCloseRuleHdrId = autoCloseRuleHdrId;
	}

	public Integer getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}

	public AutoCloseRules getAutoCloseRules() {
		return AutoCloseRules;
	}

	public void setAutoCloseRules(AutoCloseRules autoCloseRules) {
		AutoCloseRules = autoCloseRules;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	/*public AutoCloseRuleCriteria getAutoCloseRuleCriteria() {
		return autoCloseRuleCriteria;
	}

	public void setAutoCloseRuleCriteria(AutoCloseRuleCriteria autoCloseRuleCriteria) {
		this.autoCloseRuleCriteria = autoCloseRuleCriteria;
	}*/

	
	// Property accessors

	

}