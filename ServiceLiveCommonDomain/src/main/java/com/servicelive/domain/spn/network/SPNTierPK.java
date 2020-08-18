/**
 * 
 */
package com.servicelive.domain.spn.network;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.servicelive.domain.lookup.LookupReleaseTier;

/**
 * @author hoza
 *
 */
@Embeddable
public class SPNTierPK implements Serializable {

	private static final long serialVersionUID = 20090511L;

	@ManyToOne(cascade = {CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "spn_id", unique = false, nullable = false, insertable = false, updatable = false)
	private SPNHeader spnId;
	
	
	@ManyToOne(cascade = {CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "tier_id", unique = false, nullable = false, insertable = true, updatable = true)
	private LookupReleaseTier tierId;

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
	 * @return the tierId
	 */
	public LookupReleaseTier getTierId() {
		return tierId;
	}

	/**
	 * @param tierId the tierId to set
	 */
	public void setTierId(LookupReleaseTier tierId) {
		this.tierId = tierId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((spnId == null) ? 0 : spnId.hashCode());
		result = prime * result + ((tierId == null) ? 0 : tierId.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final SPNTierPK other = (SPNTierPK) obj;
		if (spnId == null) {
			if (other.spnId != null)
				return false;
		} else if (!spnId.equals(other.spnId))
			return false;
		if (tierId == null) {
			if (other.tierId != null)
				return false;
		} else if (!tierId.equals(other.tierId))
			return false;
		return true;
	}
	

}
