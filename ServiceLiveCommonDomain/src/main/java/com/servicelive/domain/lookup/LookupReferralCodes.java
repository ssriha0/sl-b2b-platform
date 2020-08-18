package com.servicelive.domain.lookup;
// default package

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.servicelive.domain.AbstractLookupDomain;


/**
 * LookupReferralCodes entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="lu_referral_cds")
public class LookupReferralCodes  extends AbstractLookupDomain {


    // Fields    

     /**
	 * 
	 */
	private static final long serialVersionUID = 7652008089260860339L;
	/**
	 * 
	 */
	
	@Id @GeneratedValue(strategy=IDENTITY)
	@Column(name="id", unique=true, nullable=false)
	private Integer id;
	
	  @Column(name="type", nullable=false, length=30)
     private String type;
     
     @Column(name="descr", nullable=false, length=50)
     private String description;
     
     
     @Column(name="sort_order", nullable=false)
     private Integer sortOrder;
   

    // Constructors

    /** default constructor */
    public LookupReferralCodes() {
    	super();
    }

	/**
	 * minimal constructor
	 * @param type
	 * @param descr
	 * @param sortOrder
	 */
    public LookupReferralCodes(String type, String descr, Integer sortOrder) {
        this.type = type;
        this.description = descr;
        this.sortOrder = sortOrder;
    }
   

   
    // Property accessors
  

    @Override
	public Integer getId() {
        return this.id;
    }
    /**
     * 
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
     * 
     * @return String
     */
    public String getType() {
        return this.type;
    }
    /**
     * 
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }
  
    @Override
	public String getDescription() {
        return this.description;
    }
    /**
     * 
     * @param descr
     */
    public void setDescr(String descr) {
        this.description = descr;
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