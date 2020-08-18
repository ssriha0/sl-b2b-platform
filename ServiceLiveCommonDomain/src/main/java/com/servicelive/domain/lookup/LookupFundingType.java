package com.servicelive.domain.lookup;
// default package


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.servicelive.domain.AbstractLookupDomain;


/**
 * LookupFundingType entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="lu_funding_type")
@XmlRootElement()
@XmlAccessorType(XmlAccessType.FIELD)
public class LookupFundingType  extends AbstractLookupDomain {


    // Fields    

     /**
	 * 
	 */
	private static final long serialVersionUID = -5730426186809696263L;
	
	@Id 
    @Column(name="funding_type_id", unique=true, nullable=false)
	private Integer id;
	
	
	 @Column(name="type", length=50)
     private String type;
     
	 @Column(name="descr")
     private String description;
	 
	 @Column(name="sort_order")
     private Integer sortOrder;
    


    // Constructors

    /** default constructor */
    public LookupFundingType() {
    	super();
    }

	/**
	 * minimal constructor
	 * @param id
	 */
    public LookupFundingType(Integer id) {
        this.id = id;
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
    public void setDescription(String descr) {
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