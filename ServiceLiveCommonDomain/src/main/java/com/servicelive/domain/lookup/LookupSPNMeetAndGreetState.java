package com.servicelive.domain.lookup;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.servicelive.domain.AbstractLookupDomain;

/**
 * 
 * @author svanloon
 *
 */
@Entity
@Table (name = "lu_spnet_meetngreet_state")
public class LookupSPNMeetAndGreetState extends AbstractLookupDomain {

	private static final long serialVersionUID = 20090128L;

	@Id
	@Column ( name = "meetngreet_state_id", unique = true, insertable = true, length = 30, updatable =true , nullable = false)
	private String id;

	@Column ( name = "descr", insertable = true, length = 45, updatable =true , nullable = false)
	private String description;

	/**
	 * @return the id
	 */
	@Override
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
