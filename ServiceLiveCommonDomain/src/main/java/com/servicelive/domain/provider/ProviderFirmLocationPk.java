package com.servicelive.domain.provider;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.servicelive.domain.common.Location;

/**
 * 
 * @author svanloon
 *
 */
@Embeddable
public class ProviderFirmLocationPk implements Serializable {

	private static final long serialVersionUID = 20090202L;

	@ManyToOne
    @JoinColumn(name="vendor_id", unique=false, nullable= false, insertable=false, updatable=false)
	private ProviderFirm providerFirm;

	@ManyToOne
    @JoinColumn(name="locn_id", unique=false, nullable= false, insertable=false, updatable=false)
	private Location location;

	/**
	 * @return the providerFirm
	 */
	public ProviderFirm getProviderFirm() {
		return providerFirm;
	}

	/**
	 * @param providerFirm the providerFirm to set
	 */
	public void setProviderFirm(ProviderFirm providerFirm) {
		this.providerFirm = providerFirm;
	}

	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
		result = prime * result
				+ ((providerFirm == null) ? 0 : providerFirm.hashCode());
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
		final ProviderFirmLocationPk other = (ProviderFirmLocationPk) obj;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (providerFirm == null) {
			if (other.providerFirm != null)
				return false;
		} else if (!providerFirm.equals(other.providerFirm))
			return false;
		return true;
	}
}
