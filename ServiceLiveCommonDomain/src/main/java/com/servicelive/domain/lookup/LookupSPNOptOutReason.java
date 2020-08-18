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
@Table ( name = "lu_spnet_opt_out_reasons")
public class LookupSPNOptOutReason  extends AbstractLookupDomain {

	private static final long serialVersionUID = -20090119L;

	@Id
	@Column ( name = "id", unique = true, insertable = true, length = 30, updatable =true , nullable = false)
	private String id;

	@Column ( name = "reason", insertable = true, length = 50, updatable =true , nullable = false)
	private String description;

	/* (non-Javadoc)
	 * @see com.servicelive.domain.AbstractLookupDomain#getId()
	 */
	@Override
	public Object getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/* (non-Javadoc)
	 * @see com.servicelive.domain.AbstractLookupDomain#getDescription()
	 */
	@Override
	public Object getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
