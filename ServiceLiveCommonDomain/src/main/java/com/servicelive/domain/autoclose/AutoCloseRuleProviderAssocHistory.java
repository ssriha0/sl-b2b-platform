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
import javax.persistence.Transient;

import com.servicelive.domain.LoggableBaseDomain;
import com.servicelive.domain.provider.ServiceProvider;

/**
 * AutoCloseRuleProviderAssocHistory entity.
 * 
 */
@Entity
@Table(name = "autoclose_rule_provider_assoc_hist")
public class AutoCloseRuleProviderAssocHistory extends LoggableBaseDomain {


	/**
	 * 
	 */
	private static final long serialVersionUID = -8837052537664407607L;

	// Fields
	/**
	 * 
	 */
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "autoclose_rule_provider_assoc_hist_id", unique = true, nullable = false)
	private Integer autoCloseRuleProvAssocHistId;
	
	@Column(name = "auto_close_rule_provider_assoc_id", nullable = false)
	private Integer autoCloseRuleProvAssocId;	
	
    @Column(name = "auto_close_rule_hdr_id")
    private Integer autoCloseRuleHdrId;
    
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="provider_id", referencedColumnName="resource_id")
	private ServiceProvider provider;
	
    @Column(name = "excluding_reason")
    private String autoCloseRuleProvExcludeReason;
    
    @Column(name = "active_ind")
    private boolean active;
    
    @Transient
	private String modifiedDateFormatted;    

	// Constructors

	/** default constructor */
	public AutoCloseRuleProviderAssocHistory() {
		super();
	}

	public Integer getAutoCloseRuleProvAssocId() {
		return autoCloseRuleProvAssocId;
	}

	public void setAutoCloseRuleProvAssocId(Integer autoCloseRuleProvAssocId) {
		this.autoCloseRuleProvAssocId = autoCloseRuleProvAssocId;
	}

	public Integer getAutoCloseRuleProvAssocHistId() {
		return autoCloseRuleProvAssocHistId;
	}

	public void setAutoCloseRuleProvAssocHistId(Integer autoCloseRuleProvAssocHistId) {
		this.autoCloseRuleProvAssocHistId = autoCloseRuleProvAssocHistId;
	}

	public Integer getAutoCloseRuleHdrId() {
		return autoCloseRuleHdrId;
	}

	public void setAutoCloseRuleHdrId(Integer autoCloseRuleHdrId) {
		this.autoCloseRuleHdrId = autoCloseRuleHdrId;
	}

	public ServiceProvider getProvider() {
		return provider;
	}

	public void setProvider(ServiceProvider provider) {
		this.provider = provider;
	}

	public String getAutoCloseRuleProvExcludeReason() {
		return autoCloseRuleProvExcludeReason;
	}

	public void setAutoCloseRuleProvExcludeReason(
			String autoCloseRuleProvExcludeReason) {
		this.autoCloseRuleProvExcludeReason = autoCloseRuleProvExcludeReason;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getModifiedDateFormatted() {
		return modifiedDateFormatted;
	}

	public void setModifiedDateFormatted(String modifiedDateFormatted) {
		this.modifiedDateFormatted = modifiedDateFormatted;
	}
	
}