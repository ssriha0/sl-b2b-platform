package com.servicelive.domain.provider;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.servicelive.domain.LoggableBaseDomain;

/**
 * 
 * @author svanloon
 *
 */
@Entity
@Table ( name = "vendor_location")
public class ProviderFirmLocation extends LoggableBaseDomain {

	private static final long serialVersionUID = 20090202L;

	@EmbeddedId
	private ProviderFirmLocationPk id;

	/**
	 * @return the id
	 */
	public ProviderFirmLocationPk getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(ProviderFirmLocationPk id) {
		this.id = id;
	}
}
