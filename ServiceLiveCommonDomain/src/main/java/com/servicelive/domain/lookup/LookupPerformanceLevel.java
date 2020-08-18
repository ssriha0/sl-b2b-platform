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
@Table (name="lu_spnet_performance_level" )
public class LookupPerformanceLevel extends AbstractLookupDomain implements Comparable<LookupPerformanceLevel> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5616345699280497770L;


	@Id @GeneratedValue(strategy=IDENTITY)
	@Column(name="performance_id", unique=true, nullable=false, insertable=false, updatable=false)
	private Integer id;
	
	    
     @Column(name="descr", nullable=false, length=50, insertable=false, updatable=false)
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

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(LookupPerformanceLevel o) {
		if(o.getId() != null  && this.getId() != null) {
			return (((Integer)this.getId()).intValue() -  ((Integer)o.getId()).intValue() ) ;
		}
		return 0;
	}
	
	

}
