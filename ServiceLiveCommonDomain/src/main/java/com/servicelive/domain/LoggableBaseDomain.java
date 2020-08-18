/**
 * 
 */
package com.servicelive.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * @author hoza
 *
 */
@MappedSuperclass
public abstract class LoggableBaseDomain extends BaseDomain {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5458947571987712418L;
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
