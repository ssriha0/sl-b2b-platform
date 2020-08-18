package com.servicelive.domain.lookup;


import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.servicelive.domain.AbstractLookupDomain;
/*
 * 
 * 
 *
 */
@Entity
@Table(name="lu_vendor_credential_type" )
public class LookupVendorCredentialType  extends AbstractLookupDomain {


	private static final long serialVersionUID = -9192646908258622658L;

	// Fields    


	@Id 
	@Column(name="cred_type_id", unique=true, nullable=false)
    private Integer id;

	@Column(name="cred_type",  nullable=false, length=50)
    private String credType;
	
	
	@Column(name="cred_type_desc", nullable=false, length=255)
    private String description;
	
	@OneToMany(mappedBy="credentialType",targetEntity=LookupVendorCredentialCategory.class,
		       fetch=FetchType.EAGER)
	private List<LookupVendorCredentialCategory> credentialCategories ;

    // Constructors
	/** default constructor */
    public LookupVendorCredentialType()
    {	super();
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
	/**
	 * 
	 * @return String
	 */
	public String getCredType()
	{
		return credType;
	}
	/**
	 * 
	 * @param credType
	 */
	public void setCredType(String credType)
	{
		this.credType = credType;
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
	 * @return the credentialCategories
	 */
	public List<LookupVendorCredentialCategory> getCredentialCategories() {
		return credentialCategories;
	}

	/**
	 * @param credentialCategories the credentialCategories to set
	 */
	public void setCredentialCategories(
			List<LookupVendorCredentialCategory> credentialCategories) {
		this.credentialCategories = credentialCategories;
	}
}