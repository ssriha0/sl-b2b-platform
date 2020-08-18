package com.servicelive.domain.spn.network;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * SimpleSPNTierReleaseInfo used for direct access to tier release info.
 */
@Entity
@Table(name="spnet_release_tier_minutes" )
public class SimpleSPNTierReleaseInfo {
    @EmbeddedId
    private SimpleSPNTierPK spnTierReleasePK;

    @Column(name="advanced_minutes" )
    private Long advancedMinutes;

    @Column (name="advanced_hours" )
    private Long advancedHours;

    @Column (name="advanced_days" )
    private Long advancedDays;
    
    @Column (name="no_of_members" ) 
    private Integer numberOfProviders;

    
    public SimpleSPNTierPK getSpnTierReleasePK() {
        return spnTierReleasePK;
    }

    public void setSpnTierReleasePK(SimpleSPNTierPK spnTierReleasePK) {
        this.spnTierReleasePK = spnTierReleasePK;
    }

    public Long getAdvancedMinutes() {
        return advancedMinutes;
    }

    public void setAdvancedMinutes(Long advancedMinutes) {
        this.advancedMinutes = advancedMinutes;
    }

    public Long getAdvancedHours() {
        return advancedHours;
    }

    public void setAdvancedHours(Long advancedHours) {
        this.advancedHours = advancedHours;
    }

    public Long getAdvancedDays() {
        return advancedDays;
    }

    public void setAdvancedDays(Long advancedDays) {
        this.advancedDays = advancedDays;
    }

	public Integer getNumberOfProviders() {
		return numberOfProviders;
	}

	public void setNumberOfProviders(Integer numberOfProviders) {
		this.numberOfProviders = numberOfProviders;
	}
}
