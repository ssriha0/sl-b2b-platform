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
import com.servicelive.domain.provider.ProviderFirm;

/**
 * AutoCloseRuleFirmAssoc entity.
 * 
 */
@Entity
@Table(name = "auto_close_rule_firm_assoc")
public class AutoCloseRuleFirmAssoc extends LoggableBaseDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3705944642466806783L;

	// Fields
	/**
	 * 
	 */
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "auto_close_rule_firm_assoc_id", unique = true, nullable = false)
	private Integer autoCloseRuleFirmAssocId;
	
    @Column(name = "auto_close_rule_hdr_id")
    private Integer autoCloseRuleHdrId;
    
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="firm_id", referencedColumnName="vendor_id")
    @ForeignKey(name="FK_auto_close_rule_firm_id")
	private ProviderFirm firm;
	
    @Column(name = "excluding_reason")
    private String autoCloseRuleFirmExcludeReason;
    
    @Column(name = "active_ind")
    private boolean active;

	// Constructors

	/** default constructor */
	public AutoCloseRuleFirmAssoc() {
		super();
	}

	public Integer getAutoCloseRuleFirmAssocId() {
		return autoCloseRuleFirmAssocId;
	}

	public void setAutoCloseRuleFirmAssocId(Integer autoCloseRuleFirmAssocId) {
		this.autoCloseRuleFirmAssocId = autoCloseRuleFirmAssocId;
	}

	public Integer getAutoCloseRuleHdrId() {
		return autoCloseRuleHdrId;
	}

	public void setAutoCloseRuleHdrId(Integer autoCloseRuleHdrId) {
		this.autoCloseRuleHdrId = autoCloseRuleHdrId;
	}

	public String getAutoCloseRuleFirmExcludeReason() {
		return autoCloseRuleFirmExcludeReason;
	}

	public void setAutoCloseRuleFirmExcludeReason(
			String autoCloseRuleFirmExcludeReason) {
		this.autoCloseRuleFirmExcludeReason = autoCloseRuleFirmExcludeReason;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public ProviderFirm getFirm() {
		return firm;
	}

	public void setFirm(ProviderFirm firm) {
		this.firm = firm;
	}
	
}