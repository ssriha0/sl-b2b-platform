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
@Table (name="lu_spnet_release_tier" )
public class LookupReleaseTier extends AbstractLookupDomain {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4085075466192219015L;



	@Id @GeneratedValue(strategy=IDENTITY)
	@Column(name="tier_id", unique=true, nullable=false)
	private Integer id;
	
	    
     @Column(name="descr", nullable=false, length=50)
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
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
