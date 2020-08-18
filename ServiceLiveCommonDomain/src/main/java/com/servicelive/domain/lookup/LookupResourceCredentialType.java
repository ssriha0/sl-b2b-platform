package com.servicelive.domain.lookup;


import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.servicelive.domain.AbstractLookupDomain;

/**
 * 
 * 
 *
 */
@Entity
@Table(name="lu_resource_credential_type" )
public class LookupResourceCredentialType  extends AbstractLookupDomain {


	// Fields    

	private static final long serialVersionUID = 5562769947741358564L;

	@Id 
	@Column(name="cred_type_id", unique=true, nullable=false)
    private Integer id;

	@Column(name="cred_type",  nullable=true, length=30)
    private String credType;
	
	
	@Column(name="cred_type_desc", nullable=false, length=50)
     private String description;
	
	@OneToMany(mappedBy="credentialType",targetEntity=LookupResourceCredentialCategory.class,
		       fetch=FetchType.EAGER)
	private List<LookupResourceCredentialCategory> credentialCategories ;
	  
   

	// Constructors
    /**
     * 
     */
	public LookupResourceCredentialType() {
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
	public List<LookupResourceCredentialCategory> getCredentialCategories() {
		return credentialCategories;
	}




	/**
	 * @param credentialCategories the credentialCategories to set
	 */
	public void setCredentialCategories(
			List<LookupResourceCredentialCategory> credentialCategories) {
		this.credentialCategories = credentialCategories;
	}


}