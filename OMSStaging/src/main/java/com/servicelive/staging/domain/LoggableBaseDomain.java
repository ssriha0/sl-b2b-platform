/**
 * 
 */
package com.servicelive.staging.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * @author hoza
 *
 */
@MappedSuperclass
public abstract class LoggableBaseDomain extends BaseDomain {
	@Column(name= "modified_by", nullable = false, length =50)
	private String modifiedBy;

	/**
	 * @return the modifiedBy
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
}
