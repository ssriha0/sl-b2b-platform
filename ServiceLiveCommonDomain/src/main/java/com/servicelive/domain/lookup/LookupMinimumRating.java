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
import com.servicelive.domain.LookupDomain;

/**
 * @author hoza
 *
 */
@LookupDomain
@Entity
@Table (name = "lu_spnet_minimum_rating")
public class LookupMinimumRating extends AbstractLookupDomain {

	private static final long serialVersionUID = 20081219L;

	@Id @GeneratedValue(strategy=IDENTITY)
	@Column(name="id", length=11)
	private Integer id;
	
	@Column (name = "type" , length=50)
	private String type;
	
	
	@Column ( name = "descr", length = 50)
	private String description;
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
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
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
