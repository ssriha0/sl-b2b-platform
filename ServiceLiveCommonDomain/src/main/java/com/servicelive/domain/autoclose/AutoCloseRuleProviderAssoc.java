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
import com.servicelive.domain.provider.ServiceProvider;

/**
 * AutoCloseRuleProviderAssoc entity.
 * 
 */
@Entity
@Table(name = "auto_close_rule_provider_assoc")
public class AutoCloseRuleProviderAssoc extends LoggableBaseDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8098339314044466367L;

	// Fields
	/**
	 * 
	 */
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "auto_close_rule_provider_assoc_id", unique = true, nullable = false)
	private Integer autoCloseRuleProvAssocId;
	
    @Column(name = "auto_close_rule_hdr_id")
    private Integer autoCloseRuleHdrId;
    
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="provider_id", referencedColumnName="resource_id")
    @ForeignKey(name="FK_auto_close_rule_provider_id")
	private ServiceProvider provider;
	
    @Column(name = "excluding_reason")
    private String autoCloseRuleProvExcludeReason;
    
    @Column(name = "active_ind")
    private boolean active;

	// Constructors

	/** default constructor */
	public AutoCloseRuleProviderAssoc() {
		super();
	}

	public Integer getAutoCloseRuleProvAssocId() {
		return autoCloseRuleProvAssocId;
	}

	public void setAutoCloseRuleProvAssocId(Integer autoCloseRuleProvAssocId) {
		this.autoCloseRuleProvAssocId = autoCloseRuleProvAssocId;
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
	
}