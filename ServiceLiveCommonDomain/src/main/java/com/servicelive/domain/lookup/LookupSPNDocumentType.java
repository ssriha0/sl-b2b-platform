/**
 *
 */
package com.servicelive.domain.lookup;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.servicelive.domain.AbstractLookupDomain;

/**
 * @author hoza
 *
 */
@Entity
@Table (name = "lu_spnet_document_type")
public class LookupSPNDocumentType extends AbstractLookupDomain {

	/**
	 *
	 */
	private static final long serialVersionUID = 5450983936407996332L;

	@Id  @GeneratedValue(strategy=IDENTITY)
	@Column ( name = "id", unique = true, insertable = true, length = 10, updatable =true , nullable = false)
	private Integer id;

	@Column ( name = "descr", insertable = true, length = 50, updatable =true , nullable = false)
	private String description;
	
	@Column(name="sort_order", unique=false, nullable=true, insertable=true, updatable=true)
	private Integer sortOrder;

	/* (non-Javadoc)
	 * @see com.servicelive.domain.AbstractLookupDomain#getDescription()
	 */
	@Override
	public Object getDescription() {
		return description;
	}

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
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 
	 * @return Integer
	 */
    public Integer getSortOrder() {
        return this.sortOrder;
    }
    /**
     * 
     * @param sortOrder
     */
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

}
