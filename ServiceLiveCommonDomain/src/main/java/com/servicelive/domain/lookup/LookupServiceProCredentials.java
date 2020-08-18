/**
 *
 */
package com.servicelive.domain.lookup;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.servicelive.domain.AbstractLookupDomain;

/**
 * @author hoza
 *
 */

@Entity
@Table (name= "lu_resource_credential_type")
public class LookupServiceProCredentials extends AbstractLookupDomain {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4759262800420166878L;

	@Id
	@Column ( name= "cred_type_id")
	private Integer id;

	@Column ( name= "cred_type")
	private String credType;

	@Column ( name= "cred_type_desc")
	private String description;



	/**
	 * @return the credType
	 */
	public String getCredType() {
		return credType;
	}

	/**
	 * @param credType the credType to set
	 */
	public void setCredType(String credType) {
		this.credType = credType;
	}

	/**
	 * @return the id
	 */
	@Override
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
	 * @return the description
	 */
	@Override
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}





}
