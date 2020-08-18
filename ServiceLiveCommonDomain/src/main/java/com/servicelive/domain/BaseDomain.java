/**
 *
 */
package com.servicelive.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author hoza
 *
 */
@MappedSuperclass
public abstract class BaseDomain  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7818566682439631585L;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name= "created_date", nullable = false)
	private Date createdDate = new Date();

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name= "modified_date", nullable = false)
	private Date modifiedDate = new Date();


	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @return the modifiedDate
	 */
	public Date getModifiedDate() {
		return modifiedDate;
	}
	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
}
