package com.servicelive.domain.lookup;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.servicelive.domain.AbstractLookupDomain;

/**
 * 
 *
 */
@Entity
@Table(name="lu_routing_alert_type")
public class LookupRoutingAlertType extends AbstractLookupDomain {

	private static final long serialVersionUID = -5332231645924234960L;
	private Integer id;
	private String description;

	/**
	 * 
	 */
	public LookupRoutingAlertType() {
		super();
	}

	/**
	 * 
	 * @param id
	 */
	public LookupRoutingAlertType(Integer id) {
		this.id = id;
	}

	@Override
	@Column(name="descr", nullable=false, length=30)
	public String getDescription() {
		return this.description;
	}

	@Override
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="alert_type_id", unique=true, nullable=false)
	public Integer getId() {
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
}
