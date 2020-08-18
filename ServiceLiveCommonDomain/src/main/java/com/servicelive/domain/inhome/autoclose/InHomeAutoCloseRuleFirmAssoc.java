package com.servicelive.domain.inhome.autoclose;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;

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
@Table(name = "inhome_auto_close_rule_firm_assoc")
public class InHomeAutoCloseRuleFirmAssoc extends LoggableBaseDomain {

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
	@Column(name = "inhome_auto_close_rule_firm_assoc_id", unique = true, nullable = false)
	private Integer inhomeAutoCloseRuleFirmAssocId;
	
    
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="firm_id", referencedColumnName="vendor_id")
    @ForeignKey(name="FK_auto_close_rule_firm_id")
	private ProviderFirm firm;
	
	@Column(name = "reimbursement_rate")
	private BigDecimal reimbursementRate;
	 
    @Column(name = "overrided_reason")
    private String overridedReason;
    
    @Column(name = "active_ind")
    private boolean active;

	// Constructors

	/** default constructor */
	public InHomeAutoCloseRuleFirmAssoc() {
		super();
	}

	public Integer getInhomeAutoCloseRuleFirmAssocId() {
		return inhomeAutoCloseRuleFirmAssocId;
	}
	
	public void setInhomeAutoCloseRuleFirmAssocId(
			Integer inhomeAutoCloseRuleFirmAssocId) {
		this.inhomeAutoCloseRuleFirmAssocId = inhomeAutoCloseRuleFirmAssocId;
	}


	public String getOverridedReason() {
		return overridedReason;
	}



	public void setOverridedReason(String overridedReason) {
		this.overridedReason = overridedReason;
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

	public BigDecimal getReimbursementRate() {
		return reimbursementRate;
	}

	public void setReimbursementRate(BigDecimal reimbursementRate) {
		this.reimbursementRate = reimbursementRate;
	}
	
}