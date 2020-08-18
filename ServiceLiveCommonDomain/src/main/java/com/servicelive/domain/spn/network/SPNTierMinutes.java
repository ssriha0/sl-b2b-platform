/**
 * 
 */
package com.servicelive.domain.spn.network;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.servicelive.domain.LoggableBaseDomain;

/**
 * @author hoza
 *
 */
@Entity
@Table ( name = "spnet_release_tier_minutes")
public class SPNTierMinutes extends LoggableBaseDomain {
	
	private static final long serialVersionUID = 20090511L;

	@EmbeddedId
	private SPNTierPK spnTierPK ;
	
	@Column (name="advanced_minutes" )
	private Integer advancedMinutes;
	
	@Column (name="advanced_hours" )
	private Integer advancedHours;
	
	@Column (name="advanced_days" )
	private Integer advancedDays;
	
	@Column (name="no_of_members" )
	private Integer noOfMembers;

	/**
	 * @return the spnTierPK
	 */
	public SPNTierPK getSpnTierPK() {
		return spnTierPK;
	}

	/**
	 * @param spnTierPK the spnTierPK to set
	 */
	public void setSpnTierPK(SPNTierPK spnTierPK) {
		this.spnTierPK = spnTierPK;
	}

	
	public SPNTierMinutes() {
		super();
	}

	public Integer getAdvancedMinutes() {
		return advancedMinutes;
	}

	public void setAdvancedMinutes(Integer advancedMinutes) {
		this.advancedMinutes = advancedMinutes;
	}

	public Integer getAdvancedDays() {
		return advancedDays;
	}

	public void setAdvancedDays(Integer advancedDays) {
		this.advancedDays = advancedDays;
	}

	/**
	 * @return the advancedHours
	 */
	public Integer getAdvancedHours() {
		return advancedHours;
	}

	/**
	 * @param advancedHours the advancedHours to set
	 */
	public void setAdvancedHours(Integer advancedHours) {
		this.advancedHours = advancedHours;
	}

	public Integer getNoOfMembers() {
		return noOfMembers;
	}

	public void setNoOfMembers(Integer noOfMembers) {
		this.noOfMembers = noOfMembers;
	}	
	 
}
