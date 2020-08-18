package com.servicelive.domain.lookup;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.servicelive.domain.AbstractLookupDomain;

/**
 * 
 * @author svanloo
 *
 */
@Entity
@Table(name="lu_resource_credential_category" )
public class LookupResourceCredentialCategory  extends AbstractLookupDomain {


	private static final long serialVersionUID = -285397672012961941L;

	// Fields    

	@Id 
	@Column(name="cred_category_id", unique=true, nullable=false)
    private Integer id;

	@ManyToOne(optional=false)
    @JoinColumn(name="cred_type_id",referencedColumnName="cred_type_id")
    private LookupResourceCredentialType credentialType;
	
	
	@Column(name="cred_category", nullable=true, length=50)
     private String description;
	  
	@Column(name="cred_category_desc", nullable=true, length=255)
    private String categoryDesc;

	@Column(name="buyer_id",  nullable=true)
    private Integer buyerId;

	@Column(name="sort_order", nullable=false)
	private Integer sortOrder;

    // Constructors

    /** default constructor */
    public LookupResourceCredentialCategory() {
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



	/**
	 * 
	 * @return Integer
	 */
	public Integer getBuyerId()
	{
		return buyerId;
	}

	/**
	 * 
	 * @param buyerId
	 */
	public void setBuyerId(Integer buyerId)
	{
		this.buyerId = buyerId;
	}

	/**
	 * 
	 * @return String
	 */
	public String getCategoryDesc()
	{
		return categoryDesc;
	}

	/**
	 * 
	 * @param categoryDesc
	 */
	public void setCategoryDesc(String categoryDesc)
	{
		this.categoryDesc = categoryDesc;
	}


	/**
	 * @return the sortOrder
	 */
	public Integer getSortOrder() {
		return sortOrder;
	}

	/**
	 * @param sortOrder the sortOrder to set
	 */
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	/**
	 * @return the credentialType
	 */
	public LookupResourceCredentialType getCredentialCategory() {
		return credentialType;
	}

	/**
	 * @param credentialType the credentialType to set
	 */
	public void setCredentialCategory(
			LookupResourceCredentialType credentialType) {
		this.credentialType = credentialType;
	}

}