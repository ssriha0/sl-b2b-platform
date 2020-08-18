package com.servicelive.domain.lookup;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * @author svanloo
 *
 */
@Entity
@Table(name="lu_vendor_credential_category" )
public class LookupVendorCredentialCategory  implements java.io.Serializable {


	private static final long serialVersionUID = -2421976775894719929L;

	// Fields    


	@Id 
	@Column(name="cred_category_id", unique=true, nullable=false)
    private Integer id;

	@ManyToOne(optional=false)
    @JoinColumn(name="cred_type_id",referencedColumnName="cred_type_id")
    private LookupVendorCredentialType credentialType;
	
	@Column(name="cred_category", nullable=false, length=50)
     private String description;
	  
	@Column(name="cred_category_desc", nullable=false, length=255)
    private String categoryDesc;
   


    // Constructors

    /** default constructor */
    public LookupVendorCredentialCategory()
    {	super();
    }


    /**
     * 
     * @return Integer
     */
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
	 * @return the credentialType
	 */
	public LookupVendorCredentialType getCredentialCategory() {
		return credentialType;
	}



	/**
	 * @param credentialType the credentialCategory to set
	 */
	public void setCredentialCategory(
			LookupVendorCredentialType credentialType) {
		this.credentialType = credentialType;
	}







}