/**
 * 
 */
package com.servicelive.domain.spn.network;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

import com.servicelive.domain.LoggableBaseDomain;
import com.servicelive.domain.lookup.LookupPerformanceLevel;
import com.servicelive.domain.lookup.LookupReleaseTier;

/**
 * @author hoza
 *
 */
@Entity
@Table ( name = "spnet_release_tier_performance")
public class SPNTierPerformanceLevel extends LoggableBaseDomain {
	
	private static final long serialVersionUID = 20090511L;

	@Id @GeneratedValue(strategy=IDENTITY)
	private Integer id ;

	@ManyToOne
    @JoinColumn(name="spn_id")
    @ForeignKey(name="FK_RELEASE_TIER_SPN_ID")
	private SPNHeader spnId;

	@ManyToOne(cascade = {}, fetch = FetchType.EAGER)
	@JoinColumn(name = "tier_id", unique = false, nullable = true, insertable = true, updatable = true)
	@ForeignKey(name="FK_RELEASE_TIER_ID")
	private LookupReleaseTier tierId;

	@ManyToOne(cascade = {}, fetch = FetchType.EAGER)
	@JoinColumn(name = "performance_id", unique = false, nullable = true, insertable = true, updatable = true)
	@ForeignKey(name="FK_RELEASE_TIER_PERFORMANCE_LEVEL")
	private LookupPerformanceLevel performanceLevelId;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the spnId
	 */
	public SPNHeader getSpnId() {
		return spnId;
	}

	/**
	 * @param spnId the spnId to set
	 */
	public void setSpnId(SPNHeader spnId) {
		this.spnId = spnId;
	}

	/**
	 * @return the teirId
	 */
	public LookupReleaseTier getTierId() {
		return tierId;
	}

	/**
	 * @param teirId the teirId to set
	 */
	public void setTierId(LookupReleaseTier teirId) {
		this.tierId = teirId;
	}

	/**
	 * @return the performanceLevelId
	 */
	public LookupPerformanceLevel getPerformanceLevelId() {
		return performanceLevelId;
	}

	/**
	 * @param performanceLevelId the performanceLevelId to set
	 */
	public void setPerformanceLevelId(LookupPerformanceLevel performanceLevelId) {
		this.performanceLevelId = performanceLevelId;
	}

	public SPNTierPerformanceLevel() {
		super();
	}	
	
}
