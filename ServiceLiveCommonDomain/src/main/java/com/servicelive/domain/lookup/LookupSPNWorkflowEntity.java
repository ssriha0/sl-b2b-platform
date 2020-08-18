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
@Table (name = "lu_spnet_workflow_entity")
public class LookupSPNWorkflowEntity extends AbstractLookupDomain {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4595226172992607242L;

	@Id  
	@Column ( name = "id", unique = true, insertable = true, length = 20, updatable =true , nullable = false)
	private String id;

	@Column ( name = "descr", insertable = true, length = 50, updatable =true , nullable = false)
	private String description;

	/* (non-Javadoc)
	 * @see com.servicelive.domain.AbstractLookupDomain#getDescription()
	 */
	@Override
	public Object getDescription() {
		return this.description;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.domain.AbstractLookupDomain#getId()
	 */
	@Override
	public Object getId() {
		return this.id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
