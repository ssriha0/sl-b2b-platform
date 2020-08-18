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
@Table ( name = "lu_spnet_document_state")
public class LookupSPNDocumentState extends AbstractLookupDomain {

	private static final long serialVersionUID = -20090114L;

	@Id
	@Column ( name = "doc_state_id", unique = true, insertable = true, length = 30, updatable =true , nullable = false)
	private String id;

	@Column ( name = "descr", insertable = true, length = 45, updatable = true, nullable = false)
	private String description;

	@Column ( name = "is_actionable", nullable = true)
	private Boolean isActionable = Boolean.FALSE;

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
	/**
	 * 
	 * @return Boolean
	 */
	public Boolean getIsActionable() {
		return isActionable;
	}
	/**
	 * 
	 * @param isActionable
	 */
	public void setIsActionable(Boolean isActionable) {
		this.isActionable = isActionable;
	}
}
