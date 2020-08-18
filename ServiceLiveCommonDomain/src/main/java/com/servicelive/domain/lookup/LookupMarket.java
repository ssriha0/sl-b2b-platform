package com.servicelive.domain.lookup;
// default package


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.servicelive.domain.AbstractLookupDomain;

/**
 * 
 *
 *
 */
@Entity
@Table(name="lu_market" )
public class LookupMarket extends AbstractLookupDomain {

	private static final long serialVersionUID = -6525918825859624376L;

	// Fields
	@Id 
	@Column(name="market_id", unique=true, nullable=false)
    private Integer id;
	  
	@Column(name="market_name", nullable=false, length=100)
     private String description;
	  
    // Constructors

    /** default constructor */
    public LookupMarket(){
    	super();
    }


	@Override
	public Integer getId()
	{
		return id;
	}
	/**
	 * 
	 * @param id
	 */
	public void setId(Integer id)
	{
		this.id = id;
	}

	@Override
	public String getDescription()
	{
		return description;
	}
	/**
	 * 
	 * @param description
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}
}