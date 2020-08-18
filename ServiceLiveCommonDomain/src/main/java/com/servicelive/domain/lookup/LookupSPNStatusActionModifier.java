package com.servicelive.domain.lookup;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="lu_spnet_status_action_modifier")
public class LookupSPNStatusActionModifier {

	//modified_by_id	varchar(30)	NO	PRI	(null)
	@Id
	@Column ( name = "modified_by_id", unique = true, insertable = true, length = 30, updatable = false , nullable = false)
	private String id;

	//system_user_ind	int(1)	NO		1
	@Column ( name = "system_user_ind", insertable = false, length = 1, updatable = false, nullable = false)
	private Boolean systemUserInd;

	//descr	varchar(255)	YES		(null)
	@Column ( name = "descr", insertable = false, length = 255, updatable = false, nullable = false)
	private String description;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the systemUserInd
	 */
	public Boolean getSystemUserInd() {
		return systemUserInd;
	}

	/**
	 * @param systemUserInd the systemUserInd to set
	 */
	public void setSystemUserInd(Boolean systemUserInd) {
		this.systemUserInd = systemUserInd;
	}

	/**
	 * @return the description
	 */
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
